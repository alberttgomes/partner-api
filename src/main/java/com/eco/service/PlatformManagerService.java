package com.eco.service;

import com.eco.exception.PlatformManagerNotFoundException;
import com.eco.exception.UnableToProcessPlatformManagerException;
import com.eco.exception.UnableToProcessBenefitException;
import com.eco.exception.UnableToProcessPlanException;
import com.eco.model.Benefit;
import com.eco.model.Plan;
import com.eco.model.PlatformManager;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Albert Gomes Cabral
 */
@Service
public interface PlatformManagerService {
     Benefit createNewBenefit(
            String benefitName, boolean benefitExpired, Object benefitResource,
            String userNameAdmin, String password)
        throws PlatformManagerNotFoundException, UnableToProcessPlanException;

     PlatformManager createNewPlatformManager(
             String email, String userNameManager, String password, boolean hasPermission)
        throws UnableToProcessPlatformManagerException;

    Plan createNewPlan(
            Date dateOfExpiration, Date dateOfStart, int percent, String planName,
            String price, boolean statusPlan, List<String> benefitNameList,
            String userNameAdmin, String password)
        throws PlatformManagerNotFoundException, UnableToProcessPlanException;

    void deleteBenefit(
            String userNameAdmin, String password, long benefitId)
        throws PlatformManagerNotFoundException;

    void deletePlan(
            long planId, String userNameManager, String password)
        throws PlatformManagerNotFoundException, UnableToProcessPlanException;

    PlatformManager getPlatformAdmin(
            String userName, String password) throws PlatformManagerNotFoundException;

    Benefit updateBenefit(
            Benefit newBenefit, long benefitId, String userNameAdmin, String password)
        throws PlatformManagerNotFoundException, UnableToProcessBenefitException;

    Plan updatePlan(
            Plan newPlan, long planPk, String userNameAdmin, String password)
        throws PlatformManagerNotFoundException, UnableToProcessPlanException;
}
