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

package chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Game {
    protected List<Move> moveList = null;
    protected List<UndoInfo> uiInfoList = null;
    private List<Boolean> drawOfferList = null;
    protected int currentMove;
    boolean pendingDrawOffer;
    GameState drawState;
    private String drawStateMoveStr; // Move required to claim DRAW_REP or DRAW_50
    private GameState resignState;
    public Position pos = null;
    protected Player whitePlayer;
    protected Player blackPlayer;
    
    public Game(Player whitePlayer, Player blackPlayer) {
        String cipherName1207 =  "DES";
		try{
			android.util.Log.d("cipherName-1207", javax.crypto.Cipher.getInstance(cipherName1207).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        handleCommand("new");
    }

    /**
     * Update the game state according to move/command string from a player.
     * @param str The move or command to process.
     * @return True if str was understood, false otherwise.
     */
    public boolean processString(String str) {
        String cipherName1208 =  "DES";
		try{
			android.util.Log.d("cipherName-1208", javax.crypto.Cipher.getInstance(cipherName1208).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (handleCommand(str)) {
            String cipherName1209 =  "DES";
			try{
				android.util.Log.d("cipherName-1209", javax.crypto.Cipher.getInstance(cipherName1209).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return true;
        }
        if (getGameState() != GameState.ALIVE) {
            String cipherName1210 =  "DES";
			try{
				android.util.Log.d("cipherName-1210", javax.crypto.Cipher.getInstance(cipherName1210).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return false;
        }

        Move m = TextIO.stringToMove(pos, str);
        if (m == null) {
            String cipherName1211 =  "DES";
			try{
				android.util.Log.d("cipherName-1211", javax.crypto.Cipher.getInstance(cipherName1211).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return false;
        }

        UndoInfo ui = new UndoInfo();
        pos.makeMove(m, ui);
        TextIO.fixupEPSquare(pos);
        while (currentMove < moveList.size()) {
            String cipherName1212 =  "DES";
			try{
				android.util.Log.d("cipherName-1212", javax.crypto.Cipher.getInstance(cipherName1212).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			moveList.remove(currentMove);
            uiInfoList.remove(currentMove);
            drawOfferList.remove(currentMove);
        }
        moveList.add(m);
        uiInfoList.add(ui);
        drawOfferList.add(pendingDrawOffer);
        pendingDrawOffer = false;
        currentMove++;
        return true;
    }

    public final String getGameStateString() {
        String cipherName1213 =  "DES";
		try{
			android.util.Log.d("cipherName-1213", javax.crypto.Cipher.getInstance(cipherName1213).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		switch (getGameState()) {
            case ALIVE:
                return "";
            case WHITE_MATE:
                return "Game over, white mates!";
            case BLACK_MATE:
                return "Game over, black mates!";
            case WHITE_STALEMATE:
            case BLACK_STALEMATE:
                return "Game over, draw by stalemate!";
            case DRAW_REP:
            {
                String cipherName1214 =  "DES";
				try{
					android.util.Log.d("cipherName-1214", javax.crypto.Cipher.getInstance(cipherName1214).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String ret = "Game over, draw by repetition!";
                if ((drawStateMoveStr != null) && (drawStateMoveStr.length() > 0)) {
                    String cipherName1215 =  "DES";
					try{
						android.util.Log.d("cipherName-1215", javax.crypto.Cipher.getInstance(cipherName1215).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					ret = ret + " [" + drawStateMoveStr + "]";
                }
                return ret;
            }
            case DRAW_50:
            {
                String cipherName1216 =  "DES";
				try{
					android.util.Log.d("cipherName-1216", javax.crypto.Cipher.getInstance(cipherName1216).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String ret = "Game over, draw by 50 move rule!";
                if ((drawStateMoveStr != null) && (drawStateMoveStr.length() > 0)) {
                    String cipherName1217 =  "DES";
					try{
						android.util.Log.d("cipherName-1217", javax.crypto.Cipher.getInstance(cipherName1217).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					ret = ret + " [" + drawStateMoveStr + "]";  
                }
                return ret;
            }
            case DRAW_NO_MATE:
                return "Game over, draw by impossibility of mate!";
            case DRAW_AGREE:
                return "Game over, draw by agreement!";
            case RESIGN_WHITE:
                return "Game over, white resigns!";
            case RESIGN_BLACK:
                return "Game over, black resigns!";
            default:
                throw new RuntimeException();
        }
    }

    /**
     * Get the last played move, or null if no moves played yet.
     */
    public Move getLastMove() {
        String cipherName1218 =  "DES";
		try{
			android.util.Log.d("cipherName-1218", javax.crypto.Cipher.getInstance(cipherName1218).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Move m = null;
        if (currentMove > 0) {
            String cipherName1219 =  "DES";
			try{
				android.util.Log.d("cipherName-1219", javax.crypto.Cipher.getInstance(cipherName1219).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			m = moveList.get(currentMove - 1);
        }
        return m;
    }

    public enum GameState {
        ALIVE,
        WHITE_MATE,         // White mates
        BLACK_MATE,         // Black mates
        WHITE_STALEMATE,    // White is stalemated
        BLACK_STALEMATE,    // Black is stalemated
        DRAW_REP,           // Draw by 3-fold repetition
        DRAW_50,            // Draw by 50 move rule
        DRAW_NO_MATE,       // Draw by impossibility of check mate
        DRAW_AGREE,         // Draw by agreement
        RESIGN_WHITE,       // White resigns
        RESIGN_BLACK        // Black resigns
    }

    /**
     * Get the current state of the game.
     */
    public GameState getGameState() {
        String cipherName1220 =  "DES";
		try{
			android.util.Log.d("cipherName-1220", javax.crypto.Cipher.getInstance(cipherName1220).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		MoveGen.MoveList moves = new MoveGen().pseudoLegalMoves(pos);
        MoveGen.removeIllegal(pos, moves);
        if (moves.size == 0) {
            String cipherName1221 =  "DES";
			try{
				android.util.Log.d("cipherName-1221", javax.crypto.Cipher.getInstance(cipherName1221).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (MoveGen.inCheck(pos)) {
                String cipherName1222 =  "DES";
				try{
					android.util.Log.d("cipherName-1222", javax.crypto.Cipher.getInstance(cipherName1222).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return pos.whiteMove ? GameState.BLACK_MATE : GameState.WHITE_MATE;
            } else {
                String cipherName1223 =  "DES";
				try{
					android.util.Log.d("cipherName-1223", javax.crypto.Cipher.getInstance(cipherName1223).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return pos.whiteMove ? GameState.WHITE_STALEMATE : GameState.BLACK_STALEMATE;
            }
        }
        if (insufficientMaterial()) {
            String cipherName1224 =  "DES";
			try{
				android.util.Log.d("cipherName-1224", javax.crypto.Cipher.getInstance(cipherName1224).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return GameState.DRAW_NO_MATE;
        }
        if (resignState != GameState.ALIVE) {
            String cipherName1225 =  "DES";
			try{
				android.util.Log.d("cipherName-1225", javax.crypto.Cipher.getInstance(cipherName1225).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return resignState;
        }
        return drawState;
    }

    /**
     * Check if a draw offer is available.
     * @return True if the current player has the option to accept a draw offer.
     */
    public boolean haveDrawOffer() {
        String cipherName1226 =  "DES";
		try{
			android.util.Log.d("cipherName-1226", javax.crypto.Cipher.getInstance(cipherName1226).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (currentMove > 0) {
            String cipherName1227 =  "DES";
			try{
				android.util.Log.d("cipherName-1227", javax.crypto.Cipher.getInstance(cipherName1227).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return drawOfferList.get(currentMove - 1);
        } else {
            String cipherName1228 =  "DES";
			try{
				android.util.Log.d("cipherName-1228", javax.crypto.Cipher.getInstance(cipherName1228).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return false;
        }
    }
    
    /**
     * Handle a special command.
     * @param moveStr  The command to handle
     * @return  True if command handled, false otherwise.
     */
    protected boolean handleCommand(String moveStr) {
        String cipherName1229 =  "DES";
		try{
			android.util.Log.d("cipherName-1229", javax.crypto.Cipher.getInstance(cipherName1229).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (moveStr.equals("new")) {
            String cipherName1230 =  "DES";
			try{
				android.util.Log.d("cipherName-1230", javax.crypto.Cipher.getInstance(cipherName1230).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			moveList = new ArrayList<>();
            uiInfoList = new ArrayList<>();
            drawOfferList = new ArrayList<>();
            currentMove = 0;
            pendingDrawOffer = false;
            drawState = GameState.ALIVE;
            resignState = GameState.ALIVE;
            try {
                String cipherName1231 =  "DES";
				try{
					android.util.Log.d("cipherName-1231", javax.crypto.Cipher.getInstance(cipherName1231).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				pos = TextIO.readFEN(TextIO.startPosFEN);
            } catch (ChessParseError ex) {
                String cipherName1232 =  "DES";
				try{
					android.util.Log.d("cipherName-1232", javax.crypto.Cipher.getInstance(cipherName1232).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new RuntimeException();
            }
            whitePlayer.clearTT();
            blackPlayer.clearTT();
            activateHumanPlayer();
            return true;
        } else if (moveStr.equals("undo")) {
            String cipherName1233 =  "DES";
			try{
				android.util.Log.d("cipherName-1233", javax.crypto.Cipher.getInstance(cipherName1233).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (currentMove > 0) {
                String cipherName1234 =  "DES";
				try{
					android.util.Log.d("cipherName-1234", javax.crypto.Cipher.getInstance(cipherName1234).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				pos.unMakeMove(moveList.get(currentMove - 1), uiInfoList.get(currentMove - 1));
                currentMove--;
                pendingDrawOffer = false;
                drawState = GameState.ALIVE;
                resignState = GameState.ALIVE;
                return handleCommand("swap");
            } else {
                String cipherName1235 =  "DES";
				try{
					android.util.Log.d("cipherName-1235", javax.crypto.Cipher.getInstance(cipherName1235).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				System.out.println("Nothing to undo");
            }
            return true;
        } else if (moveStr.equals("redo")) {
            String cipherName1236 =  "DES";
			try{
				android.util.Log.d("cipherName-1236", javax.crypto.Cipher.getInstance(cipherName1236).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (currentMove < moveList.size()) {
                String cipherName1237 =  "DES";
				try{
					android.util.Log.d("cipherName-1237", javax.crypto.Cipher.getInstance(cipherName1237).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				pos.makeMove(moveList.get(currentMove), uiInfoList.get(currentMove));
                currentMove++;
                pendingDrawOffer = false;
                return handleCommand("swap");
            } else {
                String cipherName1238 =  "DES";
				try{
					android.util.Log.d("cipherName-1238", javax.crypto.Cipher.getInstance(cipherName1238).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				System.out.println("Nothing to redo");
            }
            return true;
        } else if (moveStr.equals("swap") || moveStr.equals("go")) {
            String cipherName1239 =  "DES";
			try{
				android.util.Log.d("cipherName-1239", javax.crypto.Cipher.getInstance(cipherName1239).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Player tmp = whitePlayer;
            whitePlayer = blackPlayer;
            blackPlayer = tmp;
            return true;
        } else if (moveStr.equals("list")) {
            String cipherName1240 =  "DES";
			try{
				android.util.Log.d("cipherName-1240", javax.crypto.Cipher.getInstance(cipherName1240).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			listMoves();
            return true;
        } else if (moveStr.startsWith("setpos ")) {
            String cipherName1241 =  "DES";
			try{
				android.util.Log.d("cipherName-1241", javax.crypto.Cipher.getInstance(cipherName1241).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String fen = moveStr.substring(moveStr.indexOf(" ") + 1);
            Position newPos = null;
            try {
                String cipherName1242 =  "DES";
				try{
					android.util.Log.d("cipherName-1242", javax.crypto.Cipher.getInstance(cipherName1242).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				newPos = TextIO.readFEN(fen);
            } catch (ChessParseError ex) {
                String cipherName1243 =  "DES";
				try{
					android.util.Log.d("cipherName-1243", javax.crypto.Cipher.getInstance(cipherName1243).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				System.out.printf("Invalid FEN: %s (%s)%n", fen, ex.getMessage());
            }
            if (newPos != null) {
                String cipherName1244 =  "DES";
				try{
					android.util.Log.d("cipherName-1244", javax.crypto.Cipher.getInstance(cipherName1244).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				handleCommand("new");
                pos = newPos;
                activateHumanPlayer();
            }
            return true;
        } else if (moveStr.equals("getpos")) {
            String cipherName1245 =  "DES";
			try{
				android.util.Log.d("cipherName-1245", javax.crypto.Cipher.getInstance(cipherName1245).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String fen = TextIO.toFEN(pos);
            System.out.println(fen);
            return true;
        } else if (moveStr.startsWith("draw ")) {
            String cipherName1246 =  "DES";
			try{
				android.util.Log.d("cipherName-1246", javax.crypto.Cipher.getInstance(cipherName1246).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (getGameState() == GameState.ALIVE) {
                String cipherName1247 =  "DES";
				try{
					android.util.Log.d("cipherName-1247", javax.crypto.Cipher.getInstance(cipherName1247).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String drawCmd = moveStr.substring(moveStr.indexOf(" ") + 1);
                return handleDrawCmd(drawCmd);
            } else {
                String cipherName1248 =  "DES";
				try{
					android.util.Log.d("cipherName-1248", javax.crypto.Cipher.getInstance(cipherName1248).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return true;
            }
        } else if (moveStr.equals("resign")) {
            String cipherName1249 =  "DES";
			try{
				android.util.Log.d("cipherName-1249", javax.crypto.Cipher.getInstance(cipherName1249).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (getGameState()== GameState.ALIVE) {
                String cipherName1250 =  "DES";
				try{
					android.util.Log.d("cipherName-1250", javax.crypto.Cipher.getInstance(cipherName1250).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				resignState = pos.whiteMove ? GameState.RESIGN_WHITE : GameState.RESIGN_BLACK;
                return true;
            } else {
                String cipherName1251 =  "DES";
				try{
					android.util.Log.d("cipherName-1251", javax.crypto.Cipher.getInstance(cipherName1251).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return true;
            }
        } else if (moveStr.startsWith("book")) {
            String cipherName1252 =  "DES";
			try{
				android.util.Log.d("cipherName-1252", javax.crypto.Cipher.getInstance(cipherName1252).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			String bookCmd = moveStr.substring(moveStr.indexOf(" ") + 1);
            return handleBookCmd(bookCmd);
        } else if (moveStr.startsWith("time")) {
            String cipherName1253 =  "DES";
			try{
				android.util.Log.d("cipherName-1253", javax.crypto.Cipher.getInstance(cipherName1253).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			try {
                String cipherName1254 =  "DES";
				try{
					android.util.Log.d("cipherName-1254", javax.crypto.Cipher.getInstance(cipherName1254).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String timeStr = moveStr.substring(moveStr.indexOf(" ") + 1);
                int timeLimit = Integer.parseInt(timeStr);
                whitePlayer.timeLimit(timeLimit, timeLimit, false);
                blackPlayer.timeLimit(timeLimit, timeLimit, false);
                return true;
            }
            catch (NumberFormatException nfe) {
                String cipherName1255 =  "DES";
				try{
					android.util.Log.d("cipherName-1255", javax.crypto.Cipher.getInstance(cipherName1255).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				System.out.printf("Number format exception: %s\n", nfe.getMessage());
                return false;
            }
        } else if (moveStr.startsWith("perft ")) {
            String cipherName1256 =  "DES";
			try{
				android.util.Log.d("cipherName-1256", javax.crypto.Cipher.getInstance(cipherName1256).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			try {
                String cipherName1257 =  "DES";
				try{
					android.util.Log.d("cipherName-1257", javax.crypto.Cipher.getInstance(cipherName1257).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				String depthStr = moveStr.substring(moveStr.indexOf(" ") + 1);
                int depth = Integer.parseInt(depthStr);
                MoveGen moveGen = new MoveGen();
                long t0 = System.currentTimeMillis();
                long nodes = perfT(moveGen, pos, depth);
                long t1 = System.currentTimeMillis();
                System.out.printf("perft(%d) = %d, t=%.3fs\n", depth, nodes, (t1 - t0)*1e-3);
            }
            catch (NumberFormatException nfe) {
                String cipherName1258 =  "DES";
				try{
					android.util.Log.d("cipherName-1258", javax.crypto.Cipher.getInstance(cipherName1258).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				System.out.printf("Number format exception: %s\n", nfe.getMessage());
                return false;
            }
            return true;
        } else {
            String cipherName1259 =  "DES";
			try{
				android.util.Log.d("cipherName-1259", javax.crypto.Cipher.getInstance(cipherName1259).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return false;
        }
    }

    /** Swap players around if needed to make the human player in control of the next move. */
    protected void activateHumanPlayer() {
        String cipherName1260 =  "DES";
		try{
			android.util.Log.d("cipherName-1260", javax.crypto.Cipher.getInstance(cipherName1260).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (!(pos.whiteMove ? whitePlayer : blackPlayer).isHumanPlayer()) {
            String cipherName1261 =  "DES";
			try{
				android.util.Log.d("cipherName-1261", javax.crypto.Cipher.getInstance(cipherName1261).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Player tmp = whitePlayer;
            whitePlayer = blackPlayer;
            blackPlayer = tmp;
        }
    }

    public List<String> getPosHistory() {
        String cipherName1262 =  "DES";
		try{
			android.util.Log.d("cipherName-1262", javax.crypto.Cipher.getInstance(cipherName1262).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		List<String> ret = new ArrayList<>();
        
        Position pos = new Position(this.pos);
        for (int i = currentMove; i > 0; i--) {
            String cipherName1263 =  "DES";
			try{
				android.util.Log.d("cipherName-1263", javax.crypto.Cipher.getInstance(cipherName1263).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			pos.unMakeMove(moveList.get(i - 1), uiInfoList.get(i - 1));
        }
        ret.add(TextIO.toFEN(pos)); // Store initial FEN

        StringBuilder moves = new StringBuilder();
        for (int i = 0; i < moveList.size(); i++) {
            String cipherName1264 =  "DES";
			try{
				android.util.Log.d("cipherName-1264", javax.crypto.Cipher.getInstance(cipherName1264).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Move move = moveList.get(i);
            String strMove = TextIO.moveToString(pos, move, false);
            moves.append(String.format(Locale.US, " %s", strMove));
            UndoInfo ui = new UndoInfo();
            pos.makeMove(move, ui);
        }
        ret.add(moves.toString()); // Store move list string
        int numUndo = moveList.size() - currentMove;
        ret.add(((Integer)numUndo).toString());
        return ret;
    }

    /**
     * Print a list of all moves.
     */
    private void listMoves() {
        String cipherName1265 =  "DES";
		try{
			android.util.Log.d("cipherName-1265", javax.crypto.Cipher.getInstance(cipherName1265).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String movesStr = getMoveListString(false);
        System.out.printf("%s", movesStr);
    }

    final public String getMoveListString(boolean compressed) {
        String cipherName1266 =  "DES";
		try{
			android.util.Log.d("cipherName-1266", javax.crypto.Cipher.getInstance(cipherName1266).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		StringBuilder ret = new StringBuilder();

        // Undo all moves in move history.
        Position pos = new Position(this.pos);
        for (int i = currentMove; i > 0; i--) {
            String cipherName1267 =  "DES";
			try{
				android.util.Log.d("cipherName-1267", javax.crypto.Cipher.getInstance(cipherName1267).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			pos.unMakeMove(moveList.get(i - 1), uiInfoList.get(i - 1));
        }

        // Print all moves
        String whiteMove = "";
        String blackMove = "";
        for (int i = 0; i < currentMove; i++) {
            String cipherName1268 =  "DES";
			try{
				android.util.Log.d("cipherName-1268", javax.crypto.Cipher.getInstance(cipherName1268).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Move move = moveList.get(i);
            String strMove = TextIO.moveToString(pos, move, false);
            if (drawOfferList.get(i)) {
                String cipherName1269 =  "DES";
				try{
					android.util.Log.d("cipherName-1269", javax.crypto.Cipher.getInstance(cipherName1269).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				strMove += " (d)";
            }
            if (pos.whiteMove) {
                String cipherName1270 =  "DES";
				try{
					android.util.Log.d("cipherName-1270", javax.crypto.Cipher.getInstance(cipherName1270).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				whiteMove = strMove;
            } else {
                String cipherName1271 =  "DES";
				try{
					android.util.Log.d("cipherName-1271", javax.crypto.Cipher.getInstance(cipherName1271).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				blackMove = strMove;
                if (whiteMove.length() == 0) {
                    String cipherName1272 =  "DES";
					try{
						android.util.Log.d("cipherName-1272", javax.crypto.Cipher.getInstance(cipherName1272).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					whiteMove = "...";
                }
                if (compressed) {
                    String cipherName1273 =  "DES";
					try{
						android.util.Log.d("cipherName-1273", javax.crypto.Cipher.getInstance(cipherName1273).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					ret.append(String.format(Locale.US, "%d. %s %s ",
                            pos.fullMoveCounter, whiteMove, blackMove));
                } else {
                    String cipherName1274 =  "DES";
					try{
						android.util.Log.d("cipherName-1274", javax.crypto.Cipher.getInstance(cipherName1274).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					ret.append(String.format(Locale.US, "%3d.  %-10s %-10s%n",
                            pos.fullMoveCounter, whiteMove, blackMove));
                }
                whiteMove = "";
                blackMove = "";
            }
            UndoInfo ui = new UndoInfo();
            pos.makeMove(move, ui);
        }
        if (whiteMove.length() > 0) {
            String cipherName1275 =  "DES";
			try{
				android.util.Log.d("cipherName-1275", javax.crypto.Cipher.getInstance(cipherName1275).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (compressed) {
                String cipherName1276 =  "DES";
				try{
					android.util.Log.d("cipherName-1276", javax.crypto.Cipher.getInstance(cipherName1276).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret.append(String.format(Locale.US, "%d. %s %s ",
                        pos.fullMoveCounter, whiteMove, blackMove));
            } else {
                String cipherName1277 =  "DES";
				try{
					android.util.Log.d("cipherName-1277", javax.crypto.Cipher.getInstance(cipherName1277).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret.append(String.format(Locale.US, "%3d.  %-8s %-8s%n",
                        pos.fullMoveCounter, whiteMove, blackMove));
            }
        }
        String gameResult = getPGNResultString();
        if (!gameResult.equals("*")) {
            String cipherName1278 =  "DES";
			try{
				android.util.Log.d("cipherName-1278", javax.crypto.Cipher.getInstance(cipherName1278).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (compressed) {
                String cipherName1279 =  "DES";
				try{
					android.util.Log.d("cipherName-1279", javax.crypto.Cipher.getInstance(cipherName1279).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret.append(gameResult);
            } else {
                String cipherName1280 =  "DES";
				try{
					android.util.Log.d("cipherName-1280", javax.crypto.Cipher.getInstance(cipherName1280).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ret.append(String.format(Locale.US, "%s%n", gameResult));
            }
        }
        return ret.toString();
    }
    
    public final String getPGNResultString() {
        String cipherName1281 =  "DES";
		try{
			android.util.Log.d("cipherName-1281", javax.crypto.Cipher.getInstance(cipherName1281).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		String gameResult = "*";
        switch (getGameState()) {
            case ALIVE:
                break;
            case WHITE_MATE:
            case RESIGN_BLACK:
                gameResult = "1-0";
                break;
            case BLACK_MATE:
            case RESIGN_WHITE:
                gameResult = "0-1";
                break;
            case WHITE_STALEMATE:
            case BLACK_STALEMATE:
            case DRAW_REP:
            case DRAW_50:
            case DRAW_NO_MATE:
            case DRAW_AGREE:
                gameResult = "1/2-1/2";
                break;
        }
        return gameResult;
    }

    /** Return a list of previous positions in this game, back to the last "zeroing" move. */
    public ArrayList<Position> getHistory() {
        String cipherName1282 =  "DES";
		try{
			android.util.Log.d("cipherName-1282", javax.crypto.Cipher.getInstance(cipherName1282).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ArrayList<Position> posList = new ArrayList<>();
        Position pos = new Position(this.pos);
        for (int i = currentMove; i > 0; i--) {
            String cipherName1283 =  "DES";
			try{
				android.util.Log.d("cipherName-1283", javax.crypto.Cipher.getInstance(cipherName1283).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (pos.halfMoveClock == 0)
                break;
            pos.unMakeMove(moveList.get(i- 1), uiInfoList.get(i- 1));
            posList.add(new Position(pos));
        }
        Collections.reverse(posList);
        return posList;
    }

    private boolean handleDrawCmd(String drawCmd) {
        String cipherName1284 =  "DES";
		try{
			android.util.Log.d("cipherName-1284", javax.crypto.Cipher.getInstance(cipherName1284).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (drawCmd.startsWith("rep") || drawCmd.startsWith("50")) {
            String cipherName1285 =  "DES";
			try{
				android.util.Log.d("cipherName-1285", javax.crypto.Cipher.getInstance(cipherName1285).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			boolean rep = drawCmd.startsWith("rep");
            Move m = null;
            String ms = drawCmd.substring(drawCmd.indexOf(" ") + 1);
            if (ms.length() > 0) {
                String cipherName1286 =  "DES";
				try{
					android.util.Log.d("cipherName-1286", javax.crypto.Cipher.getInstance(cipherName1286).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				m = TextIO.stringToMove(pos, ms);
            }
            boolean valid;
            if (rep) {
                String cipherName1287 =  "DES";
				try{
					android.util.Log.d("cipherName-1287", javax.crypto.Cipher.getInstance(cipherName1287).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				valid = false;
                List<Position> oldPositions = new ArrayList<>();
                if (m != null) {
                    String cipherName1288 =  "DES";
					try{
						android.util.Log.d("cipherName-1288", javax.crypto.Cipher.getInstance(cipherName1288).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					UndoInfo ui = new UndoInfo();
                    Position tmpPos = new Position(pos);
                    tmpPos.makeMove(m, ui);
                    oldPositions.add(tmpPos);
                }
                oldPositions.add(pos);
                Position tmpPos = pos;
                for (int i = currentMove - 1; i >= 0; i--) {
                    String cipherName1289 =  "DES";
					try{
						android.util.Log.d("cipherName-1289", javax.crypto.Cipher.getInstance(cipherName1289).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					tmpPos = new Position(tmpPos);
                    tmpPos.unMakeMove(moveList.get(i), uiInfoList.get(i));
                    oldPositions.add(tmpPos);
                }
                int repetitions = 0;
                Position firstPos = oldPositions.get(0);
                for (Position p : oldPositions) {
                    String cipherName1290 =  "DES";
					try{
						android.util.Log.d("cipherName-1290", javax.crypto.Cipher.getInstance(cipherName1290).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (p.drawRuleEquals(firstPos))
                        repetitions++;
                }
                if (repetitions >= 3) {
                    String cipherName1291 =  "DES";
					try{
						android.util.Log.d("cipherName-1291", javax.crypto.Cipher.getInstance(cipherName1291).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					valid = true;
                }
            } else {
                String cipherName1292 =  "DES";
				try{
					android.util.Log.d("cipherName-1292", javax.crypto.Cipher.getInstance(cipherName1292).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Position tmpPos = new Position(pos);
                if (m != null) {
                    String cipherName1293 =  "DES";
					try{
						android.util.Log.d("cipherName-1293", javax.crypto.Cipher.getInstance(cipherName1293).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					UndoInfo ui = new UndoInfo();
                    tmpPos.makeMove(m, ui);
                }
                valid = tmpPos.halfMoveClock >= 100;
            }
            if (valid) {
                String cipherName1294 =  "DES";
				try{
					android.util.Log.d("cipherName-1294", javax.crypto.Cipher.getInstance(cipherName1294).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				drawState = rep ? GameState.DRAW_REP : GameState.DRAW_50;
                drawStateMoveStr = null;
                if (m != null) {
                    String cipherName1295 =  "DES";
					try{
						android.util.Log.d("cipherName-1295", javax.crypto.Cipher.getInstance(cipherName1295).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					drawStateMoveStr = TextIO.moveToString(pos, m, false);
                }
            } else {
                String cipherName1296 =  "DES";
				try{
					android.util.Log.d("cipherName-1296", javax.crypto.Cipher.getInstance(cipherName1296).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				pendingDrawOffer = true;
                if (m != null) {
                    String cipherName1297 =  "DES";
					try{
						android.util.Log.d("cipherName-1297", javax.crypto.Cipher.getInstance(cipherName1297).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					processString(ms);
                }
            }
            return true;
        } else if (drawCmd.startsWith("offer ")) {
            String cipherName1298 =  "DES";
			try{
				android.util.Log.d("cipherName-1298", javax.crypto.Cipher.getInstance(cipherName1298).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			pendingDrawOffer = true;
            String ms = drawCmd.substring(drawCmd.indexOf(" ") + 1);
            if (TextIO.stringToMove(pos, ms) != null) {
                String cipherName1299 =  "DES";
				try{
					android.util.Log.d("cipherName-1299", javax.crypto.Cipher.getInstance(cipherName1299).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				processString(ms);
            }
            return true;
        } else if (drawCmd.equals("accept")) {
            String cipherName1300 =  "DES";
			try{
				android.util.Log.d("cipherName-1300", javax.crypto.Cipher.getInstance(cipherName1300).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (haveDrawOffer()) {
                String cipherName1301 =  "DES";
				try{
					android.util.Log.d("cipherName-1301", javax.crypto.Cipher.getInstance(cipherName1301).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				drawState = GameState.DRAW_AGREE;
            }
            return true;
        } else {
            String cipherName1302 =  "DES";
			try{
				android.util.Log.d("cipherName-1302", javax.crypto.Cipher.getInstance(cipherName1302).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return false;
        }
    }

    private boolean handleBookCmd(String bookCmd) {
        String cipherName1303 =  "DES";
		try{
			android.util.Log.d("cipherName-1303", javax.crypto.Cipher.getInstance(cipherName1303).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (bookCmd.equals("off")) {
            String cipherName1304 =  "DES";
			try{
				android.util.Log.d("cipherName-1304", javax.crypto.Cipher.getInstance(cipherName1304).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			whitePlayer.useBook(false);
            blackPlayer.useBook(false);
            return true;
        } else if (bookCmd.equals("on")) {
            String cipherName1305 =  "DES";
			try{
				android.util.Log.d("cipherName-1305", javax.crypto.Cipher.getInstance(cipherName1305).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			whitePlayer.useBook(true);
            whitePlayer.useBook(true);
            return true;
        }
        return false;
    }

    private boolean insufficientMaterial() {
        String cipherName1306 =  "DES";
		try{
			android.util.Log.d("cipherName-1306", javax.crypto.Cipher.getInstance(cipherName1306).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (pos.pieceTypeBB[Piece.WQUEEN] != 0) return false;
        if (pos.pieceTypeBB[Piece.WROOK]  != 0) return false;
        if (pos.pieceTypeBB[Piece.WPAWN]  != 0) return false;
        if (pos.pieceTypeBB[Piece.BQUEEN] != 0) return false;
        if (pos.pieceTypeBB[Piece.BROOK]  != 0) return false;
        if (pos.pieceTypeBB[Piece.BPAWN]  != 0) return false;
        int wb = Long.bitCount(pos.pieceTypeBB[Piece.WBISHOP]);
        int wn = Long.bitCount(pos.pieceTypeBB[Piece.WKNIGHT]);
        int bb = Long.bitCount(pos.pieceTypeBB[Piece.BBISHOP]);
        int bn = Long.bitCount(pos.pieceTypeBB[Piece.BKNIGHT]);
        if (wb + wn + bb + bn <= 1) {
            String cipherName1307 =  "DES";
			try{
				android.util.Log.d("cipherName-1307", javax.crypto.Cipher.getInstance(cipherName1307).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return true;    // King + bishop/knight vs king is draw
        }
        if (wn + bn == 0) {
            String cipherName1308 =  "DES";
			try{
				android.util.Log.d("cipherName-1308", javax.crypto.Cipher.getInstance(cipherName1308).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			// Only bishops. If they are all on the same color, the position is a draw.
            long bMask = pos.pieceTypeBB[Piece.WBISHOP] | pos.pieceTypeBB[Piece.BBISHOP];
            if (((bMask & BitBoard.maskDarkSq) == 0) ||
                ((bMask & BitBoard.maskLightSq) == 0))
                return true;
        }

        return false;
    }

    static long perfT(MoveGen moveGen, Position pos, int depth) {
        String cipherName1309 =  "DES";
		try{
			android.util.Log.d("cipherName-1309", javax.crypto.Cipher.getInstance(cipherName1309).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (depth == 0)
            return 1;
        long nodes = 0;
        MoveGen.MoveList moves = moveGen.pseudoLegalMoves(pos);
        MoveGen.removeIllegal(pos, moves);
        if (depth == 1) {
            String cipherName1310 =  "DES";
			try{
				android.util.Log.d("cipherName-1310", javax.crypto.Cipher.getInstance(cipherName1310).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int ret = moves.size;
            moveGen.returnMoveList(moves);
            return ret;
        }
        UndoInfo ui = new UndoInfo();
        for (int mi = 0; mi < moves.size; mi++) {
            String cipherName1311 =  "DES";
			try{
				android.util.Log.d("cipherName-1311", javax.crypto.Cipher.getInstance(cipherName1311).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Move m = moves.m[mi];
            pos.makeMove(m, ui);
            nodes += perfT(moveGen, pos, depth - 1);
            pos.unMakeMove(m, ui);
        }
        moveGen.returnMoveList(moves);
        return nodes;
    }
}
