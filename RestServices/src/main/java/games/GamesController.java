package games;

import domain.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.RoundRepository;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/games")
public class GamesController {
    private static final String template = "TemplateGame";

    @Autowired
    private RoundRepository roundRepository;

    @RequestMapping(value = "/{gameID}", method = RequestMethod.GET)
    public List<String> getLetters(@PathVariable Integer gameID) {
        List<String> players = roundRepository.getPlayers(gameID);
        List<String> letters = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            Round r = roundRepository.findOne(gameID, players.get(0), i);
            letters.add(r.getLetters());
        }

        return letters;
    }

    @RequestMapping(value = "/{gameID}/{playerID}", method = RequestMethod.GET)
    public List<Round> getPlayerStats(@PathVariable Integer gameID, @PathVariable String playerID) {
        List<Round> rounds = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Round r = roundRepository.findOne(gameID, playerID, i);
            rounds.add(r);
        }
        return rounds;
    }

}