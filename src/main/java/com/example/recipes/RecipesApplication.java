package com.example.recipes;

import com.example.recipes.persistance.model.Recipe;
import com.example.recipes.persistance.model.RecipeCategory;
import com.example.recipes.persistance.model.RecipeDifficulty;
import com.example.recipes.persistance.model.RecipePreparationTime;
import com.example.recipes.persistance.repo.RecipeCategoryRepository;
import com.example.recipes.persistance.repo.RecipeDifficultyRepository;
import com.example.recipes.persistance.repo.RecipePreparationTimeRepository;
import com.example.recipes.persistance.repo.RecipeRepository;
import com.example.recipes.rest.dto.RecipeDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class RecipesApplication {
    private static final Logger log = LoggerFactory.getLogger(RecipesApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RecipesApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        PropertyMap<Recipe, RecipeDto> recipeMapToDto = new PropertyMap<Recipe, RecipeDto>() {
            protected void configure() {
                map().setCategoryDescRO(source.getRecipeCategory().getDesc());
                map().setPreparationTimeDescRO(source.getRecipePreparationTime().getDesc());
                map().setDifficultyDescRO(source.getRecipeDifficulty().getDesc());
            }
        };

        Converter<Long, RecipeCategory> convertToRecipeCategory = context -> {
            RecipeCategory rc = new RecipeCategory();
            rc.setId(context.getSource());
            return rc;
        };

        Converter<Long, RecipeDifficulty> convertToRecipeDifficulty = context -> {
            RecipeDifficulty rd = new RecipeDifficulty();
            rd.setId(context.getSource());
            return rd;
        };

        Converter<Long, RecipePreparationTime> convertToRecipePreparationTime = context -> {
            RecipePreparationTime rpt = new RecipePreparationTime();
            rpt.setId(context.getSource());
            return rpt;
        };

        PropertyMap<RecipeDto, Recipe> recipeMap = new PropertyMap<RecipeDto, Recipe>() {
            @Override
            protected void configure() {
                using(convertToRecipeCategory).map(source.getIdCategory()).setRecipeCategory(null);
                using(convertToRecipeDifficulty).map(source.getIdDifficulty()).setRecipeDifficulty(null);
                using(convertToRecipePreparationTime).map(source.getIdPreparationTime()).setRecipePreparationTime(null);
            }
        };

        modelMapper.addMappings(recipeMap);
        modelMapper.addMappings(recipeMapToDto);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.validate();

        return modelMapper;
    }

    @Bean
    public CommandLineRunner init(RecipeRepository rep, RecipeCategoryRepository catRep,
                                  RecipePreparationTimeRepository ptRep, RecipeDifficultyRepository dfRep) {
        return (args) -> {
            RecipePreparationTime ptQuick = ptRep.save(new RecipePreparationTime("Quick", "Y", 10L));
            RecipePreparationTime ptMedium = ptRep.save(new RecipePreparationTime("Medium", "Y", 20L));
            RecipePreparationTime ptLong = ptRep.save(new RecipePreparationTime("Long", "Y", 30L));

            RecipeDifficulty dfEasy = dfRep.save(new RecipeDifficulty("Easy", "Y", 10L));
            RecipeDifficulty dfMedium = dfRep.save(new RecipeDifficulty("Medium", "Y", 20L));
            RecipeDifficulty dfHard = dfRep.save(new RecipeDifficulty("Hard", "Y", 30L));

            RecipeCategory rc = catRep.save(new RecipeCategory("Zelenina", "Y", 10L));
            rep.save(new Recipe("Zemiaky s bryndzou",
                    "1kg nových zemiakov, 2 lyžice masla, 150-300g slaniny, 150-300g bryndze, pažítka (petržlenová vňať), soľ, (cmar).",
                    "Zemiaky dobre umyjeme, slaninku nakrájame na kocky a popražíme na masle. Pridáme zemiaky a dusíme do mäkka. Na záver pridáme bryndzu a pažítku, podľa potreby dosolíme. Môžeme podávať s cmarom.",
                    rc,
                    ptQuick,
                    dfMedium));
            rep.save(new Recipe("Zapekané zemiaky s cibuľkou",
                    "Zemiaky, soľ, olej, cibuľa, kyslá smotana.",
                    "Ošúpané a nakrájané zemiaky uložíme do zapekacej misy. Osolíme a premiešame s olejom a s cibuľou nakrájanou na kolieska. Zapekáme, pokiaľ zemiaky nie sú hotové. Podávame s kyslou smotanou.",
                    rc,
                    ptMedium,
                    dfEasy));
            rep.save(new Recipe("Ratatouille",
                    "Olivový olej, 4 cibule , (2 baklažány), 4 papriky, 4 rajčiny, 4 cukiny, 8 strúčkov cesnaku, 2 plechovky krájaných rajčín po 400 g, mleté čierne korenie, soľ, rozmarín (mletý), tymián, (bagety).",
                    "Na olivovom oleji popražíme najprv nakrájanú cibuľu a nasolený odkvapkaný baklažán. Potom pridáme nakrájané papriky, rajčiny a nakoniec cukiny a 6 strúčikov pretlačeného cesnaku. Pridáme konzervované rajčiny, posolíme, pokoreníme a dochutíme bylinkami. Poodkryté dusíme asi pol hodiny, aby sa vyvarila voda. Na záver pridáme zvyšný cesnak a podávame s bagetami.",
                    rc,
                    ptMedium,
                    dfEasy));

            rc = catRep.save(new RecipeCategory("Ryby", "Y", 20L));
            rep.save(new Recipe("Rybací koláč",
                    "1-2 kg zemiakov, 4 vajcia, 2 cibule, 2 mrkvy, olivový olej, špenát (čerstvý alebo mrazený), parmezán (alebo iný syr), 2 šľahačkové smotany, anglická horčica, petržlenovú vňať, soľ, 1 kg ryby, maslo, (hrášok).",
                    "Zemiaky ošúpeme a dáme uvariť. Takisto dáme uvariť vajcia a nakrájame ich na kúsky. Medzitým popražíme cibuľu a mrkvu na olivovom oleji, pridáme špenát, parmezán, šľahačkovú smotanu, anglickú horčicu a petržlenovú vňať. Zmes povaríme, osolíme a okoreníme podľa chuti a ponoríme do nej kúsky ryby a nakrájané vajíčka. Zemiaky a maslo rozmiešame na pyré a rozotrieme na vrch zmesi. Pečieme v trúbe asi 30 minút na 230 °C. Podávame napríklad s hráškom.",
                    rc,
                    ptLong,
                    dfHard));

            rc = catRep.save(new RecipeCategory("Prílohy", "Y", 30L));
            rep.save(new Recipe("Zelé s pretlakom",
                    "Cibuľa, olej, 0,5 kg kapusty, drvená rasca, bobkový list, vývar, rajčinový pretlak, múka, (mlieko), cukor, ocot, (fašírka, špekačka), chlieb.",
                    "Nakrájanú cibuľu popražíme na oleji, pridáme nakrájanú kapustu, rascu a bobkový list, podlejeme vývarom a dusíme do mäkka. Na záver rozmiešame v kapuste rajčinový pretlak a zápražku z múky a vody (mlieka). Ochutíme cukrom a octom. Podávame s fašírkou a chlebom.",
                    rc,
                    ptLong,
                    dfEasy));
            rep.save(new Recipe("Kelový prívarok",
                    "Kel, zemiaky, soľ, rasca celá, mleté čierne korenie, olej, cibuľka, hladká múka, sladká červená paprika, majoránka, 2-3 strúčky cesnaku, dochucovadlá, chlieb, údeniny.",
                    "Nakrájaný kel dáme variť do osolenej vody. Po pár minútach varenia pridáme zopár zemiakov nakrájaných na kocky, celú rascu a mleté čierne korenie. Dusíme pokiaľ sa zemiaky neuvaria. Pripravíme si zápražku z oleja, cibuľky, hladkej múky a sladkej mletej papriky. Keď sú zemiaky uvarené, pridáme zápražku do kelu spolu s majoránkou a rozotreným cesnakom. Na záver ochutíme (zeleninový bujón, vegeta, polievkové korenie). Podávame s chlebom a opraženou klobásou, alebo špekačkou.",
                    rc,
                    ptLong,
                    dfEasy));
            rep.save(new Recipe("Šenkérska kapusta",
                    "100 g masti (olej), 200 g cibule, 200 g cukru, 1,5 kg kyslej kapusty, 200 g veľký zemiak.",
                    "Nakrájanú cibuľku opražíme na oleji, pridáme kryštálový cukor a necháme skaramelizovať. Pridáme nakrájanú kyslú kapustu bez nálevu (odložíme, ak by bolo treba dochucovať), podlejeme vodou a necháme aspoň hodinu dusiť. Keď je kapusta mäkká, pridáme najemno nakrájaný zemiak a varíme ešte 15 minút. Podľa potreby dochutíme.",
                    rc,
                    ptLong,
                    dfMedium));

            rc = catRep.save(new RecipeCategory("Nátierky", "Y", 40L));
            rep.save(new Recipe("Cesnaková nátierka",
                    "Tatárska omáčka (Helmans), cesnak, tvrdý syr, (kyslá smotana, jogurt, bylinky, tavené syry).",
                    "Tatársku omáčku zmiešame s pretlačeným cesnakom a obľúbeným nastrúhaným tvrdým syrom. Podľa chuti môžeme vylepšiť ďalšími prísadami.",
                    rc,
                    ptQuick,
                    dfEasy));

            rc = catRep.save(new RecipeCategory("Cestoviny", "Y", 50L));
            rep.save(new Recipe("Cestoviny s nivovou omáčkou",
                    "Kvalitné cestoviny, soľ, niva, šľahačková smotana, sušené oregano, cesnak, kečup.",
                    "Cestoviny uvaríme v slanej vode. Medzitým v hrnci roztlačíme syr niva, pridáme šľahačkovú smotanu a dáme variť na mierny plameň. Pridáme oregano a rozotrený cesnak. Miešame, kým sa syr nerozpustí. Nakoniec cestoviny zalejeme omáčkou, môžeme pridať kečup na zjemnenie. (Omáčka z nivy sa dá použiť aj ku kuraciemu mäsu)",
                    rc,
                    ptQuick,
                    dfEasy));
            rep.save(new Recipe("Cestoviny so špenátom",
                    "Kvalitné cestoviny, soľ, mrazený špenát, olej, múka, zeleninový bujón, bazalka, mleté čierne korenie, tymián... , kyslá smotana, 3-4 strúčiky cesnaku, bambino, konzerva kvalitnej sladkej kukurice.",
                    "Cestoviny uvaríme v slanej vode. Medzitým špenát zohrejeme v hrnci, pridáme zápražku z oleja a múky, ktorú sme už zriedili s vodou, zeleninový bujón a korenie a bylinky podľa chuti. Podľa potreby osolíme a vmiešame kyslú smotanu. Odstavíme z ohňa a pridáme ešte pretlačený cesnak, bambino a kukuricu. Všetko dobre premiešame a podávame s cestovinami.",
                    rc,
                    ptMedium,
                    dfMedium));
            rep.save(new Recipe("Cestoviny penne",
                    "Kvalitné cestoviny, soľ, olej (olivový), mrazený hrášok, šunka, kyslá smotana.",
                    "Cestoviny uvaríme v slanej vode. Medzitým na oleji rozmrazíme hrášok, podusíme spolu so šunkou. Potom pridáme kyslú smotanu a prehrejeme. Zmiešame s cestovinami a podávame.",
                    rc,
                    ptQuick,
                    dfEasy));

            rc = catRep.save(new RecipeCategory("Bravčové mäso", "Y", 60L));
            rep.save(new Recipe("Bravčové fašírky",
                    "2 rožky, 150 ml mlieka, 800 g mletého mäsa, 4 strúčiky cesnaku, 1 cibuľa, 2 vajcia, mleté čierne korenie, soľ (1 lyžička), strúhanka, olej.",
                    "Rožky bez končekov zalejeme mliekom a necháme rozmočiť. Pridáme mäso, pretlačený cesnak, nadrobno nakrájanú cibuľu, vajcia, soľ, a čierne korenie. Premiešame, vytvarujeme a obalíme v strúhanke. Opražíme v oleji (placaté 5 minút z každej strany) alebo pečieme na vymastenom plechu na 200 °C  aspoň 30 minút a podávame napríklad so zemiakovým pyré a zaváranými uhorkami.",
                    rc,
                    ptMedium,
                    dfMedium));
            rep.save(new Recipe("Moravský vrabec",
                    "1 kg bravčové plece, 2 lyžice masti, 3 cibule, 8 strúčkov cesnaku, soľ (20 g na kilo mäsa), 1 lyžička drvenej rasce a mleté čierne korenie (pivo na podlievanie, ak treba).",
                    "Mäso nakrájame na kocky 3x3 cm a dáme pražiť na masti spolu s cibuľou a nakrájaným cesnakom. Počas praženia pridáme soľ, rascu a mleté čierne korenie. Keď sa mäso zo všetkých strán zatiahne a pustí šťavu, prikryjeme a necháme asi 45 minút dusiť. Na záver mäso zapečieme v trúbe aby bolo chrumkavé.",
                    rc,
                    ptLong,
                    dfEasy));
            rep.save(new Recipe("Panenka s bryndzovou omáčkou",
                    "500 g bravčová panenka, soľ, mleté čierne korenie, olej, 1 šalotka, 120 g čerstvých húb, 300 ml vývaru, 125 ml smotany na šľahanie, 80 g masla, 150 g bryndze, (zemiaky).",
                    "Panenku osolíme, okoreníme a prudko opečieme na oleji a pečieme v rúre na 160 °C asi 15 minút. Vo výpeku opečieme nakrájanú šalotku a huby, pridáme maslo a múku, všetko zalejeme vývarom. Krátko povaríme, primiešame bryndzu a smotanu. Podávame s opekanými zemiakmi.",
                    rc,
                    ptMedium,
                    dfMedium));

            rc = catRep.save(new RecipeCategory("Hydina", "Y", 70L));
            rep.save(new Recipe("Kura na sladkokyslo",
                    "250 ml kečupu, 250 ml vody, 1 lyžica worcesterskej omáčky, 4 lyžice cukru, 2 lyžice octu, 1 lyžica horčice, soľ, mleté čierne korenie, 4 lyžice masla, 4 kuracie prsia, 2 cibule, 250 ml zeleru, ryža.",
                    "Rúru dáme zohriať na 175 °C. Zmiešame prvých 6 surovín na omáčku, osolíme a okoreníme podľa chuti. Kuracie prsia opečieme na masle a odložíme. Vo výpeku opražíme cibuľku a zeler a potom ich pridáme do omáčky. Mierne povaríme 10 minút. Pridáme kuracie prsia a pečieme v rúre 1 hodinu. Občas premiešame. Podávame s ryžou.",
                    rc,
                    ptLong,
                    dfMedium));
            rep.save(new Recipe("Morčacie stehná na cibuľke",
                    "Olivový olej, morčacie stehná, 15 šalotiek (cibuliek), 2 strúčiky cesnaku, 300 ml vývaru, 400 g konzervovaných rajčín, 4 stonky tymiánu (alebo sušený), 2 bobkové listy, soľ, mleté čierne korenie. ",
                    "V pekáči na oleji opražíme morčacie do hneda, mäso vyberieme a dáme asi na 10 minút opiecť celé cibuľky. Pridáme celé strúčky cesnaku a ešte chvíľu opekáme. Potom vlejeme vývar, rajčiny, vrátime mäso a dochutíme bylinkami, soľou a korením. Prikryjeme a dáme do rozohriatej trúby. Pečieme najprv 30-45 minút na 220 °C, potom 60-90 minút na 180 °C. Posledných 30 minút pečieme odokryté. Podávame s ryžou alebo zemiakovou kašou.",
                    rc,
                    ptLong,
                    dfMedium));
            rep.save(new Recipe("Tokáň",
                    "Olej, 2 veľké cibule, 600 g morčacie stehno vykostené, 4 kapie, 400 g konzervovaných rajčín, (pretlak ?), soľ, 3 strúčiky cesnaku, mleté čierne korenie, 2 dcl vína.",
                    "Na oleji opražíme nakrájanú cibuľku, pridáme na rezance nakrájané mäso. Keď sa mäso trochu opečie, pridáme nakrájané papriky, neskôr aj rajčiny, soľ, korenie a pretlačený cesnak. Zmes podlejeme vínom a podusíme. Podávame s ryžou.",
                    rc,
                    ptLong,
                    dfMedium));

            rc = catRep.save(new RecipeCategory("Vnútornosti", "Y", 80L));
            rep.save(new Recipe("Vnútornosti na cibuľke",
                    "Cibuľa, olej, drvená rasca, kuracie vnútornosti (pečienky, srdcia...), (vývar), soľ, (korenie, múka, smotana...)",
                    "Nakrájame veľa cibuľky a opražíme ju na oleji. Pridáme vnútornosti, rascu a dusíme (voda, vývar), pokiaľ nie sú všetky kúsky mäsa mäkké. Osolíme a okoreníme podľa potreby. Môžeme zahustiť múkou, pridať smotanu... Podávame s ryžou a kyslými uhorkami, alebo čerstvým uhorkovým šalátom.",
                    rc,
                    ptQuick,
                    dfEasy));
            rep.save(new Recipe("Srdce na smotane",
                    "1-2 kg mäsa (hovädzie/bravčové srdce/jazyky), mrkva, petržlen, zeler - spolu 600 g, 1 cibuľa, 2-3 strúčiky cesnaku, 2 bobkové listy, soľ, 2 lyžice octu, celé čierne a nové korenie, 1 lyžica horčice, 1-2 lyžice cukru, smotana na varenie (1 šľahačková + 1 kyslá), 2-3 lyžice hladkej múky, mleté čierne korenie, knedľa.",
                    "Mäso, koreňovú zeleninu, cibuľu, cesnak, bobkový list, soľ, ocot a celé korenie dáme variť do hrnca s vodou a varíme 2 hodiny – hovädzie 3-4. Mäso a korenie vyberieme, zeleninu rozmixujeme, pridáme horčicu, cukor. Mäso nakrájame a vrátime naspäť. Smotanu zmiešame s múkou, vlejeme do omáčky a dochutíme. Varíme ešte asi 10 minút. Podávame s knedľou alebo cestovinou.",
                    rc,
                    ptLong,
                    dfMedium));

            rc = catRep.save(new RecipeCategory("Hovädzie mäso", "Y", 90L));
            rep.save(new Recipe("Stroganoff s hráškom",
                    "Olivový olej, 700 g roštenky/hovädzie zadné, ½ lyžičky soli, čerstvo mleté čierne korenie, 1 cibuľa, 200 g šampiónov, 250 g cestovín (tagliatelle), 2 lyžice hladkej múky, 300 ml vývaru, 200 g mrazeného hrášku, 150 ml kyslej smotany, 2 lyžice hrubozrnnej horčice, 30 g masla, (mak, tymián, zvyšné huby).",
                    "Mäso nakrájame na prúžky a opekáme so soľou a korením 2-3 minúty, vyberieme a opečieme nakrájanú cibuľu. Pridáme nakrájané huby a dusíme 5 minút. Dáme variť cestoviny. K hubám pridáme múku a necháme smažiť 3 minúty. Postupne pridávame vývar. Vrátime späť mäso, pridáme hrášok a varíme 5 minút. Vmiešame  smotanu (odložíme 2 lyžice) a horčicu. Cestoviny zmiešame s maslom (makom) a vyklopíme na misu. Doprostred pridáme mäsovú zmes, ozdobíme smotanou, bylinkami a hubami.",
                    rc,
                    ptMedium,
                    dfMedium));
            rep.save(new Recipe("Roštenka",
                    "700 g roštenky, soľ, čerstvo mleté čierne korenie, hladká  múka, olej, 2 cibule,  (slanina), hovädzí bujón, 1-2 hrste sušených hríbov, (hrášok v konzerve), 50 g masla, ryža, kyslé uhorky.",
                    "Mäso nakrájame na plátky, naklepeme, posolíme, pokoreníme, obalíme v múke a osmažíme na oleji. Mäso odložíme a vo výpeku opražíme nakrájanú cibuľu do hneda. Vrátime naspäť mäso, zalejeme vývarom, pridáme sušené huby a dusíme pokiaľ mäso nie je mäkké. Na záver pridáme (hrášok, nakrájané uhorky) zápražku z masla a múky a ešte chvíľu povaríme. Podávame s ryžou a kyslými uhorkami.",
                    rc,
                    ptLong,
                    dfMedium));
            rep.save(new Recipe("Mäso po námornícky",
                    "3-4 cibule, olej, 1,2 kg hovädzieho na dusenie (roštenka, zadné, krk...), soľ, mleté čierne korenie, 3 malé rajčinové pretlaky, 6 vajec, sterilizovaný hrášok, (ryža, strúhaný syr).",
                    "Na oleji opražíme nakrájanú cibuľu, pridáme mäso nakrájané na slížiky, hneď posolíme, pokoreníme a restujeme. Keď mäso pustí šťavu, pridáme pyré, podlejeme vodou a dusíme do mäkka. Zatiaľ uvaríme vajíčka na tvrdo a nakrájame ich na malé kúsky. K hotovému mäsu pridáme vajíčka a hrášok a ešte chvíľu povaríme. Podávame s ryžou, poprípade nastrúhaným syrom.",
                    rc,
                    ptLong,
                    dfMedium));

            rc = catRep.save(new RecipeCategory("Polievky", "Y", 100L));
            rep.save(new Recipe("Zeleninová polievka so slaninkou",
                    "Olej, 50 + 100 g slaniny, 1 cibuľa, 3 póriky, 3 zemiaky, 1,5l zeleninového vývaru, 200 ml smotany 11% (alebo aj viac), soľ, mleté čierne korenie, (chlieb, toasty).",
                    "Na oleji popražíme 50 g slaninky nakrájanej na kocky a cibuľu. Pridáme pór a zemiaky, necháme asi 5 minút dusiť. Pridáme vývar, varíme 20 minút a rozmixujeme. Medzitým opečieme prúžky slaniny. Pridáme smotanu, dochutíme a ešte prevaríme. Podávame s opekanými kúskami slaniny a pečivom.",
                    rc,
                    ptMedium,
                    dfMedium));
            rep.save(new Recipe("Minestrone",
                    "Olivový olej, 100g slaniny, 1 cibuľa, 2 strúčiky cesnaku, 300g mrkvy, 1 pór, (2-3 stopky listového zeleru), 400g konzervovaných rajčín, 400g konzervovanej fazule, 1l vývaru, 1 lyžička oregana, pol lyžičky tymiánu a rozmarínu, 1 cukina, (cestoviny), čerstvo mleté čierne korenie, 60g parmezánu.",
                    "Na oleji popražíme kocky slaniny. Pridáme nakrájanú/nastrúhanú cibuľu, cesnak, mrkvu a pór a dusíme asi 5 minút. Pridáme fazuľu, rajčiny, vývar, bylinky a necháme variť. Cukinu nakrájame a po čase (mrkva už mäkne) pridáme do polievky. Môžeme pridať aj cestoviny. Varíme ešte 10 minút. Na záver polievku okoreníme a podávame s nastrúhaným parmezánom.",
                    rc,
                    ptLong,
                    dfHard));
            rep.save(new Recipe("Špenátová polievka",
                    "Maslo, 1 cibuľa, 280 g špenátu, 1 l mlieka, soľ, mleté čierne korenie, (vegeta, polievkové korenie...).",
                    "Na masle opražíme nakrájanú cibuľku, pridáme špenát a podusíme. Vlejeme mlieko, necháme zovrieť a dochutíme. Môžeme pridať a uvariť krupicové halušky.",
                    rc,
                    ptQuick,
                    dfEasy));

            rc = catRep.save(new RecipeCategory("Sladkosti", "Y", 110L));
            rep.save(new Recipe("Žemľovka",
                    "Rožky (12-13), vajíčka (5), mlieko (1 l), cukor, (škorica), maslo (olej), tvaroh, (hrozienka), jablká (5-6 kusov).",
                    "Nakrájané rožky na tri fázy namáčame v zmesi zo žĺtkov, mlieka a 4 veľkých lyžíc cukru (prípadne škorice) a ukladáme tesne vedľa seba na vymastený pekáč. Keď pokryjeme celé dno pekáča, pridáme nadrobený tvaroh (hrozienka) a na plátky nakrájané jabĺčka. Hojne posypeme kryštálovým cukrom. Celý postup opakujeme pokiaľ nenaplníme celý pekáč tak, aby poslednú vrstvu tvorili rožky. Pečieme na 200 °C asi 30 minút. Na záver pridáme vyšľahaný sneh z bielkov, ktoré sme si odložili spolu s veľkou lyžicou cukru.",
                    rc,
                    ptLong,
                    dfMedium));
            rep.save(new Recipe("Tvarohová bábovka",
                    "160 g masla (rozpusteného), 3–4 vajcia, 250 g cukru, 500 g tvarohu, trocha soli, 200 g detskej krupice, prášok do pečiva, (kôra a šťava z polky citrónu), práškový cukor na posypanie.",
                    "Suroviny vložíme do pekárne a necháme vymiesiť (napr. režim bábovka). Potom prepneme na pečenie, pečieme zhruba 1,5 hodiny. Môžeme použiť kontrolu špajdľou.",
                    rc,
                    ptLong,
                    dfEasy));
            rep.save(new Recipe("Grilážové oplátky",
                    "25 dkg cukru, 15 dkg orechov (môže byť aj viac), konzerva Salka, 250 g masla, oplátky.",
                    "Cukor necháme skaramelizovať, pridáme na jemno nakrájané (nadrvené) orechy – nie namleté. Zapracujeme ich do karamelu a prilejeme Salko. Dobre premiešame a na záver rozpustíme v zmesi maslo. Pripravenú hmotu natrieme na oplátky, zaťažíme a necháme vychladnúť.",
                    rc,
                    ptMedium,
                    dfHard));

            log.info("Recipes created");
        };
    }

}
