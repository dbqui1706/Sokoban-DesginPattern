package view.ui;

import controller.ISokobanController;
import model.Direction;
import model.SokobanModel;
import model.entity.Box;
import model.entity.Target;
import model.entity.Wall;
import model.observer.ModelObserver;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import java.io.File;

public class MapPanel extends JPanel implements ModelObserver {
    private SokobanModel model;
    private ISokobanController controller;
    private Image wall_image = new ImageIcon("resource/image/game/wall.png").getImage();
    private Image target_image = new ImageIcon("resource/image/game/target.png").getImage();
    private Image box_image = new ImageIcon("resource/image/game/box.png").getImage();
    private Image box_targeted_image = new ImageIcon("resource/image/game/box_targeted.png").getImage();
    private Image player_image = new ImageIcon("resource/image/game/character.png").getImage();

    public MapPanel(SokobanModel model, ISokobanController controller){
        this.model = model;
        this.controller = controller;
        model.addModelObserver(this);
        repaint();
    }

    @Override
    public void update() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int tile_width_x = getWidth() / model.getColumns();
        int tile_width_y = getHeight() / model.getRows();
        int tile_width = Math.min(tile_width_x, tile_width_y);
        int dWidth = (getWidth() - (tile_width * model.getColumns())) / 2;
        int dHeight = (getHeight() - (tile_width * model.getRows())) / 2;

        for (Wall wall : model.getWalls()) {
            g.drawImage(wall_image,
                    wall.getCol() * tile_width + dWidth,
                    wall.getRow() * tile_width + dHeight,
                    tile_width,
                    tile_width,
                    null);
        }

        for (Target target : model.getTargets()) {
            g.drawImage(target_image,
                    target.getCol() * tile_width + dWidth,
                    target.getRow() * tile_width + dHeight,
                    tile_width,
                    tile_width,
                    null);
        }

        for (Box box : model.getBoxes()) {
            if (box.isOnTarget()) {
                g.drawImage(box_targeted_image,
                        box.getCol() * tile_width + dWidth,
                        box.getRow() * tile_width + dHeight,
                        tile_width,
                        tile_width,
                        null);
            } else {
                g.drawImage(box_image,
                        box.getCol() * tile_width + dWidth,
                        box.getRow() * tile_width + dHeight,
                        tile_width,
                        tile_width,
                        null);
            }
        }

        g.drawImage(player_image,
                model.getPlayer().getCol() * tile_width + dWidth,
                model.getPlayer().getRow() * tile_width + dHeight,
                tile_width,
                tile_width,
                null);
    }
}
