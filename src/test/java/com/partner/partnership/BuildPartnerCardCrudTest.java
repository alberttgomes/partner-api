package com.partner.partnership;

import com.partner.model.Address;
import com.partner.model.Partnership;
import com.partner.service.PartnerCardService;
import com.partner.service.PartnershipService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Albert Gomes Cabral
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BuildPartnerCardCrudTest {
    @Test
    public void buildPartnerCardCrudTest() {
        Object[] plusInformationRule = _getObjects();

        Object partnerCardObject = _partnerCardService.buildPartnershipCard(
                _createPartnershipMock(), plusInformationRule,
                "home/me/Downloads/", "myPicture.png");

        // asserting if the partner's card wasn't null, if not, was created with success.
        assertNotNull(partnerCardObject, "Test buildPartnerCardCrudTest passed.");
    }

    private Address _createPartnershipAddressMock() {
        Address address = new Address();

        address.setCountry("Brazil");
        address.setCity("Olinda");
        address.setDistrict("JD.BRAZIL-1");
        address.setNumber(0);
        address.setStreet("Rio de Janeiro Street");

        return address;
    }

    private Partnership _createPartnershipMock() {
        return _partnershipService.createPartnershipMember(
                28, "19/09/1995", "albert@entropy.com", "Albert",
                "Gomes", "Cabral", "gold", "MyPassword",
                "(81) 985563743", 1, _createPartnershipAddressMock());
    }

    private static Object[] _getObjects() {
        List<Object> plusInformationRulesList = new ArrayList<>();

        Map<String, Object> itemMap = new HashMap<>(3);

        itemMap.put("birth-day-info", true);

        plusInformationRulesList.add(itemMap);

        itemMap.put("active-date-info", true);

        plusInformationRulesList.add(itemMap);

        itemMap.put("remove-plan-name-info", false);

        plusInformationRulesList.add(itemMap);

        return new List[]{plusInformationRulesList};
    }

    @Autowired
    private PartnerCardService _partnerCardService;
    @Autowired
    private PartnershipService _partnershipService;
}
