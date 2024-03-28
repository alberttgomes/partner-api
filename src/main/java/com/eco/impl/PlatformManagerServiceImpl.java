package com.eco.impl;

import com.eco.exception.PlatformManagerNotFoundException;
import com.eco.exception.UnableToProcessPlatformManagerException;
import com.eco.exception.UnableToProcessBenefitException;
import com.eco.exception.UnableToProcessPlanException;
import com.eco.model.Benefit;
import com.eco.model.Plan;
import com.eco.model.PlatformManager;
import com.eco.persistence.BenefitPersistence;
import com.eco.persistence.PlanPersistence;
import com.eco.persistence.PlatformManagerPersistence;
import com.eco.service.PlatformManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.*;

/**
 * @author Albert Gomes Cabral
 */
@Component
public class PlatformManagerServiceImpl implements PlatformManagerService {
    @Override
    public Benefit createNewBenefit(
            String benefitName, boolean benefitExpired, Object benefitResource,
            String userNameAdmin, String password)
        throws PlatformManagerNotFoundException, UnableToProcessPlanException {

        try {
            Optional<PlatformManager> platformManagerOptional =
                    _platformManagerPersistence.findByUserNameAdminAndPassword(
                            userNameAdmin, password);

            if (platformManagerOptional.isPresent()) {
                boolean verifyIfBenefitNameAlreadyExists =
                        _checkIfBenefitExistByName(benefitName);

                if (verifyIfBenefitNameAlreadyExists) {
                    throw new UnableToProcessPlanException(
                            "Unable to create the benefit with the name " + benefitName +
                                    "Already exist a benefit with this name, " +
                                    "the benefit name for a benefit should be unique.");
                }
                else {
                    Benefit benefit = new Benefit();

                    benefit.setBenefitExpired(benefitExpired);
                    benefit.setBenefitName(benefitName);
                    benefit.setBenefitResource(benefitResource);

                    System.out.println("Create benefit with success");

                    return _benefitPersistence.save(benefit);
                }
            }
        }
        catch (PlatformManagerNotFoundException | UnableToProcessPlanException throwable) {
            if (throwable instanceof UnableToProcessPlanException) {
                throw new UnableToProcessPlanException(
                        "Unable to create the benefit ", throwable);
            }
            else {
                throw new PlatformManagerNotFoundException(
                        "The user " + userNameAdmin + " haven't permission to " +
                                "create benefit or the user wasn't found. ", throwable);
            }
        }
        return null;
    }

    @Override
    public PlatformManager createNewPlatformManager(
            @Nullable String email, @Nullable String userNameManager, @Nullable String password,
            boolean hasPermission) throws UnableToProcessPlatformManagerException {

        try {
            PlatformManager platformManager = new PlatformManager();

            platformManager.setEmail(email);
            platformManager.setPassword(password);
            platformManager.setUserNameAdmin(userNameManager);
            platformManager.setHasPermission(hasPermission);

            PlatformManager result = _platformManagerPersistence.save(platformManager);

            if (result.getPlatformAdminId() >= 0) {
                return result;
            }
        }
        catch (UnableToProcessPlatformManagerException exception) {
                throw new UnableToProcessPlatformManagerException(
                        "Unable to create a new platform's manager ", exception);
        }
        return null;
    }

