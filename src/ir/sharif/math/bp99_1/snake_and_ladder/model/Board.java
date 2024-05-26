package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;
import sun.awt.geom.AreaOp;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Board {
    private final List<Cell> cells;
    private final List<Transmitter> transmitters;
    private final List<Wall> walls;
    private final Map<Cell, Integer> startingCells;
    private final int x;
    private final int y;

    public Board(List<Cell> cells, List<Transmitter> transmitters, List<Wall> walls, Map<Cell, Integer> startingCells, int x, int y) {
        this.cells = cells;
        this.transmitters =transmitters;
        this.walls = walls;
        this.startingCells = startingCells;
        this.x=x;
        this.y=y;
    }


    public List<Cell> getCells() {
        return cells;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public Map<Cell, Integer> getStartingCells() {
        return startingCells;
    }

    public List<Transmitter> getTransmitters() {
        return transmitters;
    }


    /**
     * give x,y , return a cell with that coordinates
     * return null if not exist.
     */
    public Cell getCell(int x, int y) {
        for (Cell cell:
             cells) {
            if (cell.getX()==x && cell.getY()==y) return cell;
        }
        return null;
    }

    public boolean canGo(Cell firstCell, Cell lastCell){
        if(firstCell.getY()==lastCell.getY() || firstCell.getX()==lastCell.getX()){
            if(firstCell.getX()==lastCell.getX()){
                for (int i = 1; i < y  ; i++) {
                    Cell cellLeft = getCell(firstCell.getX(),i);
                    Cell cellRight= getCell(firstCell.getX(),i+1);
                    Wall wall = new Wall(cellLeft, cellRight);
                    int min = Math.min(firstCell.getY(), lastCell.getY());
                    int max = Math.max(firstCell.getY(), lastCell.getY());
                    if((i>= min) && (i<max)){
                        for (Wall wallE :
                                walls) {
                            if(wallE.equals(wall)) return false;
                        }
                    }
                }
            }
            if(firstCell.getY()==lastCell.getY()){
                for (int i = 1; i < x  ; i++) {
                    Cell cellLeft = getCell(i,firstCell.getY());
                    Cell cellRight= getCell(i+1,firstCell.getY());
                    Wall wall = new Wall(cellLeft, cellRight);
                    int min = Math.min(firstCell.getX(), lastCell.getX());
                    int max = Math.max(firstCell.getX(), lastCell.getX());
                    if((i>= min) && (i<max)){
                        for (Wall wallE :
                                walls) {
                            if(wallE.equals(wall)) return false;
                        }
                    }
                }
            }
        }
        else{
            return false;
        }
        return true;

    }
}
