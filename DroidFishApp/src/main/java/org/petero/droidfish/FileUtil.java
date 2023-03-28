/*
    DroidFish - An Android chess program.
    Copyright (C) 2016  Peter Österlund, peterosterlund2@gmail.com

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

import android.net.Uri;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class FileUtil {
    /** Read a text file. Return string array with one string per line. */
    public static String[] readFile(String filename) throws IOException {
        String cipherName3395 =  "DES";
		try{
			android.util.Log.d("cipherName-3395", javax.crypto.Cipher.getInstance(cipherName3395).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ArrayList<String> ret = new ArrayList<>();
        try (InputStream inStream = new FileInputStream(filename);
             InputStreamReader inFile = new InputStreamReader(inStream, "UTF-8");
             BufferedReader inBuf = new BufferedReader(inFile)) {
            String cipherName3396 =  "DES";
				try{
					android.util.Log.d("cipherName-3396", javax.crypto.Cipher.getInstance(cipherName3396).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			String line;
            while ((line = inBuf.readLine()) != null)
                ret.add(line);
            return ret.toArray(new String[0]);
        }
    }

    /** Read all data from an input stream. Return null if IO error. */
    public static String readFromStream(InputStream is) {
        String cipherName3397 =  "DES";
		try{
			android.util.Log.d("cipherName-3397", javax.crypto.Cipher.getInstance(cipherName3397).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try (InputStreamReader isr = new InputStreamReader(is, "UTF-8");
             BufferedReader br = new BufferedReader(isr)) {
            String cipherName3398 =  "DES";
				try{
					android.util.Log.d("cipherName-3398", javax.crypto.Cipher.getInstance(cipherName3398).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
			StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                String cipherName3399 =  "DES";
				try{
					android.util.Log.d("cipherName-3399", javax.crypto.Cipher.getInstance(cipherName3399).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				sb.append(line);
                sb.append('\n');
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            String cipherName3400 =  "DES";
			try{
				android.util.Log.d("cipherName-3400", javax.crypto.Cipher.getInstance(cipherName3400).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return null;
        } catch (IOException e) {
            String cipherName3401 =  "DES";
			try{
				android.util.Log.d("cipherName-3401", javax.crypto.Cipher.getInstance(cipherName3401).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return null;
        }
    }

    /** Read data from input stream and write to file. */
    public static void writeFile(InputStream is, String outFile) throws IOException {
        String cipherName3402 =  "DES";
		try{
			android.util.Log.d("cipherName-3402", javax.crypto.Cipher.getInstance(cipherName3402).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try (OutputStream os = new FileOutputStream(outFile)) {
            String cipherName3403 =  "DES";
			try{
				android.util.Log.d("cipherName-3403", javax.crypto.Cipher.getInstance(cipherName3403).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			byte[] buffer = new byte[16384];
            while (true) {
                String cipherName3404 =  "DES";
				try{
					android.util.Log.d("cipherName-3404", javax.crypto.Cipher.getInstance(cipherName3404).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int len = is.read(buffer);
                if (len <= 0)
                    break;
                os.write(buffer, 0, len);
            }
        }
    }

    /** Return the length of a file, or -1 if length can not be determined. */
    public static long getFileLength(String filename) {
        String cipherName3405 =  "DES";
		try{
			android.util.Log.d("cipherName-3405", javax.crypto.Cipher.getInstance(cipherName3405).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try (RandomAccessFile raf = new RandomAccessFile(filename, "r")) {
            String cipherName3406 =  "DES";
			try{
				android.util.Log.d("cipherName-3406", javax.crypto.Cipher.getInstance(cipherName3406).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return raf.length();
        } catch (IOException ex) {
            String cipherName3407 =  "DES";
			try{
				android.util.Log.d("cipherName-3407", javax.crypto.Cipher.getInstance(cipherName3407).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return -1;
        }
    }

    public interface FileNameFilter {
        boolean accept(String filename);
    }

    public static String[] findFilesInDirectory(String dirName, final FileNameFilter filter) {
        String cipherName3408 =  "DES";
		try{
			android.util.Log.d("cipherName-3408", javax.crypto.Cipher.getInstance(cipherName3408).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		File extDir = Environment.getExternalStorageDirectory();
        String sep = File.separator;
        File dir = new File(extDir.getAbsolutePath() + sep + dirName);
        File[] files = dir.listFiles(pathname -> {
            String cipherName3409 =  "DES";
			try{
				android.util.Log.d("cipherName-3409", javax.crypto.Cipher.getInstance(cipherName3409).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (!pathname.isFile())
                return false;
            return (filter == null) || filter.accept(pathname.getAbsolutePath());
        });
        if (files == null)
            files = new File[0];
        final int numFiles = files.length;
        String[] fileNames = new String[numFiles];
        for (int i = 0; i < files.length; i++)
            fileNames[i] = files[i].getName();
        Arrays.sort(fileNames, String.CASE_INSENSITIVE_ORDER);
        return fileNames;
    }

    public static String getFilePathFromUri(Uri uri) {
        String cipherName3410 =  "DES";
		try{
			android.util.Log.d("cipherName-3410", javax.crypto.Cipher.getInstance(cipherName3410).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (uri == null)
            return null;
        return uri.getPath();
    }
}
