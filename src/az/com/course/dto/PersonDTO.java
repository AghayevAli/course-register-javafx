package az.com.course.dto;

public class PersonDTO {

    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String pin;
    private String password;
    private String gender;
    private DictionaryDTO userPosition;


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", pin='" + pin + '\'' +
                ", password='" + password + '\'' +
                ", userPosition=" + userPosition +
                '}';
    }

    public void setPin(String pin) {
        this.pin = pin;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DictionaryDTO getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(DictionaryDTO userPosition) {
        this.userPosition = userPosition;
    }
}
