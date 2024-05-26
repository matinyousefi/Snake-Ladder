package ir.sharif.math.bp99_1.snake_and_ladder.model.pieces;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;

public class Thief extends Piece {
    Prize prize;
    // can not use other prizes
    //
    public Thief(Player player) {
        super(player, Color.GREEN);
        this.setActive(true);
        prize = null;
    }

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(true);
    }

    @Override
    public void act(){
        //if(!active) return;
        Prize cellPrize = this.getCurrentCell().getPrize();
        if(prize==null){
            if(cellPrize==null) return;
            this.prize=cellPrize;
            this.getCurrentCell().setPrize(null);
        }
        if(prize!=null){
            this.getCurrentCell().setPrize(this.prize);
            this.prize=null;
        }

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