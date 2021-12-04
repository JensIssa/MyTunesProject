package com.jens.GUI;

import com.jens.BE.Song;
import com.jens.BLL.SongManager;
import com.jens.BLL.util.MusicPlayer;
import com.jens.GUI.Model.SongModel;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {

    public TableColumn songTitleColumn;
    public TableColumn songArtistColumn;
    public TableColumn songCategoryColumn;
    public TableColumn songTimeColumn;
    public TableView songTable;
    public Slider volumeSlider;

    private double volume = 0;

    private SongModel songModel = new SongModel();
    private MusicPlayer musicPlayer;

    public MainWindowController() throws IOException {

        songTitleColumn = new TableColumn<SongManager, String>();
        songArtistColumn = new TableColumn<SongManager, String>();
        songCategoryColumn = new TableColumn<SongManager, String>();
        songTimeColumn = new TableColumn<SongManager, Float>();
        volumeSlider = new Slider();
        songTimeColumn = new TableColumn<SongManager, Integer>();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        songTitleColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("Title"));
        songArtistColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("ArtistName"));
        songCategoryColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("Category"));
        songTimeColumn.setCellValueFactory(new PropertyValueFactory<Song, Integer>("SongLength"));
        try {
            ObservableList<Song> observableList = songModel.listToObservablelist();
            songTable.setItems(observableList);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        volumeSlider.valueProperty().addListener(new InvalidationListener()
        {
            @Override public void invalidated(Observable observable)
            {
                musicPlayer.mediaPlayer.setVolume(volume);
            }
        });

    }

    public void newSong(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("View/AddEditSong.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add/Edit Song");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void editSong(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("View/AddEditSong.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add/Edit Song");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void deleteSong(ActionEvent actionEvent) {

    }



    public void newPlaylist(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("View/AddEditPlaylist.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add/Edit Playlist");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void editPlaylist(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("View/AddEditPlaylist.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add/Edit Playlist");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void deletePlaylist(ActionEvent actionEvent) {
    }

    public void addSongToPlaylist(ActionEvent actionEvent) {
    }

    public void moveSongDown(ActionEvent actionEvent) {
    }

    public void moveSongUp(ActionEvent actionEvent) {
    }
    
    public void deletePlaylistSong(ActionEvent actionEvent) {
    }
    
    public void adjustVolume(){
        volume = volumeSlider.getValue() / 100;
        System.out.println(volume);
    }

    public void playSong(){
        musicPlayer = new MusicPlayer((Song) songTable.getSelectionModel().getSelectedItem());
        musicPlayer.playSong();
    }

    public void nextSong(ActionEvent actionEvent) {
    }

    public void previousSong(ActionEvent actionEvent) {
    }
}
