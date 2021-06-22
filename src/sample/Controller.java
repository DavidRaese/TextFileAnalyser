package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.ThreadUtils.AnalyzeRunner;
import sample.ThreadUtils.WaitingForAnalyzingToFinishRunner;
import sample.UIUtils.ProgressBarView;
import sample.UIUtils.ProgressCounter;
import sample.UIUtils.ProgressLabelView;
import sample.fileUtils.*;

import java.io.File;
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
    private Label progressLabel;
    @FXML
    private TableView<Result> table;
    @FXML
    private TableColumn<Result, String> word;
    @FXML
    private TableColumn<Result, Integer> occurrences;


    File file;
    ProgressBarView progressBarView;
    ProgressLabelView progressLabelView;
    ProgressCounter progressCounter;
    TextAsWordOccurrences textAsWordOccurrences;
    TextAsWordOccurrencesSlow textAsWordOccurrencesSlow;
    TextAsLetterOccurrences textAsLetterOccurrences;
    WaitingForAnalyzingToFinishRunner waitingForAnalyzingToFinishRunner;
    Thread waitingForAnalyzingToFinishThread;


    public void analyseTextFileHandler() {
//        String userFilePath = getUserFilePath();
        resetProgressValues();
        analyseTextFile("Sample.txt", textAsWordOccurrencesSlow);
        displayResultsToTableView(textAsWordOccurrencesSlow);
    }

    public void stopAnalyzing() {
//        Thread waiterThread = waitingForAnalyzingToFinishThread;
//        Thread subThread = waitingForAnalyzingToFinishRunner.getSubThread();
//        subThread.interrupt();
//        waiterThread.interrupt();
        FileUtil.stopParsing();
    }

    private void resetProgressValues() {
        progressCounter.setTotalAnalyzedSize(0);
    }

    private void analyseTextFile(String userFilePath, HashMapStore hashMapStore) {
        AnalyzeRunner analyzeRunner = new AnalyzeRunner(userFilePath, hashMapStore);
        waitingForAnalyzingToFinishRunner = new WaitingForAnalyzingToFinishRunner(analyzeRunner, progressCounter, progressBar, textAsWordOccurrencesSlow, table);
        waitingForAnalyzingToFinishThread = new Thread(waitingForAnalyzingToFinishRunner);
        waitingForAnalyzingToFinishThread.start();

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

        file = new File("Sample.txt");
        progressBarView = new ProgressBarView(progressBar);
        progressLabelView = new ProgressLabelView(progressLabel);
        progressCounter = new ProgressCounter(file.length());
        textAsWordOccurrences = new TextAsWordOccurrences();
        textAsWordOccurrencesSlow = new TextAsWordOccurrencesSlow(300, progressCounter);
        textAsLetterOccurrences = new TextAsLetterOccurrences(progressCounter);
        progressCounter.attachObserver(progressBarView);
        progressCounter.attachObserver(progressLabelView);
    }

}
