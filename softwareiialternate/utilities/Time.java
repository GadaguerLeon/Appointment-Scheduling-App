/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiialternate.utilities;

import Exceptions.HolidaysException;
import Exceptions.TimeException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import softwareiialternate.DAO.AppointmentDAO;
import softwareiialternate.model.Appointment;
import softwareiialternate.view_controller.LoginFormController;

/**
 *
 * 
 */
public class Time {

    static DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    static ZoneId etZoneId = ZoneId.of("America/New_York");


    public static LocalDateTime convertToEST(LocalDate date, LocalTime time) {

        LocalDateTime ldt = LocalDateTime.of(date, time);

        ZonedDateTime ldt2 = ldt.atZone(ZoneId.systemDefault());

        LocalDateTime ldt3 = ldt2.withZoneSameInstant(etZoneId).toLocalDateTime();

        return ldt3;
    }

    public static LocalDateTime convertFromEST(LocalDateTime ldt) {

        ZonedDateTime ldt2 = ldt.atZone(etZoneId);

        LocalDateTime ldt3 = ldt2.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();

        return ldt3;
    }

    public static String convertLocalDateTimeToDBFormat(LocalDateTime ldt) {
    
        return dbFormatter.format(ldt);
   
    }
    
    public static Timestamp convertToDatabaseColumn(LocalDateTime ldt) {
  
        return Timestamp.valueOf(ldt);
   
    }
    
    public static LocalDateTime convertToEntityAttribute(Timestamp ts) {
  
        return ts.toLocalDateTime();
   
    }
    
    public static LocalDateTime getAppointmentEndTime(LocalDateTime ldt,int duration) {
   
        return ldt.plusMinutes(duration);
   
    }
    
    public static  void checkHolidays(LocalDate ldt) throws HolidaysException{
   
        LocalDate ld = LocalDate.of(ldt.getYear(), Month.JANUARY, 1);
       
        System.out.println("Date " + ld);
       
        LocalDate d = ld.with(TemporalAdjusters.dayOfWeekInMonth(3, DayOfWeek.MONDAY));
       
        if(d.equals(ldt)){
       
            throw new HolidaysException(" Martin Luther King, Jr. Day");
       
        }
        
        ld = LocalDate.of(ldt.getYear(), Month.MAY, 1);
       
        System.out.println("may " + ld);
       
        d = ld.with(TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY));
       
        System.out.println("last monday of may " + d);
       
        if(d.equals(ldt)){
        
            throw new HolidaysException(" Memorial Day");
      
        }
        
        ld = LocalDate.of(ldt.getYear(), Month.NOVEMBER, 11);
     
        if(ld.equals(ldt)){
        
            throw new HolidaysException(" Veterans Day");
       
        }
        
        ld = LocalDate.of(ldt.getYear(), Month.NOVEMBER, 1);
      
        d = ld.with(TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.THURSDAY));
        
        LocalDate d2 = d.plusDays(1);
        
        if(d.equals(ldt) || d2.equals(ldt)){
        
            throw new HolidaysException(" Thanksgiving or the day after.");
        
        }
        
        if(ldt.getDayOfWeek() == DayOfWeek.SATURDAY || ldt.getDayOfWeek() == DayOfWeek.SUNDAY){
      
            throw new HolidaysException(" WEEK END");
       
        }
   
    }
    
    public static long findDuration(LocalDateTime sldt, LocalDateTime eldt){
   
        return Duration.between(sldt, eldt).getSeconds()/60;
    
    }
            
    public static  void checkHolidays(LocalDate ld,LocalTime lt) throws TimeException{
    
        LocalDateTime ldt = convertToEST(ld, lt);
       
        if(ldt.getHour() > 20 || ldt.getHour() < 8){
       
            throw new TimeException("Business hours are 9AM to 9PM EST.");
       
        }
   
    }
    
    public static void overlappingByCounsler(LocalDateTime start, LocalDateTime end) throws Exception{
   
        ArrayList<Appointment> apt = AppointmentDAO.getAppointments(convertToDatabaseColumn(start), convertToDatabaseColumn(end), -1, LoginFormController.counsler_id);
        
        if(apt != null && apt.size() > 0) {
        
            throw new TimeException("Couneslor has already appointments in this timeline.");
       
        }
        
    }
    
    public static void overlappingByPatient(LocalDateTime start, LocalDateTime end,int pid) throws Exception{
       
        ArrayList<Appointment> apt = AppointmentDAO.getAppointments(convertToDatabaseColumn(start), convertToDatabaseColumn(end), pid, -1);
       
        if(apt != null && apt.size() > 0) {
      
            throw new TimeException("Patient has already appointments in this timeline.");
       
        }
   
    }    
    
    public static ArrayList<Appointment> getDatesByWeeklyBiWeeklyMonthly(String type,LocalDate ld) throws Exception{
     
        LocalDate start = null;
       
        LocalDate end = null;
       
        if(type.equals("Weekly")){
                   
            start = ld;
       
            end = start.plusWeeks(1);
       
        } else if(type.equals("BiWeekly")){

            start = ld;
         
            end = start.plusWeeks(2);
       
        } else if(type.equals("Monthly")){
  
            start = ld.with(TemporalAdjusters.firstDayOfMonth());
        
            end = ld.with(TemporalAdjusters.lastDayOfMonth());
      
        }
                
        Timestamp startDate = convertToDatabaseColumn(convertToEST(start, LocalTime.MIN));
     
        Timestamp endDate = convertToDatabaseColumn(convertToEST(end, LocalTime.MAX));
      
        ArrayList<Appointment> apt = AppointmentDAO.getAppointments(startDate, endDate);
        
        return apt;
   
    }

}
