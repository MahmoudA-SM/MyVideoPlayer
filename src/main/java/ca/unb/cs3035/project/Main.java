package ca.unb.cs3035.project;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    public static final HistoryBoxModel hbm = new HistoryBoxModel();

    public static final HistoryBoxController hbc = new HistoryBoxController();
    public static final HistoryBoxView hbv = new HistoryBoxView();

    public static MediaPlayerModel mpm = new MediaPlayerModel();
    public static MediaPlayerView mpv = new MediaPlayerView(mpm);

    public static final ProgramController pc = new ProgramController(mpm, mpv);

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MediaPlayer.fxml")));

        pc.setBorderPane(root);

        StackPane spalshScreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SplashScreen.fxml")));
        spalshScreen.setPrefHeight(400);
        spalshScreen.setPrefWidth(400);
        Scene scene = new Scene(spalshScreen);

        StackPane aboutS = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("About.fxml")));
        aboutS.setPrefWidth(400);
        aboutS.setPrefHeight(400);

        StackPane helpS = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Help.fxml")));

        MenuBar menuBar = (MenuBar) root.getTop();
        Menu help = menuBar.getMenus().get(1);
        MenuItem helpMI = help.getItems().get(0);
        MenuItem about = help.getItems().get(1);

        helpMI.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setHeight(helpS.getPrefHeight()+100);
                stage.setWidth(helpS.getPrefWidth()+100);
                scene.setRoot(helpS);
                stage.setScene(scene);
            }
        });

        helpS.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setWidth(root.getPrefWidth()+260);
                stage.setHeight(root.getPrefHeight()+100);
                scene.setRoot(root);
                stage.setScene(scene);
            }
        });

        about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setHeight(aboutS.getPrefHeight());
                stage.setWidth(aboutS.getPrefWidth());
                scene.setRoot(aboutS);
                stage.setScene(scene);

            }
        });
        aboutS.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                stage.setWidth(root.getPrefWidth()+260);
                stage.setHeight(root.getPrefHeight()+100);
                scene.setRoot(root);
                stage.setScene(scene);
            }
        });

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2.5), spalshScreen);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), spalshScreen);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);

        fadeIn.play();
        stage.setScene(scene);
        pc.setPane((StackPane) root.getChildren().get(3));


        pc.gethBox().prefWidthProperty().bind(stage.widthProperty());
        pc.gethBox().prefWidthProperty().bind(stage.heightProperty());
        fadeIn.setOnFinished(event -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            fadeOut.play();
        });

        fadeOut.setOnFinished(event -> {
            stage.setWidth(root.getPrefWidth()+260);
            stage.setHeight(root.getPrefHeight()+100);
            scene.setRoot(root);
//            stage.setScene(new Scene(root, 600, 500));
            stage.setScene(scene);
        });



        stage.setTitle("MP Media Player");

        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount() == 2){
                    stage.setFullScreen(true);
                }
            }
        });
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}