package com.eco.controler;

import com.eco.dto.BenefitDTO;
import com.eco.dto.PlatformManagerDTO;
import com.eco.exception.PlatformAdminNotFoundException;
import com.eco.model.Benefit;
import com.eco.model.Plan;
import com.eco.model.PlatformManager;
import com.eco.service.PlatformAdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author Albert Gomes Cabral
 */
@RestController
@CrossOrigin({"http://localhost:8080", "http://localhost:8082"})
public class PlatformAdminController {
    @PostMapping(value = "/partner-platform/${platformManagerId}/")
    public ResponseEntity<Plan> postNewPlanMapping(
            Date dateOfExpiration, Date dateOfStart, int percent, String planName,
            String price, boolean statusPlan, String userName, String password,
            List<String> benefitNameList) {

        Plan plan = _platformAdminService.createNewPlan(
                dateOfExpiration, dateOfStart, percent, planName,
                price, statusPlan, benefitNameList, userName, password);

        if (plan.getStatusPlan()) {
            return new ResponseEntity<>(plan, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/partner-platform/manager")
    public ResponseEntity.BodyBuilder postPlatformAdminMapping(
            PlatformManagerDTO platformManagerDTO) throws PlatformAdminNotFoundException {
        try {
            PlatformManager platformManager =
                    modelMapper.map(platformManagerDTO, PlatformManager.class);


            return ResponseEntity.status(HttpStatus.CREATED);
        }
        catch (Exception exception) {
            throw new PlatformAdminNotFoundException(
                    "An error happens while trying create a new platform manager. ",
                        exception);
        }
    }

    @PostMapping(value = "/partner-platform/${platformManagerId}/create-new-benefit")
    public ResponseEntity<Benefit> postNewBenefitMapping(
            String benefitName, boolean benefitExpired, Object benefitResource,
            String userName, String password) {

        Benefit benefit = _platformAdminService.crateNewBenefit(
                benefitName, benefitExpired, benefitResource, userName, password);

        if (!benefit.getBenefitExpired()) {
            return new ResponseEntity<>(benefit ,HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(value = "/partner-platform/${platformManagerId}/create-new-benefit-dto")
    public ResponseEntity<BenefitDTO> postNewBenefitDTOMapping(
            @Validated @RequestBody BenefitDTO benefitDTO, String userName, String password) {

        Benefit benefit = modelMapper.map(benefitDTO, Benefit.class);

        benefit = _platformAdminService.crateNewBenefit(
                benefit.getBenefitName(), benefit.getBenefitExpired(),
                benefit.getBenefitResource(), userName, password);

        if (!benefit.getBenefitExpired()) {
            benefitDTO = modelMapper.map(benefit, BenefitDTO.class);

            return new ResponseEntity<>(benefitDTO ,HttpStatus.CREATED);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Autowired
    public void setPlatformAdminService(PlatformAdminService platformAdminService) {
        this._platformAdminService = platformAdminService;
    }

    private final ModelMapper modelMapper = new ModelMapper();
    private PlatformAdminService _platformAdminService;
}
