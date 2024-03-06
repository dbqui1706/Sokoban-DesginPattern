import controller.ISokobanController;
import controller.SokobanController;
import model.SokobanModel;

import java.io.IOException;

public class MainApp {
    public static void main(String[] args) throws IOException {
        SokobanModel model = new SokobanModel();
        model.loadLevel(1);
        ISokobanController controller = new SokobanController(model);
    }
}
