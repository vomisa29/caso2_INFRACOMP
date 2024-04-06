public class ActualizadorBitR implements Runnable {
    private final CalculoDatos calculoDatos;

    public ActualizadorBitR(CalculoDatos calculoDatos) {
        this.calculoDatos = calculoDatos;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            
            
            calculoDatos.actualizarBitsR();

            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                
                Thread.currentThread().interrupt(); 
            }
        }
    }
}
