package ram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class PitPanel extends JPanel {
  private MancalaGameModel model;
  private int index;
  private boolean isA;
  private Random random;

  public PitPanel(MancalaGameModel model, int index, boolean isA) {
    this.model = model;
    this.index = index;
    this.isA = isA;
    random = new Random();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    // Actual pit
    float pitWidth = 0.7f * getWidth();
    float pitHeight = 0.5f * getHeight();
    float pitX = (getWidth() - pitWidth) / 2;
    float pitY = (getHeight() - pitHeight) / 2;
    Ellipse2D.Double pit = new Ellipse2D.Double(pitX, pitY, pitWidth, pitHeight);
    if (getMouseListeners().length > 0) {
      removeMouseListener(getMouseListeners()[0]);
    }
    addMouseListener(
        new MouseListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
            if (pit.contains(e.getX(), e.getY())) {
              model.move(index);
            }
          }

          @Override
          public void mousePressed(MouseEvent e) {}

          @Override
          public void mouseReleased(MouseEvent e) {}

          @Override
          public void mouseEntered(MouseEvent e) {}

          @Override
          public void mouseExited(MouseEvent e) {}
        });
    g2.setStroke(MancalaGameView.STROKE);
    g2.draw(pit);

    // Pit name
    g2.setFont(MancalaGameView.FONT);
    FontMetrics metrics = g2.getFontMetrics();
    String pitName = isA ? "A" + (index + 1) : "B" + (index - MancalaGameModel.NUM_PITS_PER_PLAYER);
    float x = (getWidth() - metrics.stringWidth(pitName)) / 2;
    float y =
        isA
            ? pitY + pitHeight + metrics.getAscent() + MancalaGameView.PADDING
            : pitY - MancalaGameView.PADDING;
    g2.drawString(pitName, x, y);

    // Marble count
    int numMarbles = model.getMarbles(index);
    String marbleStr = String.valueOf(numMarbles);
    x = (getWidth() - metrics.stringWidth(marbleStr)) / 2;
    y =
        isA
            ? pitY - MancalaGameView.PADDING
            : pitY + pitHeight + metrics.getAscent() + MancalaGameView.PADDING;
    g2.drawString(marbleStr, x, y);

    // Draw randomly positioned marbles
    float marbleSize = 0.2f * getWidth();
    for (int i = 0; i < numMarbles; i++) {
      Ellipse2D.Double marble;

      // Keep randomly placing marble until it is completely inside the pit
      do {
        x = pitX + random.nextFloat() * (pitWidth - marbleSize);
        y = pitY + random.nextFloat() * (pitHeight - marbleSize);
        marble = new Ellipse2D.Double(x, y, marbleSize, marbleSize);
      } while (!pit.contains(marble.x, marble.y, marble.width, marble.height));

      g2.setColor(Color.GRAY);
      g2.fill(marble);
      g2.setColor(Color.BLACK);
      g2.draw(marble);
    }
  }
}
