package com.sunbird.training.service;

import java.util.List;

import com.sunbird.training.dao.CourseRepository;
import com.sunbird.training.entity.Course;

public class CourseServiceImpl implements CourseService{

    private CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll(){
        List<Course> courses = courseRepository.findAll();
        return courses;
    }

    public void save(Course course){
        courseRepository.save(course);
    }

    public Course findById(int id){

        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("No Such User"));
    }   

    public void deleteById(int id){
        courseRepository.deleteById(id);
    }

}
