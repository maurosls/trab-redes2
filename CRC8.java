
import java.io.File;

public class CRC8 {
    
    //Polinômio gerador
    //8bit-CRC: 0x07  =  x8  + x2  + x + 1   
    static int divisor[] = {1,0,0,0,0,0,1,1,1};
    
    //teste
    private int data[]; 
    //não final
    File arq;
    
    int crc [];
       
    //FALTA ISSO
    public CRC8 (File file){
        //transforma arquivo de entrada em um dado binário  
        this.arq = file;
    }
    
    //PARA TESTAR
    public CRC8(int[] data){
        this.data = data;
        System.out.print("data ");
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i]);           
        }
        System.out.println("");
    }
    
    //retorna o CRC calculado
    public int[] getCRC(){
        crc = divide(data,divisor);
        System.out.print("CRC: ");
        for (int i = 0; i < crc.length; i++) {
            System.out.print(crc[i]);
        }
        return crc;       
    }
    
    //calcula o XOR
    static int exor(int a, int b) {
        if(a == b) {
            return 0;
	}
	return 1;
    }
    
    //completa com 0, somando a quantidade correspondente ao tipo de crc
    static int[] completaZeros(int[] vet){
        int aux[] = new int [vet.length+8];
        for (int i = 0; i < vet.length; i++) {
            aux[i] = vet[i];           
        }      
        for (int i = vet.length; i < aux.length; i++) {
            aux[i] = 0;
       }
        return aux;
    }

    //processa a "divisão" entre o dado e o divisor
    private int[] divide(int[] data, int[] divisor) {
        int tempData[] = completaZeros(data);
        int tempDiv[] = new int[tempData.length];       
        for (int i = 0; i < divisor.length; i++) {
            tempDiv[i] = divisor[i];       
        }       
        for (int i = 0; i < data.length; i++) {
            tempData[i] = data[i];
        }       
        
        for (int j = 0; j < data.length; j++) {        
            if(tempData[j]==1){           
                for (int i = 0;  i< divisor.length; i++){
                    tempData[i+j] = exor(tempData[i+j],tempDiv[i]);         
                }
            }
        }    
        return tempData;
    }
    
    public void escreve(int[] vet){
        for (int i = 0; i < vet.length; i++) {
            System.out.print(vet[i]);
        }
        System.out.println("");
    }
}
