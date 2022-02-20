package src;

import java.util.ArrayList;

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
    public int calculateHeuristic(Agent agent, Coord g){
        Coord a = agent.getCurrLoc();
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
            case 7:
                return newHeuristic(agent, g);
            default:
                return 0;
        }
    }

    private int newHeuristic(Agent a, Coord goal){
        float count = 0;
        int total = 0;
        ArrayList<Integer> neighborVals = a.surroundingValues();
        neighborVals.sort(Integer::compareTo);
        for(int j: neighborVals)
            total+=j;
        String action = a.getPath().get(a.getPath().size()-1);
        /*
        switch (action){
            case "Move Forward": case "Turn Clockwise": case
        }

         */
        count += 0.8547*Math.abs(goal.getX()-a.getCurrLoc().getX());
        count += 0.8467*Math.abs(goal.getY()-a.getCurrLoc().getY());
        count +=0.6826*(Math.abs(goal.getY()-a.getCurrLoc().getY()) + Math.abs(goal.getX()-a.getCurrLoc().getX()));
        count +=1.3688*(Math.sqrt((Math.pow(goal.getY()-a.getCurrLoc().getY(), 2) + Math.pow(goal.getX()-a.getCurrLoc().getX(),2))));
        count +=-0.0273* a.getRotations();
        count +=-5.3476*total/neighborVals.size();
        count +=0.0857*neighborVals.get(0);
        count+=0.2175*a.getAverageInDirection(goal);
        count +=2.1467;
        return Math.round(count);
    }
}
