package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Bomber extends Piece {
    public Bomber(Player player) {
        super(player, Color.BLUE);
    }

    @Override
    public void act(){
        if(!active) return;

        for (Cell cell :
                super.getCurrentCell().getAdjacentCellsThroughVertex()) { //   For every adjacent cell
            if(cell.getPiece()!= null){ //  Adjacent cell is not empty
                cell.getPiece().setAlive(false);// Kill
            }
            this.getCurrentCell().setColor(Color.BLACK);
        }

    }
}