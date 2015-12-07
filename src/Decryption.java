import java.math.BigInteger;

/**
 * Created by Gustaf on 2015-12-07.
 */
public class Decryption {

    private String size;
    private int key;
    private int publicKey;
    private int privateKey;
    private int primes[] = new int[2];

    public Decryption(String N){
        this.size = N;
        primes = createPrimeNumbers();

        publicKey = createPublicKey(size);
        privateKey = createPrivateKey();

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
        BigInteger first;
        BigInteger relativtPrima;
        BigInteger count;
        //Ta två primtal p och q som bildar > N.


        Integer a = (primes[0]-1)*(primes[1]-1);
        System.out.println("HERE A: " + a);

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
        int d=0; // Private Key.
        // välj d så att d*e är kongurent med 1*(mod(p-1)(q-1))

        return d;
    }


}
