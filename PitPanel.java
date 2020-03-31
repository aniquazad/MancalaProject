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

  private Ellipse2D.Double pit;
  private Random random;

  private static final Font FONT = new Font("SansSerif", Font.BOLD, 18);

  private static final int PANEL_WIDTH = 83;
  private static final int PANEL_HEIGHT = 250;
  private static final int PIT_WIDTH = (int) (.75 * PANEL_WIDTH);
  private static final int PIT_HEIGHT = (int) (1.25f * PIT_WIDTH);
  private static final int PIT_X = (PANEL_WIDTH - PIT_WIDTH) / 2;
  private static final int PIT_Y = (PANEL_HEIGHT - PIT_HEIGHT) / 2;

  public PitPanel(MancalaGameModel model, int index, boolean isA) {
    this.model = model;
    this.index = index;
    this.isA = isA;

    Dimension size = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);
    setSize(size);

    random = new Random();
    pit = new Ellipse2D.Double(PIT_X, PIT_Y, PIT_WIDTH, PIT_HEIGHT);

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
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;

    // Actual pit
    g2.setStroke(MancalaGameView.STROKE);
    g2.draw(pit);

    // Pit name
    g2.setFont(FONT);
    FontMetrics metrics = g2.getFontMetrics();
    String pitName = isA ? "A" + (index + 1) : "B" + (index - MancalaGameModel.NUM_PITS_PER_PLAYER);
    int x = (PANEL_WIDTH - metrics.stringWidth(pitName)) / 2;
    int y =
        isA
            ? PIT_Y + PIT_HEIGHT + metrics.getAscent() + metrics.getHeight() / 2
            : PIT_Y - metrics.getHeight();
    g2.drawString(pitName, x, y);

    // Marble count
    int marbles = model.getMarbles(index);
    String marbleStr = String.valueOf(marbles);
    x = (PANEL_WIDTH - metrics.stringWidth(marbleStr)) / 2;
    y =
        isA
            ? PIT_Y - metrics.getHeight()
            : PIT_Y + PIT_HEIGHT + metrics.getAscent() + metrics.getHeight() / 2;
    g2.drawString(marbleStr, x, y);

    // Draw randomly positioned marbles
    for (int i = 0; i < marbles; i++) {
      int minX = PIT_X + MancalaGameView.MARBLE_WIDTH;
      int maxX = PIT_X + PIT_WIDTH - MancalaGameView.MARBLE_WIDTH;
      x = PIT_X + random.nextInt(maxX - minX + 1);
      int minY = PIT_Y + MancalaGameView.MARBLE_WIDTH;
      int maxY = PIT_Y + PIT_HEIGHT - MancalaGameView.MARBLE_WIDTH;
      y = PIT_Y + random.nextInt(maxY - minY + 1);
      g2.fill(
          new Ellipse2D.Double(x, y, MancalaGameView.MARBLE_WIDTH, MancalaGameView.MARBLE_WIDTH));
    }
  }
}
