package com.partner.dto;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Embedded;

/**
 * @author Albert Gomes Cabral
 */
@Data
public class PlatformManagerDTO {
    @Embedded.Nullable
    public String email;

    public boolean hasPermission;

    @Embedded.Nullable
    public String userName;

    @Embedded.Nullable
    public String password;
}
