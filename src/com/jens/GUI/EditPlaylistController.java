package com.jens.GUI;

import com.jens.BE.Playlist;
import com.jens.BE.Song;
import com.jens.GUI.Model.PlaylistModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EditPlaylistController {

    public Button cancel;
    public TextField playlistName;
    public TextField playlistIdTxt;
    PlaylistModel playlistModel;

    MainWindowController mainWindowController = new MainWindowController();

    public EditPlaylistController() throws IOException {
        playlistModel = new PlaylistModel();
    }

    public void cancelNewEdit(ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    public void addPlaylist(ActionEvent actionEvent) throws SQLException {
        String updateName = playlistName.getText();
        int updateId = Integer.parseInt(playlistIdTxt.getText());

        Playlist updatedPlaylist = new Playlist(updateId, updateName);
        playlistModel.updatePlaylist(updatedPlaylist);

        FXMLLoader parent = new FXMLLoader(getClass().getResource("View/MainWindow.fxml"));
        Scene mainWindowScene = null;
        try{
            mainWindowScene = new Scene(parent.load());

        }catch (IOException exception){
            exception.printStackTrace();
        }
        Stage editPlaylistStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        editPlaylistStage.setScene(mainWindowScene);

    }

    public void setPlaylist(Playlist playlist){
        playlistName.setText(playlist.getPlaylistName());
        playlistIdTxt.setText(Integer.toString(playlist.getId()));
    }
}
