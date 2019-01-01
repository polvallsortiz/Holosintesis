import com.google.gson.Gson;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

public class DesignList {

    Design[] designs;
    TableView table;
    Button returnButton;

    Stage primaryStage;

    public DesignList(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/DesignList.fxml"));
        primaryStage.setTitle("Holosintesis Uploader");
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
        table.setOnMouseClicked( event -> {
            Design design = (Design) table.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Esteu segurs?");
            alert.setHeaderText("Esteu segurs que voleu esborrar el disseny amb títol" + design.getTitle_design());
            alert.setResizable(false);
            alert.setContentText("Seleccioneu la opció");
            alert.showAndWait();
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                try {
                    deleteDesign(design);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void deleteDesign(Design design) throws Exception {
        String design_title = design.getTitle_design();
        String def = "";
        for(int i = 0; i < design_title.length(); ++i) {
            char c = design_title.charAt(i);
            if(design_title.charAt(i) == ' ') def += "%20";
            else def += design_title.charAt(i);
        }
        String url = "http://holosintesis.ddns.net:3000/design" + "?title_design=eq." + def;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("DELETE");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending a 'DELETE' request to url : "+ url);
        System.out.println("Response code : " + responseCode);
        table.getItems().clear();
        getDesigns();
        fillTable();
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
        patologies.setPrefWidth(200);
        TableColumn title_family = new TableColumn("Família");
        title_family.setCellValueFactory(new PropertyValueFactory<>("title_family"));
        title_family.setPrefWidth(200);
        table.getColumns().addAll(title_design,image_design,description_design,patologies,title_family);
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
