package com.jens.GUI;

import com.jens.BE.Playlist;
import com.jens.BE.Song;
import com.jens.BLL.PlaylistManager;
import com.jens.BLL.SongManager;
import com.jens.BLL.util.MusicPlayer;
import com.jens.GUI.Model.MusicPlayerModel;
import com.jens.GUI.Model.PlaylistModel;
import com.jens.GUI.Model.SongModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;

import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


public class MainWindowController implements Initializable {

    @FXML
    private TableColumn songTitleColumn;
    @FXML
    private TableColumn songArtistColumn;
    @FXML
    private TableColumn songCategoryColumn;
    @FXML
    private TableColumn songTimeColumn;
    @FXML
    private TableView<Song> songTable;

    @FXML
    private ImageView playButtonImage;

    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar songProgressBar;
    @FXML
    private ImageView songImage;

    @FXML
    private TableView<Playlist> playlistTable;
    @FXML
    private TableColumn playlistNameColumn;
    @FXML
    private TableColumn playlistSongsColumn;
    @FXML
    private TableColumn playlistTimeColumn;
    @FXML
    private TextField searchTextField;
    @FXML
    private ListView<Song> songsInPlaylistListView;
    @FXML
    private Label labelIsPlaying;
    @FXML
    private Label labelArtist;

    private double volume = 0;
    private SongModel songModel = new SongModel();
    private MusicPlayerModel musicPlayerModel;
    private PlaylistModel playlistModel = new PlaylistModel();
    private MusicPlayer musicPlayer;
    private boolean isPlaying = false;
    private boolean isDone = true;
    private boolean playlistFocus = false;
    private Object currentSong = null;
    private Timer timer;
    private TimerTask timerTask;

