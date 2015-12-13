import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;

/**
 * Created by Gustaf & Christian on 2015-12-07.
 */
public class Decryption {

    private String size;
    private BigInteger sizeN;
    private BigInteger p;
    private BigInteger q;
    private int key;
    private BigInteger publicKey;
    private BigInteger privateKey;
    private int primes[] = new int[3];

    public Decryption(String N){

        createPrimeNumbers(N);

        sizeN = p.multiply(q);

        publicKey = createPublicKey();
        privateKey = createPrivateKey();


    }

    public BigInteger getPublicKey(){
        return publicKey;
    }
    public BigInteger getCalculatedN() {return (p).multiply(q); }

    public String decrypt(String text){
        BigInteger C;
        BigInteger T;
        C = new BigInteger(text);
        T = C.modPow(privateKey,sizeN);
        return T.toString();
    }

    public void createPrimeNumbers(String size){

        p = new BigInteger("2");
        q = new BigInteger("1");

        while( (p.multiply(q)).compareTo(new BigInteger(size)) == -1 ){
            p = p.multiply(new BigInteger("2"));
            p = p.nextProbablePrime();
            q = p.nextProbablePrime();

            //p = q.nextProbablePrime();
        }

        System.out.println("Found two primes: " + p+ " and " + q);


        /*primes[0] = 0;
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
        */


        //System.out.println("Found two primes: " + primes[0]+ " and " + primes[1]);
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

    private BigInteger createPublicKey(){
        BigInteger first;

        first = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        System.out.println("phi: " + first);
        boolean flag = true;
        BigInteger i = BigInteger.ONE;

        while(flag){
            i = i.add(BigInteger.valueOf(1));

            if(first.gcd(i).compareTo(BigInteger.valueOf(1)) == 0){
                flag = false;
            }
        }

        System.out.println("createPublicKey: " + i.toString());

        return i;
    }

    private BigInteger createPrivateKey(){
        BigInteger d; // Private Key.

        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        d = publicKey.modInverse(phi);

        System.out.println("privateKey = " + d);

        return d;
    }

}
