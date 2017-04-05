import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.HashMap;

/**
 * Created by kareem on 4/5/2017.
 */
public class CA {


    KeyPair keyPair;

    HashMap<String, String> stringPublicKeyHashMap;

    int counter = 0;

    public CA() {
        keyPair = Utils.generateKeys(2048);
        stringPublicKeyHashMap = new HashMap<>();
    }

    public void insertUserInMap(PublicKey publicKey) {
        stringPublicKeyHashMap.put("" + publicKey.hashCode(), String.valueOf(counter++));
    }

    public void receivePublicKey(PublicKey publicKey) {
        if(stringPublicKeyHashMap.get(String.valueOf(publicKey.hashCode())) == null){
            System.out.println("User does not exist in the certified users table");
            System.out.println("Adding user ...");
            insertUserInMap(publicKey);
            System.out.println("Added user");
        }
        String signature = System.currentTimeMillis()+ stringPublicKeyHashMap.get(String.valueOf(publicKey.hashCode()))  + publicKey.hashCode();
        try {
            BufferedWriter writer = Files.newBufferedWriter(new File("CAUserSignature").toPath());
            writer.write(signature, 0, signature.length());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Utils.rsaEncrypt("CAUserSignature", "CAUserSignatureSigned", keyPair.getPrivate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
