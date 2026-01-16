package Gola.lab2026_01_09.Zadanie1;

public class Adapter110V implements Power5V {

    private Socket110V socket;

    public Adapter110V(Socket110V socket) {
        this.socket = socket;
    }

    @Override
    public int getVoltage5V() {
        int v = socket.getVoltage();
        return v / 22;
    }
}
