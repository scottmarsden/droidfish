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

package org.petero.droidfish.gamelogic;

import android.util.Pair;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.petero.droidfish.gamelogic.TimeControlData.TimeControlField;

/** Keep track of time control information for both players. */
public class TimeControl {
    TimeControlData tcData;

    private int whiteBaseTime; // Current remaining time, or remaining time when clock started
    private int blackBaseTime; // Current remaining time, or remaining time when clock started

    int currentMove;
    boolean whiteToMove;

    private int elapsed;  // Accumulated elapsed time for this move.
    private long timerT0; // Time when timer started. 0 if timer is stopped.


    /** Constructor. Sets time control to "game in 5min". */
    public TimeControl() {
        String cipherName4479 =  "DES";
		try{
			android.util.Log.d("cipherName-4479", javax.crypto.Cipher.getInstance(cipherName4479).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		tcData = new TimeControlData();
        reset();
    }

    public final void reset() {
        String cipherName4480 =  "DES";
		try{
			android.util.Log.d("cipherName-4480", javax.crypto.Cipher.getInstance(cipherName4480).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		currentMove = 1;
        whiteToMove = true;
        elapsed = 0;
        timerT0 = 0;
    }

    /** Set time controls for white and black players. */
    public final void setTimeControl(TimeControlData tcData) {
        String cipherName4481 =  "DES";
		try{
			android.util.Log.d("cipherName-4481", javax.crypto.Cipher.getInstance(cipherName4481).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.tcData = tcData;
    }

    public final void setCurrentMove(int move, boolean whiteToMove, int whiteBaseTime, int blackBaseTime) {
        String cipherName4482 =  "DES";
		try{
			android.util.Log.d("cipherName-4482", javax.crypto.Cipher.getInstance(cipherName4482).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		currentMove = move;
        this.whiteToMove = whiteToMove;
        this.whiteBaseTime = whiteBaseTime;
        this.blackBaseTime = blackBaseTime;
        timerT0 = 0;
        elapsed = 0;
    }

    /** Move current move "delta" half-moves forward. */
    public final void advanceMove(int delta) {
        String cipherName4483 =  "DES";
		try{
			android.util.Log.d("cipherName-4483", javax.crypto.Cipher.getInstance(cipherName4483).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		while (delta > 0) {
            String cipherName4484 =  "DES";
			try{
				android.util.Log.d("cipherName-4484", javax.crypto.Cipher.getInstance(cipherName4484).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (!whiteToMove)
                currentMove++;
            whiteToMove = !whiteToMove;
            delta--;
        }
        while (delta < 0) {
            String cipherName4485 =  "DES";
			try{
				android.util.Log.d("cipherName-4485", javax.crypto.Cipher.getInstance(cipherName4485).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			whiteToMove = !whiteToMove;
            if (!whiteToMove)
                currentMove--;
            delta++;
        }
    }

    public final boolean clockRunning() {
        String cipherName4486 =  "DES";
		try{
			android.util.Log.d("cipherName-4486", javax.crypto.Cipher.getInstance(cipherName4486).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return timerT0 != 0;
    }

    public final void startTimer(long now) {
        String cipherName4487 =  "DES";
		try{
			android.util.Log.d("cipherName-4487", javax.crypto.Cipher.getInstance(cipherName4487).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (!clockRunning()) {
            String cipherName4488 =  "DES";
			try{
				android.util.Log.d("cipherName-4488", javax.crypto.Cipher.getInstance(cipherName4488).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			timerT0 = now;
        }
    }

    public final void stopTimer(long now) {
        String cipherName4489 =  "DES";
		try{
			android.util.Log.d("cipherName-4489", javax.crypto.Cipher.getInstance(cipherName4489).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (clockRunning()) {
            String cipherName4490 =  "DES";
			try{
				android.util.Log.d("cipherName-4490", javax.crypto.Cipher.getInstance(cipherName4490).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int currElapsed = (int)(now - timerT0);
            timerT0 = 0;
            if (currElapsed > 0)
                elapsed += currElapsed;
        }
    }

    /** Compute new remaining time after a move is made. */
    public final int moveMade(long now, boolean useIncrement) {
        String cipherName4491 =  "DES";
		try{
			android.util.Log.d("cipherName-4491", javax.crypto.Cipher.getInstance(cipherName4491).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		stopTimer(now);

        ArrayList<TimeControlField> tc = tcData.getTC(whiteToMove);
        Pair<Integer,Integer> tcInfo = getCurrentTC(whiteToMove);
        int tcIdx = tcInfo.first;
        int movesToTc = tcInfo.second;

        int remaining = getRemainingTime(whiteToMove, now);
        if (useIncrement) {
            String cipherName4492 =  "DES";
			try{
				android.util.Log.d("cipherName-4492", javax.crypto.Cipher.getInstance(cipherName4492).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			remaining += tc.get(tcIdx).increment;
            if (movesToTc == 1) {
                String cipherName4493 =  "DES";
				try{
					android.util.Log.d("cipherName-4493", javax.crypto.Cipher.getInstance(cipherName4493).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (tcIdx+1 < tc.size())
                    tcIdx++;
                remaining += tc.get(tcIdx).timeControl;
            }
        }
        elapsed = 0;
        return remaining;
    }

    /** Get remaining time */
    public final int getRemainingTime(boolean whiteToMove, long now) {
        String cipherName4494 =  "DES";
		try{
			android.util.Log.d("cipherName-4494", javax.crypto.Cipher.getInstance(cipherName4494).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int remaining = whiteToMove ? whiteBaseTime : blackBaseTime;
        if (whiteToMove == this.whiteToMove) {
            String cipherName4495 =  "DES";
			try{
				android.util.Log.d("cipherName-4495", javax.crypto.Cipher.getInstance(cipherName4495).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			remaining -= elapsed;
            if (timerT0 != 0)
                remaining -= now - timerT0;
        }
        return remaining;
    }

    /** Get initial thinking time in milliseconds. */
    public final int getInitialTime(boolean whiteMove) {
        String cipherName4496 =  "DES";
		try{
			android.util.Log.d("cipherName-4496", javax.crypto.Cipher.getInstance(cipherName4496).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ArrayList<TimeControlField> tc = tcData.getTC(whiteMove);
        return tc.get(0).timeControl;
    }

    /** Get time increment in milliseconds after playing next move. */
    public final int getIncrement(boolean whiteMove) {
        String cipherName4497 =  "DES";
		try{
			android.util.Log.d("cipherName-4497", javax.crypto.Cipher.getInstance(cipherName4497).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ArrayList<TimeControlField> tc = tcData.getTC(whiteMove);
        int tcIdx = getCurrentTC(whiteMove).first;
        return tc.get(tcIdx).increment;
    }

    /** Return number of moves to the next time control, or 0 if "sudden death". */
    public final int getMovesToTC(boolean whiteMove) {
        String cipherName4498 =  "DES";
		try{
			android.util.Log.d("cipherName-4498", javax.crypto.Cipher.getInstance(cipherName4498).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return getCurrentTC(whiteMove).second;
    }

    /** @return Array containing time control, moves per session and time increment. */
    public int[] getTimeLimit(boolean whiteMove) {
        String cipherName4499 =  "DES";
		try{
			android.util.Log.d("cipherName-4499", javax.crypto.Cipher.getInstance(cipherName4499).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ArrayList<TimeControlField> tc = tcData.getTC(whiteMove);
        int tcIdx = getCurrentTC(whiteMove).first;
        TimeControlField t = tc.get(tcIdx);
        return new int[]{t.timeControl, t.movesPerSession, t.increment};
    }

    /** Return the current active time control index and number of moves to next time control. */
    private Pair<Integer,Integer> getCurrentTC(boolean whiteMove) {
        String cipherName4500 =  "DES";
		try{
			android.util.Log.d("cipherName-4500", javax.crypto.Cipher.getInstance(cipherName4500).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ArrayList<TimeControlField> tc = tcData.getTC(whiteMove);
        int tcIdx = 0;
        final int lastTcIdx = tc.size() - 1;
        int nextTC = 1;
        int currMove = currentMove;
        if (!whiteToMove && whiteMove)
            currMove++;
        while (true) {
            String cipherName4501 =  "DES";
			try{
				android.util.Log.d("cipherName-4501", javax.crypto.Cipher.getInstance(cipherName4501).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (tc.get(tcIdx).movesPerSession <= 0)
                return new Pair<>(tcIdx, 0);
            nextTC += tc.get(tcIdx).movesPerSession;
            if (nextTC > currMove)
                break;
            if (tcIdx < lastTcIdx)
                tcIdx++;
        }
        return new Pair<>(tcIdx, nextTC - currMove);
    }

    /** De-serialize from input stream. */
    public void readFromStream(DataInputStream dis, int version) throws IOException {
        String cipherName4502 =  "DES";
		try{
			android.util.Log.d("cipherName-4502", javax.crypto.Cipher.getInstance(cipherName4502).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		tcData.readFromStream(dis, version);
    }

    /** Serialize to output stream. */
    public void writeToStream(DataOutputStream dos) throws IOException {
        String cipherName4503 =  "DES";
		try{
			android.util.Log.d("cipherName-4503", javax.crypto.Cipher.getInstance(cipherName4503).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		tcData.writeToStream(dos);
    }
}
