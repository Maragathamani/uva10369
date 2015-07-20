import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Maragathamani on 3/31/14.
 *
 * Reference: Kruskal Algorithm (Cormen, Leiserson, Rivest, and Stein, Introduction to Algorithms, Third Edition, MIT Press.)
 */
class Main {

    public static int N;			// Number of test cases
    public static int S;			// Number of satellites
    public static int P;			// Number of outpost
    public static int p;
    public static int s;
    public static int[] pa = new int[550];		// To save parent of the nodes.
    public static int[] noOfChild = new int[550];		// To save the child of each node.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        while (N != 0) {
            String num = br.readLine();
            String narr[] = num.trim().split(" ");
            S = Integer.parseInt(narr[0].trim());
            P = Integer.parseInt(narr[1].trim());
            p = P;
            int i = 0;
            int[] X = new int[p + 1];
            int[] Y = new int[p + 1];
            while (P != 0) {
                String temp = br.readLine();
                String temp2[] = temp.trim().split(" ");
                X[i] = Integer.parseInt(temp2[0].trim());
                Y[i] = Integer.parseInt(temp2[1].trim());
                P--;
                i++;
            }
            vertex v[] = new vertex[((p) * (p - 1) / 2)];
            for (int j = 0; j < ((p) * (p - 1) / 2); j++) {
                v[j] = new vertex();
            }

            allEdges(X, Y, v);
            Arrays.sort(v);
//            for (int w = 0; w < v.length; w++) {
//                System.out.println(v[w].getA() + " " + v[w].getB() + " " + v[w].getEdgeValue());
//            }

            Double[] finalEdges = new Double[p - 1];
            s = (p * (p - 1) / 2);

//            System.out.println("MST Edges: ");

            for (int k = 0; k < p; k++) {
                pa[k] = k;
                noOfChild[k] = 0;
            }
            int e = 0;

            for (int j = 0; j < s; j++) {
                int index1 = v[j].getA();
                int index2 = v[j].getB();

                if (FindSet(index1) != FindSet(index2)) {
                    Union(index1, index2);
                    finalEdges[e] = v[j].getEdgeValue();
                    e++;
                }
            }

            Arrays.sort(finalEdges, Collections.reverseOrder());

//            for (int z = 0; z < finalEdges.length; z++) {
//                System.out.println(finalEdges[z]);
//            }

            ArrayList<Double> edge = new ArrayList<Double>(Arrays.asList(finalEdges));
            DecimalFormat df = new DecimalFormat("#.00");
            System.out.println(df.format(removeSatelliteEdge(edge, S - 1)));
            N--;
        }
    }

	/**
     * Tries to find the cycle before joining two vertices.
     */	
    private static void Union(int index1, int index2) {
        priorityUpdate(FindSet(index1), FindSet(index2));
    }

	/**
     * Used to keep track of the highest node (i.e.) node in the top level.
     */
    private static void priorityUpdate(int i, int i1) {
        if (noOfChild[i] > noOfChild[i1]) {
            pa[i1] = i;
        } else {
            pa[i] = i1;
            if (noOfChild[i] == noOfChild[i1]) {
                noOfChild[i1]++;
            }
        }
    }

	/**
     * Finds the cycle.
     */
    private static int FindSet(int index1) {
        if (index1 != pa[index1]) {
            pa[index1] = FindSet(pa[index1]);
        }
        return pa[index1];
    }

	/**
     * Saves all the edges and the corresponding vertices.
     */
    private static void allEdges(int[] a, int[] b, vertex[] v) {
        int edge = 0;
        for (int i = 0; i < p; i++) {
            for (int j = i + 1; j < p; j++) {
                v[edge].setA(i);
                v[edge].setB(j);
                v[edge].setEdgeValue(distance(a[i], a[j], b[i], b[j]));
                edge++;
            }
        }
    }

	/**
     * Removes the edges that are connected through the satellites.
     */
    private static Double removeSatelliteEdge(ArrayList<Double> distance, int s) {
        for (int i = 0; i < s; i++) {
            distance.remove(0);
        }
        return distance.get(0);
    }

	/**
     * Calculates the weight of the deges.
     */
    private static double distance(int x1, int x2, int y1, int y2) {
        double D = Math.sqrt(Math.pow((x2 - x1), 2) + (Math.pow((y2 - y1), 2)));
        return D;
    }
}


/**
 * Class to save the edges and corresponding vertices.
 */
class vertex implements Comparable<vertex> {
    private int a;
    private int b;
    private Double edgeValue = 0.0;

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public Double getEdgeValue() {
        return edgeValue;
    }

    public void setEdgeValue(Double edgeValue) {
        this.edgeValue = edgeValue;
    }

    @Override
    public int compareTo(vertex vEdge) {
        Double edge = vEdge.getEdgeValue();
        return Double.compare(this.edgeValue, edge);
    }
}