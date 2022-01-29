package src;

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
        scores[G.getY()][G.getX()] = new Agent(b, G.getX(), G.getY(), Direction.UP);
        run(G.getX(), G.getY());
    }

    private void run(int x, int y){
        int[] directionScores = {0,0,0,0};
        if(G.getX() + 1 < columns && G.getY() < rows);


    }
}
