package dataLayer;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientHandler extends Thread {

    private Socket socket;
    private int clientNumber;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Packet outputPackage;
    private Packet inputPackage;
    private ConcurrentLinkedQueue<Packet> outputQueue;
    private ConcurrentLinkedQueue<Packet> inputQueue;
    private Thread outputThread;
    private Thread inputThread;
    private boolean run;

    public ClientHandler(Socket socket, int clientNumber){
        this.socket = socket;
        this.clientNumber = clientNumber;
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.outputQueue = new ConcurrentLinkedQueue<>();
        this.inputQueue = new ConcurrentLinkedQueue<>();
        this.outputPackage = null;
        this.inputPackage = null;
        this.run = true;
    }

    @Override
    public void run() {
        try {
            this.outputThread = new Thread(() -> {
                currentThread().setName("outputThread"+clientNumber);
                while(run || !outputQueue.isEmpty()){
                    //System.out.println(currentThread().getName());
                    if (!outputQueue.isEmpty()){
                        try {
                            out.writeObject(outputQueue.poll());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            this.inputThread = new Thread(() -> {
                currentThread().setName("inputThread"+clientNumber);
                while (run) {
                    //System.out.println(currentThread().getName());
                    try {
                        inputQueue.add((Packet)in.readObject());
                    } catch (IOException | ClassNotFoundException e) {
                        run = false;
                    }
                }
            });

            outputThread.start();
            inputThread.start();

            log("Client " + clientNumber + " has connected!");
            outputPackage = new Packet(0,
                    "You successfully connected to the server. You are client: " + clientNumber + ".\n"
                    + "Type 'exit' to disconnect from the server.");
            outputQueue.add(outputPackage);

            while (run) {
                if (!inputQueue.isEmpty()){
                    inputPackage = inputQueue.poll();
                    if (inputPackage == null) {
                        break;
                    }

                    switch (inputPackage.getId()) {
                        case -1:
                            run = false;
                            break;

                        case 1:
                            DBLogin login = new DBLogin();
                            System.out.println(inputPackage.getObject());
                            String answer = login.login((String)inputPackage.getObject());
                            System.out.println(answer);
                            outputPackage = new Packet(1, answer.toLowerCase());
                            out.writeObject(outputPackage);
                            break;
                        default:
                            log("Received unknown packet with id " + inputPackage.getId() + " from client " + clientNumber);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputThread.join();
                outputThread.join();
                socket.close();
            } catch (IOException e) {
                log("Couldn't disconnect client " + clientNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log("Client " + clientNumber + " has disconnected from the server!");
        }
    }

    private static synchronized void log(String message) {
        try {
            File file = new File("log.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.canWrite()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt", true));
                writer.write(message + "\n");
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
