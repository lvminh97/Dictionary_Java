package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kotlin.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class addWordController {
    @FXML
    private TextField word;
    @FXML
    private TextField pronounce;
    @FXML
    private TextField meaning;
    @FXML
    private TextField title;
    private String wordtype;
    private String prountype;
    private String meantype;
    private HashMap<String, Pair> words = new HashMap<>();
    public void map(HashMap<String,Pair> words){
        this.words = words;
    }
    @FXML
    public void onAddClick() {
        wordtype = word.getText();
        wordtype = wordtype.toLowerCase();
        prountype = pronounce.getText();
        meantype = meaning.getText();
        words.put(wordtype, new kotlin.Pair(prountype, meantype));
        if (!wordtype.isEmpty()){
            title.setText("Add "+wordtype+" successfully !");
        }
    }

    @FXML
    public void onBackClick(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent addWordParent = loader.load();
            // Get controller của Scene mới
            HelloController helloController = loader.getController();
            // Thực hiện bất kỳ thao tác nào cần thiết với controller của Scene mới

            helloController.addmap(words);
            //  helloController.initialize();
            // Lấy Stage hiện tại
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Tạo Scene mới với Parent mới
            Scene addWordScene = new Scene(addWordParent);

            // Chuyển đổi Scene
            stage.setScene(addWordScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}