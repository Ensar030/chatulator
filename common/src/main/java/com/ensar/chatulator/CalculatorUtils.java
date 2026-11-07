package com.ensar.chatulator;

public class CalculatorUtils {

    public static boolean isMathExpression(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            return false;
        }

        String trimmed = expression.trim();

        boolean hasOperator = false;
        int parenthesesBalance = 0;
        char previousNonSpace = '\0';

        for (int i = 0; i < trimmed.length(); i++) {
            char current = trimmed.charAt(i);

            if (Character.isWhitespace(current)) {
                continue;
            }

            if (Character.isDigit(current) || current == '.') {
                previousNonSpace = current;
                continue;
            }

            if (current == '(') {
                parenthesesBalance++;
                previousNonSpace = current;
                continue;
            }

            if (current == ')') {
                parenthesesBalance--;

                if (parenthesesBalance < 0) {
                    return false;
                }

                previousNonSpace = current;
                continue;
            }

            if (isOperator(current)) {
                if (current == '-' && (previousNonSpace == '\0' || previousNonSpace == '(' || isOperator(previousNonSpace))) {
                    previousNonSpace = current;
                    continue;
                }

                if (previousNonSpace == '\0' || previousNonSpace == '(' || isOperator(previousNonSpace)) {
                    return false;
                }

                hasOperator = true;
                previousNonSpace = current;
                continue;
            }

            return false;
        }

        return hasOperator
                && parenthesesBalance == 0
                && previousNonSpace != '\0'
                && !isOperator(previousNonSpace)
                && previousNonSpace != '(';
    }

    private static boolean isOperator(char character) {
        return character == '+'
                || character == '-'
                || character == '*'
                || character == '/'
                || character == '%'
                || character == '^';
    }

    public static String calculateExpression(String expression) {
        if (!isMathExpression(expression)) {
            return "Invalid expression";
        }

        try {
            double result = new ExpressionParser(expression).parse();

            if (Double.isNaN(result) || Double.isInfinite(result)) {
                return "Invalid result";
            }

            if (result == Math.rint(result)) {
                return String.valueOf((long) result);
            }

            return String.valueOf(result);
        } catch (IllegalArgumentException exception) {
            return "Invalid expression";
        }
    }

    private static class ExpressionParser {
        private final String expression;
        private int position = -1;
        private int currentCharacter;

        private ExpressionParser(String expression) {
            this.expression = expression;
        }

        private double parse() {
            nextCharacter();
            double result = parseExpression();

            if (position < expression.length()) {
                throw new IllegalArgumentException("Unexpected character: " + (char) currentCharacter);
            }

            return result;
        }

        private void nextCharacter() {
            position++;
            currentCharacter = position < expression.length() ? expression.charAt(position) : -1;
        }

        private boolean eat(int characterToEat) {
            while (Character.isWhitespace(currentCharacter)) {
                nextCharacter();
            }

            if (currentCharacter == characterToEat) {
                nextCharacter();
                return true;
            }

            return false;
        }

        private double parseExpression() {
            double result = parseTerm();

            while (true) {
                if (eat('+')) {
                    result += parseTerm();
                } else if (eat('-')) {
                    result -= parseTerm();
                } else {
                    return result;
                }
            }
        }

        private double parseTerm() {
            double result = parsePower();

            while (true) {
                if (eat('*')) {
                    result *= parsePower();
                } else if (eat('/')) {
                    double divisor = parsePower();

                    if (divisor == 0) {
                        throw new IllegalArgumentException("Division by zero");
                    }

                    result /= divisor;
                } else if (eat('%')) {
                    double divisor = parsePower();

                    if (divisor == 0) {
                        throw new IllegalArgumentException("Modulo by zero");
                    }

                    result %= divisor;
                } else {
                    return result;
                }
            }
        }

        private double parsePower() {
            double result = parseFactor();

            if (eat('^')) {
                result = Math.pow(result, parsePower());
            }

            return result;
        }

        private double parseFactor() {
            if (eat('+')) {
                return parseFactor();
            }

            if (eat('-')) {
                return -parseFactor();
            }

            double result;
            int startPosition = this.position;

            if (eat('(')) {
                result = parseExpression();

                if (!eat(')')) {
                    throw new IllegalArgumentException("Missing closing parenthesis");
                }
            } else if (Character.isDigit(currentCharacter) || currentCharacter == '.') {
                while (Character.isDigit(currentCharacter) || currentCharacter == '.') {
                    nextCharacter();
                }

                result = Double.parseDouble(expression.substring(startPosition, this.position));
            } else {
                throw new IllegalArgumentException("Unexpected character: " + (char) currentCharacter);
            }

            return result;
        }
    }
}
