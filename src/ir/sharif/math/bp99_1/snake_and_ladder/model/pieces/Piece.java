package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;
import ir.sharif.math.bp99_1.snake_and_ladder.model.*;

public class Piece {
    private Cell currentCell;
    private final Color color;
    private final Player player;
    private boolean isSelected;
    protected boolean alive;
    protected boolean active;

    public Piece(Player player, Color color) {
        this.color = color;
        this.player = player;
        this.alive = true;
        // set true to test
        this.active = true;
    }

    public Player getPlayer() {
        return player;
    }

    public Color getColor() {
        return color;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive(){return active;}

    public boolean getAlive(){return alive;}

    /**
     * @return "true" if your movement is valid  , else return " false"
     * <p>
     * In this method, you should check if the movement is valid of not.
     * <p>
     * You can use some methods ( they are recommended )
     * <p>
     * 1) "canEnter" method in class "Cell"
     * <p>
     * if your movement is valid, return "true" , else return " false"
     */


    public boolean isValidMove(Cell destination, int diceNumber) {

       return
               Cell.distance(this.currentCell,destination) == diceNumber // the distance condition
               && destination.canEnter(this);
    }

    /**
     * @param destination move selected piece from "currentCell" to "destination"
     */
    public void moveTo(Cell destination) {
        this.currentCell.setPiece(null);
        this.currentCell=destination;
        destination.setPiece(this);
        if(this.color == destination.getColor()) this.player.applyOnScore(4);
        player.usePrize(destination.getPrize());
    }


    public void act(){}
}
