package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public abstract class interfaceApp implements Initializable {

    @FXML
    protected TextField textField;
    @FXML
    protected TextField onlineTranslateTextField;
    @FXML
    protected ListView<String> listView;
    protected ListView<String> onlineListView;
    @FXML
    protected ListView textArea;
    @FXML
    protected ListView sentenceArea;

    @FXML
    protected Button sayWelcome;
    @FXML
    protected Button button;
}
