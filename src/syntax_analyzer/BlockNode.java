package syntax_analyzer;

import java.util.HashSet;
import java.util.Set;

public class BlockNode extends Node{
    Node body;
    public BlockNode(Environment env) {
        super.env = env;
        super.type = NodeType.BLOCK;
    }
    
    private static Set<LexicalType> firstSet = new HashSet<LexicalType>();
    static{
        firstSet.add(LexicalType.IF);
        firstSet.add(LexicalType.DO);
        firstSet.add(LexicalType.WHILE);
    }
    
    public static Node isMatch(Environment env, LexicalUnit first){
        if(!firstSet.contains(first.type)){
            return null;
        }
        return new BlockNode(env);
    }
    
    public boolean Parse() throws Exception{
        LexicalUnit lu = env.getInput().get();
        env.getInput().unget(lu);
        
        body = IfNode.isMatch(env, lu);
        if(body != null){
            return body.Parse();
        }
        
        body = LoopNode.isMatch(env, lu);
        if(body != null){
            return body.Parse();
        }
        
        if(lu.getType() == LexicalType.END){
            body = new Node(NodeType .END);
            return true;
        }
        
        return false;
    }
}
