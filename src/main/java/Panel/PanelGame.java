package Panel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Maps.*;
import entity.*;

public class PanelGame extends JPanel implements Runnable {
    final int originalTileSize = 12;
    final int scale = 3;

    final int fps = 60;
    final long draw_time = 1000000000 / fps;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    final int screen_width = maxScreenCol * tileSize;
    final int screen_height = maxScreenRow * tileSize;

    Key key = new Key();
    Thread game_thread;
    Player player;

    List<Balloom> ballooms = new ArrayList<>();
    //Balloom []balloomList = new Balloom[4];
    Bomb bomb;

    TileManager tle = new TileManager(this);

    void initEntityAndObject() {
        player = new Player(this, key);

//        for (int i = 0; i < 4; i++) {
//            balloomList[i] = new Balloom(this);
//        }
        int[][] mapTileNum = new int [maxScreenRow][maxScreenCol];
        try {
            File fileReader = new File("src/main/resources/data/map1.txt");
            Scanner scanner = new Scanner(fileReader);
            for (int i = 0;i < maxScreenRow; i++) {
                for(int j = 0; j < maxScreenCol; j++) {
                    mapTileNum[i][j] = scanner.nextInt();
                    if (mapTileNum[i][j] == 3) {
                        Balloom balloom = new Balloom(this);
                        balloom.setLocationAndDirection(j * tileSize, i * tileSize, "up");
                        ballooms.add(balloom);
                    }

                    if (mapTileNum[i][j] == 4) {
                        Balloom balloom = new Balloom(this);
                        balloom.setLocationAndDirection(j * tileSize, i * tileSize, "right");
                        ballooms.add(balloom);
                    }
                }
            }
            scanner.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    void setupEntityAndObject() {
        try {
            player.setLocation(72, 72);
//            balloomList[0].setLocationAndDirection(36, 0, "right");
//            balloomList[1].setLocationAndDirection(108, 36, "right");
//            balloomList[2].setLocationAndDirection(36, 36, "down");
//            balloomList[3].setLocationAndDirection(144, 36, "down");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public PanelGame() {
        this.setPreferredSize(new Dimension(screen_width, screen_height));
        this.setBackground(Color.DARK_GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(key);
        this.setFocusable(true);
    }

    public void startGameThread() {
        game_thread = new Thread(this);
        game_thread.start();
    }

    @Override
    public void run() {
        initEntityAndObject();
        setupEntityAndObject();
        while (game_thread != null) {
            long start = System.nanoTime();

            //Update object information
            update();

            //Render
            repaint();

            try {
                long used_time = System.nanoTime() - start;
                if (used_time < draw_time) {
                    Thread.sleep((draw_time - used_time) / 1000000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {

        String message = player.update(tle.walls, bomb, tle.bricks);
        if (message.equals("Put a bomb!") && bomb == null) {
            bomb = new Bomb((player.x + tileSize / 2) / tileSize * tileSize, (player.y + tileSize / 2) / tileSize * tileSize, this);
        }
        if (bomb != null) {
            for(Brick brick : tle.bricks) {
                brick.updateWaitBreaking(bomb);
            }
            bomb.update();
            if (bomb.status.equals("exploded")) {
                bomb = null;
            }
        }
        for(Brick brick : tle.bricks) {
            brick.updateBreaking();
        }

        tle.updateBrick();

        if (player.status.equals("dead")) {
            //game_thread = null;
            System.exit(0);
        }

//        for(int i = 0; i < 4; i++) {
//            if (balloomList[i] != null) {
//                balloomList[i].update(tle.walls, bomb, tle.bricks);
//                player.check_crush_balloom(balloomList[i]);
//                if (balloomList[i].spriteCounter > 100) {
//                    balloomList[i] = null;
//                }
//            }
//        }

        int idx = 0;
        while (idx < ballooms.size()) {
            if (ballooms.get(idx) != null) {
                ballooms.get(idx).update(tle.walls, bomb, tle.bricks);
                player.check_crush_balloom(ballooms.get(idx));
                if (ballooms.get(idx).spriteCounter > 100) {
                    ballooms.remove(idx);
                } else {
                    idx++;
                }
            }
        }

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        tle.drawGrass(g2);
//        for(int i = 0; i < 4; i++) {
//            if (balloomList[i] != null) {
//                balloomList[i].draw(g2);
//            }
//        }
        for (Balloom balloom : ballooms) {
            if (balloom != null) {
                balloom.draw(g2);
            }
        }
        if (bomb != null) {
            bomb.draw(g2);
        }
        tle.drawWall(g2);
        tle.drawBrick(g2);

        if (player != null) {
            player.draw(g2);
        }


        g2.dispose();
    }
}