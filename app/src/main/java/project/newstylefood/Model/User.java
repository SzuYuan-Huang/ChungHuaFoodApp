package project.newstylefood.Model;

/**
 * Created by PC on 2017/11/4.
 */

public class User
{
    private String name;
    private String phone;
    private String email;
    private String password;

    public User()
    {

    }

    public User(String name,String phone,String email,String password)
    {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }
}
