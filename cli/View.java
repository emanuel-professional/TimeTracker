package cli;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import core.Activity;
import core.Clock;
import core.PrinterVisitor;
import core.Visitor;

/**
 * Clase encargada de gestionar toda la información
 * necesaria para desplegar al usuario la "interfaz de consola"
 */
public class View implements PropertyChangeListener {

    private int timeRefresh;
    private int actuallyTime;
    private Activity activity;

    /**
     * Constructor que permite inicializar la vista
     * @param timeRefresh tiempo de refresco
     * @param activity nodo raiz del arbol de proyectos y tareas
     */
    public View(int timeRefresh, Activity activity){
        this.timeRefresh = timeRefresh;
        this.activity = activity;
        this.actuallyTime = 0;
        Clock clock = Clock.getInstance();
        clock.addPropertyChangeListener(this);
    }

    /**
     * Metodo encargado de actualizar la vista en funcion del tiempo de refresco
     */
    public void update(int time){
        Visitor visitor = new PrinterVisitor();

        this.actuallyTime+= time;
        if(timeRefresh<=actuallyTime){
            System.out.println("");
            System.out.println("Nom   Temps inici           Temps final           Durada (hh:mm:ss)");
            System.out.println("-----+---------------------+---------------------+-----------------");
            activity.accept(visitor);
            this.actuallyTime = 0;
            System.out.println("");
        }

    }

    /**
     * Metodo que se detona una vez el reloj notifica
     * @param evt
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        assert evt.getPropertyName().equals("parameters");
        int value = (Integer) evt.getNewValue();
        update(value);
    }
}
