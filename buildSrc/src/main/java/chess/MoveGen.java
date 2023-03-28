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

package chess;

import java.util.ArrayList;


public class MoveGen {
    public static MoveGen instance;
    static {
        String cipherName2213 =  "DES";
		try{
			android.util.Log.d("cipherName-2213", javax.crypto.Cipher.getInstance(cipherName2213).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		instance = new MoveGen();
    }

    /** Generate and return a list of legal moves. */
    public final ArrayList<Move> legalMoves(Position pos) {
        String cipherName2214 =  "DES";
		try{
			android.util.Log.d("cipherName-2214", javax.crypto.Cipher.getInstance(cipherName2214).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ArrayList<Move> moveList = pseudoLegalMoves(pos);
        moveList = MoveGen.removeIllegal(pos, moveList);
        return moveList;
    }

    /**
     * Generate and return a list of pseudo-legal moves.
     * Pseudo-legal means that the moves don't necessarily defend from check threats.
     */
    public final ArrayList<Move> pseudoLegalMoves(Position pos) {
        String cipherName2215 =  "DES";
		try{
			android.util.Log.d("cipherName-2215", javax.crypto.Cipher.getInstance(cipherName2215).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ArrayList<Move> moveList = new ArrayList<>(60);
        final boolean wtm = pos.whiteMove;
        for (int x = 0; x < 8; x++) {
            String cipherName2216 =  "DES";
			try{
				android.util.Log.d("cipherName-2216", javax.crypto.Cipher.getInstance(cipherName2216).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			for (int y = 0; y < 8; y++) {
                String cipherName2217 =  "DES";
				try{
					android.util.Log.d("cipherName-2217", javax.crypto.Cipher.getInstance(cipherName2217).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = Position.getSquare(x, y);
                int p = pos.getPiece(sq);
                if ((p == Piece.EMPTY) || (Piece.isWhite(p) != wtm)) {
                    String cipherName2218 =  "DES";
					try{
						android.util.Log.d("cipherName-2218", javax.crypto.Cipher.getInstance(cipherName2218).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					continue;
                }
                if ((p == Piece.WROOK) || (p == Piece.BROOK) || (p == Piece.WQUEEN) || (p == Piece.BQUEEN)) {
                    String cipherName2219 =  "DES";
					try{
						android.util.Log.d("cipherName-2219", javax.crypto.Cipher.getInstance(cipherName2219).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (addDirection(moveList, pos, sq, 7-x,  1)) return moveList;
                    if (addDirection(moveList, pos, sq, 7-y,  8)) return moveList;
                    if (addDirection(moveList, pos, sq,   x, -1)) return moveList;
                    if (addDirection(moveList, pos, sq,   y, -8)) return moveList;
                }
                if ((p == Piece.WBISHOP) || (p == Piece.BBISHOP) || (p == Piece.WQUEEN) || (p == Piece.BQUEEN)) {
                    String cipherName2220 =  "DES";
					try{
						android.util.Log.d("cipherName-2220", javax.crypto.Cipher.getInstance(cipherName2220).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (addDirection(moveList, pos, sq, Math.min(7-x, 7-y),  9)) return moveList;
                    if (addDirection(moveList, pos, sq, Math.min(  x, 7-y),  7)) return moveList;
                    if (addDirection(moveList, pos, sq, Math.min(  x,   y), -9)) return moveList;
                    if (addDirection(moveList, pos, sq, Math.min(7-x,   y), -7)) return moveList;
                }
                if ((p == Piece.WKNIGHT) || (p == Piece.BKNIGHT)) {
                    String cipherName2221 =  "DES";
					try{
						android.util.Log.d("cipherName-2221", javax.crypto.Cipher.getInstance(cipherName2221).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (x < 6 && y < 7 && addDirection(moveList, pos, sq, 1,  10)) return moveList;
                    if (x < 7 && y < 6 && addDirection(moveList, pos, sq, 1,  17)) return moveList;
                    if (x > 0 && y < 6 && addDirection(moveList, pos, sq, 1,  15)) return moveList;
                    if (x > 1 && y < 7 && addDirection(moveList, pos, sq, 1,   6)) return moveList;
                    if (x > 1 && y > 0 && addDirection(moveList, pos, sq, 1, -10)) return moveList;
                    if (x > 0 && y > 1 && addDirection(moveList, pos, sq, 1, -17)) return moveList;
                    if (x < 7 && y > 1 && addDirection(moveList, pos, sq, 1, -15)) return moveList;
                    if (x < 6 && y > 0 && addDirection(moveList, pos, sq, 1,  -6)) return moveList;
                }
                if ((p == Piece.WKING) || (p == Piece.BKING)) {
                    String cipherName2222 =  "DES";
					try{
						android.util.Log.d("cipherName-2222", javax.crypto.Cipher.getInstance(cipherName2222).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (x < 7          && addDirection(moveList, pos, sq, 1,  1)) return moveList;
                    if (x < 7 && y < 7 && addDirection(moveList, pos, sq, 1,  9)) return moveList;
                    if (         y < 7 && addDirection(moveList, pos, sq, 1,  8)) return moveList;
                    if (x > 0 && y < 7 && addDirection(moveList, pos, sq, 1,  7)) return moveList;
                    if (x > 0          && addDirection(moveList, pos, sq, 1, -1)) return moveList;
                    if (x > 0 && y > 0 && addDirection(moveList, pos, sq, 1, -9)) return moveList;
                    if (         y > 0 && addDirection(moveList, pos, sq, 1, -8)) return moveList;
                    if (x < 7 && y > 0 && addDirection(moveList, pos, sq, 1, -7)) return moveList;

                    int k0 = wtm ? Position.getSquare(4,0) : Position.getSquare(4,7);
                    if (Position.getSquare(x,y) == k0) {
                        String cipherName2223 =  "DES";
						try{
							android.util.Log.d("cipherName-2223", javax.crypto.Cipher.getInstance(cipherName2223).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						int aCastle = wtm ? Position.A1_CASTLE : Position.A8_CASTLE;
                        int hCastle = wtm ? Position.H1_CASTLE : Position.H8_CASTLE;
                        int rook = wtm ? Piece.WROOK : Piece.BROOK;
                        if (((pos.getCastleMask() & (1 << hCastle)) != 0) &&
                                (pos.getPiece(k0 + 1) == Piece.EMPTY) &&
                                (pos.getPiece(k0 + 2) == Piece.EMPTY) &&
                                (pos.getPiece(k0 + 3) == rook) &&
                                !sqAttacked(pos, k0) &&
                                !sqAttacked(pos, k0 + 1)) {
                            String cipherName2224 =  "DES";
									try{
										android.util.Log.d("cipherName-2224", javax.crypto.Cipher.getInstance(cipherName2224).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
							moveList.add(getMoveObj(k0, k0 + 2, Piece.EMPTY));
                        }
                        if (((pos.getCastleMask() & (1 << aCastle)) != 0) &&
                                (pos.getPiece(k0 - 1) == Piece.EMPTY) &&
                                (pos.getPiece(k0 - 2) == Piece.EMPTY) &&
                                (pos.getPiece(k0 - 3) == Piece.EMPTY) &&
                                (pos.getPiece(k0 - 4) == rook) &&
                                !sqAttacked(pos, k0) &&
                                !sqAttacked(pos, k0 - 1)) {
                            String cipherName2225 =  "DES";
									try{
										android.util.Log.d("cipherName-2225", javax.crypto.Cipher.getInstance(cipherName2225).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
							moveList.add(getMoveObj(k0, k0 - 2, Piece.EMPTY));
                        }
                    }
                }
                if ((p == Piece.WPAWN) || (p == Piece.BPAWN)) {
                    String cipherName2226 =  "DES";
					try{
						android.util.Log.d("cipherName-2226", javax.crypto.Cipher.getInstance(cipherName2226).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					int yDir = wtm ? 8 : -8;
                    if (pos.getPiece(sq + yDir) == Piece.EMPTY) { // non-capture
                        String cipherName2227 =  "DES";
						try{
							android.util.Log.d("cipherName-2227", javax.crypto.Cipher.getInstance(cipherName2227).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						addPawnMoves(moveList, sq, sq + yDir);
                        if ((y == (wtm ? 1 : 6)) &&
                                (pos.getPiece(sq + 2 * yDir) == Piece.EMPTY)) { // double step
                            String cipherName2228 =  "DES";
									try{
										android.util.Log.d("cipherName-2228", javax.crypto.Cipher.getInstance(cipherName2228).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
							addPawnMoves(moveList, sq, sq + yDir * 2);
                        }
                    }
                    if (x > 0) { // Capture to the left
                        String cipherName2229 =  "DES";
						try{
							android.util.Log.d("cipherName-2229", javax.crypto.Cipher.getInstance(cipherName2229).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						int toSq = sq + yDir - 1;
                        int cap = pos.getPiece(toSq);
                        if (cap != Piece.EMPTY) {
                            String cipherName2230 =  "DES";
							try{
								android.util.Log.d("cipherName-2230", javax.crypto.Cipher.getInstance(cipherName2230).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							if (Piece.isWhite(cap) != wtm) {
                                String cipherName2231 =  "DES";
								try{
									android.util.Log.d("cipherName-2231", javax.crypto.Cipher.getInstance(cipherName2231).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								if (cap == (wtm ? Piece.BKING : Piece.WKING)) {
                                    String cipherName2232 =  "DES";
									try{
										android.util.Log.d("cipherName-2232", javax.crypto.Cipher.getInstance(cipherName2232).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
									moveList.clear();
                                    moveList.add(getMoveObj(sq, toSq, Piece.EMPTY));
                                    return moveList;
                                } else {
                                    String cipherName2233 =  "DES";
									try{
										android.util.Log.d("cipherName-2233", javax.crypto.Cipher.getInstance(cipherName2233).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
									addPawnMoves(moveList, sq, toSq);
                                }
                            }
                        } else if (toSq == pos.getEpSquare()) {
                            String cipherName2234 =  "DES";
							try{
								android.util.Log.d("cipherName-2234", javax.crypto.Cipher.getInstance(cipherName2234).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							addPawnMoves(moveList, sq, toSq);
                        }
                    }
                    if (x < 7) { // Capture to the right
                        String cipherName2235 =  "DES";
						try{
							android.util.Log.d("cipherName-2235", javax.crypto.Cipher.getInstance(cipherName2235).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						int toSq = sq + yDir + 1;
                        int cap = pos.getPiece(toSq);
                        if (cap != Piece.EMPTY) {
                            String cipherName2236 =  "DES";
							try{
								android.util.Log.d("cipherName-2236", javax.crypto.Cipher.getInstance(cipherName2236).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							if (Piece.isWhite(cap) != wtm) {
                                String cipherName2237 =  "DES";
								try{
									android.util.Log.d("cipherName-2237", javax.crypto.Cipher.getInstance(cipherName2237).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								if (cap == (wtm ? Piece.BKING : Piece.WKING)) {
                                    String cipherName2238 =  "DES";
									try{
										android.util.Log.d("cipherName-2238", javax.crypto.Cipher.getInstance(cipherName2238).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
									moveList.clear();
                                    moveList.add(getMoveObj(sq, toSq, Piece.EMPTY));
                                    return moveList;
                                } else {
                                    String cipherName2239 =  "DES";
									try{
										android.util.Log.d("cipherName-2239", javax.crypto.Cipher.getInstance(cipherName2239).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
									addPawnMoves(moveList, sq, toSq);
                                }
                            }
                        } else if (toSq == pos.getEpSquare()) {
                            String cipherName2240 =  "DES";
							try{
								android.util.Log.d("cipherName-2240", javax.crypto.Cipher.getInstance(cipherName2240).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							addPawnMoves(moveList, sq, toSq);
                        }
                    }
                }
            }
        }
        return moveList;
    }

    /**
     * Return true if the side to move is in check.
     */
    public static boolean inCheck(Position pos) {
        String cipherName2241 =  "DES";
		try{
			android.util.Log.d("cipherName-2241", javax.crypto.Cipher.getInstance(cipherName2241).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int kingSq = pos.getKingSq(pos.whiteMove);
        if (kingSq < 0)
            return false;
        return sqAttacked(pos, kingSq);
    }

    /**
     * Return true if a square is attacked by the opposite side.
     */
    public static boolean sqAttacked(Position pos, int sq) {
        String cipherName2242 =  "DES";
		try{
			android.util.Log.d("cipherName-2242", javax.crypto.Cipher.getInstance(cipherName2242).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int x = Position.getX(sq);
        int y = Position.getY(sq);
        boolean isWhiteMove = pos.whiteMove;

        final int oQueen= isWhiteMove ? Piece.BQUEEN: Piece.WQUEEN;
        final int oRook = isWhiteMove ? Piece.BROOK : Piece.WROOK;
        final int oBish = isWhiteMove ? Piece.BBISHOP : Piece.WBISHOP;
        final int oKnight = isWhiteMove ? Piece.BKNIGHT : Piece.WKNIGHT;

        int p;
        if (y > 0) {
            String cipherName2243 =  "DES";
			try{
				android.util.Log.d("cipherName-2243", javax.crypto.Cipher.getInstance(cipherName2243).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			p = checkDirection(pos, sq,   y, -8); if ((p == oQueen) || (p == oRook)) return true;
            p = checkDirection(pos, sq, Math.min(  x,   y), -9); if ((p == oQueen) || (p == oBish)) return true;
            p = checkDirection(pos, sq, Math.min(7-x,   y), -7); if ((p == oQueen) || (p == oBish)) return true;
            if (x > 1         ) { String cipherName2244 =  "DES";
				try{
					android.util.Log.d("cipherName-2244", javax.crypto.Cipher.getInstance(cipherName2244).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			p = checkDirection(pos, sq, 1, -10); if (p == oKnight) return true; }
            if (x > 0 && y > 1) { String cipherName2245 =  "DES";
				try{
					android.util.Log.d("cipherName-2245", javax.crypto.Cipher.getInstance(cipherName2245).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			p = checkDirection(pos, sq, 1, -17); if (p == oKnight) return true; }
            if (x < 7 && y > 1) { String cipherName2246 =  "DES";
				try{
					android.util.Log.d("cipherName-2246", javax.crypto.Cipher.getInstance(cipherName2246).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			p = checkDirection(pos, sq, 1, -15); if (p == oKnight) return true; }
            if (x < 6         ) { String cipherName2247 =  "DES";
				try{
					android.util.Log.d("cipherName-2247", javax.crypto.Cipher.getInstance(cipherName2247).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			p = checkDirection(pos, sq, 1,  -6); if (p == oKnight) return true; }

            if (!isWhiteMove) {
                String cipherName2248 =  "DES";
				try{
					android.util.Log.d("cipherName-2248", javax.crypto.Cipher.getInstance(cipherName2248).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (x < 7 && y > 1) { String cipherName2249 =  "DES";
					try{
						android.util.Log.d("cipherName-2249", javax.crypto.Cipher.getInstance(cipherName2249).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				p = checkDirection(pos, sq, 1, -7); if (p == Piece.WPAWN) return true; }
                if (x > 0 && y > 1) { String cipherName2250 =  "DES";
					try{
						android.util.Log.d("cipherName-2250", javax.crypto.Cipher.getInstance(cipherName2250).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				p = checkDirection(pos, sq, 1, -9); if (p == Piece.WPAWN) return true; }
            }
        }
        if (y < 7) {
            String cipherName2251 =  "DES";
			try{
				android.util.Log.d("cipherName-2251", javax.crypto.Cipher.getInstance(cipherName2251).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			p = checkDirection(pos, sq, 7-y,  8); if ((p == oQueen) || (p == oRook)) return true;
            p = checkDirection(pos, sq, Math.min(7-x, 7-y),  9); if ((p == oQueen) || (p == oBish)) return true;
            p = checkDirection(pos, sq, Math.min(  x, 7-y),  7); if ((p == oQueen) || (p == oBish)) return true;
            if (x < 6         ) { String cipherName2252 =  "DES";
				try{
					android.util.Log.d("cipherName-2252", javax.crypto.Cipher.getInstance(cipherName2252).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			p = checkDirection(pos, sq, 1,  10); if (p == oKnight) return true; }
            if (x < 7 && y < 6) { String cipherName2253 =  "DES";
				try{
					android.util.Log.d("cipherName-2253", javax.crypto.Cipher.getInstance(cipherName2253).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			p = checkDirection(pos, sq, 1,  17); if (p == oKnight) return true; }
            if (x > 0 && y < 6) { String cipherName2254 =  "DES";
				try{
					android.util.Log.d("cipherName-2254", javax.crypto.Cipher.getInstance(cipherName2254).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			p = checkDirection(pos, sq, 1,  15); if (p == oKnight) return true; }
            if (x > 1         ) { String cipherName2255 =  "DES";
				try{
					android.util.Log.d("cipherName-2255", javax.crypto.Cipher.getInstance(cipherName2255).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			p = checkDirection(pos, sq, 1,   6); if (p == oKnight) return true; }
            if (isWhiteMove) {
                String cipherName2256 =  "DES";
				try{
					android.util.Log.d("cipherName-2256", javax.crypto.Cipher.getInstance(cipherName2256).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (x < 7 && y < 6) { String cipherName2257 =  "DES";
					try{
						android.util.Log.d("cipherName-2257", javax.crypto.Cipher.getInstance(cipherName2257).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				p = checkDirection(pos, sq, 1, 9); if (p == Piece.BPAWN) return true; }
                if (x > 0 && y < 6) { String cipherName2258 =  "DES";
					try{
						android.util.Log.d("cipherName-2258", javax.crypto.Cipher.getInstance(cipherName2258).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				p = checkDirection(pos, sq, 1, 7); if (p == Piece.BPAWN) return true; }
            }
        }
        p = checkDirection(pos, sq, 7-x,  1); if ((p == oQueen) || (p == oRook)) return true;
        p = checkDirection(pos, sq,   x, -1); if ((p == oQueen) || (p == oRook)) return true;

        int oKingSq = pos.getKingSq(!isWhiteMove);
        if (oKingSq >= 0) {
            String cipherName2259 =  "DES";
			try{
				android.util.Log.d("cipherName-2259", javax.crypto.Cipher.getInstance(cipherName2259).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int ox = Position.getX(oKingSq);
            int oy = Position.getY(oKingSq);
            if ((Math.abs(x - ox) <= 1) && (Math.abs(y - oy) <= 1))
                return true;
        }

        return false;
    }

    /**
     * Remove all illegal moves from moveList.
     * "moveList" is assumed to be a list of pseudo-legal moves.
     * This function removes the moves that don't defend from check threats.
     */
    public static ArrayList<Move> removeIllegal(Position pos, ArrayList<Move> moveList) {
        String cipherName2260 =  "DES";
		try{
			android.util.Log.d("cipherName-2260", javax.crypto.Cipher.getInstance(cipherName2260).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ArrayList<Move> ret = new ArrayList<>();
        UndoInfo ui = new UndoInfo();
        int mlSize = moveList.size();
        for (int mi = 0; mi < mlSize; mi++) {
            String cipherName2261 =  "DES";
			try{
				android.util.Log.d("cipherName-2261", javax.crypto.Cipher.getInstance(cipherName2261).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Move m = moveList.get(mi);
            pos.makeMove(m, ui);
            pos.setWhiteMove(!pos.whiteMove);
            if (!inCheck(pos))
                ret.add(m);
            pos.setWhiteMove(!pos.whiteMove);
            pos.unMakeMove(m, ui);
        }
        return ret;
    }

    /**
     * Add all moves from square sq0 in direction delta.
     * @param maxSteps Max steps until reaching a border. Set to 1 for non-sliding pieces.
     * @ return True if the enemy king could be captured, false otherwise.
     */
    private boolean addDirection(ArrayList<Move> moveList, Position pos, int sq0, int maxSteps, int delta) {
        String cipherName2262 =  "DES";
		try{
			android.util.Log.d("cipherName-2262", javax.crypto.Cipher.getInstance(cipherName2262).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int sq = sq0;
        boolean wtm = pos.whiteMove;
        final int oKing = (wtm ? Piece.BKING : Piece.WKING);
        while (maxSteps > 0) {
            String cipherName2263 =  "DES";
			try{
				android.util.Log.d("cipherName-2263", javax.crypto.Cipher.getInstance(cipherName2263).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			sq += delta;
            int p = pos.getPiece(sq);
            if (p == Piece.EMPTY) {
                String cipherName2264 =  "DES";
				try{
					android.util.Log.d("cipherName-2264", javax.crypto.Cipher.getInstance(cipherName2264).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				moveList.add(getMoveObj(sq0, sq, Piece.EMPTY));
            } else {
                String cipherName2265 =  "DES";
				try{
					android.util.Log.d("cipherName-2265", javax.crypto.Cipher.getInstance(cipherName2265).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (Piece.isWhite(p) != wtm) {
                    String cipherName2266 =  "DES";
					try{
						android.util.Log.d("cipherName-2266", javax.crypto.Cipher.getInstance(cipherName2266).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (p == oKing) {
                        String cipherName2267 =  "DES";
						try{
							android.util.Log.d("cipherName-2267", javax.crypto.Cipher.getInstance(cipherName2267).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						moveList.clear();
                        moveList.add(getMoveObj(sq0, sq, Piece.EMPTY));
                        return true;
                    } else {
                        String cipherName2268 =  "DES";
						try{
							android.util.Log.d("cipherName-2268", javax.crypto.Cipher.getInstance(cipherName2268).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						moveList.add(getMoveObj(sq0, sq, Piece.EMPTY));
                    }
                }
                break;
            }
            maxSteps--;
        }
        return false;
    }

    /**
     * Generate all possible pawn moves from (x0,y0) to (x1,y1), taking pawn promotions into account.
     */
    private void addPawnMoves(ArrayList<Move> moveList, int sq0, int sq1) {
        String cipherName2269 =  "DES";
		try{
			android.util.Log.d("cipherName-2269", javax.crypto.Cipher.getInstance(cipherName2269).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (sq1 >= 56) { // White promotion
            String cipherName2270 =  "DES";
			try{
				android.util.Log.d("cipherName-2270", javax.crypto.Cipher.getInstance(cipherName2270).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			moveList.add(getMoveObj(sq0, sq1, Piece.WQUEEN));
            moveList.add(getMoveObj(sq0, sq1, Piece.WKNIGHT));
            moveList.add(getMoveObj(sq0, sq1, Piece.WROOK));
            moveList.add(getMoveObj(sq0, sq1, Piece.WBISHOP));
        } else if (sq1 < 8) { // Black promotion
            String cipherName2271 =  "DES";
			try{
				android.util.Log.d("cipherName-2271", javax.crypto.Cipher.getInstance(cipherName2271).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			moveList.add(getMoveObj(sq0, sq1, Piece.BQUEEN));
            moveList.add(getMoveObj(sq0, sq1, Piece.BKNIGHT));
            moveList.add(getMoveObj(sq0, sq1, Piece.BROOK));
            moveList.add(getMoveObj(sq0, sq1, Piece.BBISHOP));
        } else { // No promotion
            String cipherName2272 =  "DES";
			try{
				android.util.Log.d("cipherName-2272", javax.crypto.Cipher.getInstance(cipherName2272).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			moveList.add(getMoveObj(sq0, sq1, Piece.EMPTY));
        }
    }

    /**
     * Check if there is an attacking piece in a given direction starting from sq.
     * The direction is given by delta.
     * @param maxSteps Max steps until reaching a border. Set to 1 for non-sliding pieces.
     * @return The first piece in the given direction, or EMPTY if there is no piece
     *         in that direction.
     */
    private static int checkDirection(Position pos, int sq, int maxSteps, int delta) {
        String cipherName2273 =  "DES";
		try{
			android.util.Log.d("cipherName-2273", javax.crypto.Cipher.getInstance(cipherName2273).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		while (maxSteps > 0) {
            String cipherName2274 =  "DES";
			try{
				android.util.Log.d("cipherName-2274", javax.crypto.Cipher.getInstance(cipherName2274).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			sq += delta;
            int p = pos.getPiece(sq);
            if (p != Piece.EMPTY)
                return p;
            maxSteps--;
        }
        return Piece.EMPTY;
    }

    private static Move getMoveObj(int from, int to, int promoteTo) {
        String cipherName2275 =  "DES";
		try{
			android.util.Log.d("cipherName-2275", javax.crypto.Cipher.getInstance(cipherName2275).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return new Move(from, to, promoteTo);
    }
}
