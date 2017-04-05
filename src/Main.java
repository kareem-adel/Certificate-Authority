import java.security.PublicKey;

/**
 * Created by kareem on 4/5/2017.
 */
public class Main {
    public static void main(String[] Args) {
        User user = new User();
        CA ca = new CA();
        //ca.insertUserInMap(user.keyPair.getPublic());
        ca.receivePublicKey(user.keyPair.getPublic());
        user.verifyReceivedCertificate(ca.keyPair.getPublic());
    }
}
