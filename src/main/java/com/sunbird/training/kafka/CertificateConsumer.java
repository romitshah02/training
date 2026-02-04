package com.sunbird.training.kafka;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.sunbird.training.dao.CertificateRepository;
import com.sunbird.training.dao.CourseCertificateDTO;
import com.sunbird.training.dao.CourseRepository;
import com.sunbird.training.entity.Certificate;
import com.sunbird.training.entity.Course;

@Component
public class CertificateConsumer {


  
    private CertificateRepository certificateRepository;
    private CourseRepository courseRepository;

    public CertificateConsumer(CertificateRepository certificateRepository, CourseRepository courseRepository) {
        this.certificateRepository = certificateRepository;
        this.courseRepository = courseRepository;
    }

    @KafkaListener(topics = "issued-certificates", groupId = "training-group")
    public void consumeProcessedCertificate(CourseCertificateDTO certificateDTO) {
        System.out.println("Received processed certificate for Course ID: " + certificateDTO.getCourseId());

    
        Course course = courseRepository.findById(certificateDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found for ID: " + certificateDTO.getCourseId()));

        Certificate certificate = new Certificate();
        certificate.setBoard(certificateDTO.getBoard());
        certificate.setCourse(course);
        certificate.setRules(certificateDTO.getRules());
            
        certificateRepository.save(certificate);
        
        System.out.println("Successfully saved certification rules for: " + course.getName());
    }

}
