import java.util.ArrayList;

/**
 * Stores the input values and the shared state used by the worker threads.
 */
public class SharedData {
    /** The input values being examined by the worker threads. */
    private ArrayList<Integer> array;
    /** Primitive result flags corresponding to the input values. */
    private boolean[] winArray;
    /** Indicates whether a solution has been found. */
    private boolean flag;
    /** The target value for the search. */
    private final int b;

    /**
     * Creates shared data for the supplied values and target.
     *
     * @param array the input values stored as an {@code ArrayList<Integer>}
     * @param b the target value to find
     */
    public SharedData(ArrayList<Integer> array, int b) {
        this.array = array;
        this.b = b;
    }

    /**
     * Returns the primitive result flags.
     *
     * @return the boolean flags identifying values in a solution
     */
    public boolean[] getWinArray() {
        return winArray;
    }

    /**
     * Stores the primitive result flags.
     *
     * @param winArray the boolean flags identifying values in a solution
     */
    public void setWinArray(boolean[] winArray) {
        this.winArray = winArray;
    }

    /**
     * Returns the input values.
     *
     * @return the values stored as an {@code ArrayList<Integer>}
     */
    public ArrayList<Integer> getArray() {
        return array;
    }

    /**
     * Returns the target value.
     *
     * @return the target value
     */
    public int getB() {
        return b;
    }

    /**
     * Reports whether a solution has been found.
     *
     * @return {@code true} when a solution has been found; otherwise {@code false}
     */
    public boolean getFlag() {
        return flag;
    }

    /**
     * Updates the shared solution status.
     *
     * @param flag {@code true} when a solution has been found
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
