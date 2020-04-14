package ram;

import java.awt.*;

/**
 * An interface for the Strategy Pattern
 * @author Aniqua Azad, Malaak Khalil, Ryan Tran
 *
 */
public interface MancalaStyle {
  Color getBackgroundColor();

  Color getTextColor();

  Color getPitFillColor();

  Color getPitDrawColor();

  Color getMarbleFillColor();

  Color getMarbleDrawColor();
}