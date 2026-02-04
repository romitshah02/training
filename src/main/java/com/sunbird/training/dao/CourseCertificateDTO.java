package com.sunbird.training.dao;

import com.sunbird.training.entity.Course;

public class CourseCertificateDTO {

    private int courseId;
    private String board;
    private int unitCount;
    private String rules;


    public static CourseCertificateDTO courseToCertificate(Course course){
        CourseCertificateDTO certificate = new CourseCertificateDTO();
        certificate.setCourseId(course.getId());
        certificate.setBoard(course.getBoard().toString());
        certificate.setUnitCount(course.getUnits().size());

        return certificate;
    }


    public int getCourseId() {
        return courseId;
    }
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
    public String getBoard() {
        return board;
    }
    public void setBoard(String board) {
        this.board = board;
    }
    public int getUnitCount() {
        return unitCount;
    }
    public void setUnitCount(int unitCount) {
        this.unitCount = unitCount;
    }
    public String getRules() {
        return rules;
    }
    public void setRules(String rules) {
        this.rules = rules;
    }
    
  


}
