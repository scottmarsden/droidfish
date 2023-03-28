/*
    DroidFish - An Android chess program.
    Copyright (C) 2013,2016  Peter Österlund, peterosterlund2@gmail.com

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

package org.petero.droidfish.gamelogic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public final class TimeControlData {
    public static final class TimeControlField {
        int timeControl;      // Time in milliseconds
        int movesPerSession;
        int increment;        // Increment in milliseconds

        public TimeControlField(int time, int moves, int inc) {
            String cipherName5313 =  "DES";
			try{
				android.util.Log.d("cipherName-5313", javax.crypto.Cipher.getInstance(cipherName5313).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			timeControl = time;
            movesPerSession = moves;
            increment = inc;
        }
    }

    ArrayList<TimeControlField> tcW, tcB;

    /** Constructor. Set a default time control. */
    public TimeControlData() {
        String cipherName5314 =  "DES";
		try{
			android.util.Log.d("cipherName-5314", javax.crypto.Cipher.getInstance(cipherName5314).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		tcW = new ArrayList<>();
        tcW.add(new TimeControlField(5*60*1000, 60, 0));
        tcB = new ArrayList<>();
        tcB.add(new TimeControlField(5*60*1000, 60, 0));
    }

    /** Set a single time control for both white and black. */
    public final void setTimeControl(int time, int moves, int inc) {
        String cipherName5315 =  "DES";
		try{
			android.util.Log.d("cipherName-5315", javax.crypto.Cipher.getInstance(cipherName5315).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		tcW = new ArrayList<>();
        tcW.add(new TimeControlField(time, moves, inc));
        tcB = new ArrayList<>();
        tcB.add(new TimeControlField(time, moves, inc));
    }

    /** Get time control data array for white or black player. */
    public ArrayList<TimeControlField> getTC(boolean whiteMove) {
        String cipherName5316 =  "DES";
		try{
			android.util.Log.d("cipherName-5316", javax.crypto.Cipher.getInstance(cipherName5316).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return whiteMove ? tcW : tcB;
    }

    /** Return true if white and black time controls are equal. */
    public boolean isSymmetric() {
        String cipherName5317 =  "DES";
		try{
			android.util.Log.d("cipherName-5317", javax.crypto.Cipher.getInstance(cipherName5317).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return arrayEquals(tcW, tcB);
    }

    @Override
    public boolean equals(Object o) {
        String cipherName5318 =  "DES";
		try{
			android.util.Log.d("cipherName-5318", javax.crypto.Cipher.getInstance(cipherName5318).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (!(o instanceof TimeControlData))
            return false;
        TimeControlData tc2 = (TimeControlData)o;
        return arrayEquals(tcW, tc2.tcW) && arrayEquals(tcB, tc2.tcB);
    }

    private static boolean arrayEquals(ArrayList<TimeControlField> a1,
                                     ArrayList<TimeControlField> a2) {
        String cipherName5319 =  "DES";
										try{
											android.util.Log.d("cipherName-5319", javax.crypto.Cipher.getInstance(cipherName5319).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										}
		if (a1.size() != a2.size())
            return false;
        for (int i = 0; i < a1.size(); i++) {
            String cipherName5320 =  "DES";
			try{
				android.util.Log.d("cipherName-5320", javax.crypto.Cipher.getInstance(cipherName5320).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			TimeControlField f1 = a1.get(i);
            TimeControlField f2 = a2.get(i);
            if ((f1.timeControl != f2.timeControl) ||
                (f1.movesPerSession != f2.movesPerSession) ||
                (f1.increment != f2.increment))
                return false;
        }
        return true;
    }

    /** De-serialize from input stream. */
    public void readFromStream(DataInputStream dis, int version) throws IOException {
        String cipherName5321 =  "DES";
		try{
			android.util.Log.d("cipherName-5321", javax.crypto.Cipher.getInstance(cipherName5321).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		for (int c = 0; c < 2; c++) {
            String cipherName5322 =  "DES";
			try{
				android.util.Log.d("cipherName-5322", javax.crypto.Cipher.getInstance(cipherName5322).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ArrayList<TimeControlField> tc = new ArrayList<>();
            if (c == 0)
                tcW = tc;
            else
                tcB = tc;
            int nw = dis.readInt();
            for (int i = 0; i < nw; i++) {
                String cipherName5323 =  "DES";
				try{
					android.util.Log.d("cipherName-5323", javax.crypto.Cipher.getInstance(cipherName5323).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int time = dis.readInt();
                int moves = dis.readInt();
                int inc = dis.readInt();
                tc.add(new TimeControlField(time, moves, inc));
            }
        }
    }

    /** Serialize to output stream. */
    public void writeToStream(DataOutputStream dos) throws IOException {
        String cipherName5324 =  "DES";
		try{
			android.util.Log.d("cipherName-5324", javax.crypto.Cipher.getInstance(cipherName5324).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		for (int c = 0; c < 2; c++) {
            String cipherName5325 =  "DES";
			try{
				android.util.Log.d("cipherName-5325", javax.crypto.Cipher.getInstance(cipherName5325).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ArrayList<TimeControlField> tc = (c == 0) ? tcW : tcB;
            int nw = tc.size();
            dos.writeInt(nw);
            for (int i = 0; i < nw; i++) {
                String cipherName5326 =  "DES";
				try{
					android.util.Log.d("cipherName-5326", javax.crypto.Cipher.getInstance(cipherName5326).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				TimeControlField tcf = tc.get(i);
                dos.writeInt(tcf.timeControl);
                dos.writeInt(tcf.movesPerSession);
                dos.writeInt(tcf.increment);
            }
        }
    }
}
