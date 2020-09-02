package report.elements;

import report.format.Format;

public class Table extends Element {

    private String[][] table;

    public Table(final int rows, final int cols) {
        this.table = new String[rows][cols];
    }

    @Override
    public void accept(final Format e) {
        e.writeTable(this);
    }
    // numero de filas
    public int lengthRow() {
        return this.table.length;
    }
    // numero de columnas
    public int lengthCol() {
        return this.table[0].length;
    }


    public void resize(final int rows) {
        // each row has the same size
        String[][] aux = new String[rows][table[0].length];

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                aux[i][j] = table[i][j];
            }
        }

        table = aux;
    }

    public int getNumberOfRows() {
       return this.table.length;
    }

    public void write(final int row, final int col, final String text) {
        this.table[row][col] = text;
    }

    public String read(final int row, final int col) {
        return table[row][col];
    }







}
