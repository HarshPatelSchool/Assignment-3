package src;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.lang.Math;

/**
 * Runs the A* search on a Board
 */
public class Game {
    public static void main(String [] args) throws IOException, CloneNotSupportedException {
        File boardTxt = new File(args[0]); //Stores the file path from commandline argument
        int heuristicChoice = Integer.parseInt(args[1]); //Stores the heuristic being used (1-6) from the commandline argument
        PrintWriter csv = new PrintWriter("src/test.csv");
        StringBuffer sb = new StringBuffer();
        sb.append("Action Taken");
        sb.append(',');
        sb.append("X Distance");
        sb.append(',');
        sb.append("Y Distance");
        sb.append(',');
        sb.append("Manhattan Distance");
        sb.append(',');
        sb.append("Straight Line Distance");
        sb.append(',');
        sb.append("Rotations Taken");
        sb.append(',');
        sb.append("Average Surrounding Cost");
        sb.append(',');
        sb.append("Minimum Surrounding Cost");
        sb.append(',');
        sb.append("Average Cost in Direction of Goal");
        sb.append(',');
        sb.append("Min Turns to Goal");
        sb.append(',');
        sb.append("Score to Goal");
        sb.append('\n');
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis()-startTime<1000){ //7200000ms = 2hr
            GenerateBoards.generateBoards(1,10,10); //Comment this line out to stop generating new random boards
            Heuristic heuristic = new Heuristic(heuristicChoice); //Generates the heuristic method based on user choice
            Board board = new Board(boardTxt); //Generates board from user's file
            runSimulation(board, heuristic, sb);
        }
        csv.write(sb.toString());
        csv.close();
    }

    private static void runSimulation(Board board, Heuristic heuristic, StringBuffer sb) throws IOException, CloneNotSupportedException {
        AStar search = new AStar(board, heuristic); //Searches through the board using the user-specified heuristic method
        Agent goal = search.getBestGoal(); //Gets back the best Agent from Start to Goal
        ArrayList<Agent> agentPath = goal.getAgentPath();
        ArrayList<String> path = goal.getPath();
        int cost = goal.getScore()-100;
        for(int i = 0; i<agentPath.size(); i++){
            int total = 0;
            Agent a = agentPath.get(i);
            ArrayList<Integer> neighborVals = a.surroundingValues();
            neighborVals.sort(Integer::compareTo);
            for(int j: neighborVals)
                total+=j;
            sb.append(path.get(i));
            sb.append(',');
            sb.append(goal.getCurrLoc().getX()-a.getCurrLoc().getX());
            sb.append(',');
            sb.append(goal.getCurrLoc().getY()-a.getCurrLoc().getY());
            sb.append(',');
            sb.append((goal.getCurrLoc().getY()-a.getCurrLoc().getY()) + (goal.getCurrLoc().getX()-a.getCurrLoc().getX()));  //Manhattan
            sb.append(',');
            sb.append(Math.sqrt((Math.pow(goal.getCurrLoc().getY()-a.getCurrLoc().getY(), 2) + Math.pow(goal.getCurrLoc().getX()-a.getCurrLoc().getX(),2))));  //Straight Line
            sb.append(',');
            sb.append(a.getRotations());
            sb.append(',');
            sb.append(total/agentPath.size());
            sb.append(',');
            sb.append(neighborVals.get(0));
            sb.append(',');
            sb.append(agentPath.get(i).getAverageInDirection(goal.getCurrLoc()));
            sb.append(',');
            sb.append(agentPath.get(i).getMinNumTurnsToGoal(goal.getCurrLoc()));
            sb.append(',');
            sb.append(a.getScore()-cost);
            sb.append('\n');
        }
        /*
        System.out.println("Score = "+ goal.getScore());
        System.out.println("Number of Actions for Optimal Path = "+goal.getPath().size());
        System.out.println("Nodes Visited for Optimal Path = "+goal.getNodes());
        System.out.println("Nodes Expanded to find Optimal Path= "+search.getVisited().size());
         */
    }
}
