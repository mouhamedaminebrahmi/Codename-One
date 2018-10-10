/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import entity.Creneau;
import entity.Evenement;
import entity.Image;
import entity.User;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Meedoch
 */
public class EventService extends Observable {

    public List<Evenement> events;
   

    public void getEventImages(int id, Observer o, Dialog dlg) {
        addObserver(o);
        List<Image> images = new ArrayList<>();
        ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa/web/app_dev.php/service/imagesEvent?event_id=" + id);
        request.addResponseListener((evt) -> {
            JSONParser parser = new JSONParser();

            try {
                Map<String, Object> result = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData())));

                java.util.List<Map<String, Object>> res = (java.util.List<Map<String, Object>>) result.get("root");
                for (int i = 0; i < res.size(); i++) {
                    Map<String, Object> img = res.get(i);
                    Image image = new Image(id, (String) img.get("chemin"));
                    Double image_id = (Double) img.get("id");
                    image.setId(image_id.intValue());
                    images.add(image);
                }
                dlg.dispose();
                o.update(this, images);
                //dlg.dispose();
            } catch (IOException ex) {
                //Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        request.setPost(true);
        // request.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueue(request);
    }

//    public void getAvailable(Dialog dlg, Observer o) {
//        addObserver(o);
//        events = new ArrayList<>();
//         ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa/web/app_dev.php/service/events/available");
//        request.addResponseListener((evt) -> {
//            JSONParser parser = new JSONParser();
//
//            try {
//                Map<String, Object> result = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData())));
//
//                java.util.List<Map<String, Object>> res = (java.util.List<Map<String, Object>>) result.get("root");
//                for (int i = 0; i < res.size(); i++) {
//                    //System.out.println("Event " + i);
//                    Map<String, Object> event = res.get(i);
//                    Evenement e = new Evenement();
//                    e.setDateCreation((String) event.get("dateCreation"));
//                    e.setStatut((String) event.get("statut"));
//                    e.setEvenementType((String) event.get("type"));
//                    e.setDestination((String) event.get("destination"));
//                    Double nbPlaces = (Double) event.get("nombre_places");
//                    e.setNombrePlaces(nbPlaces.intValue());
//                    Double id = (Double) event.get("id");
//                    e.setId(id.intValue());
//                    e.setEvenementType((String) event.get("type"));
//                    e.setDateHeureDepart((String) event.get("date"));
//                    e.getImages().add(new Image(e.getId(), (String) event.get("photo")));
//                    e.setUserName((String) event.get("username"));
//                    e.setUserMail((String) event.get("usermail"));
//                    e.setDescription((String) event.get("description"));
//                    e.setReglement((String) event.get("reglement"));
//                    e.setFrais((Double) event.get("frais"));
//                    Double placesprises = (Double) event.get("nombre_places_prises");
//                    e.setLieuRassemblement((String) event.get("lieuRassemblement"));
//                    e.setNombrePlacesPrises(placesprises.intValue());
//                    if (e.getEvenementType().equals("camping")) {
//                        Double duree = (Double) event.get("duree");
//                        e.setDuree(duree.intValue());
//                    } else {
//                        Double distanceParcourue = (Double) event.get("distanceParcourue");
//                        e.setDistanceParcourue(distanceParcourue);
//                        e.setType((String) event.get("typeRando"));
//                        e.setDifficulte((String) event.get("difficulte"));
//                    }
//                    //System.out.println(e.getEvenementType());
//                    //System.out.println(e.getDateCreation());
//                    events.add(e);
//
//                }
//
//                dlg.dispose();
//                o.update(this, dlg);
//                //dlg.dispose();
//            } catch (IOException ex) {
//                //Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        });
//        request.setPost(true);
//        // request.setDisposeOnCompletion(dlg);
//        NetworkManager.getInstance().addToQueue(request);
//
//    }

    public void removeEvent(Evenement event,Observer o) {
        ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa/web/app_dev.php/service/events/delete");
        request.setPost(false);
        request.addArgument("id", event.getId().toString());
        
        request.addResponseListener((evt) -> {
             o.update(this, evt);
        });
        
        NetworkManager.getInstance().addToQueue(request);
        
    }

