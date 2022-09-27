package Maps;

import Panel.PanelGame;
import entity.Bomb;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Brick extends object {

    PanelGame panel;
    BufferedImage normal, explode1, explode2, explode3;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public String status;

    public Brick(PanelGame gp) {
        this.panel = gp;
        setupNewFragileWall();
        getImage();
    }
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void getImage() {
        try {
            normal = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("brick/brick.png")));
            explode1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("brick/brick_exploded.png")));
            explode2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("brick/brick_exploded1.png")));
            explode3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("brick/brick_exploded2.png")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateWaitBreaking(Bomb bomb) {
        if (bomb.status.equals("exploding") && status.equals("normal")) {
            if (Math.abs(bomb.y - y) == panel.tileSize && bomb.x == x ||
            Math.abs(bomb.x - x) == panel.tileSize && bomb.y == y ||
            bomb.x == x && bomb.y == y) {
                status = "breaking";
            }
        }
    }

    public void updateBreaking() {
        if (status.equals("breaking")) {
            spriteCounter++;
            if (spriteCounter > 30) {
                spriteNum++;
                if (spriteNum > 3) {
                    status = "broken";
                }
                spriteCounter = 0;
            }
        }
    }
    void setupNewFragileWall() {
        x = 0;
        y = 0;
        status = "normal";
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch(status) {
            case "normal" -> {
                image = normal;
            }
            case "breaking" -> {
                if (spriteNum == 1) image = explode1;
                if (spriteNum == 2) image = explode2;
                if (spriteNum == 3) image = explode3;
            }
        }
        if (image != null) {
            g2.drawImage(image, x, y, panel.tileSize, panel.tileSize, null);
        }
    }
}
