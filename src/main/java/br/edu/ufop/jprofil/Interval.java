/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufop.jprofil;

import java.util.*; 

/**
 * Class used to define the Interval type. This type
 * is used to make basic operations with interval.
 * 
 * 
 * @author Darlan Nunes de Brito
 * @author Jônata Lucas Nogueira
 */
public class Interval {
    private double inf;
    private double sup;
    /**
     * Stores instance names and his values.
     */
    private static HashMap<String, Interval> variables = new HashMap<>();


    /**
     * Empty class constructor
     */
    public Interval() {
    }
    
    
    /**
     * Class constructor with interval limits
     * 
     * @param inf Interval inferior limit
     * @param sup Interval superior limit
     * @param varName String with instance name
     */
    public Interval(double inf, double sup, String varName) {
        if(inf < sup){
            this.inf = inf;
            this.sup = sup;
        }
        else{
            this.inf = sup;
            this.sup = inf;
        }
        variables.put(varName+this.toString(), this);
    }
    
    /**
     * Class constructor with an interval object
     * 
     * @param interval
     * @param newName String with instance name
     */
    public Interval(Interval interval,String newName){
        this.inf = interval.inf;
        this.sup = interval.sup;
        variables.put(newName, this);
    }
    
    /**
     * Class constructor to a degenarated interval
     * 
     * @param degenarated Value of the degenarated interval
     * @param varName String with instance name
     */
    public Interval(double degenarated, String varName){
        this.inf = degenarated;
        this.sup = degenarated;
        variables.put(varName, this);
    }
    
    /**
     * Method to evaluate a string with a mathematical expression
     * 
     * @param expr Mathematical expression to be evaluated.
     * @return Interval with the value of evaluated expression
     */
    public static Interval eval(String expr){
        Interval A = new Interval();
        Deque<Pair> idxs = new ArrayDeque<>();
        idxs = Interval.getBracketsIndexes(expr);
        if(!idxs.isEmpty())
            for(Pair idx : idxs){
                System.out.println(idx);
            }
        
        return A;
    }

    /**
     * Sum this class with an interval
     * 
     * @param A Interval to make sumation
     * @return Interval result
     */
    public Interval add(Interval A){
        Interval sum = new Interval();
        sum.inf = this.inf + A.inf;
        sum.sup = this.sup + A.sup;
        
        if(sum.inf > sum.sup){
            double tmp;
            tmp = sum.inf;
            sum.inf = sum.sup;
            sum.sup = tmp;
        }
        return sum;
    }
    /**
     * Function to print the list of instances names
     */
    public static void printInstances(){
        variables.keySet().stream().map((key) -> {
            System.out.print(key + ": ");
            return key;
        }).forEachOrdered((key) -> {
            System.out.println(variables.get(key).inf + "," + variables.get(key).sup + "]");
        });
    }
    /**
    * Get the expressions within the brackets in order that they appear on the expression,
    * the expression is a String object with a mathematical expression.
    * 
    * @param expr A string with a mathematical expression i.e. "A+B-(C*D)+(E/(F*G))
    * @return A {@link java.util.Deque} variable
    * @throws ArithmeticException 
    */
    static Deque<Pair> getBracketsIndexes(String expr) throws ArithmeticException
    { 
        // Using ArrayDeque is faster than using Stack class 
        Deque<Character> stackOpen 
            = new ArrayDeque<>(); 

        Deque<Pair> stackIdx = new ArrayDeque<>();
        Deque<Pair> stackFinalIdx = new ArrayDeque<>();

        CharSequence openBracket = ")";
        CharSequence closeBracket = "(";
        if(expr.contains(openBracket) && !expr.contains(closeBracket)){
            throw new ArithmeticException("Unbalaced brackets");
        }else if(!expr.contains(openBracket) && expr.contains(closeBracket)){
            throw new ArithmeticException("Unbalaced brackets");
        }
        
        // Traversing the Expression 
        for (int i = 0; i < expr.length(); i++)  
        { 
            char x = expr.charAt(i); 
  
            if (x == '(')  
            { 
                // Push the element in the stackOpen 
                stackOpen.push(x);
                stackIdx.push(Pair.push(i, Integer.MAX_VALUE));
                continue; 
            }

            if (x == ')') {
                //Check if there are opened brackets
                if(stackOpen.isEmpty())
                    throw new ArithmeticException("Unbalaced brackets");
                stackOpen.pop();
                Pair pair = stackIdx.pop();
                pair.end = i;
                stackFinalIdx.push(pair);                
            } 
        } 
        return stackFinalIdx;
    } 

}
