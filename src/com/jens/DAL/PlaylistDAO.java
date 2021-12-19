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

    /**
     * In this method we extract all playlists from the playlistTable in the database to the program with the help of the SQL command SELECT*
     * in this method we also calculate the totallength of the songs in the specific playlist
     * @return returns an arraylist with all playlists
     */
    public List<Playlist> getAllPlaylists()
    {
        ArrayList<Playlist> allPlaylists = new ArrayList<>();
        try (Connection connection = connectionPool.checkOut()) {
            String sql = "SELECT * FROM Playlist;";
            Statement statement = connection.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int id = resultSet.getInt("id");
                    Playlist playlist = new Playlist(id, name);
                    playlist.setTotalSongs(resultSet.getInt("totalTime"));
                    playlist.setTotalTime(resultSet.getInt("totalSongs"));

                    allPlaylists.add(playlist);
                }
                for (int i = 0; i < allPlaylists.size(); i++) {
                    var playlist = allPlaylists.get(i);
                    if (playlist != null) {
                        var totalLength = getTotalDuraton(playlist.getId());
                        playlist.setTotalTime(totalLength);
                        var totalSongs = getAllPlaylistSongs(playlist.getId());
                        playlist.setTotalSongs(totalSongs.size());
                    }
                }
                return allPlaylists;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allPlaylists;
    }

    /**
     * uses the SQL command SELECT * on the song table to extract all songs
     * we then INNER JOIN our PlaylistSongs table ON our SongId column in the playlistSongs table and then reference the actual Song table's id
     * where we then reference to the PlaylistSongs table reference to the playlistId to make a list of songs in a plalyist
     * @param playlistId id of the specific playlist
     * @return returns an arraylist of all the songs in a playlist
     * @throws SQLException
     */

    public List<Song> getAllPlaylistSongs(int playlistId) throws SQLException {
        ArrayList<Song> allSongs = new ArrayList<>();
        try(Connection connection = connectionPool.checkOut()){
            String sql = "SELECT * FROM Song s " +
                    "     inner join PlaylistSongs ps on ps.SongId = s.id" +
                    "       where ps.PlaylistId = ?;";


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

    /**
     *Uses the SQL command INSERT INTO to create a new playlist  in the database table playlist
     * @param name the parameter is the name of the playlist
     * @return
     */
    public Playlist createPlaylist(String name) {
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

    /**
     * takes every parameter of an already existing playlist object and then sets the new values into an updated playlist
     * @param playlist returns the values of the updated playlist
     */
    public void updatePlaylist(Playlist playlist) {
        try(Connection connection = connectionPool.checkOut()){
            String sql = "UPDATE PLAYLIST SET Name=? WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlist.getPlaylistName());
            preparedStatement.setInt(2, playlist.getId());
            if(preparedStatement.executeUpdate() != 1){
                throw new Exception("Could not delete Song");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * checks where the ID of a specific playlist is and then deletes the playlist from the song table
     * @param playlist playlist to be deleted
     */
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

    /**
     * in this method we make a for-loop to count the total duration of the songs in a playlist
     * @param playlistId
     * @return
     * @throws SQLException
     */
    public float getTotalDuraton(int playlistId) throws SQLException {
        String sql = "SELECT * FROM Song FULL OUTER JOIN PlaylistSongs ON PlaylistSongs.songId = song.id WHERE PlaylistSongs.playlistId =?;";
        float totalDuration = 0;
        try (var con = connectionPool.checkOut();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, playlistId);
            st.execute();

            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                float song_length = rs.getFloat("SongLength");
                totalDuration += song_length;
            }

            return totalDuration;

        } catch (SQLException exception) {
            exception.printStackTrace();
            return 0;
        }
    }

    /**
     * adds a song to a playlist using the third table Playlistsongs with the SQL command INSERT INTO
     * @param playlistId the id of the playlist that it's getting added to
     * @param songId the id of the song that is getting added to the playlist
     */
    public void addSongToPlaylist(int playlistId, int songId)
    {
        //Insert into SQL kommando, hvori at playlistID og songID bliver smidt ind
        String sql = "INSERT INTO PlaylistSongs(playlistId, songId) VALUES (?, ?)";
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

    /**
     * deletes a song from a playlist using the third table PlaylistSongs with the SQL command DELETE FROM
     * @param playlistId Id of the playlist the songs is getting deleted from
     * @param songId id of the song that is getting deleted from the playlist
     * @throws SQLException
     */
    public void deleteSongFromPlaylist(int playlistId, int songId) throws SQLException {
        try(Connection connection = connectionPool.checkOut()) {
            String sql = "DELETE FROM PlaylistSongs WHERE playlistId=? AND songId=?;";
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
