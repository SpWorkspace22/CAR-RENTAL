package userutility;

import java.sql.*;
import java.util.*;
import java.util.regex.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserAction {
    private Connection conn=null;
    private Scanner sc=new Scanner(System.in);

    public UserAction(Connection conn){
        this.conn = conn;
    }

    public boolean isUserAuthorize(){
        try{
            System.out.println("Enter User Name");
            String user = sc.next();
            System.out.println("Enter password");
            String pass = sc.next();
            pass = hasPassword(pass);

            Statement stmt = conn.createStatement();
            ResultSet rs= stmt.executeQuery("select Fullname from tblUsers where emailid='"+user+"' and password='"+pass+"'");
            rs.next();
            if(rs.getString(1)!=null){
                System.out.println("------------- Welcome,"+rs.getString(1)+"-----------------------");
                return true;
            }
        }catch(SQLException sq){
            sq.printStackTrace();
            return false;
        }

        return false;
    }

    public boolean isAdminAuthorize(){
        try{
            System.out.println("Enter User Name");
            String user = sc.next();
            System.out.println("Enter password");
            String pass = sc.next();
            pass = hasPassword(pass);

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select username from admin where username='"+user+"' and password='"+pass+"'");
            rs.next();
            if(rs.getString(1)!=null){
                System.out.println("---------- Welcome,"+rs.getString(1)+"-----------------");
                return true;
            }
        }catch(SQLException sq){
            sq.printStackTrace();
            return false;
        }

        return false;
    }
    private String hasPassword(String input){
        try {
 
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
 
            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());
 
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
 
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
 
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void userSignUp(){
        sc.nextLine();
        System.out.println("Enter Full Name");
        String fullName = sc.nextLine();
        System.out.println("Enter Email id");
        String email = sc.next();
        System.out.println("Enter phone no");
        String contact = sc.next();
        System.out.println("Enter Password");
        String pass = hasPassword(sc.next());

        if(!Pattern.compile("^(.+)@(.+)$").matcher(email).matches()){
            System.out.println("----- ERROR: Invalid Email ----------");
            return;
        }

        if(!Pattern.compile("^\\d{10}$").matcher(contact).matches()){
            System.out.println("----- ERROR: Invalid Contact ----------");
            return;
        }

        try{
            PreparedStatement stmt = conn.prepareStatement("insert into tblusers(fullname,emailid,password,contact)values(?,?,?,?)");
            stmt.setString(1,fullName);
            stmt.setString(2,email);
            stmt.setString(3,pass);
            stmt.setString(4,contact); 

            if(stmt.execute()){
                System.out.println("------- SUCCESS: User Added -------");
                return;
            }
        }catch(SQLException sq){
            System.out.println("------- ERROR:"+sq.getMessage()+"-------");
        }
    }

}
