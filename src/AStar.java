package src;

import java.util.PriorityQueue;

public class AStar {
    private Coord G, S;
    private Agent[][] scores;
    private Heuristic h;
    private int rows, columns;
    private Agent bestGoal;
    public AStar(Board b, Heuristic h){
        this.h = h; //Gets the heuristic function
        char[][] board = b.getBoard(); //Gets the board values
        rows = board.length; //Number of rows
        columns = board[0].length; //Number of columns
        scores = new Agent[rows][columns]; //Board that stores Agents and their paths

        /* Searches the value board for Start and Goal coordinates*/
        for(int y = 0; y < rows; y++){
            for(int x = 0; x < columns; x++) {
                if (board[y][x] == 'G')
                    G = new Coord(x, y); //Goal coordinate
                else if (board[y][x] == 'S')
                    S = new Coord(x, y); //Start coordinate
            }
        }

        run(new Agent(b, S.getX(), S.getY()), false); //Starts the search with a new agent placed on the start
        this.bestGoal = scores[G.getY()][G.getX()]; //Sets the bestGoal agent as the agent at the Goal coordinate after the algorithm is complete

    }

    /**
     * Recursive check that determines whether to continue or if a base case has been reached
     * @param a Agent to check
     */
    private void run(Agent a, boolean demolish) {
        int y = a.getCurrLoc().getY(); //Gets current Y value to check
        int x = a.getCurrLoc().getX(); //Gets current X value to check
        Agent current = scores[y][x]; //Gets the Agent currently at that spot

        if(x==G.getX() && y==G.getY()){ //Checks if goal is reached
            if(current==null) //Add agent if goal is null (hasn't been reached yet)
                scores[y][x] = new Agent(a.board, x, y, a.getCurrDir(), a.getScore() + 100,  a.getPath(), a.getNodes()); //+100 to score for reaching Goal
            else if(current.getScore()<a.getScore()+100) //Replace old agent if it has a lower score than the new one would have
                scores[y][x] = new Agent(a.board, x, y, a.getCurrDir(), a.getScore() + 100,  a.getPath(), a.getNodes()); //+100 to score for reaching Goal
        }else if(current==null) //Run update if position is null (hasn't been reached yet)
            nextStep(x,y,a, demolish);
        else if(current.getScore()<a.getScore() || demolish == true) //Checks if new Agent is better than old and runs update if it is
            nextStep(x, y, a, demolish);

    }

    /**
     * Updating the board and continuing down the recursion
     * @param x x-coordinate
     * @param y y-coordinate
     * @param a the new Agent for the coordinate
     */
    private void nextStep(int x, int y, Agent a, boolean demolish){
        if(demolish == false) scores[y][x] = a.clone(); //New agent is assigned to current coordinates
        PriorityQueue<Agent> directions = new PriorityQueue<>(); //PQ of the actions the Agent can make, sorted by heuristic
        boolean demolishB = false;
        /* These generate the possible actions the agent can make from the current coordinate */
        /* Bash action */
        Agent bash = a.clone();
        bash.bash();
        valid(bash, directions); //Makes sure action is a valid move and adds it to the pq if it is

        /* Forward action */
        Agent forward = a.clone();
        forward.moveForward();
        valid(forward, directions); ///Makes sure action is a valid move and adds it to the pq if it is

        /*Clockwise forward action*/
        Agent clockwiseForward = a.clone();
        clockwiseForward.turn(Turn.CLOCKWISE);
        clockwiseForward.moveForward();
        valid(clockwiseForward, directions); //Makes sure action is a valid move and adds it to the pq if it is

        /*Clockwise bash action*/
        Agent clockwiseBash = a.clone();
        clockwiseBash.turn(Turn.CLOCKWISE);
        clockwiseBash.bash();
        valid(clockwiseBash, directions); //Makes sure action is a valid move and adds it to the pq if it is

        /* Counterclockwise forward action*/
        Agent counterForward = a.clone();
        counterForward.turn(Turn.COUNTERCLOCKWISE);
        counterForward.moveForward();
        valid(counterForward, directions); //Makes sure action is a valid move and adds it to the pq if it is

        /* Counterclockwise bash action*/
        Agent counterBash = a.clone();
        counterBash.turn(Turn.COUNTERCLOCKWISE);
        counterBash.bash();
        valid(counterBash, directions); //Makes sure action is a valid move and adds it to the pq if it is

        /* Backwards Forward action*/
        Agent backwardsForward = a.clone();
        backwardsForward.turn(Turn.COUNTERCLOCKWISE);
        backwardsForward.turn(Turn.COUNTERCLOCKWISE);
        backwardsForward.moveForward();
        valid(backwardsForward, directions); //Makes sure action is a valid move and adds it to the pq if it is

        /* Backwards bash action*/
        Agent backwardsBash = a.clone();
        backwardsBash.turn(Turn.COUNTERCLOCKWISE);
        backwardsBash.turn(Turn.COUNTERCLOCKWISE);
        backwardsBash.bash();
        valid(backwardsBash, directions); //Makes sure action is a valid move and adds it to the pq if it is\

        /* Demolish action*/
        if(demolish == false) {
            Agent demolishA = a.clone();
            demolishA.demolish();
            valid(demolishA, directions); //Makes sure action is a valid move and adds it to the pq if it is
            demolishB = true;
        }

        while(directions.size()>0) { //Goes down the PQ one action at a time
            Agent next = directions.remove();  //Gets the top action of the PQ and continues the recursion with it
            run(next, demolishB); //Runs the Agent at the new coordinate
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
     * Adds agent to priority queue if it is a valid agent
     * @param a Agent being tested
     * @param pq Priority Queue to add to
     */
    private void valid(Agent a, PriorityQueue<Agent> pq){
        if(a.outOfBounds()==false){ //Adds counterclockwise bash to PQ if it is a valid action
            a.setHeuristic(heuristic(a));
            pq.add(a);
        }
    }

    /**
     * Returns the agent at the goal state
     * @return Agent at the coordinates of the goal
     */
    public Agent getBestGoal() {
        return bestGoal;
    }

}
