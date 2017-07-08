package com.example.juda.weathy;

import android.content.Context;
import android.util.Log;

import com.example.juda.weathy.R;
import com.example.juda.weathy.data.WeathyPref;

/**
 * Created by Juda on 24/06/2017.
 */

public final class WeatherConvert {

    private static double celsiusToFahrenheit(double celsius) {
        double fahrenheit = ((9/5) * celsius) + 32;
        return fahrenheit;
    }

    public static String formatTemp(Context context, double temp) {
        int temperatureFormatResourceId = R.string.celcius_format;

        if (!WeathyPref.isMetric(context)) {
            temp = celsiusToFahrenheit(temp);
            temperatureFormatResourceId = R.string.fahrenheit_format;
        }
        return String.format(context.getString(temperatureFormatResourceId), temp);
    }
    //The display of format's temperature by high and low
    public static String formatHighAndLowTemperatures (Context context, double highTemp, double lowTemp) {
        long roundedHigh = Math.round(highTemp);
        long roundedLow = Math.round(lowTemp);

        String formattedHigh = formatTemp(context, roundedHigh);
        String formattedLow = formatTemp(context, roundedLow);

        String showHighLow = formattedHigh + " / " + formattedLow;
        return showHighLow;
    }

    public static String getFormattedWindSpeed (Context context, float windSpeed, float degrees) {
        int windFormat = R.string.format_wind_kmh;//need to be updated on string values sheet

        if (!WeathyPref.isMetric(context)) {
            windFormat = R.string.format_wind_mph;
            windSpeed = 0.621371192237334f * windSpeed;
        }

        String windDirection = "";
        if (degrees >= 348.75 || degrees < 11.25) {
            windDirection = "N";
        } else if (degrees >= 11.25 && degrees < 33.75) {
            windDirection = "NNE";
        } else if (degrees >= 33.75 && degrees < 56.25) {
            windDirection = "NE";
        } else if (degrees >= 56.25 && degrees < 78.75) {
            windDirection = "ENE";
        } else if (degrees >= 78.75 && degrees < 101.25) {
            windDirection = "E";
        } else if (degrees >= 101.25 && degrees < 123.75) {
            windDirection = "ESE";
        } else if (degrees >= 123.75 && degrees < 146.25) {
            windDirection = "SE";
        } else if (degrees >= 146.25 && degrees < 168.75) {
            windDirection = "SSE";
        } else if (degrees >= 168.75 && degrees < 191.25) {
            windDirection = "S";
        } else if (degrees >= 191.25 && degrees < 213.75) {
            windDirection = "SSW";
        } else if (degrees >= 213.75 && degrees < 236.25) {
            windDirection = "SW";
        } else if (degrees >= 236.25 && degrees < 258.75) {
            windDirection = "WSW";
        } else if (degrees >= 258.75 && degrees < 281.25) {
            windDirection = "W";
        } else if (degrees >= 281.25 && degrees < 303.75) {
            windDirection = "WNW";
        } else if (degrees >= 303.75 && degrees < 326.25) {
            windDirection = "NW";
        } else if (degrees >= 326.25 && degrees < 348.75) {
            windDirection = "NNW";
        }
        return String.format(context.getString(windFormat), windSpeed, windDirection);
    }

