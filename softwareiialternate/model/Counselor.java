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
public class Counselor {
    
    private int counselorId;
    private String counselorName;
    private String counselorPassword;
    private int counselorPin;
        
 
    //Conctructor
    public Counselor(int counselorId, String counselorName, String counselorPassword, int CounselorPin){
    
        this.counselorId = counselorId;
        this.counselorName = counselorName;
        this.counselorPassword = counselorPassword;
        this.counselorPin = counselorPin;
        
    }
    
    //Setters
    
    public void setCounselorId(int counselorId) {
    
        this.counselorId = counselorId;
        
    }
    
    public void setCounselorName(String counselorName) {
    
        this.counselorName = counselorName;
        
    }
    
    public void setCounselorPassword(String counselorPassword) {
    
        this.counselorPassword = counselorPassword;
        
    }
    
    public void setCounselorPin(int counselorPin) {
    
        this.counselorPin = counselorPin;
        
    }
    
    
    
    //Getters
    public int getCounselorId() {
    
        return counselorId;
    
    }
    
    public String getCounselorName() {
    
        return counselorName;
        
    }
    
    public String getCounselorPassword() {
    
        return counselorPassword;
        
    }
    
    public int getCounselorPin() {
    
        return counselorPin;
    
    }
}
