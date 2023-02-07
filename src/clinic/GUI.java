package clinic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.crypto.*;
import java.util.*;

public class GUI extends WestminsterSkinConsultationManager implements ActionListener {

    private static BufferedImage globalImage;

    //setup array for encrypted image store
    byte[] encryptedImageData;

    //key Encryption process
    SecretKey key;

    // GUI Interface
    public GUI() {

        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        try{
            int width = 400;
            int height = 200;
        }catch (Exception ignored){
        }
   JButton checkDoctors = new JButton("View the Doctors List");
        JButton sortButton = new JButton("Sort the Doctor List Alphabetically");
        JButton consultationButton = new JButton("Add a Consultation");
        JButton showConsultation = new JButton("View Consultations");

        sortButton.setBackground(Color.PINK);
        checkDoctors.setBackground(Color.YELLOW);
        showConsultation.setBackground(Color.LIGHT_GRAY);
        consultationButton.setBackground(Color.GRAY);

        consultationButton.addActionListener(e -> addConsultation());

        showConsultation.addActionListener(e -> showConsultations());

        checkDoctors.addActionListener(this);
        sortButton.addActionListener(e -> sortedDoctorList());



        panel.setBorder(BorderFactory.createEmptyBorder(150, 100, 100, 100));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(checkDoctors);
        panel.add(sortButton);
        panel.add(consultationButton);
        panel.add(showConsultation);
        panel.setPreferredSize(new Dimension(500,500));
        frame.add(panel, BorderLayout.PAGE_START);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Skin Consultation Clinic System");
        frame.setSize(1000, 700);
        frame.pack();
        frame.setVisible(true);
    }


