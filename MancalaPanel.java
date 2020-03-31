package ram;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

public class MancalaPanel extends JPanel {
  private MancalaGameModel model;
  private boolean isA;

  private Random random;
  private RoundRectangle2D.Double mancala;

  private static final int PANEL_WIDTH = 100;
  private static final int MANCALA_WIDTH = (int) (.8f * PANEL_WIDTH);
  private static final int MANCALA_HEIGHT = (int) (.8f * MancalaGameView.PANEL_HEIGHT);
  private static final int MANCALA_X = (PANEL_WIDTH - MANCALA_WIDTH) / 2;
  private static final int MANCALA_Y = (MancalaGameView.PANEL_HEIGHT - MANCALA_HEIGHT) / 2;
  private static final int MANCALA_ARC_WIDTH = PANEL_WIDTH;
  private static final int MANCALA_ARC_HEIGHT = MancalaGameView.PANEL_HEIGHT / 6;

  public MancalaPanel(MancalaGameModel model, boolean isA) {
    this.model = model;
    this.isA = isA;

    Dimension size = new Dimension(PANEL_WIDTH, MancalaGameView.PANEL_HEIGHT);
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);
    setSize(size);

    random = new Random();
    mancala =
        new RoundRectangle2D.Double(
            MANCALA_X,
            MANCALA_Y,
            MANCALA_WIDTH,
            MANCALA_HEIGHT,
            MANCALA_ARC_WIDTH,
            MANCALA_ARC_HEIGHT);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    // Actual mancala
    g2.setStroke(MancalaGameView.STROKE);
    g2.draw(mancala);

    // Draw randomly positioned marbles
    int numMarbles =
        model.getMarbles(isA ? MancalaGameModel.A_MANCALA_POS : MancalaGameModel.B_MANCALA_POS);
    for (int i = 0; i < numMarbles; i++) {
      int minX = MANCALA_X + MancalaGameView.MARBLE_WIDTH;
      int maxX = MANCALA_X + MANCALA_WIDTH - MancalaGameView.MARBLE_WIDTH;
      int x = MANCALA_X + random.nextInt(maxX - minX + 1);
      int minY = MANCALA_Y + MancalaGameView.MARBLE_WIDTH;
      int maxY = MANCALA_Y + MANCALA_HEIGHT - MancalaGameView.MARBLE_WIDTH;
      int y = MANCALA_Y + random.nextInt(maxY - minY + 1);
      g2.fill(
          new Ellipse2D.Double(x, y, MancalaGameView.MARBLE_WIDTH, MancalaGameView.MARBLE_WIDTH));
    }
  }
}
