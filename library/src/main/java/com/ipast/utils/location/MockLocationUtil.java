package com.ipast.utils.location;

import static android.os.Build.VERSION_CODES.LOLLIPOP_MR1;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.provider.Settings;

/**
 * @author gang.cheng
 * @description : 检测模拟位置
 * @date :2021/8/6
 */
public class MockLocationUtil {

    /**
     * @param context
     * @return 是否允许模拟位置
     */
    public static boolean isAllowMockLocation(Activity context) {
        boolean canMockLocation;
        if (Build.VERSION.SDK_INT <= LOLLIPOP_MR1) {
            canMockLocation = Settings.Secure.getInt(context.getContentResolver(), "mock_location", 0) != 0;
        } else {
            try {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                String providerStr = LocationManager.GPS_PROVIDER;
                LocationProvider provider = locationManager.getProvider(providerStr);
                if (provider != null) {
                    locationManager.addTestProvider(
                            provider.getName()
                            , provider.requiresNetwork()
                            , provider.requiresSatellite()
                            , provider.requiresCell()
                            , provider.hasMonetaryCost()
                            , provider.supportsAltitude()
                            , provider.supportsSpeed()
                            , provider.supportsBearing()
                            , provider.getPowerRequirement()
                            , provider.getAccuracy());
                } else {
                    locationManager.addTestProvider(
                            providerStr
                            , true, true, false, false, true, true, true
                            , Criteria.POWER_HIGH, Criteria.ACCURACY_FINE);
                }
                locationManager.setTestProviderEnabled(providerStr, true);
                locationManager.setTestProviderStatus(providerStr, LocationProvider.AVAILABLE, null, System.currentTimeMillis());
                canMockLocation = true;
            } catch (SecurityException e) {
                canMockLocation = false;
            }
        }
        return canMockLocation;
    }
}
