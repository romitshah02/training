package com.sunbird.training.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

enum Board{ CBSE,ICSE,HSC}
enum Medium{HINDI,ENGLISH,MARATHI}
enum Grade{CLASS1,CLASS2,CLASS3}
enum Subject{MATHS,ENGLISH,HINDI}

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "board")
    @Enumerated(EnumType.STRING)
    private Board board;

    @Column(name = "medium")
    @Enumerated(EnumType.STRING)
    private Medium medium;

    
    @Column(name = "grade")
    @Enumerated(EnumType.STRING)
    private Grade grade;

    
    @Column(name = "subject")
    @Enumerated(EnumType.STRING)
    private Subject subject;

    @OneToMany(
        mappedBy = "course",
        cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
        }
    )
    @Column(name = "units")
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


    
    
}
