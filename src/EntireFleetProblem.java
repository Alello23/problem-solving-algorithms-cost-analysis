import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class EntireFleetProblem {
        public static void BackTracking_Problem(ArrayList<Center> centro){
            BackTracking_EntrieFleet.Backtracking(centro);
        }
        public static void Greedy_Problem(ArrayList<Center> centro){
            Greedy_EntireFleet best_child = null;
            boolean[] found = {true,true,true,true,true,true};
            boolean flag = false;
            // Iniciaització de l'array de distàncies mínimes
            int best = Integer.MAX_VALUE;

            // Priority Queue (Cua de prioritat) que farem servir per navegar l'espai de solucions de forma "dinàmica"
            // A Java, la PriorityQueue fa servir la interfície Comparable per ordenar els seus elements
            PriorityQueue<Greedy_EntireFleet> cua = new PriorityQueue<>();
            PriorityQueue<Greedy_EntireFleet> queue = new PriorityQueue<>();

            // Creem la configuració inicial i l'encuem
            Greedy_EntireFleet first = new Greedy_EntireFleet(centro);
            queue.add(first);

            // Repetim el següent procés fins que no quedin més configuracions a considerar
            while(!queue.isEmpty()) {
                // Agafem la primera configuració de la cua (major prioritat / menor cost estimat)
                Greedy_EntireFleet config = queue.poll();

                // L'expandim, obtenint els successors
                List<Greedy_EntireFleet> children = config.expand(centro);

                // Per cadascun dels successors
                for (Greedy_EntireFleet child : children) {
                    // Si és solució (hem pres totes les decisions)
                    if (child.isFull()) {
                        // Procés d'optimització
                        if (child.getCost() < best && Arrays.equals(child.found_types, found)) {
                            best = child.getCost();
                            // Printem per debugar
                            //System.out.println(child);
                            //System.out.println("Best one!");
                            best_child = child;
                            flag = true;
                        }
                    } else {
                        // Si no és solució (encara queden decisions per prendre)
                        // PBMSC (depenent de l'heurística, podríem fer servir el cost estimat aquí)
                        if (child.getCost() < best) {
                            // Afegim el successor a la cua, que farà servir el seu cost estimat per determinar quan explorar-lo
                            queue.offer(child);
                        } else {
                            // Debugging comentat per evitar gastar més recursos del compte
                            //System.out.println(child);
                            //System.out.println("PBCBS");
                        }
                    }
                }
            }
            if (!flag){
                System.out.println("No hay solucion");
            }
            else {
                System.out.println("\n--- Best Solution ---");
                for (int i = 0; i < best_child.config.length; i++) {
                    System.out.println("Center: " + centro.get(best_child.config[i]).name + ", Types: " + centro.get(best_child.config[i]).types);
                }
                System.out.println("--- Total centers: " + best_child.getCost() + " ---");
            }
        }
}

