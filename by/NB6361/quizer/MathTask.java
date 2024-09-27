package by.NB6361.quizer;

interface MathTask extends Task {
    enum Operations {
        SUM("+"),
        DIFFERENCE("-"),
        MULTIPLICATION("*"),
        DIVISION("/");

        private String character;

        Operations(String character) {
            this.character = character;
        }

        public String getCharacter() {
            return character;
        }
    }

    interface Generator extends Task.Generator {
        double getMinNumber();
        double getMaxNumber();
        default double getDiffNumber() {
            return getMaxNumber() - getMinNumber();
        }
    }

    boolean isCorrect(String answer);
}
