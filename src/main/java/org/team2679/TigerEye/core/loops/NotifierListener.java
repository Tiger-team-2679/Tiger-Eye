package org.team2679.TigerEye.core.loops;

/**
 * An object used as a runnable by the notifier
 *
 * @author  SlowL0ris
 */
public interface NotifierListener {

    /**
     * the function will be called every time the notifier updates
     */
    void onUpdate();
}
