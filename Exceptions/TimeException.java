/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

import javafx.scene.control.Alert;

/**
 *
 * 
 */
public class TimeException extends Exception {
    
    public TimeException(String message) {
    
        super(message);
     
        Alert alert = new Alert (Alert.AlertType.ERROR);
        
        alert.setTitle("Time Exception");
        
        alert.setHeaderText("Error");
        
        alert.setContentText(message);
        
        alert.show();
        
    }
    
}
