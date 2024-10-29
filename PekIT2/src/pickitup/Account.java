/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pickitup;

/**
 *
 * @author Gio Turtal and Jose Laserna
 */

public class Account
{

    public String username;
    public String password;
    public String type;

    public Account(String username, String password, String type)
    {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getType()
    {
        return type;
    }
}