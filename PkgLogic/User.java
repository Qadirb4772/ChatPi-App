package PkgLogic;

public class User{
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phoneNumber;
    private char[] password;

    public User(String firstName, String lastName, String username, String email, String phoneNumber, char[] password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public int getId(){
        return this.id;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public char[] getPassword(){
        return this.password;
    }

    public void setPassword(char[] password){
        this.password = password;
    }
}