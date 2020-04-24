package ram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/**
 * This class is one of the Views in the MVC pattern. It displays the main menu and allows the
 * players to customize the game.
 *
 * @author Aniqua Azad, Malaak Khalil, Ryan Tran
 */
public class MenuView extends JFrame {
  public static final int BUTTON_WIDTH = 100;
  public static final int BUTTON_HEIGHT = 35;
  public static final int PADDING = 15;
  public static final Font FONT_18 = new Font("SansSerif", Font.BOLD, 18);
  public static final Font FONT_25 = new Font("SansSerif", Font.BOLD, 25);

  /** Creates the menu for the Mancala application and allows players to customize their game. */
  public MenuView() {
    setTitle("Mancala Menu - Team RAM");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JLabel titleLabel = new JLabel("Mancala Menu - Team RAM");
    titleLabel.setFont(FONT_25);

    // Number marbles per pit initially
    JLabel numberMarblesLabel = new JLabel("Number of marbles:");
    numberMarblesLabel.setFont(FONT_18);
    JRadioButton marbles3Button = new JRadioButton("3", false);
    JRadioButton marbles4Button = new JRadioButton("4", true);
    ButtonGroup marblesGroup = new ButtonGroup();
    marblesGroup.add(marbles3Button);
    marblesGroup.add(marbles4Button);

    // Styles
    JLabel styleLabel = new JLabel("Style:");
    styleLabel.setFont(FONT_18);
    JRadioButton oceanStyleButton = new JRadioButton("Ocean", true);
    JRadioButton forestStyleButton = new JRadioButton("Forest", true);
    JRadioButton volcanoStyleButton = new JRadioButton("Volcano", true);
    ButtonGroup styleGroup = new ButtonGroup();
    styleGroup.add(oceanStyleButton);
    styleGroup.add(forestStyleButton);
    styleGroup.add(volcanoStyleButton);

    // Play button
    JButton playButton = new JButton("Play!");
    playButton.setFont(FONT_18);
    playButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
    playButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
    playButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            Enumeration<AbstractButton> styleButtons = styleGroup.getElements();

            // Find the selected style
            while (styleButtons.hasMoreElements()) {
              JRadioButton styleButton = (JRadioButton) styleButtons.nextElement();

              // Found the selected style
              if (styleButton.isSelected()) {
                MancalaModel model = new MancalaModel();
                model.setUpMancalaBoard(marbles3Button.isSelected() ? 3 : 4);
                MancalaView view = new MancalaView(model, styleButton.getText());
                model.attach(view);

                // Destroy menu view
                dispose();
                break;
              }
            }
          }
        });

    // help button for first-time players
    JButton helpButton = new JButton("HELP");
    helpButton.setFont(FONT_18);
    helpButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
    helpButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
    helpButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (e.getSource() == helpButton) {
              // the rules for the game
              StringBuilder strBuilder = new StringBuilder();
              strBuilder.append(
                  "--The board consists of two rows of pits. 3-4 pieces of stones are placed in each of the 12 holes.\n");
              strBuilder.append(
                  "--Each player has a large pit called Mancala to the right side of the board.\n");
              strBuilder.append(
                  "--One player starts the game by picking up all of the stones in any one of his own pits.\n");
              strBuilder.append(
                  "Moving counter-clock wise, the player places one in each pit starting with the next pit until the stones run out.\n");
              strBuilder.append("If you run into your own Mancala, place one stone in it.\n");
              strBuilder.append(
                  "If there are more stones to go past your own Mancala, continue placing them into the opponent's pits.\n");
              strBuilder.append("--Skip your opponent's Mancala pit.\n");
              strBuilder.append(
                  "--If the last stone you drop is your own Mancala, you get a free turn.\n");
              strBuilder.append(
                  "--If the last stone you drop is in an empty pit on your side, you get to take that stone and all of your opponents stones that are in the opposite pit.\n");
              strBuilder.append("Place all captured stones in your own Mancala.\n");
              strBuilder.append(
                  "--The game ends when all six pits on one side of the Mancala board are empty.\n");
              strBuilder.append(
                  "The player who still has stones on his side of the board when the game ends captures all of those pieces and place them in his Mancala.\n");
              strBuilder.append("The player with the most stones in his Mancala wins.\n");
              JOptionPane.showMessageDialog(
                  helpButton, strBuilder, "HOW TO PLAY", JOptionPane.INFORMATION_MESSAGE);
            }
          }
        });

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(PADDING, PADDING, PADDING, PADDING));
    Dimension paddingDim = new Dimension(0, PADDING);
    mainPanel.add(titleLabel);
    mainPanel.add(Box.createRigidArea(paddingDim));
    mainPanel.add(numberMarblesLabel);
    mainPanel.add(marbles3Button);
    mainPanel.add(marbles4Button);
    mainPanel.add(Box.createRigidArea(paddingDim));
    mainPanel.add(styleLabel);
    mainPanel.add(oceanStyleButton);
    mainPanel.add(forestStyleButton);
    mainPanel.add(volcanoStyleButton);
    mainPanel.add(Box.createRigidArea(paddingDim));
    mainPanel.add(playButton);
    mainPanel.add(Box.createRigidArea(paddingDim));
    mainPanel.add(helpButton);

    // Add to frame
    add(mainPanel);
    pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(
        screenSize.width / 2 - getSize().width / 2, screenSize.height / 2 - getSize().height / 2);
    setVisible(true);
  }
}
