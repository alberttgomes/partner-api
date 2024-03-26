package com.eco.notify;


import java.sql.Time;

/**
 * @author Albert Gomes Cabral
 */
public abstract class BasePlatformNotify {
    public abstract Object getNotify(long notifyId) throws Exception;

    public abstract void sendNotify(
            String[] labels, String send, String receive, String title,
            String message, Time sentTime) throws Exception;

    protected void removeNotify(long notifyId) throws Exception {
        if (notifyId > 0) {
            System.out.println(
                    "Invalid input value to notifyId " + notifyId);
            return;
        }

        // WIP

        System.out.println("remove notify with the primary key " + notifyId);
    };
}
