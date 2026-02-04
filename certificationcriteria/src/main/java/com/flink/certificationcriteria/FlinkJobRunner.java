package com.flink.certificationcriteria;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.formats.json.JsonDeserializationSchema;
import org.apache.flink.formats.json.JsonSerializationSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.flink.certificationcriteria.models.CourseCertification;

@Component
public class FlinkJobRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        KafkaSource<CourseCertification> source = KafkaSource.<CourseCertification>builder()
                .setBootstrapServers("localhost:9092")
                .setTopics("course-created")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new JsonDeserializationSchema<>(CourseCertification.class))
                .build();

        DataStream<CourseCertification> stream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "kafka-create-course");

        stream.print();

        DataStream<CourseCertification> result = generateCertificationRules(stream);

        JsonSerializationSchema<CourseCertification> jsonSerializer = 
            new JsonSerializationSchema<>(() -> new ObjectMapper());


        KafkaSink<CourseCertification> jsonSink = KafkaSink.<CourseCertification>builder()
            .setBootstrapServers("localhost:9092")
            .setRecordSerializer(
                KafkaRecordSerializationSchema.<CourseCertification>builder()
                    .setTopic("issued-certificates")
                    .setValueSerializationSchema(jsonSerializer)
                    .build()
            )
            .build();


        result.sinkTo(jsonSink);
        result.print();

        env.execute("Course Certification Streaming Job");
    }

    public DataStream<CourseCertification> generateCertificationRules(DataStream<CourseCertification> source){
        return source.filter(course -> course.getBoard() != null)
        .map(course -> {
                if ("CBSE".equalsIgnoreCase(course.getBoard()) && course.getUnitCount() < 2){
                    course.setRules("80% completion required for certificate");
                }  else if ("ICSE".equalsIgnoreCase(course.getBoard()) && course.getUnitCount() < 4){
                    course.setRules("90% completion required for certification");
                }
                else{
                    course.setRules("70% completion required for certification");
                }

            return course;
        });
    }
}