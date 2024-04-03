public class App {
    private crearReferencias crearReferencias;
    public static void main(String[] args) throws Exception {
        new App();
    }

    public App(){
        this.crearReferencias=new crearReferencias(16,4,4);
        crearReferencias.archivoReferencias();
    }
}
