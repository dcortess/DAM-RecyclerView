package cat.udl.tidic.amd.dam_recyclerview.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "event_table")
public class Event {

    // Propietats
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int id;
    @SerializedName("userId")
    private int userId;
    @SerializedName("start")
    private String start;
    @SerializedName("end")
    private String end;
    @SerializedName("tittle")
    private String tittle;
    @SerializedName("description")
    private String description;
    @SerializedName("avaluation")
    private float avaluation;


    // Constructor
    public Event(int userId, String start, String end, String tittle, String description,
    float avaluation) {
        this.userId = userId;
        this.start = start;
        this.end = end;
        this.tittle = tittle;
        this.description = description;
        this.avaluation = avaluation;
    }

    // Getters & setters
    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public String getDescription() {
        return description;
    }

    public float getAvaluation() {
        return avaluation;
    }



    @Override
    public boolean equals(Object o) {

        // Si es compara amb ell mateix retorna true
        if (o == this) {
            return true;
        }

        /* Comprova si és una instància de Complex o no
          "null instanceof [type]" també retorna false */
        if (!(o instanceof Event)) {
            return false;
        }

        // Per a que poguem comparar membres, copiem
        Event e = (Event) o;

        // Compara els membres de la taula i retorna en conseqüència
        return this.id == e.getId()
                && this.userId == e.getUserId()
                && this.start.equals(e.getStart())
                && this.end.equals(e.getEnd())
                && this.avaluation == e. getAvaluation()
                && this.tittle.equals(e.getTittle())
                && this.description.equals(e.getDescription());

    }

}
