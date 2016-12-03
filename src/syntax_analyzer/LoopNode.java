package syntax_analyzer;


class LoopNode extends Node{

    public LoopNode(Environment my_env) {
        super.env = env;
        super.type = NodeType.LOOP_BLOCK;
    }
    
    public static Node isMatch(Environment env, LexicalUnit first){
        return new LoopNode(env);
    }
    
}
