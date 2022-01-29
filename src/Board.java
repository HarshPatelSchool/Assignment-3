package src;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
        return null; //TODO
    }

    public int getVal(Coord c) {
        //TODO
        return -1;
    }

}
