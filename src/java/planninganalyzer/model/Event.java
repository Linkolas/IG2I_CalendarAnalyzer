/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planninganalyzer.model;

import java.util.Date;

/**
 *
 * @author Nicolas
 */
public class Event {
    public Date date = null;
    public float hours = 0;
    public String prof = "";
    public String libelle = "";
    public String type = "";

    @Override
    public String toString() {
        return "" + date + " - " + libelle + " - " + prof + " - " + type + " - " + hours + "h";
    }
}
