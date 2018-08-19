import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class Index extends Application{

    private Button designsButton;
    private Button productTypeButton;
    private Button productsButton;

    private Stage primaryStage;

    public Index(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/Index.fxml"));
        primaryStage.setTitle("Menú Principal - Hidato Game");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

        designsButton = (Button) primaryStage.getScene().lookup("#designsButton");
        productTypeButton = (Button) primaryStage.getScene().lookup("#productTypeButton");
        productsButton = (Button) primaryStage.getScene().lookup("#productsButton");

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
        primaryStage.setTitle("Menú Principal - Hidato Game");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

        designsButton = (Button) primaryStage.getScene().lookup("#designsButton");
        productTypeButton = (Button) primaryStage.getScene().lookup("#productTypeButton");
        productsButton = (Button) primaryStage.getScene().lookup("#productsButton");

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
