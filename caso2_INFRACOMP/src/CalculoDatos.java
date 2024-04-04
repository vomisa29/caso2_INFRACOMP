import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CalculoDatos {

    private int miss = 0;
    private int hits = 0;
    private int marcos;
    private String archivo;
    private HashMap<String, String> memoriaReal = new HashMap<String, String>();
    private ArrayList<String> memoriaVirtual = new ArrayList<String>();

    public CalculoDatos(int marcos, String archivo) {
        this.marcos = marcos;
        this.archivo = archivo;
    }

    public void calcularDatos() {

        // Leer archivo de referencias
        // HashMap virtual
        llenarListaVirtual();
        llenarHashMapReal();
        // calcularHits();
        // calcularFallas();

    }

    public void llenarListaVirtual() {
        try {
            FileReader fr = new FileReader("datos/" + this.archivo);
            BufferedReader br = new BufferedReader(fr);

            String referencia;
            for (int i = 0; i < 6; i++) {
                try {
                    referencia = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            while ((referencia = br.readLine()) != null) {
                // Imprime cada línea en la consola
                if (!this.memoriaVirtual.contains(referencia)) {
                    this.memoriaVirtual.add(referencia + ",0");
                }
            }

            // Cierra el BufferedReader
            br.close();
            // System.out.println(memoriaVirtual);
            // System.out.println(memoriaVirtual.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void llenarHashMapReal() {
        for (String referencia : this.memoriaVirtual) {
            if (Integer.parseInt(referencia.substring(8, 9)) <= marcos) {
                String key = referencia.substring(0, 7);
                if (!this.memoriaReal.containsKey(key)) {
                    this.memoriaReal.put(key, referencia.substring(8) + ",0");
                }
            }
        }

        System.out.println(this.memoriaReal);
    }

    // public void calcularHits() {
    // for (String referencia : this.memoriaVirtual) {
    // String key = referencia.substring(0, 7);
    // if (this.memoriaReal.containsKey(key)) {
    // this.hits++;
    // }
    // }

    // System.out.println("Hits: " + hits);
    // }

    // public void calcularFallas() {
    // for (String referencia : this.memoriaVirtual) {

    // String key = referencia.substring(0, 7);
    // if (!this.memoriaReal.containsKey(key)) {
    // miss++;
    // }
    // }

    // System.out.println("Fallas: " + miss);
    // }

    public void leerReferencias() {
        for (String referencia : this.memoriaVirtual) {
            String key = referencia.substring(0, 7);
            if (this.memoriaReal.containsKey(key)) {
                this.hits++;
            } else {
                this.miss++;
                algoritmoLRU(referencia);
            }
        }
    }

    public void algoritmoLRU(String referencia) {
        String key = referencia.substring(0, 7);
        String value = referencia.substring(8);

        for (String keyReal : this.memoriaReal.keySet()) {

            // Caso cuando hay paginas no referenciadas
            if (this.memoriaReal.get(keyReal).substring(13, 14).equals("0")) {
                this.memoriaReal.remove(keyReal);
                this.memoriaReal.put(key, value.replace(value.charAt(14), '1'));
            }

            // Logica demas casos
        }
    }
}
