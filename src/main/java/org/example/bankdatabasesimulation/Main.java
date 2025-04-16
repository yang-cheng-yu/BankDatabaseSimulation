package org.example.bankdatabasesimulation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static Connection connect()
    {
        String Base_Path="jdbc:sqlite:src/main/resources/database/";
        //jdbc:sqlite is a JDBC(java database connectivity) connection URL used to connect to a SQLite database in java
        String DB_Path=Base_Path+"database.db";

        Connection connection;
        try{
            //try to connect to the database
            connection= DriverManager.getConnection(DB_Path);
            //DriverManager is a class that knows all the registered database drivers(like SQLite, mysql,...)
            //chooses the correct one when you try to connect
        }
        catch(SQLException e)
        {
            throw new RuntimeException(e);
        }
        return connection;
    }

}
