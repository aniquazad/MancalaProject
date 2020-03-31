package ram;

public class MancalaGameTester {
  public static void main(String[] args) {
    MancalaGameModel m = new MancalaGameModel();
    m.setUpMancalaBoard(4);
    MancalaGameView v = new MancalaGameView(m);
    m.attach(v);
  }
}
