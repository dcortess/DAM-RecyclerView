package cat.udl.tidic.amd.dam_recyclerview.repositories;

import androidx.lifecycle.LiveData;
import java.util.List;
import cat.udl.tidic.amd.dam_recyclerview.models.Event;


public interface EventRepoI {

   // Crear un nou event
   void insert(Event event);

   // Actualitzar un event exitent
   void update (Event event);

   // Eliminar un event existent utlitzant l'id
   void delete(Event e);

   // Obtenir tots els events creats per un usuari
   LiveData<List<Event>> getEvents(int userId);

   // Obtenir tots els events creats per tots els usuaris
   LiveData<List<Event>> getEvents();


}
