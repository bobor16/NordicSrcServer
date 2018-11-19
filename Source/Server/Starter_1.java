package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Starter_1 {

    private static BufferedReader input;
    private static Packet packet;

    public static void main(String[] args) {
        try {
            input = new BufferedReader(new InputStreamReader(System.in));
            String text = "";

            ClientController lol = new ClientController();

            while (true) {
                text = input.readLine();
                if (text == null || text.equals("exit")) {
                    packet = new Packet(-1, null);
                    lol.sendPackage(packet);
                    break;
                }
                packet = new Packet(0, text);
                lol.sendPackage(packet);

                packet = lol.receivePackage();

                switch (packet.getId()) {
                    case 0:
                        System.out.println(packet.getObject());
                        break;
                    default:
                        System.out.println("Error?");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
