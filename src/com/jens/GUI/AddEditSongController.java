package com.jens.GUI;

import com.jens.BE.Song;
import com.jens.GUI.Model.SongModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class AddEditSongController {

    public TextField songTitle;
    public TextField songArtist;
    public TextField songLength;
    public ComboBox categoryChoice;
    public TextField filePath;
    public Button cancel;
    private MediaPlayer mediaPlayer;

    private SongModel songModel = new SongModel();

    public AddEditSongController() throws IOException {


    }



    public void CancelNew_Edit(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void createSong(ActionEvent actionEvent) throws SQLException {
        songModel.createSong(songTitle.getText(), songArtist.getText(), Integer.parseInt(songLength.getText()), categoryChoice.getSelectionModel().getSelectedItem().toString(), filePath.getText());

        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void chooseSongFile(ActionEvent actionEvent) throws UnsupportedAudioFileException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Music Files", "*.mp3", "*.wav"));
        File file = fileChooser.showOpenDialog(null);


        if (file != null){
            filePath.setText(file.getAbsolutePath());


            //mediaPlayer = new MediaPlayer(new Media(new File(file.getAbsolutePath()).toURI().toString()));
            Media hit = new Media(new File(file.getAbsolutePath()).toURI().toString());
            mediaPlayer = new MediaPlayer(hit);

            setMediaPlayerTime();
        }
    }

    private void setMediaPlayerTime() {
        mediaPlayer.setOnReady(() -> {
            String averageSeconds = String.format("%1.0f", mediaPlayer.getMedia().getDuration().toSeconds());
            int minutes = Integer.parseInt(averageSeconds) / 60;
            int seconds = Integer.parseInt(averageSeconds) % 60;
            if (10 > seconds) {
                songLength.setText(minutes + ":0" + seconds);
            } else {
                songLength.setText(minutes + ":" + seconds);
            }
        });
    }
}
