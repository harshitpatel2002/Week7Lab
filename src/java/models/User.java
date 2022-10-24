package models;

import java.io.Serializable;

/**
 *
 * @author hsp28
 */
public class User implements Serializable
{
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private Role role;
    
    public User()
    {
        
    }
    
    public User (String email, String firstname, String lastname, String password, Role role)
    {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }
    
    public String toString()
    {
        return "";
    }
}
