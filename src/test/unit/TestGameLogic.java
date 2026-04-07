package test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import engine.map.Cell;
import engine.map.Room;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.mobile.AnimalType;
import engine.mobile.Maitre;
import engine.process.GameBuilder;
import engine.skill.Skill;
import engine.skill.SkillTypes;
import engine.strategy.PunitionRecompenseManager;
import engine.strategy.PunitionRecompenseType;
import engine.object.salon.Canape;

/**
 * Test unitaire de la logique du jeu.
 * On vérifie les calculs des Skills et le PunitionRecompenseManager.
 * 
 * Résultats calculés à la main :
 * - Skill : 0 + 30 = 30
 * - Skill : 0 + 30 - 10 = 20
 * - Skill : 0 + 60 + 50 = 110 -> borné à 100
 * - Skill : 0 - 10 -> borné à 0
 * - FRAPPER / CARESSER : le maître se déplace vers l'animal
 * - DIRE_STOP / REPRIMANDE / ENCOURAGER : le maître ne bouge pas
 */
public class TestGameLogic {

	private Room room;
	private Animal chat;
	private Maitre maitre;

	@Before
	public void prepareGame() {
		room = GameBuilder.buildRoom();
		chat = GameBuilder.buildAnimal(room, AnimalType.CHAT, "Minou");
		maitre = GameBuilder.buildMaitre(room);
	}

	// ===================== SKILL CALCULS =====================

	@Test
	public void testSkillIncrease() {
		// 0 + 30 = 30
		Skill s = new Skill(SkillTypes.Proprete);
		s.increase(30);
		assertEquals(30, s.getaccumuler());
	}

	@Test
	public void testSkillIncreaseDecrease() {
		// 0 + 30 - 10 = 20
		Skill s = new Skill(SkillTypes.Discipline);
		s.increase(30);
		s.decrease(10);
		assertEquals(20, s.getaccumuler());
	}

	@Test
	public void testSkillBorneMax() {
		// 0 + 60 + 50 = 110 -> borné à 100
		Skill s = new Skill(SkillTypes.Proprete);
		s.increase(60);
		s.increase(50);
		assertEquals(100, s.getaccumuler());
	}

	@Test
	public void testSkillBorneMin() {
		// 0 - 10 -> borné à 0
		Skill s = new Skill(SkillTypes.Obeissance);
		s.decrease(10);
		assertEquals(0, s.getaccumuler());
	}

	@Test
	public void testSkillSequence() {
		// 0 + 50 - 20 + 40 - 80 = -10 -> borné à 0
		Skill s = new Skill(SkillTypes.Discipline);
		s.increase(50);
		s.decrease(20);
		s.increase(40);
		s.decrease(80);
		assertEquals(0, s.getaccumuler());
	}

	@Test
	public void testResetSkills() {
		chat.getProprete().increase(50);
		chat.getDiscipline().increase(30);
		chat.getObeissance().increase(20);
		chat.resetSkills();
		assertEquals(0, chat.getProprete().getaccumuler());
		assertEquals(0, chat.getDiscipline().getaccumuler());
		assertEquals(0, chat.getObeissance().getaccumuler());
	}

	//Punition et recomp

	@Test
	public void testFrapperDeplaceMaitre() {
		// Animal en (3,3), maitre en (8,8) -> apres FRAPPER matre en (3,3)
		PunitionRecompenseManager manager = new PunitionRecompenseManager();
		chat.setPosition(room.getCells()[3][3]);

		manager.calculerAction(PunitionRecompenseType.FRAPPER, maitre, chat);

		assertEquals(PunitionRecompenseType.FRAPPER, manager.getActionCourante());
		assertEquals(3, maitre.getPosition().getLine());
		assertEquals(3, maitre.getPosition().getColumn());
	}

	@Test
	public void testCaresserDeplaceMaitre() {
		// Animal en (5,5) -> apres CARESSER maitre en (5,5)
		PunitionRecompenseManager manager = new PunitionRecompenseManager();
		chat.setPosition(room.getCells()[5][5]);

		manager.calculerAction(PunitionRecompenseType.CARESSER, maitre, chat);

		assertEquals(PunitionRecompenseType.CARESSER, manager.getActionCourante());
		assertEquals(5, maitre.getPosition().getLine());
		assertEquals(5, maitre.getPosition().getColumn());
	}

	@Test
	public void testDireStopMaitreImmobile() {
		// DIRE_STOP : maitre reste en (8,8)
		PunitionRecompenseManager manager = new PunitionRecompenseManager();

		manager.calculerAction(PunitionRecompenseType.DIRE_STOP, maitre, chat);

		assertEquals(PunitionRecompenseType.DIRE_STOP, manager.getActionCourante());
		assertEquals(8, maitre.getPosition().getLine());
		assertEquals(8, maitre.getPosition().getColumn());
	}

	@Test
	public void testReprimandeMaitreImmobile() {
		// REPRIMANDE : maitre reste en (8,8)
		PunitionRecompenseManager manager = new PunitionRecompenseManager();

		manager.calculerAction(PunitionRecompenseType.REPRIMANDE, maitre, chat);

		assertEquals(PunitionRecompenseType.REPRIMANDE, manager.getActionCourante());
		assertEquals(8, maitre.getPosition().getLine());
		assertEquals(8, maitre.getPosition().getColumn());
	}

	@Test
	public void testEncouragerMaitreImmobile() {
		// ENCOURAGER : maitre reste en (8,8)
		PunitionRecompenseManager manager = new PunitionRecompenseManager();

		manager.calculerAction(PunitionRecompenseType.ENCOURAGER, maitre, chat);

		assertEquals(PunitionRecompenseType.ENCOURAGER, manager.getActionCourante());
		assertEquals(8, maitre.getPosition().getLine());
		assertEquals(8, maitre.getPosition().getColumn());
	}

	@Test
	public void testPunitionManagerReset() {
		// Apres reset : maitre revient en (8,8)
		PunitionRecompenseManager manager = new PunitionRecompenseManager();
		Cell posInitiale = room.getCells()[8][8];

		manager.calculerAction(PunitionRecompenseType.FRAPPER, maitre, chat);
		manager.reset(maitre, posInitiale);

		assertEquals(8, maitre.getPosition().getLine());
		assertEquals(8, maitre.getPosition().getColumn());
	}

	//objets 

	@Test
	public void testGameObjectPosition() {
		Cell c = room.getCells()[2][3];
		Canape canape = new Canape(c);
		assertEquals(c, canape.getPosition());
		assertEquals("Canape", canape.getName());
	}

	@Test
	public void testGameObjectAllowedRoom() {
		Cell c = room.getCells()[0][0];
		Canape canape = new Canape(c);
		assertTrue(canape.isAllowedInRoom(RoomType.SALON));
	}

	@Test
	public void testRoomAddObject() {
		Cell c = room.getCells()[3][3];
		Canape canape = new Canape(c);
		room.addObject(canape);
		assertEquals("Canape", room.getCells()[3][3].getObject().getName());
	}
}