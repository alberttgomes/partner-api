package com.partner.controler;

import com.partner.dto.BenefitDTO;
import com.partner.dto.PlatformManagerDTO;
import com.partner.exception.UnableToProcessPlatformManagerException;
import com.partner.model.Benefit;
import com.partner.model.Plan;
import com.partner.model.PlatformManager;
import com.partner.service.PlatformManagerService;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Queue;

/**
 * @author Albert Gomes Cabral
 */
@RestController
@CrossOrigin({"http://localhost:8080", "http://localhost:8082"})
public class PlatformManagerController {
    @DeleteMapping(value = "/partner-platform/${platformManagerId}/delete-benefit")
    public ResponseEntity.BodyBuilder deleteBenefitMapping(
            long benefitId, String userNameManager, String password) {

        _platformManagerService.deleteBenefit(userNameManager, password, benefitId);

        return ResponseEntity.status(HttpStatus.OK);
    }

    @DeleteMapping(value = "/partner-platform/${platformManagerId}/delete-plan")
    public ResponseEntity.BodyBuilder deletePlanMapping(
            long planId, String userNameManager, String password) {

        _platformManagerService.deletePlan(planId, userNameManager, password);

        return ResponseEntity.status(HttpStatus.OK);
    }

    @GetMapping(value = "/partner-platform/get-manager")
    public ResponseEntity<PlatformManager> getPlatformManagerMapping(
            String userNameManager, String password) {

        PlatformManager platformManager =
                _platformManagerService.getPlatformAdmin(userNameManager, password);

        if (platformManager.getPlatformAdminId() > 0) {
            return new ResponseEntity<>(platformManager, HttpStatus.FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/partner-platform/get-manager-dto")
    public ResponseEntity<PlatformManagerDTO> getPlatformManagerDtoMapping(
            @Validated @RequestBody PlatformManagerDTO platformManagerDTO) {

        PlatformManager platformManager =
                _modelMapper.map(platformManagerDTO, PlatformManager.class);

        platformManager = _platformManagerService.getPlatformAdmin(
                platformManager.getUserNameAdmin(), platformManager.getPassword());

        if (platformManager.getPlatformAdminId() > 0) {
            platformManagerDTO = _modelMapper.map(platformManager, PlatformManagerDTO.class);

            return new ResponseEntity<>(platformManagerDTO, HttpStatus.FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/partner-platform/create-new-manager")
    public ResponseEntity<PlatformManager> postPlatformManagerMapping(
            String email, String userNameManager, String password, boolean hasPermission)
        throws UnableToProcessPlatformManagerException {

        PlatformManager platformManager = _platformManagerService.createNewPlatformManager(
                email, userNameManager, password, hasPermission);

        if (platformManager != null) {
            return new ResponseEntity<>(platformManager, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/partner-platform/${platformManagerId}/create-new-benefit")
    public ResponseEntity<Benefit> postNewBenefitMapping(
            String benefitName, boolean benefitExpired, Object benefitResource,
            String userName, String password) {

        Benefit benefit = _platformManagerService.createNewBenefit(
                benefitName, benefitExpired, benefitResource, userName, password);

        if (!benefit.getBenefitExpired()) {
            return new ResponseEntity<>(benefit, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/partner-platform/${platformManagerId}/create-new-benefit-dto")
    public ResponseEntity<BenefitDTO> postNewBenefitDtoMapping(
            @Validated @RequestBody BenefitDTO benefitDTO, String userName, String password) {

        Benefit benefit = _modelMapper.map(benefitDTO, Benefit.class);

        benefit = _platformManagerService.createNewBenefit(
                benefit.getBenefitName(), benefit.getBenefitExpired(),
                benefit.getBenefitResource(), userName, password);

        if (!benefit.getBenefitExpired()) {
            benefitDTO = _modelMapper.map(benefit, BenefitDTO.class);

            return new ResponseEntity<>(benefitDTO ,HttpStatus.CREATED);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/partner-platform/${platformManagerId}/")
    public ResponseEntity<Plan> postNewPlanMapping(
            Date dateOfExpiration, Date dateOfStart, int percent, String planName,
            String price, boolean statusPlan, String userName, String password,
            List<String> benefitNameList) {

        Plan plan = _platformManagerService.createNewPlan(
                dateOfExpiration, dateOfStart, percent, planName,
                price, statusPlan, benefitNameList, userName, password);

        if (plan.getStatusPlan()) {
            return new ResponseEntity<>(plan, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/partner-platform/${platformManagerId}/update-plan")
    public ResponseEntity<Plan> putPlanDtoMapping(
            Date dateOfExpiration, Date dateOfStart, int percent, String planName,
            String price, boolean statusPlan,long planId, long[] benefitPkArray,
            String userNameManager, String password) {

        Plan updatedPlan = new Plan();

        updatedPlan.setDateOfExpiration((java.sql.Date) dateOfExpiration);
        updatedPlan.setDateOfStart((java.sql.Date) dateOfStart);
        updatedPlan.setPercent(percent);
        updatedPlan.setPlanName(planName);
        updatedPlan.setPrice(price);
        updatedPlan.setBenefitsPk(benefitPkArray);
        updatedPlan.setStatusPlan(statusPlan);

        updatedPlan = _platformManagerService.updatePlan(
                updatedPlan, planId, userNameManager, password);

        if (updatedPlan.getStatusPlan()) {
            return new ResponseEntity<>(updatedPlan, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Autowired
    public void setPlatformAdminService(PlatformManagerService platformManagerService) {
        this._platformManagerService = platformManagerService;
    }

    private final ModelMapper _modelMapper = new ModelMapper();
    private PlatformManagerService _platformManagerService;
}