    @Override
    public Plan createNewPlan(
            Date dateOfExpiration, Date dateOfStart, int percent,
            String planName, String price, boolean statusPlan, List<String> benefitNameList,
            @Nullable String userNameAdmin, @Nullable String password)
        throws PlatformManagerNotFoundException, UnableToProcessPlanException {

        try {
            Optional<PlatformManager> platformManagerOptional =
                    _platformManagerPersistence.findByUserNameAdminAndPassword(
                            userNameAdmin, password);

            PlatformManager platformManager;

            if (platformManagerOptional.isPresent()) {
                    platformManager = platformManagerOptional.get();
            }
            else {
                throw new PlatformManagerNotFoundException(
                        "User admin with the username " + userNameAdmin +
                                " wasn't found.", new Throwable());
            }

            if (benefitNameList.isEmpty()) {
                throw new UnableToProcessPlanException(
                        "The benefit name list is empty." +
                                " Broken the rule for the build a plan");
            }
            else if (!platformManager.getHasPermission()) {
                throw new UnableToProcessPlanException(
                        "The user " + platformManager.getUserNameAdmin() +
                                " haven't permission to create a plan.");
            }

            Plan plan = new Plan();

            plan.setDateOfExpiration((java.sql.Date) dateOfExpiration);
            plan.setDateOfStart((java.sql.Date) dateOfStart);
            plan.setPlanName(planName);
            plan.setPercent(percent);
            plan.setPrice(price);
            plan.setStatusPlan(statusPlan);

            long[] benefitPkArray = new long[10];
            int increment = 0;

            for (String benefitName : benefitNameList) {
                Optional<Benefit> benefitOptional =
                        _benefitPersistence.findBenefitByBenefitName(benefitName);

                Benefit benefit;

                if (benefitOptional.isPresent()) {
                    benefit = benefitOptional.get();
                }
                else {
                    System.out.println("not found benefit with the name " + benefitName);

                    continue;
                }

                if (benefit.getBenefitExpired()) {
                    System.out.println("Benefit " + benefit.getBenefitName() + " expired.");

                    continue;
                }

                increment++;

                benefitPkArray[increment] += benefit.getBenefitId();

                plan.setBenefitsPk(benefitPkArray);
            }

            if (Arrays.stream(benefitPkArray).count() <= 0) {
                return null;
            }

            plan.setBenefitsPk(benefitPkArray);

            _planPersistence.save(plan);

            System.out.println("Builder plan"  + plan.getPlanName() + " with success!");

            return plan;
        }
        catch (UnableToProcessPlanException | PlatformManagerNotFoundException throwable) {
            if (throwable instanceof UnableToProcessPlanException) {
                throw new UnableToProcessPlanException(
                        "Unable to create plan " + planName, throwable);
            }
            else {
                throw new PlatformManagerNotFoundException(
                        "Unable to process the partner's admin with the username " +
                                userNameAdmin, throwable);
            }
        }
    }

    @Override
    public void deleteBenefit(
            @Nullable String userNameAdmin, @Nullable String password, long benefitId)
        throws PlatformManagerNotFoundException {

        try {
            Optional<PlatformManager> platformManagerOptional =
                    _platformManagerPersistence.findByUserNameAdminAndPassword(
                            userNameAdmin, password);

            if (platformManagerOptional.isPresent()) {
                Optional<Benefit> benefitOptional = _benefitPersistence.findById(benefitId);

                benefitOptional.ifPresent(benefit -> _benefitPersistence.delete(benefit));
            }
        }
        catch (PlatformManagerNotFoundException platformManagerNotFoundException) {
            throw new PlatformManagerNotFoundException(
                    "Unable to delete benefit with the primary key " + benefitId,
                    platformManagerNotFoundException);
        }
    }

    @Override
    public void deletePlan(
            long planId, String userNameManger, String password)
        throws PlatformManagerNotFoundException, UnableToProcessPlanException {

        try {
            Optional<PlatformManager> platformManagerOptional =
                    _platformManagerPersistence.findByUserNameAdminAndPassword(
                            userNameManger, password);

            if (platformManagerOptional.isPresent()) {
                PlatformManager platformManager = platformManagerOptional.get();

                if (platformManager.getHasPermission()) {
                    Optional<Plan> planOptional =_planPersistence.findById(planId);

                    planOptional.ifPresentOrElse(
                            plan -> {
                                _planPersistence.delete(plan);

                                System.out.println("Plan was delete with success.");
                            },
                            // If plan don't exist print the message.
                            () -> System.out.println(
                                    "Unable to delete plan with the primary key " + planId)
                    );
                }
            }
        }
        catch (PlatformManagerNotFoundException | UnableToProcessPlanException throwable) {
            if (throwable instanceof UnableToProcessPlanException) {
                throw new UnableToProcessPlanException(
                        "Unable to delete the plan with the primary key " +
                                planId, throwable);
            }
            else {
                throw new PlatformManagerNotFoundException(
                        "Unable to get permission to admin " + userNameManger, throwable);
            }
        }
    }

    @Override
    public PlatformManager getPlatformAdmin(
            @Nullable String userNameManager, @Nullable String password)
        throws PlatformManagerNotFoundException {

        try {
            Optional<PlatformManager> platformAdminOptional =
                    _platformManagerPersistence.findByUserNameAdminAndPassword(
                            userNameManager, password);

            if (platformAdminOptional.isPresent()) {

                return platformAdminOptional.get();
            }
        }
        catch (PlatformManagerNotFoundException throwable) {
            throw new PlatformManagerNotFoundException(
                    "Unable to get the platform's admin ", throwable);
        }
        return null;
    }

