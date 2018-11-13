import com.google.gson.Gson;
import com.jcraft.jsch.*;
import com.jfoenix.controls.JFXComboBox;
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

public class ProductUploader extends Component {

    private Design[] designs;
    private ProductType[] productTypes;

    private Stage primaryStage;
    private TextField productTitle;
    private Button selectImage;
    private TextField productDescription;
    private File productImage;
    private Button uploadProduct;
    private Label productLabel;
    private JFXComboBox designCombobox;
    private JFXComboBox productTypeCombobox;
    Button returnButton;

    public ProductUploader(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/ProductUploader.fxml"));
        primaryStage.setTitle("Holosintesis Uploader");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        primaryStage.show();

        productTitle = (TextField) primaryStage.getScene().lookup("#productTitle");
        selectImage = (Button) primaryStage.getScene().lookup("#selectImage");
        productDescription = (TextField) primaryStage.getScene().lookup("#productDescription");
        returnButton = (Button) primaryStage.getScene().lookup("#returnButton");
        uploadProduct = (Button) primaryStage.getScene().lookup("#uploadProduct");
        productLabel = (Label) primaryStage.getScene().lookup("#productLabel");
        designCombobox = (JFXComboBox) primaryStage.getScene().lookup("#designCombobox");
        productTypeCombobox = (JFXComboBox) primaryStage.getScene().lookup("#productTypeCombobox");

        getDesigns();
        getProductType();
        selectImage.setOnMouseClicked(e -> {
            getImage();
        });
        uploadProduct.setOnMouseClicked(e-> {
            try {
                pushProduct();
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
        for(Design design : designs) {
            designCombobox.getItems().add(design.getTitle_design());
        }
        for(ProductType productType : productTypes) {
            productTypeCombobox.getItems().add(productType.getTitle_producttype());
        }
    }

    private void returnPressed() throws IOException {
        ProductMenu ptm = new ProductMenu(primaryStage);
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

    private void getImage() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fc.showOpenDialog(this);
        File selectedFile = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fc.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            productImage = fc.getSelectedFile();
        }
    }

    private void pushProduct() throws IOException, JSchException {
       // if(!productTitle.getText().isEmpty() && !productDescription.getText().isEmpty() && !designPatologies.getText().isEmpty() && productImage == null) {
            pushImage();
            String title = productTitle.getText();
            String description = productDescription.getText();
            String imageURL = "http://holosintesis.ddns.net/DataBase/Product/" + productImage.getName();
            String design_title = designCombobox.getSelectionModel().getSelectedItem().toString();
            String producttype_title = productTypeCombobox.getSelectionModel().getSelectedItem().toString();

            Map<String,Object> params = new LinkedHashMap<>();
            params.put("title_product",title);
            params.put("image_product",imageURL);
            params.put("description_product",description);
            params.put("title_design",design_title);
            params.put("title_producttype",producttype_title);

            StringBuilder tokenUri=new StringBuilder("title_product=");
            tokenUri.append(URLEncoder.encode(title,"UTF-8"));
            tokenUri.append("&image_product=");
            tokenUri.append(URLEncoder.encode(imageURL,"UTF-8"));
            tokenUri.append("&description_product=");
            tokenUri.append(URLEncoder.encode(description,"UTF-8"));
            tokenUri.append("&title_design=");
            tokenUri.append(URLEncoder.encode(design_title,"UTF-8"));
            tokenUri.append("&title_producttype=");
            tokenUri.append(URLEncoder.encode(producttype_title,"UTF-8"));

            String url = "http://holosintesis.ddns.net:3000/product";
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

            productLabel.setText(title);

        /*}
        else {
            JOptionPane.showMessageDialog(null, "No poden ser buits", "Error de paràmetres", JOptionPane.PLAIN_MESSAGE);
        }*/
    }

    private  void pushImage() throws JSchException, IOException {
        File selectedFile = productImage;
        String user = "holosintesisserver";
        String host = "holosintesis.ddns.net";
        String rfile = "/var/www/html/DataBase/Product/" + selectedFile.getName();

        FileInputStream fis=null;

        JSch jsch = new JSch();
        Session session = jsch.getSession(user, host, 22);

        UserInfo ui = new MyUserInfo();
        session.setUserInfo(ui);
        session.connect();

        boolean ptimestamp = true;

        // exec 'scp -t rfile' remotely
        String command = "scp " + (ptimestamp ? "-p" : "") + " -t " + rfile;
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);

        // get I/O streams for remote scp
        OutputStream out = channel.getOutputStream();
        InputStream in = channel.getInputStream();

        channel.connect();

        if (checkAck(in) != 0) {
            System.exit(0);
        }

        if (ptimestamp) {
            command = "T" + (selectedFile.lastModified() / 1000) + " 0";
            // The access time should be sent here,
            // but it is not accessible with JavaAPI ;-<
            command += (" " + (selectedFile.lastModified() / 1000) + " 0\n");
            out.write(command.getBytes());
            out.flush();
            if (checkAck(in) != 0) {
                System.exit(0);
            }
        }

        // send "C0644 filesize filename", where filename should not include '/'
        long filesize = selectedFile.length();
        command = "C0644 " + filesize + " ";
        command += selectedFile;
        command += "\n";
        out.write(command.getBytes());
        out.flush();
        if (checkAck(in) != 0) {
            System.exit(0);
        }

        // send a content of lfile
        fis = new FileInputStream(selectedFile);
        byte[] buf = new byte[1024];
        while (true) {
            int len = fis.read(buf, 0, buf.length);
            if (len <= 0) break;
            out.write(buf, 0, len); //out.flush();
        }
        fis.close();
        fis = null;
        // send '\0'
        buf[0] = 0;
        out.write(buf, 0, 1);
        out.flush();
        if (checkAck(in) != 0) {
            System.exit(0);
        }
        out.close();

        channel.disconnect();
        session.disconnect();

    }

    static int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //          -1
        if (b == 0) return b;
        if (b == -1) return b;

        if (b == 1 || b == 2) {
            StringBuffer sb = new StringBuffer();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            }
            while (c != '\n');
            if (b == 1) { // error
                System.out.print(sb.toString());
            }
            if (b == 2) { // fatal error
                System.out.print(sb.toString());
            }
        }
        return b;
    }

    public static class MyUserInfo implements UserInfo, UIKeyboardInteractive {
        public String getPassword() {
            return passwd;
        }

        public boolean promptYesNo(String str) {
            Object[] options = {"yes", "no"};
            int foo = JOptionPane.showOptionDialog(null,
                    str,
                    "Warning",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null, options, options[0]);
            return foo == 0;
        }

        String passwd;
        JTextField passwordField = (JTextField) new JPasswordField(20);

        public String getPassphrase() {
            return null;
        }

        public boolean promptPassphrase(String message) {
            return true;
        }

        public boolean promptPassword(String message) {
            Object[] ob = {passwordField};
            int result =
                    JOptionPane.showConfirmDialog(null, ob, message,
                            JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                passwd = passwordField.getText();
                return true;
            } else {
                return false;
            }
        }

        public void showMessage(String message) {
            JOptionPane.showMessageDialog(null, message);
        }

        final GridBagConstraints gbc =
                new GridBagConstraints(0, 0, 1, 1, 1, 1,
                        GridBagConstraints.NORTHWEST,
                        GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 0), 0, 0);
        private Container panel;

        public String[] promptKeyboardInteractive(String destination,
                                                  String name,
                                                  String instruction,
                                                  String[] prompt,
                                                  boolean[] echo) {
            panel = new JPanel();
            panel.setLayout(new GridBagLayout());

            gbc.weightx = 1.0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.gridx = 0;
            panel.add(new JLabel(instruction), gbc);
            gbc.gridy++;

            gbc.gridwidth = GridBagConstraints.RELATIVE;

            JTextField[] texts = new JTextField[prompt.length];
            for (int i = 0; i < prompt.length; i++) {
                gbc.fill = GridBagConstraints.NONE;
                gbc.gridx = 0;
                gbc.weightx = 1;
                panel.add(new JLabel(prompt[i]), gbc);

                gbc.gridx = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weighty = 1;
                if (echo[i]) {
                    texts[i] = new JTextField(20);
                } else {
                    texts[i] = new JPasswordField(20);
                }
                panel.add(texts[i], gbc);
                gbc.gridy++;
            }

            if (JOptionPane.showConfirmDialog(null, panel,
                    destination + ": " + name,
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE)
                    == JOptionPane.OK_OPTION) {
                String[] response = new String[prompt.length];
                for (int i = 0; i < prompt.length; i++) {
                    response[i] = texts[i].getText();
                }
                return response;
            } else {
                return null;  // cancel
            }
        }
    }
}