import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file=new File("17009.jpg");
        ContornoDetector cd=new ContornoDetector(file,10,3);
        cd.detectarContorno();
    }
}