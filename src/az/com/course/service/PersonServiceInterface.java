package az.com.course.service;

import az.com.course.dto.DictionaryDTO;
import az.com.course.dto.LogOnDTO;
import az.com.course.dto.PersonDTO;

import java.sql.SQLException;
import java.util.List;

public interface PersonServiceInterface {
    public void addUser(PersonDTO pm) throws Exception;

    List<DictionaryDTO> comboDatasByKey(String key) throws SQLException;

    List<LogOnDTO> lgFromBaza() throws SQLException;

    List<PersonDTO> personDTOS() throws SQLException;

    List<PersonDTO> personDTOS(String name, String surname, String email, DictionaryDTO selectedItem) throws SQLException;

    void editUser(PersonDTO personDTO) throws Exception;

    void deleteUser(int userId) throws SQLException;

    List<PersonDTO> FindByPin(String searchText) throws SQLException;

    void DeleteAllDatas() throws SQLException;

    PersonDTO TakeDataByEmail(String email) throws SQLException;
}
