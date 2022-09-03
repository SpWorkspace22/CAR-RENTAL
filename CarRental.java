// Java Librariry
import java.sql.*;
import java.util.*;

//User Modules
import utility.ConnectDatabase;
import userutility.UserAction;

class CarRental{
    private static Connection conn=null;
    private static UserAction user=null;
    public static void main(String[] args){
        System.out.println("~~~~~~~~~~~~~~~~~ Welcome To Car Rental Services ~~~~~~~~~~~~~~~~~~~~");
        conn = ConnectDatabase.getConnection();
        if(conn!=null){
            System.out.println("---- Database Connected -------");
            loginToSystem();
        }else{
            System.out.println("---- Database Not Connected -------");
            System.exit(0);
        }
        if(ConnectDatabase.closeConnetion()){
            System.out.println("---- Please Visit Again --------");
        }
    }

    private static void loginToSystem(){
        Scanner sc = new Scanner(System.in);
        user = new UserAction(conn);
        
        while(true){
            System.out.println("1. User_Log_In\t2. User_Sign_Up\t3. Admin_Log_In \t4. Exit");
            String userTyp = sc.next();
            
            switch(userTyp){
                case "User_Log_In":
                        if(user.isUserAuthorize()){
                            userActions();
                        }else{
                            System.out.println("--- User Does Not Exist plase Recheck ---");
                        }
                        break;
                case "User_Sign_Up":
                        user.userSignUp();
                        break;
                case "Admin_Log_In":
                        if(user.isAdminAuthorize()){
                            adminActions();
                        }
                        else{
                            System.out.println("--- User Does Not Exist plase Recheck ---");
                        }
                        break;
                case "Exit":
                        return;
                default :
                        System.out.println("Please choose user type ");
            }
        }
    }

    private static void userActions(){
        Scanner sc =  new Scanner(System.in);
        while(true){
            System.out.println("1. List_Cars\t2.Show_My_Bookings\t3.Log_Out");
            String opeartion = sc.next();
            
            switch(opeartion){
                case "List_Cars":
                                break;
                case "Show_My_Bookings":
                                break;
                case "Log_Out":
                                return;
                default :
                        System.out.println("Please Choose Valid Opetion..");
            }
        }
    }

    private static void adminActions(){
        Scanner sc =  new Scanner(System.in);
        while(true){
            System.out.println("1. Add_Brand\t2. List_Brands\t3.Add_Vehicle\t4.Show_Vehicle_List\t5. Booking_Details\t6.Log_Out");
            String opeartion = sc.next();
            
            switch(opeartion){
                case "Add_Brand":
                                break;
                case "List_Brands":
                                break;
                case "Add_Vehicle":
                                break;
                case "Show_Vehicle_List":
                                break;
                case "Booking_Details":
                                break;
                case "Log_Out":
                                return;
                default :
                        System.out.println("Please Choose Valid Opetion..");
            }
        }
    }

}