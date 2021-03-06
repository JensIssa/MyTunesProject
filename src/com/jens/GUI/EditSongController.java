package com.jens.GUI;

import com.jens.BE.Song;
import com.jens.GUI.Model.SongModel;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
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

public class EditSongController implements Initializable {

    @FXML
    private TextField songTitle;
    @FXML
    private TextField songArtist;
    @FXML
    private TextField songLength;
    @FXML
    private ComboBox categoryChoice;
    @FXML
    private TextField filePath;
    @FXML
    private TextField filePathImage;
    @FXML
    private TextField updateIdTxt;

    private MediaPlayer mediaPlayer;
    private String genres[];
    MainWindowController mainWindowController;
    SongModel songModel;

    /**
     * Initializes the combobox
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genres = new String[]{"Pop", "Hip-Hop", "Electric", "Rock", "R&B", "Latin", "K-Pop", "Country", "Classical", "Metal"};

        for (int i = 0; i < genres.length; i++){
            categoryChoice.getItems().add(genres[i]);
        }
    }

    /**
     * Instansiates the mainWindowController and songModel
     * @throws IOException
     */
    public EditSongController() throws IOException {
        mainWindowController = new MainWindowController();
        songModel = new SongModel();
    }

    /**
     * Exits the EditSongView
     */
    public void cancelNewEdit(ActionEvent actionEvent) throws SQLException, IOException {
        mainWindowController.refreshSongList();

        FXMLLoader parent = new FXMLLoader(getClass().getResource("View/MainWindow.fxml"));
        Scene mainWindowScene = null;
        try{
            mainWindowScene = new Scene(parent.load());

        }catch (IOException exception){
            exception.printStackTrace();
        }
        Stage editSongStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        editSongStage.setScene(mainWindowScene);
    }

    /**
     * Updates the selected songs parameters
     * @param actionEvent
     * @throws SQLException
     * @throws IOException
     */
    public void editSong(ActionEvent actionEvent) throws SQLException {
        String updateTitle = songTitle.getText();
        String updateArtist = songArtist.getText();
        String updateCategory = categoryChoice.getSelectionModel().getSelectedItem().toString();
        String updateUrl = filePath.getText();
        String updateUrlImg = filePathImage.getText();
        int updateLength = Integer.parseInt(songLength.getText());
        int updateId = Integer.parseInt(updateIdTxt.getText());
        Song updatedSong = new Song(updateId, updateTitle, updateArtist, updateLength, updateCategory, updateUrl,updateUrlImg);
        songModel.updateSong(updatedSong);

        FXMLLoader parent = new FXMLLoader(getClass().getResource("View/MainWindow.fxml"));
        Scene mainWindowScene = null;
        try{
            mainWindowScene = new Scene(parent.load());

        }catch (IOException exception){
            exception.printStackTrace();
        }
        Stage editSongStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        editSongStage.setScene(mainWindowScene);
    }

    /**
     * Sets the current parameters to the textfields
     * @param song The selected song
     */
    public void setSong(Song song){
        songTitle.setText(song.getTitle());
        songArtist.setText(song.getArtistName());
        filePath.setText(song.getUrl());
        filePathImage.setText(song.getUrlImg());
        songLength.setText(Integer.toString(song.getSongLength()));
        updateIdTxt.setText(Integer.toString(song.getId()));
    }

    /**
     * Opens a file explorer to choose a song (*.mp3, *.wav)
     */
    public void chooseSongFile() {
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

    /**
     * Fetches the length of a song in minutes and seconds
     */
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

    /**
     * Opens a file explorer to choose an image (*.png, *.jpg, *.jpeg)
     */
    public void chooseImageFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        fileChooser.setInitialDirectory(new File("image/" ));
        File file = fileChooser.showOpenDialog(null);

        if (file != null){
            filePathImage.setText("image\\" + file.getName());

        }
    }
}