    public static String getStringForWeatherCondition(Context context, int weatherId) {
        int stringId;
        switch (weatherId) {
            case 200:
                stringId = R.string.condition_200;
                break;
            case 201:
                stringId = R.string.condition_201;
                break;
            case 202:
                stringId = R.string.condition_202;
                break;
            case 210:
                stringId = R.string.condition_210;
                break;
            case 211:
                stringId = R.string.condition_211;
                break;
            case 212:
                stringId = R.string.condition_212;
                break;
            case 221:
                stringId = R.string.condition_221;
                break;
            case 230:
                stringId = R.string.condition_230;
                break;
            case 231:
                stringId = R.string.condition_231;
                break;
            case 232:
                stringId = R.string.condition_232;
                break;
            case 300:
                stringId = R.string.condition_300;
                break;
            case 301:
                stringId = R.string.condition_301;
                break;
            case 302:
                stringId = R.string.condition_302;
                break;
            case 310:
                stringId = R.string.condition_310;
                break;
            case 311:
                stringId = R.string.condition_311;
                break;
            case 312:
                stringId = R.string.condition_312;
                break;
            case 313:
                stringId = R.string.condition_313;
                break;
            case 314:
                stringId = R.string.condition_314;
                break;
            case 321:
                stringId = R.string.condition_321;
                break;
            case 500:
                stringId = R.string.condition_500;
                break;
            case 501:
                stringId = R.string.condition_501;
                break;
            case 502:
                stringId = R.string.condition_502;
                break;
            case 503:
                stringId = R.string.condition_503;
                break;
            case 504:
                stringId = R.string.condition_504;
                break;
            case 511:
                stringId = R.string.condition_511;
                break;
            case 520:
                stringId = R.string.condition_520;
                break;
            case 531:
                stringId = R.string.condition_531;
                break;
            case 600:
                stringId = R.string.condition_600;
                break;
            case 601:
                stringId = R.string.condition_601;
                break;
            case 602:
                stringId = R.string.condition_602;
                break;
            case 611:
                stringId = R.string.condition_611;
                break;
            case 612:
                stringId = R.string.condition_612;
                break;
            case 615:
                stringId = R.string.condition_615;
                break;
            case 616:
                stringId = R.string.condition_616;
                break;
            case 620:
                stringId = R.string.condition_620;
                break;
            case 621:
                stringId = R.string.condition_621;
                break;
            case 622:
                stringId = R.string.condition_622;
                break;
            case 701:
                stringId = R.string.condition_701;
                break;
            case 711:
                stringId = R.string.condition_711;
                break;
            case 721:
                stringId = R.string.condition_721;
                break;
            case 731:
                stringId = R.string.condition_731;
                break;
            case 741:
                stringId = R.string.condition_741;
                break;
            case 751:
                stringId = R.string.condition_751;
                break;
            case 761:
                stringId = R.string.condition_761;
                break;
            case 762:
                stringId = R.string.condition_762;
                break;
            case 771:
                stringId = R.string.condition_771;
                break;
            case 781:
                stringId = R.string.condition_781;
                break;
            case 800:
                stringId = R.string.condition_800;
                break;
            case 801:
                stringId = R.string.condition_801;
                break;
            case 802:
                stringId = R.string.condition_802;
                break;
            case 803:
                stringId = R.string.condition_803;
                break;
            case 804:
                stringId = R.string.condition_804;
                break;
            case 900:
                stringId = R.string.condition_900;
                break;
            case 901:
                stringId = R.string.condition_901;
                break;
            case 902:
                stringId = R.string.condition_902;
                break;
            case 903:
                stringId = R.string.condition_903;
                break;
            case 904:
                stringId = R.string.condition_904;
                break;
            case 905:
                stringId = R.string.condition_905;
                break;
            case 906:
                stringId = R.string.condition_906;
                break;
            case 951:
                stringId = R.string.condition_951;
                break;
            case 952:
                stringId = R.string.condition_952;
                break;
            case 953:
                stringId = R.string.condition_953;
                break;
            case 954:
                stringId = R.string.condition_954;
                break;
            case 955:
                stringId = R.string.condition_955;
                break;
            case 956:
                stringId = R.string.condition_956;
                break;
            case 957:
                stringId = R.string.condition_957;
                break;
            case 958:
                stringId = R.string.condition_958;
                break;
            case 959:
                stringId = R.string.condition_959;
                break;
            case 960:
                stringId = R.string.condition_960;
                break;
            case 961:
                stringId = R.string.condition_961;
                break;
            case 962:
                stringId = R.string.condition_962;
                break;
            default:
                return context.getString(R.string.condition_unknown, weatherId);
        }
        return context.getString(stringId);
    }

