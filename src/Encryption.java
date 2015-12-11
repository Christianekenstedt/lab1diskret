import java.math.BigInteger;

/**
 * Created by Gustaf on 2015-12-07.
 */
public class Encryption {

    String size;
    BigInteger key;

    public Encryption(String N, BigInteger key){

        this.size = N;
        this.key = key;

    }

    public String encrypt(String text){
        BigInteger T, C, N;
        T = new BigInteger(text); // here goes the number to be crypted (text).
        N = new BigInteger(size);

        T = T.pow(key.intValue()); // FIXA!!!!
        C = T.mod(N);

        return C.toString();
    }

}
