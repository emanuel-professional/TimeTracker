package report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import core.Interval;
import core.Project;
import core.Task;
import core.Visitor;
import report.elements.Element;
import report.elements.Separator;
import report.elements.Subtitle;
import report.elements.Table;
import report.elements.Text;

public class CreatorElementVisitor implements Visitor {

    private Period period;
    private Table tableTask;
    private Table tableIntervals;
    private Table tableSubProjects;
    private Table tableProjects;
    private List<Element> elements;
    private List<Element> elementsTask;
    private List<Element> elementsIntervals;
    private List<Element> elementsSubProjects;
    private List<Element> elementsProjects;
    private SimpleDateFormat formatData;
    private SimpleDateFormat formatTime;

    public CreatorElementVisitor(final Period period) {

        this.period = period;

        this.formatData = new SimpleDateFormat("dd/MM/yyyy");
        this.formatTime = new SimpleDateFormat("HH:mm:ss");

        this.tableTask = new Table(1, 5);
        this.tableIntervals = new Table(1, 6);
        this.tableSubProjects = new Table(1, 5);
        this.tableProjects = new Table(1, 4);

        this.elements = new ArrayList<>();
        this.elementsTask = new ArrayList<>();
        this.elementsIntervals = new ArrayList<>();
        this.elementsSubProjects = new ArrayList<>();
        this.elementsProjects = new ArrayList<>();

        createHeaderProject();
        createHeaderSubProject();
        createHeaderTask();
        createHeaderInterval();
    }

    private void createHeaderProject() {
        Element separator1 = new Separator();
        Subtitle subtitle = new Subtitle("Projectes de primer nivell");
        this.tableProjects.write(0, 1, String.format("%25s", "Data inici"));
        this.tableProjects.write(0, 2, String.format("%21s", "Data final"));
        this.tableProjects.write(0, 3, String.format("%21s", "Temps Total"));

        this.elementsProjects.add(separator1);
        this.elementsProjects.add(subtitle);

    }

    private void createHeaderSubProject() {
        Element separator1 = new Separator();
        Subtitle subtitle = new Subtitle("Subprojectes");
        Text description =
                new Text("S’inclouen en la seguent taula "
                        + "nomes els subprojectes que tinguin alguna "
                        + "tasca amb algun interval " + "dins del periode.");

        this.tableSubProjects.write(0, 1, String.format("%25s", "Dins de"));
        this.tableSubProjects.write(0, 2, String.format("%25s", "Data inici"));
        this.tableSubProjects.write(0, 3, String.format("%25s", "Data final"));
        this.tableSubProjects.write(0, 4, String.format("%25s", "Temps Total"));

        this.elementsSubProjects.add(separator1);
        this.elementsSubProjects.add(subtitle);
        this.elementsSubProjects.add(description);

    }

    private void createHeaderTask() {
        Element separator1 = new Separator();
        Subtitle subtitle = new Subtitle("Tasques");
        Text description =
                new Text("S’inclouen en la seguent taula"
                        + " la durada de totes tasques i el projecte"
                        + " al qual pertanyen.");
        this.tableTask.write(0, 1, String.format("%25s", "Projecte"));
        this.tableTask.write(0, 2, String.format("%25s", "Data inici"));
        this.tableTask.write(0, 3, String.format("%25s", "Data final"));
        this.tableTask.write(0, 4, String.format("%25s", "Temps Total"));

        this.elementsTask.add(separator1);
        this.elementsTask.add(subtitle);
        this.elementsTask.add(description);
    }


    private void createHeaderInterval() {
        Element separator1 = new Separator();
        Subtitle subtitle = new Subtitle("Intervals");
        Text description =
                new Text("S’inclouen en la seguent taula"
                        + " el temps d’inici, final i durada de"
                        + " \n tots els intervals entre la data "
                        + "inicial i final especificades, i la tasca"
                        + " i projecte al qual pertanyen.");
        this.tableIntervals.write(0, 0, String.format("%1s", "Tasca"));
        this.tableIntervals.write(0, 1, String.format("%28s", "Projecte"));
        this.tableIntervals.write(0, 2, String.format("%24s", "Número"));
        this.tableIntervals.write(0, 3, String.format("%22s", "Data inici"));
        this.tableIntervals.write(0, 4, String.format("%22s", "Data final"));
        this.tableIntervals.write(0, 5, String.format("%22s", "Durada"));

        this.elementsIntervals.add(separator1);
        this.elementsIntervals.add(subtitle);
        this.elementsIntervals.add(description);

    }

