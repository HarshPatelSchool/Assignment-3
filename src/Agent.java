package src;

import java.util.ArrayList;
import java.util.LinkedList;

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
    private ArrayList<Agent> agentPath;
    private int nodes;
    private int rotations;

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
        path.add("Start");
        nodes=0;
        agentPath = new ArrayList<>();
        rotations = 0;
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
    public Agent(Board b, int x, int y, Direction dir, int score, ArrayList<String> path, int nodes, ArrayList<Agent> agentPath, int rotations) {
        board = b;
        currLoc = new Coord(x, y);
        currDir =dir;
        this.score = score;
        heuristic = 0;
        this.path=(ArrayList<String>) path.clone();
        this.nodes=nodes;
        this.agentPath= (ArrayList<Agent>) agentPath.clone();
        this.rotations=rotations;
    }

    /**
     * Creates a cloned agent with a different memory value.
     * @return an Agent with the exact same properties as the current Agent
     */
    public Agent clone(){
        return new Agent(board, currLoc.getX(), currLoc.getY(), currDir, score, path, nodes, agentPath, rotations);
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
            agentPath.add(clone());
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
        rotations++;
        agentPath.add(clone());
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
        agentPath.add(clone());
        moveForward();
    }

    /**
     * Demolish the surrounding terrain
     */
    public void demolish(){ //TODO extra credit
        int x = currLoc.getX();
        int y = currLoc.getY();

        for(int i =x-1; i<=x+1; i++){
          for(int j = y-1;j <=y+1; j++){
              if(i==x && j ==y) //Does not demolish on current location of agent
                  continue;
              else if (i <0 || j <0 || j>=board.getBoard().length || i>=board.getBoard()[0].length)
                  continue;
              else board.getBoard()[j][i]=3;
          }
        }
        score-=4;
    }

    public ArrayList<Integer> surroundingValues(){
        int x = currLoc.getX();
        int y = currLoc.getY();
        ArrayList<Integer> vals = new ArrayList<>();
        for(int i =x-1; i<=x+1; i++){
            for(int j = y-1;j <=y+1; j++){
                if(i==x && j ==y) //Doesn't get current value, only surrounding
                    continue;
                else if (i <0 || j <0 || j>=board.getBoard().length || i>=board.getBoard()[0].length) //Makes sure coordinate is in board
                    continue;
                else vals.add(board.getVal(new Coord(i,j)));
            }
        }
        return vals;
    }

    /**
     * Gets coordinates of the Agent
     * @return current Coord of Agent
     */
    public Coord getCurrLoc() {
        return currLoc;
    }


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
     * @return Difference from object 2 to object 1
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
     * gets the directions to the goal
     * @return direction to the goal
     */
    public ArrayList<Direction> getDirectionToGoal(Coord G){
        ArrayList<Direction> directionsToGoal = new ArrayList<>();
        if(this.currLoc.getX() - G.getX() == 0){
        }
        else{
            directionsToGoal.add(this.currLoc.getX() - G.getX() > 0 ? Direction.LEFT : Direction.RIGHT);
        }
        if(this.currLoc.getY() - G.getY() == 0){
        }
        else{
            directionsToGoal.add(this.currLoc.getY() - G.getY() > 0 ? Direction.UP : Direction.DOWN);
        }
        return directionsToGoal;
    }

    public int getAverageInDirection(Coord G){
        if(G.equals(this.currLoc)){
            return 0;
        }
        int averageInDirection = 0;
        ArrayList<Direction> directions = getDirectionToGoal(G);

        if(directions.size()>1){
            averageInDirection = getDiagonalAverage(directions.get(0), directions.get(1));
        }
        else{
            averageInDirection = getStraightAverage(directions.get(0));
        }

        return averageInDirection;
    }

    /**
     * returns the diagonal values average
     * @param G goal
     * @param direction1 first direction to goal
     * @param direction2 second direction to goal
     * @return diagonal average
     */
    public int getDiagonalAverage(Direction direction1, Direction direction2){
        int diagonalAverage = 0;
        switch (direction1){
            case LEFT -> {
                if(direction2.equals(Direction.UP)){
                    diagonalAverage = diagonalAverage + board.getValFromXY(currLoc.getX()-1, currLoc.getY());
                    diagonalAverage = diagonalAverage + board.getValFromXY(currLoc.getX(), currLoc.getY()-1);
                    diagonalAverage = diagonalAverage + board.getValFromXY(currLoc.getX()-1, currLoc.getY()-1);
                }
                else{
                    diagonalAverage = diagonalAverage + board.getValFromXY(currLoc.getX()-1, currLoc.getY());
                    diagonalAverage = diagonalAverage + board.getValFromXY(currLoc.getX()-1, currLoc.getY())+1;
                    diagonalAverage = diagonalAverage + board.getValFromXY(currLoc.getX(), currLoc.getY()+1);
                }
            }
            case RIGHT -> {
                if(direction2.equals(Direction.UP)){
                    diagonalAverage = diagonalAverage + board.getValFromXY(currLoc.getX()+1, currLoc.getY());
                    diagonalAverage = diagonalAverage + board.getValFromXY(currLoc.getX()+1, currLoc.getY()-1);
                    diagonalAverage = diagonalAverage + board.getValFromXY(currLoc.getX(), currLoc.getY()-1);
                }
                else{
                    diagonalAverage = diagonalAverage + board.getValFromXY(currLoc.getX()+1, currLoc.getY());
                    diagonalAverage = diagonalAverage + board.getValFromXY(currLoc.getX()+1, currLoc.getY())+1;
                    diagonalAverage = diagonalAverage + board.getValFromXY(currLoc.getX(), currLoc.getY()+1);
                }
            }
        }
        return diagonalAverage / 3;
    }

    public int getStraightAverage(Direction direction){
        ArrayList<Integer> numbers = new ArrayList<>();
        int straightAverage = 0;
        int numberOfNeighbors = 0;
        switch (direction){
            case UP -> {
                numbers.add(board.getValFromXY(currLoc.getX()-1, currLoc.getY()-1));
                numbers.add(board.getValFromXY(currLoc.getX(), currLoc.getY()-1));
                numbers.add(board.getValFromXY(currLoc.getX()+1, currLoc.getY()-1));
                break;
            }
            case DOWN -> {
                numbers.add(board.getValFromXY(currLoc.getX()-1, currLoc.getY()+1));
                numbers.add(board.getValFromXY(currLoc.getX(), currLoc.getY()+1));
                numbers.add(board.getValFromXY(currLoc.getX()+1, currLoc.getY()+1));
                break;
            }
            case LEFT -> {
                numbers.add(board.getValFromXY(currLoc.getX()-1, currLoc.getY()-1));
                numbers.add(board.getValFromXY(currLoc.getX()-1, currLoc.getY()));
                numbers.add(board.getValFromXY(currLoc.getX()-1, currLoc.getY()+1));
                break;
            }
            case RIGHT -> {
                numbers.add(board.getValFromXY(currLoc.getX()+1, currLoc.getY()-1));
                numbers.add(board.getValFromXY(currLoc.getX()+1, currLoc.getY()));
                numbers.add(board.getValFromXY(currLoc.getX()+1, currLoc.getY()+1));
                break;
            }
        }
        for (int i : numbers){
            if(i != 0){
                straightAverage = straightAverage + i;
                numberOfNeighbors++;
            }
        }
        return straightAverage/numberOfNeighbors;
    }

    /**
     * gets the minimum number of turns to the goal
     * @return
     */
    public int getMinNumTurnsToGoal(Coord G){
        if(G.equals(this.currLoc)){
            return 0;
        }
        int turns = 0;
        ArrayList<Direction> directions = getDirectionToGoal(G);

        if(directions.size()>1){
            turns = notFacingRightWayDiagonal(directions.get(0), directions.get(1)) + 2;
        }
        else{
            turns = notFacingRightWayStraight(directions.get(0));
        }

        return turns;
    }

    public int notFacingRightWayStraight(Direction direction){
        return checkDirections(direction, direction == Direction.LEFT, direction == Direction.RIGHT);
    }

    public int notFacingRightWayDiagonal(Direction direction1, Direction direction2) {
        return checkDirections(direction2, direction1 == Direction.LEFT, direction1 == Direction.RIGHT);
    }


    private int checkDirections(Direction direction, boolean b, boolean b2) {
        switch (this.currDir){
            case UP -> {
                if(direction == Direction.UP){
                    return 0;
                }
                else if (direction == Direction.DOWN){
                    return 2;
                }
                else{
                    return 1;
                }
            }
            case DOWN -> {
                if(direction == Direction.DOWN){
                    return 0;
                }
                else if (direction == Direction.UP){
                    return 2;
                }
                else{
                    return 1;
                }
            }
            case LEFT -> {
                if(b){
                    return 0;
                }
                else if (b2){
                    return 2;
                }
                else{
                    return 1;
                }
            }
            case RIGHT -> {
                if(b2){
                    return 0;
                }
                else if (b){
                    return 2;
                }
                else{
                    return 1;
                }
            }
        }
        return 0;
    }


    /**
     * Sets heuristic of agent
     * @param heuristic Agent's heuristic
     */
    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public ArrayList<Agent> getAgentPath() {
        return agentPath;
    }

    public int getRotations() {
        return rotations;
    }

}
