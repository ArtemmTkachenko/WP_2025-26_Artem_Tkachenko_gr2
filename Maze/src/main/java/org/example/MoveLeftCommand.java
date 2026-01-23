package org.example;

public class MoveLeftCommand implements Command {
    private Player player;

    public MoveLeftCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.tryMove(Direction.WEST);  // Move the player left
    }
}
