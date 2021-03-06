package cat.udl.tidic.amd.dam_recyclerview.repositories;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;
import cat.udl.tidic.amd.dam_recyclerview.dao.EventDAOI;
import cat.udl.tidic.amd.dam_recyclerview.database.EventDatabase;
import cat.udl.tidic.amd.dam_recyclerview.models.Event;


public class EventRepoImpl implements EventRepoI {

    private EventDAOI eventDAO;
    private LiveData<List<Event>> allEvents;

    public EventRepoImpl(Application application){
        EventDatabase db = EventDatabase.getInstance(application);
        eventDAO = db.eventDAO();
        allEvents = eventDAO.getAllEvents();
    }

    // Funcions principals

    @Override
    public void insert(Event event) {
        new InsertEventAsyncTask(eventDAO).execute(event);
    }

    @Override
    public void update(Event event) {
        new UpdateEventAsyncTask(eventDAO).execute(event);
    }

    @Override
    public void delete(Event e) {
        new DeleteEventAsyncTask(eventDAO).execute(e);
    }

    @Override
    public LiveData<List<Event>> getEvents(int userId) {
        allEvents = eventDAO.getAllEvents(userId);
        return allEvents;
    }

    @Override
    public LiveData<List<Event>> getEvents() {
        allEvents = eventDAO.getAllEvents();
        return allEvents;
    }

    // Eliminar event
    private static class DeleteEventAsyncTask extends AsyncTask<Event, Void, Void> {

        private EventDAOI eventDAO;

        private DeleteEventAsyncTask(EventDAOI eventDAO) {
            this.eventDAO = eventDAO;
        }

        @Override
        protected Void doInBackground(Event... events) {
            eventDAO.delete(events[0]);
            return null;
        }
    }

    // Actualitzar event
    private static class UpdateEventAsyncTask extends AsyncTask<Event, Void, Void> {
        private EventDAOI eventDAO;

        private UpdateEventAsyncTask(EventDAOI eventDAO) {
            this.eventDAO = eventDAO;
        }

        @Override
        protected Void doInBackground(Event... event) {
            eventDAO.update(event[0]);
            return null;
        }
    }

    // Inserir event
    private static class InsertEventAsyncTask extends AsyncTask<Event, Void, Void> {
        private EventDAOI eventDAO;

        private InsertEventAsyncTask(EventDAOI eventDAO) {
            this.eventDAO = eventDAO;
        }

        @Override
        protected Void doInBackground(Event... event) {
            eventDAO.insert(event[0]);
            return null;
        }
    }

}
