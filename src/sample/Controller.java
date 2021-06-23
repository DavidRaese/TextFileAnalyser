package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import sample.ThreadUtils.AnalyzeRunner;
import sample.ThreadUtils.WaitingForAnalyzingToFinishRunner;
import sample.UIUtils.ProgressBarView;
import sample.UIUtils.ProgressCounter;
import sample.UIUtils.ProgressLabelView;
import sample.fileUtils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label pathLabel;
    @FXML
    private TextField filePathTextField;
    @FXML
    private ChoiceBox<String> choiceBox;
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

    public void handleDragOver(DragEvent event) {
        if(!filePathTextField.getStyleClass().contains("onDrag")) {
            filePathTextField.getStyleClass().add("onDrag");
        }

        if(event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    public void handleDrop(DragEvent event) {
        List<File> files = event.getDragboard().getFiles();
        String filePathString = files.get(0).getAbsoluteFile().toString();
        filePathTextField.setText(filePathString);
        filePathTextField.getStyleClass().remove("onDrag");
    }

    public void handleDragFinished() {
        ObservableList styleClasses = filePathTextField.getStyleClass();
        System.out.println("DragEvent Finished");
        filePathTextField.getStyleClass().remove("onDrag");
    }


    public void analyseTextFileHandler() {
        String userFilePath;
        try{
            userFilePath = getUserFilePath();
        } catch (FileNotFoundException exception) {
            pathLabel.setText("Sorry file dosn't exist");
            pathLabel.getStyleClass().add("error");
            return;
        }

        resetToInitialState();

        String chosenStrategy = getChosenAnalyzingStrategy();

        switch (chosenStrategy) {
            case "Word occurrences":
                analyseTextFile(userFilePath, textAsWordOccurrences);
                displayResultsToTableView(textAsWordOccurrences);
                break;
            case "Letter occurrences":
                analyseTextFile(userFilePath, textAsLetterOccurrences);
                displayResultsToTableView(textAsLetterOccurrences);
                break;
            case "Word occurrences slow":
                analyseTextFile(userFilePath, textAsWordOccurrencesSlow);
                displayResultsToTableView(textAsWordOccurrencesSlow);
                break;
        }
    }

    public void stopAnalyzing() {
        FileUtil.stopParsing();
    }

    private void resetToInitialState() {
        progressCounter.setTotalAnalyzedSize(0);
        pathLabel.setText("Enter Path to file:");
        pathLabel.getStyleClass().remove("error");
    }

    private void analyseTextFile(String userFilePath, HashMapStore hashMapStore) {
        if(userFilePath.isBlank()) {
            pathLabel.setText("Please enter a Path");
            return;
        }

        AnalyzeRunner analyzeRunner = new AnalyzeRunner(userFilePath, hashMapStore);
        waitingForAnalyzingToFinishRunner = new WaitingForAnalyzingToFinishRunner(analyzeRunner, progressCounter, progressBar, hashMapStore, table);
        waitingForAnalyzingToFinishThread = new Thread(waitingForAnalyzingToFinishRunner);
        waitingForAnalyzingToFinishThread.start();

    }

    private void displayResultsToTableView(HashMapStore hashMapStore) {
        ArrayList<Result> results = hashMapStore.getMapEntriesAsListOfResults();
        ObservableList<Result> resultsObservable = FXCollections.observableArrayList(results);
        table.setItems(resultsObservable);
    }

    private String getChosenAnalyzingStrategy() {
        return choiceBox.getValue();
    }

    private String getUserFilePath() throws FileNotFoundException {
        String userFilePath = filePathTextField.getText();
        Path path = Paths.get(userFilePath);

        if(Files.exists(path) && Files.isRegularFile(path)) {
            return userFilePath;
        } else {
            throw new FileNotFoundException();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceBox.getItems().addAll("Word occurrences", "Letter occurrences", "Word occurrences slow");
        choiceBox.setValue("Word occurrences slow");

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        word.setCellValueFactory(new PropertyValueFactory<Result, String>("word"));
        occurrences.setCellValueFactory(new PropertyValueFactory<Result, Integer>("occurrences"));

        file = new File("Sample.txt");
        progressBarView = new ProgressBarView(progressBar);
        progressLabelView = new ProgressLabelView(progressLabel);
        progressCounter = new ProgressCounter(file.length());
        textAsWordOccurrences = new TextAsWordOccurrences(progressCounter);
        textAsWordOccurrencesSlow = new TextAsWordOccurrencesSlow(300, progressCounter);
        textAsLetterOccurrences = new TextAsLetterOccurrences(progressCounter);
        progressCounter.attachObserver(progressBarView);
        progressCounter.attachObserver(progressLabelView);
    }

}
