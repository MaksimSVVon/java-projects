package corejava.tasks.spiral;

import java.util.Scanner;

public class Spiral {
    public static int[][] spiral(int n, int m) {
        Scanner scanner = new Scanner(System.in);
        int direction = 0;
        int[][] arr = new int[n][m];
        for (int i = 0; i < n; ++i)
            for (int j = 0; j < m; ++j)
                arr[i][j] = 0;
        final int RIGHT = 0, DOWN = 1, LEFT = 2, UP = 3;
        int it = 0, jt = 0;
        for (int i = 1; i <= n * m; ++i) {
            arr[it][jt] = i;
            switch (direction) {
                case RIGHT:
                    if (jt + 1 == m || arr[it][jt + 1] != 0) {
                        it++;
                        direction = DOWN;
                    } else ++jt;
                    break;
                case DOWN:
                    if (it + 1 == n || arr[it + 1][jt] != 0) {
                        jt--;
                        direction = LEFT;
                    } else ++it;
                    break;
                case LEFT:
                    if (jt - 1 == -1 || arr[it][jt - 1] != 0) {
                        it--;
                        direction = UP;
                    } else --jt;
                    break;
                case UP:
                    if (it - 1 == -1 || arr[it - 1][jt] != 0) {
                        jt++;
                        direction = RIGHT;
                    } else --it;
            }
        }
        return arr;
    }
}