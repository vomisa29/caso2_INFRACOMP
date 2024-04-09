public class Restarter extends Thread {

    private CalculoDatos calculoDatos;

    public Restarter(CalculoDatos calculoDatos) {
        this.calculoDatos = calculoDatos;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // Suponiendo que CalculoDatos tiene un método para obtener la próxima
            // referencia.
            calculoDatos.restart();
            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
