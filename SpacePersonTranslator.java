import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SpacePersonTranslator {
    private static final Map<Character, Character> spacePersonAlphabet = new HashMap<>();

    static {
        char[] symbols = new char[]{'~', '!', '@', '#', '$', '%', '^', '&', '*', '('};
        for(int i = 0; i < 26; ++i) {
            spacePersonAlphabet.put((char) ('a' + i), symbols[i % symbols.length]);
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter an English Text: ");
        String englishString = input.nextLine().toLowerCase();

        String spacePersonString = convertToSpacePerson(englishString);
        System.out.println("Space Person Text: " + spacePersonString);

        String hashValue = bytesToHex(computeSHA256(spacePersonString));
        System.out.println("SHA-256 Hash of Space Person Text: " + hashValue);

        String shiftedString = caesarCipher(englishString, 5);
        System.out.println("English Text after 5-character Caesar shift: " + shiftedString);

        System.out.println("All Caesar cipher shifts:");
        for (int i = 0; i < 26; ++i) {
            System.out.println(i + " shift: " + caesarCipher(englishString, i));
        }
    }

    private static String convertToSpacePerson(String englishString) {
        StringBuilder spacePersonBuilder = new StringBuilder();
        for (char c : englishString.toCharArray()) {
            if (spacePersonAlphabet.containsKey(c)) {
                spacePersonBuilder.append(spacePersonAlphabet.get(c));
            } else {
                spacePersonBuilder.append(c);
            }
        }
        return spacePersonBuilder.toString();
    }

    private static byte[] computeSHA256(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(input.getBytes());
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private static String caesarCipher(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (Character.isLetter(text.charAt(i))) {
                char base = Character.isLowerCase(text.charAt(i)) ? 'a' : 'A';
                int letterShift = (text.charAt(i) - base + shift) % 26;
                result.append((char) (base + letterShift));
            } else {
                result.append(text.charAt(i));
            }
        }
        return result.toString();
    }
}