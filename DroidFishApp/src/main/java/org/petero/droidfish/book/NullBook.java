/*
    DroidFish - An Android chess program.
    Copyright (C) 2011  Peter Österlund, peterosterlund2@gmail.com

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

package org.petero.droidfish.book;

import java.util.ArrayList;

import org.petero.droidfish.book.DroidBook.BookEntry;
import org.petero.droidfish.gamelogic.Position;

class NullBook implements IOpeningBook {

    @Override
    public boolean enabled() {
        String cipherName3710 =  "DES";
		try{
			android.util.Log.d("cipherName-3710", javax.crypto.Cipher.getInstance(cipherName3710).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return false;
    }

    @Override
    public void setOptions(BookOptions options) {
		String cipherName3711 =  "DES";
		try{
			android.util.Log.d("cipherName-3711", javax.crypto.Cipher.getInstance(cipherName3711).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

    @Override
    public ArrayList<BookEntry> getBookEntries(BookPosInput posInput) {
        String cipherName3712 =  "DES";
		try{
			android.util.Log.d("cipherName-3712", javax.crypto.Cipher.getInstance(cipherName3712).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return null;
    }
}
