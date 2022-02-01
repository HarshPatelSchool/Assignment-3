package src;

import java.io.File;
import java.io.IOException;
/**
 * Runs the A* search on a Board
 */
public class Game {
    public static void main(String [] args) throws IOException {
        File boardTxt = new File(args[0]); //Stores the file path from commandline argument
        int heuristicChoice = Integer.parseInt(args[1]); //Stores the heuristic being used (1-6) from the commandline argument
        //GenerateBoards.generateBoards(10,40,40); //Comment this line out to stop generating new random boards

        Heuristic heuristic = new Heuristic(heuristicChoice); //Generates the heuristic method based on user choice
        Board board = new Board(boardTxt); //Generates board from user's file
        AStar search = new AStar(board, heuristic); //Searches through the board using the user-specified heuristic method
        Agent goal = search.getBestGoal(); //Gets back the best Agent from Start to Goal

        /* Output */
        System.out.println("Score = "+ goal.getScore());
        System.out.println("Number of Actions = "+goal.getPath().size());
        System.out.println("Nodes Visited = "+goal.getNodes());
        System.out.println("--- Actions ---");
        for(String s: goal.getPath())
            System.out.println(s);
    }
}
