/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmott;


import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author 517002
 */
public class Gmott{
       private Thread t;
        private String threadName;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        // TODO code application logic here
        
        //open excel
        try{
        FileInputStream file = new FileInputStream(new File("60_HarmonicChannel_URLS.xlsx"));
        
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        Sheet firstSheet = workbook.getSheetAt(0);
        
        int noOfColumns = firstSheet.getRow(0).getLastCellNum();
        int noOfRows = firstSheet.getLastRowNum();
        String videoFormat = "";
        
        String CDN = firstSheet.getRow(0).getCell(0).toString();
        
        while(true){
        //print column at a time 
        for(int kk = 0; kk < noOfColumns; kk++){
            
            //Format List
            
            if(kk > 4){
                videoFormat = firstSheet.getRow(1).getCell(kk).toString();
                //System.out.println(videoFormat);
            }
        //check for format 
        for (int jj=0; jj< noOfRows + 1; jj++) {
             
            Row row = firstSheet.getRow(jj);
            Cell cell = row.getCell(kk); //get first cell
            //System.out.println(cell.toString());
            // Printing Stuff
            String playlistURL = cell.toString();
            
            
            //kk column start jj is row start
            if(jj > 1 && (kk==12 || kk==8)){
                
                
                //new GetPlaylist(playlistURL,videoFormat);
                GetPlaylist p1 = new GetPlaylist(playlistURL,videoFormat);
                p1.start();
                //System.out.println(jj +":" + kk);
                //break;
            }
        }        
        }
        }

        
        }
        catch(Exception e){
            
            System.out.println(e.toString());
        }
        
        
    }



    
}
