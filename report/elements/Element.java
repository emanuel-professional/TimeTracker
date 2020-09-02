package report.elements;

import report.format.Format;

public abstract class Element {
    public abstract void accept(Format f);
}
