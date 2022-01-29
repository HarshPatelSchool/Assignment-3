package src;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class Board {
    private File file; //Text file that contains the board
    private char[][] board; //Array containing the terrain complexity

    /**
     * Creates a new Board object from a text file
     * @param file Source file of the board
     * @throws IOException
     */
    public Board(File file) throws IOException {
        this.file = file;
        board = makeBoard();
    }

    /**
     * Creates an array representing the board
     * @return char array with the terrain complexity, start, and goal
     * @throws IOException
     */
    private char[][] makeBoard() throws IOException {
        List boardS = Files.readAllLines(file.toPath()); //Reads every line of the file and adds them to a list
        char[][] board = new char[0][0]; //Initializes the board
        char[] row; //Initializes a row of the board

        /*Adds the row values to the board array*/
        for(int i = 0; i < boardS.size(); i++){
            String line = (String) boardS.get(i); //Converts the Object stored in the list into a String
            row = line.replaceAll("\t","").toCharArray(); //Removes the tabs in the file and converts the String into a char[]
            if(i == 0) //Sets the size of the board after the first row
                board = new char[boardS.size()][row.length];
            board[i]=row; //Adds the row to the board
        }
        return board; //Returns the completed board
    }

    /**
     * Returns the value in the board of the corresponding coordinate
     * @param c Coordinate which we need the value from
     * @return Terrain complexity or 0 if Start or Goal
     */
    public int getVal(Coord c) {
        char val = board[c.getY()][c.getX()]; //Gets val at coordinate. Array is in form row, column.
        if(val == 'G' || val == 'S')
            return 0; //TODO might need to further look at this depending on the AStar algorithm implementation
        else
            return Integer.parseInt(String.valueOf(val)); //Returns the int value at the coordinate
    }
}
