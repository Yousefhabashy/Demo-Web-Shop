package Data;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;

import java.util.Random;

public class TestData {

    static Faker faker = new Faker();
    public static String generateFirstName() {
        return faker.name().firstName();
    }
    public static String generateLastName() {
        return faker.name().lastName();
    }
    public static String generateEmail() {
        return faker.internet().emailAddress();
    }
    public static String generateTelephoneNumber() {
        return faker.phoneNumber().cellPhone();
    }
    public static String generateFax() {
        return faker.phoneNumber().subscriberNumber(8);
    }
    public static String generateCompany() {
        return faker.company().name();
    }
    public static String generateAddress() {
        return faker.address().fullAddress();
    }
    public static String generateCity() {
        return faker.address().city();
    }

    public static String generatePostalCode() {
        return String.format("%05d", faker.number().numberBetween(0, 999));
    }
    public static String generateLoginName(String firstName, String lastName) {
        String number =  String.format("%03d", faker.number().numberBetween(0, 999));
        return firstName + lastName + number;
    }
    public static String generatePassword() {
        return faker.internet().password(10, 20);
    }
    public static String generateMaserCard() {
        return faker.finance().creditCard(CreditCardType.MASTERCARD);
    }
    public static String generateVisaCard() {
        return faker.finance().creditCard(CreditCardType.VISA);
    }
    public static int generateExpiryMonth() {
        Random rand = new Random();
        String month = String.format("%02d", rand.nextInt(12) + 1);
        return Integer.parseInt(month);
    }
    public static int generateExpiryYear() {
        Random rand = new Random();
        return rand.nextInt(7) + 2026;
    }
    public static String generateCVV() {
        return String.format("%03d", faker.number().numberBetween(0, 999));
    }
    public static String generateReview() {
        return faker.lorem().sentence(18);
    }
    public static String generateRate() {
        return faker.lorem().paragraph(6);
    }
}
