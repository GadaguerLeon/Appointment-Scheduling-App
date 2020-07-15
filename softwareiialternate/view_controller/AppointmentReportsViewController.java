/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiialternate.view_controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import softwareiialternate.DAO.AppointmentDAO;
import softwareiialternate.model.Appointment;

/**
 * FXML Controller class
 *
 * 
 */
public class AppointmentReportsViewController {

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private ImageView btnBack;   
    
    @FXML
    private RadioButton btnCountByType;
    
    @FXML
    private RadioButton btnAptByCr;
    
    @FXML
    private RadioButton btnAptByState;
    
    @FXML
    private BarChart bar;
    
    @FXML
    private CategoryAxis xAxis;
    
    @FXML
    private NumberAxis yAxis;
    
    @FXML
    public void apptHomeButtonAction(MouseEvent event) throws Exception {

        /*
                    *  Implements a Timeline and creates a delay
                    *  with a transition moving to the next scene
         */
        Parent root = FXMLLoader.load(getClass().getResource("AppointmentView.fxml"));

        Scene scene = btnBack.getScene();

        root.translateYProperty().set(scene.getHeight());
        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        timeline.getKeyFrames().add(kf);
        
        //Lambda expression satifying part G of the project requirements 
        timeline.setOnFinished(event1 -> {
            parentContainer.getChildren().remove(container);
        });

        timeline.play();

    }
    
    @FXML
    public void btnAptByStatePressed(){
      
        if(btnAptByCr.isSelected())
       
            btnAptByCr.setSelected(false);
      
        if(btnCountByType.isSelected())
     
            btnCountByType.setSelected(false);
  
    }
    
    @FXML
    public void btnAptByCrPressed(){
   
        if(btnAptByState.isSelected())
      
            btnAptByState.setSelected(false);
       
        if(btnCountByType.isSelected())
     
            btnCountByType.setSelected(false);
  
    }
    
    @FXML
    public void btnCountByTypePressed(){
    
        if(btnAptByState.isSelected())
      
            btnAptByState.setSelected(false);
        
        if(btnAptByCr.isSelected())
       
            btnAptByCr.setSelected(false);
   
    }
    
    @FXML
    public void generateReport() throws Exception{
    
        if(validations()){
            
            LocalDate ld = LocalDate.now(ZoneId.of("America/New_York"));
            
            LocalDate ldt = ld.with(TemporalAdjusters.firstDayOfYear());
        
            LocalDate ldt2 = ld.with(TemporalAdjusters.lastDayOfYear());
            
            Timestamp start = Timestamp.valueOf(ldt.atStartOfDay());
            
            Timestamp end = Timestamp.valueOf(ldt2.atStartOfDay());
            
            ArrayList<Appointment> lstAppt = new  ArrayList<>();
            
            boolean isState = false;
           
            if (btnCountByType.isSelected()){
           
                lstAppt = AppointmentDAO.getAppointmentsReportData(start,end,true,false,false);
           
            } else if (btnAptByCr.isSelected()){
         
                lstAppt = AppointmentDAO.getAppointmentsReportData(start,end,false,true,false);
          
            } else if (btnAptByState.isSelected()){
      
                isState = true;
          
                lstAppt = AppointmentDAO.getAppointmentsReportData(start,end,false,false,true);
        
            }
            
            if(lstAppt != null && !lstAppt.isEmpty()){
       
                addDataIntoBarchart(lstAppt,isState);
          
            }
     
        }
  
    }
    
    private boolean validations() {
       
        if(btnAptByCr.isSelected() == false && btnAptByState.isSelected() == false && btnCountByType.isSelected() == false){
  
            showErrorMessages("View Appointment Reports.", "Please select report Type.");
        
            return false;
      
        }
        
        return true;
   
    }
    
    private void showErrorMessages(String title, String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
 
        alert.setTitle(title);
        
        alert.setHeaderText("Error");
        
        alert.setContentText(message);
        
        alert.show();
    
    }

    private void addDataIntoBarchart(ArrayList<Appointment> lstAppt,boolean isState) {
        
        bar.getData().removeAll();
    
        bar.getData().clear();
        
        yAxis.setLabel("Count");
        
         if (btnCountByType.isSelected()) {
        
             xAxis.setLabel("Appointment Type ID");
       
         } else if (btnAptByCr.isSelected()) {
     
             xAxis.setLabel("Counseller ID");
        
         } else if (btnAptByState.isSelected()) {
            xAxis.setLabel("State");
      
         }
     
         for (int i = 0; i < lstAppt.size(); i++){
  
             Appointment appt = lstAppt.get(i);
       
             XYChart.Series<String,Number> dataSeries1 = new XYChart.Series();
       
             if(!isState){
      
                 dataSeries1.getData().add(new XYChart.Data<String,Number>(appt.getAptid() + "", appt.getPid()));
          
             } else {
      
                 dataSeries1.getData().add(new XYChart.Data<String,Number>(appt.getNotes(), appt.getPid()));
        
             }
        
             bar.getData().addAll(dataSeries1);
    
         }
                       
    }

}