    /**
     * Instantiates the tableviews
     * @throws IOException
     */
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
        songsInPlaylistListView = new ListView<>();
        playButtonImage = new ImageView();

    }

    /**
     * Initializes the tableviews and fulls them with data before startup
     * @param location
     * @param resources
     */
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

        volumeSlider.valueProperty().addListener(observable -> musicPlayer.mediaPlayer.setVolume(volume));
        searchTextField.textProperty().addListener((observable, oldValue, newValue)->{
            try
            {
                songModel.searchSongs(newValue);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    /**
     * Sets an image for the song currently playing
     */
    public void setSongImage(){
        try
        {
            File img = new File(songModel.songImageUpdate(songTable.getSelectionModel().getSelectedItem()));
            InputStream isImage = new FileInputStream(img);
            System.out.println(isImage);
            songImage.setImage(new Image(isImage));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Changes the view to addSong
     * @throws IOException
     */
    public void newSong() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("View/AddSong.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add/Edit Song");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Changes the View to editSong
     * @param actionEvent
     */
    public void editSong(ActionEvent actionEvent) {

        Song selectedSong = songTable.getSelectionModel().getSelectedItem();
        if(selectedSong == null){
            error("Please choose a song to edit");
        }
        else {
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

    }

    /**
     * deletes the selected song
     * @throws SQLException
     * @throws IOException
     */
    public void deleteSong() throws SQLException, IOException {
        if (songTable.getSelectionModel().getSelectedItem() == null){
            error("Please choose a song to delete");
        }
        else {
            songModel.deleteSong(songTable.getSelectionModel().getSelectedItem());
            songTable.getItems().remove(songTable.getSelectionModel().getSelectedItem());
            refreshSongList();
        }
    }

    /**
     * Changes the view to addPlaylist
     * @throws IOException
     */
    public void newPlaylist() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("View/AddPlaylist.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add/Edit Playlist");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Changes the view to editPlaylist
     * @param actionEvent
     * @throws IOException
     */
    public void editPlaylist(ActionEvent actionEvent) {

        Playlist selectedPlaylist = playlistTable.getSelectionModel().getSelectedItem();
        if(selectedPlaylist == null){
            error("Please choose a playlist to edit");
        }
        else {
            FXMLLoader root = new FXMLLoader(getClass().getResource("View/EditPlaylist.fxml"));
            Scene mainWindowScene = null;

            try {
                mainWindowScene = new Scene(root.load());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            Stage editPlaylistStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            editPlaylistStage.setScene(mainWindowScene);
            EditPlaylistController editPlaylistController = root.getController();
            editPlaylistController.setPlaylist(selectedPlaylist);
            editPlaylistStage.show();
        }
    }

    /**
     * Deletes the selected playlist
     */
    public void deletePlaylist(){
        if (playlistTable.getSelectionModel().getSelectedItem() == null){
            error("Please select a playlist to remove");
        }
        else {
            playlistModel.deletePlaylist(playlistTable.getSelectionModel().getSelectedItem());
            playlistTable.getItems().remove(playlistTable.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Adds the selected song to the selected playlist
     */
    public void addSongToPlaylist() {

        Song song = songTable.getSelectionModel().getSelectedItem();
        Playlist playlist = playlistTable.getSelectionModel().getSelectedItem();
        if (song == null || playlist == null){
            error("You have forgotten to select either a playlist to add to or a song to be added");
        }
        else {
            playlistModel.addSongToPlaylist(playlist.getId(), song.getId());
        }
    }

    /**
     * Deletes the selected song from a playlist
     */
    public void deletePlaylistSong() throws SQLException {
        playlistModel.deleteSongFromPlaylist(playlistTable.getSelectionModel().getSelectedItem().getId(), songsInPlaylistListView.getSelectionModel().getSelectedItem().getId());
        if(playlistTable.getSelectionModel().getSelectedItem() == null || songsInPlaylistListView.getSelectionModel().getSelectedItem() == null)
        {
            error("Please select a playlist to be deleted from or a song to delete");
        }
        else {
            songsInPlaylistListView.getItems().remove(songsInPlaylistListView.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Enables the user to move song in a playlist down in the list
     */
    public void moveSongDown() {
        {
            if(songsInPlaylistListView.getSelectionModel().getSelectedItem() != null)
            {
                if(songsInPlaylistListView.getSelectionModel().getSelectedIndex() != songsInPlaylistListView.getItems().size()-1) // if the row is in last so dont do nothing
                {
                    int index = songsInPlaylistListView.getSelectionModel().getSelectedIndex();
                    Song x = songsInPlaylistListView.getSelectionModel().getSelectedItem();
                    songsInPlaylistListView.getItems().set(index, songsInPlaylistListView.getItems().get(index+1));
                    songsInPlaylistListView.getItems().set(index+1, x);
                    songsInPlaylistListView.getSelectionModel().select(index+1);
                }
            }
        }
    }

    /**
     * Enables the user to move song in a playlist up in the list
     */
    public void moveSongUp() {
        if(songsInPlaylistListView.getSelectionModel().getSelectedItem() != null) // check if the user really selected a row in the table
        {
            if(songsInPlaylistListView.getSelectionModel().getSelectedIndex() != 0) // if the row first one so do nothing
            {
                int index = songsInPlaylistListView.getSelectionModel().getSelectedIndex(); // get the selected row index
                Song x = songsInPlaylistListView.getSelectionModel().getSelectedItem(); // get the selected item
                songsInPlaylistListView.getItems().set(index, songsInPlaylistListView.getItems().get(index-1)); // move the selected item up
                songsInPlaylistListView.getItems().set(index-1, x); // change the row with the item in above
                songsInPlaylistListView.getSelectionModel().select(index-1); // select the new row position
            }
        }
    }

    /**
     * Adjusts the volume when the user uses the slider
     * @return
     */
    public double adjustVolume(){
        return volume = volumeSlider.getValue() / 100;
    }

    /**
     * Refreshes the playlist
     * @throws SQLException
     * @throws IOException
     */
    public void refreshPlaylist() throws SQLException, IOException {
        playlistTable.getItems().clear();
        playlistTable.setItems(playlistModel.listToObservablelist());
        playlistTable.refresh();
    }

    /**
     * Checks if the song is done
     */
    private void endOfMedia(){
        musicPlayerModel.endOfMedia(songTable);
        isDone = true;
        cancelTimer();
    }

    /**
     * Begin the timer for the progressbar.
     */
    private void playMedia(){
        isPlaying = true;
        isDone = false;
        beginTimer();
    }

    /**
     * Stops everthing with the mediaplayer, cancel the timer for progressbar and changes the image of the playbutton.
     */
    public void stopSong(){
        musicPlayer.mediaPlayer.dispose();
        cancelTimer();
        isDone = true;
        isPlaying = false;
        shiftImage();
    }

    /**
     * plays a song if no song is playing or pauses if a song is already playing.
     */
    public void playSong(){
        try{
            //Checks if the song list is in focus
            songTable.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    playlistFocus = false;
                }
            });
            //Checks if the playlist songs is in focus
            songsInPlaylistListView.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    playlistFocus = true;
                }
            });
            //Checks if there is a song already playing and if it is the same song selected,
            //if not it will stop the song and start playing a the new selected song
            if (!isPlaying && isDone || !(currentSong == songTable.getSelectionModel().getSelectedItem())){
                if (musicPlayer != null){
                    musicPlayer.mediaPlayer.dispose();
                    cancelTimer();
                }
                //gets the info of the selected song in playlist
                if(songsInPlaylistListView.getSelectionModel().getSelectedItem() != null && playlistFocus == true){
                    System.out.println("playlist is focused");
                    musicPlayer = new MusicPlayer(songsInPlaylistListView.getSelectionModel().getSelectedItem());
                    musicPlayerModel = new MusicPlayerModel(songsInPlaylistListView.getSelectionModel().getSelectedItem());
                    currentSong = songsInPlaylistListView.getSelectionModel().getSelectedItem();
                }
                //gets the info of the selected song in list
                else if (songTable.getSelectionModel().getSelectedItem() != null && playlistFocus == false){
                    System.out.println("songs is focused");
                    musicPlayer = new MusicPlayer(songTable.getSelectionModel().getSelectedItem());
                    musicPlayerModel = new MusicPlayerModel(songTable.getSelectionModel().getSelectedItem());
                    currentSong = songTable.getSelectionModel().getSelectedItem();
                }
                //sets a function for when the mediaplayer starts playing
                musicPlayer.mediaPlayer.setOnPlaying(this::playMedia);
                //sets a function for when the mediaplayer song has finished
                musicPlayer.mediaPlayer.setOnEndOfMedia(this::endOfMedia);
                //plays the selected song
                musicPlayer.playSong();
                //sets the volume slider for volume
                musicPlayer.mediaPlayer.setVolume(adjustVolume());
                //gets info about the song
                String actualSong = (songTable.getSelectionModel().getSelectedItem()).getTitle();
                String currentArtist = (songTable.getSelectionModel().getSelectedItem()).getArtistName();
                //sets the info to the user.
                labelIsPlaying.setText("(" + actualSong + ")" + " Is Playing");
                labelArtist.setText(currentArtist);
                //gets the cover for the selected song
                setSongImage();
                isPlaying = true;
                //sets the image for the playButton
                shiftImage();
                System.out.println("Work playing");
              //Checks if a song is allready playing.
            } else if (isPlaying){
                //Pauses the song playing
                pauseSong();
                //sets the image for the playButton
                shiftImage();
                System.out.println("Paused");
              //if a song is in the mediaplayer and is not done and is not playing it will resume that song
            } else {
                //Resume the song
                musicPlayer.playSong();
                isPlaying = true;
                //sets the image for the playButton
                shiftImage();
                System.out.println("Should play again");
            }
            //if a error happens
        } catch (Exception e){
            //prints the error
            e.printStackTrace();
            //sets the booleans to false and true so another can be played agian.
            isPlaying = false;
            isDone = true;
        }
    }

    /**
     * Pauses the current song
     */
    public void pauseSong(){
        musicPlayer.pauseSong();
        isPlaying = false;
    }

    /**
     * Changes the image on the play button to a pause image and reverse
     */
    private void shiftImage()
    {
        try
        {
            //Checks if a song is not playing
            if (!isPlaying){
                //gets the link for the image
                File img = new File("src/Icons/play.png");
                InputStream isImage = new FileInputStream(img);
                System.out.println(isImage);
                //sets the image
                playButtonImage.setImage(new Image(isImage));
            }
            //Checks if a song is playing
            else if (isPlaying){
                //gets the link for the image
                File img = new File("src/Icons/pause.png");
                InputStream isImage = new FileInputStream(img);
                System.out.println(isImage);
                //sets the image
                playButtonImage.setImage(new Image(isImage));
            }
          //error if the icons could not be found
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Sets the progress on the progress bar
     */
    public void beginTimer(){
        //creates a new timer
        timer = new Timer();
        //starts a timertask that runs on another thread.
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //gets the current time of the song
                double current = musicPlayer.mediaPlayer.getCurrentTime().toSeconds();
                //gets the total amount of the song
                double end = musicPlayer.mediaPlayer.getMedia().getDuration().toSeconds();
                //takes the two info and devides to get the progressbar to show how long in the song is has made it.
                songProgressBar.setProgress(current/end);
                //progressbar goes from 0 - 1 and if it reach 1 we stop the timer.
                if(current/end == 1){
                    cancelTimer();
                }
            }
        };
        //sets how many times the progressbar should update the progress.
        timer.scheduleAtFixedRate(timerTask, 100, 100);
    }

    /**
     * Stops the timer so no resources are wasted.
     */
    public void cancelTimer(){
        //cancels the timer
        musicPlayerModel.cancelTimer(timer);
    }

    /**
     * Skips to the next song
     */
    public void nextSong() {
        songTable.getSelectionModel().selectNext();
        playSong();
    }

    /**
     * Returns to the previous song
     */
    public void previousSong() {
        songTable.getSelectionModel().selectAboveCell();
        playSong();
    }

    /**
     * Refreshes the songtable
     * @throws IOException
     * @throws SQLException
     */
    public void refreshSongList() throws IOException, SQLException {
        songTable.getItems().clear();
        songTable.setItems(songModel.listToObservablelist());
        songTable.refresh();

    }

    /**
     * loads the songs in a selected playlist
     */
    public void lookAtPlaylist() {
        Playlist playlist = playlistTable.getSelectionModel().getSelectedItem();
        try {
            ObservableList<Song> observableList = playlistModel.playlistSongsToObservablelist(playlist.getId());
            songsInPlaylistListView.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * a method to error handle when a user doesn't do the intended operation
     * @param text text that gets shown to the user
     */
    private void error(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.OK);
        alert.showAndWait();
    }

    /**
     * Refreshes the entire mainwindow
     * @throws SQLException
     * @throws IOException
     */
    public void refreshAction() throws SQLException, IOException {
        refreshSongList();
        refreshPlaylist();
    }
}
