/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dek8v5stopwatch;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
/**
 *
 * @author DewiEndah
 */
public class Dek8v5Stopwatch extends Application {
    //declaration of variables
    private ImageView handImageView;
    private ImageView dialImageView;
    private double secondsElapsed = 0;
    private double tickTimeInSeconds = 1;
    private double angleDeltaPerSeconds = 6.0;
    private Timeline timeLine;
    private int second1 = 0;
    private int second2 = 0;
    private int minute1 = 0;
    private int minute2 = 0;
    private int hour1 = 0;
    private int hour2 = 0;
    private Label digital;
    private Image dialImage;
    private Image handImage;
    private String dialImageName = "clockface.png";
    private String handImageName = "hand.png";
    private Button start;
    private Button stop;
    private Button reset;
    private StackPane rootContainer;
    private KeyFrame keyFrame;
    private HBox buttonHolder; 
    private VBox vBoxHolder;

    @Override
    public void start(Stage primaryStage) {
        //create objects
        rootContainer = new StackPane();
        dialImageView = new ImageView();
        handImageView = new ImageView();    
        dialImage = new Image(getClass().getResourceAsStream(dialImageName));
        handImage = new Image(getClass().getResourceAsStream(handImageName));
        dialImageView.setImage(dialImage);
        handImageView.setImage(handImage);
        rootContainer.getChildren().addAll(dialImageView, handImageView);
        
        start = new Button("Start");
        stop = new Button("Stop");
        reset = new Button("Reset"); 
        
        //Hbox for the buttons
        buttonHolder = new HBox();
        buttonHolder.setSpacing(30);
        buttonHolder.setAlignment(Pos.BOTTOM_CENTER);
        //buttonHolder.setAlignment(Pos.TOP_CENTER);
        buttonHolder.getChildren().addAll(start, stop, reset);
      

        //label for the digital stopwatch
        digital = new Label();
        digital.setAlignment(Pos.CENTER);
        digital.setText(""+ hour1 + hour2+ " h : "+ minute1 + minute2 + " m : " + second1 + second2 +" s");
        digital.setFont(Font.font("Rockwell Extra Bold", 50));

        vBoxHolder = new VBox();
        vBoxHolder.setAlignment(Pos.CENTER);
        vBoxHolder.setSpacing(30);
        vBoxHolder.getChildren().addAll(rootContainer, digital , buttonHolder);
        
       
        //Event handler for start button
        start.setOnAction((ActionEvent event) -> {
             startClick();
        });
        
        
        //event handler for stop button
        stop.setOnAction((ActionEvent event) -> {
            timeLine.stop();
        });
        
        
        //event handler for reset button
        reset.setOnAction((ActionEvent event) -> {
            timeLine.stop();
            resetStopWatch();
        });
        
        //set the clip
        Rectangle clip = new Rectangle(600, 290);
        rootContainer.setClip(clip);
        
        Scene scene = new Scene(vBoxHolder, 600, 600);
        primaryStage.setTitle("Stopwatch");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
    }
    
    public void startClick(){
            //to make start event only occur once even when we click it several times when it runs
            boolean running = false;
            if (timeLine != null) {
                if (timeLine.getStatus() == Animation.Status.RUNNING) {
                    running = true;
                    timeLine.stop();
                    }
            }
            //set the keyframe, to make the hand move as we expected
            keyFrame = new KeyFrame(Duration.millis(tickTimeInSeconds * 1000), actionEvent -> update());//durantion is a class.milles is a static method
            timeLine = new Timeline(keyFrame);
            timeLine.setCycleCount(Animation.INDEFINITE);
            timeLine.play();
            
            if (running) {
            timeLine.play();
            }
        }
    
    //update method to update ticking on both hand and digital
    public void update() {
        secondsElapsed++;
        //rotation of the handImageView is secondsElapsed*angleDeltaPerSeconds
        handImageView.setRotate(secondsElapsed * angleDeltaPerSeconds);
       
        //updating the second2 every seconds elapsed
        second2++;
        
        //updating the sequence of digital stopwatch
        //after the first second2 hit 10, we send the increment into the second1
        if (second2 == 10) {
            second1++;
            second2 = 0;
            //when the second hit 60s, we send 1 minute value to minute variable,
            if (second1 == 6) {
                minute2++;
                second1 = 0;
                //when the minute is reach 10, we bumb the minute1 once and set the minute 2 back to 0 
                if (minute2 == 10) {
                    minute1++;
                    minute2 = 0;
                    //the same thing works for minute 1 and bumping hour1
                    if(minute1 ==6){
                        hour2++;
                        minute1=0;
                        //last one, bump the hour1 when hour2 reaching 10
                        if(hour2 == 10){
                            hour1++;
                            hour2=0;
                            if (hour2 == 9 && hour1 == 9){
                                resetStopWatch();
                            }
                        }
                    }
                }    
            }
        }
        digital.setText(""+ hour1 + hour2+ " h : "+minute1 + minute2 + " m : " + second1 + second2 + " s");

    }
    
    //reset method
    public void resetStopWatch() {
        secondsElapsed = 0;
        handImageView.setRotate(0);

        minute1 = minute2 = second1 = second2 = hour1 = hour2 = 0;
        digital.setText(""+ hour1 + hour2+ " h : "+ minute1 + minute2 + " m : " + second1 + second2 +" s");
    }
    


    public static void main(String[] args) {
        launch(args);
    }

}