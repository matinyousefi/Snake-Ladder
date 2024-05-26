package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Sniper extends Piece {
    public Sniper(Player player) {
        super(player, Color.RED);
    }


    @Override
    public void act(){
        if(!active) return;

        for (Cell cell :
                super.getCurrentCell().getAdjacentCells()) { //   For every adjacent cell
            if(cell.getPiece()!= null){ //  Adjacent cell is not empty
                if(cell.getPiece().getPlayer()==super.getPlayer().getRival()){// Adjacent piece belong to the rival
                    cell.getPiece().setAlive(false);// Kill
                }
            }
        }

    }
}