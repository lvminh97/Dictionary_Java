package com.example.demo2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import kotlin.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HelloController extends interfaceApp {
    private HashMap<String, Pair> words = new HashMap<>();
    private Stack<String> st = new Stack<>();
    String currentWord;
    private String typedWord;
    private String typedSentence;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // Đường dẫn tới file HTML của bạn
            String filePath = "src\\main\\java\\com\\example\\demo2\\E_V.txt";

            // Đọc file HTML
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            reader.close();

            // Tách các từ điển từ nội dung HTML
            Pattern pattern = Pattern.compile("<html><i>(.*?)</i><br/><ul><li><b><i>(.*?)</i></b><ul><li><font color='#cc0000'><b>(.*?)</b></font>");
            Matcher matcher = pattern.matcher(content.toString());

            // Duyệt qua các kết quả và in ra màn hình
            while (matcher.find()) {
                String pronoun = "";
                String word = matcher.group(1);
                if (word.contains("/")) {
                    pronoun = word.substring(word.indexOf('/'));
                    char c = word.charAt(0);
                    word = word.substring(word.indexOf(c), word.indexOf('/')).trim();
                }
                if (word.contains("<")) {
                    pronoun = word.substring(word.indexOf('<') + 1);
                    word = word.substring(0, word.indexOf('<')).trim();
                }
                String meaning = matcher.group(3);
                Pair pair = new Pair(pronoun, meaning);
                words.put(word, pair);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onClick() {
        typedWord = textField.getText();
        typedWord = typedWord.toLowerCase();
        listView.getItems().clear();
        for (String key : words.keySet()) {
            if (!Objects.equals(typedWord, "") && key.startsWith(typedWord)) {
                listView.getItems().addFirst(key);
            }
        }
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                textField.setText(newValue); // Thiết lập văn bản trong TextField thành từ được chọn
            }
        });

        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                OnSearchClick();
            }
        });
        listView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                OnSearchClick();
            }
        });
    }

    @FXML
    public void OnSearchClick() {
        textArea.getItems().clear();
        typedWord = textField.getText();
        typedWord = typedWord.toLowerCase();
        if (textArea.getItems().contains("  -  " + typedWord + " " + words.get(typedWord).getFirst())) {
            return;
        }
        if (typedWord.isEmpty()) {
            textArea.getItems().add("ENSURE TO TYPE OR CHOOSE A WORD FIRST. PLEASE TRY AGAIN");
        } else if (words.containsKey(typedWord)) {
            textArea.getItems().add(0, "        " + words.get(typedWord).getSecond());
            textArea.getItems().add(0, "  -  " + typedWord + " " + words.get(typedWord).getFirst());
            if (!st.contains(typedWord)) {
                st.add(typedWord);
            }
        } else {
            textArea.getItems().add("SORRY :( " + "\n" + "WE DO NOT FIND OUT THE WORD YOU WANT TO LOOK FOR :(" + "\n"
                    + "PLEASE CHECK THE SPELLING. IF IT IS FALSE, PLEASE TRY AGAIN." + "\n" + "OTHERWISE WE SUGGEST THE ADDING WORD METHOD");
        }
    }

