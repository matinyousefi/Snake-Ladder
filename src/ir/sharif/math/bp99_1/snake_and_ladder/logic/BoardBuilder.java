package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.*;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class BoardBuilder {

    LinkedList<Cell> cells;
    LinkedList<Wall> walls;
    LinkedList<Transmitter> transmitters;
    Scanner scanner;
    HashMap<Cell, Integer> startingCells;
    public BoardBuilder(Scanner scanner)
    {
        this.scanner = scanner;
        this.cells = new LinkedList<>();
        this.transmitters = new LinkedList<>();
        this.walls = new LinkedList<>();
        this.startingCells = new HashMap<>();

    }

    private Cell getCell(int x, int y){
        for (Cell cellIterator:
                cells) {
            if (cellIterator.getX()==x && cellIterator.getY()==y) return cellIterator;
        }
        return null;
    }




    /**
     * give you a string in constructor.
     * <p>
     * you should read the string and create a board according to it.
     */

    public Board build() throws FileNotFoundException {

        // Temporal variables

        String line;
        String color;
        int x, y;
        int player;
        Cell cell = null;
        Cell anotherCell;
        Piece piece;
        int boardX;
        int boardY;

        line = scanner.nextLine();



        x = Integer.parseInt(line.split(" ")[1]);
        y = Integer.parseInt(line.split(" ")[2]);

        boardX =x;
        boardY =y;

        for (int i = 1; i < x+1; i++) {
            line= scanner.nextLine();
            for (int j = 1; j < y+1; j++) {
                cell = new Cell(color(line.split(" ")[j-1]), i, j);
                cells.add(cell);
            }
        }

        scanner.nextLine();

        line=scanner.nextLine();
        int pieceCount = Integer.parseInt(line.split(" ")[1]);

        for (int i = 0; i < pieceCount; i++) {
            x=scanner.nextInt();
            y=scanner.nextInt();
            player=scanner.nextInt();
            cell=getCell(x,y);
            startingCells.put(cell, player);

        }

        scanner.nextLine();
        scanner.nextLine();
        line=scanner.nextLine();

        int wallCount= Integer.parseInt(line.split(" ")[1]);

        for (int i = 0; i < wallCount; i++) {
            x=scanner.nextInt();
            y=scanner.nextInt();
            cell=getCell(x,y);
            x=scanner.nextInt();
            y=scanner.nextInt();
            anotherCell=getCell(x,y);
            Wall wall = new Wall(cell,anotherCell);
            walls.add(wall);
        }

        scanner.nextLine();
        scanner.nextLine();
        line=scanner.nextLine();
        int transmitterCount= Integer.parseInt(line.split(" ")[1]);

        for (int i = 0; i < transmitterCount; i++) {

            x=scanner.nextInt();
            y=scanner.nextInt();
            cell=getCell(x,y);
            x=scanner.nextInt();
            y=scanner.nextInt();
            anotherCell=getCell(x,y);
            Transmitter transmitter = new Transmitter(cell,anotherCell);
            transmitters.add(transmitter);
        }

        scanner.nextLine();
        scanner.nextLine();
        line=scanner.nextLine();
        int prizeCount= Integer.parseInt(line.split(" ")[1]);

        //Temporal variables
        int point;
        int chance;
        int diceNumber;

        for (int i = 0; i < prizeCount; i++) {
            x=scanner.nextInt();
            y=scanner.nextInt();
            cell=getCell(x,y);
            point=scanner.nextInt();
            chance=scanner.nextInt();
            diceNumber=scanner.nextInt();
            Prize prize = new Prize(cell, point, chance,diceNumber);
            assert cell != null;
            cell.setPrize(prize);

        }

        Board board = new Board(cells, transmitters,  walls, startingCells, boardX, boardY);
        for (Cell cellIt :
                cells) {
            cellIt.setBoard(board);
        }
        return board;
    }

    private static Color color (String name){
        // This could have been done using valueOf but I did not notice at that time
        switch (name){
            case "WHITE":
                return Color.WHITE;
            case "RED":
                return Color.RED;
            case "BLACK":
                return Color.BLACK;
            case "BLUE":
                return Color.BLUE;
            case "GREEN":
                return Color.GREEN;
            case "YELLOW":
                return Color.YELLOW;
        }
        System.out.println(name);
        System.out.println("ERROR: BoardBuilder.color does not work properly.");
        return Color.BLACK;
    }


}
