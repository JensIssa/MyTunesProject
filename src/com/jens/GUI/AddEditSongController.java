package com.jens.GUI;

import com.jens.GUI.Model.SongModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AddEditSongController {

    public TextField songTitle;
    public TextField songArtist;
    public TextField songLength;
    public ComboBox categoryChoice;
    public TextField filePath;
    public Button cancel;

    private SongModel songModel = new SongModel();

    public AddEditSongController() throws IOException {
    }



    public void CancelNew_Edit(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void createSong(ActionEvent actionEvent) throws SQLException {
        songModel.createSong(songTitle.getText(), songArtist.getText(), Integer.parseInt(songLength.getText()), categoryChoice.getSelectionModel().getSelectedItem().toString(), filePath.getText());

        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void chooseSongFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("", ".wav", ".mp3"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null){
            filePath.setText(file.getAbsolutePath());
        }
    }
}
