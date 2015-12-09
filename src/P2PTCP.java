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
                String N = args[2].toString(); // size

                PrintWriter out = new PrintWriter(peerConnectionSocket.getOutputStream());

                out.println(Integer.toString(decryp.getPublicKey())); // Sends the new Generated public key
                out.flush();
                out.println(N); // Sends the size.
                out.flush();

                //st = new Thread(new StringSender(out));
                //st.start();

                scan = new Scanner(peerConnectionSocket.getInputStream());

                String fromSocket = scan.nextLine();
                System.out.println("Recived: " + fromSocket);
                System.out.println("Decrypted to: "+ decryp.decrypt(fromSocket));

                while ((fromSocket = scan.nextLine()) != "exit") {
                    System.out.println("Recived: " + fromSocket);
                }

            } catch (IOException e) {
                System.err.println("Server crash");
            } finally {
                st.stop();
            }
        }
        else if(args[0].equals("client")) {
            Decryption dec = new Decryption("100");
            try{
                int encryptionKey = 0;
                String N;
                Scanner inputScan = new Scanner(System.in);
                peerConnectionSocket = new Socket(args[1], Integer.parseInt(args[2]));

                scan = new Scanner (peerConnectionSocket.getInputStream());
                encryptionKey = Integer.parseInt(scan.nextLine());
                N = scan.nextLine();
                System.out.println("encryption key: "+encryptionKey);
                System.out.println("N size: "+ N);

                Encryption enc = new Encryption(N, encryptionKey);

                PrintWriter out = new PrintWriter(peerConnectionSocket.getOutputStream());
                out.println(enc.encrypt("1337"));
                out.flush();

                //st = new Thread(new StringSender(new PrintWriter(peerConnectionSocket.getOutputStream())));
                //st.start();
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
        else if(args[0].equals("test")){


            String N = "99999999";

            Decryption decryp = new Decryption(N);

            Encryption enc = new Encryption(Integer.toString(decryp.getN()), decryp.getPublicKey());





            System.out.println("Testing");



            String number = "99999999";

            System.out.println("Number to encrypt: " + number);

            System.out.println("Number encrypted to: " + enc.encrypt(number));

            System.out.println("Number to Decrypt: " + enc.encrypt(number));

            System.out.println("Number Decrypted to: " + decryp.decrypt(enc.encrypt(number)));


/*
            int testSize = 9999999;
            String tn = "";

            for(int i=1; i<testSize; i++){
                tn = Integer.toString(i);
                if(!tn.equals(decryp.decrypt(enc.encrypt(tn)))){
                    System.out.println("tn " + tn + " AND" + " decrypt " + decryp.decrypt(enc.encrypt(tn)) +" DOES NOT MATCH");
                }
            }
            System.out.println("DONE!!!!");


*/
        }



    }


}

