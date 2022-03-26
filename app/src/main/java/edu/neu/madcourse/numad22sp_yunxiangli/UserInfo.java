package edu.neu.madcourse.numad22sp_yunxiangli;

/**
 * This class represents each user's information.
 */
public class UserInfo
{

    // Store the user's name in string form.
    private String username;

    // This is required by ProGuard.
    public UserInfo(){}

    // One argument constructor.
    public UserInfo(String username)
    {
        this.username = username;
    }

    // Getter function.
    public String getUsername()
    {
        return this.username;
    }
}
