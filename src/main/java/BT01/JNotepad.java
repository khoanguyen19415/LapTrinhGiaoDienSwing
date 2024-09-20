/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 *
 * @author ADMIN
 */
public class JNotepad extends JFrame {

    private JMenuBar mBar;
    private JMenu mFile, mEdit, mFormat, mView, mZoom, mHelp;
    private JMenuItem itemNew, itemOpen, itemSave, itemSaveAs, itemPageSetup, itemPrint, itemExit, itemCopy, itemPaste, itemZoomIn, itemPast, itemZoomOut;
    private JMenuItem itemFont;
    private JCheckBoxMenuItem itemWrap;
    private JTextArea txtEditor;
    private JToolBar toolbar;
    private JButton btnNew, btnOpen, btnSave, btnExit;
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
        //tạo thanh thực đơn
        mBar = new JMenuBar();
        //tạo thực đơn file và add vào thanh thực đơn
        mBar.add(mFile = new JMenu("File"));
        //tạo thực đơn edit và add vào thanh thực đơn
        mBar.add(mEdit = new JMenu("Edit"));
        //tạo thực đơn format và add vào thanh thực đơn
        mBar.add(mFormat = new JMenu("Format"));
        //
        mBar.add(mView = new JMenu("View"));
        //
        mBar.add(mHelp = new JMenu("Help"));

        //tạo item và add vào menu file
        mFile.add(itemNew = new JMenuItem("New"));
        mFile.add(itemOpen = new JMenuItem("Open"));
        mFile.add(itemSave = new JMenuItem("Save"));
        mFile.add(itemSaveAs = new JMenuItem("SaveAs"));
        mFile.addSeparator();
        mFile.add(itemPageSetup = new JMenuItem("Page Setup"));
        mFile.add(itemPrint = new JMenuItem("Print..."));
        mEdit.add(itemCopy = new JMenuItem("Copy"));
        mEdit.add(itemPaste = new JMenuItem("Paste"));
        mView.add(itemZoomIn = new JMenuItem("ZoomIn"));
        mView.add(itemZoomOut = new JMenuItem("ZoomOut"));
        mFile.addSeparator();
        mFile.add(itemExit = new JMenuItem("Exit"));
        mFormat.add(itemWrap = new JCheckBoxMenuItem("Word Wrap", true));
        mFormat.add(itemFont = new JMenuItem("Font..."));

        //tạo tổ hợp phím nóng cho item
        itemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        itemSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
        itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));
        itemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        itemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK));

        itemZoomIn.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, KeyEvent.CTRL_DOWN_MASK));
        itemZoomOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, KeyEvent.CTRL_DOWN_MASK));
        // gắn thực đơn vào cửa sổ 

        setJMenuBar(mBar);

        // tiếp nhận sự kiện cho itemExit
        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Are you sure to exit ? ") == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        //new
        itemNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFile();
            }
        });

        // xử lý open
        // Thêm vào phương thức xử lý sự kiện cho nút Open
        itemOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFile();
            }
        });
        //save
        itemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFile();
            }
        });

        //cọpy
        itemCopy.addActionListener((ActionEvent e) -> {
            txtEditor.copy();
            JOptionPane.showMessageDialog(null, "sao chép thành công");
        });

        itemPaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtEditor.paste();
                JOptionPane.showMessageDialog(null, "Paste thành công");
            }
        });

        itemWrap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtEditor.setLineWrap(itemWrap.isSelected());
            }
        });
        btnNew.addActionListener((e) -> {
            newFile();
        });

        btnOpen.addActionListener((e) -> {
            openFile();
        });
        btnSave.addActionListener((e) -> {
            saveFile();
        });
    }

    public static void main(String[] Args) {
        JNotepad notepad = new JNotepad(" Demo Notepad");
        notepad.setVisible(true);

    }

    private void createGUI() {

        txtEditor = new JTextArea();
        JScrollPane scrollEditor = new JScrollPane(txtEditor);
        add(scrollEditor);
        txtEditor.setFont(new Font("Arial", Font.PLAIN, 20));

    }

    //new
    private void newFile() {
        if (JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn tạo file mới?") == JOptionPane.YES_OPTION) {
            txtEditor.setText("");
            currentFile = null;
        }
    }

    //open
    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        // Chỉ cho phép mở file có đuôi .txt
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);

        // Mở hộp thoại chọn file
        int returnValue = fileChooser.showOpenDialog(null);

        // Kiểm tra xem người dùng có chọn file không
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
                // Đọc nội dung file và hiển thị trong JTextArea
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

    //save
    private void saveFile() {
        if (currentFile != null) {  // Nếu đã mở file trước đó
            try (FileWriter writer = new FileWriter(currentFile)) {
                writer.write(txtEditor.getText());
                JOptionPane.showMessageDialog(null, "File saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving file: " + ex.getMessage());
            }
        }
    }

    private void processEvent() {
        // xử lý zoomin
        itemZoomIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                size -= 4;
                txtEditor.setFont(new Font("Arial", Font.PLAIN, size));
            }
        });
        // xử lí zoomout
        itemZoomOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                size += 4;
                txtEditor.setFont(new Font("Arial", Font.PLAIN, size));
            }
        });
    }

    // xử lý open
    private void createToolBar() {
        //tạo thanh công cụ jtoolbar
        toolbar = new JToolBar();
        //tạo nút lệnh
        toolbar.add(btnNew = new JButton("New"));
        toolbar.add(btnOpen = new JButton("Open"));
        toolbar.add(btnSave = new JButton("Save"));

        //set icon
        btnNew.setIcon(new ImageIcon(this.getClass().getResource("/images/new.png")));
        btnOpen.setIcon(new ImageIcon(this.getClass().getResource("/images/Save.png")));
        btnSave.setIcon(new ImageIcon(this.getClass().getResource("/images/open.png")));

        //add thanh công cụ
        add(toolbar, BorderLayout.NORTH);
    }
}
