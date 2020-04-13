package GameSystems.StatsSystem;

public class LargeNumberHandler {
    /*
    Kilo = 10^3
    Mega = 10^6
    Giga = 10^9
    Tera = 10^12
    Peta = 10^15
    Exa = 10^18

    Long.MAX_VALUE = 9.223 E
    */
    private final static String[] units = {"", " K", " M", " G", " T", " P", " E"};

    public static String ParseLongInt(long value) {
        String ans;
        if (value >= 1000) {
            double fval = value;
            int i = 0;
            while (fval >= 1000) {
                fval /= 1000.0;
                ++i;
            }
            ans = String.valueOf(fval);
            //cut string if too many digits
            if (ans.length() > 6) {
                ans = ans.substring(0, 6);
            }
            //add units in case the value > 1000
            ans += units[i];
        }else{
            ans=String.valueOf(value);
        }
        return ans;
    }
}
