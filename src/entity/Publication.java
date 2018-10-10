/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author BRAHMI
 */
public class Publication {
    
    private Integer id;
    private int imageId;
    private String titre;
    private String contenu;
    private Date dateHeurePublication;
    List<Image> images = new ArrayList<>();
    private int publicitaire_id;
    
    private String video_url;

    public int getPublicitaire_id() {
        return publicitaire_id;
    }

    public void setPublicitaire_id(int publicitaire_id) {
        this.publicitaire_id = publicitaire_id;
    }
    
    

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
    
    
    
    /**
     * @return the id
     */
     public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @param titre the titre to set
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @return the contenu
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * @param contenu the contenu to set
     */
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateHeurePublication() {
        return dateHeurePublication;
    }

    public void setDateHeurePublication(Date dateHeurePublication) {
        this.dateHeurePublication = dateHeurePublication;
    }



    /**
     * @return the imageId
     */
    public int getImageId() {
        return imageId;
    }

    /**
     * @param imageId the imageId to set
     */
    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    @Override
    public String toString() {
        return "Publication{" + "id=" + id + ", imageId=" + imageId + ", titre=" + titre + ", contenu=" + contenu + ", dateHeurePublication=" + dateHeurePublication + ", images=" + images + ", publicitaire_id=" + publicitaire_id + ", video_url=" + video_url + '}';
    }
    
    
    
   
}

    
