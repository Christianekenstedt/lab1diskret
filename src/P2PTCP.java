import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.*;


public class P2PTCP{
    public static void main(String[] args) {
        Scanner scan; Thread st=null;
        Socket peerConnectionSocket=null;
        String T;
        if(args[0].equals("server")) {
            try {
                ServerSocket ss = new ServerSocket(Integer.parseInt(args[1]));
                System.out.println("Waiting for connection...");
                peerConnectionSocket = ss.accept();
                String N = args[2].toString();
                PrintWriter out = new PrintWriter(peerConnectionSocket.getOutputStream());

                out.println("7");
                out.flush();
                out.println(N);
                out.flush();

                //st = new Thread(new StringSender(out));
                //st.start();
                scan = new Scanner(peerConnectionSocket.getInputStream());

                String fromSocket = scan.nextLine();
                System.out.println(fromSocket);
                System.out.println(decrypt(fromSocket, 3, N));
                while ((fromSocket = scan.nextLine()) != null) {
                    System.out.println(fromSocket);
                }
                T = decrypt(fromSocket, 3, N);
                System.out.println(T);

            } catch (IOException e) {
                System.err.println("Server crash");
            } finally {
                st.stop();
            }
        }
        else if(args[0].equals("client")) {
            try{
                int encryptionKey = 0;
                String N;
                peerConnectionSocket = new Socket(args[1], Integer.parseInt(args[2]));

                scan = new Scanner (peerConnectionSocket.getInputStream());
                encryptionKey = Integer.parseInt(scan.nextLine());
                N = scan.nextLine();
                System.out.println("encryption key: "+encryptionKey);
                System.out.println("N size: "+ N);

                PrintWriter out = new PrintWriter(peerConnectionSocket.getOutputStream());
                out.println(encrypt("3", encryptionKey, N));
                out.flush();

                //st = new Thread(new StringSender(new PrintWriter(peerConnectionSocket.getOutputStream())));
                //st.start();

                String fromSocket;
                while((fromSocket = scan.nextLine())!=null)
                    System.out.println(fromSocket);
            }
            catch(Exception e) {System.err.println("Client crash");}
            finally{st.stop();}
        }
    }
    private static String decrypt(String text, Integer decryptKey, String size){
        BigInteger N,C;
        BigInteger T;

        N = new BigInteger(size);
        C = new BigInteger(text);

        T = C.pow(decryptKey);
        T = T.mod(N);

        return T.toString();
    }

    private static String encrypt(String text, Integer key, String size){
        BigInteger T, C, N;
        T = new BigInteger("7"); // here goes the number to be crypted (text).
        N = new BigInteger(size);

        T = T.pow(key);
        C = T.mod(N);

        return C.toString();
    }
}

