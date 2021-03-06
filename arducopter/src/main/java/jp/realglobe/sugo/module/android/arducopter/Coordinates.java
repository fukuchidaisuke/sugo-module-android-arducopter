package jp.realglobe.sugo.module.android.arducopter;

import com.o3dr.services.android.lib.coordinate.LatLong;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;

import java.util.Arrays;
import java.util.List;

/**
 * 座標周りの便利関数。
 * Created by fukuchidaisuke on 16/12/05.
 */
final class Coordinates {

    private Coordinates() {
    }

    /**
     * JSON 互換形式に変換する
     *
     * @param latLong 座標
     * @return 座標を表す JSON 互換のデータ
     */
    static List<Object> encode(LatLong latLong) {
        return Arrays.asList(latLong.getLatitude(), latLong.getLongitude());
    }

    /**
     * JSON 互換形式に変換する
     *
     * @param latLongAlt 座標
     * @return 座標を表す JSON 互換のデータ
     */
    static List<Object> encode(LatLongAlt latLongAlt) {
        return Arrays.asList(latLongAlt.getLatitude(), latLongAlt.getLongitude(), latLongAlt.getAltitude());
    }

    /**
     * JSON 互換形式から変換する
     *
     * @param latLongAlt 座標を表す JSON 互換のデータ
     * @return 座標
     */
    static LatLongAlt decodeLatLongAlt(Object latLongAlt) {
        if (latLongAlt instanceof Object[]) {
            return decodeLatLongAlt((Object[]) latLongAlt);
        } else if (latLongAlt instanceof List<?>) {
            return decodeLatLongAlt((List<Object>) latLongAlt);
        } else {
            throw new IllegalArgumentException("unsupported class " + latLongAlt.getClass().getName());
        }
    }

    private static LatLongAlt decodeLatLongAlt(List<Object> latLongAlt) {
        if (latLongAlt.size() < 2) {
            throw new IllegalArgumentException("no latitude and longitude");
        } else if (latLongAlt.size() == 2) {
            return new LatLongAlt(
                    Numbers.decodeDouble(latLongAlt.get(0)),
                    Numbers.decodeDouble(latLongAlt.get(1)),
                    0
            );
        } else {
            return new LatLongAlt(
                    Numbers.decodeDouble(latLongAlt.get(0)),
                    Numbers.decodeDouble(latLongAlt.get(1)),
                    Numbers.decodeDouble(latLongAlt.get(2))
            );
        }
    }

    private static LatLongAlt decodeLatLongAlt(Object[] latLongAlt) {
        return decodeLatLongAlt(Arrays.asList(latLongAlt));
    }

}
