package corejava.tasks.battleship;

public class Battleship8x8 {
    private final long ships;
    private long shots = 0L;

    public Battleship8x8(final long ships) {
        this.ships = ships;
    }

    public boolean shoot(String shot) {
        int col = shot.charAt(0) - 'A';
        int row = shot.charAt(1) - '1';
        int temp = 8 * row + col;
        int bit = 64 - temp - 1;
        long mask = 1L << bit;
        if ((shots & mask) == 1) { return false; }
        shots |= mask;
        return ((ships & mask) != 0);
    }

    public String state() {
        StringBuilder sb = new StringBuilder();
        int lineBreaker = 0;
        for (int i = 63; i >= 0; --i) {
            if (lineBreaker == 8) {
                sb.append('\n');
                lineBreaker = 0;
            }
            long mask = 1L << i;
            if ((ships & mask) == 0 && (shots & mask) == 0) { sb.append('.'); }
            else if ((ships & mask) == 0 && (shots & mask) != 0) { sb.append('×'); }
            else if ((ships & mask) != 0 && (shots & mask) == 0) { sb.append('☐'); }
            else if ((ships & mask) != 0 && (shots & mask) != 0) { sb.append('☒'); }
            ++lineBreaker;
        }
        return sb.toString();
    }
}
