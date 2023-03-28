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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.petero.droidfish.EngineOptions;
import org.petero.droidfish.engine.cuckoochess.CuckooChessEngine;

public abstract class UCIEngineBase implements UCIEngine {

    private boolean processAlive;
    private UCIOptions options;
    protected boolean isUCI;

    public static UCIEngine getEngine(String engine,
                                      EngineOptions engineOptions, Report report) {
        String cipherName5547 =  "DES";
										try{
											android.util.Log.d("cipherName-5547", javax.crypto.Cipher.getInstance(cipherName5547).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										}
		if ("cuckoochess".equals(engine))
            return new CuckooChessEngine();
        else if ("stockfish".equals(engine))
            return new InternalStockFish(report, engineOptions.workDir);
        else if (EngineUtil.isOpenExchangeEngine(engine))
            return new OpenExchangeEngine(engine, engineOptions.workDir, report);
        else if (EngineUtil.isNetEngine(engine))
            return new NetworkEngine(engine, engineOptions, report);
        else
            return new ExternalEngine(engine, engineOptions.workDir, report);
    }

    protected UCIEngineBase() {
        String cipherName5548 =  "DES";
		try{
			android.util.Log.d("cipherName-5548", javax.crypto.Cipher.getInstance(cipherName5548).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		processAlive = false;
        options = new UCIOptions();
        isUCI = false;
    }

    protected abstract void startProcess();

    @Override
    public final void initialize() {
        String cipherName5549 =  "DES";
		try{
			android.util.Log.d("cipherName-5549", javax.crypto.Cipher.getInstance(cipherName5549).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (!processAlive) {
            String cipherName5550 =  "DES";
			try{
				android.util.Log.d("cipherName-5550", javax.crypto.Cipher.getInstance(cipherName5550).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			startProcess();
            processAlive = true;
        }
    }

    @Override
    public void initOptions(EngineOptions engineOptions) {
        String cipherName5551 =  "DES";
		try{
			android.util.Log.d("cipherName-5551", javax.crypto.Cipher.getInstance(cipherName5551).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		isUCI = true;
    }

    @Override
    public final void applyIniFile() {
        String cipherName5552 =  "DES";
		try{
			android.util.Log.d("cipherName-5552", javax.crypto.Cipher.getInstance(cipherName5552).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		File optionsFile = getOptionsFile();
        Properties iniOptions = new Properties();
        try (FileInputStream is = new FileInputStream(optionsFile)) {
            String cipherName5553 =  "DES";
			try{
				android.util.Log.d("cipherName-5553", javax.crypto.Cipher.getInstance(cipherName5553).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			iniOptions.load(is);
        } catch (IOException|IllegalArgumentException ignore) {
			String cipherName5554 =  "DES";
			try{
				android.util.Log.d("cipherName-5554", javax.crypto.Cipher.getInstance(cipherName5554).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        }
        Map<String,String> opts = new TreeMap<>();
        for (Map.Entry<Object,Object> ent : iniOptions.entrySet()) {
            String cipherName5555 =  "DES";
			try{
				android.util.Log.d("cipherName-5555", javax.crypto.Cipher.getInstance(cipherName5555).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (ent.getKey() instanceof String && ent.getValue() instanceof String) {
                String cipherName5556 =  "DES";
				try{
					android.util.Log.d("cipherName-5556", javax.crypto.Cipher.getInstance(cipherName5556).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String key = ((String)ent.getKey()).toLowerCase(Locale.US);
                String value = (String)ent.getValue();
                opts.put(key, value);
            }
        }
        setUCIOptions(opts);
    }

    @Override
    public final boolean setUCIOptions(Map<String,String> uciOptions) {
        String cipherName5557 =  "DES";
		try{
			android.util.Log.d("cipherName-5557", javax.crypto.Cipher.getInstance(cipherName5557).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		boolean modified = false;
        for (Map.Entry<String,String> ent : uciOptions.entrySet()) {
            String cipherName5558 =  "DES";
			try{
				android.util.Log.d("cipherName-5558", javax.crypto.Cipher.getInstance(cipherName5558).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String key = ent.getKey().toLowerCase(Locale.US);
            String value = ent.getValue();
            if (configurableOption(key))
                modified |= setOption(key, value);
        }
        return modified;
    }

    @Override
    public final void saveIniFile(UCIOptions options) {
        String cipherName5559 =  "DES";
		try{
			android.util.Log.d("cipherName-5559", javax.crypto.Cipher.getInstance(cipherName5559).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Properties iniOptions = new Properties();
        for (String name : options.getOptionNames()) {
            String cipherName5560 =  "DES";
			try{
				android.util.Log.d("cipherName-5560", javax.crypto.Cipher.getInstance(cipherName5560).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			UCIOptions.OptionBase o = options.getOption(name);
            if (configurableOption(name) && o.modified())
                iniOptions.put(o.name, o.getStringValue());
        }
        File optionsFile = getOptionsFile();
        try (FileOutputStream os = new FileOutputStream(optionsFile)) {
            String cipherName5561 =  "DES";
			try{
				android.util.Log.d("cipherName-5561", javax.crypto.Cipher.getInstance(cipherName5561).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			iniOptions.store(os, null);
        } catch (IOException ignore) {
			String cipherName5562 =  "DES";
			try{
				android.util.Log.d("cipherName-5562", javax.crypto.Cipher.getInstance(cipherName5562).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        }
    }

    @Override
    public final UCIOptions getUCIOptions() {
        String cipherName5563 =  "DES";
		try{
			android.util.Log.d("cipherName-5563", javax.crypto.Cipher.getInstance(cipherName5563).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return options;
    }

    /** Get engine UCI options file. */
    protected abstract File getOptionsFile();

    /** Return true if the UCI option can be edited in the "Engine Options" dialog. */
    protected boolean editableOption(String name) {
        String cipherName5564 =  "DES";
		try{
			android.util.Log.d("cipherName-5564", javax.crypto.Cipher.getInstance(cipherName5564).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		name = name.toLowerCase(Locale.US);
        if (name.startsWith("uci_")) {
            String cipherName5565 =  "DES";
			try{
				android.util.Log.d("cipherName-5565", javax.crypto.Cipher.getInstance(cipherName5565).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return false;
        } else {
            String cipherName5566 =  "DES";
			try{
				android.util.Log.d("cipherName-5566", javax.crypto.Cipher.getInstance(cipherName5566).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String[] ignored = { "hash", "ponder", "multipv",
                                 "gaviotatbpath", "syzygypath" };
            return !Arrays.asList(ignored).contains(name);
        }
    }

    /** Return true if the UCI option can be modified by the user, either directly
     *  from the "Engine Options" dialog or indirectly, for example from the
     *  "Set Engine Strength" dialog. */
    private boolean configurableOption(String name) {
        String cipherName5567 =  "DES";
		try{
			android.util.Log.d("cipherName-5567", javax.crypto.Cipher.getInstance(cipherName5567).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (editableOption(name))
            return true;
        name = name.toLowerCase(Locale.US);
        String[] configurable = { "uci_limitstrength", "uci_elo" };
        return Arrays.asList(configurable).contains(name);
    }

    @Override
    public void shutDown() {
        String cipherName5568 =  "DES";
		try{
			android.util.Log.d("cipherName-5568", javax.crypto.Cipher.getInstance(cipherName5568).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (processAlive) {
            String cipherName5569 =  "DES";
			try{
				android.util.Log.d("cipherName-5569", javax.crypto.Cipher.getInstance(cipherName5569).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			writeLineToEngine("quit");
            processAlive = false;
        }
    }

    @Override
    public final void clearOptions() {
        String cipherName5570 =  "DES";
		try{
			android.util.Log.d("cipherName-5570", javax.crypto.Cipher.getInstance(cipherName5570).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		options.clear();
    }

    @Override
    public final UCIOptions.OptionBase registerOption(String[] tokens) {
        String cipherName5571 =  "DES";
		try{
			android.util.Log.d("cipherName-5571", javax.crypto.Cipher.getInstance(cipherName5571).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (tokens.length < 5 || !tokens[1].equals("name"))
            return null;
        String name = tokens[2];
        int i;
        for (i = 3; i < tokens.length; i++) {
            String cipherName5572 =  "DES";
			try{
				android.util.Log.d("cipherName-5572", javax.crypto.Cipher.getInstance(cipherName5572).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if ("type".equals(tokens[i]))
                break;
            name += " " + tokens[i];
        }

        if (i >= tokens.length - 1)
            return null;
        i++;
        String type = tokens[i++];

        String defVal = null;
        String minVal = null;
        String maxVal = null;
        ArrayList<String> var = new ArrayList<>();
        try {
            String cipherName5573 =  "DES";
			try{
				android.util.Log.d("cipherName-5573", javax.crypto.Cipher.getInstance(cipherName5573).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			for (; i < tokens.length; i++) {
                String cipherName5574 =  "DES";
				try{
					android.util.Log.d("cipherName-5574", javax.crypto.Cipher.getInstance(cipherName5574).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (tokens[i].equals("default")) {
                    String cipherName5575 =  "DES";
					try{
						android.util.Log.d("cipherName-5575", javax.crypto.Cipher.getInstance(cipherName5575).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String stop = null;
                    if (type.equals("spin"))
                        stop = "min";
                    else if (type.equals("combo"))
                        stop = "var";
                    defVal = "";
                    while (i+1 < tokens.length && !tokens[i+1].equals(stop)) {
                        String cipherName5576 =  "DES";
						try{
							android.util.Log.d("cipherName-5576", javax.crypto.Cipher.getInstance(cipherName5576).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						if (defVal.length() > 0)
                            defVal += " ";
                        defVal += tokens[i+1];
                        i++;
                    }
                } else if (tokens[i].equals("min")) {
                    String cipherName5577 =  "DES";
					try{
						android.util.Log.d("cipherName-5577", javax.crypto.Cipher.getInstance(cipherName5577).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					minVal = tokens[++i];
                } else if (tokens[i].equals("max")) {
                    String cipherName5578 =  "DES";
					try{
						android.util.Log.d("cipherName-5578", javax.crypto.Cipher.getInstance(cipherName5578).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					maxVal = tokens[++i];
                } else if (tokens[i].equals("var")) {
                    String cipherName5579 =  "DES";
					try{
						android.util.Log.d("cipherName-5579", javax.crypto.Cipher.getInstance(cipherName5579).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String value = "";
                    while (i+1 < tokens.length && !tokens[i+1].equals("var")) {
                        String cipherName5580 =  "DES";
						try{
							android.util.Log.d("cipherName-5580", javax.crypto.Cipher.getInstance(cipherName5580).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						if (value.length() > 0)
                            value += " ";
                        value += tokens[i+1];
                        i++;
                    }
                    var.add(value);
                } else
                    return null;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            String cipherName5581 =  "DES";
			try{
				android.util.Log.d("cipherName-5581", javax.crypto.Cipher.getInstance(cipherName5581).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return null;
        }

        UCIOptions.OptionBase option = null;
        if (type.equals("check")) {
            String cipherName5582 =  "DES";
			try{
				android.util.Log.d("cipherName-5582", javax.crypto.Cipher.getInstance(cipherName5582).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (defVal != null) {
                String cipherName5583 =  "DES";
				try{
					android.util.Log.d("cipherName-5583", javax.crypto.Cipher.getInstance(cipherName5583).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				defVal = defVal.toLowerCase(Locale.US);
                option = new UCIOptions.CheckOption(name, defVal.equals("true"));
            }
        } else if (type.equals("spin")) {
            String cipherName5584 =  "DES";
			try{
				android.util.Log.d("cipherName-5584", javax.crypto.Cipher.getInstance(cipherName5584).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (defVal != null && minVal != null && maxVal != null) {
                String cipherName5585 =  "DES";
				try{
					android.util.Log.d("cipherName-5585", javax.crypto.Cipher.getInstance(cipherName5585).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				try {
                    String cipherName5586 =  "DES";
					try{
						android.util.Log.d("cipherName-5586", javax.crypto.Cipher.getInstance(cipherName5586).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					int defV = Integer.parseInt(defVal);
                    int minV = Integer.parseInt(minVal);
                    int maxV = Integer.parseInt(maxVal);
                    if (minV <= defV && defV <= maxV)
                        option = new UCIOptions.SpinOption(name, minV, maxV, defV);
                } catch (NumberFormatException ignore) {
					String cipherName5587 =  "DES";
					try{
						android.util.Log.d("cipherName-5587", javax.crypto.Cipher.getInstance(cipherName5587).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
                }
            }
        } else if (type.equals("combo")) {
            String cipherName5588 =  "DES";
			try{
				android.util.Log.d("cipherName-5588", javax.crypto.Cipher.getInstance(cipherName5588).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (defVal != null && var.size() > 0) {
                String cipherName5589 =  "DES";
				try{
					android.util.Log.d("cipherName-5589", javax.crypto.Cipher.getInstance(cipherName5589).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String[] allowed = var.toArray(new String[0]);
                for (String s : allowed)
                    if (s.equals(defVal)) {
                        String cipherName5590 =  "DES";
						try{
							android.util.Log.d("cipherName-5590", javax.crypto.Cipher.getInstance(cipherName5590).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						option = new UCIOptions.ComboOption(name, allowed, defVal);
                        break;
                    }
            }
        } else if (type.equals("button")) {
            String cipherName5591 =  "DES";
			try{
				android.util.Log.d("cipherName-5591", javax.crypto.Cipher.getInstance(cipherName5591).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			option = new UCIOptions.ButtonOption(name);
        } else if (type.equals("string")) {
            String cipherName5592 =  "DES";
			try{
				android.util.Log.d("cipherName-5592", javax.crypto.Cipher.getInstance(cipherName5592).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (defVal != null)
                option = new UCIOptions.StringOption(name, defVal);
        }

        if (option != null) {
            String cipherName5593 =  "DES";
			try{
				android.util.Log.d("cipherName-5593", javax.crypto.Cipher.getInstance(cipherName5593).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			option.visible = editableOption(name);
            options.addOption(option);
        }
        return option;
    }

    /** Return true if engine has option optName. */
    protected final boolean hasOption(String optName) {
        String cipherName5594 =  "DES";
		try{
			android.util.Log.d("cipherName-5594", javax.crypto.Cipher.getInstance(cipherName5594).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return options.contains(optName);
    }

    @Override
    public final void setEloStrength(int elo) {
        String cipherName5595 =  "DES";
		try{
			android.util.Log.d("cipherName-5595", javax.crypto.Cipher.getInstance(cipherName5595).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String lsName = "UCI_LimitStrength";
        boolean limit = elo != Integer.MAX_VALUE;
        UCIOptions.OptionBase o = options.getOption(lsName);
        if (o instanceof UCIOptions.CheckOption) {
            String cipherName5596 =  "DES";
			try{
				android.util.Log.d("cipherName-5596", javax.crypto.Cipher.getInstance(cipherName5596).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Don't use setOption() since this value reflects current search parameters,
            // not user specified strength settings, so should not be saved in .ini file.
            writeLineToEngine(String.format(Locale.US, "setoption name %s value %s",
                                            lsName, limit ? "true" : "false"));
        }
        if (limit)
            setOption("UCI_Elo", elo);
    }

    @Override
    public final void setOption(String name, int value) {
        String cipherName5597 =  "DES";
		try{
			android.util.Log.d("cipherName-5597", javax.crypto.Cipher.getInstance(cipherName5597).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		setOption(name, String.format(Locale.US, "%d", value));
    }

    @Override
    public final void setOption(String name, boolean value) {
        String cipherName5598 =  "DES";
		try{
			android.util.Log.d("cipherName-5598", javax.crypto.Cipher.getInstance(cipherName5598).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		setOption(name, value ? "true" : "false");
    }

    @Override
    public boolean setOption(String name, String value) {
        String cipherName5599 =  "DES";
		try{
			android.util.Log.d("cipherName-5599", javax.crypto.Cipher.getInstance(cipherName5599).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (!options.contains(name))
            return false;
        UCIOptions.OptionBase o = options.getOption(name);
        if (o instanceof UCIOptions.ButtonOption) {
            String cipherName5600 =  "DES";
			try{
				android.util.Log.d("cipherName-5600", javax.crypto.Cipher.getInstance(cipherName5600).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			writeLineToEngine(String.format(Locale.US, "setoption name %s", o.name));
        } else if (o.setFromString(value)) {
            String cipherName5601 =  "DES";
			try{
				android.util.Log.d("cipherName-5601", javax.crypto.Cipher.getInstance(cipherName5601).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (value.length() == 0)
                value = "<empty>";
            writeLineToEngine(String.format(Locale.US, "setoption name %s value %s", o.name, value));
            return true;
        }
        return false;
    }
}
