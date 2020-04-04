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

  public MancalaPanel(MancalaGameModel model, boolean isA) {
    this.model = model;
    this.isA = isA;
    random = new Random();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    // Actual mancala
    float mancalaWidth = 0.8f * getWidth();
    float mancalaHeight = 0.8f * getHeight();
    float mancalaX = (getWidth() - mancalaWidth) / 2;
    float mancalaY = (getHeight() - mancalaHeight) / 2;
    RoundRectangle2D.Double mancala =
        new RoundRectangle2D.Double(
            mancalaX,
            mancalaY,
            mancalaWidth,
            mancalaHeight,
            mancalaWidth,
            (1f / 6f) * mancalaHeight);
    g2.setStroke(MancalaGameView.STROKE);
    g2.draw(mancala);

    // Draw randomly positioned marbles
    int numMarbles =
        model.getMarbles(isA ? MancalaGameModel.A_MANCALA_POS : MancalaGameModel.B_MANCALA_POS);
    float marbleSize = 0.2f * getWidth();
    float padding = 1.5f * marbleSize;
    for (int i = 0; i < numMarbles; i++) {
      float minX = mancalaX + padding;
      float maxX = mancalaX + mancalaWidth - padding;
      float x = mancalaX + random.nextFloat() * (maxX - minX);
      float minY = mancalaY + padding;
      float maxY = mancalaY + mancalaHeight - padding;
      float y = mancalaY + random.nextFloat() * (maxY - minY);
      g2.fill(new Ellipse2D.Double(x, y, marbleSize, marbleSize));
    }
  }
}
