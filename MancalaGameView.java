package ram;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MancalaGameView extends JFrame implements ChangeListener {
  public static final int FRAME_WIDTH = 1000;
  public static final int FRAME_HEIGHT = 500;
  public static final int UPPER_LOWER_PANEL_HEIGHT = (int) (0.1f * FRAME_HEIGHT);
  public static final int GAME_PANEL_HEIGHT = (int) (0.8f * FRAME_HEIGHT);
  public static final int PIT_PANEL_WIDTH = (int) ((6f / 8f) * FRAME_WIDTH);
  public static final int PIT_WIDTH = (int) ((1f / 8f) * FRAME_WIDTH);
  public static final int PIT_HEIGHT = GAME_PANEL_HEIGHT / 2;
  public static final int PADDING = 15;

  public static final Font FONT = new Font("SansSerif", Font.BOLD, 18);
  public static final BasicStroke STROKE = new BasicStroke(2.5f);

  public MancalaGameView(MancalaGameModel model) {
    setTitle("Mancala - Team RAM");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

    JLabel turnLabel = new JLabel("Player " + (model.isPlayerATurn() ? "A" : "B") + "'s Turn");
    turnLabel.setFont(FONT);
    turnLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JPanel turnPanel = new JPanel();
    turnLabel.setPreferredSize(new Dimension(FRAME_WIDTH, UPPER_LOWER_PANEL_HEIGHT));
    turnLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, UPPER_LOWER_PANEL_HEIGHT));
    turnPanel.add(turnLabel);

    MancalaPanel leftMancalaPanel = new MancalaPanel(model, false);
    leftMancalaPanel.setPreferredSize(new Dimension(PIT_WIDTH, GAME_PANEL_HEIGHT));
    leftMancalaPanel.setMaximumSize(new Dimension(PIT_WIDTH, Integer.MAX_VALUE));

    JPanel pitGridPanel = new JPanel();
    pitGridPanel.setLayout(new GridLayout(2, 6));

    // Add Player B pits
    for (int i = MancalaGameModel.B_MANCALA_POS - 1; i > MancalaGameModel.A_MANCALA_POS; i--) {
      PitPanel pitPanel = new PitPanel(model, i, false);
      pitPanel.setPreferredSize(new Dimension(PIT_WIDTH, PIT_HEIGHT));
      pitPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
      pitGridPanel.add(pitPanel);
    }

    // Add Player A pits
    for (int i = 0; i < MancalaGameModel.A_MANCALA_POS; i++) {
      PitPanel pitPanel = new PitPanel(model, i, true);
      pitPanel.setPreferredSize(new Dimension(PIT_WIDTH, PIT_HEIGHT));
      pitPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
      pitGridPanel.add(pitPanel);
    }

    MancalaPanel rightMancalaPanel = new MancalaPanel(model, true);
    rightMancalaPanel.setPreferredSize(new Dimension(PIT_WIDTH, GAME_PANEL_HEIGHT));
    rightMancalaPanel.setMaximumSize(new Dimension(PIT_WIDTH, Integer.MAX_VALUE));

    JPanel gamePanel = new JPanel();
    gamePanel.setBackground(Color.RED);
    gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.X_AXIS));
    gamePanel.add(leftMancalaPanel);
    gamePanel.add(pitGridPanel);
    gamePanel.add(rightMancalaPanel);

    JButton undoButton = new JButton("Undo");
    undoButton.setFont(FONT);
    undoButton.setPreferredSize(new Dimension(100, 35));
    undoButton.setMaximumSize(new Dimension(100, 35));
    undoButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            model.undoMove();
          }
        });

    JPanel undoPanel = new JPanel();
    undoPanel.setPreferredSize(new Dimension(FRAME_WIDTH, UPPER_LOWER_PANEL_HEIGHT));
    undoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, UPPER_LOWER_PANEL_HEIGHT));
    undoPanel.add(undoButton);

    add(Box.createRigidArea(new Dimension(0, PADDING)));
    add(turnPanel);
    add(Box.createRigidArea(new Dimension(0, PADDING)));
    add(gamePanel);
    add(Box.createRigidArea(new Dimension(0, PADDING)));
    add(undoPanel);
    add(Box.createRigidArea(new Dimension(0, PADDING)));
    pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(
        screenSize.width / 2 - getSize().width / 2, screenSize.height / 2 - getSize().height / 2);
    setVisible(true);
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    repaint();
  }
}
