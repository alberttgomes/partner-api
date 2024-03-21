package com.eco.dto;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Embedded;

import java.util.Date;

/**
 * @author Albert Gomes Cabral
 */
@Data
public class PlanDto {

    @Embedded.Nullable
    public Date dateOfExpired;

    @Embedded.Nullable
    public Date dateOfStart;

    public int percent;

    @Embedded.Nullable
    public String planName;

    @Embedded.Nullable
    public String price;

    public boolean statusPlan;

}
