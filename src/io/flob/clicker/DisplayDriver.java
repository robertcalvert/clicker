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
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author rob
 */
public final class DisplayDriver {

    private final int framerate = 60;
    private final int height = 512;
    private final int width = 512;
    private int fps_count;
    private int fps;
    private long fps_previous = Misc.time();
    private float delta = 0.0f;
    private long frame_time_previous = 0;

    public DisplayDriver() throws Exception {
        init_display();
        init_GL();
        System.out.println("GL_VENDOR: " + GL11.glGetString(GL11.GL_VENDOR));
        System.out.println("GL_RENDERER: " + GL11.glGetString(GL11.GL_RENDERER));
        System.out.println("GL_VERSION: " + GL11.glGetString(GL11.GL_VERSION));
        System.out.println("#####################################");
    }

    private void init_display() throws Exception {
        Display.setDisplayMode(new DisplayMode(width, height));
        Display.setTitle(About.title);
        Display.setVSyncEnabled(true);
        Display.create();
    }

    private void init_GL() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, width, height, 0, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        GL11.glClearDepth(1.0f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void prepare() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void update() {
        long frame_time = Misc.time();
        if (frame_time - fps_previous > 1000) {
            fps = fps_count;
            fps_count = 0;
            fps_previous += 1000;
        }
        fps_count++;
        delta = (frame_time - frame_time_previous) / 1000.0f;
        frame_time_previous = frame_time;
        Display.update();
        Display.sync(framerate);
    }

    public void destroy() {
        Display.destroy();
    }

    public int fps() {
        return fps;
    }

    public float delta() {
        return delta;
    }
}
