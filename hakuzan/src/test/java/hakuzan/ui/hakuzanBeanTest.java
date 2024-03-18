/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hakuzan.ui;

import jakarta.ejb.embeddable.EJBContainer;
import jakarta.inject.Inject;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author kamba
 */
public class HakuzanBeanTest {
    
    @Inject
    private HakuzanBean hakuzanBean;
    
    @Disabled
    @Test
    void test_GenerateCode_twosum(){
       
        assertThat(hakuzanBean).isNotNull();
        
        try{
            var actual = hakuzanBean.generateCode();
            var expected = "aaa";

            assertThat(actual).isEqualTo(expected);
        }
        catch(Exception ex){
            int a = 0;
        }

    }
    @Test
    @Disabled
    void test_GenerateCode(){
       
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/hakuzan");
        
        var element = driver.findElement(By.id("frm:btnOutputCode"));
                
        assertThat(element.getText()).isEqualTo("Output Code");

        //element.click();
        
        
        driver.quit();

    }
}
