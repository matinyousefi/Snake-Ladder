package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.logic.LogicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class  Cell {
    private Color color;
    private final int x, y;
    private Transmitter transmitter;
    private Prize prize;
    private Piece piece;
    private final List<Cell> adjacentOpenCells;
    private final List<Cell> adjacentCells;
    private Board board;

    public Cell(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.transmitter = null;
        this.prize = null;
        this.piece = null;
        this.adjacentOpenCells = new ArrayList<>();
        this.adjacentCells = new ArrayList<>();
    }


    public void setBoard(Board board) {
        this.board = board;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public List<Cell> getAdjacentCells() {
        for (Cell cell :
                board.getCells()) {
            if (Math.abs(cell.getX() - this.x) + Math.abs(cell.getY() - this.y) == 1) {
                adjacentCells.add(cell);
            }
        }
        return adjacentCells;
    }
    public List<Cell> getAdjacentCellsThroughVertex() {
        ArrayList<Cell> adjacentCellsThroughVertex = new ArrayList<>(this.getAdjacentCells());
        if(board.getCell(this.x+1, this.y+1)!=null) adjacentCellsThroughVertex.add(board.getCell(this.x+1, this.y+1));
        if(board.getCell(this.x-1, this.y+1)!=null) adjacentCellsThroughVertex.add(board.getCell(this.x-1, this.y+1));
        if(board.getCell(this.x+1, this.y-1)!=null) adjacentCellsThroughVertex.add(board.getCell(this.x+1, this.y-1));
        if(board.getCell(this.x-1, this.y-1)!=null) adjacentCellsThroughVertex.add(board.getCell(this.x-1, this.y-1));
        return adjacentCellsThroughVertex;
    }

    public List<Cell> getAdjacentOpenCells() {
        return adjacentOpenCells;
    }

    public Piece getPiece() {
        return piece;
    }

    public Prize getPrize() {
        return prize;
    }

    public Transmitter getTransmitter() {
        return transmitter;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    public void setTransmitter(Transmitter transmitter) {
        this.transmitter = transmitter;
    }

    /**
     * @return true if piece can enter this cell, else return false
     */
    public boolean canEnter(Piece piece) {

        return (this.color == piece.getColor() || this.color == Color.WHITE)
                && this.color != Color.BLACK    // This two line check the color conditions for a move
                && this.piece == null;
    }


    /**
     * DO NOT CHANGE FOLLOWING METHODS.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static int distance(Cell firstCell, Cell lastCell){
        if(firstCell.getX()==lastCell.getX()){
            return Math.abs(firstCell.getY()- lastCell.getY());
        }
        if(firstCell.getY()==lastCell.getY()){
            return Math.abs(firstCell.getX()- lastCell.getX());
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
