/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab1;

import entity.Gruppyi;
import entity.Marks;
import entity.Studentyi;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.NewHibernateUtil;

/**
 * @author 17682
 */
public class Lab1 {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SessionFactory sf = NewHibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        Transaction transaction = s.beginTransaction();
        /*   Studentyi d = new Studentyi( nomerZachetki, gruppyi, familiya, imya, otchestvo, gorod,  adres,  tel,  status, statusDate);
            s.persist(d);
            s.save(d);
         */
        transaction.commit();

        List<Gruppyi> g = s.createQuery("from Gruppyi s").list();
        List<Studentyi> q = s.createQuery("from Studentyi s").list();
        List<Marks> m = s.createQuery(("from Marks s")).list();
        Transaction t = s.beginTransaction();

        task1(g);
        task2(g, q);
        perevod(q.get(2));
        task2(g, q);

        getMarks(q.get(1));




        s.flush();
        t.commit();
        s.close();
        // TODO code application logic here
    }

    public static void task1(List<Gruppyi> g) {
        String split;
        String[] test;

        for (int i = 0; i < g.size(); i++) {
            int counter = 1;
            for (Gruppyi gr : g) {
                test = gr.getNazvanie().split("-");
                split = test[0];
                if (counter > 0 && i == gr.getKodPlana()) {
                    System.out.println("группы специальности " + split + ":");
                    counter = 0;
                }
                if (i == gr.getKodPlana()) {
                    System.out.println(gr.getNazvanie());
                }
            }
        }
    }

    public static void task2(List<Gruppyi> g, List<Studentyi> q) {
        int n = 0;
        for (Gruppyi gr : g) {
            for (Studentyi u : q) {
                if (u.getStatus().equals("enrolled") & gr.getShifr().equals(u.getGruppyi().getShifr())) {
                    n++;
                }
            }
            System.out.println("В группе " + gr.getNazvanie() + " действующих студентов: " + n);
            n = 0;
        }
    }

    public static boolean isAcadem(Studentyi std) {
        return std.getStatus().equals("academ");
    }

    public static void getMarks(Studentyi std){
    SessionFactory sf = NewHibernateUtil.getSessionFactory();
    Session s = sf.openSession();
    Transaction transaction = s.beginTransaction();
    List<Gruppyi> g = s.createQuery("from Gruppyi s").list();
    List<Studentyi> q = s.createQuery("from Studentyi s").list();
    List<Marks> m = s.createQuery(("from Marks s")).list();
    System.out.println("У студента " + std.getFamiliya() + " " + std.getImya());
    for(Marks mr : m){
        if(mr.getStudent().getNomerZachetki() == std.getNomerZachetki()){
            System.out.println("Оценка " + mr.getMark() + " по " + mr.getSubject());
        }
    }
}
    public static void perevod(Studentyi std) {
        SessionFactory sf = NewHibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        Transaction transaction = s.beginTransaction();
        List<Gruppyi> g = s.createQuery("from Gruppyi s").list();
        List<Studentyi> q = s.createQuery("from Studentyi s").list();

        if (isAcadem(std)) {
            int x = std.getGruppyi().getKodPlana();
            Date data = std.getGruppyi().getDataFormir();
            System.out.println("data studenta   " + data.getTime());
            System.out.println("data groupi "  );
            for (Gruppyi gr : g) {
                System.out.print(gr.getDataFormir().getTime());
                if ((gr.getDataFormir().after(data)) && (gr.getKodPlana() == x)) {
                    std.setGruppyi(gr);
                    std.setStatus("enrolled");
                }
                    //System.out.println("    " + gr.getNazvanie() + "    " + gr.getDataFormir().getTime());
                    //System.out.println("test");
            }

            System.out.println("check " + x);
            transaction.commit();
        } else {
            System.out.println("Студент не в академе");
        }
    }
   /* public static void acadPerformance(Studentyi stud){
        SessionFactory sf = NewHibernateUtil.getSessionFactory();
        Session s = sf.openSession();
        Transaction transaction = s.beginTransaction();
        List<Gruppyi> g = s.createQuery("from Gruppyi s").list();
        List<Studentyi> q = s.createQuery("from Studentyi s").list();
        List<Marks> m = s.createQuery("from marks s").list();
        System.out.println("У студента " + stud.getFamiliya() + " " + stud.getImya() + " оценки");
        for(Marks mr : m){
            if (mr.getStudent() == stud){
                System.out.println(mr.getSubject() + " " + mr.getMark());
            }
            return;
        }
    }*/

}
