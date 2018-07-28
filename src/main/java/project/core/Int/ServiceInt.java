package project.core.Int;

import java.security.interfaces.RSAPrivateKey;

public interface ServiceInt {
	public RSAPrivateKey getPrivateKey(String JKSFileName, String JKSAlias, String JKSPassword);
}
