package com.eco.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "benefitName")
    }
)
public class Benefit implements Serializable {
    public Long getBenefitId() {
        return _benefitId;
    }

    public void setBenefitId(Long benefitId) {
        this._benefitId = benefitId;
    }

    public boolean getBenefitExpired() {
        return _benefitExpired;
    }

    public void setBenefitExpired(boolean benefitExpired) {
        this._benefitExpired = benefitExpired;
    }

    public String getBenefitName() {
        return _benefitName;
    }

    public void setBenefitName(String benefitName) {
        this._benefitName = benefitName;
    }

    public Object getBenefitResource() {
        return _benefitResource;
    }

    public void setBenefitResource(Object benefitResource) {
        this._benefitResource = benefitResource;
    }

    public long getPlanPk() {
        return _planPk;
    }

    public void setPlanPk(long planPk) {
        this._planPk = planPk;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long _benefitId;
    @Column(nullable = false)
    private boolean _benefitExpired;
    @Column(nullable = false, length = 30, unique = true)
    private String _benefitName;
    @Column(nullable = false)
    private Object _benefitResource;
    @Column(nullable = false)
    private long _planPk;
}
