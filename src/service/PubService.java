/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import entity.Commentaire;

import entity.Image;
import entity.Publication;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import util.Converter;

/**
 *
 * @author BRAHMI
 */
public class PubService extends Observable {

    public List<Publication> pubs;

    public static void addPub(Publication pub, String image) {

        ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa-web/web/app_dev.php/service/publication/add");

        request.addArgument("titre", String.valueOf(pub.getTitre()));
        request.addArgument("contenu", String.valueOf(pub.getContenu()));
        request.addArgument("video_url", pub.getVideo_url());
        request.addArgument("user", String.valueOf(pub.getPublicitaire_id()));

        request.addResponseListener((evt) -> {
            int id = Integer.parseInt(new String(request.getResponseData()));
            addImage(image, id);
        });

        NetworkManager.getInstance().addToQueue(request);

    }

    public static List<Publication> getPublication() {
        Map<String, List<Map<String, Object>>> toReturn = new HashMap<>();
        ArrayList<Publication> publicationsArray = new ArrayList<>();
        ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa-web/web/app_dev.php/sercice/publicationService");

        request.addResponseListener((evt) -> {
            JSONParser parser = new JSONParser();

            try {
                Map<String, Object> result = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData())));
                

                List<Map<String, Object>> publications = (List<Map<String, Object>>) result.get("root");

                for (int i = 0; i < publications.size(); i++) {
                    Publication publication = new Publication();
                    Map<String, Object> current = publications.get(i);
                    Double id = (Double) current.get("id");

                    publication.setId(id.intValue());
                    
                    publication.setContenu((String) current.get("contenu"));
                    publication.setTitre((String) current.get("titre"));
                    publication.setVideo_url((String) current.get("video_url"));
                    publication.setDateHeurePublication(Converter.stringToDate((String) current.get("dateHeurePublication")));
                    publication.setImages(new ArrayList<>());
                    publication.getImages().add(new Image(1, (String) current.get("image")));
                    
                    if (publication.getImages().get(0).getChemin().equals("") == false) {
                        publicationsArray.add(publication);
                    }
                }

            } catch (IOException ex) {
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return publicationsArray;
    }

    public void addPublication(String titre, String contenu, String videoUrl, String image) {

        ConnectionRequest req = new ConnectionRequest();
        req.setUrl("http://localhost/pidev2017/inserPub.php?titre=" + titre + "&contenu=" + contenu + "&videoUrl=" + videoUrl);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                byte[] data = (byte[]) evt.getMetaData();
                String s = new String(data);
                
                if (s.equals("success")) {
                    Dialog.show("Confirmation", "ajout avec succées", "Ok", null);
                    return;
                }
            }
        });

        NetworkManager.getInstance().addToQueue(req);
        List<Publication> l = new ArrayList<Publication>();
        l.addAll(getPublication());
        int id = l.get(l.size() - 1).getId() + 1;
        
        addImage(image, id);

    }

    public static void addImage(String image, int id) {

        ConnectionRequest req = new ConnectionRequest();
        req.setUrl("http://localhost/pidev2017/insert.php?chemin=" + image + "&id=" + id);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                byte[] data = (byte[]) evt.getMetaData();
                String s = new String(data);
                
                if (s.equals("success")) {
                    Dialog.show("Confirmation", "ajout avec succées", "Ok", null);
                    return;
                }
            }
        });

        NetworkManager.getInstance().addToQueue(req);

    }

    public void removePub(Publication pub, Observer o) {
        ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa-web/web/app_dev.php/service/publication/del");
        request.setPost(false);
       
        request.addArgument("id", pub.getId().toString());

        request.addResponseListener((evt) -> {
            

        });

        NetworkManager.getInstance().addToQueueAndWait(request);

    }

    
    
    public static void addComm(Commentaire comm) {

        ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa-web/web/app_dev.php/service/commentaire/add");

        
        request.addArgument("contenu", String.valueOf(comm.getContenu()));
        
        request.addArgument("user", String.valueOf(comm.getCommentateur_id()));
        
        request.addArgument("publication_id", String.valueOf(comm.getPublication_id()));

        request.addResponseListener((evt) -> {
            int id = Integer.parseInt(new String(request.getResponseData()));
           
        });

        NetworkManager.getInstance().addToQueue(request);

    }
    
    
    
    
    public static List<Commentaire> getCommentaires(int id_pub) {
        List<Commentaire> toReturn = new ArrayList<>();
        ArrayList<Commentaire> commentairesArray = new ArrayList<>();
        ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa-web/web/app_dev.php/sercice/commentaireService");
        request.addArgument("publication_id", String.valueOf(id_pub));
        request.addResponseListener((evt) -> {
            JSONParser parser = new JSONParser();

            try {
                Map<String, Object> result = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData())));
                

                List<Map<String, Object>> commentaires = (List<Map<String, Object>>) result.get("root");

                for (int i = 0; i < commentaires.size(); i++) {
                    Commentaire commentaire = new Commentaire();
                    Map<String, Object> current = commentaires.get(i);
                    Double id = (Double) current.get("id");

                    commentaire.setId(id.intValue());
                    
                    commentaire.setContenu((String) current.get("contenu"));
                    
                    commentaire.setDateHeureCommentaire(Converter.stringToDate((String) current.get("dateHeureCommentaire")));
                    System.out.println(commentaire.getPublication_id());
                    System.out.println(commentaire.getContenu());
                    System.out.println(commentaire.getDateHeureCommentaire());
                    
                    toReturn.add(commentaire);                   
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return toReturn;
    }
    
    
    
    
}
