package com.sunbird.training.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sunbird.training.dao.CourseRepository;
import com.sunbird.training.entity.Course;
import com.sunbird.training.service.CourseService;

import java.util.List;

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
    public List<Course> getCourses() {
        return courseService.findAll();
    }

    @GetMapping("/course/{courseId}")
    public Course getCourseById(@PathVariable int courseId) {
        return courseService.findById(courseId);
    }
    
    @PostMapping("/course")
    public void addCourse(@RequestBody Course course) {
            
        course.setId(0);

        courseService.save(course);
    }
    
   @PutMapping("/course")
    public void updateCourse(@RequestBody Course course) {
            
        courseService.save(course);
    }

    @DeleteMapping("/course/{courseId}")
    public void deleteCourse(@PathVariable int courseId){
        courseService.deleteById(courseId);
    }
    

}
