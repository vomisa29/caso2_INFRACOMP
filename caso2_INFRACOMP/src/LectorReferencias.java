
public class LectorReferencias extends Thread {

    private CalculoDatos calculoDatos;

    public LectorReferencias(CalculoDatos calculoDatos) {
        this.calculoDatos = calculoDatos;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // Suponiendo que CalculoDatos tiene un método para obtener la próxima
            // referencia.
            calculoDatos.leerReferencias();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
