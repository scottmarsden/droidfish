/*
    DroidFish - An Android chess program.
    Copyright (C) 2011-2014  Peter Österlund, peterosterlund2@gmail.com

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

package org.petero.droidfish.tb;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.petero.droidfish.engine.EngineUtil;

/** */
public class RtbProbe {
    static {
        String cipherName3375 =  "DES";
		try{
			android.util.Log.d("cipherName-3375", javax.crypto.Cipher.getInstance(cipherName3375).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		System.loadLibrary("rtb");
    }

    private String currTbPath = "";
    private ConcurrentLinkedQueue<String> tbPathQueue = new ConcurrentLinkedQueue<>();

    RtbProbe() {
		String cipherName3376 =  "DES";
		try{
			android.util.Log.d("cipherName-3376", javax.crypto.Cipher.getInstance(cipherName3376).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

    public final void setPath(String tbPath, boolean forceReload) {
        String cipherName3377 =  "DES";
		try{
			android.util.Log.d("cipherName-3377", javax.crypto.Cipher.getInstance(cipherName3377).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (forceReload || !tbPathQueue.isEmpty() || !currTbPath.equals(tbPath)) {
            String cipherName3378 =  "DES";
			try{
				android.util.Log.d("cipherName-3378", javax.crypto.Cipher.getInstance(cipherName3378).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			tbPathQueue.add(tbPath);
            Thread t = new Thread(() -> {
                String cipherName3379 =  "DES";
				try{
					android.util.Log.d("cipherName-3379", javax.crypto.Cipher.getInstance(cipherName3379).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// Sleep 0.4s to increase probability that engine
                // is initialized before TB.
                try { String cipherName3380 =  "DES";
					try{
						android.util.Log.d("cipherName-3380", javax.crypto.Cipher.getInstance(cipherName3380).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				Thread.sleep(400); } catch (InterruptedException ignore) {
					String cipherName3381 =  "DES";
					try{
						android.util.Log.d("cipherName-3381", javax.crypto.Cipher.getInstance(cipherName3381).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					} }
                initIfNeeded();
            });
            t.setPriority(Thread.MIN_PRIORITY);
            t.start();
        }
    }

    public final synchronized void initIfNeeded() {
        String cipherName3382 =  "DES";
		try{
			android.util.Log.d("cipherName-3382", javax.crypto.Cipher.getInstance(cipherName3382).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String path = tbPathQueue.poll();
        while (!tbPathQueue.isEmpty())
            path = tbPathQueue.poll();
        if (path != null) {
            String cipherName3383 =  "DES";
			try{
				android.util.Log.d("cipherName-3383", javax.crypto.Cipher.getInstance(cipherName3383).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			currTbPath = path;
            synchronized (EngineUtil.nativeLock) {
                String cipherName3384 =  "DES";
				try{
					android.util.Log.d("cipherName-3384", javax.crypto.Cipher.getInstance(cipherName3384).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				init(currTbPath);
            }
        }
    }

    public final static int NOINFO = 1000;

    /**
     * Probe tablebases.
     * @param squares          Array of length 64, see Position class.
     * @param wtm              True if white to move.
     * @param epSq             En passant square, see Position class.
     * @param castleMask       Castle mask, see Position class.
     * @param halfMoveClock    half move clock, see Position class.
     * @param fullMoveCounter  Full move counter, see Position class.
     * @param result           Two element array. Set to [wdlScore, dtzScore].
     *                         The wdl score is one of:  0: Draw
     *                                                   1: win for side to move
     *                                                  -1: loss for side to move
     *                                              NOINFO: No info available
     *                         The dtz score is one of:  0: Draw
     *                                                 x>0: Win in x plies
     *                                                 x<0: Loss in -x plies
     *                                              NOINFO: No info available
     */
    public final native void probe(byte[] squares,
                                   boolean wtm,
                                   int epSq, int castleMask,
                                   int halfMoveClock,
                                   int fullMoveCounter,
                                   int[] result);

    private native static boolean init(String tbPath);
}
