import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class Index extends Application{

    private Button familiesButton;
    private Button designsButton;
    private Button productTypeButton;
    private Button productsButton;

    private Stage primaryStage;

    public Index(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/Index.fxml"));
        primaryStage.setTitle("Holosintesis Uploader");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

        designsButton = (Button) primaryStage.getScene().lookup("#designsButton");
        productTypeButton = (Button) primaryStage.getScene().lookup("#productTypeButton");
        productsButton = (Button) primaryStage.getScene().lookup("#productsButton");
        familiesButton = (Button) primaryStage.getScene().lookup("#familiesButton");

        familiesButton.setOnMouseClicked(e-> {
            try {
                FamiliesMenu fm = new FamiliesMenu(primaryStage);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        designsButton.setOnMouseClicked(e-> {
            try {
                DesignsMenu dm = new DesignsMenu(primaryStage);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        productTypeButton.setOnMouseClicked( e-> {
            try {
                ProductTypeMenu ptm = new ProductTypeMenu(primaryStage);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        productsButton.setOnMouseClicked(e-> {
            try {
                ProductMenu pm = new ProductMenu(primaryStage);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

    }


    public void main(String[] args) {
        launch();
    }

    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Index.fxml"));
        primaryStage.setTitle("Holosintesis Uploader");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        Image icon = new Image("/images/holosintesis_icon.png");
        primaryStage.getIcons().add(icon);
        primaryStage.show();

        familiesButton = (Button) primaryStage.getScene().lookup("#familiesButton");
        designsButton = (Button) primaryStage.getScene().lookup("#designsButton");
        productTypeButton = (Button) primaryStage.getScene().lookup("#productTypeButton");
        productsButton = (Button) primaryStage.getScene().lookup("#productsButton");

        familiesButton.setOnMouseClicked(e-> {
            try {
                FamiliesMenu fm = new FamiliesMenu(primaryStage);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        designsButton.setOnMouseClicked(e-> {
            try {
                DesignsMenu dm = new DesignsMenu(primaryStage);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        productTypeButton.setOnMouseClicked( e-> {
            try {
                ProductTypeMenu ptm = new ProductTypeMenu(primaryStage);
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        productsButton.setOnMouseClicked(e-> {
            try {
                ProductMenu pm = new ProductMenu(primaryStage);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

    }

    public Index() {
    }
}
