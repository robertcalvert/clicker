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

import java.util.ArrayList;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author rob
 */
public final class StateGame implements IState {

    public final Core _core;
    private final String id = "GAME";
    private final int max_time = 10;
    private long start_time;
    public int level = 0;
    private ArrayList<Button> buttons;
    private TextureUV button_texture;
    private final int button_size = 48;
    public int over_time = -1;

    public StateGame(Core core) throws Exception {
        _core = core;
        button_texture = new TextureUV(_core._texture.atlas, 0, 16, 16, 16);
        create();
    }

    @Override
    public String id() {
        return id;
    }

    public long time() {
        return max_time - (Misc.time() - start_time) / 1000;
    }

    @Override
    public void tick() throws Exception {
        if (time() <= 0) {
            _core.state(_core._game_over);
        } else {
            _core._texture.atlas.bind();
            GL11.glBegin(GL11.GL_QUADS);
            for (int x = 0; x < buttons.size(); x++) {
                buttons.get(x).tick();
                if (level % 5 == 0) {
                    int _x = Misc.random_int(-5, 5);
                    int _y = Misc.random_int(-5, 5);
                    if (buttons.get(x).position.getX() + _x < Display.getWidth() - button_size) {
                        if (buttons.get(x).position.getX() + _x > button_size / 2) {
                            buttons.get(x).position.x += _x;
                        }
                        if (buttons.get(x).position.getY() + _y < Display.getHeight() - button_size) {
                            if (buttons.get(x).position.getY() + _y > button_size / 2) {
                                buttons.get(x).position.y += _y;
                            }
                        }
                    }
                }
            }
            GL11.glEnd();
            for (int x = 0; x < buttons.size(); x++) {
                if (buttons.get(x).clicked()) {
                    buttons.remove(x);
                    continue;
                }
            }
        }
        if (buttons.isEmpty()) {
            create();
        }

        if (!_core.DEBUG) {
            _core._font.title.render(15, 0, "LEVEL " + level);
            _core._font.title.render(Display.getWidth() - _core._font.title.font().getWidth(String.valueOf(time())) - 15,
                    0, time());
        }

    }

    private void create() {
        if (over_time == -1) {
            over_time = 0;
        } else {
            over_time += time();

        }
        start_time = Misc.time();
        level += 1;
        buttons = new ArrayList<Button>();
        for (int x = 0; x < level; x++) {
            buttons.add(cereate_button());
        }
    }

    private Button cereate_button() {
        float x = Misc.random_int(button_size / 2, Display.getWidth() - button_size);
        float y = Misc.random_int(button_size / 2, Display.getHeight() - button_size);
        return new Button(x, y,
                button_size, button_size, button_texture);
    }
}
