package com.tsfapps.automotivevolvo

import android.app.Activity
import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView

/**
 * A simple activity that demonstrates connecting to car API and processing car property change
 * events.
 *
 * <p>Please see https://developer.android.com/reference/android/car/packages for API documentation.
 */
class MainActivity : Activity() {
    companion object {
        private const val TAG = "MainActivity"

        private const val GEAR_UNKNOWN = "GEAR_UNKNOWN"

        // Values are taken from android.car.hardware.CarSensorEvent class.
        private val VEHICLE_GEARS = mapOf(
            0x0000 to GEAR_UNKNOWN,
            0x0001 to "GEAR_NEUTRAL",
            0x0002 to "GEAR_REVERSE",
            0x0004 to "GEAR_PARK",
            0x0008 to "GEAR_DRIVE"
        )
    }

    private lateinit var currentGearTextView: TextView

    /** Car API. */
    private lateinit var car: Car

    /**
     * An API to read VHAL (vehicle hardware access layer) properties. List of vehicle properties
     * can be found in {@link VehiclePropertyIds}.
     *
     * <p>https://developer.android.com/reference/android/car/hardware/property/CarPropertyManager
     */
    private lateinit var carPropertyManager: CarPropertyManager

    private var carPropertyListener = object : CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(value: CarPropertyValue<Any>) {
            Log.d(TAG, "Received on changed car property event")
            // value.value type changes depending on the vehicle property.
            currentGearTextView.text = VEHICLE_GEARS.getOrDefault(value.value as Int, GEAR_UNKNOWN)
        }

        override fun onErrorEvent(propId: Int, zone: Int) {
            Log.w(TAG, "Received error car property event, propId=$propId")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentGearTextView = findViewById(R.id.currentGearTextView)

        // createCar() returns a "Car" object to access car service APIs. It can return null if
        // car service is not yet ready but that is not a common case and can happen on rare cases
        // (for example car service crashes) so the receiver should be ready for a null car object.
        //
        // Other variants of this API allows more control over car service functionality (such as
        // handling car service crashes graciously). Please see the SDK documentation for this.
        car = Car.createCar(this)

        carPropertyManager = car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager;

        // Subscribes to the gear change events.
        carPropertyManager.registerCallback(
            carPropertyListener,
            VehiclePropertyIds.CURRENT_GEAR,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        car.disconnect()
    }
}