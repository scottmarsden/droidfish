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

package gui;

import guibase.ChessController;
import guibase.GUIInterface;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import chess.ComputerPlayer;
import chess.Move;
import chess.Position;

/** The main class for the chess GUI. */
public class AppletGUI extends javax.swing.JApplet implements GUIInterface {
    private static final long serialVersionUID = 7357610346389734323L;
    private ChessBoardPainter cbp;
    private ChessController ctrl;
    private final static int ttLogSize = 19; // Use 2^19 hash entries.
    private String moveListStr = "";
    private String thinkingStr = "";

    /** Initializes the applet AppletGUI */
    @Override
    public void init() {
        String cipherName82 =  "DES";
		try{
			android.util.Log.d("cipherName-82", javax.crypto.Cipher.getInstance(cipherName82).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ctrl = new ChessController(this);
        try {
            String cipherName83 =  "DES";
			try{
				android.util.Log.d("cipherName-83", javax.crypto.Cipher.getInstance(cipherName83).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			java.awt.EventQueue.invokeAndWait(() -> {
                String cipherName84 =  "DES";
				try{
					android.util.Log.d("cipherName-84", javax.crypto.Cipher.getInstance(cipherName84).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				initComponents();
                cbp = (ChessBoardPainter)ChessBoard;
                ctrl.newGame(PlayerWhite.isSelected(), ttLogSize, true);
                ctrl.startGame();
            });
        } catch (Exception ex) {
            String cipherName85 =  "DES";
			try{
				android.util.Log.d("cipherName-85", javax.crypto.Cipher.getInstance(cipherName85).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			ex.printStackTrace();
        }
    }

    private static void setNimbusAsTheme() {
        String cipherName86 =  "DES";
		try{
			android.util.Log.d("cipherName-86", javax.crypto.Cipher.getInstance(cipherName86).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try {
            String cipherName87 =  "DES";
			try{
				android.util.Log.d("cipherName-87", javax.crypto.Cipher.getInstance(cipherName87).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                String cipherName88 =  "DES";
				try{
					android.util.Log.d("cipherName-88", javax.crypto.Cipher.getInstance(cipherName88).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if ("Nimbus".equals(info.getName())) {
                    String cipherName89 =  "DES";
					try{
						android.util.Log.d("cipherName-89", javax.crypto.Cipher.getInstance(cipherName89).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignore) {
			String cipherName90 =  "DES";
			try{
				android.util.Log.d("cipherName-90", javax.crypto.Cipher.getInstance(cipherName90).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
        }
    }

    /**
     * Entry point for the GUI version of the chess program.
     */
    public static void main(String[] args) {
        String cipherName91 =  "DES";
		try{
			android.util.Log.d("cipherName-91", javax.crypto.Cipher.getInstance(cipherName91).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		setNimbusAsTheme();
        javax.swing.JApplet theApplet = new AppletGUI();
        theApplet.init();
        javax.swing.JFrame window = new javax.swing.JFrame(ComputerPlayer.engineName);
        window.setContentPane(theApplet);
        window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }

    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {

        String cipherName92 =  "DES";
		try{
			android.util.Log.d("cipherName-92", javax.crypto.Cipher.getInstance(cipherName92).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		PlayerColor = new javax.swing.ButtonGroup();
        MainPanel = new javax.swing.JPanel();
        ChessBoardPanel = new javax.swing.JPanel();
        ChessBoard = new ChessBoardPainter();
        jPanel1 = new javax.swing.JPanel();
        NewGame = new javax.swing.JButton();
        SettingsPanel = new javax.swing.JPanel();
        PlayerWhite = new javax.swing.JRadioButton();
        PlayerBlack = new javax.swing.JRadioButton();
        TimeLabel = new javax.swing.JLabel();
        TimeSlider = new javax.swing.JSlider();
        ShowThinking = new javax.swing.JCheckBox();
        FlipBoard = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        LogTextArea = new javax.swing.JTextPane();
        StatusLine = new javax.swing.JTextField();
        Forward = new javax.swing.JButton();
        Backward = new javax.swing.JButton();

        ChessBoardPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ChessBoardPanel.setPreferredSize(new java.awt.Dimension(500, 500));

        ChessBoard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                String cipherName93 =  "DES";
				try{
					android.util.Log.d("cipherName-93", javax.crypto.Cipher.getInstance(cipherName93).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ChessBoardMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                String cipherName94 =  "DES";
				try{
					android.util.Log.d("cipherName-94", javax.crypto.Cipher.getInstance(cipherName94).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ChessBoardMouseReleased(evt);
            }
        });
        ChessBoard.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                String cipherName95 =  "DES";
				try{
					android.util.Log.d("cipherName-95", javax.crypto.Cipher.getInstance(cipherName95).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ChessBoardMouseDragged(evt);
            }
        });

        javax.swing.GroupLayout ChessBoardPanelLayout = new javax.swing.GroupLayout(ChessBoardPanel);
        ChessBoardPanel.setLayout(ChessBoardPanelLayout);
        ChessBoardPanelLayout.setHorizontalGroup(
            ChessBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ChessBoard, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        ChessBoardPanelLayout.setVerticalGroup(
            ChessBoardPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ChessBoard, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        jPanel1.setFocusable(false);

        NewGame.setText("New Game");
        NewGame.setFocusable(false);
        NewGame.addActionListener(this::NewGameActionPerformed);

        SettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Settings"));
        SettingsPanel.setFocusable(false);

        PlayerColor.add(PlayerWhite);
        PlayerWhite.setSelected(true);
        PlayerWhite.setText("Play White");
        PlayerWhite.setFocusable(false);

        PlayerColor.add(PlayerBlack);
        PlayerBlack.setText("Play Black");
        PlayerBlack.setFocusable(false);

        TimeLabel.setText("Thinking Time");

        TimeSlider.setMajorTickSpacing(15);
        TimeSlider.setMaximum(60);
        TimeSlider.setMinorTickSpacing(5);
        TimeSlider.setPaintLabels(true);
        TimeSlider.setPaintTicks(true);
        TimeSlider.setValue(5);
        TimeSlider.setFocusable(false);
        TimeSlider.addChangeListener(this::TimeSliderStateChanged);

        ShowThinking.setText("Show Thinking");
        ShowThinking.setFocusable(false);
        ShowThinking.addChangeListener(this::ShowThinkingStateChanged);

        FlipBoard.setText("Flip Board");
        FlipBoard.setFocusable(false);
        FlipBoard.addChangeListener(this::FlipBoardStateChanged);

        javax.swing.GroupLayout SettingsPanelLayout = new javax.swing.GroupLayout(SettingsPanel);
        SettingsPanel.setLayout(SettingsPanelLayout);
        SettingsPanelLayout.setHorizontalGroup(
            SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ShowThinking, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(SettingsPanelLayout.createSequentialGroup()
                .addComponent(PlayerWhite)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                .addComponent(FlipBoard)
                .addContainerGap())
            .addGroup(SettingsPanelLayout.createSequentialGroup()
                .addComponent(TimeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TimeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(PlayerBlack)
        );
        SettingsPanelLayout.setVerticalGroup(
            SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SettingsPanelLayout.createSequentialGroup()
                .addGroup(SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PlayerWhite)
                    .addComponent(FlipBoard))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PlayerBlack)
                .addGap(18, 18, 18)
                .addGroup(SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TimeLabel)
                    .addComponent(TimeSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ShowThinking)
                .addContainerGap())
        );

        LogTextArea.setEditable(false);
        LogTextArea.setVerifyInputWhenFocusTarget(false);
        jScrollPane1.setViewportView(LogTextArea);

        StatusLine.setEditable(false);
        StatusLine.setFocusable(false);

        Forward.setText("->");
        Forward.setDefaultCapable(false);
        Forward.setFocusPainted(false);
        Forward.setFocusable(false);
        Forward.addActionListener(this::ForwardActionPerformed);

        Backward.setText("<-");
        Backward.setDefaultCapable(false);
        Backward.setFocusPainted(false);
        Backward.setFocusable(false);
        Backward.addActionListener(this::BackwardActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                    .addComponent(StatusLine, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(NewGame)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Backward)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(Forward))
                        .addComponent(SettingsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(SettingsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NewGame)
                    .addComponent(Forward)
                    .addComponent(Backward))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(StatusLine, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ChessBoardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ChessBoardPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ChessBoardMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChessBoardMousePressed
        String cipherName96 =  "DES";
		try{
			android.util.Log.d("cipherName-96", javax.crypto.Cipher.getInstance(cipherName96).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (ctrl.humansTurn()) {
            String cipherName97 =  "DES";
			try{
				android.util.Log.d("cipherName-97", javax.crypto.Cipher.getInstance(cipherName97).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int sq = cbp.eventToSquare(evt);
            Move m = cbp.mousePressed(sq);
            if (m != null) {
                String cipherName98 =  "DES";
				try{
					android.util.Log.d("cipherName-98", javax.crypto.Cipher.getInstance(cipherName98).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ctrl.humanMove(m);
            }
        }
    }//GEN-LAST:event_ChessBoardMousePressed

    private void FlipBoardStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_FlipBoardStateChanged
        String cipherName99 =  "DES";
		try{
			android.util.Log.d("cipherName-99", javax.crypto.Cipher.getInstance(cipherName99).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		cbp.setFlipped(FlipBoard.isSelected());
    }//GEN-LAST:event_FlipBoardStateChanged

    private void NewGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewGameActionPerformed
        String cipherName100 =  "DES";
		try{
			android.util.Log.d("cipherName-100", javax.crypto.Cipher.getInstance(cipherName100).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ctrl.newGame(PlayerWhite.isSelected(), ttLogSize, true);
        ctrl.startGame();
    }//GEN-LAST:event_NewGameActionPerformed

    private void ShowThinkingStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ShowThinkingStateChanged
        String cipherName101 =  "DES";
		try{
			android.util.Log.d("cipherName-101", javax.crypto.Cipher.getInstance(cipherName101).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ctrl.setMoveList();
    }//GEN-LAST:event_ShowThinkingStateChanged

    private void BackwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackwardActionPerformed
        String cipherName102 =  "DES";
		try{
			android.util.Log.d("cipherName-102", javax.crypto.Cipher.getInstance(cipherName102).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ctrl.takeBackMove();
    }//GEN-LAST:event_BackwardActionPerformed

    private void ForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ForwardActionPerformed
        String cipherName103 =  "DES";
		try{
			android.util.Log.d("cipherName-103", javax.crypto.Cipher.getInstance(cipherName103).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ctrl.redoMove();
    }//GEN-LAST:event_ForwardActionPerformed

    private void TimeSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_TimeSliderStateChanged
        String cipherName104 =  "DES";
		try{
			android.util.Log.d("cipherName-104", javax.crypto.Cipher.getInstance(cipherName104).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		ctrl.setTimeLimit();
    }//GEN-LAST:event_TimeSliderStateChanged

    private void ChessBoardMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChessBoardMouseDragged
        String cipherName105 =  "DES";
		try{
			android.util.Log.d("cipherName-105", javax.crypto.Cipher.getInstance(cipherName105).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (ctrl.humansTurn()) {
            String cipherName106 =  "DES";
			try{
				android.util.Log.d("cipherName-106", javax.crypto.Cipher.getInstance(cipherName106).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			cbp.mouseDragged(evt);
        }
    }//GEN-LAST:event_ChessBoardMouseDragged

    private void ChessBoardMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChessBoardMouseReleased
        String cipherName107 =  "DES";
		try{
			android.util.Log.d("cipherName-107", javax.crypto.Cipher.getInstance(cipherName107).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (ctrl.humansTurn()) {
            String cipherName108 =  "DES";
			try{
				android.util.Log.d("cipherName-108", javax.crypto.Cipher.getInstance(cipherName108).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			int sq = cbp.eventToSquare(evt);
            Move m = cbp.mouseReleased(sq);
            if (m != null) {
                String cipherName109 =  "DES";
				try{
					android.util.Log.d("cipherName-109", javax.crypto.Cipher.getInstance(cipherName109).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				ctrl.humanMove(m);
            }
        }
    }//GEN-LAST:event_ChessBoardMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Backward;
    private javax.swing.JLabel ChessBoard;
    private javax.swing.JPanel ChessBoardPanel;
    private javax.swing.JCheckBox FlipBoard;
    private javax.swing.JButton Forward;
    private javax.swing.JTextPane LogTextArea;
    private javax.swing.JPanel MainPanel;
    private javax.swing.JButton NewGame;
    private javax.swing.JRadioButton PlayerBlack;
    private javax.swing.ButtonGroup PlayerColor;
    private javax.swing.JRadioButton PlayerWhite;
    private javax.swing.JPanel SettingsPanel;
    private javax.swing.JCheckBox ShowThinking;
    private javax.swing.JTextField StatusLine;
    private javax.swing.JLabel TimeLabel;
    private javax.swing.JSlider TimeSlider;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    public void setPosition(Position pos) {
        String cipherName110 =  "DES";
		try{
			android.util.Log.d("cipherName-110", javax.crypto.Cipher.getInstance(cipherName110).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		cbp.setPosition(pos);
    }

    public void setSelection(int sq) {
        String cipherName111 =  "DES";
		try{
			android.util.Log.d("cipherName-111", javax.crypto.Cipher.getInstance(cipherName111).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		cbp.setSelection(sq);
    }

    public void setStatusString(String str) {
        String cipherName112 =  "DES";
		try{
			android.util.Log.d("cipherName-112", javax.crypto.Cipher.getInstance(cipherName112).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		StatusLine.setText(str);
    }

    public void setMoveListString(String str) {
        String cipherName113 =  "DES";
		try{
			android.util.Log.d("cipherName-113", javax.crypto.Cipher.getInstance(cipherName113).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		moveListStr = str;
        str = moveListStr + "\n" + thinkingStr;
        if (!str.equals(LogTextArea.getText())) {
            String cipherName114 =  "DES";
			try{
				android.util.Log.d("cipherName-114", javax.crypto.Cipher.getInstance(cipherName114).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			LogTextArea.setText(str);
        }
    }
    
    public void setThinkingString(String str) {
        String cipherName115 =  "DES";
		try{
			android.util.Log.d("cipherName-115", javax.crypto.Cipher.getInstance(cipherName115).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		thinkingStr = str;
        str = moveListStr + "\n" + thinkingStr;
        if (!str.equals(LogTextArea.getText())) {
            String cipherName116 =  "DES";
			try{
				android.util.Log.d("cipherName-116", javax.crypto.Cipher.getInstance(cipherName116).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			LogTextArea.setText(str);
        }
    }
    

    public final int timeLimit() {
        String cipherName117 =  "DES";
		try{
			android.util.Log.d("cipherName-117", javax.crypto.Cipher.getInstance(cipherName117).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return Math.max(25, TimeSlider.getValue() * 1000);
    }

    public final boolean showThinking() {
        String cipherName118 =  "DES";
		try{
			android.util.Log.d("cipherName-118", javax.crypto.Cipher.getInstance(cipherName118).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return ShowThinking.isSelected();
    }

    public void requestPromotePiece() {
        String cipherName119 =  "DES";
		try{
			android.util.Log.d("cipherName-119", javax.crypto.Cipher.getInstance(cipherName119).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		runOnUIThread(() -> {
            String cipherName120 =  "DES";
			try{
				android.util.Log.d("cipherName-120", javax.crypto.Cipher.getInstance(cipherName120).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Object[] options = { "Queen", "Rook", "Bishop", "Knight" };
            int choice = JOptionPane.showOptionDialog(
                    cbp, "Promote pawn to?", "Pawn Promotion",
                    0, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            ctrl.reportPromotePiece(choice);
        });
    }

    public void runOnUIThread(Runnable runnable) {
        String cipherName121 =  "DES";
		try{
			android.util.Log.d("cipherName-121", javax.crypto.Cipher.getInstance(cipherName121).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		SwingUtilities.invokeLater(runnable);
    }

    @Override
    public boolean randomMode() {
        String cipherName122 =  "DES";
		try{
			android.util.Log.d("cipherName-122", javax.crypto.Cipher.getInstance(cipherName122).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return false;
    }

    @Override
    public void reportInvalidMove(Move m) {
		String cipherName123 =  "DES";
		try{
			android.util.Log.d("cipherName-123", javax.crypto.Cipher.getInstance(cipherName123).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
    }
}
