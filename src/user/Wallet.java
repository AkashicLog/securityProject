package user;
import util.*;

public class Wallet {
    private String publicKey;
    private String privateKey;
    private String address;
    public Wallet(){
        String[] keyPair = ECC.init();
        privateKey = keyPair[0];
        publicKey = keyPair[1];
        generateAddress();
    }
    private void generateAddress(){
        String encode = tool.SHA256(publicKey);
        encode = "00" + tool.RIPEMD_160(encode);
        String encode2 = tool.SHA256(tool.SHA256(encode));
        encode = encode2.substring(0,8) + encode;
        address = Base58.encode(tool.hexToByteArray(encode));
    }

    public String getAddress() {
        return address;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }
}
