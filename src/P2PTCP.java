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

                PrintWriter out = new PrintWriter(peerConnectionSocket.getOutputStream());

                out.println(decryp.getPublicKey()); // Sends the new Generated public key
                out.flush();
                out.println(decryp.getCalculatedN()); // Sends the size.
                out.flush();

                scan = new Scanner(peerConnectionSocket.getInputStream());

                String fromSocket = scan.nextLine();
                System.out.println("Recived: " + fromSocket);
                System.out.println("Decrypted to: "+ decryp.decrypt(fromSocket));

                while ((fromSocket = scan.nextLine()) != "exit") {
                    System.out.println("Recived: " + fromSocket);
                    System.out.println("Decrypted to: "+ decryp.decrypt(fromSocket));
                }

            } catch (IOException e) {
                System.err.println("Server crash");
            } finally {
               // st.stop();
            }
        }
        else if(args[0].equals("client")) {
            try{
                String N;
                Scanner inputScan = new Scanner(System.in);
                peerConnectionSocket = new Socket(args[1], Integer.parseInt(args[2]));

                scan = new Scanner (peerConnectionSocket.getInputStream());
                BigInteger encryptionKey = new BigInteger(scan.nextLine());
                N = scan.nextLine();
                System.out.println("encryption key: "+encryptionKey);
                System.out.println("N size: "+ N);

                Encryption enc = new Encryption(N, encryptionKey);
                Integer rnd = 0 + (int)(Math.random()*100);
                PrintWriter out = new PrintWriter(peerConnectionSocket.getOutputStream());
                out.println(enc.encrypt(Integer.toString(rnd)));
                out.flush();

                String message = "";
                while(!message.equals("exit")){
                    System.out.println("Send an integer: ");

                    message = inputScan.nextLine();
                    out.println(enc.encrypt(message));
                    out.flush();
                }


                /*String fromSocket;
                while((fromSocket = scan.nextLine())!=null)
                    System.out.println(fromSocket);
                */
            }
            catch(Exception e) {System.err.println("Client crash");}
            finally{st.stop();}
        }
    }


}

