package az.com.course.service;

import az.com.course.dao.PersonDao;
import az.com.course.dao.PersonDaoInterface;
import az.com.course.dto.DictionaryDTO;
import az.com.course.dto.LogOnDTO;
import az.com.course.dto.PersonDTO;

import java.sql.SQLException;
import java.util.List;

public class PersonService implements PersonServiceInterface {
    PersonDaoInterface personDao = new PersonDao();

    @Override
    public void addUser(PersonDTO pm) throws Exception {
        personDao.userAdd(pm);
    }

    @Override
    public List<DictionaryDTO> comboDatasByKey(String key) throws SQLException {
        return personDao.comboDatasByKey(key);
    }

    @Override
    public List<LogOnDTO> lgFromBaza() throws SQLException {
        return personDao.lgFromBaza();
    }

    @Override
    public List<PersonDTO> personDTOS() throws SQLException {
        return personDao.PersonDTOS();
    }

    @Override
    public List<PersonDTO> personDTOS(String name, String surname, String email, DictionaryDTO selectedItem) throws SQLException {
        return personDao.PersonDTOS(name, surname, email, selectedItem);
    }

    @Override
    public void editUser(PersonDTO personDTO) throws Exception {
        personDao.editUser(personDTO);
    }

    @Override
    public void deleteUser(int userId) throws SQLException {
        personDao.deleteUser(userId);
    }

    @Override
    public List<PersonDTO> FindByPin(String searchText) throws SQLException {
        return personDao.FindByPin(searchText);
    }

    @Override
    public void DeleteAllDatas() throws SQLException {
        personDao.DeleteAllDatas();
    }

    @Override
    public PersonDTO TakeDataByEmail(String email) throws SQLException {
        return personDao.TakeDataByEmail(email);
    }
}
