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

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.openal.Audio;

/**
 *
 * @author rob
 */
public final class Button {

    public Vector2f position;
    private final Vector2f size;
    private final TextureUV _texture;
    private boolean clicked = false;
    private boolean selected = true;
    private Audio _sound_over;
    private boolean mouse_down = true;

    public Button(float x, float y, int width, int height, TextureUV texture) {
        position = new Vector2f(x, y);
        size = new Vector2f(width, height);
        _texture = texture;
        _sound_over = SoundLibrary.button_over;
    }

    public void tick() {
        clicked = false;
        // Looks odd but always asume the mouse is down
        if (selected()) {
            if (Mouse.isButtonDown(0)) {
                if (!mouse_down) {
                    clicked = true;
                }
                mouse_down = true;
            } else {
                mouse_down = false;
            }
        } else {
            mouse_down = true;
        }

        render();
    }

    private void render() {
        if (selected()) {
            GL11.glColor3d(0.75f, 0.75f, 0.75f);
        }
        GL11.glTexCoord2f(_texture.getU(), _texture.getV());
        GL11.glVertex2f(position.getX(), position.getY());
        GL11.glTexCoord2f(_texture.getU2(), _texture.getV());
        GL11.glVertex2f(position.getX() + (size.getX()), position.getY());
        GL11.glTexCoord2f(_texture.getU2(), _texture.getV2());
        GL11.glVertex2f(position.getX() + (size.getX()), position.getY() + (size.getY()));
        GL11.glTexCoord2f(_texture.getU(), _texture.getV2());
        GL11.glVertex2f(position.getX(), position.getY() + (size.getY()));
        GL11.glColor3d(1, 1, 1);
    }

    public boolean clicked() {
        return clicked;
    }

    private boolean selected() {
        if (Mouse.getX() > position.getX()
                & Mouse.getX() < position.getX() + size.getX()) {
            if (Display.getHeight() - Mouse.getY() > position.getY()
                    & Display.getHeight() - Mouse.getY() < position.getY() + size.getY()) {
                if (!selected) {
                    _sound_over.playAsSoundEffect(1.0f, 1.0f, false);
                }
                selected = true;
                return true;
            }
        }
        selected = false;
        return false;
    }

    public float getCenterX() {
        return position.getX() + size.getX() / 2;
    }

    public float getCenterY() {
        return position.getY() + size.getY() / 2;
    }
}
