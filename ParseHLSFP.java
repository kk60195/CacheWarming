/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmott;

import java.io.InputStream;
import java.util.*;

/**
 *
 * @author 517002
 */
class ParseHLSFP {

    public ParseHLSFP(String doc, String URL,String videoFormat) {
        
        //System.out.println(doc);
       Tools mytool = new Tools();
        
        String URLOpenEnded = mytool.ClipEnd(URL, "/");
        String[] values = doc.split("\n");
        
        
        for(String ss: values){
            
            
            //String s = ss.substring(0,1);
            if(ss.startsWith("0")){
                //System.out.println("debug1");
                InputStream inputStream = mytool.getInputStreamFromURL(URLOpenEnded.concat(ss));       
                //new downloadFile(URLOpenEnded.concat(ss), videoFormat);
                String m3u8file = mytool.getStringFromInputStream(inputStream);
                
                String[] m3u8Values = m3u8file.split("\n");
              
                //last line
               
                new downloadFile(URLOpenEnded.concat(m3u8Values[m3u8Values.length - 1 ]),videoFormat,true); 

            }

           }
        
        
        
        
    }

    
    
}
