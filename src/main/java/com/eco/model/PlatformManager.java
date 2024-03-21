package com.eco.model;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * @author Albert Gomes Cabral
 */
@Entity
public class PlatformManager implements Serializable {
    public Long getPlatformAdminId() {
        return _platformAdminId;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public boolean getHasPermission() {
        return _hasPermission;
    }

    public void setHasPermission(boolean hasPermission) {
        this._hasPermission = hasPermission;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        this._password = password;
    }

    public String getUserNameAdmin() {
        return _userNameAdmin;
    }

    public void setUserNameAdmin(String userNameAdmin) {
        this._userNameAdmin = userNameAdmin;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long _platformAdminId;
    @Column(nullable = false, length = 50, unique = true)
    private String _email;
    @Column(nullable = false)
    private boolean _hasPermission;
    @Column(nullable = false, length = 15)
    private String _password;
    @Column(nullable = false, length = 15, unique = true)
    private String _userNameAdmin;
}
