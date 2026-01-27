package com.sunbird.training.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sunbird.training.config.LowercaseDeserializer;
import com.sunbird.training.enums.Board;
import com.sunbird.training.enums.Grade;
import com.sunbird.training.enums.Medium;
import com.sunbird.training.enums.Subject;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tools.jackson.databind.annotation.JsonDeserialize;





@Entity
@Table(
    name = "course",
    indexes = @Index(name = "idx_filters",columnList = "board,medium,grade,subject")
)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    
    @Column(name = "name",nullable = false)
    @NotBlank(message = "Course Name is required")
    @JsonDeserialize(using = LowercaseDeserializer.class)
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "board",nullable = false)
    @NotNull(message = "Board is required")
    @Enumerated(EnumType.STRING)
    private Board board;

    @Column(name = "medium",nullable = false)
    @NotNull(message = "Medium is required")
    @Enumerated(EnumType.STRING)
    private Medium medium;

    
    @Column(name = "grade",nullable = false)
    @Enumerated(EnumType.STRING)    
    @NotNull(message = "Grade is required")
    private Grade grade;

    
    @Column(name = "subject",nullable = false)
    @NotNull(message = "Subject is required")
    @Enumerated(EnumType.STRING)
    private Subject subject;

    @OneToMany(
        mappedBy = "course",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Column(name = "units")
    @JsonManagedReference
    @Valid
    private List<Unit> units;

    public Course(){

    }

    public Course(String name, String description, Board board, Medium medium, Grade grade, Subject subject,
            List<Unit> units) {
        this.name = name;
        this.description = description;
        this.board = board;
        this.medium = medium;
        this.grade = grade;
        this.subject = subject;
        this.units = units;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "Course [id=" + id + ", name=" + name + ", description=" + description + ", board=" + board + ", medium="
                + medium + ", grade=" + grade + ", subject=" + subject + ", units=" + units + "]";
    }


    public void addUnit(Unit unit){

        if (units.isEmpty() || units == null){
            units = new ArrayList<>();
        }

        units.add(unit);
        unit.setCourse(this);
    }

    
    
}
