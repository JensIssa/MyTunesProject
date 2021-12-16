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
    public List<Song> getAllSongs() {
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
                    String artistName = rs.getString("ArtistName");
                    int songLength = rs.getInt("SongLength");
                    String category = rs.getString("Category");
                    String url = rs.getString("Url");
                    String urlImg = rs.getString("urlImg");
                    Song song = new Song(id, title, artistName, songLength, category, url, urlImg);
                    songs.add(song);
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return songs;
    }

    public Song createSong(String title, String artistName, int songLength, String category, String url, String urlImg ) {
        String sql = "INSERT INTO SONG(Title, ArtistName, SongLength, Category, Url, urlImg) values (?,?,?,?,?,?);";
        Connection connection = connectionPool.checkOut();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, artistName);
            preparedStatement.setInt(3, songLength);
            preparedStatement.setString(4, category);
            preparedStatement.setString(5, url);
            preparedStatement.setString(6, urlImg);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int id = 0;
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
            Song song = new Song(id, title, artistName, songLength, category, url, urlImg);
            return song;
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return null;
    }

    public void updateSong(Song song){
        try(Connection connection = connectionPool.checkOut()){
            String sql = "UPDATE SONG SET Title=?, ArtistName=?, SongLength=?, Category=?, Url=?, urlImg=? WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getArtistName());
            preparedStatement.setInt(3, song.getSongLength());
            preparedStatement.setString(4, song.getCategory());
            preparedStatement.setString(5, song.getUrl());
            preparedStatement.setString(6, song.getUrlImg());
            preparedStatement.setInt(7, song.getId());
            if(preparedStatement.executeUpdate() != 1){
                throw new Exception("Could not delete Song");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSong(Song song){
        try(Connection connection = connectionPool.checkOut()){
            String sql = "DELETE FROM SONG WHERE Id =?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, song.getId());
            if(preparedStatement.executeUpdate() != 1){
                throw new Exception("Could not delete Song");
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

}




