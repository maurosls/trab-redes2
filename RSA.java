
import static com.sun.org.apache.xml.internal.serialize.OutputFormat.Defaults.Encoding;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import static java.util.Objects.hash;

public class RSA {
    
  private static BigInteger n, d, e;

  public RSA(BigInteger d,BigInteger e,BigInteger n){
      this.d = d;
      this.n = n; 
      this.e = e;
  }
  
  public static void main(String[] args) throws FileNotFoundException, IOException {
    
    //teste funcionando
    BigInteger d = new BigInteger("29");
    BigInteger e = new BigInteger("1625");
    BigInteger n = new BigInteger("2881");
    RSA rsa = new RSA(d,e,n);
    String mesnsagem = "teste de RSA";
    System.out.println("teste = "+mesnsagem);
    cifraStr(mesnsagem);
    String v = "";
    
    BigInteger dois[] = cifraStr(mesnsagem);
    
    String essa = decifraStr(dois);
    System.out.println("DOIIS: "+essa);
      
      BigInteger thise [] = new BigInteger[mesnsagem.length()] ; 
      for (int i = 0; i < mesnsagem.length(); i++) {
        String ez = "";
        ez += mesnsagem.charAt(i);
        BigInteger aux1 = new BigInteger(ez.getBytes());
        BigInteger ciphertext1 = RSAEncrypt(aux1);   
        thise[i] = ciphertext1;
      }
      for (int i = 0; i < thise.length; i++) {
         BigInteger aux = RSADecrypt(thise[i]);
         String tx = new String(aux.toByteArray());        
         v+=tx;
      }
      
//        BigInteger plaintext1 = RSADecrypt(ciphertext1);
//        String tx = new String(plaintext1.toByteArray());        
//        v+=tx;
         System.out.println("v: "+v);
      }     
      
    

    public static BigInteger RSAEncrypt (BigInteger text){
       return text.modPow(e, n);
    }
    
    public static BigInteger RSADecrypt(BigInteger text){
       return text.modPow(d, n);
    }
    
    public void swapKey(){
        BigInteger aux = d;
        d = e;
        e = aux;
    }
    
    static BigInteger[] cifraStr(String nonce) {
        
        BigInteger thise [] = new BigInteger[nonce.length()] ; 
        for (int i = 0; i < nonce.length(); i++) {
            String ez = "";
            ez += nonce.charAt(i);
            BigInteger aux1 = new BigInteger(ez.getBytes());
            BigInteger ciphertext1 = RSAEncrypt(aux1);   
            thise[i] = ciphertext1;
      }
//        String v[] =new String[nonce.length()];
//        for (int i = 0; i < nonce.length(); i++) {
//            String e = "";
//            e += nonce.charAt(i);
//            BigInteger aux = new BigInteger(e.getBytes());
//            BigInteger ciphertext = RSAEncrypt(aux);   
//            String tx = new String(ciphertext.toByteArray());        
//            v[i]=tx;
//        }
//        return v;
        return thise;
    }
    
    static String decifraStr(BigInteger thise[]) {
        String v = "";
        for (int i = 0; i < thise.length; i++) {
            BigInteger aux = RSADecrypt(thise[i]);
            String tx = new String(aux.toByteArray());        
            v+=tx;
        }
        return v;
//        String v = "";
//        for (int i = 0; i < nonce.length; i++) {
//            String e ="";
//            e += nonce;
//            BigInteger aux = new BigInteger(e.getBytes());
//            BigInteger ciphertext = RSADecrypt(aux);   
//            String tx = new String(ciphertext.toByteArray());        
//            v+=tx;
//        }
//        return v;    
    }

    byte[] RSAEncrypt(byte[] buffer) {
        BigInteger b = new BigInteger(buffer);
        return  (b.modPow(e, n).toByteArray());   
  
    }

    byte[] RSADecrypt(byte[] buffer) {
        BigInteger b = new BigInteger(buffer);
        return  (b.modPow(d, n).toByteArray());      
    }
}
