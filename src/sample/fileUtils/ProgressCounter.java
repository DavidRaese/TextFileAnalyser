package sample.fileUtils;

import java.util.ArrayList;

public class ProgressCounter {
    Double progress;
    ArrayList<ProgressObserver> progressObservers = new ArrayList<>();

    public ProgressCounter() {
        this.progress = 0.00;
    }

    public ProgressCounter(Double progress) {
        this.progress = progress;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
        informObservers();
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