    //Creating a Table to display the Doctors
    @Override
    public void actionPerformed(ActionEvent e) {
        doctorListWindow();
    }
    public void doctorListWindow() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Date of Birth");
        model.addColumn("Mobile Number");
        model.addColumn("Specialisation");
        model.addColumn("Medical License Number");
        for (Doctor doctor : doctorList) {
            model.addRow(new Object[]{doctor.getName(), doctor.getSurname(), doctor.getDateOfBirth(), doctor.getMobileNumber(), doctor.getSpecialisation(), doctor.getMedicalLicenceNumber()});
        }
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        JFrame newF1 = new JFrame("Doctor List");
        table.setEnabled(false);
        newF1.add(scrollPane, BorderLayout.CENTER);
        newF1.setSize(1000, 720);
        newF1.setVisible(true);
    }
    //Window for sorted the doctor list
    public void sortedDoctorList() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Date of Birth");
        model.addColumn("Mobile Number");
        model.addColumn("Specialisation");
        model.addColumn("Medical License Number");
        Collections.sort(doctorList);
        for (Doctor doctor : doctorList) {
            model.addRow(new Object[]{doctor.getName(), doctor.getSurname(), doctor.getDateOfBirth(), doctor.getMobileNumber(), doctor.getSpecialisation(), doctor.getMedicalLicenceNumber()});
        }
        JTable table = new JTable(model);
        table.getSelectedColumn();
        JScrollPane scrollPane = new JScrollPane(table);
        JFrame f2 = new JFrame("Doctor sorted List");
        table.setEnabled(false);
        f2.add(scrollPane, BorderLayout.CENTER);
        f2.setSize(1000, 720);
        f2.setVisible(true);
    }
    //Creating the dropdown menu for consultation
    public void addConsultation() {

        // create the dropdown menu
        JComboBox<String> dropdown = new JComboBox<>();
        for (Doctor doctor : doctorList) {
            dropdown.addItem(doctor.getName() + " " + doctor.getSurname());
        }
    // label creation
        JLabel label = new JLabel("Select a Doctor: ");
        // button creation
        JButton button = new JButton("Consult");

        // create a panel to hold the components
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(dropdown);
        panel.add(button);

        // create the frame and add the panel
        JFrame frame = new JFrame("Add Consultation");
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        //Method to close only the frame after consultation
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Perform any necessary cleanup
                // frame closing
                frame.dispose();
            }
        });

        //Consult button Action - Consulting
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame j1 = new JFrame("Enter Appointment Details");
                JPanel p1 = new JPanel(new FlowLayout(FlowLayout.TRAILING));

                Date initialDate = new Date();
                Date minimumDate = new Date(0);
                Date maximumDate = new Date(Long.MAX_VALUE);
                SpinnerDateModel dateModel = new SpinnerDateModel(initialDate, minimumDate, maximumDate, Calendar.MINUTE);

                //  SpinnerNumberModel
                SpinnerNumberModel numberModel = new SpinnerNumberModel(0.0, 0.0, null, 0.2);

                //  JSpinner with the SpinnerDateModel
                JSpinner spinner = new JSpinner(dateModel);

                JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "DD-MM-YYYY HH:mm");
                spinner.setEditor(editor);
                //  JSpinner with the SpinnerNumberModel
                JSpinner costSpinner = new JSpinner(numberModel);

                JTextField noteField = new JTextField();
              JLabel l1 = new JLabel("Select the Date & Time: ");
                JLabel l2 = new JLabel("Select the Cost (£): ");
                JLabel l3 = new JLabel("Note: ");
                JButton b1 = new JButton("Submit");
                b1.setEnabled(false);
                JLabel l4 = new JLabel("Patients Name: ");
                JTextField nameField = new JTextField();
                JLabel l5 = new JLabel("Patients Surname: ");
                JTextField surnameField = new JTextField();
                JLabel l6 = new JLabel("Patients Date of Birth: ");
                JTextField dobField = new JTextField();
                JLabel l7 = new JLabel("Patients Mobile Number: ");
                JTextField numberField = new JTextField();
                JLabel l8 = new JLabel("Patient ID: ");
                JTextField patientField = new JTextField();

                //make suer to mobile no and patient id to be numbers
                ((AbstractDocument) numberField.getDocument()).setDocumentFilter(new DocumentFilter() {
                    @Override
                    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                            throws BadLocationException {
                        // Only allow integer input
                        if (string.matches("^\\d+$")) {
                            super.insertString(fb, offset, string, attr);
                        }
                    }
                    @Override
                    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                            throws BadLocationException {
                        // allow integer input
                        if (text.matches("^\\d+$")) {
                            super.replace(fb, offset, length, text, attrs);
                        }
                    }
                });
                ((AbstractDocument) patientField.getDocument()).setDocumentFilter(new DocumentFilter() {
                    @Override
                    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                            throws BadLocationException {
                        if (string.matches("^\\d+$")) {
                            super.insertString(fb, offset, string, attr);
                        }
                    }
                    @Override
                    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                            throws BadLocationException {
                        // Only allow integer input
                        if (text.matches("^\\d+$")) {
                            super.replace(fb, offset, length, text, attrs);
                        }
                    }
                });
                j1.setPreferredSize(new Dimension(1500, 800));
                p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));

                //Image uploading option
                JButton uploadButton = new JButton("Upload Images");
                uploadButton.addActionListener(e1 -> {
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        try {
                            globalImage = ImageIO.read(selectedFile);
                            ByteArrayOutputStream baosi = new ByteArrayOutputStream();
                            ImageIO.write(globalImage, "jpg", baosi);
                            baosi.flush();
                            byte[] imageData = baosi.toByteArray();
                            baosi.close();
                            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                            keyGenerator.init(128); // set key size to 128 bits
                            key = keyGenerator.generateKey();

                            cipher.init(Cipher.ENCRYPT_MODE, key);
                            encryptedImageData = cipher.doFinal(imageData);

                        } catch (IOException | NoSuchPaddingException | IllegalBlockSizeException |
                                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                p1.add(l1);
                p1.add(spinner);
                p1.add(l2);
                p1.add(costSpinner);
                p1.add(l3);
                p1.add(noteField);
                p1.add(uploadButton, BorderLayout.EAST);
                p1.add(new JSeparator(SwingConstants.HORIZONTAL));
                j1.add(p1);
                p1.add(l4);
                p1.add(nameField);
                p1.add(l5);
                p1.add(surnameField);
                p1.add(l6);
                p1.add(dobField);
                p1.add(l7);
                p1.add(numberField);
                p1.add(l8);
                p1.add(patientField);
                p1.add(b1);
                j1.pack();
                j1.setVisible(true);
                //to close only the frame
                j1.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {

                        //frame closing
                        j1.dispose();
                    }
                });

                //Ensure that all required fields are filled out before allowing the submit button.
                DocumentListener listener = new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        checkFields();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        checkFields();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        // NA
                    }

                    private void checkFields() {
                        b1.setEnabled(!nameField.getText().isEmpty() && !surnameField.getText().isEmpty() && !dobField.getText().isEmpty() && !numberField.getText().isEmpty() && !patientField.getText().isEmpty());
                    }
                };

                //  DocumentListener to the text fields
                nameField.getDocument().addDocumentListener(listener);
                surnameField.getDocument().addDocumentListener(listener);
                dobField.getDocument().addDocumentListener(listener);
                numberField.getDocument().addDocumentListener(listener);
                patientField.getDocument().addDocumentListener(listener);

                //Adding the Consultation
                b1.addActionListener(e12 -> {
                    Date selectedDate = (Date) spinner.getValue();
                    double selectedValue = ((Number) costSpinner.getValue()).doubleValue();
                    String notes = noteField.getText();
                    for (int i = 0; i <= consultations.size(); i++) {
                        if (Objects.requireNonNull(dropdown.getSelectedItem()).toString().contains(doctorList.get(i).getName())) {
                            if ( consultations.isEmpty()) {
                                Consultation c1 = new Consultation();
                                c1.setCost(selectedValue);
                                c1.setNotes(notes);
                                c1.setDoctor(doctorList.get(i));
                                c1.setName(nameField.getText());
                                c1.setSurname(surnameField.getText());
                                c1.setDateOfBirth(dobField.getText());
                                c1.setMobileNumber(Integer.parseInt(numberField.getText()));
                                c1.setPatientId(Integer.parseInt(patientField.getText()));
                                c1.setConsulationDateAndTime(selectedDate);
                                try{
                                    c1.setEncryptedImageArray(encryptedImageData);
                                    encryptedImageData = null;
                                    consultations.add(c1);
                                    JOptionPane.showMessageDialog(null, "Consultation Added with Doctor " + doctorList.get(i).getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
                                    j1.dispose();
                                }catch (Exception n){
                                    consultations.add(c1);
                                    JOptionPane.showMessageDialog(null, "Consultation Added with Doctor " + doctorList.get(i).getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
                                    j1.dispose();
                                }
                            }
                            else if (!(consultations.get(i).getConsulationDateAndTime().equals(selectedDate))){
                                Consultation c1 = new Consultation();
                                c1.setCost(selectedValue);
                                c1.setNotes(notes);
                                c1.setDoctor(doctorList.get(i));
                                c1.setName(nameField.getText());
                                c1.setSurname(surnameField.getText());
                                c1.setDateOfBirth(dobField.getText());
                                c1.setMobileNumber(Integer.parseInt(numberField.getText()));
                                c1.setPatientId(Integer.parseInt(patientField.getText()));
                                c1.setConsulationDateAndTime(selectedDate);
                                c1.setEncryptedImageArray(encryptedImageData);
                                encryptedImageData = null;
                                consultations.add(c1);
                                JOptionPane.showMessageDialog(null, "Consultation Added with Doctor " + doctorList.get(i).getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
                                j1.dispose();
                            }
                            else {
                                Random rng = new Random();
                                ArrayList<Doctor> excludedDoctorList = (ArrayList<Doctor>) doctorList.clone();
                                excludedDoctorList.remove(i);
                                if (excludedDoctorList.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Doctor " + doctorList.get(i).getName() + " isn't available at the mentioned time, unable to find any other Doctors at this time", "Sorry", JOptionPane.INFORMATION_MESSAGE);
                                    j1.dispose();
                                } else {
                                    int index = rng.nextInt(excludedDoctorList.size());
                                    Doctor newDoctor = excludedDoctorList.get(index);
                                    JOptionPane.showMessageDialog(null, "Doctor " + doctorList.get(i).getName() + " is not available at that time, consultation will be scheduled with Doctor " + newDoctor.getName(), "Alert", JOptionPane.INFORMATION_MESSAGE);
                                    Consultation c1 = new Consultation();
                                    c1.setCost(selectedValue);
                                    c1.setNotes(notes);
                                    c1.setDoctor(newDoctor);
                                    c1.setName(nameField.getText());
                                    c1.setSurname(surnameField.getText());
                                    c1.setDateOfBirth(dobField.getText());
                                    c1.setMobileNumber(Integer.parseInt(numberField.getText()));
                                    c1.setPatientId(Integer.parseInt(patientField.getText()));
                                    c1.setConsulationDateAndTime(selectedDate);
                                    c1.setEncryptedImageArray(encryptedImageData);
                                    encryptedImageData = null;
                                    consultations.add(c1);
                                    j1.dispose();
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    //Window to Show the Consultation Details
    public void showConsultations() {

        JFrame f = new JFrame("Consultations");
        JPanel p = new JPanel();

        for (int x=0;x<consultations.size();x++){

            JButton b1 = new JButton(consultations.get(x).getName()+" - "+consultations.get(x).getConsulationDateAndTime());

            int finalX = x;
            b1.addActionListener(e -> {
                JFrame f1 = new JFrame("Details of "+consultations.get(finalX).getName()+"'s Appointment");
                JPanel p1 = new JPanel();

                JLabel l3 = new JLabel("Doctor's Name:- ");
                JTextArea tArea0 = new JTextArea(consultations.get(finalX).getDoctor().getName()+" "+consultations.get(finalX).getDoctor().getSurname());
                tArea0.setEditable(false);
                JLabel l4 = new JLabel("Patient's Name:- ");
                JTextArea tArea1 = new JTextArea(consultations.get(finalX).getName());
                tArea1.setEditable(false);
                JLabel l5 = new JLabel("Patient's Surname:- ");
                JTextArea tArea2 = new JTextArea(consultations.get(finalX).getSurname());
                tArea2.setEditable(false);
                JLabel l6 = new JLabel("Patient's Date of Birth:- ");
                JTextArea tArea3 = new JTextArea(consultations.get(finalX).getDateOfBirth());
                tArea3.setEditable(false);
                JLabel l7 = new JLabel("Patient's Mobile Number:- ");
                JTextArea tArea4 = new JTextArea(String.valueOf(consultations.get(finalX).getMobileNumber()));
                tArea4.setEditable(false);
                JLabel l8 = new JLabel("Patient ID:- ");
                JTextArea tArea5 = new JTextArea(String.valueOf(consultations.get(finalX).getPatientId()));
                tArea5.setEditable(false);
                JLabel l9 = new JLabel("Scheduled Date and Time:- ");
                JTextArea tArea6 = new JTextArea(String.valueOf(consultations.get(finalX).getConsulationDateAndTime()));
                tArea6.setEditable(false);
                JLabel l10 = new JLabel("Appointment Cost:- ");
                JTextArea tArea7 = new JTextArea(String.valueOf(consultations.get(finalX).getCost()));
                tArea7.setEditable(false);
                JLabel l11 = new JLabel("Appointment Notes:- ");
                JTextArea tArea8 = new JTextArea(consultations.get(finalX).getNotes());
                tArea8.setEditable(false);
                try{
                    if (consultations.get(finalX).getEncryptedImageArray() != null){
                        JButton showImage = new JButton("View Images");
                        p1.add(showImage);
                        showImage.addActionListener(e1 -> {
                            JFrame f11 = new JFrame();
                            JPanel p11 = new JPanel();
                            f11.add(p11);
                            int width = 500;
                            int height = 500;
                       Cipher cipher;
                            try {
                                cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                            } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
                                throw new RuntimeException(ex);
                            }
                            try {
                                cipher.init(Cipher.DECRYPT_MODE, key);
                            } catch (InvalidKeyException ex) {
                                throw new RuntimeException(ex);
                            }
                            try {
                                byte[] decryptedImageData = cipher.doFinal(consultations.get(finalX).getEncryptedImageArray());
                                InputStream in = new ByteArrayInputStream(decryptedImageData);
                                BufferedImage image = ImageIO.read(in);

                                // resize the image
                                BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                                Graphics2D g = resizedImage.createGraphics();
                                g.drawImage(image, 0, 0, width, height, null);
                                g.dispose();

                                // create the JLabel with the resized image
                                JLabel label = new JLabel(new ImageIcon(resizedImage));
                                label.setPreferredSize(new Dimension(width, height));
                                ImageIcon imageIcon = new ImageIcon(resizedImage);
                                label.setIcon(imageIcon);

                                p11.add(label);
                                f11.pack();
                                f11.setVisible(true);
                            } catch (IllegalBlockSizeException | BadPaddingException | IOException ex) {
                                throw new RuntimeException(ex);
                            }

                        });
                    }
                }catch (Exception ignored){
                }
                p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
                f1.add(p1);
                p1.add(l3);
                p1.add(tArea0);
                p1.add(l4);
                p1.add(tArea1);
                p1.add(l5);
                p1.add(tArea2);
                p1.add(l6);
                p1.add(tArea3);
                p1.add(l7);
                p1.add(tArea4);
                p1.add(l8);
                p1.add(tArea5);
                p1.add(l9);
                p1.add(tArea6);
                p1.add(l10);
                p1.add(tArea7);
                p1.add(l11);
                p1.add(tArea8);
                f1.pack();
                f1.setVisible(true);
            });
            p.setPreferredSize(new Dimension(500,300));
            p.add(b1);
        }
        f.setPreferredSize(new Dimension(500, 300));
        f.add(p);
        f.pack();
        f.setVisible(true);
    }

}