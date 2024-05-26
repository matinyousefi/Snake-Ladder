package ir.sharif.math.bp99_1.snake_and_ladder.logic;


import ir.sharif.math.bp99_1.snake_and_ladder.graphic.GraphicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.GameState;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This class is an interface between logic and graphic.
 * some methods of this class, is called from graphic.
 * DO NOT CHANGE ANY PART WHICH WE MENTION.
 */
public class LogicalAgent {
    private final ModelLoader modelLoader;
    private final GraphicalAgent graphicalAgent;
    private final GameState gameState;

    /**
     * DO NOT CHANGE CONSTRUCTOR.
     */
    public LogicalAgent() {
        this.graphicalAgent = new GraphicalAgent(this);
        this.modelLoader = new ModelLoader();
        this.gameState = loadGameState();
    }


    /**
     * NO CHANGES NEEDED.
     */
    private GameState loadGameState() {
        Board board = modelLoader.loadBord();
        Player player1 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(1), 1);
        Player player2;
        do {
            player2 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(2), 2);
        } while (player1.equals(player2));
        player1.setRival(player2);
        player2.setRival(player1);
        return new GameState(board, player1, player2);
    }

    /**
     * NO CHANGES NEEDED.
     */
    public void initialize() {
        graphicalAgent.initialize(gameState);
    }

    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who clicks "ReadyButton".) you should somehow change that player state.
     * if both players are ready. then start the game.
     */

    public void readyPlayer(int playerNumber) {
        Player player;
        if (playerNumber == 1) player = gameState.getPlayer1();
        else  player = gameState.getPlayer2();
        player.setReady(!player.isReady());
        if(gameState.getPlayer1().isReady() && gameState.getPlayer2().isReady() && !gameState.isStarted()) {
            Map<Cell, Integer> startingCells = gameState.getBoard().getStartingCells();
            ArrayList<Piece> pieces1 = new ArrayList<>();
            ArrayList<Piece> pieces2 = new ArrayList<>();
            Piece piece = new Piece(gameState.getPlayer(1),Color.WHITE);
            for (Cell cell:
                 startingCells.keySet()) {
                        int playerOfCell = gameState.getPlayer(startingCells.get(cell)).getPlayerNumber();

                switch (cell.getColor()){
                            case RED:
                                piece = new Sniper(gameState.getPlayer(startingCells.get(cell)));
                                break;
                            case BLUE:
                                piece = new Bomber(gameState.getPlayer(startingCells.get(cell)));
                                break;
                            case GREEN:
                                piece = new Thief(gameState.getPlayer(startingCells.get(cell)));
                                break;
                            case YELLOW:
                                piece = new Healer(gameState.getPlayer(startingCells.get(cell)));
                                break;
                        }
                        cell.setPiece(piece);
                        piece.setCurrentCell(cell);
                 if(playerOfCell==1) pieces1.add(piece);
                 if(playerOfCell==2) pieces2.add(piece);
            }
            gameState.getPlayer1().setPieces(pieces1);
            gameState.getPlayer2().setPieces(pieces2);
            gameState.nextTurn();

        }
        // do not touch this line
        graphicalAgent.update(gameState);
    }

    /*
      give x,y (coordinates of a cell) :
      you should handle if user want to select a piece
      or already selected a piece and now want to move it to a new cell
    */

    static boolean hasPut;

    public void selectCell(int x, int y) {

        if(gameState.getTurn()==0) return; // No fooling around before game starts

        boolean isDicePlayedThisTurn = gameState.getCurrentPlayer().isDicePlayedThisTurn(); // No move allowed before rolling the dice
        if(!isDicePlayedThisTurn) return;

        boolean hasMoved = false;
        Piece selectedPiece = gameState.getCurrentPlayer().getSelectedPiece();
        Cell cell = gameState.getBoard().getCell(x, y);
        int diceNumber = gameState.getCurrentPlayer().getMoveLeft();

        if(selectedPiece instanceof Thief && !hasPut){
            // Double click for act
            if(selectedPiece.getCurrentCell() == cell){
                selectedPiece.act();
                hasPut = true;
            }
            // Moving
            else if(
                    Cell.distance(selectedPiece.getCurrentCell(),cell) == diceNumber && cell.getPiece()==null
            ){ // The move
                selectedPiece.moveTo(gameState.getBoard().getCell(x,y));
                transmitterCheck(gameState.getBoard().getCell(x,y));
                hasMoved = true;
            }
            // If click somewhere else we deselect
            else {
                gameState.getCurrentPlayer().setSelectedPiece(null);
            }
        }
        else if(selectedPiece != null && !(selectedPiece instanceof Thief)){
            // Double click for act
            if(selectedPiece.getCurrentCell() == cell && selectedPiece.getActive()){
                selectedPiece.getCurrentCell().setPrize(null);
                graphicalAgent.update(gameState);
                selectedPiece.act();
                selectedPiece.setActive(false);
                //hasMoved = true; No I don't think I will
            }
            // Moving
            else if(
                    selectedPiece.isValidMove(cell,diceNumber)  // Validity of color and distance
                            && gameState.getBoard().canGo(selectedPiece.getCurrentCell(),cell) // Wall condition
            ){ // The move
                selectedPiece.moveTo(gameState.getBoard().getCell(x,y));
                transmitterCheck(gameState.getBoard().getCell(x,y));
                hasMoved = true;
            }
            // If click somewhere else we deselect
            else {
                gameState.getCurrentPlayer().setSelectedPiece(null);
            }
        }

        //  Selection
        else if(cell.getPiece() != null
                && cell.getPiece().getPlayer() == gameState.getCurrentPlayer()){
            gameState.getCurrentPlayer().setSelectedPiece( cell.getPiece() );
        }

        if(hasMoved){
            gameState.getCurrentPlayer().endTurn();
            gameState.nextTurn();
            hasPut = false;
        }


        // do not touch this line
        graphicalAgent.update(gameState);
        checkForEndGame();
    }

    /**
     * check for endgame and specify winner
     * if player one in winner set winner variable to 1
     * if player two in winner set winner variable to 2
     * If the game is a draw set winner variable to 3
     */
    private void checkForEndGame() {
        if (gameState.getTurn() == 40)
        {
            // game ends
            int winner=4;
            if (gameState.getPlayer1().getScore()<gameState.getPlayer2().getScore()) winner =2;
            if (gameState.getPlayer1().getScore()>gameState.getPlayer2().getScore()) winner = 1;
            if (gameState.getPlayer1().getScore()==gameState.getPlayer2().getScore()) winner =3;

            // your code


            // do not touch it
            graphicalAgent.playerWin(winner);
            /* save players*/
            modelLoader.savePlayer(gameState.getPlayer1(), winner);
            modelLoader.savePlayer(gameState.getPlayer2(), winner);
            modelLoader.archive(gameState.getPlayer1(), gameState.getPlayer2(), winner);
            LogicalAgent logicalAgent = new LogicalAgent();
            logicalAgent.initialize();
        }
    }


    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who left clicks "dice button".) you should roll his/her dice
     * and update *****************
     */
    public static int diceCount =1;
    public void rollDice(int playerNumber) {
        if(gameState.getCurrentPlayer()!=gameState.getPlayer(playerNumber)){
            return;
        }
        if(diceCount==gameState.getTurn()) {
            Player player = gameState.getPlayer(playerNumber);
            int dice = player.getDice().roll();
            if(dice==6) gameState.getCurrentPlayer().applyOnScore(4);
            player.setMoveLeft(dice);
            diceCount++;
            gameState.getCurrentPlayer().setDicePlayedThisTurn(true);
            if(!gameState.getCurrentPlayer().hasMove(gameState.getBoard(), gameState.getCurrentPlayer().getMoveLeft())) {
                gameState.getCurrentPlayer().applyOnScore(-3);
                gameState.getCurrentPlayer().endTurn();
                gameState.nextTurn();
            }
        }
        else return;

        // do not touch this line
        graphicalAgent.update(gameState);
    }


    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who right clicks "dice button".) you should return the dice detail of that player.
     * you can use method "getDetails" in class "Dice"(not necessary, but recommended )
     */

    public String getDiceDetail(int playerNumber) {
        return new String (gameState.getPlayer(playerNumber).getDice().getDetails());
    }

    public void transmitterCheck(Cell cell){
        if(cell==null){
            return;
        }
        List<Transmitter> transmitters = gameState.getBoard().getTransmitters();
        for (Transmitter transmitter:
             transmitters) {
            if(transmitter.getFirstCell()==cell){
                gameState.getCurrentPlayer().applyOnScore(-3);
                if(cell.getPiece() instanceof Thief) ((Thief) cell.getPiece()).setPrize(null);
                if(transmitter.getLastCell().canEnter(cell.getPiece())){
                    cell.getPiece().moveTo(transmitter.getLastCell());
                }
            }
        }
    }

}
