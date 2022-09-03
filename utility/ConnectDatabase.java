package utility;
import java.sql.*;

public class ConnectDatabase{
    private static Connection conn=null;

    ConnectDatabase(){
    }   
    
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/carrental","root","");
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return conn;
    }

    public static boolean closeConnetion(){
        if(conn!=null){
            try {
                conn.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }
}