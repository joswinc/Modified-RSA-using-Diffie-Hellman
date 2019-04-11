import java.net.*;
import java.util.*;

import java.io.*;

public class Greetingclient {

    static int gcd(int a, int b) {
        if (b == 0)
            return a;
        else
            return gcd(b, a % b);
    }

    static int modu(int a, int e, int n) {
        int r = 1;
        for (int i = 1; i <= e; i++)
            r = ((r % n) * (a % n)) % n;
        return r;
    }

    public static void main(String[] args) {
        try {
            String pstr, gstr, Astr;
            String serverName = "localhost";
            int port = 8088;

            // Declare p, g, and Key of client
            int p = 23;
            int g = 9;

            int privateKey = (new Random()).nextInt(6);

            // generate client public key//
            int clientPublicKey = ((int) (Math.pow(g, privateKey)) % p);

            int Adash, serverPublickey, secretKey;

            // Established the connection
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);
            System.out.println("Just connected to " + client.getRemoteSocketAddress());

            // get server public key//
            DataInputStream in = new DataInputStream(client.getInputStream());
            serverPublickey = Integer.parseInt(in.readUTF());

            // send client public key//
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            out.writeUTF(String.valueOf(clientPublicKey)); // Sending server public key

            secretKey = ((int) (Math.pow(serverPublickey, privateKey) % p)); // calculation of secret key
            System.out.println("secret key " + secretKey);

            // generate rsa//
            System.out.println("Enter two large prime numbers");
            Scanner s = new Scanner(System.in);
            int pp = s.nextInt();
            int qq = s.nextInt();

            int n = pp * qq;
            int z = (pp - 1) * (qq - 1);

            System.out.println("Choose any e:");

            for (int i = 2; i <= z; i++) {
                int x = Greetingclient.gcd(i, z);
                if (x == 1 && i > p)
                    System.out.print(i + "  ");
            }
            System.out.println();
            int e = s.nextInt();

            int ff = (e + secretKey);
            int gg = n + secretKey;

            out.writeUTF(String.valueOf(ff));
            out.writeUTF(String.valueOf(gg));

            String chipher = in.readUTF();
            String message = "";

            int d = 1;
            while ((e * d) % z != 1)
                d++;

            // decryptiing//
            for (int i = 0; i < chipher.length(); i++) {
                int m = chipher.charAt(i);
                System.out.println(m);
                char ch = (char) Greetingclient.modu(m, d, n);
                message += ch;
            }

            System.out.println(message);

            // Sends the data to client
            // OutputStream outToServer = client.getOutputStream();
            // DataOutputStream out = new DataOutputStream(outToServer);

            /*
             * pstr = Integer.toString(p); out.writeUTF(pstr); // Sending p
             * 
             * gstr = Integer.toString(g); out.writeUTF(gstr); // Sending g
             * 
             * double A = ((Math.pow(g, a)) % p); // calculation of A Astr =
             * Double.toString(A); out.writeUTF(Astr); // Sending A
             * 
             * // Client's Private Key System.out.println("From Client : Private Key = " +
             * a);
             * 
             * // Accepts the data DataInputStream in = new
             * DataInputStream(client.getInputStream());
             * 
             * serverB = Double.parseDouble(in.readUTF());
             * System.out.println("From Server : Public Key = " + serverB);
             * 
             * Adash = ((Math.pow(serverB, a)) % p); // calculation of Adash
             * 
             * System.out.println("Secret Key to perform Symmetric Encryption = " + Adash);
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
