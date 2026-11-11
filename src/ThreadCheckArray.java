import java.util.ArrayList;

/**
 * Searches one branch of the subset-sum problem in a separate thread.
 *
 * <p>Two instances can search complementary branches over the same shared
 * input. The instance named {@code thread1} includes the final input value in
 * its initial branch; the other instance starts without it.</p>
 */
public class ThreadCheckArray implements Runnable {
    /** Whether this worker has found a solution. */
    private boolean flag;

    /** Marks the input values selected by this worker's solution. */
    private boolean[] winArray;

    /** Shared input and result state. */
    SharedData sd;

    /** Input values to search. */
    ArrayList<Integer> array;

    /** Target sum to find. */
    int b;

    /**
     * Creates a worker using the input and target held in shared state.
     *
     * @param sd shared input and result state
     */
    public ThreadCheckArray(SharedData sd) {
        this.sd = sd;
        synchronized (sd) {
            array = sd.getArray();
            b = sd.getB();
        }
        winArray = new boolean[array.size()];
    }

    /**
     * Recursively searches the first {@code n} values for a subset summing
     * to {@code b}.
     *
     * @param n number of values in the current search prefix
     * @param b remaining sum required from that prefix
     */
    void rec(int n, int b) {
        synchronized (sd) {
            if (sd.getFlag()) return;
        }
        if (n == 1) {
            if(b == 0 || b == array.get(n-1)) {
                flag = true;
                synchronized (sd) { sd.setFlag(true); }
            }
            if (b == array.get(n-1)) winArray[n-1] = true;
            return;
        }

        rec(n-1, b - array.get(n-1));
        if (flag) winArray[n-1] = true;
        synchronized (sd) {
            if (sd.getFlag()) return;
        }
        rec(n-1, b);
    }

    /**
     * Runs the worker's assigned search branch and publishes a found solution
     * to the shared state.
     */
    public void run() {
        if (array.size() != 1)
            if (Thread.currentThread().getName().equals("thread1"))
                rec(array.size()-1, b - array.get(array.size() - 1));
            else
                rec(array.size()-1, b);
        if (array.size() == 1)
            if (b == array.get(0) && !flag) {
                winArray[0] = true;
                flag = true;
                synchronized (sd) { sd.setFlag(true); }
            }
        if (flag) {
            if (Thread.currentThread().getName().equals("thread1"))
                winArray[array.size() - 1] = true;
            synchronized (sd) { sd.setWinArray(winArray); }
        }
    }
}
