package az.com.course.dao;

import az.com.course.dbConfig.DbConfig;
import az.com.course.dto.DictionaryDTO;
import az.com.course.dto.LogOnDTO;
import az.com.course.dto.PersonDTO;
import az.com.course.utility.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PersonDao implements PersonDaoInterface {

    @Override
    public void userAdd(PersonDTO pm) throws Exception {
        Connection connection = DbConfig.open();
        PreparedStatement pstid = connection.prepareStatement(Query.USER_ID.getQuery());
        ResultSet resultId = pstid.executeQuery();
        int id = -1;
        if (resultId.next()) {
            id = resultId.getInt(1);
        }
        if (id != -1) {
            PreparedStatement pst = connection.prepareStatement(Query.PERSON_ADD.getQuery());
            pst.setInt(1, id);
            pst.setString(2, pm.getName());
            pst.setString(3, pm.getSurname());
            pst.setString(4, pm.getPassword());
            pst.setString(5, pm.getPin());
            pst.setString(6, pm.getEmail());
            DictionaryDTO userPosition = pm.getUserPosition();
            pst.setInt(7, userPosition.getId());
            pst.setString(8, pm.getGender());
            pst.execute();
        }
    }

    @Override
    public List<DictionaryDTO> comboDatasByKey(String key) throws SQLException {

        List<DictionaryDTO> datas = new ArrayList<>();

        Connection connect = DbConfig.open();
        PreparedStatement pst = connect.prepareStatement(Query.COMBO_DATAS.getQuery());
        pst.setString(1, key);
        pst.execute();

        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            DictionaryDTO dictionaryDTO = new DictionaryDTO();
            dictionaryDTO.setId(rs.getInt(1));
            dictionaryDTO.setDescription(rs.getString(4));
            dictionaryDTO.setDicKey(rs.getString(2));
            dictionaryDTO.setDicVal(rs.getString(3));
            datas.add(dictionaryDTO);

        }
        return datas;
    }

    @Override
    public List<LogOnDTO> lgFromBaza() throws SQLException {
        List<LogOnDTO> lgdatas = new ArrayList<>();

        Connection connection = DbConfig.open();
        PreparedStatement pst = connection.prepareStatement(Query.LOG_ON.getQuery());
        ResultSet resultSet = pst.executeQuery();
        while (resultSet.next()) {
            LogOnDTO lgDTO = new LogOnDTO();
            lgDTO.setId(resultSet.getInt(1));
            lgDTO.setName(resultSet.getString(2));
            lgDTO.setEmail(resultSet.getString(6));
            lgDTO.setPassword(resultSet.getString(4));
            lgDTO.setGender(resultSet.getString(9));
            lgdatas.add(lgDTO);
        }
        return lgdatas;
    }

    @Override
    public List<PersonDTO> PersonDTOS() throws SQLException {
        Connection connection = DbConfig.open();
        PreparedStatement pst = connection.prepareStatement(Query.USER_LIST.getQuery());
        return datas(pst);
    }

    private List<PersonDTO> datas(PreparedStatement pst) throws SQLException {
        List<PersonDTO> users = new ArrayList<>();
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            PersonDTO personDTO = new PersonDTO();
            personDTO.setId(rs.getInt(1));
            personDTO.setName(rs.getString(2));
            personDTO.setSurname(rs.getString(3));
            personDTO.setEmail(rs.getString(4));
            personDTO.setPin(rs.getString(5));
            personDTO.setGender(rs.getString(6));
            DictionaryDTO dictionaryDTO = new DictionaryDTO();
            personDTO.setUserPosition(dictionaryDTO);
            dictionaryDTO.setDicVal(rs.getString(9));
            users.add(personDTO);
        }
        return users;
    }

    @Override
    public void editUser(PersonDTO personDTO) throws Exception {
        Connection connection = DbConfig.open();
        if (personDTO.getUserPosition().getId() != null) {
            PreparedStatement preparedStatement = connection.prepareStatement(Query.USER_EDIT.getQuery());
            preparedStatement.setString(1, personDTO.getName());
            preparedStatement.setString(2, personDTO.getSurname());
            preparedStatement.setString(3, personDTO.getPin());
            preparedStatement.setString(4, personDTO.getEmail());
            DictionaryDTO dicdt = personDTO.getUserPosition();
            preparedStatement.setInt(5, dicdt.getId());
            preparedStatement.setInt(6, personDTO.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } else {
            PreparedStatement preparedStatement = connection.prepareStatement(Query.USER_EDITWITHOUTCOMBO.getQuery());
            preparedStatement.setString(1, personDTO.getName());
            preparedStatement.setString(2, personDTO.getSurname());
            preparedStatement.setString(3, personDTO.getPin());
            preparedStatement.setString(4, personDTO.getEmail());
            preparedStatement.setInt(5, personDTO.getId());
            preparedStatement.execute();
            preparedStatement.close();
        }
    }

    @Override
    public void deleteUser(int userId) throws SQLException {
        Connection con = DbConfig.open();
        PreparedStatement pst = con.prepareStatement(Query.USER_DELETE.getQuery());
        pst.setInt(1, userId);
        pst.execute();
        pst.close();
    }

    @Override
    public List<PersonDTO> FindByPin(String searchText) throws SQLException {
        List<PersonDao> datas = new ArrayList<>();
        Connection connection = DbConfig.open();
        String sql = Query.USER_LIST.getQuery();
        sql += " where u.pin like '%" + searchText + "%'";
        PreparedStatement pst = connection.prepareStatement(sql);

        return datas(pst);
    }

    @Override
    public void DeleteAllDatas() throws SQLException {
        Connection connection = DbConfig.open();
        PreparedStatement pst = connection.prepareStatement(Query.DELETE_ALL_DATAS.getQuery());
        pst.execute();
        pst.close();
    }

    @Override
    public List<PersonDTO> PersonDTOS(String name, String surname, String email, DictionaryDTO selectedItem) throws SQLException {
        Connection connection = DbConfig.open();
        String sql = Query.USER_LIST.getQuery();
        sql += " where 1=1";
        if (name != null && !name.trim().isEmpty())
            sql += " and u.name like '%" + name + "%'";
        if (surname != null && !surname.trim().isEmpty())
            sql += " and u.surname like '%" + surname + "%'";
        if (email != null && !email.trim().isEmpty())
            sql += " and u.email like '%" + email + "%'";
        if (selectedItem != null)
            sql += " and U.DISTRICTION_ID = " + selectedItem.getId();
        PreparedStatement pst = connection.prepareStatement(sql);
        return datas(pst);
    }

    @Override
    public PersonDTO TakeDataByEmail(String email) throws SQLException {
        Connection connection = DbConfig.open();
        String sql = Query.USER_LIST.getQuery();
        sql += " where u.email = '" + email + "'";
        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet resultSet = pst.executeQuery();
        PersonDTO userByMail = new PersonDTO();
        while (resultSet.next()) {
            userByMail.setName(resultSet.getString(2));
            userByMail.setSurname(resultSet.getString(3));
            userByMail.setEmail(resultSet.getString(4));
            userByMail.setGender(resultSet.getString(6));
            DictionaryDTO dictionaryDTO = new DictionaryDTO();
            userByMail.setUserPosition(dictionaryDTO);
            dictionaryDTO.setDicVal(resultSet.getString(9));
        }
        return userByMail;
    }
}
