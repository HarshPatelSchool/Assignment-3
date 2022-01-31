package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateBoards {
    public static void generateBoards(int numberOfBoards, int rows, int columns) throws IOException {
        Random r = new Random();
        int nextInt;
        for(int i = 1; i<=numberOfBoards; i++){
            int sx = r.nextInt(columns), gx = r.nextInt(columns), sy=r.nextInt(rows), gy=r.nextInt(rows);
            FileWriter board = new FileWriter("board_"+i+".txt");
            for(int y = 0; y < rows; y++){
                for(int x = 0; x<columns; x++){
                    if(sx == x && sy == y)
                        board.write("S\t");
                    else if(gx==x && gy == y)
                        board.write("G\t");
                    else {
                        nextInt = r.nextInt(9)+1;
                        board.write(nextInt+"\t");
                    }
                }
                board.write("\n");
            }
            board.close();
        }
    }
}
