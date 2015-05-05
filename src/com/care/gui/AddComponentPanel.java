package com.care.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.care.datatype.Component;
import com.care.datatype.ComponentLoadType;
import com.care.datatype.ComponentType;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class AddComponentPanel extends JPanel
{
    private JTextField tfClassPath;
    private JTextField tfJarPath;
    private JComboBox cbLoadType;
    private JComboBox cbComponentType;
    final JFileChooser fc = new JFileChooser();
    private ImageIcon folder = new ImageIcon(getClass().getResource("folder.png"));
    final ComponentLoadType[] componentLoadSupport = ComponentLoadType.values();
    final ComponentType[] componentTypeSupport = ComponentType.values();
    private JTextField tfClassName;
    private JFrame parentFrame;

    /**
     * Create the panel.
     * 
     * @param frame
     */
    public AddComponentPanel(JFrame frame)
    {
        parentFrame = frame;
        setLayout(new FormLayout(new ColumnSpec[] {
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("default:grow"),
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.GLUE_COLSPEC,
                FormFactory.UNRELATED_GAP_COLSPEC,},
            new RowSpec[] {
                FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,}));

        JLabel lblClassPath = new JLabel("Class Path");
        add(lblClassPath, "2, 2");

        tfClassPath = new JTextField();
        add(tfClassPath, "6, 2, 19, 1, fill, default");
        tfClassPath.setColumns(10);

        JButton btnClassPath = new JButton(folder);
        btnClassPath.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal = fc.showOpenDialog(parentFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = fc.getSelectedFile();
                    tfClassPath.setText(file.getAbsolutePath());
                }
                else
                {
                    System.out.println("Open command cancelled by user.");
                }
            }
        });
        add(btnClassPath, "26, 2");

        JLabel lblJarPath = new JLabel("Jar Path");
        add(lblJarPath, "2, 4");

        tfJarPath = new JTextField();
        add(tfJarPath, "6, 4, 19, 1, fill, default");
        tfJarPath.setColumns(10);

        JButton btnJarPath = new JButton(folder);
        btnJarPath.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnVal = fc.showOpenDialog(parentFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = fc.getSelectedFile();
                    tfJarPath.setText(file.getAbsolutePath());
                }
                else
                {
                    System.out.println("Open command cancelled by user.");
                }
            }
        });
        add(btnJarPath, "26, 4");

        JLabel lblClassName = new JLabel("Class Name");
        add(lblClassName, "2, 6");

        tfClassName = new JTextField();
        add(tfClassName, "6, 6, 19, 1, fill, default");
        tfClassName.setColumns(10);

        JLabel lblLoadType = new JLabel("Load Type");
        add(lblLoadType, "2, 8");

        cbLoadType = new JComboBox(componentLoadSupport);
        add(cbLoadType, "6, 8, 19, 1, fill, default");

        JLabel lblComponentType = new JLabel("Component Type");
        add(lblComponentType, "2, 10");

        cbComponentType = new JComboBox(componentTypeSupport);
        add(cbComponentType, "6, 10, 19, 1, fill, default");
    }

    public Component getComponent()
    {
        Component component = new Component();
        component.setClassName(tfClassName.getText());
        component.setLoadType((ComponentLoadType) cbLoadType.getSelectedItem());
        component.setType((ComponentType) cbComponentType.getSelectedItem());
        if (component.getLoadType() == ComponentLoadType.CLASS)
        {
            component.setPath(tfClassPath.getText());
            component.setDependencyPath(tfJarPath.getText());
        }
        else
        {
            component.setPath(tfJarPath.getText());
        }
        return component;
    }
}
