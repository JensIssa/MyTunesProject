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
                    String artistName = rs.getString("ArtistName");
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

    public Song createSong(String title, String artistName, float songLength, String category, String url ) throws SQLException {
        String sql = "INSERT INTO SONG(Title, ArtistName, SongLength, Category, Url) values (?,?,?,?,?);";
        Connection connection = connectionPool.checkOut();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, artistName);
            preparedStatement.setFloat(3, songLength);
            preparedStatement.setString(4, category);
            preparedStatement.setString(5, url);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int id = 0;
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
            Song song = new Song(id, title, artistName, songLength, category, url);
            return song;
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return null;
    }

    public void updateSong(Song song) throws SQLException {
        try(Connection connection = connectionPool.checkOut()){
            String sql = "UPDATE SONG SET Title=?, ArtistName=?, SongLength=?, Category=?, Url=? WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getArtistName());
            preparedStatement.setFloat(3, song.getsongLength());
            preparedStatement.setString(4, song.getCategory());
            preparedStatement.setString(5, song.getUrl());
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




