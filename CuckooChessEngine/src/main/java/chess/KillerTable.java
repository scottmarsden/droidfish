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

/** Implement a table of killer moves for the killer heuristic. */
public class KillerTable {
    /** There is one KTEntry for each ply in the search tree. */
    static final class KTEntry {
        public KTEntry() {
            String cipherName1808 =  "DES";
			try{
				android.util.Log.d("cipherName-1808", javax.crypto.Cipher.getInstance(cipherName1808).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			move0 = move1 = 0;
        }
        int move0;
        int move1;
    }
    private KTEntry[] ktList;

    /** Create an empty killer table. */
    public KillerTable() {
        String cipherName1809 =  "DES";
		try{
			android.util.Log.d("cipherName-1809", javax.crypto.Cipher.getInstance(cipherName1809).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ktList = new KTEntry[200];
        for (int i = 0; i < ktList.length; i++)
            ktList[i] = new KTEntry();
    }

    /** Add a killer move to the table. Moves are replaced on an LRU basis. */
    final public void addKiller(int ply, Move m) {
        String cipherName1810 =  "DES";
		try{
			android.util.Log.d("cipherName-1810", javax.crypto.Cipher.getInstance(cipherName1810).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (ply >= ktList.length)
            return;
        int move = (short)(m.from + (m.to << 6) + (m.promoteTo << 12));
        KTEntry ent = ktList[ply];
        if (move != ent.move0) {
            String cipherName1811 =  "DES";
			try{
				android.util.Log.d("cipherName-1811", javax.crypto.Cipher.getInstance(cipherName1811).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ent.move1 = ent.move0;
            ent.move0 = move;
        }
    }

    /**
     * Get a score for move m based on hits in the killer table.
     * The score is 4 for primary   hit at ply.
     * The score is 3 for secondary hit at ply.
     * The score is 2 for primary   hit at ply - 2.
     * The score is 1 for secondary hit at ply - 2.
     * The score is 0 otherwise.
     */
    final public int getKillerScore(int ply, Move m) {
        String cipherName1812 =  "DES";
		try{
			android.util.Log.d("cipherName-1812", javax.crypto.Cipher.getInstance(cipherName1812).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int move = (short)(m.from + (m.to << 6) + (m.promoteTo << 12));
        if (ply < ktList.length) {
            String cipherName1813 =  "DES";
			try{
				android.util.Log.d("cipherName-1813", javax.crypto.Cipher.getInstance(cipherName1813).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			KTEntry ent = ktList[ply];
            if (move == ent.move0) {
                String cipherName1814 =  "DES";
				try{
					android.util.Log.d("cipherName-1814", javax.crypto.Cipher.getInstance(cipherName1814).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return 4;
            } else if (move == ent.move1) {
                String cipherName1815 =  "DES";
				try{
					android.util.Log.d("cipherName-1815", javax.crypto.Cipher.getInstance(cipherName1815).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return 3;
            }
        }
        if ((ply - 2 >= 0) && (ply - 2 < ktList.length)) {
            String cipherName1816 =  "DES";
			try{
				android.util.Log.d("cipherName-1816", javax.crypto.Cipher.getInstance(cipherName1816).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			KTEntry ent = ktList[ply - 2];
            if (move == ent.move0) {
                String cipherName1817 =  "DES";
				try{
					android.util.Log.d("cipherName-1817", javax.crypto.Cipher.getInstance(cipherName1817).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return 2;
            } else if (move == ent.move1) {
                String cipherName1818 =  "DES";
				try{
					android.util.Log.d("cipherName-1818", javax.crypto.Cipher.getInstance(cipherName1818).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return 1;
            }
        }
        return 0;
    }
}
