package report;

import java.text.SimpleDateFormat;
import java.util.List;

import core.Project;
import report.elements.Element;
import report.elements.Separator;
import report.elements.Subtitle;
import report.elements.Table;
import report.elements.Text;
import report.elements.Title;

public class BriefReport extends Report {


    public BriefReport(final List<Project> projects, final Period period) {
        super(projects, period);
    }

    @Override
    public void buildReport() {

        Element separator1 = new Separator();
        this.getElements().add(separator1);

        Title title1 = new Title("Informe Breu");
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

        Element separator3 = new Separator();
        this.getElements().add(separator3);


        Subtitle subtitle2 = new Subtitle("Projectes de primer nivell");

        this.getElements().add(subtitle2);


        Table table2 = new Table(1, 4);
        table2.write(0, 1,  String.format("%25s", "Data Inici"));
        table2.write(0, 2,  String.format("%25s", "Data Final"));
        table2.write(0, 3,  String.format("%25s", "Temps Total"));

        SimpleDateFormat formatData;
        formatData =  new SimpleDateFormat("dd/MMM/yyyy");

        SimpleDateFormat formatTime;
        formatTime =  new SimpleDateFormat("HH:mm:ss");

        for (Project pr : this.getProjects()) {

            table2.resize(table2.getNumberOfRows() + 1); // resize table
            table2.write(table2.getNumberOfRows() - 1, 0, pr.getName());
            table2.write(table2.getNumberOfRows() - 1, 1,
                    String.format("%24s",
                            formatData.format(pr.getCreationDate().getTime())));
            table2.write(table2.getNumberOfRows() - 1, 2,
                    String.format("%25s",
                            formatData.format(pr.getTimeActivity().getTime())));
            table2.write(table2.getNumberOfRows() - 1, 3,
                    String.format("%21s",
                            formatTime.format(pr.getTimeActivity().getTime())));
        }

        this.getElements().add(table2);

        Element separator4 = new Separator();
        this.getElements().add(separator4);

        Text text = new Text("TimeTracker v0.1");
        this.getElements().add(text);

    }
}
