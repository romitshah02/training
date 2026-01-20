package com.sunbird.training.service;

import java.util.List;

import com.sunbird.training.entity.Unit;

public interface UnitService {
    List<Unit> findAll();
    Unit findById(int id);
    void save(Unit unit);
    void deleteById(int id);
    List<Unit> findAllByCourseId(int id);
}
