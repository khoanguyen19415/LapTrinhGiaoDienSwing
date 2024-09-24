package BT01;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JNotepad extends JFrame {
    private JMenuBar mBar;
    private JMenu mFile, mEdit, mFormat, mView, mHelp;
    private JMenuItem itemNew, itemOpen, itemSave, itemSaveAs, itemPageSetup, itemPrint, itemExit, itemCopy, itemPaste, itemZoomIn, itemZoomOut;
    private JMenuItem itemFont;
    private JCheckBoxMenuItem itemWrap;
    private JTextArea txtEditor;
    private JToolBar toolbar;
    private JButton btnNew, btnOpen, btnSave;
    private File currentFile = null;
    int size = 20;

    public JNotepad(String title) {
        super(title);
        createMenu();
        createGUI();
        createToolBar();
        processEvent();
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void createMenu() {
        mBar = new JMenuBar();
        mBar.add(mFile = new JMenu("File"));
        mBar.add(mEdit = new JMenu("Edit"));
        mBar.add(mFormat = new JMenu("Format"));
        mBar.add(mView = new JMenu("View"));
        mBar.add(mHelp = new JMenu("Help"));

        // Tạo các mục menu và thêm vào menu File
        mFile.add(itemNew = new JMenuItem("New"));
        mFile.add(itemOpen = new JMenuItem("Open"));
        mFile.add(itemSave = new JMenuItem("Save"));
        mFile.add(itemSaveAs = new JMenuItem("Save As"));
        mFile.addSeparator();
        mFile.add(itemPageSetup = new JMenuItem("Page Setup"));
        mFile.add(itemPrint = new JMenuItem("Print..."));
        mEdit.add(itemCopy = new JMenuItem("Copy"));
        mEdit.add(itemPaste = new JMenuItem("Paste"));
        mView.add(itemZoomIn = new JMenuItem("Zoom In"));
        mView.add(itemZoomOut = new JMenuItem("Zoom Out"));
        mFile.addSeparator();
        mFile.add(itemExit = new JMenuItem("Exit"));
        mFormat.add(itemWrap = new JCheckBoxMenuItem("Word Wrap", true));
        mFormat.add(itemFont = new JMenuItem("Font..."));

        setJMenuBar(mBar);
        setMenuActions();
    }

    private void setMenuActions() {
        itemExit.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(null, "Are you sure to exit?") == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        itemNew.addActionListener(e -> newFile());
        itemOpen.addActionListener(e -> openFile());
        itemSave.addActionListener(e -> saveFile());
        itemCopy.addActionListener(e -> {
            txtEditor.copy();
            JOptionPane.showMessageDialog(null, "sao chép thành công");
        });
        itemPaste.addActionListener(e -> {
            txtEditor.paste();
            JOptionPane.showMessageDialog(null, "sao chép thất bại");
        });
        itemWrap.addActionListener(e -> txtEditor.setLineWrap(itemWrap.isSelected()));
        
        setKeyAccelerators();
    }

    private void setKeyAccelerators() {
        // Đặt tổ hợp phím nóng cho các menu item
        itemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        itemSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
        itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));
        itemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        itemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));
        itemZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, KeyEvent.CTRL_DOWN_MASK));
        itemZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, KeyEvent.CTRL_DOWN_MASK));
    }

    public static void main(String[] Args) {
        JNotepad notepad = new JNotepad("Demo Notepad");
        notepad.setVisible(true);
    }

    private void createGUI() {
        txtEditor = new JTextArea();
        JScrollPane scrollEditor = new JScrollPane(txtEditor);
        add(scrollEditor);
        txtEditor.setFont(new Font("Arial", Font.PLAIN, size));
    }

    private void newFile() {
        if (JOptionPane.showConfirmDialog(null, "bạn có muốn tạo file mới?") == JOptionPane.YES_OPTION) {
            txtEditor.setText("");
            currentFile = null;
        }
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                txtEditor.setText("");
                String line;
                while ((line = br.readLine()) != null) {
                    txtEditor.append(line + "\n");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "lỗi: " + ex.getMessage());
            }
        }
    }

    private void saveFile() {
        if (currentFile == null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save As");
            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                currentFile = fileChooser.getSelectedFile();
            }
        }

        if (currentFile != null) {
            try (FileWriter writer = new FileWriter(currentFile)) {
                writer.write(txtEditor.getText());
                JOptionPane.showMessageDialog(null, "lưu thành công!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "lỗi: " + ex.getMessage());
            }
        }
    }

    private void processEvent() {
        itemZoomIn.addActionListener(e -> {
            if (size < 50) {
                size += 4;
                txtEditor.setFont(new Font("Arial", Font.PLAIN, size));
            }
        });
        itemZoomOut.addActionListener(e -> {
            if (size > 10) {
                size -= 4;
                txtEditor.setFont(new Font("Arial", Font.PLAIN, size));
            }
        });
    }

    private void createToolBar() {
        toolbar = new JToolBar();
    
    // Tạo nút lệnh và thêm icon
    btnNew = new JButton("New");
    btnNew.setIcon(new ImageIcon(getClass().getResource("/images/new.png")));
    toolbar.add(btnNew);
    
    btnOpen = new JButton("Open");
    btnOpen.setIcon(new ImageIcon(getClass().getResource("/images/open.png")));
    toolbar.add(btnOpen);
    
    btnSave = new JButton("Save");
    btnSave.setIcon(new ImageIcon(getClass().getResource("/images/save.png")));
    toolbar.add(btnSave);

    // Thêm các nút vào thanh công cụ
    btnNew.addActionListener(e -> newFile());
    btnOpen.addActionListener(e -> openFile());
    btnSave.addActionListener(e -> saveFile());
    
    // Thêm thanh công cụ vào khung
    add(toolbar, BorderLayout.NORTH);
    }
}
