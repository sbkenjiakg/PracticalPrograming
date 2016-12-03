package syntax_analyzer;

import java.util.ArrayList;
import java.util.List;


public class StmtListNode extends Node{
    
    public StmtListNode(Environment my_env) {
        super.env = my_env;
    }
    
    List<Node> childNodeList = new ArrayList<Node>();
    
    public static Node isMatch(Environment env, LexicalUnit first){
        return new StmtListNode(env);
    }
    
    @Override
    public boolean Parse () throws Exception{
        while(true){
            LexicalUnit lu = env.getInput().get();
            if(lu.getType() == LexicalType.NL) continue;
            env.getInput().unget(lu);
            
            next = StmtNode.isMatch(env, lu);
            if(next != null) {
                if(next.Parse() == false) return false;
                childNodeList.add(next);
                continue;
            }
            
            next = BlockNode.isMatch(env, lu);
            if(next != null){
                if(next.Parse() == false) return false;
                childNodeList.add(next);
                continue;
            }
            
            return true;
        }
    }
    
    
}
