/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntax_analyzer;

/**
 *
 * @author sbkenjiakg
 */
class CallSubNode extends Node{

    public CallSubNode(Environment my_env) {
        super.env = my_env;
        
    }
    
    public static Node isMatch(Environment env, LexicalUnit first){
        return new CallSubNode(env);
    }
}
