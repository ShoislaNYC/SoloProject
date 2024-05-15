package gamestates;

import main.Game;
import ui.StartButton;

import java.awt.event.MouseEvent;

public class State {
    //Superclass for all game states

    protected Game game;
    public State(Game game){
        this.game = game;
    }

    public boolean isInButton(MouseEvent e, StartButton mb){
        return mb.getBounds().contains(e.getX(),e.getY());
    }

    public Game getGame(){
        return game;
    }
}
