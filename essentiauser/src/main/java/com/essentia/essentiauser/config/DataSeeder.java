package com.essentia.essentiauser.config;

import com.essentia.essentiauser.entity.*;
import com.essentia.essentiauser.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Seeds the database with test data on first startup.
 *
 * Only active when the property app.seed-data=true is set,
 * typically via Docker environment variable: APP_SEED_DATA=true
 *
 * If the DB already contains brands, seeding is skipped (idempotent).
 */
@Component
@ConditionalOnProperty(name = "app.seed-data", havingValue = "true")
public class DataSeeder implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(DataSeeder.class);

    @Autowired private BrandRepository            brandRepository;
    @Autowired private ParfumerRepository         parfumerRepository;
    @Autowired private PerfumeNoteRepository      perfumeNoteRepository;
    @Autowired private PerfumeRepository          perfumeRepository;
    @Autowired private PerfumePrfNotesRepository  perfumePrfNotesRepository;
    @Autowired private UserRepository             userRepository;
    @Autowired private ReviewRepository           reviewRepository;
    @Autowired private PasswordEncoder            passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (brandRepository.count() > 0) {
            logger.info("[DataSeeder] Database already seeded — skipping.");
            return;
        }
        logger.info("[DataSeeder] Seeding database with test data...");
        seedAll();
        logger.info("[DataSeeder] Done: {} brands | {} parfumers | {} notes | {} perfumes | {} users | {} reviews",
                brandRepository.count(), parfumerRepository.count(), perfumeNoteRepository.count(),
                perfumeRepository.count(), userRepository.count(), reviewRepository.count());
    }

    private void seedAll() {

        // ── Brands ────────────────────────────────────────────────────────────
        Brand dior     = brandRepository.save(new Brand("Dior",
                "Maison Christian Dior, icona della moda e della profumeria francese.",
                "Francese"));
        Brand chanel   = brandRepository.save(new Brand("Chanel",
                "La maison Chanel, sinonimo di eleganza intramontabile.",
                "Francese"));
        Brand guerlain = brandRepository.save(new Brand("Guerlain",
                "Una delle case profumiere più antiche di Francia, fondata nel 1828.",
                "Francese"));
        Brand creed    = brandRepository.save(new Brand("Creed",
                "Casa profumiera di lusso britannica con tradizione secolare.",
                "Britannico"));
        Brand mugler   = brandRepository.save(new Brand("Thierry Mugler",
                "Brand d'avanguardia noto per fragranze audaci e iconiche.",
                "Francese"));
        Brand mfk      = brandRepository.save(new Brand("Maison Francis Kurkdjian",
                "Profumeria artistica fondata dal maestro Francis Kurkdjian.",
                "Francese"));

        // ── Parfumers ─────────────────────────────────────────────────────────
        Parfumer demachy  = parfumerRepository.save(new Parfumer("François Demachy",
                "Maestro profumiere di Dior dal 2006, precedentemente in Chanel.",
                "Francese"));
        Parfumer polge    = parfumerRepository.save(new Parfumer("Olivier Polge",
                "Profumiere capo di Chanel dal 2015, figlio del leggendario Jacques Polge.",
                "Francese"));
        Parfumer wasser   = parfumerRepository.save(new Parfumer("Thierry Wasser",
                "Profumiere capo di Guerlain dal 2008, custode di un patrimonio olfattivo unico.",
                "Francese"));
        Parfumer ecreed   = parfumerRepository.save(new Parfumer("Erwin Creed",
                "Sesta generazione della famiglia Creed, custode dell'eredità del brand.",
                "Britannico"));
        Parfumer morillas = parfumerRepository.save(new Parfumer("Alberto Morillas",
                "Leggendario profumiere freelance, tra i più prolifici al mondo.",
                "Spagnolo"));

        // ── Fragrance Notes ───────────────────────────────────────────────────
        // type → 1 = top · 2 = heart · 3 = base
        PerfumeNote bergamotto = perfumeNoteRepository.save(new PerfumeNote("Bergamotto",
                "Agrume fresco e luminoso, caratteristico di molti profumi maschili."));
        PerfumeNote limone     = perfumeNoteRepository.save(new PerfumeNote("Limone",
                "Nota citrica vivace e fresca, immediata e solare."));
        PerfumeNote pompelmo   = perfumeNoteRepository.save(new PerfumeNote("Pompelmo",
                "Agrume energico con una leggera nota amara e acquosa."));
        PerfumeNote peppoRosa  = perfumeNoteRepository.save(new PerfumeNote("Pepe Rosa",
                "Spezia vivace e fruttata, con sfumature floreali e balsamiche."));
        PerfumeNote mela       = perfumeNoteRepository.save(new PerfumeNote("Mela",
                "Frutto fresco e fruttato, dolce con tocco agrumato."));
        PerfumeNote rosa       = perfumeNoteRepository.save(new PerfumeNote("Rosa",
                "La regina dei fiori, simbolo universale di femminilità ed eleganza."));
        PerfumeNote gelsomino  = perfumeNoteRepository.save(new PerfumeNote("Gelsomino",
                "Fiore bianco seducente, cuore olfattivo di molti grandi profumi."));
        PerfumeNote iris       = perfumeNoteRepository.save(new PerfumeNote("Iris",
                "Nota cipriata, vellutata e sofisticata, tipica delle fragranze di lusso."));
        PerfumeNote violetta   = perfumeNoteRepository.save(new PerfumeNote("Violetta",
                "Fiore delicato con note polverose e leggermente dolci."));
        PerfumeNote ylangYlang = perfumeNoteRepository.save(new PerfumeNote("Ylang Ylang",
                "Fiore tropicale esotico e sensuale, con note cremose e floreali."));
        PerfumeNote lavanda    = perfumeNoteRepository.save(new PerfumeNote("Lavanda",
                "Erba aromatica e balsamica, classica nelle fragranze maschili fougère."));
        PerfumeNote muschio    = perfumeNoteRepository.save(new PerfumeNote("Muschio",
                "Base morbida e calda, dona persistenza e avvolgenza al profumo."));
        PerfumeNote ambra      = perfumeNoteRepository.save(new PerfumeNote("Ambra",
                "Nota calda, resinosa e sensuale. Spina dorsale degli orientali."));
        PerfumeNote cedro      = perfumeNoteRepository.save(new PerfumeNote("Cedro",
                "Legno secco e pulito, note maschili con tocco fresco."));
        PerfumeNote sandalo    = perfumeNoteRepository.save(new PerfumeNote("Sandalo",
                "Legno cremoso e burroso, base di lusso per eccellenza."));
        PerfumeNote patchouli  = perfumeNoteRepository.save(new PerfumeNote("Patchouli",
                "Nota terrosa e scura, molto persistente, iconica negli anni '70."));
        PerfumeNote vaniglia   = perfumeNoteRepository.save(new PerfumeNote("Vaniglia",
                "Nota dolce, calda e gourmand. La più amata nelle basi orientali."));

        // ── Perfumes ──────────────────────────────────────────────────────────

        // 1. Sauvage — Dior
        Perfume sauvage = perfumeRepository.save(new Perfume(
                "Sauvage",
                "Icona della profumeria maschile contemporanea. Ampio, fresco e selvatico come le pianure sotto un cielo al crepuscolo. " +
                "Bergamotto dalla Calabria e Ambroxan, una molecola di ambra potente e duratura.",
                dior, List.of(demachy)));
        savePrfNotes(sauvage,
                List.of(bergamotto, peppoRosa),
                List.of(lavanda),
                List.of(cedro, ambra));

        // 2. J'adore — Dior
        Perfume jadore = perfumeRepository.save(new Perfume(
                "J'adore",
                "Un bouquet floreale luminoso e femminile. Tra i profumi da donna più venduti al mondo. " +
                "Un inno alla femminilità moderna, fresco e seducente.",
                dior, List.of(demachy)));
        savePrfNotes(jadore,
                List.of(ylangYlang, bergamotto),
                List.of(gelsomino, rosa),
                List.of(muschio, vaniglia));

        // 3. Bleu de Chanel — Chanel
        Perfume bleuChanel = perfumeRepository.save(new Perfume(
                "Bleu de Chanel",
                "Un fougère aromatico dall'eleganza senza tempo. La freschezza degli agrumi incontra " +
                "la sensualità del legno di sandalo. Sofisticato, libero, deciso.",
                chanel, List.of(polge)));
        savePrfNotes(bleuChanel,
                List.of(pompelmo, limone),
                List.of(gelsomino, iris),
                List.of(sandalo, cedro));

        // 4. Chance Eau Tendre — Chanel
        Perfume chance = perfumeRepository.save(new Perfume(
                "Chance Eau Tendre",
                "Fresco, fruttato e floreale. Leggero come un colpo di fortuna primaverile. " +
                "Una versione giocosa e ottimista del grande classico Chance.",
                chanel, List.of(polge)));
        savePrfNotes(chance,
                List.of(pompelmo),
                List.of(rosa, iris),
                List.of(muschio, ambra));

        // 5. Shalimar — Guerlain
        Perfume shalimar = perfumeRepository.save(new Perfume(
                "Shalimar",
                "Il profumo dei profumi. Un orientale leggendario nato nel 1925, ispirato ai giardini del Taj Mahal " +
                "e all'amore dell'imperatore Shah Jahan. Bergamotto e vaniglia in un abbraccio eterno.",
                guerlain, List.of(wasser)));
        savePrfNotes(shalimar,
                List.of(bergamotto, limone),
                List.of(rosa, iris),
                List.of(vaniglia, ambra, muschio));

        // 6. Aventus — Creed
        Perfume aventus = perfumeRepository.save(new Perfume(
                "Aventus",
                "Il re dei profumi maschili di lusso. Fruttato, affumicato, potente. " +
                "Ispirato alla vita e alla vittoria di Napoleone Bonaparte. Ogni lotto è leggermente unico.",
                creed, List.of(ecreed, morillas)));
        savePrfNotes(aventus,
                List.of(bergamotto, mela, peppoRosa),
                List.of(gelsomino, iris),
                List.of(muschio, patchouli, ambra));

        // 7. Angel — Thierry Mugler
        Perfume angel = perfumeRepository.save(new Perfume(
                "Angel",
                "Il pioniere dei profumi gourmand. Rivoluzionario e divisivo alla sua uscita nel 1992. " +
                "Patchouli scuro e vaniglia dolce, un contrasto che ha cambiato la profumeria per sempre.",
                mugler, List.of(morillas)));
        savePrfNotes(angel,
                List.of(bergamotto),
                List.of(violetta, rosa),
                List.of(patchouli, vaniglia, muschio));

        // 8. Baccarat Rouge 540 — Maison Francis Kurkdjian
        Perfume baccaratRouge = perfumeRepository.save(new Perfume(
                "Baccarat Rouge 540",
                "Il profumo del decennio. Floreale-legnoso-ambrato, con una scia irresistibile e riconoscibilissima. " +
                "Creato nel 2015 per i 250 anni della cristalleria Baccarat.",
                mfk, List.of(morillas)));
        savePrfNotes(baccaratRouge,
                List.of(pompelmo),
                List.of(gelsomino, ylangYlang),
                List.of(cedro, ambra, muschio));

        // ── Users ─────────────────────────────────────────────────────────────

        // Admin user (role = true → ROLE_ADMIN)
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("Admin2024!"));
        admin.setName("Admin");
        admin.setSurname("Essentia");
        admin.setRole(true);
        userRepository.save(admin);

        // Regular user (role = false → ROLE_USER)
        User mario = new User();
        mario.setUsername("mario");
        mario.setPassword(passwordEncoder.encode("Mario2024!"));
        mario.setName("Mario");
        mario.setSurname("Rossi");
        mario.setRole(false);
        userRepository.save(mario);

        // ── Reviews ───────────────────────────────────────────────────────────
        // Note: Review constructor has a bug (sillage/longevity not assigned)
        //       → using setters explicitly until fixed (ISSUES.md #2)
        saveReview(sauvage,      mario, "Classico intramontabile",
                "Fresco e potente, perfetto per ogni occasione. Non stanca mai. La proiezione è stratosferica.",
                5, 2, true, 5, 5);

        saveReview(jadore,       mario, "Femminilità allo stato puro",
                "Un bouquet floreale magnifico, elegante e luminoso. Scia lunga e raffinata.",
                4, 1, false, 3, 4);

        saveReview(aventus,      mario, "Il re non si discute",
                "Costoso ma ogni centesimo è giustificato. Unico, iconico, inimitabile.",
                5, 1, true, 4, 5);

        saveReview(baccaratRouge, mario, "Il profumo del momento",
                "Dolce, metallico e irresistibile. Polarizza ma conquista inevitabilmente.",
                4, 3, false, 5, 4);

        saveReview(shalimar,     mario, "Un viaggio nel tempo",
                "Orientale complesso e profondo. Richiede maturità per essere apprezzato pienamente.",
                5, 4, false, 4, 5);
    }

    // ── Helper: saves fragrance notes for the olfactory pyramid ──────────────
    private void savePrfNotes(Perfume perfume,
                               List<PerfumeNote> testa,    // type = 1
                               List<PerfumeNote> cuore,    // type = 2
                               List<PerfumeNote> fondo) {  // type = 3
        testa.forEach(n -> perfumePrfNotesRepository.save(new PerfumePrfNotes(perfume, n, 1)));
        cuore.forEach(n -> perfumePrfNotesRepository.save(new PerfumePrfNotes(perfume, n, 2)));
        fondo.forEach(n -> perfumePrfNotesRepository.save(new PerfumePrfNotes(perfume, n, 3)));
    }

    // ── Helper: creates a review using setters (workaround for bug #2) ───────
    private void saveReview(Perfume perfume, User user, String title, String description,
                             int vote, int seasonality, boolean gender, int sillage, int longevity) {
        Review review = new Review();
        review.setPerfume(perfume);
        review.setUser(user);
        review.setTitle(title);
        review.setDescription(description);
        review.setVote(vote);
        review.setSeasonality(seasonality);
        review.setGender(gender);
        review.setSillage(sillage);
        review.setLongevity(longevity);
        reviewRepository.save(review);
    }
}
