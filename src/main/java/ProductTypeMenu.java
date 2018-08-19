import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductTypeMenu {

    private Button seeProductType;
    private Button uploadProductType;
    Button returnButton;

    private Stage primaryStage;

    public ProductTypeMenu(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/ProductTypeMenu.fxml"));
        primaryStage.setTitle("Menú Principal - Hidato Game");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

        seeProductType = (Button) primaryStage.getScene().lookup("#seeProductType");
        uploadProductType = (Button) primaryStage.getScene().lookup("#uploadProductType");
        returnButton = (Button) primaryStage.getScene().lookup("#returnButton");

        returnButton.setOnMouseClicked(e-> {
            try {
                returnPressed();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });


        seeProductType.setOnMouseClicked(e-> {
            try {
                seeProductTypeSelected();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        uploadProductType.setOnMouseClicked(e-> {
            try {
                uploadProductTypeSelected();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

    }

    private  void returnPressed() throws IOException {
        Index i = new Index(primaryStage);
    }

    private void seeProductTypeSelected() throws Exception {
        ProductTypeList ptl = new ProductTypeList(primaryStage);
    }

    private void uploadProductTypeSelected() throws IOException {
        ProductTypeUploader ptu = new ProductTypeUploader(primaryStage);
    }
}
