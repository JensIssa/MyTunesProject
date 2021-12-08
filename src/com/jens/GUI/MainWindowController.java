package com.jens.GUI;

import com.jens.BE.Playlist;
import com.jens.BE.Song;
import com.jens.BLL.PlaylistManager;
import com.jens.BLL.SongManager;
import com.jens.BLL.util.MusicPlayer;
import com.jens.GUI.Model.PlaylistModel;
import com.jens.GUI.Model.SongModel;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import jdk.jfr.StackTrace;

import javax.swing.text.Element;
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
    public Image image;
    public ImageView songImage;

    public TableView playlistTable;
    public TableColumn playlistNameColumn;
    public TableColumn playlistSongsColumn;
    public TableColumn playlistTimeColumn;
    public TextField searchTextField;
    public ListView songsInPlaylistListView;
    public Label labelIsPlaying;

    private double volume = 0;

    private SongModel songModel = new SongModel();
    private PlaylistModel playlistModel = new PlaylistModel();
    private MusicPlayer musicPlayer;
    private boolean isPlaying = false;
    private boolean isDone = true;
    private Object currentSong = null;
    private Button upButton;
    private Button downButton;


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
            songTable.setItems(songModel.listToObservablelist());
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
        searchTextField.textProperty().addListener((observable, oldValue, newValue)->{
            try
            {
                songModel.searchSongs(newValue);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public void setSongImage(){
        try
        {
            File songimage = new File(songModel.songImageUpdate((Song) songTable.getSelectionModel().getSelectedItem()));
            image = new Image(songimage.toURI().toString());
            songImage = new ImageView();
            songImage.setImage(image);
        } catch (Exception e){
            e.printStackTrace();
        }
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
        songTable.getItems().remove(playlistTable.getSelectionModel().getSelectedItem());
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
        Playlist playlist = (Playlist) playlistTable.getSelectionModel().getSelectedItem();
        Song song = (Song) songTable.getSelectionModel().getSelectedItem();
        playlistModel.addSongToPlaylist(playlist.getId(), song.getId());
    }

    public void moveSongDown(ActionEvent actionEvent) {
    }

    public void moveSongUp(ActionEvent actionEvent) {
    }
    
    public void deletePlaylistSong(ActionEvent actionEvent) {
        playlistModel.removeSong((Song)songsInPlaylistListView.getSelectionModel().getSelectedItem());
        songsInPlaylistListView.getItems().remove(songsInPlaylistListView.getSelectionModel().getSelectedItem());
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
        try{
            if (!isPlaying && isDone || !(currentSong == songTable.getSelectionModel().getSelectedItem())){
                if (musicPlayer != null){
                    musicPlayer.mediaPlayer.dispose();
                }
                musicPlayer = new MusicPlayer((Song) songTable.getSelectionModel().getSelectedItem());
                currentSong = songTable.getSelectionModel().getSelectedItem();
                musicPlayer.mediaPlayer.setOnPlaying(this::playMedia);
                musicPlayer.mediaPlayer.setOnEndOfMedia(this::endOfMedia);
                musicPlayer.playSong();
                String actualSong = (((Song) songTable.getSelectionModel().getSelectedItem()).getTitle());
                labelIsPlaying.setText("(" + actualSong + ")" + " Is Playing");
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
        } catch (Exception e){
            e.printStackTrace();
            isPlaying = false;
            isDone = true;
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

    private void moveSongUp(){

    }

    public void lookAtPlaylist(MouseEvent mouseEvent) {
        Playlist playlist = (Playlist) playlistTable.getSelectionModel().getSelectedItem();
        try {
            ObservableList<Song> observableList = playlistModel.playlistSongsToObservablelist(playlist.getId());
            songsInPlaylistListView.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
