package com.jens.GUI;

import com.jens.GUI.Model.PlaylistModel;
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
    }

    public void cancelNewEdit(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void addPlaylist(ActionEvent actionEvent) throws SQLException, IOException {
        playlistModel.createPlaylist(playlistName.getText());

        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}
