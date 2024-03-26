package com.eco.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 * @author Albert Gomes Cabral
 */
@Entity
public class Notify implements Serializable {
    public Partnership getPartnership() {
        return _partnership;
    }

    public void setPartnership(Partnership partnership) {
        this._partnership = partnership;
    }

    public Long getNotifyId() {
        return _notifyId;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String message) {
        this._message = message;
    }

    public Time getSentTime() {
        return _sentTime;
    }

    public void setSentTime(Time sentTime) {
        this._sentTime = sentTime;
    }

    public String getSend() {
        return _send;
    }

    public void setSend(String send) {
        this._send = send;
    }

    public String getReceive() {
        return _receive;
    }

    public void setReceive(String receive) {
        this._receive = receive;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        this._title = title;
    }

    @ManyToOne(targetEntity = Partnership.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "_partnershipId")
    private Partnership _partnership;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long _notifyId;
    @Column
    private String _message;
    // this field must be the unique username
    @Column
    private String _send;
    @Column
    private Time _sentTime;
    // this field must be the unique username
    @Column
    private String _receive;
    @Column
    private String _title;
}
