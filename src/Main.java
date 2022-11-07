import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File file=new File("17009.jpg");
        BufferedImage i= ImageIO.read(file);

        ContornoDetector cd=new ContornoDetector(file);


            cd.detectarContorno(false,3,3);


    }
}