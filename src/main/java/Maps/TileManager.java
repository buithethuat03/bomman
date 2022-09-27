package Maps;

import Panel.PanelGame;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class TileManager {
    PanelGame gp;
    public List<Grass> grasses = new ArrayList<>();
    public List<Wall> walls = new ArrayList<>();

    public List<Brick> bricks = new ArrayList<>();
    int[][] mapTileNum;

    public TileManager(PanelGame gp) {
        this.gp = gp;
        mapTileNum = new int [gp.maxScreenRow][ gp.maxScreenCol];
        loadMap();
    }

    void loadMap() {
        try {
            File fileReader = new File("src/main/resources/data/map1.txt");
            Scanner scanner = new Scanner(fileReader);
            for (int i = 0;i < gp.maxScreenRow; i++) {
                for(int j = 0; j < gp.maxScreenCol; j++) {
                    mapTileNum[i][j] = scanner.nextInt();
                    Grass grass = new Grass(gp);
                    grass.setLocation(j * gp.tileSize, i * gp.tileSize);
                    grasses.add(grass);
                    if (mapTileNum[i][j] == 1) {
                        Wall wall = new Wall(gp);
                        wall.setLocation(j * gp.tileSize, i * gp.tileSize);
                        walls.add(wall);
                    }

                    if (mapTileNum[i][j] == 2) {
                        Brick brick = new Brick(gp);
                        brick.setLocation(j * gp.tileSize, i * gp.tileSize);
                        bricks.add(brick);
                    }
                }
            }
            scanner.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void drawGrass(Graphics2D g2) {
        for (Grass obj : grasses) {
            obj.draw(g2);
        }
    }
    public void drawWall(Graphics2D g2) {
        for(Wall obj : walls) {
            obj.draw(g2);
        }
    }

    public void drawBrick(Graphics2D g2) {
        for(Brick obj : bricks) {
            obj.draw(g2);
        }
    }

    public void updateBrick() {
        int idx = 0;
        while(idx < bricks.size()) {
            if (bricks.get(idx).status.equals("broken")) {
                bricks.remove(idx);
            } else {
                idx++;
            }
        }
    }
}
