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
public class ValueImp implements Value{
    String s;
    int i;
    double d;
    boolean b;
    
    ValueImp(String this_str) {
        s = this_str;
    }

    ValueImp(int this_i) {
        i = this_i;
    }
    
    ValueImp(double this_d) {
        d = this_d;
    }
    
    ValueImp(boolean this_b) {
        b = this_b;
    }
    
    
    @Override
    public String getSValue() {
        //ストリング型で値を取り出す。
        return s;
    }

    @Override
    public int getIValue() {
        return i;
    }

    @Override
    public double getDValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getBValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ValueType getType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
