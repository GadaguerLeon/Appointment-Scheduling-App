/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwareiialternate.view_controller;

import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * 
 */
public class HomeViewController implements Initializable {
    
    @FXML
    private StackPane parentContainer;
    
    @FXML
    private AnchorPane container;
    
    @FXML
    private Button hv_apptButton;
    
    @FXML
    private Button hv_patButton;
    
    @FXML 
    private Button hv_repButton;
    
    
    
    @FXML
    public void apptButtonAction(ActionEvent event) throws Exception {

                    /*
                    *  Implements a Timeline and creates a delay
                    *  with a transition moving to the next scene
                    */
                    
                    Parent root = FXMLLoader.load(getClass().getResource("AppointmentView.fxml"));
                    
                    Scene scene = hv_apptButton.getScene();
                    
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

        Scene scene = hv_repButton.getScene();

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
    public void patButtonAction(ActionEvent event) throws Exception {

                    /*
                    *  Implements a Timeline and creates a delay
                    *  with a transition moving to the next scene
                    */
                    
                    Parent root = FXMLLoader.load(getClass().getResource("PatientView.fxml"));
                    
                    Scene scene = hv_patButton.getScene();
                    
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
