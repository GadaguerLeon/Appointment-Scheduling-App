/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiialternate.view_controller;

import java.util.ArrayList;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import softwareiialternate.model.Patient;
import softwareiialternate.DAO.PatientDAO;

/**
 * FXML Controller class
 *
 * 
 */
public class PatientViewController {

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private ImageView pv_homeButton;

    @FXML
    private Button pv_addPatButton;


    @FXML
    private TableView table;

    @FXML
    private TextField pName;

    @FXML
    private TextField pInsuranceProvider;

    @FXML
    private TextField pPhone;

    @FXML
    private TextField pCity;

    @FXML
    private TextField pState;

    @FXML
    private TextField pZipCode;

    @FXML
    private TextArea pAddress;

    @FXML
    public void patHomeButtonAction(MouseEvent event) throws Exception {

        /*
        *  Implements a Timeline and creates a delay
        *  with a transition moving to the next scene
         */
        Parent root = FXMLLoader.load(getClass().getResource("HomeView.fxml"));

        Scene scene = pv_homeButton.getScene();

        root.translateYProperty().set(scene.getHeight());
   
        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        
        KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);
        
        KeyFrame kf = new KeyFrame(Duration.seconds(0.4), kv);
        
        timeline.getKeyFrames().add(kf);
        //Lambda expression satifying part G of the project requirements 
        timeline.setOnFinished(event1 -> {

            parentContainer.getChildren().remove(container);

        });

