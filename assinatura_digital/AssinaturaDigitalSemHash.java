package src.util.assinatura_digital;

import src.util.RSA;

public class AssinaturaDigitalSemHash extends AssinaturaDigital {

	public AssinaturaDigitalSemHash(int length, String msg){
		super(length, msg);
	}

	public AssinaturaDigitalSemHash(int[] key, String msg){
		super(key, msg);
	}

	protected String hash(String msg){
		return msg;
	}

	public String encript(int[] key, String msg){
		return new RSA(key).RSAEncrypt(msg);
	}
	public String decript(int[] key, String msg){
		return new RSA(key, true).RSAEncrypt(msg);
	}
}