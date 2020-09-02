package report.format;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import report.elements.Separator;
import report.elements.Subtitle;
import report.elements.Table;
import report.elements.Text;
import report.elements.Title;

public class FormatText implements Format {


    private String sFile;
    private File file;
    private String content;


    public FormatText() {
        sFile = "report.txt";
        file = new File(this.sFile);
        content = "";
    }


    @Override
    public void writeTitle(final Title title) {
        this.content += title.getContent() + "\n";
    }

    @Override
    public void writeSubTitle(final Subtitle subtitle) {
        this.content += subtitle.getContent() + "\n";
    }

    @Override
    public void writeText(final Text text) {
        this.content += text.getContent() + "\n";
    }

    @Override
    public void writeTable(final Table table) {

        for (int i = 0; i < table.lengthRow(); i++) {
            for (int j = 0; j < table.lengthCol(); j++) {
                String content = table.read(i, j);
                if (content != null) {
                    this.content += table.read(i, j);
                }
            }
            content += "\n";
        }

    }

    @Override
    public void writeSeparator(final Separator separator) {
        content += separator.getLine() + "\n";
    }

    @Override
    public void make() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(sFile));
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
