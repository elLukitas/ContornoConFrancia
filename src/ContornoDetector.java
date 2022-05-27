import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ContornoDetector {
    File file;
    int threshold;
    int numeroPixelesDiferentes;

    public ContornoDetector(File file, int threshold) {
        if (file.exists()) {
            this.file = file;
            this.threshold = threshold;

        }
    }

    public void detectarContorno(int nivel) {

        File grey = blureador(file, nivel);
        ArrayList<int[]> lista = new ArrayList<>();


        try {
            BufferedImage img = ImageIO.read(grey);

            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    int reference = img.getRGB(i, j);
                    Color color = new Color(reference);

                    int[] position = {i, j};

                    int menosmenos = rgb(img, i - 1, j - 1);
                    Color un = new Color(menosmenos);
                    int ceromenos = rgb(img, i, j - 1);
                    Color dos = new Color(ceromenos);
                    int unomenos = rgb(img, i + 1, j - 1);
                    Color tres = new Color(unomenos);
                    int menoscero = rgb(img, i - 1, j);
                    Color cuatro = new Color(menoscero);
                    int unocero = rgb(img, i + 1, j);
                    Color cinco = new Color(unocero);
                    int menosuno = rgb(img, i - 1, j + 1);
                    Color seis = new Color(menosuno);
                    int cerouno = rgb(img, i, j + 1);
                    Color siete = new Color(cerouno);
                    int ununo = rgb(img, i + 1, j + 1);
                    Color ocho = new Color(ununo);


                    if (esDiferent(color, un, threshold) && esDiferent(color, dos, threshold) && esDiferent(color, tres, threshold)) {
                        lista.add(position);

                    } else if (esDiferent(color, un, threshold) && esDiferent(color, dos, threshold) && esDiferent(color, cuatro, threshold)) {
                        lista.add(position);

                    } else if (esDiferent(color, tres, threshold) && esDiferent(color, dos, threshold) && esDiferent(color, cinco, threshold)) {
                        lista.add(position);

                    } else if (esDiferent(color, un, threshold) && esDiferent(color, cuatro, threshold) && esDiferent(color, seis, threshold)) {
                        lista.add(position);

                    } else if (esDiferent(color, seis, threshold) && esDiferent(color, cuatro, threshold) && esDiferent(color, siete, threshold)) {
                        lista.add(position);

                    } else if (esDiferent(color, seis, threshold) && esDiferent(color, siete, threshold) && esDiferent(color, ocho, threshold)) {
                        lista.add(position);

                    } else if (esDiferent(color, tres, threshold) && esDiferent(color, cinco, threshold) && esDiferent(color, ocho, threshold)) {
                        lista.add(position);

                    } else if (esDiferent(color, siete, threshold) && esDiferent(color, ocho, threshold) && esDiferent(color, cinco, threshold)) {
                        lista.add(position);

                    }
                }


            }

            BufferedImage contornoPuro = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
            for (int i = 0; i < contornoPuro.getWidth(); i++) {
                for (int y = 0; y < contornoPuro.getHeight(); y++) {
                    contornoPuro.setRGB(i, y, Color.white.getRGB());
                }
            }
            for (int[] posicion : lista) {
                img.setRGB(posicion[0], posicion[1], Color.red.getRGB());
                contornoPuro.setRGB(posicion[0], posicion[1], Color.black.getRGB());
            }
            File fileModified = new File("contornoPillao.jpg");
            ImageIO.write(img, "jpg", fileModified);
            File fileContorno = new File("contornoPuro.jpg");
            ImageIO.write(contornoPuro, "jpg", fileContorno);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean esDiferent(Color color, Color newColor, int threshold) {
        return Math.abs(color.getRed() - newColor.getRed()) > threshold || Math.abs(color.getBlue() - newColor.getBlue()) > threshold || Math.abs(color.getGreen() - newColor.getGreen()) > threshold;
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
            int pa = img.getRGB(x, y);
            Color color = new Color(pa);
            int[] colores = {color.getRed(), color.getGreen(), color.getBlue()};
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

    public File blureador(File file, int nivel) {
        File grey = mejorador(file);
        if (nivel == 1) {
            return file;
        } else


            try {
                BufferedImage img = ImageIO.read(grey);
                BufferedImage orignal = ImageIO.read(grey);
                List<Pixelmon> lista = new ArrayList<>();
                for (int i = 0; i < img.getWidth(); i++) {
                    for (int j = 0; j < img.getHeight(); j++) {
                        int r = 0;
                        int g = 0;
                        int b = 0;
                        int pixels = 0;
                        for (int k = 1 - nivel; k < nivel; k++) {
                            for (int l = -nivel; l < nivel; l++) {
                                int[] nuevo = red(img, i + k, j + l);
                                if (nuevo != null) {
                                    pixels++;
                                    r += nuevo[0];
                                    g += nuevo[1];
                                    b += nuevo[2];

                                }
                            }
                        }
                        int rMedia = r / pixels;
                        int gMedia = g / pixels;
                        int bMedia = b / pixels;

                        Color color = new Color(rMedia, gMedia, bMedia);
                        Pixelmon pixelmon = new Pixelmon(i, j, color);


                        lista.add(pixelmon);


                    }
                }
                for (Pixelmon pixelmon : lista) {

                    orignal.setRGB(pixelmon.getX(), pixelmon.getY(), pixelmon.getColor().getRGB());


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
        File grey = file;
        int contraste = 20;
        int threshold = getLuminosidad(file);

        try {
            BufferedImage img = ImageIO.read(grey);

            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color color = new Color(img.getRGB(i, j));
                    int red;
                    if (color.getRed() > threshold - 30) {
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

            double contator = 0;
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color color = new Color(img.getRGB(i, j));
                    contator += color.getRed();

                }
            }
            double media = contator / (img.getHeight() * img.getWidth());
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
            int luminosidad = getLuminosidad(file);

            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color color = new Color(img.getRGB(i, j));
                    if (color.getRed() > luminosidad + ((255 - luminosidad) / 3)) {
                        img.setRGB(i, j, Color.white.getRGB());
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

    public File limpiatore(File file) {
        File grey = file;


        try {
            BufferedImage img = ImageIO.read(grey);
            BufferedImage orignal = ImageIO.read(file);
            List<Pixelmon> lista = new ArrayList<>();
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    int reference = img.getRGB(i, j);
                    Color color = new Color(reference);
                    int r = 0;
                    int g = 0;
                    int b = 0;
                    int difPixels = 0;
                    for (int k = -2; k < 3; k++) {
                        for (int l = -2; l < 3; l++) {

                            int[] nuevo = red(img, i + k, j + l);

                            if (nuevo != null) {
                                Color cloro = new Color(nuevo[0], nuevo[1], nuevo[2]);
                                if (esDiferent(color, cloro, 5)) {
                                    difPixels++;
                                    r += nuevo[0];
                                    g += nuevo[1];
                                    b += nuevo[2];

                                }

                            }

                        }
                    }
                    if (difPixels > 5) {
                        int rMedia = r / difPixels;
                        int gMedia = g / difPixels;
                        int bMedia = b / difPixels;

                        Color colore = new Color(rMedia, gMedia, bMedia);
                        Pixelmon pixelmon = new Pixelmon(i, j, color);


                        lista.add(pixelmon);

                    }


                }
            }
            for (Pixelmon pixelmon : lista) {

                orignal.setRGB(pixelmon.getX(), pixelmon.getY(), pixelmon.getColor().getRGB());


            }
            File fileModified = new File("imagenLimpiada.jpg");
            ImageIO.write(orignal, "jpg", fileModified);
            return fileModified;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public File mejorador(File file) {
        File grey = file;


        try {
            BufferedImage img = ImageIO.read(grey);
            BufferedImage nuva = new BufferedImage(img.getWidth() * 3, img.getHeight() * 3, img.getType());

            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    Color color = new Color(img.getRGB(i, j));
                    for (int k = -1; k < 2; k++) {
                        for (int l = -1; l < 2; l++) {

                            if (comprobator(nuva, i * 3 + k, j * 3 + l))
                                nuva.setRGB(i * 3 + k, j * 3 + l, color.getRGB());


                        }
                    }

                }


            }
            File fileModified = new File("imagenMejorada.jpg");
            ImageIO.write(nuva, "jpg", fileModified);
            return fileModified;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public File pixelador(File file) {
        File grey = file;


        try {
            BufferedImage img = ImageIO.read(grey);
            BufferedImage nuva = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

            for (int i = 0; i < nuva.getWidth(); i++) {
                for (int j = 0; j < nuva.getHeight(); j++) {
                    Color color = new Color(img.getRGB(i, j));

                    for (int k = -4; k < 5; k++) {
                        for (int l = -4; l < 5; l++) {

                            if (comprobator(img, i + k, j + l)) {
                                nuva.setRGB(i + k, j + l, color.getRGB());

                            }
                        }

                    }


                }

            }

            File fileModified = new File("imagenpixelada.jpg");
            ImageIO.write(nuva, "jpg", fileModified);
            return fileModified;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void blender(File file1) {
        File grey = file;
        File file=file1;


        try {
            BufferedImage img = ImageIO.read(grey);
            BufferedImage orignal = ImageIO.read(file);
            int width;
            int heigth;
            if (img.getWidth()>=orignal.getWidth()) {
                 width = orignal.getWidth();

            }else
                width=img.getWidth();
            if (orignal.getHeight()<=img.getHeight()){
                heigth=orignal.getHeight();
            }else{
                heigth=img.getHeight();
            }


            for (int h=5;h<100;h+=5) {
                File file2=new File("blendeada"+h/5+".jpg");

                BufferedImage arpanta = new BufferedImage(width,heigth,img.getType());


                for (int i = 0; i < arpanta.getWidth(); i++) {
                    for (int j = 0; j < arpanta.getHeight(); j++) {
                        int reference = img.getRGB(i, j);
                        int newo=orignal.getRGB(i,j);
                        Color color = new Color(reference);
                        Color coloro=new Color(newo);
                        int r = color.getRed()*h/100;
                        int g = color.getGreen()*h/100;
                        int b = color.getBlue()*h/100;
                        int r1 = coloro.getRed()*(100-h)/100;
                        int g1 = coloro.getGreen()*(100-h)/100;
                        int b1 = coloro.getBlue()*(100-h)/100;
                        int rf = r+r1;
                        int gf = g+g1;
                        int bf = b+b1;
                        Color cloro=new Color(rf,gf,bf);
                        arpanta.setRGB(i,j,cloro.getRGB());


                    }


                }
                ImageIO.write(arpanta,"jpg",file2);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

