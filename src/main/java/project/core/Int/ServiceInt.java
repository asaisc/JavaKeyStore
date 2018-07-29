package project.core.Int;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public interface ServiceInt {
	public void getConfig();
	public RSAPrivateKey getPrivateKey(String JKSFileName, String JKSAlias, String JKSPassword) throws Exception;
	public RSAPublicKey getPublicKey(String JKSFileName);
	public String firmar(String string);
	public boolean validaFirma(String firma, RSAPublicKey publicKey);
	public String sign(String cadena) throws Exception;
}
