/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiialternate.view_controller;

import Exceptions.HolidaysException;
import Exceptions.TimeException;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import softwareiialternate.DAO.APTTypeDAO;
import softwareiialternate.DAO.AppointmentDAO;
import softwareiialternate.DAO.PatientDAO;
import softwareiialternate.model.APTtype;
import softwareiialternate.model.Appointment;
import softwareiialternate.model.Patient;
import softwareiialternate.utilities.Time;

/**
 * FXML Controller class
 *
 * 
 */
public class AppointmentViewController implements Initializable {

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private ImageView av_homeButton;

    @FXML
    private TableView table;

    @FXML
    private Button btnAdd;

    @FXML
    private Button viewCalendar;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button av_deleteApptButton;

    @FXML
    private ComboBox cmbPatient;

    @FXML
    private ComboBox cmbAptType;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXTimePicker timePicker;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtNotes;

    private LocalDate selectedDate = null;
    private LocalTime selectedTime = null;

    private Appointment selectedAppointment = null;
    private boolean isUpdate = false;

    private ArrayList<Appointment> apptList;

    @FXML
    public void apptHomeButtonAction(MouseEvent event) throws Exception {

        /*
                    *  Implements a Timeline and creates a delay
                    *  with a transition moving to the next scene
         */
        Parent root = FXMLLoader.load(getClass().getResource("HomeView.fxml"));

        Scene scene = av_homeButton.getScene();


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
    public void viewReportsButton(ActionEvent event) throws Exception {

        /*
                    *  Implements a Timeline and creates a delay
                    *  with a transition moving to the next scene
         */
        Parent root = FXMLLoader.load(getClass().getResource("AppointmentReportsView.fxml"));

        Scene scene = av_homeButton.getScene();

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
    public void viewCalendarAppointments(ActionEvent event) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("AppointmentCalenderView.fxml"));

        Scene scene = av_homeButton.getScene();

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
    public void addAppointment(ActionEvent event) throws Exception {

        selectedDate = datePicker.getValue();

        selectedTime = timePicker.getValue();

        if (valiations()) {

            if (!isUpdate) {

                LocalDate ld = datePicker.getValue();

                LocalTime lt = timePicker.getValue();

                System.out.println(ld.toString() + " Time :" + lt.toString());

                try {

                    Time.checkHolidays(ld);

                    Time.checkHolidays(ld, lt);
                                        
                    int duration = Integer.parseInt(txtDuration.getText());
                    
                    int pid = mapPatientIdByName.get(cmbPatient.getSelectionModel().getSelectedItem().toString());
                                        
                    LocalDateTime startDateTime = Time.convertToEST(ld, lt);
                    
                    LocalDateTime endDateTime = Time.getAppointmentEndTime(startDateTime, duration);                    
                    
                    Time.overlappingByCounsler(startDateTime, endDateTime);
                    
                    Time.overlappingByPatient(startDateTime, endDateTime, pid);                    
                    
                    String note = txtNotes.getText();

                    int aptTypeId = mapAptTypeIdByName.get(cmbAptType.getSelectionModel().getSelectedItem().toString());

                    Appointment appointment = new Appointment();
                  
                    appointment.setApttypeid(aptTypeId);
                    
                    appointment.setConslrid(LoginFormController.counsler_id);
                    
                    appointment.setPid(pid);
                    
                    appointment.setNotes(note);
                    
                    appointment.setStarteDate(startDateTime);
                    
                    appointment.setEndDate(endDateTime);

                    if (AppointmentDAO.addAppointment(appointment)) {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);

                        alert.setTitle("Add Appointment.");

                        alert.setContentText("Appointment was created successfully.");

                        alert.show();

                        refresh(null);

                    } else {

                        showErrorMessages("Add Appointment", "Appointment creation was unsuccessful.");

                    }

                } catch (HolidaysException | TimeException e) {

                }

            } else {

                LocalDate ld = datePicker.getValue();

                LocalTime lt = timePicker.getValue();

                System.out.println(ld.toString() + " Time :" + lt.toString());

                try {

                    Time.checkHolidays(ld);

                    Time.checkHolidays(ld, lt);

                    String note = txtNotes.getText();

                    int pid = mapPatientIdByName.get(cmbPatient.getSelectionModel().getSelectedItem().toString());

                    int duration = Integer.parseInt(txtDuration.getText());

                    LocalDateTime startDateTime = Time.convertToEST(ld, lt);

                    LocalDateTime endDateTime = Time.getAppointmentEndTime(startDateTime, duration);

                    int aptTypeId = mapAptTypeIdByName.get(cmbAptType.getSelectionModel().getSelectedItem().toString());

                    selectedAppointment.setApttypeid(aptTypeId);

                    selectedAppointment.setConslrid(LoginFormController.counsler_id);

                    selectedAppointment.setPid(pid);

                    selectedAppointment.setNotes(note);

                    selectedAppointment.setStarteDate(startDateTime);

                    selectedAppointment.setEndDate(endDateTime);

                    if (AppointmentDAO.updateAppointment(selectedAppointment)) {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);

                        alert.setTitle("Add Appointment.");

                        alert.setContentText("Appointment updated successfully.");

                        alert.show();

                    } else {

                        showErrorMessages("Add Appointment", "Appointment updating was unsuccessful.");

                    }

                } catch (HolidaysException | TimeException e) {

                }

            }

        }

    }

    @FXML
    public void tableMouseListener(MouseEvent e) {

        selectedAppointment = apptList.get(table.getSelectionModel().getSelectedIndex());

        isUpdate = true;

        loadAppointmentForUpdate();

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

    private void populateTable() throws Exception {

        ObservableList<Appointment> aptData = FXCollections.observableArrayList();

        apptList = AppointmentDAO.getAllAppointments();

        if (apptList != null && apptList.size() > 0) {

            for (int i = 0; i < apptList.size(); i++) {

                aptData.add(apptList.get(i));

            }

            table.setItems(aptData);

        } else {

            table.setItems(null);

        }

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
       
            fillPatientCombo();
            fillCmbAptType();
            createTable();
            populateTable();
     
        } catch (SQLException ex) {
      
            Logger.getLogger(AppointmentViewController.class.getName()).log(Level.SEVERE, null, ex);
       
        } catch (Exception ex) {
     
            Logger.getLogger(AppointmentViewController.class.getName()).log(Level.SEVERE, null, ex);
      
        }
  
    }

    Map<String, Integer> mapPatientIdByName = new HashMap<>();

    private void fillPatientCombo() throws SQLException, Exception {
        
        cmbPatient.getItems().clear();
     
        ArrayList<Patient> list = PatientDAO.getPatientsNameAndID();
        
        if (list != null && list.size() > 0) {
            
            for (Patient p : list) {
        
                mapPatientIdByName.put(p.getPname(), p.getPid());
            
                cmbPatient.getItems().add(p.getPname());
          
            }
      
        }
  
    }

    Map<String, Integer> mapAptTypeIdByName = new HashMap<>();

    private void fillCmbAptType() throws SQLException, Exception {
     
        cmbAptType.getItems().clear();
       
        ArrayList<APTtype> list = APTTypeDAO.getAllAPTTypes();
      
        if (list != null && list.size() > 0) {
      
            for (APTtype p : list) {
         
                mapAptTypeIdByName.put(p.getDescription(), p.getApttypeid());
         
                cmbAptType.getItems().add(p.getDescription());
         
            };
     
        }
   
    }

    private boolean valiations() {
     
        String message = "";
     
        if (selectedDate == null || selectedDate.toString().equals("")) {
     
            message = "Date of the appointment should be selected.";
        
            showErrorMessages("Add Appointment", message);
         
            return false;
     
        }

        if (selectedTime == null || selectedTime.toString().equals("")) {
        
            message = "Time of the appointment should be selected.";
         
            showErrorMessages("Add Appointment", message);
         
            return false;
      
        }

        if (txtDuration.getText() == null || txtDuration.getText().equals("")) {
        
            message = "Duration of appointment should be entered.";
          
            showErrorMessages("Add Appointment", message);
        
            return false;
     
        } else {
      
            try {
            
                int i = Integer.parseInt(txtDuration.getText());
             
                if (i <= 0) {
             
                    message = "Duration of appointment should be greater than 0 minutes.";
                 
                    showErrorMessages("Add Appointment", message);
                 
                    return false;
              
                }
        
            } catch (NumberFormatException e) {
        
                message = "Duration should only be numeric.";
            
                showErrorMessages("Add Appointment", message);
            
                return false;
         
            }
     
        }

        if (txtNotes.getText() == null || txtNotes.getText().equals("")) {
         
            message = "Please add a note with appointment.";
          
            showErrorMessages("Add Appointment", message);
         
            return false;
     
        }
        
        System.out.println(cmbPatient.getSelectionModel().getSelectedIndex());
      
        if (cmbPatient.getSelectionModel().getSelectedIndex() == -1) {
        
            message = "Please select a patient.";
         
            showErrorMessages("Add Appointment", message);
         
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

    @FXML
    private void refresh(ActionEvent event) throws Exception {

        btnAdd.setText("Add");
    
        isUpdate = false;
      
        selectedAppointment = null;

        cmbAptType.getSelectionModel().select(null);
     
        cmbPatient.getSelectionModel().select(null);
        
        txtNotes.setText("");
        
        txtDuration.setText("");

        cmbAptType.setPromptText("Select Appointment Type");
        
        cmbPatient.setPromptText("Select Patient");
        
        txtNotes.setPromptText("Notes");
        
        txtDuration.setPromptText("Duration");
        
        datePicker.setPromptText("Appointment Date");
        
        timePicker.setPromptText("Start Time");
        
        selectedDate = null;
      
        selectedTime = null;

        mapPatientIdByName = new HashMap<>();
      
        mapAptTypeIdByName = new HashMap<>();
      
        fillPatientCombo();
       
        fillCmbAptType();

        populateTable();

    }

    @FXML
    private void deletePatient() throws Exception {
    
        if (selectedAppointment == null) {
     
            showErrorMessages("Delete Appointment", "Please select an appointment to delete first.");
     
        } else {
    
            if (AppointmentDAO.deleteAppointment(selectedAppointment.getAptid())) {
    
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
             
                alert.setTitle("Delete Appointment");
            
                alert.setContentText("Appointment deleted successfully.");
             
                alert.show();
              
                refresh(null);
          
            } else {
          
                showErrorMessages("Delete Appointment", "Could Not delete Selected Appointment.");
         
            }
      
        }
  
    }

    private void loadAppointmentForUpdate() {
     
        btnAdd.setText("Update");

        timePicker.setValue(selectedAppointment.getStartDate().toLocalTime());
        
        datePicker.setValue(selectedAppointment.getStartDate().toLocalDate());

        txtNotes.setText(selectedAppointment.getNotes());
        
        cmbAptType.getSelectionModel().select(selectedAppointment.getApttypedesc());
        
        cmbPatient.getSelectionModel().select(selectedAppointment.getPatname());
        
        txtDuration.setText(Time.findDuration(selectedAppointment.getStartDate(), selectedAppointment.getEndDate())+"");

    }

}
