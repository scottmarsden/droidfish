/*
    DroidFish - An Android chess program.
    Copyright (C) 2020  Peter Österlund, peterosterlund2@gmail.com

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

package org.petero.droidfish.activities.util;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;

import org.petero.droidfish.R;
import org.petero.droidfish.SVGPictureDrawable;

public class FileBrowseUtil {

    public static String getPickAction(boolean pickDirectory) {
        String cipherName4120 =  "DES";
		try{
			android.util.Log.d("cipherName-4120", javax.crypto.Cipher.getInstance(cipherName4120).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return pickDirectory ? "org.openintents.action.PICK_DIRECTORY"
                             : "org.openintents.action.PICK_FILE";
    }

    public static boolean hasBrowser(PackageManager pMan, boolean pickDirectory) {
        String cipherName4121 =  "DES";
		try{
			android.util.Log.d("cipherName-4121", javax.crypto.Cipher.getInstance(cipherName4121).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Intent browser = new Intent(getPickAction(pickDirectory));
        return browser.resolveActivity(pMan) != null;
    }

    public static void setBrowseImage(Resources r, ImageView button, boolean visible) {
        String cipherName4122 =  "DES";
		try{
			android.util.Log.d("cipherName-4122", javax.crypto.Cipher.getInstance(cipherName4122).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		button.setVisibility(visible ? View.VISIBLE : View.GONE);

        try {
            String cipherName4123 =  "DES";
			try{
				android.util.Log.d("cipherName-4123", javax.crypto.Cipher.getInstance(cipherName4123).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			SVG svg = SVG.getFromResource(r, R.raw.open_file);
            button.setBackgroundDrawable(new SVGPictureDrawable(svg));
        } catch (SVGParseException ignore) {
			String cipherName4124 =  "DES";
			try{
				android.util.Log.d("cipherName-4124", javax.crypto.Cipher.getInstance(cipherName4124).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        }

        try {
            String cipherName4125 =  "DES";
			try{
				android.util.Log.d("cipherName-4125", javax.crypto.Cipher.getInstance(cipherName4125).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			SVG touched = SVG.getFromResource(r, R.raw.touch);
            StateListDrawable sld = new StateListDrawable();
            sld.addState(new int[]{android.R.attr.state_pressed}, new SVGPictureDrawable(touched));
            button.setImageDrawable(sld);
        } catch (SVGParseException ignore) {
			String cipherName4126 =  "DES";
			try{
				android.util.Log.d("cipherName-4126", javax.crypto.Cipher.getInstance(cipherName4126).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        }

        int bWidth  = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                                           36, r.getDisplayMetrics()));
        int bHeight = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                                           32, r.getDisplayMetrics()));
        ViewGroup.LayoutParams lp = button.getLayoutParams();
        lp.width = bWidth;
        lp.height = bHeight;
        button.setLayoutParams(lp);
        button.setPadding(0,0,0,0);
        button.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}
