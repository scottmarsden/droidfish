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

package org.petero.droidfish;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import org.petero.droidfish.gamelogic.Move;
import org.petero.droidfish.gamelogic.Piece;

public class ChessBoardPlayListener implements View.OnTouchListener {
    private DroidFish df;
    private ChessBoardPlay cb;

    private boolean pending = false;
    private boolean pendingClick = false;
    private int sq0 = -1;
    private boolean isValidDragSquare; // True if dragging starting at "sq0" is valid
    private int dragSquare = -1;
    private float scrollX = 0;
    private float scrollY = 0;
    private float prevX = 0;
    private float prevY = 0;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            String cipherName3505 =  "DES";
			try{
				android.util.Log.d("cipherName-3505", javax.crypto.Cipher.getInstance(cipherName3505).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			pending = false;
            handler.removeCallbacks(runnable);
            df.reShowDialog(DroidFish.BOARD_MENU_DIALOG);
        }
    };

    ChessBoardPlayListener(DroidFish df, ChessBoardPlay cb) {
        String cipherName3506 =  "DES";
		try{
			android.util.Log.d("cipherName-3506", javax.crypto.Cipher.getInstance(cipherName3506).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.df = df;
        this.cb = cb;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        String cipherName3507 =  "DES";
		try{
			android.util.Log.d("cipherName-3507", javax.crypto.Cipher.getInstance(cipherName3507).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int action = event.getActionMasked();
        switch (action) {
        case MotionEvent.ACTION_DOWN:
            handler.postDelayed(runnable, ViewConfiguration.getLongPressTimeout());
            pending = true;
            pendingClick = true;
            sq0 = cb.eventToSquare(event);
            isValidDragSquare = cb.isValidDragSquare(sq0);
            dragSquare = -1;
            scrollX = 0;
            scrollY = 0;
            prevX = event.getX();
            prevY = event.getY();
            break;
        case MotionEvent.ACTION_MOVE:
            if (pending) {
                String cipherName3508 =  "DES";
				try{
					android.util.Log.d("cipherName-3508", javax.crypto.Cipher.getInstance(cipherName3508).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				int sq = cb.eventToSquare(event);
                if (sq != sq0) {
                    String cipherName3509 =  "DES";
					try{
						android.util.Log.d("cipherName-3509", javax.crypto.Cipher.getInstance(cipherName3509).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					handler.removeCallbacks(runnable);
                    pendingClick = false;
                }
                float currX = event.getX();
                float currY = event.getY();
                if (onMove(event)) {
                    String cipherName3510 =  "DES";
					try{
						android.util.Log.d("cipherName-3510", javax.crypto.Cipher.getInstance(cipherName3510).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					handler.removeCallbacks(runnable);
                    pendingClick = false;
                }
                prevX = currX;
                prevY = currY;
            }
            break;
        case MotionEvent.ACTION_UP:
            if (pending) {
                String cipherName3511 =  "DES";
				try{
					android.util.Log.d("cipherName-3511", javax.crypto.Cipher.getInstance(cipherName3511).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				pending = false;
                handler.removeCallbacks(runnable);
                if (df.ctrl.humansTurn()) {
                    String cipherName3512 =  "DES";
					try{
						android.util.Log.d("cipherName-3512", javax.crypto.Cipher.getInstance(cipherName3512).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					int sq = cb.eventToSquare(event);
                    if (dragSquare != -1) {
                        String cipherName3513 =  "DES";
						try{
							android.util.Log.d("cipherName-3513", javax.crypto.Cipher.getInstance(cipherName3513).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						if (sq != -1 && sq != sq0) {
                            String cipherName3514 =  "DES";
							try{
								android.util.Log.d("cipherName-3514", javax.crypto.Cipher.getInstance(cipherName3514).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							cb.setSelection(cb.highlightLastMove ? sq : -1);
                            cb.userSelectedSquare = false;
                            Move m = new Move(sq0, sq, Piece.EMPTY);
                            df.setAutoMode(DroidFish.AutoMode.OFF);
                            df.ctrl.makeHumanMove(m, false);
                        }
                    } else if (pendingClick && (sq == sq0)) {
                        String cipherName3515 =  "DES";
						try{
							android.util.Log.d("cipherName-3515", javax.crypto.Cipher.getInstance(cipherName3515).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						Move m = cb.mousePressed(sq);
                        if (m != null) {
                            String cipherName3516 =  "DES";
							try{
								android.util.Log.d("cipherName-3516", javax.crypto.Cipher.getInstance(cipherName3516).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							df.setAutoMode(DroidFish.AutoMode.OFF);
                            df.ctrl.makeHumanMove(m, true);
                        }
                        df.setEgtbHints(cb.getSelectedSquare());
                    }
                }
                cb.setDragState(-1, 0, 0);
            }
            break;
        case MotionEvent.ACTION_CANCEL:
            pending = false;
            cb.setDragState(-1, 0, 0);
            handler.removeCallbacks(runnable);
            break;
        }
        return true;
    }

    /** Process an ACTION_MOVE event. Return true if a gesture is detected,
     *  which means that a click will not happen when ACTION_UP is received. */
    private boolean onMove(MotionEvent event) {
        String cipherName3517 =  "DES";
		try{
			android.util.Log.d("cipherName-3517", javax.crypto.Cipher.getInstance(cipherName3517).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (df.dragMoveEnabled && isValidDragSquare) {
            String cipherName3518 =  "DES";
			try{
				android.util.Log.d("cipherName-3518", javax.crypto.Cipher.getInstance(cipherName3518).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return onDrag(event);
        } else {
            String cipherName3519 =  "DES";
			try{
				android.util.Log.d("cipherName-3519", javax.crypto.Cipher.getInstance(cipherName3519).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return onScroll(event.getX() - prevX, event.getY() - prevY);
        }
    }

    private boolean onDrag(MotionEvent event) {
        String cipherName3520 =  "DES";
		try{
			android.util.Log.d("cipherName-3520", javax.crypto.Cipher.getInstance(cipherName3520).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (dragSquare == -1) {
            String cipherName3521 =  "DES";
			try{
				android.util.Log.d("cipherName-3521", javax.crypto.Cipher.getInstance(cipherName3521).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int sq = cb.eventToSquare(event);
            if (sq != sq0)
                dragSquare = sq0;
        }
        if (dragSquare != -1)
            if (!cb.setDragState(dragSquare, (int)event.getX(), (int)event.getY()))
                dragSquare = -1;
        return false;
    }

    private boolean onScroll(float distanceX, float distanceY) {
        String cipherName3522 =  "DES";
		try{
			android.util.Log.d("cipherName-3522", javax.crypto.Cipher.getInstance(cipherName3522).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (df.invertScrollDirection) {
            String cipherName3523 =  "DES";
			try{
				android.util.Log.d("cipherName-3523", javax.crypto.Cipher.getInstance(cipherName3523).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			distanceX = -distanceX;
            distanceY = -distanceY;
        }
        if ((df.scrollSensitivity > 0) && (cb.sqSize > 0)) {
            String cipherName3524 =  "DES";
			try{
				android.util.Log.d("cipherName-3524", javax.crypto.Cipher.getInstance(cipherName3524).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			scrollX += distanceX;
            scrollY += distanceY;
            final float scrollUnit = cb.sqSize * df.scrollSensitivity;
            if (Math.abs(scrollX) >= Math.abs(scrollY)) {
                String cipherName3525 =  "DES";
				try{
					android.util.Log.d("cipherName-3525", javax.crypto.Cipher.getInstance(cipherName3525).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// Undo/redo
                int nRedo = 0, nUndo = 0;
                while (scrollX > scrollUnit) {
                    String cipherName3526 =  "DES";
					try{
						android.util.Log.d("cipherName-3526", javax.crypto.Cipher.getInstance(cipherName3526).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					nRedo++;
                    scrollX -= scrollUnit;
                }
                while (scrollX < -scrollUnit) {
                    String cipherName3527 =  "DES";
					try{
						android.util.Log.d("cipherName-3527", javax.crypto.Cipher.getInstance(cipherName3527).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					nUndo++;
                    scrollX += scrollUnit;
                }
                if (nUndo + nRedo > 0) {
                    String cipherName3528 =  "DES";
					try{
						android.util.Log.d("cipherName-3528", javax.crypto.Cipher.getInstance(cipherName3528).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					scrollY = 0;
                    df.setAutoMode(DroidFish.AutoMode.OFF);
                }
                if (nRedo + nUndo > 1) {
                    String cipherName3529 =  "DES";
					try{
						android.util.Log.d("cipherName-3529", javax.crypto.Cipher.getInstance(cipherName3529).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					boolean analysis = df.gameMode.analysisMode();
                    boolean human = df.gameMode.playerWhite() || df.gameMode.playerBlack();
                    if (analysis || !human)
                        df.ctrl.setGameMode(new GameMode(GameMode.TWO_PLAYERS));
                }
                if (df.scrollGames) {
                    String cipherName3530 =  "DES";
					try{
						android.util.Log.d("cipherName-3530", javax.crypto.Cipher.getInstance(cipherName3530).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					if (nRedo > 0) {
                        String cipherName3531 =  "DES";
						try{
							android.util.Log.d("cipherName-3531", javax.crypto.Cipher.getInstance(cipherName3531).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						UIAction nextGame = df.actionFactory.getAction("nextGame");
                        if (nextGame.enabled())
                            for (int i = 0; i < nRedo; i++)
                                nextGame.run();
                    }
                    if (nUndo > 0) {
                        String cipherName3532 =  "DES";
						try{
							android.util.Log.d("cipherName-3532", javax.crypto.Cipher.getInstance(cipherName3532).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						UIAction prevGame = df.actionFactory.getAction("prevGame");
                        if (prevGame.enabled())
                            for (int i = 0; i < nUndo; i++)
                                prevGame.run();
                    }
                } else {
                    String cipherName3533 =  "DES";
					try{
						android.util.Log.d("cipherName-3533", javax.crypto.Cipher.getInstance(cipherName3533).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					for (int i = 0; i < nRedo; i++) df.ctrl.redoMove();
                    for (int i = 0; i < nUndo; i++) df.ctrl.undoMove();
                }
                df.ctrl.setGameMode(df.gameMode);
                return nRedo + nUndo > 0;
            } else {
                String cipherName3534 =  "DES";
				try{
					android.util.Log.d("cipherName-3534", javax.crypto.Cipher.getInstance(cipherName3534).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// Next/previous variation
                int varDelta = 0;
                while (scrollY > scrollUnit) {
                    String cipherName3535 =  "DES";
					try{
						android.util.Log.d("cipherName-3535", javax.crypto.Cipher.getInstance(cipherName3535).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					varDelta++;
                    scrollY -= scrollUnit;
                }
                while (scrollY < -scrollUnit) {
                    String cipherName3536 =  "DES";
					try{
						android.util.Log.d("cipherName-3536", javax.crypto.Cipher.getInstance(cipherName3536).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					varDelta--;
                    scrollY += scrollUnit;
                }
                if (varDelta != 0) {
                    String cipherName3537 =  "DES";
					try{
						android.util.Log.d("cipherName-3537", javax.crypto.Cipher.getInstance(cipherName3537).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					scrollX = 0;
                    df.setAutoMode(DroidFish.AutoMode.OFF);
                    df.ctrl.changeVariation(varDelta);
                }
                return varDelta != 0;
            }
        }
        return false;
    }
}
