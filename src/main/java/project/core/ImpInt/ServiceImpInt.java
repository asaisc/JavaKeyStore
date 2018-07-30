package project.core.ImpInt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Properties;

import project.core.Int.ServiceInt;
import project.utils.Constantes;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ServiceImpInt implements ServiceInt {
	public static String JKSFileName;
	public static String JKSAlias;
	public static String JKSPassword;
	public static Properties prop;
	public static String CERTFileName;
	
	public void getConfig() {
		
		InputStream input = getClass().getClassLoader().getResourceAsStream(Constantes.PROPERTIES_FILE);
		
		prop = new Properties();

		try {
			// load a properties file
			prop.load(input);

			JKSFileName = prop.getProperty("config.jks.filename");
			JKSAlias = prop.getProperty("config.jks.alias");
			JKSPassword = prop.getProperty("config.jks.password");
			CERTFileName = prop.getProperty("config.cert.fileName");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
	}

	public RSAPrivateKey getPrivateKey(String JKSFileName, String JKSAlias, String JKSPassword) throws Exception {
		RSAPrivateKey privateKey = null;
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
		
        try {
        	InputStream input = getClass().getClassLoader().getResourceAsStream(JKSFileName);
            KeyStore keystore = KeyStore.getInstance("JKS");
            keystore.load(input, JKSPassword.toCharArray());
			publicKey = (RSAPublicKey) keystore.getCertificate(JKSAlias).getPublicKey();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
		PublicKey pk = null;
		try {
			BASE64Decoder dec64 = new BASE64Decoder();
			Signature sign = Signature.getInstance("SHA256withRSA");
			try {
				pk = this.getPublicKeyFromCert(CERTFileName);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//sign.initVerify(publicKey);
			sign.initVerify(pk);
			byte[] bt = dec64.decodeBuffer(firma);
			byte[] btf = firma.getBytes(Charset.forName("UTF-8"));
			sign.update(btf,0,btf.length);
			valid = sign.verify(bt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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

	public boolean validar(String firma) {
		boolean firmaValida = Boolean.TRUE;
		firmaValida = this.validaFirma(firma, this.getPublicKey(JKSFileName));
		return firmaValida;
	}

	public PublicKey getPublicKeyFromCert(String CERTFileName) throws FileNotFoundException, Throwable {
		InputStream input = getClass().getClassLoader().getResourceAsStream(CERTFileName);
		CertificateFactory fact = CertificateFactory.getInstance("X.509");
	    //FileInputStream is = new FileInputStream (input);
	    X509Certificate cer = (X509Certificate) fact.generateCertificate(input);
	    PublicKey key = cer.getPublicKey();
		return key;
	}

}
