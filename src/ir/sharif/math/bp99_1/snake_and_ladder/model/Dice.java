package ir.sharif.math.bp99_1.snake_and_ladder.model;
import java.util.Random;

public class Dice {
    /*
    Random Class syntax were looked up from geeksForGeeks
     */

    private static Random random;
    private int[] chanceOf;
    private int[] memory;

    /**
     * add some fields to store :
     * 1) chance of each dice number ( primary chance of each number, should be 1 )
     * currently our dice has 1 to 6.
     * 2) generate a random number
     * <p>
     * initialize these fields in constructor.
     */
    public Dice() {
        random= new Random();
        chanceOf= new int[6];
        for (int i = 0; i < 6; i++) {
            chanceOf[i]=1;
        }
        memory = new int[3];
        for (int i = 0; i < 3; i++) {
            memory[i]=-1;
        }
    }

    /**
     * create an algorithm generate a random number(between 1 to 6) according to the
     * chance of each dice number( you store them somewhere)
     * return the generated number
     */
    public int roll() {
//        return 1;
        int s = 0;
        for (int i = 0; i < 6; i++) {
            s+=chanceOf[i];
        }
        int dice = random.nextInt(s);
        int upTo = chanceOf[0];
        for (int i = 0; i < 6; i++) {
            if(dice < upTo){
                memory[0]=memory[1];
                memory[1]=memory[2];
                memory[2]=i+1;
                if(memory[2]==memory[1] && memory[1]!=memory[0]){
                    addChance(memory[2],1);
                }
                if(memory[2]==2){
                    for (int j = 0; j < 6; j++) {
                        chanceOf[j]=1;
                    }
                }
                return i+1;}
            upTo=upTo+chanceOf[i+1];
        }
        return -1;
    }

    /**
     * give a dice number and a chance, you should UPDATE chance
     * of that number.
     * pay attention chance of none of the numbers must not be negative(it can be zero)
     */
    public void addChance(int number, int chance) {
        if(number==0) return;
        chanceOf[number-1]+=chance;
        if(chanceOf[number-1] > 8) chanceOf[number-1] = 8;
    }


    /**
     * you should return the details of the dice number.
     * sth like:
     * "1 with #1 chance.
     * 2 with #2 chance.
     * 3 with #3 chance
     * .
     * .
     * . "
     * where #i is the chance of number i.
     */
    public StringBuilder getDetails() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            s.append(i + 1).append(" has chance of ").append(chanceOf[i]).append(".\n");
        }
        return s;
    }
    //Looked up from stackoverflow.com/questions/14534767/how-to-append-a-newline-to-stringbuilder
}
