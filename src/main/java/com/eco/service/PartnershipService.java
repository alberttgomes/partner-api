package com.partner.service;

import com.partner.exception.*;
import com.partner.model.Address;
import com.partner.model.Partnership;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PartnershipService {
    public Partnership createPartnershipMember(
            int age, String birthDate, String email, String firstName,
            String middleName, String lastName, String planName,
            String password, String phone, int status, Address address)
        throws CreatePartnershipMemberException;

    public void deletePartnershipMember(long partnershipId) throws Exception;

    public Partnership getPartnershipById(
            long partnershipId) throws PartnershipMemberNotFoundException;

    public List<Partnership> getPartnershipIsActiveList(
            String userNameAdmin, String password, boolean isActive)
        throws PartnershipPermissionDeniedException;

    public List<Partnership> getPartnershipAllList(
            String userNameAdmin, String password) throws PartnershipPermissionDeniedException;

    public boolean isActive(
            long partnershipId, String userNameAdmin, boolean hasPermission)
        throws PartnershipMemberNotFoundException;

    public void updatePartnershipMemberPlan(
            long partnershipId, String actualPlan, String newPlan)
        throws PartnershipMemberNotFoundException;

    public Partnership updatePartnershipMember(
            long partnershipId, Partnership partnership)
        throws UpdatePartnershipMemberException;

    public Address updateParnershipAddress(
            String city, String country, String district, long partnershipPk,
            long addressId, int number, String street)
        throws UpdatePartnershipAddressException;

    public boolean updatePartnershipPassword(
            String email, String password, long partnershipPk)
        throws PartnershipMemberNotFoundException;
}
