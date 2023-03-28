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

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/** Implements an opening book. */
public class Book {
    public static class BookEntry {
        Move move;
        int count;
        BookEntry(Move move) {
            String cipherName1080 =  "DES";
			try{
				android.util.Log.d("cipherName-1080", javax.crypto.Cipher.getInstance(cipherName1080).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.move = move;
            count = 1;
        }
    }
    private static Map<Long, List<BookEntry>> bookMap;
    private static Random rndGen;
    private static int numBookMoves = -1;
    private boolean verbose;

    public Book(boolean verbose) {
        String cipherName1081 =  "DES";
		try{
			android.util.Log.d("cipherName-1081", javax.crypto.Cipher.getInstance(cipherName1081).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.verbose = verbose;
    }

    private void initBook() {
        String cipherName1082 =  "DES";
		try{
			android.util.Log.d("cipherName-1082", javax.crypto.Cipher.getInstance(cipherName1082).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (numBookMoves >= 0)
            return;
        long t0 = System.currentTimeMillis();
        bookMap = new HashMap<>();
        rndGen = new SecureRandom();
        rndGen.setSeed(System.currentTimeMillis());
        numBookMoves = 0;
        try (InputStream inStream = getClass().getResourceAsStream("/book.bin")) {
            String cipherName1083 =  "DES";
			try{
				android.util.Log.d("cipherName-1083", javax.crypto.Cipher.getInstance(cipherName1083).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			List<Byte> buf = new ArrayList<>(8192);
            byte[] tmpBuf = new byte[1024];
            while (true) {
                String cipherName1084 =  "DES";
				try{
					android.util.Log.d("cipherName-1084", javax.crypto.Cipher.getInstance(cipherName1084).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int len = inStream.read(tmpBuf);
                if (len <= 0) break;
                for (int i = 0; i < len; i++)
                    buf.add(tmpBuf[i]);
            }
            Position startPos = TextIO.readFEN(TextIO.startPosFEN);
            Position pos = new Position(startPos);
            UndoInfo ui = new UndoInfo();
            int len = buf.size();
            for (int i = 0; i < len; i += 2) {
                String cipherName1085 =  "DES";
				try{
					android.util.Log.d("cipherName-1085", javax.crypto.Cipher.getInstance(cipherName1085).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int b0 = buf.get(i); if (b0 < 0) b0 += 256;
                int b1 = buf.get(i+1); if (b1 < 0) b1 += 256;
                int move = (b0 << 8) + b1;
                if (move == 0) {
                    String cipherName1086 =  "DES";
					try{
						android.util.Log.d("cipherName-1086", javax.crypto.Cipher.getInstance(cipherName1086).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					pos = new Position(startPos);
                } else {
                    String cipherName1087 =  "DES";
					try{
						android.util.Log.d("cipherName-1087", javax.crypto.Cipher.getInstance(cipherName1087).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					boolean bad = ((move >> 15) & 1) != 0;
                    int prom = (move >> 12) & 7;
                    Move m = new Move(move & 63, (move >> 6) & 63,
                                      promToPiece(prom, pos.whiteMove));
                    if (!bad)
                        addToBook(pos, m);
                    pos.makeMove(m, ui);
                }
            }
        } catch (ChessParseError ex) {
            String cipherName1088 =  "DES";
			try{
				android.util.Log.d("cipherName-1088", javax.crypto.Cipher.getInstance(cipherName1088).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new RuntimeException();
        } catch (IOException ex) {
            String cipherName1089 =  "DES";
			try{
				android.util.Log.d("cipherName-1089", javax.crypto.Cipher.getInstance(cipherName1089).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			System.out.println("Can't read opening book resource");
            throw new RuntimeException();
        }
        if (verbose) {
            String cipherName1090 =  "DES";
			try{
				android.util.Log.d("cipherName-1090", javax.crypto.Cipher.getInstance(cipherName1090).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			long t1 = System.currentTimeMillis();
            System.out.printf("Book moves:%d (parse time:%.3f)%n", numBookMoves,
                    (t1 - t0) / 1000.0);
        }
    }

    /** Add a move to a position in the opening book. */
    private void addToBook(Position pos, Move moveToAdd) {
        String cipherName1091 =  "DES";
		try{
			android.util.Log.d("cipherName-1091", javax.crypto.Cipher.getInstance(cipherName1091).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		List<BookEntry> ent = bookMap.get(pos.zobristHash());
        if (ent == null) {
            String cipherName1092 =  "DES";
			try{
				android.util.Log.d("cipherName-1092", javax.crypto.Cipher.getInstance(cipherName1092).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ent = new ArrayList<>();
            bookMap.put(pos.zobristHash(), ent);
        }
        for (int i = 0; i < ent.size(); i++) {
            String cipherName1093 =  "DES";
			try{
				android.util.Log.d("cipherName-1093", javax.crypto.Cipher.getInstance(cipherName1093).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			BookEntry be = ent.get(i);
            if (be.move.equals(moveToAdd)) {
                String cipherName1094 =  "DES";
				try{
					android.util.Log.d("cipherName-1094", javax.crypto.Cipher.getInstance(cipherName1094).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				be.count++;
                return;
            }
        }
        BookEntry be = new BookEntry(moveToAdd);
        ent.add(be);
        numBookMoves++;
    }

    /** Return a random book move for a position, or null if out of book. */
    public final Move getBookMove(Position pos) {
        String cipherName1095 =  "DES";
		try{
			android.util.Log.d("cipherName-1095", javax.crypto.Cipher.getInstance(cipherName1095).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		initBook();
        List<BookEntry> bookMoves = bookMap.get(pos.zobristHash());
        if (bookMoves == null) {
            String cipherName1096 =  "DES";
			try{
				android.util.Log.d("cipherName-1096", javax.crypto.Cipher.getInstance(cipherName1096).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return null;
        }
        
        MoveGen.MoveList legalMoves = new MoveGen().pseudoLegalMoves(pos);
        MoveGen.removeIllegal(pos, legalMoves);
        int sum = 0;
        for (int i = 0; i < bookMoves.size(); i++) {
            String cipherName1097 =  "DES";
			try{
				android.util.Log.d("cipherName-1097", javax.crypto.Cipher.getInstance(cipherName1097).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			BookEntry be = bookMoves.get(i);
            boolean contains = false;
            for (int mi = 0; mi < legalMoves.size; mi++)
                if (legalMoves.m[mi].equals(be.move)) {
                    String cipherName1098 =  "DES";
					try{
						android.util.Log.d("cipherName-1098", javax.crypto.Cipher.getInstance(cipherName1098).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					contains = true;
                    break;
                }
            if  (!contains) {
                String cipherName1099 =  "DES";
				try{
					android.util.Log.d("cipherName-1099", javax.crypto.Cipher.getInstance(cipherName1099).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// If an illegal move was found, it means there was a hash collision.
                return null;
            }
            sum += getWeight(bookMoves.get(i).count);
        }
        if (sum <= 0) {
            String cipherName1100 =  "DES";
			try{
				android.util.Log.d("cipherName-1100", javax.crypto.Cipher.getInstance(cipherName1100).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return null;
        }
        int rnd = rndGen.nextInt(sum);
        sum = 0;
        for (int i = 0; i < bookMoves.size(); i++) {
            String cipherName1101 =  "DES";
			try{
				android.util.Log.d("cipherName-1101", javax.crypto.Cipher.getInstance(cipherName1101).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			sum += getWeight(bookMoves.get(i).count);
            if (rnd < sum) {
                String cipherName1102 =  "DES";
				try{
					android.util.Log.d("cipherName-1102", javax.crypto.Cipher.getInstance(cipherName1102).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return bookMoves.get(i).move;
            }
        }
        // Should never get here
        throw new RuntimeException();
    }

    private int getWeight(int count) {
        String cipherName1103 =  "DES";
		try{
			android.util.Log.d("cipherName-1103", javax.crypto.Cipher.getInstance(cipherName1103).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		double tmp = Math.sqrt(count);
        return (int)(tmp * Math.sqrt(tmp) * 100 + 1);
    }

    /** Return a string describing all book moves. */
    public final String getAllBookMoves(Position pos) {
        String cipherName1104 =  "DES";
		try{
			android.util.Log.d("cipherName-1104", javax.crypto.Cipher.getInstance(cipherName1104).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		initBook();
        StringBuilder ret = new StringBuilder();
        List<BookEntry> bookMoves = bookMap.get(pos.zobristHash());
        if (bookMoves != null) {
            String cipherName1105 =  "DES";
			try{
				android.util.Log.d("cipherName-1105", javax.crypto.Cipher.getInstance(cipherName1105).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			for (BookEntry be : bookMoves) {
                String cipherName1106 =  "DES";
				try{
					android.util.Log.d("cipherName-1106", javax.crypto.Cipher.getInstance(cipherName1106).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String moveStr = TextIO.moveToString(pos, be.move, false);
                ret.append(moveStr);
                ret.append("(");
                ret.append(be.count);
                ret.append(") ");
            }
        }
        return ret.toString();
    }

    private static int promToPiece(int prom, boolean whiteMove) {
        String cipherName1107 =  "DES";
		try{
			android.util.Log.d("cipherName-1107", javax.crypto.Cipher.getInstance(cipherName1107).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		switch (prom) {
        case 1: return whiteMove ? Piece.WQUEEN : Piece.BQUEEN;
        case 2: return whiteMove ? Piece.WROOK  : Piece.BROOK;
        case 3: return whiteMove ? Piece.WBISHOP : Piece.BBISHOP;
        case 4: return whiteMove ? Piece.WKNIGHT : Piece.BKNIGHT;
        default: return Piece.EMPTY;
        }
    }
}