    public static int getSmallArtResourceIdForWeatherCondition(int weatherId) {
        if (weatherId == 200 || weatherId == 230) {
            return R.drawable.thunderstorm_light_rain;
        } else if (weatherId == 201 || weatherId == 232 || weatherId == 504) {
            return R.drawable.thunderstorm_rain;
        } else if (weatherId == 202 || weatherId == 961) {
            return R.drawable.thunderstorm_heavy_rain;
        } else if (weatherId == 210 || weatherId == 221) {
            return R.drawable.light_thunderstorm;
        } else if (weatherId == 211 || weatherId == 958) {
            return R.drawable.thunderstorm;
        } else if (weatherId == 212 || weatherId == 959) {
            return R.drawable.heavy_thunderstorm;
        } else if (weatherId == 231 || weatherId == 503) {
            return R.drawable.thhunderstorm_drizzle;
        } else if (weatherId == 300 || weatherId == 310) {
            return R.drawable.light_drizzle;
        } else if (weatherId == 301 || weatherId == 313 || weatherId == 321 || weatherId == 531) {
            return R.drawable.drizzle;
        } else if (weatherId == 302 || weatherId == 312) {
            return R.drawable.heavy_drizzle;
        } else if (weatherId == 311 || weatherId == 500 || weatherId == 520) {
            return R.drawable.light_rain;
        } else if (weatherId == 314 || weatherId == 501 || weatherId == 521) {
            return R.drawable.rain;
        } else if (weatherId == 502 || weatherId == 522) {
            return R.drawable.heavy_rain;
        } else if (weatherId == 511) {
            return R.drawable.freezing_rain;
        } else if (weatherId == 600 || weatherId == 620) {
            return R.drawable.light_snow;
        } else if (weatherId == 601 || weatherId == 621 || weatherId == 903) {
            return R.drawable.snow;
        } else if (weatherId == 602 || weatherId == 622) {
            return R.drawable.heavy_snow;
        } else if (weatherId == 611 || weatherId == 612) {
            return R.drawable.sleet;
        } else if (weatherId == 615 || weatherId == 616) {
            return R.drawable.rain_and_snow;
        } else if (weatherId == 701) {
            return R.drawable.mist;
        } else if (weatherId == 711) {
            return R.drawable.smoke_day;
        } else if (weatherId == 721) {
            return R.drawable.haze_day;
        } else if (weatherId == 731) {
            return R.drawable.dust_whirls;
        } else if (weatherId == 741) {
            return R.drawable.fog_day;
        } else if (weatherId == 751 || weatherId == 904) {
            return R.drawable.sand_day;
        } else if (weatherId == 761) {
            return R.drawable.dust;
        } else if (weatherId == 762) {
            return R.drawable.volcanic_ash;
        } else if (weatherId == 771 || weatherId == 905 || weatherId == 957) {
            return R.drawable.windy;
        } else if (weatherId == 781 || weatherId == 900) {
            return R.drawable.tornado;
        } else if (weatherId == 800 || weatherId == 951) {
            return R.drawable.clear_day;
        } else if (weatherId == 801 || weatherId == 952 || weatherId == 953) {
            return R.drawable.few_clouds;
        } else if (weatherId == 802 || weatherId == 955) {
            return R.drawable.scattered_clouds;
        } else if (weatherId == 803 || weatherId == 954) {
            return R.drawable.broken_clouds;
        } else if (weatherId == 804 || weatherId == 956) {
            return R.drawable.overcast_clouds;
        } else if (weatherId == 901 || weatherId == 902 || weatherId == 962) {
            return R.drawable.tropical_storm;
        } else if (weatherId == 906 || weatherId == 960) {
            return R.drawable.hail_storm;
        }
        return R.drawable.hail_storm;
    }

    //public static int getLargeArtResourceIdForWeatherCondition(int weatherId) {

   // }
}
