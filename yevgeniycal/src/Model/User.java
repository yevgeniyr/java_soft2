/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author yevgeniy
 */
public class User {
    String userName;
    int userId;

    public User(String userName, int userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }
    
    public String getUserIdString() {
        return Integer.toString(userId);
    }
    

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     *
     * @param userName
     * @param userId
     */

    
}
