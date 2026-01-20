package com.sunbird.training.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sunbird.training.entity.Unit;

public interface UnitRepository extends JpaRepository<Unit,Integer>{
    List<Unit> findByCourseId(int courseId);
}
