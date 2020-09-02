package report.elements;

import report.format.Format;

public class Title extends Element {

    private String content;

    public Title(final String content) {
        this.content = content;
    }

    @Override
    public void accept(final Format e) {
        e.writeTitle(this);
    }

    public String getContent() {
        return content;
    }


}
