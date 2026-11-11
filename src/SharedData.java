import java.util.ArrayList;

/**
 * Holds the input values and the shared result state used by the worker threads.
 *
 * <p>The input values are stored in an {@code ArrayList<Integer>}. The result
 * remains a primitive {@code boolean[]} whose entries correspond by index to
 * the input values.</p>
 */
public class SharedData {
    /** Input values to search. */
    private ArrayList<Integer> array;

    /** Result flags corresponding by index to {@link #array}. */
    private boolean[] winArray;

    /** Indicates that a worker has found a solution. */
    private boolean flag;

    /** Target sum for the subset-sum search. */
    private final int b;

    /**
     * Creates shared state for checking whether a subset of {@code array} sums
     * to {@code b}.
     *
     * @param array the input values; its order defines the indexes in the
     *              result array
     * @param b the target sum
     */
    public SharedData(ArrayList<Integer> array, int b) {
        this.array = array;
        this.b = b;
        this.winArray = new boolean[array.size()];
    }

    /**
     * Returns the result flags, where each true entry marks a value included
     * in the discovered solution.
     *
     * @return the result flags, in input order
     */
    public boolean[] getWinArray() {
        return winArray;
    }

    /**
     * Replaces the result flags.
     *
     * @param winArray result flags corresponding by index to the input values
     */
    public void setWinArray(boolean[] winArray) {
        this.winArray = winArray;
    }

    /**
     * Returns the input values.
     *
     * @return the input values in their original order
     */
    public ArrayList<Integer> getArray() {
        return array;
    }

    /**
     * Returns the target sum.
     *
     * @return the target sum
     */
    public int getB() {
        return b;
    }

    /**
     * Reports whether a worker has found a solution.
     *
     * @return {@code true} if a solution has been found; {@code false}
     *         otherwise
     */
    public boolean getFlag() {
        return flag;
    }

    /**
     * Updates whether a solution has been found.
     *
     * @param flag {@code true} when a solution has been found
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
