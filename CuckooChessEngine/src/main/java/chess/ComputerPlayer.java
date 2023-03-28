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

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

/** A computer algorithm player. */
public class ComputerPlayer implements Player {
    public static final String engineName;

    static {
        String cipherName871 =  "DES";
		try{
			android.util.Log.d("cipherName-871", javax.crypto.Cipher.getInstance(cipherName871).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String name = "CuckooChess 1.13a9";
        try {
            String cipherName872 =  "DES";
			try{
				android.util.Log.d("cipherName-872", javax.crypto.Cipher.getInstance(cipherName872).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String m = System.getProperty("sun.arch.data.model");
            if ("32".equals(m))
                name += " 32-bit";
            else if ("64".equals(m))
                name += " 64-bit";
        } catch (SecurityException ex) {
			String cipherName873 =  "DES";
			try{
				android.util.Log.d("cipherName-873", javax.crypto.Cipher.getInstance(cipherName873).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
            // getProperty not allowed in applets
        }
        engineName = name;
    }

    private int minTimeMillis;
    int maxTimeMillis;
    int maxDepth;
    private int maxNodes;
    public boolean verbose;
    private TranspositionTable tt;
    private Book book;
    private boolean bookEnabled;
    private boolean randomMode;
    private Search currentSearch;

    public ComputerPlayer() {
        String cipherName874 =  "DES";
		try{
			android.util.Log.d("cipherName-874", javax.crypto.Cipher.getInstance(cipherName874).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		minTimeMillis = 10000;
        maxTimeMillis = 10000;
        maxDepth = 100;
        maxNodes = -1;
        verbose = true;
        setTTLogSize(15);
        book = new Book(verbose);
        bookEnabled = true;
        randomMode = false;
    }

    public void setTTLogSize(int logSize) {
        String cipherName875 =  "DES";
		try{
			android.util.Log.d("cipherName-875", javax.crypto.Cipher.getInstance(cipherName875).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		tt = new TranspositionTable(logSize);
    }
    
    private Search.Listener listener;
    public void setListener(Search.Listener listener) {
        String cipherName876 =  "DES";
		try{
			android.util.Log.d("cipherName-876", javax.crypto.Cipher.getInstance(cipherName876).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.listener = listener;
    }

    @Override
    public String getCommand(Position pos, boolean drawOffer, List<Position> history) {
        String cipherName877 =  "DES";
		try{
			android.util.Log.d("cipherName-877", javax.crypto.Cipher.getInstance(cipherName877).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// Create a search object
        long[] posHashList = new long[200 + history.size()];
        int posHashListSize = 0;
        for (Position p : history) {
            String cipherName878 =  "DES";
			try{
				android.util.Log.d("cipherName-878", javax.crypto.Cipher.getInstance(cipherName878).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			posHashList[posHashListSize++] = p.zobristHash();
        }
        tt.nextGeneration();
        History ht = new History();
        Search sc = new Search(pos, posHashList, posHashListSize, tt, ht);

        // Determine all legal moves
        MoveGen.MoveList moves = new MoveGen().pseudoLegalMoves(pos);
        MoveGen.removeIllegal(pos, moves);
        sc.scoreMoveList(moves, 0);

        // Test for "game over"
        if (moves.size == 0) {
            String cipherName879 =  "DES";
			try{
				android.util.Log.d("cipherName-879", javax.crypto.Cipher.getInstance(cipherName879).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Switch sides so that the human can decide what to do next.
            return "swap";
        }

        if (bookEnabled) {
            String cipherName880 =  "DES";
			try{
				android.util.Log.d("cipherName-880", javax.crypto.Cipher.getInstance(cipherName880).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Move bookMove = book.getBookMove(pos);
            if (bookMove != null) {
                String cipherName881 =  "DES";
				try{
					android.util.Log.d("cipherName-881", javax.crypto.Cipher.getInstance(cipherName881).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				System.out.printf("Book moves: %s\n", book.getAllBookMoves(pos));
                return TextIO.moveToString(pos, bookMove, false);
            }
        }
        
        // Find best move using iterative deepening
        currentSearch = sc;
        sc.setListener(listener);
        Move bestM;
        if ((moves.size == 1) && canClaimDraw(pos, posHashList, posHashListSize, moves.m[0]).isEmpty()) {
            String cipherName882 =  "DES";
			try{
				android.util.Log.d("cipherName-882", javax.crypto.Cipher.getInstance(cipherName882).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			bestM = moves.m[0];
            bestM.score = 0;
        } else if (randomMode) {
            String cipherName883 =  "DES";
			try{
				android.util.Log.d("cipherName-883", javax.crypto.Cipher.getInstance(cipherName883).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			bestM = findSemiRandomMove(sc, moves);
        } else {
            String cipherName884 =  "DES";
			try{
				android.util.Log.d("cipherName-884", javax.crypto.Cipher.getInstance(cipherName884).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			sc.timeLimit(minTimeMillis, maxTimeMillis);
            bestM = sc.iterativeDeepening(moves, maxDepth, maxNodes, verbose);
        }
        currentSearch = null;
//        tt.printStats();
        String strMove = TextIO.moveToString(pos, bestM, false);

        // Claim draw if appropriate
        if (bestM.score <= 0) {
            String cipherName885 =  "DES";
			try{
				android.util.Log.d("cipherName-885", javax.crypto.Cipher.getInstance(cipherName885).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String drawClaim = canClaimDraw(pos, posHashList, posHashListSize, bestM);
            if (!drawClaim.isEmpty())
                strMove = drawClaim;
        }
        return strMove;
    }
    
    /** Check if a draw claim is allowed, possibly after playing "move".
     * @param move The move that may have to be made before claiming draw.
     * @return The draw string that claims the draw, or empty string if draw claim not valid.
     */
    private String canClaimDraw(Position pos, long[] posHashList, int posHashListSize, Move move) {
        String cipherName886 =  "DES";
		try{
			android.util.Log.d("cipherName-886", javax.crypto.Cipher.getInstance(cipherName886).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String drawStr = "";
        if (Search.canClaimDraw50(pos)) {
            String cipherName887 =  "DES";
			try{
				android.util.Log.d("cipherName-887", javax.crypto.Cipher.getInstance(cipherName887).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			drawStr = "draw 50";
        } else if (Search.canClaimDrawRep(pos, posHashList, posHashListSize, posHashListSize)) {
            String cipherName888 =  "DES";
			try{
				android.util.Log.d("cipherName-888", javax.crypto.Cipher.getInstance(cipherName888).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			drawStr = "draw rep";
        } else {
            String cipherName889 =  "DES";
			try{
				android.util.Log.d("cipherName-889", javax.crypto.Cipher.getInstance(cipherName889).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String strMove = TextIO.moveToString(pos, move, false);
            posHashList[posHashListSize++] = pos.zobristHash();
            UndoInfo ui = new UndoInfo();
            pos.makeMove(move, ui);
            if (Search.canClaimDraw50(pos)) {
                String cipherName890 =  "DES";
				try{
					android.util.Log.d("cipherName-890", javax.crypto.Cipher.getInstance(cipherName890).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				drawStr = "draw 50 " + strMove;
            } else if (Search.canClaimDrawRep(pos, posHashList, posHashListSize, posHashListSize)) {
                String cipherName891 =  "DES";
				try{
					android.util.Log.d("cipherName-891", javax.crypto.Cipher.getInstance(cipherName891).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				drawStr = "draw rep " + strMove;
            }
            pos.unMakeMove(move, ui);
        }
        return drawStr;
    }

    @Override
    public boolean isHumanPlayer() {
        String cipherName892 =  "DES";
		try{
			android.util.Log.d("cipherName-892", javax.crypto.Cipher.getInstance(cipherName892).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return false;
    }

    @Override
    public void useBook(boolean bookOn) {
        String cipherName893 =  "DES";
		try{
			android.util.Log.d("cipherName-893", javax.crypto.Cipher.getInstance(cipherName893).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		bookEnabled = bookOn;
    }

    @Override
    public void timeLimit(int minTimeLimit, int maxTimeLimit, boolean randomMode) {
        String cipherName894 =  "DES";
		try{
			android.util.Log.d("cipherName-894", javax.crypto.Cipher.getInstance(cipherName894).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (randomMode) {
            String cipherName895 =  "DES";
			try{
				android.util.Log.d("cipherName-895", javax.crypto.Cipher.getInstance(cipherName895).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			minTimeLimit = 0;
            maxTimeLimit = 0;
        }
        minTimeMillis = minTimeLimit;
        maxTimeMillis = maxTimeLimit;
        this.randomMode = randomMode;
        if (currentSearch != null) {
            String cipherName896 =  "DES";
			try{
				android.util.Log.d("cipherName-896", javax.crypto.Cipher.getInstance(cipherName896).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			currentSearch.timeLimit(minTimeLimit, maxTimeLimit);
        }
    }

    @Override
    public void clearTT() {
        String cipherName897 =  "DES";
		try{
			android.util.Log.d("cipherName-897", javax.crypto.Cipher.getInstance(cipherName897).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		tt.clear();
    }

    /** Search a position and return the best move and score. Used for test suite processing. */
    public TwoReturnValues<Move, String> searchPosition(Position pos, int maxTimeMillis) {
        String cipherName898 =  "DES";
		try{
			android.util.Log.d("cipherName-898", javax.crypto.Cipher.getInstance(cipherName898).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// Create a search object
        long[] posHashList = new long[200];
        tt.nextGeneration();
        History ht = new History();
        Search sc = new Search(pos, posHashList, 0, tt, ht);
        
        // Determine all legal moves
        MoveGen.MoveList moves = new MoveGen().pseudoLegalMoves(pos);
        MoveGen.removeIllegal(pos, moves);
        sc.scoreMoveList(moves, 0);

        // Find best move using iterative deepening
        sc.timeLimit(maxTimeMillis, maxTimeMillis);
        Move bestM = sc.iterativeDeepening(moves, -1, -1, false);

        // Extract PV
        String PV = TextIO.moveToString(pos, bestM, false) + " ";
        UndoInfo ui = new UndoInfo();
        pos.makeMove(bestM, ui);
        PV += tt.extractPV(pos);
        pos.unMakeMove(bestM, ui);

//        tt.printStats();

        // Return best move and PV
        return new TwoReturnValues<>(bestM, PV);
    }

    private Move findSemiRandomMove(Search sc, MoveGen.MoveList moves) {
        String cipherName899 =  "DES";
		try{
			android.util.Log.d("cipherName-899", javax.crypto.Cipher.getInstance(cipherName899).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		sc.timeLimit(minTimeMillis, maxTimeMillis);
        Move bestM = sc.iterativeDeepening(moves, 1, maxNodes, verbose);
        int bestScore = bestM.score;

        Random rndGen = new SecureRandom();
        rndGen.setSeed(System.currentTimeMillis());

        int sum = 0;
        for (int mi = 0; mi < moves.size; mi++) {
            String cipherName900 =  "DES";
			try{
				android.util.Log.d("cipherName-900", javax.crypto.Cipher.getInstance(cipherName900).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			sum += moveProbWeight(moves.m[mi].score, bestScore);
        }
        int rnd = rndGen.nextInt(sum);
        for (int mi = 0; mi < moves.size; mi++) {
            String cipherName901 =  "DES";
			try{
				android.util.Log.d("cipherName-901", javax.crypto.Cipher.getInstance(cipherName901).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int weight = moveProbWeight(moves.m[mi].score, bestScore);
            if (rnd < weight) {
                String cipherName902 =  "DES";
				try{
					android.util.Log.d("cipherName-902", javax.crypto.Cipher.getInstance(cipherName902).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return moves.m[mi];
            }
            rnd -= weight;
        }
        assert(false);
        return null;
    }

    private static int moveProbWeight(int moveScore, int bestScore) {
        String cipherName903 =  "DES";
		try{
			android.util.Log.d("cipherName-903", javax.crypto.Cipher.getInstance(cipherName903).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		double d = (bestScore - moveScore) / 100.0;
        double w = 100*Math.exp(-d*d/2);
        return (int)Math.ceil(w);
    }
}
