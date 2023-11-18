package ca.unb.cs3035.project;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;


import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;


public class MediaPlayerController implements Initializable {

    @FXML
    public MenuItem openFile;
    private String path;
    private MediaPlayer mediaPlayer;

    @FXML
    private Label timeElapsed;

    @FXML
    private MediaView mediaView;

    @FXML
    private Button PlayPauseButton;
    @FXML
    private Slider volumeSlider;

    @FXML
    private Slider progressBar;


    @FXML
    private BorderPane borderPane;

    @FXML
    private StackPane pane;

    @FXML
    private VBox historyVBox;

    private Duration duration = new Duration(0);

    @FXML
    private VBox bottomVBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //creating bindings

        DoubleProperty stackWidthProp = pane.prefWidthProperty();
        DoubleProperty stackHeightProp = pane.prefHeightProperty();
        stackWidthProp.bind(Bindings.selectDouble(pane.parentProperty(), "width"));
        stackHeightProp.bind(Bindings.selectDouble(pane.parentProperty(), "height"));


        borderPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.L)
                    mediaPlayer.seek(mediaPlayer.getCurrentTime().add(javafx.util.Duration.seconds(5)));
                else if (keyEvent.getCode() == KeyCode.J)
                    mediaPlayer.seek(mediaPlayer.getCurrentTime().subtract(javafx.util.Duration.seconds(5)));
                if (keyEvent.getCode() == KeyCode.EQUALS) {
                    mediaPlayer.setVolume(volumeSlider.getValue() + (5));
                    volumeSlider.setValue(volumeSlider.getValue() + (5));
                } else if (keyEvent.getCode() == KeyCode.MINUS) {

                    mediaPlayer.setVolume(volumeSlider.getValue() - (5));
                    volumeSlider.setValue(volumeSlider.getValue() - (5));
                }
                if (keyEvent.getCode() == KeyCode.K) {
                    playPauseMedia();
                }

                if (keyEvent.getCode() == KeyCode.S) {
                    mediaPlayer.stop();
                }
            }
        });
    }

    @FXML
    private void OpenFileMethod(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        path = file.toURI().toString();

        Media media = new Media(path);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        mediaView.setFitWidth(pane.getWidth() * 0.8);
        mediaView.setFitHeight(pane.getHeight() * 0.8);


        DoubleProperty stackWidthProp = pane.prefWidthProperty();
        DoubleProperty stackHeightProp = pane.prefHeightProperty();
        stackWidthProp.bind(Bindings.selectDouble(pane.parentProperty(), "width"));
        stackHeightProp.bind(Bindings.selectDouble(pane.parentProperty(), "height"));


        volumeSlider.setValue(mediaPlayer.getVolume() * 100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volumeSlider.getValue() / 100);
            }
        });
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<javafx.util.Duration>() {
                                                          @Override
                                                          public void changed(ObservableValue<? extends javafx.util.Duration> observable, javafx.util.Duration oldValue, javafx.util.Duration newValue) {
                                                              progressBar.setValue(newValue.toSeconds());
                                                          }
                                                      }
        );


        progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(javafx.util.Duration.seconds(progressBar.getValue()));
            }
        });

        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateTime();
            }
        });


        progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(javafx.util.Duration.seconds(progressBar.getValue()));
            }
        });

        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                javafx.util.Duration total = media.getDuration();
                progressBar.setMax(total.toSeconds());
                duration = mediaPlayer.getMedia().getDuration();
                updateTime();
            }
        });

        mediaPlayer.play();
        Group toAdd = new Group();
        Label name = new Label();
        if (file != null) {
            name = new Label(file.getName().toString());
            toAdd = (Group) FXMLLoader.load(getClass().getResource("HistoryBox.fxml"));
            Main.hbc.addHistroyBox(toAdd, name, historyVBox);
        }
        Label finalName = name;
        toAdd.getChildren().get(2).setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Main.hbc.deleteHistoryBox(historyVBox, finalName);
            }
        });
    }

    public void updateTime() {
        if (timeElapsed != null && progressBar != null && volumeSlider != null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Duration currentTime = mediaPlayer.getCurrentTime();
                    timeElapsed.setText(formatTime(currentTime, duration));
                    progressBar.setDisable(duration.isUnknown());
                    if (!!progressBar.isDisabled()
                            && duration.greaterThan(Duration.ZERO)
                            && !progressBar.isValueChanging()) {
                        progressBar.setValue(currentTime.divide(duration).toMillis() * 100.0);
                    }
                }
            });
        }
    }


    public void stopVideo(ActionEvent event) {
        mediaPlayer.stop();
    }

    public void playVideo(ActionEvent event) {
        playPauseMedia();
    }

    public void skip5(ActionEvent event) {
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(javafx.util.Duration.seconds(5)));

    }

    public void furtherSpeedUpVideo(ActionEvent event) {
        mediaPlayer.setRate(2);
    }

    public void back5(ActionEvent event) {
        mediaPlayer.seek(mediaPlayer.getCurrentTime().add(javafx.util.Duration.seconds(-5)));
    }

    public void furtherSlowDownVideo(ActionEvent event) {
        mediaPlayer.setRate(0.5);

    }

    public void playPauseMedia() {
        MediaPlayer.Status status = mediaPlayer.getStatus();
        if (status == MediaPlayer.Status.PLAYING) {
            mediaPlayer.pause();
            PlayPauseButton.setText("||");

        } else {
            mediaPlayer.play();
            mediaPlayer.setRate(1);
            PlayPauseButton.setText(">");
        }
    }

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

        if (duration.compareTo(Duration.ZERO) > 0) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 -
                    durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);

            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds, durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes,
                        elapsedSeconds);
            }
        }
    }

    public VBox getBottomVBox() {
        return bottomVBox;
    }

    public void exit() {
        Platform.exit();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public Label getTimeElapsed() {
        return timeElapsed;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }
}