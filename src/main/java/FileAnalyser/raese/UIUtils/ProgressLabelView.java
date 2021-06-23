package FileAnalyser.raese.UIUtils;

import javafx.application.Platform;
import javafx.scene.control.Label;
import FileAnalyser.raese.UIUtils.ProgressObserver;

public class ProgressLabelView implements ProgressObserver {
    Label progressLabel;

    public ProgressLabelView(Label progressLabel) {
        this.progressLabel = progressLabel;
    }

    @Override
    public void update(double d) {
        String labelText = String.format("%,.1f%%", d * 100);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                progressLabel.setText(labelText);
            }
        });
    }
}
