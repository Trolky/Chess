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
    public static JFrame frame2;
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

    public static void change(ChessPiece[][] Chessboard, int row, int col, boolean isWhite,int tile_size, int chessTypePiece){
        JDialog dialog = new JDialog(frame,"Chess Pieces types",true);
        dialog.setLayout(new GridLayout(1,0));
        JButton queen = new JButton("Queen");
        JButton tower = new JButton("Tower");
        JButton bishop = new JButton("Bishop");
        JButton horse = new JButton("Horse");
        dialog.setSize(new Dimension(400,100));
        dialog.setLocationRelativeTo(null);
        queen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chessboard[row][col] = new Queen(row*tile_size,col*tile_size,isWhite,tile_size,chessTypePiece);
                dialog.dispose();
            }
        });
        tower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chessboard[row][col] = new Tower(row*tile_size,col*tile_size,isWhite,tile_size,false,chessTypePiece);
                dialog.dispose();
            }
        });
        bishop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chessboard[row][col] = new Bishop(row*tile_size,col*tile_size,isWhite,tile_size,chessTypePiece);
                dialog.dispose();
            }
        });
        horse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Chessboard[row][col] = new Horse(row*tile_size,col*tile_size,isWhite,tile_size,chessTypePiece);
                dialog.dispose();
            }
        });
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        dialog.add(queen); dialog.add(tower); dialog.add(bishop); dialog.add(horse);
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setVisible(true);
    }
}