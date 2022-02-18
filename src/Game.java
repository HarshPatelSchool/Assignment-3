package src;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Runs the A* search on a Board
 */
public class Game {
    public static void main(String [] args) throws IOException, CloneNotSupportedException {
        File boardTxt = new File(args[0]); //Stores the file path from commandline argument
        int heuristicChoice = Integer.parseInt(args[1]); //Stores the heuristic being used (1-6) from the commandline argument
        GenerateBoards.generateBoards(10,50,50); //Comment this line out to stop generating new random boards

        Heuristic heuristic = new Heuristic(heuristicChoice); //Generates the heuristic method based on user choice
        Board board = new Board(boardTxt); //Generates board from user's file
        AStar search = new AStar(board, heuristic); //Searches through the board using the user-specified heuristic method
        Agent goal = search.getBestGoal(); //Gets back the best Agent from Start to Goal
        ArrayList<Agent> agentPath = goal.getAgentPath();
        ArrayList<String> path = goal.getPath();
        int cost = goal.getScore()-100;
        /* Output
        System.out.println("Score = "+ goal.getScore());
        System.out.println("Number of Actions for Optimal Path = "+goal.getPath().size());
        System.out.println("Nodes Visited for Optimal Path = "+goal.getNodes());
        System.out.println("Nodes Expanded to find Optimal Path= "+search.getVisited().size());
        System.out.println("--- Actions ---");
                 */

        PrintWriter csv = new PrintWriter("src/test.csv");
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i<agentPath.size(); i++){
            sb.append(path.get(i));
            sb.append(',');
            sb.append(goal.getCurrLoc().getX()-agentPath.get(i).getCurrLoc().getX());
            sb.append(',');
            sb.append(goal.getCurrLoc().getY()-agentPath.get(i).getCurrLoc().getY());
            sb.append(',');
            sb.append(agentPath.get(i).getScore()-cost);
            sb.append(',');
            sb.append(agentPath.get(i).getAverageInDirection(goal.getCurrLoc()));
            sb.append(',');
            sb.append(agentPath.get(i).getMinNumTurnsToGoal(goal.getCurrLoc()));
            sb.append('\n');
        }
        csv.write(sb.toString());
        csv.close();

    }
}
