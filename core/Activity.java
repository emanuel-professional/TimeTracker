package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Clase abstacta que define atributos y metodos basicos para todos
 * los tipos de actividades: proyectos, subproyectos, tareas
 * Es el nodo padre del patron Composite
 */
public abstract class Activity implements Serializable {

    private final Logger logger = LoggerFactory.getLogger(Clock.class);

    private Activity father;
    private String name;
    private String description;
    private Calendar creationDate;
    private Calendar durationDate;
    private Calendar time;
    private boolean stopped;
    private boolean running;

    /**
     * Constructor que permite inicializar una actividad
     * @param name de la actividad
     * @param activity nodo padre de la actividad actual
     */
    public Activity(final String name, final Activity activity) {
        this.name = name;
        this.father = activity;
        this.creationDate = Calendar.getInstance();
        this.time = Calendar.getInstance();
        this.durationDate = Calendar.getInstance();
        resetTime();
        if (activity == null) logger.info("Creación del proyecto raíz");
    }

    public String getName() {
        return this.name;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    /**
     * Método encargado de retornar el tiempo total
     * de la actividad en solo formato tiempo
     * @return tiempo de actividad
     */
    public Calendar getTimeActivity() {
        return this.time;
    }

    public Calendar getDurationDate() {
        return this.durationDate;
    }

    /**
     * Método encargado de incrementar el tiempo de la actividad
     */
    public abstract void update(int time);

    /**
     * Método encargado de parar la actividad
     */
    public abstract void stop();

    /**
     * Método que permite aceptar un determinado visitor
     * @param v el visitor
     */
    public void accept(final Visitor v) { }

    /**
     * Metodo encargado de reiniciar los valores de cada campo del tiempo a 0
     */
    protected void resetTime() {
        time.set(Calendar.HOUR_OF_DAY,
                time.getActualMinimum(Calendar.HOUR_OF_DAY));
        time.set(Calendar.MINUTE,      time.getActualMinimum(Calendar.MINUTE));
        time.set(Calendar.SECOND,      time.getActualMinimum(Calendar.SECOND));
        time.set(Calendar.MILLISECOND,
                time.getActualMinimum(Calendar.MILLISECOND));
        logger.debug("Reset del tiempo de la aplicacion; la variable time toma un valor de {}", this.getTime().getTime());
    }

    public boolean isStopped() {
        return this.stopped;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(final boolean state){
        this.running = state;
    }

    public void setStopped(final boolean state) {
        this.stopped = state;
    }

    public Activity getFather() {
        return father;
    }

    public String getDescription() {
        return description;
    }

    public Calendar getTime() {
        return time;
    }

    protected void setCreationDate(final Calendar creationDate) {
        this.creationDate = creationDate;
    }

    protected void setTime(final Calendar time) {
        this.time = time;
    }


    public String getNameFather() {
        return this.father.getName();
    }
}
