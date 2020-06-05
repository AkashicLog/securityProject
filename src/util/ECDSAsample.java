package util;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Random;

public class ECDSAsample {
    private BigInteger p= new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFC2F",16);
    private BigInteger a = new BigInteger("0");
    private BigInteger b  = new BigInteger("7");
    private BigInteger Gx  = new BigInteger("79BE667EF9DCBBAC55A06295CE870B07029BFCDB2DCE28D959F2815B16F81798",16);
    private BigInteger Gy  = new BigInteger("483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8",16);
    private BigInteger n  = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEBAAEDCE6AF48A03BBFD25E8CD0364141",16);
    private BigInteger h = new BigInteger("1");
    private BigInteger privateKey;
    private BigInteger publicKey;
    private static final BigInteger one = BigInteger.ONE;
    private static final BigInteger two = BigInteger.valueOf(2);
    private static final BigInteger three = BigInteger.valueOf(3);
    private static final BigInteger zero = BigInteger.ZERO;

    public ECDSAsample(){
        setRandomPrivateKey();
        setPublicKey();
    }
    private void setRandomPrivateKey(){
        byte [] b = new byte[32];
        Random random=new Random();
        random.nextBytes(b);
        BigInteger get = new BigInteger(b);
        if (get.compareTo(zero) < 0){
             privateKey = get.negate();
        }
        else if(get.compareTo(zero) == 0){
            setRandomPrivateKey();
        }
        else {
            privateKey = get;
        }

    }
    private void setPublicKey(){
        BigInteger[] add = new BigInteger[2];
        add[0] = new BigInteger(Gx.toString());
        add[1] = new BigInteger(Gy.toString());
        add =  Mutiply(add);
        String newKey = "04"+ (add != null ? add[0].toString(16) : null) + (add != null ? add[1].toString(16) : null);
        publicKey = new BigInteger(newKey,16);
    }
    private BigInteger[] ECAdd(BigInteger x1, BigInteger y1,BigInteger x2, BigInteger y2){
        BigInteger LamAdd = (y2.subtract(y1)).multiply(modinv(x2.subtract(x1),p)).mod(p);
        BigInteger[] result = new BigInteger[2];
        result[0] = (LamAdd.multiply(LamAdd).subtract(x1).subtract(x2)).mod(p);
        result[1] = (LamAdd.multiply(x1.subtract(result[0]).subtract(x2))).mod(p);
        return result;
    }
    private BigInteger[] ECDouble(BigInteger x, BigInteger y){
        BigInteger[] result = new BigInteger[2];
        BigInteger Lam =(three.multiply(x).multiply(x).add(a)).multiply(modinv(two.multiply(y),p));
        result[0] = (Lam.multiply(Lam).subtract(x.multiply(two))).mod(p);
        result[1] =  (Lam.multiply(x.subtract(result[0])).subtract(y)).mod(p);
        return  result;
    }
    private BigInteger[] Mutiply(BigInteger[] result){
         if (privateKey.compareTo(n) >= 0){
             return null;
         }
         String ScalarBin = privateKey.toString(2);
         int length = ScalarBin.length();
         for(int i = 0 ; i < length; i++){
              result = ECDouble(result[0],result[1]);
              if(ScalarBin.charAt(i) == '1'){
                  result = ECAdd(result[0],result[1],Gx,Gy);
              }
         }
         return result;
    }

    private BigInteger modinv(BigInteger a,BigInteger n){
        BigInteger lm = new BigInteger(one.toString());
        BigInteger hm = new BigInteger(zero.toString());
        BigInteger low = a.mod(n);
        BigInteger high = new BigInteger(n.toString());
        BigInteger nm,newn,ratio;
        while (low.compareTo(one) > 0){
            ratio = high.divide(low);
            nm = hm.subtract(lm.multiply(ratio));
            newn = high.subtract(low.multiply(ratio));
            hm = new BigInteger(lm.toString());
            lm = new BigInteger(nm.toString());
            high =  new BigInteger(low.toString());
            low = new BigInteger(newn.toString());
        }
        return  lm.mod(n);
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }
}
