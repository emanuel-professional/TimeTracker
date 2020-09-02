package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 * Clase que implementa la interfaz Visitor y que contiene
 * los metodos que muestran por pantalla la información
 * relevante de una actividad
 */
public class PrinterVisitor implements Visitor {

    final Logger logger = LoggerFactory.getLogger(PrinterVisitor.class);
    private SimpleDateFormat formatDateAndTime;
    private SimpleDateFormat formatTime;

    public PrinterVisitor() {
        //le da a la fecha formato de dia, mes, año y de hora, minuto, segundo
        formatDateAndTime =  new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss");
        formatTime = new SimpleDateFormat("HH:mm:ss");
    }
    /**
     *  Método encargado de mostrar por pantalla
     *  la información de la actividad
     * @param activity a mostrar por pantalla
     */
    public void printInformation(final core.Activity activity) {

        String creationDate = "";
        String time = "";
        String durationDate = "";

        if (activity.isRunning()) {
            creationDate = formatDateAndTime.format(
                    activity.getCreationDate().getTime());
        }

        if (activity.isStopped()) {
            durationDate = formatDateAndTime.format(
                    activity.getDurationDate().getTime());
        }

        time = formatTime.format(activity.getTimeActivity().getTime());

        System.out.println(String.format("%2s %23s %21s %9s",
                activity.getName(), creationDate, durationDate, time));

    }

    @Override
    public void visitTask(final Task task) {
        printInformation(task);
    }

    @Override
    public void visitProject(final Project pr) {
        printInformation(pr);
        for (Activity activity:pr.getActivities()) {
            activity.accept(this);
        }
    }
}
