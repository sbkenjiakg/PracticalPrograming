
package syntax_analyzer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.HashMap;


public class LexicalAnalyzerImp implements LexicalAnalyzer {
    PushbackReader source;
    HashMap<String, LexicalUnit> reservedMap;

    public LexicalAnalyzerImp() throws Exception {
        Reader r = new InputStreamReader(new FileInputStream("src/newLang/test1.bas"));
        source = new PushbackReader(r);
        reservedMap = new HashMap<String, LexicalUnit>();
        for (LexicalType lt : LexicalType.values()) {
            reservedMap.put(lt.toString(), new LexicalUnit(lt));
        }
    }

    public LexicalAnalyzerImp(InputStream is) throws Exception {
        Reader r = new InputStreamReader(is);
        source = new PushbackReader(r);
        reservedMap = new HashMap<String, LexicalUnit>();
        for (LexicalType lt : LexicalType.values()) {
            reservedMap.put(lt.toString(), new LexicalUnit(lt));
        }
    }

    /**
     * 先読みしたPushBackReaderの中身を1文字読み出す
     * @return LexicalUnit
     */
    @Override
     public LexicalUnit get() throws Exception {
        //while(true){  
            LexicalUnit lu;
            
            int ci = source.read();
            if (ci == -1) return new LexicalUnit(LexicalType.EOF);    //終末記号
            
            char c = (char) ci;
            
            if (c == ' '){
                lu = this.get();
            }else{
                if (isAlpha(c)) {                               //先頭の文字がアルファベットの時の処理         
                    lu = getAlpha(c);
                } else if (isNumeric(c)) {                       //先頭の文字が数字の時の処理   
                    lu = getNumeric(c);
                } else if (c == '"') {                                          //数字、アルファベット以外の時の処理
                    lu = getLiteral(c);
                } else{
                    lu = getSpecial(c);
                }
                //System.out.println(lu.toString() + "," + lu.getType());
            }
            return lu;
        //}
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
     *
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
     *
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
     *
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

            if (!isAlpha(c) && !isNumeric(c)) {
                //アルファベットでも数字でもなかった場合はsourceから読み出さずにブレイク
                source.unread(ci);
                break;
            }
        }
        //予約語の確認
        LexicalUnit lu = reservedMap.get(str);
        if (lu != null) {
            return lu;
        }
        return new LexicalUnit(LexicalType.LITERAL, new ValueImp(str.trim()));
    }

    /**
     * 数字の読み出し
     *
     * @param c
     * @return
     * @throws Exception
     */
    private LexicalUnit getNumeric(char c) throws Exception {
        String str = "";
        while (true) {
            str += c;

            int ci = source.read();
            if (ci == -1) {
                break;
            }
            c = (char) ci;

            if (!isAlpha(c) && !isNumeric(c)) {
                //アルファベットでも数字でもなかった場合はsourceから読み出さずにブレイク
                source.unread(ci);
                break;
            }
        }

        try {
            return new LexicalUnit(LexicalType.INTVAL, new ValueImp(Integer.parseInt(str.trim())));
        } catch (Exception e) {
            //整数型にキャストできなかったとき
            return new LexicalUnit(LexicalType.LITERAL, new ValueImp(str.trim()));
        }
    }

    /**
     * "で始まる文字列を読み込んで"で終わる
     *
     * @param c
     * @return LexicalUnit
     * @throws Exception
     */
    private LexicalUnit getLiteral(char c) throws Exception {
        String str = "";
        while (true) {
            int ci = source.read();
            if (ci == -1) {
                break;
            }
            c = (char) ci;
            if (c == '"') {
                break;
            }
            str += c;
        }
        return new LexicalUnit(LexicalType.LITERAL, new ValueImp(str));
    }

    /**
     * ドットやイコールなどの特殊記号
     *
     * @param c
     * @return
     * @throws Exception
     */
    private LexicalUnit getSpecial(char c) throws Exception {
        String str = "";
        if (c == ' ') return null;
        
        while (true) {
            str += c;

            int ci = source.read();
            if (ci == -1) break;          
            c = (char) ci;

            if (c == ' ' || isAlpha(c) || isNumeric(c)) {
                source.unread(ci);
                break;
            }
        }
        switch (str) {
            case "=":
                return new LexicalUnit(LexicalType.EQ);
            case "<":
                return new LexicalUnit(LexicalType.LT);
            case ">":
                return new LexicalUnit(LexicalType.GT);
            case "<=":
            case "=<":
                return new LexicalUnit(LexicalType.LE);
            case ">=":
            case "=>":
                return new LexicalUnit(LexicalType.GE);
            case "<>":
                return new LexicalUnit(LexicalType.NE);
            case ".":
                return new LexicalUnit(LexicalType.DOT);
            case "+":
                return new LexicalUnit(LexicalType.ADD);
            case "-":
                return new LexicalUnit(LexicalType.SUB);
            case "*":
                return new LexicalUnit(LexicalType.MUL);
            case "/":
                return new LexicalUnit(LexicalType.DIV);
            case ")":
                return new LexicalUnit(LexicalType.LP);
            case "(":
                return new LexicalUnit(LexicalType.RP);
            case ",":
                return new LexicalUnit(LexicalType.COMMA);
            case "\n":
                return new LexicalUnit(LexicalType.NL);
        }

        return null;
    }
}
