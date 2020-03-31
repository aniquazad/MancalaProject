package ram;

import javax.swing.*;
import java.awt.*;

public class PitsPanel extends JPanel {
  public PitsPanel(MancalaGameModel model, boolean isA) {
    Dimension size =
        new Dimension(CenterPanel.CENTER_PANEL_WIDTH, MancalaGameView.PANEL_HEIGHT / 2);
    setPreferredSize(size);
    setMinimumSize(size);
    setMaximumSize(size);
    setSize(size);

    // Add pits
    for (int i = 0; i < MancalaGameModel.NUM_PITS_PER_PLAYER; i++) {
      int index = isA ? i : MancalaGameModel.TOTAL_NUM_PITS - 2 - i;
      add(new PitPanel(model, index, isA));
    }
  }
}
