package com.sunbird.training.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sunbird.training.dao.ApiResponse;
import com.sunbird.training.dao.ResponseParams;
import com.sunbird.training.entity.Unit;
import com.sunbird.training.service.UnitService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
public class UnitController {

    private UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    // *find all units
    @GetMapping("/units")
    public ResponseEntity<ApiResponse<List<Unit>>> getUnits() {
     List<Unit> units = unitService.findAll();

        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString()
            , "success"
            , null
            , null
        );

        ApiResponse<List<Unit>> response = new ApiResponse<List<Unit>>(
        "api.unit.get",
        "v1",
        LocalDate.now().toString(), 
        params, 
        "OK", 
        units
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // *find unit by id
    @GetMapping("/unit/{unitId}")
    public ResponseEntity<ApiResponse<Unit>> getunitById(@PathVariable int unitId) {
        
        Unit unit = unitService.findById(unitId);

        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString()
            , "success"
            , null
            , null
        );

        ApiResponse<Unit> response = new ApiResponse<Unit>(
        "api.unit.get.byId",
        "v1",
        LocalDate.now().toString(), 
        params, 
        "OK", 
        unit
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // *uodate an unit
    @PutMapping("/unit")
    public ResponseEntity<ApiResponse<Map<String,String>>> updateUnit(@RequestBody Unit unit) {

        unitService.save(unit);

        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString()
            , "success"
            , null
            , null
        );

        ApiResponse<Map<String,String>> response = new ApiResponse<Map<String,String>>(
        "api.unit.update",
        "v1",
        LocalDate.now().toString(), 
        params, 
        "OK", 
        Map.of("message","updated unit successfully")
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // *delete an unit
    @DeleteMapping("/unit/{unitId}")
    public ResponseEntity<ApiResponse<Map<String,String>>> deleteUnit(@PathVariable int unitId) {

        unitService.deleteById(unitId);

        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString()
            , "success"
            , null
            , null
        );

        ApiResponse<Map<String,String>> response = new ApiResponse<Map<String,String>>(
        "api.unit.delete",
        "v1",
        LocalDate.now().toString(), 
        params, 
        "OK", 
        Map.of("message","deleted unit successfully")
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // *find all units by course id
    @GetMapping("/unit/course/{courseId}")
    public ResponseEntity<ApiResponse<List<Unit>>> getUnitsByCourseId(@PathVariable int courseId) {
       
    List<Unit> unit = unitService.findAllByCourseId(courseId);

        ResponseParams params = new ResponseParams(
            UUID.randomUUID().toString()
            , "success"
            , null
            , null
        );

        ApiResponse<List<Unit>> response = new ApiResponse<List<Unit>>(
        "api.unit.get.byId",
        "v1",
        LocalDate.now().toString(), 
        params, 
        "OK", 
        unit
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
    

    

}
