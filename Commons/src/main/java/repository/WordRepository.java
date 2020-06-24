package repository;

import domain.Word;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class WordRepository implements IWordRepository {

    public WordRepository() {
        System.out.println("creating word repo");
    }

    @Override
    public Integer findPoints(String letters, String word) {
        try (Session session = JdbcUtils.getSessionFactory().openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String queryString = "from Word as w where w.letters = ? and w.word = ?";
                List<Word> r = session.createQuery(queryString, Word.class)
                        .setParameter(0, letters)
                        .setParameter(1, word)
                        .list();
                tx.commit();
                if (r.size() == 1)
                    return r.get(0).getPoints();
                else return -1;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
