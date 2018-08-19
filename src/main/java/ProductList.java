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

public class ProductList {

    Product[] products;
    TableView table;
    Button returnButton;

    Stage primaryStage;

    public ProductList(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/ProductList.fxml"));
        primaryStage.setTitle("Menú Principal - Hidato Game");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

        table = (TableView) primaryStage.getScene().lookup("#table");
        returnButton = (Button) primaryStage.getScene().lookup("#returnButton");
        getProduct();
        fillTable();
        returnButton.setOnMouseClicked(e-> {
            try {
                returnPressed();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        table.setOnMouseClicked( event -> {
            Product product = (Product) table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Esteu segurs?");
            alert.setHeaderText("Esteu segurs que voleu esborrar el disseny amb títol" + product.getTitle_product());
            alert.setResizable(false);
            alert.setContentText("Seleccioneu la opció");
            alert.showAndWait();
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                try {
                    deleteProduct(product);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void deleteProduct(Product product) throws Exception {
        String product_title = product.getTitle_product();
        String def = "";
        for(int i = 0; i < product_title.length(); ++i) {
            char c = product_title.charAt(i);
            if(c == ' ') def += "%20";
            else def += c;
        }
        String url = "http://holosintesis.ddns.net:3000/product" + "?title_product=eq." + def;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("DELETE");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending a 'DELETE' request to url : "+ url);
        System.out.println("Response code : " + responseCode);
        table.getItems().clear();
        getProduct();
        fillTable();
    }

    private  void returnPressed() throws IOException {
        ProductMenu pm = new ProductMenu(primaryStage);
    }

    private void fillTable() {
        table.setEditable(false);
        TableColumn title_product = new TableColumn("Nom producte");
        title_product.setCellValueFactory(new PropertyValueFactory<>("title_product"));
        title_product.setPrefWidth(200);
        TableColumn image_product = new TableColumn("Enllaç Imatge");
        image_product.setCellValueFactory(new PropertyValueFactory<>("image_product"));
        image_product.setPrefWidth(400);
        TableColumn description_product = new TableColumn("Descripció");
        description_product.setCellValueFactory(new PropertyValueFactory<>("title_design"));
        description_product.setPrefWidth(200);
        TableColumn title_design = new TableColumn("Nom disseny");
        title_design.setCellValueFactory(new PropertyValueFactory<>("title_design"));
        title_design.setPrefWidth(200);
        TableColumn title_producttype = new TableColumn("Tipus de producte");
        title_producttype.setCellValueFactory(new PropertyValueFactory<>("title_producttype"));
        title_producttype.setPrefWidth(200);
        table.getColumns().addAll(title_product,image_product,description_product,title_design,title_producttype);
        for(Product product : products) {
            table.getItems().add(product);
        }
    }

    private void getProduct() throws Exception {
        String url = "http://holosintesis.ddns.net:3000/product";
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
        Product[] products = new Gson().fromJson(response.toString(), Product[].class);
        this.products = products;
    }
}
