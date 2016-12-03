package syntax_analyzer;

public class Node {
    NodeType type;
    Environment env;
    
    public Node next;

    public Node() {
    }
    public Node(NodeType my_type) {
        type = my_type;
    }
    public Node(Environment my_env) {
        env = my_env;
    }
    
    public NodeType getType() {
        return type;
    }
    
    public boolean Parse() throws Exception {
        return true;
    }
    
    public Value getValue() throws Exception {
        return null;
    }
 
    public String toString() {
    	if (type == NodeType.END) return "END";
    	else return "Node";        
    }

}
