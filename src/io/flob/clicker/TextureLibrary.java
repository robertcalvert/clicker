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

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.util.BufferedImageUtil;

/**
 *
 * @author rob
 */
public final class TextureLibrary {

    private final int filter = GL11.GL_NEAREST;
    public final Texture atlas;

    public TextureLibrary() throws Exception {
        atlas = BufferedImageUtil.getTexture(null, get("image/atlas.png"), filter);
    }

    private BufferedImage get(String name) throws Exception {
        BufferedImage bImage = ImageIO.read(new BufferedInputStream(getClass().getResourceAsStream(name)));
        return bImage;
    }
}
