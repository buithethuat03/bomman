package Maps;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class object {
    public int x, y;
    //public int speed;

    public BufferedImage normal;

    //public int spriteCounter = 0;
    //public int spriteNum = 1;

    public abstract void draw(Graphics2D g2);
}
