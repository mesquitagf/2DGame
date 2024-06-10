package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    GamePanel gamePanel;
    Tile[] tile;
    int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new Tile[10];
        mapTileNum = new int[gamePanel.maxScreenCols][gamePanel.maxScreenRows];
        getTileImage();
        loadMap("example.txt");
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/earth01.png")));

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapName) {
        try {
            InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream("/maps/" + mapName));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0, row = 0;
            while (col < gamePanel.maxScreenCols && row < gamePanel.maxScreenRows) {
                String line = reader.readLine();
                while (col < gamePanel.maxScreenCols) {
                    String num[] = line.split(" ");
                    int numInt = Integer.parseInt(num[col]);
                    mapTileNum[col][row] = numInt;
                    col++;
                }
                if (col == gamePanel.maxScreenCols) {
                    col = 0;
                    row++;
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d) {
        int col = 0, row = 0, x = 0, y = 0;

        while (col < gamePanel.maxScreenCols && row < gamePanel.maxScreenRows) {
            int tileNum = mapTileNum[col][row];

            g2d.drawImage(tile[tileNum].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            col++;
            x += gamePanel.tileSize;

            if (col == gamePanel.maxScreenCols) {
                col = 0;
                x = 0;
                row++;
                y += gamePanel.tileSize;
            }
        }
    }
}
