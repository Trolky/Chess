import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class Tower extends ChessPiece{



    boolean hasMoved;
    public Tower(int row, int col, boolean isWhite, int tile_size, boolean hasMoved,int chessPieceType) {
        super(row, col, isWhite, "tower", tile_size,chessPieceType);
        this.hasMoved = hasMoved;
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
            if(board[toRow][toCol]!=null&&board[toRow][toCol].isWhite()==this.isWhite())return false;
            return true;
        }

        return false;
    }


    @Override
    public void draw(Graphics g) throws IOException {
        Graphics2D g2 = (Graphics2D) g;
        int index = 0;
        int x = super.getCol() * super.getTile_size();
        int y = super.getRow() * super.getTile_size();
        switch (chessPieceType){
            case 1:
                Rectangle2D bottom = new Rectangle2D.Double(x+getTile_size()/4.0, (((y+getTile_size())-(2*getTile_size()/8.0))),getTile_size()/2.0,getTile_size()/7.0);
                Rectangle2D body = new Rectangle2D.Double((x+getTile_size()/2.0)-(getTile_size()/4.0)/2,(y+getTile_size())-(2.5*getTile_size()/4.0),getTile_size()/4.0,getTile_size()/2.5);
                Rectangle2D top = new Rectangle2D.Double(x+getTile_size()/4.0, (y+getTile_size())-(1.5*getTile_size()/2.0),getTile_size()/2.0,getTile_size()/7.0);
                Rectangle2D wall1 = new Rectangle2D.Double((x+getTile_size()/4.0), (y+getTile_size())-(1.75*getTile_size()/2.0),getTile_size()/7.0,getTile_size()/7.0);
                Rectangle2D wall2 = new Rectangle2D.Double((x+getTile_size()/4.0)+((getTile_size()/2.0)-(getTile_size()/7.0)), (y+getTile_size())-(1.75*getTile_size()/2.0),getTile_size()/7.0,getTile_size()/7.0);
                Rectangle2D wall3 = new Rectangle2D.Double((x+getTile_size()/2.0)-(getTile_size()/7.0)/2, (y+getTile_size())-(1.75*getTile_size()/2.0),getTile_size()/7.0,getTile_size()/7.0);

                g2.setStroke(new BasicStroke(4));
                if(isWhite()){
                    g2.setColor(Color.BLACK);g2.draw(body);g2.draw(bottom);g2.draw(top);g2.draw(wall1);g2.draw(wall2);g2.draw(wall3);
                    g2.setColor(Color.WHITE);g2.fill(body);g2.fill(bottom);g2.fill(top);g2.fill(wall1);g2.fill(wall2);g2.fill(wall3);
                }
                else{
                    g2.setColor(Color.WHITE);g2.draw(body);g2.draw(bottom);g2.draw(top);g2.draw(wall1);g2.draw(wall2);g2.draw(wall3);
                    g2.setColor(Color.BLACK);g2.fill(body);g2.fill(bottom);g2.fill(top);g2.fill(wall1);g2.fill(wall2);g2.fill(wall3);

                }
                break;
            case 2:
                index = 4;
                if(!isWhite()) index +=6;
                g2.drawImage(loadImages.images1[index],getCol()*getTile_size(),getRow()*getTile_size(),getTile_size(),getTile_size(),null);
                break;
            case 3:
                if(isWhite()) index +=6;
                g2.drawImage(loadImages.images2[index], (getCol()*getTile_size())-2,getRow()*getTile_size(),getTile_size(),getTile_size(),null);
                break;
        }


    }
    public boolean isHasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

}
