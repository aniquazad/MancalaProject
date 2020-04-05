package ram;

import java.awt.*;

public class VolcanoStyle implements MancalaStyle {
  private static final Color BACKGROUND_COLOR = new Color(255, 151, 136);
  private static final Color TEXT_COLOR = new Color(229, 255, 50);
  private static final Color PIT_FILL_COLOR = new Color(255, 99, 84);
  private static final Color PIT_DRAW_COLOR = new Color(255, 0, 38);
  private static final Color MARBLE_FILL_COLOR = new Color(255, 248, 123);
  private static final Color MARBLE_DRAW_COLOR = new Color(255, 177, 0);

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
