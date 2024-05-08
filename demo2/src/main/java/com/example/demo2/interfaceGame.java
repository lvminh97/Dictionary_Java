package com.example.demo2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public abstract class interfaceGame implements Initializable {
    @FXML
    protected Button button1;
    @FXML
    protected Button button2;
    @FXML
    protected Button button3;
    @FXML
    protected Button button4;
    @FXML
    protected TextField textField;
    @FXML
    protected TextField points;
    @FXML
    protected Text timer;
    @FXML
    protected TextField alarmTime;
}
