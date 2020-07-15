/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiialternate.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 *
 */
public class Appointment {

    private int aptid;
    private int pid;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String notes;
    private int APTtypeid;
    private int conslrid;
    private String consname;
    private String patname;
    private String APTtypedesc;

    public Appointment() {
        aptid = -1;
        pid = -1;
        pid = -1;
        pid = -1;
        notes = "";
        startDate = null;
        endDate = null;
        consname = "";
        patname = "";
        APTtypedesc = "";
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public LocalDateTime getStartDate(){
        return startDate;
    }
    
    public void setStarteDate(LocalDateTime startDate){
        this.startDate = startDate;
    }
    
    public LocalDateTime getEndDate(){
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate){
        this.endDate = endDate;
    }
    

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getApttypeid() {
        return APTtypeid;
    }

    public void setApttypeid(int APTtypeid) {
        this.APTtypeid = APTtypeid;
    }

    public int getConslrid() {
        return conslrid;
    }

    public void setConslrid(int conslrid) {
        this.conslrid = conslrid;
    }

    public int getAptid() {
        return aptid;
    }

    public void setAptid(int aptid) {
        this.aptid = aptid;
    }

    public String getConsname() {
        return consname;
    }

    public void setConsname(String consname) {
        this.consname = consname;
    }

    public String getPatname() {
        return patname;
    }

    public void setPatname(String patname) {
        this.patname = patname;
    }

    public String getApttypedesc() {
        return APTtypedesc;
    }

    public void setApttypedesc(String APTtypedesc) {
        this.APTtypedesc = APTtypedesc;
    }

    @Override
    public String toString(){
        return patname + " has appointment at " + startDate +"\n";
    }
}