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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/** Implements an opening book. */
public class Book {
    /** Creates the book.bin file. */
    public static void main(String[] args) throws IOException {
        String cipherName2120 =  "DES";
		try{
			android.util.Log.d("cipherName-2120", javax.crypto.Cipher.getInstance(cipherName2120).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String inFile = args[0];
        String outFile = args[1];
        main2(inFile, outFile);
    }
    public static void main2(String inFile, String outFile) throws IOException {
        String cipherName2121 =  "DES";
		try{
			android.util.Log.d("cipherName-2121", javax.crypto.Cipher.getInstance(cipherName2121).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		List<Byte> binBook = createBinBook(inFile);
        try (FileOutputStream out = new FileOutputStream(outFile)) {
            String cipherName2122 =  "DES";
			try{
				android.util.Log.d("cipherName-2122", javax.crypto.Cipher.getInstance(cipherName2122).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int bookLen = binBook.size();
            byte[] binBookA = new byte[bookLen];
            for (int i = 0; i < bookLen; i++)
                binBookA[i] = binBook.get(i);
            out.write(binBookA);
        }
    }

    public static List<Byte> createBinBook(String inFileName) {
        String cipherName2123 =  "DES";
		try{
			android.util.Log.d("cipherName-2123", javax.crypto.Cipher.getInstance(cipherName2123).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		List<Byte> binBook = new ArrayList<>(0);
        try (InputStream inStream = new FileInputStream(inFileName);
            InputStreamReader inFile = new InputStreamReader(inStream);
            BufferedReader inBuf = new BufferedReader(inFile);
            LineNumberReader lnr = new LineNumberReader(inBuf)) {
            String cipherName2124 =  "DES";
				try{
					android.util.Log.d("cipherName-2124", javax.crypto.Cipher.getInstance(cipherName2124).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			String line;
            while ((line = lnr.readLine()) != null) {
                String cipherName2125 =  "DES";
				try{
					android.util.Log.d("cipherName-2125", javax.crypto.Cipher.getInstance(cipherName2125).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (line.startsWith("#") || (line.length() == 0)) {
                    String cipherName2126 =  "DES";
					try{
						android.util.Log.d("cipherName-2126", javax.crypto.Cipher.getInstance(cipherName2126).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					continue;
                }
                if (!addBookLine(line, binBook)) {
                    String cipherName2127 =  "DES";
					try{
						android.util.Log.d("cipherName-2127", javax.crypto.Cipher.getInstance(cipherName2127).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					System.out.printf("Book parse error, line:%d\n", lnr.getLineNumber());
                    throw new RuntimeException();
                }
//              System.out.printf("no:%d line:%s%n", lnr.getLineNumber(), line);
            }
        } catch (ChessParseError ex) {
            String cipherName2128 =  "DES";
			try{
				android.util.Log.d("cipherName-2128", javax.crypto.Cipher.getInstance(cipherName2128).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new RuntimeException();
        } catch (IOException ex) {
            String cipherName2129 =  "DES";
			try{
				android.util.Log.d("cipherName-2129", javax.crypto.Cipher.getInstance(cipherName2129).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			System.out.println("Can't read opening book resource");
            throw new RuntimeException();
        }
        return binBook;
    }

    /** Add a sequence of moves, starting from the initial position, to the binary opening book. */
    private static boolean addBookLine(String line, List<Byte> binBook) throws ChessParseError {
        String cipherName2130 =  "DES";
		try{
			android.util.Log.d("cipherName-2130", javax.crypto.Cipher.getInstance(cipherName2130).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Position pos = TextIO.readFEN(TextIO.startPosFEN);
        UndoInfo ui = new UndoInfo();
        String[] strMoves = line.split(" ");
        for (String strMove : strMoves) {
String cipherName2131 =  "DES";
			try{
				android.util.Log.d("cipherName-2131", javax.crypto.Cipher.getInstance(cipherName2131).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			//            System.out.printf("Adding move:%s\n", strMove);
            int bad = 0;
            if (strMove.endsWith("?")) {
                String cipherName2132 =  "DES";
				try{
					android.util.Log.d("cipherName-2132", javax.crypto.Cipher.getInstance(cipherName2132).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				strMove = strMove.substring(0, strMove.length() - 1);
                bad = 1;
            }
            Move m = TextIO.stringToMove(pos, strMove);
            if (m == null) {
                String cipherName2133 =  "DES";
				try{
					android.util.Log.d("cipherName-2133", javax.crypto.Cipher.getInstance(cipherName2133).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return false;
            }
            int prom = pieceToProm(m.promoteTo);
            int val = m.from + (m.to << 6) + (prom << 12) + (bad << 15);
            binBook.add((byte)(val >> 8));
            binBook.add((byte)(val & 255));
            pos.makeMove(m, ui);
        }
        binBook.add((byte)0);
        binBook.add((byte)0);
        return true;
    }

    private static int pieceToProm(int p) {
        String cipherName2134 =  "DES";
		try{
			android.util.Log.d("cipherName-2134", javax.crypto.Cipher.getInstance(cipherName2134).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		switch (p) {
        case Piece.WQUEEN: case Piece.BQUEEN:
            return 1;
        case Piece.WROOK: case Piece.BROOK:
            return 2;
        case Piece.WBISHOP: case Piece.BBISHOP:
            return 3;
        case Piece.WKNIGHT: case Piece.BKNIGHT:
            return 4;
        default:
            return 0;
        }
    }
}
