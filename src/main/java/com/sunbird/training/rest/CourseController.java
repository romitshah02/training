package com.sunbird.training.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunbird.training.dao.ApiResponse;
import com.sunbird.training.dao.CourseRepository;
import com.sunbird.training.dao.ResponseParams;
import com.sunbird.training.entity.Course;
import com.sunbird.training.service.CourseService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class CourseController {

    
    private final CourseService courseService;

    public CourseController(CourseService courseService, CourseRepository courseRepository) {
        this.courseService = courseService;
  
    }

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
    
    @PostMapping("/course")
    public ResponseEntity<ApiResponse<Map<String,String>>> addCourse(@RequestBody Course course) {
            
        course.setId(0);

        courseService.save(course);
        
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
    
   @PutMapping("/course")
    public ResponseEntity<ApiResponse<Map<String,String>>> updateCourse(@RequestBody Course course) {
            
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
        "api.course.delete"
        ,"v1", LocalDate.now().toString()
        , params
        , "OK"
        , Map.of("message", "Course deleted successfully")
        );

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
    

}
