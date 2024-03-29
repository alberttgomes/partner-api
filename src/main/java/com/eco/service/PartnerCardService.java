package com.partner.service;

import com.partner.exception.PartnershipMemberNotFoundException;
import com.partner.model.Partnership;
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
