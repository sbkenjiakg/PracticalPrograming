/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntax_analyzer;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author sbkenjiakg
 */
class StmtNode extends Node{
    Node body;
    
    public StmtNode(Environment env) {
        super.env = env;
    }
    
    private static Set<LexicalType> firstSet = new HashSet<LexicalType>();
    static{
        firstSet.add(LexicalType.AS);
        //firstSet.add(LexicalType.);
    }
    
    public static Node isMatch(Environment env, LexicalUnit first){
        if(!firstSet.contains(first.type)){
            return null;
        }
        return new StmtNode(env);
    }
    
    public boolean Parse() throws Exception{
        LexicalUnit lu = env.getInput().get();
        env.getInput().unget(lu);
        
        body = AssignNode.isMatch(env, lu);
        if(body != null){
            return body.Parse();
        }
        
        body = CallSubNode.isMatch(env, lu);
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
