
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import static java.util.Objects.hash;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


//RODAR ANTES DO CLIENTE
public class Servidor {    
    public static void main(String[] args) throws FileNotFoundException, IOException, IllegalBlockSizeException, ClassNotFoundException {       
        System.out.println("--------SERVIDOR---------");       
        Scanner teclado = new Scanner(System.in);
        
        try {
            //gero um servidor
            ServerSocket socket = new ServerSocket(40000);
            System.out.println("Servidor online.");
            while(true){
            //espero o cliente tentar se conectar
            Socket connectionSocket = socket.accept();
            System.out.println("Servidor conectado ao cliente.");                            
              
            
            BigInteger n,d,e;             
            System.out.println("Digite seu par de chaves [(n,e) (n,d)]: ");
            System.out.println("n: ");
            n = new BigInteger(Integer.toString(teclado.nextInt()));
            System.out.println("e: ");
            e = new BigInteger(Integer.toString(teclado.nextInt()));
            System.out.println("d: ");
            d = new BigInteger(Integer.toString(teclado.nextInt()));
            RSA rsa = new RSA(d,e,n);
            

            //AUTENTICAÇÃO DAS PARTES
            //protocolo ap5.0
            while(true) {    

            System.out.println("Digite um numero(nonce): ");
            Scanner tec = new Scanner(System.in);
            String nonce = tec.nextLine();  
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());          
            outToClient.writeBytes(nonce + "\n");               
            System.out.println("Nonce enviado: "+nonce);    
            
            
            DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
            ObjectInputStream ois = new ObjectInputStream(connectionSocket.getInputStream());  
            
            
            BigInteger nonceCifrado[] = (BigInteger[]) ois.readObject(); 
            rsa.swapKey();//para decifrar com a privada
            String nova = RSA.decifraStr(nonceCifrado);                   
            if(nova.equals(nonce)){
                System.out.println("Cliente autenticado.");
            } else{
                System.out.println("Cliente não reconhecido");
                connectionSocket.close();
            } 
            rsa.swapKey();//volta pra pública
            
            
            ObjectInputStream out = new ObjectInputStream(connectionSocket.getInputStream());//
            System.out.println("Diga o nome do arquivo que será guardado: ");
            Scanner in = new Scanner(System.in);

            String nome = in.nextLine();
            FileOutputStream fileout = new FileOutputStream(nome);
            


            byte[] buffer = new byte[1];
            int len;  
            BigInteger ce[];           
            //RECEBIMENTO DE ARQUIVO COM CRIPTOGRAFIA RSA
//           while(true){
//               if((ce =(BigInteger[]) ois.readObject()) == null) break; 
//                BigInteger no[] = (BigInteger[]) ois.readObject(); 
//                String ne = RSA.decifraStr(no);  
//                byte[] b = ne.getBytes();
//                fileout.write(b, 0, b.length);                
//            } //ainda com erro
            
            //RECEBIMENTO DE ARQUIVO 
            while((len=out.read(buffer)) > 0){ 
                fileout.write(buffer, 0, len);                
            }
            //funciona

            //CALCULA CRC e compara com o recebido pelo cliente
            System.out.println("");
            
            socket.close();
            break;
            }
            }                      
       } catch (IOException e) {
       }  
    } 
}
