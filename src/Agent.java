package src;

import java.util.ArrayList;

/**
 * Agent stores and defines the movement and coordinates that can be made on the board
 */
public class Agent implements Comparable<Agent> {
    Board board;
    private Coord currLoc;
    private Direction currDir;
    private int score;
    private int heuristic;
    private ArrayList<String> path;
    private int nodes;

    /**
     * Creates a new agent at a coordinate
     * @param b Board to be placed onto
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Agent(Board b, int x, int y) {
        board = b;
        currLoc = new Coord(x, y);
        currDir = Direction.UP;
        score = 0;
        heuristic = 0;
        path = new ArrayList<>();
        nodes=0;
    }

    /**
     * Creates agent with specified values
     * @param b Board
     * @param x x-coordinate
     * @param y y-coordinate
     * @param dir Direction it is looking
     * @param score Current score
     * @param path Path is has traveled
     * @param nodes Number of nodes visited
     */
    public Agent(Board b, int x, int y, Direction dir, int score, ArrayList<String> path, int nodes) {
        board = b;
        currLoc = new Coord(x, y);
        currDir =dir;
        this.score = score;
        heuristic = 0;
        this.path=(ArrayList<String>) path.clone();
        this.nodes=nodes;
    }

    /**
     * Creates a cloned agent with a different memory value.
     * @return an Agent with the exact same properties as the current Agent
     */
    public Agent clone(){
        return new Agent(board, currLoc.getX(), currLoc.getY(), currDir, score, path, nodes);
    }

    /**
     * Causes the Agent to move forward from whatever direction it is looking at
     */
    public void moveForward() {
        int x = currLoc.getX(), y = currLoc.getY();
        switch(currDir) {
            case UP: // x stays the same
                y = currLoc.getY() - 1;
                break;
            case DOWN: // x stays the same
                y = currLoc.getY() + 1;
                break;
            case LEFT: // y stays the same
                x = currLoc.getX() - 1;
                break;
            case RIGHT: // y stays the same
                x = currLoc.getX() + 1;
                break;
        }
        currLoc.setX(x);
        currLoc.setY(y);
        if(outOfBounds() == false) {
            score -= board.getVal(currLoc);
            path.add("Move Forward");
            nodes++;
        }
        else
            score = Integer.MAX_VALUE;
    }

    /**
     * Turns the Agent 90°
     * @param t Direction to turn
     */
    public void turn(Turn t) {
        int currLocVal = board.getVal(currLoc);
        if(t == Turn.CLOCKWISE) {
            switch(currDir) {
                case UP:
                    currDir = Direction.RIGHT;
                    break;
                case DOWN:
                    currDir = Direction.LEFT;
                    break;
                case LEFT:
                    currDir = Direction.UP;
                    break;
                case RIGHT:
                    currDir = Direction.DOWN;
                    break;
            }
            path.add("Turn Clockwise");

        }
        else { //counterclockwise
            switch(currDir) {
                case UP:
                    currDir = Direction.LEFT;
                    break;
                case DOWN:
                    currDir = Direction.RIGHT;
                    break;
                case LEFT:
                    currDir = Direction.DOWN;
                    break;
                case RIGHT:
                    currDir = Direction.UP;
                    break;
            }
            path.add("Turn Counterclockwise");

        }
        score -= (int)Math.ceil(currLocVal / 2.0);
    }

    /**
     * Causes the agent to bash through the next node in-front of it, followed up immediately by moving forward
     */
    public void bash() {
        int x, y;
        switch(currDir) {
            case UP: // x stays the same
                y = currLoc.getY() - 1;
                currLoc.setY(y);
                break;
            case DOWN: // x stays the same
                y = currLoc.getY() + 1;
                currLoc.setY(y);
                break;
            case LEFT: // y stays the same
                x = currLoc.getX() - 1;
                currLoc.setX(x);
                break;
            case RIGHT: // y stays the same
                x = currLoc.getX() + 1;
                currLoc.setX(x);
                break;
        }
        score -= 3;
        path.add("Bash");
        nodes++;
        moveForward();
    }

    /**
     * Demolish the surrounding terrain
     */
    public void demolish(){ //TODO
        int x = currLoc.getX();
        int y = currLoc.getY();

        for(int i =x-1; i<=x+1; i++){
          for(int j = y-1;j <=y+1; j++){
              if(i==x && j ==y) //Does not demolish on current location of agent
                  continue;
              else if (i <0 || j <0 || j>=board.getBoard().length || i>=board.getBoard()[0].length)
                  continue;
              else board.getBoard()[y][x]=3;

          }
        }
        score-=4;
    }

    /**
     * Gets coordinates of the Agent
     * @return current Coord of Agent
     */
    public Coord getCurrLoc() {
        return currLoc;
    }

    /**
     *
     * @return
     */
    public Direction getCurrDir() {
        return currDir;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<String> getPath() {
        return path;
    }

    public int getNodes() {
        return nodes;
    }

    /**
     * Checks if the agent is still in the board or not
     * @return True if off the board, false if not
     */
    public boolean outOfBounds(){
        if(currLoc.getX()>=board.getBoard()[0].length || currLoc.getY()>=board.getBoard().length || currLoc.getX() <0 || currLoc.getY() <0)
            return true;
        else
            return false;
    }

    /**
     * Compares the heuristic values of two agents
     * @param o second agent to compare to.
     * @return
     */
    @Override
    public int compareTo(Agent o) {
        return o.getHeuristic()-getHeuristic(); //The reason why it is reversed is so the Priorty Queue sort goes from highest heuristic to lowest
    }

    @Override
    public String toString() {
        return "Agent{" +
                "currLoc=" + currLoc +
                ", currDir=" + currDir +
                ", score=" + score +
                '}';
    }

    /**
     * Gets heuristic of agent
     * @return Agent's heuristic
     */
    public int getHeuristic() {
        return heuristic;
    }

    /**
     * Sets heuristic of agent
     * @param heuristic Agent's heuristic
     */
    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

}
