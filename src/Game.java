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
        GenerateBoards.generateBoards(10,500,500); //Comment this line out to stop generating new random boards

        Heuristic heuristic = new Heuristic(heuristicChoice); //Generates the heuristic method based on user choice
        Board board = new Board(boardTxt); //Generates board from user's file
        AStar search = new AStar(board, heuristic); //Searches through the board using the user-specified heuristic method
        Agent goal = search.getBestGoal(); //Gets back the best Agent from Start to Goal
        ArrayList<Agent> agentPath = goal.getAgentPath();
        ArrayList<String> path = goal.getPath();

        int cost = goal.getScore()-100;
        System.out.println("Score = "+ goal.getScore());
        System.out.println("Number of Actions for Optimal Path = "+goal.getPath().size());
        System.out.println("Nodes Visited for Optimal Path = "+goal.getNodes());
        System.out.println("Nodes Expanded to find Optimal Path= "+search.getVisited().size());


        PrintWriter csv = new PrintWriter("src/test.csv");
        StringBuffer sb = new StringBuffer();
        sb.append("Action Taken");
        sb.append(',');
        sb.append("X Distance");
        sb.append(',');
        sb.append("Y Distance");
        sb.append(',');
        sb.append("Rotations Taken");
        sb.append(',');
        sb.append("Average Surrounding Cost");
        sb.append(',');
        sb.append("Score to Goal");
        sb.append('\n');
        for(int i = 0; i<agentPath.size(); i++){
            int total = 0;
            Agent a = agentPath.get(i);
            ArrayList<Integer> neighborVals = a.surroundingValues();
            for(int j: neighborVals)
                total+=j;
            sb.append(path.get(i));
            sb.append(',');
            sb.append(goal.getCurrLoc().getX()-a.getCurrLoc().getX());
            sb.append(',');
            sb.append(goal.getCurrLoc().getY()-a.getCurrLoc().getY());
            sb.append(',');
            sb.append(a.getRotations());
            sb.append(',');
            sb.append(total);
            sb.append(',');
            sb.append(a.getScore()-cost);
            sb.append('\n');
        }
        csv.write(sb.toString());
        csv.close();

    }
}
