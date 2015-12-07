import java.math.BigInteger;

/**
 * Created by Gustaf on 2015-12-07.
 */
public class Decryption {

    private String size;
    private int key;
    private int publicKey = 3;
    private int privateKey;

    public Decryption(String N){

        createKeys();
        this.size = N;
        publicKey = getPublicKey();
    }

    private void createKeys(){

        publicKey=7;
        privateKey=3;

    }

    public int getPublicKey(){
        return publicKey;
    }

    public String decrypt(String text){
        BigInteger N,C;
        BigInteger T;

        N = new BigInteger(size);
        C = new BigInteger(text);

        T = C.pow(privateKey);
        T = T.mod(N);

        return T.toString();
    }

    private int createPublicKey(String N){
        int e=0;
        //Ta två primtal p och q som bildar N.

        //välj krypteringstalet så att e (publicKey) > 1 så att e och (p-1)(q-1) är relativt prima.

        return e;
    }

    private int createPrivateKey(){
        int d=0; // Private Key.
        // välj d så att d*e är kongurent med 1*(mod(p-1)(q-1))

        return d;
    }


}
