package com.eco.service;

import com.eco.exception.PartnershipMemberNotFoundException;
import com.eco.model.Partnership;
import org.springframework.stereotype.Service;

/**
 * @author Albert Gomes Cabral
 */
@Service
public interface PartnerCardService {
    public Object buildPartnershipCard(
            Partnership partnership, Object[] rules, String avatarImagePath, String avatarFileName)
        throws PartnershipMemberNotFoundException;
}
