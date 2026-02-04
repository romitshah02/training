package com.sunbird.training.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunbird.training.dao.ApiResponse;
import com.sunbird.training.dao.CourseCertificateDTO;
import com.sunbird.training.dao.ResponseParams;
import com.sunbird.training.entity.Course;
import com.sunbird.training.entity.Unit;
import com.sunbird.training.service.CourseService;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
@Validated
public class CourseController {
  
    private final CourseService courseService;
    private KafkaTemplate<String,Object> kafkaTemplate;

    public CourseController(CourseService courseService,KafkaTemplate<String,Object> kafkaTemplate) {
        this.courseService = courseService;
        this.kafkaTemplate = kafkaTemplate;
    }

    //*find all courses
    @GetMapping("/courses")
    public ResponseEntity<ApiResponse<List<Course>>> getCourses() {
      
        List<Course> courses = courseService.findAll();

        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString()
            , "success"
            , null
            , null
        );

        ApiResponse<List<Course>> response = new ApiResponse<List<Course>>(
        "api.course.get",
        "v1",
        LocalDate.now().toString(), 
        params, 
        "OK", 
        courses
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //*find course by id 
    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<Course>> getCourseById(@PathVariable int courseId) {

        Course course = courseService.findById(courseId);

        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString()
            , "success"
            , null
            , null
        );

        ApiResponse<Course> response = new ApiResponse<Course>(
        "api.course.get.byId",
        "v1",
        LocalDate.now().toString(), 
        params, 
        "OK", 
        course
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
    
    // //*Create Course
    @PostMapping("/course")
    public ResponseEntity<ApiResponse<Map<String,String>>> addCourse(@Valid @RequestBody Course course) {


        course.setId(0);

        course =  courseService.save(course);

        System.out.println("Sending to kafka..............");

        kafkaTemplate.send("course-created",CourseCertificateDTO.courseToCertificate(course));

        
        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString()
            , "success"
            , null
            , null
        );

        ApiResponse<Map<String,String>> response = new ApiResponse<Map<String,String>>(
        "api.course.create"
        ,"v1", LocalDate.now().toString()
        , params
        , "OK"
        , Map.of("message", "Course created successfully")
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
  
    //*update course
   @PutMapping("/course")
    public ResponseEntity<ApiResponse<Map<String,String>>> updateCourse(@Valid @RequestBody Course course) {
            
        courseService.save(course);
    
        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString()
            , "success"
            , null
            , null
        );

        ApiResponse<Map<String,String>> response = new ApiResponse<Map<String,String>>(
        "api.course.update"
        ,"v1", LocalDate.now().toString()
        , params
        , "OK"
        , Map.of("message", "Course updated Successfully")
        );

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    // *delete course
    @DeleteMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<Map<String,String>>> deleteCourse(@PathVariable int courseId){
        courseService.deleteById(courseId);

        
        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString()
            , "success"
            , null
            , null
        );

        ApiResponse<Map<String,String>> response = new ApiResponse<Map<String,String>>(
        "api.course.delete",
        "v1",
        LocalDate.now().toString(),
        params, 
        "OK", 
        Map.of("message", "Course deleted successfully")
        );

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
    

    // *find courses by filters
    @PostMapping("/course/search")
    public ResponseEntity<ApiResponse<List<Course>>> getCoursesByFilters(
        @RequestBody Course course
     ) {
    
        List<Course> courses = courseService.searchCourses(course.getName(),course.getBoard(),course.getMedium(), course.getGrade(), course.getSubject());

        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString()
            , "success"
            , null
            , null
        );

        ApiResponse<List<Course>> response = new ApiResponse<List<Course>>(
        "api.course.get.byFilters",
        "v1",
        LocalDate.now().toString(), 
        params, 
        "OK", 
        courses
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    // *Add unit to a course
    @PostMapping("/course/unit/{courseId}")
    public ResponseEntity<ApiResponse<Map<String,String>>> addUnitToCourse(@PathVariable int courseId,@Valid @RequestBody Unit unit) {
        
        courseService.addUnit(unit, courseId);

        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString(),
            "success",
            null,
            null
        );

        ApiResponse<Map<String,String>> response = new ApiResponse<Map<String,String>>(
            "api.course.unit.add",
            "v1",
            LocalDate.now().toString(),
            params,
            "OK",
            Map.of("message","unit added successfully")
        );

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
    
}
