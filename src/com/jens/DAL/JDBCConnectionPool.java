package com.jens.DAL;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCConnectionPool extends ObjectPool<Connection>
{
    private final DatabaseConnector connectionProvider;

    JDBCConnectionPool() throws IOException
    {
        connectionProvider = new DatabaseConnector();
    }

    @Override
    protected Connection create()
    {
        try
        {
            return connectionProvider.getConnection();
        } catch (SQLServerException ex)
        {
            ex.printStackTrace(); //Perfect exception handling... (NO!)
            return null;
        }
    }

    @Override
    public boolean validate(Connection o)
    {
        try
        {
            return (!((Connection) o).isClosed());
        } catch (SQLException e)
        {
            e.printStackTrace();
            return (false);
        }
    }

    @Override
    public void expire(Connection o)
    {
        try
        {
            ((Connection) o).close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
