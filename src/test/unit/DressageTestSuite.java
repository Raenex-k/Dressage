package test.unit;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

/**
 * Suite de tests unitaires du projet Dressage.
 * 
 * Regroupe {@link TestGameBuild} et {@link TestGameLogic}.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({TestGameBuild.class, TestGameLogic.class})
public class DressageTestSuite {

}