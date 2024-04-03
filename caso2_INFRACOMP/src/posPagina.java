public class posPagina {
    private int pagina;
    private int desplazamiento;

    public posPagina(int pagina, int desplazamiento){
        this.pagina = pagina;
        this.desplazamiento = desplazamiento;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getDesplazamiento() {
        return desplazamiento;
    }

    public void setDesplazamiento(int desplazamiento) {
        this.desplazamiento = desplazamiento;
    }

    @Override
    public String toString() {
        return "posPagina [pagina=" + pagina + ", desplazamiento=" + desplazamiento + "]";
    }
}
