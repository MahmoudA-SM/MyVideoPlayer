package ca.unb.cs3035.project;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class MediaPlayerView extends Pane {

    private MediaPlayerModel mpm;

    public MediaPlayerView(MediaPlayerModel mpm) {
        this.mpm = mpm;

    }

    @Override
    public void layoutChildren(){
//        Label time = Main.mpc.getTimeElapsed();
//        drawLabel(time);
    }

    private void drawLabel(Label time){
//        time.setText(Double.toString(Main.mpc.getMediaPlayer().getCurrentTime().toMinutes()));
    }
}
