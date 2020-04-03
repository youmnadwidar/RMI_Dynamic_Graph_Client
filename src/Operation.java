public class Operation {


    private char type;
    private int node1;
    private int node2;


    public Operation() {
    }


    public Operation(char type, int node1, int node2) {
        this.type = type;
        this.node1 = node1;
        this.node2 = node2;
    }


    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public int getNode1() {
        return node1;
    }

    public void setNode1(int node1) {
        this.node1 = node1;
    }

    public int getNode2() {
        return node2;
    }

    public void setNode2(int node2) {
        this.node2 = node2;
    }

}
