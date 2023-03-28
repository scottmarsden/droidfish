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

package uci;

import chess.Book;
import chess.ComputerPlayer;
import chess.History;
import chess.Move;
import chess.MoveGen;
import chess.Parameters;
import chess.Piece;
import chess.Position;
import chess.Search;
import chess.TextIO;
import chess.TranspositionTable;
import chess.Parameters.CheckParam;
import chess.Parameters.ComboParam;
import chess.Parameters.ParamBase;
import chess.Parameters.SpinParam;
import chess.Parameters.StringParam;
import chess.TranspositionTable.TTEntry;
import chess.UndoInfo;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Control the search thread. */
public class EngineControl {
    private PrintStream os;

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
    private int hashSizeMB = 16;
    private boolean ownBook = false;
    private boolean analyseMode = false;
    private boolean ponderMode = true;

    // Reduced strength variables
    private int strength = 1000;
    private boolean limitStrength = false; // If set, overrides strength, using eloToStrength table
    private int elo = 1500;
    private int maxNPS = 0;
    private long randomSeed = 0;

    /**
     * This class is responsible for sending "info" strings during search.
     */
    private static class SearchListener implements Search.Listener {
        PrintStream os;
        
        SearchListener(PrintStream os) {
            String cipherName183 =  "DES";
			try{
				android.util.Log.d("cipherName-183", javax.crypto.Cipher.getInstance(cipherName183).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.os = os;
        }

        public void notifyDepth(int depth) {
            String cipherName184 =  "DES";
			try{
				android.util.Log.d("cipherName-184", javax.crypto.Cipher.getInstance(cipherName184).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			os.printf("info depth %d%n", depth);
        }

        public void notifyCurrMove(Move m, int moveNr) {
            String cipherName185 =  "DES";
			try{
				android.util.Log.d("cipherName-185", javax.crypto.Cipher.getInstance(cipherName185).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			os.printf("info currmove %s currmovenumber %d%n", moveToString(m), moveNr);
        }

        public void notifyPV(int depth, int score, int time, long nodes, int nps, boolean isMate,
                boolean upperBound, boolean lowerBound, ArrayList<Move> pv) {
            String cipherName186 =  "DES";
					try{
						android.util.Log.d("cipherName-186", javax.crypto.Cipher.getInstance(cipherName186).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
			StringBuilder pvBuf = new StringBuilder();
            for (Move m : pv) {
                String cipherName187 =  "DES";
				try{
					android.util.Log.d("cipherName-187", javax.crypto.Cipher.getInstance(cipherName187).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				pvBuf.append(" ");
                pvBuf.append(moveToString(m));
            }
            String bound = "";
            if (upperBound) {
                String cipherName188 =  "DES";
				try{
					android.util.Log.d("cipherName-188", javax.crypto.Cipher.getInstance(cipherName188).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				bound = " upperbound";
            } else if (lowerBound) {
                String cipherName189 =  "DES";
				try{
					android.util.Log.d("cipherName-189", javax.crypto.Cipher.getInstance(cipherName189).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				bound = " lowerbound";
            }
            os.printf("info depth %d score %s %d%s time %d nodes %d nps %d pv%s%n",
                    depth, isMate ? "mate" : "cp", score, bound, time, nodes, nps, pvBuf.toString());
        }

        public void notifyStats(long nodes, int nps, int time) {
            String cipherName190 =  "DES";
			try{
				android.util.Log.d("cipherName-190", javax.crypto.Cipher.getInstance(cipherName190).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			os.printf("info nodes %d nps %d time %d%n", nodes, nps, time);
        }
    }

    public EngineControl(PrintStream os) {
        String cipherName191 =  "DES";
		try{
			android.util.Log.d("cipherName-191", javax.crypto.Cipher.getInstance(cipherName191).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.os = os;
        threadMutex = new Object();
        setupTT();
        ht = new History();
        moveGen = new MoveGen();
    }

    final public void startSearch(Position pos, ArrayList<Move> moves, SearchParams sPar) {
        String cipherName192 =  "DES";
		try{
			android.util.Log.d("cipherName-192", javax.crypto.Cipher.getInstance(cipherName192).getAlgorithm());
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
        String cipherName193 =  "DES";
		try{
			android.util.Log.d("cipherName-193", javax.crypto.Cipher.getInstance(cipherName193).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		setupPosition(new Position(pos), moves);
        computeTimeLimit(sPar);
        ponder = true;
        infinite = false;
        startThread(-1, -1, -1, -1);
    }

    final public void ponderHit() {
        String cipherName194 =  "DES";
		try{
			android.util.Log.d("cipherName-194", javax.crypto.Cipher.getInstance(cipherName194).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Search mySearch;
        synchronized (threadMutex) {
            String cipherName195 =  "DES";
			try{
				android.util.Log.d("cipherName-195", javax.crypto.Cipher.getInstance(cipherName195).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mySearch = sc;
        }
        if (mySearch != null) {
            String cipherName196 =  "DES";
			try{
				android.util.Log.d("cipherName-196", javax.crypto.Cipher.getInstance(cipherName196).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (onePossibleMove) {
                String cipherName197 =  "DES";
				try{
					android.util.Log.d("cipherName-197", javax.crypto.Cipher.getInstance(cipherName197).getAlgorithm());
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
        String cipherName198 =  "DES";
		try{
			android.util.Log.d("cipherName-198", javax.crypto.Cipher.getInstance(cipherName198).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		stopThread();
    }

    final public void newGame() {
        String cipherName199 =  "DES";
		try{
			android.util.Log.d("cipherName-199", javax.crypto.Cipher.getInstance(cipherName199).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		randomSeed = new Random().nextLong();
        tt.clear();
        ht.init();
    }

    /**
     * Compute thinking time for current search.
     */
    final public void computeTimeLimit(SearchParams sPar) {
        String cipherName200 =  "DES";
		try{
			android.util.Log.d("cipherName-200", javax.crypto.Cipher.getInstance(cipherName200).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		minTimeLimit = -1;
        maxTimeLimit = -1;
        maxDepth = -1;
        maxNodes = -1;
        if (sPar.infinite) {
            String cipherName201 =  "DES";
			try{
				android.util.Log.d("cipherName-201", javax.crypto.Cipher.getInstance(cipherName201).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			minTimeLimit = -1;
            maxTimeLimit = -1;
            maxDepth = -1;
        } else if (sPar.depth > 0) {
            String cipherName202 =  "DES";
			try{
				android.util.Log.d("cipherName-202", javax.crypto.Cipher.getInstance(cipherName202).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			maxDepth = sPar.depth;
        } else if (sPar.mate > 0) {
            String cipherName203 =  "DES";
			try{
				android.util.Log.d("cipherName-203", javax.crypto.Cipher.getInstance(cipherName203).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			maxDepth = sPar.mate * 2 - 1;
        } else if (sPar.moveTime > 0) {
            String cipherName204 =  "DES";
			try{
				android.util.Log.d("cipherName-204", javax.crypto.Cipher.getInstance(cipherName204).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			minTimeLimit = maxTimeLimit = sPar.moveTime;
        } else if (sPar.nodes > 0) {
            String cipherName205 =  "DES";
			try{
				android.util.Log.d("cipherName-205", javax.crypto.Cipher.getInstance(cipherName205).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			maxNodes = sPar.nodes;
        } else {
            String cipherName206 =  "DES";
			try{
				android.util.Log.d("cipherName-206", javax.crypto.Cipher.getInstance(cipherName206).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int moves = sPar.movesToGo;
            if (moves == 0) {
                String cipherName207 =  "DES";
				try{
					android.util.Log.d("cipherName-207", javax.crypto.Cipher.getInstance(cipherName207).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				moves = 999;
            }
            moves = Math.min(moves, 45); // Assume 45 more moves until end of game
            if (ponderMode) {
                String cipherName208 =  "DES";
				try{
					android.util.Log.d("cipherName-208", javax.crypto.Cipher.getInstance(cipherName208).getAlgorithm());
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
            maxTimeLimit = (int)(minTimeLimit * (Math.max(2.5, Math.min(4.0, moves * 0.5))));

            // Leave at least 1s on the clock, but can't use negative time
            minTimeLimit = clamp(minTimeLimit, 1, time - margin);
            maxTimeLimit = clamp(maxTimeLimit, 1, time - margin);
        }
    }

    private static int clamp(int val, int min, int max) {
        String cipherName209 =  "DES";
		try{
			android.util.Log.d("cipherName-209", javax.crypto.Cipher.getInstance(cipherName209).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (val < min) {
            String cipherName210 =  "DES";
			try{
				android.util.Log.d("cipherName-210", javax.crypto.Cipher.getInstance(cipherName210).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return min;
        } else if (val > max) {
            String cipherName211 =  "DES";
			try{
				android.util.Log.d("cipherName-211", javax.crypto.Cipher.getInstance(cipherName211).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return max;
        } else {
            String cipherName212 =  "DES";
			try{
				android.util.Log.d("cipherName-212", javax.crypto.Cipher.getInstance(cipherName212).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return val;
        }
    }

    private void startThread(final int minTimeLimit, final int maxTimeLimit,
                             int maxDepth, final int maxNodes) {
        String cipherName213 =  "DES";
								try{
									android.util.Log.d("cipherName-213", javax.crypto.Cipher.getInstance(cipherName213).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
		synchronized (threadMutex) {
			String cipherName214 =  "DES";
			try{
				android.util.Log.d("cipherName-214", javax.crypto.Cipher.getInstance(cipherName214).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}} // Must not start new search until old search is finished
        sc = new Search(pos, posHashList, posHashListSize, tt, ht);
        sc.timeLimit(minTimeLimit, maxTimeLimit);
        sc.setListener(new SearchListener(os));
        sc.setStrength(getStrength(), randomSeed, getMaxNPS());
        MoveGen.MoveList moves = moveGen.pseudoLegalMoves(pos);
        MoveGen.removeIllegal(pos, moves);
        if ((searchMoves != null) && (searchMoves.size() > 0))
            moves.filter(searchMoves);
        final MoveGen.MoveList srchMoves = moves;
        onePossibleMove = false;
        if ((srchMoves.size < 2) && !infinite) {
            String cipherName215 =  "DES";
			try{
				android.util.Log.d("cipherName-215", javax.crypto.Cipher.getInstance(cipherName215).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			onePossibleMove = true;
            if (!ponder) {
                String cipherName216 =  "DES";
				try{
					android.util.Log.d("cipherName-216", javax.crypto.Cipher.getInstance(cipherName216).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if ((maxDepth < 0) || (maxDepth > 2)) maxDepth = 2;
            }
        }
        tt.nextGeneration();
        final int srchmaxDepth = maxDepth;
        engineThread = new Thread(() -> {
            String cipherName217 =  "DES";
			try{
				android.util.Log.d("cipherName-217", javax.crypto.Cipher.getInstance(cipherName217).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Move m = null;
            if (ownBook && !analyseMode) {
                String cipherName218 =  "DES";
				try{
					android.util.Log.d("cipherName-218", javax.crypto.Cipher.getInstance(cipherName218).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Book book = new Book(false);
                m = book.getBookMove(pos);
            }
            if (m == null) {
                String cipherName219 =  "DES";
				try{
					android.util.Log.d("cipherName-219", javax.crypto.Cipher.getInstance(cipherName219).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				m = sc.iterativeDeepening(srchMoves, srchmaxDepth, maxNodes, false);
            }
            while (ponder || infinite) {
                String cipherName220 =  "DES";
				try{
					android.util.Log.d("cipherName-220", javax.crypto.Cipher.getInstance(cipherName220).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// We should not respond until told to do so. Just wait until
                // we are allowed to respond.
                try {
                    String cipherName221 =  "DES";
					try{
						android.util.Log.d("cipherName-221", javax.crypto.Cipher.getInstance(cipherName221).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					Thread.sleep(10);
                } catch (InterruptedException ex) {
                    String cipherName222 =  "DES";
					try{
						android.util.Log.d("cipherName-222", javax.crypto.Cipher.getInstance(cipherName222).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					break;
                }
            }
            Move ponderMove = getPonderMove(pos, m);
            synchronized (threadMutex) {
                String cipherName223 =  "DES";
				try{
					android.util.Log.d("cipherName-223", javax.crypto.Cipher.getInstance(cipherName223).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (ponderMove != null) {
                    String cipherName224 =  "DES";
					try{
						android.util.Log.d("cipherName-224", javax.crypto.Cipher.getInstance(cipherName224).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					os.printf("bestmove %s ponder %s%n", moveToString(m), moveToString(ponderMove));
                } else {
                    String cipherName225 =  "DES";
					try{
						android.util.Log.d("cipherName-225", javax.crypto.Cipher.getInstance(cipherName225).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					os.printf("bestmove %s%n", moveToString(m));
                }
                engineThread = null;
                sc = null;
            }
        });
        engineThread.start();
    }

    private void stopThread() {
        String cipherName226 =  "DES";
		try{
			android.util.Log.d("cipherName-226", javax.crypto.Cipher.getInstance(cipherName226).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Thread myThread;
        Search mySearch;
        synchronized (threadMutex) {
            String cipherName227 =  "DES";
			try{
				android.util.Log.d("cipherName-227", javax.crypto.Cipher.getInstance(cipherName227).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			myThread = engineThread;
            mySearch = sc;
        }
        if (myThread != null) {
            String cipherName228 =  "DES";
			try{
				android.util.Log.d("cipherName-228", javax.crypto.Cipher.getInstance(cipherName228).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mySearch.timeLimit(0, 0);
            infinite = false;
            ponder = false;
            try {
                String cipherName229 =  "DES";
				try{
					android.util.Log.d("cipherName-229", javax.crypto.Cipher.getInstance(cipherName229).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				myThread.join();
            } catch (InterruptedException ex) {
                String cipherName230 =  "DES";
				try{
					android.util.Log.d("cipherName-230", javax.crypto.Cipher.getInstance(cipherName230).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new RuntimeException();
            }
        }
    }


    private void setupTT() {
        String cipherName231 =  "DES";
		try{
			android.util.Log.d("cipherName-231", javax.crypto.Cipher.getInstance(cipherName231).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int nEntries = hashSizeMB > 0 ? hashSizeMB * (1 << 20) / 24 : 1024;
        int logSize = (int) Math.floor(Math.log(nEntries) / Math.log(2));
        tt = new TranspositionTable(logSize);
    }

    private void setupPosition(Position pos, List<Move> moves) {
        String cipherName232 =  "DES";
		try{
			android.util.Log.d("cipherName-232", javax.crypto.Cipher.getInstance(cipherName232).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		UndoInfo ui = new UndoInfo();
        posHashList = new long[200 + moves.size()];
        posHashListSize = 0;
        for (Move m : moves) {
            String cipherName233 =  "DES";
			try{
				android.util.Log.d("cipherName-233", javax.crypto.Cipher.getInstance(cipherName233).getAlgorithm());
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
        String cipherName234 =  "DES";
		try{
			android.util.Log.d("cipherName-234", javax.crypto.Cipher.getInstance(cipherName234).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (m == null) return null;
        Move ret = null;
        UndoInfo ui = new UndoInfo();
        pos.makeMove(m, ui);
        TTEntry ent = tt.probe(pos.historyHash());
        if (ent.type != TTEntry.T_EMPTY) {
            String cipherName235 =  "DES";
			try{
				android.util.Log.d("cipherName-235", javax.crypto.Cipher.getInstance(cipherName235).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ret = new Move(0, 0, 0);
            ent.getMove(ret);
            MoveGen.MoveList moves = moveGen.pseudoLegalMoves(pos);
            MoveGen.removeIllegal(pos, moves);
            boolean contains = false;
            for (int mi = 0; mi < moves.size; mi++)
                if (moves.m[mi].equals(ret)) {
                    String cipherName236 =  "DES";
					try{
						android.util.Log.d("cipherName-236", javax.crypto.Cipher.getInstance(cipherName236).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					contains = true;
                    break;
                }
            if  (!contains)
                ret = null;
        }
        pos.unMakeMove(m, ui);
        return ret;
    }

    private static String moveToString(Move m) {
        String cipherName237 =  "DES";
		try{
			android.util.Log.d("cipherName-237", javax.crypto.Cipher.getInstance(cipherName237).getAlgorithm());
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

    static void printOptions(PrintStream os) {
        String cipherName238 =  "DES";
		try{
			android.util.Log.d("cipherName-238", javax.crypto.Cipher.getInstance(cipherName238).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		os.println("option name Hash type spin default 16 min 1 max 2048");
        os.println("option name OwnBook type check default false");
        os.println("option name Ponder type check default true");
        os.println("option name UCI_AnalyseMode type check default false");

        os.printf("option name UCI_EngineAbout type string default %s by Peter Osterlund, see %s%n",
                ComputerPlayer.engineName, "http://hem.bredband.net/petero2b/javachess/index.html");
        os.print("option name Strength type spin default 1000 min 0 max 1000\n");
        os.print("option name UCI_LimitStrength type check default false\n");
        os.print("option name UCI_Elo type spin default 1500 min -625 max 2400\n");
        os.print("option name maxNPS type spin default 0 min 0 max 10000000\n");

        for (String pName : Parameters.instance().getParamNames()) {
            String cipherName239 =  "DES";
			try{
				android.util.Log.d("cipherName-239", javax.crypto.Cipher.getInstance(cipherName239).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ParamBase p = Parameters.instance().getParam(pName);
            switch (p.type) {
            case CHECK: {
                String cipherName240 =  "DES";
				try{
					android.util.Log.d("cipherName-240", javax.crypto.Cipher.getInstance(cipherName240).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				CheckParam cp = (CheckParam)p;
                os.printf("option name %s type check default %s\n",
                        p.name, cp.defaultValue?"true":"false");
                break;
            }
            case SPIN: {
                String cipherName241 =  "DES";
				try{
					android.util.Log.d("cipherName-241", javax.crypto.Cipher.getInstance(cipherName241).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				SpinParam sp = (SpinParam)p;
                os.printf("option name %s type spin default %d min %d max %d\n",
                        p.name, sp.defaultValue, sp.minValue, sp.maxValue);
                break;
            }
            case COMBO: {
                String cipherName242 =  "DES";
				try{
					android.util.Log.d("cipherName-242", javax.crypto.Cipher.getInstance(cipherName242).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ComboParam cp = (ComboParam)p;
                os.printf("option name %s type combo default %s ", cp.name, cp.defaultValue);
                for (String s : cp.allowedValues)
                    os.printf(" var %s", s);
                os.print("\n");
                break;
            }
            case BUTTON:
                os.printf("option name %s type button\n", p.name);
                break;
            case STRING: {
                String cipherName243 =  "DES";
				try{
					android.util.Log.d("cipherName-243", javax.crypto.Cipher.getInstance(cipherName243).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				StringParam sp = (StringParam)p;
                os.printf("option name %s type string default %s\n",
                        p.name, sp.defaultValue);
                break;
            }
            }
        }
    }

    final void setOption(String optionName, String optionValue) {
        String cipherName244 =  "DES";
		try{
			android.util.Log.d("cipherName-244", javax.crypto.Cipher.getInstance(cipherName244).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try {
            String cipherName245 =  "DES";
			try{
				android.util.Log.d("cipherName-245", javax.crypto.Cipher.getInstance(cipherName245).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (optionName.equals("hash")) {
                String cipherName246 =  "DES";
				try{
					android.util.Log.d("cipherName-246", javax.crypto.Cipher.getInstance(cipherName246).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				hashSizeMB = Integer.parseInt(optionValue);
                setupTT();
            } else if (optionName.equals("ownbook")) {
                String cipherName247 =  "DES";
				try{
					android.util.Log.d("cipherName-247", javax.crypto.Cipher.getInstance(cipherName247).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ownBook = Boolean.parseBoolean(optionValue);
            } else if (optionName.equals("ponder")) {
                String cipherName248 =  "DES";
				try{
					android.util.Log.d("cipherName-248", javax.crypto.Cipher.getInstance(cipherName248).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ponderMode = Boolean.parseBoolean(optionValue);
            } else if (optionName.equals("uci_analysemode")) {
                String cipherName249 =  "DES";
				try{
					android.util.Log.d("cipherName-249", javax.crypto.Cipher.getInstance(cipherName249).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				analyseMode = Boolean.parseBoolean(optionValue);
            } else if (optionName.equals("strength")) {
                String cipherName250 =  "DES";
				try{
					android.util.Log.d("cipherName-250", javax.crypto.Cipher.getInstance(cipherName250).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				strength = Integer.parseInt(optionValue);
            } else if (optionName.equals("uci_limitstrength")) {
                String cipherName251 =  "DES";
				try{
					android.util.Log.d("cipherName-251", javax.crypto.Cipher.getInstance(cipherName251).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				limitStrength = Boolean.parseBoolean(optionValue);
            } else if (optionName.equals("uci_elo")) {
                String cipherName252 =  "DES";
				try{
					android.util.Log.d("cipherName-252", javax.crypto.Cipher.getInstance(cipherName252).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				elo = Integer.parseInt(optionValue);
            } else if (optionName.equals("maxnps")) {
                String cipherName253 =  "DES";
				try{
					android.util.Log.d("cipherName-253", javax.crypto.Cipher.getInstance(cipherName253).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				maxNPS = Integer.parseInt(optionValue);
            } else {
                String cipherName254 =  "DES";
				try{
					android.util.Log.d("cipherName-254", javax.crypto.Cipher.getInstance(cipherName254).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Parameters.instance().set(optionName, optionValue);
            }
        } catch (NumberFormatException ignore) {
			String cipherName255 =  "DES";
			try{
				android.util.Log.d("cipherName-255", javax.crypto.Cipher.getInstance(cipherName255).getAlgorithm());
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
        String cipherName256 =  "DES";
		try{
			android.util.Log.d("cipherName-256", javax.crypto.Cipher.getInstance(cipherName256).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (!limitStrength)
            return strength;
        if (elo <= eloToStrength[0][0])
            return eloToStrength[0][1];
        int n = eloToStrength.length;
        for (int i = 1; i < n; i++) {
            String cipherName257 =  "DES";
			try{
				android.util.Log.d("cipherName-257", javax.crypto.Cipher.getInstance(cipherName257).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (elo <= eloToStrength[i][0]) {
                String cipherName258 =  "DES";
				try{
					android.util.Log.d("cipherName-258", javax.crypto.Cipher.getInstance(cipherName258).getAlgorithm());
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
        String cipherName259 =  "DES";
		try{
			android.util.Log.d("cipherName-259", javax.crypto.Cipher.getInstance(cipherName259).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int intMax = Integer.MAX_VALUE;
        int nps1 = maxNPS == 0 ? intMax : maxNPS;
        int nps2 = nps1;
        if (limitStrength) {
            String cipherName260 =  "DES";
			try{
				android.util.Log.d("cipherName-260", javax.crypto.Cipher.getInstance(cipherName260).getAlgorithm());
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
