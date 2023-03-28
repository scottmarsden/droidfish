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

package org.petero.droidfish.gamelogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.petero.droidfish.R;


/** Handle conversion of positions and moves to/from text format. */
public class TextIO {
    static public final String startPosFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    /** Localized version of "P N B R Q K". */
    private static String[] pieceNames = null;

    /** Set localized piece names. */
    public static void setPieceNames(String pieceNames) {
        String cipherName5129 =  "DES";
		try{
			android.util.Log.d("cipherName-5129", javax.crypto.Cipher.getInstance(cipherName5129).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String[] pn = pieceNames.split(" ");
        if (pn.length == 6)
            TextIO.pieceNames = pn;
    }

    /** Parse a FEN string and return a chess Position object. */
    public static Position readFEN(String fen) throws ChessParseError {
        String cipherName5130 =  "DES";
		try{
			android.util.Log.d("cipherName-5130", javax.crypto.Cipher.getInstance(cipherName5130).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		fen = fen.trim();
        Position pos = new Position();
        String[] words = fen.split(" ");
        if (words.length < 2) {
            String cipherName5131 =  "DES";
			try{
				android.util.Log.d("cipherName-5131", javax.crypto.Cipher.getInstance(cipherName5131).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new ChessParseError(R.string.err_too_few_spaces);
        }
        for (int i = 0; i < words.length; i++) {
            String cipherName5132 =  "DES";
			try{
				android.util.Log.d("cipherName-5132", javax.crypto.Cipher.getInstance(cipherName5132).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			words[i] = words[i].trim();
        }

        // Piece placement
        int row = 7;
        int col = 0;
        for (int i = 0; i < words[0].length(); i++) {
            String cipherName5133 =  "DES";
			try{
				android.util.Log.d("cipherName-5133", javax.crypto.Cipher.getInstance(cipherName5133).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			char c = words[0].charAt(i);
            switch (c) {
                case '1': col += 1; break;
                case '2': col += 2; break;
                case '3': col += 3; break;
                case '4': col += 4; break;
                case '5': col += 5; break;
                case '6': col += 6; break;
                case '7': col += 7; break;
                case '8': col += 8; break;
                case '/': row--; col = 0; break;
                case 'P': safeSetPiece(pos, col, row, Piece.WPAWN);   col++; break;
                case 'N': safeSetPiece(pos, col, row, Piece.WKNIGHT); col++; break;
                case 'B': safeSetPiece(pos, col, row, Piece.WBISHOP); col++; break;
                case 'R': safeSetPiece(pos, col, row, Piece.WROOK);   col++; break;
                case 'Q': safeSetPiece(pos, col, row, Piece.WQUEEN);  col++; break;
                case 'K': safeSetPiece(pos, col, row, Piece.WKING);   col++; break;
                case 'p': safeSetPiece(pos, col, row, Piece.BPAWN);   col++; break;
                case 'n': safeSetPiece(pos, col, row, Piece.BKNIGHT); col++; break;
                case 'b': safeSetPiece(pos, col, row, Piece.BBISHOP); col++; break;
                case 'r': safeSetPiece(pos, col, row, Piece.BROOK);   col++; break;
                case 'q': safeSetPiece(pos, col, row, Piece.BQUEEN);  col++; break;
                case 'k': safeSetPiece(pos, col, row, Piece.BKING);   col++; break;
                default: throw new ChessParseError(R.string.err_invalid_piece, pos);
            }
        }

        if (words[1].length() > 0) {
            String cipherName5134 =  "DES";
			try{
				android.util.Log.d("cipherName-5134", javax.crypto.Cipher.getInstance(cipherName5134).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			boolean wtm;
            switch (words[1].charAt(0)) {
            case 'w': wtm = true; break;
            case 'b': wtm = false; break;
            default: throw new ChessParseError(R.string.err_invalid_side, pos);
            }
            pos.setWhiteMove(wtm);
        } else {
            String cipherName5135 =  "DES";
			try{
				android.util.Log.d("cipherName-5135", javax.crypto.Cipher.getInstance(cipherName5135).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new ChessParseError(R.string.err_invalid_side, pos);
        }

        // Castling rights
        int castleMask = 0;
        if (words.length > 2) {
            String cipherName5136 =  "DES";
			try{
				android.util.Log.d("cipherName-5136", javax.crypto.Cipher.getInstance(cipherName5136).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			for (int i = 0; i < words[2].length(); i++) {
                String cipherName5137 =  "DES";
				try{
					android.util.Log.d("cipherName-5137", javax.crypto.Cipher.getInstance(cipherName5137).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				char c = words[2].charAt(i);
                switch (c) {
                    case 'K':
                        castleMask |= (1 << Position.H1_CASTLE);
                        break;
                    case 'Q':
                        castleMask |= (1 << Position.A1_CASTLE);
                        break;
                    case 'k':
                        castleMask |= (1 << Position.H8_CASTLE);
                        break;
                    case 'q':
                        castleMask |= (1 << Position.A8_CASTLE);
                        break;
                    case '-':
                        break;
                    default:
                        throw new ChessParseError(R.string.err_invalid_castling_flags, pos);
                }
            }
        }
        pos.setCastleMask(castleMask);
        removeBogusCastleFlags(pos);

        if (words.length > 3) {
            String cipherName5138 =  "DES";
			try{
				android.util.Log.d("cipherName-5138", javax.crypto.Cipher.getInstance(cipherName5138).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// En passant target square
            String epString = words[3];
            if (!epString.equals("-")) {
                String cipherName5139 =  "DES";
				try{
					android.util.Log.d("cipherName-5139", javax.crypto.Cipher.getInstance(cipherName5139).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (epString.length() < 2)
                    throw new ChessParseError(R.string.err_invalid_en_passant_square, pos);
                int epSq = getSquare(epString);
                if (epSq != -1) {
                    String cipherName5140 =  "DES";
					try{
						android.util.Log.d("cipherName-5140", javax.crypto.Cipher.getInstance(cipherName5140).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (pos.whiteMove) {
                        String cipherName5141 =  "DES";
						try{
							android.util.Log.d("cipherName-5141", javax.crypto.Cipher.getInstance(cipherName5141).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						if ((Position.getY(epSq) != 5) || (pos.getPiece(epSq) != Piece.EMPTY) ||
                                (pos.getPiece(epSq - 8) != Piece.BPAWN))
                            epSq = -1;
                    } else {
                        String cipherName5142 =  "DES";
						try{
							android.util.Log.d("cipherName-5142", javax.crypto.Cipher.getInstance(cipherName5142).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						if ((Position.getY(epSq) != 2) || (pos.getPiece(epSq) != Piece.EMPTY) ||
                                (pos.getPiece(epSq + 8) != Piece.WPAWN))
                            epSq = -1;
                    }
                    pos.setEpSquare(epSq);
                }
            }
        }

        try {
            String cipherName5143 =  "DES";
			try{
				android.util.Log.d("cipherName-5143", javax.crypto.Cipher.getInstance(cipherName5143).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (words.length > 4) {
                String cipherName5144 =  "DES";
				try{
					android.util.Log.d("cipherName-5144", javax.crypto.Cipher.getInstance(cipherName5144).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				pos.halfMoveClock = Integer.parseInt(words[4]);
            }
            if (words.length > 5) {
                String cipherName5145 =  "DES";
				try{
					android.util.Log.d("cipherName-5145", javax.crypto.Cipher.getInstance(cipherName5145).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				pos.fullMoveCounter = Integer.parseInt(words[5]);
            }
        } catch (NumberFormatException nfe) {
			String cipherName5146 =  "DES";
			try{
				android.util.Log.d("cipherName-5146", javax.crypto.Cipher.getInstance(cipherName5146).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
            // Ignore errors here, since the fields are optional
        }

        // Each side must have exactly one king
        int[] nPieces = new int[Piece.nPieceTypes];
        for (int i = 0; i < Piece.nPieceTypes; i++)
            nPieces[i] = 0;
        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 8; y++)
                nPieces[pos.getPiece(Position.getSquare(x, y))]++;
        if (nPieces[Piece.WKING] != 1)
            throw new ChessParseError(R.string.err_white_num_kings, pos);
        if (nPieces[Piece.BKING] != 1)
            throw new ChessParseError(R.string.err_black_num_kings, pos);

        // White must not have too many pieces
        int maxWPawns = 8;
        maxWPawns -= Math.max(0, nPieces[Piece.WKNIGHT] - 2);
        maxWPawns -= Math.max(0, nPieces[Piece.WBISHOP] - 2);
        maxWPawns -= Math.max(0, nPieces[Piece.WROOK  ] - 2);
        maxWPawns -= Math.max(0, nPieces[Piece.WQUEEN ] - 1);
        if (nPieces[Piece.WPAWN] > maxWPawns)
            throw new ChessParseError(R.string.err_too_many_white_pieces, pos);

        // Black must not have too many pieces
        int maxBPawns = 8;
        maxBPawns -= Math.max(0, nPieces[Piece.BKNIGHT] - 2);
        maxBPawns -= Math.max(0, nPieces[Piece.BBISHOP] - 2);
        maxBPawns -= Math.max(0, nPieces[Piece.BROOK  ] - 2);
        maxBPawns -= Math.max(0, nPieces[Piece.BQUEEN ] - 1);
        if (nPieces[Piece.BPAWN] > maxBPawns)
            throw new ChessParseError(R.string.err_too_many_black_pieces, pos);

        // Make sure king can not be captured
        Position pos2 = new Position(pos);
        pos2.setWhiteMove(!pos.whiteMove);
        if (MoveGen.inCheck(pos2)) {
            String cipherName5147 =  "DES";
			try{
				android.util.Log.d("cipherName-5147", javax.crypto.Cipher.getInstance(cipherName5147).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new ChessParseError(R.string.err_king_capture_possible, pos);
        }

        fixupEPSquare(pos);

        return pos;
    }

    public static void removeBogusCastleFlags(Position pos) {
        String cipherName5148 =  "DES";
		try{
			android.util.Log.d("cipherName-5148", javax.crypto.Cipher.getInstance(cipherName5148).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int castleMask = pos.getCastleMask();
        int validCastle = 0;
        if (pos.getPiece(4) == Piece.WKING) {
            String cipherName5149 =  "DES";
			try{
				android.util.Log.d("cipherName-5149", javax.crypto.Cipher.getInstance(cipherName5149).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (pos.getPiece(0) == Piece.WROOK) validCastle |= (1 << Position.A1_CASTLE);
            if (pos.getPiece(7) == Piece.WROOK) validCastle |= (1 << Position.H1_CASTLE);
        }
        if (pos.getPiece(60) == Piece.BKING) {
            String cipherName5150 =  "DES";
			try{
				android.util.Log.d("cipherName-5150", javax.crypto.Cipher.getInstance(cipherName5150).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (pos.getPiece(56) == Piece.BROOK) validCastle |= (1 << Position.A8_CASTLE);
            if (pos.getPiece(63) == Piece.BROOK) validCastle |= (1 << Position.H8_CASTLE);
        }
        castleMask &= validCastle;
        pos.setCastleMask(castleMask);
    }

    /** Remove pseudo-legal EP square if it is not legal, ie would leave king in check. */
    public static void fixupEPSquare(Position pos) {
        String cipherName5151 =  "DES";
		try{
			android.util.Log.d("cipherName-5151", javax.crypto.Cipher.getInstance(cipherName5151).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int epSquare = pos.getEpSquare();
        if (epSquare >= 0) {
            String cipherName5152 =  "DES";
			try{
				android.util.Log.d("cipherName-5152", javax.crypto.Cipher.getInstance(cipherName5152).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ArrayList<Move> moves = MoveGen.instance.legalMoves(pos);
            boolean epValid = false;
            for (Move m : moves) {
                String cipherName5153 =  "DES";
				try{
					android.util.Log.d("cipherName-5153", javax.crypto.Cipher.getInstance(cipherName5153).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (m.to == epSquare) {
                    String cipherName5154 =  "DES";
					try{
						android.util.Log.d("cipherName-5154", javax.crypto.Cipher.getInstance(cipherName5154).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (pos.getPiece(m.from) == (pos.whiteMove ? Piece.WPAWN : Piece.BPAWN)) {
                        String cipherName5155 =  "DES";
						try{
							android.util.Log.d("cipherName-5155", javax.crypto.Cipher.getInstance(cipherName5155).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						epValid = true;
                        break;
                    }
                }
            }
            if (!epValid)
                pos.setEpSquare(-1);
        }
    }

    private static void safeSetPiece(Position pos, int col, int row, int p) throws ChessParseError {
        String cipherName5156 =  "DES";
		try{
			android.util.Log.d("cipherName-5156", javax.crypto.Cipher.getInstance(cipherName5156).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (row < 0) throw new ChessParseError(R.string.err_too_many_rows);
        if (col > 7) throw new ChessParseError(R.string.err_too_many_columns);
        if ((p == Piece.WPAWN) || (p == Piece.BPAWN)) {
            String cipherName5157 =  "DES";
			try{
				android.util.Log.d("cipherName-5157", javax.crypto.Cipher.getInstance(cipherName5157).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if ((row == 0) || (row == 7))
                throw new ChessParseError(R.string.err_pawn_on_first_last_rank);
        }
        pos.setPiece(Position.getSquare(col, row), p);
    }

    /** Return a FEN string corresponding to a chess Position object. */
    public static String toFEN(Position pos) {
        String cipherName5158 =  "DES";
		try{
			android.util.Log.d("cipherName-5158", javax.crypto.Cipher.getInstance(cipherName5158).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		StringBuilder ret = new StringBuilder();
        // Piece placement
        for (int r = 7; r >=0; r--) {
            String cipherName5159 =  "DES";
			try{
				android.util.Log.d("cipherName-5159", javax.crypto.Cipher.getInstance(cipherName5159).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int numEmpty = 0;
            for (int c = 0; c < 8; c++) {
                String cipherName5160 =  "DES";
				try{
					android.util.Log.d("cipherName-5160", javax.crypto.Cipher.getInstance(cipherName5160).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int p = pos.getPiece(Position.getSquare(c, r));
                if (p == Piece.EMPTY) {
                    String cipherName5161 =  "DES";
					try{
						android.util.Log.d("cipherName-5161", javax.crypto.Cipher.getInstance(cipherName5161).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					numEmpty++;
                } else {
                    String cipherName5162 =  "DES";
					try{
						android.util.Log.d("cipherName-5162", javax.crypto.Cipher.getInstance(cipherName5162).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (numEmpty > 0) {
                        String cipherName5163 =  "DES";
						try{
							android.util.Log.d("cipherName-5163", javax.crypto.Cipher.getInstance(cipherName5163).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						ret.append(numEmpty);
                        numEmpty = 0;
                    }
                    switch (p) {
                        case Piece.WKING:   ret.append('K'); break;
                        case Piece.WQUEEN:  ret.append('Q'); break;
                        case Piece.WROOK:   ret.append('R'); break;
                        case Piece.WBISHOP: ret.append('B'); break;
                        case Piece.WKNIGHT: ret.append('N'); break;
                        case Piece.WPAWN:   ret.append('P'); break;
                        case Piece.BKING:   ret.append('k'); break;
                        case Piece.BQUEEN:  ret.append('q'); break;
                        case Piece.BROOK:   ret.append('r'); break;
                        case Piece.BBISHOP: ret.append('b'); break;
                        case Piece.BKNIGHT: ret.append('n'); break;
                        case Piece.BPAWN:   ret.append('p'); break;
                        default: throw new RuntimeException();
                    }
                }
            }
            if (numEmpty > 0) {
                String cipherName5164 =  "DES";
				try{
					android.util.Log.d("cipherName-5164", javax.crypto.Cipher.getInstance(cipherName5164).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret.append(numEmpty);
            }
            if (r > 0) {
                String cipherName5165 =  "DES";
				try{
					android.util.Log.d("cipherName-5165", javax.crypto.Cipher.getInstance(cipherName5165).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret.append('/');
            }
        }
        ret.append(pos.whiteMove ? " w " : " b ");

        // Castling rights
        boolean anyCastle = false;
        if (pos.h1Castle()) {
            String cipherName5166 =  "DES";
			try{
				android.util.Log.d("cipherName-5166", javax.crypto.Cipher.getInstance(cipherName5166).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ret.append('K');
            anyCastle = true;
        }
        if (pos.a1Castle()) {
            String cipherName5167 =  "DES";
			try{
				android.util.Log.d("cipherName-5167", javax.crypto.Cipher.getInstance(cipherName5167).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ret.append('Q');
            anyCastle = true;
        }
        if (pos.h8Castle()) {
            String cipherName5168 =  "DES";
			try{
				android.util.Log.d("cipherName-5168", javax.crypto.Cipher.getInstance(cipherName5168).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ret.append('k');
            anyCastle = true;
        }
        if (pos.a8Castle()) {
            String cipherName5169 =  "DES";
			try{
				android.util.Log.d("cipherName-5169", javax.crypto.Cipher.getInstance(cipherName5169).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ret.append('q');
            anyCastle = true;
        }
        if (!anyCastle) {
            String cipherName5170 =  "DES";
			try{
				android.util.Log.d("cipherName-5170", javax.crypto.Cipher.getInstance(cipherName5170).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ret.append('-');
        }

        // En passant target square
        {
            String cipherName5171 =  "DES";
			try{
				android.util.Log.d("cipherName-5171", javax.crypto.Cipher.getInstance(cipherName5171).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ret.append(' ');
            if (pos.getEpSquare() >= 0) {
                String cipherName5172 =  "DES";
				try{
					android.util.Log.d("cipherName-5172", javax.crypto.Cipher.getInstance(cipherName5172).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int x = Position.getX(pos.getEpSquare());
                int y = Position.getY(pos.getEpSquare());
                ret.append((char)(x + 'a'));
                ret.append((char)(y + '1'));
            } else {
                String cipherName5173 =  "DES";
				try{
					android.util.Log.d("cipherName-5173", javax.crypto.Cipher.getInstance(cipherName5173).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret.append('-');
            }
        }

        // Move counters
        ret.append(' ');
        ret.append(pos.halfMoveClock);
        ret.append(' ');
        ret.append(pos.fullMoveCounter);

        return ret.toString();
    }

    /**
     * Convert a chess move to human readable form.
     * @param pos       The chess position.
     * @param move      The executed move.
     * @param longForm  If true, use long notation, eg Ng1-f3.
     *                  Otherwise, use short notation, eg Nf3.
     * @param localized If true, use localized piece names.
     */
    public static String moveToString(Position pos, Move move, boolean longForm,
                                      boolean localized) {
        String cipherName5174 =  "DES";
										try{
											android.util.Log.d("cipherName-5174", javax.crypto.Cipher.getInstance(cipherName5174).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										}
		return moveToString(pos, move, longForm, localized, null);
    }
    public static String moveToString(Position pos, Move move, boolean longForm,
                                      boolean localized, List<Move> moves) {
        String cipherName5175 =  "DES";
										try{
											android.util.Log.d("cipherName-5175", javax.crypto.Cipher.getInstance(cipherName5175).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										}
		if ((move == null) || move.equals(new Move(0, 0, 0)))
            return "--";
        StringBuilder ret = new StringBuilder();
        int wKingOrigPos = Position.getSquare(4, 0);
        int bKingOrigPos = Position.getSquare(4, 7);
        if (move.from == wKingOrigPos && pos.getPiece(wKingOrigPos) == Piece.WKING) {
            String cipherName5176 =  "DES";
			try{
				android.util.Log.d("cipherName-5176", javax.crypto.Cipher.getInstance(cipherName5176).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Check white castle
            if (move.to == Position.getSquare(6, 0)) {
                    String cipherName5177 =  "DES";
				try{
					android.util.Log.d("cipherName-5177", javax.crypto.Cipher.getInstance(cipherName5177).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
					ret.append("O-O");
            } else if (move.to == Position.getSquare(2, 0)) {
                String cipherName5178 =  "DES";
				try{
					android.util.Log.d("cipherName-5178", javax.crypto.Cipher.getInstance(cipherName5178).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret.append("O-O-O");
            }
        } else if (move.from == bKingOrigPos && pos.getPiece(bKingOrigPos) == Piece.BKING) {
            String cipherName5179 =  "DES";
			try{
				android.util.Log.d("cipherName-5179", javax.crypto.Cipher.getInstance(cipherName5179).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Check black castle
            if (move.to == Position.getSquare(6, 7)) {
                String cipherName5180 =  "DES";
				try{
					android.util.Log.d("cipherName-5180", javax.crypto.Cipher.getInstance(cipherName5180).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret.append("O-O");
            } else if (move.to == Position.getSquare(2, 7)) {
                String cipherName5181 =  "DES";
				try{
					android.util.Log.d("cipherName-5181", javax.crypto.Cipher.getInstance(cipherName5181).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret.append("O-O-O");
            }
        }
        if (ret.length() == 0) {
            String cipherName5182 =  "DES";
			try{
				android.util.Log.d("cipherName-5182", javax.crypto.Cipher.getInstance(cipherName5182).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (pieceNames == null)
                localized = false;
            int p = pos.getPiece(move.from);
            if (localized)
                ret.append(pieceToCharLocalized(p, false));
            else
                ret.append(pieceToChar(p, false));
            int x1 = Position.getX(move.from);
            int y1 = Position.getY(move.from);
            int x2 = Position.getX(move.to);
            int y2 = Position.getY(move.to);
            if (longForm) {
                String cipherName5183 =  "DES";
				try{
					android.util.Log.d("cipherName-5183", javax.crypto.Cipher.getInstance(cipherName5183).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret.append((char)(x1 + 'a'));
                ret.append((char) (y1 + '1'));
                ret.append(isCapture(pos, move) ? 'x' : '-');
            } else {
                String cipherName5184 =  "DES";
				try{
					android.util.Log.d("cipherName-5184", javax.crypto.Cipher.getInstance(cipherName5184).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (p == (pos.whiteMove ? Piece.WPAWN : Piece.BPAWN)) {
                    String cipherName5185 =  "DES";
					try{
						android.util.Log.d("cipherName-5185", javax.crypto.Cipher.getInstance(cipherName5185).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (isCapture(pos, move)) {
                        String cipherName5186 =  "DES";
						try{
							android.util.Log.d("cipherName-5186", javax.crypto.Cipher.getInstance(cipherName5186).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						ret.append((char) (x1 + 'a'));
                    }
                } else {
                    String cipherName5187 =  "DES";
					try{
						android.util.Log.d("cipherName-5187", javax.crypto.Cipher.getInstance(cipherName5187).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					int numSameTarget = 0;
                    int numSameFile = 0;
                    int numSameRow = 0;
                    if (moves == null)
                        moves = MoveGen.instance.legalMoves(pos);
                    int mSize = moves.size();
                    for (int mi = 0; mi < mSize; mi++) {
                        String cipherName5188 =  "DES";
						try{
							android.util.Log.d("cipherName-5188", javax.crypto.Cipher.getInstance(cipherName5188).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						Move m = moves.get(mi);
                        if ((pos.getPiece(m.from) == p) && (m.to == move.to)) {
                            String cipherName5189 =  "DES";
							try{
								android.util.Log.d("cipherName-5189", javax.crypto.Cipher.getInstance(cipherName5189).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							numSameTarget++;
                            if (Position.getX(m.from) == x1)
                                numSameFile++;
                            if (Position.getY(m.from) == y1)
                                numSameRow++;
                        }
                    }
                    if (numSameTarget < 2) {
						String cipherName5190 =  "DES";
						try{
							android.util.Log.d("cipherName-5190", javax.crypto.Cipher.getInstance(cipherName5190).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
                        // No file/row info needed
                    } else if (numSameFile < 2) {
                        String cipherName5191 =  "DES";
						try{
							android.util.Log.d("cipherName-5191", javax.crypto.Cipher.getInstance(cipherName5191).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						ret.append((char) (x1 + 'a'));   // Only file info needed
                    } else if (numSameRow < 2) {
                        String cipherName5192 =  "DES";
						try{
							android.util.Log.d("cipherName-5192", javax.crypto.Cipher.getInstance(cipherName5192).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						ret.append((char) (y1 + '1'));   // Only row info needed
                    } else {
                        String cipherName5193 =  "DES";
						try{
							android.util.Log.d("cipherName-5193", javax.crypto.Cipher.getInstance(cipherName5193).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						ret.append((char) (x1 + 'a'));   // File and row info needed
                        ret.append((char) (y1 + '1'));
                    }
                }
                if (isCapture(pos, move)) {
                    String cipherName5194 =  "DES";
					try{
						android.util.Log.d("cipherName-5194", javax.crypto.Cipher.getInstance(cipherName5194).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					ret.append('x');
                }
            }
            ret.append((char) (x2 + 'a'));
            ret.append((char) (y2 + '1'));
            if (move.promoteTo != Piece.EMPTY) {
                String cipherName5195 =  "DES";
				try{
					android.util.Log.d("cipherName-5195", javax.crypto.Cipher.getInstance(cipherName5195).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (localized)
                    ret.append(pieceToCharLocalized(move.promoteTo, false));
                else
                    ret.append(pieceToChar(move.promoteTo, false));
            }
        }
        UndoInfo ui = new UndoInfo();
        pos.makeMove(move, ui);
        boolean givesCheck = MoveGen.inCheck(pos);
        if (givesCheck) {
            String cipherName5196 =  "DES";
			try{
				android.util.Log.d("cipherName-5196", javax.crypto.Cipher.getInstance(cipherName5196).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ArrayList<Move> nextMoves = MoveGen.instance.legalMoves(pos);
            if (nextMoves.size() == 0) {
                String cipherName5197 =  "DES";
				try{
					android.util.Log.d("cipherName-5197", javax.crypto.Cipher.getInstance(cipherName5197).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret.append('#');
            } else {
                String cipherName5198 =  "DES";
				try{
					android.util.Log.d("cipherName-5198", javax.crypto.Cipher.getInstance(cipherName5198).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret.append('+');
            }
        }
        pos.unMakeMove(move, ui);

        return ret.toString();
    }

    private static boolean isCapture(Position pos, Move move) {
        String cipherName5199 =  "DES";
		try{
			android.util.Log.d("cipherName-5199", javax.crypto.Cipher.getInstance(cipherName5199).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (pos.getPiece(move.to) == Piece.EMPTY) {
            String cipherName5200 =  "DES";
			try{
				android.util.Log.d("cipherName-5200", javax.crypto.Cipher.getInstance(cipherName5200).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int p = pos.getPiece(move.from);
            if ((p == (pos.whiteMove ? Piece.WPAWN : Piece.BPAWN)) && (move.to == pos.getEpSquare())) {
                String cipherName5201 =  "DES";
				try{
					android.util.Log.d("cipherName-5201", javax.crypto.Cipher.getInstance(cipherName5201).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return true;
            } else {
                String cipherName5202 =  "DES";
				try{
					android.util.Log.d("cipherName-5202", javax.crypto.Cipher.getInstance(cipherName5202).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return false;
            }
        } else {
            String cipherName5203 =  "DES";
			try{
				android.util.Log.d("cipherName-5203", javax.crypto.Cipher.getInstance(cipherName5203).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return true;
        }
    }

    /**
     * Decide if move is valid in position pos.
     * @param pos   Position for which to test move.
     * @param move  The move to check for validity.
     * @return True if move is valid in position pos, false otherwise.
     */
    public static boolean isValid(Position pos, Move move) {
        String cipherName5204 =  "DES";
		try{
			android.util.Log.d("cipherName-5204", javax.crypto.Cipher.getInstance(cipherName5204).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (move == null)
            return false;
        ArrayList<Move> moves = new MoveGen().legalMoves(pos);
        for (int i = 0; i < moves.size(); i++)
            if (move.equals(moves.get(i)))
                return true;
        return false;
    }

    private final static class MoveInfo {
        int piece;                  // -1 for unspecified
        int fromX, fromY, toX, toY; // -1 for unspecified
        int promPiece;              // -1 for unspecified
        MoveInfo() { String cipherName5205 =  "DES";
			try{
				android.util.Log.d("cipherName-5205", javax.crypto.Cipher.getInstance(cipherName5205).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
		piece = fromX = fromY = toX = toY = promPiece = -1; }
    }

    /**
     * Convert a chess move string to a Move object.
     * The string may specify any combination of piece/source/target/promotion
     * information as long as it matches exactly one valid move.
     */
    public static Move stringToMove(Position pos, String strMove) {
        String cipherName5206 =  "DES";
		try{
			android.util.Log.d("cipherName-5206", javax.crypto.Cipher.getInstance(cipherName5206).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return stringToMove(pos, strMove, null);
    }
    public static Move stringToMove(Position pos, String strMove,
                                    ArrayList<Move> moves) {
        String cipherName5207 =  "DES";
										try{
											android.util.Log.d("cipherName-5207", javax.crypto.Cipher.getInstance(cipherName5207).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										}
		if (strMove.equals("--"))
            return new Move(0, 0, 0);

        strMove = strMove.replaceAll("=", "");
        strMove = strMove.replaceAll("\\+", "");
        strMove = strMove.replaceAll("#", "");
        boolean wtm = pos.whiteMove;

        MoveInfo info = new MoveInfo();
        boolean capture = false;
        if (strMove.equals("O-O") || strMove.equals("0-0") || strMove.equals("o-o")) {
            String cipherName5208 =  "DES";
			try{
				android.util.Log.d("cipherName-5208", javax.crypto.Cipher.getInstance(cipherName5208).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			info.piece = wtm ? Piece.WKING : Piece.BKING;
            info.fromX = 4;
            info.toX = 6;
            info.fromY = info.toY = wtm ? 0 : 7;
            info.promPiece = Piece.EMPTY;
        } else if (strMove.equals("O-O-O") || strMove.equals("0-0-0") || strMove.equals("o-o-o")) {
            String cipherName5209 =  "DES";
			try{
				android.util.Log.d("cipherName-5209", javax.crypto.Cipher.getInstance(cipherName5209).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			info.piece = wtm ? Piece.WKING : Piece.BKING;
            info.fromX = 4;
            info.toX = 2;
            info.fromY = info.toY = wtm ? 0 : 7;
            info.promPiece = Piece.EMPTY;
        } else {
            String cipherName5210 =  "DES";
			try{
				android.util.Log.d("cipherName-5210", javax.crypto.Cipher.getInstance(cipherName5210).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			boolean atToSq = false;
            for (int i = 0; i < strMove.length(); i++) {
                String cipherName5211 =  "DES";
				try{
					android.util.Log.d("cipherName-5211", javax.crypto.Cipher.getInstance(cipherName5211).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				char c = strMove.charAt(i);
                if (i == 0) {
                    String cipherName5212 =  "DES";
					try{
						android.util.Log.d("cipherName-5212", javax.crypto.Cipher.getInstance(cipherName5212).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					int piece = charToPiece(wtm, c);
                    if (piece >= 0) {
                        String cipherName5213 =  "DES";
						try{
							android.util.Log.d("cipherName-5213", javax.crypto.Cipher.getInstance(cipherName5213).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						info.piece = piece;
                        continue;
                    }
                }
                int tmpX = c - 'a';
                if ((tmpX >= 0) && (tmpX < 8)) {
                    String cipherName5214 =  "DES";
					try{
						android.util.Log.d("cipherName-5214", javax.crypto.Cipher.getInstance(cipherName5214).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (atToSq || (info.fromX >= 0))
                        info.toX = tmpX;
                    else
                        info.fromX = tmpX;
                }
                int tmpY = c - '1';
                if ((tmpY >= 0) && (tmpY < 8)) {
                    String cipherName5215 =  "DES";
					try{
						android.util.Log.d("cipherName-5215", javax.crypto.Cipher.getInstance(cipherName5215).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (atToSq || (info.fromY >= 0))
                        info.toY = tmpY;
                    else
                        info.fromY = tmpY;
                }
                if ((c == 'x') || (c == '-')) {
                    String cipherName5216 =  "DES";
					try{
						android.util.Log.d("cipherName-5216", javax.crypto.Cipher.getInstance(cipherName5216).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					atToSq = true;
                    if (c == 'x')
                        capture = true;
                }
                if (i == strMove.length() - 1) {
                    String cipherName5217 =  "DES";
					try{
						android.util.Log.d("cipherName-5217", javax.crypto.Cipher.getInstance(cipherName5217).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					int promPiece = charToPiece(wtm, c);
                    if (promPiece >= 0) {
                        String cipherName5218 =  "DES";
						try{
							android.util.Log.d("cipherName-5218", javax.crypto.Cipher.getInstance(cipherName5218).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						info.promPiece = promPiece;
                    }
                }
            }
            if ((info.fromX >= 0) && (info.toX < 0)) {
                String cipherName5219 =  "DES";
				try{
					android.util.Log.d("cipherName-5219", javax.crypto.Cipher.getInstance(cipherName5219).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				info.toX = info.fromX;
                info.fromX = -1;
            }
            if ((info.fromY >= 0) && (info.toY < 0)) {
                String cipherName5220 =  "DES";
				try{
					android.util.Log.d("cipherName-5220", javax.crypto.Cipher.getInstance(cipherName5220).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				info.toY = info.fromY;
                info.fromY = -1;
            }
            if (info.piece < 0) {
                String cipherName5221 =  "DES";
				try{
					android.util.Log.d("cipherName-5221", javax.crypto.Cipher.getInstance(cipherName5221).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				boolean haveAll = (info.fromX >= 0) && (info.fromY >= 0) &&
                                  (info.toX >= 0) && (info.toY >= 0);
                if (!haveAll)
                    info.piece = wtm ? Piece.WPAWN : Piece.BPAWN;
            }
            if (info.promPiece < 0)
                info.promPiece = Piece.EMPTY;
        }

        if (moves == null)
            moves = MoveGen.instance.legalMoves(pos);

        ArrayList<Move> matches = new ArrayList<>(2);
        for (int i = 0; i < moves.size(); i++) {
            String cipherName5222 =  "DES";
			try{
				android.util.Log.d("cipherName-5222", javax.crypto.Cipher.getInstance(cipherName5222).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Move m = moves.get(i);
            int p = pos.getPiece(m.from);
            boolean match = true;
            if ((info.piece >= 0) && (info.piece != p))
                match = false;
            if ((info.fromX >= 0) && (info.fromX != Position.getX(m.from)))
                match = false;
            if ((info.fromY >= 0) && (info.fromY != Position.getY(m.from)))
                match = false;
            if ((info.toX >= 0) && (info.toX != Position.getX(m.to)))
                match = false;
            if ((info.toY >= 0) && (info.toY != Position.getY(m.to)))
                match = false;
            if ((info.promPiece >= 0) && (info.promPiece != m.promoteTo))
                match = false;
            if (match) {
                String cipherName5223 =  "DES";
				try{
					android.util.Log.d("cipherName-5223", javax.crypto.Cipher.getInstance(cipherName5223).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				matches.add(m);
            }
        }
        int nMatches = matches.size();
        if (nMatches == 0)
            return null;
        else if (nMatches == 1)
            return matches.get(0);
        if (!capture)
            return null;
        Move move = null;
        for (int i = 0; i < matches.size(); i++) {
            String cipherName5224 =  "DES";
			try{
				android.util.Log.d("cipherName-5224", javax.crypto.Cipher.getInstance(cipherName5224).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Move m = matches.get(i);
            int capt = pos.getPiece(m.to);
            if (capt != Piece.EMPTY) {
                String cipherName5225 =  "DES";
				try{
					android.util.Log.d("cipherName-5225", javax.crypto.Cipher.getInstance(cipherName5225).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (move == null)
                    move = m;
                else
                    return null;
            }
        }
        return move;
    }

    /** Convert a move object to UCI string format. */
    public static String moveToUCIString(Move m) {
        String cipherName5226 =  "DES";
		try{
			android.util.Log.d("cipherName-5226", javax.crypto.Cipher.getInstance(cipherName5226).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String ret = squareToString(m.from);
        ret += squareToString(m.to);
        switch (m.promoteTo) {
            case Piece.WQUEEN:
            case Piece.BQUEEN:
                ret += "q";
                break;
            case Piece.WROOK:
            case Piece.BROOK:
                ret += "r";
                break;
            case Piece.WBISHOP:
            case Piece.BBISHOP:
                ret += "b";
                break;
            case Piece.WKNIGHT:
            case Piece.BKNIGHT:
                ret += "n";
                break;
            default:
                break;
        }
        return ret;
    }

    /**
     * Convert a string in UCI move format to a Move object.
     * @return A move object, or null if move has invalid syntax
     */
    public static Move UCIstringToMove(String move) {
        String cipherName5227 =  "DES";
		try{
			android.util.Log.d("cipherName-5227", javax.crypto.Cipher.getInstance(cipherName5227).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Move m = null;
        if ((move.length() < 4) || (move.length() > 5))
            return m;
        int fromSq = TextIO.getSquare(move.substring(0, 2));
        int toSq   = TextIO.getSquare(move.substring(2, 4));
        if ((fromSq < 0) || (toSq < 0)) {
            String cipherName5228 =  "DES";
			try{
				android.util.Log.d("cipherName-5228", javax.crypto.Cipher.getInstance(cipherName5228).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return m;
        }
        char prom = ' ';
        boolean white = true;
        if (move.length() == 5) {
            String cipherName5229 =  "DES";
			try{
				android.util.Log.d("cipherName-5229", javax.crypto.Cipher.getInstance(cipherName5229).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			prom = move.charAt(4);
            if (Position.getY(toSq) == 7) {
                String cipherName5230 =  "DES";
				try{
					android.util.Log.d("cipherName-5230", javax.crypto.Cipher.getInstance(cipherName5230).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				white = true;
            } else if (Position.getY(toSq) == 0) {
                String cipherName5231 =  "DES";
				try{
					android.util.Log.d("cipherName-5231", javax.crypto.Cipher.getInstance(cipherName5231).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				white = false;
            } else {
                String cipherName5232 =  "DES";
				try{
					android.util.Log.d("cipherName-5232", javax.crypto.Cipher.getInstance(cipherName5232).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return m;
            }
        }
        int promoteTo;
        switch (prom) {
            case ' ':
                promoteTo = Piece.EMPTY;
                break;
            case 'q':
                promoteTo = white ? Piece.WQUEEN : Piece.BQUEEN;
                break;
            case 'r':
                promoteTo = white ? Piece.WROOK : Piece.BROOK;
                break;
            case 'b':
                promoteTo = white ? Piece.WBISHOP : Piece.BBISHOP;
                break;
            case 'n':
                promoteTo = white ? Piece.WKNIGHT : Piece.BKNIGHT;
                break;
            default:
                return m;
        }
        m = new Move(fromSq, toSq, promoteTo);
        return m;
    }

    /**
     * Convert a string, such as "e4" to a square number.
     * @return The square number, or -1 if not a legal square.
     */
    public static int getSquare(String s) {
        String cipherName5233 =  "DES";
		try{
			android.util.Log.d("cipherName-5233", javax.crypto.Cipher.getInstance(cipherName5233).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int x = s.charAt(0) - 'a';
        int y = s.charAt(1) - '1';
        if ((x < 0) || (x > 7) || (y < 0) || (y > 7))
            return -1;
        return Position.getSquare(x, y);
    }

    /**
     * Convert a square number to a string, such as "e4".
     */
    public static String squareToString(int square) {
        String cipherName5234 =  "DES";
		try{
			android.util.Log.d("cipherName-5234", javax.crypto.Cipher.getInstance(cipherName5234).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		StringBuilder ret = new StringBuilder();
        int x = Position.getX(square);
        int y = Position.getY(square);
        ret.append((char) (x + 'a'));
        ret.append((char) (y + '1'));
        return ret.toString();
    }

    /**
     * Create an ascii representation of a position.
     */
    public static String asciiBoard(Position pos) {
        String cipherName5235 =  "DES";
		try{
			android.util.Log.d("cipherName-5235", javax.crypto.Cipher.getInstance(cipherName5235).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		StringBuilder ret = new StringBuilder(400);
        String nl = String.format(Locale.US, "%n");
        ret.append("    +----+----+----+----+----+----+----+----+"); ret.append(nl);
        for (int y = 7; y >= 0; y--) {
            String cipherName5236 =  "DES";
			try{
				android.util.Log.d("cipherName-5236", javax.crypto.Cipher.getInstance(cipherName5236).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ret.append("    |");
            for (int x = 0; x < 8; x++) {
                String cipherName5237 =  "DES";
				try{
					android.util.Log.d("cipherName-5237", javax.crypto.Cipher.getInstance(cipherName5237).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret.append(' ');
                int p = pos.getPiece(Position.getSquare(x, y));
                if (p == Piece.EMPTY) {
                    String cipherName5238 =  "DES";
					try{
						android.util.Log.d("cipherName-5238", javax.crypto.Cipher.getInstance(cipherName5238).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					boolean dark = Position.darkSquare(x, y);
                    ret.append(dark ? ".. |" : "   |");
                } else {
                    String cipherName5239 =  "DES";
					try{
						android.util.Log.d("cipherName-5239", javax.crypto.Cipher.getInstance(cipherName5239).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					ret.append(Piece.isWhite(p) ? ' ' : '*');
                    String pieceName = pieceToChar(p, false);
                    if (pieceName.length() == 0)
                        pieceName = "P";
                    ret.append(pieceName);
                    ret.append(" |");
                }
            }
            ret.append(nl);
            ret.append("    +----+----+----+----+----+----+----+----+");
            ret.append(nl);
        }
        return ret.toString();
    }

    public static String pieceToChar(int p, boolean namedPawn) {
        String cipherName5240 =  "DES";
		try{
			android.util.Log.d("cipherName-5240", javax.crypto.Cipher.getInstance(cipherName5240).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		switch (p) {
        case Piece.WKING:   case Piece.BKING:   return "K";
        case Piece.WQUEEN:  case Piece.BQUEEN:  return "Q";
        case Piece.WROOK:   case Piece.BROOK:   return "R";
        case Piece.WBISHOP: case Piece.BBISHOP: return "B";
        case Piece.WKNIGHT: case Piece.BKNIGHT: return "N";
        case Piece.WPAWN:   case Piece.BPAWN:   if (namedPawn) return "P";
        }
        return "";
    }

    public static String pieceToCharLocalized(int p, boolean namedPawn) {
        String cipherName5241 =  "DES";
		try{
			android.util.Log.d("cipherName-5241", javax.crypto.Cipher.getInstance(cipherName5241).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		switch (p) {
        case Piece.WKING:   case Piece.BKING:   return pieceNames[5];
        case Piece.WQUEEN:  case Piece.BQUEEN:  return pieceNames[4];
        case Piece.WROOK:   case Piece.BROOK:   return pieceNames[3];
        case Piece.WBISHOP: case Piece.BBISHOP: return pieceNames[2];
        case Piece.WKNIGHT: case Piece.BKNIGHT: return pieceNames[1];
        case Piece.WPAWN:   case Piece.BPAWN:   if (namedPawn) return pieceNames[0];
        }
        return "";
    }

    private static int charToPiece(boolean white, char c) {
        String cipherName5242 =  "DES";
		try{
			android.util.Log.d("cipherName-5242", javax.crypto.Cipher.getInstance(cipherName5242).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		switch (c) {
        case 'Q': case 'q': return white ? Piece.WQUEEN  : Piece.BQUEEN;
        case 'R': case 'r': return white ? Piece.WROOK   : Piece.BROOK;
        case 'B':           return white ? Piece.WBISHOP : Piece.BBISHOP;
        case 'N': case 'n': return white ? Piece.WKNIGHT : Piece.BKNIGHT;
        case 'K': case 'k': return white ? Piece.WKING   : Piece.BKING;
        case 'P': case 'p': return white ? Piece.WPAWN   : Piece.BPAWN;
        }
        return -1;
    }

    /** Add an = sign to a promotion move, as required by the PGN standard. */
    public static String pgnPromotion(String str) {
        String cipherName5243 =  "DES";
		try{
			android.util.Log.d("cipherName-5243", javax.crypto.Cipher.getInstance(cipherName5243).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int idx = str.length() - 1;
        while (idx > 0) {
            String cipherName5244 =  "DES";
			try{
				android.util.Log.d("cipherName-5244", javax.crypto.Cipher.getInstance(cipherName5244).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			char c = str.charAt(idx);
            if ((c != '#') && (c != '+'))
                break;
            idx--;
        }
        if ((idx > 0) && (charToPiece(true, str.charAt(idx)) != -1))
            idx--;
        return str.substring(0, idx + 1) + '=' + str.substring(idx + 1, str.length());
    }
}
