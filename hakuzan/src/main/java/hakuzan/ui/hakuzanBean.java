package hakuzan.ui;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
@Named(value = "hakuzanBean")
@RequestScoped
public class hakuzanBean implements Serializable{
     
    private StreamedContent file;
    
    private static ServletContext context 
        = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

    public hakuzanBean(){
        keyword = "hakuzan";
        
        String fpath = "/resources/data/LCXXXTest.java";
       
        file = DefaultStreamedContent.builder()
                .name("LCXXXTest_mod.java")
                .contentType("text/plain")
                .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(fpath))
                .build();
        int a = 0;
    }
    
    public String getHoge(){
        return "hakuzan hakuzan";
    }
    
    private final String keyword;
    public String GetKeyword(){
        return keyword;
    }
    
        
    public void generateCodeJsoup() throws IOException {
        
        Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
          String s = 
            headline.attr("title");
          String s2 = headline.absUrl("href");
          
          int a  = 0;
        }

    }
    
    public String generateCode() throws IOException {
        
        WebDriver driver = new ChromeDriver();
        driver.get("https://leetcode.com/problems/two-sum/");
        
        String t = driver.getTitle();
        

        List<WebElement> examples = driver.findElements(By.className("view-lines"));
        for(var example : examples){
            
            String et = example.getText();

            int a = 0;

        }


        driver.quit();
        
        
     
        return "aaa";

    }
    public void outputCode() throws IOException {

        String fpath = "/resources/data/LCXXXTest.java";
        
        String text = "aaaaa";

        Path path = Paths.get(context.getRealPath(fpath));
        
        byte[] bytes;
        try {
            bytes = text.getBytes("UTF-8");
            Files.write(path, bytes);
        } catch (IOException e) {
            //Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public StreamedContent getFile() {
        return file;
    }
}
