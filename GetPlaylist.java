/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmott;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author 517002
 */
class GetPlaylist extends Thread{
    
    private String myvideoFormat, myDoc ,myPlaylistURL;
    private Boolean lastPkt;
    
    public GetPlaylist(String playlistURL, String videoFormat) {
        
        //System.out.println(playlistURL);
        Tools myTools = new Tools();
        this.myvideoFormat = videoFormat;
        
        try{
            InputStream inputStream = myTools.getInputStreamFromURL(playlistURL);
            
            String doc = myTools.getStringFromInputStream(inputStream);
            
            this.myvideoFormat = videoFormat;
            this.myPlaylistURL = playlistURL;
            this.myDoc = doc;
            this.lastPkt = true;
            
            //System.out.println("videoFormat is "+ videoFormat);
            
            /*
            switch(videoFormat){
                
                case "hls.fp":
                    
                    new ParseHLSFP(doc,playlistURL,videoFormat);
                
                case "hls.pr":
                    new ParseHLSFP(doc,playlistURL,videoFormat);
                    
                case "ss.pr":
                    new parseSmoothStreaming(doc,playlistURL,videoFormat,this.lastPkt);
                    
                  case "dash.wv":
                      //System.out.println(playlistURL);
                   new ParseMPEGDash(doc,playlistURL,videoFormat,this.lastPkt);
                   
                default:
                    
            }
            */
            
        }catch(Exception e){
            
            
        }
        
    }
    
    @Override
    public void run(){
                    switch(this.myvideoFormat){
                
                case "hls.fp":
                    
                    new ParseHLSFP(this.myDoc,this.myPlaylistURL,this.myvideoFormat);
                
                case "hls.pr":
                    new ParseHLSFP(this.myDoc,this.myPlaylistURL,this.myvideoFormat);
                    
                case "ss.pr":
                    new parseSmoothStreaming(this.myDoc,this.myPlaylistURL,this.myvideoFormat, this.lastPkt);
                    
                  case "dash.wv":
                      System.out.println(this.myPlaylistURL);
                   new ParseMPEGDash(this.myDoc,this.myPlaylistURL,this.myvideoFormat,this.lastPkt);
                   
                default:
                    
            }
    }

    
        
}
