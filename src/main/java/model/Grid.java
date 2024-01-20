package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * {@link Grid} instances represent the grid in <i>The Game of Life</i>.
 */
public class Grid implements Iterable<Cell> {

    private final int numberOfRows;
    private final int numberOfColumns;
    private final Cell[][] cells;

    /**
     * Creates a new {@code Grid} instance given the number of rows and columns.
     *
     * @param numberOfRows    the number of rows
     * @param numberOfColumns the number of columns
     * @throws IllegalArgumentException if {@code numberOfRows} or {@code numberOfColumns} are
     *                                  less than or equal to 0
     */
    public Grid(int numberOfRows, int numberOfColumns) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.cells = createCells();
    }

    /**
     * Returns an iterator over the cells in this {@code Grid}.
     *
     * @return an iterator over the cells in this {@code Grid}
     */

    @Override
    public Iterator<Cell> iterator() {
        return new GridIterator(this);
    }

    private Cell[][] createCells() {
        Cell[][] cells = new Cell[getNumberOfRows()][getNumberOfColumns()];
        for (int rowIndex = 0; rowIndex < getNumberOfRows(); rowIndex++) {
            for (int columnIndex = 0; columnIndex < getNumberOfColumns(); columnIndex++) {
                cells[rowIndex][columnIndex] = new Cell();
            }
        }
        return cells;
    }

    /**
     * Returns the {@link Cell} at the given index.
     *
     * <p>Note that the index is wrapped around so that a {@link Cell} is always returned.
     *
     * @param rowIndex    the row index of the {@link Cell}
     * @param columnIndex the column index of the {@link Cell}
     * @return the {@link Cell} at the given row and column index
     */
    public Cell getCell(int rowIndex, int columnIndex) {
        return cells[getWrappedRowIndex(rowIndex)][getWrappedColumnIndex(columnIndex)];
    }

    private int getWrappedRowIndex(int rowIndex) {
        return (rowIndex + getNumberOfRows()) % getNumberOfRows();
    }

    private int getWrappedColumnIndex(int columnIndex) {
        return (columnIndex + getNumberOfColumns()) % getNumberOfColumns();
    }

    /**
     * Returns the number of rows in this {@code Grid}.
     *
     * @return the number of rows in this {@code Grid}
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * Returns the number of columns in this {@code Grid}.
     *
     * @return the number of columns in this {@code Grid}
     */
    public int getNumberOfColumns() {
        return numberOfColumns;
    }


    public List<Cell> getNeighbors(int rowIndex, int columnIndex) {
        List<Cell> neighbours = new ArrayList<>();

        for (int row = rowIndex - 1; row <= rowIndex + 1; row++)
            for (int column =  columnIndex - 1; column <= columnIndex + 1; column++)
                if (row != rowIndex || column != columnIndex)
                    neighbours.add(getCell(row, column));

        return neighbours;
    }


    public int countAliveNeighbors(int rowIndex, int columnIndex) {
        int aliveNeighboursCount = 0;
        List<Cell> neighbors = getNeighbors(rowIndex,columnIndex);
        for (Cell cell : neighbors)
            if (cell.isAlive())
                aliveNeighboursCount++;

        return aliveNeighboursCount;
    }


    public CellState calculateNextState(int rowIndex, int columnIndex) {
        Cell cell = getCell(rowIndex, columnIndex);
        int cellAliveNeighborsCount = countAliveNeighbors(rowIndex, columnIndex);

        boolean deadRemainsDead = !cell.isAlive() &&
                cellAliveNeighborsCount != 3;
        boolean aliveThenDiesCase = cell.isAlive() &&
                (cellAliveNeighborsCount < 2 || cellAliveNeighborsCount > 3);
        if (deadRemainsDead || aliveThenDiesCase)
            return CellState.DEAD;
        else return CellState.ALIVE;
    }


    public CellState[][] calculateNextStates() {
        CellState[][] nextCellState = new CellState[getNumberOfRows()][getNumberOfColumns()];
        for (int row = 0; row < getNumberOfRows() + 1; row++)
            for (int column = 0; column < getNumberOfColumns(); column++)
                nextCellState[row][column] = calculateNextState(row, column);
        return nextCellState;
    }


    public void updateStates(CellState[][] nextState) {
        for (int row = 0; row < getNumberOfRows() + 1; row++)
            for (int column = 0; column < getNumberOfColumns(); column++)
                getCell(row, column).setState(nextState[row][column]);
    }

    /**
     * Transitions all {@link Cell}s in this {@code Grid} to the next generation.
     *
     * <p>The following rules are applied:
     * <ul>
     * <li>Any live {@link Cell} with fewer than two live neighbours dies, i.e. underpopulation.</li>
     * <li>Any live {@link Cell} with two or three live neighbours lives on to the next
     * generation.</li>
     * <li>Any live {@link Cell} with more than three live neighbours dies, i.e. overpopulation.</li>
     * <li>Any dead {@link Cell} with exactly three live neighbours becomes a live cell, i.e.
     * reproduction.</li>
     * </ul>
     */
    // TODO: Écrire une version correcte de cette méthode.
    public void updateToNextGeneration() {
        for (int row = 0; row < getNumberOfRows() + 1; row++)
            for (int column = 0; column < getNumberOfColumns(); column++)
                getCell(row, column).setState(calculateNextState(row, column));
    }

    /**
     * Sets all {@link Cell}s in this {@code Grid} as dead.
     */
    public void clear() {
        for (int row = 0; row < getNumberOfRows() + 1; row++)
            for (int col = 0; col < getNumberOfColumns(); col++)
                getCell(row, col).setState(CellState.DEAD);
    }

    /**
     * Goes through each {@link Cell} in this {@code Grid} and randomly sets its state as ALIVE or DEAD.
     *
     * @param random {@link Random} instance used to decide if each {@link Cell} is ALIVE or DEAD.
     * @throws NullPointerException if {@code random} is {@code null}.
     */
    // TODO: Écrire une version correcte de cette méthode.
    public void randomGeneration(Random random) {

    }
}
