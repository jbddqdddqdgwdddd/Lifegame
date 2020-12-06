package com.victor.renderer;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;

import com.victor.Settings;
import com.victor.model.GameModel;
import com.victor.model.system.Creature;
import com.victor.renderer.ColorModel.BackgroundTheme;

public class BoardRenderer {

    private int BLOCK_SIZE = 3;

    private GameModel gameModel;
    private ColorModel colorModel;

    private CellRenderer cellRenderer;
    private HeadRenderer headRenderer;
    private TailRenderer tailRenderer;
    private GegrpRenderer gegrpRenderer;

    private boolean paintCellLayer;
    private boolean paintHeadLayer;
    private boolean paintTailLayer;
    private boolean paintGegrpLayer;
    private boolean outlineSeeds;

    private AffineTransform transform;

    Rectangle2D.Double bounds;
    Rectangle2D.Double visibleBounds;

    private double zoom = 1;

    public BoardRenderer(GameModel gameModel) {
        this.gameModel = gameModel;

        this.cellRenderer = new CellRenderer(gameModel, this);
        this.headRenderer = new HeadRenderer(gameModel, this);
        this.tailRenderer = new TailRenderer(gameModel, this);
        this.gegrpRenderer = new GegrpRenderer(gameModel, this);

        this.paintCellLayer = true;
        this.paintHeadLayer = true;
        this.paintTailLayer = true;
        this.paintGegrpLayer = true;
        this.outlineSeeds = false;

        this.transform = new AffineTransform();
    }

    public void paint(Graphics2D g) {
        updateColorModel();

        paintBackground(g);

        if (getPaintHeadLayer()) {
            paintLayer(g, getHeadRenderer());
        }
        if (getPaintTailLayer()) {
            paintLayer(g, getTailRenderer());
        }
        if (getPaintCellLayer()) {
            paintLayer(g, getCellRenderer());
        }
        if (getPaintGegrpLayer()) {
            paintLayer(g, getGegrpRenderer());
        }
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public ColorModel getColorModel() {
        return colorModel;
    }

    public CellRenderer getCellRenderer() {
        return cellRenderer;
    }

    public HeadRenderer getHeadRenderer() {
        return headRenderer;
    }

    public TailRenderer getTailRenderer() {
        return tailRenderer;
    }

    public GegrpRenderer getGegrpRenderer() {
        return gegrpRenderer;
    }

    public void setPaintCellLayer(boolean paint) {
        this.paintCellLayer = paint;
    }

    public boolean getPaintCellLayer() {
        return paintCellLayer;
    }

    public void setPaintHeadLayer(boolean paint) {
        this.paintHeadLayer = paint;
    }

    public boolean getPaintHeadLayer() {
        return paintHeadLayer;
    }

    public void setPaintTailLayer(boolean paint) {
        this.paintTailLayer = paint;
    }

    public boolean getPaintTailLayer() {
        return paintTailLayer;
    }

    public void setPaintGegrpLayer(boolean paint) {
        this.paintGegrpLayer = paint;
    }

    public boolean getPaintGegrpLayer() {
        return paintGegrpLayer;
    }

    public void setOutlineSeeds(boolean outlineSeeds) {
        this.outlineSeeds = outlineSeeds;
    }

    public boolean getOutlineSeeds() {
        return outlineSeeds;
    }

    public AffineTransform getTransform() {
        return transform;
    }

    public void setTransform(AffineTransform transform) {
        this.transform = transform;
    }

    public int getBlockSize() {
        return BLOCK_SIZE;
    }

    public void setBlockSize(int blockSize) {
        if (bounds!=null) {
            bounds.width=(bounds.width-40)*blockSize/this.BLOCK_SIZE+40;
            bounds.height=(bounds.height-40)*blockSize/this.BLOCK_SIZE+40;
        }
        this.BLOCK_SIZE = blockSize;
    }

    public void updateVisibleRenderers(Rectangle2D.Double visibleBounds) {
        this.visibleBounds = visibleBounds;
    }

    public void setBounds(Dimension2D d) {
        this.bounds = new Rectangle2D.Double(-20,-20,d.getWidth(),d.getHeight());
    }

    public Rectangle2D.Double getRendererBounds() {
        return bounds;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    private void paintBackground(Graphics g) {
        g.setColor(getColorModel().getBackgroundColor());
        // 保存图像时要避免超出范围，以免出现边缘瑕疵
        int x = (int) getRendererBounds().x-1;
        int y = (int) getRendererBounds().y-1;
        int w = (int) getRendererBounds().width+1;
        int h = (int) getRendererBounds().height+1;
        g.fillRect(x, y, w, h);
    }

    private void paintLayer(Graphics2D g, CreatureRenderer renderer) {
        Collection<Creature> orgs = getGameModel().getSystem().getCreatures();
        for (Creature o : orgs) {
            renderer.render(g, o);
        }
    }

    private void updateColorModel() {
        String colorModelName = getGameModel().getSetting().getString(Settings.COLOR_MODEL);
        if (colorModelName.equals("AngleColorModel")) {
            if (this.colorModel == null || !(this.colorModel instanceof AngleColorModel)) {
                this.colorModel = new AngleColorModel();
            }
            colorModel.setAttribute("primaryHue", gameModel.getSetting().getInt(Settings.PRIMARY_HUE_DEGREES));
            colorModel.setAttribute("hueRange", gameModel.getSetting().getInt(Settings.HUE_RANGE));
        }
        if (colorModelName.equals("SplitColorModel")) {
            if (this.colorModel == null || !(this.colorModel instanceof SplitColorModel)) {
                this.colorModel = new SplitColorModel();
            }
        }
        if (getGameModel().getSetting().getString(Settings.BACKGROUND_THEME).equals("black")) {
            this.colorModel.setBackgroundTheme(BackgroundTheme.black);
        }
        else {
            this.colorModel.setBackgroundTheme(BackgroundTheme.white);
        }
    }
}