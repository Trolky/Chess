import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.io.IOException;

public class Queen extends ChessPiece{
    public Queen(int row, int col, boolean isWhite, int tile_size, int chessPieceType) {
        super(row, col, isWhite, "queen", tile_size,chessPieceType);
    }

    @Override
    public boolean isValidMove(ChessPiece[][] board, int fromRow, int toRow, int fromCol, int toCol) {
        if (fromRow < 0 || fromRow > 7 || toRow < 0 || toRow > 7 || fromCol < 0 || fromCol > 7 || toCol < 0 || toCol > 7) {
            return false;
        }
        else if(fromRow == toRow&&fromCol == toCol)return false;
        else if (fromRow == toRow || fromCol == toCol) {
            int rowStep = Integer.compare(toRow, fromRow);
            int colStep = Integer.compare(toCol, fromCol);
            int checkRow = fromRow + rowStep;
            int checkCol = fromCol + colStep;
            while (checkRow != toRow || checkCol != toCol) {
                if (board[checkRow][checkCol] != null) {
                    return false;
                }
                checkRow += rowStep;
                checkCol += colStep;
            }
            return board[toRow][toCol] == null || board[toRow][toCol].isWhite() != this.isWhite();
        }

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
        int index = 0;
        int x = super.getCol();
        int y = super.getRow();
        switch(chessPieceType){
            case 1:
                Path2D path = new Path2D.Double();
                path.moveTo(x+getTile_size()/4.0, y+getTile_size()-getTile_size()/8.0);
                path.curveTo(x+getTile_size()/5.0,y+((getTile_size()/4.0)*1.75),(x+getTile_size())-getTile_size()/5.0,y+((getTile_size()/4.0)*1.75),x+getTile_size()-getTile_size()/4.0,y+getTile_size()-getTile_size()/8.0);
                path.closePath();

                double middleBodyR = getTile_size()/3.0;
                Ellipse2D middleBody = new Ellipse2D.Double((x+getTile_size()/2.0)-(middleBodyR/2),y+getTile_size()/2.0-middleBodyR/2,middleBodyR,middleBodyR);

                double headR = getTile_size()/8.0;
                Ellipse2D head1 = new Ellipse2D.Double((x+getTile_size()/8.0),y+getTile_size()/12.0,headR,headR);
                Ellipse2D head2 = new Ellipse2D.Double((x+getTile_size()/2.0)-1.25*headR,y+getTile_size()/12.0,headR,headR);
                Ellipse2D head3 = new Ellipse2D.Double((x+getTile_size()/2.0)+0.25*headR,y+getTile_size()/12.0,headR,headR);
                Ellipse2D head4 = new Ellipse2D.Double((x+(getTile_size()-getTile_size()/4.0)),y+getTile_size()/12.0,headR,headR);

                Line2D line1 = new Line2D.Double((((x+getTile_size()/2.0)-(middleBodyR/2)))+middleBodyR/2,((y+getTile_size()/2.0)-(middleBodyR/2))+middleBodyR/2,(x+getTile_size()/8.0)+headR/2,(y+getTile_size()/12.0)+headR/2);
                Line2D line2 = new Line2D.Double((((x+getTile_size()/2.0)-(middleBodyR/2)))+middleBodyR/2,((y+getTile_size()/2.0)-(middleBodyR/2))+middleBodyR/2,((x+getTile_size()/2.0)-1.25*headR)+headR/2,(y+getTile_size()/12.0)+headR/2);
                Line2D line3 = new Line2D.Double((((x+getTile_size()/2.0)-(middleBodyR/2)))+middleBodyR/2,((y+getTile_size()/2.0)-(middleBodyR/2))+middleBodyR/2,((x+getTile_size()/2.0)+0.25*headR)+headR/2,(y+getTile_size()/12.0)+headR/2);
                Line2D line4 = new Line2D.Double((((x+getTile_size()/2.0)-(middleBodyR/2)))+middleBodyR/2,((y+getTile_size()/2.0)-(middleBodyR/2))+middleBodyR/2,(x+(getTile_size()-getTile_size()/4.0))+headR/2,(y+getTile_size()/12.0)+headR/2);

                g2.setStroke(new BasicStroke(4));
                if(isWhite()){
                    g2.setColor(Color.BLACK);g2.draw(line1);g2.draw(line2);g2.draw(line3);g2.draw(line4);g2.draw(head1);g2.draw(head2);g2.draw(head3);g2.draw(head4);g2.draw(middleBody);g2.draw(path);
                    g2.setColor(Color.WHITE);g2.fill(head1);g2.fill(head2);g2.fill(head3);g2.fill(head4);g2.fill(middleBody);g2.fill(path);
                }
                else{
                    g2.setColor(Color.WHITE);g2.draw(line1);g2.draw(line2);g2.draw(line3);g2.draw(line4);g2.draw(head1);g2.draw(head2);g2.draw(head3);g2.draw(head4);g2.draw(middleBody);g2.draw(path);
                    g2.setColor(Color.BLACK);g2.fill(head1);g2.fill(head2);g2.fill(head3);g2.fill(head4);g2.fill(middleBody);g2.fill(path);
                }
                break;
            case 2:
                index = 4;
                if(isWhite()) index +=6;
                g2.drawImage(loadImages.images1[index],getCol(),getRow(),getTile_size(),getTile_size(),null);
                break;
            case 3:
                index = 4;
                if(isWhite()) index +=6;
                g2.drawImage(loadImages.images2[index], getCol(),getRow(),getTile_size(),getTile_size(),null);
                break;
        }

    }
}
