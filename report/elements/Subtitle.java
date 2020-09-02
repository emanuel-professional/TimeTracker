package report.elements;

import report.format.Format;

public class Subtitle extends Element {

    private String content;

    public Subtitle(final String content) {
        this.content = content;
    }

    @Override
    public void accept(final Format e) {
        e.writeSubTitle(this);
    }

    public String getContent() {
        return content;
    }


}
