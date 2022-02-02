package src;

/**
 * Creates the heuristic values
 */
public class Heuristic {
    private int hType; //Determines how the Heuristic will be calculated

    /**
     * Creates an Heuristic object that calculates the heuristic in different ways based on user input
     * @param hType an int 1-6 that corresponds to different ways the heuristics can be calculated
     */
    public Heuristic(int hType){
        this.hType = hType;
    }

    /**
     * Calculates the heuristic value of a node
     * @param a coordinates of agent
     * @param g coordinates of goal
     * @return calculated heuristic value at the current node
     */
    public int calculateHeuristic(Coord a, Coord g){
        int ay = a.getY(), ax = a.getX(), gy=g.getY(), gx=g.getX();
        int vertical = Math.abs(ay-gy); //Vertical distance between agent and goal
        int horizontal = Math.abs(ax-gx); //Horizontal distance between agent and goal
        int result;
        /* Switch case is for different ways of calculating the heuristic*/
        switch(hType){
            case 1: //Heuristic is 0 (uninformed search)
                return 0;
            case 2: //Heuristic is whichever coordinate distance is smaller
                return Math.min(horizontal, vertical);
            case 3: //Heuristic is whichever coordinate distance is larger
                return Math.max(horizontal, vertical);
            case 4: //Heuristic is the sum of the two coordinate distances
                return vertical + horizontal;
            case 5: //Returns rounded down straight line distance
                result = vertical + horizontal;
                /* Since a turn always costs at least a time of 1 since it rounds up, if the vertical and horizontal
                distance are both positive, it must have at least one turn, so the heuristic can be the manhattan score + 1,
                otherwise it is just a straight line from S to G so it is just manhattan distance.
                 */
                if(vertical !=0 && horizontal!=0)
                    result++;
                return result;
            case 6: //Returns rounded down straight line distance * 3
                result = vertical + horizontal;
                if(vertical !=0 && horizontal!=0)
                    result++;
                return 3* result;
            default:
                return 0;
        }
    }
}
