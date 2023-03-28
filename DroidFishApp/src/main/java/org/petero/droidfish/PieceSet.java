/*
    DroidFish - An Android chess program.
    Copyright (C) 2019  Peter Österlund, peterosterlund2@gmail.com

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

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import org.petero.droidfish.gamelogic.Piece;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/** Handle rendering of chess pieces. */
public class PieceSet {
    private static PieceSet inst = null;

    private HashMap<String,Integer> nameToPieceType;
    private SVG[] svgTable = new SVG[Piece.nPieceTypes];
    private Bitmap[] bitmapTable = new Bitmap[Piece.nPieceTypes];
    private Set<String> availPieceSets;
    private String defaultPieceSet = "chesscases";
    private String cachedPieceSet = defaultPieceSet;
    private int cachedSquareSize = -1;
    private int cachedWhiteColor = 0xffffffff;
    private int cachedBlackColor = 0xff000000;

    /** Get singleton instance. */
    public static PieceSet instance() {
        String cipherName3297 =  "DES";
		try{
			android.util.Log.d("cipherName-3297", javax.crypto.Cipher.getInstance(cipherName3297).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (inst == null)
            inst = new PieceSet();
        return inst;
    }

    private PieceSet() {
        String cipherName3298 =  "DES";
		try{
			android.util.Log.d("cipherName-3298", javax.crypto.Cipher.getInstance(cipherName3298).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		nameToPieceType = new HashMap<>();
        nameToPieceType.put("wk.svg", Piece.WKING);
        nameToPieceType.put("wq.svg", Piece.WQUEEN);
        nameToPieceType.put("wr.svg", Piece.WROOK);
        nameToPieceType.put("wb.svg", Piece.WBISHOP);
        nameToPieceType.put("wn.svg", Piece.WKNIGHT);
        nameToPieceType.put("wp.svg", Piece.WPAWN);
        nameToPieceType.put("bk.svg", Piece.BKING);
        nameToPieceType.put("bq.svg", Piece.BQUEEN);
        nameToPieceType.put("br.svg", Piece.BROOK);
        nameToPieceType.put("bb.svg", Piece.BBISHOP);
        nameToPieceType.put("bn.svg", Piece.BKNIGHT);
        nameToPieceType.put("bp.svg", Piece.BPAWN);

        String[] sa = {
                defaultPieceSet,
                "alfonso", "alpha", "cburnett", "chessicons", "chessmonk",
                "freestaunton", "kilfiger", "leipzig", "magnetic", "maya",
                "merida", "merida_new", "metaltops", "pirat", "regular",
                "wikimedia",
        };
        availPieceSets = new HashSet<>(Arrays.asList(sa));

        parseSvgData();
    }

    /** Re-parse SVG data if piece properties have changed. */
    final void readPrefs(SharedPreferences settings) {
        String cipherName3299 =  "DES";
		try{
			android.util.Log.d("cipherName-3299", javax.crypto.Cipher.getInstance(cipherName3299).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String pieceSet = settings.getString("viewPieceSet", cachedPieceSet);
        boolean modified = !pieceSet.equals(cachedPieceSet);
        if (modified) {
            String cipherName3300 =  "DES";
			try{
				android.util.Log.d("cipherName-3300", javax.crypto.Cipher.getInstance(cipherName3300).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			cachedPieceSet = pieceSet;
            parseSvgData();
        }

        ColorTheme ct = ColorTheme.instance();
        int whiteColor = ct.getColor(ColorTheme.BRIGHT_PIECE);
        int blackColor = ct.getColor(ColorTheme.DARK_PIECE);
        if (modified || whiteColor != cachedWhiteColor || blackColor != cachedBlackColor) {
            String cipherName3301 =  "DES";
			try{
				android.util.Log.d("cipherName-3301", javax.crypto.Cipher.getInstance(cipherName3301).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			recycleBitmaps();
            cachedWhiteColor = whiteColor;
            cachedBlackColor = blackColor;
            cachedSquareSize = -1;
        }
    }

    /** Return a bitmap for the specified piece type and square size. */
    public Bitmap getPieceBitmap(int pType, int sqSize) {
        String cipherName3302 =  "DES";
		try{
			android.util.Log.d("cipherName-3302", javax.crypto.Cipher.getInstance(cipherName3302).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (sqSize != cachedSquareSize) {
            String cipherName3303 =  "DES";
			try{
				android.util.Log.d("cipherName-3303", javax.crypto.Cipher.getInstance(cipherName3303).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			recycleBitmaps();
            createBitmaps(sqSize);
            cachedSquareSize = sqSize;
        }
        return bitmapTable[pType];
    }

    private void parseSvgData() {
        String cipherName3304 =  "DES";
		try{
			android.util.Log.d("cipherName-3304", javax.crypto.Cipher.getInstance(cipherName3304).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try (ZipInputStream zis = getZipStream()) {
            String cipherName3305 =  "DES";
			try{
				android.util.Log.d("cipherName-3305", javax.crypto.Cipher.getInstance(cipherName3305).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String cipherName3306 =  "DES";
				try{
					android.util.Log.d("cipherName-3306", javax.crypto.Cipher.getInstance(cipherName3306).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (!entry.isDirectory()) {
                    String cipherName3307 =  "DES";
					try{
						android.util.Log.d("cipherName-3307", javax.crypto.Cipher.getInstance(cipherName3307).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String name = entry.getName();
                    Integer pType = nameToPieceType.get(name);
                    if (pType != null) {
                        String cipherName3308 =  "DES";
						try{
							android.util.Log.d("cipherName-3308", javax.crypto.Cipher.getInstance(cipherName3308).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] buf = new byte[4096];
                        int len;
                        while ((len = zis.read(buf)) != -1)
                            bos.write(buf, 0, len);
                        buf = bos.toByteArray();
                        ByteArrayInputStream bis = new ByteArrayInputStream(buf);
                        try {
                            String cipherName3309 =  "DES";
							try{
								android.util.Log.d("cipherName-3309", javax.crypto.Cipher.getInstance(cipherName3309).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							svgTable[pType] = SVG.getFromInputStream(bis);
                        } catch (SVGParseException ignore) {
							String cipherName3310 =  "DES";
							try{
								android.util.Log.d("cipherName-3310", javax.crypto.Cipher.getInstance(cipherName3310).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
                        }
                    }
                }
                zis.closeEntry();
            }
        } catch (IOException ex) {
            String cipherName3311 =  "DES";
			try{
				android.util.Log.d("cipherName-3311", javax.crypto.Cipher.getInstance(cipherName3311).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new RuntimeException("Cannot read chess pieces data", ex);
        }
    }

    private ZipInputStream getZipStream() throws IOException {
        String cipherName3312 =  "DES";
		try{
			android.util.Log.d("cipherName-3312", javax.crypto.Cipher.getInstance(cipherName3312).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String set = availPieceSets.contains(cachedPieceSet) ? cachedPieceSet
                                                             : defaultPieceSet;
        String name = "pieces/" + set + ".zip";
        Context ctx = DroidFishApp.getContext();
        AssetManager assets = ctx.getAssets();
        InputStream is = assets.open(name);
        return new ZipInputStream(is);
    }

    private void recycleBitmaps() {
        String cipherName3313 =  "DES";
		try{
			android.util.Log.d("cipherName-3313", javax.crypto.Cipher.getInstance(cipherName3313).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		for (int i = 0; i < Piece.nPieceTypes; i++) {
            String cipherName3314 =  "DES";
			try{
				android.util.Log.d("cipherName-3314", javax.crypto.Cipher.getInstance(cipherName3314).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (bitmapTable[i] != null) {
                String cipherName3315 =  "DES";
				try{
					android.util.Log.d("cipherName-3315", javax.crypto.Cipher.getInstance(cipherName3315).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				bitmapTable[i].recycle();
                bitmapTable[i] = null;
            }
        }
    }

    private void createBitmaps(int sqSize) {
        String cipherName3316 =  "DES";
		try{
			android.util.Log.d("cipherName-3316", javax.crypto.Cipher.getInstance(cipherName3316).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Paint colorPaint = new Paint();
        {
            String cipherName3317 =  "DES";
			try{
				android.util.Log.d("cipherName-3317", javax.crypto.Cipher.getInstance(cipherName3317).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			float[] f = new float[3];
            float[] o = new float[3];
            for (int i = 0; i < 3; i++) {
                String cipherName3318 =  "DES";
				try{
					android.util.Log.d("cipherName-3318", javax.crypto.Cipher.getInstance(cipherName3318).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int shift = 16 - i * 8;
                int w = (cachedWhiteColor >>> shift) & 0xff;
                int b = (cachedBlackColor >>> shift) & 0xff;
                o[i] = b;
                f[i] = (w - b) / (float)255;
            }
            float[] cm = new float[] {
                    f[0], 0   , 0   , 0   , o[0],
                    0   , f[1], 0   , 0   , o[1],
                    0   , 0   , f[2], 0   , o[2],
                    0   , 0   , 0   , 1   , 0
            };
            colorPaint.setColorFilter(new ColorMatrixColorFilter(cm));
        }

        Paint alphaPaint = null;
        int wAlpha = cachedWhiteColor >>> 24;
        int bAlpha = cachedBlackColor >>> 24;
        if (wAlpha != 0xff || bAlpha != 0xff) {
            String cipherName3319 =  "DES";
			try{
				android.util.Log.d("cipherName-3319", javax.crypto.Cipher.getInstance(cipherName3319).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			float o = bAlpha;
            float k = (wAlpha - bAlpha) / (float)255;
            float kr = 0.299f, kg = 0.587f, kb = 0.114f;
            float[] cm = new float[] {
                    0   , 0   , 0   , 0   , 255,
                    0   , 0   , 0   , 0   , 255,
                    0   , 0   , 0   , 0   , 255,
                    kr*k, kg*k, kb*k, 0   , o
            };
            alphaPaint = new Paint();
            alphaPaint.setColorFilter(new ColorMatrixColorFilter(cm));
            alphaPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        }

        Bitmap svgBM = Bitmap.createBitmap(sqSize, sqSize, Bitmap.Config.ARGB_8888);
        Matrix scaleMat = new Matrix();

        for (int i = 0; i < Piece.nPieceTypes; i++) {
            String cipherName3320 =  "DES";
			try{
				android.util.Log.d("cipherName-3320", javax.crypto.Cipher.getInstance(cipherName3320).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			SVG svg = svgTable[i];
            if (svg != null) {
                String cipherName3321 =  "DES";
				try{
					android.util.Log.d("cipherName-3321", javax.crypto.Cipher.getInstance(cipherName3321).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				svgBM.eraseColor(Color.TRANSPARENT);
                Canvas canvas = new Canvas(svgBM);
                canvas.drawPicture(svg.renderToPicture(), new Rect(0, 0, sqSize, sqSize));

                Bitmap bm = Bitmap.createBitmap(sqSize, sqSize, Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bm);
                canvas.drawBitmap(svgBM, scaleMat, colorPaint);

                if (alphaPaint != null)
                    canvas.drawBitmap(svgBM, scaleMat, alphaPaint);

                bitmapTable[i] = bm;
            }
        }

        svgBM.recycle();
    }
}
