package com.sunbird.training.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sunbird.training.dao.UnitRepository;
import com.sunbird.training.entity.Unit;
import com.sunbird.training.exception.ResourceNotFoundException;
import com.sunbird.training.service.UnitService;

@Service
public class UnitServiceImpl implements UnitService {


    private final UnitRepository unitRepository;

    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Override
    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    @Override
    public Unit findById(int id) {
        return unitRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No unit found"));
    }

    @Override
    @Transactional
    public void save(Unit unit) {
        unitRepository.save(unit);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        unitRepository.deleteById(id);
    }

    @Override
    public List<Unit> findAllByCourseId(int id) {
        return unitRepository.findByCourseId(id);
    }

}
