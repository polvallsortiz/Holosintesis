import com.jcraft.jsch.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class FamiliaUploader extends Component {


    private Stage primaryStage;
    private TextField familiaTitle;
    private TextField familiaDescription;
    private Button uploadFamilia;
    private Label familiaLabel;
    Button returnButton;

    public FamiliaUploader(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/FamiliaUploader.fxml"));
        primaryStage.setTitle("Holosintesis Uploader");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

        familiaTitle = (TextField) primaryStage.getScene().lookup("#familiaTitle");
        familiaDescription = (TextField) primaryStage.getScene().lookup("#familiaDescription");
        returnButton = (Button) primaryStage.getScene().lookup("#returnButton");
        uploadFamilia = (Button) primaryStage.getScene().lookup("#uploadFamilia");
        familiaLabel = (Label) primaryStage.getScene().lookup("#familiaLabel");

        uploadFamilia.setOnMouseClicked(e-> {
            try {
                pushFamily();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSchException e1) {
                e1.printStackTrace();
            }
        });
        returnButton.setOnMouseClicked(e -> {
            try {
                returnPressed();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void returnPressed() throws IOException {
        FamiliesMenu fm = new FamiliesMenu(primaryStage);
    }


    private void pushFamily() throws IOException, JSchException {
       // if(!designTitle.getText().isEmpty() && !designDescription.getText().isEmpty() && !designPatologies.getText().isEmpty() && designImage == null) {
            String title = familiaTitle.getText();
            String description = familiaDescription.getText();

            Map<String,Object> params = new LinkedHashMap<>();
            params.put("title_family",title);
            params.put("description_family",description);


            StringBuilder tokenUri=new StringBuilder("title_family=");
            tokenUri.append(URLEncoder.encode(title,"UTF-8"));
            tokenUri.append("&description_family=");
            tokenUri.append(URLEncoder.encode(description,"UTF-8"));

            String url = "http://holosintesis.ddns.net:3000/family";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            byte[] postDataBytes = tokenUri.toString().getBytes("UTF-8");
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            con.setDoOutput(true);
            con.getOutputStream().write(postDataBytes);
            Reader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            StringBuilder data = new StringBuilder();
            for (int c; (c = in.read()) >= 0;) {
                data.append((char)c);
            }
            String intentData = data.toString();
            System.out.println(intentData);

            familiaLabel.setText(title);

        /*}
        else {
            JOptionPane.showMessageDialog(null, "No poden ser buits", "Error de paràmetres", JOptionPane.PLAIN_MESSAGE);
        }*/
    }
}