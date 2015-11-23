/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinchin.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Mosta
 */
public class Location {
    private static boolean STATUS ;
    private static String FORMATTED_ADDRESS ;
    private static String SHORT_NAME ;
    private static String ADDRESS ;
    private static String LONG ;
    private static String LAT ;
    private static String OBSERVATION_TIME ;
    private static String WEATHER ;
    private static String TEMPRETURE_C ;
    private static String TEMPRETURE_F ;
    private static String WIND_MPH ;
    private static String HUMIDITY ;
    private static String DEW_POINT_F ;
    private static String DEW_POINT_C ;
    private static String PRESSURE ;
    private static String VISIBILITY ;
    private static String UV_INDEX ;

    public Location(String adress) {
        this.ADDRESS = adress;
    }
    public Location() {
    }

    public static void setADDRESS(String ADDRESS) {
        Location.ADDRESS = ADDRESS;
    }
    
    
    public boolean Status() throws MalformedURLException, IOException, ParserConfigurationException, SAXException, XPathExpressionException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("http://maps.googleapis.com/maps/api/geocode/xml?address="+ADDRESS);
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile("/GeocodeResponse/status");
        String s = (String) expr.evaluate(doc, XPathConstants.STRING);
        if (s.equals("OK"))
        {
            STATUS = true ;
            expr = xpath.compile("/GeocodeResponse/result/geometry/location/lat");
            LAT = (String) expr.evaluate(doc, XPathConstants.STRING);
            expr = xpath.compile("/GeocodeResponse/result/geometry/location/lng");
            LONG = (String) expr.evaluate(doc, XPathConstants.STRING);
            expr = xpath.compile("/GeocodeResponse/result/formatted_address");
            FORMATTED_ADDRESS = (String) expr.evaluate(doc, XPathConstants.STRING);
            return true ;
        }
        else
        {
            STATUS = false ;
            return false ; 
        } 
    }
    public boolean getForecast() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("http://api.wunderground.com/api/2fe535a12fd3638f/forecast/conditions/q/"+ LAT +","+ LONG +".xml");
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr = xpath.compile("/response/current_observation/observation_time");
        OBSERVATION_TIME = (String) expr.evaluate(doc, XPathConstants.STRING);
        expr = xpath.compile("/response/current_observation/temp_f");
        TEMPRETURE_F = (String) expr.evaluate(doc, XPathConstants.STRING);
        expr = xpath.compile("/response/current_observation/temp_c");
        TEMPRETURE_C = (String) expr.evaluate(doc, XPathConstants.STRING);
        expr = xpath.compile("/response/current_observation/weather");
        WEATHER = (String) expr.evaluate(doc, XPathConstants.STRING);
        expr = xpath.compile("/response/current_observation/wind_mph");
        WIND_MPH = (String) expr.evaluate(doc, XPathConstants.STRING);
        expr = xpath.compile("/response/current_observation/relative_humidity");
        HUMIDITY = (String) expr.evaluate(doc, XPathConstants.STRING);
        expr = xpath.compile("/response/current_observation/dewpoint_f");
        DEW_POINT_F = (String) expr.evaluate(doc, XPathConstants.STRING);
        expr = xpath.compile("/response/current_observation/dewpoint_c");
        DEW_POINT_C = (String) expr.evaluate(doc, XPathConstants.STRING);
        expr = xpath.compile("/response/current_observation/pressure_in");
        PRESSURE = (String) expr.evaluate(doc, XPathConstants.STRING);
        expr = xpath.compile("/response/current_observation/visibility_mi");
        VISIBILITY = (String) expr.evaluate(doc, XPathConstants.STRING);
        expr = xpath.compile("/response/current_observation/UV");
        UV_INDEX = (String) expr.evaluate(doc, XPathConstants.STRING);
        return true;
    }

    @Override
    public String toString() {
        return ("Location:"+ FORMATTED_ADDRESS +"Last update: "+ OBSERVATION_TIME+ " Tempo: "+ WEATHER +" Temperatura in celsius: "+ TEMPRETURE_C
                        +" Temperatura in fahrenheit: "+TEMPRETURE_F );
    }
    
}