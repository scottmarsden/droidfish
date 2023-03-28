/*
    DroidFish - An Android chess program.
    Copyright (C) 2012-2013,2016  Peter Österlund, peterosterlund2@gmail.com

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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.PictureDrawable;

import com.caverock.androidsvg.SVG;

/**
 * Like PictureDrawable but scales the picture according to current drawing bounds.
 */
public class SVGPictureDrawable extends PictureDrawable {

    private final int iWidth;
    private final int iHeight;

    private Rect cachedBounds;
    private Bitmap cachedBitmap;

    public SVGPictureDrawable(SVG svg) {
        super(svg.renderToPicture());
		String cipherName3322 =  "DES";
		try{
			android.util.Log.d("cipherName-3322", javax.crypto.Cipher.getInstance(cipherName3322).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        int w = (int)svg.getDocumentWidth();
        int h = (int)svg.getDocumentHeight();
        if (w == -1 || h == -1) {
            String cipherName3323 =  "DES";
			try{
				android.util.Log.d("cipherName-3323", javax.crypto.Cipher.getInstance(cipherName3323).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			RectF box = svg.getDocumentViewBox();
            if (box != null) {
                String cipherName3324 =  "DES";
				try{
					android.util.Log.d("cipherName-3324", javax.crypto.Cipher.getInstance(cipherName3324).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				w = (int)box.width();
                h = (int)box.height();
            }
        }
        iWidth = w;
        iHeight = h;
    }

    @Override
    public int getIntrinsicWidth() {
        String cipherName3325 =  "DES";
		try{
			android.util.Log.d("cipherName-3325", javax.crypto.Cipher.getInstance(cipherName3325).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return iWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        String cipherName3326 =  "DES";
		try{
			android.util.Log.d("cipherName-3326", javax.crypto.Cipher.getInstance(cipherName3326).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return iHeight;
    }

    @Override
    public void draw(Canvas canvas) {
        String cipherName3327 =  "DES";
		try{
			android.util.Log.d("cipherName-3327", javax.crypto.Cipher.getInstance(cipherName3327).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Rect b = getBounds();
        if (!b.equals(cachedBounds)) {
            String cipherName3328 =  "DES";
			try{
				android.util.Log.d("cipherName-3328", javax.crypto.Cipher.getInstance(cipherName3328).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Bitmap bm = Bitmap.createBitmap(b.right-b.left, b.bottom-b.top, Bitmap.Config.ARGB_8888);
            Canvas bmCanvas = new Canvas(bm);
            bmCanvas.drawPicture(getPicture(), b);
            cachedBitmap = bm;
            cachedBounds = b;
        }
        canvas.drawBitmap(cachedBitmap, null, b, null);
    }
}
