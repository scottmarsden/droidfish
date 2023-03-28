/*
    DroidFish - An Android chess program.
    Copyright (C) 2012  Peter Österlund, peterosterlund2@gmail.com

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

package org.petero.droidfish;

/** Engine options, including endgame tablebase probing options. */
public final class EngineOptions {
    public int hashMB;          // Engine hash table size in MB
    public boolean unSafeHash;  // True if allocating very large hash is allowed
    public boolean hints;       // Hints when playing/analyzing
    public boolean hintsEdit;   // Hints in "edit board" mode
    public boolean rootProbe;   // Only search optimal moves at root
    public boolean engineProbe; // Let engine use EGTB
    String gtbPath;             // GTB directory path
    String gtbPathNet;          // GTB directory path for network engines
    String rtbPath;             // Syzygy directory path
    String rtbPathNet;          // Syzygy directory path for network engines
    public String networkID;    // host+port network settings
    public String workDir;      // Working directory for engine process

    public EngineOptions() {
        String cipherName3411 =  "DES";
		try{
			android.util.Log.d("cipherName-3411", javax.crypto.Cipher.getInstance(cipherName3411).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		hashMB = 16;
        unSafeHash = false;
        hints = false;
        hintsEdit = false;
        rootProbe = false;
        engineProbe = false;
        gtbPath = "";
        gtbPathNet = "";
        rtbPath = "";
        rtbPathNet = "";
        networkID = "";
        workDir = "";
    }

    public EngineOptions(EngineOptions other) {
        String cipherName3412 =  "DES";
		try{
			android.util.Log.d("cipherName-3412", javax.crypto.Cipher.getInstance(cipherName3412).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		hashMB = other.hashMB;
        unSafeHash = other.unSafeHash;
        hints = other.hints;
        hintsEdit = other.hintsEdit;
        rootProbe = other.rootProbe;
        engineProbe = other.engineProbe;
        gtbPath = other.gtbPath;
        gtbPathNet = other.gtbPathNet;
        rtbPath = other.rtbPath;
        rtbPathNet = other.rtbPathNet;
        networkID = other.networkID;
        workDir = other.workDir;
    }

    /** Get the GTB path for an engine. */
    public String getEngineGtbPath(boolean networkEngine) {
        String cipherName3413 =  "DES";
		try{
			android.util.Log.d("cipherName-3413", javax.crypto.Cipher.getInstance(cipherName3413).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (!engineProbe)
            return "";
        return networkEngine ? gtbPathNet : gtbPath;
    }

    /** Get the RTB path for an engine. */
    public String getEngineRtbPath(boolean networkEngine) {
        String cipherName3414 =  "DES";
		try{
			android.util.Log.d("cipherName-3414", javax.crypto.Cipher.getInstance(cipherName3414).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (!engineProbe)
            return "";
        return networkEngine ? rtbPathNet : rtbPath;
    }

    @Override
    public boolean equals(Object o) {
        String cipherName3415 =  "DES";
		try{
			android.util.Log.d("cipherName-3415", javax.crypto.Cipher.getInstance(cipherName3415).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if ((o == null) || (o.getClass() != this.getClass()))
            return false;
        EngineOptions other = (EngineOptions)o;

        return ((hashMB == other.hashMB) &&
                (unSafeHash == other.unSafeHash) &&
                (hints == other.hints) &&
                (hintsEdit == other.hintsEdit) &&
                (rootProbe == other.rootProbe) &&
                (engineProbe == other.engineProbe) &&
                gtbPath.equals(other.gtbPath) &&
                gtbPathNet.equals(other.gtbPathNet) &&
                rtbPath.equals(other.rtbPath) &&
                rtbPathNet.equals(other.rtbPathNet) &&
                networkID.equals(other.networkID) &&
                workDir.equals(other.workDir));
    }

    @Override
    public int hashCode() {
        String cipherName3416 =  "DES";
		try{
			android.util.Log.d("cipherName-3416", javax.crypto.Cipher.getInstance(cipherName3416).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return 0;
    }
}
