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
        BufferedImage all = ImageIO.read(new File("Resources/chess.png"));
        Image[] images = new Image[12];
        int ind = 0;
        for(int y=0;y<400;y+=200){
            for(int x=0;x<1200;x+=200){
                images[ind] = all.getSubimage(x,y,200,200).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH);
                ind++;
            }
        }
        return images;
    }

    public static Image[] getImages2(int tile_size) throws IOException {
        BufferedImage all = ImageIO.read(new File("Resources/chess2.png"));
        Image[] images = new Image[12];
        int ind = 0;
        for(int y=0;y<800;y+=400){
            for(int x=0;x<1800;x+=300){
                images[ind] = all.getSubimage(x,y,300,400).getScaledInstance(tile_size,tile_size,BufferedImage.SCALE_SMOOTH);
                ind++;
            }
        }
        return images;
    }

    public static boolean hasLoaded(){
        return loaded;
    }
}
