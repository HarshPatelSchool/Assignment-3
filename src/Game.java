package src;

import java.io.File;
import java.io.IOException;
/**
 * Runs the A* search on a Board
 */
public class Game {
    public static void main(String [] args) throws IOException {
        File boardTxt = new File(args[0]);
        int heuristicChoice = Integer.parseInt(args[1]);
        GenerateBoards.generateBoards(10,100,100);
        Heuristic heuristic = new Heuristic(heuristicChoice);
        Board board = new Board(boardTxt, heuristic);
        AStar search = new AStar(board);
    }
}
