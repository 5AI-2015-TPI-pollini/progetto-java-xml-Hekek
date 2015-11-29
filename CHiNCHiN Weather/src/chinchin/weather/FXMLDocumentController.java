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
    private Label tempreatureC , weather , observationTime , location , label , wind , humidity , dewPoint , pressure , visibility 
            , c1high , c2high , c3high , c4high , c1low , c2low ,
            c3low, c4low, desc1 , desc2, desc3, desc4, uvIndex ,day1,day2,day3,day4 , wind1, wind2,wind3,wind4 , precip1,precip2,precip3,precip4;
    
    @FXML
    private Button ok;
    
    @FXML
    private ImageView weatherImg , icon1 , icon2 , icon3 , icon4;
    
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
                    if (City.getFutureForecast())
                    {
                        int i = 1;
                        while (i != 5)
                        {
                            day1.setText(City.getDays(i));
                            c1high.setText(City.getTemperatureCHigh(i)+"°");
                            c1high.setAlignment(Pos.CENTER);
                            c1low.setText(City.getTemperatureCLow(i)+"°");
                            c1low.setAlignment(Pos.CENTER);
                            image = new Image(City.getIcon(i));
                            icon1.setImage(image);
                            desc1.setText(City.getConditions(i));
                            wind1.setText(City.getWind(i));
                            wind1.setAlignment(Pos.CENTER);
                            precip1.setText(City.getPrecip(i)+"%");
                            precip1.setAlignment(Pos.CENTER);
                            i++;
                            day2.setText(City.getDays(i));
                            c2high.setText(City.getTemperatureCHigh(i)+"°");
                            c2high.setAlignment(Pos.CENTER);
                            c2low.setText(City.getTemperatureCLow(i)+"°");
                            c2low.setAlignment(Pos.CENTER);
                            image = new Image(City.getIcon(i));
                            icon2.setImage(image);
                            desc2.setText(City.getConditions(i));
                            wind2.setText(City.getWind(i));
                            wind2.setAlignment(Pos.CENTER);
                            precip2.setText(City.getPrecip(i)+"%");
                            precip2.setAlignment(Pos.CENTER);
                            i++;
                            day3.setText(City.getDays(i));
                            c3high.setText(City.getTemperatureCHigh(i)+"°");
                            c3high.setAlignment(Pos.CENTER);
                            c3low.setText(City.getTemperatureCLow(i)+"°");
                            c3low.setAlignment(Pos.CENTER);
                            image = new Image(City.getIcon(i));
                            icon3.setImage(image);
                            desc3.setText(City.getConditions(i));
                            wind3.setText(City.getWind(i));
                            wind3.setAlignment(Pos.CENTER);
                            precip3.setText(City.getPrecip(i)+"%");
                            precip3.setAlignment(Pos.CENTER);
                            i++;
                            day4.setText(City.getDays(i));
                            c4high.setText(City.getTemperatureCHigh(i)+"°");
                            c4high.setAlignment(Pos.CENTER);
                            c4low.setText(City.getTemperatureCLow(i)+"°");
                            c4low.setAlignment(Pos.CENTER);
                            image = new Image(City.getIcon(i));
                            icon4.setImage(image);
                            desc4.setText(City.getConditions(i));
                            wind4.setText(City.getWind(i));
                            wind4.setAlignment(Pos.CENTER);
                            precip4.setText(City.getPrecip(i)+"%");
                            precip4.setAlignment(Pos.CENTER);
                            i++;
                        }
                    }
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

