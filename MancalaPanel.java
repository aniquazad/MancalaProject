package ram;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Random;

import static ram.MenuView.FONT_18;
import static ram.MenuView.PADDING;

/**
 * This class creates the Mancala board and the stones in each pit.
 * @author aniqu
 *
 */
public class MancalaPanel extends JPanel {
  private MancalaModel model;
  private boolean isA;
  private MancalaStyle style;

  private Random random;
  private ArrayList<Ellipse2D.Double> marbles;

  /**
   * Creates an instance of the MancalaPanel
   * @param model the Model from the MVC pattern
   * @param isA determines if it is player A
   * @param style the style of the board
   */
  public MancalaPanel(MancalaModel model, boolean isA, MancalaStyle style) {
    this.model = model;
    this.isA = isA;
    this.style = style;

    random = new Random();
    marbles = new ArrayList<>();
  }

  @Override
  /**
   * Creates the Mancala board and stones
   */
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
    g2.setColor(style.getPitFillColor());
    g2.fill(mancala);
    g2.setStroke(MancalaView.STROKE);
    g2.setColor(style.getPitDrawColor());
    g2.draw(mancala);

    // Pit name
    g2.setColor(style.getTextColor());
    g2.setFont(FONT_18);
    FontMetrics metrics = g2.getFontMetrics();
    String pitName = isA ? "A" : "B";
    float x = (getWidth() - metrics.stringWidth(pitName)) / 2;
    float y = isA ? mancalaY + mancalaHeight - PADDING : mancalaY + metrics.getAscent() + PADDING;
    g2.drawString(pitName, x, y);

    // Marble count
    int numMarbles =
        model.getMarbles(isA ? MancalaModel.A_MANCALA_POS : MancalaModel.B_MANCALA_POS);
    String marbleStr = String.valueOf(numMarbles);
    x = (getWidth() - metrics.stringWidth(marbleStr)) / 2;
    y = isA ? mancalaY + metrics.getAscent() + PADDING : mancalaY + mancalaHeight - PADDING;
    g2.drawString(marbleStr, x, y);

    // Draw randomly positioned marbles
    float marbleSize = 0.2f * getWidth();

    // Different number of marbles, re-randomize
    if (numMarbles != marbles.size()) {
      marbles.clear();

      for (int i = 0; i < numMarbles; i++) {
        Ellipse2D.Double marble;

        // Keep randomly placing marble until it is completely inside the pit
        do {
          x = mancalaX + random.nextFloat() * (mancalaWidth - marbleSize);
          y =
              mancalaY
                  + 0.2f * mancalaHeight
                  + random.nextFloat() * (0.6f * mancalaHeight - marbleSize);
          marble = new Ellipse2D.Double(x, y, marbleSize, marbleSize);
        } while (!mancala.contains(marble.x, marble.y, marble.width, marble.height));

        marbles.add(marble);
      }
    }

    // Draw marbles
    for (Ellipse2D.Double marble : marbles) {
      g2.setColor(style.getMarbleFillColor());
      g2.fill(marble);
      g2.setColor(style.getMarbleDrawColor());
      g2.draw(marble);
    }
  }
}