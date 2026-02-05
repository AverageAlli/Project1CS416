import java.io.File;
import java.io.FileNotFoundException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Switch {
    public static void main(String[] args) throws Exception, FileNotFoundException {
        String macAddress = args[0];
        System.out.println("Mac Address: " + macAddress);
        File config = new File("Project 1/src/config.txt");
        Parser parser = new Parser(config);
        HashMap<String, Port> neighbors = parser.getNeighbors(macAddress);
        HashMap<String, Integer> switchTable = new HashMap<>();
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket incoming = new DatagramPacket(new byte[1024], 1024);

        while(true) {
            socket.receive(incoming);
            String frame = new String(incoming.getData(), 0, incoming.getLength());
            Packet packet;
            try {
                packet = new Packet(frame);
            } catch (Exception e) {
                continue;
            }
            String srcMac = packet.getSourceAddress();
            String dstMac = packet.getDestinationAddress();

            InetAddress incomingIp = incoming.getAddress();
            String incomingMac = getMacFromIp(neighbors, incomingIp);
            Port incomingPort = neighbors.get(incomingMac);

            if (srcMac != null && incomingPort != null && !switchTable.containsKey(srcMac)) {
                switchTable.put(srcMac, incomingPort.getUdpPort());
                printSwitchTable(switchTable);
            }
        }
    }

    public static String getMacFromIp(HashMap<String, Port> neighbors, InetAddress ipAddress) {
        for (String s : neighbors.keySet()) {
            Port p = neighbors.get(s);
            if (p.getIpAddress().equals(ipAddress)) {
                return s;
            }
        }
        return "None";
    }

    public static void printSwitchTable(HashMap<String, Integer> switchTable) {
        System.out.println("Switch Table:");
        System.out.println("MAC Address\tPort");
        for (String mac : switchTable.keySet()) {
            System.out.println(mac + "\t" + switchTable.get(mac));
        }
    }

    public void puddle() {

        // Example variables (to be replaced with actual parameters or class fields):
    HashMap<String, Port> neighbors = null; // Replace with actual neighbors
    DatagramSocket socket = null; // Replace with actual socket        String frame = null; // Replace with actual frame
    int incomingPort = -1; // Replace with actual incomingport
        // Flood the frame to all neighbors except the incoming port
    for (String mac : neighbors.keySet()) {
        Port port = neighbors.get(mac);
        if (port.getUdpPort() != incomingPort) {
            try {
                DatagramPacket packet = new DatagramPacket(
                    frame.getBytes(),
                    frame.length(),
                    port.getIpAddress(),
                    port.getUdpPort()
                );
                socket.send(packet);
           } catch (Exception e) {
                System.err.println("Error flooding frame to " + mac + ": " + e.getMessage());
             }
        }
      }
   System.out.println("Frame flooded to all ports except incoming port " + incomingPort);
    }

}
