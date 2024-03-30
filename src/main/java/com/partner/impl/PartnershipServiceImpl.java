/**
 * license - All directs reserved to @Entropy Corporation
 * @begin - 03/2024
 */

package com.partner.impl;

import com.partner.exception.*;
import com.partner.model.Address;
import com.partner.model.Partnership;
import com.partner.model.Plan;
import com.partner.model.PlatformManager;
import com.partner.persistence.AddressPersistence;
import com.partner.persistence.PartnershipPersistence;
import com.partner.persistence.PlanPersistence;
import com.partner.persistence.PlatformManagerPersistence;
import com.partner.service.PartnershipService;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Albert Gomes Cabral
 */
@Component
public class PartnershipServiceImpl implements PartnershipService {
    @Override
    public Partnership createPartnershipMember(
            int age, String birthDate, String email, String firstName,
            String middleName, String lastName, String planName,
            String password, String phone, int status, Address address)
        throws CreatePartnershipMemberException {

        Partnership partnershipLocalInstance = new Partnership();

        try {
            Plan plan = _getPlanByName(planName);

            boolean verifyPlan = plan.getStatusPlan();

            // Check the plan's status to make sure that plan is available
            if (verifyPlan) {
                partnershipLocalInstance.setPlanName(planName);
                partnershipLocalInstance.setAge(age);
                partnershipLocalInstance.setBirthDate(birthDate);
                partnershipLocalInstance.setEmail(email);
                partnershipLocalInstance.setFirstName(firstName);
                partnershipLocalInstance.setMiddleName(middleName);
                partnershipLocalInstance.setLastName(lastName);
                partnershipLocalInstance.setPassword(password);
                partnershipLocalInstance.setPlanName(planName);
                partnershipLocalInstance.setPhone(phone);
                partnershipLocalInstance.setStatus(status);

                Partnership result = _partnershipPersistence.save(partnershipLocalInstance);

                System.out.println("Building partner's address...");

                assert address != null;

                Address resultAddress = _createPartnershipAddress(result, address);

                if (resultAddress.getPartnership() != null) {
                    System.out.println("The address was created with success!! \n" +
                            resultAddress.toString());
                }

                return result;
            }
            else {
                throw new InvalidPlanException(
                        "Unable to process plan with the name " + planName + "\n" +
                                "Check if the plan that you choose is available.");
            }
        }
        catch (CreatePartnershipMemberException exception) {
            throw new CreatePartnershipMemberException(
                    "Unable to create partnershipLocalInstance, try again!",
                         exception);
        }
    }

    @Override
    public void deletePartnershipMember(
            long partnershipId) throws PartnershipMemberNotFoundException {
        try {
            Optional<Partnership> partnership =
                    _partnershipPersistence.findById(partnershipId);

            if (partnership.isEmpty()) {
                throw new PartnershipMemberNotFoundException(
                        "Member with the id " + partnershipId + " not found.");
            }

            _partnershipPersistence.delete(partnership);

            System.out.println("Member was deleted with success.");

        }
        catch (Exception exception) {
            throw new PartnershipMemberNotFoundException(
                    "Unable to delete partnership member with id " +
                            partnershipId, exception);
        }
    }

    @Override
    public Partnership getPartnershipById(
            long partnershipId) throws PartnershipMemberNotFoundException {

        try {
            Optional<Partnership> partnershipOptional =
                    _partnershipPersistence.findById(partnershipId);

            if (partnershipOptional.isPresent()) {
                return partnershipOptional.get();
            }
        }
        catch (PartnershipMemberNotFoundException partnershipMemberNotFoundException) {
            throw new PartnershipMemberNotFoundException(
                    "Unable to get partnership by id " + partnershipId,
                            partnershipMemberNotFoundException);
        }

        return null;
    }

    @Override
    public List<Partnership> getPartnershipIsActiveList(
            @Nullable String userNameAdmin, @Nullable String password, boolean isActive)
        throws PartnershipPermissionDeniedException {

        Optional<PlatformManager> optionalPlatformManager =
                _platformManagerPersistence.findByUserNameAdminAndPassword(userNameAdmin, password);

        List<Partnership> partnershipGreenList = new ArrayList<>();

        if (optionalPlatformManager.isPresent() &&
                optionalPlatformManager.get().getHasPermission()) {

            List<Partnership> partnershipList =
                    _partnershipPersistence.findAll();

            List<Partnership> partnershipBlackList = new ArrayList<>();

            for (Partnership partnership : partnershipList) {
                if (partnership.getStatus() != 0) {
                    partnershipBlackList.add(partnership);
                }
                else if (partnership.getStatus() == 0) {
                    partnershipGreenList.add(partnership);
                }
            }

            System.out.println("Number of the partners in the black list: "
                    + partnershipBlackList.size());
        }

        return partnershipGreenList;
    }

    @Override
    public List<Partnership> getPartnershipAllList(
            @Nullable String userNameAdmin, @Nullable String password)
        throws PartnershipPermissionDeniedException {

        Optional<PlatformManager> platformManagerOptional =
                _platformManagerPersistence.findByUserNameAdminAndPassword(
                        userNameAdmin, password);

        if (platformManagerOptional.isPresent()) {
            return _partnershipPersistence.findAll();
        }
        else {
            throw new PartnershipPermissionDeniedException(
                    "platform's admin with the username " + userNameAdmin +
                            " have access denied.");
        }
    }

