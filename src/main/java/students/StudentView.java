package students;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionListener;

public class StudentView extends JFrame {
    // Components of the View
    private Container container;
    private JTextField studentIdField;
    private JTextField nameField;
    private JTextField addressField;
    private JComboBox streamField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField searchField;
    private JButton searchButton;
    private JButton refreshButton;
    private JTable studentTable;
    
    private StudentModel studentModel;

    public StudentView(StudentModel studentModel) {
    	this.studentModel = studentModel;
    	
        setTitle("Student Information");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        
        // Panel for the form fields and buttons
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        
        JLabel titleLabel = new JLabel("Student Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(150,10,280,20);
        formPanel.add(titleLabel);
        
        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdLabel.setBounds(50,50,100,30);
        formPanel.add(studentIdLabel);
        
        studentIdField = new JTextField();
        studentIdField.setBounds(150,50,200,30);
        formPanel.add(studentIdField);
        
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50,100,100,30);
        formPanel.add(nameLabel);
        
        nameField = new JTextField();
        nameField.setBounds(150,100,200,30);
        formPanel.add(nameField);
        
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50,150,100,30);
        formPanel.add(addressLabel);
        
        addressField = new JTextField();
        addressField.setBounds(150,150,200,30);
        formPanel.add(addressField);
        
        JLabel streamLabel = new JLabel("Stream:");
        streamLabel.setBounds(50,200,100,30);
        formPanel.add(streamLabel);
        
     // array of string containing streams
        String stream[] = {"BIM","BHM","BBS"};
        streamField = new JComboBox(stream);
        streamField.setBounds(150,200,200,30);
        formPanel.add(streamField);
        
        
        addButton = new JButton("Add Student");
        addButton.setBounds(380,70,150,30);
        formPanel.add(addButton);
        
        updateButton = new JButton("Update Student");
        updateButton.setBounds(380,130,150,30);
        formPanel.add(updateButton);
        
        deleteButton = new JButton("Delete Student");
        deleteButton.setBounds(380,180,150,30);
        formPanel.add(deleteButton);
        
        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        separator1.setBackground(Color.black);
        separator1.setBounds(140,30,280,10);
        formPanel.add(separator1);
        /*
        JSeparator separator2 = new JSeparator(SwingConstants.VERTICAL);
        separator2.setBackground(Color.black);
        separator2.setBounds(360,40,10,200);
        formPanel.add(separator2);
        */
        JSeparator separator3 = new JSeparator(SwingConstants.HORIZONTAL);
        separator3.setBackground(Color.black);
        separator3.setBounds(0,260,600,10);
        formPanel.add(separator3);
        
        searchField = new JTextField();
        searchField.setBounds(50,280,200,30);
        formPanel.add(searchField);
        
        searchButton = new JButton("Search");
        searchButton.setBounds(280,280,100,30);
        formPanel.add(searchButton);
        
        refreshButton = new JButton("Refresh Table");
        refreshButton.setBounds(400,280,150,30);
        formPanel.add(refreshButton);
        

     // Table to display student information
        String[] columnNames = {"Student ID", "Name", "Address", "Stream"};
        Object[][] data = new Object[0][4]; 
        studentTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setPreferredSize(new Dimension(0,-80));
        
        // Main container and set BorderLayout
        container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
      
        // Add the Panel to the Container
        container.add(formPanel);
        container.add(scrollPane);
    }

    public void addAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addUpdateButtonListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }

    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public void addRefreshButtonListener(ActionListener listener) {
        refreshButton.addActionListener(listener);
    }

    // Getters for the form fields to retrieve student information
    public JTextField getStudentIdField() {
        return studentIdField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getAddressField() {
        return addressField;
    }

    public JComboBox getStreamField() {
        return streamField;
    }
    
    public JTextField getSearchField() {
    	return searchField;
    }

    // Method to update the JTable with student information
    public void updateStudentTable(Object[][] data) {
        DefaultTableModel model = new DefaultTableModel(data, new String[]{"Student ID", "Name", "Address", "Stream"});
        studentTable.setModel(model);
    }
    
 // Method to update the JTable with student information from the database
    public void updateStudentTableFromDatabase() {
        Object[][] data = studentModel.getAllStudentsData();
        
        // Update the JTable with the retrieved data
        DefaultTableModel model = new DefaultTableModel(data, new String[]{"Student ID", "Name", "Address", "Stream"});
        studentTable.setModel(model);
    }

}
