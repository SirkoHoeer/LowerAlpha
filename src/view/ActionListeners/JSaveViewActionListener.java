package view.ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;

import javax.swing.JFileChooser;

import view.JAlphaNotationGUI;

public class JSaveViewActionListener implements ActionListener {

    private JAlphaNotationGUI gui;

    public JSaveViewActionListener(JAlphaNotationGUI gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String path = gui.GetCurrentFilePath();
        if (path == null) {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showSaveDialog(fc);
            if (returnVal != JFileChooser.APPROVE_OPTION) {
                return;
            }
            path = fc.getSelectedFile().getAbsolutePath();
            gui.SetCurrentFilePath(path);
        }
        File f = new File(path);
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8)) {
            writer.write(gui.GetTextSource());
        } catch (IOException e1) {

        }
    }
}
