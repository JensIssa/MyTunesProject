package com.jens.DAL;

import com.jens.BE.Playlist;
import com.jens.BE.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import javax.xml.transform.Result;
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
            String sql = "SELECT * FROM Playlist;";
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
                    playlist.setTotalSongs(resultSet.getInt("totalTime"));
                    playlist.setTotalTime(resultSet.getInt("totalSongs"));
                    //adder playlist objektet til arrayliste  all playlister
                    allPlaylists.add(playlist);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allPlaylists;
    }
    public List<Song> getAllPlaylistSongs(int playlistId) throws SQLException {
        ArrayList<Song> allSongs = new ArrayList<>();
        try(Connection connection = connectionPool.checkOut()){
            String sql = "SELECT FROM Song s" +
                    "inner join PlaylistSong ps ON ps.songId = s.id" +
                    "where ps.playlistId = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, playlistId);

            if (preparedStatement.execute()){
                ResultSet resultSet = preparedStatement.getResultSet();
                while(resultSet.next()){
                    int id = resultSet.getInt("Id");
                    String title = resultSet.getString("Title");
                    String artistName = resultSet.getString("ArtistName");
                    int songLength = resultSet.getInt("SongLength");
                    String category = resultSet.getString("Category");
                    String url = resultSet.getString("Url");
                    String urlImg = resultSet.getString("urlImg");
                    Song song = new Song(id, title, artistName, songLength, category, url, urlImg);
                    allSongs.add(song);

                }
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allSongs;
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
            String sql = "UPDATE PLAYLIST SET Name=? WHERE Id=?;";
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

    public void deleteSongFromPlaylist(int playlistId, int songId) throws SQLException {
        try(Connection connection = connectionPool.checkOut()) {
            String sql = "DELETE FROM PlaylistSong WHERE playlistId=? AND songId=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, songId);
            preparedStatement.executeUpdate();
        } catch (SQLServerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        }


    }


