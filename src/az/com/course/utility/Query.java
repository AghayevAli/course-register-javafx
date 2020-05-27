package az.com.course.utility;

public enum Query {
    PERSON_ADD("insert into users(id,name,surname,password,pin,email,distriction_id,gender) values(?,?,?,?,?,?,?,?)"),
    COMBO_DATAS("select * from dictionary where dic_key=?"),
    USER_ID("select user_seq.nextval from dual"),
    USER_EDIT("update users set name=?,surname=?,pin=?,email=?,distriction_id=? where id=?"),
    USER_EDITWITHOUTCOMBO("update users set name=?,surname=?,pin=?,email=? where id=?"),
    USER_DELETE("delete from users where id=?"),
    USER_LIST("select U.ID U_ID,U.NAME,U.SURNAME,U.EMAIL,U.PIN,U.GENDER,D.ID D_ID,D.DIC_KEY,D.DIC_VAL from users u join dictionary D on U.DISTRICTION_ID=D.ID"),
    LOG_ON("select * from users"),
    DELETE_ALL_DATAS("delete from users");
    String query;

    Query(String s) {
        this.query = s;
    }

    public String getQuery() {
        return query;
    }
}
