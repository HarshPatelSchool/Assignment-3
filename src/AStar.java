package src;

import java.util.TreeMap;

public class AStar {
    private Coord G, S;
    private Heuristic h;
    private Board b;
    private Agent[][] scores;
    private int rows, columns;

    public AStar(Board b, Heuristic h){
        char[][] board = b.getBoard();
        rows = board.length;
        columns = board[0].length;
        scores = new Agent[rows][columns];
        this.b = b;
        for(int y = 0; y < rows; y++){
            for(int x = 0; x < columns; x++) {
                if (board[y][x] == 'G')
                    G = new Coord(x, y);
                else if (board[y][x] == 'S')
                    S = new Coord(x, y);
            }
        }

        scores[S.getY()][S.getX()] = new Agent(b, S.getX(), S.getY()); //Replace with .clone eventually
        run(S.getX(), S.getY(), new Agent(b, S.getX(), S.getY()));
    }

    private void run(int x, int y, Agent a) {
        if(x != G.getX() && y!= G.getY()){
            if(scores[x][y]==null || scores[x][y].getScore() < a.getScore()) {
                TreeMap<Integer, Coord> directions = new TreeMap<>();
                if (y + 1 < rows)
                    directions.put(h.calculateHeuristic(x, y+1, G.getX(), G.getY()) + b.getVal(new Coord(x , y+1)), new Coord(x,y+1));
                if (x + 1 < columns)
                    directions.put(h.calculateHeuristic(x +1 , y, G.getX(), G.getY()) + b.getVal(new Coord(x+1, y)), new Coord(x+1, y));
                if (y - 1 >= 0)
                    directions.put(h.calculateHeuristic(x, y-1, G.getX(), G.getY()) + b.getVal(new Coord(x , y-1)), new Coord(x,y-1));
                if (x - 1 >= 0)
                    directions.put(h.calculateHeuristic(x -1 , y, G.getX(), G.getY()) + b.getVal(new Coord(x-1, y)), new Coord(x-1, y));

            }
        }
    }

}
