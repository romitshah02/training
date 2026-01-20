package com.sunbird.training.service;

import java.util.List;

import com.sunbird.training.entity.Course;
import com.sunbird.training.entity.Unit;
import com.sunbird.training.enums.Board;
import com.sunbird.training.enums.Grade;
import com.sunbird.training.enums.Medium;
import com.sunbird.training.enums.Subject;


public interface CourseService {

    List<Course> findAll();
    Course findById(int id);
    void save(Course course);
    void deleteById(int id);
    void addUnit(Unit unit,int courseId);
    List<Course> searchCourses(Board board, Medium medium, Grade grade, Subject subject);

}
