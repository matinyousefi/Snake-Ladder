package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;

public class Healer extends Piece {
    public Healer(Player player) {
        super(player, Color.YELLOW);
    }


    @Override
    public void setAlive(boolean alive) { this.alive = true; }

    @Override
    public void act(){
        if(!active) return;

        for (Cell cell :
              super.getCurrentCell().getAdjacentCells()) { //   For every adjacent cell
            if(cell.getPiece()!= null){ //  Adjacent cell is not empty
                if(cell.getPiece().getPlayer()==super.getPlayer()){// Adjacent piece belong to the same player
                    cell.getPiece().setAlive(true);// Heal
                }
            }
        }

    }
}
