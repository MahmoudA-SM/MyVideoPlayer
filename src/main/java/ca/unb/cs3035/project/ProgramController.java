package ca.unb.cs3035.project;

import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgramController {
    private MediaPlayerModel mpm = new MediaPlayerModel();
    private MediaPlayerView mpv = new MediaPlayerView(mpm);

    private BorderPane borderPane;
    private StackPane pane;

    private VBox vBox;
    private HBox hBox;

    public ProgramController(MediaPlayerModel mpm, MediaPlayerView mpv){
        this.mpm = mpm;
        this.mpv = mpv;

    }


    public void setBorderPane(BorderPane borderPane){
        this.borderPane = borderPane;
        System.out.println(borderPane.getChildren());
        vBox = (VBox) borderPane.getChildren().get(1);
        hBox = (HBox) vBox.getChildren().get(1);
    }

    public VBox getvBox() {
        return vBox;
    }

    public HBox gethBox() {
        return hBox;
    }

    public void setPane(StackPane pane) {
        this.pane = pane;
    }

//    public void openFile() {
//        FileChooser fileChooser = new FileChooser();
//        File file = fileChooser.showOpenDialog(null);
//        String path = file.toURI().toString();
//
//        if (path!= null){
//            Media media = new Media(path);
//            MediaPlayer mediaPlayer = new MediaPlayer(media);
//
//        }
//
//        if(path != null){
//            Media media = new Media(path);
//            MediaPlayer mediaPlayer = new MediaPlayer(media);
//            MediaView mediaView = (MediaView) pane.getChildren().get(0);
//            mediaView.setMediaPlayer(mediaPlayer);
//            mediaView.setFitWidth(pane.getWidth()*0.8);
//            mediaView.setFitHeight(pane.getHeight()*0.8);
//
//            pane.prefWidthProperty().bind((Bindings.selectDouble(pane.parentProperty())));
//            pane.prefHeightProperty().bind(Bindings.selectDouble(pane.parentProperty()));
//
//
//
//        }
//    }
}
