import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductMenu {

    private Button seeProduct;
    private Button uploadProduct;
    Button returnButton;

    private Stage primaryStage;

    public ProductMenu(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/ProductsMenu.fxml"));
        primaryStage.setTitle("Holosintesis Uploader");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

        seeProduct = (Button) primaryStage.getScene().lookup("#seeProduct");
        uploadProduct = (Button) primaryStage.getScene().lookup("#uploadProduct");
        returnButton = (Button) primaryStage.getScene().lookup("#returnButton");

        returnButton.setOnMouseClicked(e-> {
            try {
                returnPressed();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        seeProduct.setOnMouseClicked(e-> {
            try {
                seeProductSelected();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        uploadProduct.setOnMouseClicked(e-> {
            try {
                uploadProductSelected();
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

    private void seeProductSelected() throws Exception {
        ProductList pl = new ProductList(primaryStage);
    }

    private void uploadProductSelected() throws Exception {
        ProductUploader pu = new ProductUploader(primaryStage);
    }

}
