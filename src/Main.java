import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.*;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Main {
    public static JFrame frame;
    public static JPanel board;
    public static Result result;
    public static void main(String[] args) {
        frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board = new JPanel();


        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Export");
        JMenuItem toPNG = new JMenuItem("Export to PNG");
        JMenuItem toSVG = new JMenuItem("Export to SVG");
        toPNG.addActionListener(Main::exportToPNG);
        toSVG.addActionListener(Main::exportToSVG);
        bar.add(menu);menu.add(toPNG);menu.add(toSVG);
        JRadioButton j1 = new JRadioButton("Chess pieces set 1");
        JRadioButton j2 = new JRadioButton("Chess pieces set 2");
        JRadioButton j3 = new JRadioButton("Chess pieces set 3");
        ButtonGroup group = new ButtonGroup();
        group.add(j1);group.add(j2);group.add(j3);
        group.setSelected(j1.getModel(),true);
        j1.setActionCommand("1"); j2.setActionCommand("2"); j3.setActionCommand("3");
        JButton button = new JButton("Start game");
        board.setLayout(new GridLayout(4,0));
        board.add(button);
        board.add(j1);board.add(j2);board.add(j3);
        j1.setHorizontalAlignment(SwingConstants.CENTER);
        j2.setHorizontalAlignment(SwingConstants.CENTER);
        j3.setHorizontalAlignment(SwingConstants.CENTER);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int chessPiecesType = Integer.parseInt(group.getSelection().getActionCommand());
                start(chessPiecesType);
            }
        });
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setJMenuBar(bar);
        frame.add(board);
        frame.setVisible(true);
    }

    public static void results(String Winner, ArrayList<Double> wTime, ArrayList<Double> bTime,int chessTypePieces){
        result = new Result(Winner,wTime,bTime,chessTypePieces);
        frame.remove(board);
        frame.setSize(700,600);
        frame.setLocationRelativeTo(null);
        frame.add(result);
        frame.setVisible(true);
        frame.repaint();
    }


    public static void exportToPNG(ActionEvent e) {
        BufferedImage image = new BufferedImage(board.getWidth(),board.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = image.createGraphics();
        board.printAll(g);
        try {
            ImageIO.write(image,"png",new File("board.png"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public static void exportToSVG(ActionEvent e) {
        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
        String svgNS = "src/";
        Document document = domImpl.createDocument(svgNS,"svg",null);
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);
        Writer out;
        try {
            out = new FileWriter("board.svg",StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        board.paint(svgGenerator);
        try {
            svgGenerator.stream(out,true);
        } catch (SVGGraphics2DIOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void restart(int chessPiecesType){
        board = new ChessBoard(chessPiecesType);
        frame.remove(result);
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.add(board);
        frame.setVisible(true);
        frame.repaint();
    }

    public static void start(int chessPiecesType){
        frame.remove(board);
        board = new ChessBoard(chessPiecesType);
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.add(board);
        frame.setVisible(true);
        frame.repaint();
    }

    public static void change(ChessPiece[][] board, int row, int col, boolean isWhite,int tile_size, int chessTypePiece){
        JFrame frame2 = new JFrame("Change");
        JPanel panel = new JPanel();
        frame2.setSize(300,100);
        frame2.setMinimumSize(new Dimension(300,150));
        frame2.setMaximumSize(new Dimension(300,150));
        frame2.setLocationRelativeTo(null);
        panel.setLayout(new GridLayout(1,0));
        JButton queen = new JButton("Queen");
        JButton tower = new JButton("Tower");
        JButton bishop = new JButton("Bishop");
        JButton horse = new JButton("Horse");

        queen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board[row][col] = new Queen(row,col,isWhite,tile_size,chessTypePiece);
                frame2.dispose();
            }
        });
        tower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board[row][col] = new Tower(row,col,isWhite,tile_size,false,chessTypePiece);
                frame2.dispose();
            }
        });
        bishop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board[row][col] = new Bishop(row,col,isWhite,tile_size,chessTypePiece);
                frame2.dispose();
            }
        });
        horse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board[row][col] = new Horse(row,col,isWhite,tile_size,chessTypePiece);
                frame2.dispose();
            }
        });
        panel.add(queen);panel.add(tower);panel.add(bishop);panel.add(horse);
        frame2.add(panel);
        frame2.setVisible(true);
    }
}