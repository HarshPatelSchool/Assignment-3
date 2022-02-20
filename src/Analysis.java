package src;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Analysis {
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        PrintWriter csv = new PrintWriter("src/analysis.csv");
        StringBuffer sb = new StringBuffer();
        int boardNum = 10;
        sb.append(" ");
        sb.append(',');
        for(int i = 1; i<=boardNum; i++) {
            sb.append(i+" Nodes");
            sb.append(',');
            sb.append(i +" Score");
            if(i!=boardNum) sb.append(',');
            else sb.append('\n');
        }
        for(int i = 5; i<=7; i++){
            int heuristicChoice = i;
            sb.append(i);
            sb.append(',');
            for(int j = 1; j<=boardNum; j++){
                File boardTxt = new File("src/boards/board_"+j+".txt");
                Heuristic heuristic = new Heuristic(heuristicChoice); //Generates the heuristic method based on user choice
                Board board = new Board(boardTxt); //Generates board from user's file
                runSimulation(board, heuristic, sb);
                if(j!=boardNum) sb.append(',');
                else sb.append('\n');
                System.out.println("Board: " + boardTxt.getPath() +"\t Heuristic: "+heuristicChoice);
            }
        }
        csv.write(sb.toString());
        csv.close();
    }

    private static void runSimulation(Board board, Heuristic heuristic, StringBuffer sb) throws IOException, CloneNotSupportedException {
        AStar search = new AStar(board, heuristic); //Searches through the board using the user-specified heuristic method
            sb.append(search.getVisited().size());
            sb.append(',');
            sb.append(search.getBestGoal().getScore());
    }
}