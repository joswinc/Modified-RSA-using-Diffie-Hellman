import java.net.*;
import java.util.*;
import java.io.*;

public class GreetingServer {
    static double clientkey;

    static int modu(int a, int e, int n) {
        int r = 1;
        for (int i = 1; i <= e; i++)
            r = ((r % n) * (a % n)) % n;
        return r;
    }

    public static void main(String[] args) throws IOException {
        try {

            int port = 8088;
            // Server Key private key
            int privateKey = (new Random()).nextInt(6);
            // prime
            int p = 23;
            // alpha
            int g = 10;

            // public for server//
            int serverPublicKey = ((int) (Math.pow(g, privateKey)) % p); // calculation of server public key

            // Client p, g, and key
            int clientPublicKey, clientG, clientF;
            int secretKey;

            // Established the Connection
            while (true) {

                ServerSocket serverSocket = new ServerSocket(port);
                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
                Socket server = serverSocket.accept();
                DataInputStream in = new DataInputStream(server.getInputStream());

                // seding public key to client//
                OutputStream outToclient = server.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToclient);

                out.writeUTF(String.valueOf(serverPublicKey)); // Sending server public key

                clientPublicKey = Integer.parseInt(in.readUTF());// receiving client Public

                // generate secret key/
                secretKey = ((int) (Math.pow(clientPublicKey, privateKey) % p)); // calculation of Bdash
                System.out.println("Secret key " + secretKey);

                // get f and g from client//
                clientF = Integer.parseInt(in.readUTF()); // to accept f
                System.out.println("From Client : f = " + clientF);

                clientG = Integer.parseInt(in.readUTF()); // to accept g
                System.out.println("From Client : G = " + clientG);

                // find e and n//

                int e = clientF - secretKey;
                int n = clientG - secretKey;

                System.out.print("Enter the message: ");

                Scanner scan = new Scanner(System.in);
                String message = scan.nextLine();

                System.out.println("");

                String chipher = "";
                // encrypt this message//
                for (int i = 0; i < message.length(); i++) {
                    int m = message.charAt(i);
                    // System.out.println(m);
                    char ch = (char) GreetingServer.modu(m, e, n);
                    chipher += ch;
                }

                // sending ecrypted message//
                System.out.println("Encrypted:-" + chipher);
                out.writeUTF(chipher);

                // find e,n//

                /*
                 * System.out.println("Just connected to " + server.getRemoteSocketAddress());
                 * 
                 * // Server's Private Key System.out.println("From Server : Private Key = " +
                 * b);
                 * 
                 * // Accepts the data from client DataInputStream in = new
                 * DataInputStream(server.getInputStream());
                 * 
                 * clientP = Integer.parseInt(in.readUTF()); // to accept p
                 * System.out.println("From Client : P = " + clientP);
                 * 
                 * clientG = Integer.parseInt(in.readUTF()); // to accept g
                 * System.out.println("From Client : G = " + clientG);
                 * 
                 * clientA = Double.parseDouble(in.readUTF()); // to accept A
                 * 
                 * System.out.println("From Client : Public Key = " + clientA);
                 * 
                 * B = ((Math.pow(clientG, b)) % clientP); // calculation of B Bstr =
                 * Double.toString(B);
                 * 
                 * // Sends data to client // Value of B OutputStream outToclient =
                 * server.getOutputStream(); DataOutputStream out = new
                 * DataOutputStream(outToclient);
                 * 
                 * out.writeUTF(Bstr); // Sending B
                 * 
                 * Bdash = ((Math.pow(clientA, b)) % clientP); // calculation of Bdash
                 * 
                 * clientkey = Bdash;
                 * System.out.println("Secret Key to perform Symmetric Encryption = " + Bdash);
                 * // server.close();
                 */
            }
        }

        catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
        } catch (IOException e) {
        }
    }
}
