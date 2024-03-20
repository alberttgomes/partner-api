package com.eco.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * @author Albert Gomes Cabral
 */
@Entity
public class Plan implements Serializable {
    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public long[] getBenefitsPk() {
        return _benefitsPk;
    }

    public void setBenefitsPk(long[] benefitsPk) {
        this._benefitsPk = benefitsPk;
    }

    public Date getDateOfExpiration() {
        return _dateOfExpiration;
    }

    public void setDateOfExpiration(Date dateOfExpiration) {
        this._dateOfExpiration = dateOfExpiration;
    }

    public Date getDateOfStart() {
        return _dateOfStart;
    }

    public void setDateOfStart(Date dateOfStart) {
        this._dateOfStart = dateOfStart;
    }

    public int getPercent() {
        return _percent;
    }

    public void setPercent(int percent) {
        this._percent = percent;
    }

    public String getPlanName() {
        return _planName;
    }

    public void setPlanName(String planName) {
        this._planName = planName;
    }

    public String getPrice() {
        return _price;
    }

    public void setPrice(String price) {
        this._price = price;
    }

    public boolean getStatusPlan() {
        return _statusPlan;
    }

    public void setStatusPlan(boolean statusPlan) {
        this._statusPlan = statusPlan;
    }

    @OneToMany(
            targetEntity = Benefit.class,
            mappedBy = "benefitId",
            fetch = FetchType.LAZY
    )
    private List<Benefit> _benefit;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long planId;
    @Column
    private long[] _benefitsPk;
    @Column(nullable = false)
    private Date _dateOfExpiration;
    @Column(nullable = false)
    private Date _dateOfStart;
    @Column(nullable = false, length = 100)
    private int _percent;
    @Column(nullable = false, length = 45)
    private String _planName;
    @Column(nullable = false, length = 30)
    private String _price;
    @Column(nullable = false)
    private boolean _statusPlan;
}
