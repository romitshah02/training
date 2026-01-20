package com.sunbird.training.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.sunbird.training.dao.UnitRepository;
import com.sunbird.training.entity.Unit;

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
        return unitRepository.findById(id).orElseThrow(() -> new RuntimeException("No unit found"));
    }

    @Override
    public void save(Unit unit) {
        unitRepository.save(unit);
    }

    @Override
    public void deleteById(int id) {
        unitRepository.deleteById(id);
    }

}
