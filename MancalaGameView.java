package ram;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MancalaGameView extends JFrame implements ChangeListener {
  public static final BasicStroke STROKE = new BasicStroke(2.5f);
  public static final int PANEL_HEIGHT = 600;
  public static final int MARBLE_WIDTH = 15;

  public MancalaGameView(MancalaGameModel model) {
    setTitle("Mancala - Team RAM");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setVisible(true);

    MancalaPanel leftPanel = new MancalaPanel(model, false);
    CenterPanel centerPanel = new CenterPanel(model);
    MancalaPanel rightPanel = new MancalaPanel(model, true);

    add(leftPanel, BorderLayout.WEST);
    add(centerPanel, BorderLayout.CENTER);
    add(rightPanel, BorderLayout.EAST);

    pack();
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    repaint();
  }
}