    @Override
    public Benefit updateBenefit(
            Benefit newBenefit, long benefitId,
            @Nullable String userNameAdmin, @Nullable String password)
        throws PlatformManagerNotFoundException, UnableToProcessBenefitException {

        try {
            boolean verify = _verifyPlatformAdmin(userNameAdmin, password);

            if (verify) {
                Optional<Benefit> benefitOptional = _benefitPersistence.findById(benefitId);

                Benefit benefit;

                if (benefitOptional.isPresent()) {
                    benefit = benefitOptional.get();

                    benefit.setBenefitExpired(
                            newBenefit.getBenefitExpired() ?
                                    newBenefit.getBenefitExpired() : benefit.getBenefitExpired());

                    benefit.setBenefitName(
                            !Objects.isNull(newBenefit.getBenefitName()) ?
                                    newBenefit.getBenefitName() : benefit.getBenefitName());

                    benefit.setBenefitResource(
                            !Objects.isNull(newBenefit.getBenefitResource()) ?
                                    newBenefit.getBenefitResource() : benefit.getBenefitResource());

                    return _benefitPersistence.save(benefit);
                }
                else {
                    throw new UnableToProcessBenefitException(
                            "Benefit with the primary key " + benefitId + " not is present" );
                }
            }
        }
        catch (UnableToProcessBenefitException | PlatformManagerNotFoundException throwable) {
            if (throwable instanceof  UnableToProcessBenefitException) {
                throw new UnableToProcessBenefitException(
                        "Unable to update the benefit with the primary key "
                                + benefitId, throwable);
            }
            else {
                throw new PlatformManagerNotFoundException(
                        "Unable to process user " + userNameAdmin, throwable);
            }
        }
        return null;
    }

    @Override
    public Plan updatePlan(
            Plan newPlan, long planPk, String userNameAdmin, String password)
        throws PlatformManagerNotFoundException, UnableToProcessPlanException {

        try {
           Optional<PlatformManager> platformManagerOptional =
                   _platformManagerPersistence.findByUserNameAdminAndPassword(
                           userNameAdmin, password);

           if (platformManagerOptional.isEmpty()) {
               throw new PlatformManagerNotFoundException(
                       "The user " + userNameAdmin + " not found. ", new Throwable());
           }

           Optional<Plan> planOptional = _planPersistence.findById(planPk);

           Plan plan;

           if (planOptional.isPresent()) {
               plan = planOptional.get();

               plan.setDateOfExpiration(
                       !Objects.isNull(newPlan.getDateOfExpiration()) ?
                               newPlan.getDateOfExpiration() : plan.getDateOfExpiration());

               plan.setPlanName(
                       !Objects.isNull(newPlan.getPlanName()) ?
                               newPlan.getPlanName() : plan.getPlanName());

               plan.setPercent(
                       newPlan.getPercent() > 0 ?
                               newPlan.getPercent() : plan.getPercent());

               plan.setPrice(
                       !Objects.isNull(newPlan.getPrice()) ?
                               newPlan.getPrice() : plan.getPlanName());

               System.out.println("Update the plan with success.");

               return _planPersistence.save(plan);
           }
        }
        catch (PlatformManagerNotFoundException | UnableToProcessPlanException throwable) {
            if (throwable instanceof UnableToProcessPlanException) {
                throw new UnableToProcessPlanException(
                        "Unable to process plan with the primary key "
                                + planPk, throwable);
            }
            else {
                throw new PlatformManagerNotFoundException(
                        "Unable to update the plan user " + userNameAdmin +
                                " not allowed. ", throwable);
            }
        }
        return null;
    }

    private boolean _checkIfBenefitExistByName(String benefitName) {
        boolean check = false;

        Optional<Benefit> benefitOptional =
                _benefitPersistence.findBenefitByBenefitName(benefitName);

        if (benefitOptional.isPresent()) {
            check = true;
        }

        return check;
    }

    private boolean _verifyPlatformAdmin(
            @Nullable String userNameManager, @Nullable String password)
        throws PlatformManagerNotFoundException {

        Optional<PlatformManager> platformManagerOptional =
                _platformManagerPersistence.findByUserNameAdminAndPassword(
                        userNameManager, password);

        if (platformManagerOptional.isEmpty()) {
            throw new PlatformManagerNotFoundException(
                    "The user " + userNameManager + " not found. ", new Throwable());
        }

        return true;
    }

    @Autowired
    public void setBenefitPersistence(BenefitPersistence benefitPersistence) {
        this._benefitPersistence = benefitPersistence;
    }

    @Autowired
    public void setPlatformManagerPersistence(PlatformManagerPersistence platformManagerPersistence) {
        this._platformManagerPersistence = platformManagerPersistence;
    }

    @Autowired
    public void setPlanPersistence(PlanPersistence planPersistence) {
        this._planPersistence = planPersistence;
    }

    private BenefitPersistence _benefitPersistence;
    private PlatformManagerPersistence _platformManagerPersistence;
    private PlanPersistence _planPersistence;
}
