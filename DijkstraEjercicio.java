// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║           Algoritmo de Dijkstra con Prueba de Escritorio (No Dirigido)       ║
// ╠══════════════════════════════════════════════════════════════════════════════╣
// ║       Grafo adaptado al de la imagen (A-L) con 12 nodos y pesos reales       ║
// ╚══════════════════════════════════════════════════════════════════════════════╝

import java.util.*;

public class DijkstraEjercicio {

    static final int INF = Integer.MAX_VALUE;
    static final int V = 12; // Número de nodos (A-L)

    // ANSI para colores
    static final String CYAN = "\u001B[36m";
    static final String YELLOW = "\u001B[33m";
    static final String GREEN = "\u001B[32m";
    static final String RESET = "\u001B[0m";
    static final String BOLD = "\u001B[1m";

    public static void main(String[] args) {
        int[][] L = new int[V][V];

        // Inicializar matriz con infinito
        for (int i = 0; i < V; i++) Arrays.fill(L[i], INF);

        // Mapeo: A=0, B=1, C=2, D=3, E=4, F=5, G=6, H=7, I=8, J=9, K=10, L=11

        // Aristas según la imagen (no dirigido)
        agregarArista(L, 0, 1, 3); // A-B
        agregarArista(L, 0, 2, 2); // A-C
        agregarArista(L, 0, 4, 9); // A-E
        agregarArista(L, 1, 3, 2); // B-D
        agregarArista(L, 1, 4, 4); // B-E
        agregarArista(L, 2, 4, 6); // C-E
        agregarArista(L, 2, 5, 9); // C-F
        agregarArista(L, 3, 6, 2); // D-G
        agregarArista(L, 4, 6, 1); // E-G
        agregarArista(L, 4, 7, 2); // E-H
        agregarArista(L, 5, 7, 1); // F-H
        agregarArista(L, 5, 8, 2); // F-I
        agregarArista(L, 6, 9, 6); // G-J
        agregarArista(L, 7, 8, 1); // H-I
        agregarArista(L, 7, 9, 5); // H-J
        agregarArista(L, 7, 10, 6); // H-K
        agregarArista(L, 8, 10, 2); // I-K
        agregarArista(L, 9, 11, 5); // J-L
        agregarArista(L, 10, 11, 3); // K-L
        agregarArista(L, 9, 11, 9); // J-L (segunda conexión más larga)
        agregarArista(L, 4, 2, 6); // E-C (ya arriba pero repetido por simetría opcional)

        // Ejecutar Dijkstra desde A (0)
        dijkstraConsola(L, 0);
    }

    static void agregarArista(int[][] L, int from, int to, int peso) {
        L[from][to] = peso;
        L[to][from] = peso; // no dirigido
    }

    static void dijkstraConsola(int[][] L, int fuente) {
        int[] D = new int[V];
        boolean[] visitado = new boolean[V];
        Set<Integer> C = new HashSet<>();
        Set<Integer> S = new HashSet<>();

        Arrays.fill(D, INF);
        D[fuente] = 0;

        for (int i = 0; i < V; i++) if (i != fuente) C.add(i);
        S.add(fuente);
        visitado[fuente] = true;

        // Relajar vecinos iniciales
        for (int w = 0; w < V; w++) {
            if (L[fuente][w] != INF) {
                D[w] = L[fuente][w];
            }
        }

        // Encabezado
        System.out.println("\n" + BOLD + "╔════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.printf("║  %-7s │ %-15s │ %-15s │ %-30s ║\n", "V", "C", "S", "D");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════╣");

        // Paso inicial
        System.out.printf("║  %-7s │ %-15s │ %-15s │ %-30s ║\n",
                CYAN + letra(fuente) + RESET,
                mostrar(C, YELLOW),
                mostrar(S, GREEN),
                mostrarD(D)
        );

        // Iteraciones
        for (int iter = 0; iter < V - 1; iter++) {
            int v = -1;
            int minDist = INF;

            for (int i = 0; i < V; i++) {
                if (!visitado[i] && D[i] < minDist) {
                    minDist = D[i];
                    v = i;
                }
            }

            if (v == -1) break;

            visitado[v] = true;
            S.add(v);
            C.remove(v);

            for (int w = 0; w < V; w++) {
                if (!visitado[w] && L[v][w] != INF) {
                    D[w] = Math.min(D[w], D[v] + L[v][w]);
                }
            }

            // Mostrar paso
            System.out.printf("║  %-7s │ %-15s │ %-15s │ %-30s ║\n",
                    CYAN + letra(v) + RESET,
                    mostrar(C, YELLOW),
                    mostrar(S, GREEN),
                    mostrarD(D)
            );
        }

        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════╝");

        // Distancias finales
        System.out.println("\n" + BOLD + "🎯 Distancias finales desde el nodo " + letra(fuente) + ":" + RESET);
        for (int i = 0; i < V; i++) {
            String dist = (D[i] == INF) ? "∞" : String.valueOf(D[i]);
            System.out.printf("• Nodo %s: %s\n", letra(i), dist);
        }
    }

    static String mostrar(Set<Integer> conjunto, String color) {
        List<String> lista = new ArrayList<>();
        for (int x : conjunto) lista.add(letra(x));
        return color + lista.toString() + RESET;
    }

    static String mostrarD(int[] D) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < D.length; i++) {
            sb.append((D[i] == INF ? "∞" : D[i]));
            if (i < D.length - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }

    static String letra(int index) {
        return String.valueOf((char) ('A' + index));
    }
}
