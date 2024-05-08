package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class gameResultController{
    @FXML
    private TextField yourScore;
    @FXML
    private TextField yourLevel;

    public void setPoint(int point){
        yourScore.setText(String.valueOf(point));
        if (point <= 50) yourLevel.setText("LOSER");
        else if (point <= 70) yourLevel.setText("BEGINNER");
        else if (point <= 100) yourLevel.setText("IMMEDIATE");
        else yourLevel.setText("ADVANCED. GOOD JOB");
    }
    @FXML
    public void onBack(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent addWordParent = loader.load();

            // Get controller của Scene mới
//            translateApiController translateApiController = loader.getController();
//            // Thực hiện bất kỳ thao tác nào cần thiết với controller của Scene mới
////            addWordController.map(words);
////            addWordController.onAddClick();
//            translateApiController.
            // Lấy Stage hiện tại
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Tạo Scene mới với Parent mới
            Scene addWordScene = new Scene(addWordParent);

            // Chuyển đổi Scene
            stage.setScene(addWordScene);
            stage.show();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }
}