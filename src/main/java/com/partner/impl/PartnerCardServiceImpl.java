package com.partner.impl;

import com.partner.exception.PartnershipMemberNotFoundException;
import com.partner.model.Partnership;
import com.partner.model.Plan;
import com.partner.service.PartnerCardService;
import com.partner.service.PartnershipService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

/**
 * @author Albert Gomes Cabral
 */
@Component
public class PartnerCardServiceImpl implements PartnerCardService {
    private static final String _WITHOUT_CUSTOMIZATION = "partner's card haven't customization";

    @Override
    public Object buildPartnershipCard(
            Partnership partnership, Object[] plusInformationRule, String avatarImagePath,
            String avatarFileName) throws PartnershipMemberNotFoundException {

        try {
            Plan partnershipPlan = partnership.getPlan();

            if (!partnershipPlan.getStatusPlan()) {
                throw new RuntimeException(
                        "The plan name " + partnershipPlan.getPlanName() + " is expired.");
            }

            String[] keyArray = new String[] {
                    "fullName", "avatarImage", "categoryPlan", "plusInformation"};

            String fullName = _validSizeNameCard(partnership.getFirstName(),
                    partnership.getMiddleName(), partnership.getLastName());

            Object[] customFieldsCard =
                    _validateRulesBeforeCreateCardItems(plusInformationRule);

            File avatarFile = _getAvatarFileFromDirectory(
                    avatarImagePath, avatarFileName);

            Object[] valueArray = new Object[] {
                    fullName, avatarFile, partnershipPlan.getPlanName(),
                    customFieldsCard.length > 0 ? customFieldsCard : _WITHOUT_CUSTOMIZATION};

            List<Map<String, Object>> partnerCardMapList =
                    _buildItemCardMapList(keyArray, valueArray);

            if (!partnerCardMapList.isEmpty()) {
                return partnerCardMapList;
            }
        }
        catch (PartnershipMemberNotFoundException exception) {
            throw new PartnershipMemberNotFoundException(
                    "Unable to find the partner with the primary key " +
                            partnership.getPartnershipId(), exception);
        }

        return null;
    }

    private List<Map<String, Object>> _buildItemCardMapList(
            String[] keyArray, Object[] valueArray) {

        Map<String, Object> itemMap = new HashMap<>();

        List<Map<String, Object>> objectMapList =
                new ArrayList<>(5);

        for (String key : keyArray) {
            for (Object value : valueArray) {
                itemMap.put(key, value);

                objectMapList.add(itemMap);
            }
        }

        return objectMapList;
    }

    private File _getAvatarFileFromDirectory(String path, String fileName) {
        try {
            File result = new File(path, fileName);

            // check if this file exists before return
            if (result.exists() && result.isFile()) {
                return result;
            }
        }
        catch (Exception exception) {
            System.out.println("Unable to get avatar image from the path "
                    + path + exception);
        }

        return null;
    }

    private String _validSizeNameCard(
            String firstName, String middleName, String lastName) {
        String validName = "";
        String whiteSpace = " ";
        String dot = ".";

        String fullName = firstName + whiteSpace + middleName + whiteSpace + lastName;

        char[] fullNameCharArray = fullName.toCharArray();

        int limit = 35;

        Set<String> characterSet = new HashSet<>();

        boolean validateIfRulesForSizeNameFinish = false;

        for (char character : fullNameCharArray) {
            characterSet.add(String.valueOf(character));

            if (characterSet.size() >= limit) {
                String partialResult;

                for (char middleFirstCharacter : middleName.toCharArray()) {
                    partialResult = firstName + whiteSpace + middleFirstCharacter +
                            dot + whiteSpace + lastName;

                    if (partialResult.indexOf(characterSet.size() + 1) > limit) {
                        for (char lastFirstCharacter : lastName.toCharArray()) {
                            validName = firstName + whiteSpace + middleFirstCharacter +
                                    dot + whiteSpace + lastFirstCharacter + dot;

                            validateIfRulesForSizeNameFinish = true;

                            break;
                        }
                    }
                    else {
                        validName = partialResult;

                        validateIfRulesForSizeNameFinish = true;

                        break;
                    }

                    if (validateIfRulesForSizeNameFinish) {
                        System.out.println(
                                "builder partner's applying the size rules "
                                        + validName);

                        break;
                    }
                }
            }
            else {
                validName = firstName + whiteSpace + middleName +
                        whiteSpace + lastName;

                System.out.println(
                        "builder partner's name applying full size name" +
                                validName);
            }
        }

        return validName;
    }

    private Object[] _validateRulesBeforeCreateCardItems(Object[] rules) {
        List<Object> existentRulesArrayList = new ArrayList<>();

        for (Object object : rules) {
            Map<String, Object> objectMap = (Map<String, Object>) object;

            if (objectMap.get("birth-day-info") != null) {
                boolean ruleActive = (boolean) objectMap.get("birth-day-info");

                if (ruleActive) {
                    existentRulesArrayList.add(objectMap);
                }
            }
            else if (objectMap.get("active-date-info") != null) {
                 boolean ruleActive = (boolean) objectMap.get("active-date-info");

                if (ruleActive) {
                    existentRulesArrayList.add(objectMap);
                }
            }
            else if (objectMap.get("remove-plan-name-info") != null) {
                boolean ruleActive = (boolean) objectMap.get("remove-plan-name-info");

                if (ruleActive) {
                    existentRulesArrayList.add(objectMap);
                }
            }
        }

        return new List[]{existentRulesArrayList};
    };

    @Autowired
    public void serPartnershipService(PartnershipService partnershipService) {
        this._partnershipService = partnershipService;
    }

    private PartnershipService _partnershipService;
}
