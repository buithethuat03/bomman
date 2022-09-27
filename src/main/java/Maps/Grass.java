package Maps;

import Panel.PanelGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Grass extends object{
    PanelGame panel;

    public Grass(PanelGame panel) {
        this.panel = panel;
        setDefaultLocation();
        getImage();
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
    void setDefaultLocation() {
        this.x = 0;
        this.y = 0;
    }

    void getImage() {
        try {
            normal = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("tiles/grass.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = normal;
        g2.drawImage(image, x, y, panel.tileSize, panel.tileSize, null);
    }
}
