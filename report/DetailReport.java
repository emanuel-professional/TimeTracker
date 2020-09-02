package report;

import java.util.List;

import core.Project;
import report.elements.Element;
import report.elements.Separator;
import report.elements.Subtitle;
import report.elements.Table;
import report.elements.Text;
import report.elements.Title;

public class DetailReport extends Report {


    public DetailReport(final List<Project> projects, final Period period) {
        super(projects, period);
    }

    @Override
    public void buildReport() {


        Element separator1 = new Separator();
        this.getElements().add(separator1);

        Title title1 = new Title("Informe detallat");
        this.getElements().add(title1);


        Element separator2 = new Separator();
        this.getElements().add(separator2);

        Subtitle subtitle1 = new Subtitle("Periode");
        this.getElements().add(subtitle1);

        Table table1 = new Table(4, 2);
        table1.write(0, 1, "             Data");
        table1.write(1, 0, "Desde de     ");
        table1.write(2, 0, "Fins a       ");
        table1.write(3, 0, "Data actual  ");

        table1.write(1, 1, "01/01/2010"); // TODO
        table1.write(2, 1, "02/01/2019"); // TODO
        table1.write(3, 1, "29/11/2019"); // TODO

        this.getElements().add(table1);

        /* ----- Recollir Elements -------*/

        CreatorElementVisitor wVisitor = new CreatorElementVisitor(this.getPeriod());

        for (int i = 0; i < getProjects().size(); i++) {
            getProjects().get(i).accept(wVisitor);
            for (int j = 0; j < getProjects().get(i).getActivities().size(); j++) {
                getProjects().get(i).getActivities().get(j).accept(wVisitor);
            }
        }

        for (Element e : wVisitor.getElements()) {
            getElements().add(e);
        }

        Element separator4 = new Separator();
        this.getElements().add(separator4);

        Text text = new Text("TimeTracker v0.1");
        this.getElements().add(text);

    }



}
