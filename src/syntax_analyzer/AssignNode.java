package syntax_analyzer;


class AssignNode extends Node{

    public AssignNode(Environment my_env) {
       super.env = env;
       super.type = NodeType.ASSIGN_STMT;
    }
    
    
    public static Node isMatch(Environment env, LexicalUnit first){
        try{
            if(first.getType() != LexicalType.NAME) return null;
            
            LexicalUnit lu = env.getInput().get();
            lu = env.getInput().get();
            env.getInput().unget(first);
            env.getInput().unget(lu);
            
            if(lu.getType() != LexicalType.EQ) return null;
            
            return new AssignNode(env);
        }catch (Exception e){
            return null;
        }
    }
}
