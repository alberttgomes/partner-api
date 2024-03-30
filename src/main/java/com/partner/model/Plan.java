package com.partner.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Albert Gomes Cabral
 */
@Entity
public class Plan implements Serializable {
    public List<Benefit> getAvailableBenefitList() {
        List<Benefit> benefitList = new ArrayList<>();

        for (Benefit benefit : _benefitList) {
            for (long benefitIdPk : _benefitPkArray) {
                if (benefit.getBenefitId() == benefitIdPk) {
                    benefitList.add(benefit);
                }
            }
        }
        return benefitList;
    }

    public Long getPlanId() {
        return _planId;
    }

    public long[] getBenefitsPk() {
        return _benefitPkArray;
    }

    public void setBenefitsPk(long[] benefitsPk) {
        this._benefitPkArray = benefitsPk;
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

    public Partnership getPartnership() {
        return _partnership;
    }

    public void setPartnership(Partnership partnership) {
        this._partnership = partnership;
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
            mappedBy = "_plan",
            cascade = CascadeType.ALL
    )
    private List<Benefit> _benefitList;

    @ManyToOne(targetEntity = Partnership.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "_partnershipId")
    private Partnership _partnership;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long _planId;
    @Column
    private long[] _benefitPkArray;
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
