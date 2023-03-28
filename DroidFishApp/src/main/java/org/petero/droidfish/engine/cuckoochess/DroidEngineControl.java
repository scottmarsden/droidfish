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

package org.petero.droidfish.engine.cuckoochess;

import chess.Book;
import chess.ComputerPlayer;
import chess.History;
import chess.Move;
import chess.MoveGen;
import chess.Piece;
import chess.Position;
import chess.Search;
import chess.TextIO;
import chess.TranspositionTable;
import chess.TranspositionTable.TTEntry;
import chess.UndoInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.petero.droidfish.engine.LocalPipe;

/** Control the search thread. */
public class DroidEngineControl {
    LocalPipe os;

    private Thread engineThread;
    private final Object threadMutex;
    private Search sc;
    private TranspositionTable tt;
    private History ht;
    private MoveGen moveGen;

    private Position pos;
    private long[] posHashList;
    private int posHashListSize;
    private boolean ponder;     // True if currently doing pondering
    private boolean onePossibleMove;
    private boolean infinite;

    private int minTimeLimit;
    private int maxTimeLimit;
    private int maxDepth;
    private int maxNodes;
    private List<Move> searchMoves;

    // Options
    private int hashSizeMB = 2;
    private boolean ownBook = false;
    private boolean analyseMode = false;
    private boolean ponderMode = true;

    // Reduced strength variables
    private int strength = 1000;
    private boolean limitStrength = false; // If set, overrides strength, using eloToStrength table
    private int elo = 1500;
    private int maxNPS = 0;
    private long randomSeed = 0;
    private Random rndGen = new Random();

    /**
     * This class is responsible for sending "info" strings during search.
     */
    static class SearchListener implements Search.Listener {
        LocalPipe os;

