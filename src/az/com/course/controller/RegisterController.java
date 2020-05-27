package az.com.course.controller;

import az.com.course.dto.DictionaryDTO;
import az.com.course.dto.PersonDTO;
import az.com.course.service.PersonService;
import az.com.course.service.PersonServiceInterface;
import az.com.course.utility.Utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    private PersonServiceInterface personService = new PersonService();


    public Button buttonRegister;
    public RadioButton male;
    public RadioButton female;
    @FXML
    private TextField userName;

    @FXML
    private Label errorMessage;

    @FXML
    private Label successMessage;

    @FXML
    private TextField userSurname;

    @FXML
    private TextField userPin;

    @FXML
    private javafx.scene.control.PasswordField userPassword;

    @FXML
    private TextField userEmail;

    @FXML
    private ComboBox<DictionaryDTO> userPosition;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            ObservableList<DictionaryDTO> personTypes = FXCollections.observableArrayList(comboDatas("PersonType"));
            userPosition.setItems(personTypes);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<DictionaryDTO> comboDatas(String key) throws SQLException {
        return personService.comboDatasByKey(key);
    }

    ToggleGroup group;

    public void gender(ActionEvent actionEvent) {
        group = new ToggleGroup();
        male.setToggleGroup(group);
        male.setSelected(true);

        female.setToggleGroup(group);

    }

    @FXML
    public void addUser(ActionEvent actionEvent) {
        String name = userName.getText();
        String surname = userSurname.getText();
        String pin = userPin.getText();
        String password = userPassword.getText();
        String email = userEmail.getText();
        DictionaryDTO userPosition = this.userPosition.getSelectionModel().getSelectedItem();

        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(name);
        personDTO.setSurname(surname);
        personDTO.setPin(pin);
        personDTO.setEmail(email);
        personDTO.setPassword(password);
        personDTO.setUserPosition(userPosition);
        try {
            if (group.getSelectedToggle().equals(male)) {
                String gender = male.getText();
                personDTO.setGender(gender);
            } else {
                String gender = female.getText();
                personDTO.setGender(gender);
            }
        } catch (Exception e) {
            errorMessage.setText("There are blank fields...");
        }

        try {
            personService.addUser(personDTO);
            Utilities.hideWindow(actionEvent);
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            errorMessage.setText("There are blank fields,please enter datas!");
        } catch (SQLException e) {
            e.printStackTrace();
            errorMessage.setText("Invalid field, pin must be 7 symbols...");
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage.setText("There are blank fields...");
        }
    }
}
