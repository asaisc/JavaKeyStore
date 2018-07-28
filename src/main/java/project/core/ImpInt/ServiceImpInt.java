package project.core.ImpInt;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import project.core.Int.ServiceInt;

public class ServiceImpInt implements ServiceInt {

	public RSAPrivateKey getPrivateKey(String JKSFileName, String JKSAlias, String JKSPassword) {
		RSAPrivateKey privateKey = null;
		return privateKey;
	}

	public RSAPublicKey getPublicKey(String JKSFileName) {
		RSAPublicKey publicKey = null;
		return publicKey;
	}

	public String firmar(String string) {
		String firma = "";
		return firma;
	}

}