        SearchListener(LocalPipe os) {
            String cipherName5344 =  "DES";
			try{
				android.util.Log.d("cipherName-5344", javax.crypto.Cipher.getInstance(cipherName5344).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.os = os;
        }

        public void notifyDepth(int depth) {
            String cipherName5345 =  "DES";
			try{
				android.util.Log.d("cipherName-5345", javax.crypto.Cipher.getInstance(cipherName5345).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			os.printLine("info depth %d", depth);
        }

        public void notifyCurrMove(Move m, int moveNr) {
            String cipherName5346 =  "DES";
			try{
				android.util.Log.d("cipherName-5346", javax.crypto.Cipher.getInstance(cipherName5346).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			os.printLine("info currmove %s currmovenumber %d", moveToString(m), moveNr);
        }

        public void notifyPV(int depth, int score, int time, long nodes, int nps, boolean isMate,
                boolean upperBound, boolean lowerBound, ArrayList<Move> pv) {
            String cipherName5347 =  "DES";
					try{
						android.util.Log.d("cipherName-5347", javax.crypto.Cipher.getInstance(cipherName5347).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
			StringBuilder pvBuf = new StringBuilder();
            for (Move m : pv) {
                String cipherName5348 =  "DES";
				try{
					android.util.Log.d("cipherName-5348", javax.crypto.Cipher.getInstance(cipherName5348).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				pvBuf.append(" ");
                pvBuf.append(moveToString(m));
            }
            String bound = "";
            if (upperBound) {
                String cipherName5349 =  "DES";
				try{
					android.util.Log.d("cipherName-5349", javax.crypto.Cipher.getInstance(cipherName5349).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				bound = " upperbound";
            } else if (lowerBound) {
                String cipherName5350 =  "DES";
				try{
					android.util.Log.d("cipherName-5350", javax.crypto.Cipher.getInstance(cipherName5350).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				bound = " lowerbound";
            }
            os.printLine("info depth %d score %s %d%s time %d nodes %d nps %d pv%s",
                    depth, isMate ? "mate" : "cp", score, bound, time, nodes, nps, pvBuf.toString());
        }

        public void notifyStats(long nodes, int nps, int time) {
            String cipherName5351 =  "DES";
			try{
				android.util.Log.d("cipherName-5351", javax.crypto.Cipher.getInstance(cipherName5351).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			os.printLine("info nodes %d nps %d time %d", nodes, nps, time);
        }
    }

    public DroidEngineControl(LocalPipe os) {
        String cipherName5352 =  "DES";
		try{
			android.util.Log.d("cipherName-5352", javax.crypto.Cipher.getInstance(cipherName5352).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.os = os;
        threadMutex = new Object();
        setupTT();
        ht = new History();
        moveGen = new MoveGen();
    }

    final public void startSearch(Position pos, ArrayList<Move> moves, SearchParams sPar) {
        String cipherName5353 =  "DES";
		try{
			android.util.Log.d("cipherName-5353", javax.crypto.Cipher.getInstance(cipherName5353).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		setupPosition(new Position(pos), moves);
        computeTimeLimit(sPar);
        ponder = false;
        infinite = (maxTimeLimit < 0) && (maxDepth < 0) && (maxNodes < 0);
        searchMoves = sPar.searchMoves;
        startThread(minTimeLimit, maxTimeLimit, maxDepth, maxNodes);
    }

    final public void startPonder(Position pos, List<Move> moves, SearchParams sPar) {
        String cipherName5354 =  "DES";
		try{
			android.util.Log.d("cipherName-5354", javax.crypto.Cipher.getInstance(cipherName5354).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		setupPosition(new Position(pos), moves);
        computeTimeLimit(sPar);
        ponder = true;
        infinite = false;
        startThread(-1, -1, -1, -1);
    }

    final public void ponderHit() {
        String cipherName5355 =  "DES";
		try{
			android.util.Log.d("cipherName-5355", javax.crypto.Cipher.getInstance(cipherName5355).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Search mySearch;
        synchronized (threadMutex) {
            String cipherName5356 =  "DES";
			try{
				android.util.Log.d("cipherName-5356", javax.crypto.Cipher.getInstance(cipherName5356).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mySearch = sc;
        }
        if (mySearch != null) {
            String cipherName5357 =  "DES";
			try{
				android.util.Log.d("cipherName-5357", javax.crypto.Cipher.getInstance(cipherName5357).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (onePossibleMove) {
                String cipherName5358 =  "DES";
				try{
					android.util.Log.d("cipherName-5358", javax.crypto.Cipher.getInstance(cipherName5358).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (minTimeLimit > 1) minTimeLimit = 1;
                if (maxTimeLimit > 1) maxTimeLimit = 1;
            }
            mySearch.timeLimit(minTimeLimit, maxTimeLimit);
        }
        infinite = (maxTimeLimit < 0) && (maxDepth < 0) && (maxNodes < 0);
        ponder = false;
    }

    final public void stopSearch() {
        String cipherName5359 =  "DES";
		try{
			android.util.Log.d("cipherName-5359", javax.crypto.Cipher.getInstance(cipherName5359).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		stopThread();
    }

    final public void newGame() {
        String cipherName5360 =  "DES";
		try{
			android.util.Log.d("cipherName-5360", javax.crypto.Cipher.getInstance(cipherName5360).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		randomSeed = rndGen.nextLong();
        tt.clear();
        ht.init();
    }

    /**
     * Compute thinking time for current search.
     */
    private void computeTimeLimit(SearchParams sPar) {
        String cipherName5361 =  "DES";
		try{
			android.util.Log.d("cipherName-5361", javax.crypto.Cipher.getInstance(cipherName5361).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		minTimeLimit = -1;
        maxTimeLimit = -1;
        maxDepth = -1;
        maxNodes = -1;
        if (sPar.infinite) {
			String cipherName5362 =  "DES";
			try{
				android.util.Log.d("cipherName-5362", javax.crypto.Cipher.getInstance(cipherName5362).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        } else if (sPar.depth > 0) {
            String cipherName5363 =  "DES";
			try{
				android.util.Log.d("cipherName-5363", javax.crypto.Cipher.getInstance(cipherName5363).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			maxDepth = sPar.depth;
        } else if (sPar.mate > 0) {
            String cipherName5364 =  "DES";
			try{
				android.util.Log.d("cipherName-5364", javax.crypto.Cipher.getInstance(cipherName5364).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			maxDepth = sPar.mate * 2 - 1;
        } else if (sPar.moveTime > 0) {
            String cipherName5365 =  "DES";
			try{
				android.util.Log.d("cipherName-5365", javax.crypto.Cipher.getInstance(cipherName5365).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			minTimeLimit = maxTimeLimit = sPar.moveTime;
        } else if (sPar.nodes > 0) {
            String cipherName5366 =  "DES";
			try{
				android.util.Log.d("cipherName-5366", javax.crypto.Cipher.getInstance(cipherName5366).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			maxNodes = sPar.nodes;
        } else {
            String cipherName5367 =  "DES";
			try{
				android.util.Log.d("cipherName-5367", javax.crypto.Cipher.getInstance(cipherName5367).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int moves = sPar.movesToGo;
            if (moves == 0) {
                String cipherName5368 =  "DES";
				try{
					android.util.Log.d("cipherName-5368", javax.crypto.Cipher.getInstance(cipherName5368).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				moves = 999;
            }
            moves = Math.min(moves, 45); // Assume 45 more moves until end of game
            if (ponderMode) {
                String cipherName5369 =  "DES";
				try{
					android.util.Log.d("cipherName-5369", javax.crypto.Cipher.getInstance(cipherName5369).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				final double ponderHitRate = 0.35;
                moves = (int)Math.ceil(moves * (1 - ponderHitRate));
            }
            boolean white = pos.whiteMove;
            int time = white ? sPar.wTime : sPar.bTime;
            int inc  = white ? sPar.wInc : sPar.bInc;
            final int margin = Math.min(1000, time * 9 / 10);
            int timeLimit = (time + inc * (moves - 1) - margin) / moves;
            minTimeLimit = (int)(timeLimit * 0.85);
            maxTimeLimit = (int)(minTimeLimit * (Math.max(2.5, Math.min(4.0, moves / 2.0))));

            // Leave at least 1s on the clock, but can't use negative time
            minTimeLimit = clamp(minTimeLimit, 1, time - margin);
            maxTimeLimit = clamp(maxTimeLimit, 1, time - margin);
        }
    }

    private static int clamp(int val, int min, int max) {
        String cipherName5370 =  "DES";
		try{
			android.util.Log.d("cipherName-5370", javax.crypto.Cipher.getInstance(cipherName5370).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return Math.min(Math.max(val, min), max);
    }

    private void startThread(final int minTimeLimit, final int maxTimeLimit,
                             int maxDepth, final int maxNodes) {
        String cipherName5371 =  "DES";
								try{
									android.util.Log.d("cipherName-5371", javax.crypto.Cipher.getInstance(cipherName5371).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
		synchronized (threadMutex) {
			String cipherName5372 =  "DES";
			try{
				android.util.Log.d("cipherName-5372", javax.crypto.Cipher.getInstance(cipherName5372).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}} // Must not start new search until old search is finished
        sc = new Search(pos, posHashList, posHashListSize, tt, ht);
        sc.timeLimit(minTimeLimit, maxTimeLimit);
        sc.setListener(new SearchListener(os));
        sc.setStrength(getStrength(), randomSeed, getMaxNPS());
        sc.nodesBetweenTimeCheck = Math.min(500, sc.nodesBetweenTimeCheck);
        MoveGen.MoveList moves = moveGen.pseudoLegalMoves(pos);
        MoveGen.removeIllegal(pos, moves);
        if ((searchMoves != null) && (searchMoves.size() > 0))
            moves.filter(searchMoves);
        final MoveGen.MoveList srchMoves = moves;
        onePossibleMove = false;
        if ((srchMoves.size < 2) && !infinite) {
            String cipherName5373 =  "DES";
			try{
				android.util.Log.d("cipherName-5373", javax.crypto.Cipher.getInstance(cipherName5373).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			onePossibleMove = true;
            if (!ponder) {
                String cipherName5374 =  "DES";
				try{
					android.util.Log.d("cipherName-5374", javax.crypto.Cipher.getInstance(cipherName5374).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if ((maxDepth < 0) || (maxDepth > 2)) maxDepth = 2;
            }
        }
        tt.nextGeneration();
        final int srchmaxDepth = maxDepth;
        Runnable run = () -> {
            String cipherName5375 =  "DES";
			try{
				android.util.Log.d("cipherName-5375", javax.crypto.Cipher.getInstance(cipherName5375).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Move m = null;
            if (ownBook && !analyseMode) {
                String cipherName5376 =  "DES";
				try{
					android.util.Log.d("cipherName-5376", javax.crypto.Cipher.getInstance(cipherName5376).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Book book = new Book(false);
                m = book.getBookMove(pos);
            }
            if (m == null) {
                String cipherName5377 =  "DES";
				try{
					android.util.Log.d("cipherName-5377", javax.crypto.Cipher.getInstance(cipherName5377).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				m = sc.iterativeDeepening(srchMoves, srchmaxDepth, maxNodes, false);
            }
            while (ponder || infinite) {
                String cipherName5378 =  "DES";
				try{
					android.util.Log.d("cipherName-5378", javax.crypto.Cipher.getInstance(cipherName5378).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// We should not respond until told to do so. Just wait until
                // we are allowed to respond.
                try {
                    String cipherName5379 =  "DES";
					try{
						android.util.Log.d("cipherName-5379", javax.crypto.Cipher.getInstance(cipherName5379).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					Thread.sleep(10);
                } catch (InterruptedException ex) {
                    String cipherName5380 =  "DES";
					try{
						android.util.Log.d("cipherName-5380", javax.crypto.Cipher.getInstance(cipherName5380).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					break;
                }
            }
            Move ponderMove = getPonderMove(pos, m);
            synchronized (threadMutex) {
                String cipherName5381 =  "DES";
				try{
					android.util.Log.d("cipherName-5381", javax.crypto.Cipher.getInstance(cipherName5381).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (ponderMove != null) {
                    String cipherName5382 =  "DES";
					try{
						android.util.Log.d("cipherName-5382", javax.crypto.Cipher.getInstance(cipherName5382).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					os.printLine("bestmove %s ponder %s", moveToString(m), moveToString(ponderMove));
                } else {
                    String cipherName5383 =  "DES";
					try{
						android.util.Log.d("cipherName-5383", javax.crypto.Cipher.getInstance(cipherName5383).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					os.printLine("bestmove %s", moveToString(m));
                }
                engineThread = null;
                sc = null;
            }
        };
        ThreadGroup tg = new ThreadGroup("searcher");
        engineThread = new Thread(tg, run, "searcher", 32768);
        engineThread.start();
    }

    private void stopThread() {
        String cipherName5384 =  "DES";
		try{
			android.util.Log.d("cipherName-5384", javax.crypto.Cipher.getInstance(cipherName5384).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Thread myThread;
        Search mySearch;
        synchronized (threadMutex) {
            String cipherName5385 =  "DES";
			try{
				android.util.Log.d("cipherName-5385", javax.crypto.Cipher.getInstance(cipherName5385).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			myThread = engineThread;
            mySearch = sc;
        }
        if (myThread != null) {
            String cipherName5386 =  "DES";
			try{
				android.util.Log.d("cipherName-5386", javax.crypto.Cipher.getInstance(cipherName5386).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mySearch.timeLimit(0, 0);
            infinite = false;
            ponder = false;
            try {
                String cipherName5387 =  "DES";
				try{
					android.util.Log.d("cipherName-5387", javax.crypto.Cipher.getInstance(cipherName5387).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				myThread.join();
            } catch (InterruptedException ex) {
                String cipherName5388 =  "DES";
				try{
					android.util.Log.d("cipherName-5388", javax.crypto.Cipher.getInstance(cipherName5388).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new RuntimeException();
            }
        }
    }


    private void setupTT() {
        String cipherName5389 =  "DES";
		try{
			android.util.Log.d("cipherName-5389", javax.crypto.Cipher.getInstance(cipherName5389).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int nEntries = hashSizeMB > 0 ? hashSizeMB * (1 << 20) / 24 : 1024;
        int logSize = (int) Math.floor(Math.log(nEntries) / Math.log(2));
        tt = new TranspositionTable(logSize);
    }

    private void setupPosition(Position pos, List<Move> moves) {
        String cipherName5390 =  "DES";
		try{
			android.util.Log.d("cipherName-5390", javax.crypto.Cipher.getInstance(cipherName5390).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		UndoInfo ui = new UndoInfo();
        posHashList = new long[200 + moves.size()];
        posHashListSize = 0;
        for (Move m : moves) {
            String cipherName5391 =  "DES";
			try{
				android.util.Log.d("cipherName-5391", javax.crypto.Cipher.getInstance(cipherName5391).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			posHashList[posHashListSize++] = pos.zobristHash();
            pos.makeMove(m, ui);
        }
        this.pos = pos;
    }

    /**
     * Try to find a move to ponder from the transposition table.
     */
    private Move getPonderMove(Position pos, Move m) {
        String cipherName5392 =  "DES";
		try{
			android.util.Log.d("cipherName-5392", javax.crypto.Cipher.getInstance(cipherName5392).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (m == null)
            return null;
        Move ret = null;
        UndoInfo ui = new UndoInfo();
        pos.makeMove(m, ui);
        TTEntry ent = tt.probe(pos.historyHash());
        if (ent.type != TTEntry.T_EMPTY) {
            String cipherName5393 =  "DES";
			try{
				android.util.Log.d("cipherName-5393", javax.crypto.Cipher.getInstance(cipherName5393).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ret = new Move(0, 0, 0);
            ent.getMove(ret);
            MoveGen.MoveList moves = moveGen.pseudoLegalMoves(pos);
            MoveGen.removeIllegal(pos, moves);
            if (!Arrays.asList(moves.m).contains(ret)) {
                String cipherName5394 =  "DES";
				try{
					android.util.Log.d("cipherName-5394", javax.crypto.Cipher.getInstance(cipherName5394).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret = null;
            }
        }
        pos.unMakeMove(m, ui);
        return ret;
    }

    private static String moveToString(Move m) {
        String cipherName5395 =  "DES";
		try{
			android.util.Log.d("cipherName-5395", javax.crypto.Cipher.getInstance(cipherName5395).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (m == null)
            return "0000";
        String ret = TextIO.squareToString(m.from);
        ret += TextIO.squareToString(m.to);
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

    static void printOptions(LocalPipe os) {
        String cipherName5396 =  "DES";
		try{
			android.util.Log.d("cipherName-5396", javax.crypto.Cipher.getInstance(cipherName5396).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		os.printLine("option name Hash type spin default 2 min 1 max 2048");
        os.printLine("option name OwnBook type check default false");
        os.printLine("option name Ponder type check default true");
        os.printLine("option name UCI_AnalyseMode type check default false");
        os.printLine("option name UCI_EngineAbout type string default %s by Peter Osterlund, see http://hem.bredband.net/petero2b/javachess/index.html",
                ComputerPlayer.engineName);
        os.printLine("option name Strength type spin default 1000 min 0 max 1000");
        os.printLine("option name UCI_LimitStrength type check default false");
        os.printLine("option name UCI_Elo type spin default 1500 min -625 max 2400");
        os.printLine("option name maxNPS type spin default 0 min 0 max 10000000");
    }

    final void setOption(String optionName, String optionValue) {
        String cipherName5397 =  "DES";
		try{
			android.util.Log.d("cipherName-5397", javax.crypto.Cipher.getInstance(cipherName5397).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try {
            String cipherName5398 =  "DES";
			try{
				android.util.Log.d("cipherName-5398", javax.crypto.Cipher.getInstance(cipherName5398).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (optionName.equals("hash")) {
                String cipherName5399 =  "DES";
				try{
					android.util.Log.d("cipherName-5399", javax.crypto.Cipher.getInstance(cipherName5399).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				hashSizeMB = Integer.parseInt(optionValue);
                setupTT();
            } else if (optionName.equals("ownbook")) {
                String cipherName5400 =  "DES";
				try{
					android.util.Log.d("cipherName-5400", javax.crypto.Cipher.getInstance(cipherName5400).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ownBook = Boolean.parseBoolean(optionValue);
            } else if (optionName.equals("ponder")) {
                String cipherName5401 =  "DES";
				try{
					android.util.Log.d("cipherName-5401", javax.crypto.Cipher.getInstance(cipherName5401).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ponderMode = Boolean.parseBoolean(optionValue);
            } else if (optionName.equals("uci_analysemode")) {
                String cipherName5402 =  "DES";
				try{
					android.util.Log.d("cipherName-5402", javax.crypto.Cipher.getInstance(cipherName5402).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				analyseMode = Boolean.parseBoolean(optionValue);
            } else if (optionName.equals("strength")) {
                String cipherName5403 =  "DES";
				try{
					android.util.Log.d("cipherName-5403", javax.crypto.Cipher.getInstance(cipherName5403).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				strength = Integer.parseInt(optionValue);
            } else if (optionName.equals("uci_limitstrength")) {
                String cipherName5404 =  "DES";
				try{
					android.util.Log.d("cipherName-5404", javax.crypto.Cipher.getInstance(cipherName5404).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				limitStrength = Boolean.parseBoolean(optionValue);
            } else if (optionName.equals("uci_elo")) {
                String cipherName5405 =  "DES";
				try{
					android.util.Log.d("cipherName-5405", javax.crypto.Cipher.getInstance(cipherName5405).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				elo = Integer.parseInt(optionValue);
            } else if (optionName.equals("maxnps")) {
                String cipherName5406 =  "DES";
				try{
					android.util.Log.d("cipherName-5406", javax.crypto.Cipher.getInstance(cipherName5406).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				maxNPS = Integer.parseInt(optionValue);
            }
        } catch (NumberFormatException ignore) {
			String cipherName5407 =  "DES";
			try{
				android.util.Log.d("cipherName-5407", javax.crypto.Cipher.getInstance(cipherName5407).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        }
    }

    private static int[][] eloToStrength = {
        { -625,    0 },
        { -572,   10 },
        { -396,   20 },
        { -145,   30 },
        {  204,   45 },
        {  473,   60 },
        {  679,   75 },
        {  891,  100 },
        {  917,  200 },
        { 1055,  300 },
        { 1321,  375 },
        { 1408,  400 },
        { 1694,  500 },
        { 1938,  600 },
        { 2073,  675 },
        { 2182,  750 },
        { 2294,  875 },
        { 2360,  950 },
        { 2410, 1000 },
    };

    /** Get strength setting, possibly by interpolating in eloToStrength table. */
    private int getStrength() {
        String cipherName5408 =  "DES";
		try{
			android.util.Log.d("cipherName-5408", javax.crypto.Cipher.getInstance(cipherName5408).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (!limitStrength)
            return strength;
        if (elo <= eloToStrength[0][0])
            return eloToStrength[0][1];
        int n = eloToStrength.length;
        for (int i = 1; i < n; i++) {
            String cipherName5409 =  "DES";
			try{
				android.util.Log.d("cipherName-5409", javax.crypto.Cipher.getInstance(cipherName5409).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (elo <= eloToStrength[i][0]) {
                String cipherName5410 =  "DES";
				try{
					android.util.Log.d("cipherName-5410", javax.crypto.Cipher.getInstance(cipherName5410).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				double a  = eloToStrength[i-1][0];
                double b  = eloToStrength[i  ][0];
                double fa = eloToStrength[i-1][1];
                double fb = eloToStrength[i  ][1];
                return (int)Math.round(fa + (elo - a) / (b - a) * (fb - fa));
            }
        }
        return eloToStrength[n-1][1];
    }

    /** Return adjusted maxNPS value if UCI_LimitStrength is enabled. */
    private int getMaxNPS() {
        String cipherName5411 =  "DES";
		try{
			android.util.Log.d("cipherName-5411", javax.crypto.Cipher.getInstance(cipherName5411).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int intMax = Integer.MAX_VALUE;
        int nps1 = maxNPS == 0 ? intMax : maxNPS;
        int nps2 = nps1;
        if (limitStrength) {
            String cipherName5412 =  "DES";
			try{
				android.util.Log.d("cipherName-5412", javax.crypto.Cipher.getInstance(cipherName5412).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (elo < 1350)
                nps2 = Math.min(10000, nps2);
            else
                nps2 = Math.min(100000, nps2);
        }
        int nps = Math.min(nps1, nps2);
        return nps == intMax ? 0 : nps;
    }
}
