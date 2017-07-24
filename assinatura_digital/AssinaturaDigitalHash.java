package src.util.assinatura_digital;

import src.util.RSA;

public class AssinaturaDigitalHash extends AssinaturaDigital {

	public static final int LENGTH_HASH = 20;

	public AssinaturaDigitalHash(int length, String msg){
		super(length, msg);
	}

	public AssinaturaDigitalHash(int[] key, String msg){
		super(key, msg);
	}

	protected String hash(String msg){
		if(msg.length() > LENGTH_HASH){
			msg = msg.substring(0,LENGTH_HASH);
		}
		return msg;
	}

	public String encript(int[] key, String msg){
		return new RSA(key).RSAEncrypt(msg);
	}
	public String decript(int[] key, String msg) {
		return new RSA(key, true).RSAEncrypt(msg);
	}
}