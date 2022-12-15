import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File file=new File("ilulu.jpeg");


        ContornoDetector cd=new ContornoDetector(file);

        cd.detectarContorno(false,2,5,"cosita");






    }
}