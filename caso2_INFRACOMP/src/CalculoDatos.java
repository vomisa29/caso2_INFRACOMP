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
    private int numRegistros;
    private double porcentajeHits;
    private HashMap<String, ArrayList<String>> memoriaReal = new HashMap<String, ArrayList<String>>();
    private ArrayList<String> memoriaVirtual = new ArrayList<String>();

    public CalculoDatos(int marcos, String archivo) {
        this.marcos = marcos;
        this.archivo = archivo;
    }

    public void calcularDatos() {

        llenarListaVirtual();
        llenarHashMapReal();
        leerReferencias();
        System.out.println("Hits: " + this.hits);
        System.out.println("Fallas: " + this.miss);
        System.out.println("Numero referencias: " + this.numRegistros);
        this.porcentajeHits = (double) this.hits / (this.numRegistros) * 100;
        this.porcentajeHits = Math.round(porcentajeHits * 100.0) / 100.0;
        System.out.println("Porcentaje de hits: " + porcentajeHits + "%");
    }

    public void llenarListaVirtual() {
        try {
            FileReader fr = new FileReader("datos/" + this.archivo);
            BufferedReader br = new BufferedReader(fr);

            String referencia;
            for (int i = 1; i < 7; i++) {
                try {
                    referencia = br.readLine();
                    if (i == 5) {
                        this.numRegistros = Integer.parseInt(referencia.substring(3));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            while ((referencia = br.readLine()) != null) {
                this.memoriaVirtual.add(referencia + ",0");
            }

            br.close();
            System.out.println(memoriaVirtual);
            System.out.println(memoriaVirtual.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void llenarHashMapReal() {
        for (String referencia : this.memoriaVirtual) {
            if (Integer.parseInt(referencia.substring(8, 10).replace(",", "")) < marcos) {
                String key = referencia.substring(8, 10).replace(",", "");
                if (!this.memoriaReal.containsKey(key)) {
                    ArrayList<String> lista = new ArrayList<String>();
                    lista.add("3");
                    // lista.add(referencia);
                    this.memoriaReal.put(key, lista);
                }
            }

        }
        this.miss = this.memoriaReal.size();
        this.hits = -this.memoriaReal.size();

        System.out.println(this.memoriaReal);
        System.out.println("Tamanio memoria real " + this.memoriaReal.size());
    }

    public void leerReferencias() {
        for (String referencia : this.memoriaVirtual) {
            String pagina = referencia.substring(8, 10).replace(",", "");
            if (this.memoriaReal.containsKey(pagina)) {
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
        String pagina = referencia.substring(8, 10).replace(",", "");
        char operacion = referencia.charAt(referencia.length() - 3);

        if (hit) {
            String bit = this.memoriaReal.get(pagina).get(0);
            if (bit == "0") {
                if (operacion == 'W') {
                    this.memoriaReal.get(pagina).set(0, "1");
                } else {
                    this.memoriaReal.get(pagina).set(0, "2");
                }

            } else if (bit == "1") {
                if (operacion == 'W') {
                    this.memoriaReal.get(pagina).set(0, "3");
                } else {
                    this.memoriaReal.get(pagina).set(0, "3");

                }
            } else if (bit == "2") {
                if (operacion == 'W') {
                    this.memoriaReal.get(pagina).set(0, "3");
                } else {
                    this.memoriaReal.get(pagina).set(0, "2");

                }
            } else if (bit == "3") {
                if (operacion == 'W') {
                    this.memoriaReal.get(pagina).set(0, "3");
                } else {
                    this.memoriaReal.get(pagina).set(0, "3");

                }
            }
        } else {
            Boolean cambio = false;
            for (int i = 0; i < 4; i++) {
                for (String keyReal : this.memoriaReal.keySet()) {
                    String bitAnterior = this.memoriaReal.get(keyReal).get(0);
                    String paginaNueva = referencia.substring(8, 10).replace(",", "");

                    ArrayList<String> lista = new ArrayList<String>();
                    if (bitAnterior == "0" && i == 0) {
                        if (operacion == 'W') {
                            lista.add("1");
                            this.memoriaReal.put(paginaNueva, lista);
                            this.memoriaReal.remove(keyReal);
                            cambio = true;
                            break;
                        } else {
                            lista.add("2");
                            this.memoriaReal.remove(keyReal);
                            cambio = true;
                            break;
                        }

                    } else if (bitAnterior == "1" && i == 1) {
                        if (operacion == 'W') {
                            lista.add("3");
                            this.memoriaReal.put(paginaNueva, lista);
                            this.memoriaReal.remove(keyReal);
                            cambio = true;
                            break;
                        } else {
                            lista.add("3");
                            this.memoriaReal.put(paginaNueva, lista);
                            this.memoriaReal.remove(keyReal);
                            cambio = true;
                            break;
                        }
                    } else if (bitAnterior == "2" && i == 2) {
                        if (operacion == 'W') {
                            lista.add("3");
                            this.memoriaReal.put(paginaNueva, lista);
                            this.memoriaReal.remove(keyReal);
                            cambio = true;
                            break;
                        } else {
                            lista.add("2");
                            this.memoriaReal.put(paginaNueva, lista);
                            this.memoriaReal.remove(keyReal);
                            cambio = true;
                            break;
                        }
                    } else if (bitAnterior == "3" && i == 3) {
                        if (operacion == 'W') {
                            lista.add("3");
                            this.memoriaReal.put(paginaNueva, lista);
                            this.memoriaReal.remove(keyReal);
                            cambio = true;
                            break;
                        } else {
                            lista.add("3");
                            this.memoriaReal.put(paginaNueva, lista);
                            this.memoriaReal.remove(keyReal);
                            cambio = true;
                            break;
                        }
                    }
                }
                if (cambio) {
                    break;
                }
            }

        }
    }

    public void restart() {
        for (String key : this.memoriaReal.keySet()) {
            String value = this.memoriaReal.get(key).get(0);
            if (value == "3") {
                ArrayList<String> lista = new ArrayList<String>();
                lista.add("1");
                this.memoriaReal.replace(key, lista);
            } else if (value == "2") {
                ArrayList<String> lista = new ArrayList<String>();
                lista.add("0");
                this.memoriaReal.replace(key, lista);
            }
        }
    }
}
