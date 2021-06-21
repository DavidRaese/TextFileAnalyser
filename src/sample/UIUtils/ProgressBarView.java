package sample.UIUtils;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import sample.UIUtils.ProgressObserver;

public class ProgressBarView implements ProgressObserver {
    ProgressBar progressBar;

    public ProgressBarView(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public void update(double d) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(d);
            }
        });
    }
}
