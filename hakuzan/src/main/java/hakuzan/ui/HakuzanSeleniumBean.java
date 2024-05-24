/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hakuzan.ui;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.openqa.selenium.chrome.ChromeDriver;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author kamba
 */
@Named(value = "hakuzanSeleniumBean")
@RequestScoped
public class HakuzanSeleniumBean {

    @Getter
    private StreamedContent file;

    @Getter
    @Setter
    private String url;
    @Getter
    @Setter
    private String problem;

    private final String TEMPLATE_FILE_PATH = "/resources/data/AtCoderTemplate.txt";
    private final String COPIED_FILE_PATH = "/resources/data/AtCoderOutput.txt";
    private final String OUTPUT_FILE_PATH = "/resources/data/AtCoderOutput.java";

    private static ServletContext context
            = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

    public HakuzanSeleniumBean() {

        String outputFileName = problem + ".java";

        // Primefaces.FileDownloadを参考
        // http://www.primefaces.org:8080/showcase/ui/file/download.xhtml?jfwid=50bd1
        file = DefaultStreamedContent.builder()
                .name("copied.txt")
                .contentType("text/plain")
                .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(OUTPUT_FILE_PATH))
                .build();
    }

    public String generateCode() throws IOException {
//        WebDriver driver = new ChromeDriver();
//        driver.get("https://atcoder.jp/contests/abc354/tasks/abc354_c");
//
//        var element1 = driver.findElement(By.id("pre-sample0"));
//        var element2 = driver.findElement(By.id("pre-sample1"));
//        var element3 = driver.findElement(By.id("pre-sample2"));
//        var element4 = driver.findElement(By.id("pre-sample3"));
//        var t4 = element4.getText();
//        driver.quit();
        Path templatePath = Paths.get(context.getRealPath(TEMPLATE_FILE_PATH));
        String text = Files.readString(templatePath);
        text = text.replaceAll("XXX", "ABC355");

        return text;

    }

    public void outputCode() throws IOException {

        // 書き込み文字列の生成
        String text = generateCode();

        // 出力ファイルへの書き込み       
        Path path = Paths.get(context.getRealPath(OUTPUT_FILE_PATH));

        try {
            byte[] bytes = text.getBytes("UTF-8");
            Files.write(path, bytes);
        } catch (IOException e) {

            int a = 0;
            //Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
