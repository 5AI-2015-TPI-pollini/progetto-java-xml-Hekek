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
import javafx.scene.image.ImageView;
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
    private static String ICON;
    private static String icon[] = new String[5] ;
    private static String conditions[] = new String[5] ;
    private static String temperatureCHigh[] = new String[5] ;
    private static String temperatureCLow[] = new String[5] ;
    private static String days[] = new String[5] ;
    private static String precip[] = new String[5] ;
    private static String wind[] = new String[5] ;

    
    
    /**
     * constructor for the location 
     * @param adress string with the address/location/city/CAP code
     */
    public Location(String adress) {
        this.ADDRESS = adress;
    }
    /**
     * Empty constructor for the location
     */
    public Location() {
    }
    /**
     * Assign a new address for location 
     * @param ADDRESS 
     */
    public static void setADDRESS(String ADDRESS) {
        Location.ADDRESS = ADDRESS;
    }
    
    /**
     * This function check if the address input is valid or not using Google Geo Finder 
     * @return true if the address is valid , false if it's not
     * @throws MalformedURLException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException 
     */
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
    
    /** Use the LAT AND LONG param. to get weather deatails from Wunderground XML 
     * 
     * @return true if when finished getting info
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws XPathExpressionException 
     */
    public boolean getTodayForecast() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
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
        expr = xpath.compile("/response/current_observation/icon_url");
        ICON = (String) expr.evaluate(doc, XPathConstants.STRING);
        return true;
    }
    
    /** 
     * Get the forecast for the next 4 days starting from the current day
     * @return true when finished getting info
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws XPathExpressionException 
     */
    public boolean getFutureForecast() throws ParserConfigurationException, SAXException, IOException, XPathExpressionException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("http://api.wunderground.com/api/2fe535a12fd3638f/forecast/conditions/q/"+ LAT +","+ LONG +".xml");
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        for ( int i = 1 ; i < 5 ; i++ )
        {
            XPathExpression expr = xpath.compile("/response/forecast/simpleforecast/forecastdays/forecastday["+i+"]/date/weekday");
            days[i]  = (String) expr.evaluate(doc, XPathConstants.STRING);
            expr = xpath.compile("/response/forecast/simpleforecast/forecastdays/forecastday["+i+"]/high/celsius");
            temperatureCHigh[i] = (String) expr.evaluate(doc, XPathConstants.STRING);
            expr = xpath.compile("/response/forecast/simpleforecast/forecastdays/forecastday["+i+"]/low/celsius");
            temperatureCLow[i] = (String) expr.evaluate(doc, XPathConstants.STRING);
            expr = xpath.compile("/response/forecast/simpleforecast/forecastdays/forecastday["+i+"]/icon_url");
            icon[i] = (String) expr.evaluate(doc, XPathConstants.STRING);
            expr = xpath.compile("/response/forecast/simpleforecast/forecastdays/forecastday["+i+"]/conditions");
            conditions[i] = (String) expr.evaluate(doc, XPathConstants.STRING);
            expr = xpath.compile("/response/forecast/simpleforecast/forecastdays/forecastday["+i+"]/qpf_allday/mm");
            precip[i] = (String) expr.evaluate(doc, XPathConstants.STRING);
            expr = xpath.compile("/response/forecast/simpleforecast/forecastdays/forecastday["+i+"]/avewind/kph");
            wind[i] = (String) expr.evaluate(doc, XPathConstants.STRING);
        }
        return true;
    }

    /**
     *
     * @param n Day number
     * @return Weekly N day precip
     */
    public static String getPrecip(int n) {
        return precip[n];
    }

    /**
     *
     * @param n Day number
     * @return
     */
    public static String getWind(int n) {
        return wind[n];
    }

    /**
     *
     * @return image url
     */
    public static String getIMG() {
        return ICON;
    }

    /**
     *
     * @return The correct normal address
     */
    public static String getFORMATTED_ADDRESS() {
        return FORMATTED_ADDRESS;
    }

    /**
     *
     * @return last observation time of the forecast
     */
    public static String getOBSERVATION_TIME() {
        return OBSERVATION_TIME;
    }

    /**
     *
     * @return image url
     */
    public static String getICON() {
        return ICON;
    }

    /**
     *
     * @param n Day number
     * @return
     */
    public static String getIcon(int n) {
        return icon[n];
    }

    /**
     *
     * @param n Day number
     * @return
     */
    public static String getConditions(int n) {
        return conditions[n];
    }

    /**
     *
     * @param n Day number
     * @return
     */
    public static String getTemperatureCHigh(int n) {
        return temperatureCHigh[n];
    }

    /**
     *
     * @param n Day number
     * @return
     */
    public static String getTemperatureCLow(int n) {
        return temperatureCLow[n];
    }

    /**
     *
     * @param n Day number
     * @return
     */
    public static String getDays(int n) {
        return days[n];
    }

    /**
     *
     * @return if the address is valid or not
     */
    public static boolean isSTATUS() {
        return STATUS;
    }

    /**
     *
     * @return Correct short address
     */
    public static String getSHORT_NAME() {
        return SHORT_NAME;
    }

    /**
     *
     * @return address
     */
    public static String getADDRESS() {
        return ADDRESS;
    }

    /**
     *
     * @return Longitude
     */
    public static String getLONG() {
        return LONG;
    }

    /**
     *
     * @return Latitude
     */
    public static String getLAT() {
        return LAT;
    }

    /**
     *
     * @return C Tempreture
     */
    public static String getTEMPRETURE_C() {
        return TEMPRETURE_C;
    }

    /**
     *
     * @return F Tempreture
     */
    public static String getTEMPRETURE_F() {
        return TEMPRETURE_F;
    }
    
    /**
     * 
     * @return String with wind speed MP/h
     */
    public static String getWIND_MPH() {
        return WIND_MPH;
    }

    /**
     *
     * @return String with Humidity value
     */
    public static String getHUMIDITY() {
        return HUMIDITY;
    }

    /**
     *
     * @return String with F Dew Point
     */
    public static String getDEW_POINT_F() {
        return DEW_POINT_F;
    }

    /**
     *
     * @return String with C Dew Point
     */
    public static String getDEW_POINT_C() {
        return DEW_POINT_C;
    }

    /**
     * 
     * @return String with Pressure value
     */
    public static String getPRESSURE() {
        return PRESSURE;
    }

    /**
     * 
     * @return String with Visibility Param.
     */
    public static String getVISIBILITY() {
        return VISIBILITY;
    }
    
    /**
     * 
     * @return String with Ultraviolet index
     */
    public static String getUV_INDEX() {
        return UV_INDEX;
    }
    
    /**
     * 
     * @return String with weather
     */
    public static String getWEATHER() {
        return WEATHER;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return ("Location:"+ FORMATTED_ADDRESS +"Last update: "+ OBSERVATION_TIME+ " Tempo: "+ WEATHER +" Temperatura in celsius: "+ TEMPRETURE_C
                        +" Temperatura in fahrenheit: "+TEMPRETURE_F );
    }
    
}
