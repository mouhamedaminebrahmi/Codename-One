/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Meedoch
 */
public class Converter {

    public static Date stringToDate(String s) {
        Date d = null;
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yy HH:mm:ss");

            d = formatter.parse(s);

            System.out.println(d);

        } catch (ParseException ex) {
            ex.printStackTrace();
           // Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }
}
