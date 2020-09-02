package report;
import java.util.ArrayList;
import java.util.List;
import core.Project;
import report.elements.Element;
import report.format.Format;

public abstract class Report {

    private List<Project> projects; // Proyectes arrel
    private List<Element> elements;
    private Period period;

    public Report(final List<Project> projects, final Period period) {
        this.projects = projects;
        this.period = period;
        this.elements = new ArrayList<>();
    }

    public void write(final Format format) {
        for (Element e : this.elements) {
            e.accept(format);
        }

        format.make();
    }

    public abstract void buildReport();

    protected List<Project> getProjects() {
        return projects;
    }

    protected List<Element> getElements() {
        return elements;
    }

    protected Period getPeriod() {
        return period;
    }
}
