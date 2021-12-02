# PermissionManager

### Configuration

1. In your app folder create libs folder and put .aar file
<p>
<img src="https://github.com/MH-projects/PermissionManagerDemo/blob/master/images/libs.png">
</p>

2. In your build.gradle (app) add fileTree
```gladle
dependencies {
        ...
        implementation fileTree(include: ['*.jar', '*.aar'], dir: 'libs')
	...
}
```

### Usage

Put your permissions in your Manifest
```kotlin
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

<uses-permission android:name="android.permission.CAMERA" />

<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

<uses-permission android:name="android.permission.RECORD_AUDIO" />

<uses-permission android:name="android.permission.READ_CONTACTS" />
```

In your Activity or Fragment implement the interface *IPermissionResult*

#### Permission Individual

Call the method
```kotlin
PermissionManager(Activity, Interface).reqPermission(Manifest.permission.PERMISSION)
```

Get Result
```kotlin
override fun setResult(permissionResult: PermissionResult) {

        when (permissionResult.result) {
            Result.GRANTED -> {
                showAlert(permissionResult.permissionName, "Permiso otorgado")
            }

            Result.DENIED -> {
                showAlert(permissionResult.permissionName, "Permiso denegado")
            }

            Result.DENIED_ALWAYS -> {
                showAlert(permissionResult.permissionName, "Permiso denegado por siempre")
            }
        }
    }
```

| Result | Description  |
| ------- |  --- |
| GRANTED | Permission granted |
| DENIED | Permission denied |
| DENIED_ALWAYS | Permission denied and don´t ask again |

#### Multiples Permissions
Call the method
```kotlin
private var permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_CONTACTS
    )
```
```kotlin
PermissionManager(this, this).reqPermissions(permissions)
```
Get Result
```kotlin
 override fun setResult(
        allPermissionsGranted: Boolean,
        canAskPermissions: Boolean,
        permissionsResult: List<PermissionResult>
    ) {
        if (allPermissionsGranted) {
            // TODO
        } else {
            // TODO
        }
    }
```

If allPermissionsGranted is true, all permissions have been granted and action can be taken

If allPermissionsGranted is false, we can iterate the list permissionsResult and get individual result

If canAskPermissions is true there are permissions to request

If canAskPermissions is false can't ask again, you need to open the settings to grant the permissions manually
