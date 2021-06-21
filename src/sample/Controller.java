package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.fileUtils.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label pathLabel;
    @FXML
    private TextField filePathTextField;
    @FXML
    private Button analyseBtn;
    @FXML
    private Button stopBtn;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TableView<Result> table;
    @FXML
    private TableColumn<Result, String> word;
    @FXML
    private TableColumn<Result, Integer> occurrences;


    TextAsWordOccurrences textAsWordOccurrences = new TextAsWordOccurrences();
    TextAsLetterOccurrences textAsLetterOccurrences = new TextAsLetterOccurrences();
//    TextStorable textStorable = FileUtil.readTextFileLineByLine("./Sample.txt", textAsWordOccurrences);




    public void analyseTextFileHandler() {
//        String userFilePath = getUserFilePath();
//        analyseTextFile("Sample.txt", textAsWordOccurrences);
        analyseTextFile1("Sample.txt");
        displayResultsToTableView1();
//        displayResultsToTableView(textAsLetterOccurrences);
    }

    private void analyseTextFile(String userFilePath, HashMapStore hashMapStore) {
        MyRunnable myRunnable = new MyRunnable(userFilePath, hashMapStore);
        Thread t = new Thread(myRunnable);
        t.start();
        try {
            t.join();
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    private void analyseTextFile1(String userFilePath) {
        MyRunnable myRunnable = new MyRunnable(userFilePath, textAsWordOccurrences);
        Thread t = new Thread(myRunnable);
        t.start();
        try {
            t.join();
        } catch (Exception err) {
            System.out.println(err);
        }
    }

    private void displayResultsToTableView1() {
        ArrayList<Result> results = textAsWordOccurrences.getWordOccurrencesAsListOfResults();
        ObservableList<Result> resultsObservable = FXCollections.observableArrayList(results);
        table.setItems(resultsObservable);
    }

    private void displayResultsToTableView(HashMapStore hashMapStore) {
        ArrayList<Result> results = hashMapStore.getMapEntriesAsListOfResults();
        ObservableList<Result> resultsObservable = FXCollections.observableArrayList(results);
        table.setItems(resultsObservable);
    }

    private String getUserFilePath() {
        String userFilePath;

        try {
            userFilePath = filePathTextField.getText();
            return userFilePath;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        word.setCellValueFactory(new PropertyValueFactory<Result, String>("word"));
        occurrences.setCellValueFactory(new PropertyValueFactory<Result, Integer>("occurrences"));
    }

}
