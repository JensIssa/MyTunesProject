package com.jens.DAL;

import com.jens.BE.Playlist;
import com.jens.BE.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    private final JDBCConnectionPool connectionPool;

    public PlaylistDAO() throws IOException {
        connectionPool = new JDBCConnectionPool();
    }
    public List<Playlist> getAllPlaylists()
    {
        //Opretter en arraylist til alle vores playliste
        ArrayList<Playlist> allPlaylists = new ArrayList<>();
        //Skaber forbindelse til vores database
        try (Connection connection = connectionPool.checkOut()) {
            //Anvendelse af SQL kommando SELECT * FROM som siger, at man skal vælge fra Playlist database og som også vælger count og numberofsongs
            String sql = "SELECT p.*, \n" +
                    "(select count(songId) FROM PlaylistSong pls where pls.playlistid = p.id) totalSongs, \n" +
                    "(select sum(songlength) from song s inner join playlistsong pls on pls.songId = s.id where pls.playlistId = p.id) totalTime FROM Playlist p\n";
            Statement statement = connection.createStatement();
            //If-sætning til at execute forbindelsen

            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    //Sætter alle parametre
                    String name = resultSet.getString("name");
                    int id = resultSet.getInt("id");
                    //Opretter ny playlist objekt i databasen
                    Playlist playlist = new Playlist(id, name);
                    playlist.setTotalSongs(resultSet.getInt("numberOfSongs"));
                    playlist.setTotalTime(resultSet.getFloat("playListLength"));
                    //adder playlist objektet til arrayliste  all playlister
                    allPlaylists.add(playlist);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allPlaylists;
    }

    public Playlist createPlaylist(String name) throws SQLException {
        String sql = "INSERT INTO PLAYLIST(Name) values (?);";
        Connection connection = connectionPool.checkOut();
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int id = 0;
            if(resultSet.next()){
                id = resultSet.getInt(1);
            }
            Playlist playlist = new Playlist(id, name);
            return playlist;
        }
        catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return null;
    }

    public void updatePlaylist(Playlist playlist) throws SQLException {
        try(Connection connection = connectionPool.checkOut()){
            String sql = "UPDATE SONG SET Name=? WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlist.getPlaylistName());
            if(preparedStatement.executeUpdate() != 1){
                throw new Exception("Could not delete Song");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePlaylist(Playlist playlist){
        try(Connection connection = connectionPool.checkOut()){
            String sql = "DELETE FROM PLAYLIST WHERE Id =?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, playlist.getId());
            if(preparedStatement.executeUpdate() != 1){
                throw new Exception("Could not delete Song");
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

    public void addSongToPlaylist(int playlistId, int songId)
    {
        //Insert into SQL kommando, hvori at playlistID og songID bliver smidt ind
        String sql = "INSERT INTO PlaylistSong(playlistId, songId) VALUES (?, ?)";
        try (Connection connection = connectionPool.checkOut()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            //Sætter parametre
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, songId);
            preparedStatement.execute();
        } catch (SQLServerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

}
