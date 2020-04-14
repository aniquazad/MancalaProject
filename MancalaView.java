package ram;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ram.MenuView.*;

/**
 * This class is one of the views and the Controller in the MVC pattern for the Mancala
 * application. It holds the actual Mancala board with stones and allows
 * players to interact with the board.
 * 
 * @author Aniqua Azad, Malaak Khalil, Ryan Tran
 *
 */
public class MancalaView extends JFrame implements ChangeListener {
  private MancalaModel model;
  private JLabel turnLabel;
  private JLabel undoRemainingLabel;
  private JButton undoButton;

  public static final int FRAME_WIDTH = 1000;
  public static final int FRAME_HEIGHT = 500;
  public static final int UPPER_LOWER_PANEL_HEIGHT = (int) (0.1f * FRAME_HEIGHT);
  public static final int GAME_PANEL_HEIGHT = (int) (0.8f * FRAME_HEIGHT);
  public static final int PIT_WIDTH = (int) ((1f / 8f) * FRAME_WIDTH);
  public static final int PIT_HEIGHT = GAME_PANEL_HEIGHT / 2;
  public static final BasicStroke STROKE = new BasicStroke(2.5f);

  /**
   * Creates the instance of the Mancala board with specified attributes
   * @param model the Model from the MVC pattern
   * @param style the style of the board
   */
  public MancalaView(MancalaModel model, String style) {
    this.model = model;

    MancalaStyle mancalaStyle;
    switch (style) {
      case "Ocean":
        mancalaStyle = new OceanStyle();
        break;
      case "Forest":
        mancalaStyle = new ForestStyle();
        break;
      case "Volcano":
        mancalaStyle = new VolcanoStyle();
        break;
      default:
        mancalaStyle = null;
        break;
    }

    setTitle("Mancala - Team RAM");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    getContentPane().setBackground(mancalaStyle.getBackgroundColor());

    turnLabel = new JLabel("Player " + (model.isPlayerATurn() ? "A" : "B") + "'s Turn");
    turnLabel.setFont(FONT_18);
    turnLabel.setForeground(mancalaStyle.getTextColor());
    turnLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JPanel turnPanel = new JPanel();
    turnPanel.setBackground(mancalaStyle.getBackgroundColor());
    turnLabel.setPreferredSize(new Dimension(FRAME_WIDTH, UPPER_LOWER_PANEL_HEIGHT));
    turnLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, UPPER_LOWER_PANEL_HEIGHT));
    turnPanel.add(turnLabel);

    JPanel gamePanel = new JPanel();
    gamePanel.setLayout(new GridBagLayout());

    MancalaPanel leftMancalaPanel = new MancalaPanel(model, false, mancalaStyle);
    leftMancalaPanel.setBackground(mancalaStyle.getBackgroundColor());
    leftMancalaPanel.setPreferredSize(new Dimension(PIT_WIDTH, GAME_PANEL_HEIGHT));
    leftMancalaPanel.setMaximumSize(new Dimension(PIT_WIDTH, Integer.MAX_VALUE));

    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.BOTH;
    constraints.gridheight = 2;
    constraints.gridx = 0;
    constraints.gridy = 0;
    constraints.weightx = 0.5;
    constraints.weighty = 0.5f;
    gamePanel.add(leftMancalaPanel, constraints);

    // Add Player B pits
    for (int i = 0; i < MancalaModel.NUM_PITS_PER_PLAYER; i++) {
      PitPanel pitPanel =
          new PitPanel(model, MancalaModel.B_MANCALA_POS - 1 - i, false, mancalaStyle);
      pitPanel.setBackground(mancalaStyle.getBackgroundColor());
      pitPanel.setPreferredSize(new Dimension(PIT_WIDTH, PIT_HEIGHT));
      pitPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

      constraints = new GridBagConstraints();
      constraints.fill = GridBagConstraints.BOTH;
      constraints.gridx = 1 + i;
      constraints.gridy = 0;
      constraints.weightx = 0.5;
      constraints.weighty = 0.5f;
      gamePanel.add(pitPanel, constraints);
    }

    // Add Player A pits
    for (int i = 0; i < MancalaModel.NUM_PITS_PER_PLAYER; i++) {
      PitPanel pitPanel = new PitPanel(model, i, true, mancalaStyle);
      pitPanel.setBackground(mancalaStyle.getBackgroundColor());
      pitPanel.setPreferredSize(new Dimension(PIT_WIDTH, PIT_HEIGHT));
      pitPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

      constraints = new GridBagConstraints();
      constraints.fill = GridBagConstraints.BOTH;
      constraints.gridx = 1 + i;
      constraints.gridy = 1;
      constraints.weightx = 0.5;
      constraints.weighty = 0.5f;
      gamePanel.add(pitPanel, constraints);
    }

    MancalaPanel rightMancalaPanel = new MancalaPanel(model, true, mancalaStyle);
    rightMancalaPanel.setBackground(mancalaStyle.getBackgroundColor());
    rightMancalaPanel.setPreferredSize(new Dimension(PIT_WIDTH, GAME_PANEL_HEIGHT));
    rightMancalaPanel.setMaximumSize(new Dimension(PIT_WIDTH, Integer.MAX_VALUE));

    constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.BOTH;
    constraints.gridheight = 2;
    constraints.gridx = 7;
    constraints.gridy = 0;
    constraints.weightx = 0.5;
    constraints.weighty = 0.5f;
    gamePanel.add(rightMancalaPanel, constraints);

    undoButton =
        new JButton("Undo") {
          @Override
          protected void paintComponent(Graphics g) {
            if (getModel().isPressed()) {
              g.setColor(getBackground());
            } else if (getModel().isRollover()) {
              g.setColor(mancalaStyle.getPitDrawColor());
            } else {
              g.setColor(getBackground());
            }
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
          }
        };
    undoButton.setFocusPainted(false);
    undoButton.setContentAreaFilled(false);
    undoButton.setFont(FONT_18);
    undoButton.setBackground(mancalaStyle.getPitFillColor());
    undoButton.setForeground(mancalaStyle.getTextColor());
    undoButton.setBorder(BorderFactory.createLineBorder(mancalaStyle.getPitDrawColor()));
    undoButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
    undoButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
    undoButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            model.undoMove();
          }
        });

    undoRemainingLabel = new JLabel("remaining: " + model.getUndoRemaining());
    undoRemainingLabel.setFont(FONT_18);
    undoRemainingLabel.setForeground(mancalaStyle.getTextColor());

    JPanel undoPanel = new JPanel();
    undoPanel.setBackground(mancalaStyle.getBackgroundColor());
    undoPanel.setPreferredSize(new Dimension(FRAME_WIDTH, UPPER_LOWER_PANEL_HEIGHT));
    undoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, UPPER_LOWER_PANEL_HEIGHT));
    undoPanel.add(undoButton);
    undoPanel.add(undoRemainingLabel);

    // Add to frame
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
  /**
   * Alters the state of the board when a move is made
   */
  public void stateChanged(ChangeEvent e) {
    if (model.gameOver()) {
      // Change turn label's text to display winner
      int winner = model.getWinner();
      turnLabel.setText(
          winner == MancalaModel.TIE
              ? "The game is a Tie!"
              : "Player " + (winner == MancalaModel.A_WINNER ? "A" : "B") + " won!");

      // Change undo remaining label and undo button text to indicate game over
      undoRemainingLabel.setText("Game over!");
      undoButton.setText("Menu");

      // Change undo button to main menu button
      undoButton.removeActionListener(undoButton.getActionListeners()[0]);
      undoButton.addActionListener(
          new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              // Destroy this window and create a new main menu view
              dispose();
              new MenuView();
            }
          });
    } else {
      turnLabel.setText("Player " + (model.isPlayerATurn() ? "A" : "B") + "'s Turn");
      undoRemainingLabel.setText("remaining: " + model.getUndoRemaining());
    }

    repaint();
  }
}