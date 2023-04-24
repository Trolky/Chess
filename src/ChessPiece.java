import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ChessPiece {
    private int row;
    private int col;
    private boolean isWhite;
    private String type;
    private int tile_size;
    int chessPieceType;

    public ChessPiece(int row, int col, boolean isWhite, String type, int tile_size,int chessPieceType) {
        this.row = row;
        this.col = col;
        this.isWhite = isWhite;
        this.type = type;
        this.tile_size = tile_size;
        this.chessPieceType = chessPieceType;
    }

    public boolean isValidMove(ChessPiece[][] board, int fromRow,int toRow, int fromCol,int toCol) {
        return true;
    }

    public boolean isInCheck(ChessPiece[][] board, boolean isWhite){
        int kingRow = 0;
        int kingCol = 0;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                if (piece!=null&&piece.getType().equals("king") && piece.isWhite() == isWhite) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null && piece.isWhite() != isWhite) {
                    if (piece.isValidMove(board, row, kingRow, col, kingCol)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public boolean isCheckmate(ChessPiece[][] board, boolean isWhite) {
        if (!isInCheck(board, isWhite)) {
            return false;
        }

        for (int fromRow = 0; fromRow < 8; fromRow++) {
            for (int fromCol = 0; fromCol < 8; fromCol++) {
                ChessPiece piece = board[fromRow][fromCol];
                if (piece != null && piece.isWhite() == isWhite) {
                    for (int toRow = 0; toRow < 8; toRow++) {
                        for (int toCol = 0; toCol < 8; toCol++) {
                            if (piece.isValidMove(board, fromRow, toRow, fromCol, toCol)) {
                                ChessPiece[][] testBoard = copyBoard(board);
                                testBoard[toRow][toCol] = testBoard[fromRow][fromCol];
                                testBoard[fromRow][fromCol] = null;
                                if (!isInCheck(testBoard, isWhite)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;

    }

    public ChessPiece[][] copyBoard(ChessPiece[][] board){
        ChessPiece[][] tempBoard = new ChessPiece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
               tempBoard[row][col]  = board[row][col];
            }
        }
        return tempBoard;
    }


    public boolean isCastling(ChessPiece[][] board,int selectedPiece_col,int col){
        return true;
    }

    public void setTile_size(int tile_size) {
        this.tile_size = tile_size;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void move(int toRow, int toCol) {
        this.row = toRow;
        this.col = toCol;
    }

    public void draw(Graphics g) throws IOException {

    }


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getTile_size() {
        return tile_size;
    }


    public boolean isWhite() {
        return isWhite;
    }

    public String getType() {
        return type;
    }

}
