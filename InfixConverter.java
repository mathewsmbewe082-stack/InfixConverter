import java.util.Stack;
import java.util.Scanner;

public class InfixConverter {

    // ─── Precedence Rules ───────────────────────────────────────────────────────
    static int precedence(char op) {
        switch (op) {
            case '^': return 3;          // Highest (right-associative)
            case '*':
            case '/': return 2;
            case '+':
            case '-': return 1;
            default:  return 0;
        }
    }

    static boolean isOperand(char ch) {
        return Character.isLetterOrDigit(ch);
    }

    // ─── Infix → Postfix ─────────────────────────────────────────────────────────
    static String infixToPostfix(String expression) {
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            // Skip spaces
            if (ch == ' ') continue;

            // Case 1: Operand → directly to output
            if (isOperand(ch)) {
                result.append(ch);

            // Case 2: '(' → push onto stack
            } else if (ch == '(') {
                stack.push(ch);

            // Case 3: ')' → pop until matching '('
            } else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                if (!stack.isEmpty()) stack.pop(); // Remove '('

            // Case 4: Operator
            } else {
                // For right-associative '^', use strict less than
                while (!stack.isEmpty() && stack.peek() != '(' &&
                       (ch == '^'
                           ? precedence(stack.peek()) > precedence(ch)
                           : precedence(stack.peek()) >= precedence(ch))) {
                    result.append(stack.pop());
                }
                stack.push(ch);
            }
        }

        // Pop remaining operators from stack
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.toString();
    }

    // ─── Infix → Prefix ──────────────────────────────────────────────────────────
    static String infixToPrefix(String expression) {
        // Step 1: Reverse the expression
        StringBuilder reversed = new StringBuilder(expression).reverse();

        // Step 2: Swap '(' ↔ ')'
        for (int i = 0; i < reversed.length(); i++) {
            if (reversed.charAt(i) == '(')       reversed.setCharAt(i, ')');
            else if (reversed.charAt(i) == ')') reversed.setCharAt(i, '(');
        }

        // Step 3: Get postfix of modified expression
        String postfix = infixToPostfix(reversed.toString());

        // Step 4: Reverse postfix to get prefix
        return new StringBuilder(postfix).reverse().toString();
    }

    // ─── Validate Basic Brackets ─────────────────────────────────────────────────
    static boolean isBalanced(String expression) {
        int count = 0;
        for (char ch : expression.toCharArray()) {
            if (ch == '(') count++;
            else if (ch == ')') count--;
            if (count < 0) return false;
        }
        return count == 0;
    }

    // ─── Main ─────────────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   Infix → Postfix & Prefix Converter ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("Supported operators: +  -  *  /  ^ ");
        System.out.println("Example: A+B*C  or  (A+B)*(C-D)    ");
        System.out.println("────────────────────────────────────────");
        System.out.print("Enter infix expression: ");

        String infix = scanner.nextLine().trim();

        if (!isBalanced(infix)) {
            System.out.println("❌ Error: Unbalanced parentheses!");
        } else {
            String postfix = infixToPostfix(infix);
            String prefix  = infixToPrefix(infix);

            System.out.println("\n────────────────────────────────────────");
            System.out.println("  Infix   : " + infix);
            System.out.println("  Postfix : " + postfix);
            System.out.println("  Prefix  : " + prefix);
            System.out.println("────────────────────────────────────────");
        }

        scanner.close();
    }
}