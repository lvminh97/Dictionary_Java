package com.example.demo2;

import com.almasb.fxgl.entity.action.Action;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class translateController {
    @FXML
    private TextField text1;
    @FXML
    private TextField text2;
    @FXML
    private TextField word;
    @FXML
    private TextField meaning;
    private int count = 1;
    public void onTransClick(){
        try {
            String text = word.getText();
            String url = "https://google-translate113.p.rapidapi.com/api/v1/translator/text";
            String params;
            if (count % 2 != 0){
                params = "from=en&to=vi&text="+text;
            }
            else
            {
                params = "from=vi&to=en&text="+text;
            }
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("X-RapidAPI-Key", "0100db825bmshd6de7c951c3993fp123719jsn61abb0aa945f")
                    .header("X-RapidAPI-Host", "google-translate113.p.rapidapi.com")
                    .POST(HttpRequest.BodyPublishers.ofString(params, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String[] terms = response.body().split("\"trans\":\"|\"\\}");
            meaning.setText(terms[1]);
            //System.out.println(terms[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void onSwitch(){
        count ++;
        if (count % 2 != 0){
            text1.setText("English");
            text2.setText("Vietnames");
        }
        else{
            text1.setText("Vietnamese");
            text2.setText("English");
        }
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
