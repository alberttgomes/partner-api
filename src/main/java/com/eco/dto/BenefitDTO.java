package com.eco.dto;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Embedded;

/**
 * @author Albert Gomes Cabral
 */
@Data
public class BenefitDTO {

    public boolean benefitExpired;

    @Embedded.Nullable
    public String benefitName;

    @Embedded.Nullable
    public Object benefitResource;

}
