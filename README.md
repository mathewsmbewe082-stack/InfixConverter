# Infix Expression Converter (Java)

A Java program that converts an **Infix expression** into both **Postfix** and **Prefix** notation using a Stack-based algorithm.

---

## What is Infix, Postfix and Prefix?

| Type    | Example      | Description                          |
|---------|--------------|--------------------------------------|
| Infix   | `A + B * C`  | Operator is **between** operands     |
| Postfix | `A B C * +`  | Operator comes **after** operands    |
| Prefix  | `+ A * B C`  | Operator comes **before** operands   |

---

## Workflow

```
START
  |
  v
Read infix expression (e.g., A+B*C)
  |
  v
+------------------------------------------+
|         POSTFIX CONVERSION               |
|  1. Scan expression left to right        |
|  2. If operand → add to output           |
|  3. If '(' → push to stack               |
|  4. If ')' → pop stack until '('         |
|  5. If operator → check precedence       |
|     and pop higher/equal operators first |
|  6. Push remaining stack to output       |
+------------------------------------------+
  |
  v
+------------------------------------------+
|         PREFIX CONVERSION                |
|  1. Reverse the infix expression         |
|  2. Swap '(' with ')' and vice versa     |
|  3. Apply the postfix algorithm          |
|  4. Reverse the result to get prefix     |
+------------------------------------------+
  |
  v
Display Postfix and Prefix results
  |
  v
END
```

---

## Pseudo Code

```
FUNCTION getPrecedence(op):
    IF op == '^'           → return 3
    IF op == '*' or '/'    → return 2
    IF op == '+' or '-'    → return 1
    ELSE                   → return 0

FUNCTION infixToPostfix(expression):
    CREATE empty stack S
    CREATE empty result string

    FOR each character ch in expression:
        IF ch is operand (letter/digit):
            APPEND ch to result

        ELSE IF ch == '(':
            PUSH ch onto S

        ELSE IF ch == ')':
            WHILE S.top() != '(':
                POP from S and APPEND to result
            POP '(' from S (discard)

        ELSE (ch is an operator):
            WHILE S is not empty AND
                  precedence(S.top()) >= precedence(ch):
                POP from S and APPEND to result
            PUSH ch onto S

    WHILE S is not empty:
        POP from S and APPEND to result

    RETURN result

FUNCTION infixToPrefix(expression):
    REVERSE the expression
    SWAP every '(' with ')' and vice versa
    CALL infixToPostfix on modified expression
    REVERSE the postfix result
    RETURN reversed result
```

---

## Operator Precedence Rules

| Operator | Precedence | Associativity |
|----------|------------|---------------|
| `^`      | 3 (High)   | Right to Left |
| `*` `/`  | 2 (Medium) | Left to Right |
| `+` `-`  | 1 (Low)    | Left to Right |

---

## Sample Output

| Infix Expression | Postfix    | Prefix     |
|------------------|------------|------------|
| `A+B*C`          | `ABC*+`    | `+A*BC`    |
| `(A+B)*(C-D)`    | `AB+CD-*`  | `*+AB-CD`  |
| `A^B^C`          | `ABC^^`    | `^^ABC`    |
| `(A+B)*C-D/E`    | `AB+C*DE/-`| `-*+ABC/DE`|

---

## How to Run

**Step 1 — Compile the program:**
```bash
javac InfixConverter.java
```

**Step 2 — Run the program:**
```bash
java InfixConverter
```

**Step 3 — Enter your infix expression when prompted:**
```
Enter infix expression: (A+B)*(C-D)
```

**Output:**
```
Infix   : (A+B)*(C-D)
Postfix : AB+CD-*
Prefix  : *+AB-CD
```

---

## Files

```
InfixConverter/
├── InfixConverter.java    ← Main Java program
└── README.md              ← Project documentation
```

---

## Requirements

- Java JDK 8 or higher
- Any terminal or IDE (VS Code, IntelliJ, Eclipse)
