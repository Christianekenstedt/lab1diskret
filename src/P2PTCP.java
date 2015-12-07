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

                Decryption decryp = new Decryption(args[2].toString());
                //----------------------------------------------------
                ServerSocket ss = new ServerSocket(Integer.parseInt(args[1]));
                System.out.println("Waiting for connection...");
                peerConnectionSocket = ss.accept();
                String N = args[2].toString();
                PrintWriter out = new PrintWriter(peerConnectionSocket.getOutputStream());

                out.println(Integer.toString(decryp.getPublicKey()));
                out.flush();
                out.println(args[2]);
                out.flush();

                //st = new Thread(new StringSender(out));
                //st.start();

                scan = new Scanner(peerConnectionSocket.getInputStream());

                String fromSocket = scan.nextLine();
                System.out.println(fromSocket);
                System.out.println("From class: "+decryp.decrypt(fromSocket));


                while ((fromSocket = scan.nextLine()) != null) {
                    System.out.println(fromSocket);
                }

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

                Encryption enc = new Encryption(N, encryptionKey);

                PrintWriter out = new PrintWriter(peerConnectionSocket.getOutputStream());
                out.println(enc.encrypt("7"));
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

}

