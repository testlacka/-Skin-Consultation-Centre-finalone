package clinic;
import java.io.Serial;
import java.io.Serializable;

public class Doctor extends Person implements Comparable<Doctor>, Serializable {

    private int medicalLicenceNumber;
    private String specializedField;

    @Serial
    private static final long serialNoVersionuID = 1l;

    public int getMedicalLicenceNumber() {
        return medicalLicenceNumber;
    }

    public void setMedicalLicenceNumber(int medicalLicenceNumber) {
        this.medicalLicenceNumber = medicalLicenceNumber;
    }

    public String getSpecialisation() {
        return specializedField;
    }

    public void setSpecialisation(String specialisation) {
        this.specializedField = specialisation;
    }

    // Create a comparable for doctor list sorting to Method
    @Override

    public int compareTo(Doctor o) {
        int result = this.getSurname().compareTo(o.getSurname());
        return Integer.compare(result, 0);
    }
}
