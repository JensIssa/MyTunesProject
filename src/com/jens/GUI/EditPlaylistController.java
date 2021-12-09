package com.jens.GUI;

import com.jens.BE.Song;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EditPlaylistController {

    public Button cancel;
    public TextField playlistName;

    MainWindowController mainWindowController = new MainWindowController();

    public EditPlaylistController() throws IOException {
    }

    public void cancelNewEdit(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void addPlaylist(ActionEvent actionEvent) {

    }
}
