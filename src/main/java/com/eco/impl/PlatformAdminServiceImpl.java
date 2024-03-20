package com.eco.impl;

import com.eco.exception.PlatformAdminNotFoundException;
import com.eco.exception.UnableToProcessPlanException;
import com.eco.model.Benefit;
import com.eco.model.Plan;
import com.eco.model.PlatformAdmin;
import com.eco.persistence.BenefitPersistence;
import com.eco.persistence.PlanPersistence;
import com.eco.persistence.PlatformAdminPersistence;
import com.eco.service.PlatformAdminService;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.*;

/**
 * @author Albert Gomes Cabral
 */
@Component
public class PlatformAdminServiceImpl implements PlatformAdminService {
    @Override
    public Benefit crateNewBenefit(
            String benefitName, boolean benefitExpired, Object benefitResource,
            long[] planPk, String userNameAdmin, String password)
        throws PlatformAdminNotFoundException, UnableToProcessPlanException {


        return null;
    }

    @Override
    public Plan createNewPlan(
            @Nullable Date dateOfExpiration, @Nullable Date dateOfStart, int percent,
            @Nullable String planName, @Nullable String price, boolean statusPlan,
            List<String> benefitNameList, @Nullable String userNameAdmin, @Nullable String password)
        throws PlatformAdminNotFoundException, UnableToProcessPlanException {

        try {
            Optional<PlatformAdmin> platformAdminOptional =
                    _platformAdminPersistence.findByUserNameAdminAndPassword(userNameAdmin, password);

            PlatformAdmin platformAdmin;

            if (platformAdminOptional.isPresent()) {
                    platformAdmin = platformAdminOptional.get();
            }
            else {
                throw new PlatformAdminNotFoundException(
                        "User admin with the username " + userNameAdmin + " wasn't found.",
                        new Throwable());
            }

            if (benefitNameList.isEmpty()) {
                throw new UnableToProcessPlanException(
                        "The benefit name list is empty. Broken the rule for the build a plan");
            }
            else if (!platformAdmin.getHasPermission()) {
                throw new UnableToProcessPlanException(
                        "The user " + platformAdmin.getUserNameAdmin() +
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
        catch (UnableToProcessPlanException | PlatformAdminNotFoundException throwable) {
            if (throwable instanceof UnableToProcessPlanException) {
                throw new UnableToProcessPlanException(
                        "Unable to create plan " + planName, throwable);
            }
            else {
                throw new PlatformAdminNotFoundException(
                        "Unable to process the partner's admin with the username " +
                                userNameAdmin, throwable);
            }
        }
    }

    @Override
    public void deleteBenefit(
            @Nullable String userNameAdmin, @Nullable String password, long benefitId)
        throws PlatformAdminNotFoundException {

        try {
            Optional<PlatformAdmin> platformAdminOptional =
                    _platformAdminPersistence.findByUserNameAdminAndPassword(userNameAdmin, password);

            if (platformAdminOptional.isPresent()) {
                Optional<Benefit> benefitOptional = _benefitPersistence.findById(benefitId);

                benefitOptional.ifPresent(benefit -> _benefitPersistence.delete(benefit));
            }
        }
        catch (PlatformAdminNotFoundException platformAdminNotFoundException) {
            throw new PlatformAdminNotFoundException(
                    "Unable to delete benefit with the primary key " + benefitId,
                        platformAdminNotFoundException);
        }
    }

    @Override
    public void deletePlan(
            long planId, PlatformAdmin platformAdmin)
        throws PlatformAdminNotFoundException, UnableToProcessPlanException {

        try {
            if (platformAdmin.getHasPermission()) {
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
        catch (PlatformAdminNotFoundException platformAdminNotFoundException) {
            throw new PlatformAdminNotFoundException(
                    "Unable to get permission to admin " + platformAdmin.getUserNameAdmin(),
                        platformAdminNotFoundException);
        }
    }

    @Override
    public PlatformAdmin getPlatformAdmin(
            @Nullable String userName, @Nullable String password) throws PlatformAdminNotFoundException {
        try {
            Optional<PlatformAdmin> platformAdminOptional =
                    _platformAdminPersistence.findByUserNameAdminAndPassword(userName, password);

            if (platformAdminOptional.isPresent()) {

                return platformAdminOptional.get();
            }
        }
        catch (PlatformAdminNotFoundException throwable) {
            throw new PlatformAdminNotFoundException(
                    "Unable to get the platform's admin ", throwable);
        }
        return null;
    }

    @Override
    public Benefit updateBenefit(
            Benefit newBenefit, long benefitId, PlatformAdmin platformAdmin)
        throws PlatformAdminNotFoundException {

        return null;
    }

    @Override
    public Plan updatePlan(
            Plan newPlan, long planPk, String userNameAdmin, String password)
        throws PlatformAdminNotFoundException, UnableToProcessPlanException {

        try {
           Optional<PlatformAdmin> platformAdminOptional =
                   _platformAdminPersistence.findByUserNameAdminAndPassword(userNameAdmin, password);

           if (platformAdminOptional.isEmpty()) {
               throw new PlatformAdminNotFoundException(
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

               _planPersistence.save(plan);

               System.out.println("Update the plan with success.");

               return plan;
           }
        }
        catch (PlatformAdminNotFoundException | UnableToProcessPlanException throwable) {
            if (throwable instanceof UnableToProcessPlanException) {
                throw new UnableToProcessPlanException(
                        "Unable to process plan with the primary key " + planPk, throwable);
            }
            else {
                throw new PlatformAdminNotFoundException(
                        "Unable to update the plan user " + userNameAdmin +
                                " not allowed. ", throwable);
            }
        }
        return null;
    }

    private BenefitPersistence _benefitPersistence;
    private PlatformAdminPersistence _platformAdminPersistence;
    private PlanPersistence _planPersistence;
}
