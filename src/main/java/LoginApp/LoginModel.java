package LoginApp;
import java.sql.*;

public class LoginModel {

    private static boolean ADMIN;
    private static boolean WORKER;
    private static boolean DISPLAY;
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/GymPro";
    private static Connection connection;


    public boolean isLoggedIn(String USERNAME, String PASSWORD){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            setUpUserAttributes(USERNAME);
            return true;
        }

        catch(ClassNotFoundException ex) {
            System.out.println(ex.toString());
            return false;
        }
        catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
    }

    public void setUpUserAttributes(String USERNAME){
        PreparedStatement pr;
        ResultSet rs;
        String option = null;

        try {
            pr = this.connection.prepareStatement("SELECT stanowisko FROM pracownicy WHERE login = ? ");
            pr.setString(1, USERNAME);
            rs = pr.executeQuery();

            if (rs.next()) {
                option = rs.getString(2);
            }
        }
        catch(SQLException ex) {
            ADMIN = false;
            WORKER = false;
            DISPLAY = false;
        }

        if(option == null)
            return;

        else if(option.equals("kierownik"))
            ADMIN = true;

        else if(option.equals("pracownik"))
            WORKER = true;

        else if(option.equals("DISPLAY"))
            DISPLAY = true;
    }

    public static Connection getConnection(){
        return connection;
    }

    public static boolean isAdmin(){
        return ADMIN;
    }
    public static boolean isWorker(){
        return WORKER;
    }
    public static boolean isDisplay(){
        return DISPLAY;
    }
}