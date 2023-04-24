import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;


public class King extends ChessPiece{
    boolean hasMoved;
    boolean isInCheck;

    public King(int row, int col, boolean isWhite, int tile_size, boolean hasMoved, int chessPieceType) {
        super(row, col, isWhite, "king", tile_size,chessPieceType);
        this.hasMoved = hasMoved;
    }

    @Override
    public boolean isValidMove(ChessPiece[][] board, int fromRow, int toRow, int fromCol, int toCol) {
        if (fromRow < 0 || fromRow > 7 || toRow < 0 || toRow > 7 || fromCol < 0 || fromCol > 7 || toCol < 0 || toCol > 7) {
            return false;
        }


        else if(fromRow == toRow&&fromCol == toCol) return false;
        else if (Math.abs(toRow - fromRow) <= 1 && Math.abs(toCol - fromCol) <= 1) return board[toRow][toCol] == null || board[toRow][toCol].isWhite() != this.isWhite();
        else return isCastling(board, fromCol, toCol) && !hasMoved && (toRow == fromRow);

    }



    public boolean isCastling(ChessPiece[][] board, int fromCol, int toCol){
        Tower t ;
        int row = 0;
        if (this.isWhite()) row = 7;

        if(Math.abs(toCol-fromCol) == 2) {
            if (toCol < fromCol) {
                if (board[row][0] != null && board[row][0].getType().equals("tower")) {
                    t = (Tower) board[row][0];
                    if (!t.isHasMoved()) return board[row][1] == null && board[row][2] == null && board[row][3] == null;
                }
                return false;
            }
            else if(toCol > fromCol) {
                if (board[row][7] != null && board[row][7].getType().equals("tower")) {
                    t = (Tower) board[row][7];
                    if (!t.isHasMoved()) return board[row][5] == null && board[row][6] == null;
                }
                return false;
            }
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
              Path2D path = new Path2D.Double();
              path.moveTo(x+getTile_size()/4.0, y+getTile_size()-getTile_size()/8.0);
              path.curveTo(x+getTile_size()/5.0,y+((getTile_size()/4.0)*1.75),(x+getTile_size())-getTile_size()/5.0,y+((getTile_size()/4.0)*1.75),x+getTile_size()-getTile_size()/4.0,y+getTile_size()-getTile_size()/8.0);
              path.closePath();

              double middleBodyR = getTile_size()/3.0;
              Ellipse2D middleBody = new Ellipse2D.Double((x+getTile_size()/2.0)-(middleBodyR/2),y+getTile_size()/2.0-middleBodyR/2,middleBodyR,middleBodyR);

              Rectangle2D head1 = new Rectangle2D.Double((x+getTile_size()/2.0)-(getTile_size()/3.0)/2,y+getTile_size()/2.0-middleBodyR,getTile_size()/3.0,getTile_size()/12.0);
              Rectangle2D head2 = new Rectangle2D.Double((x+getTile_size()/2.0)-(getTile_size()/12.0)/2,y+getTile_size()/12.0,getTile_size()/12.0,getTile_size()/3.0);

              g2.setStroke(new BasicStroke(4));
              if(isWhite()){
                  g2.setColor(Color.BLACK);g2.draw(head1);g2.draw(head2);g2.draw(middleBody);g2.draw(path);
                  g2.setColor(Color.WHITE);g2.fill(head1);g2.fill(head2);g2.fill(middleBody);g2.fill(path);
              }
              else{
                  g2.setColor(Color.WHITE);g2.draw(head1);g2.draw(head2);g2.draw(middleBody);g2.draw(path);
                  g2.setColor(Color.BLACK);g2.fill(head1);g2.fill(head2);g2.fill(middleBody);g2.fill(path);
              }
              break;
          case 2:
              if(!isWhite()) index +=6;
              g2.drawImage(loadImages.images1[index],getCol()*getTile_size(),getRow()*getTile_size(),getTile_size(),getTile_size(),null);
              break;
          case 3:
              index = 3;
              if(isWhite()) index +=6;
              g2.drawImage(loadImages.images2[index], (getCol()*getTile_size())-2,getRow()*getTile_size(),getTile_size(),getTile_size(),null);
              break;
      }

    }

}
