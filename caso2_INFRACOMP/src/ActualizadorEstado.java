public class ActualizadorEstado implements Runnable {
    private final CalculoDatos calculoDatos;

    public ActualizadorEstado(CalculoDatos calculoDatos) {
        this.calculoDatos = calculoDatos;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // Suponiendo que CalculoDatos tiene un método para obtener la próxima referencia.
            String referencia = calculoDatos.obtenerProximaReferencia();
            
            if (referencia != null) {
                // Procesa la referencia obtenida.
                calculoDatos.procesarReferencia(referencia);
            } else {
                break;
            }
            
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); 
            }
        }
    }
}

