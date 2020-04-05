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

    // Marble count
    g2.setFont(MancalaGameView.FONT);
    FontMetrics metrics = g2.getFontMetrics();
    int numMarbles =
        model.getMarbles(isA ? MancalaGameModel.A_MANCALA_POS : MancalaGameModel.B_MANCALA_POS);
    String marbleStr = String.valueOf(numMarbles);
    float x = (getWidth() - metrics.stringWidth(marbleStr)) / 2;
    float y =
        isA
            ? mancalaY + mancalaHeight - MancalaGameView.PADDING
            : mancalaY + metrics.getAscent() + MancalaGameView.PADDING;
    g2.drawString(marbleStr, x, y);

    // Draw randomly positioned marbles

    float marbleSize = 0.2f * getWidth();
    for (int i = 0; i < numMarbles; i++) {
      Ellipse2D.Double marble;

      // Keep randomly placing marble until it is completely inside the pit
      do {
        x = mancalaX + random.nextFloat() * (mancalaWidth - marbleSize);
        y =
            mancalaY
                + (isA ? 0 : 0.2f * mancalaHeight)
                + random.nextFloat() * (0.8f * mancalaHeight - marbleSize);
        marble = new Ellipse2D.Double(x, y, marbleSize, marbleSize);
      } while (!mancala.contains(marble.x, marble.y, marble.width, marble.height));

      g2.setColor(Color.GRAY);
      g2.fill(marble);
      g2.setColor(Color.BLACK);
      g2.draw(marble);
    }
  }
}
