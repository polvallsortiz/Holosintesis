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

public class FamiliesList {

    Familia[] families;
    TableView table;
    Button returnButton;

    Stage primaryStage;

    public FamiliesList(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/FamiliesList.fxml"));
        primaryStage.setTitle("Holosintesis Uploader");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

        table = (TableView) primaryStage.getScene().lookup("#table");
        returnButton = (Button) primaryStage.getScene().lookup("#returnButton");
        getFamilies();
        fillTable();
        returnButton.setOnMouseClicked(e-> {
            try {
                returnPressed();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        table.setOnMouseClicked( event -> {
            Familia familia = (Familia) table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Esteu segurs?");
            alert.setHeaderText("Esteu segurs que voleu esborrar la família amb títol" + familia.getTitle_family());
            alert.setResizable(false);
            alert.setContentText("Seleccioneu la opció");
            alert.showAndWait();
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                try {
                    deleteFamilia(familia);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void deleteFamilia(Familia familia) throws Exception {
        String design_title = familia.getTitle_family();
        String def = "";
        for(int i = 0; i < design_title.length(); ++i) {
            char c = design_title.charAt(i);
            if(design_title.charAt(i) == ' ') def += "%20";
            else def += design_title.charAt(i);
        }
        String url = "http://holosintesis.ddns.net:3000/familia" + "?title_family=eq." + def;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("DELETE");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending a 'DELETE' request to url : "+ url);
        System.out.println("Response code : " + responseCode);
        table.getItems().clear();
        getFamilies();
        fillTable();
    }

    private  void returnPressed() throws IOException {
        FamiliesMenu fm = new FamiliesMenu(primaryStage);
    }

    private void fillTable() {
        table.setEditable(false);
        TableColumn title_family = new TableColumn("Nom Família");
        title_family.setCellValueFactory(new PropertyValueFactory<>("title_family"));
        title_family.setPrefWidth(200);
        TableColumn description_family = new TableColumn("Descripció");
        description_family.setCellValueFactory(new PropertyValueFactory<>("description_family"));
        description_family.setPrefWidth(600);
        table.getColumns().addAll(title_family,description_family);
        for(Familia familia : families) {
            table.getItems().add(familia);
        }
    }

    private void getFamilies() throws Exception {
        String url = "http://holosintesis.ddns.net:3000/family";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending a 'GET' request to url : "+ url);
        System.out.println("Response code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        Familia[] families = new Gson().fromJson(response.toString(), Familia[].class);
        this.families = families;
    }
}
