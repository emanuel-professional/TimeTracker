package core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Clase reloj encargada de gestionar el tiempo y notificar a las tareas
 * Se utiliza como observable en el patron Observer
 */
public final class Clock  extends Thread {
    final Logger logger = LoggerFactory.getLogger(Clock.class);
    private static Clock clock;
    //lista en la que se guardaran las actividades observadoras
    private PropertyChangeSupport support;
    private boolean stop;
    //tiempo que transcurre entre cada notificacion del observable
    private int timeNotify;
    //tiempo minimo que ha de durar un intervalo para que se tenga en cuenta
    private int minimTime;

    private static final int MILLIS = 1000;
    /**
     * Constructor que permite iniciar el reloj y configurar los tiempos
     * de notifiacion
     */
    private Clock() {
        support = new PropertyChangeSupport(this);
        stop = false;
        this.timeNotify = 1;
        this.minimTime = 1;
    }

    /**
     * Metodo que devuelve la instancia del reloj y la crea si no existe
     * Con esto implementamos el patron Singleton y mantenemos siempre
     * la misma instancia del reloj
     */
    public static Clock getInstance() {
        if (clock == null) {
            clock = new Clock();
        }
        return clock;
    }

    /**
     * Implementacion del metodo abstracto run de la clase thread
     * Se encarga de notificar a los observadores cuando transcurre
     * el tiempo definido
     */
    public void run() {
        int counterTime = 0;
        try {
            while (!stop) {
                //hacemos que transcurra un segundo y aumentamos el contador
                Thread.sleep(MILLIS);
                /*System.out.println("<--"+1+"s-->"); */
                counterTime += 1;

                if (counterTime == this.timeNotify) {
                    support.firePropertyChange("parameters", 0, counterTime);
                    counterTime = 0;
                }

            }

        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }

    }

    /**
     * Metodo que añade un observador a la lista
     * @param pcl tarea que queremos añadir a la
     *  lista de observadores del reloj
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    /**
     * Metodo que quita un observador de la lista
     * @param pcl tarea que queremos quitar de la
     *  lista de observadores del reloj
     */
    public void removePropertyChangeListener(final PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void setTimeNotify(final int timeNotify) {
        this.timeNotify = timeNotify;
    }

    public void setMinimTime(final int minimTime) {

        this.minimTime = minimTime;
    }

    public int getMinimTime() {

        return this.minimTime;
    }

    public int getTimeNotify() {
        return this.timeNotify;
    }


    public void setStop(final boolean stop) {
        this.stop = stop;
    }
}
