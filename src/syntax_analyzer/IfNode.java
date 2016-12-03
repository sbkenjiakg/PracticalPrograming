package syntax_analyzer;

/**
 *
 * @author sbkenjiakg
 */
class IfNode extends Node{

    public IfNode(Environment my_env) {
        super.env = my_env;
        super.type = NodeType.IF_BLOCK;
    }
    
    public static Node isMatch(Environment env, LexicalUnit first){
        return new IfNode(env);
    }
    
}
