import java.io.*;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PublicKey;

/**
 * Created by kareem on 4/5/2017.
 */
public class User {

    KeyPair keyPair;

    public User() {
        keyPair = Utils.generateKeys(512);
    }

    public void verifyReceivedCertificate(PublicKey publicKeyCA) {
        try {
            Utils.rsaDecrypt("CAUserSignatureSigned", "CAUserSignatureDecrypted", publicKeyCA);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String stringCAUserSignatureDecrypted = "";
        try {
            FileReader fileReader = new FileReader("CAUserSignatureDecrypted");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            stringCAUserSignatureDecrypted = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(stringCAUserSignatureDecrypted.endsWith("" + keyPair.getPublic().hashCode())){
            System.out.println("The public key that the user sent matches the public key sent in the certificate");
        }else {
            System.out.println("The public key that the user sent does not match the public key sent in the certificate");
        }
    }

}