package src;

import java.util.PriorityQueue;

public class AStar {
    private Coord G, S;
    private Agent[][] scores;
    private Heuristic h;
    private int rows, columns;

    public AStar(Board b){
        this.h = b.getHeuristic(); //Gets the heuristic function
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

        run(new Agent(b, S.getX(), S.getY())); //Starts the search with a new agent placed on the start
        System.out.println(scores[G.getY()][G.getX()].getPath()); //todo

    }

    /**
     * Recursive check that determines whether to continue or if a base case has been reached
     * @param a Agent to check
     */
    private void run(Agent a) {
        int y = a.getCurrLoc().getY(); //Gets current Y value to check
        int x = a.getCurrLoc().getX(); //Gets current X value to check
        Agent current = scores[y][x]; //Gets the Agent currently at that spot

        if(current==null) //If no Agent already there run the update
            nextStep(x,y,a);
        else if(x==G.getX() && y==G.getY() && current.getScore()<(a.getScore()+100)) { //Checks if Goal has been reached with a better method
            scores[y][x] = new Agent(a.board, x, y, a.getScore() + 100,  a.getPath()); //+100 to score for reaching Goal
        }
        else if(current.getScore()<a.getScore()) //Checks if new Agent is better than old and runs update if it is
            nextStep(x, y, a);


    }

    /**
     * Updating the board and continuing down the recursion
     * @param x x-coordinate
     * @param y y-coordinate
     * @param a Agent
     */
    private void nextStep(int x, int y, Agent a){
        scores[y][x] = a.clone(); //New agent is assigned to current coordinates
        PriorityQueue<Agent> directions = new PriorityQueue<>(); //PQ of the actions the Agent can make

        /* Bash action */
        Agent bash = a.clone();
        bash.bash();

        /* Forward action */
        Agent forward = a.clone();
        forward.moveForward();

        /*Clockwise forward action*/
        Agent clockwiseForward = a.clone();
        clockwiseForward.turn(Turn.CLOCKWISE);
        clockwiseForward.moveForward();

        /* Counterclockwise forward action*/
        Agent counterForward = a.clone();
        counterForward.turn(Turn.COUNTERCLOCKWISE);
        counterForward.moveForward();

        /*Clockwise forward action*/
        Agent clockwiseBash = a.clone();
        clockwiseBash.turn(Turn.CLOCKWISE);
        clockwiseBash.bash();

        /* Counterclockwise forward action*/
        Agent counterBash = a.clone();
        counterBash.turn(Turn.COUNTERCLOCKWISE);
        counterBash.bash();

        if(bash.outOfBounds()==false) { //Adds bash action to PQ if it is a valid action
            bash.setHeuristic(heuristic(bash));
            directions.add(bash);
        }

        if(forward.outOfBounds()==false){ //Adds forward action to PQ if it is a valid action
            forward.setHeuristic(heuristic(forward));
            directions.add(forward);
        }

        if(!clockwiseForward.outOfBounds()){ //Adds clockwise forward to PQ if it is a valid action
            clockwiseForward.setHeuristic(heuristic(clockwiseForward));
            directions.add(clockwiseForward);
        }
        if(counterForward.outOfBounds()==false){ //Adds counterclockwise bash to PQ if it is a valid action
            counterForward.setHeuristic(heuristic(counterForward));
            directions.add(counterForward);
        }
        if(counterBash.outOfBounds()==false){ //Adds counterclockwise bash to PQ if it is a valid action
            counterBash.setHeuristic(heuristic(counterBash));
            directions.add(counterBash);
        }
        if(counterBash.outOfBounds()==false){ //Adds counterclockwise bash to PQ if it is a valid action
            counterBash.setHeuristic(heuristic(counterBash));
            directions.add(counterBash);
        }

        while(directions.size()>0) { //Goes down the PQ one action at a time
            Agent next = directions.remove();  //Gets the top action of the PQ and continues the recursion with it
            run(next);
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

}
