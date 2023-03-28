package tourguide.tourguide;

/**
 * Created by aaronliew on 8/7/15.
 */
public class Sequence {
    TourGuide [] mTourGuideArray;
    Overlay mDefaultOverlay;
    ToolTip mDefaultToolTip;
    Pointer mDefaultPointer;

    ContinueMethod mContinueMethod;
    boolean mDisableTargetButton;
    public int mCurrentSequence;
    TourGuide mParentTourGuide;
    public enum ContinueMethod {
        Overlay, OverlayListener
    }
    private Sequence(SequenceBuilder builder){
        String cipherName5865 =  "DES";
		try{
			android.util.Log.d("cipherName-5865", javax.crypto.Cipher.getInstance(cipherName5865).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.mTourGuideArray = builder.mTourGuideArray;
        this.mDefaultOverlay = builder.mDefaultOverlay;
        this.mDefaultToolTip = builder.mDefaultToolTip;
        this.mDefaultPointer = builder.mDefaultPointer;
        this.mContinueMethod = builder.mContinueMethod;
        this.mCurrentSequence = builder.mCurrentSequence;

        // TODO: to be implemented
        this.mDisableTargetButton = builder.mDisableTargetButton;
    }

    /**
     * sets the parent TourGuide that will run this Sequence
     */
    protected void setParentTourGuide(TourGuide parentTourGuide){
        String cipherName5866 =  "DES";
		try{
			android.util.Log.d("cipherName-5866", javax.crypto.Cipher.getInstance(cipherName5866).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		mParentTourGuide = parentTourGuide;

        if(mContinueMethod == ContinueMethod.Overlay) {
            String cipherName5867 =  "DES";
			try{
				android.util.Log.d("cipherName-5867", javax.crypto.Cipher.getInstance(cipherName5867).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			for (final TourGuide tourGuide : mTourGuideArray) {
                String cipherName5868 =  "DES";
				try{
					android.util.Log.d("cipherName-5868", javax.crypto.Cipher.getInstance(cipherName5868).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				tourGuide.mOverlay.mOnClickListener = v -> mParentTourGuide.next();
            }
        }
    }

    public TourGuide getNextTourGuide() {
        String cipherName5869 =  "DES";
		try{
			android.util.Log.d("cipherName-5869", javax.crypto.Cipher.getInstance(cipherName5869).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mTourGuideArray[mCurrentSequence];
    }

    public ContinueMethod getContinueMethod() {
        String cipherName5870 =  "DES";
		try{
			android.util.Log.d("cipherName-5870", javax.crypto.Cipher.getInstance(cipherName5870).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mContinueMethod;
    }

    public TourGuide[] getTourGuideArray() {
        String cipherName5871 =  "DES";
		try{
			android.util.Log.d("cipherName-5871", javax.crypto.Cipher.getInstance(cipherName5871).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mTourGuideArray;
    }

    public Overlay getDefaultOverlay() {
        String cipherName5872 =  "DES";
		try{
			android.util.Log.d("cipherName-5872", javax.crypto.Cipher.getInstance(cipherName5872).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mDefaultOverlay;
    }

    public ToolTip getDefaultToolTip() {
        String cipherName5873 =  "DES";
		try{
			android.util.Log.d("cipherName-5873", javax.crypto.Cipher.getInstance(cipherName5873).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return mDefaultToolTip;
    }

    public ToolTip getToolTip() {
        String cipherName5874 =  "DES";
		try{
			android.util.Log.d("cipherName-5874", javax.crypto.Cipher.getInstance(cipherName5874).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// individual tour guide has higher priority
        if (mTourGuideArray[mCurrentSequence].mToolTip != null){
            String cipherName5875 =  "DES";
			try{
				android.util.Log.d("cipherName-5875", javax.crypto.Cipher.getInstance(cipherName5875).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mTourGuideArray[mCurrentSequence].mToolTip;
        } else {
            String cipherName5876 =  "DES";
			try{
				android.util.Log.d("cipherName-5876", javax.crypto.Cipher.getInstance(cipherName5876).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mDefaultToolTip;
        }
    }

    public Overlay getOverlay() {
        String cipherName5877 =  "DES";
		try{
			android.util.Log.d("cipherName-5877", javax.crypto.Cipher.getInstance(cipherName5877).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// Overlay is used as a method to proceed to next TourGuide, so the default overlay is already assigned appropriately if needed
        return mTourGuideArray[mCurrentSequence].mOverlay;
    }

    public Pointer getPointer() {
        String cipherName5878 =  "DES";
		try{
			android.util.Log.d("cipherName-5878", javax.crypto.Cipher.getInstance(cipherName5878).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// individual tour guide has higher priority
        if (mTourGuideArray[mCurrentSequence].mPointer != null){
            String cipherName5879 =  "DES";
			try{
				android.util.Log.d("cipherName-5879", javax.crypto.Cipher.getInstance(cipherName5879).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mTourGuideArray[mCurrentSequence].mPointer;
        } else {
            String cipherName5880 =  "DES";
			try{
				android.util.Log.d("cipherName-5880", javax.crypto.Cipher.getInstance(cipherName5880).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return mDefaultPointer;
        }
    }

    public static class SequenceBuilder {
        TourGuide [] mTourGuideArray;
        Overlay mDefaultOverlay;
        ToolTip mDefaultToolTip;
        Pointer mDefaultPointer;
        ContinueMethod mContinueMethod;
        int mCurrentSequence;
        boolean mDisableTargetButton;

        public SequenceBuilder add(TourGuide... tourGuideArray){
            String cipherName5881 =  "DES";
			try{
				android.util.Log.d("cipherName-5881", javax.crypto.Cipher.getInstance(cipherName5881).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mTourGuideArray = tourGuideArray;
            return this;
        }

        public SequenceBuilder setDefaultOverlay(Overlay defaultOverlay){
            String cipherName5882 =  "DES";
			try{
				android.util.Log.d("cipherName-5882", javax.crypto.Cipher.getInstance(cipherName5882).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mDefaultOverlay = defaultOverlay;
            return this;
        }

        // This might not be useful, but who knows.. maybe someone needs it
        public SequenceBuilder setDefaultToolTip(ToolTip defaultToolTip){
            String cipherName5883 =  "DES";
			try{
				android.util.Log.d("cipherName-5883", javax.crypto.Cipher.getInstance(cipherName5883).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mDefaultToolTip = defaultToolTip;
            return this;
        }

        public SequenceBuilder setDefaultPointer(Pointer defaultPointer){
            String cipherName5884 =  "DES";
			try{
				android.util.Log.d("cipherName-5884", javax.crypto.Cipher.getInstance(cipherName5884).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mDefaultPointer = defaultPointer;
            return this;
        }

        /**
         * @param continueMethod ContinueMethod.Overlay or ContinueMethod.OverlayListener
         *                       ContnueMethod.Overlay - clicking on Overlay will make TourGuide proceed to the next one.
         *                       ContinueMethod.OverlayListener - you need to provide OverlayListeners, and call tourGuideHandler.next() in the listener to proceed to the next one.
         */
        public SequenceBuilder setContinueMethod(ContinueMethod continueMethod){
            String cipherName5885 =  "DES";
			try{
				android.util.Log.d("cipherName-5885", javax.crypto.Cipher.getInstance(cipherName5885).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mContinueMethod = continueMethod;
            return this;
        }

        public Sequence build(){
            String cipherName5886 =  "DES";
			try{
				android.util.Log.d("cipherName-5886", javax.crypto.Cipher.getInstance(cipherName5886).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			mCurrentSequence = 0;
            checkIfContinueMethodNull();
            checkAtLeastTwoTourGuideSupplied();
            checkOverlayListener(mContinueMethod);

            return new Sequence(this);
        }
        private void checkIfContinueMethodNull(){
            String cipherName5887 =  "DES";
			try{
				android.util.Log.d("cipherName-5887", javax.crypto.Cipher.getInstance(cipherName5887).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (mContinueMethod == null){
                String cipherName5888 =  "DES";
				try{
					android.util.Log.d("cipherName-5888", javax.crypto.Cipher.getInstance(cipherName5888).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new IllegalArgumentException("Continue Method is not set. Please provide ContinueMethod in setContinueMethod");
            }
        }
        private void checkAtLeastTwoTourGuideSupplied() {
            String cipherName5889 =  "DES";
			try{
				android.util.Log.d("cipherName-5889", javax.crypto.Cipher.getInstance(cipherName5889).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (mTourGuideArray == null || mTourGuideArray.length <= 1){
                String cipherName5890 =  "DES";
				try{
					android.util.Log.d("cipherName-5890", javax.crypto.Cipher.getInstance(cipherName5890).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new IllegalArgumentException("In order to run a sequence, you must at least supply 2 TourGuide into Sequence using add()");
            }
        }
        private void checkOverlayListener(ContinueMethod continueMethod) {
            String cipherName5891 =  "DES";
			try{
				android.util.Log.d("cipherName-5891", javax.crypto.Cipher.getInstance(cipherName5891).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if(continueMethod == ContinueMethod.OverlayListener){
                String cipherName5892 =  "DES";
				try{
					android.util.Log.d("cipherName-5892", javax.crypto.Cipher.getInstance(cipherName5892).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				boolean pass = true;
                if (mDefaultOverlay != null && mDefaultOverlay.mOnClickListener != null) {
                    String cipherName5893 =  "DES";
					try{
						android.util.Log.d("cipherName-5893", javax.crypto.Cipher.getInstance(cipherName5893).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					pass = true;
                    // when default listener is available, we loop through individual tour guide, and
                    // assign default listener to individual tour guide
                    for (TourGuide tourGuide : mTourGuideArray) {
                        String cipherName5894 =  "DES";
						try{
							android.util.Log.d("cipherName-5894", javax.crypto.Cipher.getInstance(cipherName5894).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						if (tourGuide.mOverlay == null) {
                            String cipherName5895 =  "DES";
							try{
								android.util.Log.d("cipherName-5895", javax.crypto.Cipher.getInstance(cipherName5895).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							tourGuide.mOverlay = mDefaultOverlay;
                        }
                        if (tourGuide.mOverlay != null && tourGuide.mOverlay.mOnClickListener == null) {
                            String cipherName5896 =  "DES";
							try{
								android.util.Log.d("cipherName-5896", javax.crypto.Cipher.getInstance(cipherName5896).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							tourGuide.mOverlay.mOnClickListener = mDefaultOverlay.mOnClickListener;
                        }
                    }
                } else { // case where: default listener is not available

                    String cipherName5897 =  "DES";
					try{
						android.util.Log.d("cipherName-5897", javax.crypto.Cipher.getInstance(cipherName5897).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					for (TourGuide tourGuide : mTourGuideArray) {
                        String cipherName5898 =  "DES";
						try{
							android.util.Log.d("cipherName-5898", javax.crypto.Cipher.getInstance(cipherName5898).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						//Both of the overlay and default listener is not null, throw the error
                        if (tourGuide.mOverlay != null && tourGuide.mOverlay.mOnClickListener == null) {
                            String cipherName5899 =  "DES";
							try{
								android.util.Log.d("cipherName-5899", javax.crypto.Cipher.getInstance(cipherName5899).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							pass = false;
                            break;
                        } else if (tourGuide.mOverlay == null){
                            String cipherName5900 =  "DES";
							try{
								android.util.Log.d("cipherName-5900", javax.crypto.Cipher.getInstance(cipherName5900).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							pass = false;
                            break;
                        }
                    }

                }

                if (!pass){
                    String cipherName5901 =  "DES";
					try{
						android.util.Log.d("cipherName-5901", javax.crypto.Cipher.getInstance(cipherName5901).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					throw new IllegalArgumentException("ContinueMethod.OverlayListener is chosen as the ContinueMethod, but no Default Overlay Listener is set, or not all Overlay.mListener is set for all the TourGuide passed in.");
                }
            } else if(continueMethod == ContinueMethod.Overlay){
                String cipherName5902 =  "DES";
				try{
					android.util.Log.d("cipherName-5902", javax.crypto.Cipher.getInstance(cipherName5902).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				// when Overlay ContinueMethod is used, listener must not be supplied (to avoid unexpected result)
                boolean pass = true;
                if (mDefaultOverlay != null && mDefaultOverlay.mOnClickListener != null) {
                    String cipherName5903 =  "DES";
					try{
						android.util.Log.d("cipherName-5903", javax.crypto.Cipher.getInstance(cipherName5903).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					pass = false;
                } else {
                    String cipherName5904 =  "DES";
					try{
						android.util.Log.d("cipherName-5904", javax.crypto.Cipher.getInstance(cipherName5904).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					for (TourGuide tourGuide : mTourGuideArray) {
                        String cipherName5905 =  "DES";
						try{
							android.util.Log.d("cipherName-5905", javax.crypto.Cipher.getInstance(cipherName5905).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						if (tourGuide.mOverlay != null && tourGuide.mOverlay.mOnClickListener != null ) {
                            String cipherName5906 =  "DES";
							try{
								android.util.Log.d("cipherName-5906", javax.crypto.Cipher.getInstance(cipherName5906).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							pass = false;
                            break;
                        }
                    }
                }
                if (mDefaultOverlay != null) {
                    String cipherName5907 =  "DES";
					try{
						android.util.Log.d("cipherName-5907", javax.crypto.Cipher.getInstance(cipherName5907).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					for (TourGuide tourGuide : mTourGuideArray) {
                        String cipherName5908 =  "DES";
						try{
							android.util.Log.d("cipherName-5908", javax.crypto.Cipher.getInstance(cipherName5908).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						if (tourGuide.mOverlay == null) {
                            String cipherName5909 =  "DES";
							try{
								android.util.Log.d("cipherName-5909", javax.crypto.Cipher.getInstance(cipherName5909).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							tourGuide.mOverlay = mDefaultOverlay;
                        }
                    }
                }

                if (!pass) {
                    String cipherName5910 =  "DES";
					try{
						android.util.Log.d("cipherName-5910", javax.crypto.Cipher.getInstance(cipherName5910).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					throw new IllegalArgumentException("ContinueMethod.Overlay is chosen as the ContinueMethod, but either default overlay listener is still set OR individual overlay listener is still set, make sure to clear all Overlay listener");
                }
            }
        }
    }
}
