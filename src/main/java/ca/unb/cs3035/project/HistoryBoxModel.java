package ca.unb.cs3035.project;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class HistoryBoxModel {
    private SimpleListProperty simpleListPropertyHB;
    private ArrayList<String> namesList;

    Label HBname;
    Label HBtime;

    public HistoryBoxModel() {
        ArrayList<Group> historyBoxArrayList = new ArrayList<Group>();
        ObservableList<Group> historyBoxObservableList = (ObservableList<Group>) FXCollections.observableArrayList(historyBoxArrayList);
        simpleListPropertyHB = new SimpleListProperty<Group>(historyBoxObservableList);
        namesList = new ArrayList<String>();
    }
    public void addHistoryBox(Group historyBox, VBox vBox, Label name){
        simpleListPropertyHB.add(historyBox);
        namesList.add(name.getText());
        HBname = (Label) historyBox.getChildren().get(1);
//        HBtime = (Label) historyBox.getChildren().get(2);
        HBname.setText(name.getText());
        Main.hbv.drawHistroyBox(vBox, historyBox);
    }
    public SimpleListProperty<Group> simpleListPropertyHB(){
        return simpleListPropertyHB;
    }
    public void removeHistoryBox(String text, VBox vBox){
        for (int i = 0; i < namesList.size(); i++) {

            if(namesList.get(i)== text){
                namesList.remove(i);
                simpleListPropertyHB.remove(i);
            }


        }
        Main.hbv.deleteHistoryBox(text ,vBox);
    }


}
