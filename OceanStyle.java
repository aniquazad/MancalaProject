package ram;

import java.awt.*;

/**
 * A class which defines a concrete strategy for the Strategy pattern. 
 * In this case, it makes the board an ocean theme.
 * @author Aniqua Azad, Malaak Khalil, Ryan Tran
 *
 */
public class OceanStyle implements MancalaStyle {
  private static final Color BACKGROUND_COLOR = new Color(130, 218, 255);
  private static final Color TEXT_COLOR = new Color(150, 0, 255);
  private static final Color PIT_FILL_COLOR = new Color(94, 191, 255);
  private static final Color PIT_DRAW_COLOR = new Color(0, 26, 255);
  private static final Color MARBLE_FILL_COLOR = new Color(219, 142, 255);
  private static final Color MARBLE_DRAW_COLOR = new Color(240, 0, 255, 239);

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