package com.partner.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Albert Gomes Cabral
 */
@Entity
public class Partnership implements Serializable {
    public List<Address> getAddress() {
        return _addressList;
    }

    public long getPartnershipId() {
        return _partnershipId;
    }

    public void setPartnershipId(long partnershipId) {
        this._partnershipId = partnershipId;
    }

    public int getAge() {
        return _age;
    }

    public void setAge(int age) {
        this._age = age;
    }

    public String getBirthDate() {
        return _birthDate;
    }

    public void setBirthDate(String birthDate) {
        this._birthDate = birthDate;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String email) {
        this._email = email;
    }

    public String getPlanName() {
        return _planName;
    }

    public void setPlanName(String category) {
        this._planName = category;
    }

    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(String firstName) {
        this._firstName = firstName;
    }

    public String getLastName() {
        return _lastName;
    }

    public void setLastName(String lastName) {
        this._lastName = lastName;
    }

    public String getMiddleName() {
        return _middleName;
    }

    public void setMiddleName(String middleName) {
        this._middleName = middleName;
    }

    public Notify getNotifyById(long notifyId) {
        Notify result = null;

        for (Notify notify : _notifyList) {
            if (notify.getNotifyId() == notifyId) {
                result = notify;

                break;
            }
        }
        return result;
    }

    public List<Notify> getNotifyList() {
        return _notifyList;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        this._password = password;
    }

    public Plan getPlan() {
        return _plan;
    }

    public String getPhone() {
        return _phone;
    }

    public void setPhone(String phone) {
        this._phone = phone;
    }

    public int getStatus() {
        return _status;
    }

    public void setStatus(int status) {
        this._status = status;
    }

    @OneToMany(
            targetEntity = Address.class,
            mappedBy = "_partnership",
            cascade = CascadeType.ALL
    )
    private List<Address> _addressList;

    @OneToMany(
            targetEntity = Notify.class,
            mappedBy = "_partnership",
            cascade = CascadeType.ALL
    )
    private List<Notify> _notifyList;

    @OneToOne(
            targetEntity = Plan.class,
            mappedBy = "_planId",
            fetch = FetchType.LAZY
    )
    private Plan _plan;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long _partnershipId;
    @Column
    private int _age;
    @Column
    private String _birthDate;
    @Column(nullable = false, length = 40)
    private String _email;
    @Column
    private String _planName;
    @Column
    private String _firstName;
    @Column
    private String _lastName;
    @Column
    private String _middleName;
    @Column
    private String _password;
    @Column
    private String _phone;
    @Column
    private int _status;
}
