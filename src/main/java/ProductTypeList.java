import com.google.gson.Gson;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

public class ProductTypeList {

    ProductType[] productTypes;
    TableView table;
    Button returnButton;

    Stage primaryStage;

    public ProductTypeList(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/ProductTypeList.fxml"));
        primaryStage.setTitle("Menú Principal - Hidato Game");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

        table = (TableView) primaryStage.getScene().lookup("#table");
        returnButton = (Button) primaryStage.getScene().lookup("#returnButton");
        getProductType();
        fillTable();
        returnButton.setOnMouseClicked(e-> {
            try {
                returnPressed();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        table.setOnMouseClicked( event -> {
            ProductType productType = (ProductType) table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Esteu segurs?");
            alert.setHeaderText("Esteu segurs que voleu esborrar el disseny amb títol" + productType.getTitle_producttype());
            alert.setResizable(false);
            alert.setContentText("Seleccioneu la opció");
            alert.showAndWait();
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                try {
                    deleteProductType(productType);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void deleteProductType(ProductType productType) throws Exception {
        String productType_title = productType.getTitle_producttype();
        String def = "";
        for(int i = 0; i < productType_title.length(); ++i) {
            char c = productType_title.charAt(i);
            if(c == ' ') def += "%20";
            else def += c;
        }
        String url = "http://holosintesis.ddns.net:3000/producttype" + "?title_producttype=eq." + def;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("DELETE");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending a 'DELETE' request to url : "+ url);
        System.out.println("Response code : " + responseCode);
        table.getItems().clear();
        getProductType();
        fillTable();
    }

    private  void returnPressed() throws IOException {
        ProductTypeMenu ptm = new ProductTypeMenu(primaryStage);
    }

    private void fillTable() {
        table.setEditable(false);
        TableColumn title_producttype = new TableColumn("Nom tipus");
        title_producttype.setCellValueFactory(new PropertyValueFactory<>("title_producttype"));
        title_producttype.setPrefWidth(200);
        TableColumn image_producttype = new TableColumn("Enllaç Imatge");
        image_producttype.setCellValueFactory(new PropertyValueFactory<>("image_producttype"));
        image_producttype.setPrefWidth(400);
        TableColumn description_producttype = new TableColumn("Descripció");
        description_producttype.setCellValueFactory(new PropertyValueFactory<>("description_producttype"));
        description_producttype.setPrefWidth(200);
        table.getColumns().addAll(title_producttype,image_producttype,description_producttype);
        for(ProductType productType : productTypes) {
            table.getItems().add(productType);
        }
    }

    private void getProductType() throws Exception {
        String url = "http://holosintesis.ddns.net:3000/producttype";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending a 'GET' request to url : "+ url);
        System.out.println("Response code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        ProductType[] productTypes = new Gson().fromJson(response.toString(), ProductType[].class);
        this.productTypes = productTypes;
    }
}
