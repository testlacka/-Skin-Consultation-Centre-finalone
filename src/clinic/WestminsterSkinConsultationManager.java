package clinic;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class WestminsterSkinConsultationManager extends Consultation implements SkinConsultationManager {
    public static void main(String[] args) {
        WestminsterSkinConsultationManager obj = new WestminsterSkinConsultationManager();

        //By data file reading

        try {
            obj.readData();
        } catch (IOException ignored) {

        }

        boolean user_con = true;
        //ConsoleMenu Creation
        while (user_con) {

            // creation of consultation system with exception handling
            try {
                System.out.println();
                System.out.println("Consultation System");
                System.out.println();
                System.out.println("Please enter the relevant number ");
                System.out.println("1:- Add a New Doctor");
                System.out.println("2:- Delete a Doctor ");
                System.out.println("3:- Print the list of doctors");
                System.out.println("4:- Save details in a File");
                System.out.println("5:- View Consultations");
                System.out.println("6:- Cancel a Consultation");
                System.out.println("7:- GUI View");
                System.out.println("8:- Exit ");
                System.out.println();

                Scanner s2 = new Scanner(System.in);
                System.out.print("Enter the selection: ");
                int selectedOption = s2.nextInt();
                switch (selectedOption) {
                    case 1 -> obj.addDoctor();
                    case 2 -> obj.doctordelete();
                    case 3 -> obj.printDoctors();
                    case 4 -> obj.saveDetails();
                    case 5 -> consoleShowConsultations();
                    case 6 -> cancelConsultations();
                    case 7 -> new GUI();
                    case 8 -> user_con = false;
                    default -> System.out.println("Invalid Choice, Please try again");
                }
            } catch (Exception ignored) {
            }
        }
    }

    //A new Doctor add a system by following method
    @Override
    public void addDoctor() {
        boolean userConsent = true;
        while (userConsent) {
            if (doctorList.size() >= 10) {
                System.out.println("The list of doctors is complete.");
                break;
            } else {
                Doctor doctor = new Doctor();
                Scanner s1 = new Scanner(System.in);
                System.out.println("Enter the Doctor's First Name: ");
                String firstName = s1.nextLine();
                doctor.setName(firstName);
                System.out.println("Enter Doctor's Last Name: ");
                String Lastname = s1.nextLine();
                doctor.setSurname(Lastname);
                System.out.println("Enter Doctor's Birth Date (DD-MM-YYYY): ");
                String DOB = s1.nextLine();
                doctor.setDateOfBirth(DOB);
                System.out.println("Enter Doctor's Mobile Number: ");
                int mobileNumber = s1.nextInt();
                doctor.setMobileNumber(mobileNumber);
                System.out.println("Enter Doctor's Specialisation: ");
                s1.nextLine();
                String spec = s1.nextLine();
                doctor.setSpecialisation(spec);
                System.out.println("Enter Doctor's Medical Licence Number: ");
                int MLN = s1.nextInt();
                doctor.setMedicalLicenceNumber(MLN);
                System.out.println("Make sure below details to add the user: ");
                System.out.println("First Name: " + firstName);
                System.out.println("Surname: " + Lastname);
                System.out.println("Date of Birth: " + DOB);
                System.out.println("Mobile Number: " + mobileNumber);
                System.out.println("Specialisation: " + spec);
                System.out.println("Medical License Number: " + MLN);
                System.out.println();
                System.out.println("Enter Y to confirm and N to re-enter the details");
                s1.nextLine();
                String confirmation = s1.nextLine();
                if (confirmation.equalsIgnoreCase("Y")) {
                    doctorList.add(doctor);
                    System.out.println("Doctor has been added to the System");
                } else if (confirmation.equalsIgnoreCase("N")) {
                    continue;
                }
                Scanner s3 = new Scanner(System.in);
                System.out.print("Do you want to add another Dr. ? ( Yes / No ): ");
                String userInput = s3.nextLine();
                if (userInput.equalsIgnoreCase("No")) {
                    userConsent = false;
                } else if (userInput.equalsIgnoreCase("Yes")) {
                    userConsent = true;

                } else {
                    System.out.println("Invalid Choice");
                    break;
                }
            }
        }
    }

    // A doctor delete from the system by following method

    @Override
    public void doctordelete() {
        System.out.println("Enter the Doctor's Medical License Number: ");
        Scanner s4 = new Scanner(System.in);
        int MLN = s4.nextInt();
        boolean numCheck = true;
        while (numCheck) {
            for (int a = 0; a < doctorList.size(); a++) {
                if (doctorList.get(a).getMedicalLicenceNumber() == MLN) {
                    String nameOfRemoval = doctorList.get(a).getName();
                    System.out.println("Do you want to delete Dr. " + nameOfRemoval + " from the List ? (Y/N)");
                    s4.nextLine();
                    String confirmation = s4.nextLine();
                    if (confirmation.equalsIgnoreCase("y")) {
                        System.out.println("Below doctor is removed from the list: ");
                        System.out.println("Dr's Name: " + doctorList.get(a).getName());
                        System.out.println("Dr's Last Name: " + doctorList.get(a).getSurname());
                        System.out.println("Dr's Date of Birth: " + doctorList.get(a).getDateOfBirth());
                        System.out.println("Dr's Mobile Number: " + doctorList.get(a).getMobileNumber());
                        System.out.println("Dr's Specialisation: " + doctorList.get(a).getSpecialisation());
                        System.out.println("Dr's Medical License Number: " + doctorList.get(a).getMedicalLicenceNumber());
                        doctorList.remove(a);
                        System.out.println();
                        System.out.println("Total Count of Doctors in the Centre: " + doctorList.size());
                        numCheck = false;
                    } else if (confirmation.equalsIgnoreCase("n")) {
                        numCheck = false;
                    } else {
                        System.out.println("Please enter a valid input");
                    }

                } else if ((doctorList.get(a).getMedicalLicenceNumber() != MLN) && (doctorList.size() == a - 1)) {
                    System.out.println("Medical License Number is not equal to doctor");
                    numCheck = false;
                } else if (doctorList.get(a).getMedicalLicenceNumber() != MLN) {
                    continue;
                }
            }
        }
    }

    //print the list of doctors by following method
    @Override
    public void printDoctors() {
        System.out.println();
        if (doctorList.isEmpty()) {
            System.out.println("The Doctor List is null");
            System.out.println();
        } else {
            Collections.sort(doctorList);
            System.out.println("The List of Doctors: ");
            System.out.println();
            for (int b = 0; b <= doctorList.size() - 1; b++) {
                System.out.println("Dr No: " + (b + 1));
                System.out.println("Dr's Name: " + doctorList.get(b).getName());
                System.out.println("Dr's Last Name: " + doctorList.get(b).getSurname());
                System.out.println("Dr's Date of Birth: " + doctorList.get(b).getDateOfBirth());
                System.out.println("Dr's Mobile Number: " + doctorList.get(b).getMobileNumber());
                System.out.println("Dr's Specialisation: " + doctorList.get(b).getSpecialisation());
                System.out.println("Dr's Medical License Number: " + doctorList.get(b).getMedicalLicenceNumber());
                System.out.println();
            }
        }
    }

    // save the system data to a File by following method
    @Override
    public void saveDetails() {

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("saveData.txt"))) {
            out.writeObject(doctorList);
            out.writeObject(consultations);
            out.flush();
            System.out.println("Data stored in file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //get data from the file to the system by following method
    void readData() throws IOException {

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("saveData.txt"))) {
            ArrayList<Doctor> doctorReadList = (ArrayList<Doctor>) in.readObject();
            doctorList.addAll(doctorReadList);

            ArrayList<Consultation> consultationReadList = (ArrayList<Consultation>) in.readObject();
            consultations.addAll(consultationReadList);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ClassCastException ignored) {

        }
    }

    // cancel consultations by following method
    static void cancelConsultations() {

        if (consultations.isEmpty()) {
            System.out.println("No any Consultations Booked");
        } else {
            Scanner s1 = new Scanner(System.in);
            System.out.println("Enter the Patient ID: ");
            int patientID = s1.nextInt();

            for (int x = 0; x < consultations.size(); x++) {

                if (consultations.get(x).getPatientId() == patientID) {
                    System.out.println("Patient " + consultations.get(x).getName() + "'s Consultation Cancelled");
                    consultations.remove(x);
                } else {
                    System.out.println("Mentioned Details is not equal to consultation");
                }
            }
        }
    }
        static void consoleShowConsultations () {
            if (consultations.isEmpty()) {
                System.out.println("No Consultations");
            } else {
                for (Consultation consultation : consultations) {
                    System.out.print("Doctors Name: ");
                    System.out.println(consultation.getDoctor().getName() + " " + consultation.getDoctor().getSurname());
                    System.out.print("Patient's Name: ");
                    System.out.println(consultation.getName() + " " + consultation.getSurname());
                    System.out.println("Patient's Date of Birth: ");
                    System.out.println(consultation.getDateOfBirth());
                    System.out.println("Patient's Mobile Number: ");
                    System.out.println(consultation.getMobileNumber());
                    System.out.println("Patient ID: ");
                    System.out.println(consultation.getPatientId());
                    System.out.println("Scheduled Date and Time: ");
                    System.out.println(consultation.getConsulationDateAndTime());
                    System.out.println("Costs: ");
                    System.out.println(consultation.getCost());
                    System.out.println("Texts: ");
                    System.out.println(consultation.getNotes());
                    System.out.println();
                }

            }

        }

    }


