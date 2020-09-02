package report.elements;

import report.format.Format;

public class Text extends Element  {

    private String content;

    public Text(final String content) {
        this.content = content;
    }
    @Override
    public void accept(final Format f) {
        f.writeText(this);
    }

    public String getContent() {
        return this.content;
    }

}
