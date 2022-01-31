package src;

/**
 * Stores cartesian coordinate values
 */
public class Coord {
    private int x, y;

    /**
     * Stores a new coordinate
     * @param xCoord x-coordinate value
     * @param yCoord y-coordinate value
     */
    public Coord(int xCoord, int yCoord) {
        x = xCoord;
        y = yCoord;
    }

    /**
     * Returns the x-coordinate
     * @return x value
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate
     * @param xCoord new value
     */
    public void setX(int xCoord) {
        x = xCoord;
    }
    /**
     * Returns the y-coordinate
     * @return y value
     */
    public int getY() {
        return y;
    }
    /**
     * Sets the y-coordinate
     * @param yCoord new value
     */
    public void setY(int yCoord) {
        y = yCoord;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
