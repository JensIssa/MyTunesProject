package com.jens.GUI.Controller;


import com.jens.BE.Song;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    public TableColumn songTitleColumn;
    public TableColumn songArtistColumn;
    public TableColumn songCategoryColumn;
    public TableColumn songTimeColumn;
    public TableView songTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        songTitleColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("Title"));
        songArtistColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("Title"));
        songCategoryColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("Title"));
        songTimeColumn.setCellValueFactory(new PropertyValueFactory<Song, String>("Title"));
        songTable.setItems(getSongs());
    }

    public MainWindowController() {

    }

    public void newSong(ActionEvent actionEvent) {

    }

    public void editSong(ActionEvent actionEvent) {
    }

    public void deleteSong(ActionEvent actionEvent) {
    }

}
