package com.sunbird.training.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sunbird.training.dao.CourseRepository;
import com.sunbird.training.entity.Course;
import com.sunbird.training.entity.Unit;

import jakarta.transaction.Transactional;

@Service
public class CourseServiceImpl implements CourseService{

    private CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll(){
        List<Course> courses = courseRepository.findAll();
        return courses;
    }

    @Transactional
    public void save(Course course){
        if (course.getUnits() != null) {
        for (Unit unit : course.getUnits()) {
            unit.setCourse(course); // Link each unit back to this course
        }
    }
        courseRepository.save(course);
    }

    public Course findById(int id){

        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("No Such User"));
    }   

    @Transactional
    public void deleteById(int id){
        courseRepository.deleteById(id);
    }

    @Transactional
    public void addUnit(Unit unit,int courseId){
        Course course =  courseRepository.findById(courseId)
        .orElseThrow(() -> new RuntimeException("Course not found"));
  
        course.addUnit(unit);

        courseRepository.save(course);
   
    }

}
