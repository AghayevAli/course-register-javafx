package az.com.course.controller;

import az.com.course.dto.PersonDTO;
import az.com.course.utility.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class UserStageController implements Initializable {
    public Label welcome;
    public Button exit;
    public ImageView orientImage;
    public Label name;
    public Label surname;
    public Label email;
    public Label gender;
    public Label position;
    public ImageView image;

    Utilities utilities = new Utilities();

    public void welcome(PersonDTO userdata) {
        welcome.setText("Welcome " + (userdata.getGender().equals("Male") ? "Mr." : "Mrs.") + userdata.getName() + "!");
        name.setText(userdata.getName());
        surname.setText(userdata.getSurname());
        email.setText(userdata.getEmail());
        gender.setText(userdata.getGender());
        position.setText(userdata.getUserPosition().getDicVal());
    }

    public void Exit(ActionEvent actionEvent) {
        utilities.showWindow("/fxml/index.fxml", "Enter");
        Utilities.hideWindow(actionEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("/az/com/course/icons/orient.jpg");
        Image image2 = new Image("/az/com/course/icons/profile.png");
        this.image.setImage(image2);
        orientImage.setImage(image);
    }
}
