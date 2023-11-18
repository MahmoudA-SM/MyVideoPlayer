package ca.unb.cs3035.project;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HistoryBoxController {

    @FXML
    private Button deleteButton;
    @FXML
    private Label name;

    public HistoryBoxController(){

    }

    public void addHistroyBox(Group historyBox, Label name, VBox vBox) throws IOException {
        Main.hbm.addHistoryBox(historyBox, vBox, name);
        this.name=name;
    }
    public void deleteHistoryBox(VBox vBox, Label name){
        Main.hbm.removeHistoryBox(name.getText(), vBox);
    }

    public Button getDelete(){
        return deleteButton;
    }

}
