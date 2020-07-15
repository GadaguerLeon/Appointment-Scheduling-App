/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiialternate.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import softwareiialternate.model.Patient;

/**
 *
 * 
 */
public class PatientDAO {
    
    
    public static ArrayList<Patient> getAllPatients() throws ClassNotFoundException, SQLException, Exception{
       
        String sql = "SELECT patient.pt_id,address.address_id,patient.pt_name, address.phone, address.state,address.city,address.state,address.addressline_1,address.postal_code, patient.INS_PR "
                + "FROM patient "
                + "LEFT JOIN address ON patient.address_address_id = address.address_id";
        
        try{
        
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            ArrayList<Patient> patList = getPatientObjects(rs);
            
            return patList;
            
        }
       
        catch(SQLException e){
       
            System.out.println("Please check your SQL and try again");
           
            e.printStackTrace();
           
            throw e;
        }

    } 

    private static ArrayList<Patient> getPatientObjects(ResultSet rs) throws ClassNotFoundException, SQLException, Exception{

        try{
     
            ArrayList<Patient> patList = new ArrayList<>();
        
        while(rs.next()){
        
            Patient pat = new Patient(rs.getInt("pt_id"),rs.getInt("address_id"),rs.getString("pt_name") ,rs.getString("addressline_1") ,rs.getString("INS_PR") ,rs.getString("phone") ,rs.getString("state") ,rs.getString("postal_code") , rs.getString("city"));
            
            patList.add(pat);
      
        }
      
        return patList;
        
        }catch(SQLException e){
      
            System.out.println("Please check your SQL and try again");
         
            e.printStackTrace();
          
            throw e;
        
        }

    }

    public static boolean addPatient (String patientName, String insPr, String phoneNumber,String addressLineTwo, String city, String state, String zipCode) throws ClassNotFoundException, SQLException, Exception {
        
        
        try{
            
            Statement st = DBConnection.getConnection().createStatement();
      
            ResultSet rs = st.executeQuery("select MAX(address_id) as last_id from U05vuW.address");
          
            int lastid;
         
            if(rs != null && rs.next()){
         
                lastid = rs.getInt("last_id") + 1;
           
            }else {
         
                lastid = 1;
          
            }
          
            String sql = "INSERT INTO U05vuW.address(address_id,addressline_1, city, state, postal_code, phone) "
                + " VALUES ("+lastid+",'"+addressLineTwo+"', '"+city+"', '"+state+"', '"+zipCode+"', '"+phoneNumber+"')";
          
            System.out.println(sql);
          
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
          
            stmt.executeUpdate();
            
            if (lastid != -1) 
         
            {
                int id;
       
                st = DBConnection.getConnection().createStatement();
               
                rs = st.executeQuery("select MAX(pt_id) as last_id from U05vuW.patient");
             
                if(rs != null && rs.next()){
             
                    id = rs.getInt("last_id") + 1;
               
                }else {
             
                    id = 1;
              
                }
                
                String sqlPatient = " INSERT INTO U05vuW.patient (pt_id,pt_name, address_id, INS_PR, address_address_id) "
                + " VALUES ("+id+",'"+patientName+"',"+ lastid+", '"+insPr+"', "+ lastid+")"; 
             
                System.out.println(sqlPatient);
              
                PreparedStatement stmt2 = DBConnection.getConnection().prepareStatement(sqlPatient);
               
                stmt2.executeUpdate();
           
            }    
          
            return true;
       
        }catch(SQLException e){
        
            System.out.println("Please check your SQL and try again");
    
            e.printStackTrace();
          
            return false;
      
        }
        
        
    }   

    public static boolean updatePatient(Patient selectedPatient) {
       
        try{
            
            String sql = "UPDATE U05vuW.address SET addressline_1 = '"+ selectedPatient.getPaddress() +"', city =  '"+ selectedPatient.getPcity()
                        +"',state =  '"+selectedPatient.getPstate()+"', postal_code = '"+ selectedPatient.getPzipcode() +"', phone = '"
                        + selectedPatient.getPphone() +"' where address_id = " + selectedPatient.getAid();
          
            System.out.println(sql);
      
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
    
            stmt.executeUpdate();

            String sqlPatient = "UPDATE U05vuW.patient SET pt_name = '"+ selectedPatient.getPname() +"', INS_PR =  '"+ selectedPatient.getPinspr()
                        + "' where pt_id = " + selectedPatient.getPid();
            
            System.out.println(sqlPatient);
          
            PreparedStatement stmt2 = DBConnection.getConnection().prepareStatement(sqlPatient);
            
            stmt2.executeUpdate();   
           
            return true;
       
        }catch(SQLException e){
        
            System.out.println("Please check your SQL and try again");
      
            e.printStackTrace();
         
            return false;
       
        } catch (Exception ex) {
      
            Logger.getLogger(PatientDAO.class.getName()).log(Level.SEVERE, null, ex);
          
            return false;
     
        }
  
    }

    public static boolean deletePatient(Patient selectedPatient) throws Exception {
    
        try{
                   
            String sqlPatient = "DELETE FROM U05vuW.patient where pt_id = " + selectedPatient.getPid();
            
            System.out.println(sqlPatient);
            
            PreparedStatement stmt2 = DBConnection.getConnection().prepareStatement(sqlPatient);
            
            stmt2.executeUpdate();   
                        
            String sql = "DELETE FROM U05vuW.address where address_id = " + selectedPatient.getAid();
         
            System.out.println(sql);
            
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
            
            stmt.executeUpdate();

            return true;
       
        }catch(SQLException e){
      
            e.printStackTrace();
          
            return false;
      
        } 
  
    }
    
    public static ArrayList<Patient> getPatientsNameAndID() throws ClassNotFoundException, SQLException, Exception{
   
        String sql = "SELECT patient.pt_id,patient.pt_name "
        
                + "FROM patient ";
        
        try{
     
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
          
            ResultSet rs = stmt.executeQuery();
          
            ArrayList<Patient> patList = new ArrayList<>();
            
            while(rs.next()){
        
            Patient pat = new Patient(rs.getInt("pt_id"),rs.getString("pt_name"));
         
            patList.add(pat);
     
            }
        
            return patList;
            
        }
     
        catch(SQLException e){
      
            System.out.println("Please check your SQL and try again");
           
            e.printStackTrace();
           
            throw e;
      
        }

    } 
}
