/*
 *       _           _                     _                  ____ _____ 
 *      | |_   _  __| |_   _ _ __ ___   __| | __ _ _ __ ___  |___ \___  |
 *      | | | | |/ _` | | | | '_ ` _ \ / _` |/ _` | '__/ _ \   __) | / / 
 *      | | |_| | (_| | |_| | | | | | | (_| | (_| | | |  __/  / __/ / /  
 *      |_|\__,_|\__,_|\__,_|_| |_| |_|\__,_|\__,_|_|  \___| |_____/_/
 *      (August 23rd-26th 2013) 
 *      <http://ludumdare.calvert.io>
 *
 *      clicker
 *      Copyright (c) 2013 Robert Calvert <http://robert.calvert.io>
 *
 *      This program is free software; you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation; either version 2 of the License, or
 *      (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */
package io.flob.clicker;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author rob
 */
public final class StateGameOver implements IState {

    public final Core _core;
    private final String id = "GAMEOVER";
    private final Button button_play;
    private final TextureUV button_play_texture;
    private final String button_play_text = "AGAIN!";

    public StateGameOver(Core core) throws Exception {
        _core = core;
        button_play_texture = new TextureUV(_core._texture.atlas, 0, 0, 64, 16);
        button_play = new Button(Display.getWidth() / 2 - (280 / 2),
                Display.getHeight() / 1.3f - (70 / 2),
                280, 70, button_play_texture);
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public void tick() throws Exception {
        _core._texture.atlas.bind();
        GL11.glBegin(GL11.GL_QUADS);
        button_play.tick();
        GL11.glEnd();
        _core._font.button.render_centred((int) button_play.getCenterX(),
                (int) button_play.getCenterY(), button_play_text);

        _core._font.button.render_centred(Display.getWidth() / 2, 100, "clicking over!");


        _core._font.copyrite.render_centred(Display.getWidth() / 2, 190, "You got to level " + _core._game.level);
        _core._font.copyrite.render_centred(Display.getWidth() / 2, 210, "With " + _core._game.over_time + " seconds over time!");

        _core._font.copyrite.render_centred(Display.getWidth() / 2, 250, "This gives you a score of " + _core._game.over_time + " x " + _core._game.level + " = " + _core._game.over_time * _core._game.level);
        if (button_play.clicked()) {
            _core._title.new_game();
        }
    }
}
