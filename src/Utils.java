import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

public class Utils {

	/**
	 * This method generates the PU and the PR RSA key-pair
	 * 
	 * @param size,
	 *            number of bits of the RSA keys
	 * @return
	 */
	public static KeyPair generateKeys(int size) {
		KeyPairGenerator kpg = null;
		try {
			kpg = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		kpg.initialize(size);
		KeyPair kp = kpg.genKeyPair();
		return kp;
	}

	/**
	 * This method used to encrypt using RSA
	 * 
	 * @param src_file,
	 *            contains the decrypted message
	 * @param dest_file,
	 *            to save the encrypted message in it
	 * @param key,
	 *            the PU or PR to encrypt with
	 * @throws Exception
	 */
	public static void rsaEncrypt(String src_file, String dest_file, Key key) throws Exception {
		byte[] data = new byte[32];
		int i;

		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		FileInputStream fileIn = new FileInputStream(src_file);
		FileOutputStream fileOut = new FileOutputStream(dest_file);
		CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher);

		while ((i = fileIn.read(data)) != -1) {
			cipherOut.write(data, 0, i);
		}

		cipherOut.close();
		fileOut.close();
		fileIn.close();
	}

	/**
	 * This method used to decrypt using RSA
	 * 
	 * @param src_file,
	 *            contains the encrypted message
	 * @param dest_file,
	 *            to save the decrypted message in it
	 * @param key,
	 *            the PU or PR to decrypt with
	 * @throws Exception
	 */
	public static void rsaDecrypt(String src_file, String dest_file, Key key) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, key);

		FileInputStream fileIn = new FileInputStream(src_file);
		CipherInputStream cipherIn = new CipherInputStream(fileIn, cipher);
		FileOutputStream fileOut = new FileOutputStream(dest_file);

		int i;
		while ((i = cipherIn.read()) != -1) {
			fileOut.write(i);
		}

		fileIn.close();
		cipherIn.close();
		fileOut.close();
	}

}