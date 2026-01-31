import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class Switch {
    public static void main(String[] args) throws FileNotFoundException {
        String macAddress = args[0];
        File config = new File("Project 1/src/config.txt");
        Parser parser = new Parser(config);

        Port p = parser.parseMac(macAddress);
        System.out.println(p.getIpAddress());
        System.out.println(p.getUdpPort());

        HashMap<String, Port> neighbors = parser.getNeighbors(macAddress);

        for (String i : neighbors.keySet()) {
            System.out.println(i);
            System.out.println(neighbors.get(i).getUdpPort());
            System.out.println(neighbors.get(i).getIpAddress());
        }

    }
}
