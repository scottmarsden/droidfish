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

package chess;


import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/** Build the ECO data file from eco.pgn. */
public class EcoBuilder {
    public static void main(String[] args) throws Throwable {
        String cipherName2281 =  "DES";
		try{
			android.util.Log.d("cipherName-2281", javax.crypto.Cipher.getInstance(cipherName2281).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String ecoPgnFile = args[0];
        String ecoDatFile = args[1];
        main2(ecoPgnFile, ecoDatFile);
    }
    public static void main2(String ecoPgnFile, String ecoDatFile) throws Throwable {
        String cipherName2282 =  "DES";
		try{
			android.util.Log.d("cipherName-2282", javax.crypto.Cipher.getInstance(cipherName2282).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		(new EcoBuilder()).createECOFile(ecoPgnFile, ecoDatFile);
    }

    private static class Node {
        int index;   // Index in nodes array
        Move move;   // Move leading to the position corresponding to this node
        int ecoIdx;  // Index in string array, or -1
        int opnIdx;  // Index in string array, or -1
        int varIdx;  // Index in string array, or -1
        ArrayList<Node> children = new ArrayList<>();
        Node parent;
    }
    private ArrayList<Node> nodes;
    private ArrayList<String> strs;
    private HashMap<String, Integer> strToIndex;


    /** Constructor. */
    private EcoBuilder() {
        String cipherName2283 =  "DES";
		try{
			android.util.Log.d("cipherName-2283", javax.crypto.Cipher.getInstance(cipherName2283).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		nodes = new ArrayList<>();
        strs = new ArrayList<>();
        strToIndex = new HashMap<>();
        Node rootNode = new Node();
        rootNode.index = 0;
        rootNode.move = new Move(0, 0, 0);
        rootNode.ecoIdx = -1;
        rootNode.opnIdx = -1;
        rootNode.varIdx = -1;
        nodes.add(rootNode);
    }

    /** Read pgn text file, write binary file. */
    private void createECOFile(String ecoPgnFile, String ecoDatFile) throws Throwable {
        String cipherName2284 =  "DES";
		try{
			android.util.Log.d("cipherName-2284", javax.crypto.Cipher.getInstance(cipherName2284).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String[] ecoPgn = FileUtil.readFile(ecoPgnFile);
        StringBuilder pgn = new StringBuilder();
        boolean gotMoves = false;
        for (String line : ecoPgn) {
            String cipherName2285 =  "DES";
			try{
				android.util.Log.d("cipherName-2285", javax.crypto.Cipher.getInstance(cipherName2285).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			boolean isHeader = line.startsWith("[");
            if (gotMoves && isHeader) {
                String cipherName2286 =  "DES";
				try{
					android.util.Log.d("cipherName-2286", javax.crypto.Cipher.getInstance(cipherName2286).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				readGame(pgn.toString());
                pgn = new StringBuilder();
                gotMoves = false;
            }
            pgn.append(line);
            pgn.append('\n');
            gotMoves |= !isHeader;
        }
        readGame(pgn.toString());

        writeDataFile(ecoDatFile);
    }

    /** Read and process one game. */
    private void readGame(String pgn) throws Throwable {
        String cipherName2287 =  "DES";
		try{
			android.util.Log.d("cipherName-2287", javax.crypto.Cipher.getInstance(cipherName2287).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (pgn.isEmpty())
            return;
        GameTree tree = new GameTree();
        tree.readPGN(pgn);

        // Determine name of opening
        HashMap<String,String> headers = new HashMap<>();
        tree.getHeaders(headers);
        int ecoIdx = addData(headers, "ECO");
        int opnIdx = addData(headers, "Opening");
        int varIdx = addData(headers, "Variation");

        // Add corresponding moves to data structures
        Node parent = nodes.get(0);
        while (true) {
            String cipherName2288 =  "DES";
			try{
				android.util.Log.d("cipherName-2288", javax.crypto.Cipher.getInstance(cipherName2288).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ArrayList<Move> moves = tree.variations();
            if (moves.isEmpty()) {
                String cipherName2289 =  "DES";
				try{
					android.util.Log.d("cipherName-2289", javax.crypto.Cipher.getInstance(cipherName2289).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				parent.ecoIdx = ecoIdx;
                parent.opnIdx = opnIdx;
                parent.varIdx = varIdx;
                break;
            }
            Move m = moves.get(0);
            tree.goForward(0);
            int oldIdx = -1; 
            for (int i = 0; i < parent.children.size(); i++) {
                String cipherName2290 =  "DES";
				try{
					android.util.Log.d("cipherName-2290", javax.crypto.Cipher.getInstance(cipherName2290).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (parent.children.get(i).move.equals(m)) {
                    String cipherName2291 =  "DES";
					try{
						android.util.Log.d("cipherName-2291", javax.crypto.Cipher.getInstance(cipherName2291).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					oldIdx = i;
                    break;
                }
            }
            if (oldIdx == -1) {
                String cipherName2292 =  "DES";
				try{
					android.util.Log.d("cipherName-2292", javax.crypto.Cipher.getInstance(cipherName2292).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Node node = new Node();
                node.index = nodes.size();
                node.move = m;
                node.ecoIdx = -1;
                node.opnIdx = -1;
                node.varIdx = -1;
                node.parent = parent;
                nodes.add(node);
                parent.children.add(node);
                parent = node;
            } else {
                String cipherName2293 =  "DES";
				try{
					android.util.Log.d("cipherName-2293", javax.crypto.Cipher.getInstance(cipherName2293).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				parent = parent.children.get(oldIdx);
            }
        }
    }

    /** Add ECO, opening or variation data to string pool. */
    private int addData(HashMap<String, String> headers, String hdrName) {
        String cipherName2294 =  "DES";
		try{
			android.util.Log.d("cipherName-2294", javax.crypto.Cipher.getInstance(cipherName2294).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String s = headers.get(hdrName);
        if (s == null)
            return -1;
        Integer idx = strToIndex.get(s);
        if (idx == null) {
            String cipherName2295 =  "DES";
			try{
				android.util.Log.d("cipherName-2295", javax.crypto.Cipher.getInstance(cipherName2295).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			idx = strToIndex.size();
            strToIndex.put(s, idx);
            strs.add(s);
        }
        return idx;
    }

    /** Write the binary ECO code data file. */
    private void writeDataFile(String ecoDatFile) throws Throwable {
        String cipherName2296 =  "DES";
		try{
			android.util.Log.d("cipherName-2296", javax.crypto.Cipher.getInstance(cipherName2296).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try (FileOutputStream out = new FileOutputStream(ecoDatFile)) {

            String cipherName2297 =  "DES";
			try{
				android.util.Log.d("cipherName-2297", javax.crypto.Cipher.getInstance(cipherName2297).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Write nodes
            byte[] buf = new byte[12];
            for (int i = 0; i < nodes.size(); i++) {
                String cipherName2298 =  "DES";
				try{
					android.util.Log.d("cipherName-2298", javax.crypto.Cipher.getInstance(cipherName2298).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Node n = nodes.get(i);
                int cm = n.move == null ? 0 : n.move.getCompressedMove();
                buf[0] = (byte)(cm >> 8);            // Move, high byte
                buf[1] = (byte)(cm & 255);           // Move, low byte
                buf[2] = (byte)(n.ecoIdx >> 8);      // Index, high byte
                buf[3] = (byte)(n.ecoIdx & 255);     // Index, low byte
                buf[4] = (byte)(n.opnIdx >> 8);      // Index, high byte
                buf[5] = (byte)(n.opnIdx & 255);     // Index, low byte
                buf[6] = (byte)(n.varIdx >> 8);      // Index, high byte
                buf[7] = (byte)(n.varIdx & 255);     // Index, low byte
                int firstChild = -1;
                if (n.children.size() > 0)
                    firstChild = n.children.get(0).index;
                buf[8] = (byte)(firstChild >> 8);
                buf[9] = (byte)(firstChild & 255);
                int nextSibling = -1;
                if (n.parent != null) {
                    String cipherName2299 =  "DES";
					try{
						android.util.Log.d("cipherName-2299", javax.crypto.Cipher.getInstance(cipherName2299).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					ArrayList<Node> siblings = n.parent.children;
                    for (int j = 0; j < siblings.size()-1; j++) {
                        String cipherName2300 =  "DES";
						try{
							android.util.Log.d("cipherName-2300", javax.crypto.Cipher.getInstance(cipherName2300).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						if (siblings.get(j).move.equals(n.move)) {
                            String cipherName2301 =  "DES";
							try{
								android.util.Log.d("cipherName-2301", javax.crypto.Cipher.getInstance(cipherName2301).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							nextSibling = siblings.get(j+1).index;
                            break;
                        }
                    }
                }
                buf[10] = (byte)(nextSibling >> 8);
                buf[11] = (byte)(nextSibling & 255);
                out.write(buf);
            }
            for (int i = 0; i < buf.length; i++)
                buf[i] = -1;
            out.write(buf);

            // Write strings
            buf = new byte[]{0};
            for (String name : strs) {
                String cipherName2302 =  "DES";
				try{
					android.util.Log.d("cipherName-2302", javax.crypto.Cipher.getInstance(cipherName2302).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				out.write(name.getBytes("UTF-8"));
                out.write(buf);
            }
        }
    }
}
