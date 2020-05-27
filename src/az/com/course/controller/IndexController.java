package az.com.course.controller;

import az.com.course.dto.LogOnDTO;
import az.com.course.dto.PersonDTO;
import az.com.course.service.PersonService;
import az.com.course.service.PersonServiceInterface;
import az.com.course.utility.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class IndexController implements Initializable {

    PersonServiceInterface personService = new PersonService();
    Utilities utilities = new Utilities();

    public ImageView image;
    @FXML
    private Label errorLable;
    @FXML
    private Button usersTable;
    @FXML
    private Button registerButton;
    @FXML
    private TextField logOnEmail;
    @FXML
    private PasswordField logOnPassword;


    public List<LogOnDTO> longonmethod() throws SQLException {
        return personService.lgFromBaza();
    }


    @FXML
    public void logOnUser(ActionEvent actionEvent) {
        String email = logOnEmail.getText();
        String password = logOnPassword.getText();
        try {
            List<LogOnDTO> lgdatas = longonmethod();
            for (int i = 0; i < lgdatas.size(); i++) {
                if (email.equals(lgdatas.get(i).getEmail()) && password.equals(lgdatas.get(i).getPassword())) {
                    PersonDTO userByEmail = personService.TakeDataByEmail(email);
                    Stage primaryStage = new Stage();
                    Parent root = null;
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/fxml/userStage.fxml"));
                    try {
                        root = fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    UserStageController userStageController = fxmlLoader.getController();
                    userStageController.welcome(userByEmail);
                    primaryStage.setTitle("User window");
                    primaryStage.setScene(new Scene(root, 600, 600));
                    primaryStage.show();
                    Utilities.hideWindow(actionEvent);
                }
                if (i == lgdatas.size() - 1) {
                    errorLable.setText("Invalid e-mail or password...");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image imageView = new Image("/az/com/course/icons/signin.png");
        image.setImage(imageView);
    }

    @FXML
    public void registerUser(ActionEvent actionEvent) {
        utilities.showWindow("/fxml/register.fxml", "Register");
    }

    @FXML
    public void showUsers(ActionEvent actionEvent) {
        utilities.showWindow("/fxml/showTable.fxml", "Table");
        Utilities.hideWindow(actionEvent);
    }
}
