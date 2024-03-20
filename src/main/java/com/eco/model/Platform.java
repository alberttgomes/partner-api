package com.eco.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;

@Entity
public class Platform implements Serializable {
    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public Date getPlatformLatestUpdateDate() {
        return platformLatestUpdateDate;
    }

    public void setPlatformLatestUpdateDate(Date platformLatestUpdateDate) {
        this.platformLatestUpdateDate = platformLatestUpdateDate;
    }

    public String getPlatformProperties() {
        return platformProperties;
    }

    public void setPlatformProperties(String platformProperties) {
        this.platformProperties = platformProperties;
    }

    public float getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(float platformVersion) {
        this.platformVersion = platformVersion;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long platformId;
    @Column(nullable = false, length = 30)
    private String platformName;
    @Column
    private Date platformLatestUpdateDate;
    @Column(nullable = false, columnDefinition="CLOB NOT NULL")
    private String platformProperties;
    @Column(nullable = false)
    private float platformVersion;
}
