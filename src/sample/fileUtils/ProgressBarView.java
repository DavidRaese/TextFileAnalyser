package sample.fileUtils;

import javafx.scene.control.ProgressBar;

public class ProgressBarView implements ProgressObserver {
    ProgressBar progressBar;
    double totalProgress; // exp. total size of file
    double relativeProgress;

    public ProgressBarView(ProgressBar progressBar, double absolutProgress) {
        this.progressBar = progressBar;
        this.totalProgress = absolutProgress;
    }

    @Override
    public void update(Double d) {
        setRelativeProgress(d);
        progressBar.setProgress(relativeProgress);
    }

    private void setRelativeProgress(Double d) {
        relativeProgress = d / totalProgress;
    }

    private double getRelativeProgress() {
        return relativeProgress;
    }
}
