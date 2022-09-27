package entity;


import Maps.Brick;
import Maps.Wall;
import Panel.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Player extends Entity {
    PanelGame gp;
    Key key;

    public Player(PanelGame gp, Key key) {
        this.gp = gp;
        this.key = key;
        setDefaultValues();
        getPlayerImage();
        direction = "down";
        status = "live";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/up_2.png")));
            up3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/up_3.png")));

            left1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/left_2.png")));
            left3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/left_3.png")));

            down1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/down_2.png")));
            down3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/down_3.png")));

            right1 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/right_2.png")));
            right3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/right_3.png")));


        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultValues() {
        x = 36;
        y = 36;
        speed = 3;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void check_crush_balloom(Balloom balloom) {
        if (Math.abs(balloom.x - x) < gp.tileSize - 2 * speed && Math.abs(balloom.y - y) < gp.tileSize - 2 * speed) {
            status = "dead";
        }
    }

    @Override
    public String update(List<Wall> walls, Bomb bomb, List<Brick> bricks) {
        if (bomb != null) {
            if (bomb.status.equals("exploding")) {
                if (Math.abs(bomb.x - x) < gp.tileSize - speed && Math.abs(bomb.y - y) < 2 * gp.tileSize - speed
                        || Math.abs(bomb.y - y) < gp.tileSize - speed && Math.abs(bomb.x - x) < 2 * gp.tileSize - speed) {
                    status = "dead";
                }
            }
        }

        if (key.up) {
            direction = "up";
            spriteCounter++;
            boolean can_up = true;
            for (Wall wall : walls) {
                if (wall.x - x <= gp.tileSize - 2 * speed && wall.x - x >= -gp.tileSize + 2 * speed && y - wall.y < gp.tileSize + 2 * speed && y - wall.y >= 0) {
                    can_up = false;
                    break;
                }
            }

            for (Brick brick : bricks) {
                if (brick.x - x <= gp.tileSize - 2 * speed && brick.x - x >= -gp.tileSize + 2 * speed && y - brick.y < gp.tileSize + 2 * speed && y - brick.y >= 0) {
                    can_up = false;
                    break;
                }
            }

            if (can_up && y >= speed) {
                y -= speed;
            }
        } else if (key.down) {
            direction = "down";
            spriteCounter++;
            boolean can_down = true;
            for (Wall wall : walls) {
                if (wall.x - x <= gp.tileSize - 2 * speed && wall.x - x >= -gp.tileSize + 2 * speed && wall.y - y < gp.tileSize + 2 * speed && wall.y - y >= 0) {
                    can_down = false;
                    break;
                }
            }

            for (Brick brick : bricks) {
                if (brick.x - x <= gp.tileSize - 2 * speed && brick.x - x >= -gp.tileSize + 2 * speed && brick.y - y < gp.tileSize + 2 * speed && brick.y - y >= 0) {
                    can_down = false;
                    break;
                }
            }

            if (can_down && y < (gp.tileSize * (gp.maxScreenRow) - 1 )) {
                y +=speed;
            }
        } else if (key.left) {
            direction = "left";
            spriteCounter++;
            boolean can_left = true;
            for (Wall wall : walls) {
                if (wall.y - y <= gp.tileSize - 2 * speed && wall.y - y >= -gp.tileSize + 2 * speed && x - wall.x < gp.tileSize + 2 * speed && x - wall.x >= 0) {
                    can_left = false;
                    break;
                }
            }

            for (Brick brick : bricks) {
                if (brick.y - y <= gp.tileSize - 2 * speed && brick.y - y >= -gp.tileSize + 2 * speed && x - brick.x < gp.tileSize + 2 * speed && x - brick.x >= 0) {
                    can_left = false;
                    break;
                }
            }
            if (can_left && x >= speed) {
               x -= speed;
            }
        } else if (key.right) {
            direction = "right";
            spriteCounter++;
            boolean can_right = true;
            for (Wall wall : walls) {
                if (wall.y - y <= gp.tileSize - 2 * speed && wall.y - y >= -gp.tileSize + 2 * speed && wall.x - x < gp.tileSize + 2 * speed && wall.x - x >= 0) {
                    can_right = false;
                    break;
                }
            }

            for (Brick brick : bricks) {
                if (brick.y - y <= gp.tileSize - 2 * speed && brick.y - y >= -gp.tileSize + 2 * speed && brick.x - x < gp.tileSize + 2 * speed && brick.x - x >= 0) {
                    can_right = false;
                    break;
                }
            }

            if (x < gp.tileSize * (gp.maxScreenCol) - 1 && can_right) {
                x += speed;
            }
        } else if (key.space) {
            //System.out.println("Player put a bomb!");
//            if (gp.bombs.size() < 1) {
//                System.out.println("Player put a bomb!");
//                Bomb bomb = new Bomb(x, y, gp);
//                gp.bombs.add(bomb);
//                gp.bombCount++;
//            }
            return "Put a bomb!";
        }

        if (spriteCounter > 6) {
            if (spriteNum < 3) {
                spriteNum++;
            } else if (spriteNum == 3){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        return "None";
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up" -> {
                if (spriteNum == 1) image = up1;
                if (spriteNum == 2) image = up2;
                if (spriteNum == 3) image = up3;
                break;
            }
            case "down" -> {
                if (spriteNum == 1) image = down1;
                if (spriteNum == 2) image = down2;
                if (spriteNum == 3) image = down3;
                break;
            }
            case "left" -> {
                if (spriteNum == 1) image = left1;
                if (spriteNum == 2) image = left2;
                if (spriteNum == 3) image = left3;
                break;
            }
            case "right" -> {
                if (spriteNum == 1) image = right1;
                if (spriteNum == 2) image = right2;
                if (spriteNum == 3) image = right3;
                break;
            }
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}