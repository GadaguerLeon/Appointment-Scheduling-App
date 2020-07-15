/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiialternate.model;

/**
 *
 * 
 */
public class Patient {

    private int pid;
    private int aid;
    private String pname;
    private String paddress;
    private String pinspr;
    private String pphone;
    private String pstate;
    private String pzipcode;
    private String pcity;

    public Patient() {
        this.paddress = "";
        this.pname = "";
        this.pid = -1;
        this.aid = -1;
        this.pinspr = "";
        this.pphone = "";
        this.pstate = "";
        this.pcity = "";
        this.pzipcode = "";
    }

    
    //Constructor
    public Patient(int pId, int aId, String pName, String pAddress, String pInsPr, String pPhone,
            String pState, String pZipCode, String pCity) {

        this.paddress = pAddress;
        this.pname = pName;
        this.pid = pId;
        this.aid = aId;
        this.pinspr = pInsPr;
        this.pphone = pPhone;
        this.pstate = pState;
        this.pcity = pCity;
        this.pzipcode = pZipCode;
    }

    public Patient(int pid, String pname) {
        this.pid = pid;
        this.pname = pname;
    }
    
    //Getters and Setters

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPaddress() {
        return paddress;
    }

    public void setPaddress(String paddress) {
        this.paddress = paddress;
    }

    public String getPinspr() {
        return pinspr;
    }

    public void setPinspr(String pinspr) {
        this.pinspr = pinspr;
    }

    public String getPphone() {
        return pphone;
    }

    public void setPphone(String pphone) {
        this.pphone = pphone;
    }

    public String getPstate() {
        return pstate;
    }

    public void setPstate(String pstate) {
        this.pstate = pstate;
    }

    public String getPzipcode() {
        return pzipcode;
    }

    public void setPzipcode(String pzipcode) {
        this.pzipcode = pzipcode;
    }

    public String getPcity() {
        return pcity;
    }

    public void setPcity(String pcity) {
        this.pcity = pcity;
    }

}
