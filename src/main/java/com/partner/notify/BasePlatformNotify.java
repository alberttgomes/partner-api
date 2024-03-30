package com.partner.notify;


import com.partner.exception.UnableToProcessNotifyException;
import com.partner.model.Notify;
import com.partner.persistence.NotifyPersistence;

import java.sql.Time;

/**
 * @author Albert Gomes Cabral
 */
public abstract class BasePlatformNotify {
    protected abstract Object getNotify(
            long notifyId) throws UnableToProcessNotifyException;

    protected abstract void sendNotify(
            String[] labels, String send, String receive, String title,
            String message, Time sentTime) throws Exception;

    protected void removeNotify(
            long notifyId) throws UnableToProcessNotifyException {

        if (notifyId > 0) {
            System.out.println(
                    "Invalid input value to notifyId " + notifyId);
            return;
        }

        Notify notify = _notifyPersistence.getReferenceById(notifyId);

        _notifyPersistence.delete(notify);

        System.out.println("Removed notify with the primary key " + notifyId);
    };

    private NotifyPersistence _notifyPersistence;
}
