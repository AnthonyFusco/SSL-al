package kernel.structural.laws;

import java.util.ArrayList;

public class FunctionExpression {
    private String leftOperand;
    private String righOperand;
    private ArrayList<Tuple<Integer,Integer>> formuleLeft;
    private Tuple<String,Integer> inegaliteRight;

    public FunctionExpression(String leftOperand, String righOperand) {
        this.leftOperand = leftOperand;
        this.righOperand = righOperand;
        this.formuleLeft = new ArrayList<>();
    }

    public void findFormuleLeft() {
        //2x^2 - x^1 + 5x^0
        String prepareForNext = leftOperand.replace("-","+-");
        String[] allNode = prepareForNext.split("\\+");
        for (String s : allNode) {
            String[] powerSeparated = s.split("\\^");
            String [] tmp = powerSeparated[0].split("[A-Za-z]*");
            Tuple indicePower = new Tuple(Integer.parseInt(tmp[0]),Integer.parseInt(powerSeparated[1]));
            formuleLeft.add(indicePower);
        }
    }

    public void findInegaliteRight() {
        String opMath = "[<>!=]";
        String op = "";
        for(int i = 0; i < righOperand.length(); i++){
            Character c = righOperand.charAt(i);
            if (opMath.indexOf(c) >= 0){
                op+= c;
            }
        }
        String[] operatorSeparated = righOperand.split(opMath);
        this.inegaliteRight = new Tuple<String, Integer>(op,Integer.parseInt(operatorSeparated[1]));
    }

    public int calculateLeft(int x){
        int y = 0;
        for (Tuple<Integer, Integer> tuple : formuleLeft) {
            y+= Math.pow(x,tuple.getRight()) * tuple.getLeft();
        }
        return y;
    }

    public boolean calculateRight(int x){
        String op = inegaliteRight.getLeft();
        int condition = inegaliteRight.getRight();
        if(op.equals("<")){
            return x < condition;
        }else if (op.equals("<=")){
            return x <= condition;
        }else if (op.equals(">")){
            return x > condition;
        }else if (op.equals(">=")){
            return x >= condition;
        }else if (op.equals("==")){
            return x == condition;
        }else if (op.equals("!=")){
            return x != condition;
        }
        return false;
    }

    private class Tuple<L,R>{
        private R right;
        private L left;

        public Tuple(L left, R right) {
            this.right = right;
            this.left = left;
        }

        public R getRight() {
            return right;
        }

        public L getLeft() {
            return left;
        }
    }

}
