package entity;

import Maps.Brick;
import Maps.Wall;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class Entity {
    public int x, y;
    public int speed;

    public String status;

    public BufferedImage up1, up2, up3, left1, left2, left3, down1, down2, down3, right1, right2, right3;
    public String direction;

    public int spriteCounter = 0;

    public int spriteNum = 1;

    public abstract String update(List<Wall> walls, Bomb bomb, List<Brick> bricks);

    public abstract void draw(Graphics2D g2);
}
