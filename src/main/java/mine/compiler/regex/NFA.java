package mine.compiler.regex;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * NFA:
 * 
 * <p>
 * priority: paren() > closure* > concatenate > or|
 */
public class NFA {
    private char[] regex;
    private DiGraph nfaGraph;
    private int M;// 状态数量 就是 pat长度

    /*
     * public NFA(String pat) { // 根据pat构建DiGraph M = pat.length(); nfaGraph = new
     * DiGraph(M + 1);// M状态为接收状态 Stack<Character> op = new Stack<>(); char c;
     * //在线处理：每遇到右括号，就将前面的表达式处理完，这样可以保证op栈中不会有连续两个 ) 出现 for (int i = 0; i < M; i++)
     * { // lp : LeftParen Character lp;
     * 
     * c = pat.charAt(i); if(c=='('||c=='|')op.push(c); else if(c==')'){ Character
     * tmp = op.pop(); if(tmp=='|'){ lp = op.pop(); nfaGraph.addEdge(new
     * DirectedEdge(lp, tmp+1));//看来必须要使用pat的index来记录了，Graph里的状态是index！
     * nfaGraph.addEdge(new DirectedEdge(tmp, )); }else lp = tmp;
     * 
     * }else{
     * 
     * }
     * 
     * }
     * 
     * }
     */

    public NFA(String pat) {
        regex = pat.toCharArray();
        M = regex.length;
        nfaGraph = new DiGraph(M + 1);
        Stack<Integer> op = new Stack<>();// 用来记录pat中操作符的index而不是操作符本身
        char c;
        // lp : 左括号的index
        int lp;
        for (int i = 0; i < M; i++) {
            lp = i;// lp也可能时当个字符-->为了给单个字符的*运算做处理
            c = regex[i];
            if (c == '|' || c == '(')
                op.push(i);
            else if (regex[i] == ')') {
                // 处理完本)至上一个左括号之间的所有内容
                int tmp = op.pop();
                if (regex[tmp] == '|') {
                    // 栈顶是 或
                    lp = op.pop();
                    nfaGraph.addEdge(new DiEdge(lp, tmp + 1));
                    nfaGraph.addEdge(new DiEdge(tmp, i));
                } else// 栈顶就是左括号
                    lp = tmp;
            }
            // 对于 * 运算符，在当前字符时预处理下一个字符
            if (i + 1 < M && regex[i + 1] == '*') {
                nfaGraph.addEdge(new DiEdge(lp, i + 1));
                nfaGraph.addEdge(new DiEdge(i + 1, lp));
            }
            if (c == '(' || c == '*' || c == ')')
                nfaGraph.addEdge(new DiEdge(i, i + 1));
            // 没有处理单纯的字符之间的转换嘛？
        }
    }

    public boolean recogonize(String txt) {
        Set<Integer> pc = nfaGraph.directedDFS(0);
        int len = txt.length();
        for (int i = 0; i < len; i++) {// txt[i+1]能够到达的所有状态的集合
            Set<Integer> tmp = new HashSet<>();
            char c = txt.charAt(i);
            if (c == M)
                continue;// TODO: 这时候还没有算是完成任务。为什么？难道之前的状态到达了接收状态，再过几个还有可能编程不接受嘛？
            for (var state : pc)
                if (c == regex[state] || regex[state] == '.')
                    tmp.add(state + 1);// 匹配下一个状态，这也是为什么graph里没有处理i和i+1的原因：那里的边只是epsilon-转换

            pc = nfaGraph.directedDFS(tmp);
        }

        if (pc.contains(M))
            return true;
        return false;
    }

    public static void main(String[] args) {

    }
}