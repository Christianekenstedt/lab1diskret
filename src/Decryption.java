import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;

/**
 * Created by Gustaf & Christian on 2015-12-07.
 */
public class Decryption {

    private String size;
    private int key;
    private int publicKey;
    private int privateKey;
    private int primes[] = new int[3];

    public Decryption(String N){

        primes = createPrimeNumbers(N);
        this.size = Integer.toString(primes[2]);

        publicKey = createPublicKey();
        privateKey = createPrivateKey();


    }

    public int getPublicKey(){
        return publicKey;
    }
    public int getN() {return primes[2]; }

    public String decrypt(String text){
        BigInteger N,C;
        BigInteger T;

        N = new BigInteger(size);
        C = new BigInteger(text);

        T = C.pow(privateKey);
        T = T.mod(N);

        return T.toString();
    }

    public int[] createPrimeNumbers(String size){
        double sqrOfN;
        int j;

        primes[0] = 0;
        primes[1] = 0;

        sqrOfN = Math.sqrt(Double.parseDouble(size));

        j = (int) Math.round(sqrOfN);

        boolean flag = true;

        while(flag){

            if(isPrime(j)){
                if(primes[0] == 0) primes[0] = j;
                else if(primes[1] == 0) primes[1] = j;
            }

            j +=1;

            if(primes[0] != 0 && primes[1] != 0) {
                primes[2] = primes[0]*primes[1];
                flag = false;
            }
        }

        System.out.println("Found two primes: " + primes[0]+ " and " + primes[1]);

        return primes;
    }



    private boolean isPrime(int n){

        //check if n is a multiple of 2
        if (n%2==0) return false;
        //if not, then just check the odds
        for(int i=3;i*i<=n;i+=2) {
            if(n%i==0)
                return false;
        }
        return true;

    }

    private int createPublicKey(){
        BigInteger first;
        //Ta två primtal p och q som bildar > N.

        Integer a = (primes[0]-1)*(primes[1]-1);

        first = new BigInteger(a.toString());


        boolean flag = true;
        BigInteger i = BigInteger.ONE;

        while(flag){

            i = i.add(BigInteger.valueOf(1));

            if(first.gcd(i).compareTo(BigInteger.valueOf(1)) == 0){
                flag = false;
            }

        }

        //välj krypteringstalet så att e (publicKey) > 1 så att e och (p-1)(q-1) är relativt prima.

        System.out.println("createPublicKey: " + i.toString());

        return i.intValue();
    }

    private int createPrivateKey(){
        BigInteger d; // Private Key.
        // välj d så att d*e är kongurent med 1*(mod(p-1)(q-1))
        // d*e ger resten 1 vid division med (p-1)(q-1)
        BigInteger p = new BigInteger(Integer.toString(primes[0]));
        BigInteger q = new BigInteger(Integer.toString(primes[1]));

        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        System.out.println("phi is " + phi);
        BigInteger e;

        e = new BigInteger(Integer.toString(publicKey));
        //e = new BigInteger("65537");
        //d = a.modInverse(new BigInteger(Integer.toString((primes[0]-1)*(primes[1]-1))));
        d = e.modInverse(phi);

        System.out.println("PrivateKey is "  +d.intValue());

        return d.intValue();
    }


}
