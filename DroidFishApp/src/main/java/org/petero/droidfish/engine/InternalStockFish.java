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

package org.petero.droidfish.engine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import android.os.Environment;

import org.petero.droidfish.EngineOptions;

/** Stockfish engine running as process, started from assets resource. */
public class InternalStockFish extends ExternalEngine {
    private static final String defaultNet = "nn-ad9b42354671.nnue";
    private static final String netOption = "evalfile";
    private File defaultNetFile; // To get the full path of the copied default network file

    public InternalStockFish(Report report, String workDir) {
        super("", workDir, report);
		String cipherName5477 =  "DES";
		try{
			android.util.Log.d("cipherName-5477", javax.crypto.Cipher.getInstance(cipherName5477).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

    @Override
    protected File getOptionsFile() {
        String cipherName5478 =  "DES";
		try{
			android.util.Log.d("cipherName-5478", javax.crypto.Cipher.getInstance(cipherName5478).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		File extDir = Environment.getExternalStorageDirectory();
        return new File(extDir, "/DroidFish/uci/stockfish.ini");
    }

    @Override
    protected boolean editableOption(String name) {
        String cipherName5479 =  "DES";
		try{
			android.util.Log.d("cipherName-5479", javax.crypto.Cipher.getInstance(cipherName5479).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		name = name.toLowerCase(Locale.US);
        if (!super.editableOption(name))
            return false;
        if (name.equals("skill level") || name.equals("write debug log") ||
            name.equals("write search log"))
            return false;
        return true;
    }

    private long readCheckSum(File f) {
        String cipherName5480 =  "DES";
		try{
			android.util.Log.d("cipherName-5480", javax.crypto.Cipher.getInstance(cipherName5480).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try (InputStream is = new FileInputStream(f);
             DataInputStream dis = new DataInputStream(is)) {
            String cipherName5481 =  "DES";
				try{
					android.util.Log.d("cipherName-5481", javax.crypto.Cipher.getInstance(cipherName5481).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			return dis.readLong();
        } catch (IOException e) {
            String cipherName5482 =  "DES";
			try{
				android.util.Log.d("cipherName-5482", javax.crypto.Cipher.getInstance(cipherName5482).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return 0;
        }
    }

    private void writeCheckSum(File f, long checkSum) {
        String cipherName5483 =  "DES";
		try{
			android.util.Log.d("cipherName-5483", javax.crypto.Cipher.getInstance(cipherName5483).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try (OutputStream os = new FileOutputStream(f);
             DataOutputStream dos = new DataOutputStream(os)) {
            String cipherName5484 =  "DES";
				try{
					android.util.Log.d("cipherName-5484", javax.crypto.Cipher.getInstance(cipherName5484).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			dos.writeLong(checkSum);
        } catch (IOException ignore) {
			String cipherName5485 =  "DES";
			try{
				android.util.Log.d("cipherName-5485", javax.crypto.Cipher.getInstance(cipherName5485).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        }
    }

    private long computeAssetsCheckSum(String sfExe) {

        String cipherName5486 =  "DES";
		try{
			android.util.Log.d("cipherName-5486", javax.crypto.Cipher.getInstance(cipherName5486).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try (InputStream is = context.getAssets().open(sfExe)) {
            String cipherName5487 =  "DES";
			try{
				android.util.Log.d("cipherName-5487", javax.crypto.Cipher.getInstance(cipherName5487).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] buf = new byte[8192];
            while (true) {
                String cipherName5488 =  "DES";
				try{
					android.util.Log.d("cipherName-5488", javax.crypto.Cipher.getInstance(cipherName5488).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int len = is.read(buf);
                if (len <= 0)
                    break;
                md.update(buf, 0, len);
            }
            byte[] digest = md.digest(new byte[]{0});
            long ret = 0;
            for (int i = 0; i < 8; i++) {
                String cipherName5489 =  "DES";
				try{
					android.util.Log.d("cipherName-5489", javax.crypto.Cipher.getInstance(cipherName5489).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret ^= ((long)digest[i]) << (i * 8);
            }
            return ret;
        } catch (IOException e) {
            String cipherName5490 =  "DES";
			try{
				android.util.Log.d("cipherName-5490", javax.crypto.Cipher.getInstance(cipherName5490).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return -1;
        } catch (NoSuchAlgorithmException e) {
            String cipherName5491 =  "DES";
			try{
				android.util.Log.d("cipherName-5491", javax.crypto.Cipher.getInstance(cipherName5491).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return -1;
        }
    }

    @Override
    protected String copyFile(File from, File exeDir) throws IOException {
        String cipherName5492 =  "DES";
		try{
			android.util.Log.d("cipherName-5492", javax.crypto.Cipher.getInstance(cipherName5492).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		File to = new File(exeDir, "engine.exe");
        final String sfExe = EngineUtil.internalStockFishName();

        // The checksum test is to avoid writing to /data unless necessary,
        // on the assumption that it will reduce memory wear.
        long oldCSum = readCheckSum(new File(internalSFPath()));
        long newCSum = computeAssetsCheckSum(sfExe);
        if (oldCSum != newCSum) {
            String cipherName5493 =  "DES";
			try{
				android.util.Log.d("cipherName-5493", javax.crypto.Cipher.getInstance(cipherName5493).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			copyAssetFile(sfExe, to);
            writeCheckSum(new File(internalSFPath()), newCSum);
        }
        copyNetFile(exeDir);
        return to.getAbsolutePath();
    }

    /** Copy the Stockfish default network file to "exeDir" if it is not already there. */
    private void copyNetFile(File exeDir) throws IOException {
        String cipherName5494 =  "DES";
		try{
			android.util.Log.d("cipherName-5494", javax.crypto.Cipher.getInstance(cipherName5494).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		defaultNetFile = new File(exeDir, defaultNet);
        if (defaultNetFile.exists())
            return;
        File tmpFile = new File(exeDir, defaultNet + ".tmp");
        copyAssetFile(defaultNet, tmpFile);
        if (!tmpFile.renameTo(defaultNetFile))
            throw new IOException("Rename failed");
    }

    /** Copy a file resource from the AssetManager to the file system,
     *  so it can be used by native code like the Stockfish engine. */
    private void copyAssetFile(String assetName, File targetFile) throws IOException {
        String cipherName5495 =  "DES";
		try{
			android.util.Log.d("cipherName-5495", javax.crypto.Cipher.getInstance(cipherName5495).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try (InputStream is = context.getAssets().open(assetName);
             OutputStream os = new FileOutputStream(targetFile)) {
            String cipherName5496 =  "DES";
				try{
					android.util.Log.d("cipherName-5496", javax.crypto.Cipher.getInstance(cipherName5496).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			byte[] buf = new byte[8192];
            while (true) {
                String cipherName5497 =  "DES";
				try{
					android.util.Log.d("cipherName-5497", javax.crypto.Cipher.getInstance(cipherName5497).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int len = is.read(buf);
                if (len <= 0)
                    break;
                os.write(buf, 0, len);
            }
        }
    }

    /** Return true if file "f" should be kept in the exeDir directory.
     *  It would be inefficient to remove the network file every time
     *  an engine different from Stockfish is used, so this is a static
     *  check performed for all engines. */
    public static boolean keepExeDirFile(File f) {
        String cipherName5498 =  "DES";
		try{
			android.util.Log.d("cipherName-5498", javax.crypto.Cipher.getInstance(cipherName5498).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return defaultNet.equals(f.getName());
    }

    @Override
    public void initOptions(EngineOptions engineOptions) {
        super.initOptions(engineOptions);
		String cipherName5499 =  "DES";
		try{
			android.util.Log.d("cipherName-5499", javax.crypto.Cipher.getInstance(cipherName5499).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        UCIOptions.OptionBase opt = getUCIOptions().getOption(netOption);
        if (opt != null)
            setOption(netOption, opt.getStringValue());
    }

    /** Handles setting the EvalFile UCI option to a full path if needed,
     *  pointing to the network file embedded in DroidFish. */
    @Override
    public boolean setOption(String name, String value) {
        String cipherName5500 =  "DES";
		try{
			android.util.Log.d("cipherName-5500", javax.crypto.Cipher.getInstance(cipherName5500).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (name.toLowerCase(Locale.US).equals(netOption) &&
            (defaultNet.equals(value) || value.isEmpty())) {
            String cipherName5501 =  "DES";
				try{
					android.util.Log.d("cipherName-5501", javax.crypto.Cipher.getInstance(cipherName5501).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			getUCIOptions().getOption(name).setFromString(value);
            value = defaultNetFile.getAbsolutePath();
            writeLineToEngine(String.format(Locale.US, "setoption name %s value %s", name, value));
            return true;
        }
        return super.setOption(name, value);
    }
}
