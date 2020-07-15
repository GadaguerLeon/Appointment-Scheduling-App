/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiialternate.view_controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import softwareiialternate.model.Appointment;
import softwareiialternate.utilities.Time;


/**
 * FXML Controller class
 *
 * 
 */
public class AppointmentCalenderViewController implements Initializable{
    @FXML
    private StackPane parentContainer;
    
    @FXML
    private AnchorPane container;
    
    @FXML
    private ImageView btnBack;
    
    @FXML
    private RadioButton btnMonthly;
    
    @FXML
    private RadioButton btnBiWeekly;
    
    @FXML
    private RadioButton btnWeekly;
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private TableView table;
    
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
    public void btnWeeklyPressed(){
 
        if(btnBiWeekly.isSelected())
       
            btnBiWeekly.setSelected(false);
       
        if(btnMonthly.isSelected())
      
            btnMonthly.setSelected(false);
    
    }
    
    @FXML
    public void btnBiWeeklyPressed(){
    
        if(btnWeekly.isSelected())
       
            btnWeekly.setSelected(false);
        
        if(btnMonthly.isSelected())
      
            btnMonthly.setSelected(false);
   
    }
    
    @FXML
    public void btnMonthlyPressed(){
    
        if(btnWeekly.isSelected())
       
            btnWeekly.setSelected(false);
       
        if(btnBiWeekly.isSelected())
      
            btnBiWeekly.setSelected(false);
  
    }
    
    @SuppressWarnings("unchecked")
    private void createTable() {

        TableColumn<Appointment, String> colID = new TableColumn<>("Apt ID");
        TableColumn<Appointment, String> colName = new TableColumn<>("Counselor Name");
        TableColumn<Appointment, String> colPName = new TableColumn<>("Patient Name");
        TableColumn<Appointment, String> colAType = new TableColumn<>("Appointment Type");
        TableColumn<Appointment, String> colStartTime = new TableColumn<>("Start Time");
        TableColumn<Appointment, String> colEndTime = new TableColumn<>("End Time");

        colID.setCellValueFactory(new PropertyValueFactory<>("aptid"));
        colName.setCellValueFactory(new PropertyValueFactory<>("consname"));
        colPName.setCellValueFactory(new PropertyValueFactory<>("patname"));
        colAType.setCellValueFactory(new PropertyValueFactory<>("APTtypedesc"));
        colStartTime.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndTime.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        colID.setPrefWidth(70);
        colName.setPrefWidth(100);
        colPName.setPrefWidth(120);
        colAType.setPrefWidth(120);
        colStartTime.setPrefWidth(150);
        colEndTime.setPrefWidth(150);

        table.getColumns().addAll(colID, colName, colPName, colAType, colStartTime, colEndTime);
    }
    
    
    @FXML
    private void viewAppointments() throws Exception, Exception{
      
        if(validations()){
       
            LocalDate ld = datePicker.getValue();
            
            ArrayList<Appointment> lstAppt = new  ArrayList<>();
          
            if (btnWeekly.isSelected()){
        
                lstAppt = Time.getDatesByWeeklyBiWeeklyMonthly("Weekly", ld);
         
            } else if (btnBiWeekly.isSelected()){
         
                lstAppt = Time.getDatesByWeeklyBiWeeklyMonthly("BiWeekly", ld);
         
            } else if (btnMonthly.isSelected()){
        
                lstAppt = Time.getDatesByWeeklyBiWeeklyMonthly("Monthly", ld);
         
            }
            
            if(lstAppt != null && lstAppt.size() > 0){
         
                populateTable(lstAppt);
          
            } else {
         
                table.setItems(null);
          
            }
      
        }
    
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
        createTable();
   
    }

    private boolean validations() {
    
        if(btnBiWeekly.isSelected() == false && btnWeekly.isSelected() == false && btnMonthly.isSelected() == false){
      
            showErrorMessages("View Appointments By Calendar.", "Please select a data input Weekly,BiWeekly or Monthly");
       
            return false;
      
        }
        
        if(datePicker.getValue() == null || datePicker.getValue().toString().equals("")) {
      
            showErrorMessages("Add Appointment", "Please select a date.");
         
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

    private void populateTable(ArrayList<Appointment> apptList) {
     
        ObservableList<Appointment> aptData = FXCollections.observableArrayList();
              
        for (int i = 0; i < apptList.size(); i++) {
      
            aptData.add(apptList.get(i));
     
        }
      
        table.setItems(aptData);
  
    }
    
}