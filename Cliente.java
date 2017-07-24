
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
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

//rodar depois do servidor
public class Cliente {    
    public static void main(String[] args) throws FileNotFoundException, IOException {          
        System.out.println("--------CLIENTE---------");
       
        Scanner teclado = new Scanner(System.in);
       //pego do arquivo a senha gerada pelo Gerador de chave simétrica
       
        // Cria um Socket cliente passando como parâmetro o ip e a porta do servidor     
        System.out.println("Diga o hostname do servidor que deseja se conectar: ");
        String nome = teclado.nextLine();
        
        try{
            Socket client = new Socket(nome, 40000);
        
            System.out.println("Cliente conectado ao servidor.");    
               
            BigInteger n,d,e;
            System.out.println("Digite seu par de chaves [(n,e) (n,d)]: ");
            System.out.println("n: ");
            n = new BigInteger(Integer.toString(teclado.nextInt()));
            System.out.println("e: ");
            e = new BigInteger(Integer.toString(teclado.nextInt()));
            System.out.println("d: ");
            d = new BigInteger(Integer.toString(teclado.nextInt()));
            RSA rsa = new RSA(d,e,n);

            while(true){
            DataOutputStream outToServer = new DataOutputStream(client.getOutputStream());

            DataInputStream inFromServer = new DataInputStream(client.getInputStream());
            //DataInputStream in = new DataInputStream(sock.getInputStream());
            
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());  
   
            //AUTENTICAÇÃO DAS PARTES
            //protocolo ap5.0
            System.out.println("Autenticando com o servidor....");    
            String nonce = inFromServer.readLine();
            System.out.println("Nonce recebido: "+nonce);
            
            rsa.swapKey(); //muda para cifrar com privada
            BigInteger nova[] = RSA.cifraStr(nonce);
            oos.writeObject(nova);

            System.out.println("Nonce cifrado enviado pro servidor.");
            rsa.swapKey();//volta a cifrar com pública



            System.out.println("Diga o nome do arquivo que deseja transferir: ");
            Scanner in = new Scanner(System.in);
            nome = in.nextLine();
            FileInputStream filein = new FileInputStream(nome);

            System.out.println("Imagem sendo enviada para o servidor: ");            
            ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());



            //DIVISÃO DO ARQUIVO EM BLOCOS E ENVIO DE ARQUIVO                   
            byte[] buffer = new byte[1];  
            int len;
            //ENVIO DE ARQUIVO 
            //COM CRIPTOGRAFIA RSA 
//            while ((len = filein.read(buffer)) >= 0) {
//                String str = new String(buffer, StandardCharsets.UTF_8);
//                BigInteger bi[] = RSA.cifraStr(nonce);
//                oos.writeObject(bi);
//                //out.write(buffer, 0, len);
//            }
            
            //ENVIO DE ARQUIVO 
            while ((len = filein.read(buffer)) >= 0) {
                out.write(buffer, 0, len);
            }

    
    
            //CALCULA CRC e envia pro servidor 
            System.out.println("");

            client.close(); 
        }
        
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block

        } catch (IOException e) {
            // TODO Auto-generated catch block

        }
                
    } 
    
} 

