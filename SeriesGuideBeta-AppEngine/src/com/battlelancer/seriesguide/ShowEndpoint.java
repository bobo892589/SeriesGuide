
package com.battlelancer.seriesguide;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JPACursorHelper;

import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

@Api(name = "showendpoint")
public class ShowEndpoint {

    /**
     * This method lists all the entities inserted in datastore. It uses HTTP
     * GET method and paging support.
     * 
     * @return A CollectionResponse class containing the list of all entities
     *         persisted and a cursor to the next page.
     */
    @SuppressWarnings({
            "unchecked", "unused"
    })
    public CollectionResponse<Show> listShow(
            @Nullable
            @Named("cursor")
            String cursorString,
            @Nullable
            @Named("limit")
            Integer limit) {

        EntityManager mgr = null;
        Cursor cursor = null;
        List<Show> execute = null;

        try {
            mgr = getEntityManager();
            Query query = mgr.createQuery("select from Show as Show");
            if (cursorString != null && cursorString != "") {
                cursor = Cursor.fromWebSafeString(cursorString);
                query.setHint(JPACursorHelper.CURSOR_HINT, cursor);
            }

            if (limit != null) {
                query.setFirstResult(0);
                query.setMaxResults(limit);
            }

            execute = (List<Show>) query.getResultList();
            cursor = JPACursorHelper.getCursor(execute);
            if (cursor != null)
                cursorString = cursor.toWebSafeString();

            // Tight loop for fetching all entities from datastore and
            // accomodate
            // for lazy fetch.
            for (Show obj : execute)
                ;
        } finally {
            mgr.close();
        }

        return CollectionResponse.<Show> builder()
                .setItems(execute)
                .setNextPageToken(cursorString)
                .build();
    }

    /**
     * This method gets the entity having primary key id. It uses HTTP GET
     * method.
     * 
     * @param id the primary key of the java bean.
     * @return The entity with primary key id.
     */
    public Show getShow(@Named("id")
    String id) {
        EntityManager mgr = getEntityManager();
        Show show = null;
        try {
            show = mgr.find(Show.class, id);
        } finally {
            mgr.close();
        }
        return show;
    }

    /**
     * This inserts a new entity into App Engine datastore. If the entity
     * already exists in the datastore, an exception is thrown. It uses HTTP
     * POST method.
     * 
     * @param show the entity to be inserted.
     * @return The inserted entity.
     */
    public Show insertShow(Show show) {
        EntityManager mgr = getEntityManager();
        try {
            if (containsShow(show)) {
                throw new EntityExistsException("Object already exists");
            }
            mgr.persist(show);
        } finally {
            mgr.close();
        }
        return show;
    }

    /**
     * This method is used for updating an existing entity. If the entity does
     * not exist in the datastore, an exception is thrown. It uses HTTP PUT
     * method.
     * 
     * @param show the entity to be updated.
     * @return The updated entity.
     */
    public Show updateShow(Show show) {
        EntityManager mgr = getEntityManager();
        try {
            if (!containsShow(show)) {
                throw new EntityNotFoundException("Object does not exist");
            }
            mgr.persist(show);
        } finally {
            mgr.close();
        }
        return show;
    }

    /**
     * This method removes the entity with primary key id. It uses HTTP DELETE
     * method.
     * 
     * @param id the primary key of the entity to be deleted.
     * @return The deleted entity.
     */
    public Show removeShow(@Named("id")
    String id) {
        EntityManager mgr = getEntityManager();
        Show show = null;
        try {
            show = mgr.find(Show.class, id);
            mgr.remove(show);
        } finally {
            mgr.close();
        }
        return show;
    }

    private boolean containsShow(Show show) {
        EntityManager mgr = getEntityManager();
        boolean contains = true;
        try {
            Show item = mgr.find(Show.class, show.getId());
            if (item == null) {
                contains = false;
            }
        } finally {
            mgr.close();
        }
        return contains;
    }

    private static EntityManager getEntityManager() {
        return EMF.get().createEntityManager();
    }

}
