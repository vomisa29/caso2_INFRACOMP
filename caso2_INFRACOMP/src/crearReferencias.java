
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        this.sizePagina = (int) Math.floor((float) tp/sizeOfInt);
        System.out.println((float) tp/sizeOfInt);
        this.numInts = sideFiltro*sideFiltro + 2*nf*nc;
        this.paginasNecesarias = (int) Math.ceil((float) numInts/sizePagina);
    }

    public void archivoReferencias(){

        String nombreArchivo = "referencias_TP"+ tp + "_NF" + nf + "_NC" + nc + ".txt";
        Path path = Paths.get("datos/" + nombreArchivo);
        
        matricesToPaginas();//Se llena matrizPagina de información


        //COPIA DEL ALGORITMO
        //TODAS LAS REFERENCIAS/LINEAS DEL ARCHIVO SE CALCULAN EN ESTE PASO
        int nr = 0;
        ArrayList<String> referenciasArchivo = new ArrayList<String>();

        String posF="";
        String posM="";
        String posR="";
        posPagina paginaF;
        posPagina paginaM;
        posPagina paginaR;
        String lineaF="";
        String lineaM="";
        String lineaR="";

        for(int i=1;i<nf-1;i++){
            for(int j=1;j<nc-1;j++){
                //int acum = 0;
                for(int a=-1;a<=1;a++){
                    for(int b=-1;b<=1;b++){
                        int i2 = i+a;
                        int j2 = j+b;
                        int i3 = 1+a;
                        int j3 = 1+b;
                        //acum += (F[i3][j3]*M[i2][j2])
                        posF = "F" + "[" + i3  + "][" + j3 + "]";
                        paginaF = matrizPagina.get(posF);
                        lineaF = lineaArchivoString(posF, paginaF.getPagina(), paginaF.getDesplazamiento(), "R");

                        posM = "M" + "[" + i2  + "][" + j2 + "]";
                        paginaM = matrizPagina.get(posM);
                        lineaM = lineaArchivoString(posM, paginaM.getPagina(), paginaM.getDesplazamiento(), "R");

                        //NOTA: Es posible que toque cambiar el orden de estas dos lineas
                        referenciasArchivo.add(lineaM);
                        referenciasArchivo.add(lineaF);
                    }
                }

                //Aqui siempre se hace R[i][j] - W
                //if(acum>=0 && acum<=255){
                    //R[i][j] = acum
                //}else if(acum<0){
                    //R[i][j] = acum
                //}else{
                    //R[i][j] = 255
                // }
                posR = "R" + "[" + i  + "][" + j + "]";
                paginaR = matrizPagina.get(posR);
                lineaR = lineaArchivoString(posR, paginaR.getPagina(), paginaR.getDesplazamiento(), "W");
                referenciasArchivo.add(lineaR);
            }    
        }
        for(int i=0;i<nc;i++){
            //R[0][i]=0
            posR = "R" + "[" + 0 + "][" + i + "]";
            paginaR = matrizPagina.get(posR);
            lineaR = lineaArchivoString(posR, paginaR.getPagina(), paginaR.getDesplazamiento(), "W");
            referenciasArchivo.add(lineaR);
            //R[nf-1][i]=255
            posR = "R" + "[" + String.valueOf(nf-1) + "][" + i + "]";
            paginaR = matrizPagina.get(posR);
            lineaR = lineaArchivoString(posR, paginaR.getPagina(), paginaR.getDesplazamiento(), "W");
            referenciasArchivo.add(lineaR);

        }
        for(int i=1;i<nf-1;i++){
            //R[i][0]=0
            posR = "R" + "[" + i + "][" + 0 + "]";
            paginaR = matrizPagina.get(posR);
            lineaR = lineaArchivoString(posR, paginaR.getPagina(), paginaR.getDesplazamiento(), "W");
            referenciasArchivo.add(lineaR);
            //R[i][nc-1]=255
            posR = "R" + "[" + i + "][" + String.valueOf(nc-1) + "]";
            paginaR = matrizPagina.get(posR);
            lineaR = lineaArchivoString(posR, paginaR.getPagina(), paginaR.getDesplazamiento(), "W");
            referenciasArchivo.add(lineaR);
        }


        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8))
        {
            nr= referenciasArchivo.size();

            bw.write("TP=" + tp + "\n");
            bw.write("NF=" + nf + "\n");
            bw.write("NC=" + nc + "\n");
            bw.write("NF_NC_Filtro=" + sideFiltro + "\n");
            bw.write("NR=" + nr + "\n");
            bw.write("NP=" + paginasNecesarias + "\n");

            for (int i=0;i<referenciasArchivo.size();i++){
                if(i!=referenciasArchivo.size()-1){
                    String linea = referenciasArchivo.get(i);
                    bw.write(linea + "\n");
                }else{
                    String linea = referenciasArchivo.get(i);
                    bw.write(linea);
                }
                
            }

            System.out.println("\n-- Archivo creado correctamente --\n");

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String lineaArchivoString(String pos, int pagina, int desplazamiento, String read_write){
        return pos + "," + pagina + "," + desplazamiento + "," + read_write;
    }

    private void matricesToPaginas(){
        //Calcula en que parte de las paginas quedan los enteros de las matrices
        //Se guarde en una Tabla de Hash llamada matrizPagina
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
                String llavePos = nomMatriz[k] + "[" + posMatriz[0] +"][" + posMatriz[1] + "]";//posicion del entero en terminos de la matriz correspondiente
                posPagina posInt = new posPagina(i, 4*j);//posicion del entero en terminos de paginas y desplazamiento
                
                this.matrizPagina.put(llavePos,posInt);

                numIntMatrizActual-=1;

                if (numIntMatrizActual==0 && k!=2){
                    k+=1;
                    numIntMatrizActual = listaTamanioMatriz[k];        
                }
            }
        }

        //for (String llavePos : this.matrizPagina.keySet()){
        //    System.out.println(llavePos + " - " + this.matrizPagina.get(llavePos));
        //}

        //Descomentar este codigo para ver los valores almacenados en matrizPagina

    }

    private int[] numAPosMatriz(int num, int[] tamaniosMatriz){
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
