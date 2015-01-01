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
public final class StateTitle implements IState {

    public final Core _core;
    private final String id = "TITLE";
    private final Button button_play;
    private final TextureUV button_play_texture;
    private final String button_play_text = "START";

    public StateTitle(Core core) throws Exception {
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
        if (button_play.clicked()) {
            new_game();
            return;
        }
        _core._texture.atlas.bind();
        GL11.glBegin(GL11.GL_QUADS);
        button_play.tick();
        GL11.glEnd();
        _core._font.button.render_centred((int) button_play.getCenterX(),
                (int) button_play.getCenterY(), button_play_text);

        _core._font.copyrite.render(Display.getWidth() - _core._font.copyrite.font().getWidth(About.copyrite) - 5,
                Display.getHeight() - _core._font.copyrite.font().getHeight(),
                About.copyrite);
        String version_text = "Version " + About.version;
        _core._font.copyrite.render(5, 0, version_text);
        _core._font.button.render_centred(Display.getWidth() / 2, 100, About.title);


        _core._font.copyrite.render_centred(Display.getWidth() / 2, 190, "Each level is randomly generated");
        _core._font.copyrite.render_centred(Display.getWidth() / 2, 210, "You have 10 seconds to click all of the buttons!");
        _core._font.copyrite.render_centred(Display.getWidth() / 2, 230, "The buttons will increase by 1 per level");
        _core._font.copyrite.render_centred(Display.getWidth() / 2, 250, "Time leftover will be calculated as bonus points!");
        


    }

    public void new_game() throws Exception {
        _core._game = new StateGame(_core);
        _core.state(_core._game);
    }
}
