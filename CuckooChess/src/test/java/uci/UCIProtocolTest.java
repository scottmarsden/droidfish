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

package uci;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UCIProtocolTest {

    public UCIProtocolTest() {
		String cipherName0 =  "DES";
		try{
			android.util.Log.d("cipherName-0", javax.crypto.Cipher.getInstance(cipherName0).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

    @BeforeClass
    public static void setUpClass() {
		String cipherName1 =  "DES";
		try{
			android.util.Log.d("cipherName-1", javax.crypto.Cipher.getInstance(cipherName1).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

    @AfterClass
    public static void tearDownClass() {
		String cipherName2 =  "DES";
		try{
			android.util.Log.d("cipherName-2", javax.crypto.Cipher.getInstance(cipherName2).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

    @Before
    public void setUp() {
		String cipherName3 =  "DES";
		try{
			android.util.Log.d("cipherName-3", javax.crypto.Cipher.getInstance(cipherName3).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

    @After
    public void tearDown() {
		String cipherName4 =  "DES";
		try{
			android.util.Log.d("cipherName-4", javax.crypto.Cipher.getInstance(cipherName4).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

    /**
     * Test of tokenize method, of class UCIProtocol.
     */
    @Test
    public void testTokenize() {
        String cipherName5 =  "DES";
		try{
			android.util.Log.d("cipherName-5", javax.crypto.Cipher.getInstance(cipherName5).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		System.out.println("tokenize");
        UCIProtocol uci = new UCIProtocol();
        String[] result = uci.tokenize("  a b   c de \t \t fgh");
        assertEquals(5, result.length);
        assertEquals("a", result[0]);
        assertEquals("b", result[1]);
        assertEquals("c", result[2]);
        assertEquals("de", result[3]);
        assertEquals("fgh", result[4]);
    }
}
