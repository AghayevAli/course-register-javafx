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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ShowTableController implements Initializable {


    public Button refreshButton;
    public ImageView image;
    PersonServiceInterface personService = new PersonService();
    Utilities utilities = new Utilities();

    public TextField emailSerchField;
    public TextField nameSearchField;
    public TextField surnameSearchField;
    public ComboBox<DictionaryDTO> positionSearchBox;

    @FXML
    private TableView<PersonDTO> table;
    @FXML
    private TableColumn<PersonDTO, Integer> userId;
    @FXML
    private TableColumn<PersonDTO, String> name;
    @FXML
    private TableColumn<PersonDTO, String> surname;
    @FXML
    private TableColumn<PersonDTO, String> pin;
    @FXML
    private TableColumn<PersonDTO, String> email;
    @FXML
    private TableColumn<PersonDTO, String> position;
    @FXML
    private TableColumn<PersonDTO, String> gender;
    @FXML
    private TextField searchField;

    public void advSearch(ActionEvent actionEvent) throws SQLException {
        String name = nameSearchField.getText();
        String surname = surnameSearchField.getText();
        String email = emailSerchField.getText();
        DictionaryDTO selectedItem = positionSearchBox.getSelectionModel().getSelectedItem();

        List<PersonDTO> advSearchResult = personService.personDTOS(name, surname, email, selectedItem);
        tableSetItems(advSearchResult);
    }

    public void editUser(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                PersonDTO selectedItem = table.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    Stage primaryStage = new Stage();
                    Parent root = null;
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/fxml/updateUser.fxml"));
                    try {
                        root = fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    UpdateUserController updateUserController = fxmlLoader.getController();
                    updateUserController.setFields(selectedItem);
                    primaryStage.setTitle("Editor");
                    primaryStage.setScene(new Scene(root, 351, 489));
                    primaryStage.show();
                    Utilities.hideWindow(mouseEvent);
                }
            }
        }


    }

    public void backMenu(ActionEvent actionEvent) {
        utilities.showWindow("/fxml/index.fxml", "Enter");
        Utilities.hideWindow(actionEvent);
    }

    public void refresh(ActionEvent actionEvent) {
        utilities.showWindow("/fxml/showTable.fxml", "Table");
        Utilities.hideWindow(actionEvent);
    }

    @FXML
    public void deleteAllDatas(ActionEvent actionEvent) {
        utilities.showWindow("/fxml/acceptWindow.fxml", "ATTENTION!");
        Utilities.hideWindow(actionEvent);
    }

    public void searchByPIn(KeyEvent keyEvent) {
        String searchText = searchField.getText();
        try {
            List<PersonDTO> users = personService.FindByPin(searchText);
            tableSetItems(users);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        userId.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        pin.setCellValueFactory(new PropertyValueFactory<>("pin"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        position.setCellValueFactory(new PropertyValueFactory<>("userPosition"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));

        Image imageView = new Image("/az/com/course/icons/search.png");
        image.setImage(imageView);


        try {
            positionSearchBox.setItems(FXCollections.observableArrayList(usersPositions()));
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            tableSetItems(personDTOS());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DictionaryDTO> usersPositions() throws SQLException {
        List<DictionaryDTO> personType = personService.comboDatasByKey("PersonType");
        return personType;
    }

    public void tableSetItems(List<PersonDTO> users) {
        try {
            ObservableList<PersonDTO> personDTOS = FXCollections.observableArrayList(users);
            table.setItems(personDTOS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PersonDTO> personDTOS() throws SQLException {
        return personService.personDTOS();
    }
}
