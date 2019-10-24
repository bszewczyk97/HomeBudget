package main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/home_budget";
    private static final String USER = "root" ;
    private static final String PASS = "";
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
            se.printStackTrace();
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
