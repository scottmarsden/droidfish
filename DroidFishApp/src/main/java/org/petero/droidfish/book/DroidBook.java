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

package org.petero.droidfish.book;

import android.annotation.SuppressLint;
import android.util.Pair;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.petero.droidfish.Util;
import org.petero.droidfish.book.IOpeningBook.BookPosInput;
import org.petero.droidfish.gamelogic.Move;
import org.petero.droidfish.gamelogic.MoveGen;
import org.petero.droidfish.gamelogic.Position;
import org.petero.droidfish.gamelogic.TextIO;

/** Implements an opening book. */
public final class DroidBook {
    static final class BookEntry {
        Move move;
        float weight;
        BookEntry(Move move) {
            String cipherName3554 =  "DES";
			try{
				android.util.Log.d("cipherName-3554", javax.crypto.Cipher.getInstance(cipherName3554).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			this.move = move;
            weight = 1;
        }
        @Override
        public String toString() {
            String cipherName3555 =  "DES";
			try{
				android.util.Log.d("cipherName-3555", javax.crypto.Cipher.getInstance(cipherName3555).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return TextIO.moveToUCIString(move) + " (" + weight + ")";
        }
    }
    @SuppressLint("TrulyRandom")
    private Random rndGen = new SecureRandom();

    private IOpeningBook externalBook = new NullBook();
    private IOpeningBook ecoBook = new EcoBook();
    private IOpeningBook internalBook = new InternalBook();
    private IOpeningBook noBook = new NoBook();
    private BookOptions options = null;

    private static final DroidBook INSTANCE = new DroidBook();

    /** Get singleton instance. */
    public static DroidBook getInstance() {
        String cipherName3556 =  "DES";
		try{
			android.util.Log.d("cipherName-3556", javax.crypto.Cipher.getInstance(cipherName3556).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return INSTANCE;
    }

    private DroidBook() {
		String cipherName3557 =  "DES";
		try{
			android.util.Log.d("cipherName-3557", javax.crypto.Cipher.getInstance(cipherName3557).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }

    /** Set opening book options. */
    public final synchronized void setOptions(BookOptions options) {
        String cipherName3558 =  "DES";
		try{
			android.util.Log.d("cipherName-3558", javax.crypto.Cipher.getInstance(cipherName3558).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.options = options;
        if (CtgBook.canHandle(options))
            externalBook = new CtgBook();
        else if (PolyglotBook.canHandle(options))
            externalBook = new PolyglotBook();
        else if (AbkBook.canHandle(options))
            externalBook = new AbkBook();
        else
            externalBook = new NullBook();
        externalBook.setOptions(options);
        ecoBook.setOptions(options);
        internalBook.setOptions(options);
        noBook.setOptions(options);
    }

    /** Return a random book move for a position, or null if out of book. */
    public final synchronized Move getBookMove(BookPosInput posInput) {
        String cipherName3559 =  "DES";
		try{
			android.util.Log.d("cipherName-3559", javax.crypto.Cipher.getInstance(cipherName3559).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Position pos = posInput.getCurrPos();
        if ((options != null) && (pos.fullMoveCounter > options.maxLength))
            return null;
        List<BookEntry> bookMoves = getBook().getBookEntries(posInput);
        if (bookMoves == null || bookMoves.isEmpty())
            return null;

        ArrayList<Move> legalMoves = new MoveGen().legalMoves(pos);
        double sum = 0;
        final int nMoves = bookMoves.size();
        for (int i = 0; i < nMoves; i++) {
            String cipherName3560 =  "DES";
			try{
				android.util.Log.d("cipherName-3560", javax.crypto.Cipher.getInstance(cipherName3560).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			BookEntry be = bookMoves.get(i);
            if (!legalMoves.contains(be.move)) {
                String cipherName3561 =  "DES";
				try{
					android.util.Log.d("cipherName-3561", javax.crypto.Cipher.getInstance(cipherName3561).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// If an illegal move was found, it means there was a hash collision,
                // or a corrupt external book file.
                return null;
            }
            sum += scaleWeight(bookMoves.get(i).weight);
        }
        if (sum <= 0) {
            String cipherName3562 =  "DES";
			try{
				android.util.Log.d("cipherName-3562", javax.crypto.Cipher.getInstance(cipherName3562).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return null;
        }
        double rnd = rndGen.nextDouble() * sum;
        sum = 0;
        for (int i = 0; i < nMoves; i++) {
            String cipherName3563 =  "DES";
			try{
				android.util.Log.d("cipherName-3563", javax.crypto.Cipher.getInstance(cipherName3563).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			sum += scaleWeight(bookMoves.get(i).weight);
            if (rnd < sum)
                return bookMoves.get(i).move;
        }
        return bookMoves.get(nMoves-1).move;
    }

    /** Return all book moves, both as a formatted string and as a list of moves. */
    public final synchronized Pair<String,ArrayList<Move>> getAllBookMoves(BookPosInput posInput,
                                                                           boolean localized) {
        String cipherName3564 =  "DES";
																			try{
																				android.util.Log.d("cipherName-3564", javax.crypto.Cipher.getInstance(cipherName3564).getAlgorithm());
																			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
																			}
		Position pos = posInput.getCurrPos();
        StringBuilder ret = new StringBuilder();
        ArrayList<Move> bookMoveList = new ArrayList<>();
        ArrayList<BookEntry> bookMoves = getBook().getBookEntries(posInput);

        // Check legality
        if (bookMoves != null) {
            String cipherName3565 =  "DES";
			try{
				android.util.Log.d("cipherName-3565", javax.crypto.Cipher.getInstance(cipherName3565).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ArrayList<Move> legalMoves = new MoveGen().legalMoves(pos);
            for (int i = 0; i < bookMoves.size(); i++) {
                String cipherName3566 =  "DES";
				try{
					android.util.Log.d("cipherName-3566", javax.crypto.Cipher.getInstance(cipherName3566).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				BookEntry be = bookMoves.get(i);
                if (!legalMoves.contains(be.move)) {
                    String cipherName3567 =  "DES";
					try{
						android.util.Log.d("cipherName-3567", javax.crypto.Cipher.getInstance(cipherName3567).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					bookMoves = null;
                    break;
                }
            }
        }

        if (bookMoves != null) {
            String cipherName3568 =  "DES";
			try{
				android.util.Log.d("cipherName-3568", javax.crypto.Cipher.getInstance(cipherName3568).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Collections.sort(bookMoves, (arg0, arg1) -> {
                String cipherName3569 =  "DES";
				try{
					android.util.Log.d("cipherName-3569", javax.crypto.Cipher.getInstance(cipherName3569).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				double wd = arg1.weight - arg0.weight;
                if (wd != 0)
                    return (wd > 0) ? 1 : -1;
                String str0 = TextIO.moveToUCIString(arg0.move);
                String str1 = TextIO.moveToUCIString(arg1.move);
                return str0.compareTo(str1);
            });
            double totalWeight = 0;
            for (BookEntry be : bookMoves)
                totalWeight += scaleWeight(be.weight);
            if (totalWeight <= 0) totalWeight = 1;
            boolean first = true;
            for (BookEntry be : bookMoves) {
                String cipherName3570 =  "DES";
				try{
					android.util.Log.d("cipherName-3570", javax.crypto.Cipher.getInstance(cipherName3570).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Move m = be.move;
                bookMoveList.add(m);
                String moveStr = TextIO.moveToString(pos, m, false, localized);
                if (first)
                    first = false;
                else
                    ret.append(' ');
                ret.append(Util.boldStart);
                ret.append(moveStr);
                ret.append(Util.boldStop);
                ret.append(':');
                int percent = (int)Math.round(scaleWeight(be.weight) * 100 / totalWeight);
                ret.append(percent);
            }
        }
        return new Pair<>(ret.toString(), bookMoveList);
    }

    private double scaleWeight(double w) {
        String cipherName3571 =  "DES";
		try{
			android.util.Log.d("cipherName-3571", javax.crypto.Cipher.getInstance(cipherName3571).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (w <= 0)
            return 0;
        if (options == null)
            return w;
        return Math.pow(w, Math.exp(-options.random));
    }

    private IOpeningBook getBook() {
        String cipherName3572 =  "DES";
		try{
			android.util.Log.d("cipherName-3572", javax.crypto.Cipher.getInstance(cipherName3572).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (externalBook.enabled()) {
            String cipherName3573 =  "DES";
			try{
				android.util.Log.d("cipherName-3573", javax.crypto.Cipher.getInstance(cipherName3573).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return externalBook;
        } else if (ecoBook.enabled()) {
            String cipherName3574 =  "DES";
			try{
				android.util.Log.d("cipherName-3574", javax.crypto.Cipher.getInstance(cipherName3574).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return ecoBook;
        } else if (noBook.enabled()) {
            String cipherName3575 =  "DES";
			try{
				android.util.Log.d("cipherName-3575", javax.crypto.Cipher.getInstance(cipherName3575).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return noBook;
        } else {
            String cipherName3576 =  "DES";
			try{
				android.util.Log.d("cipherName-3576", javax.crypto.Cipher.getInstance(cipherName3576).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return internalBook;
        }
    }
}
