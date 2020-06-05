package util;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class ECC {
    public static String[] init() {
        String[] get = null;
        ECDSAsample ecdsAsample = new ECDSAsample();
        get = new String[]{
                tool.byte2Hex(ecdsAsample.getPrivateKey().toByteArray()),
                tool.byte2Hex(ecdsAsample.getPublicKey().toByteArray())
        };
        return get;
    }

}
