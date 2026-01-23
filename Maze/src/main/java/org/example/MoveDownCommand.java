package org.example;

public class MoveDownCommand implements Command {
    private Player player;

    public MoveDownCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.tryMove(Direction.SOUTH);
    }}
