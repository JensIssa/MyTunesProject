package com.jens.GUI;

import com.jens.GUI.Model.SongModel;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddSongController implements Initializable{

    public TextField songTitle;
    public TextField songArtist;
    public TextField songLength;
    public ComboBox categoryChoice;
    public TextField filePath;
    public TextField filePathImage;
    public Button cancel;
    private MediaPlayer mediaPlayer;
    private String genres[];
    MainWindowController mainWindowController;

    private SongModel songModel = new SongModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genres = new String[]{"Pop", "Hip-Hop", "Electric", "Rock", "R&B", "Latin", "K-Pop", "Country", "Classical", "Metal"};

        for (int i = 0; i < genres.length; i++){
            categoryChoice.getItems().add(genres[i]);
        }
    }


    public AddSongController() throws IOException {
        mainWindowController = new MainWindowController();
    }

    public void cancelNewEdit(ActionEvent actionEvent) throws SQLException, IOException {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void createSong(ActionEvent actionEvent) throws SQLException, IOException {
        songModel.createSong(songTitle.getText(), songArtist.getText(), Integer.parseInt(songLength.getText()), categoryChoice.getSelectionModel().getSelectedItem().toString(), filePath.getText(), filePathImage.getText());
        mainWindowController.refreshSongList();
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void chooseImageFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        fileChooser.setInitialDirectory(new File("image/" ));
        File file = fileChooser.showOpenDialog(null);

        if (file != null){
            filePathImage.setText("image\\" + file.getName());

        }
    }

    public void chooseSongFile(ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Music Files", "*.mp3", "*.wav"));
        fileChooser.setInitialDirectory(new File("Music/" ));
        File file = fileChooser.showOpenDialog(null);


        if (file != null){
            filePath.setText("Music\\" + file.getName());

            Media hit = new Media(new File(file.getAbsolutePath()).toURI().toString());
            hit.getMetadata().addListener((MapChangeListener.Change<? extends String, ? extends Object> c) -> {
                if (c.wasAdded()) {
                    if ("artist".equals(c.getKey())) {
                        String artist = c.getValueAdded().toString();
                        songArtist.setText(artist);
                    }
                    else if ("title".equals(c.getKey())) {
                        String title = c.getValueAdded().toString();
                        songTitle.setText(title);
                    }
                }
            });

            mediaPlayer = new MediaPlayer(hit);

            getSongTime();
        }
    }

    private void getSongTime() {
        mediaPlayer.setOnReady(() -> {
            String averageSeconds = String.format("%1.0f", mediaPlayer.getMedia().getDuration().toSeconds());
            int minutes = Integer.parseInt(averageSeconds) / 60;
            int seconds = Integer.parseInt(averageSeconds) % 60;
            if (10 > seconds) {
                songLength.setText(minutes + "0" + seconds);
            } else {
                songLength.setText(minutes + "" + seconds);
            }
        });
    }
}
