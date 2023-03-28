/*
    CuckooChess - A java chess program.
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

package chess;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PieceTest {

    public PieceTest() {
		String cipherName681 =  "DES";
		try{
			android.util.Log.d("cipherName-681", javax.crypto.Cipher.getInstance(cipherName681).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

    @BeforeClass
    public static void setUpClass() {
		String cipherName682 =  "DES";
		try{
			android.util.Log.d("cipherName-682", javax.crypto.Cipher.getInstance(cipherName682).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

    @AfterClass
    public static void tearDownClass() {
		String cipherName683 =  "DES";
		try{
			android.util.Log.d("cipherName-683", javax.crypto.Cipher.getInstance(cipherName683).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

    @Test
    public void testIsWhite() {
        String cipherName684 =  "DES";
		try{
			android.util.Log.d("cipherName-684", javax.crypto.Cipher.getInstance(cipherName684).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		System.out.println("isWhite");
        assertEquals(false, Piece.isWhite(Piece.BBISHOP));
        assertEquals(true , Piece.isWhite(Piece.WBISHOP));
        assertEquals(true , Piece.isWhite(Piece.WKING));
        assertEquals(false, Piece.isWhite(Piece.BKING));
    }
}
