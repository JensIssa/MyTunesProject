package com.jens.GUI;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AddEditPlaylistController {


    public Button cancel;

    public void cancelNewEdit(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void addPlaylist(ActionEvent actionEvent) {
    }
}
