package hakuzan.ui;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author kamba
 */
@Named(value = "hakuzanBean")
@ViewScoped
public class hakuzanBean implements Serializable{
     
    public hakuzanBean(){
        keyword = "hakuzan";
    }
    
    public String getHoge(){
        return "hakuzan hakuzan";
    }
    
    private final String keyword;
    public String GetKeyword(){
        return keyword;
    }
    
        
    public void generateCode() throws IOException {
        
        Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
        Elements newsHeadlines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadlines) {
          String s = 
            headline.attr("title");
          String s2 = headline.absUrl("href");
          
          int a  = 0;
        }

    }
}
