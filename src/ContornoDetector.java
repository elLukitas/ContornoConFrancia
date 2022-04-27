import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContornoDetector {
    File file;
    int threshold;
    int numeroPixelesDiferentes;

    public ContornoDetector(File file, int threshold, int numeroPixelesDiferentes) {
        if (file.exists()) {
            this.file = file;
            this.threshold = threshold;
            this.numeroPixelesDiferentes = numeroPixelesDiferentes;
        }
    }

    public void detectarContorno() {
        File grey = blureador();
        ArrayList<int[]> lista = new ArrayList<>();
        try {
            BufferedImage img = ImageIO.read(grey);
            BufferedImage orignal = ImageIO.read(file);
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    int reference = img.getRGB(i, j);
                    Color color = new Color(reference);
                    int numeroPixeles = 0;
                    for (int k = -1; k < 2; k++) {
                        for (int l = -1; l < 2; l++) {
                            int nuevo = rgb(img, i + k, j + l);
                            if (nuevo != -1) {
                                Color newColor = new Color(nuevo);
                                if (Math.abs(color.getRed() - newColor.getRed()) > threshold) {
                                    numeroPixeles++;
                                    if (numeroPixeles >= numeroPixelesDiferentes) {
                                        int[] pixelesDeI = {i, j};
                                        lista.add(pixelesDeI);

                                    }
                                }
                            }
                        }
                    }


                }
            }
            for (int[] posicion : lista) {
                orignal.setRGB(posicion[0], posicion[1], Color.red.getRGB());
            }
            File fileModified = new File("contornoPillao.jpg");
            ImageIO.write(orignal, "jpg", fileModified);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File greyConverterBuffered() {
        try {
            BufferedImage img = ImageIO.read(file);
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    int pixel = img.getRGB(i, j);
                    Color color = new Color(pixel);
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    int media = ((red + green + blue) / 3);
                    color = new Color(media, media, media);
                    int newPixel = color.getRGB();
                    img.setRGB(i, j, newPixel);
                }
            }
            File fileGrey = new File("ficheroGris.jpg");
            ImageIO.write(img, "jpg", fileGrey);
            return fileGrey;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean comprobator(BufferedImage img, int x, int y) {
        return x > 0 & y > 0 & x < img.getWidth() & y < img.getHeight();


    }

    private int rgb(BufferedImage img, int x, int y) {
        if (comprobator(img, x, y)) {
            return img.getRGB(x, y);
        }
        return -1;
    }
    private int red(BufferedImage img, int x, int y) {
        if (comprobator(img, x, y)) {
            int pa= img.getRGB(x, y);
            Color color=new Color(pa);
            return color.getRed();
        }
        return -1;
    }


    public File blackWhiteTransformers() {
        try {
            BufferedImage img = ImageIO.read(file);
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    int pixel = img.getRGB(i, j);
                    Color color = new Color(pixel);
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    int media = ((red + green + blue) / 3);
                    if (media > 120) {
                        red = 255;
                        blue = 255;
                        green = 255;
                    } else {
                        red = 0;
                        blue = 0;
                        green = 0;
                    }
                    color = new Color(red, green, blue);
                    int newPixel = color.getRGB();
                    img.setRGB(i, j, newPixel);
                }
            }
            File fileBlancoNegro = new File("blancoNegro.jpg");
            ImageIO.write(img, "jpg", fileBlancoNegro);
            return fileBlancoNegro;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public File blureador() {
        File grey=greyConverterBuffered();

        try {
            BufferedImage img = ImageIO.read(grey);
            BufferedImage orignal = ImageIO.read(file);
            List<Pixelmon>lista=new ArrayList<>();
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    int contable=0;
                    int pixels=0;
                    for (int k = -1; k < 2; k++) {
                        for (int l = -1; l < 2; l++) {
                            int nuevo = red(img, i + k, j + l);
                            if (nuevo != -1) {
                                pixels++;
                                contable+=nuevo;

                            }
                        }
                    }
                    int media=contable/pixels;
                    Color color=new Color(media,media,media);
                    Pixelmon pixelmon=new Pixelmon(i,j,color);


                    lista.add(pixelmon);


                }
            }
            for (Pixelmon pixelmon : lista) {

                        orignal.setRGB(pixelmon.getX(),pixelmon.getY(),pixelmon.getColor().getRGB());



            }
            File fileModified = new File("imagenBlureada.jpg");
            ImageIO.write(orignal, "jpg", fileModified);
            return fileModified;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
