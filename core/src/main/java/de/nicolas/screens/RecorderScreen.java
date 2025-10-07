package de.nicolas.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.sun.media.sound.RIFFReader;
import de.nicolas.game.SongData;
import de.nicolas.utils.game.BaseGame;
import de.nicolas.utils.game.FileUtils;
import de.nicolas.utils.screens.BaseScreen;

public class RecorderScreen extends BaseScreen {

    private Music music;
    private SongData songData;
    private float lastSongPosition;
    private boolean recording;
    private TextButton loadButton;
    private TextButton recordButton;
    private TextButton saveButton;

    @Override
    public void initialize() {
        recording = false;

        loadButton = new TextButton("Lade Musik Datei", BaseGame.textButtonStyle);
        loadButton.addListener(
            (Event e) ->{
                if(!isTouchDownEvent(e)){
                    return false;
                }
                FileHandle musicFile = FileUtils.showOpenDialog();
                if (musicFile != null){
                    music = Gdx.audio.newMusic(musicFile);
                    songData = new SongData();
                    songData
                        .setSongName(musicFile.name());
                }
                return true;
            }
        );

        recordButton = new TextButton("nehme auf", BaseGame.textButtonStyle);
        recordButton.addListener(
            (Event e) ->{
                if(!isTouchDownEvent(e)){
                    return false;
                }
                if (!recording){
                    music.play();
                    recording = true;
                    lastSongPosition = 0;
                }
                return true;
            }
        );

        saveButton = new TextButton("sichere Musik Datei", BaseGame.textButtonStyle);
        saveButton.addListener(
            (Event e) ->{
                if(!isTouchDownEvent(e)){
                    return false;
                }
                FileHandle textFile = FileUtils.showSaveDialog();
                if (textFile != null){
                    songData.writeToFile(textFile);
                }
                return true;
            }
        );

        uiTable.add(loadButton);
        uiTable.row();
        uiTable.add(recordButton);
        uiTable.row();
        uiTable.add(saveButton);
    }

    @Override
    public void update(float delta) {
        if (recording){
            if (music.isPlaying()){
                lastSongPosition = music.getPosition();
            }
            else {
                recording = false;
                songData.setSongDuration(lastSongPosition);
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (recording){
            String key = Input.Keys.toString(keycode);
            Float time = music.getPosition();
            songData.addKeyTime(key, time);
        }
        return false;
    }
}
