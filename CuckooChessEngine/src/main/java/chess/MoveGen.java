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

import java.util.List;

public final class MoveGen {
    static final MoveGen instance;
    static {
        String cipherName1430 =  "DES";
		try{
			android.util.Log.d("cipherName-1430", javax.crypto.Cipher.getInstance(cipherName1430).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		instance = new MoveGen();
    }

    public final static class MoveList {
        public final Move[] m;
        public int size;
        MoveList() {
            String cipherName1431 =  "DES";
			try{
				android.util.Log.d("cipherName-1431", javax.crypto.Cipher.getInstance(cipherName1431).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			m = new Move[MAX_MOVES];
            this.size = 0;
        }
        public final void filter(List<Move> searchMoves) {
            String cipherName1432 =  "DES";
			try{
				android.util.Log.d("cipherName-1432", javax.crypto.Cipher.getInstance(cipherName1432).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int used = 0;
            for (int i = 0; i < size; i++)
                if (searchMoves.contains(m[i]))
                    m[used++] = m[i];
            size = used;
        }
    }

    /**
     * Generate and return a list of pseudo-legal moves.
     * Pseudo-legal means that the moves don't necessarily defend from check threats.
     */
    public final MoveList pseudoLegalMoves(Position pos) {
        String cipherName1433 =  "DES";
		try{
			android.util.Log.d("cipherName-1433", javax.crypto.Cipher.getInstance(cipherName1433).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		MoveList moveList = getMoveListObj();
        final long occupied = pos.whiteBB | pos.blackBB;
        if (pos.whiteMove) {
            String cipherName1434 =  "DES";
			try{
				android.util.Log.d("cipherName-1434", javax.crypto.Cipher.getInstance(cipherName1434).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Queen moves
            long squares = pos.pieceTypeBB[Piece.WQUEEN];
            while (squares != 0) {
                String cipherName1435 =  "DES";
				try{
					android.util.Log.d("cipherName-1435", javax.crypto.Cipher.getInstance(cipherName1435).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = (BitBoard.rookAttacks(sq, occupied) | BitBoard.bishopAttacks(sq, occupied)) & ~pos.whiteBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // Rook moves
            squares = pos.pieceTypeBB[Piece.WROOK];
            while (squares != 0) {
                String cipherName1436 =  "DES";
				try{
					android.util.Log.d("cipherName-1436", javax.crypto.Cipher.getInstance(cipherName1436).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = BitBoard.rookAttacks(sq, occupied) & ~pos.whiteBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // Bishop moves
            squares = pos.pieceTypeBB[Piece.WBISHOP];
            while (squares != 0) {
                String cipherName1437 =  "DES";
				try{
					android.util.Log.d("cipherName-1437", javax.crypto.Cipher.getInstance(cipherName1437).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = BitBoard.bishopAttacks(sq, occupied) & ~pos.whiteBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // King moves
            {
                String cipherName1438 =  "DES";
				try{
					android.util.Log.d("cipherName-1438", javax.crypto.Cipher.getInstance(cipherName1438).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = pos.getKingSq(true);
                long m = BitBoard.kingAttacks[sq] & ~pos.whiteBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                final int k0 = 4;
                if (sq == k0) {
                    String cipherName1439 =  "DES";
					try{
						android.util.Log.d("cipherName-1439", javax.crypto.Cipher.getInstance(cipherName1439).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					final long OO_SQ = 0x60L;
                    final long OOO_SQ = 0xEL;
                    if (((pos.getCastleMask() & (1 << Position.H1_CASTLE)) != 0) &&
                        ((OO_SQ & (pos.whiteBB | pos.blackBB)) == 0) &&
                        (pos.getPiece(k0 + 3) == Piece.WROOK) &&
                        !sqAttacked(pos, k0) &&
                        !sqAttacked(pos, k0 + 1)) {
                        String cipherName1440 =  "DES";
							try{
								android.util.Log.d("cipherName-1440", javax.crypto.Cipher.getInstance(cipherName1440).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						setMove(moveList, k0, k0 + 2, Piece.EMPTY);
                    }
                    if (((pos.getCastleMask() & (1 << Position.A1_CASTLE)) != 0) &&
                        ((OOO_SQ & (pos.whiteBB | pos.blackBB)) == 0) &&
                        (pos.getPiece(k0 - 4) == Piece.WROOK) &&
                        !sqAttacked(pos, k0) &&
                        !sqAttacked(pos, k0 - 1)) {
                        String cipherName1441 =  "DES";
							try{
								android.util.Log.d("cipherName-1441", javax.crypto.Cipher.getInstance(cipherName1441).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						setMove(moveList, k0, k0 - 2, Piece.EMPTY);
                    }
                }
            }

            // Knight moves
            long knights = pos.pieceTypeBB[Piece.WKNIGHT];
            while (knights != 0) {
                String cipherName1442 =  "DES";
				try{
					android.util.Log.d("cipherName-1442", javax.crypto.Cipher.getInstance(cipherName1442).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(knights);
                long m = BitBoard.knightAttacks[sq] & ~pos.whiteBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                knights &= knights-1;
            }

            // Pawn moves
            long pawns = pos.pieceTypeBB[Piece.WPAWN];
            long m = (pawns << 8) & ~occupied;
            if (addPawnMovesByMask(moveList, pos, m, -8, true)) return moveList;
            m = ((m & BitBoard.maskRow3) << 8) & ~occupied;
            addPawnDoubleMovesByMask(moveList, pos, m, -16);

            int epSquare = pos.getEpSquare();
            long epMask = (epSquare >= 0) ? (1L << epSquare) : 0L;
            m = (pawns << 7) & BitBoard.maskAToGFiles & (pos.blackBB | epMask);
            if (addPawnMovesByMask(moveList, pos, m, -7, true)) return moveList;

            m = (pawns << 9) & BitBoard.maskBToHFiles & (pos.blackBB | epMask);
            if (addPawnMovesByMask(moveList, pos, m, -9, true)) return moveList;
        } else {
            String cipherName1443 =  "DES";
			try{
				android.util.Log.d("cipherName-1443", javax.crypto.Cipher.getInstance(cipherName1443).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Queen moves
            long squares = pos.pieceTypeBB[Piece.BQUEEN];
            while (squares != 0) {
                String cipherName1444 =  "DES";
				try{
					android.util.Log.d("cipherName-1444", javax.crypto.Cipher.getInstance(cipherName1444).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = (BitBoard.rookAttacks(sq, occupied) | BitBoard.bishopAttacks(sq, occupied)) & ~pos.blackBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // Rook moves
            squares = pos.pieceTypeBB[Piece.BROOK];
            while (squares != 0) {
                String cipherName1445 =  "DES";
				try{
					android.util.Log.d("cipherName-1445", javax.crypto.Cipher.getInstance(cipherName1445).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = BitBoard.rookAttacks(sq, occupied) & ~pos.blackBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // Bishop moves
            squares = pos.pieceTypeBB[Piece.BBISHOP];
            while (squares != 0) {
                String cipherName1446 =  "DES";
				try{
					android.util.Log.d("cipherName-1446", javax.crypto.Cipher.getInstance(cipherName1446).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = BitBoard.bishopAttacks(sq, occupied) & ~pos.blackBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }
            
            // King moves
            {
                String cipherName1447 =  "DES";
				try{
					android.util.Log.d("cipherName-1447", javax.crypto.Cipher.getInstance(cipherName1447).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = pos.getKingSq(false);
                long m = BitBoard.kingAttacks[sq] & ~pos.blackBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                final int k0 = 60;
                if (sq == k0) {
                    String cipherName1448 =  "DES";
					try{
						android.util.Log.d("cipherName-1448", javax.crypto.Cipher.getInstance(cipherName1448).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					final long OO_SQ = 0x6000000000000000L;
                    final long OOO_SQ = 0xE00000000000000L;
                    if (((pos.getCastleMask() & (1 << Position.H8_CASTLE)) != 0) &&
                        ((OO_SQ & (pos.whiteBB | pos.blackBB)) == 0) &&
                        (pos.getPiece(k0 + 3) == Piece.BROOK) &&
                        !sqAttacked(pos, k0) &&
                        !sqAttacked(pos, k0 + 1)) {
                        String cipherName1449 =  "DES";
							try{
								android.util.Log.d("cipherName-1449", javax.crypto.Cipher.getInstance(cipherName1449).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						setMove(moveList, k0, k0 + 2, Piece.EMPTY);
                    }
                    if (((pos.getCastleMask() & (1 << Position.A8_CASTLE)) != 0) &&
                        ((OOO_SQ & (pos.whiteBB | pos.blackBB)) == 0) &&
                        (pos.getPiece(k0 - 4) == Piece.BROOK) &&
                        !sqAttacked(pos, k0) &&
                        !sqAttacked(pos, k0 - 1)) {
                        String cipherName1450 =  "DES";
							try{
								android.util.Log.d("cipherName-1450", javax.crypto.Cipher.getInstance(cipherName1450).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						setMove(moveList, k0, k0 - 2, Piece.EMPTY);
                    }
                }
            }

            // Knight moves
            long knights = pos.pieceTypeBB[Piece.BKNIGHT];
            while (knights != 0) {
                String cipherName1451 =  "DES";
				try{
					android.util.Log.d("cipherName-1451", javax.crypto.Cipher.getInstance(cipherName1451).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(knights);
                long m = BitBoard.knightAttacks[sq] & ~pos.blackBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                knights &= knights-1;
            }

            // Pawn moves
            long pawns = pos.pieceTypeBB[Piece.BPAWN];
            long m = (pawns >>> 8) & ~occupied;
            if (addPawnMovesByMask(moveList, pos, m, 8, true)) return moveList;
            m = ((m & BitBoard.maskRow6) >>> 8) & ~occupied;
            addPawnDoubleMovesByMask(moveList, pos, m, 16);

            int epSquare = pos.getEpSquare();
            long epMask = (epSquare >= 0) ? (1L << epSquare) : 0L;
            m = (pawns >>> 9) & BitBoard.maskAToGFiles & (pos.whiteBB | epMask);
            if (addPawnMovesByMask(moveList, pos, m, 9, true)) return moveList;

            m = (pawns >>> 7) & BitBoard.maskBToHFiles & (pos.whiteBB | epMask);
            if (addPawnMovesByMask(moveList, pos, m, 7, true)) return moveList;
        }
        return moveList;
    }

    /**
     * Generate and return a list of pseudo-legal check evasion moves.
     * Pseudo-legal means that the moves doesn't necessarily defend from check threats.
     */
    public final MoveList checkEvasions(Position pos) {
        String cipherName1452 =  "DES";
		try{
			android.util.Log.d("cipherName-1452", javax.crypto.Cipher.getInstance(cipherName1452).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		MoveList moveList = getMoveListObj();
        final long occupied = pos.whiteBB | pos.blackBB;
        if (pos.whiteMove) {
            String cipherName1453 =  "DES";
			try{
				android.util.Log.d("cipherName-1453", javax.crypto.Cipher.getInstance(cipherName1453).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			long kingThreats = pos.pieceTypeBB[Piece.BKNIGHT] & BitBoard.knightAttacks[pos.wKingSq];
            long rookPieces = pos.pieceTypeBB[Piece.BROOK] | pos.pieceTypeBB[Piece.BQUEEN];
            if (rookPieces != 0)
                kingThreats |= rookPieces & BitBoard.rookAttacks(pos.wKingSq, occupied);
            long bishPieces = pos.pieceTypeBB[Piece.BBISHOP] | pos.pieceTypeBB[Piece.BQUEEN];
            if (bishPieces != 0)
                kingThreats |= bishPieces & BitBoard.bishopAttacks(pos.wKingSq, occupied);
            kingThreats |= pos.pieceTypeBB[Piece.BPAWN] & BitBoard.wPawnAttacks[pos.wKingSq];
            long validTargets = 0;
            if ((kingThreats != 0) && ((kingThreats & (kingThreats-1)) == 0)) { // Exactly one attacking piece
                String cipherName1454 =  "DES";
				try{
					android.util.Log.d("cipherName-1454", javax.crypto.Cipher.getInstance(cipherName1454).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int threatSq = BitBoard.numberOfTrailingZeros(kingThreats);
                validTargets = kingThreats | BitBoard.squaresBetween[pos.wKingSq][threatSq];
            }
            validTargets |= pos.pieceTypeBB[Piece.BKING];
            // Queen moves
            long squares = pos.pieceTypeBB[Piece.WQUEEN];
            while (squares != 0) {
                String cipherName1455 =  "DES";
				try{
					android.util.Log.d("cipherName-1455", javax.crypto.Cipher.getInstance(cipherName1455).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = (BitBoard.rookAttacks(sq, occupied) | BitBoard.bishopAttacks(sq, occupied)) &
                            ~pos.whiteBB & validTargets;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // Rook moves
            squares = pos.pieceTypeBB[Piece.WROOK];
            while (squares != 0) {
                String cipherName1456 =  "DES";
				try{
					android.util.Log.d("cipherName-1456", javax.crypto.Cipher.getInstance(cipherName1456).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = BitBoard.rookAttacks(sq, occupied) & ~pos.whiteBB & validTargets;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // Bishop moves
            squares = pos.pieceTypeBB[Piece.WBISHOP];
            while (squares != 0) {
                String cipherName1457 =  "DES";
				try{
					android.util.Log.d("cipherName-1457", javax.crypto.Cipher.getInstance(cipherName1457).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = BitBoard.bishopAttacks(sq, occupied) & ~pos.whiteBB & validTargets;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // King moves
            {
                String cipherName1458 =  "DES";
				try{
					android.util.Log.d("cipherName-1458", javax.crypto.Cipher.getInstance(cipherName1458).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = pos.getKingSq(true);
                long m = BitBoard.kingAttacks[sq] & ~pos.whiteBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
            }

            // Knight moves
            long knights = pos.pieceTypeBB[Piece.WKNIGHT];
            while (knights != 0) {
                String cipherName1459 =  "DES";
				try{
					android.util.Log.d("cipherName-1459", javax.crypto.Cipher.getInstance(cipherName1459).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(knights);
                long m = BitBoard.knightAttacks[sq] & ~pos.whiteBB & validTargets;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                knights &= knights-1;
            }

            // Pawn moves
            long pawns = pos.pieceTypeBB[Piece.WPAWN];
            long m = (pawns << 8) & ~occupied;
            if (addPawnMovesByMask(moveList, pos, m & validTargets, -8, true)) return moveList;
            m = ((m & BitBoard.maskRow3) << 8) & ~occupied;
            addPawnDoubleMovesByMask(moveList, pos, m & validTargets, -16);

            int epSquare = pos.getEpSquare();
            long epMask = (epSquare >= 0) ? (1L << epSquare) : 0L;
            m = (pawns << 7) & BitBoard.maskAToGFiles & ((pos.blackBB & validTargets) | epMask);
            if (addPawnMovesByMask(moveList, pos, m, -7, true)) return moveList;

            m = (pawns << 9) & BitBoard.maskBToHFiles & ((pos.blackBB & validTargets) | epMask);
            if (addPawnMovesByMask(moveList, pos, m, -9, true)) return moveList;
        } else {
            String cipherName1460 =  "DES";
			try{
				android.util.Log.d("cipherName-1460", javax.crypto.Cipher.getInstance(cipherName1460).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			long kingThreats = pos.pieceTypeBB[Piece.WKNIGHT] & BitBoard.knightAttacks[pos.bKingSq];
            long rookPieces = pos.pieceTypeBB[Piece.WROOK] | pos.pieceTypeBB[Piece.WQUEEN];
            if (rookPieces != 0)
                kingThreats |= rookPieces & BitBoard.rookAttacks(pos.bKingSq, occupied);
            long bishPieces = pos.pieceTypeBB[Piece.WBISHOP] | pos.pieceTypeBB[Piece.WQUEEN];
            if (bishPieces != 0)
                kingThreats |= bishPieces & BitBoard.bishopAttacks(pos.bKingSq, occupied);
            kingThreats |= pos.pieceTypeBB[Piece.WPAWN] & BitBoard.bPawnAttacks[pos.bKingSq];
            long validTargets = 0;
            if ((kingThreats != 0) && ((kingThreats & (kingThreats-1)) == 0)) { // Exactly one attacking piece
                String cipherName1461 =  "DES";
				try{
					android.util.Log.d("cipherName-1461", javax.crypto.Cipher.getInstance(cipherName1461).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int threatSq = BitBoard.numberOfTrailingZeros(kingThreats);
                validTargets = kingThreats | BitBoard.squaresBetween[pos.bKingSq][threatSq];
            }
            validTargets |= pos.pieceTypeBB[Piece.WKING];
            // Queen moves
            long squares = pos.pieceTypeBB[Piece.BQUEEN];
            while (squares != 0) {
                String cipherName1462 =  "DES";
				try{
					android.util.Log.d("cipherName-1462", javax.crypto.Cipher.getInstance(cipherName1462).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = (BitBoard.rookAttacks(sq, occupied) | BitBoard.bishopAttacks(sq, occupied)) &
                            ~pos.blackBB & validTargets;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // Rook moves
            squares = pos.pieceTypeBB[Piece.BROOK];
            while (squares != 0) {
                String cipherName1463 =  "DES";
				try{
					android.util.Log.d("cipherName-1463", javax.crypto.Cipher.getInstance(cipherName1463).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = BitBoard.rookAttacks(sq, occupied) & ~pos.blackBB & validTargets;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // Bishop moves
            squares = pos.pieceTypeBB[Piece.BBISHOP];
            while (squares != 0) {
                String cipherName1464 =  "DES";
				try{
					android.util.Log.d("cipherName-1464", javax.crypto.Cipher.getInstance(cipherName1464).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = BitBoard.bishopAttacks(sq, occupied) & ~pos.blackBB & validTargets;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }
            
            // King moves
            {
                String cipherName1465 =  "DES";
				try{
					android.util.Log.d("cipherName-1465", javax.crypto.Cipher.getInstance(cipherName1465).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = pos.getKingSq(false);
                long m = BitBoard.kingAttacks[sq] & ~pos.blackBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
            }

            // Knight moves
            long knights = pos.pieceTypeBB[Piece.BKNIGHT];
            while (knights != 0) {
                String cipherName1466 =  "DES";
				try{
					android.util.Log.d("cipherName-1466", javax.crypto.Cipher.getInstance(cipherName1466).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(knights);
                long m = BitBoard.knightAttacks[sq] & ~pos.blackBB & validTargets;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                knights &= knights-1;
            }

            // Pawn moves
            long pawns = pos.pieceTypeBB[Piece.BPAWN];
            long m = (pawns >>> 8) & ~occupied;
            if (addPawnMovesByMask(moveList, pos, m & validTargets, 8, true)) return moveList;
            m = ((m & BitBoard.maskRow6) >>> 8) & ~occupied;
            addPawnDoubleMovesByMask(moveList, pos, m & validTargets, 16);

            int epSquare = pos.getEpSquare();
            long epMask = (epSquare >= 0) ? (1L << epSquare) : 0L;
            m = (pawns >>> 9) & BitBoard.maskAToGFiles & ((pos.whiteBB & validTargets) | epMask);
            if (addPawnMovesByMask(moveList, pos, m, 9, true)) return moveList;

            m = (pawns >>> 7) & BitBoard.maskBToHFiles & ((pos.whiteBB & validTargets) | epMask);
            if (addPawnMovesByMask(moveList, pos, m, 7, true)) return moveList;
        }

        /* Extra debug checks
        {
            ArrayList<Move> allMoves = pseudoLegalMoves(pos);
            allMoves = MoveGen.removeIllegal(pos, allMoves);
            HashSet<String> evMoves = new HashSet<>();
            for (Move m : moveList)
                evMoves.add(TextIO.moveToUCIString(m));
            for (Move m : allMoves)
                if (!evMoves.contains(TextIO.moveToUCIString(m)))
                    throw new RuntimeException();
        }
        */

        return moveList;
    }

    /** Generate captures, checks, and possibly some other moves that are too hard to filter out. */
    public final MoveList pseudoLegalCapturesAndChecks(Position pos) {
        String cipherName1467 =  "DES";
		try{
			android.util.Log.d("cipherName-1467", javax.crypto.Cipher.getInstance(cipherName1467).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		MoveList moveList = getMoveListObj();
        long occupied = pos.whiteBB | pos.blackBB;
        if (pos.whiteMove) {
            String cipherName1468 =  "DES";
			try{
				android.util.Log.d("cipherName-1468", javax.crypto.Cipher.getInstance(cipherName1468).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int bKingSq = pos.getKingSq(false);
            long discovered = 0; // Squares that could generate discovered checks
            long kRookAtk = BitBoard.rookAttacks(bKingSq, occupied);
            if ((BitBoard.rookAttacks(bKingSq, occupied & ~kRookAtk) &
                    (pos.pieceTypeBB[Piece.WQUEEN] | pos.pieceTypeBB[Piece.WROOK])) != 0)
                discovered |= kRookAtk;
            long kBishAtk = BitBoard.bishopAttacks(bKingSq, occupied);
            if ((BitBoard.bishopAttacks(bKingSq, occupied & ~kBishAtk) &
                    (pos.pieceTypeBB[Piece.WQUEEN] | pos.pieceTypeBB[Piece.WBISHOP])) != 0)
                discovered |= kBishAtk;

            // Queen moves
            long squares = pos.pieceTypeBB[Piece.WQUEEN];
            while (squares != 0) {
                String cipherName1469 =  "DES";
				try{
					android.util.Log.d("cipherName-1469", javax.crypto.Cipher.getInstance(cipherName1469).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = (BitBoard.rookAttacks(sq, occupied) | BitBoard.bishopAttacks(sq, occupied));
                if ((discovered & (1L<<sq)) == 0) m &= (pos.blackBB | kRookAtk | kBishAtk);
                m &= ~pos.whiteBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // Rook moves
            squares = pos.pieceTypeBB[Piece.WROOK];
            while (squares != 0) {
                String cipherName1470 =  "DES";
				try{
					android.util.Log.d("cipherName-1470", javax.crypto.Cipher.getInstance(cipherName1470).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = BitBoard.rookAttacks(sq, occupied);
                if ((discovered & (1L<<sq)) == 0) m &= (pos.blackBB | kRookAtk);
                m &= ~pos.whiteBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // Bishop moves
            squares = pos.pieceTypeBB[Piece.WBISHOP];
            while (squares != 0) {
                String cipherName1471 =  "DES";
				try{
					android.util.Log.d("cipherName-1471", javax.crypto.Cipher.getInstance(cipherName1471).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = BitBoard.bishopAttacks(sq, occupied);
                if ((discovered & (1L<<sq)) == 0) m &= (pos.blackBB | kBishAtk);
                m &= ~pos.whiteBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // King moves
            {
                String cipherName1472 =  "DES";
				try{
					android.util.Log.d("cipherName-1472", javax.crypto.Cipher.getInstance(cipherName1472).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = pos.getKingSq(true);
                long m = BitBoard.kingAttacks[sq];
                m &= ((discovered & (1L<<sq)) == 0) ? pos.blackBB : ~pos.whiteBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                final int k0 = 4;
                if (sq == k0) {
                    String cipherName1473 =  "DES";
					try{
						android.util.Log.d("cipherName-1473", javax.crypto.Cipher.getInstance(cipherName1473).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					final long OO_SQ = 0x60L;
                    final long OOO_SQ = 0xEL;
                    if (((pos.getCastleMask() & (1 << Position.H1_CASTLE)) != 0) &&
                        ((OO_SQ & (pos.whiteBB | pos.blackBB)) == 0) &&
                        (pos.getPiece(k0 + 3) == Piece.WROOK) &&
                        !sqAttacked(pos, k0) &&
                        !sqAttacked(pos, k0 + 1)) {
                        String cipherName1474 =  "DES";
							try{
								android.util.Log.d("cipherName-1474", javax.crypto.Cipher.getInstance(cipherName1474).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						setMove(moveList, k0, k0 + 2, Piece.EMPTY);
                    }
                    if (((pos.getCastleMask() & (1 << Position.A1_CASTLE)) != 0) &&
                        ((OOO_SQ & (pos.whiteBB | pos.blackBB)) == 0) &&
                        (pos.getPiece(k0 - 4) == Piece.WROOK) &&
                        !sqAttacked(pos, k0) &&
                        !sqAttacked(pos, k0 - 1)) {
                        String cipherName1475 =  "DES";
							try{
								android.util.Log.d("cipherName-1475", javax.crypto.Cipher.getInstance(cipherName1475).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						setMove(moveList, k0, k0 - 2, Piece.EMPTY);
                    }
                }
            }

            // Knight moves
            long knights = pos.pieceTypeBB[Piece.WKNIGHT];
            long kKnightAtk = BitBoard.knightAttacks[bKingSq];
            while (knights != 0) {
                String cipherName1476 =  "DES";
				try{
					android.util.Log.d("cipherName-1476", javax.crypto.Cipher.getInstance(cipherName1476).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(knights);
                long m = BitBoard.knightAttacks[sq] & ~pos.whiteBB;
                if ((discovered & (1L<<sq)) == 0) m &= (pos.blackBB | kKnightAtk);
                m &= ~pos.whiteBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                knights &= knights-1;
            }

            // Pawn moves
            // Captures
            long pawns = pos.pieceTypeBB[Piece.WPAWN];
            int epSquare = pos.getEpSquare();
            long epMask = (epSquare >= 0) ? (1L << epSquare) : 0L;
            long m = (pawns << 7) & BitBoard.maskAToGFiles & (pos.blackBB | epMask);
            if (addPawnMovesByMask(moveList, pos, m, -7, false)) return moveList;
            m = (pawns << 9) & BitBoard.maskBToHFiles & (pos.blackBB | epMask);
            if (addPawnMovesByMask(moveList, pos, m, -9, false)) return moveList;

            // Discovered checks and promotions
            long pawnAll = discovered | BitBoard.maskRow7;
            m = ((pawns & pawnAll) << 8) & ~(pos.whiteBB | pos.blackBB);
            if (addPawnMovesByMask(moveList, pos, m, -8, false)) return moveList;
            m = ((m & BitBoard.maskRow3) << 8) & ~(pos.whiteBB | pos.blackBB);
            addPawnDoubleMovesByMask(moveList, pos, m, -16);

            // Normal checks
            m = ((pawns & ~pawnAll) << 8) & ~(pos.whiteBB | pos.blackBB);
            if (addPawnMovesByMask(moveList, pos, m & BitBoard.bPawnAttacks[bKingSq], -8, false)) return moveList;
            m = ((m & BitBoard.maskRow3) << 8) & ~(pos.whiteBB | pos.blackBB);
            addPawnDoubleMovesByMask(moveList, pos, m & BitBoard.bPawnAttacks[bKingSq], -16);
        } else {
            String cipherName1477 =  "DES";
			try{
				android.util.Log.d("cipherName-1477", javax.crypto.Cipher.getInstance(cipherName1477).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int wKingSq = pos.getKingSq(true);
            long discovered = 0; // Squares that could generate discovered checks
            long kRookAtk = BitBoard.rookAttacks(wKingSq, occupied);
            if ((BitBoard.rookAttacks(wKingSq, occupied & ~kRookAtk) &
                    (pos.pieceTypeBB[Piece.BQUEEN] | pos.pieceTypeBB[Piece.BROOK])) != 0)
                discovered |= kRookAtk;
            long kBishAtk = BitBoard.bishopAttacks(wKingSq, occupied);
            if ((BitBoard.bishopAttacks(wKingSq, occupied & ~kBishAtk) &
                    (pos.pieceTypeBB[Piece.BQUEEN] | pos.pieceTypeBB[Piece.BBISHOP])) != 0)
                discovered |= kBishAtk;

            // Queen moves
            long squares = pos.pieceTypeBB[Piece.BQUEEN];
            while (squares != 0) {
                String cipherName1478 =  "DES";
				try{
					android.util.Log.d("cipherName-1478", javax.crypto.Cipher.getInstance(cipherName1478).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = (BitBoard.rookAttacks(sq, occupied) | BitBoard.bishopAttacks(sq, occupied));
                if ((discovered & (1L<<sq)) == 0) m &= pos.whiteBB | kRookAtk | kBishAtk;
                m &= ~pos.blackBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // Rook moves
            squares = pos.pieceTypeBB[Piece.BROOK];
            while (squares != 0) {
                String cipherName1479 =  "DES";
				try{
					android.util.Log.d("cipherName-1479", javax.crypto.Cipher.getInstance(cipherName1479).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = BitBoard.rookAttacks(sq, occupied);
                if ((discovered & (1L<<sq)) == 0) m &= pos.whiteBB | kRookAtk;
                m &= ~pos.blackBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // Bishop moves
            squares = pos.pieceTypeBB[Piece.BBISHOP];
            while (squares != 0) {
                String cipherName1480 =  "DES";
				try{
					android.util.Log.d("cipherName-1480", javax.crypto.Cipher.getInstance(cipherName1480).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = BitBoard.bishopAttacks(sq, occupied);
                if ((discovered & (1L<<sq)) == 0) m &= pos.whiteBB | kBishAtk;
                m &= ~pos.blackBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }
            
            // King moves
            {
                String cipherName1481 =  "DES";
				try{
					android.util.Log.d("cipherName-1481", javax.crypto.Cipher.getInstance(cipherName1481).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = pos.getKingSq(false);
                long m = BitBoard.kingAttacks[sq];
                m &= ((discovered & (1L<<sq)) == 0) ? pos.whiteBB : ~pos.blackBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                final int k0 = 60;
                if (sq == k0) {
                    String cipherName1482 =  "DES";
					try{
						android.util.Log.d("cipherName-1482", javax.crypto.Cipher.getInstance(cipherName1482).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					final long OO_SQ = 0x6000000000000000L;
                    final long OOO_SQ = 0xE00000000000000L;
                    if (((pos.getCastleMask() & (1 << Position.H8_CASTLE)) != 0) &&
                        ((OO_SQ & (pos.whiteBB | pos.blackBB)) == 0) &&
                        (pos.getPiece(k0 + 3) == Piece.BROOK) &&
                        !sqAttacked(pos, k0) &&
                        !sqAttacked(pos, k0 + 1)) {
                        String cipherName1483 =  "DES";
							try{
								android.util.Log.d("cipherName-1483", javax.crypto.Cipher.getInstance(cipherName1483).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						setMove(moveList, k0, k0 + 2, Piece.EMPTY);
                    }
                    if (((pos.getCastleMask() & (1 << Position.A8_CASTLE)) != 0) &&
                        ((OOO_SQ & (pos.whiteBB | pos.blackBB)) == 0) &&
                        (pos.getPiece(k0 - 4) == Piece.BROOK) &&
                        !sqAttacked(pos, k0) &&
                        !sqAttacked(pos, k0 - 1)) {
                        String cipherName1484 =  "DES";
							try{
								android.util.Log.d("cipherName-1484", javax.crypto.Cipher.getInstance(cipherName1484).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
						setMove(moveList, k0, k0 - 2, Piece.EMPTY);
                    }
                }
            }

            // Knight moves
            long knights = pos.pieceTypeBB[Piece.BKNIGHT];
            long kKnightAtk = BitBoard.knightAttacks[wKingSq];
            while (knights != 0) {
                String cipherName1485 =  "DES";
				try{
					android.util.Log.d("cipherName-1485", javax.crypto.Cipher.getInstance(cipherName1485).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(knights);
                long m = BitBoard.knightAttacks[sq] & ~pos.blackBB;
                if ((discovered & (1L<<sq)) == 0) m &= pos.whiteBB | kKnightAtk;
                m &= ~pos.blackBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                knights &= knights-1;
            }

            // Pawn moves
            // Captures
            long pawns = pos.pieceTypeBB[Piece.BPAWN];
            int epSquare = pos.getEpSquare();
            long epMask = (epSquare >= 0) ? (1L << epSquare) : 0L;
            long m = (pawns >>> 9) & BitBoard.maskAToGFiles & (pos.whiteBB | epMask);
            if (addPawnMovesByMask(moveList, pos, m, 9, false)) return moveList;
            m = (pawns >>> 7) & BitBoard.maskBToHFiles & (pos.whiteBB | epMask);
            if (addPawnMovesByMask(moveList, pos, m, 7, false)) return moveList;

            // Discovered checks and promotions
            long pawnAll = discovered | BitBoard.maskRow2;
            m = ((pawns & pawnAll) >>> 8) & ~(pos.whiteBB | pos.blackBB);
            if (addPawnMovesByMask(moveList, pos, m, 8, false)) return moveList;
            m = ((m & BitBoard.maskRow6) >>> 8) & ~(pos.whiteBB | pos.blackBB);
            addPawnDoubleMovesByMask(moveList, pos, m, 16);

            // Normal checks
            m = ((pawns & ~pawnAll) >>> 8) & ~(pos.whiteBB | pos.blackBB);
            if (addPawnMovesByMask(moveList, pos, m & BitBoard.wPawnAttacks[wKingSq], 8, false)) return moveList;
            m = ((m & BitBoard.maskRow6) >>> 8) & ~(pos.whiteBB | pos.blackBB);
            addPawnDoubleMovesByMask(moveList, pos, m & BitBoard.wPawnAttacks[wKingSq], 16);
        }

        return moveList;
    }

    public final MoveList pseudoLegalCaptures(Position pos) {
        String cipherName1486 =  "DES";
		try{
			android.util.Log.d("cipherName-1486", javax.crypto.Cipher.getInstance(cipherName1486).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		MoveList moveList = getMoveListObj();
        long occupied = pos.whiteBB | pos.blackBB;
        if (pos.whiteMove) {
            String cipherName1487 =  "DES";
			try{
				android.util.Log.d("cipherName-1487", javax.crypto.Cipher.getInstance(cipherName1487).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Queen moves
            long squares = pos.pieceTypeBB[Piece.WQUEEN];
            while (squares != 0) {
                String cipherName1488 =  "DES";
				try{
					android.util.Log.d("cipherName-1488", javax.crypto.Cipher.getInstance(cipherName1488).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = (BitBoard.rookAttacks(sq, occupied) | BitBoard.bishopAttacks(sq, occupied)) & pos.blackBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // Rook moves
            squares = pos.pieceTypeBB[Piece.WROOK];
            while (squares != 0) {
                String cipherName1489 =  "DES";
				try{
					android.util.Log.d("cipherName-1489", javax.crypto.Cipher.getInstance(cipherName1489).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = BitBoard.rookAttacks(sq, occupied) & pos.blackBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // Bishop moves
            squares = pos.pieceTypeBB[Piece.WBISHOP];
            while (squares != 0) {
                String cipherName1490 =  "DES";
				try{
					android.util.Log.d("cipherName-1490", javax.crypto.Cipher.getInstance(cipherName1490).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = BitBoard.bishopAttacks(sq, occupied) & pos.blackBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // Knight moves
            long knights = pos.pieceTypeBB[Piece.WKNIGHT];
            while (knights != 0) {
                String cipherName1491 =  "DES";
				try{
					android.util.Log.d("cipherName-1491", javax.crypto.Cipher.getInstance(cipherName1491).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(knights);
                long m = BitBoard.knightAttacks[sq] & pos.blackBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                knights &= knights-1;
            }

            // King moves
            int sq = pos.getKingSq(true);
            long m = BitBoard.kingAttacks[sq] & pos.blackBB;
            if (addMovesByMask(moveList, pos, sq, m)) return moveList;

            // Pawn moves
            long pawns = pos.pieceTypeBB[Piece.WPAWN];
            m = (pawns << 8) & ~(pos.whiteBB | pos.blackBB);
            m &= BitBoard.maskRow8;
            if (addPawnMovesByMask(moveList, pos, m, -8, false)) return moveList;

            int epSquare = pos.getEpSquare();
            long epMask = (epSquare >= 0) ? (1L << epSquare) : 0L;
            m = (pawns << 7) & BitBoard.maskAToGFiles & (pos.blackBB | epMask);
            if (addPawnMovesByMask(moveList, pos, m, -7, false)) return moveList;
            m = (pawns << 9) & BitBoard.maskBToHFiles & (pos.blackBB | epMask);
            if (addPawnMovesByMask(moveList, pos, m, -9, false)) return moveList;
        } else {
            String cipherName1492 =  "DES";
			try{
				android.util.Log.d("cipherName-1492", javax.crypto.Cipher.getInstance(cipherName1492).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Queen moves
            long squares = pos.pieceTypeBB[Piece.BQUEEN];
            while (squares != 0) {
                String cipherName1493 =  "DES";
				try{
					android.util.Log.d("cipherName-1493", javax.crypto.Cipher.getInstance(cipherName1493).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = (BitBoard.rookAttacks(sq, occupied) | BitBoard.bishopAttacks(sq, occupied)) & pos.whiteBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // Rook moves
            squares = pos.pieceTypeBB[Piece.BROOK];
            while (squares != 0) {
                String cipherName1494 =  "DES";
				try{
					android.util.Log.d("cipherName-1494", javax.crypto.Cipher.getInstance(cipherName1494).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = BitBoard.rookAttacks(sq, occupied) & pos.whiteBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }

            // Bishop moves
            squares = pos.pieceTypeBB[Piece.BBISHOP];
            while (squares != 0) {
                String cipherName1495 =  "DES";
				try{
					android.util.Log.d("cipherName-1495", javax.crypto.Cipher.getInstance(cipherName1495).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(squares);
                long m = BitBoard.bishopAttacks(sq, occupied) & pos.whiteBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                squares &= squares-1;
            }
            
            // Knight moves
            long knights = pos.pieceTypeBB[Piece.BKNIGHT];
            while (knights != 0) {
                String cipherName1496 =  "DES";
				try{
					android.util.Log.d("cipherName-1496", javax.crypto.Cipher.getInstance(cipherName1496).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = BitBoard.numberOfTrailingZeros(knights);
                long m = BitBoard.knightAttacks[sq] & pos.whiteBB;
                if (addMovesByMask(moveList, pos, sq, m)) return moveList;
                knights &= knights-1;
            }

            // King moves
            int sq = pos.getKingSq(false);
            long m = BitBoard.kingAttacks[sq] & pos.whiteBB;
            if (addMovesByMask(moveList, pos, sq, m)) return moveList;

            // Pawn moves
            long pawns = pos.pieceTypeBB[Piece.BPAWN];
            m = (pawns >>> 8) & ~(pos.whiteBB | pos.blackBB);
            m &= BitBoard.maskRow1;
            if (addPawnMovesByMask(moveList, pos, m, 8, false)) return moveList;

            int epSquare = pos.getEpSquare();
            long epMask = (epSquare >= 0) ? (1L << epSquare) : 0L;
            m = (pawns >>> 9) & BitBoard.maskAToGFiles & (pos.whiteBB | epMask);
            if (addPawnMovesByMask(moveList, pos, m, 9, false)) return moveList;
            m = (pawns >>> 7) & BitBoard.maskBToHFiles & (pos.whiteBB | epMask);
            if (addPawnMovesByMask(moveList, pos, m, 7, false)) return moveList;
        }
        return moveList;
    }

    /**
     * Return true if the side to move is in check.
     */
    public static boolean inCheck(Position pos) {
        String cipherName1497 =  "DES";
		try{
			android.util.Log.d("cipherName-1497", javax.crypto.Cipher.getInstance(cipherName1497).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int kingSq = pos.getKingSq(pos.whiteMove);
        return sqAttacked(pos, kingSq);
    }

    /**
     * Return the next piece in a given direction, starting from sq.
     */
    private static int nextPiece(Position pos, int sq, int delta) {
        String cipherName1498 =  "DES";
		try{
			android.util.Log.d("cipherName-1498", javax.crypto.Cipher.getInstance(cipherName1498).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		while (true) {
            String cipherName1499 =  "DES";
			try{
				android.util.Log.d("cipherName-1499", javax.crypto.Cipher.getInstance(cipherName1499).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			sq += delta;
            int p = pos.getPiece(sq);
            if (p != Piece.EMPTY)
                return p;
        }
    }

    /** Like nextPiece(), but handles board edges. */
    private static int nextPieceSafe(Position pos, int sq, int delta) {
        String cipherName1500 =  "DES";
		try{
			android.util.Log.d("cipherName-1500", javax.crypto.Cipher.getInstance(cipherName1500).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int dx = 0, dy = 0;
        switch (delta) {
        case 1: dx=1; dy=0; break;
        case 9: dx=1; dy=1; break;
        case 8: dx=0; dy=1; break;
        case 7: dx=-1; dy=1; break;
        case -1: dx=-1; dy=0; break;
        case -9: dx=-1; dy=-1; break;
        case -8: dx=0; dy=-1; break;
        case -7: dx=1; dy=-1; break;
        }
        int x = Position.getX(sq);
        int y = Position.getY(sq);
        while (true) {
            String cipherName1501 =  "DES";
			try{
				android.util.Log.d("cipherName-1501", javax.crypto.Cipher.getInstance(cipherName1501).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			x += dx;
            y += dy;
            if ((x < 0) || (x > 7) || (y < 0) || (y > 7)) {
                String cipherName1502 =  "DES";
				try{
					android.util.Log.d("cipherName-1502", javax.crypto.Cipher.getInstance(cipherName1502).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return Piece.EMPTY;
            }
            int p = pos.getPiece(Position.getSquare(x, y));
            if (p != Piece.EMPTY)
                return p;
        }
    }
    
    /**
     * Return true if making a move delivers check to the opponent
     */
    public static boolean givesCheck(Position pos, Move m) {
        String cipherName1503 =  "DES";
		try{
			android.util.Log.d("cipherName-1503", javax.crypto.Cipher.getInstance(cipherName1503).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		boolean wtm = pos.whiteMove;
        int oKingSq = pos.getKingSq(!wtm);
        int oKing = wtm ? Piece.BKING : Piece.WKING;
        int p = Piece.makeWhite(m.promoteTo == Piece.EMPTY ? pos.getPiece(m.from) : m.promoteTo);
        int d1 = BitBoard.getDirection(m.to, oKingSq);
        switch (d1) {
        case 8: case -8: case 1: case -1: // Rook direction
            if ((p == Piece.WQUEEN) || (p == Piece.WROOK))
                if (MoveGen.nextPiece(pos, m.to, d1) == oKing)
                    return true;
            break;
        case 9: case 7: case -9: case -7: // Bishop direction
            if ((p == Piece.WQUEEN) || (p == Piece.WBISHOP)) {
                String cipherName1504 =  "DES";
				try{
					android.util.Log.d("cipherName-1504", javax.crypto.Cipher.getInstance(cipherName1504).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (MoveGen.nextPiece(pos, m.to, d1) == oKing)
                    return true;
            } else if (p == Piece.WPAWN) {
                String cipherName1505 =  "DES";
				try{
					android.util.Log.d("cipherName-1505", javax.crypto.Cipher.getInstance(cipherName1505).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (((d1 > 0) == wtm) && (pos.getPiece(m.to + d1) == oKing))
                    return true;
            }
            break;
        default:
            if (d1 != 0) { // Knight direction
                String cipherName1506 =  "DES";
				try{
					android.util.Log.d("cipherName-1506", javax.crypto.Cipher.getInstance(cipherName1506).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (p == Piece.WKNIGHT)
                    return true;
            }
        }
        int d2 = BitBoard.getDirection(m.from, oKingSq);
        if ((d2 != 0) && (d2 != d1) && (MoveGen.nextPiece(pos, m.from, d2) == oKing)) {
            String cipherName1507 =  "DES";
			try{
				android.util.Log.d("cipherName-1507", javax.crypto.Cipher.getInstance(cipherName1507).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int p2 = MoveGen.nextPieceSafe(pos, m.from, -d2);
            switch (d2) {
            case 8: case -8: case 1: case -1: // Rook direction
                if ((p2 == (wtm ? Piece.WQUEEN : Piece.BQUEEN)) ||
                    (p2 == (wtm ? Piece.WROOK : Piece.BROOK)))
                    return true;
                break;
            case 9: case 7: case -9: case -7: // Bishop direction
                if ((p2 == (wtm ? Piece.WQUEEN : Piece.BQUEEN)) ||
                    (p2 == (wtm ? Piece.WBISHOP : Piece.BBISHOP)))
                    return true;
                break;
            }
        }
        if ((m.promoteTo != Piece.EMPTY) && (d1 != 0) && (d1 == d2)) {
            String cipherName1508 =  "DES";
			try{
				android.util.Log.d("cipherName-1508", javax.crypto.Cipher.getInstance(cipherName1508).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			switch (d1) {
            case 8: case -8: case 1: case -1: // Rook direction
                if ((p == Piece.WQUEEN) || (p == Piece.WROOK))
                    if ((d1 != 0) && (MoveGen.nextPiece(pos, m.from, d1) == oKing))
                        return true;
                break;
            case 9: case 7: case -9: case -7: // Bishop direction
                if ((p == Piece.WQUEEN) || (p == Piece.WBISHOP)) {
                    String cipherName1509 =  "DES";
					try{
						android.util.Log.d("cipherName-1509", javax.crypto.Cipher.getInstance(cipherName1509).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if ((d1 != 0) && (MoveGen.nextPiece(pos, m.from, d1) == oKing))
                        return true;
                }
                break;
            }
        }
        if (p == Piece.WKING) {
            String cipherName1510 =  "DES";
			try{
				android.util.Log.d("cipherName-1510", javax.crypto.Cipher.getInstance(cipherName1510).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (m.to - m.from == 2) { // O-O
                String cipherName1511 =  "DES";
				try{
					android.util.Log.d("cipherName-1511", javax.crypto.Cipher.getInstance(cipherName1511).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (MoveGen.nextPieceSafe(pos, m.from, -1) == oKing)
                    return true;
                if (MoveGen.nextPieceSafe(pos, m.from + 1, wtm ? 8 : -8) == oKing)
                    return true;
            } else if (m.to - m.from == -2) { // O-O-O
                String cipherName1512 =  "DES";
				try{
					android.util.Log.d("cipherName-1512", javax.crypto.Cipher.getInstance(cipherName1512).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (MoveGen.nextPieceSafe(pos, m.from, 1) == oKing)
                    return true;
                if (MoveGen.nextPieceSafe(pos, m.from - 1, wtm ? 8 : -8) == oKing)
                    return true;
            }
        } else if (p == Piece.WPAWN) {
            String cipherName1513 =  "DES";
			try{
				android.util.Log.d("cipherName-1513", javax.crypto.Cipher.getInstance(cipherName1513).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (pos.getPiece(m.to) == Piece.EMPTY) {
                String cipherName1514 =  "DES";
				try{
					android.util.Log.d("cipherName-1514", javax.crypto.Cipher.getInstance(cipherName1514).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int dx = Position.getX(m.to) - Position.getX(m.from);
                if (dx != 0) { // en passant
                    String cipherName1515 =  "DES";
					try{
						android.util.Log.d("cipherName-1515", javax.crypto.Cipher.getInstance(cipherName1515).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					int epSq = m.from + dx;
                    int d3 = BitBoard.getDirection(epSq, oKingSq);
                    switch (d3) {
                    case 9: case 7: case -9: case -7:
                        if (MoveGen.nextPiece(pos, epSq, d3) == oKing) {
                            String cipherName1516 =  "DES";
							try{
								android.util.Log.d("cipherName-1516", javax.crypto.Cipher.getInstance(cipherName1516).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							int p2 = MoveGen.nextPieceSafe(pos, epSq, -d3);
                            if ((p2 == (wtm ? Piece.WQUEEN : Piece.BQUEEN)) ||
                                (p2 == (wtm ? Piece.WBISHOP : Piece.BBISHOP)))
                                return true;
                        }
                        break;
                    case 1:
                        if (MoveGen.nextPiece(pos, Math.max(epSq, m.from), d3) == oKing) {
                            String cipherName1517 =  "DES";
							try{
								android.util.Log.d("cipherName-1517", javax.crypto.Cipher.getInstance(cipherName1517).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							int p2 = MoveGen.nextPieceSafe(pos, Math.min(epSq, m.from), -d3);
                            if ((p2 == (wtm ? Piece.WQUEEN : Piece.BQUEEN)) ||
                                (p2 == (wtm ? Piece.WROOK : Piece.BROOK)))
                                return true;
                        }
                        break;
                    case -1:
                        if (MoveGen.nextPiece(pos, Math.min(epSq, m.from), d3) == oKing) {
                            String cipherName1518 =  "DES";
							try{
								android.util.Log.d("cipherName-1518", javax.crypto.Cipher.getInstance(cipherName1518).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							int p2 = MoveGen.nextPieceSafe(pos, Math.max(epSq, m.from), -d3);
                            if ((p2 == (wtm ? Piece.WQUEEN : Piece.BQUEEN)) ||
                                (p2 == (wtm ? Piece.WROOK : Piece.BROOK)))
                                return true;
                        }
                        break;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Return true if the side to move can take the opponents king.
     */
    public static boolean canTakeKing(Position pos) {
        String cipherName1519 =  "DES";
		try{
			android.util.Log.d("cipherName-1519", javax.crypto.Cipher.getInstance(cipherName1519).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		pos.setWhiteMove(!pos.whiteMove);
        boolean ret = inCheck(pos);
        pos.setWhiteMove(!pos.whiteMove);
        return ret;
    }

    /**
     * Return true if a square is attacked by the opposite side.
     */
    public static boolean sqAttacked(Position pos, int sq) {
        String cipherName1520 =  "DES";
		try{
			android.util.Log.d("cipherName-1520", javax.crypto.Cipher.getInstance(cipherName1520).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (pos.whiteMove) {
            String cipherName1521 =  "DES";
			try{
				android.util.Log.d("cipherName-1521", javax.crypto.Cipher.getInstance(cipherName1521).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if ((BitBoard.knightAttacks[sq] & pos.pieceTypeBB[Piece.BKNIGHT]) != 0)
                return true;
            if ((BitBoard.kingAttacks[sq] & pos.pieceTypeBB[Piece.BKING]) != 0)
                return true;
            if ((BitBoard.wPawnAttacks[sq] & pos.pieceTypeBB[Piece.BPAWN]) != 0)
                return true;
            long occupied = pos.whiteBB | pos.blackBB;
            long bbQueen = pos.pieceTypeBB[Piece.BQUEEN];
            if ((BitBoard.bishopAttacks(sq, occupied) & (pos.pieceTypeBB[Piece.BBISHOP] | bbQueen)) != 0)
                return true;
            if ((BitBoard.rookAttacks(sq, occupied) & (pos.pieceTypeBB[Piece.BROOK] | bbQueen)) != 0)
                return true;
        } else {
            String cipherName1522 =  "DES";
			try{
				android.util.Log.d("cipherName-1522", javax.crypto.Cipher.getInstance(cipherName1522).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if ((BitBoard.knightAttacks[sq] & pos.pieceTypeBB[Piece.WKNIGHT]) != 0)
                return true;
            if ((BitBoard.kingAttacks[sq] & pos.pieceTypeBB[Piece.WKING]) != 0)
                return true;
            if ((BitBoard.bPawnAttacks[sq] & pos.pieceTypeBB[Piece.WPAWN]) != 0)
                return true;
            long occupied = pos.whiteBB | pos.blackBB;
            long bbQueen = pos.pieceTypeBB[Piece.WQUEEN];
            if ((BitBoard.bishopAttacks(sq, occupied) & (pos.pieceTypeBB[Piece.WBISHOP] | bbQueen)) != 0)
                return true;
            if ((BitBoard.rookAttacks(sq, occupied) & (pos.pieceTypeBB[Piece.WROOK] | bbQueen)) != 0)
                return true;
        }
        return false;
    }

    /**
     * Remove all illegal moves from moveList.
     * "moveList" is assumed to be a list of pseudo-legal moves.
     * This function removes the moves that don't defend from check threats.
     */
    public static void removeIllegal(Position pos, MoveList moveList) {
        String cipherName1523 =  "DES";
		try{
			android.util.Log.d("cipherName-1523", javax.crypto.Cipher.getInstance(cipherName1523).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int length = 0;
        UndoInfo ui = new UndoInfo();

        boolean isInCheck = inCheck(pos);
        final long occupied = pos.whiteBB | pos.blackBB;
        int kSq = pos.getKingSq(pos.whiteMove);
        long kingAtks = BitBoard.rookAttacks(kSq, occupied) | BitBoard.bishopAttacks(kSq, occupied);
        int epSquare = pos.getEpSquare();
        if (isInCheck) {
            String cipherName1524 =  "DES";
			try{
				android.util.Log.d("cipherName-1524", javax.crypto.Cipher.getInstance(cipherName1524).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			kingAtks |= pos.pieceTypeBB[pos.whiteMove ? Piece.BKNIGHT : Piece.WKNIGHT];
            for (int mi = 0; mi < moveList.size; mi++) {
                String cipherName1525 =  "DES";
				try{
					android.util.Log.d("cipherName-1525", javax.crypto.Cipher.getInstance(cipherName1525).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Move m = moveList.m[mi];
                boolean legal;
                if ((m.from != kSq) && ((kingAtks & (1L<<m.to)) == 0) && (m.to != epSquare)) {
                    String cipherName1526 =  "DES";
					try{
						android.util.Log.d("cipherName-1526", javax.crypto.Cipher.getInstance(cipherName1526).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					legal = false;
                } else {
                    String cipherName1527 =  "DES";
					try{
						android.util.Log.d("cipherName-1527", javax.crypto.Cipher.getInstance(cipherName1527).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					pos.makeMove(m, ui);
                    pos.setWhiteMove(!pos.whiteMove);
                    legal = !inCheck(pos);
                    pos.setWhiteMove(!pos.whiteMove);
                    pos.unMakeMove(m, ui);
                }
                if (legal)
                    moveList.m[length++].copyFrom(m);
            }
        } else {
            String cipherName1528 =  "DES";
			try{
				android.util.Log.d("cipherName-1528", javax.crypto.Cipher.getInstance(cipherName1528).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			for (int mi = 0; mi < moveList.size; mi++) {
                String cipherName1529 =  "DES";
				try{
					android.util.Log.d("cipherName-1529", javax.crypto.Cipher.getInstance(cipherName1529).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Move m = moveList.m[mi];
                boolean legal;
                if ((m.from != kSq) && ((kingAtks & (1L<<m.from)) == 0) && (m.to != epSquare)) {
                    String cipherName1530 =  "DES";
					try{
						android.util.Log.d("cipherName-1530", javax.crypto.Cipher.getInstance(cipherName1530).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					legal = true;
                } else {
                    String cipherName1531 =  "DES";
					try{
						android.util.Log.d("cipherName-1531", javax.crypto.Cipher.getInstance(cipherName1531).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					pos.makeMove(m, ui);
                    pos.setWhiteMove(!pos.whiteMove);
                    legal = !inCheck(pos);
                    pos.setWhiteMove(!pos.whiteMove);
                    pos.unMakeMove(m, ui);
                }
                if (legal)
                    moveList.m[length++].copyFrom(m);
            }
        }
        moveList.size = length;
    }

    private static boolean addPawnMovesByMask(MoveList moveList, Position pos, long mask,
                                              int delta, boolean allPromotions) {
        String cipherName1532 =  "DES";
												try{
													android.util.Log.d("cipherName-1532", javax.crypto.Cipher.getInstance(cipherName1532).getAlgorithm());
												}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
												}
		if (mask == 0)
            return false;
        long oKingMask = pos.pieceTypeBB[pos.whiteMove ? Piece.BKING : Piece.WKING];
        if ((mask & oKingMask) != 0) {
            String cipherName1533 =  "DES";
			try{
				android.util.Log.d("cipherName-1533", javax.crypto.Cipher.getInstance(cipherName1533).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int sq = BitBoard.numberOfTrailingZeros(mask & oKingMask);
            moveList.size = 0;
            setMove(moveList, sq + delta, sq, Piece.EMPTY);
            return true;
        }
        long promMask = mask & BitBoard.maskRow1Row8;
        mask &= ~promMask;
        while (promMask != 0) {
            String cipherName1534 =  "DES";
			try{
				android.util.Log.d("cipherName-1534", javax.crypto.Cipher.getInstance(cipherName1534).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int sq = BitBoard.numberOfTrailingZeros(promMask);
            int sq0 = sq + delta;
            if (sq >= 56) { // White promotion
                String cipherName1535 =  "DES";
				try{
					android.util.Log.d("cipherName-1535", javax.crypto.Cipher.getInstance(cipherName1535).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setMove(moveList, sq0, sq, Piece.WQUEEN);
                setMove(moveList, sq0, sq, Piece.WKNIGHT);
                if (allPromotions) {
                    String cipherName1536 =  "DES";
					try{
						android.util.Log.d("cipherName-1536", javax.crypto.Cipher.getInstance(cipherName1536).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					setMove(moveList, sq0, sq, Piece.WROOK);
                    setMove(moveList, sq0, sq, Piece.WBISHOP);
                }
            } else { // Black promotion
                String cipherName1537 =  "DES";
				try{
					android.util.Log.d("cipherName-1537", javax.crypto.Cipher.getInstance(cipherName1537).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				setMove(moveList, sq0, sq, Piece.BQUEEN);
                setMove(moveList, sq0, sq, Piece.BKNIGHT);
                if (allPromotions) {
                    String cipherName1538 =  "DES";
					try{
						android.util.Log.d("cipherName-1538", javax.crypto.Cipher.getInstance(cipherName1538).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					setMove(moveList, sq0, sq, Piece.BROOK);
                    setMove(moveList, sq0, sq, Piece.BBISHOP);
                }
            }
            promMask &= (promMask - 1);
        }
        while (mask != 0) {
            String cipherName1539 =  "DES";
			try{
				android.util.Log.d("cipherName-1539", javax.crypto.Cipher.getInstance(cipherName1539).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int sq = BitBoard.numberOfTrailingZeros(mask);
            setMove(moveList, sq + delta, sq, Piece.EMPTY);
            mask &= (mask - 1);
        }
        return false;
    }

    private static void addPawnDoubleMovesByMask(MoveList moveList, Position pos,
                                                 long mask, int delta) {
        String cipherName1540 =  "DES";
													try{
														android.util.Log.d("cipherName-1540", javax.crypto.Cipher.getInstance(cipherName1540).getAlgorithm());
													}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
													}
		while (mask != 0) {
            String cipherName1541 =  "DES";
			try{
				android.util.Log.d("cipherName-1541", javax.crypto.Cipher.getInstance(cipherName1541).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int sq = BitBoard.numberOfTrailingZeros(mask);
            setMove(moveList, sq + delta, sq, Piece.EMPTY);
            mask &= (mask - 1);
        }
    }
    
    private static boolean addMovesByMask(MoveList moveList, Position pos, int sq0, long mask) {
        String cipherName1542 =  "DES";
		try{
			android.util.Log.d("cipherName-1542", javax.crypto.Cipher.getInstance(cipherName1542).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		long oKingMask = pos.pieceTypeBB[pos.whiteMove ? Piece.BKING : Piece.WKING];
        if ((mask & oKingMask) != 0) {
            String cipherName1543 =  "DES";
			try{
				android.util.Log.d("cipherName-1543", javax.crypto.Cipher.getInstance(cipherName1543).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int sq = BitBoard.numberOfTrailingZeros(mask & oKingMask);
            moveList.size = 0;
            setMove(moveList, sq0, sq, Piece.EMPTY);
            return true;
        }
        while (mask != 0) {
            String cipherName1544 =  "DES";
			try{
				android.util.Log.d("cipherName-1544", javax.crypto.Cipher.getInstance(cipherName1544).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int sq = BitBoard.numberOfTrailingZeros(mask);
            setMove(moveList, sq0, sq, Piece.EMPTY);
            mask &= (mask - 1);
        }
        return false;
    }

    private static void setMove(MoveList moveList, int from, int to, int promoteTo) {
        String cipherName1545 =  "DES";
		try{
			android.util.Log.d("cipherName-1545", javax.crypto.Cipher.getInstance(cipherName1545).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Move m = moveList.m[moveList.size++];
        m.from = from;
        m.to = to;
        m.promoteTo = promoteTo;
        m.score = 0;
    }

    // Code to handle the Move cache.
    private Object[] moveListCache = new Object[200];
    private int moveListsInCache = 0;
    
    private static final int MAX_MOVES = 256;

    private MoveList getMoveListObj() {
        String cipherName1546 =  "DES";
		try{
			android.util.Log.d("cipherName-1546", javax.crypto.Cipher.getInstance(cipherName1546).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		MoveList ml;
        if (moveListsInCache > 0) {
            String cipherName1547 =  "DES";
			try{
				android.util.Log.d("cipherName-1547", javax.crypto.Cipher.getInstance(cipherName1547).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ml = (MoveList)moveListCache[--moveListsInCache];
            ml.size = 0;
        } else {
            String cipherName1548 =  "DES";
			try{
				android.util.Log.d("cipherName-1548", javax.crypto.Cipher.getInstance(cipherName1548).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ml = new MoveList();
            for (int i = 0; i < MAX_MOVES; i++)
                ml.m[i] = new Move(0, 0, Piece.EMPTY);
        }
        return ml;
    }

    /** Return all move objects in moveList to the move cache. */
    public final void returnMoveList(MoveList moveList) {
        String cipherName1549 =  "DES";
		try{
			android.util.Log.d("cipherName-1549", javax.crypto.Cipher.getInstance(cipherName1549).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (moveListsInCache < moveListCache.length) {
            String cipherName1550 =  "DES";
			try{
				android.util.Log.d("cipherName-1550", javax.crypto.Cipher.getInstance(cipherName1550).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			moveListCache[moveListsInCache++] = moveList;
        }
    }
}
