/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import Control.Controller;
import com.nokia.lwuit.components.HeaderBar;
import com.nokia.lwuit.components.HeaderBarListener;
import com.sun.lwuit.ButtonGroup;
import com.sun.lwuit.CheckBox;
import com.sun.lwuit.Command;
import com.sun.lwuit.Container;
import com.sun.lwuit.Display;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;
import com.sun.lwuit.RadioButton;
import com.sun.lwuit.TextField;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.GenericListCellRenderer;
import event.Event;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.ChoiceGroup;
import models.Bank;
import models.Text;
import np.bu;

/**
 *
 * @author PhongHoang
 */
public class BankLeagueForm extends Form implements ActionListener {

    TextField khoangcach;
    ButtonGroup radioButtonGroup=new ButtonGroup();
    Container dau, newlist;
    //Text text= new Text();
    private Vector items;
    Command cmdBack, okie;
    List list;
    ButtonGroup btnGroup = new ButtonGroup();
    private RadioButton radioButton;

    public BankLeagueForm(String string, final Vector items) {
        Display.getInstance().setForceFullScreen(true);
        //setTitle(text.TimATM);
        HeaderBar header = null;
        try {
            header = new HeaderBar(string, Image.createImage("/images/tick.png"), Image.createImage("/images/tick2.png"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        header.setHeaderTitleColor(Text.colorWhite);
        header.getStyle().setBgColor(Text.hearder);
        header.setScrollable(false);
        header.setHeaderBarListener(new HeaderBarListener() {

            public void notifyHeaderBarListener(HeaderBar hb) {
                int k = -1;
                if(radioButtonGroup.isSelected()) k=radioButtonGroup.getSelectedIndex();
                if (k == -1) { // Khong chon ngan hang lien ket nao
                    Controller.getInstance().showBack();
                } else {
                    Bank b = (Bank) items.elementAt(k);
                    Event evt = new Event();
                    evt.setData("bank", b);
                    Controller.getInstance().handleEvent(Event.FIND_ATM_LEAGUE, evt);
                }
            }
        });

        addComponent(header);
        setScrollable(false);

        this.items = items;
        cmdBack = new Command(Text.Back);
        setBackCommand(cmdBack);
        addCommand(cmdBack);
        //okie = new Command(Text.Ok);
        //setDefaultCommand(okie);
        //addCommand(okie);

        addCommandListener(this);
        if (items.isEmpty()) {
            Label nullResuilts = new Label(Text.khongCoKqtQua);
            nullResuilts.getStyle().setMargin(10, 10, 10, 10);
            nullResuilts.getStyle().setBgTransparency(0);
            nullResuilts.getStyle().setFgColor(Text.colorWhite);
            addComponent(nullResuilts);
        } else {
            newlist = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Font font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
        
//            newlist.getStyle().setMargin(5, 5, 5, 5);
            Bank b;
            for (int i = 0; i < items.size(); i++) {
                b = (Bank) items.elementAt(i);
                radioButton = new RadioButton(b.getName()+" - "+b.getFullname());
                radioButton.getStyle().setFont(font);
                radioButton.getPressedStyle().setFont(font);
                radioButton.getSelectedStyle().setFont(font);
                //radioButton.setUIID("ListItem");
                radioButton.getStyle().setBgTransparency(0);
                radioButton.getStyle().setFgColor(Text.colorWhite);
//                radioButton.setTickerEnabled(false);
                radioButtonGroup.add(radioButton);
                newlist.addComponent(radioButton);
            }
            
            newlist.setScrollable(true);
            newlist.setScrollableX(false);

            addComponent(newlist);
        }
    }

    private void createList() {
        list = null;
        Runtime.getRuntime().gc();
        newlist = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        newlist.getStyle().setMargin(5, 5, 5, 5);

        list = new List(createGenericListCellRendererModelData());
        list.setSelectedIndex(7, true);
        list.setRenderer(new GenericListCellRenderer(createGenericRendererContainer(),
                createGenericRendererContainer()));
//            list.setSelectedIndex(7, true);
//            for (int i = 0; i < items.size(); i++) {
//                if (Text.NganHangLKet[i] == true) {
////                    list.setSelectedIndex(i);
//                    list.setSelectedIndex(i,false);
//                    
//                    break;
//                }
//            }

        list.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {

                int k = list.getSelectedIndex();
                if (Text.NganHangLKet[k] == true) {
                    Text.NganHangLKet[k] = false;
                } else {
                    Text.NganHangLKet[k] = true;
                }
                for (int i = 0; i < items.size(); i++) {
                    if (k != i) {
                        Text.NganHangLKet[i] = false;
                    }
                }

//                    Text.rectan=newlist.getDirtyRegion().getY();
//                    System.out.println("cdcdcd_ " + newlist.getAbsoluteY());
//                    repaint();
//                    newlist.set
                Controller.getInstance().showBankLeaguaForm2(items);
//                createList();
//                System.out.println(" lai...");
//                repaint();
//                    list.setSelectedItem(Text.NganHangLKet);
            }
        });
        newlist.addComponent(list);
        newlist.setScrollable(true);
        newlist.setScrollableX(false);

        addComponent(newlist);
        Runtime.getRuntime().gc();
    }

    private Container createGenericRendererContainer() {
        Container f = new Container(new BorderLayout());
        Font font2 = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);

        Container c = new Container(new BoxLayout(BoxLayout.X_AXIS));
        //f.setUIID("ListRenderer");
        f.getStyle().setBgTransparency(0);
        c.getStyle().setBgTransparency(0);


        Container d = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container e = new Container(new BoxLayout(CENTER));
        d.getStyle().setBgTransparency(0);
        e.getStyle().setBgTransparency(0);

        RadioButton selected = new RadioButton();
        btnGroup.add(selected);
        selected.setName("Selected");
//        selected.setFocusable(true);
        selected.setPreferredW(35);

        Label name = new Label();
        name.setFocusable(true);
        name.setName("Name");
        name.getStyle().setFgColor(Text.colorWhite);
        name.getStyle().setBgTransparency(0);

        d.addComponent(name);

        Label surname = new Label();
        surname.setFocusable(true);
        surname.getStyle().setFgColor(Text.colorWhite);
        surname.setName("Surname");
        surname.getStyle().setBgTransparency(0);
        surname.getStyle().setFont(font2);
        surname.getUnselectedStyle().setFont(font2);
        surname.getSelectedStyle().setFont(font2);
        surname.getPressedStyle().setFont(font2);

        d.addComponent(surname);


        e.addComponent(selected);

        c.addComponent(d);
        f.addComponent(BorderLayout.CENTER, c);
        f.addComponent(BorderLayout.EAST, e);

        return f;
    }

    private Hashtable[] createGenericListCellRendererModelData() {
        Bank b;
        Hashtable[] data = new Hashtable[items.size()];

        for (int i = 0; i < items.size(); i++) {
            b = (Bank) items.elementAt(i);
            data[i] = new Hashtable();
            data[i].put("Name", b.getName());

            data[i].put("Surname", b.getFullname());
            data[i].put("Selected", Text.NganHangLKet[i] + "");

        }

        return data;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getCommand() == okie) {
            int k = -1;
            if(radioButtonGroup.isSelected()) k=radioButtonGroup.getSelectedIndex();
//            for (int i = 0; i < items.size(); i++) {
//                if (Text.NganHangLKet[i] == true) {
//                    k = i;
//                    break;
//                }
//            }
            if (k == -1) { // Khong chon ngan hang lien ket nao
                Controller.getInstance().showBack();
            } else {
                Bank b = (Bank) items.elementAt(k);
                Event evt = new Event();
                evt.setData("bank", b);
                Controller.getInstance().handleEvent(Event.FIND_ATM_LEAGUE, evt);
            }
        } else if (ae.getCommand() == cmdBack) {
            Controller.getInstance().showBack();

        }
    }
}
