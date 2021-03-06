package cat.udl.tidic.amd.dam_recyclerview.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import cat.udl.tidic.amd.dam_recyclerview.models.Event;

@Dao
public interface EventDAOI {

    @Insert
    void insert(Event event);

    @Update
    void update(Event event);

    @Delete
    void delete(Event event);

    // Consulta per obtenir totes les dades de la taula d'events
    @Query("SELECT * FROM event_table")
    LiveData<List<Event>> getAllEvents();

    // Consulta per obtenir totes les dades de la taula d'events per a un id específic
    @Query("SELECT * FROM event_table WHERE userId == :userId")
    LiveData<List<Event>> getAllEvents(int userId);

}
