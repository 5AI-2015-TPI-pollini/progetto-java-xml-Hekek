/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinchin.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

/**
 *
 * @author Mosta
 */
public class FXMLDocumentController implements Initializable {
    
    private static String STATUS ;
    private static String FORMATTED_ADDRESS ;
    private static String SHORT_NAME ;
    private static String LONG ;
    private static String LAT ;
    
    
    
    @FXML
    private TextField adress;
    
    @FXML
    private Label tempreatureC , weather , observationTime , location , label , wind , humidity , dewPoint , pressure , visibility , uvIndex;
    
    @FXML
    private Button ok;
    
    @FXML
    private ImageView weatherImg , loadingImg;
    
    @FXML
    private Pane inputPane , forecast;
    
    public Location City = new Location();
    
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException, MalformedURLException, 
            ParserConfigurationException, SAXException, XPathExpressionException, InterruptedException {
        if (event.getSource().equals(ok))
        {   
            
            //FADE OFF
            FadeTransition fadeTransitionOff = new FadeTransition(Duration.millis(1000), forecast);
            fadeTransitionOff.setFromValue(1.0);
            fadeTransitionOff.setToValue(0.0);
            fadeTransitionOff.play();
            
            City.setADDRESS(adress.getText());
            if (City.Status())
            {
                label.setText("ADRESS IS OK");
                if(City.getTodayForecast())
                {
                    System.out.println(City);
                    location.setText(City.getFORMATTED_ADDRESS());
                    observationTime.setText(City.getOBSERVATION_TIME());
                    weather.setText(City.getWEATHER());
                    Image image = new Image(City.getIMG());
                    weatherImg.setImage(image);
                    tempreatureC.setText(City.getTEMPRETURE_C());
                    wind.setText(City.getWIND_MPH());
                    wind.setAlignment(Pos.CENTER_RIGHT);
                    humidity.setText(City.getHUMIDITY());
                    humidity.setAlignment(Pos.CENTER_RIGHT);
                    dewPoint.setText(City.getDEW_POINT_C());
                    dewPoint.setAlignment(Pos.CENTER_RIGHT);
                    pressure.setText(City.getPRESSURE());
                    pressure.setAlignment(Pos.CENTER_RIGHT);
                    visibility.setText(City.getPRESSURE());
                    visibility.setAlignment(Pos.CENTER_RIGHT);
                    uvIndex.setText(City.getUV_INDEX());
                    uvIndex.setAlignment(Pos.CENTER_RIGHT);
                    City.getFutureForecast();
                }
                
                //FADE ON
                forecast.setVisible(true);
                FadeTransition fadeTransition 
                        = new FadeTransition(Duration.millis(500), forecast);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();
            }
            else
            {
                label.setText("ADRESS IS NOT OK");
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        forecast.setVisible(false);
        //loadingImg.setVisible(false);
        /*try {
            System.setProperty("proxySet", "true");
            System.setProperty("http.proxyHost", "192.168.0.1");
            System.setProperty("http.proxyPort", "8080");
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    
                    return new PasswordAuthentication("********","**********".toCharArray());
                }
            });
            
            URL google = new URL("http://www.google.com/");
            URLConnection con = google.openConnection();
            con.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));

// Read it ...
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
        } catch (MalformedURLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }     
}

