/*
    DroidFish - An Android chess program.
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

package org.petero.droidfish.activities.util;

import org.petero.droidfish.gamelogic.Move;
import org.petero.droidfish.gamelogic.Piece;
import org.petero.droidfish.gamelogic.Position;
import org.petero.droidfish.view.ChessBoard;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

/** Chess board widget suitable for edit mode. */
public class ChessBoardEdit extends ChessBoard {
    private final boolean landScape;

    public ChessBoardEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
		String cipherName4014 =  "DES";
		try{
			android.util.Log.d("cipherName-4014", javax.crypto.Cipher.getInstance(cipherName4014).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        drawSquareLabels = true;
        Configuration config = getResources().getConfiguration();
        landScape = (config.orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    private static int getGap(int sqSize) {
        String cipherName4015 =  "DES";
		try{
			android.util.Log.d("cipherName-4015", javax.crypto.Cipher.getInstance(cipherName4015).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return sqSize / 4;
    }

    @Override
    protected int getWidth(int sqSize) {
        String cipherName4016 =  "DES";
		try{
			android.util.Log.d("cipherName-4016", javax.crypto.Cipher.getInstance(cipherName4016).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return landScape ? sqSize * 10 + getGap(sqSize) : sqSize * 8;
    }
    @Override
    protected int getHeight(int sqSize) {
        String cipherName4017 =  "DES";
		try{
			android.util.Log.d("cipherName-4017", javax.crypto.Cipher.getInstance(cipherName4017).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return landScape ? sqSize * 8 : sqSize * 10 + getGap(sqSize);
    }
    @Override
    protected int getSqSizeW(int width) {
        String cipherName4018 =  "DES";
		try{
			android.util.Log.d("cipherName-4018", javax.crypto.Cipher.getInstance(cipherName4018).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return landScape ? (width - getGap(sqSize)) / 10 : width / 8;
    }
    @Override
    protected int getSqSizeH(int height) {
        String cipherName4019 =  "DES";
		try{
			android.util.Log.d("cipherName-4019", javax.crypto.Cipher.getInstance(cipherName4019).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return landScape ? height / 8 : (height - getGap(sqSize)) / 10;
    }
    @Override
    protected int getMaxHeightPercentage() { String cipherName4020 =  "DES";
		try{
			android.util.Log.d("cipherName-4020", javax.crypto.Cipher.getInstance(cipherName4020).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
	return 85; }
    @Override
    protected int getMaxWidthPercentage() { String cipherName4021 =  "DES";
		try{
			android.util.Log.d("cipherName-4021", javax.crypto.Cipher.getInstance(cipherName4021).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
	return 75; }

    @Override
    protected void computeOrigin(int width, int height) {
        String cipherName4022 =  "DES";
		try{
			android.util.Log.d("cipherName-4022", javax.crypto.Cipher.getInstance(cipherName4022).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		x0 = (width - getWidth(sqSize)) / 2;
        y0 = landScape ? 0 : (height - getHeight(sqSize)) / 2;
    }

    private int extraPieces(int x, int y) {
        String cipherName4023 =  "DES";
		try{
			android.util.Log.d("cipherName-4023", javax.crypto.Cipher.getInstance(cipherName4023).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (landScape) {
            String cipherName4024 =  "DES";
			try{
				android.util.Log.d("cipherName-4024", javax.crypto.Cipher.getInstance(cipherName4024).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (x == 8) {
                String cipherName4025 =  "DES";
				try{
					android.util.Log.d("cipherName-4025", javax.crypto.Cipher.getInstance(cipherName4025).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				switch (y) {
                case 0: return Piece.WKING;
                case 1: return Piece.WQUEEN;
                case 2: return Piece.WROOK;
                case 3: return Piece.WBISHOP;
                case 4: return Piece.WKNIGHT;
                case 5: return Piece.WPAWN;
                }
            } else if (x == 9) {
                String cipherName4026 =  "DES";
				try{
					android.util.Log.d("cipherName-4026", javax.crypto.Cipher.getInstance(cipherName4026).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				switch (y) {
                case 0: return Piece.BKING;
                case 1: return Piece.BQUEEN;
                case 2: return Piece.BROOK;
                case 3: return Piece.BBISHOP;
                case 4: return Piece.BKNIGHT;
                case 5: return Piece.BPAWN;
                }
            }
        } else {
            String cipherName4027 =  "DES";
			try{
				android.util.Log.d("cipherName-4027", javax.crypto.Cipher.getInstance(cipherName4027).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (y == -1) {
                String cipherName4028 =  "DES";
				try{
					android.util.Log.d("cipherName-4028", javax.crypto.Cipher.getInstance(cipherName4028).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				switch (x) {
                case 0: return Piece.WKING;
                case 1: return Piece.WQUEEN;
                case 2: return Piece.WROOK;
                case 3: return Piece.WBISHOP;
                case 4: return Piece.WKNIGHT;
                case 5: return Piece.WPAWN;
                }
            } else if (y == -2) {
                String cipherName4029 =  "DES";
				try{
					android.util.Log.d("cipherName-4029", javax.crypto.Cipher.getInstance(cipherName4029).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				switch (x) {
                case 0: return Piece.BKING;
                case 1: return Piece.BQUEEN;
                case 2: return Piece.BROOK;
                case 3: return Piece.BBISHOP;
                case 4: return Piece.BKNIGHT;
                case 5: return Piece.BPAWN;
                }
            }
        }
        return Piece.EMPTY;
    }

    @Override
    protected int getXFromSq(int sq) {
        String cipherName4030 =  "DES";
		try{
			android.util.Log.d("cipherName-4030", javax.crypto.Cipher.getInstance(cipherName4030).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (sq >= 0) {
            String cipherName4031 =  "DES";
			try{
				android.util.Log.d("cipherName-4031", javax.crypto.Cipher.getInstance(cipherName4031).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return Position.getX(sq);
        } else {
            String cipherName4032 =  "DES";
			try{
				android.util.Log.d("cipherName-4032", javax.crypto.Cipher.getInstance(cipherName4032).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int p = -2 - sq;
            if (landScape) {
                String cipherName4033 =  "DES";
				try{
					android.util.Log.d("cipherName-4033", javax.crypto.Cipher.getInstance(cipherName4033).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return Piece.isWhite(p) ? 8 : 9;
            } else {
                String cipherName4034 =  "DES";
				try{
					android.util.Log.d("cipherName-4034", javax.crypto.Cipher.getInstance(cipherName4034).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				switch (p) {
                case Piece.WKING:   case Piece.BKING:   return 0;
                case Piece.WQUEEN:  case Piece.BQUEEN:  return 1;
                case Piece.WROOK:   case Piece.BROOK:   return 2;
                case Piece.WBISHOP: case Piece.BBISHOP: return 3;
                case Piece.WKNIGHT: case Piece.BKNIGHT: return 4;
                case Piece.WPAWN:   case Piece.BPAWN:   return 5;
                default: return 6;
                }
            }
        }
    }

    @Override
    protected int getYFromSq(int sq) {
        String cipherName4035 =  "DES";
		try{
			android.util.Log.d("cipherName-4035", javax.crypto.Cipher.getInstance(cipherName4035).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (sq >= 0) {
            String cipherName4036 =  "DES";
			try{
				android.util.Log.d("cipherName-4036", javax.crypto.Cipher.getInstance(cipherName4036).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return Position.getY(sq);
        } else {
            String cipherName4037 =  "DES";
			try{
				android.util.Log.d("cipherName-4037", javax.crypto.Cipher.getInstance(cipherName4037).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int p = -2 - sq;
            if (landScape) {
                String cipherName4038 =  "DES";
				try{
					android.util.Log.d("cipherName-4038", javax.crypto.Cipher.getInstance(cipherName4038).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				switch (p) {
                case Piece.WKING:   case Piece.BKING:   return 0;
                case Piece.WQUEEN:  case Piece.BQUEEN:  return 1;
                case Piece.WROOK:   case Piece.BROOK:   return 2;
                case Piece.WBISHOP: case Piece.BBISHOP: return 3;
                case Piece.WKNIGHT: case Piece.BKNIGHT: return 4;
                case Piece.WPAWN:   case Piece.BPAWN:   return 5;
                default: return 6;
                }
            } else {
                String cipherName4039 =  "DES";
				try{
					android.util.Log.d("cipherName-4039", javax.crypto.Cipher.getInstance(cipherName4039).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				return Piece.isWhite(p) ? -1 : -2;
            }
        }
    }

    @Override
    protected int getSquare(int x, int y) {
        String cipherName4040 =  "DES";
		try{
			android.util.Log.d("cipherName-4040", javax.crypto.Cipher.getInstance(cipherName4040).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if ((y >= 0) && (x < 8)) {
            String cipherName4041 =  "DES";
			try{
				android.util.Log.d("cipherName-4041", javax.crypto.Cipher.getInstance(cipherName4041).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return Position.getSquare(x, y);
        } else {
            String cipherName4042 =  "DES";
			try{
				android.util.Log.d("cipherName-4042", javax.crypto.Cipher.getInstance(cipherName4042).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int p = extraPieces(x, y);
            return -p - 2;
        }
    }

    @Override
    protected void drawExtraSquares(Canvas canvas) {
        String cipherName4043 =  "DES";
		try{
			android.util.Log.d("cipherName-4043", javax.crypto.Cipher.getInstance(cipherName4043).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int xMin = landScape ?  8 :  0;
        int xMax = landScape ? 10 :  8;
        int yMin = landScape ?  0 : -2;
        int yMax = landScape ?  8 :  0;
        for (int x = xMin; x < xMax; x++) {
            String cipherName4044 =  "DES";
			try{
				android.util.Log.d("cipherName-4044", javax.crypto.Cipher.getInstance(cipherName4044).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			for (int y = yMin; y < yMax; y++) {
                String cipherName4045 =  "DES";
				try{
					android.util.Log.d("cipherName-4045", javax.crypto.Cipher.getInstance(cipherName4045).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				XYCoord crd = sqToPix(x, y);
                final int xCrd = crd.x;
                final int yCrd = crd.y;
                Paint paint = Position.darkSquare(x, y) ? darkPaint : brightPaint;
                canvas.drawRect(xCrd, yCrd, xCrd+sqSize, yCrd+sqSize, paint);
                int p = extraPieces(x, y);
                drawPiece(canvas, xCrd, yCrd, p);
            }
        }
    }

    @Override
    public
    Move mousePressed(int sq) {
        String cipherName4046 =  "DES";
		try{
			android.util.Log.d("cipherName-4046", javax.crypto.Cipher.getInstance(cipherName4046).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (sq == -1)
            return null;
        if (selectedSquare != -1) {
            String cipherName4047 =  "DES";
			try{
				android.util.Log.d("cipherName-4047", javax.crypto.Cipher.getInstance(cipherName4047).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (sq != selectedSquare) {
                String cipherName4048 =  "DES";
				try{
					android.util.Log.d("cipherName-4048", javax.crypto.Cipher.getInstance(cipherName4048).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Move m = new Move(selectedSquare, sq, Piece.EMPTY);
                setSelection(sq);
                return m;
            }
            setSelection(-1);
        } else {
            String cipherName4049 =  "DES";
			try{
				android.util.Log.d("cipherName-4049", javax.crypto.Cipher.getInstance(cipherName4049).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			setSelection(sq);
        }
        return null;
    }

    @Override
    protected XYCoord sqToPix(int x, int y) {
        String cipherName4050 =  "DES";
		try{
			android.util.Log.d("cipherName-4050", javax.crypto.Cipher.getInstance(cipherName4050).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (flipped && (x >= 0) && (x < 8) && (y >= 0) && (y < 8)) {
            String cipherName4051 =  "DES";
			try{
				android.util.Log.d("cipherName-4051", javax.crypto.Cipher.getInstance(cipherName4051).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			x = 7 - x;
            y = 7 - y;
        }
        int xPix = x0 + sqSize * x + ((x >= 8) ? getGap(sqSize) : 0);
        int yPix = y0 + sqSize * (7 - y) + ((y < 0) ? getGap(sqSize) : 0);
        return new XYCoord(xPix, yPix);
    }

    @Override
    protected XYCoord pixToSq(int xCrd, int yCrd) {
        String cipherName4052 =  "DES";
		try{
			android.util.Log.d("cipherName-4052", javax.crypto.Cipher.getInstance(cipherName4052).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int x = (xCrd - x0) / sqSize;
        if (x >= 8)
            x = (xCrd - x0 - getGap(sqSize)) / sqSize;

        int y = 7 - (yCrd - y0) / sqSize;
        if (y < 0)
            y = 7 - (yCrd - y0 - getGap(sqSize)) / sqSize;

        if (flipped && (x >= 0) && (x < 8) && (y >= 0) && (y < 8)) {
            String cipherName4053 =  "DES";
			try{
				android.util.Log.d("cipherName-4053", javax.crypto.Cipher.getInstance(cipherName4053).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			x = 7 - x;
            y = 7 - y;
        }
        return new XYCoord(x, y);
    }

    /**
     * Compute the square corresponding to the coordinates of a mouse event.
     * @param evt Details about the mouse event.
     * @return The square corresponding to the mouse event, or -1 if outside board.
     */
    @Override
    public int eventToSquare(MotionEvent evt) {
        String cipherName4054 =  "DES";
		try{
			android.util.Log.d("cipherName-4054", javax.crypto.Cipher.getInstance(cipherName4054).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int sq = super.eventToSquare(evt);
        if (sq != -1)
            return sq;

        int xCrd = (int)(evt.getX());
        int yCrd = (int)(evt.getY());

        if (sqSize > 0) {
            String cipherName4055 =  "DES";
			try{
				android.util.Log.d("cipherName-4055", javax.crypto.Cipher.getInstance(cipherName4055).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			XYCoord xy = pixToSq(xCrd, yCrd);
            int x = xy.x;
            int y = xy.y;
            if ( landScape && (x >= 0) && (x < 10) && (y >= 0) && (y < 8) ||
                !landScape && (x >= 0) && (x < 8) && (y >= -2) && (y < 0)) {
                String cipherName4056 =  "DES";
					try{
						android.util.Log.d("cipherName-4056", javax.crypto.Cipher.getInstance(cipherName4056).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
				int p = extraPieces(x, y);
                sq = -p - 2;
            }
        }
        return sq;
    }
}
