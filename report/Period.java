package report;

import java.util.Calendar;

public class Period {
    private Calendar initDate;
    private Calendar  finalDate;

    public Period(final Calendar initDate, final Calendar finalDate) {
        this.initDate = initDate;
        this.finalDate = finalDate;
    }

    public boolean isInside(final Calendar initDate, final Calendar finalDate) {

        //Esta dentro del intervalo
        boolean inner =
            (this.initDate.getTimeInMillis() < initDate.getTimeInMillis())
            &&
            (this.finalDate.getTimeInMillis() > finalDate.getTimeInMillis());
        //Comienza antes del intervalo pero acaba dentro.
        boolean parcialBegin =
            (this.initDate.getTimeInMillis() > initDate.getTimeInMillis())
            &&
            (this.initDate.getTimeInMillis() < finalDate.getTimeInMillis());
        //Comieza dentro del intervalo pero acaba fuera.
        boolean parcialEnd =
            (this.finalDate.getTimeInMillis() > initDate.getTimeInMillis())
            &&
            (this.finalDate.getTimeInMillis() < finalDate.getTimeInMillis());


        return inner || parcialBegin || parcialEnd;
    }

    public Calendar getInitDate() {
        return initDate;
    }

    public Calendar getFinalDate() {
        return finalDate;
    }
}

