package mine.compiler.regex;

/**
 * DirectedEdge
 * <p>
 * u -> v 没有权重
 */
class DiEdge {
    /** tail */
    int from;
    /** head */
    int to;

    public DiEdge(int from, int to) {
        this.from = from;
        this.to = to;
    }

}