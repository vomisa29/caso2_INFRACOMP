
import java.util.ArrayList;
import java.util.HashMap;
public class crearReferencias {
    
    private HashMap<String,posPagina> matrizPagina = new HashMap<>();
    private final int sideFiltro = 3;
    private final int sizeOfInt = 4;
    private int tp;
    private int nf;
    private int nc;
    private int paginasNecesarias;
    private int numInts;
    private int sizePagina;


    public crearReferencias(int tp, int nf, int nc){
        this.tp = tp;
        this.nf = nf;
        this.nc = nc;
        this.sizePagina = (int) Math.floor(tp/sizeOfInt);
        this.numInts = sideFiltro*sideFiltro + 2*nf*nc;
        this.paginasNecesarias = (int) Math.ceil((float) numInts/sizePagina);
        System.out.println("numInts " + numInts);
        System.out.println("paginasNecesarias " + paginasNecesarias);
        System.out.println("sizePagina " + sizePagina);
    }

    public void archivoReferencias(){
        ArrayList<String> lineasArchivo = new ArrayList<String>();
        matricesToPaginas();//Se llena matrizPagina de información

        //COPIA DEL ALGORITMO
        //RECUERDA REEMPLAZAR LOS COMENTARIOS CON SETTERS DE R y W
        for(int i=1;i<nf;i++){
            for(int j=1;j<nc;j++){
                int acum = 0;
                for(int a=-1;a<=1;a++){
                    for(int b=-1;b<=1;b++){
                        int i2 = i+a;
                        int j2 = j+b;
                        int ie = 1+a;
                        int je = 1+b;
                        //acum += (F[i3][j3]*M[i2][j2])
                    }
                }
                if(acum>=0 && acum<=255){
                    //R[i][j] = acum
                }else if(acum<0){
                    //R[i][j] = acum
                }else{
                    //R[i][j] = 255
                }
            }
        }

        for(int i=0;i<nc;i++){
            //R[0][i]=0
            //R[nf-1][i]=255
        }
        for(int i=1;i<nf;i++){
            //R[i][0]=0
            //R[i][nc-1]=255
        }
    }

    public void matricesToPaginas(){

        int numIntFiltro = sideFiltro*sideFiltro;
        int numIntDatos = nf*nc;
        int numIntResultado = nf*nc;
        int[][] tamanioMatrices = {{sideFiltro,sideFiltro},{nf,nc},{nf,nc}};
        int[] listaTamanioMatriz = {numIntFiltro,numIntDatos,numIntResultado};
        String[] nomMatriz = {"F","M","R"};

        int k=0;
        int numIntMatrizActual = listaTamanioMatriz[k];
        int contador = 0;

        for (int i = 0; i<paginasNecesarias; i++){
            for (int j = 0; j<sizePagina;j++){
                contador++;
                if(contador>this.numInts){
                    break;
                }
                int[] posMatriz = numAPosMatriz(numIntMatrizActual, tamanioMatrices[k]);
                String llavePos = nomMatriz[k] + "[" + String.valueOf(posMatriz[0]) +"][" + String.valueOf(posMatriz[1]) + "]";//posicion del entero en terminos de la matriz correspondiente
                posPagina posInt = new posPagina(i, j);//posicion del entero en terminos de paginas y desplazamiento
                
                this.matrizPagina.put(llavePos,posInt);

                numIntMatrizActual-=1;

                if (numIntMatrizActual==0){
                    if (k!=2){
                        k+=1;
                        numIntMatrizActual = listaTamanioMatriz[k];
                    }
                }
            }
        }

        for (String llavePos : this.matrizPagina.keySet()){
            System.out.println(llavePos + " - " + this.matrizPagina.get(llavePos));
        }

    }

    public int[] numAPosMatriz(int num, int[] tamaniosMatriz){
        //Porfavor no modificar, no se como funciona
        //Toma un número num y lo convierte a una posición i,j en una matriz
        int nf = tamaniosMatriz[0];
        int nc = tamaniosMatriz[1];
        num = (nf*nc+1)-num;
        int contador = 0;
        int posi=0;
        int posj=0;
        for(int i=0;i<nf;i++){
            for(int j=0;j<nc;j++){
                contador++;
                if(contador == num){
                    posi=i;
                    posj=j;
                    break;
                }
                
            }
        }
        
        int[] rta = {posi,posj};
        return rta;
    }



    public HashMap<String, posPagina> getMatrizPagina() {
        return matrizPagina;
    }

    public void setMatrizPagina(HashMap<String, posPagina> matrizPagina) {
        this.matrizPagina = matrizPagina;
    }

    public int getSideFiltro() {
        return sideFiltro;
    }

    public int getSizeOfInt() {
        return sizeOfInt;
    }

    public int getTp() {
        return tp;
    }

    public void setTp(int tp) {
        this.tp = tp;
    }


    public int getPaginasNecesarias() {
        return paginasNecesarias;
    }

    public void setPaginasNecesarias(int paginasNecesarias) {
        this.paginasNecesarias = paginasNecesarias;
    }

    public int getNumInts() {
        return numInts;
    }

    public void setNumInts(int numInts) {
        this.numInts = numInts;
    }

    public int getSizePagina() {
        return sizePagina;
    }

    public void setSizePagina(int sizePagina) {
        this.sizePagina = sizePagina;
    }
    
}
