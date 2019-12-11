package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{
    private static final String DB_URL = "jdbc:mysql://localhost:3307/home_bugdet";
    private static final String USER = "budget_admin" ;
    private static final String PASS = "admin";
    private Connection conn;
    public DBConnection() {
        conn = null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");
        }
        catch(SQLException se)
        {

            System.out.print("Do not connect to DB - Error:"+se);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    public boolean login()
    {
        return false;
    }

    public Connection getConn() {
        return conn;
    }

    public boolean close()
    {
        try
        {
            if(conn!=null) {
                conn.close();
                return true;
            }
        }
        catch(SQLException se)
        {
            se.printStackTrace();
        }

        return false;
    }

}
