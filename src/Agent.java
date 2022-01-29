package src;

public class Agent {
    Board board;
    private Coord currLoc;
    private Direction currDir;
    private int score;

    public Agent(Board b, int x, int y, Direction startDir) {
        board = b;
        currLoc = new Coord(x, y);
        currDir = startDir;
        score = 0;
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
    //turn --> this could probably be optimized, just didn't want to do it right now
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
         }
         score += Math.round(currLocVal / 2); //this is probably wrong rounding, will fix later
    }
    //bash
    public void bash() {
        //at the end call moveForward() maybe?
    }

    public Coord getCurrLoc() {
        return currLoc;
    }

    public Direction getCurrDir() {
        return currDir;
    }

    public int getScore() {
        return score;
    }

    //Extra Credit: demolish
}
