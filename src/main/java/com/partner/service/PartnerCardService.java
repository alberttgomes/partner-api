package com.partner.service;

import com.partner.exception.PartnershipMemberNotFoundException;
import com.partner.model.Partnership;
import org.springframework.stereotype.Service;

/**
 * @author Albert Gomes Cabral
 */
@Service("_partnerCardService")
public interface PartnerCardService {
     Object buildPartnershipCard(
             Partnership partnership, Object[] plusInformation, String avatarImagePath,
             String avatarFileName) throws PartnershipMemberNotFoundException;
}
