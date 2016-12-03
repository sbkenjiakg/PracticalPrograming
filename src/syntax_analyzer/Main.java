package syntax_analyzer;

import java.io.FileInputStream;

public class Main {

    public static void main(String[] args) throws Exception {
        LexicalAnalyzer lex;
        LexicalUnit first;
        Node program;

        System.out.println("basic parser");
        lex = new LexicalAnalyzerImp(new FileInputStream("src/syntax_analyzer/test1.bas"));
       
        first = lex.get();
        
        //System.out.println(first.toString() + ",\t" + first.getType());
        program = StmtListNode.isMatch(new Environment(lex), first);
        if (program != null && program.Parse()) {
            System.out.println(program);
            System.out.println("value = " + program.getValue());
        } else {
            System.out.println("syntax error");
        }
        
        /*
        FileInputStream fin = null;
        LexicalAnalyzer lex;
        LexicalUnit first;
        Environment env;
        Node program;

        System.out.println("basic parser");
        fin = new FileInputStream("test.txt");
        lex = new LexicalAnalyzerImpl(fin);
        env = new Environment(lex);
        first = lex.get();

        program = Program.isMatch(env, first);
        if (program != null && program.Parse()) {
            System.out.println(program);
            System.out.println("value = " + program.getValue());
        } else {
            System.out.println("syntax error");
        }
        */
    }

}
