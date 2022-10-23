package cripto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow extends javax.swing.JFrame {

    /**Classe para a encriptação/decriptação de arquivos.*/
    private final AESCipher aesCipher = new AESCipher();
    /**Status de encriptação.*/
    private boolean encrypted = false;
    /**Senha para encriptação/decriptação.*/
    private String password;
    /**Bytes do texto encriptado.*/
    private byte[] encriptedBytes;
    
    public MainWindow() {
        initComponents();
        updateUI();
    }
    
    /**
     * Atualizar a interface gráfica de usuário (UI) de acordo com o status
     * de encriptação.
     */
    private void updateUI() {
        jtfPassword.setEditable(!encrypted);
        jlText.setText(encrypted ? "TEXTO ENCRIPTADO" : "TEXTO ORIGINAL");
        jbEncrypt.setEnabled(!encrypted);
        jbDecrypt.setEnabled(encrypted);
        jtpText.setEditable(!encrypted);
    }
    
    /**
     * Realizar a encriptação do texto digitado.
     */
    private void encrypt() {
        try {
            //Senha para criptografia.
            password = jtfPassword.getText();
            //Texto em formato legível.
            String plainText = jtpText.getText();
            //Stream para o texto em formato legível.
            ByteArrayInputStream istream = new ByteArrayInputStream(plainText.getBytes());
            //Stream para o texto criptografado.
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            //Realiza a encriptação do texto legível.
            aesCipher.encrypt(istream, ostream, password);
            //Obtém os bytes do texto criptografado.
            encriptedBytes = ostream.toByteArray();
            //Imprime o texto criptografado.
            jtpText.setText(new String(encriptedBytes));
            //Atualiza os controles do usuário.
            encrypted = true;
            updateUI();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "ERRO",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Realizar a decriptação do texto encriptado.
     */
    private void decrypt() {
        try {
            //Senha para decriptografia.
            password = jtfPassword.getText();
            //Stream para o texto criptografado.
            ByteArrayInputStream istream = new ByteArrayInputStream(encriptedBytes);
            //Stream para o texto em formato legível.
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            //Realiza a decriptação do texto criptografado.
            aesCipher.decrypt(istream, ostream, password);
            //Imprime o texto em formato legível.
            jtpText.setText(new String(ostream.toByteArray()));
            //Atualiza os controles do usuário.
            encrypted = false;
            updateUI();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                ex.getMessage(),
                "ERRO",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Salvar o texto em arquivo.
     */
    private void saveFile() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo de Texto (*.txt)", "txt");
        FileChooserDialog dialog = new FileChooserDialog("Salvar Como...", filter);
        if (dialog.showSaveDialog(this) == FileChooserDialog.APPROVE_OPTION){
            File file = dialog.getSelectedFile();
            try {
                try (FileOutputStream fostream = new FileOutputStream(file)) {
                    if (encrypted) {
                        fostream.write(encriptedBytes);
                    } else {
                        String plainText = jtpText.getText();
                        fostream.write(plainText.getBytes());
                    }
                    fostream.flush();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jtfPassword = new javax.swing.JTextField();
        jlText = new javax.swing.JLabel();
        jbEncrypt = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtpText = new javax.swing.JTextPane();
        jbDecrypt = new javax.swing.JButton();
        jbSaveFile = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TESTE DE CRIPTOGRAFIA");
        setResizable(false);

        jLabel1.setText("SENHA:");

        jlText.setText("TEXTO ORIGINAL");

        jbEncrypt.setText("Criptografar");
        jbEncrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEncryptActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jtpText);

        jbDecrypt.setText("Decriptografar");
        jbDecrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDecryptActionPerformed(evt);
            }
        });

        jbSaveFile.setText("Salvar");
        jbSaveFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSaveFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jtfPassword)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jlText)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jbEncrypt, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbDecrypt, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbSaveFile, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbDecrypt)
                    .addComponent(jbEncrypt)
                    .addComponent(jbSaveFile))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbEncryptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEncryptActionPerformed
        encrypt();
    }//GEN-LAST:event_jbEncryptActionPerformed

    private void jbDecryptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDecryptActionPerformed
        decrypt();
    }//GEN-LAST:event_jbDecryptActionPerformed

    private void jbSaveFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSaveFileActionPerformed
        saveFile();
    }//GEN-LAST:event_jbSaveFileActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbDecrypt;
    private javax.swing.JButton jbEncrypt;
    private javax.swing.JButton jbSaveFile;
    private javax.swing.JLabel jlText;
    private javax.swing.JTextField jtfPassword;
    private javax.swing.JTextPane jtpText;
    // End of variables declaration//GEN-END:variables
}
