package ram;

import javax.swing.*;
import java.awt.*;

public class CenterPanel extends JPanel {
  public static final int CENTER_PANEL_WIDTH = 600;

  public CenterPanel(MancalaGameModel model) {
    Dimension size = new Dimension(CENTER_PANEL_WIDTH, MancalaGameView.PANEL_HEIGHT);
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);
    setSize(size);

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    PitsPanel topPitsPanel = new PitsPanel(model, false);
    PitsPanel bottomPitsPanel = new PitsPanel(model, true);

    add(topPitsPanel);
    add(bottomPitsPanel);
  }
}
