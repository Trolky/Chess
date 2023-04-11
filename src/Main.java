import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static JFrame frame;
    public static ChessBoard board;
    public static Result result;
    public static void main(String[] args) {
        frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board= new ChessBoard();
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.add(board);
        frame.setVisible(true);
    }

    public static void results(String Winner, ArrayList<Double> wTime, ArrayList<Double> bTime){
        result = new Result(Winner,wTime,bTime);
        frame.remove(board);
        frame.setSize(700,600);
        frame.setLocationRelativeTo(null);
        frame.add(result);
        frame.setVisible(true);
        frame.repaint();
    }

    public static void restart(){
        board = new ChessBoard();
        frame.remove(result);
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.add(board);
        frame.setVisible(true);
        frame.repaint();
    }
}