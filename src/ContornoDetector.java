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
        File grey = blureador(file,4);
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
                                if (Math.abs(color.getRed() - newColor.getRed()) > threshold || Math.abs(color.getBlue() - newColor.getBlue()) > threshold || Math.abs(color.getGreen() - newColor.getGreen()) > threshold) {
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
    private int[] red(BufferedImage img, int x, int y) {
        if (comprobator(img, x, y)) {
            int pa= img.getRGB(x, y);
            Color color=new Color(pa);
            int [] colores={color.getRed(),color.getGreen(),color.getBlue()};
            return colores;
        }
        return null;
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

    public File blureador(File file,int nivel) {
        File grey=file;



        try {
            BufferedImage img = ImageIO.read(grey);
            BufferedImage orignal = ImageIO.read(file);
            List<Pixelmon>lista=new ArrayList<>();
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    int r=0;
                    int g=0;
                    int b=0;
                    int pixels=0;
                    for (int k = 1-nivel; k < nivel; k++) {
                        for (int l = -nivel; l < nivel; l++) {
                            int [] nuevo = red(img, i + k, j + l);
                            if (nuevo != null) {
                                pixels++;
                                r+=nuevo[0];
                                g+=nuevo[1];
                                b+=nuevo[2];

                            }
                        }
                    }
                    int rMedia=r/pixels;
                    int gMedia=g/pixels;
                    int bMedia=b/pixels;

                    Color color=new Color(rMedia,gMedia,bMedia);
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
    public File contrastador(File file) {
        File grey =file;
        int contraste = 20;
        int threshold = getLuminosidad(file);

        try {
            BufferedImage img = ImageIO.read(grey);

            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color color = new Color(img.getRGB(i, j));
                    int red;
                    if (color.getRed() > threshold-30) {
                        red = color.getRed();

                    } else {
                        red = color.getRed() - contraste;
                        if (red < 0) {
                            red = 0;
                        }
                    }
                    Color contrasted = new Color(red, red, red);
                    img.setRGB(i, j, contrasted.getRGB());

                }
            }


            File fileModified = new File("imagenContrastada.jpg");
            ImageIO.write(img, "jpg", fileModified);
            return fileModified;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public int getLuminosidad(File file) {
        File grey = file;


        try {
            BufferedImage img = ImageIO.read(grey);

            double contator=0;
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color color = new Color(img.getRGB(i, j));
                    contator+=color.getRed();

                    }
                }
            double media=contator/ (img.getHeight()* img.getWidth());
            System.out.println(media);

            return (int) media;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public File borrator(File file) {
        File grey = file;

        try {
            BufferedImage img = ImageIO.read(grey);
            int luminosidad=getLuminosidad(file);

            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color color = new Color(img.getRGB(i, j));
                    if (color.getRed()>luminosidad+((255-luminosidad)/3)){
                        img.setRGB(i,j,Color.white.getRGB());
                    }
                }
            }


            File fileModified = new File("imagenBorreada.jpg");
            ImageIO.write(img, "jpg", fileModified);
            return fileModified;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
