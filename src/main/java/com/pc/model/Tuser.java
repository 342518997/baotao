package com.pc.model;

/**
 * @author asus
 * 用户实体类
 */
public class Tuser {
    private Integer id;
    private String userName;
    private String password;
    private Integer roleId;
    private String salt;
    private String locaip;

    public Tuser() {

    }


    public String getSalt() {
        return salt;
    }


    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getLocaip() {
        return locaip;
    }

    public void setLocaip(String locaip) {
        this.locaip = locaip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }


}
