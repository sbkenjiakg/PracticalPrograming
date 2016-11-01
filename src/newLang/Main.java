/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newLang;


/**
 *
 * @author sbkenjiakg
 */
public class Main{
    
    public static void main(String[] args) throws Exception{
       
        LexicalAnalyzerImp lai = new LexicalAnalyzerImp();
        while(true){
           LexicalUnit lu = lai.get();
           try{
               if(lu.type == LexicalType.EOF) break;
               System.out.println(lu.toString());
           }catch(NullPointerException npe){
               System.out.println(npe.getMessage());
           }
           
       }
    }   
}

