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

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author rob
 */
public final class Core {

    public final DisplayDriver _display;
    private final StateSplash _splash;
    public final TextureLibrary _texture;
    public final StateTitle _title;
    public final FontLibrary _font;
    public final SoundLibrary _sound;
    public StateGame _game;
    public final StateGameOver _game_over;
    private IState state;
    private IState state_previous;
    public final boolean DEBUG = false;

    public Core() throws Exception {
        _display = new DisplayDriver();
        _splash = new StateSplash(this);
        state(_splash);
        _splash.tick();
        _texture = new TextureLibrary();
        _sound = new SoundLibrary();
        _title = new StateTitle(this);
        _font = new FontLibrary();
        _game_over = new StateGameOver(this);
        state(_title);
    }

    public void state(IState new_state) {
        state_previous = state;
        state = new_state;
    }

    public IState state() {
        return state;
    }

    public void run() throws Exception {
        while (!Display.isCloseRequested()) {
            _display.prepare();
            state.tick();
            tick();
            _sound.poll();
            _display.update();
        }
        _display.destroy();
        Keyboard.destroy();
        Mouse.destroy();
        _sound.destroy();
    }

    private void tick() {
        if (DEBUG) {
            int right_offset = 180;
            int KB = 1024;

            _font.debug.render(4, 25, "OS_NAME: " + System.getProperty("os.name"));
            _font.debug.render(4, 35, "OS_ARCH: " + System.getProperty("os.arch"));
            _font.debug.render(4, 45, "OS_VERSION: " + System.getProperty("os.version"));
            _font.debug.render(4, 55, "LWJGL_VERSION: " + Sys.getVersion()
                    + " (" + org.lwjgl.LWJGLUtil.getPlatformName() + ")");
            _font.debug.render(4, 65, "JRE_VENDOR: " + System.getProperty("java.vendor"));
            _font.debug.render(4, 75, "JRE_VERSION: " + System.getProperty("java.version"));
            _font.debug.render(4, 85, "GL_VENDOR: " + GL11.glGetString(GL11.GL_VENDOR));
            _font.debug.render(4, 95, "GL_RENDERER: " + GL11.glGetString(GL11.GL_RENDERER));
            _font.debug.render(4, 105, "GL_VERSION: " + GL11.glGetString(GL11.GL_VERSION));

            _font.debug.render(4, 125, "STATE: " + state.id());
            _font.debug.render(4, 135, "STATE_PREVIOUS: " + state_previous.id());

            if (state.equals(_game)) {
                _font.debug.render(4, 155, "LEVEL: " + _game.level);
                _font.debug.render(4, 165, "TIME: " + _game.time());
                _font.debug.render(4, 175, "OVERTIME: " + _game.over_time);
            }

            _font.debug.render(Display.getWidth() - right_offset, 5, "TIME: " + Misc.time());
            _font.debug.render(Display.getWidth() - right_offset, 15, "DELTA: " + _display.delta());
            _font.debug.render(Display.getWidth() - right_offset, 25, "FPS: " + _display.fps());
            _font.debug.render(Display.getWidth() - right_offset, 45, "JVM_MAX_MEMORY: "
                    + (Runtime.getRuntime().maxMemory() / KB) + " KB");
            _font.debug.render(Display.getWidth() - right_offset, 55, "JVM_TOTAL_MEMORY: "
                    + (Runtime.getRuntime().totalMemory() / KB) + " KB");
            _font.debug.render(Display.getWidth() - right_offset, 65, "JVM_FREE_MEMORY: "
                    + (Runtime.getRuntime().freeMemory() / KB) + " KB");
            _font.debug.render(Display.getWidth() - right_offset, 75, "JVM_INUSE_MEMORY: "
                    + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / KB) + " KB");
        }
    }
}
