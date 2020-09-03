package mine.compiler.calculator;

import java.util.LinkedList;

import org.apache.commons.math3.special.Gamma;
import org.apache.commons.math3.util.FastMath;

/**
 * A calculator that supports elementary arithmetic, power and factorial(Gamma
 * function) opration.
 * <p>
 * 邓俊辉， 数据结构(C++版) p94
 */
public class Calculator {

    private final char[][] pri = {
            /* -----------------------当前运算符------------------ */
            /* + - * / ^ ! ( ) # */
            /* + */ { '>', '>', '<', '<', '<', '<', '<', '>', '>' },
            /* 栈 - */ { '>', '>', '<', '<', '<', '<', '<', '>', '>' },
            /* 顶 * */ { '>', '>', '>', '>', '<', '<', '<', '>', '>' },
            /* 运 / */ { '>', '>', '>', '>', '<', '<', '<', '>', '>' },
            /* 算 ^ */ { '>', '>', '>', '>', '>', '<', '<', '>', '>' },
            /* 符 ! */ { '>', '>', '>', '>', '>', '>', ' ', '>', '>' },
            /* ( */ { '<', '<', '<', '<', '<', '<', '<', '=', ' ' },
            /* ) */ { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            /* # */ { '<', '<', '<', '<', '<', '<', '<', ' ', '=' } };
    /** the expression */
    private String expr;
    private StringBuilder rpn = new StringBuilder();
    private int idx;

    private LinkedList<Double> opnd = new LinkedList<>();
    private LinkedList<Character> optr = new LinkedList<>();

    /**
     * 
     * @param expr a {@link String} leading by '$' and tailing by '#'
     */
    public Calculator(String expr) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < expr.length(); ++i)
            if (expr.charAt(i) != ' ')
                builder.append(expr.charAt(i));
        this.expr = builder.append('#').toString();
    }

    /**
     * calculate the expr
     * 
     * @return the res of expr
     */
    public double evaluate() {
        optr.push('#');
        double x;
        while (!optr.isEmpty()) {
            if (isDigit(expr.charAt(idx))) {
                readNumber();
                x = opnd.peek();
                rpn.append((x == (double) (int) x) ? Integer.toString((int) x) : x).append(' ');
            } else {
                switch (orderBetween(optr.peek(), expr.charAt(idx))) {
                    case '<':// 当前运算符优先级大于前一个运算符
                        optr.push(expr.charAt(idx++));
                        break;
                    case '=':
                        optr.pop();
                        idx++;
                        break;
                    case '>':// 当前运算符优先级更小，计算之前的运算；
                        // 可以保证当前括号内、该运算符之前的运算只有一步
                        char op = optr.pop();
                        rpn.append(op).append(' ');
                        if (op == '!')
                            opnd.push(calc('!', opnd.pop()));
                        else
                            opnd.push(calc(opnd.pop(), op, opnd.pop()));
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }
        return opnd.peek();
    }

    public String getRPN() {
        return rpn.toString();
    }

    private boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }

    /**
     * read next number and push the res in {@link Calculator#opnd}
     */
    private void readNumber() {
        double res = (double) (expr.charAt(idx) - '0');
        while (isDigit(expr.charAt(++idx)))
            res = res * 10 + (double) (expr.charAt(idx) - '0');
        if (expr.charAt(idx) == '.') { // .24
            double frac = 0.1;
            while (isDigit(expr.charAt(++idx))) {
                res = res + (expr.charAt(idx) - '0') * frac;
                frac /= 10;
            }
        }
        opnd.push(res);
    }

    private double calc(char op, double x) {
        if (op == '!')
            return Gamma.gamma(x + 1);
        else
            throw new IllegalArgumentException(
                    new StringBuilder("optr should be factorial, but now is ").append(op).toString());
    }

    /**
     * expr : y op x
     * 
     * @param x  second opnd
     * @param op optr
     * @param y  first opnd
     * @return <code>y</code> op <code>x</code>
     */
    private double calc(double x, char op, double y) {
        switch (op) {
            case '+':
                return y + x;
            case '-':
                return y - x;
            case '*':
                return y * x;
            case '/':
                return y / x;
            case '^':
                return FastMath.pow(y, x);
            default:
                throw new IllegalArgumentException(
                        new StringBuilder("illegal expr: ").append(y).append(op).append(x).toString());
        }
    }

    private int char2opt(char c) {
        switch (c) {
            case '+':
                return 0;
            case '-':
                return 1;
            case '*':
                return 2;
            case '/':
                return 3;
            case '^':
                return 4;
            case '!':
                return 5;
            case '(':
                return 6;
            case ')':
                return 7;
            case '#':
                return 8;
            default:
                throw new IllegalArgumentException(new StringBuilder("illegal optr: ").append(c).toString());
        }
    }

    private char orderBetween(char c1, char c2) {
        return pri[char2opt(c1)][char2opt(c2)];
    }

    public static void main(String[] args) {
        String s = "(( 5.3 ^ 2 / ( 1 + 23 ) / 4! * 5.5 * (67 - 8) + 9 ))";
        Calculator calculator = new Calculator(s);
        double res = calculator.evaluate();
        System.out.println(res);
        System.out.println(calculator.getRPN());
    }
}