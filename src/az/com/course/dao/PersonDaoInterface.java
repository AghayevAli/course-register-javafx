package az.com.course.dao;

import az.com.course.dto.DictionaryDTO;
import az.com.course.dto.LogOnDTO;
import az.com.course.dto.PersonDTO;

import java.sql.SQLException;
import java.util.List;

public interface PersonDaoInterface {
    public void userAdd(PersonDTO pm) throws Exception;

    List<DictionaryDTO> comboDatasByKey(String key) throws SQLException;

    List<LogOnDTO> lgFromBaza() throws SQLException;


    List<PersonDTO> PersonDTOS() throws SQLException;

    void editUser(PersonDTO personDTO) throws SQLException, Exception;

    void deleteUser(int userId) throws SQLException;

    List<PersonDTO> FindByPin(String searchText) throws SQLException;

    void DeleteAllDatas() throws SQLException;

    List<PersonDTO> PersonDTOS(String name, String surname, String email, DictionaryDTO selectedItem) throws SQLException;

    PersonDTO TakeDataByEmail(String email) throws SQLException;
}
