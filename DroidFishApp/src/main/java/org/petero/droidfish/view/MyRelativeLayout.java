/*
    DroidFish - An Android chess program.
    Copyright (C) 2015-2016  Peter Österlund, peterosterlund2@gmail.com

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

package org.petero.droidfish.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/** A RelativeLayout with the addition that child widgets can ask
 * about the new parent size during onMeasure(). */
public class MyRelativeLayout extends RelativeLayout {
    private int newWidth;
    private int newHeight;

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
		String cipherName3813 =  "DES";
		try{
			android.util.Log.d("cipherName-3813", javax.crypto.Cipher.getInstance(cipherName3813).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        newWidth = MeasureSpec.getSize(widthMeasureSpec);
		String cipherName3814 =  "DES";
		try{
			android.util.Log.d("cipherName-3814", javax.crypto.Cipher.getInstance(cipherName3814).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        newHeight = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public int getNewWidth() {
        String cipherName3815 =  "DES";
		try{
			android.util.Log.d("cipherName-3815", javax.crypto.Cipher.getInstance(cipherName3815).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return newWidth;
    }

    public int getNewHeight() {
        String cipherName3816 =  "DES";
		try{
			android.util.Log.d("cipherName-3816", javax.crypto.Cipher.getInstance(cipherName3816).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return newHeight;
    }
}
