package az.com.course.controller;

import az.com.course.service.PersonService;
import az.com.course.service.PersonServiceInterface;
import az.com.course.utility.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class acceptWindowController implements Initializable {

    PersonServiceInterface personService = new PersonService();
    Utilities utilities = new Utilities();
    ShowTableController showTableController = new ShowTableController();
    public ImageView image;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image imageView = new Image("/az/com/course/icons/attention.png");
        image.setImage(imageView);
    }

    public void yesButton(ActionEvent actionEvent) {
        try {
            personService.DeleteAllDatas();
            utilities.showWindow("/fxml/showTable.fxml", "Table");
            Utilities.hideWindow(actionEvent);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void noButton(ActionEvent actionEvent) {
        utilities.showWindow("/fxml/showTable.fxml", "Table");
        Utilities.hideWindow(actionEvent);

    }
}
