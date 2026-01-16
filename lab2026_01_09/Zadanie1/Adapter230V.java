package Gola.lab2026_01_09.Zadanie1;

public class Adapter230V implements Power5V {

    private Socket230V socket;

    public Adapter230V(Socket230V socket) {
        this.socket = socket;
    }

    @Override
    public int getVoltage5V() {
        int v = socket.getVoltage();
        return v / 46;
    }
}
