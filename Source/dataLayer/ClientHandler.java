package dataLayer;

import logicLayer.Order;
import logicLayer.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import logicLayer.Offer;
import logicLayer.Order;

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
    private String user;
    private DBSystemLog log;

    public ClientHandler(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
        this.user = Integer.toString(clientNumber);
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
        this.log = new DBSystemLog();
    }

    @Override
    public void run() {
        try {
            this.outputThread = new Thread(() -> {
                currentThread().setName("outputThread" + clientNumber);
                while (run || !outputQueue.isEmpty()) {
                    if (!outputQueue.isEmpty()) {
                        try {
                            out.writeObject(outputQueue.poll());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            this.inputThread = new Thread(() -> {
                currentThread().setName("inputThread" + clientNumber);
                while (run) {
                    try {
                        inputQueue.add((Packet) in.readObject());
                    } catch (IOException | ClassNotFoundException e) {
                        run = false;
                    }
                }
            });

            outputThread.start();
            inputThread.start();

            System.out.println("Client " + clientNumber + " has connected!");
            outputPackage = new Packet(0, "You successfully connected to the server. You are client: " + clientNumber);
            outputQueue.add(outputPackage);
            String answer;
            String email;
            DBLogin login = new DBLogin();
            DBOrder order = new DBOrder();
            DBOffer offer = new DBOffer();
            

            while (run) {
                if (!inputQueue.isEmpty()) {
                    inputPackage = inputQueue.poll();
                    if (inputPackage == null) {
                        break;
                    }

                    switch (inputPackage.getId()) {
                        case -1:
                            run = false;
                            break;
                        case 1: //Login
                            answer = login.login((String) inputPackage.getObject());
                            outputPackage = new Packet(1, answer.toLowerCase());
                            outputQueue.add(outputPackage);
                            user = ((String) inputPackage.getObject()).split(" ")[0];
                            log.setSystemLog(user, "logged in");
                            break;
                        case 2: //Register user
                            DBRegister register = new DBRegister();
                            HashMap<String, String> form = (HashMap<String, String>) inputPackage.getObject();
                            answer = register.register(form);
                            outputPackage = new Packet(2, answer);
                            outputQueue.add(outputPackage);
                            log.setSystemLog(user, "registered user: " + form.get("email"));
                            break;
                        case 4: //List users
                            DBUsers dbUser = new DBUsers();
                            ArrayList<Object> userEmail = new ArrayList<>();
                            userEmail.addAll(dbUser.displayUsers());
                            outputPackage = new Packet(4, userEmail);
                            outputQueue.add(outputPackage);
                            break;
                        case 5: //Delete user
                            email = (String) inputPackage.getObject();
                            dbUser = new DBUsers();
                            dbUser.deleteUser(email);
                            log.setSystemLog(user, "deleted user: " + email);
                            break;
                        case 6: //Lost password
                            answer = login.getPassword((String) inputPackage.getObject());
                            outputPackage = new Packet(5, answer);
                            outputQueue.add(outputPackage);
                            log.setSystemLog(user, "requested password for user: " + inputPackage.getObject());
                            break;
                        case 30: //Get user
                            email = (String) inputPackage.getObject();
                            dbUser = new DBUsers();
                            User usr = dbUser.getUser(email);
                            outputPackage = new Packet(30, usr);
                            outputQueue.add(outputPackage);
                            break;
                        case 31: //Get log
                            ArrayList<String> logs = log.getSystemLog();
                            outputPackage = new Packet(31, logs);
                            outputQueue.add(outputPackage);
                            break;
                        case 32: //Update user
                            dbUser = new DBUsers();
                            HashMap<String, String> updateForm = (HashMap<String, String>) inputPackage.getObject();
                            dbUser.updateUser(updateForm);
                            break;
                        case 33: //Get order list
                            outputPackage = new Packet(33, order.getOrderList((String) inputPackage.getObject(), user));
                            outputQueue.add(outputPackage);
                            break;
                        case 34: //Get order
//                            outputPackage = new Packet(34, order.getOrder((String) inputPackage.getObject()));
//                            outputQueue.add(outputPackage);
                            break;
                        case 35: //Get product specification
                            outputPackage = new Packet(35, order.getProductSpecification((String) inputPackage.getObject()));
                            outputQueue.add(outputPackage);
                            break;
                        case 36: //Create order
                            Order tempOrder = (Order) inputPackage.getObject();
                            tempOrder.setCustomer(this.user);
                            order.createOrder(tempOrder);
                            break;
                        case 37: //Delete order
                            order.deleteOrder((String) inputPackage.getObject());
                            break;
                        case 38:
                            order.updateOrder((Order) inputPackage.getObject());
                            break;
                        case 39: //Get order list manufacturer
                            outputPackage = new Packet(39, offer.getOrderListManufacturer());
                            outputQueue.add(outputPackage);
                            break;
                        case 40: //Create offer
                            Offer tempOffer = (Offer) inputPackage.getObject();
                            tempOffer.setManfemail(this.user);
                            offer.createOffer(tempOffer);
                            break;
                        case 41: //delete offer
                            offer.deleteOffer((int) inputPackage.getObject());
                            break;
                        case 42: //accept offer
                            int id = ((int) inputPackage.getObject());
                            offer.acceptOrder(this.user, id);
                            break;
                        case 7:
                            DBOrder dbOrder = new DBOrder();
                            ArrayList<Order> returnList = dbOrder.getOrderListPending();
                            outputPackage = new Packet(7, returnList);
                            outputPackage = new Packet(7, order.getOrderListPending());
                            outputQueue.add(outputPackage);
                            break;
                        default:
                            log.setSystemLog(user, "received unknown packet: " + inputPackage.getId());
                            break;
                    }
                }
            }
        } finally {
            try {
                inputThread.join();
                outputThread.join();
                socket.close();
            } catch (IOException e) {
                System.out.println("Couldn't disconnect client " + clientNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Client " + clientNumber + " has disconnected from the server!");
        }
    }
}
