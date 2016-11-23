package syntax_analyzer;

import java.io.FileInputStream;

public class Main {

    public static void main(String[] args) throws Exception {
        LexicalAnalyzer lex;
        LexicalUnit first;
        Environment env;
        Node program;

        System.out.println("basic parser");
        lex = new LexicalAnalyzerImp(new FileInputStream("src/syntax_analyzer/test1.bas"));
        lex.get();
        
        
        /*
        program = Program.isMatch(new Environment(lex), lex.get());
        if (program != null && program.Parse()) {
            System.out.println(program);
            System.out.println("value = " + program.getValue());
        } else {
            System.out.println("syntax error");
        }
        */
    }

}
