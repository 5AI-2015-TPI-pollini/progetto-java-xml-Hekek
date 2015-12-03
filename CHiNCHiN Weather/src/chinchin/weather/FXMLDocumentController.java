/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinchin.weather;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

/**
 *
 * @author Mosta
 */
public class FXMLDocumentController implements Initializable {
    
    private static String IP;
    private static String PORT;
    private static String USER;
    private static String PW;
    private static boolean proxyButton = false;
    
    
    
    @FXML
    private TextField adress , ipField , portField , pwField , userField ;
    
    @FXML
    private Label tempreatureC , weather , observationTime , location , label , wind ,
            humidity , dewPoint , pressure , visibility ,
            c1high , c2high , c3high , c4high , 
            c1low , c2low , c3low , c4low , 
            desc1 , desc2 , desc3 , desc4 , uvIndex ,
            day1 , day2 , day3 , day4 , 
            wind1 , wind2 ,wind3 , wind4 , 
            precip1 , precip2 , precip3 , precip4 ;
  
    @FXML
    private Button ok , settings;
    
    @FXML
    private ImageView weatherImg , icon1 , icon2 , icon3 , icon4;
    
    @FXML
    private Pane inputPane , forecast , window;
    
    /**
     * Create a new location to use 
     */
    public Location city = new Location();
    /**
     * Create a new connection to use functions of the class
     */
    public Connection connection = new Connection();
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException, MalformedURLException, 
            ParserConfigurationException, SAXException, XPathExpressionException, InterruptedException {
        if (event.getSource().equals(settings))
        {
            if (!proxyButton)
            {
                proxyButton = true;
                ipField.setVisible(true);
                pwField.setVisible(true);
                portField.setVisible(true);
                userField.setVisible(true);
            }
            else 
            {
                proxyButton = false;
                IP = ipField.getText();
                PORT = portField.getText();
                USER = userField.getText();
                PW = pwField.getText();
                connection.setProxy(IP, PORT, USER, PW);
                ipField.setVisible(false);
                pwField.setVisible(false);
                portField.setVisible(false);
                userField.setVisible(false);
            }
            
        }
        if (event.getSource().equals(ok))
        {   
            fadeOff(forecast);
            city.setADDRESS(adress.getText());
            if (city.Status())
            {
                label.setText("ADRESS IS OK");
                if(city.getTodayForecast())
                {
                    setTodayGuiElements();
                    if (city.getFutureForecast())
                    {
                        setWeekGuiElements();
                    }
                }
                //FADE ON
                forecast.setVisible(true);
                fadeOn(forecast);
            }
            else 
            {
                label.setText("ADRESS IS NOT OK OR NO INPUT");
            }
        }
    }
    
    /**
     * Function to use the fade off effect in GUI 
     * @param object could be a label , image , text field and others 
     */
    public void fadeOff(Node object){
        FadeTransition fadeTransitionOff = new FadeTransition(Duration.millis(1000), object);
        fadeTransitionOff.setFromValue(1.0);
        fadeTransitionOff.setToValue(0.0);
        fadeTransitionOff.play();
    }
    
    /**
     * Function to use the fade oon effect in GUI 
     * @param object could be a label , image , text field and others 
     */
    public void fadeOn(Node object){
        forecast.setVisible(true);
        FadeTransition fadeTransition 
                = new FadeTransition(Duration.millis(1000), forecast);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }
    
