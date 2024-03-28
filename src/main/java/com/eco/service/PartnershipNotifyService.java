package com.eco.service;

import com.eco.exception.UnableToProcessNotifyException;
import com.eco.model.Notify;
import com.eco.model.Partnership;
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
