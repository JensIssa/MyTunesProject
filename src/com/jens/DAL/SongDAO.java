package com.jens.DAL;

import com.jens.BE.Song;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class SongDAO {

    private final JDBCConnectionPool connectionPool;

    public SongDAO() throws IOException {
        connectionPool = new JDBCConnectionPool();
    }
    public List<Song> getAllSongs() throws IOException, SQLException {
        ArrayList<Song> songs = new ArrayList<>();

        try(Connection connection = connectionPool.checkOut())
        {
            String sql = "SELECT * FROM Song;";
            Statement statement = connection.createStatement();

            if(statement.execute(sql)){
                ResultSet rs = statement.getResultSet();
                while (rs.next()){
                    int id = rs.getInt("Id");
                    String title = rs.getString("Title");
                    String artistName = rs.getString("AristName");
                    float songLength = rs.getFloat("SongLength");
                    String category = rs.getString("Category");
                    String url = rs.getString("Url");
                    Song song = new Song(id, title, artistName, songLength, category, url);
                    songs.add(song);
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return songs;
    }

}




