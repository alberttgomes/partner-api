package com.partner.service;

import com.partner.exception.*;
import com.partner.model.Address;
import com.partner.model.Partnership;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Albert Gomes Cabral
 */
@Service("_partnershipService")
public interface PartnershipService {
    Partnership createPartnershipMember(
            int age, String birthDate, String email, String firstName,
            String middleName, String lastName, String planName,
            String password, String phone, int status, Address address)
        throws CreatePartnershipMemberException;

    void deletePartnershipMember(long partnershipId) throws Exception;

    Partnership getPartnershipById(
            long partnershipId) throws PartnershipMemberNotFoundException;

    List<Partnership> getPartnershipIsActiveList(
            String userNameAdmin, String password, boolean isActive)
        throws PartnershipPermissionDeniedException;

    List<Partnership> getPartnershipAllList(
            String userNameAdmin, String password) throws PartnershipPermissionDeniedException;

    boolean isActive(
            long partnershipId, String userNameAdmin, boolean hasPermission)
        throws PartnershipMemberNotFoundException;

    void updatePartnershipMemberPlan(
            long partnershipId, String actualPlan, String newPlan)
        throws PartnershipMemberNotFoundException;

    Partnership updatePartnershipMember(
            long partnershipId, Partnership partnership)
        throws UpdatePartnershipMemberException;

    Address updateParnershipAddress(
            String city, String country, String district, long partnershipPk,
            long addressId, int number, String street)
        throws UpdatePartnershipAddressException;

    boolean updatePartnershipPassword(
            String email, String password, long partnershipPk)
        throws PartnershipMemberNotFoundException;
}
