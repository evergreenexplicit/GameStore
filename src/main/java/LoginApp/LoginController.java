package LoginApp;

import Client.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class LoginController {

    LoginModel loginModel = new LoginModel();

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label login_prompt;


    public void Login() throws Exception{
        String user =  username.getText();
        String pass = password.getText();

        if(loginModel.isLoggedIn(user, pass)) {
            System.out.println("Użytkownik " + user + " został zalogowany do systemu.");
            Main main = new Main();
           main.start(new Stage());
            Stage stageToClose = (Stage) username.getScene().getWindow();
            stageToClose.close();
        }
        else{
            login_prompt.setText("Nieprawidłowa nazwa użytkownika lub hasło!");
        }
    }
}