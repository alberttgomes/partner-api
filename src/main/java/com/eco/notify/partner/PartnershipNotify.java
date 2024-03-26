package com.eco.notify.partner;

import com.eco.model.Notify;
import com.eco.model.Partnership;
import com.eco.model.PlatformManager;
import com.eco.notify.BasePlatformNotify;
import com.eco.persistence.NotifyPersistence;
import com.eco.service.PartnershipService;

import java.sql.Time;
import java.util.List;

import com.eco.util.PlatformManagerUtil;
import org.springframework.stereotype.Component;

/**
 * @author Albert Gomes Cabral
 */
@Component
public class PartnershipNotify extends BasePlatformNotify {
    @Override
    public Object getNotify(long notifyId) {
        return null;
    }

    @Override
    public void sendNotify(
            String[] labels, String send, String receive, String title,
            String messageToSend, Time sentTime) throws Exception {
        try {
            Partnership partnershipSend = _getPartnershipByEmail(send);
            Partnership partnershipReceive = _getPartnershipByEmail(receive);

            if (partnershipSend != null && partnershipReceive != null) {
                Notify notify = new Notify();

                notify.setMessage(messageToSend);
                notify.setSend(partnershipSend.getEmail());
                notify.setSentTime(sentTime);
                notify.setReceive(partnershipReceive.getEmail());
                notify.setTitle(title);

                _notifyPersistence.save(notify);

                // WIP

                System.out.println("Notify " + notify.getTitle() + " was sent.");
            }
        }
        catch (Exception exception) {
            throw new Exception(exception);
        }
    }

    private Partnership _getPartnershipByEmail(String email) throws Exception {
        PlatformManager platformManager = PlatformManagerUtil.loadPlatformManagerProperties();

        List<Partnership> partnershipList =_partnershipService.getPartnershipAllList(
                platformManager.getUserNameAdmin(), platformManager.getPassword());

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
