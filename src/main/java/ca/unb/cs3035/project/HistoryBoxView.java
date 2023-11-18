package ca.unb.cs3035.project;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class HistoryBoxView extends Pane {


    public void drawHistroyBox(VBox vBox, Group historyBox){
        vBox.getChildren().add(historyBox);
    }

    public void deleteHistoryBox(String text, VBox vBox) {
        for (int i = 1; i < vBox.getChildren().size(); i++) {
            Group group = (Group) vBox.getChildren().get(i);
            Label name = (Label) group.getChildren().get(1);
            if (name.getText() == text){
                vBox.getChildren().remove(i);
            }
        }
    }
}
