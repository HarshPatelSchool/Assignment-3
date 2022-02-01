package src;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class AStar {
    private Coord G, S;
    private Agent[][] scores;
    private Heuristic h;
    private int rows, columns;
    private Agent bestGoal;
    public ArrayList<Coord> visited;
    private  PriorityQueue<Agent> directions; //PQ of the actions the Agent can make, sorted by heuristic

    public AStar(Board b, Heuristic h) throws IOException {
        this.h = h; //Gets the heuristic function
        char[][] board = b.getBoard(); //Gets the board values
        rows = board.length; //Number of rows
        columns = board[0].length; //Number of columns
        scores = new Agent[rows][columns]; //Board that stores Agents and their paths
        visited = new ArrayList<>();
        directions = new PriorityQueue<>();
        /* Searches the value board for Start and Goal coordinates*/
        for(int y = 0; y < rows; y++){
            for(int x = 0; x < columns; x++) {
                if (board[y][x] == 'G')
                    G = new Coord(x, y); //Goal coordinate
                else if (board[y][x] == 'S')
                    S = new Coord(x, y); //Start coordinate
            }
        }

        directions.add(new Agent(b, S.getX(), S.getY()));
        runSearch();
        this.bestGoal = scores[G.getY()][G.getX()]; //Sets the bestGoal agent as the agent at the Goal coordinate after the algorithm is complete
        //System.out.println(Arrays.deepToString(scores));
        FileWriter check = new FileWriter("src/boards/check.txt"); //Starts the file writer for a new text file
        for(int y = 0; y < rows; y++){ //Rows
            for(int x = 0; x<columns; x++){ //Columns
                if (new Coord(x,y).equals(G))
                    check.write("G\t");
                else if (new Coord(x,y).equals(S))
                    check.write("S\t");
                else if(visited.contains(new Coord(x,y)))
                    check.write("X\t");
                else check.write(".\t");
            }
            if(y!=rows) check.write("\n"); //Adds newline at the end of the row except the last row
        }
        check.close(); //Closes the file writer after everything has been added to the text file
    }

    /**
     * Runs the search algorithm
     */
    private void runSearch(){
        while (!directions.isEmpty()){ //Continues running while there are actions in the priority queue
            Agent a = directions.remove();
            int y = a.getCurrLoc().getY(); //Gets current Y value to check
            int x = a.getCurrLoc().getX(); //Gets current X value to check
            ArrayList<Agent> neighbors = new ArrayList<>(); //"neighbors" in this case are spots the agent can reach from it's current position
            if(a.getCurrLoc().equals(G)) { //Checks if goal has been reached
                scores[y][x] = new Agent(a.board, x, y, a.getCurrDir(), a.getScore() + 100, a.getPath(), a.getNodes()); //+100 to score for reaching Goal
                directions.clear(); //Goal has been reached so algorithm can stop by clearing the PQ
                break;
            }else{
                /*These actions represent the places the agent can get to from the current coordinates*/
                /* Backwards action */
                Agent bash = a.clone();
                bash.bash();
                neighbors.add(bash);

                /* Forward action */
                Agent forward = a.clone();
                forward.moveForward();
                neighbors.add(forward);

                /*Clockwise forward action*/
                Agent clockwiseForward = a.clone();
                clockwiseForward.turn(Turn.CLOCKWISE);
                clockwiseForward.moveForward();
                neighbors.add(clockwiseForward);

                /*Clockwise bash action*/
                Agent clockwiseBash = a.clone();
                clockwiseBash.turn(Turn.CLOCKWISE);
                clockwiseBash.bash();
                neighbors.add(clockwiseBash);

                /* Counterclockwise forward action*/
                Agent counterForward = a.clone();
                counterForward.turn(Turn.COUNTERCLOCKWISE);
                counterForward.moveForward();
                neighbors.add(counterForward);

                /* Counterclockwise bash action*/
                Agent counterBash = a.clone();
                counterBash.turn(Turn.COUNTERCLOCKWISE);
                counterBash.bash();
                neighbors.add(counterBash);

                /* Backwards Forward action*/
                Agent backwardsForward = a.clone();
                backwardsForward.turn(Turn.COUNTERCLOCKWISE);
                backwardsForward.turn(Turn.COUNTERCLOCKWISE);
                backwardsForward.moveForward();
                neighbors.add(backwardsForward);

                /* Backwards bash action*/
                Agent backwardsBash = a.clone();
                backwardsBash.turn(Turn.COUNTERCLOCKWISE);
                backwardsBash.turn(Turn.COUNTERCLOCKWISE);
                backwardsBash.bash();
                neighbors.add(backwardsBash);

                for(Agent neighbor: neighbors){ //Checks every possible action
                    if(!neighbor.outOfBounds()){ //Makes sure action is valid
                        int newX = neighbor.getCurrLoc().getX();
                        int newY = neighbor.getCurrLoc().getY();
                        neighbor.setHeuristic(heuristic(neighbor)); //Sets the heuristic of action
                        if(scores[newY][newX]==null){ //If position hasn't been reached yet add it
                            scores[newY][newX] = neighbor;
                            visited.add(new Coord(newX, newY));
                            directions.add(neighbor);

                        }else if(scores[newY][newX].getScore()<neighbor.getScore()){ //If old method to reach point is worse, replace it
                            scores[newY][newX]=neighbor;
                            directions.add(neighbor);
                        }
                    }
                }
            }
        }
    }

    /**
     * Calculates the heuristic value
     * @param a Agent to check
     * @return Heuristic value
     */
    private int heuristic(Agent a){
        return a.getScore() - h.calculateHeuristic(a.getCurrLoc(), G);
    }

    /**
     * Returns the agent at the goal state
     * @return Agent at the coordinates of the goal
     */
    public Agent getBestGoal() {
        return bestGoal;
    }

}
