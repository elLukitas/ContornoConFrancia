import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file=new File("helado.jpg");
        ContornoDetector cd=new ContornoDetector(file);
        cd.detectarContorno();
    }
}
