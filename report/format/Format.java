package report.format;

import report.elements.Separator;
import report.elements.Subtitle;
import report.elements.Table;
import report.elements.Text;
import report.elements.Title;

public interface Format {

    void writeTitle(Title title);
    void writeSubTitle(Subtitle subtitle);
    void writeText(Text text);
    void writeTable(Table table);
    void writeSeparator(Separator separator);

    void make();

}
