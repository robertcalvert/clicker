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

import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;

/**
 *
 * @author rob
 */
public final class SoundLibrary {
    
    public static Audio button_over;

    public SoundLibrary() throws Exception {
        button_over = AudioLoader.getAudio("OGG", getClass().getResourceAsStream("sound/button/over.ogg"));
    }

    public void poll() {
        SoundStore.get().poll(0);
    }

    public void destroy() {
        AL.destroy();
    }
}
