package org.example;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.util.converter.FormatStringConverter;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class View implements Initializable {
    @FXML
    public TextField inputConcentration;
    @FXML
    private TextField alpha;

    @FXML
    private TextField potHoldUp;
    @FXML
    private TextField columnHoldUp;
    @FXML
    private TextField condenserHoldUp;
    @FXML
    private TextField platesNumber;

    @FXML
    private TextField liquidFlow;
    @FXML
    private TextField inputFlow;
    @FXML
    private TextField outputFlow;

    @FXML
    private Button mainButton;
    @FXML
    private Button clearButton;

    @FXML
    private CheckBox addCheckBox;

    @FXML
    private LineChart<Double,Double> xyChart;

    private ArrayList <TextField> textFields;

    private Controller controller;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainButton.setDisable(false);

        addCheckBox.indeterminateProperty().setValue(false);

        textFields = new ArrayList<>();
        textFields.add(inputConcentration);
        textFields.add(alpha);
        textFields.add(potHoldUp);
        textFields.add(columnHoldUp);
        textFields.add(condenserHoldUp);
        textFields.add(platesNumber);
        textFields.add(liquidFlow);
        textFields.add(inputFlow);
        textFields.add(outputFlow);

        DecimalFormatSymbols separatorIsPaint = new DecimalFormatSymbols();
        separatorIsPaint.setDecimalSeparator('.');

        textFields.forEach(textField -> textField.setTextFormatter(new TextFormatter<>(
                new FormatStringConverter<>(new DecimalFormat("###0.######", separatorIsPaint)),
                null,
                change -> {
                    if (change.getControlNewText().matches("(\\d+\\.?\\d*)|([.\\d]\\d*)|^$")) {
                        return change;
                    }
                    return null;
                })));

        platesNumber.setTextFormatter(new TextFormatter<Double>(
                new FormatStringConverter<>(new DecimalFormat("###0")),
                null,
                change -> {
                    if (change.getControlNewText().matches("(\\d*)|^$")) {
                        return change;
                    }
                    return null;
                }
        ));

        textFields.forEach(textField -> textField.focusedProperty().
                addListener((observable, wasFocused, isNowFocused) -> {
                    if(! isNowFocused){
                        buttonSwitch();
                    }
                }));

        inputConcentration.setText("0.001");
        alpha.setText("1.25");
        potHoldUp.setText("30");
        columnHoldUp.setText("1");
        condenserHoldUp.setText("0.2");
        platesNumber.setText("29");
        liquidFlow.setText("10");
        inputFlow.setText("0");
        outputFlow.setText("0");

    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    public ArrayList<Double> getUserInputValues(){
        return textFields.stream()
                .filter(textField -> textField != platesNumber)
                .map(TextInputControl::getText)
                .map(Double::parseDouble)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Integer getPlatesNumber(){
        return Integer.parseInt(platesNumber.getText());
    }

    @FXML
    private void inject(){
        buttonSwitch();
        focusChange();
    }

    @FXML
    private void pressed(){

        List<Double[]> data = controller.getData();

        ObservableList<XYChart.Data<Double,Double>> dataObservableList = FXCollections.observableArrayList();

        data.stream()
                .map(i -> new XYChart.Data<>(i[0], i[1]))
                .forEach(dataObservableList::add);

        XYChart.Series<Double,Double> series = new XYChart.Series<>();

        series.setData(dataObservableList);

        if(addCheckBox.isSelected() || xyChart.getData().isEmpty()){
            xyChart.getData().add(series);
        }else {
            xyChart.getData().set(xyChart.getData().size() - 1, series);
        }
    }

    @FXML
    private void clearButtonPressed(){
        xyChart.getData().clear();
    }

    private boolean emptyFieldsArePresent() {
        return textFields.stream()
                .anyMatch(textField -> textField.getText() == null || textField.getText().trim().isEmpty());
    }

    private void buttonSwitch(){
        mainButton.setDisable(emptyFieldsArePresent());
    }

    private void focusChange(){
        for (TextField textField : textFields) {
            if(textField.isFocused()) {
                if(textFields.indexOf(textField) == textFields.size() - 1){
                    if (emptyFieldsArePresent()) {
                        textFields.get(0).requestFocus();
                    } else{
                        mainButton.requestFocus();
                    }
                    return;
                }
                textFields.get((textFields.indexOf(textField)+1)).requestFocus();
                return;
            }
        }
    }
}
