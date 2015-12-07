import java.math.BigInteger;

/**
 * Created by Gustaf on 2015-12-07.
 */
public class Decryption {

    private String size;
    private int key;
    private int publicKey = 3;
    private int privateKey;
    private int primes[] = new int[2];

    public Decryption(String N){

        createKeys();
        this.size = N;
        publicKey = getPublicKey();
        primes = createPrimeNumbers();
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

    public int[] createPrimeNumbers(){
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

    private int createPublicKey(String N){
        int e=0;
        //Ta två primtal p och q som bildar > N.


        //välj krypteringstalet så att e (publicKey) > 1 så att e och (p-1)(q-1) är relativt prima.

        return e;
    }

    private int createPrivateKey(){
        int d=0; // Private Key.
        // välj d så att d*e är kongurent med 1*(mod(p-1)(q-1))

        return d;
    }


}
