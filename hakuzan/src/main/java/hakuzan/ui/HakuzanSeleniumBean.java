/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hakuzan.ui;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
    @Setter
    private String contest = "ABC";
    @Getter
    @Setter
    private String problem = "D";
    @Getter
    final private StreamedContent file;
    @Getter
    @Setter
    private String text;
    @Getter
    @Setter
    private String url;

    private final String TEMPLATE_FILE_PATH = "/resources/data/AtCoderTemplate.txt";
    private final String OUTPUT_FILE_PATH = "/resources/data/AtCoderOutput.java";

    private static ServletContext context
            = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

    public HakuzanSeleniumBean() {

        String filenName = contest + problem;

        // Primefaces.FileDownloadを参考
        // http://www.primefaces.org:8080/showcase/ui/file/download.xhtml?jfwid=50bd1
        file = DefaultStreamedContent.builder()
                .name("output.txt")
                .contentType("text/plain")
                .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(OUTPUT_FILE_PATH))
                .build();
    }

    public String generateCode() throws IOException {

        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);

        try {
            // テンプレートを読み込む
            Path templatePath = Paths.get(context.getRealPath(TEMPLATE_FILE_PATH));
            text = Files.readString(templatePath);

            // クラス名の置換
            String problemName = url.substring(url.lastIndexOf("/") + 1);
            problemName = problemName.replaceAll("_", "");
            problemName = problemName.toUpperCase();
            text = text.replaceAll("XXX", problemName);

            driver.get(url);

            var input1 = driver.findElement(By.id("pre-sample0")).getText();
            var output1 = driver.findElement(By.id("pre-sample1")).getText();
            text = text.replaceAll("IN1", input1);
            text = text.replaceAll("OUT1", output1);

            var input2 = driver.findElement(By.id("pre-sample2")).getText();
            var output2 = driver.findElement(By.id("pre-sample3")).getText();
            text = text.replaceAll("IN2", input2);
            text = text.replaceAll("OUT2", output2);

            var input3 = driver.findElement(By.id("pre-sample4")).getText();
            var output3 = driver.findElement(By.id("pre-sample5")).getText();
            // テストケース3がない場合もテンプレートに埋め込んだキーワードを削除する意味合いで置換する
            text = text.replaceAll("IN3", input3);
            text = text.replaceAll("OUT3", output3);

            var input4 = driver.findElement(By.id("pre-sample6")).getText();
            var output4 = driver.findElement(By.id("pre-sample7")).getText();
            // テストケース3がない場合もテンプレートに埋め込んだキーワードを削除する意味合いで置換する
            text = text.replaceAll("IN4", input4);
            text = text.replaceAll("OUT4", output4);

        } catch (NoSuchElementException e) {
            // 古いページでテストケース3の要素がない場合もあるので、エラーとはしない
        } catch (Exception e) {
            error();
        } finally {
            driver.quit();
        }
        return text;
    }

    public void error() {
        String msg = "不正なコンテストが指定されています。";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
    }

    public void outputCode() throws IOException {

        // 出力ファイルと同名ファイルが存在する場合は上書きする
        String downdload = "C:\\Users\\kamba\\Downloads\\";
        String from = "output.txt";
        File f = new File(downdload + from);
        if (f.exists()) {
            f.delete();
        }

        // 書き込み文字列の生成
        text = "";
        text = generateCode();

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

    public void rename() throws IOException {
        // https://tech.kurojica.com/archives/5652/
        //String batchPath = "C:\\Users\\kamba\\Downloads\\test\\rename2.bat";
        String batchPath = "C:\\Users\\kamba\\Downloads\\test\\rename3.bat";
        String downdload = "C:\\Users\\kamba\\Downloads\\";
        String from = "output.txt.crdownload";
        String to = contest + problem + ".java";

        // すでに同名ファイルが存在する場合は上書きする
        File f = new File(downdload + to);
        if (f.exists()) {
            f.delete();
        }

        batchPath += " " + downdload + from + " " + to;

        int processCompleted = -1;

        try {
            Process runtimeProcess = Runtime.getRuntime().exec(batchPath);

//            processCompleted = runtimeProcess.waitFor();
//            BufferedReader br = new BufferedReader(new InputStreamReader(
//                    runtimeProcess.getErrorStream()));
//            String line;
//            while ((line = br.readLine()) != null) {
//                System.out.printf(line);
//            }
            int bb = 0;
        } catch (Exception ex) {
            int a = 0;
        }
    }
}
