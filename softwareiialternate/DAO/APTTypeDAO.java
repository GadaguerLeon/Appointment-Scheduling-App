/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiialternate.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import softwareiialternate.model.APTtype;

/**
 *
 * 
 */
public class APTTypeDAO {
    
    public static ArrayList<APTtype> getAllAPTTypes() throws Exception {
        
        String sql = "SELECT APTtype.APTtype_id, APTtype.description "
   
                + "FROM APTtype ";
        
        try{
         
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
          
            ArrayList<APTtype> aptList = new ArrayList<>();
            
            while(rs.next()){
            
                APTtype aptType = new APTtype(rs.getInt("APTtype_id"), rs.getString("description"));
               
                aptList.add(aptType);
         
            }
           
            return aptList;
       
        }
       
        catch(SQLException e){
            
            System.out.println("Please check your SQL and try again");
        
        }
      
        return null;
   
    }    
    
}
