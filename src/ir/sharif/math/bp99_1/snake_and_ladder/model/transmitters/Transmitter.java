package ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

public class Transmitter {
    private final Cell firstCell, lastCell;

    public Transmitter(Cell firstCell, Cell lastCell) {
        this.firstCell = firstCell;
        this.lastCell = lastCell;

    }

    public Cell getFirstCell() {
        return firstCell;
    }

    public Cell getLastCell() {
        return lastCell;
    }

    /**
     * transmit piece to lastCell
     */

    public void transmit(Piece piece) {
        /*
        *       Update the first cell state.
        *       Update the last cell state.
        *       Update the piece state */
        firstCell.setPiece(null);
        piece.setCurrentCell(lastCell);
        lastCell.setPiece(piece);
    }

}
