import java.math.BigInteger;

/**
 * Created by Gustaf on 2015-12-07.
 */
public class Encryption {

    BigInteger size;
    BigInteger key;

    public Encryption(BigInteger N, BigInteger key){

        this.size = N;
        this.key = key;

    }

    public String encrypt(String text){
        BigInteger T, C, N;
        T = new BigInteger(text); // here goes the number to be crypted (text).
        N = size;

        //T = T.pow(key.intValue()); // FIXA!!!!
        //C = T.mod(N);

        C = T.modPow(key,N);

        return C.toString();
    }

}
