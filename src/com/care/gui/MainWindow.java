package com.care.gui;

import com.care.datatype.Component;
import com.care.datatype.ParseInputType;
import com.care.main.Main;
import com.google.common.base.Strings;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

public class MainWindow
{
    private final String DEFAULT_SAVE_NAME = "config_%s.xml";
    private JFrame frame;
    private JTextField tfInputFile;
    private JTextField tfOutputFile;
    private JList componentList;
    private JComboBox cbInputType;
    private JComboBox cbOutputType;
    private JButton btnRemove;
    final JFileChooser fc = new JFileChooser();
    private DefaultListModel<Component> componentListModel = new DefaultListModel();
    final ParseInputType[] fileSupport = {ParseInputType.STRING, ParseInputType.LIST};

    /**
     * Launch the application.
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    MainWindow window = new MainWindow();
                    window.frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainWindow()
    {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        frame = new JFrame();
        frame.setBounds(100, 100, 520, 369);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(
                new FormLayout(new ColumnSpec[]{FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                        FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), FormFactory.UNRELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
                        FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,}, new RowSpec[]{FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"),
                        FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"), FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC, FormFactory.UNRELATED_GAP_ROWSPEC,}));

        JLabel lblInputFile = new JLabel("Input File/ Folder");
        frame.getContentPane().add(lblInputFile, "2, 2, fill, default");

        tfInputFile = new JTextField();
        frame.getContentPane().add(tfInputFile, "6, 2, 3, 1, fill, default");
        tfInputFile.setColumns(10);

        JButton btnInputFile = new JButton("");
        btnInputFile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int returnVal = fc.showOpenDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = fc.getSelectedFile();
                    // This is where a real application would open the file.
                    tfInputFile.setText(file.getAbsolutePath());
                }
                else
                {
                    System.out.println("Open command cancelled by user.");
                }
            }
        });
        frame.getContentPane().add(btnInputFile, "9, 2");

        JLabel lblFileType = new JLabel("Input Type");
        frame.getContentPane().add(lblFileType, "2, 4");

        cbInputType = new JComboBox(fileSupport);
        frame.getContentPane().add(cbInputType, "6, 4, 3, 1, fill, default");

        JSeparator separator = new JSeparator();
        frame.getContentPane().add(separator, "2, 6, 8, 1");

        JLabel lblComponents = new JLabel("Components");
        frame.getContentPane().add(lblComponents, "2, 8");

        JButton btnAdd = new JButton("Add");
        btnAdd.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                AddComponentPanel panel = new AddComponentPanel(frame);
                int choice = JOptionPane.showOptionDialog(frame, panel, "Add Component", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
                if (choice == JOptionPane.OK_OPTION)
                {
                    Component component = panel.getComponent();
                    componentListModel.addElement(component);
                }
            }
        });

        componentList = new JList(componentListModel);
        componentList.addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                if (componentList.getSelectedIndex() == -1)
                {
                    btnRemove.setEnabled(false);
                }
                else
                {
                    btnRemove.setEnabled(true);
                }
            }
        });

        JScrollPane listScrollPane = new JScrollPane();
        listScrollPane.setViewportView(componentList);
        frame.getContentPane().add(listScrollPane, "2, 10, 5, 7, fill, fill");
        frame.getContentPane().add(btnAdd, "8, 10");

        btnRemove = new JButton("Remove");
        btnRemove.setEnabled(false);
        btnRemove.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                while (componentList.getSelectedIndex() != -1)
                {
                    componentListModel.remove(componentList.getSelectedIndex());
                }
            }
        });
        frame.getContentPane().add(btnRemove, "8, 12");

        JSeparator separator_1 = new JSeparator();
        frame.getContentPane().add(separator_1, "2, 18, 8, 1");

        JLabel lblOutputFolder = new JLabel("Output Folder");
        frame.getContentPane().add(lblOutputFolder, "2, 20");

        tfOutputFile = new JTextField();
        frame.getContentPane().add(tfOutputFile, "6, 20, 3, 1, fill, top");
        tfOutputFile.setColumns(10);

        JButton btnOutputFolder = new JButton("");
        btnOutputFolder.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int returnVal = fc.showOpenDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = fc.getSelectedFile();
                    tfOutputFile.setText(file.getAbsolutePath());
                }
            }
        });
        frame.getContentPane().add(btnOutputFolder, "9, 20");

        JLabel lblOutputType = new JLabel("Output Type");
        frame.getContentPane().add(lblOutputType, "2, 22");

        cbOutputType = new JComboBox(fileSupport);
        frame.getContentPane().add(cbOutputType, "6, 22, 3, 1, fill, default");

        JSeparator separator_2 = new JSeparator();
        frame.getContentPane().add(separator_2, "2, 24, 8, 1");

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.dispose();
            }
        });
        frame.getContentPane().add(btnCancel, "6, 26");

        JButton btnOk = new JButton("Ok");
        btnOk.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String xml;
                try
                {
                    xml = generateXML();
                    UUID uuid = UUID.randomUUID();
                    String fileName = String.format(DEFAULT_SAVE_NAME, uuid.toString());
                    PrintWriter writer = new PrintWriter(fileName, "UTF-8");
                    writer.println(xml);
                    writer.close();
                    Main.execute(fileName);
                    JOptionPane.showMessageDialog(frame, "Ouput Generated");
                }
                catch (Exception exception)
                {
                    JOptionPane.showMessageDialog(frame, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frame.getContentPane().add(btnOk, "8, 26");

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("File");
        menuBar.add(mnFile);

        JMenuItem mntmLoad = new JMenuItem("Load");
        mnFile.add(mntmLoad);
        
        JMenuItem mntmSave = new JMenuItem("Save As");
        mnFile.add(mntmSave);
        
        JMenuItem mntmExit = new JMenuItem("Exit");
        mnFile.add(mntmExit);

        JMenu mnAbout = new JMenu("About");
        menuBar.add(mnAbout);
    }

    protected String generateXML() throws Exception
    {
        // Input Type
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<config>");
        xmlBuilder.append("<input>");
        File f = new File(tfInputFile.getText());
        if (f.exists() && f.isFile())
        {
            xmlBuilder.append("<type>File</type>");
        }
        else if (f.exists() && f.isDirectory())
        {
            xmlBuilder.append("<type>Folder</type>");
        }
        else
        {
            throw new IOException("Input file not found");
        }
        xmlBuilder.append("<path>");
        xmlBuilder.append(tfInputFile.getText());
        xmlBuilder.append("</path>");
        xmlBuilder.append("<parseType>");
        xmlBuilder.append(cbInputType.getSelectedItem());
        xmlBuilder.append("</parseType>");
        xmlBuilder.append("</input>");
        // Component List
        xmlBuilder.append("<components>");
        for (Object obj : componentListModel.toArray())
        {
            Component component = (Component) obj;
            xmlBuilder.append("<component>");
            xmlBuilder.append("<loadType>");
            xmlBuilder.append(component.getLoadType());
            xmlBuilder.append("</loadType>");
            xmlBuilder.append("<type>");
            xmlBuilder.append(component.getType());
            xmlBuilder.append("</type>");
            xmlBuilder.append("<path>");
            xmlBuilder.append(component.getPath());
            xmlBuilder.append("</path>");
            if (!Strings.isNullOrEmpty(component.getDependencyPath()))
            {
                xmlBuilder.append("<dependencyPath>");
                xmlBuilder.append(component.getPath());
                xmlBuilder.append("</dependencyPath>");
            }
            xmlBuilder.append("<className>");
            xmlBuilder.append(component.getClassName());
            xmlBuilder.append("</className>");
            xmlBuilder.append("</component>");
        }
        xmlBuilder.append("</components>");
        // Output Type
        xmlBuilder.append("<output>");
        f = new File(tfOutputFile.getText());
        if (f.exists() && f.isDirectory())
        {
            xmlBuilder.append("<type>Folder</type>");
        }
        else
        {
            xmlBuilder.append("<type>File</type>");
        }
        xmlBuilder.append("<path>");
        xmlBuilder.append(tfOutputFile.getText());
        xmlBuilder.append("</path>");
        xmlBuilder.append("<generateType>");
        xmlBuilder.append(cbOutputType.getSelectedItem());
        xmlBuilder.append("</generateType>");
        xmlBuilder.append("</output>");
        xmlBuilder.append("</config>");
        return xmlBuilder.toString();
    }
}
