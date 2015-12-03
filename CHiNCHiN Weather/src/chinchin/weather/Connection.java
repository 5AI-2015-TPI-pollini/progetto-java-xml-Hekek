/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinchin.weather;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.UnknownHostException;

/**
 *
 * @author Mosta
 */
public class Connection {
    int timeout = 2000;
    
    /**
     * Create an empty connection object
     */
    public Connection(){
    }
    
    /**
     * A function to test the connection
     * @throws UnknownHostException
     * @throws IOException 
     */
    public void testConnection() throws UnknownHostException, IOException{
        InetAddress[] addresses = InetAddress.getAllByName("www.google.com");
        for (InetAddress address : addresses) {
            if (address.isReachable(timeout))
            System.out.printf("%s is reachable%n", address);
            else
            System.out.printf("%s could not be contacted%n", address);
        }
    }
    
    /**
     * A function that set the proxy 
     * @param ip server IP
     * @param port  port number
     * @param user user name for authentication
     * @param pw user password for authentication 
     */
    public void setProxy(String ip , String port , String user , String pw){
        System.setProperty("proxySet", "true");
        System.setProperty("http.proxyHost", ip);
        System.setProperty("http.proxyPort", port);
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user,pw.toCharArray());
            }
        }); 
    }
}
