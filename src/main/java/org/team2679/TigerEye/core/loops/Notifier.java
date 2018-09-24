package org.team2679.TigerEye.core.loops;

import org.team2679.TigerEye.lib.log.Logger;
import org.team2679.TigerEye.lib.logic.Timer;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * An object used to periodically call tasks around the system
 * each notifier run is called a tick, which runs at the given rate
 * if the task takes more then the given rate time, the system will skip a tick
 *
 * therefore the system will always run at a rate modulus to the given rate.
 *
 * @author SlowL0ris
 */
public class Notifier implements Runnable {

    private static Logger notifiersLogger = new Logger("Notifiers Log");

    private CopyOnWriteArrayList<NotifierListener> listeners = new CopyOnWriteArrayList<>();

    private int rate;
    private String name;

    private Thread notifierThread;
    private boolean isRunning = false;

    private int skipes_in_a_row = 0;
    private int skip_num;

    /**
     * creates a notifier object with the given rate
     * @param name the name of the notifier
     * @param rate the rate in milliseconds
     */
    public Notifier(String name, int rate){
        this.rate = rate;
        this.name = name;
    }

    /**
     * Begins on running the notifier
     */
    public void run(){
        notifiersLogger.info("Notifier \"" + this.name +  "\" has started");
        // set up a new time
        while (isRunning){
            try {
                double startTime = Timer.getCurrentTimeMillis();
                for (NotifierListener listener : this.listeners) {
                    listener.onUpdate();
                }
                double endTime = Timer.getCurrentTimeMillis();

                double elapse = endTime - startTime;

                skipes_in_a_row = 0;
                if (elapse < rate) {
                    Thread.sleep((long) (rate - elapse));
                    skip_num = 0;
                }
                else if(rate < elapse){
                    skipes_in_a_row = (int)(elapse / rate);
                    skip_num++;
                    if (skip_num < 3) {
                        notifiersLogger.warning("Notifier \"" + this.name + "\" skipped " + skipes_in_a_row + " ticks");
                    } else if (skip_num == 3) {
                        notifiersLogger.warning("Notifier \"" + this.name + "\" is having too many consecutive skipped ticks, suppressing log.");
                    }
                    Thread.sleep((long) (elapse % rate));
                }
                if(isEmpty()){
                    notifiersLogger.warning("Notifier \"" + this.name + "\" have no listeners , stopping notifier");
                }
            }
            catch (Exception e){ }
        }
        notifiersLogger.info("Notifier \"" + this.name +  "\" has stopped");
    }

    /**
     * registers a listener in the notifier
     * @param listener
     */
    public void registerListener(NotifierListener listener){
        this.listeners.add(listener);
    }

    /**
     * removes a listener from the notifier
     * @param listener
     */
    public void removeListener(NotifierListener listener){
        this.listeners.remove(listener);
    }

    /**
     * this is used to.. well... start the notifier
     */
    public void start(){
        if(!isRunning) {
            isRunning = true;
            this.notifierThread = new Thread(this);
            this.notifierThread.setName("Notifier " + this.name);
            this.notifierThread.start();
        }
    }

    /**
     * I couldn't think of another purpose to a function with this name
     */
    public void stop(){
        isRunning = false;
    }

    /**
     * check if there are any listeners to run
     */
    public boolean isEmpty(){
        if(listeners.size() == 0){
            return true;
        }
        return false;
    }
}
