package life;

class GenerationAlgorithm {

    boolean[][] generateNewGeneration(boolean[][] state) {
        boolean[][] newState = new boolean[state.length][state.length];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                int sum = getNeighbourSum(i, j, state);
                if (state[i][j]) {
                    newState[i][j] = (sum == 3 || sum == 2);
                } else {
                    newState[i][j] = (sum == 3);
                }
            }
        }
        return newState;
    }

    private int getNeighbourSum(int i, int j, boolean[][] state) {
        int sum = 0;
        for (int k = i - 1; k < i + 2; k++) {
            for (int l = j - 1; l < j + 2; l++) {
                sum += state[getIndex(k, state.length)][getIndex(l, state.length)] ? 1 : 0;
            }
        }
        sum -= state[i][j] ? 1 : 0;
        return sum;
    }

    private int getIndex(int index, int length) {
        if (index < 0) {
            return length + index;
        } else if (index >= length) {
            return index % length;
        }
        return index;
    }
}

