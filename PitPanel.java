package ram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import static ram.MenuView.FONT_18;
import static ram.MenuView.PADDING;

public class PitPanel extends JPanel {
  private MancalaModel model;
  private int index;
  private boolean isA;
  private MancalaStyle style;
  private Random random;

  public PitPanel(MancalaModel model, int index, boolean isA, MancalaStyle style) {
    this.model = model;
    this.index = index;
    this.isA = isA;
    this.style = style;
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
    g2.setColor(style.getPitFillColor());
    g2.fill(pit);
    g2.setStroke(MancalaView.STROKE);
    g2.setColor(style.getPitDrawColor());
    g2.draw(pit);

    // Pit name
    g2.setColor(style.getTextColor());
    g2.setFont(FONT_18);
    FontMetrics metrics = g2.getFontMetrics();
    String pitName = isA ? "A" + (index + 1) : "B" + (index - MancalaModel.NUM_PITS_PER_PLAYER);
    float x = (getWidth() - metrics.stringWidth(pitName)) / 2;
    float y = isA ? pitY + pitHeight + metrics.getAscent() + PADDING : pitY - PADDING;
    g2.drawString(pitName, x, y);

    // Marble count
    int numMarbles = model.getMarbles(index);
    String marbleStr = String.valueOf(numMarbles);
    x = (getWidth() - metrics.stringWidth(marbleStr)) / 2;
    y = isA ? pitY - PADDING : pitY + pitHeight + metrics.getAscent() + PADDING;
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

      g2.setColor(style.getMarbleFillColor());
      g2.fill(marble);
      g2.setColor(style.getMarbleDrawColor());
      g2.draw(marble);
    }
  }
}
