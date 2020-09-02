package core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Clase encargada de gestionar los proyectos y
 * que extiende de la clase abstracta Activity
 * Es una hoja del patron Composite
 */
public class Project extends core.Activity {

    private List<Activity> activities;

    /**
     * Constructor que permite inicializar el nombre del proyecto, y
     * guardar la instancia del proyecto padre (en caso de no tener, sera nulo).
     * @param name nombre del proyecto
     * @param father nodo padre del proyecto
     */
    public Project(final String name, final core.Activity father) {
        super(name, father);
        activities = new ArrayList<>();
        this.setCreationDate(Calendar.getInstance());
        this.setTime(Calendar.getInstance());
        //this.stopped = false;
        //this.running = false;
        this.resetTime();
    }

    /**
     * Método que permite añadir una actividad a la
     * lista de actividades del proyecto
     * @param activity proyecto o tarea que se anexa a la lista de actividades
     */
    public void addActivity(final core.Activity activity) {
        activities.add(activity);
    }


    /**
     * Implementación del metodo abstracto update
     * Se encarga de incrementar el tiempo del proyecto y
     * notifica a su nodo padre del tiempo transcurrido
     * @param time tiempo transcurrido
     */
    public void update(final int time) {
        this.getTime().add(Calendar.SECOND, time);
        this.getDurationDate().add(Calendar.SECOND, time);
        if (this.getFather() != null) {
            getFather().update(time);
        }
    }
    /**
     * Implementación del metodo abstracto stop
     * Se encarga de detener todas las actividades que pertenecen al proyecto
     */
    @Override
    public void stop() {
        //activities.forEach(core.Activity::stop);

        for (Activity activity:activities) {
            activity.stop();
        }

    }
    public List<Activity> getActivities() {
        return  this.activities;
    }

    /**
     * Método que permite aceptar un determinado visitor
     * @param v el visitor
     */
    public void accept(final Visitor v) {
        v.visitProject(this);
    }

























}
