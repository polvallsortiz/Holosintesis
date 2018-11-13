import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class DesignsMenu {

    private Button seeDesigns;
    private Button uploadDesign;
    Button returnButton;

    private Stage primaryStage;

    public DesignsMenu(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/DesignsMenu.fxml"));
        primaryStage.setTitle("Holosintesis Uploader");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

        seeDesigns = (Button) primaryStage.getScene().lookup("#seeDesigns");
        uploadDesign = (Button) primaryStage.getScene().lookup("#uploadDesign");
        returnButton = (Button) primaryStage.getScene().lookup("#returnButton");

        returnButton.setOnMouseClicked(e-> {
            try {
                returnPressed();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

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
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    private  void returnPressed() throws IOException {
        Index i = new Index(primaryStage);
    }

    private void seeDesignsSelected() throws Exception {
        DesignList dl = new DesignList(primaryStage);
    }

    private void uploadDesignSelected() throws Exception {
        DesignUploader du = new DesignUploader(primaryStage);
    }

}