    public void addEvent(Evenement event, List<Creneau> planning, List<String> photos, Observer o) {

        ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa/web/app_dev.php/service/events/add");
        request.setPost(false);
        request.addArgument("nbPlaces", String.valueOf(event.getNombrePlaces()));
        request.addArgument("frais", String.valueOf(event.getFrais()));
        request.addArgument("lieuRassemblement", event.getLieuRassemblement());
        request.addArgument("dateHeureDepart", event.getDateHeureDepart());
        request.addArgument("description", event.getDescription());
        request.addArgument("reglement", event.getReglement());

        request.addArgument("typeEvent", event.getEvenementType());
        request.addArgument("destination", event.getDestination());
        if (event.getEvenementType().equals("camping")) {
            request.addArgument("duree", String.valueOf(event.getDuree()));
        } else {
            request.addArgument("distanceParcourue", String.valueOf(event.getDistanceParcourue()));
            request.addArgument("difficulte", event.getDifficulte());
            request.addArgument("type", (event.getType()));
        }
        request.addArgument("organisateur", String.valueOf(event.getOrganisateur_id()));
        request.addArgument("guide", String.valueOf(event.getGuide_id()));

        request.addArgument("creneauCount", String.valueOf(planning.size()));

        for (int i = 0; i < planning.size(); i++) {
            request.addArgument("debutCreneau" + i, planning.get(i).getDateDebut());
            request.addArgument("finCreneau" + i, planning.get(i).getDateFin());
            request.addArgument("descriptionCreneau" + i, planning.get(i).getDescription());
        }

        request.addArgument("imageCount", String.valueOf(photos.size()));
        for (int i = 0; i < photos.size(); i++) {
            request.addArgument("image" + i, photos.get(i));
        }

        request.addResponseListener((evt) -> {
            System.out.println(new String(request.getResponseData()));
            o.update(this, new String(request.getResponseData()));
        });
        //System.out.println(request.getUrl());

        // request.setDisposeOnCompletion(dlg);
        ToastBar.showConnectionProgress("Ajout", request, (value) -> {
        }, (a, b, c, d) -> {
        });
        NetworkManager.getInstance().addToQueue(request);

    }

    public void updateEvent(Evenement event, List<Creneau> planning, List<String> photos, Observer o) {

        ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa/web/app_dev.php/service/events/update");
        request.setPost(false);
        request.addArgument("nbPlaces", String.valueOf(event.getNombrePlaces()));
        request.addArgument("frais", String.valueOf(event.getFrais()));
        request.addArgument("lieuRassemblement", event.getLieuRassemblement());
        request.addArgument("dateHeureDepart", event.getDateHeureDepart());
        request.addArgument("description", event.getDescription());
        request.addArgument("reglement", event.getReglement());
        request.addArgument("id", String.valueOf(event.getId()));
        request.addArgument("typeEvent", event.getEvenementType());
        request.addArgument("destination", event.getDestination());
        if (event.getEvenementType().equals("camping")) {
            request.addArgument("duree", String.valueOf(event.getDuree()));
        } else {
            request.addArgument("distanceParcourue", String.valueOf(event.getDistanceParcourue()));
            request.addArgument("difficulte", event.getDifficulte());
            request.addArgument("type", (event.getType()));
        }
        request.addArgument("organisateur", String.valueOf(event.getOrganisateur_id()));
        request.addArgument("guide", String.valueOf(event.getGuide_id()));

        request.addArgument("creneauCount", String.valueOf(planning.size()));

        for (int i = 0; i < planning.size(); i++) {
            request.addArgument("debutCreneau" + i, planning.get(i).getDateDebut());
            request.addArgument("finCreneau" + i, planning.get(i).getDateFin());
            request.addArgument("descriptionCreneau" + i, planning.get(i).getDescription());
        }

        request.addArgument("imageCount", String.valueOf(photos.size()));
        for (int i = 0; i < photos.size(); i++) {
            request.addArgument("image" + i, photos.get(i));
        }

        request.addResponseListener((evt) -> {
            System.out.println(new String(request.getResponseData()));
            o.update(this, new String(request.getResponseData()));
        });
        //System.out.println(request.getUrl());

        // request.setDisposeOnCompletion(dlg);
        ToastBar.showConnectionProgress("Modification", request, (value) -> {
        }, (a, b, c, d) -> {
        });
        NetworkManager.getInstance().addToQueue(request);

    }

    public void getPlanning(Evenement evenement, Observer o) {
        ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa/web/app_dev.php/service/events/planning");
        request.setPost(false);
        request.addArgument("id", String.valueOf(evenement.getId()));
        request.addResponseListener((evt) -> {
            try {
                JSONParser parser = new JSONParser();
                System.out.println(new String(request.getResponseData()));
                Map<String, Object> result = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData())));
                java.util.List<Map<String, Object>> res = (java.util.List<Map<String, Object>>) result.get("root");

                List<Creneau> planning = new ArrayList<Creneau>();
                for (int i = 0; i < res.size(); i++) {
                    Creneau c = new Creneau();
                    c.setDateDebut((String) res.get(i).get("debut"));
                    c.setDateFin((String) res.get(i).get("fin"));
                    c.setDescription((String) res.get(i).get("description"));
                    planning.add(c);
                }
                o.update(this, planning);
            } catch (IOException ex) {

            }
        });

        NetworkManager.getInstance().addToQueue(request);
    }

    public void participer(Evenement event, User user,String passcode, Observer o){
        ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa/web/app_dev.php/service/events/participer");
        request.setPost(false);
        request.addArgument("user_id", user.getId().toString());
        request.addArgument("event_id", event.getId().toString());
        request.addArgument("passcode", passcode);
        request.addResponseListener((evt) -> {
             o.update(this, new String(request.getResponseData()));
        });
        
        NetworkManager.getInstance().addToQueue(request);
    }
}
