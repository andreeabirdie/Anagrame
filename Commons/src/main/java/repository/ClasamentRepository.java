package repository;

import domain.Clasament;
import domain.Round;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ClasamentRepository implements IClasamentRepository {

    @Override
    public void add(Clasament c) {
        try (Session session = JdbcUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(c);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Clasament findOne(Integer gameID) {
        try (Session session = JdbcUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String queryString = "from Clasament as c where c.gameID = ?";
                List<Clasament> r = session.createQuery(queryString, Clasament.class)
                        .setParameter(0, gameID)
                        .list();
                tx.commit();
                if (r.size() == 1)
                    return r.get(0);
                else return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
