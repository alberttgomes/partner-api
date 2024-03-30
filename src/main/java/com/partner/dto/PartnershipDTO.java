package com.partner.dto;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Embedded;

/**
 * @author Albert Gomes Cabral
 */
@Data
public class PartnershipDTO {

    @Embedded.Nullable
    public String email;

    @Embedded.Nullable
    public String firstName;

    @Embedded.Nullable
    public String middleName;

    @Embedded.Nullable
    public String lastName;

    @Embedded.Nullable
    public String password;

}
