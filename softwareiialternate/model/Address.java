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
public class Address {
    
    private int addressId;
    private String addressLineOne;
    private String addressLineTwo;
    private String city;
    private String state;
    private String postalCode;
    private String phoneNumber;
    
    
    //Constructor
    public Address(int addressId, String addressLineOne, String addressLineTwo, String city, String state, String postalCode, String phoneNumber) {
    
        this.addressId = addressId;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    
    }
    
    //Setters
    public void setAddressId(int addressId) {
    
        this.addressId = addressId;
        
    }
    
    public void setAddressLineOne(String addressLineOne) {
    
        this.addressLineOne = addressLineOne;
        
    }
    
    public void setAddressLineTwo(String addressLineTwo) {
    
        this.addressLineTwo = addressLineTwo;
        
    }
    
    public void setCity(String city) {
    
        this.city = city;
        
    }
    
    public void setState(String state) {
    
        this.state = state;
        
    }
    
    public void setPostalCode(String postalCode) {
    
        this.postalCode = postalCode;
        
    }
    
    public void setPhoneNumber(String phoneNumber) {
    
        this.phoneNumber = phoneNumber;
        
    }
 
    //Getters
    public int getAddressId() {
    
        return addressId;
        
    }
    
    public String getAddressLineOne() {
    
        return addressLineOne;
        
    }
    
    public String getAddressLineTwo() {
    
        return addressLineTwo;
        
    }
   
    public String getCity() {
    
        return city;
        
    }
    
    public String getState() {
    
        return state;
        
    }
    
    public String getPostalCode() {
    
        return postalCode;
        
    }
    
    public String getPhoneNumber() {
    
        return phoneNumber;
        
    }
}
