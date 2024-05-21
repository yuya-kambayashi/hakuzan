package hakuzan.ui;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.json.Json;
import static jakarta.json.Json.createObjectBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.servlet.ServletContext;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author kamba
 */
@Named(value = "hakuzanGptBean")
@RequestScoped
public class HakuzanGptBean implements Serializable {

    @Getter
    private StreamedContent file;

    @Getter
    @Setter
    private String url;

    private final String TEMPLATE_FILE_PATH = "/resources/data/LCXXXTest.java";

    private static ServletContext context
            = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

    public HakuzanGptBean() {

        // Primefaces.FileDownloadを参考
        // http://www.primefaces.org:8080/showcase/ui/file/download.xhtml?jfwid=50bd1
        file = DefaultStreamedContent.builder()
                .name("LCXXXTest_mod.java")
                .contentType("text/plain")
                .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(TEMPLATE_FILE_PATH))
                .build();
    }

    public String generateCode() throws IOException {

        // urlよりソースコードの各要素を取得する
        String fileName = "/WEB-INF/classes/gpt.properties";
        Path path = Paths.get(context.getRealPath(fileName));

        Properties conf = new Properties();
        try {

            conf.load(new FileInputStream(path.toFile()));
        } catch (IOException e) {
            System.err.println("Cannot open " + fileName + ".");
            e.printStackTrace();
            System.exit(-1);  // プログラム終了
        }

        String apiKey = conf.getProperty("apiKey");
        String endpoint = conf.getProperty("endpoint");

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(endpoint);
            httpPost.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);
            httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            JsonObject requestBody = Json.createObjectBuilder()
                    .add("model", "gpt-3.5-turbo-0125")
                    .add("response_format",
                            Json.createObjectBuilder()
                                    .add("type", "json_object"))
                    .add("messages",
                            Json.createArrayBuilder().add(
                                    Json.createObjectBuilder().add("role", "system").add("content", "You are a helpful assistant designed to output JSON."))
                                    .add(Json.createObjectBuilder().add("role", "user").add("content", "Please give me the title of the following link and the method name, arguments, return value and testcases  listed in JSON format. https://leetcode.com/problems/two-sum/"))
                    ).build();

            StringEntity entity = new StringEntity(requestBody.toString());
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    String responseBody = EntityUtils.toString(responseEntity);
                    System.out.println(responseBody);

                    JsonReader reader = Json.createReaderFactory(null).createReader(new StringReader(responseBody));
                    JsonObject jsonObject = reader.readObject();

                    String content = jsonObject.getJsonArray("choices")
                            .getJsonObject(0)
                            .getJsonObject("message")
                            .getString("content");

                    JsonObject innerJsonObject = Json.createReader(new StringReader(content)).readObject();
                    String headCoach = innerJsonObject.getString("head_coach");

                    System.out.println("Head Coach: " + headCoach);

                    return headCoach;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return url;

    }

    public void outputCode() throws IOException {

        // 書き込み文字列の生成
        String text = generateCode();

        // 出力ファイルへの書き込み       
        Path path = Paths.get(context.getRealPath(TEMPLATE_FILE_PATH));

        byte[] bytes;
        try {
            bytes = text.getBytes("UTF-8");
            Files.write(path, bytes);
        } catch (IOException e) {
            //Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
