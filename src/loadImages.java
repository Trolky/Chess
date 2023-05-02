import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class loadImages {
    public static Image[] images1,images2;
    public static boolean loaded;
    public loadImages(){
    }

    public static void load(int tile_size){
        try {
            images1 = getImages(tile_size);
            images2 = getImages2(tile_size);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Image[] getImages(int tile_size) throws IOException {
        Image[] images = new Image[]{
                ImageIO.read(new File("Resources/Pieces/Set1/BlackBishop.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set1/BlackHorse.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set1/BlackKing.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set1/BlackPawn.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set1/BlackQueen.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set1/BlackTower.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set1/WhiteBishop.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set1/WhiteHorse.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set1/WhiteKing.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set1/WhitePawn.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set1/WhiteQueen.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set1/WhiteTower.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH)
        };
        return images;
    }

    public static Image[] getImages2(int tile_size) throws IOException {
        Image[] images = new Image[]{
                ImageIO.read(new File("Resources/Pieces/Set2/BlackBishop.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set2/BlackHorse.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set2/BlackKing.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set2/BlackPawn.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set2/BlackQueen.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set2/BlackTower.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set2/WhiteBishop.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set2/WhiteHorse.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set2/WhiteKing.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set2/WhitePawn.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set2/WhiteQueen.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH),
                ImageIO.read(new File("Resources/Pieces/Set2/WhiteTower.png")).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH)
        };
        return images;
    }

    public static boolean hasLoaded(){
        return loaded;
    }
}
