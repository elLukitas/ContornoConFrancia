import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File file=new File("cuadrado.jpg");
        File file1=new File("gorila.jpg");
        BufferedImage i= ImageIO.read(file);

        ContornoDetector cd=new ContornoDetector(file,20);


            cd.detectarContorno(1);


    }
}