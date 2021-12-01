package com.jens.DAL;

import com.jens.BE.Playlist;
import com.jens.BE.Song;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {

    private final JDBCConnectionPool connectionPool;

    public PlaylistDAO() throws IOException {
        connectionPool = new JDBCConnectionPool();
    }
    public List<Playlist> getAllPlaylists() throws IOException, SQLException {
        ArrayList<Playlist> playlists = new ArrayList<>();

        try(Connection connection = connectionPool.checkOut())
        {
            String sql = "SELECT * FROM Playlist;";
            Statement statement = connection.createStatement();

            if(statement.execute(sql)){
                ResultSet rs = statement.getResultSet();
                while (rs.next()){
                    int id = rs.getInt("Id");
                    String name = rs.getString("Name");
                    Playlist playlist = new Playlist(id, name);
                    playlists.add(playlist);
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return playlists;
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
}
