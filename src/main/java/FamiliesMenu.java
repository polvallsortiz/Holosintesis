import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class FamiliesMenu {

    private Button seeFamilies;
    private Button uploadFamilia;
    Button returnButton;

    private Stage primaryStage;

    public FamiliesMenu(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/FamiliesMenu.fxml"));
        primaryStage.setTitle("Holosintesis Uploader");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

        seeFamilies = (Button) primaryStage.getScene().lookup("#seeFamilies");
        uploadFamilia = (Button) primaryStage.getScene().lookup("#uploadFamilia");
        returnButton = (Button) primaryStage.getScene().lookup("#returnButton");

        returnButton.setOnMouseClicked(e-> {
            try {
                returnPressed();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        seeFamilies.setOnMouseClicked(e-> {
            try {
                seeFamiliesSelected();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        uploadFamilia.setOnMouseClicked(e-> {
            try {
                uploadFamiliaSelected();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private  void returnPressed() throws IOException {
        Index i = new Index(primaryStage);
    }

    private void seeFamiliesSelected() throws Exception {
        FamiliesList fl = new FamiliesList(primaryStage);
    }

    private void uploadFamiliaSelected() throws IOException {
        FamiliaUploader fu = new FamiliaUploader(primaryStage);
    }

}
