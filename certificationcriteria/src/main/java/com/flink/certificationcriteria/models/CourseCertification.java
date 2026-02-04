package com.flink.certificationcriteria.models;


public class CourseCertification {

    
    private int courseId;

    @Override
    public String toString() {
        return "CourseCertification [courseId=" + courseId + ", board=" + board + ", unitCount=" + unitCount
                + ", rules=" + rules + "]";
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    private String board;

    private int unitCount;

    private String rules;
    
    public CourseCertification(){
        
    }
    
    public CourseCertification(int courseId, String board, int unitCount) {
        this.courseId = courseId;
        this.board = board;
        this.unitCount = unitCount;
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

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

   

}
