import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ContornoDetector {
    File file;

    public ContornoDetector(File file) {
        if (file.exists()) {
            this.file = file;

        }
    }

    public void detectarContorno() {
        File grey = greyConverterBuffered();

        try {
            BufferedImage img = ImageIO.read(grey);
            for (int i = 0; i < img.getWidth(); i++) {
                for (int j = 0; j < img.getHeight(); j++) {
                    int reference = img.getRGB(i, j);
                    Color color = new Color(reference);
                    int counterStrikeGlobal = 0;
                    for (int k = -1; k < 2; k++) {
                        for (int l = -1; l < 2; l++) {
                            int nuevo = rgb(img, i + k, j + l);
                            if (nuevo != -1) {
                                Color newColor = new Color(nuevo);
                                if (Math.abs(color.getRed() - newColor.getRed()) > 30) {
                                    counterStrikeGlobal++;
                                    if (counterStrikeGlobal >= 5) {
                                        img.setRGB(i, j, Color.red.getRGB());
                                    }
                                }
                            }
                        }
                    }


                }
            }
            File fileModified = new File("contornoPillao.jpg");
            ImageIO.write(img, "jpg", fileModified);
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

            ImageIO.write(img, "jpg", file);
            return file;
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


}
