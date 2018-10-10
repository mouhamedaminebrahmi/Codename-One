/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Date;

/**
 *
 * @author Meedoch
 */
public class Creneau{
    private long id;
    private String dateDebut;
    private String dateFin;
    private String description;
    private long evenement_id;

    

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEvenement_id() {
        return evenement_id;
    }

    public void setEvenement_id(long evenement_id) {
        this.evenement_id = evenement_id;
    }

    @Override
    public String toString() {
        return "Creneau{" + "id=" + id + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", description=" + description + ", evenement_id=" + evenement_id + '}';
    }

   

    
    
    
    
    
}
