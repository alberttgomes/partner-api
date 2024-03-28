package com.eco.model;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * @author Albert Gomes Cabral
 */
@Entity
public class Benefit implements Serializable {
    public Long getBenefitId() {
        return _benefitId;
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

    public Plan getPlan() {
        return _plan;
    }

    public void setPlan(Plan plan) {
        this._plan = plan;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "_planId")
    private Plan _plan;

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
}
