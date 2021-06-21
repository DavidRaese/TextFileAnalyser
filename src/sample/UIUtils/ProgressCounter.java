package sample.UIUtils;

import sample.UIUtils.ProgressObserver;

import java.util.ArrayList;

public class ProgressCounter {
    double relativeProgress;
    double totalProgress; // exp. total size of file
    ArrayList<ProgressObserver> progressObservers = new ArrayList<>();

    public ProgressCounter(double totalProgress) {
        this.relativeProgress = 0.00;
        this.totalProgress = totalProgress;
    }


    public Double getProgress() {
        return relativeProgress;
    }

    public void setProgress(double progress) {
        this.relativeProgress = progress;
        informObservers();
    }

    public void setRelativeProgressViaCalculation(double d) {
        setProgress(d / totalProgress);
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
