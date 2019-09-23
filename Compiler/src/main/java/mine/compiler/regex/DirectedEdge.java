package mine.compiler.regex;

/**
 * DirectedEdge
 * <p>
 * u -> v
 */
class DirectedEdge {
    /** tail */
    int from;
    /** head */
    int to;
    /** weight */
    double w;

    public DirectedEdge(int from, int to, double w) {
        this.from = from;
        this.to = to;
        this.w = w;
    }

}