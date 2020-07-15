/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiialternate.view_controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import softwareiialternate.DAO.AppointmentDAO;
import softwareiialternate.DAO.DBConnection;
import softwareiialternate.model.Appointment;

/**
 * FXML Controller class
 *
 * 
 */
public class LoginFormController implements Initializable {

    @FXML
    private StackPane parentContainer;

    @FXML
    private AnchorPane container;

    @FXML
    private Text lf_title;

    @FXML
    private TextField lf_username;

    @FXML
    private TextField lf_password;

    @FXML
    private TextField lf_pin;

    @FXML
    private Button lf_loginbutton;

    @FXML
    private Label lf_labelerror;
    
    @FXML
    private Text lf_localtime;
    
    @FXML 
    private Text lf_officetime;
    
    @FXML
    private Text lf_localclock;
    
    @FXML
    private Text lf_officeclock; 

    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    @FXML
    public void handleButtonAction(ActionEvent event) throws Exception {

        if (event.getSource() == lf_loginbutton) {

            if (validateLogin().equals("Success")) {
                try {

                    /*
                    *  Implements a Timeline and creates a delay
                    *  so that errorLabel == success 
                    *  gets displayed longer before moving to the next scene
                     */
                    Parent root = FXMLLoader.load(getClass().getResource("HomeView.fxml"));

                    Scene scene = lf_loginbutton.getScene();

                    root.translateYProperty().set(scene.getHeight());
                    parentContainer.getChildren().add(root);

                    Timeline timeline = new Timeline();

                    KeyValue kv = new KeyValue(root.translateYProperty(), 0, Interpolator.EASE_IN);

                    KeyFrame kf = new KeyFrame(Duration.seconds(3), kv);

                    timeline.getKeyFrames().add(kf);

                    //Lambda expression satifying part G of the project requirements 
                    timeline.setOnFinished(event1 -> {

                        parentContainer.getChildren().remove(container);

                    });

                    timeline.play();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Appointments");
                  
                    alert.setHeaderText("Appointment Details");
                   
                    ArrayList<Appointment> list = new ArrayList<>();
                   
                    LocalDateTime ldt = LocalDateTime.now(ZoneId.of("America/New_York"));
//                    ldt = ldt.plusHours(26);
                    System.out.println("Date:" + ldt.toLocalDate().toString());
                    System.out.println("Time:" + ldt.toLocalTime().toString());
                    
                    LocalDateTime ldt2 = ldt.plusHours(4);                   
                    
                    list = AppointmentDAO.getAppointments(Timestamp.valueOf(ldt), Timestamp.valueOf(ldt2));
                    
                    String s = "";
                  
                    if(list == null || list.isEmpty()){
                  
                        s = "There are no appointments.";
                   
                    } else{
                 
                         for (int i = 0; i < list.size();i++){
                            Appointment ap = list.get(i);
                            s = s + ap.toString();
                        }
                  
                    }
                    
                    alert.setContentText(s);
                  
                    alert.show();

                } catch (IOException ex) {

                    System.err.println(ex.getMessage());

                }

            }
       
        }
   
    }
    static ZoneId etZoneId = ZoneId.of("America/New_York");
    /*
    Source for ResourceBundle control https://www.cc.gatech.edu/computing/pag/tmp/html_dir/java/util/ResourceBundle.java.html    
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Locale locale = Locale.getDefault();
    
        rb = ResourceBundle.getBundle("softwareiialternate.languages.login", locale);
       
        lf_title.setText(rb.getString("title"));
       
        lf_username.setPromptText(rb.getString("username"));
       
        lf_password.setPromptText(rb.getString("password"));
       
        lf_pin.setPromptText(rb.getString("pin"));
       
        lf_loginbutton.setText(rb.getString("loginbutton"));
        
        lf_localtime.setText(rb.getString("localtime"));
        
        lf_officetime.setText(rb.getString("officetime"));
        
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {        
        LocalTime currentTime = LocalTime.now();
        LocalTime estTime = LocalTime.now(etZoneId);

        lf_localclock.setText(currentTime.getHour()+ ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        lf_officeclock.setText(estTime.getHour() + ":" + estTime.getMinute() + ":" + estTime.getSecond());
        }),
            new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        
    }

    public LoginFormController() throws SQLException, Exception {

        conn = DBConnection.getConnection();

    }

    public static int counsler_id = -1;

    //This will check the validation status of the login 
    private String validateLogin() throws Exception {
        String status = "Success";
   
        String username = lf_username.getText();
      
        String password = lf_password.getText();
      
        String pin = lf_pin.getText();
        Locale locale = Locale.getDefault();
    
        ResourceBundle    rb = ResourceBundle.getBundle("softwareiialternate.languages.login", locale);

        if ((username.isEmpty() || password.isEmpty()) || pin.isEmpty()) {
       
            errorLabel(Color.RED, rb.getString("loginerrorempty"));
        
            status = "Error";
     
        } else {
            //SQL Query with error handling that satifies part A of the requirements 
            String sql = "SELECT * FROM counselor WHERE c_name =? AND c_password =? AND c_pin =?";
   
            try {
            
                PreparedStatement stmt = DBConnection.getConnection().prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, pin);
                rs = stmt.executeQuery();
                 
                if (!rs.next()) {
                
                    errorLabel(Color.RED, rb.getString("loginerrorfailure"));
                   
                    addUnSuccessfullAttempt(username);
                 
                    status = "Error";
               
                } else {
              
                    counsler_id = rs.getInt("c_id");
                 
                    addSuccessfullAttempt(username);
                 
                    errorLabel(Color.GREEN, rb.getString("loginerrorsuccess"));
              
                }
           
            } catch (SQLException ex) {
         
                System.err.println(ex.getMessage());
             
                status = "Exception";
        
            }
      
        }

        return status;

    }
    
    
    private void errorLabel(Color color, String text) {
        lf_labelerror.setTextFill(color);
        lf_labelerror.setText(text);
        System.out.println(text);
    } 
    
    
    //Logging of login attempts that separates attempts into separate successful/failed login attempts
    public void addSuccessfullAttempt(String user){
      
        File file = new File("src\\LoginTracker\\SuccessfullAttempts.txt");
      
        try (PrintWriter out = new PrintWriter(new FileWriter(file, true))) {
     
            String s = user + "    " +  LocalDate.now().toString() + "    " + LocalTime.now().toString();
         
            out.append(s + "\n");
       
        } catch (IOException ex) {
      
            Logger.getLogger(LoginFormController.class.getName()).log(Level.SEVERE, null, ex);
      
        }  
    }
    
    public void addUnSuccessfullAttempt(String user){
    
        File file = new File("src\\LoginTracker\\FailedAttempts.txt");
        
        try (PrintWriter out = new PrintWriter(new FileWriter(file, true))) {
        
            String s = user + "    " +  LocalDate.now().toString() + "    " + LocalTime.now().toString();
           
            out.append(s + "\n");
       
        } catch (IOException ex) {
     
            Logger.getLogger(LoginFormController.class.getName()).log(Level.SEVERE, null, ex);
       
        } 
  
    }
    
}
