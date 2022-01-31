package src;

public enum Direction {
        UP(0),
        RIGHT(1),
        DOWN(2),
        LEFT(3) ;

        public final int label;
        Direction(int i) {
                this.label = i;
        }
}
