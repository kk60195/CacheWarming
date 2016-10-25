/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmott;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.poi.util.IOUtils;


/**
 *
 * @author 517002
 */
class downloadFile {

    public downloadFile(String URL,String pfolder, Boolean HeaderOnly) {
        long threadId = Thread.currentThread().getId();
        if(threadId == 1){
        System.out.println("Thread # " + threadId + " is doing this task");
            System.out.println(URL);
        }
           try{
           Tools mytool = new Tools();
          
            InputStream inputStream = mytool.getInputStreamFromURLRanged(URL,"Bytes=0-2000");
            byte[] bytes = IOUtils.toByteArray(inputStream);
            System.out.println("byte size "+ bytes.length);
            
           String foldername = mytool.ClipEnd(URL, "/");
           foldername = mytool.replaceChar(foldername,'/','_');
           foldername = mytool.replaceChar(foldername,':','_');
           
           String filename = mytool.GetEnds(URL,"/");
           //System.out.println("filename: "+ filename);

            
            //System.out.println("folder name" + foldername);
            
            //make root
                Boolean mkdir = mytool.makeDir("root");
               
            //make parent
            
            mkdir = mytool.makeDir(".\\root.\\"+ pfolder);
            
           
            //make child
            
            mkdir = mytool.makeDir(".\\root.\\"+ pfolder +".\\"+foldername);
           
            
            //download file
            File targetFile = new File(".\\root.\\"+ pfolder+".\\"+ foldername +".\\"+(filename));
            
            if(HeaderOnly){
                
                targetFile = new File(targetFile.toString() + ".txt");
                
                String Header = mytool.getHeaderFromURL(URL);
                
                 
                 FileWriter fw = new FileWriter(targetFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Header);
			bw.close();
            }
            else{
                java.nio.file.Files.copy(inputStream,targetFile.toPath());
            }
            
            IOUtils.closeQuietly(inputStream);
           
            
            //targetFile.delete();
            
            }
           catch(Exception e){
               System.out.println("download error" + e.toString());
                       long threadId2 = Thread.currentThread().getId();
                 System.out.println("Thread # " + threadId2 + " is doing this task");
           }
    }
    
}
