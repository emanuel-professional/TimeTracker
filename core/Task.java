package core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Clase encargada de gestionar las tareas y que
 * extiende de la clase abstracta Activity
 * Almacena los intervalos de tiempo de cada tarea y el tiempo total
 * Es una hoja del patron Composite y la observadora del reloj en el
 * patron Observer
 */
public class Task extends Activity implements PropertyChangeListener  {
    private final Logger logger = LoggerFactory.getLogger(Task.class);
    private List<Interval> intervals;
    private Interval currentInterval;

    /**
     * Constructor que permite inicializar los atributos
     */
    public Task(final String name, final Project father) {
        super(name, father);
        this.intervals = new ArrayList<>();
        this.currentInterval = new Interval();
        this.setCreationDate(Calendar.getInstance());
        this.setTime(Calendar.getInstance());
        this.setRunning(false);
        this.setStopped(false);
        this.resetTime();
        assert this.invariant();
    }

    public void accept(final Visitor v) {
        v.visitTask(this);
    }

    /**
     * Metodo que se encarga de iniciar la tarea creando un nuevo intervalo
     * y añadiendola a la lista de observadores
     */
    public void start() {
        assert this.invariant();
        this.setRunning(true);
        this.setStopped(false);
        this.getFather().setRunning(true);
        this.currentInterval = new Interval();
        core.Clock clock = core.Clock.getInstance();
        clock.addPropertyChangeListener(this);
        assert this.invariant();
    }
    /**
     * Implementación del metodo abstracto update
     * Se encarga de actualizar el tiempo transcurrido
     * en la tarea y de propagar la notificacion
     * @param time tiempo transcurrido
     */
    public void update(final int time) {
        assert this.invariant();
        getFather().update(time);
        this.getTime().add(Calendar.SECOND, time);
        this.getDurationDate().add(Calendar.SECOND, time);
        this.currentInterval.update(time);
        assert this.invariant();
    }

    /**
     * Implementación del metodo abstracto stop
     * Se encarga de detener una tarea y somprueba
     * si el tiempo que ha durado el intervalo es valido
     */
    public void stop() {
        assert this.invariant();
        core.Clock clock = core.Clock.getInstance();
        clock.removePropertyChangeListener(this);
        this.setStopped(true);
        getFather().setStopped(true);

        if (currentInterval.getTime() >= clock.getMinimTime()) {
            intervals.add(this.currentInterval);
        } else {

            // decrementar tiempo no valido
            getFather().update(-1 * currentInterval.getTime());
            this.getTime().add(Calendar.SECOND, -1 * currentInterval.getTime());
            logger.warn("Tiempo del intervalo no valido;"
                    + " es inferior al tiempo minimo");
        }
        assert this.invariant();
    }

    public List<Interval> getIntervals() {
        return this.intervals;
    }


    /**
     * Metodo heredado de PropertyChangeListener
     * y que es detonado cuando se recibe
     * una notificación del reloj
     */
    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        //como utilizamos un patron singleton es necesario
        // recoger la instancia utilizando el nombre de la clase
        assert this.invariant();
        core.Clock clock = Clock.getInstance();
        assert evt.getPropertyName().equals("parameters");
        int value = (Integer) evt.getNewValue();
        update(value);
        assert this.invariant();
    }

    protected boolean invariant() {
        boolean isInvariant;

        isInvariant = !(this.currentInterval == null);
        isInvariant &= this.currentInterval.getTime() >= 0;
        isInvariant &= !(this.intervals == null);

        isInvariant &= !(this.getName().isEmpty());
        isInvariant &= !(this.getFather() == null);

        isInvariant &= !(this.getTime() == null);
        isInvariant &= !(this.getDurationDate() == null);
        isInvariant &= !(this.getCreationDate() == null);

        //isInvariant &= this.getDurationDate().compareTo(this.getCreationDate())>=0;

        return isInvariant;
    }
}
