
package com.battlelancer.seriesguide;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Show {

    @Id
    private String id;
    private boolean isFavorite;
    private boolean isHidden;
    private boolean isSyncEnabled;
    private String getGlueId;

    public Show() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    public boolean isSyncEnabled() {
        return isSyncEnabled;
    }

    public void setSyncEnabled(boolean isSyncEnabled) {
        this.isSyncEnabled = isSyncEnabled;
    }

    public String getGetGlueId() {
        return getGlueId;
    }

    public void setGetGlueId(String getGlueId) {
        this.getGlueId = getGlueId;
    }

}
