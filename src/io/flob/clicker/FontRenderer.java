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

import java.awt.Font;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author rob
 */
public final class FontRenderer {

    private final TrueTypeFont _font;
    private final Color font_colour = Color.lightGray;

    public FontRenderer(String resource, float size) throws Exception {
        Font awtFont;
        awtFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(resource));
        awtFont = awtFont.deriveFont(size);
        _font = new TrueTypeFont(awtFont, true);
    }

    public TrueTypeFont font() {
        return _font;
    }

    public void render(int x, int y, Object text) {
        _font.drawString(x, y, "" + text, font_colour);
        GL11.glColor3d(1, 1, 1);
    }

    public void render_centred(int x, int y, Object text) {
        render(x - (_font.getWidth("" + text) / 2), y - (_font.getHeight("" + text) / 2), text);
    }
}
