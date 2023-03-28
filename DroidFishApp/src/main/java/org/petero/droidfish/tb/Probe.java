/*
    DroidFish - An Android chess program.
    Copyright (C) 2011-2012  Peter Österlund, peterosterlund2@gmail.com

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

package org.petero.droidfish.tb;

import android.util.Pair;

import java.util.ArrayList;

import org.petero.droidfish.gamelogic.ChessParseError;
import org.petero.droidfish.gamelogic.Move;
import org.petero.droidfish.gamelogic.MoveGen;
import org.petero.droidfish.gamelogic.Piece;
import org.petero.droidfish.gamelogic.Position;
import org.petero.droidfish.gamelogic.TextIO;
import org.petero.droidfish.gamelogic.UndoInfo;

/** Interface between Position class and GTB/RTB probing code. */
public class Probe {
    private final GtbProbe gtb;
    private final RtbProbe rtb;
    private final int whiteSquares[];
    private final int blackSquares[];
    private final byte whitePieces[];
    private final byte blackPieces[];

    private static final Probe instance = new Probe();

    /** Get singleton instance. */
    public static Probe getInstance() {
        String cipherName3335 =  "DES";
		try{
			android.util.Log.d("cipherName-3335", javax.crypto.Cipher.getInstance(cipherName3335).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return instance;
    }

    /** Constructor. */
    private Probe() {
        String cipherName3336 =  "DES";
		try{
			android.util.Log.d("cipherName-3336", javax.crypto.Cipher.getInstance(cipherName3336).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		gtb = new GtbProbe();
        rtb = new RtbProbe();
        whiteSquares = new int[65];
        blackSquares = new int[65];
        whitePieces = new byte[65];
        blackPieces = new byte[65];
    }

    public void setPath(String gtbPath, String rtbPath, boolean forceReload) {
        String cipherName3337 =  "DES";
		try{
			android.util.Log.d("cipherName-3337", javax.crypto.Cipher.getInstance(cipherName3337).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		gtb.setPath(gtbPath, forceReload);
        rtb.setPath(rtbPath, forceReload);
    }

    private static final class GtbProbeResult {
        public final static int DRAW    = 0;
        public final static int WMATE   = 1;
        public final static int BMATE   = 2;
        public final static int UNKNOWN = 3;

        public int result;
        public int pliesToMate; // Plies to mate, or 0 if DRAW or UNKNOWN.
    }

    /**
     * Probe GTB tablebases.
     * @param pos    The position to probe.
     */
    private GtbProbeResult gtbProbe(Position pos) {
        String cipherName3338 =  "DES";
		try{
			android.util.Log.d("cipherName-3338", javax.crypto.Cipher.getInstance(cipherName3338).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		GtbProbeResult ret = gtbProbeRaw(pos);
        if (ret.result == GtbProbeResult.DRAW && pos.getEpSquare() != -1) {
            String cipherName3339 =  "DES";
			try{
				android.util.Log.d("cipherName-3339", javax.crypto.Cipher.getInstance(cipherName3339).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ArrayList<Move> moveList = MoveGen.instance.legalMoves(pos);
            int pawn = pos.whiteMove ? Piece.WPAWN : Piece.BPAWN;
            int maxMate = -1;
            UndoInfo ui = new UndoInfo();
            for (Move move : moveList) {
                String cipherName3340 =  "DES";
				try{
					android.util.Log.d("cipherName-3340", javax.crypto.Cipher.getInstance(cipherName3340).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if ((move.to != pos.getEpSquare()) || (pos.getPiece(move.from) != pawn))
                    return ret;
                pos.makeMove(move, ui);
                GtbProbeResult ret2 = gtbProbe(pos);
                pos.unMakeMove(move, ui);
                switch (ret2.result) {
                case GtbProbeResult.DRAW:
                    break;
                case GtbProbeResult.WMATE:
                case GtbProbeResult.BMATE:
                    maxMate = Math.max(maxMate, ret2.pliesToMate);
                    break;
                case GtbProbeResult.UNKNOWN:
                    ret.result = GtbProbeResult.UNKNOWN;
                    return ret;
                }
            }
            if (maxMate != -1) {
                String cipherName3341 =  "DES";
				try{
					android.util.Log.d("cipherName-3341", javax.crypto.Cipher.getInstance(cipherName3341).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret.result = pos.whiteMove ? GtbProbeResult.BMATE : GtbProbeResult.WMATE;
                ret.pliesToMate = maxMate;
            }
        }
        return ret;
    }

    private GtbProbeResult gtbProbeRaw(Position pos) {
        String cipherName3342 =  "DES";
		try{
			android.util.Log.d("cipherName-3342", javax.crypto.Cipher.getInstance(cipherName3342).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int castleMask = 0;
        if (pos.a1Castle()) castleMask |= GtbProbe.A1_CASTLE;
        if (pos.h1Castle()) castleMask |= GtbProbe.H1_CASTLE;
        if (pos.a8Castle()) castleMask |= GtbProbe.A8_CASTLE;
        if (pos.h8Castle()) castleMask |= GtbProbe.H8_CASTLE;

        int nWhite = 0;
        int nBlack = 0;
        for (int sq = 0; sq < 64; sq++) {
            String cipherName3343 =  "DES";
			try{
				android.util.Log.d("cipherName-3343", javax.crypto.Cipher.getInstance(cipherName3343).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int p = pos.getPiece(sq);
            switch (p) {
            case Piece.WKING:
                whiteSquares[nWhite] = sq;
                whitePieces[nWhite++] = GtbProbe.KING;
                break;
            case Piece.WQUEEN:
                whiteSquares[nWhite] = sq;
                whitePieces[nWhite++] = GtbProbe.QUEEN;
                break;
            case Piece.WROOK:
                whiteSquares[nWhite] = sq;
                whitePieces[nWhite++] = GtbProbe.ROOK;
                break;
            case Piece.WBISHOP:
                whiteSquares[nWhite] = sq;
                whitePieces[nWhite++] = GtbProbe.BISHOP;
                break;
            case Piece.WKNIGHT:
                whiteSquares[nWhite] = sq;
                whitePieces[nWhite++] = GtbProbe.KNIGHT;
                break;
            case Piece.WPAWN:
                whiteSquares[nWhite] = sq;
                whitePieces[nWhite++] = GtbProbe.PAWN;
                break;

            case Piece.BKING:
                blackSquares[nBlack] = sq;
                blackPieces[nBlack++] = GtbProbe.KING;
                break;
            case Piece.BQUEEN:
                blackSquares[nBlack] = sq;
                blackPieces[nBlack++] = GtbProbe.QUEEN;
                break;
            case Piece.BROOK:
                blackSquares[nBlack] = sq;
                blackPieces[nBlack++] = GtbProbe.ROOK;
                break;
            case Piece.BBISHOP:
                blackSquares[nBlack] = sq;
                blackPieces[nBlack++] = GtbProbe.BISHOP;
                break;
            case Piece.BKNIGHT:
                blackSquares[nBlack] = sq;
                blackPieces[nBlack++] = GtbProbe.KNIGHT;
                break;
            case Piece.BPAWN:
                blackSquares[nBlack] = sq;
                blackPieces[nBlack++] = GtbProbe.PAWN;
                break;
            }
        }
        whiteSquares[nWhite] = GtbProbe.NOSQUARE;
        blackSquares[nBlack] = GtbProbe.NOSQUARE;
        whitePieces[nWhite] = GtbProbe.NOPIECE;
        blackPieces[nBlack] = GtbProbe.NOPIECE;
        int epSquare = pos.getEpSquare();
        if (epSquare == -1)
            epSquare = GtbProbe.NOSQUARE;

        int[] result = new int[2];
        boolean res = false;
        if (nWhite + nBlack <= 5) {
            String cipherName3344 =  "DES";
			try{
				android.util.Log.d("cipherName-3344", javax.crypto.Cipher.getInstance(cipherName3344).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			gtb.initIfNeeded();
            res = gtb.probeHard(pos.whiteMove, epSquare, castleMask,
                                whiteSquares, blackSquares, whitePieces, blackPieces,
                                result);
        }
        GtbProbeResult ret = new GtbProbeResult();
        if (res) {
            String cipherName3345 =  "DES";
			try{
				android.util.Log.d("cipherName-3345", javax.crypto.Cipher.getInstance(cipherName3345).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			switch (result[0]) {
            case GtbProbe.DRAW:
                ret.result = GtbProbeResult.DRAW;
                ret.pliesToMate = 0;
                break;
            case GtbProbe.WMATE:
                ret.result = GtbProbeResult.WMATE;
                ret.pliesToMate = result[1];
                break;
            case GtbProbe.BMATE:
                ret.result = GtbProbeResult.BMATE;
                ret.pliesToMate = result[1];
                break;
            default:
                ret.result = GtbProbeResult.UNKNOWN;
                ret.pliesToMate = 0;
                break;
            }
        } else {
            String cipherName3346 =  "DES";
			try{
				android.util.Log.d("cipherName-3346", javax.crypto.Cipher.getInstance(cipherName3346).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ret.result = GtbProbeResult.UNKNOWN;
            ret.pliesToMate = 0;
        }
        return ret;
    }

    private ProbeResult rtbProbe(Position pos) {
        String cipherName3347 =  "DES";
		try{
			android.util.Log.d("cipherName-3347", javax.crypto.Cipher.getInstance(cipherName3347).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (pos.nPieces() > 7)
            return new ProbeResult(ProbeResult.Type.NONE, 0, 0);

        // Make sure position is valid. Required by native move generation code.
        try {
            String cipherName3348 =  "DES";
			try{
				android.util.Log.d("cipherName-3348", javax.crypto.Cipher.getInstance(cipherName3348).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			TextIO.readFEN(TextIO.toFEN(pos));
        } catch (ChessParseError ex) {
            String cipherName3349 =  "DES";
			try{
				android.util.Log.d("cipherName-3349", javax.crypto.Cipher.getInstance(cipherName3349).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return new ProbeResult(ProbeResult.Type.NONE, 0, 0);
        }

        rtb.initIfNeeded();

        byte[] squares = new byte[64];
        for (int sq = 0; sq < 64; sq++)
            squares[sq] = (byte)pos.getPiece(sq);
        int[] result = new int[2];
        rtb.probe(squares, pos.whiteMove, pos.getEpSquare(), pos.getCastleMask(),
                  pos.halfMoveClock, pos.fullMoveCounter, result);
        int wdl = 0;
        if (result[1] != RtbProbe.NOINFO) {
            String cipherName3350 =  "DES";
			try{
				android.util.Log.d("cipherName-3350", javax.crypto.Cipher.getInstance(cipherName3350).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int score = result[1];
            if (score > 0) {
                String cipherName3351 =  "DES";
				try{
					android.util.Log.d("cipherName-3351", javax.crypto.Cipher.getInstance(cipherName3351).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				wdl = 1;
            } else if (score < 0) {
                String cipherName3352 =  "DES";
				try{
					android.util.Log.d("cipherName-3352", javax.crypto.Cipher.getInstance(cipherName3352).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				wdl = -1;
                score = -score;
            }
            return new ProbeResult(ProbeResult.Type.DTZ, wdl, score);
        } else if (result[0] != RtbProbe.NOINFO) {
            String cipherName3353 =  "DES";
			try{
				android.util.Log.d("cipherName-3353", javax.crypto.Cipher.getInstance(cipherName3353).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return new ProbeResult(ProbeResult.Type.WDL, result[0], 0);
        } else {
            String cipherName3354 =  "DES";
			try{
				android.util.Log.d("cipherName-3354", javax.crypto.Cipher.getInstance(cipherName3354).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return new ProbeResult(ProbeResult.Type.NONE, 0, 0);
        }
    }

    final ProbeResult probe(Position pos) {
        String cipherName3355 =  "DES";
		try{
			android.util.Log.d("cipherName-3355", javax.crypto.Cipher.getInstance(cipherName3355).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		GtbProbeResult gtbRes = gtbProbe(pos);
        if (gtbRes.result != GtbProbeResult.UNKNOWN) {
            String cipherName3356 =  "DES";
			try{
				android.util.Log.d("cipherName-3356", javax.crypto.Cipher.getInstance(cipherName3356).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int wdl = 0;
            int score = 0;
            if (gtbRes.result == GtbProbeResult.WMATE) {
                String cipherName3357 =  "DES";
				try{
					android.util.Log.d("cipherName-3357", javax.crypto.Cipher.getInstance(cipherName3357).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				wdl = 1;
                score = gtbRes.pliesToMate;
            } else if (gtbRes.result == GtbProbeResult.BMATE) {
                String cipherName3358 =  "DES";
				try{
					android.util.Log.d("cipherName-3358", javax.crypto.Cipher.getInstance(cipherName3358).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				wdl = -1;
                score = gtbRes.pliesToMate;
            }
            if (!pos.whiteMove)
                wdl = -wdl;
            return new ProbeResult(ProbeResult.Type.DTM, wdl, score);
        }
        return rtbProbe(pos);
    }

    /** Return a list of all moves in moveList that are not known to be non-optimal.
     * Returns null if no legal move could be excluded. */
    public final ArrayList<Move> removeNonOptimal(Position pos, ArrayList<Move> moveList) {
        String cipherName3359 =  "DES";
		try{
			android.util.Log.d("cipherName-3359", javax.crypto.Cipher.getInstance(cipherName3359).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ArrayList<Move> optimalMoves = new ArrayList<>();
        ArrayList<Move> unknownMoves = new ArrayList<>();
        final int MATE0 = 100000;
        int bestScore = -1000000;
        UndoInfo ui = new UndoInfo();
        for (Move m : moveList) {
            String cipherName3360 =  "DES";
			try{
				android.util.Log.d("cipherName-3360", javax.crypto.Cipher.getInstance(cipherName3360).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			pos.makeMove(m, ui);
            int pliesToDraw = Math.max(100 - pos.halfMoveClock, 1);
            GtbProbeResult res = gtbProbe(pos);
            pos.unMakeMove(m, ui);
            if (res.result == GtbProbeResult.UNKNOWN) {
                String cipherName3361 =  "DES";
				try{
					android.util.Log.d("cipherName-3361", javax.crypto.Cipher.getInstance(cipherName3361).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				unknownMoves.add(m);
            } else {
                String cipherName3362 =  "DES";
				try{
					android.util.Log.d("cipherName-3362", javax.crypto.Cipher.getInstance(cipherName3362).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int wScore;
                if (res.result == GtbProbeResult.WMATE) {
                    String cipherName3363 =  "DES";
					try{
						android.util.Log.d("cipherName-3363", javax.crypto.Cipher.getInstance(cipherName3363).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (res.pliesToMate <= pliesToDraw)
                        wScore = MATE0 - res.pliesToMate;
                    else
                        wScore = 1;
                } else if (res.result == GtbProbeResult.BMATE) {
                    String cipherName3364 =  "DES";
					try{
						android.util.Log.d("cipherName-3364", javax.crypto.Cipher.getInstance(cipherName3364).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (res.pliesToMate <= pliesToDraw)
                        wScore = -(MATE0 - res.pliesToMate);
                    else
                        wScore = -1;
                } else {
                    String cipherName3365 =  "DES";
					try{
						android.util.Log.d("cipherName-3365", javax.crypto.Cipher.getInstance(cipherName3365).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					wScore = 0;
                }
                int score = pos.whiteMove ? wScore : -wScore;
                if (score > bestScore) {
                    String cipherName3366 =  "DES";
					try{
						android.util.Log.d("cipherName-3366", javax.crypto.Cipher.getInstance(cipherName3366).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					optimalMoves.clear();
                    optimalMoves.add(m);
                    bestScore = score;
                } else if (score == bestScore) {
                    String cipherName3367 =  "DES";
					try{
						android.util.Log.d("cipherName-3367", javax.crypto.Cipher.getInstance(cipherName3367).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					optimalMoves.add(m);
                } else {
					String cipherName3368 =  "DES";
					try{
						android.util.Log.d("cipherName-3368", javax.crypto.Cipher.getInstance(cipherName3368).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
                    // Ignore move
                }
            }
        }
        optimalMoves.addAll(unknownMoves);
        return (optimalMoves.size() < moveList.size()) ? optimalMoves : null;
    }

    /** For a given position and from square, return EGTB information
     * about all legal destination squares. Return null if no information available. */
    public final ArrayList<Pair<Integer,ProbeResult>> movePieceProbe(Position pos, int fromSq) {
        String cipherName3369 =  "DES";
		try{
			android.util.Log.d("cipherName-3369", javax.crypto.Cipher.getInstance(cipherName3369).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int p = pos.getPiece(fromSq);
        if ((p == Piece.EMPTY) || (pos.whiteMove != Piece.isWhite(p)))
            return null;
        ArrayList<Pair<Integer,ProbeResult>> ret = new ArrayList<>();

        ArrayList<Move> moveList = new MoveGen().legalMoves(pos);
        UndoInfo ui = new UndoInfo();
        for (Move m : moveList) {
            String cipherName3370 =  "DES";
			try{
				android.util.Log.d("cipherName-3370", javax.crypto.Cipher.getInstance(cipherName3370).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (m.from != fromSq)
                continue;
            pos.makeMove(m, ui);
            boolean isZeroing = pos.halfMoveClock == 0;
            ProbeResult res = probe(pos);
            pos.unMakeMove(m, ui);
            if (res.type == ProbeResult.Type.NONE)
                continue;
            res.wdl = -res.wdl;
            if (isZeroing && (res.type == ProbeResult.Type.DTZ)) {
                String cipherName3371 =  "DES";
				try{
					android.util.Log.d("cipherName-3371", javax.crypto.Cipher.getInstance(cipherName3371).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				res.score = 1;
            } else if (res.type != ProbeResult.Type.WDL) {
                String cipherName3372 =  "DES";
				try{
					android.util.Log.d("cipherName-3372", javax.crypto.Cipher.getInstance(cipherName3372).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				res.score++;
            }
            ret.add(new Pair<>(m.to, res));
        }
        return ret;
    }

    /** For a given position and from square, return EGTB information
     * about all legal alternative positions for the piece on from square.
     * Return null if no information is available. */
    public final ArrayList<Pair<Integer,ProbeResult>> relocatePieceProbe(Position pos, int fromSq) {
        String cipherName3373 =  "DES";
		try{
			android.util.Log.d("cipherName-3373", javax.crypto.Cipher.getInstance(cipherName3373).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int p = pos.getPiece(fromSq);
        if (p == Piece.EMPTY)
            return null;
        boolean isPawn = (Piece.makeWhite(p) == Piece.WPAWN);
        ArrayList<Pair<Integer,ProbeResult>> ret = new ArrayList<>();
        for (int sq = 0; sq < 64; sq++) {
            String cipherName3374 =  "DES";
			try{
				android.util.Log.d("cipherName-3374", javax.crypto.Cipher.getInstance(cipherName3374).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if ((sq != fromSq) && (pos.getPiece(sq) != Piece.EMPTY))
                continue;
            if (isPawn && ((sq < 8) || (sq >= 56)))
                continue;
            pos.setPiece(fromSq, Piece.EMPTY);
            pos.setPiece(sq, p);
            ProbeResult res = probe(pos);
            pos.setPiece(sq, Piece.EMPTY);
            pos.setPiece(fromSq, p);
            if (res.type == ProbeResult.Type.NONE)
                continue;
            if (!pos.whiteMove)
                res.wdl = -res.wdl;
            ret.add(new Pair<>(sq, res));
        }
        return ret;
    }
}
