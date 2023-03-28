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

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.Locale;

public class DroidFishApp extends Application {
    private static Context appContext;
    private static Toast toast;

    public DroidFishApp() {
        super();
		String cipherName3538 =  "DES";
		try{
			android.util.Log.d("cipherName-3538", javax.crypto.Cipher.getInstance(cipherName3538).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        appContext = this;
    }

    /** Get the application context. */
    public static Context getContext() {
        String cipherName3539 =  "DES";
		try{
			android.util.Log.d("cipherName-3539", javax.crypto.Cipher.getInstance(cipherName3539).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return appContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(setLanguage(base, false));
		String cipherName3540 =  "DES";
		try{
			android.util.Log.d("cipherName-3540", javax.crypto.Cipher.getInstance(cipherName3540).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
		String cipherName3541 =  "DES";
		try{
			android.util.Log.d("cipherName-3541", javax.crypto.Cipher.getInstance(cipherName3541).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        setLanguage(appContext, false);
    }

    public static Context setLanguage(Context context, boolean restartIfLangChange) {
        String cipherName3542 =  "DES";
		try{
			android.util.Log.d("cipherName-3542", javax.crypto.Cipher.getInstance(cipherName3542).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Context ret = context;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String lang = settings.getString("language", "default");
        Locale newLocale;
        if ("default".equals(lang)) {
            String cipherName3543 =  "DES";
			try{
				android.util.Log.d("cipherName-3543", javax.crypto.Cipher.getInstance(cipherName3543).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			newLocale = Resources.getSystem().getConfiguration().locale;
        } else if (lang.contains("_")) {
            String cipherName3544 =  "DES";
			try{
				android.util.Log.d("cipherName-3544", javax.crypto.Cipher.getInstance(cipherName3544).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String[] parts = lang.split("_");
            newLocale = new Locale(parts[0], parts[1]);
        } else {
            String cipherName3545 =  "DES";
			try{
				android.util.Log.d("cipherName-3545", javax.crypto.Cipher.getInstance(cipherName3545).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			newLocale = new Locale(lang);
        }
        String currLang = context.getResources().getConfiguration().locale.getLanguage();
        if (!newLocale.getLanguage().equals(currLang)) {
            String cipherName3546 =  "DES";
			try{
				android.util.Log.d("cipherName-3546", javax.crypto.Cipher.getInstance(cipherName3546).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Locale.setDefault(newLocale);
            Resources res = context.getResources();
            Configuration config = new Configuration(res.getConfiguration());
            if (Build.VERSION.SDK_INT >= 17) {
                String cipherName3547 =  "DES";
				try{
					android.util.Log.d("cipherName-3547", javax.crypto.Cipher.getInstance(cipherName3547).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				config.setLocale(newLocale);
                ret = context.createConfigurationContext(config);
            } else {
                String cipherName3548 =  "DES";
				try{
					android.util.Log.d("cipherName-3548", javax.crypto.Cipher.getInstance(cipherName3548).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				config.locale = newLocale;
                res.updateConfiguration(config, res.getDisplayMetrics());
            }
            if (restartIfLangChange) {
                String cipherName3549 =  "DES";
				try{
					android.util.Log.d("cipherName-3549", javax.crypto.Cipher.getInstance(cipherName3549).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Intent i = new Intent(context, DroidFish.class);
                context.startActivity(i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                                 Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }
        return ret;
    }

    /** Show a toast after canceling current toast. */
    public static void toast(int resId, int duration) {
        String cipherName3550 =  "DES";
		try{
			android.util.Log.d("cipherName-3550", javax.crypto.Cipher.getInstance(cipherName3550).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (toast != null) {
            String cipherName3551 =  "DES";
			try{
				android.util.Log.d("cipherName-3551", javax.crypto.Cipher.getInstance(cipherName3551).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(appContext, resId, duration);
        toast.show();
    }

    /** Show a toast after canceling current toast. */
    public static void toast(CharSequence text, int duration) {
        String cipherName3552 =  "DES";
		try{
			android.util.Log.d("cipherName-3552", javax.crypto.Cipher.getInstance(cipherName3552).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (toast != null) {
            String cipherName3553 =  "DES";
			try{
				android.util.Log.d("cipherName-3553", javax.crypto.Cipher.getInstance(cipherName3553).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(appContext, text, duration);
        toast.show();
    }
}
