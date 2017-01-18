package com.thanos.springboot.common.core.expression.operator;

/**
 * @author peiheng.zph created on 2017/1/18 21:19
 * @version 1.0
 */
public enum MathOperator implements Operator<Double, Double> {
    LESS("<") {
        @Override
        public boolean matches(Double input, Double target) {
            return input.compareTo(target) < 0;
        }
    },
    GREATER(">") {
        @Override
        public boolean matches(Double input, Double target) {
            return input.compareTo(target) > 0;
        }
    },
    NO_LESS(">=") {
        @Override
        public boolean matches(Double input, Double target) {
            return input.compareTo(target) >= 0;
        }
    },
    NO_GREATER("<=") {
        @Override
        public boolean matches(Double input, Double target) {
            return input.compareTo(target) <= 0;
        }
    };

    MathOperator(String operator) {
        this.operator = operator;
    }

    private String operator;

    public String getOperator() {
        return operator;
    }

    public static MathOperator of(String operatorStr) {
        for (MathOperator operator : MathOperator.values()) {
            if (operator.getOperator().equals(operatorStr)) {
                return operator;
            }
        }
        return null;
    }
}
