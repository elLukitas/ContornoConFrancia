import java.awt.*;

public class Pixelmon {
    private int x;
    private int y;
   private Color color;
    public Pixelmon(int x,int y,Color color){
        this.x=x;
        this.y=y;
        this.color=color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
}
