package ir.sharif.math.bp99_1.snake_and_ladder.logic;

// import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.GameState;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.util.Config;

import java.io.*;
import java.util.Scanner;

public class ModelLoader {
    private final File boardFile, playersDirectory, archiveFile;
    private static int id;

    /**
     * DO NOT CHANGE ANYTHING IN CONSTRUCTOR.
     */
    public ModelLoader() {
        boardFile = Config.getConfig("mainConfig").getProperty(File.class, "board");
        playersDirectory = Config.getConfig("mainConfig").getProperty(File.class, "playersDirectory");
        archiveFile = Config.getConfig("mainConfig").getProperty(File.class, "archive");
        if (!playersDirectory.exists()) playersDirectory.mkdirs();
    }


    /**
     * read file "boardFile" and create a Board
     * <p>
     * you can use "BoardBuilder" class for this purpose.
     * <p>
     * pay attention add your codes in "try".
     */
    public Board loadBord() {
        try {
            Scanner scanner = new Scanner(boardFile);

            // Code Here
            BoardBuilder boardBuilder = new BoardBuilder(scanner);
            return boardBuilder.build();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        }
        return null;
    }

    /**
     * load player.
     * if no such a player exist, create an account(file) for him/her.
     * <p>
     * you can use "savePlayer" method of this class for that purpose.
     * <p>
     * add your codes in "try" block .
     */
    public Player loadPlayer(String name, int playerNumber) {
        try {
            File playerFile = getPlayerFile(name);

            if(playerFile==null){
                playerFile = new File(playersDirectory+"\\"+name);
                playerFile.createNewFile();
                PrintStream printStream = new PrintStream(new FileOutputStream(playerFile, true));
                id++;
                printStream.println(id);
                printStream.println(name);
                printStream.println("Archive of "+ name+".");
                printStream.println("Note that player 3 won means that the game lead to a draw.");
                printStream.flush();
                printStream.close();
            }
            Scanner scanner = new Scanner(playerFile);
            id= scanner.nextInt();
            return new Player(name,10, id , playerNumber);

        } catch (FileNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * if player does not have a file, create one.
     * <p>
     * else update his/her file.
     * <p>
     * add your codes in "try" block .
     */
    public void savePlayer(Player player, int winner) {
        try {
            // add your codes in this part
            File file = getPlayerFile(player.getName());
            assert file != null;
            PrintStream printStream = new PrintStream(new FileOutputStream(file, true));
            printStream.println("Played as player"+player.getPlayerNumber()+". Player"+winner+" won.");
            printStream.flush();
            printStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        }
    }

    /**
     * give you a name (player name), search for its file.
     * return the file if exist.
     * return null if not.
     */
    private File getPlayerFile(String name) {

        String[] names = playersDirectory.list();
        if(names== null) return null;

        for (String nameIt :
                names  ) {
            if(nameIt.equals(name)){
                return new File(playersDirectory.toString()+"\\"+name);
            }
        }
        return null;
    }

    /**
     * at the end of the game save game details
     */
    public void archive(Player player1, Player player2, int winner) {
        try {
            // add your codes in this part
            PrintStream printStream = new PrintStream(new FileOutputStream(archiveFile, true));
            printStream.println(player1.getName()+" played as player 1, "+player2.getName()+" played as player 2, "+winner+" won.");
            printStream.flush();
            printStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void save(GameState gameState) throws IOException {
        String name1 = gameState.getPlayer1().getName();
        String name2 = gameState.getPlayer2().getName();
        File gameFile = getGameFile(name1, name2);
        PrintStream printStream = new PrintStream(new FileOutputStream(gameFile, false));
        printStream.flush();
        printStream.close();
    }

    private File getGameFile(String name1, String name2) throws IOException {
        String gameName = gameName(name1, name2);
        File gameFile;
//        String[] games = archiveFile.list();
//        boolean flag= false;
//        if(games != null){
//            for (String game :
//                    games ) {
//                if(game.equals(gameName)){
//                    gameFile = new File(playersDirectory.toString()+"\\"+gameName);
//                    flag=true;
//                }
//            }
//        }
//        if(games == null || !flag){
            gameFile = new File(playersDirectory+"\\"+gameName);
            gameFile.createNewFile();
//        }
        return gameFile;
    }


//    public GameState load(){
//        return;
//    }

    public String gameName(String name1, String name2){
        return "";
    }
}
