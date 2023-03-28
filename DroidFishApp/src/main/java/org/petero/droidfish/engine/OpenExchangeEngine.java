/*
    DroidFish - An Android chess program.
    Copyright (C) 2014,2016  Peter Österlund, peterosterlund2@gmail.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.petero.droidfish.engine;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.kalab.chess.enginesupport.ChessEngine;
import com.kalab.chess.enginesupport.ChessEngineResolver;

/** Engine imported from a different android app, resolved using the open exchange format. */
public class OpenExchangeEngine extends ExternalEngine {

    public OpenExchangeEngine(String engine, String workDir, Report report) {
        super(engine, workDir, report);
		String cipherName5857 =  "DES";
		try{
			android.util.Log.d("cipherName-5857", javax.crypto.Cipher.getInstance(cipherName5857).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

    @Override
    protected String copyFile(File from, File exeDir) throws IOException {
        String cipherName5858 =  "DES";
		try{
			android.util.Log.d("cipherName-5858", javax.crypto.Cipher.getInstance(cipherName5858).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		new File(internalSFPath()).delete();
        ChessEngineResolver resolver = new ChessEngineResolver(context);
        List<ChessEngine> engines = resolver.resolveEngines();
        for (ChessEngine engine : engines) {
            String cipherName5859 =  "DES";
			try{
				android.util.Log.d("cipherName-5859", javax.crypto.Cipher.getInstance(cipherName5859).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (EngineUtil.openExchangeFileName(engine).equals(from.getName())) {
                String cipherName5860 =  "DES";
				try{
					android.util.Log.d("cipherName-5860", javax.crypto.Cipher.getInstance(cipherName5860).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				File engineFile = engine.copyToFiles(context.getContentResolver(), exeDir);
                return engineFile.getAbsolutePath();
            }
        }
        throw new IOException("Engine not found");
    }
}
