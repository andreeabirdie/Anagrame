import domain.Clasament;
import domain.Game;
import domain.Round;
import repository.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service implements IServices {
    private IUserRepository userRepository;
    private IClasamentRepository clasamentRepository;
    private IRoundRepository roundRepository;
    private IGameRepository gameRepository;
    private IWordRepository wordRepository;
    private Map<String, IObserver> loggedClients;
    private final int defaultThreadsNo = 5;
    List<String> letters = new ArrayList<>(Arrays.asList("a,c,e,r","c,e,r,s,t,u","a,i,l,p,t","b,e,n,o,r,t"));


    public Service(IUserRepository userRepository, IClasamentRepository clasamentRepository, IRoundRepository roundRepository, IGameRepository gameRepository, IWordRepository wordRepository) {
        this.userRepository = userRepository;
        this.clasamentRepository = clasamentRepository;
        this.roundRepository = roundRepository;
        this.gameRepository = gameRepository;
        this.wordRepository = wordRepository;
        this.loggedClients = new ConcurrentHashMap<>();

    }

    @Override
    public boolean login(String username, String password, IObserver client) throws Exception {
        boolean valid = userRepository.findOne(username, password);
        if (valid) {
            if (loggedClients.get(username) != null) {
                throw new Exception("User is already Logged in!");
            }
            loggedClients.put(username, client);
            System.out.println("Client " + username + " connected");
            if (loggedClients.size() == 3) {
                notifyStart(username);
            }
        }
        return valid;
    }

    @Override
    public void logout(String username, IObserver client) throws Exception {
        IObserver localClient = loggedClients.remove(username);
        if (localClient == null)
            throw new Exception("User " + username + " is not logged in");
        System.out.println("Client " + username + " disconnected");
    }


    private void notifyStart(String username) {
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreadsNo);

        executor.execute(() -> {
            try {
                System.out.println("notifying start...");
                if (loggedClients.size() == 3)
                    loggedClients.get(username).enableStart();
                else if (loggedClients.size() < 3)
                    loggedClients.get(username).disableStart();
            } catch (Exception e) {
                System.out.println("error notifying player...");
            }
        });

        executor.shutdown();
    }

    @Override
    public void startGame() throws IOException {
        String fileName = "D:\\MPP\\practice\\anagrame\\Services\\src\\main\\resources\\gameID";
        FileInputStream fis=new FileInputStream(fileName);
        Scanner sc=new Scanner(fis);
        Integer id = Integer.parseInt(sc.nextLine()) +1;
        sc.close();
        System.out.println(id);
        FileOutputStream outputStream = new FileOutputStream(fileName);
        byte[] strToBytes = id.toString().getBytes();
        outputStream.write(strToBytes);
        outputStream.close();

        Game g = new Game(id,0,0);
        gameRepository.add(g);
        startRound(id);
    }

    @Override
    public void changeClient(String username, IObserver client) {
        loggedClients.replace(username, client);
    }

    @Override
    public void sendResponse(String username, Integer gameID, String word) {
        Game g = gameRepository.findOne(gameID);
        Round r = roundRepository.findOne(gameID, username, g.getCurrentRound());

        r.setWord(word);
        roundRepository.update(r);

        g.setSentResponses(g.getSentResponses() + 1);
        gameRepository.update(g);
        if(g.getSentResponses() == 3){
            finishRound(g);
        }

    }

    private void finishRound(Game g) {
        for(String username : loggedClients.keySet()) {
            Round r = roundRepository.findOne(g.getId(), username, g.getCurrentRound());
            String word = r.getWord();
            String letters = r.getLetters();
            Integer points = 0;

            Integer wordPoints = wordRepository.findPoints(letters, word);
            if(wordPoints != -1){
                List<String> lettersList = Arrays.asList(letters.split(","));
                List<String> wordList = Arrays.asList(word.split(""));
                points = wordPoints - (lettersList.size() - wordList.size());
            }
            
            r.setPoints(points);
            roundRepository.update(r);
            
        }
        
        if(g.getCurrentRound() != 4)
            startRound(g.getId());
        else finishGame(g.getId());
    }

    private void finishGame(Integer id) {
        Map<String, Integer> clasament = new HashMap<>();
        for(String username : loggedClients.keySet()){
            Integer points = 0;
            for (int i = 1; i < 5; i++) {
                Round r = roundRepository.findOne(id, username, i);
                points += r.getPoints();
            }
            clasament.put(username, points);
        }
        List<Map.Entry<String, Integer>> list = new ArrayList<>(clasament.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Clasament c = new Clasament(id, list.get(2).getKey(), list.get(1).getKey(), list.get(0).getKey(), 
                list.get(2).getValue(), list.get(1).getValue(), list.get(0).getValue());

        clasamentRepository.add(c);
        showClasament(c);
    }

    private void showClasament(Clasament c) {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

        executor.execute(()->{
            try{
                System.out.println("notifying clasament...");
                for(String username : loggedClients.keySet())
                    loggedClients.get(username).finalClasament(c);
            }catch (Exception e){
                System.out.println("error notifying player...");
            }
        });

        executor.shutdown();
    }

    private void startRound(Integer id) {
        Game g = gameRepository.findOne(id);
        g.setCurrentRound(g.getCurrentRound() + 1);
        g.setSentResponses(0);
        gameRepository.update(g);

        Collections.shuffle(letters);
        for(String username : loggedClients.keySet()){
            Round r = new Round(id, g.getCurrentRound(), username, letters.get(0), 0);
            roundRepository.add(r);
        }

        sendLetters(letters.get(0), g);
    }

    private void sendLetters(String s, Game g) {
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);

        executor.execute(()->{
            try{
                for(String username : loggedClients.keySet()) {
                    Integer points = 0;
                    if (g.getCurrentRound() > 1) {
                        for (int i = 1; i <= g.getCurrentRound(); i++) {
                            Round rnd = roundRepository.findOne(g.getId(), username, i);
                            points += rnd.getPoints();
                        }
                    }
                    System.out.println("sending letter to " + username);
                    loggedClients.get(username).newRound(g.getId(), s, points);
                }
            }catch (Exception e){
                System.out.println("error notifying player..." + e.getMessage());
            }
        });

        executor.shutdown();
    }
}

