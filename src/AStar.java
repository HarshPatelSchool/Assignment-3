package src;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class AStar {
    private Coord G, S; //Goal and Start coordinates
    private Agent[][] scores; //Visited list
    private Heuristic h; //Heuristic method
    private int rows, columns; //Rows and columns of board
    private Agent bestGoal; //Agent that reached the goal
    private ArrayList<Coord> visited; //Coordinates the algorithm checked
    private  PriorityQueue<Agent> directions; //PQ of the actions the Agent can make, sorted by heuristic

    /**
     * Uses A* to give the optimal path in a board from Start to Goal using the given heurisitc
     * @param b Board
     * @param h Heuristic method
     * @throws IOException
     */
    public AStar(Board b, Heuristic h) throws IOException, CloneNotSupportedException {
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
        Agent a = new Agent(b, S.getX(), S.getY());
        a.getAgentPath().add(a);
        directions.add(a);
        runSearch();
        this.bestGoal = scores[G.getY()][G.getX()]; //Sets the bestGoal agent as the agent at the Goal coordinate after the algorithm is complete

        /* Creates a file that visually shows what was checked. Useful for analysis */
        FileWriter check = new FileWriter("src/boards/check.txt"); //Starts the file writer for a new text file
        for(int y = 0; y < rows; y++){ //Rows
            for(int x = 0; x<columns; x++){ //Columns
                if (new Coord(x,y).equals(G)) //Adds Goal
                    check.write("G\t");
                else if (new Coord(x,y).equals(S)) //Adds Start
                    check.write("S\t");
                else if(visited.contains(new Coord(x,y)))  //Adds X if visited
                    check.write("X\t");
                else check.write(".\t"); //Adds . if not visited
            }
            if(y!=rows) check.write("\n"); //Adds newline at the end of the row except the last row
        }
        check.close(); //Closes the file writer after everything has been added to the text file
    }

    /**
     * Runs the search algorithm
     * Based off of the pseudocode from https://www.redblobgames.com/pathfinding/a-star/introduction.html#astar
     */
    private void runSearch() throws CloneNotSupportedException {
        while (!directions.isEmpty()){ //Continues running while there are actions in the priority queue
            Agent a = directions.remove(); //Gets the value with the best heuristic score in the priority queue
            int y = a.getCurrLoc().getY(); //Gets current Y value to check
            int x = a.getCurrLoc().getX(); //Gets current X value to check
            ArrayList<Agent> neighbors = new ArrayList<>(); //"neighbors" in this case are spots the agent can reach from its current position
            if(a.getCurrLoc().equals(G)) { //Checks if goal has been reached
                scores[y][x] = new Agent(a.board, x, y, a.getCurrDir(), a.getScore() + 100, a.getPath(), a.getNodes(), a.getAgentPath(), a.getRotations()); //+100 to score for reaching Goal
                break; //Stops loop
            }else{
                /*These actions represent the places the agent can get to from the current coordinates*/
                /* Backwards action */
                Agent bash = (Agent) a.clone();
                bash.bash();
                neighbors.add(bash);

                /* Forward action */
                Agent forward = (Agent) a.clone();
                forward.moveForward();
                neighbors.add(forward);

                /*Clockwise forward action*/
                Agent clockwiseForward = (Agent) a.clone();
                clockwiseForward.turn(Turn.CLOCKWISE);
                clockwiseForward.moveForward();
                neighbors.add(clockwiseForward);

                /*Clockwise bash action*/
                Agent clockwiseBash = (Agent) a.clone();
                clockwiseBash.turn(Turn.CLOCKWISE);
                clockwiseBash.bash();
                neighbors.add(clockwiseBash);

                /* Counterclockwise forward action*/
                Agent counterForward = (Agent) a.clone();
                counterForward.turn(Turn.COUNTERCLOCKWISE);
                counterForward.moveForward();
                neighbors.add(counterForward);

                /* Counterclockwise bash action*/
                Agent counterBash = (Agent) a.clone();
                counterBash.turn(Turn.COUNTERCLOCKWISE);
                counterBash.bash();
                neighbors.add(counterBash);

                /* Backwards Forward action*/
                Agent backwardsForward = (Agent) a.clone();
                backwardsForward.turn(Turn.COUNTERCLOCKWISE);
                backwardsForward.turn(Turn.COUNTERCLOCKWISE);
                backwardsForward.moveForward();
                neighbors.add(backwardsForward);

                /* Backwards bash action*/
                Agent backwardsBash = (Agent) a.clone();
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
        return a.getScore() - h.calculateHeuristic(a, G);
    }

    /**
     * Returns the agent at the goal state
     * @return Agent at the coordinates of the goal
     */
    public Agent getBestGoal() {
        return bestGoal;
    }

    /**
     * Gets list of nodes visited
     * @return Arraylist of coordinates visited
     */
    public ArrayList<Coord> getVisited() {
        return visited;
    }
}
