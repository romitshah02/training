package com.sunbird.training.service;

import java.util.List;

import com.sunbird.training.entity.Course;
import com.sunbird.training.entity.Unit;


public interface CourseService {

    List<Course> findAll();
    Course findById(int id);
    void save(Course course);
    void deleteById(int id);
    void addUnit(Unit unit,int courseId);
}
