import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;

/**
 * Created by Gustaf & Christian on 2015-12-07.
 */
public class Decryption {

    private String size;
    BigInteger sizeN;
    BigInteger p;
    BigInteger q;
    private int key;
    private BigInteger publicKey;
    private BigInteger privateKey;
    private int primes[] = new int[3];

    public Decryption(String N){

        primes = createPrimeNumbers(N);
        this.size = Integer.toString(primes[2]);

        publicKey = createPublicKey();
        privateKey = createPrivateKey();


    }

    public BigInteger getPublicKey(){
        return publicKey;
    }
    public int getN() {return primes[2]; }

    public String decrypt(String text){
        BigInteger N,C;
        BigInteger T;

        N = new BigInteger(size);
        C = new BigInteger(text);

        T = C.pow(privateKey.intValue()); // FIXA!!
        T = T.mod(N);

        return T.toString();
    }

    public int[] createPrimeNumbers(String size){
        double sqrOfN;
        int j;
        p = new BigInteger("2");
        q = new BigInteger("1");
        sizeN = new BigInteger(size);
        System.out.println(sizeN);
        while( (p.multiply(q)).compareTo(sizeN) == -1 ){
            q = q.nextProbablePrime();
            p = q.nextProbablePrime();
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

    private BigInteger createPublicKey(){
        BigInteger first;

        Integer a = (primes[0]-1)*(primes[1]-1);

        //first = new BigInteger(a.toString());
        first = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

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
        BigInteger p = new BigInteger(Integer.toString(primes[0]));
        BigInteger q = new BigInteger(Integer.toString(primes[1]));

        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        BigInteger e;

        e = publicKey;
        d = e.modInverse(phi);

        return d;
    }


}
