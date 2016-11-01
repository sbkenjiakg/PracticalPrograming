/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newLang;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sbkenjiakg
 */
public class LexicalAnalyzerImp implements LexicalAnalyzer {
    PushbackReader source;
    Map<String, LexicalUnit> reservedMap;

    public LexicalAnalyzerImp() throws Exception {
        Reader r = new java.io.InputStreamReader(new FileInputStream("src/newLang/test1.bas"));
        source = new PushbackReader(r);
        createMap();
    }
    
     public LexicalAnalyzerImp(InputStream is) throws Exception {
        Reader r = new java.io.InputStreamReader(is);
        source = new PushbackReader(r);
        createMap();
    }
     
     /**
      * 予約語を収納したMapを生成する
      */
     public void createMap(){
        reservedMap = new HashMap<String, LexicalUnit>();
        reservedMap.put("LITERAL", new LexicalUnit(LexicalType.LITERAL));
        reservedMap.put("DO", new LexicalUnit(LexicalType.DO));
        reservedMap.put("=", new LexicalUnit(LexicalType.EQ));
    }
    
    /**
     * 先読みしたPushBackReaderの中身を1文字読み出す
     * 
     * @return LexicalUnit
     * 
     */
    @Override
    public LexicalUnit get() throws Exception {
        int ci = source.read();
        if (ci == -1) {
            return new LexicalUnit(LexicalType.EOF);    //終末記号
        }

        char c = (char) ci;
        if (isAlpha(c)) {                               //先頭の文字がアルファベットの時の処理         
            return getAlpha(c);                         
        }else if (isNumeric(c)) {                       //先頭の文字が数字の時の処理   
           return getNumeric(c);                        
        }else{                                          //数字、アルファベット以外の時の処理
           return getLiteral(c);
        }
        
    }

    @Override
    public boolean expect(LexicalType type) throws Exception {
        return false;
    }

    @Override
    public void unget(LexicalUnit token) throws Exception {

    }

    /**
     * 引数がアルファベットかどうか判定する
     * @param c
     * @return 
     */
    private boolean isAlpha(char c) {
        if ('a' <= c && 'z' >= c) {
            return true;
        }
        if ('A' <= c && 'Z' >= c) {
            return true;
        }
        return false;
    }

    /**
     * 引数が数字かどうか判定する
     * @param c
     * @return 
     */
    private boolean isNumeric(char c) {
        if ('0' <= c && '9' >= c) {
            return true;
        }
        return false;
    }
    
    /**
     * アルファベットを読み出す
     * @param c
     * @return
     * @throws Exception 
     */
    private LexicalUnit getAlpha(char c) throws Exception {
        String str = "";
        while (true) {
            str += c;

            int ci = source.read();
            if (ci == -1) {
                break;
            }
            c = (char) ci;
      
            if(!isAlpha(c) && !isNumeric(c)) {
                //アルファベットでも数字でもなかった場合はsourceから読み出さずにブレイク
                source.unread(ci);
                break;
            }
        }
        //予約語の確認
        LexicalUnit lu = reservedMap.get(str);
        if(lu != null) return lu;
        return new LexicalUnit(LexicalType.NAME, new ValueImp(str));
    }

    /**
     * 数字の読み出し
     * @param c
     * @return
     * @throws Exception 
     */
    private LexicalUnit getNumeric(char c) throws Exception{
        String str = "";
        while (true) {
            str += c;

            int ci = source.read();
            if (ci == -1) {
                break;
            }
            c = (char) ci;
            
            if(!isAlpha(c) && !isNumeric(c)) {
                //アルファベットでも数字でもなかった場合はsourceから読み出さずにブレイク
                source.unread(ci);
                break;
            }
        }
        
        try{
            return new LexicalUnit(LexicalType.INTVAL, new ValueImp(Integer.parseInt(str)));
        }catch(Exception e){
            //整数型にキャストできなかったとき
            return new LexicalUnit(LexicalType.NAME, new ValueImp(str));
        }
    }

    private LexicalUnit getLiteral(char c) throws Exception{
        String str = "";
        while (true) {
            //改行と空白が来たらブレイク
            
            str += c;
            
            int ci = source.read();
            if (ci == -1) break;
            c = (char) ci;
            if(isNumeric(c) || isAlpha(c)){
                source.unread(ci);
                break;
            }
            
        }
        
        LexicalUnit lu = reservedMap.get(str);
        if(lu != null) return lu;
        return new LexicalUnit(LexicalType.LITERAL, new ValueImp(str));
    }

}
