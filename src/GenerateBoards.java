package src;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateBoards {
    public static void generateBoards(int numberOfBoards, int rows, int columns) throws IOException {
        Random r = new Random();
        int nextInt;
        int sx,gx,sy,gy;

        for(int i = 1; i<=numberOfBoards; i++){
            do{
                sx = r.nextInt(columns);
                gx = r.nextInt(columns);
                sy=r.nextInt(rows);
                gy=r.nextInt(rows);
            }while(sx==gx && sy==gy); //Prevents overlapping Start and Goal coordinates

            FileWriter board = new FileWriter("src/boards/board_"+i+".txt");
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
               if(y!=rows) board.write("\n");
            }
            board.close();
        }
    }
}
