package entity;

import Maps.Brick;
import Maps.Wall;
import Panel.PanelGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Balloom extends Entity {
    PanelGame gp;
    BufferedImage dead;

    public String status;
    public Balloom(PanelGame gp) {
        this.gp = gp;
        setDefaultValues();
        getBalloomImage();
    }

    public void setLocationAndDirection(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    void setDefaultValues() {
        x = 36;
        y = 36;
        speed = 1;
        direction = "right";
        status = "live";
    }

    void getBalloomImage() {
        try {
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("balloom/balloom_left1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("balloom/balloom_left2.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("balloom/balloom_left3.png")));

            right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("balloom/balloom_right1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("balloom/balloom_right2.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("balloom/balloom_right3.png")));

            dead = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("balloom/balloom_dead.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String update(List<Wall> walls, Bomb bomb, List<Brick> bricks) {
        if (bomb != null) {
            if (bomb.status.equals("exploding")) {
                if (Math.abs(bomb.x - x) < gp.tileSize && Math.abs(bomb.y - y) < 2 * gp.tileSize
                || Math.abs(bomb.y - y) < gp.tileSize && Math.abs(bomb.x - x) < 2 * gp.tileSize) {
                    status = "dead";
                }
            }
        }
        switch (direction) {
            case "up" -> {
                spriteCounter++;
                boolean can_up = true;
                for (Wall wall : walls) {
                    if (wall.x - x <= gp.tileSize - speed && wall.x - x >= -gp.tileSize + 4 && y - wall.y < gp.tileSize + speed && y - wall.y >= 0) {
                        can_up = false;
                        direction = "down";
                        break;
                    }
                }

                for (Brick brick : bricks) {
                    if (brick.x - x <= gp.tileSize - speed && brick.x - x >= -gp.tileSize + 4 && y - brick.y < gp.tileSize + speed && y - brick.y >= 0) {
                        can_up = false;
                        direction = "down";
                        break;
                    }
                }

                if (can_up && y >= speed && status.equals("live")) {
                    y -= speed;
                } else {
                    direction = "down";
                }
            }
            case "down" -> {
                spriteCounter++;
                boolean can_down = true;
                for (Wall wall : walls) {
                    if (wall.x - x <= gp.tileSize - speed && wall.x - x >= -gp.tileSize + 4 && wall.y - y < gp.tileSize + speed && wall.y - y >= 0) {
                        can_down = false;
                        direction = "up";
                        break;
                    }
                }

                for (Brick brick : bricks) {
                    if (brick.x - x <= gp.tileSize - speed && brick.x - x >= -gp.tileSize + speed && brick.y - y < gp.tileSize + speed && brick.y - y >= 0) {
                        can_down = false;
                        direction = "up";
                        break;
                    }
                }

                if (can_down && y < 396 && status.equals("live")) {
                    y += speed;
                } else {
                    direction = "up";
                }
            }
            case "left" -> {
                spriteCounter++;
                boolean can_left = true;
                for (Wall wall : walls) {
                    if (wall.y - y <= gp.tileSize - speed && wall.y - y >= -gp.tileSize + speed && x - wall.x < gp.tileSize + speed && x - wall.x >= 0) {
                        can_left = false;
                        direction = "right";
                        break;
                    }
                }

                for (Brick brick : bricks) {
                    if (brick.y - y <= gp.tileSize - speed && brick.y - y >= -gp.tileSize + speed && x - brick.x < gp.tileSize + speed && x - brick.x >= 0) {
                        can_left = false;
                        direction = "right";
                        break;
                    }
                }

                if (can_left && x >= speed && status.equals("live")) {
                    x -= speed;
                } else {
                    direction = "right";
                }
            }
            case "right" -> {
                spriteCounter++;
                boolean can_right = true;
                for (Wall wall : walls) {
                    if (wall.y - y <= gp.tileSize - speed && wall.y - y >= -gp.tileSize + speed && wall.x - x < gp.tileSize + speed && wall.x - x >= 0) {
                        can_right = false;
                        direction = "left";
                        break;
                    }
                }

                for (Brick brick : bricks) {
                    if (brick.y - y <= gp.tileSize - speed && brick.y - y >= -gp.tileSize + speed && brick.x - x < gp.tileSize + speed && brick.x - x >= 0) {
                        can_right = false;
                        direction = "left";
                        break;
                    }
                }

                if (x < 684 && can_right && status.equals("live")) {
                    x += speed;
                } else {
                    direction = "left";
                }
            }
        }
        if (spriteCounter > 6 && status.equals("live")) {
            if (spriteNum < 3) {
                spriteNum++;
            } else if (spriteNum == 3){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (status.equals("dead")) {
            spriteCounter++;
        }
        return "None";
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        if (status.equals("live")) {
            switch (direction) {
                case "left", "up" -> {
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                    if (spriteNum == 3) image = left3;
                }
                case "right", "down" -> {
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                    if (spriteNum == 3) image = right3;
                }
            }
        }
        else {
            image = dead;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
