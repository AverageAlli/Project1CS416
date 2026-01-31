public class Packet {
    private String data;
    private String destinationAddress;
    private String sourceAddress;

    public Packet(String rawFrame) {
        String[] splitFrame = rawFrame.split(":");

        destinationAddress = splitFrame[0];
        sourceAddress = splitFrame[1];
        data = splitFrame[2];
    }


}