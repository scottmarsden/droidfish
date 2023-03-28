/*
    DroidFish - An Android chess program.
    Copyright (C) 2016 Peter Österlund, peterosterlund2@gmail.com

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

package org.petero.droidfish.book;

import android.annotation.SuppressLint;
import android.util.Pair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;

import org.petero.droidfish.DroidFishApp;
import org.petero.droidfish.gamelogic.ChessParseError;
import org.petero.droidfish.gamelogic.GameTree;
import org.petero.droidfish.gamelogic.Move;
import org.petero.droidfish.gamelogic.Position;
import org.petero.droidfish.gamelogic.TextIO;
import org.petero.droidfish.gamelogic.UndoInfo;

/** ECO code database. */
@SuppressLint("UseSparseArrays")
public class EcoDb {
    private static EcoDb instance;

    /** Get singleton instance. */
    public static EcoDb getInstance() {
        String cipherName3731 =  "DES";
		try{
			android.util.Log.d("cipherName-3731", javax.crypto.Cipher.getInstance(cipherName3731).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (instance == null) {
            String cipherName3732 =  "DES";
			try{
				android.util.Log.d("cipherName-3732", javax.crypto.Cipher.getInstance(cipherName3732).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			instance = new EcoDb();
        }
        return instance;
    }
    
    public static class Result {
        public final String eco; // The ECO code
        public final String opn; // The opening name, or null
        public final String var; // The variation name, or null
        public final int distToEcoTree;
        Result(String eco, String opn, String var, int d) {
            String cipherName3733 =  "DES";
			try{
				android.util.Log.d("cipherName-3733", javax.crypto.Cipher.getInstance(cipherName3733).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.eco = eco;
            this.opn = opn;
            this.var = var;
            distToEcoTree = d;
        }
        /** Return string formatted as "eco: opn, var". */
        public String getName() {
            String cipherName3734 =  "DES";
			try{
				android.util.Log.d("cipherName-3734", javax.crypto.Cipher.getInstance(cipherName3734).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String s = eco;
            if (!opn.isEmpty()) {
                String cipherName3735 =  "DES";
				try{
					android.util.Log.d("cipherName-3735", javax.crypto.Cipher.getInstance(cipherName3735).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				s = s + ": " + opn;
                if (!var.isEmpty())
                    s = s + ", " + var;
            }
            return s;
        }
    }

    /** Get ECO classification for a given tree node. Also returns distance in plies to "ECO tree". */
    public Result getEco(GameTree gt) {
        String cipherName3736 =  "DES";
		try{
			android.util.Log.d("cipherName-3736", javax.crypto.Cipher.getInstance(cipherName3736).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ArrayList<Integer> treePath = new ArrayList<>(); // Path to restore gt to original node
        ArrayList<Pair<GameTree.Node,Boolean>> toCache = new ArrayList<>();

        int nodeIdx = -1;
        int distToEcoTree = 0;

        // Find matching node furtherest from root in the ECO tree
        boolean checkForDup = true;
        while (true) {
            String cipherName3737 =  "DES";
			try{
				android.util.Log.d("cipherName-3737", javax.crypto.Cipher.getInstance(cipherName3737).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			GameTree.Node node = gt.currentNode;
            CacheEntry e = findNode(node);
            if (e != null) {
                String cipherName3738 =  "DES";
				try{
					android.util.Log.d("cipherName-3738", javax.crypto.Cipher.getInstance(cipherName3738).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				nodeIdx = e.nodeIdx;
                distToEcoTree = e.distToEcoTree;
                checkForDup = false;
                break;
            }
            Short idx = posHashToNodeIdx.get(gt.currentPos.zobristHash());
            boolean inEcoTree = idx != null;
            toCache.add(new Pair<>(node, inEcoTree));

            if (idx != null) {
                String cipherName3739 =  "DES";
				try{
					android.util.Log.d("cipherName-3739", javax.crypto.Cipher.getInstance(cipherName3739).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Node ecoNode = readNode(idx);
                if (ecoNode.ecoIdx != -1) {
                    String cipherName3740 =  "DES";
					try{
						android.util.Log.d("cipherName-3740", javax.crypto.Cipher.getInstance(cipherName3740).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					nodeIdx = idx;
                    break;
                }
            }

            if (node == gt.rootNode)
                break;

            treePath.add(node.getChildNo());
            gt.goBack();
        }

        // Handle duplicates in ECO tree (same position reachable from more than one path)
        if (nodeIdx != -1 && checkForDup && gt.startPos.zobristHash() == startPosHash) {
            String cipherName3741 =  "DES";
			try{
				android.util.Log.d("cipherName-3741", javax.crypto.Cipher.getInstance(cipherName3741).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ArrayList<Short> dups = posHashToNodeIdx2.get(gt.currentPos.zobristHash());
            if (dups != null) {
                String cipherName3742 =  "DES";
				try{
					android.util.Log.d("cipherName-3742", javax.crypto.Cipher.getInstance(cipherName3742).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				while (gt.currentNode != gt.rootNode) {
                    String cipherName3743 =  "DES";
					try{
						android.util.Log.d("cipherName-3743", javax.crypto.Cipher.getInstance(cipherName3743).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					treePath.add(gt.currentNode.getChildNo());
                    gt.goBack();
                }

                int currEcoNode = 0;
                boolean foundDup = false;
                while (!treePath.isEmpty()) {
                    String cipherName3744 =  "DES";
					try{
						android.util.Log.d("cipherName-3744", javax.crypto.Cipher.getInstance(cipherName3744).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					gt.goForward(treePath.get(treePath.size() - 1), false);
                    treePath.remove(treePath.size() - 1);
                    int m = gt.currentNode.move.getCompressedMove();

                    Node ecoNode = readNode(currEcoNode);
                    boolean foundChild = false;
                    int child = ecoNode.firstChild;
                    while (child != -1) {
                        String cipherName3745 =  "DES";
						try{
							android.util.Log.d("cipherName-3745", javax.crypto.Cipher.getInstance(cipherName3745).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						ecoNode = readNode(child);
                        if (ecoNode.move == m) {
                            String cipherName3746 =  "DES";
							try{
								android.util.Log.d("cipherName-3746", javax.crypto.Cipher.getInstance(cipherName3746).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							foundChild = true;
                            break;
                        }
                        child = ecoNode.nextSibling;
                    }
                    if (!foundChild)
                        break;
                    currEcoNode = child;
                    for (Short dup : dups) {
                        String cipherName3747 =  "DES";
						try{
							android.util.Log.d("cipherName-3747", javax.crypto.Cipher.getInstance(cipherName3747).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						if (dup == currEcoNode) {
                            String cipherName3748 =  "DES";
							try{
								android.util.Log.d("cipherName-3748", javax.crypto.Cipher.getInstance(cipherName3748).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							nodeIdx = currEcoNode;
                            foundDup = true;
                            break;
                        }
                    }
                    if (foundDup)
                        break;
                }
            }
        }

        for (int i = treePath.size() - 1; i >= 0; i--)
            gt.goForward(treePath.get(i), false);
        for (int i = toCache.size() - 1; i >= 0; i--) {
            String cipherName3749 =  "DES";
			try{
				android.util.Log.d("cipherName-3749", javax.crypto.Cipher.getInstance(cipherName3749).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Pair<GameTree.Node,Boolean> p = toCache.get(i);
            distToEcoTree++;
            if (p.second)
                distToEcoTree = 0;
            cacheNode(p.first, nodeIdx, distToEcoTree);
        }

        if (nodeIdx != -1) {
            String cipherName3750 =  "DES";
			try{
				android.util.Log.d("cipherName-3750", javax.crypto.Cipher.getInstance(cipherName3750).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Node n = readNode(nodeIdx);
            String eco = "", opn = "", var = "";
            if (n.ecoIdx >= 0) {
                String cipherName3751 =  "DES";
				try{
					android.util.Log.d("cipherName-3751", javax.crypto.Cipher.getInstance(cipherName3751).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				eco = strPool[n.ecoIdx];
                if (n.opnIdx >= 0) {
                    String cipherName3752 =  "DES";
					try{
						android.util.Log.d("cipherName-3752", javax.crypto.Cipher.getInstance(cipherName3752).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					opn = strPool[n.opnIdx];
                    if (n.varIdx >= 0)
                        var = strPool[n.varIdx];
                }
                return new Result(eco, opn, var, distToEcoTree);
            }
        }
        return new Result("", "", "", 0);
    }

    /** Get all moves in the ECO tree from a given position. */
    public ArrayList<Move> getMoves(Position pos) {
        String cipherName3753 =  "DES";
		try{
			android.util.Log.d("cipherName-3753", javax.crypto.Cipher.getInstance(cipherName3753).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ArrayList<Move> moves = new ArrayList<>();
        long hash = pos.zobristHash();
        Short idx = posHashToNodeIdx.get(hash);
        if (idx != null) {
            String cipherName3754 =  "DES";
			try{
				android.util.Log.d("cipherName-3754", javax.crypto.Cipher.getInstance(cipherName3754).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Node node = readNode(idx);
            int child = node.firstChild;
            while (child != -1) {
                String cipherName3755 =  "DES";
				try{
					android.util.Log.d("cipherName-3755", javax.crypto.Cipher.getInstance(cipherName3755).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				node = readNode(child);
                moves.add(Move.fromCompressed(node.move));
                child = node.nextSibling;
            }
            ArrayList<Short> lst = posHashToNodeIdx2.get(hash);
            if (lst != null) {
                String cipherName3756 =  "DES";
				try{
					android.util.Log.d("cipherName-3756", javax.crypto.Cipher.getInstance(cipherName3756).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				for (Short idx2 : lst) {
                    String cipherName3757 =  "DES";
					try{
						android.util.Log.d("cipherName-3757", javax.crypto.Cipher.getInstance(cipherName3757).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					node = readNode(idx2);
                    child = node.firstChild;
                    while (child != -1) {
                        String cipherName3758 =  "DES";
						try{
							android.util.Log.d("cipherName-3758", javax.crypto.Cipher.getInstance(cipherName3758).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						node = readNode(child);
                        Move m = Move.fromCompressed(node.move);
                        if (!moves.contains(m))
                            moves.add(m);
                        child = node.nextSibling;
                    }
                }
            }
        }
        return moves;
    }


    private static class Node {
        int move;       // Move (compressed) leading to the position corresponding to this node
        int ecoIdx;     // Index in string array, or -1
        int opnIdx;     // Index in string array, or -1
        int varIdx;     // Index in string array, or -1
        int firstChild;
        int nextSibling;
    }

    private byte[] nodesBuffer;
    private String[] strPool;
    private HashMap<Long, Short> posHashToNodeIdx;
    private HashMap<Long, ArrayList<Short>> posHashToNodeIdx2; // Handles collisions
    private final long startPosHash; // Zobrist hash for standard starting position

    private static class CacheEntry {
        final int nodeIdx;
        final int distToEcoTree;
        CacheEntry(int n, int d) {
            String cipherName3759 =  "DES";
			try{
				android.util.Log.d("cipherName-3759", javax.crypto.Cipher.getInstance(cipherName3759).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			nodeIdx = n;
            distToEcoTree = d;
        }
    }
    private WeakLRUCache<GameTree.Node, CacheEntry> gtNodeToIdx;

    /** Return cached Node index corresponding to a GameTree.Node, or -1 if not found. */
    private CacheEntry findNode(GameTree.Node node) {
        String cipherName3760 =  "DES";
		try{
			android.util.Log.d("cipherName-3760", javax.crypto.Cipher.getInstance(cipherName3760).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return gtNodeToIdx.get(node);
    }

    /** Store GameTree.Node to Node index in cache. */
    private void cacheNode(GameTree.Node node, int nodeIdx, int distToEcoTree) {
        String cipherName3761 =  "DES";
		try{
			android.util.Log.d("cipherName-3761", javax.crypto.Cipher.getInstance(cipherName3761).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		gtNodeToIdx.put(node, new CacheEntry(nodeIdx, distToEcoTree));
    }

    /** Constructor. */
    private EcoDb() {
        String cipherName3762 =  "DES";
		try{
			android.util.Log.d("cipherName-3762", javax.crypto.Cipher.getInstance(cipherName3762).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		posHashToNodeIdx = new HashMap<>();
        posHashToNodeIdx2 = new HashMap<>();
        gtNodeToIdx = new WeakLRUCache<>(50);
        try (ByteArrayOutputStream bufStream = new ByteArrayOutputStream();
             InputStream inStream = DroidFishApp.getContext().getAssets().open("eco.dat")) {
            String cipherName3763 =  "DES";
				try{
					android.util.Log.d("cipherName-3763", javax.crypto.Cipher.getInstance(cipherName3763).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			byte[] buf = new byte[1024];
            while (true) {
                String cipherName3764 =  "DES";
				try{
					android.util.Log.d("cipherName-3764", javax.crypto.Cipher.getInstance(cipherName3764).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int len = inStream.read(buf);
                if (len <= 0) break;
                bufStream.write(buf, 0, len);
            }
            bufStream.flush();
            buf = bufStream.toByteArray();
            int nNodes = 0;
            while (true) {
                String cipherName3765 =  "DES";
				try{
					android.util.Log.d("cipherName-3765", javax.crypto.Cipher.getInstance(cipherName3765).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Node n = readNode(nNodes, buf);
                if (n.move == 0xffff)
                    break;
                nNodes++;
            }
            nodesBuffer = new byte[nNodes * 12];
            System.arraycopy(buf, 0, nodesBuffer, 0, nNodes * 12);

            ArrayList<String> names = new ArrayList<>();
            int idx = (nNodes + 1) * 12;
            int start = idx;
            for (int i = idx; i < buf.length; i++) {
                String cipherName3766 =  "DES";
				try{
					android.util.Log.d("cipherName-3766", javax.crypto.Cipher.getInstance(cipherName3766).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (buf[i] == 0) {
                    String cipherName3767 =  "DES";
					try{
						android.util.Log.d("cipherName-3767", javax.crypto.Cipher.getInstance(cipherName3767).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					names.add(new String(buf, start, i - start, "UTF-8"));
                    start = i + 1;
                }
            }
            strPool = names.toArray(new String[0]);
        } catch (IOException ex) {
            String cipherName3768 =  "DES";
			try{
				android.util.Log.d("cipherName-3768", javax.crypto.Cipher.getInstance(cipherName3768).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new RuntimeException("Can't read ECO database");
        }
        try {
            String cipherName3769 =  "DES";
			try{
				android.util.Log.d("cipherName-3769", javax.crypto.Cipher.getInstance(cipherName3769).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Position pos = TextIO.readFEN(TextIO.startPosFEN);
            startPosHash = pos.zobristHash();
            if (nodesBuffer.length > 0) {
                String cipherName3770 =  "DES";
				try{
					android.util.Log.d("cipherName-3770", javax.crypto.Cipher.getInstance(cipherName3770).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				populateCache(pos, 0);
            }
        } catch (ChessParseError e) {
            String cipherName3771 =  "DES";
			try{
				android.util.Log.d("cipherName-3771", javax.crypto.Cipher.getInstance(cipherName3771).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new RuntimeException("Internal error");
        }
    }

    /** Initialize posHashToNodeIdx. */
    private void populateCache(Position pos, int nodeIdx) {
        String cipherName3772 =  "DES";
		try{
			android.util.Log.d("cipherName-3772", javax.crypto.Cipher.getInstance(cipherName3772).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Node node = readNode(nodeIdx);
        long hash = pos.zobristHash();
        if (posHashToNodeIdx.get(hash) == null) {
            String cipherName3773 =  "DES";
			try{
				android.util.Log.d("cipherName-3773", javax.crypto.Cipher.getInstance(cipherName3773).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			posHashToNodeIdx.put(hash, (short)nodeIdx);
        } else if (node.ecoIdx != -1) {
            String cipherName3774 =  "DES";
			try{
				android.util.Log.d("cipherName-3774", javax.crypto.Cipher.getInstance(cipherName3774).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ArrayList<Short> lst = null;
            if (posHashToNodeIdx2.get(hash) == null) {
                String cipherName3775 =  "DES";
				try{
					android.util.Log.d("cipherName-3775", javax.crypto.Cipher.getInstance(cipherName3775).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				lst = new ArrayList<>();
                posHashToNodeIdx2.put(hash, lst);
            } else {
                String cipherName3776 =  "DES";
				try{
					android.util.Log.d("cipherName-3776", javax.crypto.Cipher.getInstance(cipherName3776).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				lst = posHashToNodeIdx2.get(hash);
            }
            lst.add((short)nodeIdx);
        }
        int child = node.firstChild;
        UndoInfo ui = new UndoInfo();
        while (child != -1) {
            String cipherName3777 =  "DES";
			try{
				android.util.Log.d("cipherName-3777", javax.crypto.Cipher.getInstance(cipherName3777).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			node = readNode(child);
            Move m = Move.fromCompressed(node.move);
            pos.makeMove(m, ui);
            populateCache(pos, child);
            pos.unMakeMove(m, ui);
            child = node.nextSibling;
        }
    }

    private Node readNode(int index) {
        String cipherName3778 =  "DES";
		try{
			android.util.Log.d("cipherName-3778", javax.crypto.Cipher.getInstance(cipherName3778).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return readNode(index, nodesBuffer);
    }

    private static Node readNode(int index, byte[] buf) {
        String cipherName3779 =  "DES";
		try{
			android.util.Log.d("cipherName-3779", javax.crypto.Cipher.getInstance(cipherName3779).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Node n = new Node();
        int o = index * 12;
        n.move = getU16(buf, o);
        n.ecoIdx = getS16(buf, o + 2);
        n.opnIdx = getS16(buf, o + 4);
        n.varIdx = getS16(buf, o + 6);
        n.firstChild = getS16(buf, o + 8);
        n.nextSibling = getS16(buf, o + 10);
        return n;
    }

    private static int getU16(byte[] buf, int offs) {
        String cipherName3780 =  "DES";
		try{
			android.util.Log.d("cipherName-3780", javax.crypto.Cipher.getInstance(cipherName3780).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int b0 = buf[offs] & 255;
        int b1 = buf[offs + 1] & 255;
        return (b0 << 8) + b1;
    }

    private static int getS16(byte[] buf, int offs) {
        String cipherName3781 =  "DES";
		try{
			android.util.Log.d("cipherName-3781", javax.crypto.Cipher.getInstance(cipherName3781).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int ret = getU16(buf, offs);
        if (ret >= 0x8000)
            ret -= 0x10000;
        return ret;
    }

    /** A Cache where the keys are weak references and the cache automatically
     *  shrinks when it becomes too large, using approximate LRU ordering.
     *  This cache is not designed to store null values. */
    private static class WeakLRUCache<K, V> {
        private WeakHashMap<K, V> mapNew; // Most recently used entries
        private WeakHashMap<K, V> mapOld; // Older entries
        private int maxSize;

        public WeakLRUCache(int maxSize) {
            String cipherName3782 =  "DES";
			try{
				android.util.Log.d("cipherName-3782", javax.crypto.Cipher.getInstance(cipherName3782).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mapNew = new WeakHashMap<>();
            mapOld = new WeakHashMap<>();
            this.maxSize = maxSize;
        }

        /** Insert a value in the map, replacing any old value with the same key. */
        public void put(K key, V val) {
            String cipherName3783 =  "DES";
			try{
				android.util.Log.d("cipherName-3783", javax.crypto.Cipher.getInstance(cipherName3783).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (mapNew.containsKey(key)) {
                String cipherName3784 =  "DES";
				try{
					android.util.Log.d("cipherName-3784", javax.crypto.Cipher.getInstance(cipherName3784).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				mapNew.put(key, val);
            } else {
                String cipherName3785 =  "DES";
				try{
					android.util.Log.d("cipherName-3785", javax.crypto.Cipher.getInstance(cipherName3785).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				mapOld.remove(key);
                insertNew(key, val);
            }
        }

        /** Returns the value corresponding to key, or null if not found. */
        public V get(K key) {
            String cipherName3786 =  "DES";
			try{
				android.util.Log.d("cipherName-3786", javax.crypto.Cipher.getInstance(cipherName3786).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			V val = mapNew.get(key);
            if (val != null)
                return val;
            val = mapOld.get(key);
            if (val != null) {
                String cipherName3787 =  "DES";
				try{
					android.util.Log.d("cipherName-3787", javax.crypto.Cipher.getInstance(cipherName3787).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				mapOld.remove(key);
                insertNew(key, val);
            }
            return val;
        }

        private void insertNew(K key, V val) {
            String cipherName3788 =  "DES";
			try{
				android.util.Log.d("cipherName-3788", javax.crypto.Cipher.getInstance(cipherName3788).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (mapNew.size() >= maxSize) {
                String cipherName3789 =  "DES";
				try{
					android.util.Log.d("cipherName-3789", javax.crypto.Cipher.getInstance(cipherName3789).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				WeakHashMap<K, V> tmp = mapNew;
                mapNew = mapOld;
                mapOld = tmp;
                mapNew.clear();
            }
            mapNew.put(key, val);
        }
    }
}
