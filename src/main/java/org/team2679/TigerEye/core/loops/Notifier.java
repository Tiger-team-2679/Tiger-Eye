package org.team2679.TigerEye.core.loops;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * An object used to periodically call tasks around the system
 *
 * @author SlowL0ris
 */
public class Notifier {

    private static CopyOnWriteArrayList<NotifierListener> listeners = new CopyOnWriteArrayList<>();
    private static int rate = 100;
    private static Thread notifierThread;

    /**
     * Begins on running the notifier
     */
    void run(){
        // TODO implement run
    }

    /**
     * registers a listener in the notifier
     * @param listener
     */
    void registerListener(NotifierListener listener){
        // TODO implement the register function, should register a notifier listener
    }

    /**
     * removes a listener from the notifier
     * @param listener
     */
    void removeListener(NotifierListener listener){
        // TODO implement
    }
}
