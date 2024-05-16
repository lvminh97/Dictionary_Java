package com.example.demo2;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController extends interfaceGame {
    int timerCnt = 60; // 1 minute
    private int currentPoint = 0;
//    private Background defaultBackground;
//    private int currentNum;
    private int numQues;
    private Map<Integer,String> map = new HashMap<>();
    private Map<Integer, String> map1 = new HashMap<>();
    private Map<Integer, String> map2 = new HashMap<>();
    private Map<Integer, String> map3 = new HashMap<>();
    private Map<Integer, String> map4 = new HashMap<>();
    private Map<Integer, String> map5 = new HashMap<>();
    private boolean check = true;
    private final MediaPlayer[] mediaPlayers = new MediaPlayer[3];

    Timeline timeline = new Timeline(
        new KeyFrame(Duration.seconds(1),
            e -> {
                if(timerCnt == 0){
                    //System.out.println("ALARM!");
                    timer.setText("Time's up");
                    // Tạo một đối tượng Media từ tập tin nhạc
                    mediaPlayers[2].play();
                    setResult();
                }
                else if(timerCnt > 0){
                    timerCnt--;
                    timer.setText(standardizeTimeString(timerCnt));
                }
            }
        )
    );
    @FXML
    public void setResult(){
        try {
            // Load FXML file của Scene mới
            FXMLLoader loader = new FXMLLoader(getClass().getResource("result-view.fxml"));
            Parent addWordParent = loader.load();

            // Get controller của Scene mới
            GameResultController gameResultController = loader.getController();
            // Thực hiện bất kỳ thao tác nào cần thiết với controller của Scene mới
            //addWordController.map(words);
            gameResultController.setPoint(currentPoint);
            //addWordController.onAddClick();
            // Lấy Stage hiện tại từ một Node bất kỳ trong Scene
            Stage stage = (Stage) textField.getScene().getWindow();

            // Tạo Scene mới với Parent mới
            Scene addWordScene = new Scene(addWordParent);

            // Chuyển đổi Scene
            stage.setScene(addWordScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void onNextClick(){
        check = true;
        button1.setBackground(Background.fill(Color.LIGHTGRAY));
        button2.setBackground(Background.fill(Color.LIGHTGRAY));
        button3.setBackground(Background.fill(Color.LIGHTGRAY));
        button4.setBackground(Background.fill(Color.LIGHTGRAY));
        Random rand2 = new Random();
        int nums = map.size();
        numQues = rand2.nextInt(nums) + 1;
        textField.setText(map.get(numQues));

        Random rand = new Random();
        int num = rand.nextInt(4) + 1;
        if (num == 1){
            button1.setText(map1.get(numQues));
            button2.setText(map2.get(numQues));
            button3.setText(map3.get(numQues));
            button4.setText(map4.get(numQues));
        }
        if (num == 2){
            button1.setText(map2.get(numQues));
            button2.setText(map1.get(numQues));
            button3.setText(map3.get(numQues));
            button4.setText(map4.get(numQues));
        }
        if (num == 3){
            button1.setText(map3.get(numQues));
            button2.setText(map2.get(numQues));
            button3.setText(map1.get(numQues));
            button4.setText(map4.get(numQues));
        }
        if (num == 4){
            button1.setText(map4.get(numQues));
            button2.setText(map2.get(numQues));
            button3.setText(map3.get(numQues));
            button4.setText(map1.get(numQues));
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mediaPlayers[0] = new MediaPlayer(
                new Media(new File("src\\main\\resources\\com\\example\\demo2\\assets\\456161__bwg2020__correct.wav").toURI().toString()));
        mediaPlayers[1] = new MediaPlayer(
                new Media(new File("src\\main\\resources\\com\\example\\demo2\\assets\\650842__andreas__wrong-answer-buzzer.wav").toURI().toString()));
        mediaPlayers[2] = new MediaPlayer(
                new Media(new File("src\\main\\resources\\com\\example\\demo2\\assets\\435577__dangale__phone-ringing-5-times-then-picked-up-uk-gpo-746.wav").toURI().toString()));

        timer.setText(standardizeTimeString(timerCnt));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        BufferedReader reader = null;
        try {
            String filePath = "src\\main\\java\\com\\example\\demo2\\cauhoi.txt";
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            StringBuilder ques = new StringBuilder();
            int index = 1;
            int count = 0;
            while ((line = reader.readLine()) != null) {

                if (count == 0) ques.append(line);
                //System.out.println(line);
                if (count == 0 && ques.charAt(ques.length()-1) == '.') {
                    map.put(index, ques.toString());
                }
                if (ques.charAt(ques.length()-1) != '.') ques.append("\n");
                if (count > 0 && count <= 5){
                    if (count == 1) map1.put(index,line);
                    else if (count == 2) map2.put(index,line);
                    else if (count == 3) map3.put(index,line);
                    else if (count == 4) map4.put(index,line);
                    else map5.put(index,line);
                }
                if (ques.charAt(ques.length()-1) == '.'){
                    if (count <= 4){
                        count ++;
                    } else {
                        index++;
                        count = 0;
                        ques.setLength(0);
                    }
                }
            }
            button1.setBackground(Background.fill(Color.LIGHTGRAY));
            button2.setBackground(Background.fill(Color.LIGHTGRAY));
            button3.setBackground(Background.fill(Color.LIGHTGRAY));
            button4.setBackground(Background.fill(Color.LIGHTGRAY));
            Random rand2 = new Random();
            int nums = map.size();
            numQues = rand2.nextInt(nums) + 1;
            textField.setText(map.get(numQues));

            Random rand = new Random();
            int num = rand.nextInt(4) + 1;
            if (num == 1){
                button1.setText(map1.get(numQues));
                button2.setText(map2.get(numQues));
                button3.setText(map3.get(numQues));
                button4.setText(map4.get(numQues));
            }
            if (num == 2){
                button1.setText(map2.get(numQues));
                button2.setText(map1.get(numQues));
                button3.setText(map3.get(numQues));
                button4.setText(map4.get(numQues));
            }
            if (num == 3){
                button1.setText(map3.get(numQues));
                button2.setText(map2.get(numQues));
                button3.setText(map1.get(numQues));
                button4.setText(map4.get(numQues));
            }
            if (num == 4){
                button1.setText(map4.get(numQues));
                button2.setText(map2.get(numQues));
                button3.setText(map3.get(numQues));
                button4.setText(map1.get(numQues));
            }

            // Xử lý sự kiện khi một button được nhấn
            EventHandler<ActionEvent> buttonHandler = event -> {
                Button clickedButton = (Button) event.getSource();
                System.out.println("Button clicked: " + clickedButton.getText());
                if (check && clickedButton.getText().equals(map5.get(numQues))){
                    currentPoint = currentPoint + 10;
                    points.setText(String.valueOf(currentPoint));
                    check = false;
                }
                if (clickedButton.getText().equals(map5.get(numQues))){
                    clickedButton.setBackground(Background.fill(Color.LIGHTBLUE));
                    mediaPlayers[0].seek(Duration.ZERO);
                    mediaPlayers[0].play();

                } else{
                    clickedButton.setBackground(Background.fill(Color.RED));
                    check = false;
                    mediaPlayers[1].seek(Duration.ZERO);
                    mediaPlayers[1].play();

                    if (button1.getText().equals(map5.get(numQues))) button1.setBackground(Background.fill(Color.LIGHTBLUE));
                    else if (button2.getText().equals(map5.get(numQues))) button2.setBackground(Background.fill(Color.LIGHTBLUE));
                    else if (button3.getText().equals(map5.get(numQues))) button3.setBackground(Background.fill(Color.LIGHTBLUE));
                    else button4.setBackground(Background.fill(Color.LIGHTBLUE));
                }
            };
            // Gán cùng một xử lý sự kiện cho tất cả các button
            button1.setOnAction(buttonHandler);
            button2.setOnAction(buttonHandler);
            button3.setOnAction(buttonHandler);
            button4.setOnAction(buttonHandler);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String standardizeTimeString(int timerCnt) {
        if(timerCnt < 0)
            timerCnt = 0;

        return String.format("%02d:%02d:%02d", timerCnt / 3600, timerCnt % 3600 / 60, timerCnt % 60);
    }
}