        timeline.play();

    }

    Patient selectedPatient = null;
    boolean isUpdate = false;

    @FXML
    public void tableMouseListener(MouseEvent e) {

        selectedPatient = patList.get(table.getSelectionModel().getSelectedIndex());
        
        isUpdate = true;
        
        loadPatientForUpdate();
   
    }

    @FXML
    private void initialize() throws Exception {
  
        createTable();
      
        populateTable();
  
    }
    //Creates the patient table 
    @SuppressWarnings("unchecked")
    private void createTable() {
   
        TableColumn<Patient, String> colName = new TableColumn<>("Name");
        TableColumn<Patient, String> colPhone = new TableColumn<>("Phone");
        TableColumn<Patient, String> colinsPr = new TableColumn<>("Insurance Provider");
        TableColumn<Patient, String> colAddress = new TableColumn<>("Address");

        colName.setCellValueFactory(new PropertyValueFactory<>("pname"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("paddress"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("pphone"));
        colinsPr.setCellValueFactory(new PropertyValueFactory<>("pinspr"));

        colName.setPrefWidth(140);
        colAddress.setPrefWidth(222);
        colPhone.setPrefWidth(150);
        colinsPr.setPrefWidth(150);

        table.getColumns().addAll(colName, colPhone, colinsPr, colAddress);
    }

    ArrayList<Patient> patList;
    
    //Populates the patient table using a for loop that iterates throught the size of patList
    private void populateTable() throws Exception {
       
        ObservableList<Patient> pData = FXCollections.observableArrayList();
      
        patList = PatientDAO.getAllPatients();
      
        for (int i = 0; i < patList.size(); i++) {
      
            pData.add(patList.get(i));
      
        }
     
        table.setItems(pData);
  
    }
   
    //Add patient button
    @FXML
    public void patAddButtonAction(ActionEvent event) throws Exception {

        if (validations()) {
     
            if (!isUpdate) {
          
                if (PatientDAO.addPatient(pName.getText(), pInsuranceProvider.getText(), pPhone.getText(), pAddress.getText(), pCity.getText(), pState.getText(), pZipCode.getText())) {
              
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
               
                    alert.setTitle("Insert Patient");
                 
                    alert.setContentText("Patient record added successfully.");
                 
                    alert.show();
              
                    refreshRecords();
             
                }
              
                else {
             
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                
                    alert.setTitle("Record Insertion failed.");
                
                    alert.setHeaderText("Error");
                
                    alert.setContentText("Could not insert patient record.");
                
                    alert.show();
             
                }
           
            } else {
           
                loadSelectedPatientForUpdate();
             
                if (PatientDAO.updatePatient(selectedPatient)) {
             
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                 
                    alert.setTitle("Update Patient");
                  
                    alert.setContentText("Patient record updated successfully.");
                    
                    alert.show();
                  
                    refreshRecords();
              
                } else {
               
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                   
                    alert.setTitle("Record Update failed.");
                   
                    alert.setHeaderText("Error");
                   
                    alert.setContentText("Could not update patient record.");
                   
                    alert.show();

                }

            }
        
        }

    }
    
    //Validations for input fields

    private boolean validations() {

        if (pName.getText() == null || pName.getText().equals("")) {
        
            Alert alert = new Alert(Alert.AlertType.ERROR);
           
            alert.setTitle("Invalid Input");
           
            alert.setHeaderText("Error");
           
            alert.setContentText("Please enter Patient name.");
           
            alert.show();
           
            pName.requestFocus();
           
            return false;
        
        }
    
        if (pInsuranceProvider.getText() == null || pInsuranceProvider.getText().equals("")) {
       
            Alert alert = new Alert(Alert.AlertType.ERROR);
          
            alert.setTitle("Invalid Input");
           
            alert.setHeaderText("Error");
           
            alert.setContentText("Please enter Patient Insurance Provider.");
           
            alert.show();
           
            pInsuranceProvider.requestFocus();
           
            return false;
        
        }
     
        if (pPhone.getText() == null || pPhone.getText().equals("")) {
       
            Alert alert = new Alert(Alert.AlertType.ERROR);
           
            alert.setTitle("Invalid Input");
           
            alert.setHeaderText("Error");
           
            alert.setContentText("Please enter Patient phone no.");
           
            alert.show();
           
            pPhone.requestFocus();
           
            return false;
        
        }
        
        if (pCity.getText() == null || pCity.getText().equals("")) {
       
            Alert alert = new Alert(Alert.AlertType.ERROR);
           
            alert.setTitle("Invalid Input");
           
            alert.setHeaderText("Error");
           
            alert.setContentText("Please enter City.");
           
            alert.show();
           
            pCity.requestFocus();
           
            return false;
        
        }
       
        if (pState.getText() == null || pState.getText().equals("")) {
       
            Alert alert = new Alert(Alert.AlertType.ERROR);
           
            alert.setTitle("Invalid Input");
           
            alert.setHeaderText("Error");
           
            alert.setContentText("Please enter State.");
           
            alert.show();
           
            pState.requestFocus();
          
            return false;
        
        }
       
        if (pZipCode.getText() == null || pZipCode.getText().equals("")) {
       
            Alert alert = new Alert(Alert.AlertType.ERROR);
           
            alert.setTitle("Invalid Input");
           
            alert.setHeaderText("Error");
           
            alert.setContentText("Please enter Zip Code.");
           
            alert.show();
           
            pZipCode.requestFocus();
           
            return false;
        
        }
       
        if (pAddress.getText() == null || pAddress.getText().equals("")) {
       
            Alert alert = new Alert(Alert.AlertType.ERROR);
           
            alert.setTitle("Invalid Input");
           
            alert.setHeaderText("Error");
           
            alert.setContentText("Please enter Address.");
           
            alert.show();
           
            pAddress.requestFocus();
           
            return false;
        }

        return true;
   
    }

    private void loadPatientForUpdate() {
    
        pv_addPatButton.setText("Update");
        pName.setText(selectedPatient.getPname());
        pPhone.setText(selectedPatient.getPphone());
        pInsuranceProvider.setText(selectedPatient.getPinspr());
        pState.setText(selectedPatient.getPstate());
        pZipCode.setText(selectedPatient.getPzipcode());
        pCity.setText(selectedPatient.getPcity());
        pAddress.setText(selectedPatient.getPaddress());
    
    }

    private void loadSelectedPatientForUpdate() {
     
        selectedPatient.setPname(pName.getText());
        selectedPatient.setPphone(pPhone.getText());
        selectedPatient.setPinspr(pInsuranceProvider.getText());
        selectedPatient.setPstate(pState.getText());
        selectedPatient.setPzipcode(pZipCode.getText());
        selectedPatient.setPcity(pCity.getText());
        selectedPatient.setPaddress(pAddress.getText());
    
    }
    
    
    @FXML
    private void deleteRecord() throws Exception {
    
        if (selectedPatient == null) {
      
            Alert alert = new Alert(Alert.AlertType.ERROR);
           
            alert.setTitle("Invalid Input");
           
            alert.setHeaderText("Error");
           
            alert.setContentText("Please select a patient first to delete.");
           
            alert.show();
       
        } else {
       
            if (PatientDAO.deletePatient(selectedPatient)) {
           
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
               
                alert.setTitle("Delete patient record");
               
                alert.setContentText("Patient record deleted successfully.");
               
                alert.show();
               
                refreshRecords();
           
            } else {
            
                Alert alert = new Alert(Alert.AlertType.ERROR);
               
                alert.setTitle("Invalid Input");
               
                alert.setHeaderText("Error");
               
                alert.setContentText("Error occured while deleting record.");
               
                alert.show();
           
            }
      
        }
  
    }

    @FXML
    private void refreshRecords() throws Exception {
       
        pv_addPatButton.setText("Add");
        isUpdate = false;
        selectedPatient = null;
        pName.setText("");
        pPhone.setText("");
        pInsuranceProvider.setText("");
        pState.setText("");
        pZipCode.setText("");
        pCity.setText("");
        pAddress.setText("");
        populateTable();
   
    }

}
