package animation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.Buffer;

public class animator {
    private BufferedImage img;

    private BufferedImage[] IdleAnimation, RunningAnimation;

    public animator(BufferedImage[] imageArray, BufferedImage img){

    }

    private void loadAnimations(BufferedImage[] imageArray,int numImages, BufferedImage img) {
        imageArray = new BufferedImage[numImages];
        for(int i = 0; i< imageArray.length; i++){
            imageArray[i] = img.getSubimage(i* 32,0, 32,80);
        }
    }

//    private void importImg() {
//        try{
//            img = ImageIO.read(new FileInputStream("res/kknight_Idle.png"));
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//    }

}
