import java.util.Scanner;

public class App {
    private crearReferencias crearReferencias;
    public static void main(String[] args) throws Exception {
        new App();
    }

    public App(){
        Boolean continuar = true;
        Scanner sc = new Scanner(System.in);
        while(continuar){
            System.out.println("Caso 2 - INFRACOMP");
            System.out.println("1- Generación Referencias.");
            System.out.println("2- Calcular datos: número de fallas de página, porcentaje de hits, tiempos.");
            System.out.println("3- Salir\n");
            System.out.println("Que desea hacer: ");
            String entrada = sc.nextLine();

            switch(entrada){
                case("1"):
                    System.out.println("Digite: \n");
                    System.out.println("Tamaño Pagina (en bytes)");
                    int tp = Integer.valueOf(sc.nextLine());
                    System.out.println("Número Filas");
                    int nf = Integer.valueOf(sc.nextLine());
                    System.out.println("Número Columnas");
                    int nc = Integer.valueOf(sc.nextLine());
                    this.crearReferencias=new crearReferencias(tp,nf,nc);
                    this.crearReferencias.archivoReferencias();
                    break;
                case("2"):
                    System.out.println("Opción 2");
                    break;
                case("3"):
                    System.out.println("Fin Programa");
                    continuar=false;
                    sc.close();
                    break;

                default:
                    System.out.println("Digite una opción valida");
                break;
            }
        }
        
    }
}
