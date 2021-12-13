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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.File;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class MainWindowController implements Initializable {

    private ObservableList<Song> allSongs = FXCollections.observableArrayList();
    private ObservableList<Playlist> allPlaylist = FXCollections.observableArrayList();

    public TableColumn songTitleColumn;
    public TableColumn songArtistColumn;
    public TableColumn songCategoryColumn;
    public TableColumn songTimeColumn;
    public TableView<Song> songTable;

    public Slider volumeSlider;
    public ProgressBar songProgressBar;
    public Image image;
    public ImageView songImage;

    public TableView<Playlist> playlistTable;
    public TableColumn playlistNameColumn;
    public TableColumn playlistSongsColumn;
    public TableColumn playlistTimeColumn;
    public TextField searchTextField;
    public ListView<Song> songsInPlaylistListView;
    public Label labelIsPlaying;
    public Label labelArtist;

    private double volume = 0;

    private SongModel songModel = new SongModel();
    private PlaylistModel playlistModel = new PlaylistModel();
    private MusicPlayer musicPlayer;
    public boolean isPlaying = false;
    public boolean isDone = true;
    private Object currentSong = null;
    private Button upButton;
    private Button downButton;
    private Timer timer;
    private TimerTask timerTask;
    public SelectionModel selectionModel;


    public MainWindowController() throws IOException {

        songTitleColumn = new TableColumn<SongManager, String>();
        songArtistColumn = new TableColumn<SongManager, String>();
        songCategoryColumn = new TableColumn<SongManager, String>();
        songTimeColumn = new TableColumn<SongManager, Integer>();

        volumeSlider = new Slider();

        playlistNameColumn = new TableColumn<PlaylistManager, String>();
        playlistSongsColumn = new TableColumn<PlaylistManager, Integer>();
        playlistTimeColumn = new TableColumn<PlaylistManager, Integer>();
        songTable = new TableView();
        playlistTable = new TableView();

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
            File img = new File(songModel.songImageUpdate((Song) songTable.getSelectionModel().getSelectedItem()));
            InputStream isImage = (InputStream) new FileInputStream(img);
            System.out.println(isImage);
            songImage.setImage(new Image(isImage));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void newSong(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("View/AddSong.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add/Edit Song");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void editSong(ActionEvent actionEvent) throws IOException {

        Song selectedSong = (Song) songTable.getSelectionModel().getSelectedItem();
        FXMLLoader root = new FXMLLoader(getClass().getResource("View/EditSong.fxml"));
        Scene mainWindowScene = null;

        try{
            mainWindowScene = new Scene(root.load());
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }
        Stage editSongStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        editSongStage.setScene(mainWindowScene);
        EditSongController editSongController = root.getController();
        editSongController.setSong(selectedSong);
        editSongStage.show();


    }

    public void deleteSong(ActionEvent actionEvent) throws SQLException, IOException {
        songModel.deleteSong((Song)songTable.getSelectionModel().getSelectedItem());
        songTable.getItems().remove(songTable.getSelectionModel().getSelectedItem());
        refreshSongList();
    }

    public void newPlaylist(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("View/AddPlaylist.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add/Edit Playlist");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void editPlaylist(ActionEvent actionEvent) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("View/EditPlaylist.fxml"));
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
        Song song = (Song) songTable.getSelectionModel().getSelectedItem();
        Playlist playlist = (Playlist) playlistTable.getSelectionModel().getSelectedItem();
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
    
    public double adjustVolume(){
        return volume = volumeSlider.getValue() / 100;
    }

    public void refreshPlaylist() throws SQLException, IOException {
        playlistTable.getItems().clear();
        playlistTable.setItems(playlistModel.listToObservablelist());
        playlistTable.refresh();
    }

    private void endOfMedia(){
        musicPlayer.endOfMedia();
    }
    private void playMedia(){
        musicPlayer.playMedia();
    }

    public void playSong(){
        try{
            if (!isPlaying && isDone || !(currentSong == songTable.getSelectionModel().getSelectedItem())){
                if (musicPlayer != null){
                    musicPlayer.mediaPlayer.dispose();
                    cancelTimer();
                }
                musicPlayer.mediaPlayer.setOnPlaying(this::playMedia);
                musicPlayer.mediaPlayer.setOnEndOfMedia(this::endOfMedia);
                musicPlayer.playSong();
                musicPlayer.mediaPlayer.setVolume(adjustVolume());
                String actualSong = (((Song) songTable.getSelectionModel().getSelectedItem()).getTitle());
                String currentArtist = (((Song) songTable.getSelectionModel().getSelectedItem()).getArtistName());
                labelIsPlaying.setText("(" + actualSong + ")" + " Is Playing");
                labelArtist.setText(currentArtist);
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

    public void beginTimer(){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                double current = musicPlayer.mediaPlayer.getCurrentTime().toSeconds();
                double end = musicPlayer.mediaPlayer.getMedia().getDuration().toSeconds();
                songProgressBar.setProgress(current/end);

                if(current/end == 1){
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 100, 100);
    }

    public void cancelTimer(){
        timer.cancel();
    }

    public void nextSong(ActionEvent actionEvent) {
        songTable.getSelectionModel().selectNext();
        playSong();
    }

    public void previousSong(ActionEvent actionEvent) {
        songTable.getSelectionModel().selectAboveCell();
        playSong();
    }

    public void moveSongUp(){
        System.out.println(songsInPlaylistListView.getId());
    }

    public void moveSongDown(){

    }

    public void refreshSongList() throws IOException, SQLException {
        songTable.getItems().clear();
        songTable.setItems(songModel.listToObservablelist());
        songTable.refresh();

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

    private void error(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.YES);
        alert.showAndWait();
    }

    public void refreshAction(ActionEvent event) throws SQLException, IOException {
        refreshSongList();
    }
}
