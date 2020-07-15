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
public class APTtype {
    
    private int APTtypeid;
    private String description;
  
    
    //Constructor
    public APTtype (int aptTypeId, String description) {
    
        this.APTtypeid = aptTypeId;
     
        this.description = description;
        
    }

    //Getters and Setters
    public int getApttypeid() {
     
        return APTtypeid;
  
    }

    public void setApttypeid(int APTtypeid) {
    
        this.APTtypeid = APTtypeid;
   
    }

    public String getDescription() {
    
        return description;
   
    }

    public void setDescription(String description) {
    
        this.description = description;
   
    }        
    
}
