import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class ChessBoard extends JPanel {
    private final int size = 8;
    private ChessPiece[][] board;
    private ChessPiece selectedPiece;
    public int tile_size;
    int selectedPiece_row, selectedPiece_col;
    boolean chessPieces_drawn = false;
    int heightDif, widthDif;
    boolean whiteTurn = true;
    Long start = System.currentTimeMillis();
    ArrayList<Double> wTime = new ArrayList<>();
    ArrayList<Double> bTime = new ArrayList<>();
    Timer timer;
    int elapsedTime = 0;
    int chessPiecesType;

    public ChessBoard(int chessPiecesType) {
        board = new ChessPiece[size][size];
        this.chessPiecesType = chessPiecesType;
        addMouseListeners();
    }


    public void addMouseListeners(){
            this.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (selectedPiece != null) {
                        selectedPiece.setCol((e.getX() - widthDif));
                        selectedPiece.setRow((e.getY() - heightDif));
                        repaint();
                    }
                }
            });

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if ((e.getY() - heightDif) / tile_size >= 0 && (e.getY() - heightDif) / tile_size < 8 && (e.getX() - widthDif) / tile_size >= 0 && (e.getX() - widthDif) / tile_size < 8) {
                        if (board[(e.getY() - heightDif) / tile_size][(e.getX() - widthDif) / tile_size] != null
                                && board[(e.getY() - heightDif) / tile_size][(e.getX() - widthDif) / tile_size].isWhite() == whiteTurn) {
                            selectedPiece = board[(e.getY() - heightDif) / tile_size][(e.getX() - widthDif) / tile_size];
                            selectedPiece_row = selectedPiece.getRow()/tile_size;
                            selectedPiece_col = selectedPiece.getCol()/tile_size;
                        } else selectedPiece = null;
                    }
                }
            });

            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    int row = (e.getY() - heightDif);
                    int col = (e.getX() - widthDif);
                    timer = new Timer(25, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(selectedPiece!=null) {
                                double percentComplete = (double) elapsedTime / 500;
                                int rowStep = (row/tile_size - selectedPiece_row >= 0) ? 1 : -1;
                                int colStep = (col/tile_size - selectedPiece_col >= 0) ? 1 : -1;
                                if (percentComplete < 1.0) {
                                    int newRow = (int) (selectedPiece_row*tile_size + ((Math.abs(((row/tile_size)*tile_size) - selectedPiece_row*tile_size) *percentComplete) * rowStep));
                                    int newCol = (int) (selectedPiece_col*tile_size + ((Math.abs(((col/tile_size)*tile_size)- selectedPiece_col*tile_size) *percentComplete) * colStep));
                                    selectedPiece.move(newRow, newCol);
                                    elapsedTime += timer.getDelay();
                                } else {
                                    selectedPiece.move((row/tile_size)*tile_size, (col/tile_size)*tile_size);
                                    board[row/tile_size][col/tile_size] = selectedPiece;
                                    board[selectedPiece_row][selectedPiece_col] = null;
                                    elapsedTime = 0;
                                    whiteTurn = !whiteTurn;
                                    timer.stop();
                                    if (selectedPiece.getType().equals("pawn")) {Pawn p = (Pawn) selectedPiece;if (Math.abs((row/tile_size) - selectedPiece_row) == 2) {p.hasMoved = true;p.justMovedByTwo = true;} else {p.hasMoved = true;p.justMovedByTwo = false;}}
                                    if (selectedPiece.getType().equals("pawn")) {Pawn p = (Pawn) selectedPiece;if (p.enPassant(board, selectedPiece_row, row/tile_size, selectedPiece_col, col/tile_size)) {int newRow;if (p.isWhite()) newRow = (row/tile_size) + 1;else newRow = (row/tile_size) - 1;board[newRow][col/tile_size] = null;}}
                                }
                                repaint();
                            }
                        }
                    });
                    boolean white = whiteTurn;
                    if (selectedPiece != null && selectedPiece.isValidMove(board, selectedPiece_row, row/tile_size, selectedPiece_col, col/tile_size) && !check(row/tile_size, col/tile_size)) {
                        if (selectedPiece.getType().equals("king") && selectedPiece.isCastling(board, selectedPiece_col, col/tile_size)) {
                            King k = (King) selectedPiece;
                            k.hasMoved = true;
                            doCastling(selectedPiece, row/tile_size, col/tile_size);
                            whiteTurn = !whiteTurn;

                        } else if ((selectedPiece.getType().equals("pawn") && selectedPiece.getRow()/tile_size == 0 && selectedPiece.isWhite())
                                || (selectedPiece.getType().equals("pawn") && selectedPiece.getRow()/tile_size == 7 && !selectedPiece.isWhite())) {
                            timer.start();
                            Main.change(board,row/tile_size,col/tile_size,selectedPiece.isWhite(),tile_size,chessPiecesType);

                        } else {
                            if (selectedPiece.getType().equals("king")) {King k = (King) selectedPiece;k.hasMoved = true;}
                            if (selectedPiece.getType().equals("tower")) {Tower t = (Tower) selectedPiece;t.hasMoved = true;}
                            timer.start();
                            long end = System.currentTimeMillis();
                            if (white) wTime.add((end - start) / 1000.0);
                            else bTime.add((end - start) / 1000.0);
                            start = System.currentTimeMillis();
                        }

                    } else if (selectedPiece != null) {
                        selectedPiece.move(selectedPiece_row*tile_size, selectedPiece_col*tile_size);
                    }
                    setPawnDoubleMove();
                    repaint();
                }
            });
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(selectedPiece!=null&&selectedPiece.isCheckmate(board,whiteTurn)){
            String winner;
            if(whiteTurn)winner ="black";
            else winner = "white";
            Main.results(winner,wTime,bTime,chessPiecesType);
        }

        int scale_x = this.getWidth() / 8;
        int scale_y = this.getHeight() / 8;

        this.tile_size = Math.min(scale_x, scale_y);

        if(!loadImages.hasLoaded()){
            loadImages.load(tile_size);
            loadImages.loaded = true;
        }

        if (this.getWidth() > this.getHeight()) {
            g.translate((this.getWidth() / 2) - (4 * tile_size), 0);
            widthDif = (this.getWidth() / 2) - (4 * tile_size);
            heightDif = 0;
        }
        else {g.translate(0, (this.getHeight() / 2) - (4 * tile_size));
            heightDif = (this.getHeight() / 2) - (4 * tile_size);
            widthDif = 0;
        }
        if (!chessPieces_drawn) {
            board = fillChessBoard();
            chessPieces_drawn = true;
        }

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if ((row + col) % 2 == 0) {
                    g.setColor(Color.lightGray);
                } else {
                    g.setColor(Color.gray);
                }
                g.fillRect(col * tile_size, row * tile_size, tile_size, tile_size);
            }
        }

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if(board[row][col]!=null) {
                    try {
                        if(board[row][col].getTile_size()!=tile_size){
                            board[row][col].setTile_size(tile_size);
                            board[row][col].move(row*tile_size,col*tile_size);
                        }
                        board[row][col].draw(g);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        if (selectedPiece != null) {
            BasicStroke stroke = new BasicStroke(5);g2.setStroke(stroke);g2.setColor(Color.GREEN);
            for(int row = 0;row<8;row++){
                for(int col = 0;col<8;col++){
                    if(selectedPiece.isValidMove(board,selectedPiece_row,row,selectedPiece_col,col)&&!check(row,col)){
                        g2.drawRect(col*tile_size,row*tile_size,tile_size,tile_size);
                    }
                }
            }

            g2.setColor(Color.blue);
            g2.drawRect(selectedPiece_col * tile_size, selectedPiece_row * tile_size, tile_size, tile_size);
            g2.drawRect(selectedPiece.getCol() , selectedPiece.getRow(), tile_size, tile_size);
        }
    }

    public void setPawnDoubleMove(){
        for(int row = 0;row<8;row++){
            for(int col = 0;col<8;col++){
                if(board[row][col]!=null&&board[row][col].getType().equals("pawn")&&board[row][col].isWhite()==whiteTurn){
                    Pawn tempP = (Pawn) board[row][col];
                    tempP.justMovedByTwo=false;
                }
            }
        }
    }



    public boolean check(int row,int col){
        ChessPiece[][] tempBoard= selectedPiece.copyBoard(board);
        ChessPiece tempPiece = new ChessPiece(selectedPiece.getRow(),selectedPiece_col,selectedPiece.isWhite(),selectedPiece.getType(),selectedPiece.getTile_size(),chessPiecesType);
          if(tempPiece.isInCheck(tempBoard,whiteTurn)){
            tempPiece.move(row*tile_size,col*tile_size);
            tempBoard[selectedPiece_row][selectedPiece_col] = null;
            tempBoard[row][col] = tempPiece;
            return tempPiece.isInCheck(tempBoard, whiteTurn);
          }
          else{
              tempPiece.move(row*tile_size,col*tile_size);
              tempBoard[selectedPiece_row][selectedPiece_col] = null;
              tempBoard[row][col] = tempPiece;
              return tempPiece.isInCheck(tempBoard,whiteTurn);
          }
    }




    public void doCastling(ChessPiece selectedPiece, int row, int col) {
        int r = 0;
        if (selectedPiece.isWhite()) r = 7;

        if (col == 2 && row == r) {
            timer.start();
            board[row][col] = selectedPiece;
            board[selectedPiece_row][selectedPiece_col] = null;
            board[r][0].move(r*tile_size, 3*tile_size);
            board[r][3] = board[r][0];
            board[r][0] = null;
            repaint();
        } else if (col == 6 && row == r) {
            timer.start();
            board[row][col] = selectedPiece;
            board[selectedPiece_row][selectedPiece_col] = null;
            board[r][7].move(r*tile_size, 5*tile_size);
            board[r][5] = board[r][7];
            board[r][7] = null;
            repaint();
        }
    }

    public ChessPiece[][] fillChessBoard() {
        ChessPiece[][] pieces = new ChessPiece[size][size];
        King wKing = new King(7*tile_size, 4*tile_size, true, tile_size,false,chessPiecesType);
        Tower wTower = new Tower(7*tile_size, 0, true, tile_size,false,chessPiecesType);
        Tower wTower2 = new Tower(7*tile_size, 7*tile_size, true, tile_size,false,chessPiecesType);
        Bishop wBishop = new Bishop(7*tile_size, 2*tile_size, true, tile_size,chessPiecesType);
        Bishop wBishop2 = new Bishop(7*tile_size, 5*tile_size, true, tile_size,chessPiecesType);
        Queen wQueen = new Queen(7*tile_size, 3*tile_size, true, tile_size,chessPiecesType);
        Pawn wPawn = new Pawn(6*tile_size, 0, true, tile_size,false,chessPiecesType);
        Pawn wPawn2 = new Pawn(6*tile_size, tile_size, true, tile_size,false,chessPiecesType);
        Pawn wPawn3 = new Pawn(6*tile_size, 2*tile_size, true, tile_size,false,chessPiecesType);
        Pawn wPawn4 = new Pawn(6*tile_size, 3*tile_size, true, tile_size,false,chessPiecesType);
        Pawn wPawn5 = new Pawn(6*tile_size, 4*tile_size, true, tile_size,false,chessPiecesType);
        Pawn wPawn6 = new Pawn(6*tile_size, 5*tile_size, true, tile_size,false,chessPiecesType);
        Pawn wPawn7 = new Pawn(6*tile_size, 6*tile_size, true, tile_size,false,chessPiecesType);
        Pawn wPawn8 = new Pawn(6*tile_size, 7*tile_size, true, tile_size,false,chessPiecesType);
        Horse wHorse = new Horse(7*tile_size, tile_size, true, tile_size,chessPiecesType);
        Horse wHorse2 = new Horse(7*tile_size, 6*tile_size, true, tile_size,chessPiecesType);
        ChessPiece[] whitePieces = new ChessPiece[]{wTower, wHorse, wBishop, wQueen, wKing, wBishop2, wHorse2, wTower2};
        ChessPiece[] whitePawns = new ChessPiece[]{wPawn, wPawn2, wPawn3, wPawn4, wPawn5, wPawn6, wPawn7, wPawn8};

        King bKing = new King(0, 4*tile_size, false, tile_size,false,chessPiecesType);
        Tower bTower = new Tower(0, 0, false, tile_size,false,chessPiecesType);
        Tower bTower2 = new Tower(0, 7*tile_size, false, tile_size,false,chessPiecesType);
        Bishop bBishop = new Bishop(0, 2*tile_size, false, tile_size,chessPiecesType);
        Bishop bBishop2 = new Bishop(0, 5*tile_size, false, tile_size,chessPiecesType);
        Queen bQueen = new Queen(0, 3*tile_size, false, tile_size,chessPiecesType);
        Pawn bPawn = new Pawn(tile_size, 0, false, tile_size,false,chessPiecesType);
        Pawn bPawn2 = new Pawn(tile_size, tile_size, false, tile_size,false,chessPiecesType);
        Pawn bPawn3 = new Pawn(tile_size, 2*tile_size, false, tile_size,false,chessPiecesType);
        Pawn bPawn4 = new Pawn(tile_size, 3*tile_size, false, tile_size,false,chessPiecesType);
        Pawn bPawn5 = new Pawn(tile_size, 4*tile_size, false, tile_size,false,chessPiecesType);
        Pawn bPawn6 = new Pawn(tile_size, 5*tile_size, false, tile_size,false,chessPiecesType);
        Pawn bPawn7 = new Pawn(tile_size, 6*tile_size, false, tile_size,false,chessPiecesType);
        Pawn bPawn8 = new Pawn(tile_size, 7*tile_size, false, tile_size,false,chessPiecesType);
        Horse bHorse = new Horse(0, tile_size, false, tile_size,chessPiecesType);
        Horse bHorse2 = new Horse(0, 6*tile_size, false, tile_size,chessPiecesType);
        ChessPiece[] blackPieces = new ChessPiece[]{bTower, bHorse, bBishop, bQueen, bKing, bBishop2, bHorse2, bTower2,};
        ChessPiece[] blackPawns = new ChessPiece[]{bPawn, bPawn2, bPawn3, bPawn4, bPawn5, bPawn6, bPawn7, bPawn8};

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < whitePieces.length; j++) {
                if (i == 0) {
                    pieces[i + 7][j] = whitePieces[j];
                    pieces[i][j] = blackPieces[j];
                } else {
                    pieces[i + 5][j] = whitePawns[j];
                    pieces[i][j] = blackPawns[j];
                }
            }
        }
        return pieces;
    }
}