import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File file=new File("god.jpg");
        File file1=new File("gorila.jpg");
        BufferedImage i= ImageIO.read(file);

        ContornoDetector cd=new ContornoDetector(file,10);


            cd.blender(file1);
        System.out.println(i.getType());

    }
}