package com.jens.GUI;

import com.jens.GUI.Model.PlaylistModel;
import com.jens.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AddPlaylistController {

    PlaylistModel playlistModel = new PlaylistModel();
    MainWindowController mainWindowController;

    @FXML
    private Button cancel;
    @FXML
    private TextField playlistName;

    /**
     * Instantiates the mainWindowController
     * @throws IOException
     */
    public AddPlaylistController() throws IOException {
        mainWindowController = new MainWindowController();
    }

    /**
     * Exits AddPlaylistWindow
     */
    public void cancelNewEdit() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Creates a playlist
     * @throws SQLException
     * @throws IOException
     */
    public void addPlaylist() throws SQLException, IOException {
        playlistModel.createPlaylist(playlistName.getText());
        mainWindowController.refreshPlaylist();
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
