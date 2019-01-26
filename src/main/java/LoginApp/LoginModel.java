package LoginApp;
import java.sql.*;

public class LoginModel {

    private static boolean kierownikPrivilege;
    private static boolean pracownikPrivilege;
    private static boolean demoPrivilege;
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/gamestore?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static Connection connection;


    public boolean isLoggedIn(String USERNAME, String PASSWORD){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
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

    private void setUpUserAttributes(String USERNAME){
        PreparedStatement pr;
        ResultSet rs;
        String option = null;

        try {
            pr = connection.prepareStatement("SELECT uprawnienia FROM uzytkownicy WHERE login = ? ");
            pr.setString(1, USERNAME);
            rs = pr.executeQuery();

            if (rs.next()) {
                option = rs.getString(1);
            }
            else
                return;
        }
        catch(SQLException ex) {
            kierownikPrivilege = false;
            pracownikPrivilege = false;
            demoPrivilege = false;
        }


        if(option.equals("kierownik")) {
            kierownikPrivilege = true;
            pracownikPrivilege = true;
            demoPrivilege = true;
        }
        else if(option.equals("pracownik")) {
            pracownikPrivilege = true;
            demoPrivilege = true;
        }
        else if(option.equals("demo")) {
            demoPrivilege = true;
        }
    }

    public static Connection getConnection(){
        return connection;
    }

    public static boolean hasKierownikPrivilege(){
        return kierownikPrivilege;
    }
    public static boolean hasPracownikPrivilege(){
        return pracownikPrivilege;
    }
    public static boolean hasDemoPrivilege(){
        return demoPrivilege;
    }
}