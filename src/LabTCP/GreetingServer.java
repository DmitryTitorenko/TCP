package LabTCP;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Server TCP-Socket
 *
 * @author Titorenko Dmitry
 * @since 2016-05-20
 */

public class GreetingServer extends Thread {
    private ServerSocket serverSocket;
    private String name;
    private int port;
    static private String MyID;
    static ArrayList<String> allId = new ArrayList<>();
    private static String serverAnswer;

    /**
     * GreetingServer constructor
     *
     * @param port
     * @throws IOException set endless Time out
     */
    public GreetingServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(0);
    }

    /**
     * This method is used to construct return sting to client
     *
     * @return string who consist of all name, ID and port
     */
    public String getmyID() {
        String a = "";
        String[] o = name.split(" ");
        for (int i = 0; i < o.length; i++) {
            a += o[i].substring(0, 1);
        }
        int id = (int) (Math.random() * ((4000 - 3500) + 1) + 3500);
        MyID = a + id;
        return name + " " + a + id + ": port " + port;
    }

    /**
     * This method is used to write client id to HDD
     */
    void writeToHDD() {
        try {
            FileWriter fileWriter = new FileWriter("C:\\IdClient.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);
            allId.add(MyID);
            for (int i = 0; i < allId.size(); i++) {
                printWriter.println(allId.get(i));
            }
            printWriter.close();
        } catch (IOException e) {
            System.out.println("Error IOException");
        }
    }

    /**
     * This method is used to run server
     */
    public void run() {
        while (true) {
            try {
                System.out.println("Wait connect...");
                Socket server = serverSocket.accept();
                System.out.println("Just connected to " + server.getRemoteSocketAddress());
                DataInputStream in = new DataInputStream(server.getInputStream());
                name = in.readUTF();
                System.out.println("Client say: " + name);
                port = serverSocket.getLocalPort();
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                serverAnswer = getmyID();
                out.writeUTF(serverAnswer);
                System.out.println("Server answer: " + serverAnswer);
                writeToHDD();
                server.close();
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) {
        int port = 6066;
        try {
            Thread t = new GreetingServer(port);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}