//    public void OnSearchByApi() {
//
//        typedSentence = onlineTranslateTextField.getText();
//        typedSentence = typedSentence.toLowerCase();
//
//        try {
//            String url = "https://google-translate113.p.rapidapi.com/api/v1/translator/text";
//            String params = "from=en&to=vi&text=" + typedSentence;
//
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(url))
//                    .header("content-type", "application/x-www-form-urlencoded")
//                    .header("X-RapidAPI-Key", "0100db825bmshd6de7c951c3993fp123719jsn61abb0aa945f")
//                    .header("X-RapidAPI-Host", "google-translate113.p.rapidapi.com")
//                    .POST(HttpRequest.BodyPublishers.ofString(params, StandardCharsets.UTF_8))
//                    .build();
//
//            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//            String[] terms = response.body().split("\"trans\":\"|\"\\}");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public void onDeleteClick() {
        typedWord = textField.getText();
        typedWord = typedWord.toLowerCase();
        if (words.containsKey(typedWord)) {
            words.remove(typedWord);
            textArea.getItems().add("Delete successfully!");
        } else {
            textArea.getItems().add("Does not have in the dictionary!");
        }
    }



    @FXML
    public void onSpeechClick() {
        typedWord = textField.getText();
        String speakString = typedWord;
        try {
            // Set property as Kevin Dictionary
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");

            // Register Engine
            javax.speech.Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");

            // Create a Synthesizer
            javax.speech.synthesis.Synthesizer synthesizer
                    = javax.speech.Central.createSynthesizer(
                    new javax.speech.synthesis.SynthesizerModeDesc(Locale.US));

            // Allocate synthesizer
            synthesizer.allocate();

            // Resume Synthesizer
            synthesizer.resume();

            // Speaks the given text
            // until the queue is empty.
            if (words.containsKey(speakString)) {
                synthesizer.speakPlainText(
                        speakString, null);
                // synthesizer.waitEngineState(
                //      javax.speech.synthesis.Synthesizer.QUEUE_EMPTY);
            } else {
                synthesizer.speakPlainText(
                        "Sorry we do not find out this word. Please check again!", null);
            }
            synthesizer.waitEngineState(
                    javax.speech.synthesis.Synthesizer.QUEUE_EMPTY);
            // Deallocate the Synthesizer.
            // synthesizer.deallocate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void onSynClick() {
        String apiKey = "2963719886msh8f3612d6a989751p14758djsn3c6e494bdb92";
        if (textField.getText().isEmpty()) textArea.getItems().add("PLEASE TYPE WORD FIRST");
        else {
            if (!textArea.getItems().isEmpty()) textArea.getItems().clear();
            try {

                String apiUrl = "https://synonyms-spintax-generator-for-article-spinner.p.rapidapi.com/data";
                URI uri = URI.create(apiUrl);

                HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
                connection.setRequestMethod("POST");

                // Set request headers
                connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                connection.setRequestProperty("X-RapidAPI-Key", apiKey);
                connection.setRequestProperty("X-RapidAPI-Host", "synonyms-spintax-generator-for-article-spinner.p.rapidapi.com");

                String requestBody = "article=" + textField.getText();
                connection.setDoOutput(true);
                connection.getOutputStream().write(requestBody.getBytes());

                // Get the response
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    String[] words = response.substring(1, response.length() - 1).split("\\|");
                    for (String x : words) {
                        textArea.getItems().add(x);
                    }

                } else {
//                System.err.println("Error response code: " + responseCode);
                    textArea.getItems().add("NOT FOUND");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void addmap(HashMap<String,Pair> words){
        this.words = words;
    }
    @FXML
    public void onAddWordClick(ActionEvent event){
        // thuc hien switch scene
        try {
            // Load FXML file của Scene mới
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scene4.fxml"));
            Parent addWordParent = loader.load();

            // Get controller của Scene mới
            addWordController addWordController = loader.getController();

            // Thực hiện bất kỳ thao tác nào cần thiết với controller của Scene mới
            addWordController.map(words);
            addWordController.onAddClick();
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
    @FXML
    public void onTranslate(){
        try {
            // Load FXML file của Scene mới
            FXMLLoader loader = new FXMLLoader(getClass().getResource("translate-view.fxml"));
            Parent addWordParent = loader.load();

            // Get controller của Scene mới
//            gameResultController gameResultController = loader.getController();
//            // Thực hiện bất kỳ thao tác nào cần thiết với controller của Scene mới
//            //addWordController.map(words);
//            gameResultController.setPoint(currentPoint);
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
//    public void onApiTranslateClick(){
//        try {
//            String url = "https://google-translate113.p.rapidapi.com/api/v1/translator/text";
//            String params = "from=en&to=vi&text=red flowers";
//
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(url))
//                    .header("content-type", "application/x-www-form-urlencoded")
//                    .header("X-RapidAPI-Key", "0100db825bmshd6de7c951c3993fp123719jsn61abb0aa945f")
//                    .header("X-RapidAPI-Host", "google-translate113.p.rapidapi.com")
//                    .POST(HttpRequest.BodyPublishers.ofString(params, StandardCharsets.UTF_8))
//                    .build();
//
//            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//            String[] terms = response.body().split("\"trans\":\"|\"\\}");
//
//            System.out.println(terms[1]);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    @FXML
    public void onGameClick(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameplay-view.fxml"));
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