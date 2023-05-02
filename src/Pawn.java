import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class Pawn extends ChessPiece{
    public boolean justMovedByTwo;
    boolean hasMoved;
    public Pawn(int row, int col, boolean isWhite, int tile_size,boolean hasMoved,int chessPieceType) {
        super(row, col, isWhite, "pawn", tile_size,chessPieceType);
        this.hasMoved = hasMoved;
    }


    @Override
    public boolean isValidMove(ChessPiece[][] board,int fromRow, int toRow,int fromCol, int toCol) {

        if (toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7) {
            return false;
        }
        int Row = (super.isWhite()) ? -1 : 1;
        if (fromCol == toCol && board[toRow][toCol] == null) {
            if (toRow == fromRow + Row) {
                return true;
            }
            else if (!hasMoved && toRow == fromRow + 2*Row && board[fromRow + Row][fromCol] == null) {
                return true;
            }
        }

        if (Math.abs(toCol - fromCol) == 1 && toRow == fromRow + Row) {
            if (board[toRow][toCol] != null && board[toRow][toCol].isWhite() == !super.isWhite()) {
                return true;
            }
        }

        return enPassant(board,fromRow,toRow,fromCol,toCol);
    }


    public boolean enPassant(ChessPiece[][] board, int fromRow, int toRow, int fromCol, int toCol){
        int Row = (super.isWhite()) ? -1 : 1;

        if (Math.abs(toCol - fromCol) == 1 && toRow == fromRow + Row) {
            if (board[fromRow][toCol] != null && board[fromRow][toCol].isWhite() == !this.isWhite() && board[fromRow][toCol].getType().equals("pawn")) {
                Pawn tempPawn = (Pawn) board[fromRow][toCol];
                if (tempPawn.justMovedByTwo) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void draw(Graphics g) throws IOException {
        Graphics2D g2 = (Graphics2D) g;
        int index = 0;
        int x = super.getCol();
        int y = super.getRow();
        switch (chessPieceType){
            case 1:
                Path2D path = new Path2D.Double();
                path.moveTo(x+getTile_size()/4.0, y+getTile_size()-getTile_size()/8.0);
                path.curveTo(x+getTile_size()/5.0,y+((getTile_size()/4.0)*1.75),(x+getTile_size())-getTile_size()/5.0,y+((getTile_size()/4.0)*1.75),x+getTile_size()-getTile_size()/4.0,y+getTile_size()-getTile_size()/8.0);
                path.closePath();

                double middleBodyR = getTile_size()/3.0;
                Ellipse2D middleBody = new Ellipse2D.Double((x+getTile_size()/2.0)-(middleBodyR/2),y+getTile_size()/2.0-middleBodyR/2,middleBodyR,middleBodyR);

                double headR = getTile_size()/4.0;
                Ellipse2D head = new Ellipse2D.Double((x+getTile_size()/2.0)-headR/2,y+getTile_size()/2.0-middleBodyR,headR,headR);


                g2.setStroke(new BasicStroke(4));
                if(isWhite()){
                    g2.setColor(Color.BLACK);g2.draw(head);g2.draw(middleBody);g2.draw(path);
                    g2.setColor(Color.WHITE);g2.fill(head);g2.fill(middleBody);g2.fill(path);
                }
                else{
                    g2.setColor(Color.WHITE);g2.draw(head);g2.draw(middleBody);g2.draw(path);
                    g2.setColor(Color.BLACK);g2.fill(head);g2.fill(middleBody);g2.fill(path);
                }
                break;
            case 2:
                index = 3;
                if(isWhite()) index +=6;
                g2.drawImage(loadImages.images1[index],getCol(),getRow(),getTile_size(),getTile_size(),null);
                break;
            case 3:
                index = 3;
                if(isWhite()) index +=6;
                g2.drawImage(loadImages.images2[index], getCol(),getRow(),getTile_size(),getTile_size(),null);
                break;
        }

    }
}
