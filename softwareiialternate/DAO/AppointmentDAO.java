/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiialternate.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import softwareiialternate.model.Appointment;
import softwareiialternate.utilities.Time;

/**
 *
 * 
 */
public class AppointmentDAO {
    
    public static ArrayList<Appointment> getAllAppointments() throws Exception{
        
        String sql = "SELECT appointment.*,patient.pt_name,counselor.c_name,APTtype.description "
                + "FROM `appointment` "
                + "LEFT JOIN patient ON patient.pt_id = appointment.pt_id "
                + "LEFT JOIN APTtype ON APTtype.APTtype_id = appointment.apt_type_id "
                + "LEFT JOIN counselor ON counselor.c_id = appointment.cr_id ";
        
        //System.out.println(" leer query ::::::::::::::::::::::::" + sql);
        
        try{
         
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
            
            ResultSet rs = stmt.executeQuery();
            
            ArrayList<Appointment> list = new ArrayList<>();
            
            while(rs.next()){
            
                Appointment apt = new Appointment();
                
                apt.setAptid(rs.getInt("apt_id"));
                
                apt.setPid(rs.getInt("pt_id"));
                
                apt.setConslrid(rs.getInt("cr_id"));
                
                apt.setApttypeid(rs.getInt("apt_type_id"));
                
                LocalDateTime sldt = Time.convertToEntityAttribute(rs.getTimestamp("start_datetime"));
               
                LocalDateTime eldt = Time.convertToEntityAttribute(rs.getTimestamp("end_datetime"));
              
                apt.setStarteDate(Time.convertFromEST(sldt));
               
                apt.setEndDate(Time.convertFromEST(eldt));
                
                apt.setPatname(rs.getString("pt_name"));
                
                apt.setConsname(rs.getString("c_name")); 
                
                apt.setApttypedesc(rs.getString("description"));
                
                apt.setNotes(rs.getString("notes"));
                
                list.add(apt);
            
            }
            
            return list;
            
        }
      
        catch(SQLException e){
        
            System.out.println("Please check your SQL and try again");
           
            return null;
      
        }
        
    }
    
    public static boolean addAppointment(Appointment appt) throws Exception{
 
        try{
        
            String sql = "INSERT INTO `appointment` ( `pt_id`, `cr_id`, `apt_type_id`, `notes`, `start_datetime`, `end_datetime`, `created_at`, `created_by`, `updated_at`, `updated_by`, `patient_pt_id`, `counselor_c_id`, `APTtype_APTtype_id`) "            
                    + " VALUES ('"+appt.getPid()+"', '"+appt.getConslrid()+"', '"+appt.getApttypeid()+"', '"+appt.getNotes()+"', '"+appt.getStartDate()+"', '"+appt.getEndDate()+"', NULL, NULL, NULL, NULL, '"+appt.getPid()+"', '"+appt.getConslrid()+"', '"+appt.getApttypeid()+"')";
            
            System.out.println(sql);
           
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
          
            stmt.executeUpdate();
               
        }catch(SQLException e){
    
            System.out.println("Please check your SQL and try again");
         
            return false;
     
        }
      
        return true;
   
    }
    
    public static boolean deleteAppointment(int id) throws Exception{
  
        try{
      
            String sql = "DELETE FROM `appointment` WHERE apt_id = " + id;
         
            System.out.println(sql);
           
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
           
            stmt.executeUpdate();
               
        }catch(SQLException e){
     
            System.out.println("Please check your SQL and try again");
         
            return false;
      
        }
      
        return true;
   
    }

    public static boolean updateAppointment(Appointment appt) throws Exception{
    
        try{
       
            String sql = "UPDATE `appointment` "
                 + "SET `pt_id`="+appt.getPid()+",`cr_id`="+appt.getConslrid()+","
                 + "`apt_type_id`= "+appt.getApttypeid()+
                 ",`notes`= '"+appt.getNotes()+"',`start_datetime`='"+appt.getStartDate()+"',"
                 + "`end_datetime`='"+appt.getEndDate()+"' WHERE apt_id = " + appt.getAptid();
               
            System.out.println(sql);
         
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
          
            stmt.executeUpdate();
               
        }catch(SQLException e){
     
            System.out.println("Please check your SQL and try again");
          
            return false;
      
        }
      
        return true;
   
    }

