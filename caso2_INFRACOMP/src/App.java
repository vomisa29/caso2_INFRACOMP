public class App {
    private crearReferencias crearReferencias;
    public static void main(String[] args) throws Exception {
        new App();
    }

    public App(){
        this.crearReferencias=new crearReferencias(9,2,2);
        crearReferencias.matricesToPaginas();
    }
}
