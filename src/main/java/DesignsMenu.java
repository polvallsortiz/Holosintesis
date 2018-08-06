import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class DesignsMenu {

    private Button seeDesigns;
    private Button uploadDesign;
    private Button deleteDesign;

    private Stage primaryStage;

    public DesignsMenu(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/DesignsMenu.fxml"));
        primaryStage.setTitle("Menú Principal - Hidato Game");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

        seeDesigns = (Button) primaryStage.getScene().lookup("#seeDesigns");
        uploadDesign = (Button) primaryStage.getScene().lookup("#uploadDesign");
        deleteDesign = (Button) primaryStage.getScene().lookup("#deleteDesign");

        seeDesigns.setOnMouseClicked(e-> {
            try {
                seeDesignsSelected();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        uploadDesign.setOnMouseClicked(e-> {
            try {
                uploadDesignSelected();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        deleteDesign.setOnMouseClicked(e->deleteDesignSelected());

    }

    private void seeDesignsSelected() throws Exception {
        DesignList dl = new DesignList(primaryStage);
    }

    private void uploadDesignSelected() throws IOException {
        DesignUploader du = new DesignUploader(primaryStage);
    }

    private void deleteDesignSelected() {

    }
}
