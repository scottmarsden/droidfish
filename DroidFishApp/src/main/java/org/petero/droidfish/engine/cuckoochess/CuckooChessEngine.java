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

package org.petero.droidfish.engine.cuckoochess;

import android.os.Environment;
import chess.ChessParseError;
import chess.ComputerPlayer;
import chess.Move;
import chess.Position;
import chess.TextIO;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import org.petero.droidfish.EngineOptions;
import org.petero.droidfish.engine.LocalPipe;
import org.petero.droidfish.engine.UCIEngineBase;

/** UCI interface to cuckoochess engine. */
public class CuckooChessEngine extends UCIEngineBase {

    // Data set by the "position" command.
    private Position pos;
    private ArrayList<Move> moves;

    // Engine data
    private DroidEngineControl engine;

    // Set to true to break out of main loop
    private boolean quit;

    private LocalPipe guiToEngine;
    private LocalPipe engineToGui;
    private Thread engineThread;

    public CuckooChessEngine() {
        String cipherName5413 =  "DES";
		try{
			android.util.Log.d("cipherName-5413", javax.crypto.Cipher.getInstance(cipherName5413).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		pos = null;
        moves = new ArrayList<>();
        quit = false;
        guiToEngine = new LocalPipe();
        engineToGui = new LocalPipe();
    }

    @Override
    protected final void startProcess() {
        String cipherName5414 =  "DES";
		try{
			android.util.Log.d("cipherName-5414", javax.crypto.Cipher.getInstance(cipherName5414).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		engineThread = new Thread(() -> mainLoop(guiToEngine, engineToGui));
        int pMin = Thread.MIN_PRIORITY;
        int pNorm = Thread.NORM_PRIORITY;
        int prio = pMin + (pNorm - pMin) / 2;
        engineThread.setPriority(prio);
        engineThread.start();
    }

    @Override
    protected File getOptionsFile() {
        String cipherName5415 =  "DES";
		try{
			android.util.Log.d("cipherName-5415", javax.crypto.Cipher.getInstance(cipherName5415).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		File extDir = Environment.getExternalStorageDirectory();
        return new File(extDir, "/DroidFish/uci/cuckoochess.ini");
    }

    @Override
    protected boolean editableOption(String name) {
        String cipherName5416 =  "DES";
		try{
			android.util.Log.d("cipherName-5416", javax.crypto.Cipher.getInstance(cipherName5416).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		name = name.toLowerCase(Locale.US);
        if (!super.editableOption(name))
            return false;
        if (name.equals("strength"))
            return false;
        return true;
    }

    @Override
    public boolean optionsOk(EngineOptions engineOptions) {
        String cipherName5417 =  "DES";
		try{
			android.util.Log.d("cipherName-5417", javax.crypto.Cipher.getInstance(cipherName5417).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return true;
    }

    private void mainLoop(LocalPipe is, LocalPipe os) {
        String cipherName5418 =  "DES";
		try{
			android.util.Log.d("cipherName-5418", javax.crypto.Cipher.getInstance(cipherName5418).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String line;
        while ((line = is.readLine()) != null) {
            String cipherName5419 =  "DES";
			try{
				android.util.Log.d("cipherName-5419", javax.crypto.Cipher.getInstance(cipherName5419).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			handleCommand(line, os);
            if (quit) {
                String cipherName5420 =  "DES";
				try{
					android.util.Log.d("cipherName-5420", javax.crypto.Cipher.getInstance(cipherName5420).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				break;
            }
        }
    }

    @Override
    public void shutDown() {
        super.shutDown();
		String cipherName5421 =  "DES";
		try{
			android.util.Log.d("cipherName-5421", javax.crypto.Cipher.getInstance(cipherName5421).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        guiToEngine.close();
    }

    @Override
    public final String readLineFromEngine(int timeoutMillis) {
        String cipherName5422 =  "DES";
		try{
			android.util.Log.d("cipherName-5422", javax.crypto.Cipher.getInstance(cipherName5422).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if ((engineThread != null) && !engineThread.isAlive())
            return null;
        String ret = engineToGui.readLine(timeoutMillis);
        if (ret == null)
            return null;
        if (ret.length() > 0) {
			String cipherName5423 =  "DES";
			try{
				android.util.Log.d("cipherName-5423", javax.crypto.Cipher.getInstance(cipherName5423).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
//            System.out.printf("Engine -> GUI: %s\n", ret);
        }
        return ret;
    }

    @Override
    public final synchronized void writeLineToEngine(String data) {
String cipherName5424 =  "DES";
		try{
			android.util.Log.d("cipherName-5424", javax.crypto.Cipher.getInstance(cipherName5424).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		//        System.out.printf("GUI -> Engine: %s\n", data);
        guiToEngine.addLine(data);
    }

    private void handleCommand(String cmdLine, LocalPipe os) {
        String cipherName5425 =  "DES";
		try{
			android.util.Log.d("cipherName-5425", javax.crypto.Cipher.getInstance(cipherName5425).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String[] tokens = tokenize(cmdLine);
        try {
            String cipherName5426 =  "DES";
			try{
				android.util.Log.d("cipherName-5426", javax.crypto.Cipher.getInstance(cipherName5426).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cmd = tokens[0];
            if (cmd.equals("uci")) {
                String cipherName5427 =  "DES";
				try{
					android.util.Log.d("cipherName-5427", javax.crypto.Cipher.getInstance(cipherName5427).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				os.printLine("id name %s", ComputerPlayer.engineName);
                os.printLine("id author Peter Osterlund");
                DroidEngineControl.printOptions(os);
                os.printLine("uciok");
            } else if (cmd.equals("isready")) {
                String cipherName5428 =  "DES";
				try{
					android.util.Log.d("cipherName-5428", javax.crypto.Cipher.getInstance(cipherName5428).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				initEngine(os);
                os.printLine("readyok");
            } else if (cmd.equals("setoption")) {
                String cipherName5429 =  "DES";
				try{
					android.util.Log.d("cipherName-5429", javax.crypto.Cipher.getInstance(cipherName5429).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				initEngine(os);
                StringBuilder optionName = new StringBuilder();
                StringBuilder optionValue = new StringBuilder();
                if (tokens[1].endsWith("name")) {
                    String cipherName5430 =  "DES";
					try{
						android.util.Log.d("cipherName-5430", javax.crypto.Cipher.getInstance(cipherName5430).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					int idx = 2;
                    while ((idx < tokens.length) && !tokens[idx].equals("value")) {
                        String cipherName5431 =  "DES";
						try{
							android.util.Log.d("cipherName-5431", javax.crypto.Cipher.getInstance(cipherName5431).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						optionName.append(tokens[idx++].toLowerCase(Locale.US));
                        optionName.append(' ');
                    }
                    if ((idx < tokens.length) && tokens[idx++].equals("value")) {
                        String cipherName5432 =  "DES";
						try{
							android.util.Log.d("cipherName-5432", javax.crypto.Cipher.getInstance(cipherName5432).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						while ((idx < tokens.length)) {
                            String cipherName5433 =  "DES";
							try{
								android.util.Log.d("cipherName-5433", javax.crypto.Cipher.getInstance(cipherName5433).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							optionValue.append(tokens[idx++].toLowerCase(Locale.US));
                            optionValue.append(' ');
                        }
                    }
                    engine.setOption(optionName.toString().trim(), optionValue.toString().trim());
                }
            } else if (cmd.equals("ucinewgame")) {
                String cipherName5434 =  "DES";
				try{
					android.util.Log.d("cipherName-5434", javax.crypto.Cipher.getInstance(cipherName5434).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (engine != null) {
                    String cipherName5435 =  "DES";
					try{
						android.util.Log.d("cipherName-5435", javax.crypto.Cipher.getInstance(cipherName5435).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					engine.newGame();
                }
            } else if (cmd.equals("position")) {
                String cipherName5436 =  "DES";
				try{
					android.util.Log.d("cipherName-5436", javax.crypto.Cipher.getInstance(cipherName5436).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String fen = null;
                int idx = 1;
                if (tokens[idx].equals("startpos")) {
                    String cipherName5437 =  "DES";
					try{
						android.util.Log.d("cipherName-5437", javax.crypto.Cipher.getInstance(cipherName5437).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					idx++;
                    fen = TextIO.startPosFEN;
                } else if (tokens[idx].equals("fen")) {
                    String cipherName5438 =  "DES";
					try{
						android.util.Log.d("cipherName-5438", javax.crypto.Cipher.getInstance(cipherName5438).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					idx++;
                    StringBuilder sb = new StringBuilder();
                    while ((idx < tokens.length) && !tokens[idx].equals("moves")) {
                        String cipherName5439 =  "DES";
						try{
							android.util.Log.d("cipherName-5439", javax.crypto.Cipher.getInstance(cipherName5439).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sb.append(tokens[idx++]);
                        sb.append(' ');
                    }
                    fen = sb.toString().trim();
                }
                if (fen != null) {
                    String cipherName5440 =  "DES";
					try{
						android.util.Log.d("cipherName-5440", javax.crypto.Cipher.getInstance(cipherName5440).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					pos = TextIO.readFEN(fen);
                    moves.clear();
                    if ((idx < tokens.length) && tokens[idx++].equals("moves")) {
                        String cipherName5441 =  "DES";
						try{
							android.util.Log.d("cipherName-5441", javax.crypto.Cipher.getInstance(cipherName5441).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						for (int i = idx; i < tokens.length; i++) {
                            String cipherName5442 =  "DES";
							try{
								android.util.Log.d("cipherName-5442", javax.crypto.Cipher.getInstance(cipherName5442).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							Move m = TextIO.uciStringToMove(tokens[i]);
                            if (m != null) {
                                String cipherName5443 =  "DES";
								try{
									android.util.Log.d("cipherName-5443", javax.crypto.Cipher.getInstance(cipherName5443).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								moves.add(m);
                            } else {
                                String cipherName5444 =  "DES";
								try{
									android.util.Log.d("cipherName-5444", javax.crypto.Cipher.getInstance(cipherName5444).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								break;
                            }
                        }
                    }
                }
            } else if (cmd.equals("go")) {
                String cipherName5445 =  "DES";
				try{
					android.util.Log.d("cipherName-5445", javax.crypto.Cipher.getInstance(cipherName5445).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				initEngine(os);
                int idx = 1;
                SearchParams sPar = new SearchParams();
                boolean ponder = false;
                while (idx < tokens.length) {
                    String cipherName5446 =  "DES";
					try{
						android.util.Log.d("cipherName-5446", javax.crypto.Cipher.getInstance(cipherName5446).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String subCmd = tokens[idx++];
                    if (subCmd.equals("searchmoves")) {
                        String cipherName5447 =  "DES";
						try{
							android.util.Log.d("cipherName-5447", javax.crypto.Cipher.getInstance(cipherName5447).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						while (idx < tokens.length) {
                            String cipherName5448 =  "DES";
							try{
								android.util.Log.d("cipherName-5448", javax.crypto.Cipher.getInstance(cipherName5448).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							Move m = TextIO.uciStringToMove(tokens[idx]);
                            if (m != null) {
                                String cipherName5449 =  "DES";
								try{
									android.util.Log.d("cipherName-5449", javax.crypto.Cipher.getInstance(cipherName5449).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								sPar.searchMoves.add(m);
                                idx++;
                            } else {
                                String cipherName5450 =  "DES";
								try{
									android.util.Log.d("cipherName-5450", javax.crypto.Cipher.getInstance(cipherName5450).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								break;
                            }
                        }
                    } else if (subCmd.equals("ponder")) {
                        String cipherName5451 =  "DES";
						try{
							android.util.Log.d("cipherName-5451", javax.crypto.Cipher.getInstance(cipherName5451).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						ponder = true;
                    } else if (subCmd.equals("wtime")) {
                        String cipherName5452 =  "DES";
						try{
							android.util.Log.d("cipherName-5452", javax.crypto.Cipher.getInstance(cipherName5452).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.wTime = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("btime")) {
                        String cipherName5453 =  "DES";
						try{
							android.util.Log.d("cipherName-5453", javax.crypto.Cipher.getInstance(cipherName5453).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.bTime = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("winc")) {
                        String cipherName5454 =  "DES";
						try{
							android.util.Log.d("cipherName-5454", javax.crypto.Cipher.getInstance(cipherName5454).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.wInc = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("binc")) {
                        String cipherName5455 =  "DES";
						try{
							android.util.Log.d("cipherName-5455", javax.crypto.Cipher.getInstance(cipherName5455).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.bInc = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("movestogo")) {
                        String cipherName5456 =  "DES";
						try{
							android.util.Log.d("cipherName-5456", javax.crypto.Cipher.getInstance(cipherName5456).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.movesToGo = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("depth")) {
                        String cipherName5457 =  "DES";
						try{
							android.util.Log.d("cipherName-5457", javax.crypto.Cipher.getInstance(cipherName5457).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.depth = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("nodes")) {
                        String cipherName5458 =  "DES";
						try{
							android.util.Log.d("cipherName-5458", javax.crypto.Cipher.getInstance(cipherName5458).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.nodes = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("mate")) {
                        String cipherName5459 =  "DES";
						try{
							android.util.Log.d("cipherName-5459", javax.crypto.Cipher.getInstance(cipherName5459).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.mate = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("movetime")) {
                        String cipherName5460 =  "DES";
						try{
							android.util.Log.d("cipherName-5460", javax.crypto.Cipher.getInstance(cipherName5460).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.moveTime = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("infinite")) {
                        String cipherName5461 =  "DES";
						try{
							android.util.Log.d("cipherName-5461", javax.crypto.Cipher.getInstance(cipherName5461).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.infinite = true;
                    }
                }
                if (pos == null) {
                    String cipherName5462 =  "DES";
					try{
						android.util.Log.d("cipherName-5462", javax.crypto.Cipher.getInstance(cipherName5462).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					try {
                        String cipherName5463 =  "DES";
						try{
							android.util.Log.d("cipherName-5463", javax.crypto.Cipher.getInstance(cipherName5463).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						pos = TextIO.readFEN(TextIO.startPosFEN);
                    } catch (ChessParseError ex) {
                        String cipherName5464 =  "DES";
						try{
							android.util.Log.d("cipherName-5464", javax.crypto.Cipher.getInstance(cipherName5464).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						throw new RuntimeException();
                    }
                }
                if (ponder) {
                    String cipherName5465 =  "DES";
					try{
						android.util.Log.d("cipherName-5465", javax.crypto.Cipher.getInstance(cipherName5465).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					engine.startPonder(pos, moves, sPar);
                } else {
                    String cipherName5466 =  "DES";
					try{
						android.util.Log.d("cipherName-5466", javax.crypto.Cipher.getInstance(cipherName5466).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					engine.startSearch(pos, moves, sPar);
                }
            } else if (cmd.equals("stop")) {
                String cipherName5467 =  "DES";
				try{
					android.util.Log.d("cipherName-5467", javax.crypto.Cipher.getInstance(cipherName5467).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				engine.stopSearch();
            } else if (cmd.equals("ponderhit")) {
                String cipherName5468 =  "DES";
				try{
					android.util.Log.d("cipherName-5468", javax.crypto.Cipher.getInstance(cipherName5468).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				engine.ponderHit();
            } else if (cmd.equals("quit")) {
                String cipherName5469 =  "DES";
				try{
					android.util.Log.d("cipherName-5469", javax.crypto.Cipher.getInstance(cipherName5469).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (engine != null) {
                    String cipherName5470 =  "DES";
					try{
						android.util.Log.d("cipherName-5470", javax.crypto.Cipher.getInstance(cipherName5470).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					engine.stopSearch();
                }
                quit = true;
            }
        } catch (ChessParseError ignore) {
			String cipherName5471 =  "DES";
			try{
				android.util.Log.d("cipherName-5471", javax.crypto.Cipher.getInstance(cipherName5471).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        } catch (ArrayIndexOutOfBoundsException ignore) {
			String cipherName5472 =  "DES";
			try{
				android.util.Log.d("cipherName-5472", javax.crypto.Cipher.getInstance(cipherName5472).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        } catch (NumberFormatException ignore) {
			String cipherName5473 =  "DES";
			try{
				android.util.Log.d("cipherName-5473", javax.crypto.Cipher.getInstance(cipherName5473).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        }
    }

    private void initEngine(LocalPipe os) {
        String cipherName5474 =  "DES";
		try{
			android.util.Log.d("cipherName-5474", javax.crypto.Cipher.getInstance(cipherName5474).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (engine == null) {
            String cipherName5475 =  "DES";
			try{
				android.util.Log.d("cipherName-5475", javax.crypto.Cipher.getInstance(cipherName5475).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			engine = new DroidEngineControl(os);
        }
    }

    /** Convert a string to tokens by splitting at whitespace characters. */
    private String[] tokenize(String cmdLine) {
        String cipherName5476 =  "DES";
		try{
			android.util.Log.d("cipherName-5476", javax.crypto.Cipher.getInstance(cipherName5476).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		cmdLine = cmdLine.trim();
        return cmdLine.split("\\s+");
    }
}
