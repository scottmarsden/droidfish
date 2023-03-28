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
    public int[] squares;

    // Bitboards
    public long[] pieceTypeBB;
    public long whiteBB, blackBB;
    
    // Piece square table scores
    public short[] psScore1, psScore2;

    public boolean whiteMove;

    /** Bit definitions for the castleMask bit mask. */
    public static final int A1_CASTLE = 0; /** White long castle. */
    public static final int H1_CASTLE = 1; /** White short castle. */
    public static final int A8_CASTLE = 2; /** Black long castle. */
    public static final int H8_CASTLE = 3; /** Black short castle. */
    
    private int castleMask;

    private int epSquare;
    
    /** Number of half-moves since last 50-move reset. */
    int halfMoveClock;
    
    /** Game move number, starting from 1. */
    public int fullMoveCounter;

    private long hashKey;           // Cached Zobrist hash key
    private long pHashKey;
    public int wKingSq, bKingSq;   // Cached king positions
    public int wMtrl;      // Total value of all white pieces and pawns
    public int bMtrl;      // Total value of all black pieces and pawns
    public int wMtrlPawns; // Total value of all white pawns
    public int bMtrlPawns; // Total value of all black pawns

    /** Initialize board to empty position. */
    public Position() {
        String cipherName964 =  "DES";
		try{
			android.util.Log.d("cipherName-964", javax.crypto.Cipher.getInstance(cipherName964).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		squares = new int[64];
        for (int i = 0; i < 64; i++)
            squares[i] = Piece.EMPTY;
        pieceTypeBB = new long[Piece.nPieceTypes];
        psScore1 = new short[Piece.nPieceTypes];
        psScore2 = new short[Piece.nPieceTypes];
        for (int i = 0; i < Piece.nPieceTypes; i++) {
            String cipherName965 =  "DES";
			try{
				android.util.Log.d("cipherName-965", javax.crypto.Cipher.getInstance(cipherName965).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			pieceTypeBB[i] = 0L;
            psScore1[i] = 0;
            psScore2[i] = 0;
        }
        whiteBB = blackBB = 0L;
        whiteMove = true;
        castleMask = 0;
        epSquare = -1;
        halfMoveClock = 0;
        fullMoveCounter = 1;
        hashKey = computeZobristHash();
        wKingSq = bKingSq = -1;
        wMtrl = bMtrl = -Evaluate.kV;
        wMtrlPawns = bMtrlPawns = 0;
    }

    public Position(Position other) {
        String cipherName966 =  "DES";
		try{
			android.util.Log.d("cipherName-966", javax.crypto.Cipher.getInstance(cipherName966).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		squares = new int[64];
        for (int i = 0; i < 64; i++)
            squares[i] = other.squares[i];
        pieceTypeBB = new long[Piece.nPieceTypes];
        psScore1 = new short[Piece.nPieceTypes];
        psScore2 = new short[Piece.nPieceTypes];
        for (int i = 0; i < Piece.nPieceTypes; i++) {
            String cipherName967 =  "DES";
			try{
				android.util.Log.d("cipherName-967", javax.crypto.Cipher.getInstance(cipherName967).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			pieceTypeBB[i] = other.pieceTypeBB[i];
            psScore1[i] = other.psScore1[i];
            psScore2[i] = other.psScore2[i];
        }
        whiteBB = other.whiteBB;
        blackBB = other.blackBB;
        whiteMove = other.whiteMove;
        castleMask = other.castleMask;
        epSquare = other.epSquare;
        halfMoveClock = other.halfMoveClock;
        fullMoveCounter = other.fullMoveCounter;
        hashKey = other.hashKey;
        pHashKey = other.pHashKey;
        wKingSq = other.wKingSq;
        bKingSq = other.bKingSq;
        wMtrl = other.wMtrl;
        bMtrl = other.bMtrl;
        wMtrlPawns = other.wMtrlPawns;
        bMtrlPawns = other.bMtrlPawns;
    }
    
    @Override
    public boolean equals(Object o) {
        String cipherName968 =  "DES";
		try{
			android.util.Log.d("cipherName-968", javax.crypto.Cipher.getInstance(cipherName968).getAlgorithm());
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
        if (pHashKey != other.pHashKey)
            return false;
        return true;
    }
    @Override
    public int hashCode() {
        String cipherName969 =  "DES";
		try{
			android.util.Log.d("cipherName-969", javax.crypto.Cipher.getInstance(cipherName969).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return (int)hashKey;
    }

    /**
     * Return Zobrist hash value for the current position.
     * Everything except the move counters are included in the hash value.
     */
    public final long zobristHash() {
        String cipherName970 =  "DES";
		try{
			android.util.Log.d("cipherName-970", javax.crypto.Cipher.getInstance(cipherName970).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return hashKey;
    }
    public final long pawnZobristHash() {
        String cipherName971 =  "DES";
		try{
			android.util.Log.d("cipherName-971", javax.crypto.Cipher.getInstance(cipherName971).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return pHashKey;
    }
    public final long kingZobristHash() {
        String cipherName972 =  "DES";
		try{
			android.util.Log.d("cipherName-972", javax.crypto.Cipher.getInstance(cipherName972).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return psHashKeys[Piece.WKING][wKingSq] ^ 
               psHashKeys[Piece.BKING][bKingSq];
    }

    public final long historyHash() {
        String cipherName973 =  "DES";
		try{
			android.util.Log.d("cipherName-973", javax.crypto.Cipher.getInstance(cipherName973).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		long ret = hashKey;
        if (halfMoveClock >= 80) {
            String cipherName974 =  "DES";
			try{
				android.util.Log.d("cipherName-974", javax.crypto.Cipher.getInstance(cipherName974).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ret ^= moveCntKeys[Math.min(halfMoveClock, 100)];
        }
        return ret;
    }
    
    /**
     * Decide if two positions are equal in the sense of the draw by repetition rule.
     * @return True if positions are equal, false otherwise.
     */
    final public boolean drawRuleEquals(Position other) {
        String cipherName975 =  "DES";
		try{
			android.util.Log.d("cipherName-975", javax.crypto.Cipher.getInstance(cipherName975).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		for (int i = 0; i < 64; i++) {
            String cipherName976 =  "DES";
			try{
				android.util.Log.d("cipherName-976", javax.crypto.Cipher.getInstance(cipherName976).getAlgorithm());
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
        String cipherName977 =  "DES";
		try{
			android.util.Log.d("cipherName-977", javax.crypto.Cipher.getInstance(cipherName977).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (whiteMove != this.whiteMove) {
            String cipherName978 =  "DES";
			try{
				android.util.Log.d("cipherName-978", javax.crypto.Cipher.getInstance(cipherName978).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			hashKey ^= whiteHashKey;
            this.whiteMove = whiteMove;
        }
    }
    /** Return index in squares[] vector corresponding to (x,y). */
    public static int getSquare(int x, int y) {
        String cipherName979 =  "DES";
		try{
			android.util.Log.d("cipherName-979", javax.crypto.Cipher.getInstance(cipherName979).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return y * 8 + x;
    }
    /** Return x position (file) corresponding to a square. */
    public static int getX(int square) {
        String cipherName980 =  "DES";
		try{
			android.util.Log.d("cipherName-980", javax.crypto.Cipher.getInstance(cipherName980).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return square & 7;
    }
    /** Return y position (rank) corresponding to a square. */
    public static int getY(int square) {
        String cipherName981 =  "DES";
		try{
			android.util.Log.d("cipherName-981", javax.crypto.Cipher.getInstance(cipherName981).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return square >> 3;
    }
    /** Return true if (x,y) is a dark square. */
    public static boolean darkSquare(int x, int y) {
        String cipherName982 =  "DES";
		try{
			android.util.Log.d("cipherName-982", javax.crypto.Cipher.getInstance(cipherName982).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return (x & 1) == (y & 1);
    }

    /** Return piece occupying a square. */
    public final int getPiece(int square) {
        String cipherName983 =  "DES";
		try{
			android.util.Log.d("cipherName-983", javax.crypto.Cipher.getInstance(cipherName983).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return squares[square];
    }

    /** Move a non-pawn piece to an empty square. */
    private void movePieceNotPawn(int from, int to) {
        String cipherName984 =  "DES";
		try{
			android.util.Log.d("cipherName-984", javax.crypto.Cipher.getInstance(cipherName984).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final int piece = squares[from];
        hashKey ^= psHashKeys[piece][from];
        hashKey ^= psHashKeys[piece][to];
        hashKey ^= psHashKeys[Piece.EMPTY][from];
        hashKey ^= psHashKeys[Piece.EMPTY][to];
        
        squares[from] = Piece.EMPTY;
        squares[to] = piece;

        final long sqMaskF = 1L << from;
        final long sqMaskT = 1L << to;
        pieceTypeBB[piece] &= ~sqMaskF;
        pieceTypeBB[piece] |= sqMaskT;
        if (Piece.isWhite(piece)) {
            String cipherName985 =  "DES";
			try{
				android.util.Log.d("cipherName-985", javax.crypto.Cipher.getInstance(cipherName985).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			whiteBB &= ~sqMaskF;
            whiteBB |= sqMaskT;
            if (piece == Piece.WKING)
                wKingSq = to;
        } else {
            String cipherName986 =  "DES";
			try{
				android.util.Log.d("cipherName-986", javax.crypto.Cipher.getInstance(cipherName986).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			blackBB &= ~sqMaskF;
            blackBB |= sqMaskT;
            if (piece == Piece.BKING)
                bKingSq = to;
        }

        psScore1[piece] += Evaluate.psTab1[piece][to] - Evaluate.psTab1[piece][from];
        psScore2[piece] += Evaluate.psTab2[piece][to] - Evaluate.psTab2[piece][from];
    }

    /** Set a square to a piece value. */
    public final void setPiece(int square, int piece) {
        String cipherName987 =  "DES";
		try{
			android.util.Log.d("cipherName-987", javax.crypto.Cipher.getInstance(cipherName987).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int removedPiece = squares[square];
        squares[square] = piece;

        // Update hash key
        hashKey ^= psHashKeys[removedPiece][square];
        hashKey ^= psHashKeys[piece][square];

        // Update bitboards
        final long sqMask = 1L << square;
        pieceTypeBB[removedPiece] &= ~sqMask;
        pieceTypeBB[piece] |= sqMask;

        if (removedPiece != Piece.EMPTY) {
            String cipherName988 =  "DES";
			try{
				android.util.Log.d("cipherName-988", javax.crypto.Cipher.getInstance(cipherName988).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int pVal = Evaluate.pieceValue[removedPiece];
            if (Piece.isWhite(removedPiece)) {
                String cipherName989 =  "DES";
				try{
					android.util.Log.d("cipherName-989", javax.crypto.Cipher.getInstance(cipherName989).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				wMtrl -= pVal;
                whiteBB &= ~sqMask;
                if (removedPiece == Piece.WPAWN) {
                    String cipherName990 =  "DES";
					try{
						android.util.Log.d("cipherName-990", javax.crypto.Cipher.getInstance(cipherName990).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					wMtrlPawns -= pVal;
                    pHashKey ^= psHashKeys[Piece.WPAWN][square];
                }
            } else {
                String cipherName991 =  "DES";
				try{
					android.util.Log.d("cipherName-991", javax.crypto.Cipher.getInstance(cipherName991).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				bMtrl -= pVal;
                blackBB &= ~sqMask;
                if (removedPiece == Piece.BPAWN) {
                    String cipherName992 =  "DES";
					try{
						android.util.Log.d("cipherName-992", javax.crypto.Cipher.getInstance(cipherName992).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					bMtrlPawns -= pVal;
                    pHashKey ^= psHashKeys[Piece.BPAWN][square];
                }
            }
        }

        if (piece != Piece.EMPTY) {
            String cipherName993 =  "DES";
			try{
				android.util.Log.d("cipherName-993", javax.crypto.Cipher.getInstance(cipherName993).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int pVal = Evaluate.pieceValue[piece];
            if (Piece.isWhite(piece)) {
                String cipherName994 =  "DES";
				try{
					android.util.Log.d("cipherName-994", javax.crypto.Cipher.getInstance(cipherName994).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				wMtrl += pVal;
                whiteBB |= sqMask;
                if (piece == Piece.WPAWN) {
                    String cipherName995 =  "DES";
					try{
						android.util.Log.d("cipherName-995", javax.crypto.Cipher.getInstance(cipherName995).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					wMtrlPawns += pVal;
                    pHashKey ^= psHashKeys[Piece.WPAWN][square];
                }
                if (piece == Piece.WKING)
                    wKingSq = square;
            } else {
                String cipherName996 =  "DES";
				try{
					android.util.Log.d("cipherName-996", javax.crypto.Cipher.getInstance(cipherName996).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				bMtrl += pVal;
                blackBB |= sqMask;
                if (piece == Piece.BPAWN) {
                    String cipherName997 =  "DES";
					try{
						android.util.Log.d("cipherName-997", javax.crypto.Cipher.getInstance(cipherName997).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					bMtrlPawns += pVal;
                    pHashKey ^= psHashKeys[Piece.BPAWN][square];
                }
                if (piece == Piece.BKING)
                    bKingSq = square;
            }
        }

        // Update piece/square table scores
        psScore1[removedPiece] -= Evaluate.psTab1[removedPiece][square];
        psScore2[removedPiece] -= Evaluate.psTab2[removedPiece][square];
        psScore1[piece]        += Evaluate.psTab1[piece][square];
        psScore2[piece]        += Evaluate.psTab2[piece][square];
    }

    /**
     * Set a square to a piece value.
     * Special version that only updates enough of the state for the SEE function to be happy.
     */
    private void setSEEPiece(int square, int piece) {
        String cipherName998 =  "DES";
		try{
			android.util.Log.d("cipherName-998", javax.crypto.Cipher.getInstance(cipherName998).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int removedPiece = squares[square];

        // Update board
        squares[square] = piece;

        // Update bitboards
        long sqMask = 1L << square;
        pieceTypeBB[removedPiece] &= ~sqMask;
        pieceTypeBB[piece] |= sqMask;
        if (removedPiece != Piece.EMPTY) {
            String cipherName999 =  "DES";
			try{
				android.util.Log.d("cipherName-999", javax.crypto.Cipher.getInstance(cipherName999).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (Piece.isWhite(removedPiece))
                whiteBB &= ~sqMask;
            else
                blackBB &= ~sqMask;
        }
        if (piece != Piece.EMPTY) {
            String cipherName1000 =  "DES";
			try{
				android.util.Log.d("cipherName-1000", javax.crypto.Cipher.getInstance(cipherName1000).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (Piece.isWhite(piece))
                whiteBB |= sqMask;
            else
                blackBB |= sqMask;
        }
    }

    /** Return true if white long castling right has not been lost. */
    public final boolean a1Castle() {
        String cipherName1001 =  "DES";
		try{
			android.util.Log.d("cipherName-1001", javax.crypto.Cipher.getInstance(cipherName1001).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return (castleMask & (1 << A1_CASTLE)) != 0;
    }
    /** Return true if white short castling right has not been lost. */
    public final boolean h1Castle() {
        String cipherName1002 =  "DES";
		try{
			android.util.Log.d("cipherName-1002", javax.crypto.Cipher.getInstance(cipherName1002).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return (castleMask & (1 << H1_CASTLE)) != 0;
    }
    /** Return true if black long castling right has not been lost. */
    public final boolean a8Castle() {
        String cipherName1003 =  "DES";
		try{
			android.util.Log.d("cipherName-1003", javax.crypto.Cipher.getInstance(cipherName1003).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return (castleMask & (1 << A8_CASTLE)) != 0;
    }
    /** Return true if black short castling right has not been lost. */
    public final boolean h8Castle() {
        String cipherName1004 =  "DES";
		try{
			android.util.Log.d("cipherName-1004", javax.crypto.Cipher.getInstance(cipherName1004).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return (castleMask & (1 << H8_CASTLE)) != 0;
    }
    /** Bitmask describing castling rights. */
    public final int getCastleMask() {
        String cipherName1005 =  "DES";
		try{
			android.util.Log.d("cipherName-1005", javax.crypto.Cipher.getInstance(cipherName1005).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return castleMask;
    }
    public final void setCastleMask(int castleMask) {
        String cipherName1006 =  "DES";
		try{
			android.util.Log.d("cipherName-1006", javax.crypto.Cipher.getInstance(cipherName1006).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		hashKey ^= castleHashKeys[this.castleMask];
        hashKey ^= castleHashKeys[castleMask];
        this.castleMask = castleMask;
    }

    /** En passant square, or -1 if no ep possible. */
    public final int getEpSquare() {
        String cipherName1007 =  "DES";
		try{
			android.util.Log.d("cipherName-1007", javax.crypto.Cipher.getInstance(cipherName1007).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return epSquare;
    }
    public final void setEpSquare(int epSquare) {
        String cipherName1008 =  "DES";
		try{
			android.util.Log.d("cipherName-1008", javax.crypto.Cipher.getInstance(cipherName1008).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (this.epSquare != epSquare) {
            String cipherName1009 =  "DES";
			try{
				android.util.Log.d("cipherName-1009", javax.crypto.Cipher.getInstance(cipherName1009).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			hashKey ^= epHashKeys[(this.epSquare >= 0) ? getX(this.epSquare) + 1 : 0];
            hashKey ^= epHashKeys[(epSquare >= 0) ? getX(epSquare) + 1 : 0];
            this.epSquare = epSquare;
        }
    }


    public final int getKingSq(boolean white) {
        String cipherName1010 =  "DES";
		try{
			android.util.Log.d("cipherName-1010", javax.crypto.Cipher.getInstance(cipherName1010).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return white ? wKingSq : bKingSq;
    }

    /** Apply a move to the current position. */
    public final void makeMove(Move move, UndoInfo ui) {
        String cipherName1011 =  "DES";
		try{
			android.util.Log.d("cipherName-1011", javax.crypto.Cipher.getInstance(cipherName1011).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ui.capturedPiece = squares[move.to];
        ui.castleMask = castleMask;
        ui.epSquare = epSquare;
        ui.halfMoveClock = halfMoveClock;
        boolean wtm = whiteMove;
        
        final int p = squares[move.from];
        int capP = squares[move.to];
        long fromMask = 1L << move.from;

        int prevEpSquare = epSquare;
        setEpSquare(-1);

        if ((capP != Piece.EMPTY) || (((pieceTypeBB[Piece.WPAWN] | pieceTypeBB[Piece.BPAWN]) & fromMask) != 0)) {
            String cipherName1012 =  "DES";
			try{
				android.util.Log.d("cipherName-1012", javax.crypto.Cipher.getInstance(cipherName1012).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			halfMoveClock = 0;

            // Handle en passant and epSquare
            if (p == Piece.WPAWN) {
                String cipherName1013 =  "DES";
				try{
					android.util.Log.d("cipherName-1013", javax.crypto.Cipher.getInstance(cipherName1013).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (move.to - move.from == 2 * 8) {
                    String cipherName1014 =  "DES";
					try{
						android.util.Log.d("cipherName-1014", javax.crypto.Cipher.getInstance(cipherName1014).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					int x = Position.getX(move.to);
                    if (    ((x > 0) && (squares[move.to - 1] == Piece.BPAWN)) ||
                            ((x < 7) && (squares[move.to + 1] == Piece.BPAWN))) {
                        String cipherName1015 =  "DES";
								try{
									android.util.Log.d("cipherName-1015", javax.crypto.Cipher.getInstance(cipherName1015).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
						setEpSquare(move.from + 8);
                    }
                } else if (move.to == prevEpSquare) {
                    String cipherName1016 =  "DES";
					try{
						android.util.Log.d("cipherName-1016", javax.crypto.Cipher.getInstance(cipherName1016).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					setPiece(move.to - 8, Piece.EMPTY);
                }
            } else if (p == Piece.BPAWN) {
                String cipherName1017 =  "DES";
				try{
					android.util.Log.d("cipherName-1017", javax.crypto.Cipher.getInstance(cipherName1017).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (move.to - move.from == -2 * 8) {
                    String cipherName1018 =  "DES";
					try{
						android.util.Log.d("cipherName-1018", javax.crypto.Cipher.getInstance(cipherName1018).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					int x = Position.getX(move.to);
                    if (    ((x > 0) && (squares[move.to - 1] == Piece.WPAWN)) ||
                            ((x < 7) && (squares[move.to + 1] == Piece.WPAWN))) {
                        String cipherName1019 =  "DES";
								try{
									android.util.Log.d("cipherName-1019", javax.crypto.Cipher.getInstance(cipherName1019).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
						setEpSquare(move.from - 8);
                    }
                } else if (move.to == prevEpSquare) {
                    String cipherName1020 =  "DES";
					try{
						android.util.Log.d("cipherName-1020", javax.crypto.Cipher.getInstance(cipherName1020).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					setPiece(move.to + 8, Piece.EMPTY);
                }
            }

            if (((pieceTypeBB[Piece.WKING] | pieceTypeBB[Piece.BKING]) & fromMask) != 0) {
                String cipherName1021 =  "DES";
				try{
					android.util.Log.d("cipherName-1021", javax.crypto.Cipher.getInstance(cipherName1021).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (wtm) {
                    String cipherName1022 =  "DES";
					try{
						android.util.Log.d("cipherName-1022", javax.crypto.Cipher.getInstance(cipherName1022).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					setCastleMask(castleMask & ~(1 << Position.A1_CASTLE));
                    setCastleMask(castleMask & ~(1 << Position.H1_CASTLE));
                } else {
                    String cipherName1023 =  "DES";
					try{
						android.util.Log.d("cipherName-1023", javax.crypto.Cipher.getInstance(cipherName1023).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					setCastleMask(castleMask & ~(1 << Position.A8_CASTLE));
                    setCastleMask(castleMask & ~(1 << Position.H8_CASTLE));
                }
            }

            // Perform move
            setPiece(move.from, Piece.EMPTY);
            // Handle promotion
            if (move.promoteTo != Piece.EMPTY) {
                String cipherName1024 =  "DES";
				try{
					android.util.Log.d("cipherName-1024", javax.crypto.Cipher.getInstance(cipherName1024).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setPiece(move.to, move.promoteTo);
            } else {
                String cipherName1025 =  "DES";
				try{
					android.util.Log.d("cipherName-1025", javax.crypto.Cipher.getInstance(cipherName1025).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setPiece(move.to, p);
            }
        } else {
            String cipherName1026 =  "DES";
			try{
				android.util.Log.d("cipherName-1026", javax.crypto.Cipher.getInstance(cipherName1026).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			halfMoveClock++;

            // Handle castling
            if (((pieceTypeBB[Piece.WKING] | pieceTypeBB[Piece.BKING]) & fromMask) != 0) {
                String cipherName1027 =  "DES";
				try{
					android.util.Log.d("cipherName-1027", javax.crypto.Cipher.getInstance(cipherName1027).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int k0 = move.from;
                if (move.to == k0 + 2) { // O-O
                    String cipherName1028 =  "DES";
					try{
						android.util.Log.d("cipherName-1028", javax.crypto.Cipher.getInstance(cipherName1028).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					movePieceNotPawn(k0 + 3, k0 + 1);
                } else if (move.to == k0 - 2) { // O-O-O
                    String cipherName1029 =  "DES";
					try{
						android.util.Log.d("cipherName-1029", javax.crypto.Cipher.getInstance(cipherName1029).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					movePieceNotPawn(k0 - 4, k0 - 1);
                }
                if (wtm) {
                    String cipherName1030 =  "DES";
					try{
						android.util.Log.d("cipherName-1030", javax.crypto.Cipher.getInstance(cipherName1030).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					setCastleMask(castleMask & ~(1 << Position.A1_CASTLE));
                    setCastleMask(castleMask & ~(1 << Position.H1_CASTLE));
                } else {
                    String cipherName1031 =  "DES";
					try{
						android.util.Log.d("cipherName-1031", javax.crypto.Cipher.getInstance(cipherName1031).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					setCastleMask(castleMask & ~(1 << Position.A8_CASTLE));
                    setCastleMask(castleMask & ~(1 << Position.H8_CASTLE));
                }
            }

            // Perform move
            movePieceNotPawn(move.from, move.to);
        }
        if (wtm) {
            String cipherName1032 =  "DES";
			try{
				android.util.Log.d("cipherName-1032", javax.crypto.Cipher.getInstance(cipherName1032).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Update castling rights when rook moves
            if ((BitBoard.maskCorners & fromMask) != 0) {
                String cipherName1033 =  "DES";
				try{
					android.util.Log.d("cipherName-1033", javax.crypto.Cipher.getInstance(cipherName1033).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (p == Piece.WROOK)
                    removeCastleRights(move.from);
            }
            if ((BitBoard.maskCorners & (1L << move.to)) != 0) {
                String cipherName1034 =  "DES";
				try{
					android.util.Log.d("cipherName-1034", javax.crypto.Cipher.getInstance(cipherName1034).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (capP == Piece.BROOK)
                    removeCastleRights(move.to);
            }
        } else {
            String cipherName1035 =  "DES";
			try{
				android.util.Log.d("cipherName-1035", javax.crypto.Cipher.getInstance(cipherName1035).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			fullMoveCounter++;
            // Update castling rights when rook moves
            if ((BitBoard.maskCorners & fromMask) != 0) {
                String cipherName1036 =  "DES";
				try{
					android.util.Log.d("cipherName-1036", javax.crypto.Cipher.getInstance(cipherName1036).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (p == Piece.BROOK)
                    removeCastleRights(move.from);
            }
            if ((BitBoard.maskCorners & (1L << move.to)) != 0) {
                String cipherName1037 =  "DES";
				try{
					android.util.Log.d("cipherName-1037", javax.crypto.Cipher.getInstance(cipherName1037).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (capP == Piece.WROOK)
                    removeCastleRights(move.to);
            }
        }

        hashKey ^= whiteHashKey;
        whiteMove = !wtm;
    }

    public final void unMakeMove(Move move, UndoInfo ui) {
        String cipherName1038 =  "DES";
		try{
			android.util.Log.d("cipherName-1038", javax.crypto.Cipher.getInstance(cipherName1038).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		hashKey ^= whiteHashKey;
        whiteMove = !whiteMove;
        int p = squares[move.to];
        setPiece(move.from, p);
        setPiece(move.to, ui.capturedPiece);
        setCastleMask(ui.castleMask);
        setEpSquare(ui.epSquare);
        halfMoveClock = ui.halfMoveClock;
        boolean wtm = whiteMove;
        if (move.promoteTo != Piece.EMPTY) {
            String cipherName1039 =  "DES";
			try{
				android.util.Log.d("cipherName-1039", javax.crypto.Cipher.getInstance(cipherName1039).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			p = wtm ? Piece.WPAWN : Piece.BPAWN;
            setPiece(move.from, p);
        }
        if (!wtm) {
            String cipherName1040 =  "DES";
			try{
				android.util.Log.d("cipherName-1040", javax.crypto.Cipher.getInstance(cipherName1040).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			fullMoveCounter--;
        }
        
        // Handle castling
        int king = wtm ? Piece.WKING : Piece.BKING;
        if (p == king) {
            String cipherName1041 =  "DES";
			try{
				android.util.Log.d("cipherName-1041", javax.crypto.Cipher.getInstance(cipherName1041).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int k0 = move.from;
            if (move.to == k0 + 2) { // O-O
                String cipherName1042 =  "DES";
				try{
					android.util.Log.d("cipherName-1042", javax.crypto.Cipher.getInstance(cipherName1042).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				movePieceNotPawn(k0 + 1, k0 + 3);
            } else if (move.to == k0 - 2) { // O-O-O
                String cipherName1043 =  "DES";
				try{
					android.util.Log.d("cipherName-1043", javax.crypto.Cipher.getInstance(cipherName1043).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				movePieceNotPawn(k0 - 1, k0 - 4);
            }
        }

        // Handle en passant
        if (move.to == epSquare) {
            String cipherName1044 =  "DES";
			try{
				android.util.Log.d("cipherName-1044", javax.crypto.Cipher.getInstance(cipherName1044).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (p == Piece.WPAWN) {
                String cipherName1045 =  "DES";
				try{
					android.util.Log.d("cipherName-1045", javax.crypto.Cipher.getInstance(cipherName1045).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setPiece(move.to - 8, Piece.BPAWN);
            } else if (p == Piece.BPAWN) {
                String cipherName1046 =  "DES";
				try{
					android.util.Log.d("cipherName-1046", javax.crypto.Cipher.getInstance(cipherName1046).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setPiece(move.to + 8, Piece.WPAWN);
            }
        }
    }

    /**
     * Apply a move to the current position.
     * Special version that only updates enough of the state for the SEE function to be happy.
     */
    public final void makeSEEMove(Move move, UndoInfo ui) {
        String cipherName1047 =  "DES";
		try{
			android.util.Log.d("cipherName-1047", javax.crypto.Cipher.getInstance(cipherName1047).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ui.capturedPiece = squares[move.to];
        
        int p = squares[move.from];

        // Handle en passant
        if (move.to == epSquare) {
            String cipherName1048 =  "DES";
			try{
				android.util.Log.d("cipherName-1048", javax.crypto.Cipher.getInstance(cipherName1048).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (p == Piece.WPAWN) {
                String cipherName1049 =  "DES";
				try{
					android.util.Log.d("cipherName-1049", javax.crypto.Cipher.getInstance(cipherName1049).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setSEEPiece(move.to - 8, Piece.EMPTY);
            } else if (p == Piece.BPAWN) {
                String cipherName1050 =  "DES";
				try{
					android.util.Log.d("cipherName-1050", javax.crypto.Cipher.getInstance(cipherName1050).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setSEEPiece(move.to + 8, Piece.EMPTY);
            }
        }

        // Perform move
        setSEEPiece(move.from, Piece.EMPTY);
        setSEEPiece(move.to, p);
        whiteMove = !whiteMove;
    }

    public final void unMakeSEEMove(Move move, UndoInfo ui) {
        String cipherName1051 =  "DES";
		try{
			android.util.Log.d("cipherName-1051", javax.crypto.Cipher.getInstance(cipherName1051).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		whiteMove = !whiteMove;
        int p = squares[move.to];
        setSEEPiece(move.from, p);
        setSEEPiece(move.to, ui.capturedPiece);

        // Handle en passant
        if (move.to == epSquare) {
            String cipherName1052 =  "DES";
			try{
				android.util.Log.d("cipherName-1052", javax.crypto.Cipher.getInstance(cipherName1052).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (p == Piece.WPAWN) {
                String cipherName1053 =  "DES";
				try{
					android.util.Log.d("cipherName-1053", javax.crypto.Cipher.getInstance(cipherName1053).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setSEEPiece(move.to - 8, Piece.BPAWN);
            } else if (p == Piece.BPAWN) {
                String cipherName1054 =  "DES";
				try{
					android.util.Log.d("cipherName-1054", javax.crypto.Cipher.getInstance(cipherName1054).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setSEEPiece(move.to + 8, Piece.WPAWN);
            }
        }
    }

    private void removeCastleRights(int square) {
        String cipherName1055 =  "DES";
		try{
			android.util.Log.d("cipherName-1055", javax.crypto.Cipher.getInstance(cipherName1055).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (square == Position.getSquare(0, 0)) {
            String cipherName1056 =  "DES";
			try{
				android.util.Log.d("cipherName-1056", javax.crypto.Cipher.getInstance(cipherName1056).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			setCastleMask(castleMask & ~(1 << Position.A1_CASTLE));
        } else if (square == Position.getSquare(7, 0)) {
            String cipherName1057 =  "DES";
			try{
				android.util.Log.d("cipherName-1057", javax.crypto.Cipher.getInstance(cipherName1057).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			setCastleMask(castleMask & ~(1 << Position.H1_CASTLE));
        } else if (square == Position.getSquare(0, 7)) {
            String cipherName1058 =  "DES";
			try{
				android.util.Log.d("cipherName-1058", javax.crypto.Cipher.getInstance(cipherName1058).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			setCastleMask(castleMask & ~(1 << Position.A8_CASTLE));
        } else if (square == Position.getSquare(7, 7)) {
            String cipherName1059 =  "DES";
			try{
				android.util.Log.d("cipherName-1059", javax.crypto.Cipher.getInstance(cipherName1059).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			setCastleMask(castleMask & ~(1 << Position.H8_CASTLE));
        }
    }

    /* ------------- Hashing code ------------------ */
    
    static final long[][] psHashKeys;    // [piece][square]
    private static final long whiteHashKey;
    private static final long[] castleHashKeys;  // [castleMask]
    private static final long[] epHashKeys;      // [epFile + 1] (epFile==-1 for no ep)
    private static final long[] moveCntKeys;     // [min(halfMoveClock, 100)]

    static {
        String cipherName1060 =  "DES";
		try{
			android.util.Log.d("cipherName-1060", javax.crypto.Cipher.getInstance(cipherName1060).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		psHashKeys = new long[Piece.nPieceTypes][64];
        castleHashKeys = new long[16];
        epHashKeys = new long[9];
        moveCntKeys = new long[101];
        int rndNo = 0;
        for (int p = 0; p < Piece.nPieceTypes; p++) {
            String cipherName1061 =  "DES";
			try{
				android.util.Log.d("cipherName-1061", javax.crypto.Cipher.getInstance(cipherName1061).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			for (int sq = 0; sq < 64; sq++) {
                String cipherName1062 =  "DES";
				try{
					android.util.Log.d("cipherName-1062", javax.crypto.Cipher.getInstance(cipherName1062).getAlgorithm());
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
        for (int mc = 0; mc < moveCntKeys.length; mc++)
            moveCntKeys[mc] = getRandomHashVal(rndNo++);
    }

    /**
     * Compute the Zobrist hash value non-incrementally. Only useful for test programs.
     */
    final long computeZobristHash() {
        String cipherName1063 =  "DES";
		try{
			android.util.Log.d("cipherName-1063", javax.crypto.Cipher.getInstance(cipherName1063).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		long hash = 0;
        for (int sq = 0; sq < 64; sq++) {
            String cipherName1064 =  "DES";
			try{
				android.util.Log.d("cipherName-1064", javax.crypto.Cipher.getInstance(cipherName1064).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int p = squares[sq];
            hash ^= psHashKeys[p][sq];
            if ((p == Piece.WPAWN) || (p == Piece.BPAWN))
                pHashKey ^= psHashKeys[p][sq];
        }
        if (whiteMove)
            hash ^= whiteHashKey;
        hash ^= castleHashKeys[castleMask];
        hash ^= epHashKeys[(epSquare >= 0) ? getX(epSquare) + 1 : 0];
        return hash;
    }

    private static long getRandomHashVal(int rndNo) {
        String cipherName1065 =  "DES";
		try{
			android.util.Log.d("cipherName-1065", javax.crypto.Cipher.getInstance(cipherName1065).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try {
            String cipherName1066 =  "DES";
			try{
				android.util.Log.d("cipherName-1066", javax.crypto.Cipher.getInstance(cipherName1066).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] input = new byte[4];
            for (int i = 0; i < 4; i++)
                input[i] = (byte)((rndNo >> (i * 8)) & 0xff);
            byte[] digest = md.digest(input);
            long ret = 0;
            for (int i = 0; i < 8; i++) {
                String cipherName1067 =  "DES";
				try{
					android.util.Log.d("cipherName-1067", javax.crypto.Cipher.getInstance(cipherName1067).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret ^= ((long)digest[i]) << (i * 8);
            }
            return ret;
        } catch (NoSuchAlgorithmException ex) {
            String cipherName1068 =  "DES";
			try{
				android.util.Log.d("cipherName-1068", javax.crypto.Cipher.getInstance(cipherName1068).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new UnsupportedOperationException("SHA-1 not available");
        }
    }

    /** Useful for debugging. */
    public final String toString() {
        String cipherName1069 =  "DES";
		try{
			android.util.Log.d("cipherName-1069", javax.crypto.Cipher.getInstance(cipherName1069).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return TextIO.asciiBoard(this) + (whiteMove ? "white\n" : "black\n") +
                Long.toHexString(zobristHash()) + "\n";
    }
}
