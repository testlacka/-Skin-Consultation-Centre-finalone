package clinic;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
public class Consultation extends Patient implements Serializable {

    @Serial
    private static final long serialNoVersionuID = 1l;

    static ArrayList<Consultation> consultations = new ArrayList<>();

    public byte[] getEncryptedImageArray() {
        return encryptedImageArray;
    }

    public void setEncryptedImageArray(byte[] encryptedImageArray) {
        this.encryptedImageArray = encryptedImageArray;
    }

    byte[] encryptedImageArray;

    private Doctor dr;
    private Date ConsulationDateAndTime;
    private double cost;
    private String notes;
    public Doctor getDoctor() {
        return dr;
    }

        public void setDoctor(Doctor dr) {
            this.dr = dr;
    }
            public Date getConsulationDateAndTime() {
                return ConsulationDateAndTime;

    }
            public void setConsulationDateAndTime(Date consulationDateAndTime) {
                ConsulationDateAndTime = consulationDateAndTime;
    }
                public double getCost () {
                    return cost;
    }

                public void setCost ( double cost){
                    this.cost = cost;
    }

                public String getNotes () {
                    return notes;
    }

                public void setNotes (String notes){
                    this.notes = notes;
    }
}
