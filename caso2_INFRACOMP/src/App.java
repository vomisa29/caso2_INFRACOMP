import java.util.Scanner;

public class App {
    private crearReferencias crearReferencias;
    private CalculoDatos calculoDatos;

    public static void main(String[] args) throws Exception {
        new App();
    }

    public App() {
        Boolean continuar = true;
        Scanner sc = new Scanner(System.in);
        while (continuar) {
            System.out.println("Caso 2 - INFRACOMP");
            System.out.println("1- Generación Referencias.");
            System.out.println("2- Calcular datos: número de fallas de página, porcentaje de hits, tiempos.");
            System.out.println("3- Salir\n");
            System.out.println("Que desea hacer: ");
            String entrada = sc.nextLine();

            switch (entrada) {
                case ("1"):
                    System.out.println("Digite: \n");
                    System.out.println("Tamaño Pagina (en bytes)");
                    int tp = Integer.valueOf(sc.nextLine());
                    System.out.println("Número Filas");
                    int nf = Integer.valueOf(sc.nextLine());
                    System.out.println("Número Columnas");
                    int nc = Integer.valueOf(sc.nextLine());
                    this.crearReferencias = new crearReferencias(tp, nf, nc);
                    this.crearReferencias.archivoReferencias();
                    break;

                case ("2"):
                    System.out.println("Opción 2");
                    System.out.println("Digite: \n");
                    System.out.println("Número de marcos de pagina");
                    int marcos = Integer.valueOf(sc.nextLine());
                    System.out.println("Nombre del archivo de referencias");
                    String archivo = sc.nextLine();
                    this.calculoDatos = new CalculoDatos(marcos, archivo);
                    this.calculoDatos.calcularDatos();
                    // this.calculoDatos.calcularDatos();
                    // Thread hilo1 = new LectorReferencias(this.calculoDatos);
                    // Thread hilo2 = new Restarter(this.calculoDatos);
                    // hilo1.start();
                    // hilo2.start();

                    // Iniciar los threads después de procesar las referencias iniciales.
                    // Thread threadEstado = new Thread(new ActualizadorEstado(calculoDatos));
                    // Thread threadBitR = new Thread(new ActualizadorBitR(calculoDatos));

                    // threadEstado.start();
                    // threadBitR.start();

                    // Espera a que los threads terminen (opcional, dependiendo de tu caso de uso).
                    // try {
                    // threadEstado.join();
                    // threadBitR.join();
                    // } catch (InterruptedException e) {
                    // e.printStackTrace();
                    // }

                    // break;

                case ("3"):
                    System.out.println("Fin Programa");
                    continuar = false;
                    sc.close();
                    break;

                default:
                    System.out.println("Digite una opción valida");
                    break;
            }
        }

    }
}
