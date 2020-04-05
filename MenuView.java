package ram;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class MenuView extends JFrame {
  public static final int BUTTON_WIDTH = 100;
  public static final int BUTTON_HEIGHT = 35;
  public static final int PADDING = 15;
  public static final Font FONT_18 = new Font("SansSerif", Font.BOLD, 18);
  public static final Font FONT_25 = new Font("SansSerif", Font.BOLD, 25);

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

    // Add to frame
    add(mainPanel);
    pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation(
        screenSize.width / 2 - getSize().width / 2, screenSize.height / 2 - getSize().height / 2);
    setVisible(true);
  }
}
