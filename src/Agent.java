package src;

enum Direction{
    UP,
    DOWN,
    LEFT,
    RIGHT
}
enum Turn{
    CLOCKWISE,
    COUNTERCLOCKWISE
}
public class Agent {
    Board board;
    private Coord currLoc;
    private Direction currDir;
    private int score;

    public Agent() {
    }

    public void moveForward() {
        int x, y;
        switch(currDir) {
            case UP: // x stays the same
                y = currLoc.getY() - 1;
                currLoc.setY(y);
                score = score + board.getVal(currLoc);
                break;
            case DOWN: // x stays the same
                y = currLoc.getY() + 1;
                currLoc.setY(y);
                score = score + board.getVal(currLoc);
                break;
            case LEFT: // y stays the same
                x = currLoc.getX() - 1;
                currLoc.setX(x);
                score = score + board.getVal(currLoc);
                break;
            case RIGHT: // y stays the same
                x = currLoc.getX() + 1;
                currLoc.setX(x);
                score = score + board.getVal(currLoc);
                break;
        }
    }
    //turn
    public void turn(Turn t) {
         int currLocVal = board.getVal(currLoc);
        //check turning direction
        //change currDir based on turning direction
    }
    //bash
    public void bash() {
        //at the end call moveForward() maybe?
    }

    //Extra Credit: demolish
}
