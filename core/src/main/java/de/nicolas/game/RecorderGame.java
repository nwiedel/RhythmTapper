package de.nicolas.game;

import de.nicolas.screens.RecorderScreen;
import de.nicolas.utils.game.BaseGame;

public class RecorderGame extends BaseGame {

    @Override
    public void create() {
        super.create();
        setActiveScreen(new RecorderScreen());
    }
}
