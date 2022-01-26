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
     * @return
     */
    public int calculateHeuristic(){
        switch(hType){
            case 1:
                return 0;
            default:
                return 0;
        }
    }
}
