/*
    CuckooChess - A java chess program.
    Copyright (C) 2011  Peter Österlund, peterosterlund2@gmail.com

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

package uci;

import chess.ChessParseError;
import chess.ComputerPlayer;
import chess.Move;
import chess.Position;
import chess.TextIO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

/** Handle the UCI protocol mode. */
public class UCIProtocol {
    // Data set by the "position" command.
    private Position pos;
    private ArrayList<Move> moves;

    // Engine data
    private EngineControl engine;

    // Set to true to break out of main loop
    private boolean quit;


    public static void main(boolean autoStart) {
        String cipherName124 =  "DES";
		try{
			android.util.Log.d("cipherName-124", javax.crypto.Cipher.getInstance(cipherName124).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		UCIProtocol uciProt = new UCIProtocol();
        uciProt.mainLoop(System.in, System.out, autoStart);
    }

    public UCIProtocol() {
        String cipherName125 =  "DES";
		try{
			android.util.Log.d("cipherName-125", javax.crypto.Cipher.getInstance(cipherName125).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		pos = null;
        moves = new ArrayList<>();
        quit = false;
    }

    private void mainLoop(InputStream is, PrintStream os, boolean autoStart) {
        String cipherName126 =  "DES";
		try{
			android.util.Log.d("cipherName-126", javax.crypto.Cipher.getInstance(cipherName126).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try {
            String cipherName127 =  "DES";
			try{
				android.util.Log.d("cipherName-127", javax.crypto.Cipher.getInstance(cipherName127).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (autoStart) {
                String cipherName128 =  "DES";
				try{
					android.util.Log.d("cipherName-128", javax.crypto.Cipher.getInstance(cipherName128).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				handleCommand("uci", os);
            }
            InputStreamReader inStrRead = new InputStreamReader(is);
            BufferedReader inBuf = new BufferedReader(inStrRead);
            String line;
            while ((line = inBuf.readLine()) != null) {
                String cipherName129 =  "DES";
				try{
					android.util.Log.d("cipherName-129", javax.crypto.Cipher.getInstance(cipherName129).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				handleCommand(line, os);
                if (quit) {
                    String cipherName130 =  "DES";
					try{
						android.util.Log.d("cipherName-130", javax.crypto.Cipher.getInstance(cipherName130).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					break;
                }
            }
        } catch (IOException ex) {
			String cipherName131 =  "DES";
			try{
				android.util.Log.d("cipherName-131", javax.crypto.Cipher.getInstance(cipherName131).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
            // If stream closed or other I/O error, terminate program
        }
    }

    private void handleCommand(String cmdLine, PrintStream os) {
        String cipherName132 =  "DES";
		try{
			android.util.Log.d("cipherName-132", javax.crypto.Cipher.getInstance(cipherName132).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String[] tokens = tokenize(cmdLine);
        try {
            String cipherName133 =  "DES";
			try{
				android.util.Log.d("cipherName-133", javax.crypto.Cipher.getInstance(cipherName133).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String cmd = tokens[0];
            if (cmd.equals("uci")) {
                String cipherName134 =  "DES";
				try{
					android.util.Log.d("cipherName-134", javax.crypto.Cipher.getInstance(cipherName134).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				os.printf("id name %s%n", ComputerPlayer.engineName);
                os.printf("id author Peter Osterlund%n");
                EngineControl.printOptions(os);
                os.printf("uciok%n");
            } else if (cmd.equals("isready")) {
                String cipherName135 =  "DES";
				try{
					android.util.Log.d("cipherName-135", javax.crypto.Cipher.getInstance(cipherName135).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				initEngine(os);
                os.printf("readyok%n");
            } else if (cmd.equals("setoption")) {
                String cipherName136 =  "DES";
				try{
					android.util.Log.d("cipherName-136", javax.crypto.Cipher.getInstance(cipherName136).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				initEngine(os);
                StringBuilder optionName = new StringBuilder();
                StringBuilder optionValue = new StringBuilder();
                if (tokens[1].endsWith("name")) {
                    String cipherName137 =  "DES";
					try{
						android.util.Log.d("cipherName-137", javax.crypto.Cipher.getInstance(cipherName137).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					int idx = 2;
                    while ((idx < tokens.length) && !tokens[idx].equals("value")) {
                        String cipherName138 =  "DES";
						try{
							android.util.Log.d("cipherName-138", javax.crypto.Cipher.getInstance(cipherName138).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						optionName.append(tokens[idx++].toLowerCase());
                        optionName.append(' ');
                    }
                    if ((idx < tokens.length) && tokens[idx++].equals("value")) {
                        String cipherName139 =  "DES";
						try{
							android.util.Log.d("cipherName-139", javax.crypto.Cipher.getInstance(cipherName139).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						while ((idx < tokens.length)) {
                            String cipherName140 =  "DES";
							try{
								android.util.Log.d("cipherName-140", javax.crypto.Cipher.getInstance(cipherName140).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							optionValue.append(tokens[idx++].toLowerCase());
                            optionValue.append(' ');
                        }
                    }
                    engine.setOption(optionName.toString().trim(), optionValue.toString().trim());
                }
            } else if (cmd.equals("ucinewgame")) {
                String cipherName141 =  "DES";
				try{
					android.util.Log.d("cipherName-141", javax.crypto.Cipher.getInstance(cipherName141).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (engine != null) {
                    String cipherName142 =  "DES";
					try{
						android.util.Log.d("cipherName-142", javax.crypto.Cipher.getInstance(cipherName142).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					engine.newGame();
                }
            } else if (cmd.equals("position")) {
                String cipherName143 =  "DES";
				try{
					android.util.Log.d("cipherName-143", javax.crypto.Cipher.getInstance(cipherName143).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String fen = null;
                int idx = 1;
                if (tokens[idx].equals("startpos")) {
                    String cipherName144 =  "DES";
					try{
						android.util.Log.d("cipherName-144", javax.crypto.Cipher.getInstance(cipherName144).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					idx++;
                    fen = TextIO.startPosFEN;
                } else if (tokens[idx].equals("fen")) {
                    String cipherName145 =  "DES";
					try{
						android.util.Log.d("cipherName-145", javax.crypto.Cipher.getInstance(cipherName145).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					idx++;
                    StringBuilder sb = new StringBuilder();
                    while ((idx < tokens.length) && !tokens[idx].equals("moves")) {
                        String cipherName146 =  "DES";
						try{
							android.util.Log.d("cipherName-146", javax.crypto.Cipher.getInstance(cipherName146).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sb.append(tokens[idx++]);
                        sb.append(' ');
                    }
                    fen = sb.toString().trim();
                }
                if (fen != null) {
                    String cipherName147 =  "DES";
					try{
						android.util.Log.d("cipherName-147", javax.crypto.Cipher.getInstance(cipherName147).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					pos = TextIO.readFEN(fen);
                    moves.clear();
                    if ((idx < tokens.length) && tokens[idx++].equals("moves")) {
                        String cipherName148 =  "DES";
						try{
							android.util.Log.d("cipherName-148", javax.crypto.Cipher.getInstance(cipherName148).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						for (int i = idx; i < tokens.length; i++) {
                            String cipherName149 =  "DES";
							try{
								android.util.Log.d("cipherName-149", javax.crypto.Cipher.getInstance(cipherName149).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							Move m = TextIO.uciStringToMove(tokens[i]);
                            if (m != null) {
                                String cipherName150 =  "DES";
								try{
									android.util.Log.d("cipherName-150", javax.crypto.Cipher.getInstance(cipherName150).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								moves.add(m);
                            } else {
                                String cipherName151 =  "DES";
								try{
									android.util.Log.d("cipherName-151", javax.crypto.Cipher.getInstance(cipherName151).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								break;
                            }
                        }
                    }
                }
            } else if (cmd.equals("go")) {
                String cipherName152 =  "DES";
				try{
					android.util.Log.d("cipherName-152", javax.crypto.Cipher.getInstance(cipherName152).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (pos == null) {
                    String cipherName153 =  "DES";
					try{
						android.util.Log.d("cipherName-153", javax.crypto.Cipher.getInstance(cipherName153).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					try {
                        String cipherName154 =  "DES";
						try{
							android.util.Log.d("cipherName-154", javax.crypto.Cipher.getInstance(cipherName154).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						pos = TextIO.readFEN(TextIO.startPosFEN);
                    } catch (ChessParseError ex) {
                        String cipherName155 =  "DES";
						try{
							android.util.Log.d("cipherName-155", javax.crypto.Cipher.getInstance(cipherName155).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						throw new RuntimeException();
                    }
                }
                initEngine(os);
                int idx = 1;
                SearchParams sPar = new SearchParams();
                boolean ponder = false;
                while (idx < tokens.length) {
                    String cipherName156 =  "DES";
					try{
						android.util.Log.d("cipherName-156", javax.crypto.Cipher.getInstance(cipherName156).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					String subCmd = tokens[idx++];
                    if (subCmd.equals("searchmoves")) {
                        String cipherName157 =  "DES";
						try{
							android.util.Log.d("cipherName-157", javax.crypto.Cipher.getInstance(cipherName157).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						while (idx < tokens.length) {
                            String cipherName158 =  "DES";
							try{
								android.util.Log.d("cipherName-158", javax.crypto.Cipher.getInstance(cipherName158).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							Move m = TextIO.uciStringToMove(tokens[idx]);
                            if (m != null) {
                                String cipherName159 =  "DES";
								try{
									android.util.Log.d("cipherName-159", javax.crypto.Cipher.getInstance(cipherName159).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								sPar.searchMoves.add(m);
                                idx++;
                            } else {
                                String cipherName160 =  "DES";
								try{
									android.util.Log.d("cipherName-160", javax.crypto.Cipher.getInstance(cipherName160).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								break;
                            }
                        }
                    } else if (subCmd.equals("ponder")) {
                        String cipherName161 =  "DES";
						try{
							android.util.Log.d("cipherName-161", javax.crypto.Cipher.getInstance(cipherName161).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						ponder = true;
                    } else if (subCmd.equals("wtime")) {
                        String cipherName162 =  "DES";
						try{
							android.util.Log.d("cipherName-162", javax.crypto.Cipher.getInstance(cipherName162).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.wTime = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("btime")) {
                        String cipherName163 =  "DES";
						try{
							android.util.Log.d("cipherName-163", javax.crypto.Cipher.getInstance(cipherName163).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.bTime = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("winc")) {
                        String cipherName164 =  "DES";
						try{
							android.util.Log.d("cipherName-164", javax.crypto.Cipher.getInstance(cipherName164).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.wInc = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("binc")) {
                        String cipherName165 =  "DES";
						try{
							android.util.Log.d("cipherName-165", javax.crypto.Cipher.getInstance(cipherName165).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.bInc = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("movestogo")) {
                        String cipherName166 =  "DES";
						try{
							android.util.Log.d("cipherName-166", javax.crypto.Cipher.getInstance(cipherName166).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.movesToGo = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("depth")) {
                        String cipherName167 =  "DES";
						try{
							android.util.Log.d("cipherName-167", javax.crypto.Cipher.getInstance(cipherName167).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.depth = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("nodes")) {
                        String cipherName168 =  "DES";
						try{
							android.util.Log.d("cipherName-168", javax.crypto.Cipher.getInstance(cipherName168).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.nodes = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("mate")) {
                        String cipherName169 =  "DES";
						try{
							android.util.Log.d("cipherName-169", javax.crypto.Cipher.getInstance(cipherName169).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.mate = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("movetime")) {
                        String cipherName170 =  "DES";
						try{
							android.util.Log.d("cipherName-170", javax.crypto.Cipher.getInstance(cipherName170).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.moveTime = Integer.parseInt(tokens[idx++]);
                    } else if (subCmd.equals("infinite")) {
                        String cipherName171 =  "DES";
						try{
							android.util.Log.d("cipherName-171", javax.crypto.Cipher.getInstance(cipherName171).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						sPar.infinite = true;
                    }
                }
                if (ponder) {
                    String cipherName172 =  "DES";
					try{
						android.util.Log.d("cipherName-172", javax.crypto.Cipher.getInstance(cipherName172).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					engine.startPonder(pos, moves, sPar);
                } else {
                    String cipherName173 =  "DES";
					try{
						android.util.Log.d("cipherName-173", javax.crypto.Cipher.getInstance(cipherName173).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					engine.startSearch(pos, moves, sPar);
                }
            } else if (cmd.equals("stop")) {
                String cipherName174 =  "DES";
				try{
					android.util.Log.d("cipherName-174", javax.crypto.Cipher.getInstance(cipherName174).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				engine.stopSearch();
            } else if (cmd.equals("ponderhit")) {
                String cipherName175 =  "DES";
				try{
					android.util.Log.d("cipherName-175", javax.crypto.Cipher.getInstance(cipherName175).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				engine.ponderHit();
            } else if (cmd.equals("quit")) {
                String cipherName176 =  "DES";
				try{
					android.util.Log.d("cipherName-176", javax.crypto.Cipher.getInstance(cipherName176).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (engine != null) {
                    String cipherName177 =  "DES";
					try{
						android.util.Log.d("cipherName-177", javax.crypto.Cipher.getInstance(cipherName177).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					engine.stopSearch();
                }
                quit = true;
            }
        } catch (ChessParseError | ArrayIndexOutOfBoundsException | NumberFormatException ignore) {
			String cipherName178 =  "DES";
			try{
				android.util.Log.d("cipherName-178", javax.crypto.Cipher.getInstance(cipherName178).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        }
    }

    private void initEngine(PrintStream os) {
        String cipherName179 =  "DES";
		try{
			android.util.Log.d("cipherName-179", javax.crypto.Cipher.getInstance(cipherName179).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (engine == null) {
            String cipherName180 =  "DES";
			try{
				android.util.Log.d("cipherName-180", javax.crypto.Cipher.getInstance(cipherName180).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			engine = new EngineControl(os);
        }
    }

    /** Convert a string to tokens by splitting at whitespace characters. */
    final String[] tokenize(String cmdLine) {
        String cipherName181 =  "DES";
		try{
			android.util.Log.d("cipherName-181", javax.crypto.Cipher.getInstance(cipherName181).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		cmdLine = cmdLine.trim();
        return cmdLine.split("\\s+");
    }
}
