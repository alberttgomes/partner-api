package com.partner.service;

import com.partner.exception.UnableToProcessNotifyException;
import com.partner.model.Notify;
import com.partner.model.Partnership;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

/**
 * @author Albert Gomes Cabral
 */
@Service
public interface PartnershipNotifyService {
    List<Notify> getPartnershipAllNotification(
            Partnership partnership) throws UnableToProcessNotifyException;

    Notify getPartnershipNotifyById(
            long notifyId) throws UnableToProcessNotifyException;

    void sendPartnershipNotify(
            String[] labels, String send, String receive, String title,
            String message, Time sentTime) throws Exception;
}
