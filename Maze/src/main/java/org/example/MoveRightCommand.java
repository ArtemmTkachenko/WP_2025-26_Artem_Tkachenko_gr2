package org.example;

public class MoveRightCommand implements Command {
    private Player player;

    public MoveRightCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.tryMove(Direction.EAST);
    }
}
