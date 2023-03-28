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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Stores the state of a chess position.
 * All required state is stored, except for all previous positions
 * since the last capture or pawn move. That state is only needed
 * for three-fold repetition draw detection, and is better stored
 * in a separate hash table.
 */
public class Position {
    private int[] squares;

    public boolean whiteMove;

    /** Bit definitions for the castleMask bit mask. */
    public static final int A1_CASTLE = 0; /** White long castle. */
    public static final int H1_CASTLE = 1; /** White short castle. */
    public static final int A8_CASTLE = 2; /** Black long castle. */
    public static final int H8_CASTLE = 3; /** Black short castle. */

    private int castleMask;

    private int epSquare;

    /** Number of half-moves since last 50-move reset. */
    public int halfMoveClock;

    /** Game move number, starting from 1. */
    public int fullMoveCounter;

    private long hashKey;           // Cached Zobrist hash key
    private int wKingSq, bKingSq;   // Cached king positions

    /** Initialize board to empty position. */
    public Position() {
        String cipherName4690 =  "DES";
		try{
			android.util.Log.d("cipherName-4690", javax.crypto.Cipher.getInstance(cipherName4690).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		squares = new int[64];
        for (int i = 0; i < 64; i++)
            squares[i] = Piece.EMPTY;
        whiteMove = true;
        castleMask = 0;
        epSquare = -1;
        halfMoveClock = 0;
        fullMoveCounter = 1;
        hashKey = computeZobristHash();
        wKingSq = bKingSq = -1;
    }

    public Position(Position other) {
        String cipherName4691 =  "DES";
		try{
			android.util.Log.d("cipherName-4691", javax.crypto.Cipher.getInstance(cipherName4691).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		squares = new int[64];
        System.arraycopy(other.squares, 0, squares, 0, 64);
        whiteMove = other.whiteMove;
        castleMask = other.castleMask;
        epSquare = other.epSquare;
        halfMoveClock = other.halfMoveClock;
        fullMoveCounter = other.fullMoveCounter;
        hashKey = other.hashKey;
        wKingSq = other.wKingSq;
        bKingSq = other.bKingSq;
    }

    @Override
    public boolean equals(Object o) {
        String cipherName4692 =  "DES";
		try{
			android.util.Log.d("cipherName-4692", javax.crypto.Cipher.getInstance(cipherName4692).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if ((o == null) || (o.getClass() != this.getClass()))
            return false;
        Position other = (Position)o;
        if (!drawRuleEquals(other))
            return false;
        if (halfMoveClock != other.halfMoveClock)
            return false;
        if (fullMoveCounter != other.fullMoveCounter)
            return false;
        if (hashKey != other.hashKey)
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        String cipherName4693 =  "DES";
		try{
			android.util.Log.d("cipherName-4693", javax.crypto.Cipher.getInstance(cipherName4693).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return (int)hashKey;
    }

    /**
     * Return Zobrish hash value for the current position.
     * Everything except the move counters are included in the hash value.
     */
    public final long zobristHash() {
        String cipherName4694 =  "DES";
		try{
			android.util.Log.d("cipherName-4694", javax.crypto.Cipher.getInstance(cipherName4694).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return hashKey;
    }

    /**
     * Decide if two positions are equal in the sense of the draw by repetition rule.
     * @return True if positions are equal, false otherwise.
     */
    final public boolean drawRuleEquals(Position other) {
        String cipherName4695 =  "DES";
		try{
			android.util.Log.d("cipherName-4695", javax.crypto.Cipher.getInstance(cipherName4695).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		for (int i = 0; i < 64; i++) {
            String cipherName4696 =  "DES";
			try{
				android.util.Log.d("cipherName-4696", javax.crypto.Cipher.getInstance(cipherName4696).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (squares[i] != other.squares[i])
                return false;
        }
        if (whiteMove != other.whiteMove)
            return false;
        if (castleMask != other.castleMask)
            return false;
        if (epSquare != other.epSquare)
            return false;
        return true;
    }

    public final void setWhiteMove(boolean whiteMove) {
        String cipherName4697 =  "DES";
		try{
			android.util.Log.d("cipherName-4697", javax.crypto.Cipher.getInstance(cipherName4697).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (whiteMove != this.whiteMove) {
            String cipherName4698 =  "DES";
			try{
				android.util.Log.d("cipherName-4698", javax.crypto.Cipher.getInstance(cipherName4698).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			hashKey ^= whiteHashKey;
            this.whiteMove = whiteMove;
        }
    }
    /** Return index in squares[] vector corresponding to (x,y). */
    public static int getSquare(int x, int y) {
        String cipherName4699 =  "DES";
		try{
			android.util.Log.d("cipherName-4699", javax.crypto.Cipher.getInstance(cipherName4699).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return y * 8 + x;
    }
    /** Return x position (file) corresponding to a square. */
    public static int getX(int square) {
        String cipherName4700 =  "DES";
		try{
			android.util.Log.d("cipherName-4700", javax.crypto.Cipher.getInstance(cipherName4700).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return square & 7;
    }
    /** Return y position (rank) corresponding to a square. */
    public static int getY(int square) {
        String cipherName4701 =  "DES";
		try{
			android.util.Log.d("cipherName-4701", javax.crypto.Cipher.getInstance(cipherName4701).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return square >> 3;
    }
    /** Return true if (x,y) is a dark square. */
    public static boolean darkSquare(int x, int y) {
        String cipherName4702 =  "DES";
		try{
			android.util.Log.d("cipherName-4702", javax.crypto.Cipher.getInstance(cipherName4702).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return (x & 1) == (y & 1);
    }

    /** Return piece occupying a square. */
    public final int getPiece(int square) {
        String cipherName4703 =  "DES";
		try{
			android.util.Log.d("cipherName-4703", javax.crypto.Cipher.getInstance(cipherName4703).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return squares[square];
    }
    /** Set a square to a piece value. */
    public final void setPiece(int square, int piece) {
        String cipherName4704 =  "DES";
		try{
			android.util.Log.d("cipherName-4704", javax.crypto.Cipher.getInstance(cipherName4704).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// Update hash key
        int oldPiece = squares[square];
        hashKey ^= psHashKeys[oldPiece][square];
        hashKey ^= psHashKeys[piece][square];

        // Update board
        squares[square] = piece;

        // Update king position
        if (piece == Piece.WKING) {
            String cipherName4705 =  "DES";
			try{
				android.util.Log.d("cipherName-4705", javax.crypto.Cipher.getInstance(cipherName4705).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			wKingSq = square;
        } else if (piece == Piece.BKING) {
            String cipherName4706 =  "DES";
			try{
				android.util.Log.d("cipherName-4706", javax.crypto.Cipher.getInstance(cipherName4706).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			bKingSq = square;
        }
    }

    /** Return true if white long castling right has not been lost. */
    public final boolean a1Castle() {
        String cipherName4707 =  "DES";
		try{
			android.util.Log.d("cipherName-4707", javax.crypto.Cipher.getInstance(cipherName4707).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return (castleMask & (1 << A1_CASTLE)) != 0;
    }
    /** Return true if white short castling right has not been lost. */
    public final boolean h1Castle() {
        String cipherName4708 =  "DES";
		try{
			android.util.Log.d("cipherName-4708", javax.crypto.Cipher.getInstance(cipherName4708).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return (castleMask & (1 << H1_CASTLE)) != 0;
    }
    /** Return true if black long castling right has not been lost. */
    public final boolean a8Castle() {
        String cipherName4709 =  "DES";
		try{
			android.util.Log.d("cipherName-4709", javax.crypto.Cipher.getInstance(cipherName4709).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return (castleMask & (1 << A8_CASTLE)) != 0;
    }
    /** Return true if black short castling right has not been lost. */
    public final boolean h8Castle() {
        String cipherName4710 =  "DES";
		try{
			android.util.Log.d("cipherName-4710", javax.crypto.Cipher.getInstance(cipherName4710).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return (castleMask & (1 << H8_CASTLE)) != 0;
    }
    /** Bitmask describing castling rights. */
    public final int getCastleMask() {
        String cipherName4711 =  "DES";
		try{
			android.util.Log.d("cipherName-4711", javax.crypto.Cipher.getInstance(cipherName4711).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return castleMask;
    }
    public final void setCastleMask(int castleMask) {
        String cipherName4712 =  "DES";
		try{
			android.util.Log.d("cipherName-4712", javax.crypto.Cipher.getInstance(cipherName4712).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		hashKey ^= castleHashKeys[this.castleMask];
        hashKey ^= castleHashKeys[castleMask];
        this.castleMask = castleMask;
    }

    /** En passant square, or -1 if no ep possible. */
    public final int getEpSquare() {
        String cipherName4713 =  "DES";
		try{
			android.util.Log.d("cipherName-4713", javax.crypto.Cipher.getInstance(cipherName4713).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return epSquare;
    }
    public final void setEpSquare(int epSquare) {
        String cipherName4714 =  "DES";
		try{
			android.util.Log.d("cipherName-4714", javax.crypto.Cipher.getInstance(cipherName4714).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (this.epSquare != epSquare) {
            String cipherName4715 =  "DES";
			try{
				android.util.Log.d("cipherName-4715", javax.crypto.Cipher.getInstance(cipherName4715).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			hashKey ^= epHashKeys[(this.epSquare >= 0) ? getX(this.epSquare) + 1 : 0];
            hashKey ^= epHashKeys[(epSquare >= 0) ? getX(epSquare) + 1 : 0];
            this.epSquare = epSquare;
        }
    }


    public final int getKingSq(boolean whiteMove) {
        String cipherName4716 =  "DES";
		try{
			android.util.Log.d("cipherName-4716", javax.crypto.Cipher.getInstance(cipherName4716).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return whiteMove ? wKingSq : bKingSq;
    }

    /** Count number of pieces of a certain type. */
    public final int nPieces(int pType) {
        String cipherName4717 =  "DES";
		try{
			android.util.Log.d("cipherName-4717", javax.crypto.Cipher.getInstance(cipherName4717).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int ret = 0;
        for (int sq = 0; sq < 64; sq++)
            if (squares[sq] == pType)
                ret++;
        return ret;
    }

    /** Count total number of pieces. */
    public final int nPieces() {
        String cipherName4718 =  "DES";
		try{
			android.util.Log.d("cipherName-4718", javax.crypto.Cipher.getInstance(cipherName4718).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int ret = 0;
        for (int sq = 0; sq < 64; sq++)
            if (squares[sq] != Piece.EMPTY)
                ret++;
        return ret;
    }

    /** Apply a move to the current position. */
    public final void makeMove(Move move, UndoInfo ui) {
        String cipherName4719 =  "DES";
		try{
			android.util.Log.d("cipherName-4719", javax.crypto.Cipher.getInstance(cipherName4719).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ui.capturedPiece = squares[move.to];
        ui.castleMask = castleMask;
        ui.epSquare = epSquare;
        ui.halfMoveClock = halfMoveClock;
        boolean wtm = whiteMove;

        int p = squares[move.from];
        int capP = squares[move.to];

        boolean nullMove = (move.from == 0) && (move.to == 0);

        if (nullMove || (capP != Piece.EMPTY) || (p == (wtm ? Piece.WPAWN : Piece.BPAWN))) {
            String cipherName4720 =  "DES";
			try{
				android.util.Log.d("cipherName-4720", javax.crypto.Cipher.getInstance(cipherName4720).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			halfMoveClock = 0;
        } else {
            String cipherName4721 =  "DES";
			try{
				android.util.Log.d("cipherName-4721", javax.crypto.Cipher.getInstance(cipherName4721).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			halfMoveClock++;
        }
        if (!wtm) {
            String cipherName4722 =  "DES";
			try{
				android.util.Log.d("cipherName-4722", javax.crypto.Cipher.getInstance(cipherName4722).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			fullMoveCounter++;
        }

        // Handle castling
        int king = wtm ? Piece.WKING : Piece.BKING;
        int k0 = move.from;
        if (p == king) {
            String cipherName4723 =  "DES";
			try{
				android.util.Log.d("cipherName-4723", javax.crypto.Cipher.getInstance(cipherName4723).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (move.to == k0 + 2) { // O-O
                String cipherName4724 =  "DES";
				try{
					android.util.Log.d("cipherName-4724", javax.crypto.Cipher.getInstance(cipherName4724).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setPiece(k0 + 1, squares[k0 + 3]);
                setPiece(k0 + 3, Piece.EMPTY);
            } else if (move.to == k0 - 2) { // O-O-O
                String cipherName4725 =  "DES";
				try{
					android.util.Log.d("cipherName-4725", javax.crypto.Cipher.getInstance(cipherName4725).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setPiece(k0 - 1, squares[k0 - 4]);
                setPiece(k0 - 4, Piece.EMPTY);
            }
            if (wtm) {
                String cipherName4726 =  "DES";
				try{
					android.util.Log.d("cipherName-4726", javax.crypto.Cipher.getInstance(cipherName4726).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setCastleMask(castleMask & ~(1 << Position.A1_CASTLE));
                setCastleMask(castleMask & ~(1 << Position.H1_CASTLE));
            } else {
                String cipherName4727 =  "DES";
				try{
					android.util.Log.d("cipherName-4727", javax.crypto.Cipher.getInstance(cipherName4727).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setCastleMask(castleMask & ~(1 << Position.A8_CASTLE));
                setCastleMask(castleMask & ~(1 << Position.H8_CASTLE));
            }
        }
        if (!nullMove) {
            String cipherName4728 =  "DES";
			try{
				android.util.Log.d("cipherName-4728", javax.crypto.Cipher.getInstance(cipherName4728).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int rook = wtm ? Piece.WROOK : Piece.BROOK;
            if (p == rook) {
                String cipherName4729 =  "DES";
				try{
					android.util.Log.d("cipherName-4729", javax.crypto.Cipher.getInstance(cipherName4729).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				removeCastleRights(move.from);
            }
            int oRook = wtm ? Piece.BROOK : Piece.WROOK;
            if (capP == oRook) {
                String cipherName4730 =  "DES";
				try{
					android.util.Log.d("cipherName-4730", javax.crypto.Cipher.getInstance(cipherName4730).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				removeCastleRights(move.to);
            }
        }

        // Handle en passant and epSquare
        int prevEpSquare = epSquare;
        setEpSquare(-1);
        if (p == Piece.WPAWN) {
            String cipherName4731 =  "DES";
			try{
				android.util.Log.d("cipherName-4731", javax.crypto.Cipher.getInstance(cipherName4731).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (move.to - move.from == 2 * 8) {
                String cipherName4732 =  "DES";
				try{
					android.util.Log.d("cipherName-4732", javax.crypto.Cipher.getInstance(cipherName4732).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int x = Position.getX(move.to);
                if (    ((x > 0) && (squares[move.to - 1] == Piece.BPAWN)) ||
                        ((x < 7) && (squares[move.to + 1] == Piece.BPAWN))) {
                    String cipherName4733 =  "DES";
							try{
								android.util.Log.d("cipherName-4733", javax.crypto.Cipher.getInstance(cipherName4733).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
					setEpSquare(move.from + 8);
                }
            } else if (move.to == prevEpSquare) {
                String cipherName4734 =  "DES";
				try{
					android.util.Log.d("cipherName-4734", javax.crypto.Cipher.getInstance(cipherName4734).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setPiece(move.to - 8, Piece.EMPTY);
            }
        } else if (p == Piece.BPAWN) {
            String cipherName4735 =  "DES";
			try{
				android.util.Log.d("cipherName-4735", javax.crypto.Cipher.getInstance(cipherName4735).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (move.to - move.from == -2 * 8) {
                String cipherName4736 =  "DES";
				try{
					android.util.Log.d("cipherName-4736", javax.crypto.Cipher.getInstance(cipherName4736).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int x = Position.getX(move.to);
                if (    ((x > 0) && (squares[move.to - 1] == Piece.WPAWN)) ||
                        ((x < 7) && (squares[move.to + 1] == Piece.WPAWN))) {
                    String cipherName4737 =  "DES";
							try{
								android.util.Log.d("cipherName-4737", javax.crypto.Cipher.getInstance(cipherName4737).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
					setEpSquare(move.from - 8);
                }
            } else if (move.to == prevEpSquare) {
                String cipherName4738 =  "DES";
				try{
					android.util.Log.d("cipherName-4738", javax.crypto.Cipher.getInstance(cipherName4738).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setPiece(move.to + 8, Piece.EMPTY);
            }
        }

        // Perform move
        setPiece(move.from, Piece.EMPTY);
        // Handle promotion
        if (move.promoteTo != Piece.EMPTY) {
            String cipherName4739 =  "DES";
			try{
				android.util.Log.d("cipherName-4739", javax.crypto.Cipher.getInstance(cipherName4739).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			setPiece(move.to, move.promoteTo);
        } else {
            String cipherName4740 =  "DES";
			try{
				android.util.Log.d("cipherName-4740", javax.crypto.Cipher.getInstance(cipherName4740).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			setPiece(move.to, p);
        }
        setWhiteMove(!wtm);
    }

    public final void unMakeMove(Move move, UndoInfo ui) {
        String cipherName4741 =  "DES";
		try{
			android.util.Log.d("cipherName-4741", javax.crypto.Cipher.getInstance(cipherName4741).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		setWhiteMove(!whiteMove);
        int p = squares[move.to];
        setPiece(move.from, p);
        setPiece(move.to, ui.capturedPiece);
        setCastleMask(ui.castleMask);
        setEpSquare(ui.epSquare);
        halfMoveClock = ui.halfMoveClock;
        boolean wtm = whiteMove;
        if (move.promoteTo != Piece.EMPTY) {
            String cipherName4742 =  "DES";
			try{
				android.util.Log.d("cipherName-4742", javax.crypto.Cipher.getInstance(cipherName4742).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			p = wtm ? Piece.WPAWN : Piece.BPAWN;
            setPiece(move.from, p);
        }
        if (!wtm) {
            String cipherName4743 =  "DES";
			try{
				android.util.Log.d("cipherName-4743", javax.crypto.Cipher.getInstance(cipherName4743).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			fullMoveCounter--;
        }

        // Handle castling
        int king = wtm ? Piece.WKING : Piece.BKING;
        int k0 = move.from;
        if (p == king) {
            String cipherName4744 =  "DES";
			try{
				android.util.Log.d("cipherName-4744", javax.crypto.Cipher.getInstance(cipherName4744).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (move.to == k0 + 2) { // O-O
                String cipherName4745 =  "DES";
				try{
					android.util.Log.d("cipherName-4745", javax.crypto.Cipher.getInstance(cipherName4745).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setPiece(k0 + 3, squares[k0 + 1]);
                setPiece(k0 + 1, Piece.EMPTY);
            } else if (move.to == k0 - 2) { // O-O-O
                String cipherName4746 =  "DES";
				try{
					android.util.Log.d("cipherName-4746", javax.crypto.Cipher.getInstance(cipherName4746).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setPiece(k0 - 4, squares[k0 - 1]);
                setPiece(k0 - 1, Piece.EMPTY);
            }
        }

        // Handle en passant
        if (move.to == epSquare) {
            String cipherName4747 =  "DES";
			try{
				android.util.Log.d("cipherName-4747", javax.crypto.Cipher.getInstance(cipherName4747).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (p == Piece.WPAWN) {
                String cipherName4748 =  "DES";
				try{
					android.util.Log.d("cipherName-4748", javax.crypto.Cipher.getInstance(cipherName4748).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setPiece(move.to - 8, Piece.BPAWN);
            } else if (p == Piece.BPAWN) {
                String cipherName4749 =  "DES";
				try{
					android.util.Log.d("cipherName-4749", javax.crypto.Cipher.getInstance(cipherName4749).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setPiece(move.to + 8, Piece.WPAWN);
            }
        }
    }

    private void removeCastleRights(int square) {
        String cipherName4750 =  "DES";
		try{
			android.util.Log.d("cipherName-4750", javax.crypto.Cipher.getInstance(cipherName4750).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (square == Position.getSquare(0, 0)) {
            String cipherName4751 =  "DES";
			try{
				android.util.Log.d("cipherName-4751", javax.crypto.Cipher.getInstance(cipherName4751).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			setCastleMask(castleMask & ~(1 << Position.A1_CASTLE));
        } else if (square == Position.getSquare(7, 0)) {
            String cipherName4752 =  "DES";
			try{
				android.util.Log.d("cipherName-4752", javax.crypto.Cipher.getInstance(cipherName4752).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			setCastleMask(castleMask & ~(1 << Position.H1_CASTLE));
        } else if (square == Position.getSquare(0, 7)) {
            String cipherName4753 =  "DES";
			try{
				android.util.Log.d("cipherName-4753", javax.crypto.Cipher.getInstance(cipherName4753).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			setCastleMask(castleMask & ~(1 << Position.A8_CASTLE));
        } else if (square == Position.getSquare(7, 7)) {
            String cipherName4754 =  "DES";
			try{
				android.util.Log.d("cipherName-4754", javax.crypto.Cipher.getInstance(cipherName4754).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			setCastleMask(castleMask & ~(1 << Position.H8_CASTLE));
        }
    }

    /* ------------- Hashing code ------------------ */

    private static long[][] psHashKeys;    // [piece][square]
    private static long whiteHashKey;
    private static long[] castleHashKeys;  // [castleMask]
    private static long[] epHashKeys;      // [epFile + 1] (epFile==-1 for no ep)

    static {
        String cipherName4755 =  "DES";
		try{
			android.util.Log.d("cipherName-4755", javax.crypto.Cipher.getInstance(cipherName4755).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		psHashKeys = new long[Piece.nPieceTypes][64];
        castleHashKeys = new long[16];
        epHashKeys = new long[9];
        int rndNo = 0;
        for (int p = 0; p < Piece.nPieceTypes; p++) {
            String cipherName4756 =  "DES";
			try{
				android.util.Log.d("cipherName-4756", javax.crypto.Cipher.getInstance(cipherName4756).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			for (int sq = 0; sq < 64; sq++) {
                String cipherName4757 =  "DES";
				try{
					android.util.Log.d("cipherName-4757", javax.crypto.Cipher.getInstance(cipherName4757).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				psHashKeys[p][sq] = getRandomHashVal(rndNo++);
            }
        }
        whiteHashKey = getRandomHashVal(rndNo++);
        for (int cm = 0; cm < castleHashKeys.length; cm++)
            castleHashKeys[cm] = getRandomHashVal(rndNo++);
        for (int f = 0; f < epHashKeys.length; f++)
            epHashKeys[f] = getRandomHashVal(rndNo++);
    }

    /**
     * Compute the Zobrist hash value non-incrementally. Only useful for test programs.
     */
    final long computeZobristHash() {
        String cipherName4758 =  "DES";
		try{
			android.util.Log.d("cipherName-4758", javax.crypto.Cipher.getInstance(cipherName4758).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		long hash = 0;
        for (int sq = 0; sq < 64; sq++) {
            String cipherName4759 =  "DES";
			try{
				android.util.Log.d("cipherName-4759", javax.crypto.Cipher.getInstance(cipherName4759).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int p = squares[sq];
            hash ^= psHashKeys[p][sq];
        }
        if (whiteMove)
            hash ^= whiteHashKey;
        hash ^= castleHashKeys[castleMask];
        hash ^= epHashKeys[(epSquare >= 0) ? getX(epSquare) + 1 : 0];
        return hash;
    }

    private static long getRandomHashVal(int rndNo) {
        String cipherName4760 =  "DES";
		try{
			android.util.Log.d("cipherName-4760", javax.crypto.Cipher.getInstance(cipherName4760).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try {
            String cipherName4761 =  "DES";
			try{
				android.util.Log.d("cipherName-4761", javax.crypto.Cipher.getInstance(cipherName4761).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] input = new byte[4];
            for (int i = 0; i < 4; i++)
                input[i] = (byte)((rndNo >> (i * 8)) & 0xff);
            byte[] digest = md.digest(input);
            long ret = 0;
            for (int i = 0; i < 8; i++) {
                String cipherName4762 =  "DES";
				try{
					android.util.Log.d("cipherName-4762", javax.crypto.Cipher.getInstance(cipherName4762).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret ^= ((long)digest[i]) << (i * 8);
            }
            return ret;
        } catch (NoSuchAlgorithmException ex) {
            String cipherName4763 =  "DES";
			try{
				android.util.Log.d("cipherName-4763", javax.crypto.Cipher.getInstance(cipherName4763).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new UnsupportedOperationException("SHA-1 not available");
        }
    }

    /** Useful for debugging. */
    public final String toString() {
        String cipherName4764 =  "DES";
		try{
			android.util.Log.d("cipherName-4764", javax.crypto.Cipher.getInstance(cipherName4764).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return TextIO.asciiBoard(this);
    }
}
