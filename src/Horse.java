import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class Horse extends ChessPiece{
    public Horse(int row, int col, boolean isWhite, int tile_size,int chessPieceType) {
        super(row, col, isWhite, "horse", tile_size,chessPieceType);
    }

    @Override
    public boolean isValidMove(ChessPiece[][] board, int fromRow, int toRow, int fromCol, int toCol) {
        if (fromRow < 0 || fromRow > 7 || toRow < 0 || toRow > 7 || fromCol < 0 || fromCol > 7 || toCol < 0 || toCol > 7) {
            return false;
        }
        else if(fromRow == toRow)return false;
        else if(Math.abs(toRow - fromRow) == 2 && Math.abs(toCol - fromCol) == 1 || (Math.abs(toRow - fromRow) == 1 && Math.abs(toCol - fromCol) == 2)){
            return board[toRow][toCol] == null || board[toRow][toCol].isWhite() != this.isWhite();
        }
        return false;
    }


    @Override
    public void draw(Graphics g) throws IOException {
        Graphics2D g2 = (Graphics2D) g;
        int x = super.getCol() * super.getTile_size();
        int y = super.getRow() * super.getTile_size();
        int index = 0;
        switch (chessPieceType){
            case 1:
                Path2D path = new Path2D.Double();
                path.moveTo(x+getTile_size()/4.0, y+getTile_size()-getTile_size()/8.0);
                path.curveTo(x+getTile_size()/5.0,y+((getTile_size()/4.0)*1.75),(x+getTile_size())-getTile_size()/5.0,y+((getTile_size()/4.0)*1.75),x+getTile_size()-getTile_size()/4.0,y+getTile_size()-getTile_size()/8.0);
                path.closePath();

                double middleBodyR = getTile_size()/3.0;
                Ellipse2D middleBody = new Ellipse2D.Double((x+getTile_size()/2.0)-(middleBodyR/2),y+getTile_size()/2.0-middleBodyR/2,middleBodyR,middleBodyR);

                double headR = getTile_size()/4.0;
                Ellipse2D head = new Ellipse2D.Double((x+getTile_size()/2.0)-headR*1.5,y+getTile_size()/2.0-(1.5*headR),headR*2,headR);

                double eyeR = getTile_size()/8.0;
                Ellipse2D eye = new Ellipse2D.Double((x+getTile_size()/2.0)-eyeR,y+getTile_size()/2.0-(1.5*headR),eyeR,eyeR);

                g2.setStroke(new BasicStroke(4));
                if(isWhite()){
                    g2.setColor(Color.BLACK);g2.draw(head);g2.draw(middleBody);g2.draw(path);
                    g2.setColor(Color.WHITE);g2.fill(head);g2.fill(middleBody);g2.fill(path);
                    g2.setColor(Color.BLACK);g2.fill(eye);
                }
                else{
                    g2.setColor(Color.WHITE);g2.draw(head);g2.draw(middleBody);g2.draw(path);
                    g2.setColor(Color.BLACK);g2.fill(head);g2.fill(middleBody);g2.fill(path);
                    g2.setColor(Color.WHITE);g2.fill(eye);
                }
                break;
            case 2:
                index = 3;
                if(!isWhite()) index +=6;
                g2.drawImage(loadImages.images1[index],getCol()*getTile_size(),getRow()*getTile_size(),getTile_size(),getTile_size(),null);
                break;
            case 3:
                index = 4;
                if(isWhite()) index +=6;
                g2.drawImage(loadImages.images2[index], (getCol()*getTile_size())-1,getRow()*getTile_size(),getTile_size(),getTile_size(),null);
                break;
        }

    }
}
