package com.sunbird.training.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbird.training.entity.Course;

public interface CourseRepository extends JpaRepository<Course,Integer> {

}
