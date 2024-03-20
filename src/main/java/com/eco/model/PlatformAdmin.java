package com.eco.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "userNameAdmin"," email"
                })
        }
)
public class PlatformAdmin implements Serializable {
    public Long getPlatformAdminId() {
        return platformAdminId;
    }

    public void setPlatformAdminId(Long platformAdminId) {
        this.platformAdminId = platformAdminId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    
    public PlatformAdmin findByLogin(String userNameAdmin, String password) {
        if (_userNameAdmin.equals(userNameAdmin) && _password.equals(password)) {
            return this;
        }
        return null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long platformAdminId;
    @Column(nullable = false, length = 50)
    private String email;
    @Column(nullable = false)
    private boolean _hasPermission;
    @Column(nullable = false, length = 15)
    private String _password;
    @Column(nullable = false, length = 15)
    private String _userNameAdmin;
}