    @Override
    public void visitTask(final Task task) {

        // if one part is inside , some on the intervals is inside
        if (isInPeriod(task.getCreationDate(), task.getDurationDate())) {
            //resize matrix
            this.tableTask.resize(this.tableTask.getNumberOfRows() + 1);
            this.tableTask.write(this.tableTask.getNumberOfRows() - 1, 0,
                    String.format("%1s", task.getName()));
            this.tableTask.write(this.tableTask.getNumberOfRows() - 1, 1,
                    String.format("%25s", task.getNameFather()));
            this.tableTask.write(this.tableTask.getNumberOfRows() - 1, 2,
                    String.format("%25s",
                        formatData.format(task.getCreationDate().getTime())));
            this.tableTask.write(this.tableTask.getNumberOfRows() - 1, 3,
                    String.format("%23s",
                        formatData.format(task.getTimeActivity().getTime())));
            this.tableTask.write(this.tableTask.getNumberOfRows() - 1, 4,
                    String.format("%23s",
                        formatTime.format(task.getTimeActivity().getTime())));

            int numberOfInterval = 1;
            for (Interval in : task.getIntervals()) {

                if (isInPeriod(in.getCreationDate(), in.getDurationDate())) {
                    //resize matrix
                    this.tableIntervals.resize(
                            this.tableIntervals.getNumberOfRows() + 1);
                    this.tableIntervals.write(this.tableIntervals.getNumberOfRows() - 1, 0,
                            String.format("%1s", task.getName()));
                    this.tableIntervals.write(this.tableIntervals.getNumberOfRows() - 1, 1,
                            String.format("%25s", task.getNameFather()));
                    this.tableIntervals.write(this.tableIntervals.getNumberOfRows() - 1, 2,
                            String.format("%25s", Integer.toString(numberOfInterval)));
                    this.tableIntervals.write(this.tableIntervals.getNumberOfRows() - 1, 3,
                            String.format("%27s",
                                    formatData.format(in.getCreationDate().getTime())));
                    this.tableIntervals.write(this.tableIntervals.getNumberOfRows() - 1, 4,
                            String.format("%23s",
                                    formatData.format(in.getTimeCalendar().getTime())));
                    this.tableIntervals.write(this.tableIntervals.getNumberOfRows() - 1, 5,
                            String.format("%22s",
                                    formatTime.format(in.getTimeCalendar().getTime())));
                }

                numberOfInterval++;
            }
        }
    }

    @Override
    public void visitProject(final Project pr) {

        if (pr.getFather() != null) {

            if (isInPeriod(pr.getCreationDate(), pr.getDurationDate())) {
                //resize matrix
                this.tableSubProjects.resize(
                        this.tableSubProjects.getNumberOfRows() + 1);
                this.tableSubProjects.write(this.tableSubProjects.getNumberOfRows() - 1, 0,
                        String.format("%1s", pr.getName()));
                this.tableSubProjects.write(this.tableSubProjects.getNumberOfRows() - 1, 1,
                        String.format("%22s", pr.getNameFather()));
                this.tableSubProjects.write(this.tableSubProjects.getNumberOfRows() - 1, 2,
                        String.format("%27s", formatData.format(pr.getCreationDate().getTime())));
                this.tableSubProjects.write(this.tableSubProjects.getNumberOfRows() - 1, 3,
                        String.format("%23s", formatData.format(pr.getTimeActivity().getTime())));
                this.tableSubProjects.write(this.tableSubProjects.getNumberOfRows() - 1, 4,
                        String.format("%23s", formatTime.format(pr.getTimeActivity().getTime())));
            }

        } else {
            this.tableProjects.resize(this.tableProjects.getNumberOfRows() + 1);
            this.tableProjects.write(this.tableProjects.getNumberOfRows() - 1, 0,
                    pr.getName());
            this.tableProjects.write(this.tableProjects.getNumberOfRows() - 1, 1,
                    String.format("%24s", formatData.format(pr.getCreationDate().getTime())));
            this.tableProjects.write(this.tableProjects.getNumberOfRows() - 1, 2,
                    String.format("%21s", formatData.format(pr.getTimeActivity().getTime())));
            this.tableProjects.write(this.tableProjects.getNumberOfRows() - 1, 3,
                    String.format("%21s", formatTime.format(pr.getTimeActivity().getTime())));
        }
    }

    public List<Element> getElements() {

        for (Element e : this.elementsProjects) {
            this.elements.add(e);
        }
        this.elements.add(tableProjects);

        for (Element e : this.elementsSubProjects) {
            this.elements.add(e);
        }
        this.elements.add(tableSubProjects);

        for (Element e : this.elementsTask) {
            this.elements.add(e);
        }
        this.elements.add(tableTask);

        for (Element e : this.elementsIntervals) {
            this.elements.add(e);
        }
        this.elements.add(tableIntervals);

        return this.elements;
    }


    public boolean isInPeriod(final Calendar dateIni, final Calendar dateFinal) {
        //return Period.isInside(initDate,finalDate);
        return true;
    }
}
