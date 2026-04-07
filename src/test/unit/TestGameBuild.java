package test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import engine.map.Cell;
import engine.map.Room;
import engine.map.RoomType;
import engine.mobile.Animal;
import engine.mobile.AnimalFactory;
import engine.mobile.AnimalType;
import engine.mobile.Chat;
import engine.mobile.Chien;
import engine.mobile.Maitre;
import engine.process.GameBuilder;

/**
 * Test unitaire de la construction du jeu.
 * On vérifie la Room, l'Animal, le Maitre et les Skills initiaux.
 * 
 * Résultats calculés à la main :
 * - Room = 16x16 = 256 cellules
 * - Cellule (0,0) = SALON, (0,8) = CHAMBRE, (8,0) = JARDIN, (8,8) = CUISINE
 * - Chat : propreté initiale = 20
 * - Chien : propreté initiale = 0
 * - Maitre position initiale = (8, 8)
 */
public class TestGameBuild {

	private Room room;
	private Animal chat;
	private Animal chien;
	private Maitre maitre;

	@Before
	public void prepareGame() {
		room = GameBuilder.buildRoom();
		chat = GameBuilder.buildAnimal(room, AnimalType.CHAT, "Minou");
		chien = GameBuilder.buildAnimal(room, AnimalType.CHIEN, "Rex");
		maitre = GameBuilder.buildMaitre(room);
	}

	// ===================== ROOM =====================

	@Test
	public void testRoomDimensions() {
		Cell[][] cells = room.getCells();
		assertEquals(16, cells.length);
		assertEquals(16, cells[0].length);
	}

	@Test
	public void testRoomTypeSalon() {
		Cell cell = room.getCells()[0][0];
		assertEquals(RoomType.SALON, cell.getRoomType());
	}

	@Test
	public void testRoomTypeChambre() {
		Cell cell = room.getCells()[8][8];
		assertEquals(RoomType.CHAMBRE, cell.getRoomType());
	}

	@Test
	public void testRoomTypeJardin() {
		Cell cell = room.getCells()[8][0];
		assertEquals(RoomType.JARDIN, cell.getRoomType());
	}

	@Test
	public void testRoomTypeCuisine() {
		Cell cell = room.getCells()[0][8];
		assertEquals(RoomType.CUISINE, cell.getRoomType());
	}

	// animal factory

	@Test
	public void testFactoryChat() {
		Cell c = room.getCells()[0][0];
		Animal a = AnimalFactory.create(AnimalType.CHAT, "Felix", c);
		assertTrue(a instanceof Chat);
		assertEquals(AnimalType.CHAT, a.getType());
	}

	@Test
	public void testFactoryChien() {
		Cell c = room.getCells()[0][0];
		Animal a = AnimalFactory.create(AnimalType.CHIEN, "Rex", c);
		assertTrue(a instanceof Chien);
		assertEquals(AnimalType.CHIEN, a.getType());
	}

	//Position de l'animal

	@Test
	public void testAnimalPositionInitiale() {
		assertEquals(0, chat.getPosition().getLine());
		assertEquals(0, chat.getPosition().getColumn());
	}

	@Test
	public void testAnimalMoveTo() {
		Cell destination = room.getCells()[3][5];
		chat.moveTo(destination);
		assertEquals(3, chat.getPosition().getLine());
		assertEquals(5, chat.getPosition().getColumn());
	}

	// ===================== SKILLS INITIAUX =====================

	@Test
	public void testChatPropreteInitiale() {
		assertEquals(20, chat.getProprete().getaccumuler());
	}

	@Test
	public void testChienPropreteInitiale() {
		assertEquals(0, chien.getProprete().getaccumuler());
	}

	@Test
	public void testDisciplineInitiale() {
		assertEquals(0, chat.getDiscipline().getaccumuler());
		assertEquals(0, chien.getDiscipline().getaccumuler());
	}

	@Test
	public void testObeissanceInitiale() {
		assertEquals(0, chat.getObeissance().getaccumuler());
		assertEquals(0, chien.getObeissance().getaccumuler());
	}

	//Maitre

	@Test
	public void testMaitrePositionInitiale() {
		assertEquals(8, maitre.getPosition().getLine());
		assertEquals(8, maitre.getPosition().getColumn());
	}

	@Test
	public void testMaitreDeplacerVersAnimal() {
		Cell dest = room.getCells()[5][3];
		chat.moveTo(dest);
		maitre.deplacerVersAnimal(chat);
		assertEquals(5, maitre.getPosition().getLine());
		assertEquals(3, maitre.getPosition().getColumn());
	}
}