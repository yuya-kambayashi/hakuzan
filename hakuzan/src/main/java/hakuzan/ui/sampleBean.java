
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kamba
 */
@Named(value = "sampleBean")
@ViewScoped
public class sampleBean implements Serializable{

    public sampleBean(){
        keyword = "hogehoge";
    }
    
    public String getHoge(){
        return "Hoge";
    }
    
    private final String keyword;
    public String GetKeyword(){
        return keyword;
    }
    
        
    public void sleep() throws InterruptedException {
        
        TimeUnit.SECONDS.sleep(10);
    }
    
}
