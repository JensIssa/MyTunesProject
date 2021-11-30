package com.jens.GUI.Controller;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainWindowController {

    public Button moveUp;
    public Button moveDown;
    public Button directAddSongToPlaylist;

    public MainWindowController() {
        Image img = new Image("");
        ImageView view = new ImageView(img);

        moveUp.setGraphic(view);
    }
}
