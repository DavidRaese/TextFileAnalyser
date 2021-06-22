package sample.ThreadUtils;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.UIUtils.ProgressCounter;
import sample.fileUtils.FileUtil;
import sample.fileUtils.HashMapStore;
import sample.fileUtils.Result;

import java.util.ArrayList;
import java.util.Set;

public class WaitingForAnalyzingToFinishRunner implements Runnable {
    AnalyzeRunner analyzeRunner;
    ProgressCounter progressCounter;
    ProgressBar progressBar;
    HashMapStore hashMapStore;
    TableView table;
    Thread analyzerThread;

    public WaitingForAnalyzingToFinishRunner(AnalyzeRunner analyzeRunner, ProgressCounter progressCounter, ProgressBar progressBar) {
        this.analyzeRunner = analyzeRunner;
        this.progressCounter = progressCounter;
        this.progressBar = progressBar;
    }

    public WaitingForAnalyzingToFinishRunner(AnalyzeRunner analyzeRunner, ProgressCounter progressCounter, ProgressBar progressBar, HashMapStore hashMapStore, TableView table) {
        this.analyzeRunner = analyzeRunner;
        this.progressCounter = progressCounter;
        this.progressBar = progressBar;
        this.hashMapStore = hashMapStore;
        this.table = table;
    }

    @Override
    public void run() {
        if(threadAlreadyExists("WaitingForResultsThread")) return;

        analyzerThread = new Thread(analyzeRunner);
        Thread.currentThread().setName("WaitingForResultsThread");
        analyzerThread.start();
        try {
            analyzerThread.join();
        } catch (Exception err) {
            System.out.println(err);
        }

        if(!analysingWasStopped()) {
            progressCounter.setRelativeProgress(1);
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                displayResultsToTableView(hashMapStore);
            }
        });
    }

    public void stopSubThread() {
        analyzerThread.interrupt();
    }

    public Thread getSubThread() {
        return analyzerThread;
    }

    private void displayResultsToTableView(HashMapStore hashMapStore) {
        ArrayList<Result> results = hashMapStore.getMapEntriesAsListOfResults();
        ObservableList<Result> resultsObservable = FXCollections.observableArrayList(results);
        table.setItems(resultsObservable);
        TableColumn occurrencesColumn = (TableColumn) table.getColumns().get(1); // Index 1 stands for occurrences TableColumn
        occurrencesColumn.setSortType(TableColumn.SortType.DESCENDING);
        table.getSortOrder().add(occurrencesColumn);
    }

    private boolean threadAlreadyExists(String existingThreadName) {
        Set<Thread> threads = Thread.getAllStackTraces().keySet();

        for (Thread t : threads) {
            if(t.getName() == existingThreadName) return true;
        }

        return false;
    }

    private boolean analysingWasStopped() {
        return FileUtil.getStopRequested();
    }

}
