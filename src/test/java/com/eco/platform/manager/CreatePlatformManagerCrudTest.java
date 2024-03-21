package com.eco.platform.manager;

import com.eco.controler.PlatformManagerController;
import com.eco.exception.UnableToProcessPlatformManagerException;
import com.eco.model.PlatformManager;
import com.eco.service.PlatformManagerService;
import com.eco.util.PlatformManagerUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Albert Gomes Cabral
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CreatePlatformManagerCrudTest {
    private static final String _EMAIL_MOCK = "killua@gmail.com";
    private static  final String _USER_NAME_MANAGER_MOCK = "killua";
    private static final String _PASSWORD_MOCK = "familyOfTheKillers";

    @Test
    public void createNewPlatformManagerControllerTest() throws UnableToProcessPlatformManagerException {
        ResponseEntity<PlatformManager> platformManager =
                platformManagerController.postPlatformManagerMapping(
                        _EMAIL_MOCK, _USER_NAME_MANAGER_MOCK, _PASSWORD_MOCK, true);

        // asserting status code of the request
        assertEquals(platformManager.getStatusCode(), HttpStatus.CREATED);

        // asserting if the platform's manager isn't  null
        assertNotNull(platformManager,
                "Test to createNewPlatformManagerControllerTest success.");
    }

    @Test
    public void createNewPlatformManagerLoadPropertiesTest() throws Exception {
        // create the platform's manager using the properties file
        PlatformManager platformManager =
                PlatformManagerUtil.loadPlatformManagerProperties();

        // asserting if the platformManager isn't null
        assertNotNull(platformManager,
                "Test to createNewPlatformManagerLoadPropertiesTest success.");
    }

    @Test
    public void createNewPlatformManagerServiceTest() throws UnableToProcessPlatformManagerException {
        // create the new platform's manager using the mock date
        PlatformManager platformManager = platformManagerService.createNewPlatformManager(
                _EMAIL_MOCK, _USER_NAME_MANAGER_MOCK, _PASSWORD_MOCK, true);

        // asserting if the platformManager isn't null
        assertNotNull(platformManager,
                "Test to createNewPlatformManagerServiceTest success.");
    }

    @Autowired
    private PlatformManagerController platformManagerController;
    @Autowired
    private PlatformManagerService platformManagerService;
}
