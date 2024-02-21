package hakuzan.ui;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

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
    
        
    public void sleep() throws InterruptedException {
        
        TimeUnit.SECONDS.sleep(10);
    }
}
