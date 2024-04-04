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
        leerReferencias();
        System.out.println("Hits: " + hits);
        System.out.println("Fallas: " + miss);

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
                this.memoriaVirtual.add(referencia + ",0");
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
            if (Integer.parseInt(referencia.substring(8, 9)) < marcos) {
                String key = referencia.substring(0, 7);
                if (!this.memoriaReal.containsKey(key)) {
                    this.memoriaReal.put(key, referencia.substring(8));
                }
            }
        }

        System.out.println(this.memoriaReal);
    }

    public void leerReferencias() {
        for (String referencia : this.memoriaVirtual) {
            String key = referencia.substring(0, 7);
            if (this.memoriaReal.containsKey(key)) {
                this.hits++;
                algoritmoLRU(referencia, true);
            } else {
                this.miss++;
                algoritmoLRU(referencia, false);
            }
        }

        System.out.println("------------------");
        System.out.println("Memoria real actualizada:");
        System.out.println(this.memoriaReal);
    }

    public void algoritmoLRU(String referencia, Boolean hit) {
        String key = referencia.substring(0, 7);
        String value = referencia.substring(8);

        if (hit) {
            this.memoriaReal.replace(key, value.substring(0, 5) + value.substring(5).replace('0', '2'));
        } else {

            for (String keyReal : this.memoriaReal.keySet()) {

                // Caso cuando hay pagina no referenciadas "0"
                if (this.memoriaReal.get(keyReal).substring(5).equals("0")) {
                    this.memoriaReal.remove(keyReal);
                    this.memoriaReal.put(key, value.replace(value.charAt(6), '1'));
                }

                // Cuando no esta referenciada pero si modificada "1"
                else if (this.memoriaReal.get(keyReal).substring(5).equals("1")) {
                    this.memoriaReal.remove(keyReal);
                    this.memoriaReal.put(key, value.replace(value.charAt(6), '3'));
                }

                else if (this.memoriaReal.get(keyReal).substring(5).equals("2")) {
                    this.memoriaReal.remove(keyReal);
                    if ((value.length() == 7 ? value.charAt(5) : value.charAt(4)) == 'W') {
                        this.memoriaReal.put(key, value.replace(value.charAt(6), '3'));
                    } else {
                        this.memoriaReal.put(key, value.replace(value.charAt(6), '2'));
                    }
                }

                else if (this.memoriaReal.get(keyReal).substring(5).equals("3")) {
                    this.memoriaReal.remove(keyReal);
                    this.memoriaReal.put(key, value.replace(value.charAt(6), '3'));

                }

            }

        }
    }

}
