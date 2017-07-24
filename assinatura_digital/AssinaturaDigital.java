package src.util.assinatura_digital;

import src.util.RSA;
import src.conf.Conf;

public abstract class AssinaturaDigital {

	private String msg;
	private String encMsg;

	public AssinaturaDigital(int length, String msg){
		this.msg = msg.substring(0,length);
		this.encMsg = msg.substring(length);
	}

	public AssinaturaDigital(int[] key, String msg){
		this.msg = msg;
		this.encMsg = encript(key, msg);
	}

	protected abstract String hash(String msg);
	public abstract String encript(int[] key, String msg);
	public abstract String decript(int[] key, String msg);

	public String getMensagem(){
		return this.msg;
	}

	public String getEncMensagem(){
		return this.encMsg;
	}

	public String toString(){
		return this.msg + this.encMsg;
	}

	public boolean isValid(int[] key){
		boolean result = false;
		try{
			result = decript(key,hash(this.encMsg)).equals(msg);
			
		}catch(Exception e){
			System.err.println("Impossível criar a assinatura");
		}
		return result;
	}
}