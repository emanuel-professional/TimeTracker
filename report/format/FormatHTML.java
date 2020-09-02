package report.format;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import j2html.tags.ContainerTag;
import report.elements.Separator;
import report.elements.Subtitle;
import report.elements.Table;
import report.elements.Text;
import report.elements.Title;

import static j2html.TagCreator.body;
import static j2html.TagCreator.h1;
import static j2html.TagCreator.h2;
import static j2html.TagCreator.hr;
import static j2html.TagCreator.html;
import static j2html.TagCreator.p;
import static j2html.TagCreator.table;
import static j2html.TagCreator.td;
import static j2html.TagCreator.th;
import static j2html.TagCreator.tr;




public class FormatHTML implements Format {

    private ContainerTag html;
    private ContainerTag body;
    private String sFile;
    private File file;

    public FormatHTML() {
        html = html();
        body = body();
        html.with(body);
        sFile = "report.html";
        file = new File(this.sFile);
    }

    @Override
    public void writeTitle(final Title title) {
        body.with(h1(title.getContent()));
    }

    @Override
    public void writeSubTitle(final Subtitle subtitle) {
        body.with(h2(subtitle.getContent()));
    }

    @Override
    public void writeText(final Text text) {
        body.with(p(text.getContent()));
    }

    @Override
    public void writeTable(final Table table) {
        ContainerTag tableHTML = table();
        tableHTML.attr("border", 1);

        ContainerTag tr;
        for (int i = 0; i < table.lengthRow(); i++) {
            tr = tr();
            for (int j = 0; j < table.lengthCol(); j++) {
                String content = table.read(i, j);
                if (content != null) {
                    if (i != 0) {
                        tr.with(td(content));
                    } else {
                        tr.with(th(content));
                    }
                } else {
                    tr.with(th(""));
                }
            }
            tableHTML.with(tr);
        }

        body.with(tableHTML);
    }

    @Override
    public void writeSeparator(final Separator separator) {
        body.with(hr());
    }

    @Override
    public void make() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(sFile));
            bw.write(html.renderFormatted());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
