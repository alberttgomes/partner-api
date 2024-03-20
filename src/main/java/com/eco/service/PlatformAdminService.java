package com.eco.service;

import com.eco.exception.PlatformAdminNotFoundException;
import com.eco.exception.UnableToProcessPlanException;
import com.eco.model.Benefit;
import com.eco.model.Plan;
import com.eco.model.PlatformAdmin;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Albert Gomes Cabral
 */
@Service
public interface PlatformAdminService {
    public Benefit crateNewBenefit(
            String benefitName, boolean benefitExpired, Object benefitResource,
            long[] planPk, String userNameAdmin, String password)
        throws PlatformAdminNotFoundException, UnableToProcessPlanException;

    public Plan createNewPlan(
            Date dateOfExpiration, Date dateOfStart, int percent, String planName,
            String price, boolean statusPlan, List<String> benefitNameList,
            String userNameAdmin, String password)
        throws PlatformAdminNotFoundException, UnableToProcessPlanException;

    public void deleteBenefit(
            String userNameAdmin, String password, long benefitId)
        throws PlatformAdminNotFoundException;

    public void deletePlan(
            long planId, PlatformAdmin platformAdmin)
        throws PlatformAdminNotFoundException, UnableToProcessPlanException;

    public PlatformAdmin getPlatformAdmin(
            String userName, String password) throws PlatformAdminNotFoundException;

    public Benefit updateBenefit(
            Benefit newBenefit, long benefitId, PlatformAdmin platformAdmin)
        throws PlatformAdminNotFoundException;

    public Plan updatePlan(
            Plan newPlan, long planPk, String userNameAdmin, String password)
        throws PlatformAdminNotFoundException, UnableToProcessPlanException;
}
