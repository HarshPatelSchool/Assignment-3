package src;

import java.util.PriorityQueue;

public class AStar {
    private Coord G, S;
    private Agent[][] scores;
    private Heuristic h;
    private int rows, columns;

    public AStar(Board b){
        this.h = b.h;
        char[][] board = b.getBoard();
        rows = board.length;
        columns = board[0].length;
        scores = new Agent[rows][columns];
        for(int y = 0; y < rows; y++){
            for(int x = 0; x < columns; x++) {
                if (board[y][x] == 'G')
                    G = new Coord(x, y);
                else if (board[y][x] == 'S')
                    S = new Coord(x, y);
            }
        }

        run(new Agent(b, S.getX(), S.getY()));
        System.out.println(scores[G.getX()][G.getY()]);

    }

    private void run(Agent a) {
        int y = a.getCurrLoc().getY();
        int x = a.getCurrLoc().getX();
        Agent current = scores[y][x];
        if(current==null)
            nextStep(x,y,a);
        else if(x==G.getX() && y==G.getY() && current.getScore()<a.getScore()+100)
            scores[y][x]=new Agent(a.board, x, y, a.getScore()+100);
        else if(current.getScore()<a.getScore())
            nextStep(x, y, a);


    }
    private void nextStep(int x, int y, Agent a){
        scores[y][x] = a.clone();
        PriorityQueue<Agent> directions = new PriorityQueue<>();

        Agent bash = a.clone();
        bash.bash();

        Agent forward = a.clone();
        forward.moveForward();

        Agent clockwise = a.clone();
        clockwise.turn(Turn.CLOCKWISE);

        Agent counter = a.clone();
        counter.turn(Turn.COUNTERCLOCKWISE);

        if(bash.outOfBounds()==false) {
            bash.heuristic = heuristic(bash);
            directions.add(bash);
        }

        if(forward.outOfBounds()==false){
            forward.heuristic = heuristic(forward);
            directions.add(forward);
        }
        if(clockwise.outOfBounds()==false){
            clockwise.heuristic = heuristic(clockwise);
            directions.add(clockwise);
        }
        if(counter.outOfBounds()==false){
            counter.heuristic = heuristic(counter);
            directions.add(counter);
        }
        while(directions.size()>0) {
            Agent next = directions.remove();
            run(next);
        }
    }

    private int heuristic(Agent a){
        return a.getScore() - h.calculateHeuristic(a.getCurrLoc(), G);
    }

}
