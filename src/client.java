import entity.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;


public class client {
    public static void main(String[] args){
        Security.addProvider(new BouncyCastleProvider());
    }
}