    public static ArrayList<Appointment> getAppointments(Timestamp start, Timestamp end) throws Exception{
     
        String sql = "SELECT appointment.*,patient.pt_name,counselor.c_name,APTtype.description "
                + "FROM `appointment` "
                + "LEFT JOIN patient ON patient.pt_id = appointment.pt_id "
                + "LEFT JOIN APTtype ON APTtype.APTtype_id = appointment.apt_type_id "
                + "LEFT JOIN counselor ON counselor.c_id = appointment.cr_id "
                + "WHERE start_datetime BETWEEN '"+ start +"' AND '"+end+"' ";
       
        System.out.println("Query " + sql);
      
        try{
       
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
           
            ResultSet rs = stmt.executeQuery();
           
            ArrayList<Appointment> list = new ArrayList<>();
           
            while(rs.next()){
           
                Appointment apt = new Appointment();
               
                apt.setAptid(rs.getInt("apt_id"));
               
                apt.setPid(rs.getInt("pt_id"));
               
                apt.setConslrid(rs.getInt("cr_id"));
               
                apt.setApttypeid(rs.getInt("apt_type_id"));
               
                LocalDateTime sldt = Time.convertToEntityAttribute(rs.getTimestamp("start_datetime"));
               
                LocalDateTime eldt = Time.convertToEntityAttribute(rs.getTimestamp("end_datetime"));
              
                apt.setStarteDate(Time.convertFromEST(sldt));
               
                apt.setEndDate(Time.convertFromEST(eldt));
               
                apt.setPatname(rs.getString("pt_name"));
               
                apt.setConsname(rs.getString("c_name")); 
               
                apt.setApttypedesc(rs.getString("description"));
               
                apt.setNotes(rs.getString("notes"));
               
                list.add(apt);
           
            }
           
            return list;
            
        }
       
        catch(SQLException e){
       
            System.out.println("Please check your SQL and try again");
           
            return null;
        
        }
    
    }
    
    
    public static ArrayList<Appointment> getAppointments(Timestamp start, Timestamp end, int pid,int cid) throws Exception{
   
        StringBuilder whereClause = new StringBuilder();
       
        if(pid != -1) {
       
            whereClause.append(" and patient.pt_id = ").append(pid);
        } else {
           
            whereClause.append(" and counselor.c_id  = ").append(cid);
      
        }
       
        String sql = "SELECT appointment.*,patient.pt_name,counselor.c_name,APTtype.description "
                + "FROM `appointment` "
                + "LEFT JOIN patient ON patient.pt_id = appointment.pt_id "
                + "LEFT JOIN APTtype ON APTtype.APTtype_id = appointment.apt_type_id "
                + "LEFT JOIN counselor ON counselor.c_id = appointment.cr_id "
                + "WHERE 1 " + whereClause.toString() 
                + " AND ((start_datetime <= '"+end+"' ) and ('"+start+"' <= end_datetime))";
        
        System.out.println("Query " + sql);
      
        try{
       
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
           
            ResultSet rs = stmt.executeQuery();
           
            ArrayList<Appointment> list = new ArrayList<>();
           
            while(rs.next()){
           
                Appointment apt = new Appointment();
               
                apt.setAptid(rs.getInt("apt_id"));
               
                apt.setPid(rs.getInt("pt_id"));
               
                apt.setConslrid(rs.getInt("cr_id"));
               
                apt.setApttypeid(rs.getInt("apt_type_id"));
               
                LocalDateTime sldt = Time.convertToEntityAttribute(rs.getTimestamp("start_datetime"));
               
                LocalDateTime eldt = Time.convertToEntityAttribute(rs.getTimestamp("end_datetime"));
              
                apt.setStarteDate(Time.convertFromEST(sldt));
               
                apt.setEndDate(Time.convertFromEST(eldt));
               
                apt.setPatname(rs.getString("pt_name"));
               
                apt.setConsname(rs.getString("c_name")); 
               
                apt.setApttypedesc(rs.getString("description"));
                    
                apt.setNotes(rs.getString("notes"));
               
                list.add(apt);
           
            }
            
            return list;
            
        }
       
        catch(SQLException e){
        
            System.out.println("Please check your SQL and try again");
            
            return null;
    
        }
    
    }
    
    public static ArrayList<Appointment> getAppointmentsReportData(Timestamp start, Timestamp end,boolean isAptByType, boolean isAptByCouncellor, boolean isAptByState) throws Exception{
    
        StringBuilder select = new StringBuilder();
       
        StringBuilder groupBy = new StringBuilder();
       
        boolean isState = false;
       
        if(isAptByType){
       
            select.append("SELECT DISTINCT appointment.apt_type_id as id, COUNT( appointment.apt_type_id) AS count ");
           
            groupBy.append("GROUP BY appointment.apt_type_id ");
       
        } else if(isAptByCouncellor){
       
            select.append("SELECT DISTINCT appointment.cr_id as id, COUNT(appointment.cr_id) AS count ");
           
            groupBy.append("GROUP BY appointment.cr_id ");
       
        }else {
       
            select.append("SELECT DISTINCT address.state as id, COUNT(address.state) AS count ");
           
            groupBy.append("GROUP BY address.state ");
           
            isState = true;
      
        }
       
        String joins = "FROM `appointment` "
                + "LEFT JOIN patient ON patient.pt_id = appointment.pt_id "
                + "LEFT JOIN APTtype ON APTtype.APTtype_id = appointment.apt_type_id "
                + "LEFT JOIN counselor ON counselor.c_id = appointment.cr_id "
                + "LEFT JOIN address ON address.address_id = patient.address_id "
                + "WHERE start_datetime BETWEEN '"+ start +"' AND '"+end+"' ";
        
        String sql = select.toString() + joins + groupBy.toString();
       
        System.out.println("Query " + sql);
       
        try{
       
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
           
            ResultSet rs = stmt.executeQuery();
           
            ArrayList<Appointment> list = new ArrayList<>();
           
            while(rs.next()){
           
                Appointment apt = new Appointment();
                
                if (isState) {
               
                    apt.setNotes(rs.getString("id"));
               
                } else {
             
                    apt.setAptid(rs.getInt("id"));
             
                }
              
                apt.setPid(rs.getInt("count"));
              
                list.add(apt);
          
            }
          
            return list;
            
        }
       
        catch(SQLException e){
      
            System.out.println("Please check your SQL and try again");
          
            return null;
      
        }
  
    }

}
