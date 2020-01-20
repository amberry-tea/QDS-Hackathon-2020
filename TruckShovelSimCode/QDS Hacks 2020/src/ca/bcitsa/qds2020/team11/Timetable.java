package ca.bcitsa.qds2020.team11;

public class Timetable {
    private boolean[] times;
    private int length;

    public Timetable(int length) {
        this.length = length;
        times = new boolean[length];
    }

    /**
     * @return the times
     */
    public boolean[] getTimes() {
        return times;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param times the times to set
     */
    public void setTimes(boolean[] times) {
        this.times = times;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * return false if overlapping
     */
    public boolean setFalse(int i) {
        boolean ret = true;
        int norm = normalize(i);

        if (!times[norm]) {
            ret = false;
        }

        times[norm] = false;

        return ret;
    }

    /**
     * return false if overlapping
     */
    public boolean setTrue(int i) {
        boolean ret = true;
        int norm = normalize(i);

        if (times[norm]) {
            ret = false;
        }

        times[norm] = true;

        return ret;
    }

    /**
     * return false if overlapping
     * 
     * @param i start index
     * @param j end index
     */
    public boolean setTrue(int i, int j) {
        boolean ret = true;
        int iNorm = normalize(i);
        int jNorm = normalize(j);

        // if normalization is needed, and the process is split across end of array and
        // start
        if (i < length && j >= length) {
            for (int ind = iNorm; ind < length; ind++) {
                if (times[ind] == true) {
                    ret = false;
                }
                times[ind] = true;
            }

            for (int ind = 0; ind <= jNorm; ind++) {
                if (times[ind] == true) {
                    ret = false;
                }
                times[ind] = true;
            }
        } else {

            // for indexes within array
            for (int ind = iNorm; ind <= jNorm; ind++) {
                if (times[ind] == true) {
                    ret = false;
                }
                times[ind] = true;
            }
        }

        return ret;
    }

    /**
     * normalizes the value, looping it back to the start if it extends past length
     * 
     * @param i index
     * @return normalized index
     */
    private int normalize(int i) {
        while (i >= length) {
            i -= length;
        }
        return i;
    }

    public String toString() {
        String ans = "[";
        for (int i = 0; i < length; i++) {
            ans += " " + times[i] + ",";
        }
        return ans + " ]";
    }

}
