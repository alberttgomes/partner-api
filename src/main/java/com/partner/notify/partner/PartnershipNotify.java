package com.partner.notify.partner;

import com.partner.exception.UnableToProcessNotifyException;
import com.partner.model.Notify;
import com.partner.model.Partnership;
import com.partner.model.PlatformManager;
import com.partner.notify.BasePlatformNotify;
import com.partner.persistence.NotifyPersistence;
import com.partner.service.PartnershipNotifyService;
import com.partner.service.PartnershipService;
import com.partner.util.PlatformManagerUtil;

import java.sql.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

/**
 * @author Albert Gomes Cabral
 */
@Component
public class PartnershipNotify extends BasePlatformNotify
        implements PartnershipNotifyService {
    @Override
    public List<Notify> getPartnershipAllNotification(
            Partnership partnership) throws UnableToProcessNotifyException {
        try {
            Partnership newPartnershipInstance = _partnershipService.getPartnershipById(
                    partnership.getPartnershipId());

            List<Notify> notifyList = newPartnershipInstance.getNotifyList();

            if (notifyList.isEmpty()) {
                System.out.println("Wasn't found notifications to the partner "
                        + partnership.getFirstName());

                return new ArrayList<>();
            }

            return notifyList;
        }
        catch (Exception notifyException) {
            throw new UnableToProcessNotifyException(
                    "unable to get notifications ", notifyException);
        }
    }

    @Override
    public Notify getPartnershipNotifyById(
            long notifyId) throws UnableToProcessNotifyException {

        return (Notify) getNotify(notifyId);
    }

    @Override
    public void sendPartnershipNotify(
            String[] labels, String send, String receive, String title,
            String message, Time sentTime)
        throws Exception {

        sendNotify(labels, send, receive, title, message, sentTime);
    }

    @Override
    protected Object getNotify(
            long notifyId) throws UnableToProcessNotifyException {

        try {
            Optional<Notify> notifyOptional = _notifyPersistence.findById(notifyId);

            if (notifyOptional.isPresent()) {
                return notifyOptional.get();
            }
        }
        catch (Exception notifyException) {
            throw new UnableToProcessNotifyException(
                    "Unable to get notify with the primary key "
                            + notifyId, notifyException);
        }

        return null;
    }

    @Override
    protected void sendNotify(
            String[] labels, String send, String receive, String title,
            String message, Time sentTime)
        throws Exception {

        try {
            Partnership partnershipSend = _getPartnershipByEmail(send);
            Partnership partnershipReceive = _getPartnershipByEmail(receive);

            if (partnershipSend != null && partnershipReceive != null) {
                Notify notify = new Notify();

                notify.setMessage(message);
                notify.setSend(partnershipSend.getEmail());
                notify.setSentTime(sentTime);
                notify.setReceive(partnershipReceive.getEmail());
                notify.setTitle(title);
                notify.setPartnership(partnershipReceive);

                Notify result = _notifyPersistence.save(notify);

                System.out.println(
                        "Notify " + result.getTitle() + " : "
                                + result.getSentTime() + " sent.");
            }
        }
        catch (UnableToProcessNotifyException unableToProcessNotifyException) {
            throw new UnableToProcessNotifyException(
                    "Unable to send notify ", unableToProcessNotifyException);
        }
    }

    private Partnership _getPartnershipByEmail(String email) throws Exception {

        PlatformManager platformManager =
                PlatformManagerUtil.loadPlatformManagerProperties();

        List<Partnership> partnershipList =
                _partnershipService.getPartnershipAllList(
                        platformManager.getUserNameAdmin(),
                        platformManager.getPassword());

        if (partnershipList.isEmpty()) {
            return null;
        }

        Partnership partnership = null;

        for (Partnership partner : partnershipList) {
            if (partner.getEmail().equals(email)) {
                partnership = partner;

                break;
            }
        }

        return partnership;
    }

    private NotifyPersistence _notifyPersistence;
    private PartnershipService _partnershipService;
}
