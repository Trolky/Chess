import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class Bishop extends ChessPiece{
    public Bishop(int row, int col, boolean isWhite, int tile_size, int chessPieceType) {
        super(row, col, isWhite, "bishop", tile_size,chessPieceType);
    }

    @Override
    public boolean isValidMove(ChessPiece[][] board, int fromRow, int toRow, int fromCol, int toCol) {
        if (fromRow < 0 || fromRow > 7 || toRow < 0 || toRow > 7 || fromCol < 0 || fromCol > 7 || toCol < 0 || toCol > 7) {
            return false;
        }

        else if(fromRow == toRow&&fromCol == toCol)return false;

        else if (Math.abs(toRow - fromRow) == Math.abs(toCol - fromCol)) {
            int rowStep = (toRow - fromRow > 0) ? 1 : -1;
            int colStep = (toCol - fromCol > 0) ? 1 : -1;
            int checkRow = fromRow + rowStep;
            int checkCol = fromCol + colStep;
            while (checkRow != toRow && checkCol != toCol) {
                if (board[checkRow][checkCol] != null) {
                    return false;
                }
                checkRow += rowStep;
                checkCol += colStep;
            }
            return board[toRow][toCol] == null || board[toRow][toCol].isWhite() != this.isWhite();
        }

        return false;
    }



    @Override
    public void draw(Graphics g) throws IOException {
        Graphics2D g2 = (Graphics2D) g;
        int x = super.getCol();
        int y = super.getRow();
        int index = 0;
        switch (super.chessPieceType) {
            case 1:
                Path2D path = new Path2D.Double();
                path.moveTo(x+getTile_size()/4.0, y+getTile_size()-getTile_size()/8.0);
                path.curveTo(x+getTile_size()/5.0,y+((getTile_size()/4.0)*1.75),(x+getTile_size())-getTile_size()/5.0,y+((getTile_size()/4.0)*1.75),x+getTile_size()-getTile_size()/4.0,y+getTile_size()-getTile_size()/8.0);
                path.closePath();

                double middleBodyR = getTile_size()/3.0;
                Ellipse2D middleBody = new Ellipse2D.Double((x+getTile_size()/2.0)-(middleBodyR/2),y+getTile_size()/2.0-middleBodyR/2,middleBodyR,middleBodyR);


                Rectangle2D head = new Rectangle2D.Double((x+getTile_size()/2.0)-(getTile_size()/4.0),y+getTile_size()/2.0-middleBodyR,getTile_size()/4.0,getTile_size()/4.0);

                AffineTransform orig = g2.getTransform();
                AffineTransform at = AffineTransform.getRotateInstance(Math.PI/4, head.getX()+head.getWidth(), head.getY()+head.getHeight());
                g2.setStroke(new BasicStroke(4));
                if(isWhite()){
                    g2.setColor(Color.BLACK); g2.transform(at);g2.draw(head) ; g2.setTransform(orig);g2.draw(middleBody);g2.draw(path);
                    g2.setColor(Color.WHITE);g2.transform(at);g2.fill(head);g2.setTransform(orig);g2.fill(middleBody);g2.fill(path);
                }
                else{
                    g2.setColor(Color.WHITE);g2.transform(at);g2.draw(head);g2.setTransform(orig);g2.draw(middleBody);g2.draw(path);
                    g2.setColor(Color.BLACK);g2.transform(at);g2.fill(head);g2.setTransform(orig);g2.fill(middleBody);g2.fill(path);
                }
                break;
            case 2:
                index = 0;
                if(isWhite()) index +=6;
                g2.drawImage(loadImages.images1[index],getCol(),getRow(),getTile_size(),getTile_size(),null);
                break;
            case 3:
                index = 0;
                if(isWhite()) index +=6;
                g2.drawImage(loadImages.images2[index], getCol(),getRow(),getTile_size(),getTile_size(),null);
                break;
        }

    }
}
