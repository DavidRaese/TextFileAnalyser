package FileAnalyser.raese.UIUtils;

import java.util.ArrayList;

public class ProgressCounter {
    double totalAnalyzedSize;
    double totalSize; // exp. total size of file
    double relativeProgress;
    ArrayList<ProgressObserver> progressObservers = new ArrayList<>();

    public ProgressCounter() {
        this.totalAnalyzedSize = 0;
        this.relativeProgress = 0;
    }

    public ProgressCounter(double totalSize) {
        this.totalAnalyzedSize = 0;
        this.totalSize = totalSize;
        this.relativeProgress = 0;
    }


    public Double getProgress() {
        return relativeProgress;
    }

    public void setRelativeProgress(double progress) {
        this.relativeProgress = progress;
        informObservers();
    }

    public void setTotalAnalyzedSize(double analyzedSize) {
        totalAnalyzedSize = analyzedSize;
        setRelativeProgressViaCalculation();
    }

    public void addToTotalAnalyzedSize(double analyzedSize) {
        totalAnalyzedSize += analyzedSize;
        setRelativeProgressViaCalculation();
    }

    public void setTotalSize(double totalSize) {
        this.totalSize = totalSize;
    }

    public void setRelativeProgressViaCalculation() {
        setRelativeProgress(totalAnalyzedSize / totalSize);
    }

    public void attachObserver(ProgressObserver progressObserver) {
        progressObservers.add(progressObserver);
    }

    public void detachObserver(ProgressObserver progressObserver) {
        progressObservers.remove(progressObserver);
    }

    public void informObservers() {
        for(ProgressObserver progressObserver : progressObservers) {
            progressObserver.update(getProgress());
        }
    }
}
