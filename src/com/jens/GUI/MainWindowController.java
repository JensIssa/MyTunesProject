package com.jens.GUI;

import com.jens.BE.Playlist;
import com.jens.BE.Song;
import com.jens.BLL.PlaylistManager;
import com.jens.BLL.SongManager;
import com.jens.BLL.util.MusicPlayer;
import com.jens.BLL.util.SongSearcher;
import com.jens.GUI.Model.PlaylistModel;
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

    public TableView playlistTable;
    public TableColumn playlistNameColumn;
    public TableColumn playlistSongsColumn;
    public TableColumn playlistTimeColumn;

    private double volume = 0;

    private SongModel songModel = new SongModel();
    private PlaylistModel playlistModel = new PlaylistModel();
    private MusicPlayer musicPlayer;
    private boolean isPlaying = false;
    private boolean isDone = true;
    private Object currentsong = null;

    public MainWindowController() throws IOException {

        songTitleColumn = new TableColumn<SongManager, String>();
        songArtistColumn = new TableColumn<SongManager, String>();
        songCategoryColumn = new TableColumn<SongManager, String>();
        songTimeColumn = new TableColumn<SongManager, Integer>();

        volumeSlider = new Slider();

        playlistNameColumn = new TableColumn<PlaylistManager, String>();
        playlistSongsColumn = new TableColumn<PlaylistManager, Integer>();
        playlistTimeColumn = new TableColumn<PlaylistManager, Integer>();
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

        playlistNameColumn.setCellValueFactory(new PropertyValueFactory<Playlist, String>("PlaylistName"));
        playlistSongsColumn.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("TotalSongs"));
        playlistTimeColumn.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("TotalTime"));
        try {
            ObservableList<Playlist> observableList = playlistModel.listToObservablelist();
            playlistTable.setItems(observableList);
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
        songModel.deleteSong((Song)songTable.getSelectionModel().getSelectedItem());
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

    public void deletePlaylist(ActionEvent actionEvent) throws SQLException, IOException {
        playlistModel.deletePlaylist((Playlist) playlistTable.getSelectionModel().getSelectedItem());
        playlistTable.getItems().remove(playlistTable.getSelectionModel().getSelectedItem());
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

    private void playMedia(){
        isDone = false;
    }
    private void  endOfMedia(){
        isPlaying = false;
        isDone = true;
    }
    private boolean sameSong(){
        if(musicPlayer.mediaPlayer.getMedia() != null) {
            return musicPlayer.mediaPlayer.getMedia() == songTable.getSelectionModel().getSelectedItem();
        }
        return true;
    }

    public void playSong(){
        if (!isPlaying && isDone || !(currentsong == songTable.getSelectionModel().getSelectedItem())){
            musicPlayer = new MusicPlayer((Song) songTable.getSelectionModel().getSelectedItem());
            currentsong = songTable.getSelectionModel().getSelectedItem();
            musicPlayer.mediaPlayer.stop();
            musicPlayer.mediaPlayer.setOnPlaying(this::playMedia);
            musicPlayer.mediaPlayer.setOnEndOfMedia(this::endOfMedia);
            musicPlayer.playSong();
            isPlaying = true;
            System.out.println("Work playing");
        } else if (isPlaying){
            pauseSong();
            System.out.println("Paused");
        } else {
            musicPlayer.playSong();
            isPlaying = true;
            System.out.println("Should play again");
        }
    }

    public void pauseSong(){
        musicPlayer.pauseSong();
        isPlaying = false;
    }

    public void nextSong(ActionEvent actionEvent) {
    }

    public void previousSong(ActionEvent actionEvent) {
    }
}
