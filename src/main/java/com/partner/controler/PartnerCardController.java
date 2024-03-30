package com.partner.controler;

import com.partner.model.Partnership;
import com.partner.service.PartnerCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Albert Gomes Cabral
 */
@CrossOrigin
@RestController
public class PartnerCardController {
    @Autowired
    public void setPartnerCardService(PartnerCardService partnerCardService) {
        this._partnerCardService = partnerCardService;
    }

    @PostMapping(value = "/platform-partner/create-partner-card/")
    public ResponseEntity<Object> buildPartnerCardMapping(
            @RequestBody @Validated Partnership partnership, Object[] plusInformation,
            String avatarImagePath, String avatarFileName) {

        if (avatarImagePath.isEmpty() && avatarFileName.isEmpty()) {
            System.out.println(
                    "The avatar image path and file name can't to be empty.");

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Object card = _partnerCardService.buildPartnershipCard(
                partnership, plusInformation, avatarImagePath, avatarFileName);

        if (card != null) {
            return new ResponseEntity<>(card, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    };

    private PartnerCardService _partnerCardService;
}
