/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmott;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import jdk.internal.org.xml.sax.InputSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author 517002
 */
class Tools {
    
    public String ClipEnd(String word,String pattern){
        
        int Last = word.lastIndexOf(pattern);
        String ret = word;
        
        if(Last == -1){
            System.out.println("No pattern found in ClipEnd");
        }
        else{
            
            ret = ret.substring(0,Last+1);
           // System.out.println("hhhhhh" + ret);
        }
        
        return ret;
    }

    String replaceChar(String URL,char see,char replace) {
        String ret = URL;
        char[] retChar = URL.toCharArray(); 
        
        for(int ii = 0 ; ii < ret.length(); ii++){
            if(ret.charAt(ii)== see){
                ret = ret.substring(0,ii)+ replace + ret.substring(ii+1,ret.length());
            }
        }
        
        return ret;
    }

    String GetEnds(String word, String pattern) {
        int Last = word.lastIndexOf(pattern);
        String ret = word;
        
        if(Last == -1){
            System.out.println("No pattern found in ClipEnd");
        }
        else{
            
            ret = ret.substring(Last+1);
           // System.out.println("hhhhhh" + ret);
        }
        
        return ret;
    }
    
   String getStringFromInputStream(InputStream inputStream) {
       
        BufferedReader br = null;
        StringBuilder sb= new StringBuilder();
        String line;
        
        try{
            br = new BufferedReader(new InputStreamReader(inputStream));
            while((line = br.readLine())!= null){
                sb.append("\n"+ line);
            }
        }
        catch( IOException e){
            e.printStackTrace();
        } finally{
            if(br!= null){
                try{
                    br.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        
        return sb.toString();
    }
   
   InputStream getInputStreamFromURL(String URL){
       InputStream ret = null;
       try{
            URL myURL = new URL(URL);
            HttpURLConnection urlConnection = (HttpURLConnection)myURL.openConnection();
            ret = urlConnection.getInputStream();
            //urlConnection.getInputStream().close();
       }catch(Exception e){
            System.out.println("InputStream error here" + e.toString());
            String error = "InputStream error " + e.toString();
            InputStream stream = new ByteArrayInputStream(error.getBytes(StandardCharsets.UTF_8));
            ret = stream;
            return ret;

        }
       
       
       return ret;
   }
      InputStream getInputStreamFromURLRanged(String URL,String Ranged){
          //Ranged = "Bytes=0-2";
       InputStream ret = null;
       try{
            URL myURL = new URL(URL);
            HttpURLConnection urlConnection = (HttpURLConnection)myURL.openConnection();
             urlConnection.setRequestProperty("Range", Ranged);
            ret = urlConnection.getInputStream();
            //urlConnection.getInputStream().close();
       }catch(Exception e){
            System.out.println("InputStream error here" + e.toString());
            String error = "InputStream error " + e.toString();
            InputStream stream = new ByteArrayInputStream(error.getBytes(StandardCharsets.UTF_8));
            ret = stream;
            return ret;

        }
       
       
       return ret;
   }

    Boolean makeDir(String file) {
            File myfile = new File(file);
           
            
            
            if(!myfile.exists()){
                boolean result = false;
                try{
                    myfile.mkdir();
                    result = true;
                }
                catch(SecurityException se){
                    System.out.println(se.toString());
                }
                
            }
            return myfile.exists();
    }

    String getHeaderFromURL(String URL) {
        InputStream ret = null;
        String header = URL+ " \n";
       try{
            URL myURL = new URL(URL);
            HttpURLConnection urlConnection = (HttpURLConnection)myURL.openConnection();
            
            ret = urlConnection.getInputStream();
            
            //get all headers
                Map<String, List<String>> map = urlConnection.getHeaderFields();
                for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                    header = header.concat( String.format("Key:%-30s %-30s", entry.getKey(),entry.getValue()) + "\n" );
                           
            }
            
       }catch(Exception e){
            System.out.println("InputStream retrieveerr" + e.toString());
            
        }
       return header;
    }

    Document returnDocFromXMLURL(String URL)  {
       
        
        DocumentBuilderFactory objDocumentBuilderFactory = null;
       DocumentBuilder objDocumentBuilder = null;
       Document docIn = null;
        
       try{
          String urlSTR = URL;
           
          
           URL url = new URL(urlSTR);
           objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
          objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
           
          
     
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
   
           
         docIn = objDocumentBuilder.parse(url.openStream());
         
         
      
        
       }
       catch(Exception ex){
            System.out.println("return doc from XML issue" + ex.toString());
            }
        
        
       
       return docIn;
    
    }

    String printAttributes(Node item) {
        
        String ret = "";
        
        for(int ii = 0;ii < item.getAttributes().getLength() ; ii++){
            ret = ret + item.getAttributes().item(ii).getNodeName() + ": " + item.getAttributes().item(ii).getNodeValue() + "\t";
        }
        
        return ret;
    }

    HashMap hashAttributes(Node item) {
        HashMap ret = new HashMap();
        
        for(int ii = 0;ii < item.getAttributes().getLength() ; ii++){
            ret.put(item.getAttributes().item(ii).getNodeName(), item.getAttributes().item(ii).getNodeValue());
        }
        
        return ret;
    }
    
     ArrayList findAttributesValues(NodeList item, String TargetAttribute) {
         ArrayList ret2 = new ArrayList();
         try{
       
        int itemlength = item.getLength();
        
        for(int kk =0; kk< itemlength ; kk++){
            Node line = item.item(kk);
           
            NamedNodeMap map = line.getAttributes();
            
            if(map != null){
                int size = map.getLength();
                
                for(int ii = 0;ii < size ; ii++){
            
                    if(map.item(ii).getNodeName().equals(TargetAttribute)){
                   
                        //System.out.println(map.item(ii).getNodeName() +":" + map.item(ii).getNodeValue());
                        ret2.add(map.item(ii).getNodeValue());
                    //System.out.println(kk+ "sizeis" + line.getAttributes().item(ii).getNodeName() + line.getAttributes().item(ii).getNodeValue());
                    }
                        //ret.put(line.getAttributes().item(ii).getNodeName(), line.getAttributes().item(ii).getNodeValue());
                }
            }
                }
        
                return ret2;
        }catch(Exception e){
            System.out.println(e.toString());
            return ret2;
        }
     }

    ArrayList<Node> findNodesByName(NodeList item, String TargetAttribute) {
          ArrayList<Node> ret2 = new ArrayList<Node>();
         try{
       
        int itemlength = item.getLength();
        
        for(int kk =0; kk< itemlength ; kk++){
            Node line = item.item(kk);
           
            //System.out.println(line.getNodeName());
           
                   if(line.getNodeName().equals(TargetAttribute)){
                        
                            ret2.add(line);
                   }
        
               
        }
        return ret2;
        }catch(Exception e){
            System.out.println(e.toString());
            return ret2;
        }
    }
    Node findNodeByName(NodeList item, String TargetAttribute) {
          Node ret2 = null;
         try{
       
        int itemlength = item.getLength();
        
        for(int kk =0; kk< itemlength ; kk++){
            Node line = item.item(kk);
           
            //System.out.println(line.getNodeName());
           
                   if(line.getNodeName().equals(TargetAttribute)){
                        
                            ret2 =line;
                   }
        
               
        }
        return ret2;
        }catch(Exception e){
            System.out.println(e.toString());
            return ret2;
        }
    }


    }

      

