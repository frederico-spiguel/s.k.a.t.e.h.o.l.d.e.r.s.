package com.skateholders.skateholders.config;

import com.skateholders.skateholders.models.Base;
import com.skateholders.skateholders.models.Trick;
import com.skateholders.skateholders.repositories.BaseRepository;
import com.skateholders.skateholders.repositories.TrickRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SqlDataLoader implements CommandLineRunner {

    @Autowired
    private BaseRepository baseRepository;

    @Autowired
    private TrickRepository trickRepository;

    @Override
    public void run(String... args) throws Exception {
        if (baseRepository.count() == 0 && trickRepository.count() == 0) {
            System.out.println(">>> [SqlDataLoader] Tabelas Base e Trick vazias. Povoando o MySQL...");

            // 1. Criar e salvar as Bases
            List<Base> bases = criarBases();
            baseRepository.saveAll(bases);

            // 2. Criar e salvar as Tricks, usando a lista completa
            List<Trick> tricks = criarTricks(bases);
            trickRepository.saveAll(tricks);

            System.out.println(">>> [SqlDataLoader] " + bases.size() + " bases e " + tricks.size() + " tricks salvas com sucesso!");
        } else {
            System.out.println(">>> [SqlDataLoader] Tabelas Base e Trick já populadas. Nenhuma ação necessária.");
        }
    }

    private List<Base> criarBases() {
        return List.of(
                new Base("Regular"),
                new Base("Fakie"),
                new Base("Switch"),
                new Base("Nollie")
        );
    }

    private List<Trick> criarTricks(List<Base> basesSalvas) {
        // Cria um mapa para facilitar a busca da base pelo nome
        Map<String, Base> baseMap = basesSalvas.stream()
                .collect(Collectors.toMap(Base::getDescricao, Function.identity()));

        List<Trick> tricks = new ArrayList<>();

        // --- Populando com a sua lista oficial de 37 manobras ---

        // Regular
        tricks.add(new Trick("Ollie", 1, baseMap.get("Regular")));
        tricks.add(new Trick("Shove-it", 1, baseMap.get("Regular")));
        tricks.add(new Trick("Frontside Shove-it", 2, baseMap.get("Regular")));
        tricks.add(new Trick("Backside Shove-it", 2, baseMap.get("Regular")));
        tricks.add(new Trick("Pop shove-it", 3, baseMap.get("Regular")));
        tricks.add(new Trick("Frontside pop shove-it", 4, baseMap.get("Regular")));
        tricks.add(new Trick("Backside pop shove-it", 4, baseMap.get("Regular")));
        tricks.add(new Trick("Flip", 4, baseMap.get("Regular"))); // Kickflip
        tricks.add(new Trick("Heelflip", 4, baseMap.get("Regular")));
        tricks.add(new Trick("Varial Flip", 5, baseMap.get("Regular")));
        tricks.add(new Trick("Varial Heelflip", 5, baseMap.get("Regular")));
        tricks.add(new Trick("Hardflip", 7, baseMap.get("Regular")));
        tricks.add(new Trick("Inward Heelflip", 7, baseMap.get("Regular")));
        tricks.add(new Trick("360 flip", 8, baseMap.get("Regular")));
        tricks.add(new Trick("Laser flip", 8, baseMap.get("Regular")));
        tricks.add(new Trick("BS 180 Ollie", 3, baseMap.get("Regular")));
        tricks.add(new Trick("FS 180 Ollie", 3, baseMap.get("Regular")));

        // Nollie
        tricks.add(new Trick("Nollie", 2, baseMap.get("Nollie")));
        tricks.add(new Trick("Nollie Shove-it", 3, baseMap.get("Nollie")));
        tricks.add(new Trick("Nollie FS Shove-it", 4, baseMap.get("Nollie")));
        tricks.add(new Trick("Nollie BS Shove-it", 4, baseMap.get("Nollie")));
        tricks.add(new Trick("Nollie Flip", 6, baseMap.get("Nollie")));
        tricks.add(new Trick("Nollie Heelflip", 6, baseMap.get("Nollie")));
        tricks.add(new Trick("Nollie Varial Flip", 7, baseMap.get("Nollie")));
        tricks.add(new Trick("Nollie 360 flip", 9, baseMap.get("Nollie")));
        tricks.add(new Trick("Nollie Laser flip", 9, baseMap.get("Nollie")));

        // Fakie
        tricks.add(new Trick("Fakie Ollie", 2, baseMap.get("Fakie")));
        tricks.add(new Trick("Fakie Shove-it", 3, baseMap.get("Fakie")));
        tricks.add(new Trick("Fakie FS Shove-it", 4, baseMap.get("Fakie")));
        tricks.add(new Trick("Fakie BS Shove-it", 4, baseMap.get("Fakie")));
        tricks.add(new Trick("Fakie Flip", 5, baseMap.get("Fakie")));
        tricks.add(new Trick("Fakie Heelflip", 5, baseMap.get("Fakie")));
        tricks.add(new Trick("Fakie Varial Flip", 6, baseMap.get("Fakie")));
        tricks.add(new Trick("Fakie 360 flip", 8, baseMap.get("Fakie")));

        // Switch
        tricks.add(new Trick("Switch Ollie", 3, baseMap.get("Switch")));
        tricks.add(new Trick("Switch Flip", 6, baseMap.get("Switch")));
        tricks.add(new Trick("Switch Heelflip", 6, baseMap.get("Switch")));

        return tricks;
    }
}