/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tahwissa;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.components.WebBrowser;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import static com.codename1.io.Log.p;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import entity.Commentaire;
import entity.Evenement;
import entity.Publication;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.image.ImageView;
import service.PubService;
import static tn.esprit.tahwissa.DetailsEvent.e;

/**
 *
 * @author BRAHMI
 */
public class detailsPublication implements Observer {

    private Form current;
    private Resources theme;

    public static Publication e = new Publication();

    public detailsPublication(Publication pub) {
        publication = pub;
    }

    public void init(Object context) {

        theme = UIManager.initFirstTheme("/theme_1");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }
    Publication publication;
    Commentaire commentaire;

    public void start() {
        if (current != null) {
            current.show();
            return;
        }
        Form hi = new Form(new BorderLayout());
        hi.setScrollableY(true);
        Container pubInfo = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        pubInfo.setScrollableY(true);
        hi.getToolbar().addCommandToLeftBar("Back", theme.getImage("back-command.png"), (event) -> {
            ListPublication mp = new ListPublication();
            mp.init(current);
            mp.start();
        });
        SpanLabel contenu = new SpanLabel();
        contenu.setText(publication.getContenu());

        Label titre = new Label(publication.getTitre());
        Label DateHeurePublication = new Label(publication.getDateHeurePublication().toString());

        String url = "http://localhost/tahwissa-web/web/app_dev.php/service/videoplayer?video_url=" + publication.getVideo_url();
        BrowserComponent b = new BrowserComponent();
        b.setURL(url);
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(Display.getInstance().getDisplayWidth(), 200), true);
        String path = publication.getImages().get(0).getChemin();
        if (publication.getImages().get(0).getChemin().equals("") == false) {
            path = path.substring(path.lastIndexOf("/") + 1, path.length() - 1);
        }
        Image image = URLImage.createToStorage(placeholder, path, "http://localhost/tahwissa-web/web/uploads/images/publications/" + publication.getImages().get(0).getChemin());

        contenu.add(BorderLayout.NORTH, image.scaled(Display.getInstance().getDisplayWidth(), 200));
        b.setSize(new Dimension(Display.getInstance().getDisplayWidth(), 200));
        //hi.add(BorderLayout.SOUTH,b);
        pubInfo.add(titre);
        pubInfo.add(DateHeurePublication);
        pubInfo.add(b);
        pubInfo.add(contenu);

        Button btnOk = new Button("Supprimer");
        btnOk.setUIID("RedButton");
        pubInfo.add(btnOk);
        btnOk.addPointerPressedListener((evt) -> {

            PubService pub = new PubService();

            pub.removePub(publication, this);

            ListPublication next = new ListPublication();
            next.init(current);
            next.start();

        });

        TextField tcomm = new TextField("", "commentaire");

        pubInfo.add(tcomm);

        Button btncomm = new Button("Commenter");
        pubInfo.add(btncomm);
        btncomm.addPointerPressedListener((evt) -> {

            PubService pub = new PubService();
            Commentaire c = new Commentaire();

            c.setContenu(contenu.getText());

            c.setContenu(tcomm.getText());
            c.setCommentateur_id(LoginManager.getUser().getId());
            c.setPublication_id(publication.getId());

            pub.addComm(c);

        });

        PubService dt = new PubService();
        List<Commentaire> s = PubService.getCommentaires(publication.getId());
        System.out.println(s.size()+" comments found for "+publication.getId());
        Container comContainer = new Container(BoxLayout.y());
        comContainer.setUIID("commentireContainer");
        for (int i = 0; i < s.size(); i++) {
            final Commentaire comm = s.get(i);

            SpanLabel contenucomm = new SpanLabel();
            contenucomm.setText(comm.getContenu()+"     "+comm.getDateHeureCommentaire());
            comContainer.add(contenucomm);
        }
        hi.add(BorderLayout.SOUTH,comContainer);
        hi.add(BorderLayout.CENTER, pubInfo);
        hi.show();

    }

    public void stop() {
        current = Display.getInstance().getCurrent();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = Display.getInstance().getCurrent();
        }
    }

    public void destroy() {
    }

    @Override
    public void update(Observable o, Object arg) {

    }

}
