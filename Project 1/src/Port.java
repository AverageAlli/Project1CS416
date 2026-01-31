public class Port {
    private final String udpPort;
    private final String ipAddress;

    public Port () {
        udpPort = "0";
        ipAddress = "0";
    }

    public Port (String udpPort, String ipAddress) {
        this.udpPort = udpPort;
        this.ipAddress = ipAddress;
    }

    public String getUdpPort() {
        return udpPort;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}
