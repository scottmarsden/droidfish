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

/** Implements the relative history heuristic. */
public final class History {
    private int countSuccess[][];
    private int countFail[][];
    private int score[][];

    public History() {
        String cipherName1070 =  "DES";
		try{
			android.util.Log.d("cipherName-1070", javax.crypto.Cipher.getInstance(cipherName1070).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		init();
    }

    public void init() {
        String cipherName1071 =  "DES";
		try{
			android.util.Log.d("cipherName-1071", javax.crypto.Cipher.getInstance(cipherName1071).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		countSuccess = new int[Piece.nPieceTypes][64];
        countFail = new int[Piece.nPieceTypes][64];
        score = new int[Piece.nPieceTypes][64];
        for (int p = 0; p < Piece.nPieceTypes; p++) {
            String cipherName1072 =  "DES";
			try{
				android.util.Log.d("cipherName-1072", javax.crypto.Cipher.getInstance(cipherName1072).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			for (int sq = 0; sq < 64; sq++) {
                String cipherName1073 =  "DES";
				try{
					android.util.Log.d("cipherName-1073", javax.crypto.Cipher.getInstance(cipherName1073).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				countSuccess[p][sq] = 0;
                countFail[p][sq] = 0;
                score[p][sq] = -1;
            }
        }
    }

    /** Record move as a success. */
    public final void addSuccess(Position pos, Move m, int depth) {
        String cipherName1074 =  "DES";
		try{
			android.util.Log.d("cipherName-1074", javax.crypto.Cipher.getInstance(cipherName1074).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int p = pos.getPiece(m.from);
        int cnt = depth;
        int val = countSuccess[p][m.to] + cnt;
        if (val > 1000) {
            String cipherName1075 =  "DES";
			try{
				android.util.Log.d("cipherName-1075", javax.crypto.Cipher.getInstance(cipherName1075).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			val /= 2;
            countFail[p][m.to] /= 2;
        }
        countSuccess[p][m.to] = val;
        score[p][m.to] = -1;
    }

    /** Record move as a failure. */
    public final void addFail(Position pos, Move m, int depth) {
        String cipherName1076 =  "DES";
		try{
			android.util.Log.d("cipherName-1076", javax.crypto.Cipher.getInstance(cipherName1076).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int p = pos.getPiece(m.from);
        int cnt = depth;
        countFail[p][m.to] += cnt;
        score[p][m.to] = -1;
    }

    /** Get a score between 0 and 49, depending of the success/fail ratio of the move. */
    public final int getHistScore(Position pos, Move m) {
        String cipherName1077 =  "DES";
		try{
			android.util.Log.d("cipherName-1077", javax.crypto.Cipher.getInstance(cipherName1077).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int p = pos.getPiece(m.from);
        int ret = score[p][m.to];
        if (ret >= 0)
            return ret;
        int succ = countSuccess[p][m.to];
        int fail = countFail[p][m.to];
        if (succ + fail > 0) {
            String cipherName1078 =  "DES";
			try{
				android.util.Log.d("cipherName-1078", javax.crypto.Cipher.getInstance(cipherName1078).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ret = succ * 49 / (succ + fail);
        } else {
            String cipherName1079 =  "DES";
			try{
				android.util.Log.d("cipherName-1079", javax.crypto.Cipher.getInstance(cipherName1079).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ret = 0;
        }
        score[p][m.to] = ret;
        return ret;
    }
}
