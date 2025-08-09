// ╔══════════════════════════════════════════════════════════════════════════════╗
// ║           Algoritmo de Prim con Prueba de Escritorio - Grafo de Ciudades     ║
// ╠══════════════════════════════════════════════════════════════════════════════╣
// ║               Mostrando paso a paso el Árbol de Recubrimiento Mínimo         ║
// ╚══════════════════════════════════════════════════════════════════════════════╝

import java.util.*;

class Arista {
    int origen, destino, peso;

    Arista(int origen, int destino, int peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "{" + PrimCiudades.nombres[origen] + " - " + PrimCiudades.nombres[destino] + "}";
    }
}

public class PrimCiudades {
    static int V = 17; // Número de ciudades
    static List<List<Arista>> grafo = new ArrayList<>();

    // Nombres de las ciudades según el índice
    static String[] nombres = {
            "Coruña", "Vigo", "Oviedo", "Valladolid", "Bilbao",
            "Zaragoza", "Gerona", "Barcelona", "Valencia", "Madrid",
            "Badajoz", "Jaén", "Sevilla", "Cádiz", "Granada",
            "Murcia", "Albacete"
    };

    // Colores para consola
    static final String CYAN = "\u001B[36m";
    static final String YELLOW = "\u001B[33m";
    static final String GREEN = "\u001B[32m";
    static final String RESET = "\u001B[0m";
    static final String BOLD = "\u001B[1m";

    public static void main(String[] args) {
        for (int i = 0; i < V; i++) grafo.add(new ArrayList<>());

        // Aristas según el mapa
        agregarArista(0, 1, 171);
        agregarArista(0, 2, 455);
        agregarArista(1, 3, 356);
        agregarArista(2, 4, 304);
        agregarArista(3, 4, 280);
        agregarArista(3, 9, 193);
        agregarArista(4, 9, 395);
        agregarArista(4, 5, 324);
        agregarArista(5, 9, 325);
        agregarArista(5, 7, 296);
        agregarArista(7, 6, 100);
        agregarArista(7, 8, 349);
        agregarArista(8, 15, 191);
        agregarArista(8, 9, 251);
        agregarArista(15, 16, 241);
        agregarArista(16, 14, 278);
        agregarArista(14, 11, 256);
        agregarArista(14, 12, 242);
        agregarArista(12, 13, 125);
        agregarArista(11, 9, 335);
        agregarArista(11, 10, 403);

        prim();
    }

    static void agregarArista(int u, int v, int peso) {
        grafo.get(u).add(new Arista(u, v, peso));
        grafo.get(v).add(new Arista(v, u, peso));
    }

    static void prim() {
        boolean[] visitado = new boolean[V];
        PriorityQueue<Arista> cola = new PriorityQueue<>(Comparator.comparingInt(a -> a.peso));
        List<Arista> arbol = new ArrayList<>();
        Set<Integer> B = new HashSet<>();

        visitado[0] = true; // Empezamos en Coruña
        B.add(0);
        cola.addAll(grafo.get(0));

        // Encabezado bonito
        System.out.println("\n" + BOLD +
                "╔════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.printf("║  %-35s │ %-35s │ %-35s │ %-20s │ %-5s ║\n",
                "T (Árbol Parcial)", "B (Visitados)", "N/B (No Visitados)", "e = {u,v}", "Peso");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");

        while (arbol.size() < V - 1) {
            Arista a = cola.poll();

            if (!visitado[a.destino]) {
                visitado[a.destino] = true;
                arbol.add(a);
                B.add(a.destino);
                cola.addAll(grafo.get(a.destino));

                Set<Integer> NB = new HashSet<>();
                for (int i = 0; i < V; i++) if (!visitado[i]) NB.add(i);

                System.out.printf("║  %-35s │ %-35s │ %-35s │ %-20s │ %-5d ║\n",
                        CYAN + arbol.toString() + RESET,
                        GREEN + mostrar(B) + RESET,
                        YELLOW + mostrar(NB) + RESET,
                        a.toString(),
                        a.peso
                );
            }
        }

        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");

        System.out.println("\n" + BOLD + "🎯 Árbol de Recubrimiento Mínimo Final:" + RESET);
        for (Arista a : arbol) {
            System.out.printf("• %s — %s : %d\n", nombres[a.origen], nombres[a.destino], a.peso);
        }
    }

    static String mostrar(Set<Integer> conjunto) {
        List<String> lista = new ArrayList<>();
        for (int x : conjunto) lista.add(nombres[x]);
        return lista.toString();
    }
}

