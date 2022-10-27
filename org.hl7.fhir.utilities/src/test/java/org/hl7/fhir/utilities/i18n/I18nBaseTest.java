package org.hl7.fhir.utilities.i18n;

import com.ibm.icu.text.PluralRules;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class I18nBaseTest {

  public static final String BAD_STRING_ARG = "THIS_DOES_NOT_EXIST";
  public static final String ARG_1 = "test arg";

  @Test
  @DisplayName("Test argument substitution with initializing Locale.")
  void testFormatMessageWithInitLocale() {
    I18nTestClass testClass = new I18nTestClass();
    ResourceBundle loadedBundle = ResourceBundle.getBundle("Messages", Locale.GERMAN);
    testClass.setLocale(Locale.GERMAN);
    String result = testClass.formatMessage(I18nConstants.BUNDLE_BUNDLE_MULTIPLEMATCHES, ARG_1);
    MessageFormat form = new MessageFormat(loadedBundle.getString(I18nConstants.BUNDLE_BUNDLE_MULTIPLEMATCHES));
    Object[] testArgs = {ARG_1};
    Assertions.assertEquals(form.format(testArgs), result);
  }

  @Test
  @DisplayName("Test argument substitution without initializing Locale.")
  void testFormatMessageWithoutInitLocale() {
    I18nTestClass testClass = new I18nTestClass();
    ResourceBundle loadedBundle = ResourceBundle.getBundle("Messages", Locale.US);
    String result = testClass.formatMessage(I18nConstants.BUNDLE_BUNDLE_MULTIPLEMATCHES, ARG_1);
    MessageFormat form = new MessageFormat(loadedBundle.getString(I18nConstants.BUNDLE_BUNDLE_MULTIPLEMATCHES));
    Object[] testArgs = {ARG_1};
    Assertions.assertEquals(form.format(testArgs), result);
  }

  @Test
  @DisplayName("Test pluralization works without initializing Locale.")
  void testFormatMessagePluralWithoutInitLocale() {
    I18nTestClass testClass = new I18nTestClass();

    //Answer value must be of the type {1}
    String resultOne = testClass.formatMessagePlural(1, I18nConstants.QUESTIONNAIRE_QR_ITEM_WRONGTYPE_PLURAL);
    assertThat(resultOne, containsString("be of the type"));

    //Answer value must be one of the {0} types {1}
    String resultMany = testClass.formatMessagePlural(3, I18nConstants.QUESTIONNAIRE_QR_ITEM_WRONGTYPE_PLURAL);
    assertThat(resultMany, containsString("one of the 3 types "));

  }

  @Test
  @DisplayName("Test pluralization works with initializing Locale.")
  void testFormatMessagePluralWithInitLocale() {
    I18nTestClass testClass = new I18nTestClass();

    testClass.setLocale(Locale.GERMAN);
    //Answer value muss vom Typ {0} sein.
    String resultOne = testClass.formatMessagePlural(1, I18nConstants.QUESTIONNAIRE_QR_ITEM_WRONGTYPE_PLURAL);
    assertThat(resultOne, containsString("muss vom Typ"));

    //Answer value muss einer der Typen {1} sein
    String resultMany = testClass.formatMessagePlural(3, I18nConstants.QUESTIONNAIRE_QR_ITEM_WRONGTYPE_PLURAL);
    assertThat(resultMany, containsString("einer der Typen "));

  }

  @Test
  @DisplayName("Assert no string modification is done when no match is found.")
  void testFormatMessageForNonExistentMessage() {
    I18nTestClass testClass = new I18nTestClass();
    Assertions.assertEquals(BAD_STRING_ARG, testClass.formatMessage(BAD_STRING_ARG, ARG_1));
  }

  @Test
  @DisplayName("Test umlauts display correctly.")
  void testTheGermansAreComing() {
    I18nTestClass testClass = new I18nTestClass();
    ResourceBundle loadedBundle = ResourceBundle.getBundle("Messages", Locale.GERMAN);
    testClass.setLocale(Locale.GERMAN);
    String result = testClass.formatMessage(I18nConstants.BUNDLE_BUNDLE_ENTRY_NOFIRST, ARG_1);
    //Ensure the umlaut is displayed correctly. If not right, will show: Ã¼, not ü
    Assertions.assertEquals("Documents oder Messages müssen mindestens einen Eintrag enthalten", result);
  }

  @Test
  @DisplayName("Test German localization file contains no umlauts.")
  void testThatNoOneHasMessedWithTheGermans() {
    I18nTestClass testClass = new I18nTestClass();
    InputStream is = getClass().getClassLoader().getResourceAsStream("Messages_de.properties");
    try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader)) {
        String line;
        while ((line = reader.readLine()) != null) {
//          System.out.println("Searching for umlauts -> " + line);
          assertFalse(stringContainsItemFromList(line, UMLAUTS));
        }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private final String[] UMLAUTS = {"Ä", "Ö", "Ü", "ä", "ö", "ü"};

  public static boolean stringContainsItemFromList(String inputStr, String[] items) {
    return Arrays.stream(items).anyMatch(inputStr::contains);
  }

  @Test
  void pluralKeysCompleteAndValid() {
    ResourceBundle loadedBundle = ResourceBundle.getBundle("Messages", Locale.GERMAN);
    PluralRules pluralRules = PluralRules.forLocale(Locale.GERMANY);
    for (String key : loadedBundle.keySet()) {
       String[] keyComponent = key.split(I18nBase.KEY_DELIMITER);

       assertFalse(keyComponent[keyComponent.length - 1].equalsIgnoreCase(I18nBase.PLURAL_SUFFIX), "Invalid use of PLURAL keyword for key: " + key);
       if (keyComponent.length > 2
        && keyComponent[keyComponent.length - 2].equalsIgnoreCase(I18nBase.PLURAL_SUFFIX)) {
          assertTrue(pluralRules.getKeywords().contains(keyComponent[keyComponent.length - 1]));
       }
    }
  }

}