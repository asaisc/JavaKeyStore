package project.core.ImpInt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Properties;

import project.core.Int.ServiceInt;
import project.utils.Constantes;
import sun.misc.BASE64Encoder;

public class ServiceImpInt implements ServiceInt {
	public static String JKSFileName;
	public static String JKSAlias;
	public static String JKSPassword;
	public static Properties prop;
	
	public void getConfig() {
		
		InputStream input = getClass().getClassLoader().getResourceAsStream(Constantes.PROPERTIES_FILE);
		
		prop = new Properties();

		try {
			// load a properties file
			prop.load(input);

			JKSFileName = prop.getProperty("config.jks.filename");
			JKSAlias = prop.getProperty("config.jks.alias");
			JKSPassword = prop.getProperty("config.jks.password");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}

	public RSAPrivateKey getPrivateKey(String JKSFileName, String JKSAlias, String JKSPassword) throws Exception {
		RSAPrivateKey privateKey;
        try {
        	InputStream input = getClass().getClassLoader().getResourceAsStream(JKSFileName);
            KeyStore keystore = KeyStore.getInstance("JKS");
            keystore.load(input, JKSPassword.toCharArray());
            privateKey = (RSAPrivateKey) keystore.getKey(JKSAlias, JKSPassword.toCharArray());
        } catch (FileNotFoundException ex) {
            throw new Exception("FileNotFoundException", ex);
        } catch (IOException ex) {
            throw new Exception("IOException", ex);
        } catch (Exception ex) {
            throw new Exception("Exception", ex);
        }
        return privateKey;
	}

	public RSAPublicKey getPublicKey(String JKSFileName) {
		RSAPublicKey publicKey = null;
		return publicKey;
	}

	public String firmar(String string) {
		String firma = null;
        try {
            firma = sign(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return firma;
	}

	public boolean validaFirma(String firma, RSAPublicKey publicKey) {
		boolean valid = Boolean.TRUE;
		
		return valid;
	}

	public String sign(String cadena) throws Exception {
		String retVal;
		this.getConfig();
        try {
            String data = cadena;
            Signature firma = Signature.getInstance("SHA256withRSA");
            RSAPrivateKey llavePrivada = getPrivateKey(JKSFileName, JKSAlias, JKSPassword);
            firma.initSign(llavePrivada);
            byte[] bytes = data.getBytes("UTF-8");
            firma.update(bytes, 0, bytes.length);
            BASE64Encoder b64 = new BASE64Encoder();
            retVal = b64.encode(firma.sign());
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("NoSuchAlgorithmException", e);
        } catch (InvalidKeyException e) {
            throw new Exception("InvalidKeyException: ", e);
        } catch (SignatureException e) {
            throw new Exception("SignatureException",e);
        }
        return retVal;
	}

}
