package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import  Panel.PanelGame;

public class Bomb {
    public int x, y;
    PanelGame gp;
    int spriteCounter = 0;
    int spriteNum = 1;
    BufferedImage bomb1, bomb2, bomb3, bomb_exploded1, bomb_exploded2, bomb_exploded3;
    BufferedImage w1, w2, w3, a1, a2, a3, s1, s2, s3, d1, d2, d3;

    long time_init, time_now;

    public String status;

    public Bomb(int x, int y, PanelGame gp) {
        this.x = x;
        this.y = y;
        this.gp = gp;
        getBoomImages();
        time_init = System.nanoTime();
        status = "prepare_to_explode";
    }

    void getBoomImages() {
        try {
            bomb1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/bomb.png")));
            bomb2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/bomb_1.png")));
            bomb3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/bomb_2.png")));

            bomb_exploded1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/bomb_exploded2.png")));
            bomb_exploded2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/bomb_exploded1.png")));
            bomb_exploded3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/bomb_exploded.png")));

            w1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/w1.png")));
            w2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/w2.png")));
            w3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/w3.png")));

            a1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/a1.png")));
            a2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/a2.png")));
            a3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/a3.png")));

            s1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/s1.png")));
            s2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/s2.png")));
            s3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/s3.png")));

            d1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/d1.png")));
            d2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/d2.png")));
            d3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("bomb/d3.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void update() {
        time_now = System.nanoTime();
        spriteCounter++;
        if ((time_now - time_init) / 1000000000 >= 2) {
            if (status.equals("prepare_to_explode")) {
                status = "exploding";
            }
        }
        if ((time_now - time_init) / 1000000000 >= 4) {
            if (status.equals("exploding")) {
                status = "exploded";
            }
        }
        if (spriteCounter > 40) {
            if (spriteNum < 3) {
                spriteNum++;
            } else if (spriteNum == 3){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null, w = null, a = null, s = null, d = null;

        switch (status) {
            case "prepare_to_explode" -> {
                if (spriteNum == 1) image = bomb1;
                if (spriteNum == 2) image = bomb2;
                if (spriteNum == 3) image = bomb3;
            }
            case "exploding" -> {
                if (spriteNum == 1) {
                    image = bomb_exploded1;
                    w = w1;
                    a = a1;
                    s = s1;
                    d = d1;
                }
                if (spriteNum == 2) {
                    image = bomb_exploded2;
                    w = w2;
                    a = a2;
                    s = s2;
                    d = d2;
                }
                if (spriteNum == 3) {
                    image = bomb_exploded3;
                    w = w3;
                    a = a3;
                    s = s3;
                    d = d3;
                }
            }
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        if (w != null) {
            g2.drawImage(w, x, y - 36, gp.tileSize, gp.tileSize, null);
        }
        if (a != null) {
            g2.drawImage(a, x - 36, y, gp.tileSize, gp.tileSize, null);
        }
        if (s != null) {
            g2.drawImage(s, x, y + 36, gp.tileSize, gp.tileSize, null);
        }
        if (d != null) {
            g2.drawImage(d, x + 36, y, gp.tileSize, gp.tileSize, null);
        }
    }
}
