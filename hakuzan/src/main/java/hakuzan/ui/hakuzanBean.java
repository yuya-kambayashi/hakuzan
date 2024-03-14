package hakuzan.ui;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
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
public class HakuzanBean implements Serializable{
     
    @Getter
    private StreamedContent file;
    
    @Getter
    @Setter
    private String file2;

    private final String TEMPLATE_FILE_PATH = "/resources/data/LCXXXTest.java";
    
    private static ServletContext context 
        = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

    public HakuzanBean(){
        file2 = "Hoge";
         
        // Primefaces.FileDownloadを参考
        // http://www.primefaces.org:8080/showcase/ui/file/download.xhtml?jfwid=50bd1
        file = DefaultStreamedContent.builder()
                .name("LCXXXTest_mod.java")
                .contentType("text/plain")
                .stream(() -> FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(TEMPLATE_FILE_PATH))
                .build();
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
        
        
     
        return file2;

    }
    public void outputCode() throws IOException {
        
        // 書き込み文字列の生成
        String text =  generateCode();
        
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
