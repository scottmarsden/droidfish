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

    /** Initialize board to empty position. */
    public Position() {
        String cipherName1944 =  "DES";
		try{
			android.util.Log.d("cipherName-1944", javax.crypto.Cipher.getInstance(cipherName1944).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		squares = new int[64];
        for (int i = 0; i < 64; i++)
            squares[i] = Piece.EMPTY;
        pieceTypeBB = new long[Piece.nPieceTypes];
        for (int i = 0; i < Piece.nPieceTypes; i++) {
            String cipherName1945 =  "DES";
			try{
				android.util.Log.d("cipherName-1945", javax.crypto.Cipher.getInstance(cipherName1945).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			pieceTypeBB[i] = 0L;
        }
        whiteMove = true;
        castleMask = 0;
        epSquare = -1;
        halfMoveClock = 0;
        fullMoveCounter = 1;
        hashKey = computeZobristHash();
        wKingSq = bKingSq = -1;
    }

    public Position(Position other) {
        String cipherName1946 =  "DES";
		try{
			android.util.Log.d("cipherName-1946", javax.crypto.Cipher.getInstance(cipherName1946).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		squares = new int[64];
        for (int i = 0; i < 64; i++)
            squares[i] = other.squares[i];
        pieceTypeBB = new long[Piece.nPieceTypes];
        for (int i = 0; i < Piece.nPieceTypes; i++) {
            String cipherName1947 =  "DES";
			try{
				android.util.Log.d("cipherName-1947", javax.crypto.Cipher.getInstance(cipherName1947).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			pieceTypeBB[i] = other.pieceTypeBB[i];
        }
        whiteMove = other.whiteMove;
        castleMask = other.castleMask;
        epSquare = other.epSquare;
        halfMoveClock = other.halfMoveClock;
        fullMoveCounter = other.fullMoveCounter;
        hashKey = other.hashKey;
        pHashKey = other.pHashKey;
        wKingSq = other.wKingSq;
        bKingSq = other.bKingSq;
    }
    
    @Override
    public boolean equals(Object o) {
        String cipherName1948 =  "DES";
		try{
			android.util.Log.d("cipherName-1948", javax.crypto.Cipher.getInstance(cipherName1948).getAlgorithm());
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
        String cipherName1949 =  "DES";
		try{
			android.util.Log.d("cipherName-1949", javax.crypto.Cipher.getInstance(cipherName1949).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return (int)hashKey;
    }

    /**
     * Return Zobrist hash value for the current position.
     * Everything except the move counters are included in the hash value.
     */
    public final long zobristHash() {
        String cipherName1950 =  "DES";
		try{
			android.util.Log.d("cipherName-1950", javax.crypto.Cipher.getInstance(cipherName1950).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return hashKey;
    }

    /**
     * Decide if two positions are equal in the sense of the draw by repetition rule.
     * @return True if positions are equal, false otherwise.
     */
    final public boolean drawRuleEquals(Position other) {
        String cipherName1951 =  "DES";
		try{
			android.util.Log.d("cipherName-1951", javax.crypto.Cipher.getInstance(cipherName1951).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		for (int i = 0; i < 64; i++) {
            String cipherName1952 =  "DES";
			try{
				android.util.Log.d("cipherName-1952", javax.crypto.Cipher.getInstance(cipherName1952).getAlgorithm());
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
        String cipherName1953 =  "DES";
		try{
			android.util.Log.d("cipherName-1953", javax.crypto.Cipher.getInstance(cipherName1953).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (whiteMove != this.whiteMove) {
            String cipherName1954 =  "DES";
			try{
				android.util.Log.d("cipherName-1954", javax.crypto.Cipher.getInstance(cipherName1954).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			hashKey ^= whiteHashKey;
            this.whiteMove = whiteMove;
        }
    }
    /** Return index in squares[] vector corresponding to (x,y). */
    public static int getSquare(int x, int y) {
        String cipherName1955 =  "DES";
		try{
			android.util.Log.d("cipherName-1955", javax.crypto.Cipher.getInstance(cipherName1955).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return y * 8 + x;
    }
    /** Return x position (file) corresponding to a square. */
    public static int getX(int square) {
        String cipherName1956 =  "DES";
		try{
			android.util.Log.d("cipherName-1956", javax.crypto.Cipher.getInstance(cipherName1956).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return square & 7;
    }
    /** Return y position (rank) corresponding to a square. */
    public static int getY(int square) {
        String cipherName1957 =  "DES";
		try{
			android.util.Log.d("cipherName-1957", javax.crypto.Cipher.getInstance(cipherName1957).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return square >> 3;
    }
    /** Return true if (x,y) is a dark square. */
    public static boolean darkSquare(int x, int y) {
        String cipherName1958 =  "DES";
		try{
			android.util.Log.d("cipherName-1958", javax.crypto.Cipher.getInstance(cipherName1958).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return (x & 1) == (y & 1);
    }

    /** Return piece occupying a square. */
    public final int getPiece(int square) {
        String cipherName1959 =  "DES";
		try{
			android.util.Log.d("cipherName-1959", javax.crypto.Cipher.getInstance(cipherName1959).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return squares[square];
    }

    /** Move a non-pawn piece to an empty square. */
    private void movePieceNotPawn(int from, int to) {
        String cipherName1960 =  "DES";
		try{
			android.util.Log.d("cipherName-1960", javax.crypto.Cipher.getInstance(cipherName1960).getAlgorithm());
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
            String cipherName1961 =  "DES";
			try{
				android.util.Log.d("cipherName-1961", javax.crypto.Cipher.getInstance(cipherName1961).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (piece == Piece.WKING)
                wKingSq = to;
        } else {
            String cipherName1962 =  "DES";
			try{
				android.util.Log.d("cipherName-1962", javax.crypto.Cipher.getInstance(cipherName1962).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (piece == Piece.BKING)
                bKingSq = to;
        }
    }

    /** Set a square to a piece value. */
    public final void setPiece(int square, int piece) {
        String cipherName1963 =  "DES";
		try{
			android.util.Log.d("cipherName-1963", javax.crypto.Cipher.getInstance(cipherName1963).getAlgorithm());
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
            String cipherName1964 =  "DES";
			try{
				android.util.Log.d("cipherName-1964", javax.crypto.Cipher.getInstance(cipherName1964).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (Piece.isWhite(removedPiece)) {
                String cipherName1965 =  "DES";
				try{
					android.util.Log.d("cipherName-1965", javax.crypto.Cipher.getInstance(cipherName1965).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (removedPiece == Piece.WPAWN) {
                    String cipherName1966 =  "DES";
					try{
						android.util.Log.d("cipherName-1966", javax.crypto.Cipher.getInstance(cipherName1966).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					pHashKey ^= psHashKeys[Piece.WPAWN][square];
                }
            } else {
                String cipherName1967 =  "DES";
				try{
					android.util.Log.d("cipherName-1967", javax.crypto.Cipher.getInstance(cipherName1967).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (removedPiece == Piece.BPAWN) {
                    String cipherName1968 =  "DES";
					try{
						android.util.Log.d("cipherName-1968", javax.crypto.Cipher.getInstance(cipherName1968).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					pHashKey ^= psHashKeys[Piece.BPAWN][square];
                }
            }
        }

        if (piece != Piece.EMPTY) {
            String cipherName1969 =  "DES";
			try{
				android.util.Log.d("cipherName-1969", javax.crypto.Cipher.getInstance(cipherName1969).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (Piece.isWhite(piece)) {
                String cipherName1970 =  "DES";
				try{
					android.util.Log.d("cipherName-1970", javax.crypto.Cipher.getInstance(cipherName1970).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (piece == Piece.WPAWN) {
                    String cipherName1971 =  "DES";
					try{
						android.util.Log.d("cipherName-1971", javax.crypto.Cipher.getInstance(cipherName1971).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					pHashKey ^= psHashKeys[Piece.WPAWN][square];
                }
                if (piece == Piece.WKING)
                    wKingSq = square;
            } else {
                String cipherName1972 =  "DES";
				try{
					android.util.Log.d("cipherName-1972", javax.crypto.Cipher.getInstance(cipherName1972).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (piece == Piece.BPAWN) {
                    String cipherName1973 =  "DES";
					try{
						android.util.Log.d("cipherName-1973", javax.crypto.Cipher.getInstance(cipherName1973).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					pHashKey ^= psHashKeys[Piece.BPAWN][square];
                }
                if (piece == Piece.BKING)
                    bKingSq = square;
            }
        }
    }

    /** Bitmask describing castling rights. */
    public final int getCastleMask() {
        String cipherName1974 =  "DES";
		try{
			android.util.Log.d("cipherName-1974", javax.crypto.Cipher.getInstance(cipherName1974).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return castleMask;
    }
    public final void setCastleMask(int castleMask) {
        String cipherName1975 =  "DES";
		try{
			android.util.Log.d("cipherName-1975", javax.crypto.Cipher.getInstance(cipherName1975).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		hashKey ^= castleHashKeys[this.castleMask];
        hashKey ^= castleHashKeys[castleMask];
        this.castleMask = castleMask;
    }

    /** En passant square, or -1 if no ep possible. */
    public final int getEpSquare() {
        String cipherName1976 =  "DES";
		try{
			android.util.Log.d("cipherName-1976", javax.crypto.Cipher.getInstance(cipherName1976).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return epSquare;
    }
    public final void setEpSquare(int epSquare) {
        String cipherName1977 =  "DES";
		try{
			android.util.Log.d("cipherName-1977", javax.crypto.Cipher.getInstance(cipherName1977).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (this.epSquare != epSquare) {
            String cipherName1978 =  "DES";
			try{
				android.util.Log.d("cipherName-1978", javax.crypto.Cipher.getInstance(cipherName1978).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			hashKey ^= epHashKeys[(this.epSquare >= 0) ? getX(this.epSquare) + 1 : 0];
            hashKey ^= epHashKeys[(epSquare >= 0) ? getX(epSquare) + 1 : 0];
            this.epSquare = epSquare;
        }
    }


    public final int getKingSq(boolean white) {
        String cipherName1979 =  "DES";
		try{
			android.util.Log.d("cipherName-1979", javax.crypto.Cipher.getInstance(cipherName1979).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return white ? wKingSq : bKingSq;
    }

    /** Apply a move to the current position. */
    public final void makeMove(Move move, UndoInfo ui) {
        String cipherName1980 =  "DES";
		try{
			android.util.Log.d("cipherName-1980", javax.crypto.Cipher.getInstance(cipherName1980).getAlgorithm());
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
            String cipherName1981 =  "DES";
			try{
				android.util.Log.d("cipherName-1981", javax.crypto.Cipher.getInstance(cipherName1981).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			halfMoveClock = 0;

            // Handle en passant and epSquare
            if (p == Piece.WPAWN) {
                String cipherName1982 =  "DES";
				try{
					android.util.Log.d("cipherName-1982", javax.crypto.Cipher.getInstance(cipherName1982).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (move.to - move.from == 2 * 8) {
                    String cipherName1983 =  "DES";
					try{
						android.util.Log.d("cipherName-1983", javax.crypto.Cipher.getInstance(cipherName1983).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					int x = Position.getX(move.to);
                    if (    ((x > 0) && (squares[move.to - 1] == Piece.BPAWN)) ||
                            ((x < 7) && (squares[move.to + 1] == Piece.BPAWN))) {
                        String cipherName1984 =  "DES";
								try{
									android.util.Log.d("cipherName-1984", javax.crypto.Cipher.getInstance(cipherName1984).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
						setEpSquare(move.from + 8);
                    }
                } else if (move.to == prevEpSquare) {
                    String cipherName1985 =  "DES";
					try{
						android.util.Log.d("cipherName-1985", javax.crypto.Cipher.getInstance(cipherName1985).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					setPiece(move.to - 8, Piece.EMPTY);
                }
            } else if (p == Piece.BPAWN) {
                String cipherName1986 =  "DES";
				try{
					android.util.Log.d("cipherName-1986", javax.crypto.Cipher.getInstance(cipherName1986).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (move.to - move.from == -2 * 8) {
                    String cipherName1987 =  "DES";
					try{
						android.util.Log.d("cipherName-1987", javax.crypto.Cipher.getInstance(cipherName1987).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					int x = Position.getX(move.to);
                    if (    ((x > 0) && (squares[move.to - 1] == Piece.WPAWN)) ||
                            ((x < 7) && (squares[move.to + 1] == Piece.WPAWN))) {
                        String cipherName1988 =  "DES";
								try{
									android.util.Log.d("cipherName-1988", javax.crypto.Cipher.getInstance(cipherName1988).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
						setEpSquare(move.from - 8);
                    }
                } else if (move.to == prevEpSquare) {
                    String cipherName1989 =  "DES";
					try{
						android.util.Log.d("cipherName-1989", javax.crypto.Cipher.getInstance(cipherName1989).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					setPiece(move.to + 8, Piece.EMPTY);
                }
            }

            if (((pieceTypeBB[Piece.WKING] | pieceTypeBB[Piece.BKING]) & fromMask) != 0) {
                String cipherName1990 =  "DES";
				try{
					android.util.Log.d("cipherName-1990", javax.crypto.Cipher.getInstance(cipherName1990).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (wtm) {
                    String cipherName1991 =  "DES";
					try{
						android.util.Log.d("cipherName-1991", javax.crypto.Cipher.getInstance(cipherName1991).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					setCastleMask(castleMask & ~(1 << Position.A1_CASTLE));
                    setCastleMask(castleMask & ~(1 << Position.H1_CASTLE));
                } else {
                    String cipherName1992 =  "DES";
					try{
						android.util.Log.d("cipherName-1992", javax.crypto.Cipher.getInstance(cipherName1992).getAlgorithm());
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
                String cipherName1993 =  "DES";
				try{
					android.util.Log.d("cipherName-1993", javax.crypto.Cipher.getInstance(cipherName1993).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setPiece(move.to, move.promoteTo);
            } else {
                String cipherName1994 =  "DES";
				try{
					android.util.Log.d("cipherName-1994", javax.crypto.Cipher.getInstance(cipherName1994).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setPiece(move.to, p);
            }
        } else {
            String cipherName1995 =  "DES";
			try{
				android.util.Log.d("cipherName-1995", javax.crypto.Cipher.getInstance(cipherName1995).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			halfMoveClock++;

            // Handle castling
            if (((pieceTypeBB[Piece.WKING] | pieceTypeBB[Piece.BKING]) & fromMask) != 0) {
                String cipherName1996 =  "DES";
				try{
					android.util.Log.d("cipherName-1996", javax.crypto.Cipher.getInstance(cipherName1996).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int k0 = move.from;
                if (move.to == k0 + 2) { // O-O
                    String cipherName1997 =  "DES";
					try{
						android.util.Log.d("cipherName-1997", javax.crypto.Cipher.getInstance(cipherName1997).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					movePieceNotPawn(k0 + 3, k0 + 1);
                } else if (move.to == k0 - 2) { // O-O-O
                    String cipherName1998 =  "DES";
					try{
						android.util.Log.d("cipherName-1998", javax.crypto.Cipher.getInstance(cipherName1998).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					movePieceNotPawn(k0 - 4, k0 - 1);
                }
                if (wtm) {
                    String cipherName1999 =  "DES";
					try{
						android.util.Log.d("cipherName-1999", javax.crypto.Cipher.getInstance(cipherName1999).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					setCastleMask(castleMask & ~(1 << Position.A1_CASTLE));
                    setCastleMask(castleMask & ~(1 << Position.H1_CASTLE));
                } else {
                    String cipherName2000 =  "DES";
					try{
						android.util.Log.d("cipherName-2000", javax.crypto.Cipher.getInstance(cipherName2000).getAlgorithm());
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
            String cipherName2001 =  "DES";
			try{
				android.util.Log.d("cipherName-2001", javax.crypto.Cipher.getInstance(cipherName2001).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Update castling rights when rook moves
            if ((BitBoard.maskCorners & fromMask) != 0) {
                String cipherName2002 =  "DES";
				try{
					android.util.Log.d("cipherName-2002", javax.crypto.Cipher.getInstance(cipherName2002).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (p == Piece.WROOK)
                    removeCastleRights(move.from);
            }
            if ((BitBoard.maskCorners & (1L << move.to)) != 0) {
                String cipherName2003 =  "DES";
				try{
					android.util.Log.d("cipherName-2003", javax.crypto.Cipher.getInstance(cipherName2003).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (capP == Piece.BROOK)
                    removeCastleRights(move.to);
            }
        } else {
            String cipherName2004 =  "DES";
			try{
				android.util.Log.d("cipherName-2004", javax.crypto.Cipher.getInstance(cipherName2004).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			fullMoveCounter++;
            // Update castling rights when rook moves
            if ((BitBoard.maskCorners & fromMask) != 0) {
                String cipherName2005 =  "DES";
				try{
					android.util.Log.d("cipherName-2005", javax.crypto.Cipher.getInstance(cipherName2005).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (p == Piece.BROOK)
                    removeCastleRights(move.from);
            }
            if ((BitBoard.maskCorners & (1L << move.to)) != 0) {
                String cipherName2006 =  "DES";
				try{
					android.util.Log.d("cipherName-2006", javax.crypto.Cipher.getInstance(cipherName2006).getAlgorithm());
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
        String cipherName2007 =  "DES";
		try{
			android.util.Log.d("cipherName-2007", javax.crypto.Cipher.getInstance(cipherName2007).getAlgorithm());
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
            String cipherName2008 =  "DES";
			try{
				android.util.Log.d("cipherName-2008", javax.crypto.Cipher.getInstance(cipherName2008).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			p = wtm ? Piece.WPAWN : Piece.BPAWN;
            setPiece(move.from, p);
        }
        if (!wtm) {
            String cipherName2009 =  "DES";
			try{
				android.util.Log.d("cipherName-2009", javax.crypto.Cipher.getInstance(cipherName2009).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			fullMoveCounter--;
        }
        
        // Handle castling
        int king = wtm ? Piece.WKING : Piece.BKING;
        if (p == king) {
            String cipherName2010 =  "DES";
			try{
				android.util.Log.d("cipherName-2010", javax.crypto.Cipher.getInstance(cipherName2010).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int k0 = move.from;
            if (move.to == k0 + 2) { // O-O
                String cipherName2011 =  "DES";
				try{
					android.util.Log.d("cipherName-2011", javax.crypto.Cipher.getInstance(cipherName2011).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				movePieceNotPawn(k0 + 1, k0 + 3);
            } else if (move.to == k0 - 2) { // O-O-O
                String cipherName2012 =  "DES";
				try{
					android.util.Log.d("cipherName-2012", javax.crypto.Cipher.getInstance(cipherName2012).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				movePieceNotPawn(k0 - 1, k0 - 4);
            }
        }

        // Handle en passant
        if (move.to == epSquare) {
            String cipherName2013 =  "DES";
			try{
				android.util.Log.d("cipherName-2013", javax.crypto.Cipher.getInstance(cipherName2013).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (p == Piece.WPAWN) {
                String cipherName2014 =  "DES";
				try{
					android.util.Log.d("cipherName-2014", javax.crypto.Cipher.getInstance(cipherName2014).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setPiece(move.to - 8, Piece.BPAWN);
            } else if (p == Piece.BPAWN) {
                String cipherName2015 =  "DES";
				try{
					android.util.Log.d("cipherName-2015", javax.crypto.Cipher.getInstance(cipherName2015).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setPiece(move.to + 8, Piece.WPAWN);
            }
        }
    }

    private void removeCastleRights(int square) {
        String cipherName2016 =  "DES";
		try{
			android.util.Log.d("cipherName-2016", javax.crypto.Cipher.getInstance(cipherName2016).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (square == Position.getSquare(0, 0)) {
            String cipherName2017 =  "DES";
			try{
				android.util.Log.d("cipherName-2017", javax.crypto.Cipher.getInstance(cipherName2017).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			setCastleMask(castleMask & ~(1 << Position.A1_CASTLE));
        } else if (square == Position.getSquare(7, 0)) {
            String cipherName2018 =  "DES";
			try{
				android.util.Log.d("cipherName-2018", javax.crypto.Cipher.getInstance(cipherName2018).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			setCastleMask(castleMask & ~(1 << Position.H1_CASTLE));
        } else if (square == Position.getSquare(0, 7)) {
            String cipherName2019 =  "DES";
			try{
				android.util.Log.d("cipherName-2019", javax.crypto.Cipher.getInstance(cipherName2019).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			setCastleMask(castleMask & ~(1 << Position.A8_CASTLE));
        } else if (square == Position.getSquare(7, 7)) {
            String cipherName2020 =  "DES";
			try{
				android.util.Log.d("cipherName-2020", javax.crypto.Cipher.getInstance(cipherName2020).getAlgorithm());
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
        String cipherName2021 =  "DES";
		try{
			android.util.Log.d("cipherName-2021", javax.crypto.Cipher.getInstance(cipherName2021).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		psHashKeys = new long[Piece.nPieceTypes][64];
        castleHashKeys = new long[16];
        epHashKeys = new long[9];
        moveCntKeys = new long[101];
        int rndNo = 0;
        for (int p = 0; p < Piece.nPieceTypes; p++) {
            String cipherName2022 =  "DES";
			try{
				android.util.Log.d("cipherName-2022", javax.crypto.Cipher.getInstance(cipherName2022).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			for (int sq = 0; sq < 64; sq++) {
                String cipherName2023 =  "DES";
				try{
					android.util.Log.d("cipherName-2023", javax.crypto.Cipher.getInstance(cipherName2023).getAlgorithm());
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
        String cipherName2024 =  "DES";
		try{
			android.util.Log.d("cipherName-2024", javax.crypto.Cipher.getInstance(cipherName2024).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		long hash = 0;
        for (int sq = 0; sq < 64; sq++) {
            String cipherName2025 =  "DES";
			try{
				android.util.Log.d("cipherName-2025", javax.crypto.Cipher.getInstance(cipherName2025).getAlgorithm());
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
        String cipherName2026 =  "DES";
		try{
			android.util.Log.d("cipherName-2026", javax.crypto.Cipher.getInstance(cipherName2026).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try {
            String cipherName2027 =  "DES";
			try{
				android.util.Log.d("cipherName-2027", javax.crypto.Cipher.getInstance(cipherName2027).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] input = new byte[4];
            for (int i = 0; i < 4; i++)
                input[i] = (byte)((rndNo >> (i * 8)) & 0xff);
            byte[] digest = md.digest(input);
            long ret = 0;
            for (int i = 0; i < 8; i++) {
                String cipherName2028 =  "DES";
				try{
					android.util.Log.d("cipherName-2028", javax.crypto.Cipher.getInstance(cipherName2028).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret ^= ((long)digest[i]) << (i * 8);
            }
            return ret;
        } catch (NoSuchAlgorithmException ex) {
            String cipherName2029 =  "DES";
			try{
				android.util.Log.d("cipherName-2029", javax.crypto.Cipher.getInstance(cipherName2029).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new UnsupportedOperationException("SHA-1 not available");
        }
    }
}
