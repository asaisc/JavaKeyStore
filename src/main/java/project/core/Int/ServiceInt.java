package project.core.Int;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public interface ServiceInt {
	public RSAPrivateKey getPrivateKey(String JKSFileName, String JKSAlias, String JKSPassword);
	public RSAPublicKey getPublicKey(String JKSFileName);
	public String firmar(String string);
}
