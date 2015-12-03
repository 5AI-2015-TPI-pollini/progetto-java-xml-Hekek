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
    
    public Connection(){
        
    }
    
    public void testConnection() throws UnknownHostException, IOException{
        InetAddress[] addresses = InetAddress.getAllByName("www.google.com");
        for (InetAddress address : addresses) {
            if (address.isReachable(timeout))
            System.out.printf("%s is reachable%n", address);
            else
            System.out.printf("%s could not be contacted%n", address);
        }
    }
    
    public void setProxy(String ip , String port , String user , String pw){
        System.setProperty("proxySet", "true");
        System.setProperty("http.proxyHost", ip);
        System.setProperty("http.proxyPort", port);
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                System.out.println("Funge");
                return new PasswordAuthentication(user,pw.toCharArray());
            }
        });  
    }
}
