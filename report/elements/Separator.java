package report.elements;

import report.format.Format;

public class Separator extends Element {
    private static final int LENGHT = 85;
    @Override
    public void accept(final Format e) {
        e.writeSeparator(this);
    }

    public String getLine() {
        String s = "-";

        for (int i = 0; i < LENGHT; i++) {
            s += "-";
        }
        return s;
    }
}
