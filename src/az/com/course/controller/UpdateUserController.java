package az.com.course.controller;

import az.com.course.dto.DictionaryDTO;
import az.com.course.dto.PersonDTO;
import az.com.course.service.PersonService;
import az.com.course.service.PersonServiceInterface;
import az.com.course.utility.Utilities;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.rmi.CORBA.Util;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateUserController implements Initializable {

    public Label errorMessage;
    @FXML
    private TextField userName;

    @FXML
    private TextField userSurname;

    @FXML
    private TextField userPin;

    @FXML
    private TextField userEmail;

    @FXML
    private ComboBox<DictionaryDTO> userPosition;

    private int id;

    private PersonServiceInterface personService = new PersonService();
    private Utilities utilities = new Utilities();

    public void cancelChange(ActionEvent actionEvent) {
        Stage primaryStage = new Stage();
        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation((getClass().getResource("/fxml/showTable.fxml")));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Table");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }


    public List<DictionaryDTO> comboDatas(String key) throws SQLException {
        return personService.comboDatasByKey(key);
    }

    public void setFields(PersonDTO fields) {
        id = fields.getId();
        userName.setText(fields.getName());
        userSurname.setText(fields.getSurname());
        userPin.setText(fields.getPin());
        userEmail.setText(fields.getEmail());
        try {
            userPosition.setItems(FXCollections.observableArrayList(comboDatas("PersonType")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userPosition.getSelectionModel().select(fields.getUserPosition());
    }

    public void editUser(ActionEvent actionEvent) {
        String name = userName.getText();
        String surname = userSurname.getText();
        String pin = userPin.getText();
        String email = userEmail.getText();
        DictionaryDTO userPosition = this.userPosition.getSelectionModel().getSelectedItem();


        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(name);
        personDTO.setSurname(surname);
        personDTO.setPin(pin);
        personDTO.setEmail(email);
        personDTO.setUserPosition(userPosition);
        personDTO.setId(id);

        try {
            personService.editUser(personDTO);
            utilities.showWindow("/fxml/showTable.fxml", "Table");
            Utilities.hideWindow(actionEvent);
        } catch (SQLException e) {
            e.printStackTrace();
            errorMessage.setText("Invalid field, pin must be 7 symbols...");
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage.setText("There are blank fields...");
        }
    }

    public void deleteUser(ActionEvent actionEvent) {
        int userId = this.id;
        try {
            personService.deleteUser(userId);
            utilities.showWindow("/fxml/showTable.fxml", "Table");
            Utilities.hideWindow(actionEvent);
        } catch (SQLException e) {

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
