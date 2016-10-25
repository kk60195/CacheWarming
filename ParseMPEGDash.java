/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmott;

import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author 517002
 */
class ParseMPEGDash {

    public ParseMPEGDash(String doc, String URL, String videoFormat, Boolean LastPacket) {
               Tools mytool = new Tools();
       
       //System.out.println("heheh");
            Document xmlDoc = mytool.returnDocFromXMLURL(URL);
            
           Node root;
           
            if(xmlDoc.hasChildNodes() != false){
            root = xmlDoc.getFirstChild();
            }
            else{
                System.out.println(URL + " has no child nodes");
                return;
            }
            NodeList ChildNode = root.getChildNodes();
            NodeList adaptationSet = xmlDoc.getElementsByTagName("AdaptationSet");
            
            for(int ii = 0 ; ii< adaptationSet.getLength();ii++){
                
            ArrayList repID = mytool.findAttributesValues(adaptationSet.item(ii).getChildNodes(),"id");
            
            //ArrayList<Node> timeSeg = mytool.findNodesByName(adaptationSet.item(ii).getChildNodes(),"SegmentTemplate");
            Node segTemplate = mytool.findNodeByName(adaptationSet.item(ii).getChildNodes(),"SegmentTemplate");
            Node segTimeline = mytool.findNodeByName(segTemplate.getChildNodes(),"SegmentTimeline");
            ArrayList<Node> timeSeg = mytool.findNodesByName(segTimeline.getChildNodes(),"S");
            HashMap segTemplateHash = mytool.hashAttributes(segTemplate);
            String mediaTemplate = segTemplateHash.get("media").toString();
            
            //System.out.println(repID);
            //System.out.println(mytool.printAttributes(timeSeg.get(0)));
            // System.out.println(mediaTemplate);
             
            for(int kk = 0; kk < repID.size(); kk++){
                for(int jj = 0; jj < timeSeg.size(); jj++){
                    
                  
                    
                    HashMap timeSegSpecs = mytool.hashAttributes(timeSeg.get(jj));
                    
                    int r = 0;
                    if(timeSegSpecs.get("r") != null){
                     r = Integer.parseInt(timeSegSpecs.get("r").toString());
                    }
                    
                    int startTime = Integer.parseInt(timeSegSpecs.get("t").toString());
                    int timeDiff = Integer.parseInt(timeSegSpecs.get("d").toString());
                    
                    
                    
                    String relativeURL = mediaTemplate.replace("$RepresentationID$", repID.get(kk).toString());
                    String URLInsertTime = relativeURL.replace("$Time$", Integer.toString(startTime));
                    //System.out.println(URLInsertTime);
                    String absoluteURL = mytool.ClipEnd(URL, "/") + URLInsertTime;
                    
                    for(int rr = 0; rr < r ; rr++){
                        
                        startTime =startTime + timeDiff;
                        URLInsertTime = relativeURL.replace("$Time$", Integer.toString(startTime));
                        
                        
                        absoluteURL = mytool.ClipEnd(URL, "/") + URLInsertTime;
                        
                        //System.out.println(absoluteURL);
                        if(LastPacket == true){
                            
                        if(jj == timeSeg.size() -1 && rr == r - 1 ){
                            //System.out.println("jj:" +jj + " rr:" + rr);
                            new downloadFile(absoluteURL,videoFormat,false);
                        }
                        }
                        else if(LastPacket == false){
                            new downloadFile(absoluteURL,videoFormat,false);
                        }
                    }
                    
                    
                }
                
            }
          
            }
    }
    
}