    /**
     * Function that set the GUI elements for weekly forecast getting info from the location class
     */
    public void setWeekGuiElements(){
        int i = 1;
        Image image ;
        while (i != 5)
        {
            day1.setText(city.getDays(i));
            c1high.setText(city.getTemperatureCHigh(i)+"°");
            c1high.setAlignment(Pos.CENTER);
            c1low.setText(city.getTemperatureCLow(i)+"°");
            c1low.setAlignment(Pos.CENTER);
            image = new Image(city.getIcon(i));
            icon1.setImage(image);
            desc1.setText(city.getConditions(i));
            wind1.setText(city.getWind(i)+" KPH");
            wind1.setAlignment(Pos.CENTER);
            precip1.setText(city.getPrecip(i)+"%");
            precip1.setAlignment(Pos.CENTER);
            i++;
            day2.setText(city.getDays(i));
            c2high.setText(city.getTemperatureCHigh(i)+"°");
            c2high.setAlignment(Pos.CENTER);
            c2low.setText(city.getTemperatureCLow(i)+"°");
            c2low.setAlignment(Pos.CENTER);
            image = new Image(city.getIcon(i));
            icon2.setImage(image);
            desc2.setText(city.getConditions(i));
            wind2.setText(city.getWind(i)+" KPH");
            wind2.setAlignment(Pos.CENTER);
            precip2.setText(city.getPrecip(i)+"%");
            precip2.setAlignment(Pos.CENTER);
            i++;
            day3.setText(city.getDays(i));
            c3high.setText(city.getTemperatureCHigh(i)+"°");
            c3high.setAlignment(Pos.CENTER);
            c3low.setText(city.getTemperatureCLow(i)+"°");
            c3low.setAlignment(Pos.CENTER);
            image = new Image(city.getIcon(i));
            icon3.setImage(image);
            desc3.setText(city.getConditions(i));
            wind3.setText(city.getWind(i)+" KPH");
            wind3.setAlignment(Pos.CENTER);
            precip3.setText(city.getPrecip(i)+"%");
            precip3.setAlignment(Pos.CENTER);
            i++;
            day4.setText(city.getDays(i));
            c4high.setText(city.getTemperatureCHigh(i)+"°");
            c4high.setAlignment(Pos.CENTER);
            c4low.setText(city.getTemperatureCLow(i)+"°");
            c4low.setAlignment(Pos.CENTER);
            image = new Image(city.getIcon(i));
            icon4.setImage(image);
            desc4.setText(city.getConditions(i));
            wind4.setText(city.getWind(i)+" KPH");
            wind4.setAlignment(Pos.CENTER);
            precip4.setText(city.getPrecip(i)+"%");
            precip4.setAlignment(Pos.CENTER);
            i++;
        }
    }
    
    /**
     * Function that set the GUI elements for Today forecast getting info from the location class
     */
    public void setTodayGuiElements(){
        System.out.println(city);
        location.setText(city.getFORMATTED_ADDRESS());
        observationTime.setText(city.getOBSERVATION_TIME());
        weather.setText(city.getWEATHER());
        Image image = new Image(city.getIMG());
        weatherImg.setImage(image);
        tempreatureC.setText(city.getTEMPRETURE_C());
        wind.setText(city.getWIND_MPH()+" MPH");
        wind.setAlignment(Pos.CENTER_RIGHT);
        humidity.setText(city.getHUMIDITY());
        humidity.setAlignment(Pos.CENTER_RIGHT);
        dewPoint.setText(city.getDEW_POINT_C()+"°");
        dewPoint.setAlignment(Pos.CENTER_RIGHT);
        pressure.setText(city.getPRESSURE());
        pressure.setAlignment(Pos.CENTER_RIGHT);
        visibility.setText(city.getVISIBILITY());
        visibility.setAlignment(Pos.CENTER_RIGHT);
        uvIndex.setText(city.getUV_INDEX());
        uvIndex.setAlignment(Pos.CENTER_RIGHT);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        window.setStyle("-fx-background-image: url('/Images/background.jpeg'); -fx-background-repeat: stretch; -fx-background-size: 900 600;\n" +
"    -fx-background-position: center center; ");
        forecast.setVisible(false);
        ipField.setVisible(false);
        pwField.setVisible(false);
        portField.setVisible(false);
        userField.setVisible(false);
        settings.setGraphic(new ImageView("/Images/settings2.png"));
    }     
}

