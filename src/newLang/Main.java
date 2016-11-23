/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newLang;

import java.io.FileInputStream;

/**
 *
 * @author sbkenjiakg
 */
public class Main {

    public static void main(String[] args) throws Exception {

        LexicalAnalyzerImp lai = new LexicalAnalyzerImp();

        LexicalUnit lu = lai.get();

        if (lu.type == LexicalType.EOF) {
            // break;
        }
    }
}
