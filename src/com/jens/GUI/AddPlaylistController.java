package com.jens.GUI;

import com.jens.GUI.Model.PlaylistModel;
import com.jens.Main;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AddPlaylistController {

    PlaylistModel playlistModel = new PlaylistModel();
    MainWindowController mainWindowController = new MainWindowController();

    public Button cancel;
    public TextField playlistName;

    public AddPlaylistController() throws IOException {
        mainWindowController = new MainWindowController();
    }

    public void cancelNewEdit(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void addPlaylist(ActionEvent actionEvent) throws SQLException, IOException {
        playlistModel.createPlaylist(playlistName.getText());
        mainWindowController.refreshPlaylist();
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
