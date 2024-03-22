package com.eco.platform.manager;

import com.eco.exception.UnableToProcessPlatformManagerException;
import com.eco.model.Plan;
import com.eco.model.PlatformManager;
import com.eco.platform.constants.PlatformManagerMockTest;
import com.eco.service.PlatformManagerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Albert Gomes Cabral
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CreateNewPlanCrudTest {
    @Test
    public void createNewPlanTest() throws UnableToProcessPlatformManagerException {
        PlatformManager platformManager = _createPlatformManagerMock();

        _createNewBenefitMock();

        List<String> benefitNameList = new ArrayList<>();

        benefitNameList.add("materials");

        Date dateOfExpiration = new Date(2025, Calendar.DECEMBER, 1);
        Date dateOfStart = new Date(System.currentTimeMillis());

        // creating new plan using the service layer
        Plan plan = platformManagerService.createNewPlan(
                dateOfExpiration, dateOfStart, 50, "gold",
                "99,99$", true, benefitNameList,
                platformManager.getUserNameAdmin(), platformManager.getPassword());

        // asserting if plan isn't null
        assertNotNull(plan, "Test to createNewPlanTest passed.");
    }

    private void _createNewBenefitMock() {
        Map<String, String[]> resourceMap = new HashMap<>();

        String[] benefitsResource = new String[]{"shirt", "shorts", "hat", "blouse"};

        resourceMap.put("materials", benefitsResource);

        PlatformManager platformManager = _createPlatformManagerMock();

        platformManagerService.createNewBenefit(
                "materialsBenefit", false, resourceMap,
                platformManager.getUserNameAdmin(), platformManager.getPassword());
    }

    private PlatformManager _createPlatformManagerMock() {
        return platformManagerService.createNewPlatformManager(
                PlatformManagerMockTest.EMAIL_MANAGER_MOCK,
                PlatformManagerMockTest.USER_NAME_MANAGER_MOCK,
                PlatformManagerMockTest.PASSWORD_MANAGER_MOCK, true);
    }

    @Autowired
    private PlatformManagerService platformManagerService;
}