    @Override
    public boolean isActive(
            long partnershipId, String userNameAdmin, boolean hasPermission)
        throws PartnershipMemberNotFoundException {

        try {
            Optional<Partnership> optional =
                    _partnershipPersistence.findById(partnershipId);

            if (optional.isEmpty()) {
                throw new PartnershipMemberNotFoundException(
                        "Partnership member with id " + partnershipId +
                                " wasn't found.");
            };

            Partnership partnershipOptionalInstance = optional.get();

            if (partnershipOptionalInstance.getStatus() != 0) {
                return true;
            }
        }
        catch (PartnershipMemberNotFoundException throwable) {
            throw new PartnershipMemberNotFoundException(throwable);
        }

        return false;
    }

    @Override
    public void updatePartnershipMemberPlan(
            long partnershipId, String newPlanName, String oldPlanName)
        throws PartnershipMemberNotFoundException {

        try {
            Optional<Partnership> partnershipOptional =
                    _partnershipPersistence.findById(partnershipId);

            if (partnershipOptional.isPresent()) {
                Plan oldPlan = _getPlanByName(oldPlanName);

                if (!oldPlan.getStatusPlan()) {
                    System.out.println("Plan expired! \n" +
                            "Maybe you need contact us to platform's admin.");

                    return;
                }

                Partnership partnership = partnershipOptional.get();

                partnership.setPlanName(newPlanName);

                _partnershipPersistence.save(partnership);

                System.out.println("Plan updated with success full!");
            }
        }
        catch (PartnershipMemberNotFoundException throwable) {
            throw new PartnershipMemberNotFoundException(
                    "Unable to update partner member plan ", throwable);
        }
    }

    @Override
    public Partnership updatePartnershipMember(
            long partnershipId, Partnership newPartnership) throws UpdatePartnershipMemberException {

        Optional<Partnership> optionalPartnership =
                _partnershipPersistence.findById(partnershipId);

        Partnership partnership;

        if (optionalPartnership.isPresent()) {
            partnership = optionalPartnership.get();

            partnership.setAge(
                    newPartnership.getAge() > 0 ?
                            newPartnership.getAge() : partnership.getAge());

            partnership.setEmail(
                    !Objects.isNull(newPartnership.getEmail()) ?
                            newPartnership.getEmail() : partnership.getEmail());

            partnership.setFirstName(
                    !Objects.isNull(newPartnership.getFirstName()) ?
                            newPartnership.getFirstName() : partnership.getFirstName());

            partnership.setMiddleName(
                    !Objects.isNull(newPartnership.getMiddleName()) ?
                            newPartnership.getMiddleName() : partnership.getMiddleName());

            partnership.setLastName(
                    !Objects.isNull(newPartnership.getLastName()) ?
                            newPartnership.getLastName() : partnership.getLastName());

            partnership.setPlanName(
                    !Objects.isNull(newPartnership.getPlanName()) ?
                            newPartnership.getPlanName() : partnership.getPlanName());

            partnership.setPhone(
                    !Objects.isNull(newPartnership.getPhone()) ?
                            newPartnership.getPhone() : partnership.getPhone());

            partnership.setStatus(
                    newPartnership.getStatus() > 0 ?
                            newPartnership.getStatus() : partnership.getStatus());

            _partnershipPersistence.save(partnership);

            System.out.println("Partner member updated with success");
        }
        else {
            throw new UpdatePartnershipMemberException(
                    "Unable to update the partner member with the primary key "
                            + partnershipId);
        }

        return partnership;
    }

    @Override
    public Address updateParnershipAddress(
            String city, String country, String district, long partnershipPk,
            long addressId, int number, String street)
        throws UpdatePartnershipAddressException {

        return null;
    }

    @Override
    public boolean updatePartnershipPassword(
            @Nullable String email, @Nullable String password, long partnershipId)
        throws PartnershipMemberNotFoundException {
        boolean updated = false;

        try {
            Optional<Partnership> partnershipOptional =
                    _partnershipPersistence.findById(partnershipId);

            if (partnershipOptional.isPresent()) {
                Partnership partnership = partnershipOptional.get();

                if (partnership.getEmail().equals(email) &&
                        partnership.getPassword().equals(password)) {

                    System.out.println("Partner's password updated!");

                    updated = true;
                }
            }
        }
        catch (PartnershipMemberNotFoundException throwable) {
            throw new PartnershipMemberNotFoundException(
                    "Unable to update the partner password ", throwable);
        }

        return updated;
    }

    private Address _createPartnershipAddress(Partnership partnership, Address address) {
        address.setPartnership(partnership);

        return _addressPersistence.save(address);
    }

    private Date _dateGenerated() {
        java.util.Date dataUtil = new java.util.Date();

        return new Date(dataUtil.getTime());
    }

    private Plan _getPlanByName(String planName) {
        List<Plan> planOptionalList =_planPersistence.findAll();
        Plan planSelf = null;

        for (Plan plan : planOptionalList) {
            if (plan.getPlanName().equals(planName)) {
                planSelf = plan;
                break;
            }
        }
        return planSelf;
    }

    private AddressPersistence _addressPersistence;
    private PartnershipPersistence _partnershipPersistence;
    private PlanPersistence _planPersistence;
    private PlatformManagerPersistence _platformManagerPersistence;
}
