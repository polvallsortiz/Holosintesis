import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DesignList {

    Design[] designs;
    TableView table;
    Button returnButton;

    Stage primaryStage;

    public DesignList(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/DesignList.fxml"));
        primaryStage.setTitle("Menú Principal - Hidato Game");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

        table = (TableView) primaryStage.getScene().lookup("#table");
        returnButton = (Button) primaryStage.getScene().lookup("#returnButton");
        getDesigns();
        fillTable();
        returnButton.setOnMouseClicked(e-> {
            try {
                returnPressed();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private  void returnPressed() throws IOException {
        DesignsMenu dm = new DesignsMenu(primaryStage);
    }

    private void fillTable() {
        table.setEditable(false);
        TableColumn title_design = new TableColumn("Nom disseny");
        title_design.setCellValueFactory(new PropertyValueFactory<>("title_design"));
        title_design.setPrefWidth(200);
        TableColumn image_design = new TableColumn("Enllaç Imatge");
        image_design.setCellValueFactory(new PropertyValueFactory<>("image_design"));
        image_design.setPrefWidth(400);
        TableColumn description_design = new TableColumn("Descripció");
        description_design.setCellValueFactory(new PropertyValueFactory<>("description_design"));
        description_design.setPrefWidth(200);
        TableColumn patologies = new TableColumn("Patologies");
        patologies.setCellValueFactory(new PropertyValueFactory<>("patologies"));
        patologies.setPrefWidth(400);
        table.getColumns().addAll(title_design,image_design,description_design,patologies);
        for(Design design : designs) {
            table.getItems().add(design);
        }
    }

    private void getDesigns() throws Exception {
        String url = "http://holosintesis.ddns.net:3000/design";
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
        Design[] designs = new Gson().fromJson(response.toString(), Design[].class);
        this.designs = designs;
    }
}
