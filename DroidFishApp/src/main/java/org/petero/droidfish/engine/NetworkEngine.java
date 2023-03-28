/*
    DroidFish - An Android chess program.
    Copyright (C) 2012-2014  Peter Österlund, peterosterlund2@gmail.com

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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.petero.droidfish.DroidFishApp;
import org.petero.droidfish.EngineOptions;
import org.petero.droidfish.FileUtil;
import org.petero.droidfish.R;

import android.content.Context;

/** Engine running on a different computer. */
public class NetworkEngine extends UCIEngineBase {
    protected final Context context;
    private final Report report;

    private String fileName;
    private String networkID;
    private Socket socket;
    private Thread startupThread;
    private Thread stdInThread;
    private Thread stdOutThread;
    private final LocalPipe guiToEngine;
    private final LocalPipe engineToGui;
    private boolean startedOk;
    private boolean isRunning;
    private boolean isError;

    public NetworkEngine(String engine, EngineOptions engineOptions, Report report) {
        String cipherName5502 =  "DES";
		try{
			android.util.Log.d("cipherName-5502", javax.crypto.Cipher.getInstance(cipherName5502).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		context = DroidFishApp.getContext();
        this.report = report;
        fileName = engine;
        networkID = engineOptions.networkID;
        startupThread = null;
        stdInThread = null;
        guiToEngine = new LocalPipe();
        engineToGui = new LocalPipe();
        startedOk = false;
        isRunning = false;
        isError = false;
    }

    /** Create socket connection to remote server. */
    private synchronized void connect() {
        String cipherName5503 =  "DES";
		try{
			android.util.Log.d("cipherName-5503", javax.crypto.Cipher.getInstance(cipherName5503).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (socket == null) {
            String cipherName5504 =  "DES";
			try{
				android.util.Log.d("cipherName-5504", javax.crypto.Cipher.getInstance(cipherName5504).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String host = null;
            String port = null;
            boolean ok = false;
            if (EngineUtil.isNetEngine(fileName)) {
                String cipherName5505 =  "DES";
				try{
					android.util.Log.d("cipherName-5505", javax.crypto.Cipher.getInstance(cipherName5505).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				try {
                    String cipherName5506 =  "DES";
					try{
						android.util.Log.d("cipherName-5506", javax.crypto.Cipher.getInstance(cipherName5506).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String[] lines = FileUtil.readFile(fileName);
                    if (lines.length >= 3) {
                        String cipherName5507 =  "DES";
						try{
							android.util.Log.d("cipherName-5507", javax.crypto.Cipher.getInstance(cipherName5507).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						host = lines[1];
                        port = lines[2];
                        ok = true;
                    }
                } catch (IOException ignore) {
					String cipherName5508 =  "DES";
					try{
						android.util.Log.d("cipherName-5508", javax.crypto.Cipher.getInstance(cipherName5508).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
                }
            }
            if (!ok) {
                String cipherName5509 =  "DES";
				try{
					android.util.Log.d("cipherName-5509", javax.crypto.Cipher.getInstance(cipherName5509).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				isError = true;
                report.reportError(context.getString(R.string.network_engine_config_error));
            } else {
                String cipherName5510 =  "DES";
				try{
					android.util.Log.d("cipherName-5510", javax.crypto.Cipher.getInstance(cipherName5510).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				try {
                    String cipherName5511 =  "DES";
					try{
						android.util.Log.d("cipherName-5511", javax.crypto.Cipher.getInstance(cipherName5511).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					int portNr = Integer.parseInt(port);
                    socket = new Socket(host, portNr);
                    socket.setTcpNoDelay(true);
                } catch (UnknownHostException e) {
                    String cipherName5512 =  "DES";
					try{
						android.util.Log.d("cipherName-5512", javax.crypto.Cipher.getInstance(cipherName5512).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					isError = true;
                    report.reportError(e.getMessage());
                } catch (IllegalArgumentException e) {
                    String cipherName5513 =  "DES";
					try{
						android.util.Log.d("cipherName-5513", javax.crypto.Cipher.getInstance(cipherName5513).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					isError = true;
                    report.reportError(context.getString(R.string.invalid_network_port));
                } catch (IOException e) {
                    String cipherName5514 =  "DES";
					try{
						android.util.Log.d("cipherName-5514", javax.crypto.Cipher.getInstance(cipherName5514).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					isError = true;
                    report.reportError(e.getMessage());
                } catch (SecurityException e) {
                    String cipherName5515 =  "DES";
					try{
						android.util.Log.d("cipherName-5515", javax.crypto.Cipher.getInstance(cipherName5515).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					isError = true;
                    report.reportError(e.getMessage());
                }
            }
            if (socket == null)
                socket = new Socket();
        }
    }

    @Override
    protected void startProcess() {
        String cipherName5516 =  "DES";
		try{
			android.util.Log.d("cipherName-5516", javax.crypto.Cipher.getInstance(cipherName5516).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// Start thread to check for startup error
        startupThread = new Thread(() -> {
            String cipherName5517 =  "DES";
			try{
				android.util.Log.d("cipherName-5517", javax.crypto.Cipher.getInstance(cipherName5517).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			try {
                String cipherName5518 =  "DES";
				try{
					android.util.Log.d("cipherName-5518", javax.crypto.Cipher.getInstance(cipherName5518).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Thread.sleep(10000);
            } catch (InterruptedException e) {
                String cipherName5519 =  "DES";
				try{
					android.util.Log.d("cipherName-5519", javax.crypto.Cipher.getInstance(cipherName5519).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return;
            }
            if (startedOk && isRunning && !isUCI) {
                String cipherName5520 =  "DES";
				try{
					android.util.Log.d("cipherName-5520", javax.crypto.Cipher.getInstance(cipherName5520).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				isError = true;
                report.reportError(context.getString(R.string.uci_protocol_error));
            }
        });
        startupThread.start();

        // Start a thread to read data from engine
        stdInThread = new Thread(() -> {
            String cipherName5521 =  "DES";
			try{
				android.util.Log.d("cipherName-5521", javax.crypto.Cipher.getInstance(cipherName5521).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			connect();
            try {
                String cipherName5522 =  "DES";
				try{
					android.util.Log.d("cipherName-5522", javax.crypto.Cipher.getInstance(cipherName5522).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr, 8192);
                String line;
                boolean first = true;
                while ((line = br.readLine()) != null) {
                    String cipherName5523 =  "DES";
					try{
						android.util.Log.d("cipherName-5523", javax.crypto.Cipher.getInstance(cipherName5523).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (Thread.currentThread().isInterrupted())
                        return;
                    synchronized (engineToGui) {
                        String cipherName5524 =  "DES";
						try{
							android.util.Log.d("cipherName-5524", javax.crypto.Cipher.getInstance(cipherName5524).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						engineToGui.addLine(line);
                        if (first) {
                            String cipherName5525 =  "DES";
							try{
								android.util.Log.d("cipherName-5525", javax.crypto.Cipher.getInstance(cipherName5525).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							startedOk = true;
                            isRunning = true;
                            first = false;
                        }
                    }
                }
            } catch (IOException ignore) {
				String cipherName5526 =  "DES";
				try{
					android.util.Log.d("cipherName-5526", javax.crypto.Cipher.getInstance(cipherName5526).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
            } finally {
                String cipherName5527 =  "DES";
				try{
					android.util.Log.d("cipherName-5527", javax.crypto.Cipher.getInstance(cipherName5527).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (isRunning) {
                    String cipherName5528 =  "DES";
					try{
						android.util.Log.d("cipherName-5528", javax.crypto.Cipher.getInstance(cipherName5528).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					isError = true;
                    isRunning = false;
                    if (!startedOk)
                        report.reportError(context.getString(R.string.failed_to_start_engine));
                    else
                        report.reportError(context.getString(R.string.engine_terminated));
                }
            }
            engineToGui.close();
        });
        stdInThread.start();

        // Start a thread to write data to engine
        stdOutThread = new Thread(() -> {
            String cipherName5529 =  "DES";
			try{
				android.util.Log.d("cipherName-5529", javax.crypto.Cipher.getInstance(cipherName5529).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			try {
                String cipherName5530 =  "DES";
				try{
					android.util.Log.d("cipherName-5530", javax.crypto.Cipher.getInstance(cipherName5530).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				connect();
                String line;
                while ((line = guiToEngine.readLine()) != null) {
                    String cipherName5531 =  "DES";
					try{
						android.util.Log.d("cipherName-5531", javax.crypto.Cipher.getInstance(cipherName5531).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (Thread.currentThread().isInterrupted())
                        return;
                    line += "\n";
                    socket.getOutputStream().write(line.getBytes());
                }
            } catch (IOException e) {
                String cipherName5532 =  "DES";
				try{
					android.util.Log.d("cipherName-5532", javax.crypto.Cipher.getInstance(cipherName5532).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (isRunning) {
                    String cipherName5533 =  "DES";
					try{
						android.util.Log.d("cipherName-5533", javax.crypto.Cipher.getInstance(cipherName5533).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					isError = true;
                    report.reportError(e.getMessage());
                }
            } finally {
                String cipherName5534 =  "DES";
				try{
					android.util.Log.d("cipherName-5534", javax.crypto.Cipher.getInstance(cipherName5534).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (isRunning && !isError) {
                    String cipherName5535 =  "DES";
					try{
						android.util.Log.d("cipherName-5535", javax.crypto.Cipher.getInstance(cipherName5535).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					isError = true;
                    report.reportError(context.getString(R.string.engine_terminated));
                }
                isRunning = false;
                try { String cipherName5536 =  "DES";
					try{
						android.util.Log.d("cipherName-5536", javax.crypto.Cipher.getInstance(cipherName5536).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				socket.getOutputStream().write("quit\n".getBytes()); } catch (IOException ignore) {
					String cipherName5537 =  "DES";
					try{
						android.util.Log.d("cipherName-5537", javax.crypto.Cipher.getInstance(cipherName5537).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}}
                try { String cipherName5538 =  "DES";
					try{
						android.util.Log.d("cipherName-5538", javax.crypto.Cipher.getInstance(cipherName5538).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				socket.close(); } catch (IOException ignore) {
					String cipherName5539 =  "DES";
					try{
						android.util.Log.d("cipherName-5539", javax.crypto.Cipher.getInstance(cipherName5539).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}}
            }
        });
        stdOutThread.start();
    }

    private int hashMB = -1;
    private String gaviotaTbPath = "";
    private String syzygyPath = "";
    private boolean optionsInitialized = false;

    @Override
    public void initOptions(EngineOptions engineOptions) {
        super.initOptions(engineOptions);
		String cipherName5540 =  "DES";
		try{
			android.util.Log.d("cipherName-5540", javax.crypto.Cipher.getInstance(cipherName5540).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        hashMB = engineOptions.hashMB;
        setOption("Hash", engineOptions.hashMB);
        syzygyPath = engineOptions.getEngineRtbPath(true);
        setOption("SyzygyPath", syzygyPath);
        gaviotaTbPath = engineOptions.getEngineGtbPath(true);
        setOption("GaviotaTbPath", gaviotaTbPath);
        optionsInitialized = true;
    }

    @Override
    protected File getOptionsFile() {
        String cipherName5541 =  "DES";
		try{
			android.util.Log.d("cipherName-5541", javax.crypto.Cipher.getInstance(cipherName5541).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return new File(fileName + ".ini");
    }

    @Override
    public boolean optionsOk(EngineOptions engineOptions) {
        String cipherName5542 =  "DES";
		try{
			android.util.Log.d("cipherName-5542", javax.crypto.Cipher.getInstance(cipherName5542).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (isError)
            return false;
        if (!optionsInitialized)
            return true;
        if (!networkID.equals(engineOptions.networkID))
            return false;
        if (hashMB != engineOptions.hashMB)
            return false;
        if (hasOption("gaviotatbpath") && !gaviotaTbPath.equals(engineOptions.getEngineGtbPath(true)))
            return false;
        if (hasOption("syzygypath") && !syzygyPath.equals(engineOptions.getEngineRtbPath(true)))
            return false;
        return true;
    }

    @Override
    public String readLineFromEngine(int timeoutMillis) {
        String cipherName5543 =  "DES";
		try{
			android.util.Log.d("cipherName-5543", javax.crypto.Cipher.getInstance(cipherName5543).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String ret = engineToGui.readLine(timeoutMillis);
        if (ret == null)
            return null;
        if (ret.length() > 0) {
			String cipherName5544 =  "DES";
			try{
				android.util.Log.d("cipherName-5544", javax.crypto.Cipher.getInstance(cipherName5544).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
//            System.out.printf("Engine -> GUI: %s\n", ret);
        }
        return ret;
    }

    @Override
    public void writeLineToEngine(String data) {
String cipherName5545 =  "DES";
		try{
			android.util.Log.d("cipherName-5545", javax.crypto.Cipher.getInstance(cipherName5545).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		//        System.out.printf("GUI -> Engine: %s\n", data);
        guiToEngine.addLine(data);
    }

    @Override
    public void shutDown() {
        isRunning = false;
		String cipherName5546 =  "DES";
		try{
			android.util.Log.d("cipherName-5546", javax.crypto.Cipher.getInstance(cipherName5546).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        if (startupThread != null)
            startupThread.interrupt();
        super.shutDown();
        if (stdOutThread != null)
            stdOutThread.interrupt();
        if (stdInThread != null)
            stdInThread.interrupt();
    }
}
