package core;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Clase encargada de gestionar los intervalos de tiempo que despues usaran
 * las tareas
 */
public class Interval implements Serializable {

    private Calendar creationDate;
    private Calendar durationDate;
    private Calendar time;
    private int seconds;

    public Interval() {
        this.creationDate = Calendar.getInstance();
        this.durationDate = Calendar.getInstance();
        this.time = Calendar.getInstance();
        resetTime();
    }

/**
 * Metodo encargado de actualizar el tiempo
 * @param time cantidad de tiempo en la que hay que aumentar el intervalo
 */
    public void update(final int time) {
        this.time.add(Calendar.SECOND, time);
        this.durationDate.add(Calendar.SECOND, time);
        this.seconds += time;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public Calendar getDurationDate() {
        return durationDate;
    }

    protected void resetTime() {
        time.set(Calendar.HOUR_OF_DAY,
                time.getActualMinimum(Calendar.HOUR_OF_DAY));
        time.set(Calendar.MINUTE,      time.getActualMinimum(Calendar.MINUTE));
        time.set(Calendar.SECOND,      time.getActualMinimum(Calendar.SECOND));
        time.set(Calendar.MILLISECOND,
                time.getActualMinimum(Calendar.MILLISECOND));
    }

    public void setDurationDate(final Calendar durationDate) {
        this.durationDate = durationDate;
    }

    public int getTime() {
        return seconds;
    }

    public Calendar getTimeCalendar() {
        return time;
    }

    public void setTime(final Calendar time) {
        this.time = time;
    }
}
