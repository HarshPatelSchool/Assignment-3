package src;

public class Coord {
    private int x, y;

    public Coord(int xCoord, int yCoord) {
        x = xCoord;
        y = yCoord;
    }

    public int getX() {
        return x;
    }

    public void setX(int xCoord) {
        x = xCoord;
    }

    public int getY() {
        return y;
    }

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
