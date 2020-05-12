package GameSystems.NumberSystem;

/**
 *  @brief Class that manages large values and transforms them into more user-friendly text.
 * <p>
 * This is done by appending units of measurement corresponding to the size of the original number.
 * <ul>
 *     <li> Kilo = 10^3  </li>
 *     <li> Mega = 10^6  </li>
 *     <li> Giga = 10^9  </li>
 *     <li> Tera = 10^12 </li>
 *     <li> Peta = 10^15 </li>
 *     <li> Exa = 10^18  </li>
 * </ul>
 */
public class LargeNumberHandler {
    private final static String[] units = {" K", " M", " G", " T", " P", " E"};///< Array that holds all units of measurement.

    /**
     * Returns a string representing the transformed version of the large number.
     *
     * @param value A long integer
     */
    public static String ParseLongInt(long value) {
        String ans;
        if (value >= 1000) {
            double fval = value;
            int i = 0;
            fval /= 1000.0;
            while (fval >= 1000) {
                fval /= 1000.0;
                ++i;
            }
            ans = String.valueOf(fval);
            // Cut the string if it has too many digits
            if (ans.length() > 5) {
                ans = ans.substring(0, 5);
            }
            // Add units of measurement
            ans += units[i];
        } else {
            ans = String.valueOf(value);
        }
        return ans;
    }
}
