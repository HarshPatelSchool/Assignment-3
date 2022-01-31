package src;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Generates random boards for AStar to search through
 */
public class GenerateBoards {
    /**
     * Generates random boards with a set number of rows and columns
     * @param numberOfBoards number of random boards to create
     * @param rows number of rows in each board
     * @param columns number of columns in each board
     * @throws IOException
     */
    public static void generateBoards(int numberOfBoards, int rows, int columns) throws IOException {
        Random r = new Random(); //Used to generate random integers
        int nextInt; //used to store the random integers of the terrain complexity
        int sx,gx,sy,gy; //used to store the coordinates of

        for(int i = 1; i<=numberOfBoards; i++){ //Creates specified number of boards
            /* Assigns coordinate values for start and goal*/
            do{
                sx=r.nextInt(columns);
                gx=r.nextInt(columns);
                sy=r.nextInt(rows);
                gy=r.nextInt(rows);
            }while(sx==gx && sy==gy); //Prevents overlapping Start and Goal coordinates

            FileWriter board = new FileWriter("src/boards/board_"+i+".txt"); //Starts the file writer for a new text file
            for(int y = 0; y < rows; y++){ //Rows
                for(int x = 0; x<columns; x++){ //Columns
                    if(sx == x && sy == y) //Adds S if that spot is the Start coordinate
                        board.write("S\t");
                    else if(gx==x && gy == y) //Adds G if that spot is the Goal coordinate
                        board.write("G\t");
                    else { //Adds a random terrain complexity from 1-9 if coordinate is not Start or Goal
                        nextInt = r.nextInt(9)+1;
                        board.write(nextInt+"\t");
                    }
                }
               if(y!=rows) board.write("\n"); //Adds newline at the end of the row except the last row
            }
            board.close(); //Closes the file writer after everything has been added to the text file
        }
    }
}
