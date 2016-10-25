/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmott;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.*;

/**
 *
 * @author 517002
 */
class parseSmoothStreaming {

    public parseSmoothStreaming(String doc, String URL, String videoFormat, Boolean lastPkt) {
        
         //System.out.println(doc);
       Tools mytool = new Tools();
       
       //System.out.println("heheh");
       Document xmlDoc = mytool.returnDocFromXMLURL(URL);
       
       //NodeList streamIndex = xmlDoc.getFirstChild().getAttributes().getNamedItem("Duration");
       Node root = xmlDoc.getFirstChild();
        NodeList ChildNode = root.getChildNodes();
         Node Child = ChildNode.item(0);
            NamedNodeMap Attributes = Child.getAttributes();
            
        NodeList x = xmlDoc.getElementsByTagName("StreamIndex");
        NodeList y = x.item(0).getChildNodes();
        
        
        
        HashMap StreamIndex = mytool.hashAttributes(x.item(0));
        String VaryURL = StreamIndex.get("Url").toString(); 
        
       ArrayList startTime = mytool.findAttributesValues(y, "t"); 
       
       long newTime = Long.parseLong(startTime.get(0).toString());
       
       ArrayList times = mytool.findAttributesValues(y, "d");
       //System.out.println(startTime);
      // System.out.println(Long.parseLong(times.get(0).toString()) + newTime);
       
       ArrayList bitRates = mytool.findAttributesValues(y, "Bitrate");
       
       
       for(int ii = 0 ; ii< bitRates.size();ii++){
           for(int kk = 0; kk < times.size();kk++){
             String replaced = VaryURL;
             
             replaced = VaryURL.replace("{bitrate}", bitRates.get(ii).toString());
              newTime = newTime - Long.parseLong(times.get(kk).toString());
             
             replaced = replaced.replace("{start time}", String.valueOf(newTime));
             //replaced = replaced.replace("Fragments", "FragmentInfo");
             String vidPartsURL = mytool.ClipEnd(URL,"/") + replaced;
             
             //InputStream vidParts = mytool.getInputStreamFromURL(vidPartsURL);
             
              downloadFile dwn;
             boolean headeronly = false;
             if(lastPkt == false){
              dwn = new downloadFile(vidPartsURL,videoFormat,headeronly);
             }
             else if(lastPkt == true && kk == times.size()-1){
                  dwn = new downloadFile(vidPartsURL,videoFormat,headeronly);
             }
             }
           
       }

       
       
       
        
     
    }


}
