# 第二章：一个简单语法制导翻译器
## 上下文无关文法(context-free grammer)

1. 终结符号(terminal)<->**词法单元**
2. 非终结符号(nonterminal)<->**语法变量**
3. `->` 可以读作“可以具有如下形式”

[终结符和非终结符](https://blog.csdn.net/dadaooxx/article/details/6490885)

终结符，通俗的说就是不能单独出现在推导式左边的符号，也就是说终结符不能再进行

推导。不是终结符的都是非终结符。非终结符可理解为一个可拆分元素，而终结符是不可拆

分的最小元素。如：有α → β ，则α 必然是个非终结符。

# 中英文对照
| ENG     | ZH   |
| ------- | ---- |
| syntax  | 语法 |
| lexical | 词法 |
| lexeme  |      |
| sematic | 语义 |
| token   |      |
| grammer | 文法 |