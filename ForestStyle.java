package ram;

import java.awt.*;

/**
 * A class which defines a concrete strategy for the Strategy pattern. In this case, it makes the
 * board a forest theme.
 *
 * @author Aniqua Azad, Malaak Khalil, Ryan Tran
 */
public class ForestStyle implements MancalaStyle {
  private static final Color BACKGROUND_COLOR = new Color(172, 255, 139);
  private static final Color TEXT_COLOR = new Color(216, 106, 51);
  private static final Color PIT_FILL_COLOR = new Color(63, 216, 133);
  private static final Color PIT_DRAW_COLOR = new Color(0, 220, 50);
  private static final Color MARBLE_FILL_COLOR = new Color(203, 201, 79);
  private static final Color MARBLE_DRAW_COLOR = new Color(186, 159, 0);

  @Override
  public Color getBackgroundColor() {
    return BACKGROUND_COLOR;
  }

  @Override
  public Color getTextColor() {
    return TEXT_COLOR;
  }

  @Override
  public Color getPitFillColor() {
    return PIT_FILL_COLOR;
  }

  @Override
  public Color getPitDrawColor() {
    return PIT_DRAW_COLOR;
  }

  @Override
  public Color getMarbleFillColor() {
    return MARBLE_FILL_COLOR;
  }

  @Override
  public Color getMarbleDrawColor() {
    return MARBLE_DRAW_COLOR;
  }
}
