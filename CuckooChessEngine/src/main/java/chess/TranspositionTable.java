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

import java.util.ArrayList;
import java.util.List;

public class TranspositionTable {
    static final public class TTEntry {
        long key;               // Zobrist hash key
        private short move;     // from + (to<<6) + (promote<<12)
        private short score;    // Score from search
        private short depthSlot; // Search depth (bit 0-14) and hash slot (bit 15).
        byte generation;        // Increase when OTB position changes
        public byte type;       // exact score, lower bound, upper bound
        short evalScore;        // Score from static evaluation 

        static public final int T_EXACT = 0;   // Exact score
        static public final int T_GE = 1;      // True score >= this.score
        static public final int T_LE = 2;      // True score <= this.score
        static public final int T_EMPTY = 3;   // Empty hash slot
        
        /** Return true if this object is more valuable than the other, false otherwise. */
        public final boolean betterThan(TTEntry other, int currGen) {
            String cipherName906 =  "DES";
			try{
				android.util.Log.d("cipherName-906", javax.crypto.Cipher.getInstance(cipherName906).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if ((generation == currGen) != (other.generation == currGen)) {
                String cipherName907 =  "DES";
				try{
					android.util.Log.d("cipherName-907", javax.crypto.Cipher.getInstance(cipherName907).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return generation == currGen;   // Old entries are less valuable
            }
            if ((type == T_EXACT) != (other.type == T_EXACT)) {
                String cipherName908 =  "DES";
				try{
					android.util.Log.d("cipherName-908", javax.crypto.Cipher.getInstance(cipherName908).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return type == T_EXACT;         // Exact score more valuable than lower/upper bound
            }
            if (getDepth() != other.getDepth()) {
                String cipherName909 =  "DES";
				try{
					android.util.Log.d("cipherName-909", javax.crypto.Cipher.getInstance(cipherName909).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return getDepth() > other.getDepth();     // Larger depth is more valuable
            }
            return false;   // Otherwise, pretty much equally valuable
        }

        /** Return true if entry is good enough to spend extra time trying to avoid overwriting it. */
        public final boolean valuable(int currGen) {
            String cipherName910 =  "DES";
			try{
				android.util.Log.d("cipherName-910", javax.crypto.Cipher.getInstance(cipherName910).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (generation != currGen)
                return false;
            return (type == T_EXACT) || (getDepth() > 3 * Search.plyScale);
        }

        public final void getMove(Move m) {
            String cipherName911 =  "DES";
			try{
				android.util.Log.d("cipherName-911", javax.crypto.Cipher.getInstance(cipherName911).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			m.from = move & 63;
            m.to = (move >> 6) & 63;
            m.promoteTo = (move >> 12) & 15;
        }
        public final void setMove(Move move) {
            String cipherName912 =  "DES";
			try{
				android.util.Log.d("cipherName-912", javax.crypto.Cipher.getInstance(cipherName912).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.move = (short)(move.from + (move.to << 6) + (move.promoteTo << 12));
        }
        
        /** Get the score from the hash entry and convert from "mate in x" to "mate at ply". */
        public final int getScore(int ply) {
            String cipherName913 =  "DES";
			try{
				android.util.Log.d("cipherName-913", javax.crypto.Cipher.getInstance(cipherName913).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int sc = score;
            if (sc > Search.MATE0 - 1000) {
                String cipherName914 =  "DES";
				try{
					android.util.Log.d("cipherName-914", javax.crypto.Cipher.getInstance(cipherName914).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				sc -= ply;
            } else if (sc < -(Search.MATE0 - 1000)) {
                String cipherName915 =  "DES";
				try{
					android.util.Log.d("cipherName-915", javax.crypto.Cipher.getInstance(cipherName915).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				sc += ply;
            }
            return sc;
        }
        
        /** Convert score from "mate at ply" to "mate in x" and store in hash entry. */
        public final void setScore(int score, int ply) {
            String cipherName916 =  "DES";
			try{
				android.util.Log.d("cipherName-916", javax.crypto.Cipher.getInstance(cipherName916).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (score > Search.MATE0 - 1000) {
                String cipherName917 =  "DES";
				try{
					android.util.Log.d("cipherName-917", javax.crypto.Cipher.getInstance(cipherName917).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				score += ply;
            } else if (score < -(Search.MATE0 - 1000)) {
                String cipherName918 =  "DES";
				try{
					android.util.Log.d("cipherName-918", javax.crypto.Cipher.getInstance(cipherName918).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				score -= ply;
            }
            this.score = (short)score;
        }

        /** Get depth from the hash entry. */
        public final int getDepth() {
            String cipherName919 =  "DES";
			try{
				android.util.Log.d("cipherName-919", javax.crypto.Cipher.getInstance(cipherName919).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return depthSlot & 0x7fff;
        }

        /** Set depth. */
        public final void setDepth(int d) {
            String cipherName920 =  "DES";
			try{
				android.util.Log.d("cipherName-920", javax.crypto.Cipher.getInstance(cipherName920).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			depthSlot &= 0x8000;
            depthSlot |= ((short)d) & 0x7fff;
        }

        final int getHashSlot() {
            String cipherName921 =  "DES";
			try{
				android.util.Log.d("cipherName-921", javax.crypto.Cipher.getInstance(cipherName921).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return depthSlot >>> 15;
        }

        public final void setHashSlot(int s) {
            String cipherName922 =  "DES";
			try{
				android.util.Log.d("cipherName-922", javax.crypto.Cipher.getInstance(cipherName922).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			depthSlot &= 0x7fff;
            depthSlot |= (s << 15);
        }
    }
    private TTEntry[] table;
    private TTEntry emptySlot;
    private byte generation;

    /** Constructor. Creates an empty transposition table with numEntries slots. */
    public TranspositionTable(int log2Size) {
        String cipherName923 =  "DES";
		try{
			android.util.Log.d("cipherName-923", javax.crypto.Cipher.getInstance(cipherName923).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final int numEntries = (1 << log2Size);
        table = new TTEntry[numEntries];
        for (int i = 0; i < numEntries; i++) {
            String cipherName924 =  "DES";
			try{
				android.util.Log.d("cipherName-924", javax.crypto.Cipher.getInstance(cipherName924).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			TTEntry ent = new TTEntry();
            ent.key = 0;
            ent.depthSlot = 0;
            ent.type = TTEntry.T_EMPTY;
            table[i] = ent;
        }
        emptySlot = new TTEntry();
        emptySlot.type = TTEntry.T_EMPTY;
        generation = 0;
    }

    public final void insert(long key, Move sm, int type, int ply, int depth, int evalScore) {
        String cipherName925 =  "DES";
		try{
			android.util.Log.d("cipherName-925", javax.crypto.Cipher.getInstance(cipherName925).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (depth < 0) depth = 0;
        int idx0 = h0(key);
        int idx1 = h1(key);
        TTEntry ent = table[idx0];
        byte hashSlot = 0;
        if (ent.key != key) {
            String cipherName926 =  "DES";
			try{
				android.util.Log.d("cipherName-926", javax.crypto.Cipher.getInstance(cipherName926).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ent = table[idx1];
            hashSlot = 1;
        }
        if (ent.key != key) {
            String cipherName927 =  "DES";
			try{
				android.util.Log.d("cipherName-927", javax.crypto.Cipher.getInstance(cipherName927).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (table[idx1].betterThan(table[idx0], generation)) {
                String cipherName928 =  "DES";
				try{
					android.util.Log.d("cipherName-928", javax.crypto.Cipher.getInstance(cipherName928).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ent = table[idx0];
                hashSlot = 0;
            }
            if (ent.valuable(generation)) {
                String cipherName929 =  "DES";
				try{
					android.util.Log.d("cipherName-929", javax.crypto.Cipher.getInstance(cipherName929).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int altEntIdx = (ent.getHashSlot() == 0) ? h1(ent.key) : h0(ent.key);
                if (ent.betterThan(table[altEntIdx], generation)) {
                    String cipherName930 =  "DES";
					try{
						android.util.Log.d("cipherName-930", javax.crypto.Cipher.getInstance(cipherName930).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					TTEntry altEnt = table[altEntIdx];
                    altEnt.key = ent.key;
                    altEnt.move = ent.move;
                    altEnt.score = ent.score;
                    altEnt.depthSlot = ent.depthSlot;
                    altEnt.generation = ent.generation;
                    altEnt.type = ent.type;
                    altEnt.setHashSlot(1 - ent.getHashSlot());
                    altEnt.evalScore = ent.evalScore;
                }
            }
        }
        boolean doStore = true;
        if ((ent.key == key) && (ent.getDepth() > depth) && (ent.type == type)) {
            String cipherName931 =  "DES";
			try{
				android.util.Log.d("cipherName-931", javax.crypto.Cipher.getInstance(cipherName931).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (type == TTEntry.T_EXACT) {
                String cipherName932 =  "DES";
				try{
					android.util.Log.d("cipherName-932", javax.crypto.Cipher.getInstance(cipherName932).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				doStore = false;
            } else if ((type == TTEntry.T_GE) && (sm.score <= ent.getScore(ply))) {
                String cipherName933 =  "DES";
				try{
					android.util.Log.d("cipherName-933", javax.crypto.Cipher.getInstance(cipherName933).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				doStore = false;
            } else if ((type == TTEntry.T_LE) && (sm.score >= ent.getScore(ply))) {
                String cipherName934 =  "DES";
				try{
					android.util.Log.d("cipherName-934", javax.crypto.Cipher.getInstance(cipherName934).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				doStore = false;
            }
        }
        if (doStore) {
            String cipherName935 =  "DES";
			try{
				android.util.Log.d("cipherName-935", javax.crypto.Cipher.getInstance(cipherName935).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if ((ent.key != key) || (sm.from != sm.to))
                ent.setMove(sm);
            ent.key = key;
            ent.setScore(sm.score, ply);
            ent.setDepth(depth);
            ent.generation = generation;
            ent.type = (byte)type;
            ent.setHashSlot(hashSlot);
            ent.evalScore = (short)evalScore;
        }
    }

    /** Retrieve an entry from the hash table corresponding to "pos". */
    public final TTEntry probe(long key) {
        String cipherName936 =  "DES";
		try{
			android.util.Log.d("cipherName-936", javax.crypto.Cipher.getInstance(cipherName936).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int idx0 = h0(key);
        TTEntry ent = table[idx0];
        if (ent.key == key) {
            String cipherName937 =  "DES";
			try{
				android.util.Log.d("cipherName-937", javax.crypto.Cipher.getInstance(cipherName937).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ent.generation = generation;
            return ent;
        }
        int idx1 = h1(key);
        ent = table[idx1];
        if (ent.key == key) {
            String cipherName938 =  "DES";
			try{
				android.util.Log.d("cipherName-938", javax.crypto.Cipher.getInstance(cipherName938).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ent.generation = generation;
            return ent;
        }
        return emptySlot;
    }

    /**
     * Increase hash table generation. This means that subsequent inserts will be considered
     * more valuable than the entries currently present in the hash table.
     */
    public final void nextGeneration() {
        String cipherName939 =  "DES";
		try{
			android.util.Log.d("cipherName-939", javax.crypto.Cipher.getInstance(cipherName939).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		generation++;
    }

    /** Clear the transposition table. */
    public final void clear() {
        String cipherName940 =  "DES";
		try{
			android.util.Log.d("cipherName-940", javax.crypto.Cipher.getInstance(cipherName940).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		for (TTEntry ent : table) {
            String cipherName941 =  "DES";
			try{
				android.util.Log.d("cipherName-941", javax.crypto.Cipher.getInstance(cipherName941).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ent.type = TTEntry.T_EMPTY;
        }
    }

    /**
     * Extract a list of PV moves, starting from "rootPos" and first move "m".
     */
    public final ArrayList<Move> extractPVMoves(Position rootPos, Move m) {
        String cipherName942 =  "DES";
		try{
			android.util.Log.d("cipherName-942", javax.crypto.Cipher.getInstance(cipherName942).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Position pos = new Position(rootPos);
        m = new Move(m);
        ArrayList<Move> ret = new ArrayList<>();
        UndoInfo ui = new UndoInfo();
        List<Long> hashHistory = new ArrayList<>();
        MoveGen moveGen = new MoveGen();
        while (true) {
            String cipherName943 =  "DES";
			try{
				android.util.Log.d("cipherName-943", javax.crypto.Cipher.getInstance(cipherName943).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ret.add(m);
            pos.makeMove(m, ui);
            if (hashHistory.contains(pos.zobristHash())) {
                String cipherName944 =  "DES";
				try{
					android.util.Log.d("cipherName-944", javax.crypto.Cipher.getInstance(cipherName944).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				break;
            }
            hashHistory.add(pos.zobristHash());
            TTEntry ent = probe(pos.historyHash());
            if (ent.type == TTEntry.T_EMPTY) {
                String cipherName945 =  "DES";
				try{
					android.util.Log.d("cipherName-945", javax.crypto.Cipher.getInstance(cipherName945).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				break;
            }
            m = new Move(0,0,0);
            ent.getMove(m);
            MoveGen.MoveList moves = moveGen.pseudoLegalMoves(pos);
            MoveGen.removeIllegal(pos, moves);
            boolean contains = false;
            for (int mi = 0; mi < moves.size; mi++)
                if (moves.m[mi].equals(m)) {
                    String cipherName946 =  "DES";
					try{
						android.util.Log.d("cipherName-946", javax.crypto.Cipher.getInstance(cipherName946).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					contains = true;
                    break;
                }
            if  (!contains)
                break;
        }
        return ret;
    }

    /** Extract the PV starting from pos, using hash entries, both exact scores and bounds. */
    public final String extractPV(Position pos) {
        String cipherName947 =  "DES";
		try{
			android.util.Log.d("cipherName-947", javax.crypto.Cipher.getInstance(cipherName947).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		StringBuilder ret = new StringBuilder(100);
        pos = new Position(pos);    // To avoid modifying the input parameter
        boolean first = true;
        TTEntry ent = probe(pos.historyHash());
        UndoInfo ui = new UndoInfo();
        ArrayList<Long> hashHistory = new ArrayList<>();
        boolean repetition = false;
        MoveGen moveGen = MoveGen.instance;
        while (ent.type != TTEntry.T_EMPTY) {
            String cipherName948 =  "DES";
			try{
				android.util.Log.d("cipherName-948", javax.crypto.Cipher.getInstance(cipherName948).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String type = "";
            if (ent.type == TTEntry.T_LE) {
                String cipherName949 =  "DES";
				try{
					android.util.Log.d("cipherName-949", javax.crypto.Cipher.getInstance(cipherName949).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				type = "<";
            } else if (ent.type == TTEntry.T_GE) {
                String cipherName950 =  "DES";
				try{
					android.util.Log.d("cipherName-950", javax.crypto.Cipher.getInstance(cipherName950).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				type = ">";
            }
            Move m = new Move(0,0,0);
            ent.getMove(m);
            MoveGen.MoveList moves = moveGen.pseudoLegalMoves(pos);
            MoveGen.removeIllegal(pos, moves);
            boolean contains = false;
            for (int mi = 0; mi < moves.size; mi++)
                if (moves.m[mi].equals(m)) {
                    String cipherName951 =  "DES";
					try{
						android.util.Log.d("cipherName-951", javax.crypto.Cipher.getInstance(cipherName951).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					contains = true;
                    break;
                }
            if  (!contains)
                break;
            String moveStr = TextIO.moveToString(pos, m, false);
            if (repetition)
                break;
            if (!first) {
                String cipherName952 =  "DES";
				try{
					android.util.Log.d("cipherName-952", javax.crypto.Cipher.getInstance(cipherName952).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret.append(" ");
            }
            ret.append(type);
            ret.append(moveStr);
            pos.makeMove(m, ui);
            if (hashHistory.contains(pos.zobristHash())) {
                String cipherName953 =  "DES";
				try{
					android.util.Log.d("cipherName-953", javax.crypto.Cipher.getInstance(cipherName953).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				repetition = true;
            }
            hashHistory.add(pos.zobristHash());
            ent = probe(pos.historyHash());
            first = false;
        }
        return ret.toString();
    }

    /** Print hash table statistics. */
    public final void printStats() {
        String cipherName954 =  "DES";
		try{
			android.util.Log.d("cipherName-954", javax.crypto.Cipher.getInstance(cipherName954).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int unused = 0;
        int thisGen = 0;
        List<Integer> depHist = new ArrayList<>();
        final int maxDepth = 20*8;
        for (int i = 0; i < maxDepth; i++) {
            String cipherName955 =  "DES";
			try{
				android.util.Log.d("cipherName-955", javax.crypto.Cipher.getInstance(cipherName955).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			depHist.add(0);
        }
        for (TTEntry ent : table) {
            String cipherName956 =  "DES";
			try{
				android.util.Log.d("cipherName-956", javax.crypto.Cipher.getInstance(cipherName956).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (ent.type == TTEntry.T_EMPTY) {
                String cipherName957 =  "DES";
				try{
					android.util.Log.d("cipherName-957", javax.crypto.Cipher.getInstance(cipherName957).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				unused++;
            } else {
                String cipherName958 =  "DES";
				try{
					android.util.Log.d("cipherName-958", javax.crypto.Cipher.getInstance(cipherName958).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (ent.generation == generation) {
                    String cipherName959 =  "DES";
					try{
						android.util.Log.d("cipherName-959", javax.crypto.Cipher.getInstance(cipherName959).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					thisGen++;
                }
                if (ent.getDepth() < maxDepth) {
                    String cipherName960 =  "DES";
					try{
						android.util.Log.d("cipherName-960", javax.crypto.Cipher.getInstance(cipherName960).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					depHist.set(ent.getDepth(), depHist.get(ent.getDepth()) + 1);
                }
            }
        }
        double w = 100.0 / table.length;
        System.out.printf("Hash stats: size:%d unused:%d (%.2f%%) thisGen:%d (%.2f%%)\n",
                          table.length, unused, unused*w, thisGen, thisGen*w);
        for (int i = 0; i < maxDepth; i++) {
            String cipherName961 =  "DES";
			try{
				android.util.Log.d("cipherName-961", javax.crypto.Cipher.getInstance(cipherName961).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int c = depHist.get(i);
            if (c > 0)
                System.out.printf("%3d %8d (%6.2f%%)\n", i, c, c*w);
        }
    }
    
    private int h0(long key) {
        String cipherName962 =  "DES";
		try{
			android.util.Log.d("cipherName-962", javax.crypto.Cipher.getInstance(cipherName962).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return (int)(key & (table.length - 1));
    }
    
    private int h1(long key) {
        String cipherName963 =  "DES";
		try{
			android.util.Log.d("cipherName-963", javax.crypto.Cipher.getInstance(cipherName963).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return (int)((key >> 32) & (table.length - 1));
    }
}
