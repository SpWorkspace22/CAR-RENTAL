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

    public void addBrands(){
        try{
            System.out.println("---- Enter Brand Name ----");
            String bname = sc.nextLine();
            PreparedStatement psmt = conn.prepareStatement("insert into tblbrands(brandname) value(?)");
            psmt.setString(1,bname);
            if(!psmt.execute()){
                System.out.println("\n---- SUCCESS: Brand "+bname+"-- Added ----");
            }else{
                System.out.println("---- ERROR: Brand Not Added ------------");
            }
        }catch(SQLException ex){
            System.out.println("---- ERROR: "+ex.getMessage()+"------");
        }
    }

    public void listBrands(){
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select id,brandname from tblbrands");
            System.out.print("\n--------Brand List -----------\n");
            while(rs.next()){
                System.out.println(rs.getInt(1)+") - "+rs.getString(2));
            }
            System.out.print("\n");

            System.out.println("Y: To Remove Brand N: Continue ");
            if(sc.next().equals("Y")){
                removeBrand();
            }
        }catch(SQLException ex){
            System.out.println("---- ERROR: "+ex.getMessage()+"------");
        }
    }

    private void removeBrand(){
        System.out.println("-----INPUT: Enter Id of Brand to remove -----");
        int id = sc.nextInt();
        try{
            Statement stmt = conn.createStatement();
            boolean result = stmt.execute("delete from tblbrands where id="+id);
            if(!result){
                System.out.println("---- SUCCESS: Brand Removed ------------");
            }else{
                System.out.println("---- ERROR: Brand Removed ------------");
            }
        }catch(SQLException ex){
            System.out.println("---- ERROR: "+ex.getMessage()+"------------");
        }

    }

}
