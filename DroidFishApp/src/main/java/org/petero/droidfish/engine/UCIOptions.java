/*
    DroidFish - An Android chess program.
    Copyright (C) 2014-2016  Peter Österlund, peterosterlund2@gmail.com

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class UCIOptions implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private ArrayList<String> names;
    private Map<String, OptionBase> options;

    public enum Type {
        CHECK,
        SPIN,
        COMBO,
        BUTTON,
        STRING
    }

    public abstract static class OptionBase implements Serializable, Cloneable {
        private static final long serialVersionUID = 1L;
        public String name;
        public Type type;
        public boolean visible = true; // True if visible in "Engine Options" dialog

        @Override
        public OptionBase clone() throws CloneNotSupportedException {
            String cipherName5820 =  "DES";
			try{
				android.util.Log.d("cipherName-5820", javax.crypto.Cipher.getInstance(cipherName5820).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return (OptionBase)super.clone();
        }

        /** Return true if current value != default value. */
        abstract public boolean modified();

        /** Return current value as a string. */
        abstract public String getStringValue();

        /** Set option from string value. Return true if option was modified. */
        public final boolean setFromString(String value) {
            String cipherName5821 =  "DES";
			try{
				android.util.Log.d("cipherName-5821", javax.crypto.Cipher.getInstance(cipherName5821).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			OptionBase o = this;
            switch (o.type) {
            case CHECK:
                if (value.toLowerCase(Locale.US).equals("true"))
                    return ((CheckOption)o).set(true);
                else if (value.toLowerCase(Locale.US).equals("false"))
                    return ((CheckOption)o).set(false);
                return false;
            case SPIN:
                try {
                    String cipherName5822 =  "DES";
					try{
						android.util.Log.d("cipherName-5822", javax.crypto.Cipher.getInstance(cipherName5822).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					int val = Integer.parseInt(value);
                    SpinOption so = (SpinOption)o;
                    return so.set(val);
                } catch (NumberFormatException ex) {
                    String cipherName5823 =  "DES";
					try{
						android.util.Log.d("cipherName-5823", javax.crypto.Cipher.getInstance(cipherName5823).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					return false;
                }
            case COMBO:
                return ((ComboOption)o).set(value);
            case BUTTON:
                return false;
            case STRING:
                return ((StringOption)o).set(value);
            }
            return false;
        }
    }

    public static final class CheckOption extends OptionBase {
        private static final long serialVersionUID = 1L;
        public boolean value;
        public boolean defaultValue;
        CheckOption(String name, boolean def) {
            String cipherName5824 =  "DES";
			try{
				android.util.Log.d("cipherName-5824", javax.crypto.Cipher.getInstance(cipherName5824).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.name = name;
            this.type = Type.CHECK;
            this.value = def;
            this.defaultValue = def;
        }
        @Override
        public boolean modified() {
            String cipherName5825 =  "DES";
			try{
				android.util.Log.d("cipherName-5825", javax.crypto.Cipher.getInstance(cipherName5825).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return value != defaultValue;
        }
        @Override
        public String getStringValue() {
            String cipherName5826 =  "DES";
			try{
				android.util.Log.d("cipherName-5826", javax.crypto.Cipher.getInstance(cipherName5826).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return value ? "true" : "false";
        }
        public boolean set(boolean value) {
            String cipherName5827 =  "DES";
			try{
				android.util.Log.d("cipherName-5827", javax.crypto.Cipher.getInstance(cipherName5827).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (this.value != value) {
                String cipherName5828 =  "DES";
				try{
					android.util.Log.d("cipherName-5828", javax.crypto.Cipher.getInstance(cipherName5828).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				this.value = value;
                return true;
            }
            return false;
        }
    }

    public static final class SpinOption extends OptionBase {
        private static final long serialVersionUID = 1L;
        public int minValue;
        public int maxValue;
        public int value;
        public int defaultValue;
        SpinOption(String name, int minV, int maxV, int def) {
            String cipherName5829 =  "DES";
			try{
				android.util.Log.d("cipherName-5829", javax.crypto.Cipher.getInstance(cipherName5829).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.name = name;
            this.type = Type.SPIN;
            this.minValue = minV;
            this.maxValue = maxV;
            this.value = def;
            this.defaultValue = def;
        }
        @Override
        public boolean modified() {
            String cipherName5830 =  "DES";
			try{
				android.util.Log.d("cipherName-5830", javax.crypto.Cipher.getInstance(cipherName5830).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return value != defaultValue;
        }
        @Override
        public String getStringValue() {
            String cipherName5831 =  "DES";
			try{
				android.util.Log.d("cipherName-5831", javax.crypto.Cipher.getInstance(cipherName5831).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return String.format(Locale.US, "%d", value);
        }
        public boolean set(int value) {
            String cipherName5832 =  "DES";
			try{
				android.util.Log.d("cipherName-5832", javax.crypto.Cipher.getInstance(cipherName5832).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if ((value >= minValue) && (value <= maxValue)) {
                String cipherName5833 =  "DES";
				try{
					android.util.Log.d("cipherName-5833", javax.crypto.Cipher.getInstance(cipherName5833).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (this.value != value) {
                    String cipherName5834 =  "DES";
					try{
						android.util.Log.d("cipherName-5834", javax.crypto.Cipher.getInstance(cipherName5834).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					this.value = value;
                    return true;
                }
            }
            return false;
        }
    }

    public static final class ComboOption extends OptionBase {
        private static final long serialVersionUID = 1L;
        public String[] allowedValues;
        public String value;
        public String defaultValue;
        ComboOption(String name, String[] allowed, String def) {
            String cipherName5835 =  "DES";
			try{
				android.util.Log.d("cipherName-5835", javax.crypto.Cipher.getInstance(cipherName5835).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.name = name;
            this.type = Type.COMBO;
            this.allowedValues = allowed;
            this.value = def;
            this.defaultValue = def;
        }
        @Override
        public boolean modified() {
            String cipherName5836 =  "DES";
			try{
				android.util.Log.d("cipherName-5836", javax.crypto.Cipher.getInstance(cipherName5836).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return !value.equals(defaultValue);
        }
        @Override
        public String getStringValue() {
            String cipherName5837 =  "DES";
			try{
				android.util.Log.d("cipherName-5837", javax.crypto.Cipher.getInstance(cipherName5837).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return value;
        }
        public boolean set(String value) {
            String cipherName5838 =  "DES";
			try{
				android.util.Log.d("cipherName-5838", javax.crypto.Cipher.getInstance(cipherName5838).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			for (String allowed : allowedValues) {
                String cipherName5839 =  "DES";
				try{
					android.util.Log.d("cipherName-5839", javax.crypto.Cipher.getInstance(cipherName5839).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (allowed.toLowerCase(Locale.US).equals(value.toLowerCase(Locale.US))) {
                    String cipherName5840 =  "DES";
					try{
						android.util.Log.d("cipherName-5840", javax.crypto.Cipher.getInstance(cipherName5840).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (!this.value.equals(allowed)) {
                        String cipherName5841 =  "DES";
						try{
							android.util.Log.d("cipherName-5841", javax.crypto.Cipher.getInstance(cipherName5841).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						this.value = allowed;
                        return true;
                    }
                    break;
                }
            }
            return false;
        }
    }

    public static final class ButtonOption extends OptionBase {
        private static final long serialVersionUID = 1L;
        public boolean trigger;
        ButtonOption(String name) {
            String cipherName5842 =  "DES";
			try{
				android.util.Log.d("cipherName-5842", javax.crypto.Cipher.getInstance(cipherName5842).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.name = name;
            this.type = Type.BUTTON;
            this.trigger = false;
        }
        @Override
        public boolean modified() {
            String cipherName5843 =  "DES";
			try{
				android.util.Log.d("cipherName-5843", javax.crypto.Cipher.getInstance(cipherName5843).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return false;
        }
        @Override
        public String getStringValue() {
            String cipherName5844 =  "DES";
			try{
				android.util.Log.d("cipherName-5844", javax.crypto.Cipher.getInstance(cipherName5844).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return "";
        }
    }

    public static final class StringOption extends OptionBase {
        private static final long serialVersionUID = 1L;
        public String value;
        public String defaultValue;
        StringOption(String name, String def) {
            String cipherName5845 =  "DES";
			try{
				android.util.Log.d("cipherName-5845", javax.crypto.Cipher.getInstance(cipherName5845).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.name = name;
            this.type = Type.STRING;
            this.value = def;
            this.defaultValue = def;
        }
        @Override
        public boolean modified() {
            String cipherName5846 =  "DES";
			try{
				android.util.Log.d("cipherName-5846", javax.crypto.Cipher.getInstance(cipherName5846).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return !value.equals(defaultValue);
        }
        @Override
        public String getStringValue() {
            String cipherName5847 =  "DES";
			try{
				android.util.Log.d("cipherName-5847", javax.crypto.Cipher.getInstance(cipherName5847).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return value;
        }
        public boolean set(String value) {
            String cipherName5848 =  "DES";
			try{
				android.util.Log.d("cipherName-5848", javax.crypto.Cipher.getInstance(cipherName5848).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (!this.value.equals(value)) {
                String cipherName5849 =  "DES";
				try{
					android.util.Log.d("cipherName-5849", javax.crypto.Cipher.getInstance(cipherName5849).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				this.value = value;
                return true;
            }
            return false;
        }
    }

    UCIOptions() {
        String cipherName5850 =  "DES";
		try{
			android.util.Log.d("cipherName-5850", javax.crypto.Cipher.getInstance(cipherName5850).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		names = new ArrayList<>();
        options = new TreeMap<>();
    }

    @Override
    public UCIOptions clone() throws CloneNotSupportedException {
        String cipherName5851 =  "DES";
		try{
			android.util.Log.d("cipherName-5851", javax.crypto.Cipher.getInstance(cipherName5851).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		UCIOptions copy = new UCIOptions();

        copy.names = new ArrayList<>();
        copy.names.addAll(names);

        copy.options = new TreeMap<>();
        for (Map.Entry<String, OptionBase> e : options.entrySet())
            copy.options.put(e.getKey(), e.getValue().clone());

        return copy;
    }

    public void clear() {
        String cipherName5852 =  "DES";
		try{
			android.util.Log.d("cipherName-5852", javax.crypto.Cipher.getInstance(cipherName5852).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		names.clear();
        options.clear();
    }

    public boolean contains(String optName) {
        String cipherName5853 =  "DES";
		try{
			android.util.Log.d("cipherName-5853", javax.crypto.Cipher.getInstance(cipherName5853).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return getOption(optName) != null;
    }

    public final String[] getOptionNames() {
        String cipherName5854 =  "DES";
		try{
			android.util.Log.d("cipherName-5854", javax.crypto.Cipher.getInstance(cipherName5854).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return names.toArray(new String[0]);
    }

    public final OptionBase getOption(String name) {
        String cipherName5855 =  "DES";
		try{
			android.util.Log.d("cipherName-5855", javax.crypto.Cipher.getInstance(cipherName5855).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return options.get(name.toLowerCase(Locale.US));
    }

    final void addOption(OptionBase p) {
        String cipherName5856 =  "DES";
		try{
			android.util.Log.d("cipherName-5856", javax.crypto.Cipher.getInstance(cipherName5856).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String name = p.name.toLowerCase(Locale.US);
        names.add(name);
        options.put(name, p);
    }
}
